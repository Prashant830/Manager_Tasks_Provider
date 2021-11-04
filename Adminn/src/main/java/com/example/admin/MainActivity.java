package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    TextView noAccount,forgetPass;
    EditText mEmail,mPassword;
    Button mLogin;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.Theme_Task2);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        progressBar=(ProgressBar)findViewById(R.id.progress_bar);
        mEmail= findViewById(R.id.email);
        mPassword=findViewById(R.id.pass);
        mLogin=findViewById(R.id.login);
        noAccount=findViewById(R.id.text2);
        mAuth = FirebaseAuth.getInstance();
        forgetPass=findViewById(R.id.forget_pass);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                 progressBar.setVisibility(View.VISIBLE);

                if (TextUtils.isEmpty(Email)) {
                       progressBar.setVisibility(View.INVISIBLE);
                    mEmail.setError("Email is Required.");
                    return;
                }
                else {
                    mEmail.setError(null);
                }
                if (TextUtils.isEmpty(password)) {
                     progressBar.setVisibility(View.INVISIBLE);
                    mPassword.setError("Password is Required");
                    return;
                }
                else {
                    mPassword.setError(null);
                }

                if (password.length() < 6) {
                     progressBar.setVisibility(View.INVISIBLE);
                    mPassword.setError("Password Must be >= 6 Character");
                    return;
                }
                else {
                    mPassword.setError(null);
                }
                if (Email.equals("") && password.equals("")) {
                      progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "Email and Password is Blank", Toast.LENGTH_SHORT).show();
                }
                mAuth.signInWithEmailAndPassword(Email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                      progressBar.setVisibility(View.INVISIBLE);
                                    Intent i = new Intent(MainActivity.this, Admin.class);
                                    startActivity(i);

                                } else {
                                       progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(MainActivity.this, "Invalid Email and Password!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            }
        });


        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Register.class);
                startActivity(i);
                finish();
            }
        });


        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, forgetPass.class);
                startActivity(i);
                finish();
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            startActivity(new Intent(MainActivity.this, Admin.class));
            finish();
        }
    }
}