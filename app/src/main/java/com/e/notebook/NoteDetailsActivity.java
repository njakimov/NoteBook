package com.e.notebook;

import android.content.res.Configuration;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class NoteDetailsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Если устройство перевернули в альбомную ориентацию, то надо эту activity закрыть
            finish();
            return;
        }
        if (savedInstanceState == null) {

            // Если эта activity запускается первый раз (с каждым новым гербом первый раз),
            // то перенаправим параметр фрагменту
            NoteDetailsFragment details = new NoteDetailsFragment();
            details.setArguments(getIntent().getExtras());

            // Добавим фрагмент на activity
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nodeDetailsContainer, details)
                    .commit();
        }
    }

}