package com.example.aiapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity2 extends AppCompatActivity {

    //mRegbtn,mLoginbtn are the button which is use for Register and Login respectfully
    private Button mRegbtn;
    private Button mLoginbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start2);
        mRegbtn = (Button) findViewById(R.id.Strt_Reg_Btn);
        mLoginbtn= (Button) findViewById(R.id.start_login_btn);

        mRegbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if user click Register button it goes to RegisterActivity page
                 Intent reg_intent=new Intent(StartActivity2.this,SignUpActivity.class);
                 startActivity(reg_intent);
            }
        });

        mLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if user click Login button it goes to Login page
                  Intent login_intent=new Intent(StartActivity2.this,LoginActivity2.class);
                  startActivity(login_intent);


            }
        });

    }
}