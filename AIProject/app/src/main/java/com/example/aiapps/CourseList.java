package com.example.aiapps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CourseList extends AppCompatActivity {

    RecyclerView recyclerView;

    CourseListAdapter adapter;
    public static Course course;
    CourseDBHelper dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        recyclerView=findViewById(R.id.courselist_recycle_view);
        recyclerView.setHasFixedSize(true);
        adapter=new CourseListAdapter(this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manger=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manger);

        dao=new CourseDBHelper();
        loadData();

    }
    public void loadData(){

        try{dao.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Course> courses=new ArrayList<>();
                for(DataSnapshot data:snapshot.getChildren()){
                    Course course=data.getValue(Course.class);
                    courses.add(course);
                }

                adapter.setItem(courses);
                adapter.notifyDataSetChanged();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });}catch (Exception e){
            Log.d("Heloo arnob", ""+e);
        }
    }
}