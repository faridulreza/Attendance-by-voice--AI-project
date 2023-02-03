package com.example.aiapps;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class Student_List_View_Holder extends RecyclerView.ViewHolder {
    public TextView roll;
    public ConstraintLayout item;
    public Student_List_View_Holder(@NonNull View itemView) {
        super(itemView);
        roll=itemView.findViewById(R.id.studenlistitem_roll);
        item=itemView.findViewById(R.id.student_list_item);


    }

}
