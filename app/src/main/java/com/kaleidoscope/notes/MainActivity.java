package com.kaleidoscope.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNotes;
    private NoteAdapter adapter;
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

        adapter = new NoteAdapter(notes);
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNotes.setAdapter(adapter);
        adapter.setOnNoteClickListener(new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int pos) {
                Toast.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int pos) {
                remove(pos);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                remove(viewHolder.getAdapterPosition());
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);

    }

    private void remove(int pos) {
        notes.remove(pos);
        adapter.notifyDataSetChanged();
    }

    public void onClickAddNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }
}