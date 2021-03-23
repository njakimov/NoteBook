package com.e.notebook.model;

import java.util.Date;

/**
 * Класс заметка для редактирования
 */
public class NoteToEdit extends Note {

    private static NoteToEdit instance;

    public static NoteToEdit getInstance() { // #3
        if (instance == null) {                                                                                            // если объект еще не создан
            instance = new NoteToEdit();                                                                                    // создать новый объект
        }
        return instance;                                                                                                // вернуть ранее созданный объект
    }

    public NoteToEdit() {
        super(0, "", "");
    }


    public void destroy() {
        instance = null;
    }


}
