package com.example.quanlychitieu.ui.adapters;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter<T extends Fragment> extends FragmentStateAdapter {

    private final List<T> list = new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void add(T fragment) {
        this.list.add(fragment);
        notifyDataSetChanged();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void add(int index, T fragment) {
        this.list.add(index, fragment);
        notifyDataSetChanged();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void addAll(List<T> fragments) {
        this.list.addAll(fragments);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public T createFragment(int position) {
        return list.get(position);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<T> getList() {
        return list;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clear() {
        this.list.clear();
        notifyDataSetChanged();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void remove(T fragment) {
        this.list.remove(fragment);
        notifyDataSetChanged();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void removes(List<T> fragments) {
        this.list.removeAll(fragments);
        notifyDataSetChanged();
    }


}
