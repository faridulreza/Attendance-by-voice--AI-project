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


def train_model():
    print("Training Model")
    dest = "./models/"
    files = glob.glob('Samples/*')

    features_all = dict()
    for path in files:
        path_split = path.split("/")
        path_split = path_split[1].split("_")
        path_split = path_split[0] + "_" + path_split[1]

        path = path.strip()

        sr, audio = read(path)
        f_vector = extract_features(audio, sr)

        if path_split not in features_all:
            features_all[path_split] = f_vector
        else:
            features_all[path_split] = np.vstack((features_all[path_split], f_vector))

    for key in features_all:
        gmm = GaussianMixture(n_components=6, max_iter=200, covariance_type='diag', n_init=3)
        gmm.fit(features_all[key])

        picklefile = key + ".gmm"
        pickle.dump(gmm, open(dest + picklefile, 'wb'))
        print('+ modeling completed for speaker:', picklefile, " with data point = ", features_all[key].shape)


def download():
    print("Downloading Files")

    bucket = storage.bucket(name="aiapps-2db00.appspot.com")
    all_list = bucket.list_blobs()
    for file in all_list:
        try:
            print(file.name)
            audio_file = bucket.get_blob(file.name)
            audio_file.download_to_filename(file.name)
            convert_to_wav(file.name)
        except Exception as e:

            print('Download Failed', e)
            return False
    return True


def convert_to_wav(file):
    print("Converting to wav")
    sound = (FFmpeg().input(file).output(file + '.wav'))
    sound.execute()
    os.remove(file)


def train():
    # clear the folder
    print("Clearing Folder")
    files = glob.glob('Samples/*')
    for f in files:
        os.remove(f)

    if download():
        print("Download Completed")
        print("Training Model")
        train_model()

        print("=====================================")
        print("Training Completed")

    else:
        print("Download Failed")
