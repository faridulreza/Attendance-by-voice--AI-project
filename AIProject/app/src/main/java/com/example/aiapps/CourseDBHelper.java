package com.example.aiapps;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class CourseDBHelper {
    private DatabaseReference databaseReference;
    public CourseDBHelper() {
        FirebaseDatabase db= FirebaseDatabase.getInstance();
        databaseReference=db.getReference(Course.class.getSimpleName());
    }
    public Query get() throws Exception{
        return databaseReference.orderByKey();
    }
    public Task<Void> add(Course course) throws Exception{
        return  databaseReference.push().setValue(course);

    }



}
