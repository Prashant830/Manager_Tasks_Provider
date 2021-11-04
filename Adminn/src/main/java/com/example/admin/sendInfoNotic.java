package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sendInfoNotic extends AppCompatActivity {

    DatabaseReference reference;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_info_notic);
        reference = FirebaseDatabase.getInstance()
                .getReference("Policies");
        editText = findViewById(R.id.editText_privacy);

    }

    public void onSendMessageButtonClicked(View view) {
        String privacyPolicies =editText.getText().toString();

        ModuleTwo d = new ModuleTwo(privacyPolicies);
        reference.setValue(d)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(sendInfoNotic.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(sendInfoNotic.this, "Informative Notice have Successfully Changed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}