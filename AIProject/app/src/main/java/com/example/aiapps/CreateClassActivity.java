package com.example.aiapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputLayout;

public class CreateClassActivity extends AppCompatActivity {
    Button newbtn;

    private Button CreatClassButton;
    private TextInputLayout ClassCode,NoOfStdnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);
        this.setTitle("Creating Class");
        CreatClassButton=findViewById(R.id.class_create_btn);
        ClassCode=findViewById(R.id.class_code);
        NoOfStdnt=findViewById(R.id.NoOfStdnt);


        CreatClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                  addButton();


                Intent intent=new Intent(CreateClassActivity.this,ClassListActivity.class);
                startActivity(intent);
            }
        });

    }

    private void addButton() {
        LinearLayout layout=findViewById(R.id.rootlayout);
        newbtn=new Button(this);
        newbtn.setText("AI");
        layout.addView(newbtn);
    }
}