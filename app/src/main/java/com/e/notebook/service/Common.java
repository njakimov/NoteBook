package com.e.notebook.service;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Common {
    /**
     * Преобразование даты в строку нужного формата
     *
     * @param date - дата
     * @return - строка
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatDateTimeToString(Date date) {
        String temp = "";
        if (date != null) {
            temp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault()).format(date);
        }
        return temp;
    }

    /**
     *  Преобразование строки в дату
     * @param year - год
     * @param month - месяц
     * @param day - день
     * @return дата
     */
    public static Date formatStringToDate(int year,int month,int day) {
        return new Date(year, month, day);
    }
}
