package com.example.aiapps;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class RecordDBHelper {
    private DatabaseReference databaseReference;
    public RecordDBHelper() {
        FirebaseDatabase db= FirebaseDatabase.getInstance();
        databaseReference=db.getReference(Record.class.getSimpleName());
    }
    public Query get() throws Exception{
        return databaseReference.orderByKey();
    }
    public Task<Void> add(Record record) throws Exception{
        return  databaseReference.push().setValue(record);

    }
}
