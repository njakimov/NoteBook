package com.e.notebook.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListNote {
    private static ListNote instance;

    private static Integer uniqIdNote = -1;                                                                             // уникальный ключ заметк
    private HashMap<Integer, Note> mListNote;                                                                           // список заметок

    public static ListNote getInstance(){ // #3
        if(instance == null){		                                                                                    // если объект еще не создан
            instance = new ListNote();	                                                                                // создать новый объект
        }
        return instance;		                                                                                        // вернуть ранее созданный объект
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

    /**
     * Получить список всех заметок
     *
     * @return список заметок
     */
    public HashMap<Integer, Note> getmListNote() {
        return mListNote;
    }

    public List<Note> toListNode() {
        List<Note> temp = new ArrayList<>();
        for (Map.Entry<Integer, Note> entry : mListNote.entrySet()) temp.add(entry.getValue());
        return temp;
    }
}
