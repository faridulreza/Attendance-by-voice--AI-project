package com.example.aiapps;

public class Record {
    private String course_code;
    private String batch_code;
    private String date;

    public Record(String course_code, String batch_code, String date, int roll) {
        this.course_code = course_code;
        this.batch_code = batch_code;
        this.date = date;
        this.roll = roll;
    }
    public Record(){}

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getBatch_code() {
        return batch_code;
    }

    public void setBatch_code(String batch_code) {
        this.batch_code = batch_code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    private int roll;
}
