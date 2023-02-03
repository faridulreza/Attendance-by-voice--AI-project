package com.example.aiapps;

public class BatchListModel {
    private String batch_code;
    private int number_of_students;

    public BatchListModel(String batch_code, int number_of_students) {
        this.batch_code = batch_code;
        this.number_of_students = number_of_students;
    }

    public String getBatch_code() {
        return batch_code;
    }

    public void setBatch_code(String batch_code) {
        this.batch_code = batch_code;
    }

    public int getNumber_of_students() {
        return number_of_students;
    }

    public void setNumber_of_students(int number_of_students) {
        this.number_of_students = number_of_students;
    }
}
