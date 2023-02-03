package com.example.aiapps;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewRecordsViewHolder extends RecyclerView.ViewHolder{
public TextView code;
public ViewRecordsViewHolder(@NonNull View itemView) {
        super(itemView);
        code=itemView.findViewById(R.id.viewrecords_list_item_code);
        }
}
