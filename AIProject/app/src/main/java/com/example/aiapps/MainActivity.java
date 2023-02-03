package com.example.aiapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    public static final String TAC=MainActivity.class.getSimpleName();
    public static String API_LINK="https://90ad-118-179-189-88.in.ngrok.io";
    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private Button TakeAttenceBtn,ViewRecordBtn,GeTVoicSampleBtn,CreatClassBtn,CreateBatchBtn,TrainModel;
    private Button mSearchBtn;
    private ViewPager mViewPager;
   // private SectionPagerAddapter mSectionPagerAddapter;

    private EditText signInEmailText,signInPasswordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();

        TakeAttenceBtn=findViewById(R.id.take_attendance);
        ViewRecordBtn=findViewById(R.id.view_record_btn);
        GeTVoicSampleBtn=findViewById(R.id.get_voice_sample);
        CreatClassBtn=findViewById(R.id.create_class);
        CreateBatchBtn=findViewById(R.id.create_batch);
        ViewRecordBtn=findViewById(R.id.view_record_btn);
        TrainModel=findViewById(R.id.train_model);

        TrainModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, API_LINK+"/train",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);

                                builder.setTitle("Training Started!");
                                builder.setMessage("During training you can not take attendance sample");

                                builder.create().show();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Sag",error.getMessage());
                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });

                Volley.newRequestQueue(MainActivity.this).add(stringRequest);
            }
        });


        CreatClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Create_course.class);
                startActivity(intent);
            }
        });

        TakeAttenceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CourseList.class);
                startActivity(intent);
            }
        });
        CreateBatchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CreateBatchActivity.class);
                startActivity(intent);
            }
        });

        GeTVoicSampleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,BatchListActivity.class);
                startActivity(intent);
            }
        });

        ViewRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ViewRecords.class);
                startActivity(intent);
            }
        });

        takeAPIURL();
    }

    private void takeAPIURL(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Enter API url");
        alert.setMessage("");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        input.setText("http://10.0.2.2:5000");
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                API_LINK = value;
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }

/*
    @Override
    public void onStart() {

        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser==null)
        {
            sentToStart();
        }


    }

    private void sentToStart()
    {
        Intent startIntent= new Intent(MainActivity.this,StartActivity2.class);
        startActivity(startIntent);
        finish();
    }

*/

}