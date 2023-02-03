package com.example.aiapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ClassListActivity extends AppCompatActivity {


    private Button AIButtn,CclassBtn,DSclassBtn;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

        this.setTitle("Class List");
        AIButtn=findViewById(R.id.AI_class_Btn);
        CclassBtn=findViewById(R.id.C_class_btn);
        DSclassBtn=findViewById(R.id.DS_Class_Btn);

        AIButtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ClassListActivity.this,AudioInputActivity.class);
                startActivity(intent);
            }
        });
        CclassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ClassListActivity.this,AudioInputActivity.class);
                startActivity(intent);
            }
        });
        DSclassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ClassListActivity.this,AudioInputActivity.class);
                startActivity(intent);
            }
        });

        //sumon
    }
}