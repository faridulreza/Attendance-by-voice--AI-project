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

public class BatchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Batch> batches=new ArrayList<>();

    public BatchListAdapter(Context context) {
        this.context = context;
    }
     public  void setItem(ArrayList<Batch> batches){

        this.batches.addAll(batches);
     }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.layout_itme,parent,false);
        return new Bacth_list_view_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Bacth_list_view_holder vh=(Bacth_list_view_holder) holder;
        Batch batch=batches.get(position);
        vh.batch_code.setText(batch.getCode());
        //vh.number_of_students.setText(""+batch.getNumbber_of_students());
        vh.item.setOnClickListener(v->show(batch));
    }

    @Override
    public int getItemCount() {
        return batches.size();
    }
    public void show(Batch batch){
        BatchListActivity.batch=batch;
        changeActivity();
        Log.d("FAdapter", batch.getCode());
    }
    public void changeActivity(){
        Intent intent=new Intent(context,StudentList.class);
        context.startActivity(intent);
    }
}
