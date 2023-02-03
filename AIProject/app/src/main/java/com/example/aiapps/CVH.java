package com.example.aiapps;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CVH extends RecyclerView.ViewHolder{
    public TextView code;
    public CVH(@NonNull View itemView) {
        super(itemView);
        code=itemView.findViewById(R.id.course_list_item_code);
    }
}
