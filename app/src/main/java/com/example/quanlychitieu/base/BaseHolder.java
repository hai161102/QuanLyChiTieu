package com.example.quanlychitieu.base;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
public abstract class BaseHolder<T extends ViewDataBinding, M> extends RecyclerView.ViewHolder {
    protected T binding;
    protected final Context context;
    protected BaseAdapter<M, T, BaseHolder<T, M>> adapter;

    public BaseAdapter<M, T, BaseHolder<T, M>> getAdapter() {
        return adapter;
    }

    public BaseHolder<T, M> setAdapter(BaseAdapter<M, T, BaseHolder<T, M>> adapter) {
        this.adapter = adapter;
        return this;
    }

    public BaseHolder(Context context, @NonNull T binding) {
        super(binding.getRoot());
        this.context = context;
        this.binding = binding;
    }

    public abstract void load(M data);

}
