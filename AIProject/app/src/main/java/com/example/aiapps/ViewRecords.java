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

public class ViewRecords extends AppCompatActivity {
    public static String course;
    RecyclerView recyclerView;
    ViewRecordsListAdapter adapter;
    RecordDBHelper dao;
    public static ArrayList<Record> records;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_records);
        recyclerView=findViewById(R.id.viewrecords_recycle_view);
        recyclerView.setHasFixedSize(true);
        adapter=new ViewRecordsListAdapter(this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manger=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manger);

        dao=new RecordDBHelper();
        loadData();
    }

    public void loadData(){

        try{dao.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                records=new ArrayList<>();
                for(DataSnapshot data:snapshot.getChildren()){
                    Record record=data.getValue(Record.class);
                    records.add(record);

                }
                Log.d("records", "RecordSize: "+records.toString());
                adapter.setItem(records);
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