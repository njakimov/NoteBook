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
     * Преобразование строки в дату
     *
     * @param year  - год
     * @param month - месяц
     * @param day   - день
     * @return дата
     */
    public static Date formatStringToDate(int year, int month, int day) {
        String s = "" + year + "-" + (month < 10 ? "0" + "" + (month + 1) : (month + 1)) + "-" + (day < 10 ? "0" + "" + day : day);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date docDate = null;
        try {
            docDate = format.parse(s);
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
        return docDate;
    }

    /**
     * Преобразование строки в дату и время
     *
     * @param s  - форматированная строка даты
     * @return дата
     */
    public static Date formatStringToDate(String s) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
        Date docDate = null;
        try {
            docDate = format.parse(s);
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
        return docDate;
    }

    /**
     * Преобразование строки в дату и время
     *
     * @param year  - год
     * @param month - месяц
     * @param day   - день
     * @param hour   - час
     * @param minute - минута
     * @return дата
     */
    public static String formatDateString(int year, int month, int day, int hour, int minute) {
        return "" + year +
                "-" + (month < 10 ? "0" + "" + (month + 1) : (month + 1)) +
                "-" + (day < 10 ? "0" + "" + day : day) +
                "T" + (hour < 10 ? "0" + "" + hour : hour) +
                ":" + (minute < 10 ? "0" + "" + minute : minute) +
                ":00";

    }
}
