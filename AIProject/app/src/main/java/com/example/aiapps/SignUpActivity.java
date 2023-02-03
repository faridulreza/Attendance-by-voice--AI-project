package com.example.aiapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.RecoverySystem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout mDisplayName;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private Button mCrateBtn;
    private Toolbar mToolbar;
    private ProgressDialog mCreateAccountProgress;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.setTitle("Sign Up");
        mAuth = FirebaseAuth.getInstance();
        mCrateBtn=findViewById(R.id.eg_creat_btn);
        mDisplayName=findViewById(R.id.reg_display_name);
        mEmail=findViewById(R.id.reg_email);
        mPassword=findViewById(R.id.reg_password);
        mCrateBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        
        userRegister();

        Intent mainIntend= new Intent(getApplicationContext(),MainActivity.class);
        startActivity(mainIntend);
    }

    private void userRegister() {
        String name=mDisplayName.getEditText().toString().trim();
        String password=mPassword.getEditText().toString().trim();
        String email=mEmail.getEditText().toString().trim();

        if(email.isEmpty())
        {
            mEmail.setError("Enter an email Address");
            mEmail.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            mPassword.setError("Enter an email Address");
            mPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Resister is Successful",Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(),"Resister is Not Successful",Toast.LENGTH_SHORT).show();
                }

            }
        });




    }
}