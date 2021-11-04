package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView alreadyAccount;
    EditText mId,mPass,mConfirmPass,mPasswordconfirmEditText;
    Button mRegister;
    ProgressBar progressBar2;
    //awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.Theme_Task2);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        alreadyAccount=findViewById(R.id.text1);
        mId=findViewById(R.id.email1);
        mPass=findViewById(R.id.pass1);
        mConfirmPass=findViewById(R.id.confirm_pass);
        mRegister=findViewById(R.id.Register);
        progressBar2=findViewById(R.id.progress_bar);
        mAuth = FirebaseAuth.getInstance();
        //awesomeValidation =new AwesomeValidation(ValidationStyle.BASIC);
        // awesomeValidation.addValidation(this,R.id.confirm_pass ,R.id.pass1,R.string.passwordisnotcorrect);


        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 progressBar2.setVisibility(View.VISIBLE);
                String Email = mId.getText().toString().trim();
                String password = mPass.getText().toString().trim();
                String confirmPass = mConfirmPass.getText().toString().trim();
                if (TextUtils.isEmpty(Email)) {
                      progressBar2.setVisibility(View.INVISIBLE);
                    mId.setError("Email is Required.");
                    return;
                }
                else {
                    mId.setError(null);
                }
                if (TextUtils.isEmpty(password)) {
                     progressBar2.setVisibility(View.INVISIBLE);
                    mPass.setError("Password is Required");
                    return;
                }
                else {
                    mPass.setError(null);
                }
                if (TextUtils.isEmpty(confirmPass)){
                      progressBar2.setVisibility(View.INVISIBLE);
                    mConfirmPass.setError("confirmPass is required");
                    return;
                }
                else {
                    mConfirmPass.setError(null);
                }
                if (!password.equals(confirmPass)){
                    mPass.setError("Password would not be matched");
                    mConfirmPass.setError("Password would not be matched");
                    return;
                }
                else {
                    mConfirmPass.setError(null);
                    mPass.setError(null);
                }
                if (password.length() < 6) {
                    progressBar2.setVisibility(View.INVISIBLE);
                    mPass.setError("Password Must be >= 6 Character");
                    return;
                }
                else {
                    mPass.setError(null);
                }
                mAuth.createUserWithEmailAndPassword(Email, password)
                        .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                     progressBar2.setVisibility(View.INVISIBLE);

                                    Toast.makeText(Register.this,"Registered Successfully!",Toast.LENGTH_SHORT).show();
                                    Intent R = new Intent(Register.this, MainActivity.class);
                                    startActivity(R);

                                } else {
                                      progressBar2.setVisibility(View.INVISIBLE);

                                    Toast.makeText(Register.this,"Process Error!",Toast.LENGTH_SHORT).show();

                                }


                            }
                        });
            }

        });

        alreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent R = new Intent(Register.this, MainActivity.class);
                startActivity(R);
                finish();
            }
        });


    }


}