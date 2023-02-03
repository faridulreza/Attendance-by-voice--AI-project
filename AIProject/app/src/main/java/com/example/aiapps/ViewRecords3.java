package com.example.aiapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ViewRecords3 extends AppCompatActivity {
    RecyclerView recyclerView;
    ViewRecordListAdapter3 adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_records3);
        recyclerView=findViewById(R.id.viewrecords3_recycle_view);
        recyclerView.setHasFixedSize(true);
        adapter=new ViewRecordListAdapter3(this);
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