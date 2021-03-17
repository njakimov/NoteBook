package com.e.notebook;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.e.notebook.model.ListNote;

public class MainActivity extends AppCompatActivity {
    private ListNote notes;                                                                                             // текущий список заметок

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notes = ListNote.getInstance();
        if (notes.size() == 0) {
            notes.addNote("Тема 1", "описание 1");
            notes.addNote("Тема 2", "описание 2");
            notes.addNote("Тема 3", "описание 3");
        }
        setContentView(R.layout.activity_main);
    }
}