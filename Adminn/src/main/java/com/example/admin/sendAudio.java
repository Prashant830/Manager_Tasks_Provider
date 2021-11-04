package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class sendAudio extends AppCompatActivity {

    private Button nrecordbtn,mfech;
    private TextView mRecordLAbler;
    private MediaRecorder recorder;
    private String fileName = null;
    String songUrl,songName;
    StorageReference mStorage;
    ProgressDialog mProgress;
    private static final String LOG_TAG = "Record_log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_audio);
        mfech = findViewById(R.id.fetching);
        nrecordbtn = findViewById(R.id.recordBtn);
        mRecordLAbler = findViewById(R.id.recordLabel);
        fileName = getExternalCacheDir().getAbsolutePath();
        fileName += System.currentTimeMillis();
        songName = "Recorded_audio.mp3";
        mStorage = FirebaseStorage.getInstance().getReference();
        mProgress = new ProgressDialog(this);

        nrecordbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startRecording();
                    mRecordLAbler.setText("Recording Started...");
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    stopRecording();
                    // Toast.makeText(MainActivity.this, "Please open Audio & storage  permission for  this app.", Toast.LENGTH_SHORT).show();
                    mRecordLAbler.setText("Recording Stopped & Uploading Audio Automatically please check Audio, After Uploading." +
                            " Thank You...");
                }
                return false;
            }
        });

    }


    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        recorder.start();
    }

    private void stopRecording() {
        //recorder.stop();
        recorder.release();
        recorder = null;
        uploadAudio();
    }
    private void uploadAudio() {
        mProgress.setMessage("Uploading Audio ...");
        mProgress.show();
        Uri uri = Uri.fromFile(new File(fileName));
        StorageReference filepath = mStorage.child("Audio").child(uri.getLastPathSegment());
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while ((!uriTask.isComplete())) ;
                Uri urlSong = uriTask.getResult();
                songUrl = urlSong.toString();
                uploadDetailsToDataBase();
                mProgress.dismiss();
                mRecordLAbler.setText("Uploading Finished");
            }
        });
    }

    private void uploadDetailsToDataBase() {
        Module songObj = new Module(songUrl,songName);
        FirebaseDatabase.getInstance().getReference("Songs")
                .push().setValue(songObj).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(sendAudio.this, "Audio uploaded", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(sendAudio.this,e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void fetch(View view) {
        startActivity(new Intent(sendAudio.this,fetchAudio.class));

    }
}



