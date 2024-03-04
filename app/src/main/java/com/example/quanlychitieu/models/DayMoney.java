package com.example.quanlychitieu.models;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.quanlychitieu.App;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity(tableName = "daymoney")
public class DayMoney {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "date")
    public long date = new Date().getTime();

    public int getCurrentDateInMonth() {
        App.calendar.setTimeInMillis(date);
        return App.calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getCurrentMonth() {
        App.calendar.setTimeInMillis(date);
        return App.calendar.get(Calendar.MONTH);
    }

    public int getCurrentYear() {
        App.calendar.setTimeInMillis(date);
        return App.calendar.get(Calendar.YEAR);
    }

    @SuppressLint("SimpleDateFormat")
    public String getDateString() {
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date(this.date));
    }
}
