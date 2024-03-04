package com.example.quanlychitieu.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.example.quanlychitieu.interfaces.OnFragmentLoaded;

public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {

    protected BaseActivity<?> parent;
    public BaseFragment(BaseActivity<?> parent) {
        this.parent = parent;
    }

    protected boolean loaded = false;
    protected OnFragmentLoaded onFragmentLoaded;
    protected T binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(inflater, this.getLayoutId(), container, false);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initEvent();
        this.loaded = true;
        if (onFragmentLoaded != null) onFragmentLoaded.onLoaded();
    }

    protected abstract void initView();
    protected abstract void initEvent();
    protected abstract int getLayoutId();
}
