package com.example.aiapps;

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

public class StudentList extends AppCompatActivity {
    public  static int roll;
    StudenListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        RecyclerView recyclerView=findViewById(R.id.student_list_recylerview);
        //recyclerView=findViewById(R.id.student_list_recylerview);
        recyclerView.setHasFixedSize(true);
        adapter=new StudenListAdapter(this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manger=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manger);

        loadData();

    }
    public void loadData(){
        adapter.setItem(BatchListActivity.batch.getNumbber_of_students());
        adapter.notifyDataSetChanged();
    }


}
