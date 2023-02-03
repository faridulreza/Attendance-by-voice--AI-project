package com.example.aiapps;

public class Batch {
    private String code;
    private int numbber_of_students;
    //// necessary for firebase
    public Batch(){}
    public Batch(String code, int number_of_students){
        this.code=code;
        this.numbber_of_students=number_of_students;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getNumbber_of_students() {
        return numbber_of_students;
    }

    public void setNumbber_of_students(int numbber_of_students) {
        this.numbber_of_students = numbber_of_students;
    }
}
