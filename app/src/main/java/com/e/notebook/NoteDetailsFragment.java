package com.e.notebook;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.e.notebook.model.ListNote;
import com.e.notebook.model.Note;

import static com.e.notebook.DataPickerFragment.FILED_NAME;
import static com.e.notebook.DataPickerFragment._ID;
import static com.e.notebook.service.Common.formatDateTimeToString;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteDetailsFragment extends Fragment {

    public static final String ID_NOTE = "idNote";
    private Integer idNote;
    private Note note;

    private TextView mTheme;                                                                                            // тема заметки
    private TextView mDataCreate;                                                                                       // дата создания заметки
    private TextView mDataChange;                                                                                       // дата изменения заметки
    private TextView mDataAlarm;                                                                                        // дата напоминания

    public NoteDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment NoteDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoteDetailsFragment newInstance(Integer param1) {
        NoteDetailsFragment fragment = new NoteDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ID_NOTE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idNote = getArguments().getInt(ID_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_note_details, container, false);
    }

    // вызывается после создания макета фрагмента, здесь мы проинициализируем список
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTheme = (TextView) view.findViewById(R.id.theme);
        mDataCreate = (TextView) view.findViewById(R.id.dateCreate);
        mDataChange = (TextView) view.findViewById(R.id.dateChange);
        mDataAlarm = (TextView) view.findViewById(R.id.dateAlarm);

        mDataAlarm.setOnClickListener((View v) -> {
// Добавим фрагмент на activity
            DataPickerFragment dataPickerFragment = new DataPickerFragment();
            Bundle args = new Bundle();
            args.putInt(_ID, idNote);
            args.putString(FILED_NAME, "mDataAlarm");
            dataPickerFragment.setArguments(args);
            // Добавим фрагмент на activity
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.noteDetailsContainer, dataPickerFragment)
                    .commit();
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        ListNote notes = ListNote.getInstance();
        note = notes.getNote(idNote);

        mTheme.setText(note.getTheme());
        mDataCreate.setText(formatDateTimeToString(note.getDateCreate()));
        mDataChange.setText(formatDateTimeToString(note.getDateChange()));
        mDataAlarm.setText(formatDateTimeToString(note.getDateAlarm()));
    }
}