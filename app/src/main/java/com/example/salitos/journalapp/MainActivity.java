package com.example.salitos.journalapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String DATABASE_CONFIG = "Journal";
    private static final String TAG = "MAIN_ACTIVITY";
    FloatingActionButton floatingActionButton;
    ProgressBar progressBar;
    JournalDataClass entry;
    RecyclerView entryList;
    String user;
    int count = 0;
    ViewAdapter adapter;
    FirebaseDatabase db;
    private List<JournalDataClass> entries;

    private FirebaseAuth auth;
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBaseGround();
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        entryList = findViewById(R.id.recyclerViewTasks);
        progressBar = findViewById(R.id.progressBar);
        entryList.setLayoutManager(linearLayoutManager);
        entries = new ArrayList<>();
        entry = new JournalDataClass();
        getEntries();
        setClicks();
        entryList.setAdapter(adapter);
    }
//
    private void setClicks() {
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewJournal.class);
                startActivity(intent);
            }
        });
        adapter = new ViewAdapter(entries, new ViewAdapter.AdapterClickListener() {
            @Override
            public void onEntryClicked(JournalDataClass entry) {
                Intent i = new Intent(MainActivity.this, EditJournal.class);
                i.putExtra(EditJournal.ENTRY_ARGS, entry);
                startActivity(i);
//Offers short term data persistence during the life time of the next activity
            }
        });
    }



    private void setBaseGround() {

        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser().getUid();
    }

    private void getEntries() {
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference entryRef = db.getReference(DATABASE_CONFIG);
        entryRef.addValueEventListener(eventListener);
    }



    private ValueEventListener eventListener = new ValueEventListener() {

        //rearrange the data model appropriately each time mutable actions occur
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            entries.clear();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                entry = snapshot.getValue(JournalDataClass.class);
                entry.setCid(count);
                entries.add(entry);
                count++;
                adapter.notifyDataSetChanged();
                if (count >= dataSnapshot.getChildrenCount()) {
                    adapter.replaceData(entries);
                    count = 0;
                }
            }

            //process has finished, no more indicators needed
            progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(MainActivity.this, "Error Occurred",
                    Toast.LENGTH_SHORT).show();
        }
    };

}
