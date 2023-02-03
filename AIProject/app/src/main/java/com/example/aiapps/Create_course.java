package com.example.aiapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Create_course extends AppCompatActivity {

    CourseDBHelper dao;
    ArrayList<Course> existting_courses=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);
        dao=new CourseDBHelper();
        read();
    }

    public void btnPressed(View view){
        create();
    }

    public void create(){

        EditText code=findViewById(R.id.batch_code);


        Course course=new Course(code.getText().toString());
        for(Course curcourse:existting_courses){
            if(curcourse.getCode().equals(course.getCode()))
            {
                Toast.makeText(this,"Course code already exist!",Toast.LENGTH_LONG).show();
                return;
            }
        }
        try {
            dao.add(course).addOnSuccessListener(v->{
                Toast.makeText(this,"Succecssfull",Toast.LENGTH_LONG).show();
            }).addOnFailureListener(er->{
                Toast.makeText(this,""+er.getMessage(),Toast.LENGTH_LONG).show();
            });
        } catch (Exception e) {
            Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void read() {
        try {
            dao.get().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<Course> courses = new ArrayList<>();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Course course = data.getValue(Course.class);
                        courses.add(course);
                    }
                    existting_courses.addAll(courses);
                    //adapter.setItem(batches);
                    //adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void btn2presswed(View view){
        Intent intent=new Intent(Create_course.this,CourseList.class);
        startActivity(intent);
    }
}