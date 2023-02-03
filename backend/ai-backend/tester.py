from firebase_admin import storage
import glob
import os
import pickle
import numpy as np
from sklearn import preprocessing
from scipy.io.wavfile import read
import python_speech_features as mfcc
from sklearn.mixture import GaussianMixture
from ffmpeg import FFmpeg


def calculate_delta(array):
    rows, cols = array.shape
    print(rows)
    print(cols)
    deltas = np.zeros((rows, 20))
    N = 2
    for i in range(rows):
        index = []
        j = 1
        while j <= N:
            if i - j < 0:
                first = 0
            else:
                first = i - j
            if i + j > rows - 1:
                second = rows - 1
            else:
                second = i + j
            index.append((second, first))
            j += 1
        deltas[i] = (array[index[0][0]] - array[index[0][1]] + (2 * (array[index[1][0]] - array[index[1][1]]))) / 10
    return deltas


def extract_features(audio, rate):
    mfcc_feature = mfcc.mfcc(audio, rate, 0.025, 0.01, 20, nfft=1200, appendEnergy=True)
    mfcc_feature = preprocessing.scale(mfcc_feature)
    # print(mfcc_feature)
    delta = calculate_delta(mfcc_feature)
    combined = np.hstack((mfcc_feature, delta))
    return combined


def test(filepath):
    modelpath = "./models/"

    gmm_files = glob.glob(modelpath + '*.gmm')

    # Load the Gaussian gender Models
    models = [pickle.load(open(fname, 'rb')) for fname in gmm_files]

    try:
        sr, audio = read(filepath)
        vector = extract_features(audio, sr)

        log_likelihood = np.zeros(len(models))

        for i in range(len(models)):
            gmm = models[i]
            scores = np.array(gmm.score(vector))  # predict
            log_likelihood[i] = scores.sum()

        winner_scr = np.amax(log_likelihood)
        winner = np.argmax(log_likelihood)
        filename = gmm_files[winner].split("/")[-1].split(".gmm")[0].split("_")

        batch = filename[0]
        roll = filename[1]
        print("Detected as - ", batch, " : ", roll," score: ", winner_scr)
        os.remove(filepath)
        if winner_scr >= -30.00:
            return {"batch": batch, "roll": int(roll), "status": "OK"}
        else:
            return {"status": "OK", "batch": "Unknown", "roll": -1}
    except Exception as e:
        print(e)
        os.remove(filepath)
        return {"status": "ERROR"}
