package com.kaleidoscope.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
    private final ArrayList<Note> notes = new ArrayList<>();
    private NotesAdapter adapter;
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();

        Cursor cursor = database.query(NotesContract.NotesEntry.TABLE_NAME, null, null, null, null, null, null);

        getData();

        adapter = new NotesAdapter(notes);
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
        int id = notes.get(pos).getId();
        String where = NotesContract.NotesEntry._ID + " = ?";
        String[] whereArgs = new String[]{Integer.toString(id)};
        database.delete(NotesContract.NotesEntry.TABLE_NAME, where, whereArgs);
        getData();
        adapter.notifyDataSetChanged();
    }

    public void onClickAddButton(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    private void getData() {
        notes.clear();
        String selection = NotesContract.NotesEntry.COLUMN_PRIORITY + " < ?";
        String[] selectionArgs = new String[]{"2"};
        Cursor cursor = database.query(NotesContract.NotesEntry.TABLE_NAME, null, selection, selectionArgs, null, null, NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK);
        Note note = null;
        while (cursor.moveToNext()) {
            try {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry._ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_TITLE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_DESCRIPTION));
                int dayOfWeek = cursor.getInt(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK));
                int priority = cursor.getInt(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_PRIORITY));
                note = new Note(id, title, description, dayOfWeek, priority);
            } catch (Exception e) {
                e.printStackTrace();
            }
            notes.add(note);
        }

        cursor.close();
    }
}
























