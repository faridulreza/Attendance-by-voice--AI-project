package com.example.aiapps;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder{

    TextView NameText;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        NameText=itemView.findViewById(R.id.bathc_list_batch_code);

    }
}
