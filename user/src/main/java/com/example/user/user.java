package com.example.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class user extends AppCompatActivity {

    private static final String TAG = "User";
    public static boolean userMode = false;
    Button logout;
    RecyclerView recyclerView;
    MessagesAdapter messagesAdapter;
    List<Message> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        logout = findViewById(R.id.logoutBtn);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference messagesReference = db.collection("messages");

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        messagesAdapter = new MessagesAdapter(messages);
        recyclerView.setAdapter(messagesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        messagesReference
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d(TAG, "onEvent: ERROR");
                        }

                        if (value != null) {
                            messages.clear();
                            for (QueryDocumentSnapshot doc : value) {
                                messages.add(doc.toObject(Message.class));
                            }

                            Log.d(TAG, "onEvent: Messages: " + messages);

                            messagesAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (userMode == false) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.info) {
            startActivity(new Intent(this, view_notice.class));
        }

        if (item.getItemId() == R.id.View_audio) {
            startActivity(new Intent(this, viewAudio.class));
        }
        return super.onOptionsItemSelected(item);
    }
}

