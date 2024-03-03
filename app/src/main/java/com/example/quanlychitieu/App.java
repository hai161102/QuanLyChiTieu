package com.example.quanlychitieu;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.example.quanlychitieu.database.AppDatabase;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppDatabase.init(this);
    }

    public static void ToastErrorNullInfo(Context context) {
        Toast.makeText(context, "Vui lòng nhập đủ thông tin", Toast.LENGTH_LONG).show();
    }
}
