package com.e.notebook.model;

import java.util.*;

public class ListNote {
    private static ListNote instance;

    private String mTxtSearch = "";                                                                                     // строка поиска

    private static Integer uniqIdNote = 0;                                                                              // уникальный ключ заметк
    private HashMap<Integer, Note> mListNote;                                                                           // список заметок

    public static ListNote getInstance() { // #3
        if (instance == null) {                                                                                         // если объект еще не создан
            instance = new ListNote();                                                                                  // создать новый объект
        }
        return instance;                                                                                                // вернуть ранее созданный объект
    }

    private ListNote() {
        this.mListNote = new HashMap<>();
    }

    /**
     * Добавить заметку
     *
     * @param description - описание заметки
     */
    public Integer addNote(String theme, String description) {
        uniqIdNote++;
        mListNote.put(uniqIdNote, new Note(uniqIdNote, theme, description));
        return uniqIdNote;
    }

    public Integer addNote(String theme, String description, Date dateAlarm) {
        uniqIdNote++;
        mListNote.put(uniqIdNote, new Note(uniqIdNote, theme, description, dateAlarm));
        return uniqIdNote;
    }

    public Integer addNote(Integer id, String theme, String description, Date dateCreate, Date dateChange, Date dateAlarm, Boolean favoriteState) {
        mListNote.put(id, new Note(uniqIdNote, theme, description, dateCreate, dateChange, dateAlarm, favoriteState));
        return id;
    }

    public Integer addNote(NoteToEdit note) {
        Integer id = note.getId();
        if (this.mListNote.get(id) == null) {
            this.addNote(note.getTheme(), note.getDescription(), note.getDateAlarm());
        } else {
            this.mListNote.get(id).editNote(note.getTheme(), note.getDescription(), note.getDateAlarm(), note.getFavoriteState());
        }
        return uniqIdNote;
    }

    /**
     * получить размер исходного списка
     *
     * @return - размер исходного списка
     */
    public int size() {
        return mListNote.size();
    }

    /**
     * Получить заметку
     *
     * @param id - ключ заметки
     * @return заметка
     */
    public Note getNote(Integer id) {
        return mListNote.get(id);
    }

    /**
     * Изменить заметку
     *
     * @param id          - ключ заметки
     * @param theme       - тема заметки
     * @param description - описание заметки
     */
    public void editNote(Integer id, String theme, String description) {
        if (this.mListNote.get(id) == null) {
            this.addNote(theme, description);
        } else {
            this.mListNote.get(id).editNote(theme, description);
        }
    }

    public void setFavorite(Integer id, Boolean state) {
        this.mListNote.get(id).setFavoriteState(state);

    }

    /**
     * Получить список всех заметок
     *
     * @return список заметок
     */
    public HashMap<Integer, Note> getListNote() {
        return mListNote;
    }

    public List<Note> toListNode() {
        List<Note> temp = new ArrayList<>();
        for (Map.Entry<Integer, Note> entry : mListNote.entrySet()) {
            if (mTxtSearch.equals("") ||
                    entry.getValue().getDescription().indexOf(mTxtSearch) > 0 ||
                    entry.getValue().getTheme().indexOf(mTxtSearch) > 0
            )
                temp.add(entry.getValue());
        }
        return temp;
    }

    public List<Note> toListNodeFavorite() {
        List<Note> temp = new ArrayList<>();
        for (Map.Entry<Integer, Note> entry : mListNote.entrySet()) {
            if (entry.getValue().getFavoriteState() &&
                    (mTxtSearch.equals("") ||
                            entry.getValue().getDescription().indexOf(mTxtSearch) > 0 ||
                            entry.getValue().getTheme().indexOf(mTxtSearch) > 0
                    )
            ) {
                temp.add(entry.getValue());
            }
        }
        return temp;
    }

    public void removeNote(int currentIdNote) {
        mListNote.remove(currentIdNote);
    }

    public String getTxtSearch() {
        return mTxtSearch;
    }

    public void setTxtSearch(String mTxtSearch) {
        this.mTxtSearch = mTxtSearch;
    }
}
