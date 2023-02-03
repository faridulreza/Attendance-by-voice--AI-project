package com.example.aiapps;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourseListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private ArrayList<Course> courses=new ArrayList<>();

    public CourseListAdapter(Context context) {
        this.context = context;
    }

    public  void setItem(ArrayList<Course> courses){

        this.courses.addAll(courses);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.coures_list_item,parent,false);
        return new CVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CVH vh=(CVH) holder;
        Course course=courses.get(position);
        vh.code.setText(course.getCode());
        vh.code.setOnClickListener(v->show(course));

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
    public void show(Course course){
        CourseList.course=course;
        changeActivity();
        //Log.d("FAdapter", batch.getCode());
    }
    public void changeActivity(){
        Intent intent=new Intent(context,Attendence.class);
        context.startActivity(intent);
    }

}
