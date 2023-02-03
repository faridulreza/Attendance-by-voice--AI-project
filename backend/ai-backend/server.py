# Importing flask module in the project is mandatory
# An object of Flask class is our WSGI application.
from flask import Flask, request
from flask_cors import CORS
from firebase_admin import credentials, firestore, initialize_app, storage, db
from trainer import train
from time import time
import os
from ffmpeg import FFmpeg
from multiprocessing import Process
from tester import test

cred = credentials.Certificate("acc_key.json")
initialize_app(cred)
print("Firebase Initialized")

# Flask constructor takes the name of
# current module (__name__) as argument.

app = Flask(__name__)
CORS(app)

print("Flask Initialized")

trainer_proceess = None


# start training
@app.route('/train', methods=['GET'])
def start_train():
    global trainer_proceess
    # if trainning is already running
    if trainer_proceess is not None and trainer_proceess.is_alive():
        dct = dict()
        dct["status"] = "Training"

    print("Training Started")
    trainer_proceess = Process(target=train)
    trainer_proceess.start()
    return "Training Started"


@app.route("/test", methods=['POST', 'GET'])
def test_audio():
    global trainer_proceess
    # if trainning is already running
    if trainer_proceess is not None and trainer_proceess.is_alive():
        dct = dict()
        dct["status"] = "Training"
        return dct

    if request.method == "POST":
        print("Request Received")
        file = request.files['file']
        saved_file_name = "tests/test_" + str(int(time() * 1000.0)) + ".3gp"
        with open(saved_file_name, "wb") as f:
            file.save(f)

        # convert to wav
        print("Converting to wav")
        sound = (FFmpeg().input(saved_file_name).output(saved_file_name + '.wav'))
        sound.execute()
        os.remove(saved_file_name)
        return test(saved_file_name + '.wav')

    else:
        return {"status": "No File Received"}


# The route() function of the Flask class is a decorator,
# which tells the application which URL should call
# the associated function.
@app.route('/')
# ‘/’ URL is bound with hello_world() function.
def hello_world():
    return 'Hello World'


# main driver function
if __name__ == '__main__':
    # run() method of Flask class runs the application
    # on the local development server.
    app.run()
