package com.mathrubhasha.speechtotextlib;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import com.mathrubhasha.speechtotextlib.interfaces.SpeechInterface;

import java.util.ArrayList;

public class SpeechToText {
    public static final Integer RecordAudioRequestCode = 1;
    private SpeechRecognizer speechRecognizer;
    SpeechInterface speechInterface;
    Context context;
    Intent speechRecognizerIntent;

    public SpeechToText(Context context, SpeechInterface speechInterface) {
        this.context = context;
        this.speechInterface = speechInterface;
        speechInitialization();
    }

    private void speechInitialization() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "te");

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
                speechInterface.onStatusChange("readyforspeech");
            }

            @Override
            public void onBeginningOfSpeech() {
                speechInterface.onStatusChange("beginningforspeech");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {
                speechInterface.onStatusChange("endofspeech");
            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                speechInterface.onStatusChange("complete");
                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                speechInterface.onResult(data.get(0));
            }

            @Override
            public void onPartialResults(Bundle bundle) {
                speechInterface.onStatusChange("partialresults");
                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                speechInterface.onResult(data.get(0));
            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });
    }

    public void startListening() {
        speechRecognizer.startListening(speechRecognizerIntent);
    }

    public void stopListening(){
        speechRecognizer.stopListening();
    }

    public void destroy() {
        speechRecognizer.stopListening();
    }

}

