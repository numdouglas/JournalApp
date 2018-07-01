package com.example.salitos.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewJournal extends AppCompatActivity {

    private static final String TAG = "Entry";
    FloatingActionButton floatingActionButton;
    private static final String DATABASE_CONFIG = "Journal";
    private FirebaseDatabase db;
    private JournalDataClass entry;
    DatabaseReference entryRef;
    String getID;
    EditText title;
    EditText details;
    EditText message;
    boolean update = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
        db = FirebaseDatabase.getInstance();
        entryRef = db.getReference(DATABASE_CONFIG);
        floatingActionButton = findViewById(R.id.fab);
        title = findViewById(R.id.title);
        details = findViewById(R.id.details);
        message = findViewById(R.id.message);

        //creaate a data type to pass to the next activity
        Bundle b=getIntent().getExtras();
        //set valus in key valu pair format
        if (b !=null){
            update = true;
            title.setText(b.getString("Title"));
            details.setText(b.getString("Details"));
            message.setText(b.getString("Message"));
            getID = b.getString("ID");
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(update){
                    updateEntry();
                }else {
                    addEntry();
                }
            }
        });
    }
//equivalent of the insert command
    private void addEntry() {
        getID = entryRef.child(DATABASE_CONFIG).push().getKey();
        entry = getData();
        entryRef.child(getID).setValue(entry).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "update successful");
                Toast.makeText(NewJournal.this, "New Event added ",
                        Toast.LENGTH_SHORT).show();
            }

        });
        finish();
    }
//constructor to initialize all fields for bundling later
    private JournalDataClass getData() {
        entry = new JournalDataClass();
        entry.setEvent(title.getText().toString());
        entry.setDetails(details.getText().toString());
        entry.setDate(message.getText().toString());
        entry.setId(getID);
        return entry;
    }
//equivalent of update command

    private void updateEntry(){
        if(getData().getId()==null){getID = entryRef.child(DATABASE_CONFIG).push().getKey();
        }
        entry = getData();
        entryRef.child(entry.getId()).setValue(entry);
        Intent intent = new Intent(NewJournal.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

