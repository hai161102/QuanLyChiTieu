package com.example.quanlychitieu.base;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    protected T binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.inflate(
                LayoutInflater.from(this),
                layoutId(),
                null,
                false
        );
        this.setContentView(this.binding.getRoot());
        this.onLoad();
    }

    protected abstract void onLoad();
    protected abstract int layoutId();

}
