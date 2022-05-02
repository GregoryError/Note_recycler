package com.kaleidoscope.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewNotes;
    public static final ArrayList<Note> notes = new ArrayList<>();
    private NotesAdapter adapter;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

//        if (notes.isEmpty()) {
//            notes.add(new Note("СТОА", "Починить автомобиль", "Вторник", 2));
//            notes.add(new Note("СТОА", "Починить автомобиль", "Вторник", 1));
//            notes.add(new Note("СТОА", "Починить автомобиль", "Вторник", 2));
//            notes.add(new Note("СТОА", "Починить автомобиль", "Вторник", 1));
//            notes.add(new Note("СТОА", "Починить автомобиль", "Вторник", 2));
//            notes.add(new Note("СТОА", "Починить автомобиль", "Вторник", 1));
//            notes.add(new Note("СТОА", "Починить автомобиль", "Вторник", 1));
//            notes.add(new Note("СТОА", "Починить автомобиль", "Вторник", 2));
//            notes.add(new Note("СТОА", "Починить автомобиль", "Вторник", 1));
//            notes.add(new Note("СТОА", "Починить автомобиль", "Вторник", 2));
//            notes.add(new Note("СТОА", "Починить автомобиль", "Вторник", 0));
//            notes.add(new Note("СТОА", "Починить автомобиль", "Вторник", 2));
//            notes.add(new Note("СТОА", "Починить автомобиль", "Вторник", 1));
//            notes.add(new Note("СТОА", "Починить автомобиль", "Вторник", 0));
//            notes.add(new Note("СТОА", "Починить автомобиль", "Вторник", 0));
//        }

        for (Note note : notes) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, note.getTitle());
            contentValues.put(NotesContract.NotesEntry.COLUMN_DESCRIPTION, note.getDescription());
            contentValues.put(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK, note.getDayOfWeek());
            contentValues.put(NotesContract.NotesEntry.COLUMN_PRIORITY, note.getPriority());
            database.insert(NotesContract.NotesEntry.TABLE_NAME, null, contentValues);
        }




        ArrayList<Note> notesFromDb = new ArrayList<>();

        Cursor cursor = database.query(NotesContract.NotesEntry.TABLE_NAME, null, null, null, null, null, null);


        Note note;
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_DESCRIPTION));
            String dayOfWeek = cursor.getString(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK));
            int priority = cursor.getInt(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_PRIORITY));
            note = new Note(title, description, dayOfWeek, priority);
            notesFromDb.add(note);
        }

        cursor.close();


        adapter = new NotesAdapter(notesFromDb);
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNotes.setAdapter(adapter);
        adapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {

            @Override
            public void onNoteClick(int pos) {

            }

            @Override
            public void onLongClick(int pos) {
                remove(pos);
            }
        });

        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                remove(viewHolder.getAdapterPosition());
            }
        });

        touchHelper.attachToRecyclerView(recyclerViewNotes);

    }

    void remove(int pos) {
        notes.remove(pos);
        adapter.notifyDataSetChanged();
    }

    public void onClickAddButton(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }
}
























