package com.sabikrahat.notekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sabikrahat.notekeeper.adapter.CustomNoteAdapter;
import com.sabikrahat.notekeeper.db_management.KeyValueDB;
import com.sabikrahat.notekeeper.model.Note;

import java.util.ArrayList;

public class ShowNoteListActivity extends AppCompatActivity {

    private ListView lvNotes;
    private ArrayList<Note> notes;
    private CustomNoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note_list);

        findViewById(R.id.btnExit).setOnClickListener(view -> finish());

        findViewById(R.id.btnAddNote).setOnClickListener(view -> startActivity(new Intent(ShowNoteListActivity.this, NoteInformationActivity.class)));

        lvNotes = findViewById(R.id.noteListId);

        // load events from database if there is any
        loadData();
    }

    private void loadData(){
        notes = new ArrayList<>();
        KeyValueDB db = new KeyValueDB(this);
        Cursor rows = db.execute("SELECT * FROM key_value_pairs");
        if (rows.getCount() == 0) {
            return;
        }

        while (rows.moveToNext()) {
            String key = rows.getString(0);
            String eventData = rows.getString(1);
            String[] fieldValues = eventData.split("-::-");

            String course = fieldValues[0];
            String topic = fieldValues[1];
            String dateTime = fieldValues[2];
            String description = fieldValues[3];

            Note note = new Note(key, course, topic, dateTime, description);
            notes.add(note);

            // sorting based on date
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                notes.sort((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()) * -1);
            }
        }
        db.close();
        adapter = new CustomNoteAdapter(this, notes);
        lvNotes.setAdapter(adapter);

        // handle the click on an event-list item
        lvNotes.setOnItemClickListener((parent, view, position, id) -> startActivity(new Intent(ShowNoteListActivity.this, NoteInformationActivity.class).putExtra("NoteKey", notes.get(position).getKey())));

        // handle the long-click on an event-list item
        lvNotes.setOnItemLongClickListener((parent, view, position, id) -> {
            String message = "Do you want to delete note named \""+ notes.get(position).getNoteTopic() +"\" ?";
            // show dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(message)
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        // delete the note
                        db.deleteDataByKey(notes.get(position).getKey());
                        loadData();
                    }).show();
            return true;
        });
    }

    @Override
    public void onRestart () {
        super.onRestart();
        // re-load events from database after coming back from the next page
        loadData();
    }
}