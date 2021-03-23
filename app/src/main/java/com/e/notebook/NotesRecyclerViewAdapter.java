package com.e.notebook;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.e.notebook.model.ListNote;
import com.e.notebook.model.Note;

import java.util.List;

import static com.e.notebook.service.Common.formatDateTimeToString;

public class NotesRecyclerViewAdapter extends RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder> {

    private List<Note> mValues;
    private ViewGroup parent;
    private Fragment parentFragment;
    private boolean isLandscape;
    private int currentIdNote = -1;

    public NotesRecyclerViewAdapter(List<Note> items, Fragment parentFragment) {
        mValues = items;
        this.parentFragment = parentFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note, parent, false);

        isLandscape = parent.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mTheme.setText(mValues.get(position).getTheme());
//        holder.mDataCreate.setText(formatDateTimeToString(mValues.get(position).getDateCreate()));
        holder.mDataChange.setText(formatDateTimeToString(mValues.get(position).getDateChange()));
        setFavorite(holder.mFavoriteState, mValues.get(position).getFavoriteState());
        final Integer idNote = mValues.get(position).getId();

        holder.mView.setOnClickListener(v -> showNoteDetails(idNote));

        holder.mView.setOnLongClickListener(v -> {
            openPopupMenu(v, idNote);
            return true;
        });

        holder.mFavoriteState.setOnClickListener((v) -> {
            ListNote notes = ListNote.getInstance();
            notes.setFavorite(idNote, !notes.getNote(idNote).getFavoriteState());
            setFavorite(v.findViewById(R.id.imgFavoriteStateOnList), notes.getNote(idNote).getFavoriteState());
        });
    }

    private void setFavorite(ImageView mFavoriteState, Boolean favoriteState) {
        if (favoriteState) {
            mFavoriteState.setImageDrawable(ContextCompat.getDrawable(parent.getContext(), android.R.drawable.btn_star_big_on));
        } else {
            mFavoriteState.setImageDrawable(ContextCompat.getDrawable(parent.getContext().getApplicationContext(), android.R.drawable.btn_star_big_off));
        }
    }

    private void showNoteDetails(Integer idNote) {
        if (isLandscape) {
            showLandNoteDetails(idNote);
        } else {
            showPortNoteDetails(idNote);
        }
    }

    private void showPortNoteDetails(Integer idNote) {
        Intent intent = new Intent();                                                                                   // Откроем вторую activity
        intent.setClass(parent.getContext(), NoteDetailsActivity.class);
        intent.putExtra(NoteDetailsFragment.ID_NOTE, idNote);                                                           // и передадим туда параметры
        parentFragment.startActivity(intent);
    }

    private void showLandNoteDetails(Integer idNote) {
        Intent intent = new Intent();

        intent.setClass(parent.getContext(), NoteDetailsActivity.class);                                                // Откроем вторую activity
        intent.putExtra(NoteDetailsFragment.ID_NOTE, idNote);                                                           // и передадим туда параметры
        parentFragment.startActivity(intent);

        NoteDetailsFragment detail = NoteDetailsFragment.newInstance(idNote);

        FragmentManager fragmentManager = parentFragment.requireActivity().getSupportFragmentManager();                 // Выполняем транзакцию по замене фрагмента
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.noteDetailsContainer, detail);                                                 // замена фрагмента
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
        //        public final TextView mDataCreate;
        public final ImageView mFavoriteState;
        public final TextView mDataChange;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTheme = (TextView) view.findViewById(R.id.theme);
//            mDataCreate = (TextView) view.findViewById(R.id.dateCreate);
            mDataChange = (TextView) view.findViewById(R.id.dateChange);
            mFavoriteState = (ImageView) view.findViewById(R.id.imgFavoriteStateOnList);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mTheme.getText() + "'";
        }
    }

    private void openPopupMenu(View view, Integer idNote) {
        currentIdNote = idNote;
        TextView text = view.findViewById(R.id.theme);

        Activity activity = parentFragment.getActivity();
        PopupMenu popupMenu = new PopupMenu(activity, view);
        activity.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
        Menu menu = popupMenu.getMenu();
//            menu.findItem(R.id.item2_popup).setVisible(false);
        menu.add(0, 1, 10, R.string.add);
        menu.add(0, 2, 12, R.string.edit);
        menu.add(0, 3, 12, R.string.remove);
        popupMenu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case 1:
                    Toast.makeText(parentFragment.getContext(), "Добавить", Toast.LENGTH_SHORT).show();
                    showNoteDetails(0);
                    return true;
                case 2:
                    Toast.makeText(parentFragment.getContext(), "Изменить", Toast.LENGTH_SHORT).show();
                    showNoteDetails(currentIdNote);
                    return true;
                case 3:
                    Toast.makeText(parentFragment.getContext(), "Удалить", Toast.LENGTH_SHORT).show();
                    removeNote(currentIdNote);
                    return true;
            }
            return true;
        });
        popupMenu.show();
    }

    private void removeNote(int currentIdNote) {
        ListNote notes = ListNote.getInstance();
        notes.removeNote(currentIdNote);
        mValues = notes.toListNode();
        notifyDataSetChanged();
    }


}