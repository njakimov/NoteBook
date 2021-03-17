package com.e.notebook.service;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Common {
    @SuppressLint("SimpleDateFormat")
    public static String formatDateTimeToString (Date date){
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault()).format(date);
    }
}
