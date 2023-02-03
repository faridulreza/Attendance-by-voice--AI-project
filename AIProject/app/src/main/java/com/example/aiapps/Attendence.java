package com.example.aiapps;

import static com.example.aiapps.MainActivity.API_LINK;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Attendence extends AppCompatActivity {


    private ImageView audioImge,muteImage;
    private TextView TopTextView;
    private TextView BottomTextViewBegin,BottomTextViewStop;

    private static int MICROPHONE_PERMISSION=200;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    private ProgressDialog progressBar;
    RecordDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);
        progressBar=new ProgressDialog(this);
        dbHelper=new RecordDBHelper();

        if(isMicrophonePresent())
            getMicroPhonePermission();

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
    }


    public void InputVoice(){
        try{
            mediaRecorder=new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(getRecordingFilePath());
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
            mediaRecorder.start();
            Toast.makeText(this,"Recording started!", Toast.LENGTH_LONG).show();
        }catch(Exception e){
            e.printStackTrace();

        }

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
    public void btnStopPressed(View view){
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder=null;
        Toast.makeText(this,"Recording stopped!", Toast.LENGTH_LONG).show();
    }
    /**
     * get bytes from input stream.
     *
     * @param inputStream inputStream.
     * @return byte array read from the inputStream.
     * @throws IOException
     */
    public static byte[] getBytes(InputStream inputStream) throws IOException {

        byte[] bytesResult = null;
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        try {
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            bytesResult = byteBuffer.toByteArray();
        } finally {
            // close the stream
            try{ byteBuffer.close(); } catch (IOException ignored){ /* do nothing */ }
        }
        return bytesResult;
    }
    public byte[] getBytes(Uri uri) throws IOException {
        InputStream iStream = this.getContentResolver().openInputStream(uri);
        try {
            return getBytes(iStream);
        } finally {
            // close the stream
            try {
                iStream.close();
            } catch (IOException ignored) { /* do nothing */ }
        }
    }

    public void upload() {
        progressBar.setMessage("verifying..");
        progressBar.show();

        Uri uri=Uri.fromFile(new File(getRecordingFilePath()));
        byte[] file = null;
        try{
            file = getBytes(uri);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        final byte[] final_file=file;


        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, API_LINK+"/test",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            progressBar.hide();
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(Attendence.this, obj.getString("status"), Toast.LENGTH_SHORT).show();

                            if(obj.getString("status").equals("OK")){
                                setAttendanceResponse(obj.getInt("roll"),obj.getString("batch"));
                            }


                        } catch (JSONException e) {
                            progressBar.hide();
                            e.printStackTrace();
                            Toast.makeText(Attendence.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("GotError",""+error.getMessage());

                    }
                }) {


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("file", new DataPart(imagename + ".png", final_file));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);

    }

    public void btnPlayPressed(View view){

    }
    public  void  play(){
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(getRecordingFilePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            Toast.makeText(this,"Recording is playing!", Toast.LENGTH_LONG).show();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    private boolean isMicrophonePresent(){
        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE))
            return true;
        else
            return false;
    }
    private void getMicroPhonePermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)==
                PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.RECORD_AUDIO},MICROPHONE_PERMISSION);
        }

    }
    private  String getRecordingFilePath(){
        ContextWrapper contextWrapper=new ContextWrapper(getApplicationContext());
        File musicDirectory=contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file= new File(musicDirectory,"testRecordingFile"+".mp3");
        return file.getPath();
    }

    public void setAttendanceResponse(int roll, String batch){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Is your roll: "+roll+" ("+batch+")").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();

                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
                String strDate = dateFormat.format(date);
                Record record=new Record(CourseList.course.getCode(),batch,""+strDate,roll);
                try {
                    dbHelper.add(record).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            progressBar.dismiss();
                            Toast.makeText(Attendence.this, "Attendence Recoreded!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                progressBar.dismiss();
                Toast.makeText(Attendence.this, "Try Again!", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
        ///back end implementation...


        Log.d("upload", "uploading... ");
    }
}