package com.example.aiapps;

public class VoiceSample {
    private String url;
    private String batch_code;
    private int roll_number;
    public VoiceSample(){}
    public VoiceSample(String url, String class_code, int roll_number) {
        this.url = url;
        this.batch_code = class_code;
        this.roll_number = roll_number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBatch_code() {
        return batch_code;
    }

    public void setBatch_code(String class_code) {
        this.batch_code = class_code;
    }

    public int getRoll_number() {
        return roll_number;
    }

    public void setRoll_number(int roll_number) {
        this.roll_number = roll_number;
    }
}
