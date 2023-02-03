package com.example.aiapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;

public class AudioInputActivity extends AppCompatActivity {

    private ImageView audioImge,muteImage;
    private TextView TopTextView;
    private TextView BottomTextViewBegin,BottomTextViewStop;
    private  static int MicroPermission=200;
    MediaPlayer mediaPlayer;
    MediaRecorder mediaRecorder;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("user");

    private StorageReference storageReference;
    private ProgressDialog progressBar;
    VoiceSampleDBHelper dbHelper;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_input);
        this.setTitle("Attendance");


        storageReference= FirebaseStorage.getInstance().getReference();
        progressBar=new ProgressDialog(this);
        dbHelper=new VoiceSampleDBHelper();


        audioImge=findViewById(R.id.audio_image);
        muteImage=findViewById(R.id.mute_image);
        BottomTextViewBegin=findViewById(R.id.BeginText);
        BottomTextViewStop=findViewById(R.id.MutText);



        audioImge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioImge.setVisibility(View.GONE);
                muteImage.setVisibility(View.VISIBLE);
                BottomTextViewBegin.setVisibility(View.GONE);
                BottomTextViewStop.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"Taking Audio Input",Toast.LENGTH_SHORT).show();
                InputVoice();
            }

        });
        muteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                audioImge.setVisibility(View.VISIBLE);
                muteImage.setVisibility(View.GONE);
                BottomTextViewBegin.setVisibility(View.VISIBLE);
                BottomTextViewStop.setVisibility(View.GONE);
                //Toast.makeText(getApplicationContext(),"Audio is taken",Toast.LENGTH_SHORT).show();

                //mediaRecorder.stop();

                mediaRecorder.release();
                mediaRecorder=null;
                upload();
                //Toast.makeText(getApplicationContext(),"Sumon",Toast.LENGTH_SHORT).show();
            }
        });






    }


    private void PlayAudio() {

        try {
            mediaPlayer=new MediaPlayer();
            mediaPlayer.setDataSource(getRecordingFilePath());
            myRef.setValue(getRecordingFilePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            Toast.makeText(getApplicationContext(),"Playing",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Playing Error",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    private void InputVoice() {
        if(isMicrophonPresent())
        {
          getMicrophonePermission();
        }
        try {
            mediaRecorder=new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(getRecordingFilePath());
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
        mediaRecorder.start();

    }
    public void upload() {
        progressBar.setMessage("uploading..");
        progressBar.show();
        String url;
        StorageReference filepath=storageReference.child("Samples").child(BatchListActivity.batch.getCode()+"_"+StudentList.roll+"_"+new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date())+".mp3");
        Uri uri=Uri.fromFile(new File(getRecordingFilePath()));
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(
                        new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                String fileLink = task.getResult().toString();
                                VoiceSample voiceSample=new VoiceSample(fileLink,BatchListActivity.batch.getCode(),StudentList.roll);
                                try {
                                    dbHelper.add(voiceSample).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            progressBar.dismiss();
                                            Toast.makeText(AudioInputActivity.this, "Sample collected!", Toast.LENGTH_SHORT).show();
                                            //play();
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }



                            }
                        });
            }
        });
    }

    private boolean isMicrophonPresent() {
        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)){
            return true;
        }
        else return false;
    }
    private void getMicrophonePermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.RECORD_AUDIO},MicroPermission);
        }

    }

    private String getRecordingFilePath()
    {
        ContextWrapper contextWrapper=new ContextWrapper(getApplicationContext());
        File musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file= new File(musicDirectory,"tesRecordingFile"+".mp3");
        return file.getPath();
    }


}