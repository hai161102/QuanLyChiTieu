package com.example.quanlychitieu.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.quanlychitieu.database.dao.DayMoneyDao;
import com.example.quanlychitieu.database.dao.MoneyDataDao;
import com.example.quanlychitieu.models.DayMoney;
import com.example.quanlychitieu.models.MoneyData;

@Database(entities = {DayMoney.class, MoneyData.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DayMoneyDao dayMoney();
    public abstract MoneyDataDao moneyData();

    private static AppDatabase instance;
    public static void init(Context context) {
        AppDatabase.instance = Room.databaseBuilder(
                context,
                AppDatabase.class,
                "qlct"
        )
                .allowMainThreadQueries()
                .build();
    }
    public static AppDatabase getInstance() {
        return instance;
    }
}
