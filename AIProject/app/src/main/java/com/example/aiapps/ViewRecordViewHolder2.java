package com.example.aiapps;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewRecordViewHolder2 extends RecyclerView.ViewHolder{
    public TextView code;
    public ViewRecordViewHolder2(@NonNull View itemView) {
        super(itemView);
        code=itemView.findViewById(R.id.viewrecords_list_item_code2);
    }
}
