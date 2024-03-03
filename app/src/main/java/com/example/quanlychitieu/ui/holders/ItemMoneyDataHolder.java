package com.example.quanlychitieu.ui.holders;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.quanlychitieu.base.BaseHolder;
import com.example.quanlychitieu.databinding.ItemMoneyDataBinding;
import com.example.quanlychitieu.interfaces.ItemMoneyDataActionListener;
import com.example.quanlychitieu.models.MoneyData;

public class ItemMoneyDataHolder extends BaseHolder<ItemMoneyDataBinding, MoneyData> {

    private ItemMoneyDataActionListener listener;

    public void setListener(ItemMoneyDataActionListener listener) {
        this.listener = listener;
    }

    public ItemMoneyDataHolder(Context context, @NonNull ItemMoneyDataBinding binding) {
        super(context, binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void load(MoneyData data) {
        this.binding.title.setText(data.getDescription());
        this.binding.money.setText("" + data.getMoney());
        this.binding.remove.setOnClickListener(view -> {
            if (this.listener != null) {
                this.listener.onDeleteClick(data);
            }
        });
    }

}
