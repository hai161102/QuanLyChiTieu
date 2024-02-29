package com.example.quanlychitieu;

import android.app.Application;

import com.example.quanlychitieu.database.AppDatabase;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppDatabase.init(this);
    }
}
