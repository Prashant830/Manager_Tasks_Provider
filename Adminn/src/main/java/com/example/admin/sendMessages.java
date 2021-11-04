package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class sendMessages extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_messages);
    }

    public void onSendMessageButtonClicked(View view) {
        EditText editText = findViewById(R.id.editText_message);
        if (editText.getText().toString().trim().isEmpty()) {
            editText.setError("Message Can't be empty!");
        } else {
            Message message = new Message(editText.getText().toString(), String.valueOf(System.currentTimeMillis()));
            FirebaseFirestore.getInstance().collection("messages")
                    .add(message)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(sendMessages.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}