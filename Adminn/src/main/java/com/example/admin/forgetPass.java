package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgetPass extends AppCompatActivity {

    EditText mfemail;
    ImageView imageView;
    String email;
    FirebaseAuth auth;
    Button resermpass;
    ProgressBar progressBar3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setTheme(R.style.Theme_Task2);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_forget_pass);

        imageView=findViewById(R.id.iv_back);
        // setSupportActionBar();
        progressBar3=findViewById(R.id.progress_bar);
        mfemail=findViewById(R.id.femail);
        resermpass=findViewById(R.id.resetpass);
        auth=FirebaseAuth.getInstance();
        resermpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
        progressBar3.setVisibility(View.INVISIBLE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(forgetPass.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }



    private void validateData(){
        email = mfemail.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            progressBar3.setVisibility(View.INVISIBLE);
            mfemail.setError("Email is Required.");
            return;
        }
        else {
            forgetpass();
            progressBar3.setVisibility(View.VISIBLE);
        }
    }

    private void forgetpass() {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(forgetPass.this, "Check Your Email", Toast.LENGTH_SHORT).show();
                            //   startActivity(new Intent(forgetPass.this, MainActivity.class));
                            progressBar3.setVisibility(View.INVISIBLE);

                            Intent i = new Intent(forgetPass.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            progressBar3.setVisibility(View.INVISIBLE);
                            Toast.makeText(forgetPass.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
