package com.example.quanlychitieu.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.base.BaseAdapter;
import com.example.quanlychitieu.databinding.ItemEarnPayDayBinding;
import com.example.quanlychitieu.interfaces.ItemDayMoneyActionListener;
import com.example.quanlychitieu.models.DayMoney;
import com.example.quanlychitieu.ui.holders.ItemEarnPayDayHolder;

public class ItemEarnPayDayAdapter extends BaseAdapter<DayMoney, ItemEarnPayDayBinding, ItemEarnPayDayHolder> {
    private ItemDayMoneyActionListener listener;

    public void setListener(ItemDayMoneyActionListener listener) {
        this.listener = listener;
    }

    public ItemEarnPayDayAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_earn_pay_day;
    }
    @NonNull
    @Override
    public ItemEarnPayDayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEarnPayDayHolder itemEarnPayDayHolder = new ItemEarnPayDayHolder(
                this.context,
                DataBindingUtil.inflate(
                        LayoutInflater.from(this.context),
                        getLayoutId(),
                        parent, false
                )
        );
        itemEarnPayDayHolder.setListener(new ItemDayMoneyActionListener() {
            @Override
            public void onShowDetailsClick(DayMoney dayMoney) {
                if (ItemEarnPayDayAdapter.this.listener != null) {
                    ItemEarnPayDayAdapter.this.listener.onShowDetailsClick(dayMoney);
                }
            }
        });
        return itemEarnPayDayHolder;
    }
}
