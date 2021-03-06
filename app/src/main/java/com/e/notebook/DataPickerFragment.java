package com.e.notebook;

import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.e.notebook.model.ListNote;
import com.e.notebook.model.Note;

import java.util.Date;

import static com.e.notebook.service.Common.formatStringToDate;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DataPickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DataPickerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String _ID = "id";
    public static final String FILED_NAME = "filed_name";

    // TODO: Rename and change types of parameters
    private Integer id;
    private String fieldName;

    DatePicker mDatePicker;

    public static final String ID_NOTE = "idNote";
    private Integer idNote;

    public DataPickerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DataPickerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DataPickerFragment newInstance(Integer param1, String param2) {
        DataPickerFragment fragment = new DataPickerFragment();
        Bundle args = new Bundle();
        args.putInt(_ID, param1);
        args.putString(FILED_NAME, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(_ID);
            fieldName = getArguments().getString(FILED_NAME);
        }

    }

    // ???????????????????? ?????????? ???????????????? ???????????? ??????????????????, ?????????? ???? ?????????????????????????????????? ????????????
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDatePicker = view.findViewById(R.id.datePicker);
        Button btnDataPicker = view.findViewById(R.id.btnDataPickerDone);
        btnDataPicker.setOnClickListener((View v) -> {
            Note note = ListNote.getInstance().getNote(id);
            if (fieldName.equals("mDataAlarm")) {
                note.setDateAlarm(formatStringToDate(mDatePicker.getYear(), mDatePicker.getMonth(), mDatePicker.getDayOfMonth()));
            }

            NoteDetailsFragment details = new NoteDetailsFragment();
            Bundle args = new Bundle();
            args.putInt(ID_NOTE, id);
            details.setArguments(args);
            // ?????????????? ???????????????? ???? activity
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nodeDetailsContainer, details)
                    .commit();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_picker, container, false);
    }
}