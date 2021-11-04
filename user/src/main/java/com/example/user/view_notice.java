package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class view_notice extends AppCompatActivity {


    TextView mtextView;
    FirebaseFirestore db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notice);
        mtextView = findViewById(R.id.privacy1);
        db = FirebaseFirestore.getInstance();
        reference = FirebaseDatabase.getInstance()
                .getReference("Policies");
        reference.child("pp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String first = snapshot.getValue(String.class);
                mtextView.setText((first));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(view_notice.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
