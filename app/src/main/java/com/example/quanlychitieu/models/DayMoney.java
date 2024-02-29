package com.example.quanlychitieu.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
@Entity(tableName = "daymoney")
public class DayMoney {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "date")
    public long date = new Date().getTime();

}
