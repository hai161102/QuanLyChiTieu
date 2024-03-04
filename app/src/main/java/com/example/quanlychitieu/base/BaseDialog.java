package com.example.quanlychitieu.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.interfaces.OnDialogLoaded;

public abstract class BaseDialog<T extends ViewDataBinding> extends Dialog {

    protected T binding;
    protected boolean loaded = false;
    protected OnDialogLoaded onDialogLoaded;

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.binding = DataBindingUtil.inflate(
                LayoutInflater.from(this.getContext()),
                this.layoutId(),
                null,
                false
        );
        this.setContentView(this.binding.getRoot());
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.windowAnimations = R.style.animation;
        params.horizontalMargin = 24f;
        window.setAttributes(params);
        this.onLoad(savedInstanceState);
        this.loaded = true;
        if (this.onDialogLoaded != null) {
            this.onDialogLoaded.onLoaded();
        }
    }

    @Override
    public void show() {
        super.show();
    }

    protected abstract void onLoad(Bundle savedInstanceState);
    protected abstract int layoutId();
}
