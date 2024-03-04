package com.example.quanlychitieu;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.example.quanlychitieu.database.AppDatabase;

import java.util.Calendar;

public class App extends Application {

    public static Calendar calendar;
    @Override
    public void onCreate() {
        super.onCreate();
        AppDatabase.init(this);
        App.calendar = Calendar.getInstance();
    }

    public static void ToastErrorNullInfo(Context context) {
        Toast.makeText(context, "Vui lòng nhập đủ thông tin", Toast.LENGTH_LONG).show();
    }
}
