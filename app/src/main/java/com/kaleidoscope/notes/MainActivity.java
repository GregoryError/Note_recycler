package com.kaleidoscope.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNotes;
    public static final ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        notes.add(new Note("Автомастер", "Починить автомобиль", "Среда", 2));
        notes.add(new Note("Автомастер", "Починить автомобиль", "Среда", 1));
        notes.add(new Note("Автомастер", "Починить автомобиль", "Среда", 2));
        notes.add(new Note("Автомастер", "Починить автомобиль", "Среда", 1));
        notes.add(new Note("Автомастер", "Починить автомобиль", "Среда", 1));
        notes.add(new Note("Автомастер", "Починить автомобиль", "Среда", 3));
        notes.add(new Note("Автомастер", "Починить автомобиль", "Среда", 2));
        notes.add(new Note("Автомастер", "Починить автомобиль", "Среда", 0));
        notes.add(new Note("Автомастер", "Починить автомобиль", "Среда", 1));
        notes.add(new Note("Автомастер", "Починить автомобиль", "Среда", 2));
        notes.add(new Note("Автомастер", "Починить автомобиль", "Среда", 2));
        notes.add(new Note("Автомастер", "Починить автомобиль", "Среда", 1));

        NoteAdapter adapter = new NoteAdapter(notes);
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNotes.setAdapter(adapter);
    }

    public void onClickAddNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }
}