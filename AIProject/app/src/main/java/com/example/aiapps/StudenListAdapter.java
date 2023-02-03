package com.example.aiapps;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudenListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Context context;
    private ArrayList<Integer> rolls=new ArrayList<>();

    public StudenListAdapter(Context context) {
        this.context = context;
    }
    public  void setItem(int number_of_students){
        for(int x=1 ;x<= number_of_students;x++)
            this.rolls.add(x);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.student_list_item,parent,false);
        return new Student_List_View_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Student_List_View_Holder vh=(Student_List_View_Holder) holder;
        int roll=rolls.get(position);
        vh.roll.setText(roll+"");
        vh.item.setOnClickListener(v->show(roll));
    }

    @Override
    public int getItemCount() {
        return rolls.size();
    }
    public void show(int roll){
        StudentList.roll=roll;
        changeActivity();
    }
    public void changeActivity(){
        Intent intent=new Intent(context,AudioInputActivity.class);
        context.startActivity(intent);
    }
}
