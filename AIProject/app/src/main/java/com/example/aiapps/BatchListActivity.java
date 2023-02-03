package com.example.aiapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BatchListActivity extends AppCompatActivity {


    BatchListAdapter adapter;
    public static Batch batch;
    DAOBatch dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_list);
        RecyclerView recyclerView=findViewById(R.id.batch_listRV);
        recyclerView.setHasFixedSize(true);
        adapter=new BatchListAdapter(this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manger=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manger);

        dao=new DAOBatch();
        loadData();

    }
    public void changeActivity(){
        //Intent intent=new Intent(RVActivity.this,ClassInfo.class);
        //startActivity(intent);
    }
    public void loadData(){

        try{dao.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Batch> batches=new ArrayList<>();
                for(DataSnapshot data:snapshot.getChildren()){
                    Batch batch=data.getValue(Batch.class);
                    batches.add(batch);
                }

                adapter.setItem(batches);
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