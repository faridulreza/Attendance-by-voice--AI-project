package com.example.aiapps;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VoiceSampleDBHelper {
    private DatabaseReference databaseReference;
    public VoiceSampleDBHelper(){
        FirebaseDatabase db= FirebaseDatabase.getInstance();
        databaseReference=db.getReference(VoiceSample.class.getSimpleName());
    }
    public Task<Void> add(VoiceSample sample) throws Exception{
        return  databaseReference.push().setValue(sample);

    }

}
