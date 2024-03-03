package com.example.quanlychitieu.base;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<M, E extends ViewDataBinding, T extends BaseHolder<E, M>> extends RecyclerView.Adapter<T> {

    protected final Context context;
    protected final List<M> list = new ArrayList<>();
    public BaseAdapter(Context context) {
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void add(M data) {
        this.list.add(data);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addMulti(List<M> data, boolean isClear) {
        if (isClear) {
            this.list.clear();
        }
        list.addAll(data);
        notifyDataSetChanged();
    }
    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull T holder, int position) {
        holder.setAdapter((BaseAdapter<M, E, BaseHolder<E, M>>) this);
        holder.load(this.list.get(position));
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public List<M> getList() {
        return list;
    }

    protected abstract int getLayoutId();
}
