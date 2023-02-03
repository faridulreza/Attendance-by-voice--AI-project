package com.example.aiapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateBatchActivity extends AppCompatActivity {

   DAOBatch dao;
   ArrayList<Batch> existting_batches=new ArrayList<>();
   TextInputLayout BatchText, NoofStndt;
    Button CreateBatchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_batch);
        this.setTitle("Creating Batch");

        dao=new DAOBatch();
        read();
        CreateBatchBtn=findViewById(R.id.batch_create_btn);
        CreateBatchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create();
            }
        });

    }
    public void create(){
        EditText batch_code=findViewById(R.id.batch_code);
        EditText number_of_students=findViewById(R.id.create_batch_student_number);
        String number_string=number_of_students.getText().toString();
        int number=0;
        try {
            number = Integer.parseInt(number_string);
        }catch (Exception e){
            Toast.makeText(this,"number is not valid",Toast.LENGTH_LONG).show();
            return;
        }

        Batch batch=new Batch(batch_code.getText().toString(),number);
        for(Batch curbatch:existting_batches){
            if(curbatch.getCode().equals(batch.getCode()))
            {
                Toast.makeText(this,"Batch code already exist!",Toast.LENGTH_LONG).show();
                return;
            }
        }
        try {
            dao.add(batch).addOnSuccessListener(v->{
                Toast.makeText(this,"Succecssfull",Toast.LENGTH_LONG).show();
            }).addOnFailureListener(er->{
                Toast.makeText(this,""+er.getMessage(),Toast.LENGTH_LONG).show();
            });
        } catch (Exception e) {
            Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void read(){
        try {
            dao.get().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<Batch> batches=new ArrayList<>();
                    for(DataSnapshot data:snapshot.getChildren()){
                        Batch batch=data.getValue(Batch.class);
                        batches.add(batch);
                    }
                    existting_batches.addAll(batches);
                    //adapter.setItem(batches);
                    //adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}