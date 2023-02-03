package com.example.aiapps;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class Bacth_list_view_holder extends RecyclerView.ViewHolder {
    public TextView batch_code,number_of_students;
    public CardView item;
    public Bacth_list_view_holder(@NonNull View itemView) {
        super(itemView);
            batch_code=itemView.findViewById(R.id.bathc_list_batch_code);
//        number_of_students=itemView.findViewById(R.id.batch_list_number);
            item=itemView.findViewById(R.id.bathch_list_item);


    }
}
