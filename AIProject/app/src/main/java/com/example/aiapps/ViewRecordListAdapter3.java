package com.example.aiapps;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ViewRecordListAdapter3 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<String> rolls=new ArrayList<>();

    public ViewRecordListAdapter3(Context context) {
        this.context = context;
    }

    public  void setItem(ArrayList<Record> records){
        Set<String> rolls=new HashSet<String>();
        for(Record record : records){
            if(record.getCourse_code().equals(ViewRecords.course)&&record.getDate().equals(ViewRecords2.date))
                rolls.add(Integer.toString(record.getRoll()));
        }

        this.rolls.addAll(rolls);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.viewrecord_list_itm3,parent,false);
        return new ViewRecordViewHolder3(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewRecordViewHolder3 vh=(ViewRecordViewHolder3) holder;
        String roll=rolls.get(position);
        vh.code.setText(roll);
        //vh.code.setOnClickListener(v->show(date));

    }

    @Override
    public int getItemCount() {
        return rolls.size();
    }
    public void show(String date){
        //ViewRecords2.date=date;
        changeActivity();
        //Log.d("FAdapter", batch.getCode());
    }
    public void changeActivity(){
        //Intent intent=new Intent(context,Attendence.class);
        //context.startActivity(intent);
    }
}
