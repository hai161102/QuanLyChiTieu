package com.example.quanlychitieu.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.base.BaseActivity;
import com.example.quanlychitieu.databinding.ActivitySplashBinding;


@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity<ActivitySplashBinding> {
    @Override
    protected void onLoad() {
        new Handler().postDelayed(() -> {
            this.startActivity(new Intent(this, MainActivity.class));
            this.finish();
        }, 1000);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_splash;
    }
}
