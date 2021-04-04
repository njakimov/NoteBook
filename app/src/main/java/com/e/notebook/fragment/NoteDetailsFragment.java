package com.e.notebook.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.e.notebook.MainActivity;
import com.e.notebook.R;
import com.e.notebook.model.ListNote;
import com.e.notebook.model.NoteToEdit;
import com.e.notebook.service.DbHelper;

import static com.e.notebook.fragment.DataPickerFragment.FILED_NAME;
import static com.e.notebook.fragment.DataPickerFragment._ID;
import static com.e.notebook.service.Common.formatDateTimeToString;
import static com.e.notebook.service.Common.formatStringToDate;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteDetailsFragment extends Fragment {

    public static final String ID_NOTE = "idNote";
    public static final String CHOSEN_DATE = "chosenDate";
    public static final String CHOSEN_FIELD_NAME = "chosenFieldName";
    private Integer idNote;
    private NoteToEdit note;

    private TextView mTheme;                                                                                            // тема заметки
    private TextView mDescription;                                                                                      // описание заметки
    private TextView mDataCreate;                                                                                       // дата создания заметки
    private TextView mDataChange;                                                                                       // дата изменения заметки
    private TextView mDataAlarm;                                                                                        // дата напоминания
    private boolean isLandscape;
    private ImageView mFavoriteState;

    public NoteDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param idNote Parameter 1.
     * @return A new instance of fragment NoteDetailsFragment.
     */
    public static NoteDetailsFragment newInstance(Integer idNote) {
        NoteDetailsFragment fragment = new NoteDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ID_NOTE, idNote);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idNote = getArguments().getInt(ID_NOTE);
            String chosenDate = getArguments().getString(CHOSEN_DATE);
            String chosenFieldName = getArguments().getString(CHOSEN_FIELD_NAME);
            initNote();
            if (chosenDate != null && chosenFieldName != null) {
                if (chosenFieldName.equals("mDataAlarm")) {
                    note.setDateAlarm(formatStringToDate(chosenDate));
                }
            }
        }
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_details, container, false);
    }

    // вызывается после создания макета фрагмента, здесь мы проинициализируем список
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTheme = (TextView) view.findViewById(R.id.theme);
        mDescription = (TextView) view.findViewById(R.id.description);
        mDataCreate = (TextView) view.findViewById(R.id.dateCreate);
        mDataChange = (TextView) view.findViewById(R.id.dateChange);
        mDataAlarm = (TextView) view.findViewById(R.id.dateAlarm);
        mFavoriteState = (ImageView) view.findViewById(R.id.imgFavoriteState);

        mDataAlarm.setOnClickListener((View v) -> {
            DataPickerFragment dataPickerFragment = new DataPickerFragment();
            Bundle args = new Bundle();
            args.putInt(_ID, idNote);
            args.putString(FILED_NAME, "mDataAlarm");
            dataPickerFragment.setArguments(args);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.noteDetailsContainer, dataPickerFragment)
                    .commit();
        });

        mFavoriteState.setOnClickListener((v) -> {
            note.setFavoriteState(!note.getFavoriteState());
            setFavorite(note.getFavoriteState());
        });


        Button btnSaveNote = view.findViewById(R.id.btnSaveNote);
        btnSaveNote.setOnClickListener(v -> {
            note.setTheme(mTheme.getText().toString());
            note.setDescription(mDescription.getText().toString());

            ListNote notes = ListNote.getInstance();
            notes.addNote(note);
            if (isLandscape) {
                showLandNoteDetails(idNote);
            } else {
                showPortNoteDetails(idNote);
            }
        });
    }

    private void showPortNoteDetails(Integer idNote) {
        Intent intent = new Intent();                                                                                   // Откроем вторую activity
        intent.setClass(getContext(), MainActivity.class);
        startActivity(intent);
    }

    private void showLandNoteDetails(Integer idNote) {
        Intent intent = new Intent();                                                                                   // Откроем вторую activity
        intent.setClass(getContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        mTheme.setText(note.getTheme());
        mDescription.setText(note.getDescription());
        mDataCreate.setText(formatDateTimeToString(note.getDateCreate()));
        mDataChange.setText(formatDateTimeToString(note.getDateChange()));
        mDataAlarm.setText(formatDateTimeToString(note.getDateAlarm()));
        setFavorite(note.getFavoriteState());
    }

    private void setFavorite(Boolean favoriteState) {
        if (favoriteState) {
            mFavoriteState.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(),android.R.drawable.btn_star_big_on));
        } else {
            mFavoriteState.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(),android.R.drawable.btn_star_big_off));
        }
    }


    private void initNote() {
        if (idNote == 0) {
            NoteToEdit.getInstance().destroy();
        }

        NoteToEdit noteEdit = NoteToEdit.getInstance();
        ListNote notes = ListNote.getInstance();

        if (idNote == 0) {                                                                                             // если новый
            noteEdit.newNote();

        } else if (idNote != -1 && (noteEdit.getId() == 0 || !noteEdit.getId().equals(notes.getNote(idNote).getId()))) {                  // если редактируемый
            noteEdit.setNote(notes.getNote(idNote));
        }

        idNote = -1;

        note = noteEdit;
    }
}