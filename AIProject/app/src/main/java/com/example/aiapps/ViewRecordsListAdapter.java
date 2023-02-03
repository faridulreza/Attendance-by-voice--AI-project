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

public class ViewRecordsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<String> courses=new ArrayList<>();

    public ViewRecordsListAdapter(Context context) {
        this.context = context;
    }

    public  void setItem(ArrayList<Record> records){
        Set<String> courses=new HashSet<String>();
        for(Record record : records){
            courses.add(record.getCourse_code());
        }

        this.courses.addAll(courses);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.viewrecord_list_item,parent,false);
        return new ViewRecordsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewRecordsViewHolder vh=(ViewRecordsViewHolder) holder;
        String course=courses.get(position);
        vh.code.setText(course);
        vh.code.setOnClickListener(v->show(course));

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
    public void show(String course){
        ViewRecords.course=course;
        changeActivity();
        //Log.d("FAdapter", batch.getCode());
    }
    public void changeActivity(){
        Intent intent=new Intent(context,ViewRecords2.class);
        context.startActivity(intent);
    }
}
