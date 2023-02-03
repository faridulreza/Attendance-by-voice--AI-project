package com.example.aiapps;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ViewRecorListAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<String> dates=new ArrayList<>();

    public ViewRecorListAdapter2(Context context) {
        this.context = context;
    }

    public  void setItem(ArrayList<Record> records){
        Set<String> dates=new HashSet<String>();
        for(Record record : records){
            if(record.getCourse_code().equals(ViewRecords.course))
                dates.add(record.getDate());
        }
        Log.d("Set", "dates: "+records.toString());
        this.dates.addAll(dates);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.viewrecordlistitem2,parent,false);
        return new ViewRecordViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewRecordViewHolder2 vh=(ViewRecordViewHolder2) holder;
        String date=dates.get(position);
        vh.code.setText(date);
        vh.code.setOnClickListener(v->show(date));

    }

    @Override
    public int getItemCount() {
        return dates.size();
    }
    public void show(String date){
        ViewRecords2.date=date;
        changeActivity();
        //Log.d("FAdapter", batch.getCode());
    }
    public void changeActivity(){
        Intent intent=new Intent(context,ViewRecords3.class);
        context.startActivity(intent);
    }
}
