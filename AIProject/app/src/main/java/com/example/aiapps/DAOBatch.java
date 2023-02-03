package com.example.aiapps;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class DAOBatch {
    private DatabaseReference databaseReference;
    public DAOBatch(){
        FirebaseDatabase db= FirebaseDatabase.getInstance();
        databaseReference=db.getReference(Batch.class.getSimpleName());
    }
    public Task<Void> add(Batch batch) throws Exception{
       return  databaseReference.push().setValue(batch);

    }
    public Task<Void> update(String key, HashMap<String,Object> hashMap)  {
        return  databaseReference.child(key).updateChildren(hashMap);

    }
    public Task<Void> delete(String key)  {
        return  databaseReference.child(key).removeValue();

    }
    public Query get() throws Exception{
        return databaseReference.orderByKey();
    }

}
