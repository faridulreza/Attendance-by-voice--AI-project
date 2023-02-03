package com.example.aiapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewRecords2 extends AppCompatActivity {
    public static String date;
    RecyclerView recyclerView;
    ViewRecorListAdapter2 adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_records2);
        recyclerView=findViewById(R.id.viewrecords2_recycle_view);
        recyclerView.setHasFixedSize(true);
        adapter=new ViewRecorListAdapter2(this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manger=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manger);
        loadData();
    }
    public void loadData(){
                adapter.setItem(ViewRecords.records);
                adapter.notifyDataSetChanged();
    }
}
