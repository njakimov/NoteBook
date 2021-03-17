package com.e.notebook;

import android.content.Intent;
import android.content.res.Configuration;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.e.notebook.model.Note;

import java.util.List;

import static com.e.notebook.service.Common.formatDateTimeToString;

public class NotesRecyclerViewAdapter extends RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder> {

    private final List<Note> mValues;
    private ViewGroup parent;
    private Fragment parentFragment;
    private boolean isLandscape;

    public NotesRecyclerViewAdapter(List<Note> items, Fragment parentFragment) {
        mValues = items;
        this.parentFragment = parentFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.node, parent, false);

        isLandscape = parent.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mTheme.setText(mValues.get(position).getTheme());
        holder.mDataCreate.setText(formatDateTimeToString(mValues.get(position).getDateCreate()));
        holder.mDataChange.setText(formatDateTimeToString(mValues.get(position).getDateChange()));

        final Integer idNote = mValues.get(position).getId();

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoteDetails(idNote);
            }
        });
    }

    private void showNoteDetails(Integer idNote) {
        if (isLandscape) {
            showLandNoteDetails(idNote);
        } else {
            showPortNoteDetails(idNote);
        }
    }

    private void showPortNoteDetails(Integer idNote) {
// Откроем вторую activity
        Intent intent = new Intent();
        intent.setClass(parent.getContext(), NoteDetailsActivity.class);
// и передадим туда параметры
        intent.putExtra(NoteDetailsFragment.ID_NOTE, idNote);
        parentFragment.startActivity(intent);
    }

    private void showLandNoteDetails(Integer idNote) {
// Откроем вторую activity
        Intent intent = new Intent();
        intent.setClass(parent.getContext(), NoteDetailsActivity.class);
// и передадим туда параметры
        intent.putExtra(NoteDetailsFragment.ID_NOTE, idNote);
        parentFragment.startActivity(intent);

        NoteDetailsFragment detail = NoteDetailsFragment.newInstance(idNote);
// Выполняем транзакцию по замене фрагмента
        FragmentManager fragmentManager = parentFragment.requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nodeDetailsContainer, detail); // замена фрагмента
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTheme;
        public final TextView mDataCreate;
        public final TextView mDataChange;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTheme = (TextView) view.findViewById(R.id.theme);
            mDataCreate = (TextView) view.findViewById(R.id.dateCreate);
            mDataChange = (TextView) view.findViewById(R.id.dateChange);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mTheme.getText() + "'";
        }
    }
}