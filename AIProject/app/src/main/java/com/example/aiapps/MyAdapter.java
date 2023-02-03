package com.example.aiapps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    public ArrayList<String> ClassCode;
    Context c;
    public MyAdapter(Context c,ArrayList<String> ClassCode) {
        this.c=c;
        this.ClassCode=ClassCode;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(c).inflate(R.layout.batchlistlayout,parent,false);
        MyViewHolder VH=new MyViewHolder(v);


        return VH;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.NameText.setText(ClassCode.get(position));

    }

    @Override
    public int getItemCount() {
        return ClassCode.size();
    }
}
