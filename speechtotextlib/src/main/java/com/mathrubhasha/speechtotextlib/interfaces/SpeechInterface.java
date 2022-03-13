package com.mathrubhasha.speechtotextlib.interfaces;

public interface SpeechInterface {
    public void onStatusChange(String status);

    public void onResult(String result);
}
