package com.e.notebook.model;

import java.util.Date;

/**
 * Класс заметка
 */
public class Note {

    private Integer mId = 0;                                                                                            // ключ заметки
    private String mTheme;                                                                                              // тема заметки / название
    private String mDescription;                                                                                        // описание заметки
    private Date mDateCreate;                                                                                           // дата создания заметки
    private Date mDateChange;                                                                                           // дата изменения заметки
    private Date mDateAlarm;                                                                                            // дата напоминания
    private Boolean mFavoriteState = false;                                                                              // флаг избранных сообщений

    public Boolean getFavoriteState() {
        return mFavoriteState;
    }

    public void setFavoriteState(Boolean favoriteState) {
        this.mFavoriteState = favoriteState;
    }

    public Note(Integer uniqIdNote, String theme, String description) {
        this.mTheme = theme;
        this.mDescription = description;
        this.mDateCreate = new Date();
        this.mDateChange = new Date();
        this.mId = uniqIdNote;
    }

    public Note(Integer uniqIdNote, String theme, String description, Date dateAlarm) {
        this.mTheme = theme;
        this.mDescription = description;
        this.mDateAlarm = dateAlarm;
        this.mDateCreate = new Date();
        this.mDateChange = new Date();
        this.mId = uniqIdNote;
    }

    public Note(Integer uniqIdNote, String theme, String description, Date dateAlarm, Boolean favoriteState) {
        this.mTheme = theme;
        this.mDescription = description;
        this.mDateAlarm = dateAlarm;
        this.mFavoriteState = favoriteState;
        this.mDateCreate = new Date();
        this.mDateChange = new Date();
        this.mId = uniqIdNote;
    }
    public Note(Integer uniqIdNote, String theme, String description, Date dateCreate, Date dateChange, Date dateAlarm, Boolean favoriteState) {
        this.mTheme = theme;
        this.mDescription = description;
        this.mDateAlarm = dateAlarm;
        this.mFavoriteState = favoriteState;
        this.mDateCreate = dateCreate;
        this.mDateChange = dateChange;
        this.mId = uniqIdNote;
    }

    public Integer getId() {
        return mId;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getTheme() {
        return mTheme;
    }

    public void setTheme(String theme) {
        this.mTheme = theme;
        this.mDateChange = new Date();
    }

    public void setDescription(String description) {
        this.mDescription = description;
        this.mDateChange = new Date();
    }

    public Date getDateCreate() {
        return mDateCreate;
    }

    public Date getDateChange() {
        return mDateChange;
    }

    public void editNote(String theme, String description) {
        this.mTheme = theme;
        this.mDescription = description;
        this.mDateChange = new Date();
    }

    public void editNote(String theme, String description, Date dateAlarm) {
        this.mTheme = theme;
        this.mDescription = description;
        this.mDateAlarm = dateAlarm;
        this.mDateChange = new Date();
    }

    public void editNote(String theme, String description, Date dateAlarm, Boolean favoriteState) {
        this.mTheme = theme;
        this.mDescription = description;
        this.mDateAlarm = dateAlarm;
        this.mFavoriteState = favoriteState;
        this.mDateChange = new Date();
    }

    public Date getDateAlarm() {
        return mDateAlarm;
    }

    public void setDateAlarm(Date mDateAlarm) {
        this.mDateAlarm = mDateAlarm;
    }

    public void setNote(Note note) {
        this.mTheme = note.getTheme();
        this.mDescription = note.getDescription();
        this.mDateCreate = note.getDateCreate();
        this.mDateChange = note.getDateChange();
        this.mFavoriteState = note.getFavoriteState();
        this.mId = note.getId();
    }

    public void newNote() {
        this.mDateCreate = new Date();
        this.mDateChange = new Date();
        this.mId = -1;
    }
}
