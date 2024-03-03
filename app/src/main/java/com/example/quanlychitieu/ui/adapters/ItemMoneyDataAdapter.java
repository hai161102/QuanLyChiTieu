package com.example.quanlychitieu.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.base.BaseAdapter;
import com.example.quanlychitieu.database.AppDatabase;
import com.example.quanlychitieu.databinding.ItemMoneyDataBinding;
import com.example.quanlychitieu.interfaces.ItemMoneyDataActionListener;
import com.example.quanlychitieu.models.MoneyData;
import com.example.quanlychitieu.ui.holders.ItemMoneyDataHolder;

public class ItemMoneyDataAdapter extends BaseAdapter<MoneyData, ItemMoneyDataBinding, ItemMoneyDataHolder> {
    public ItemMoneyDataAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_money_data;
    }

    @NonNull
    @Override
    public ItemMoneyDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMoneyDataHolder itemMoneyDataHolder = new ItemMoneyDataHolder(
                this.context,
                DataBindingUtil.inflate(
                        LayoutInflater.from(this.context),
                        getLayoutId(),
                        parent, false
                )
        );
        itemMoneyDataHolder.setListener(new ItemMoneyDataActionListener() {
            @Override
            public void onEditClick(MoneyData moneyData) {

            }

            @Override
            public void onDeleteClick(MoneyData moneyData) {
                AppDatabase.getInstance().moneyData().delete(moneyData);
                int pos = ItemMoneyDataAdapter.this.list.indexOf(moneyData);
                ItemMoneyDataAdapter.this.list.remove(moneyData);
                ItemMoneyDataAdapter.this.notifyItemRemoved(pos);
            }
        });
        return itemMoneyDataHolder;
    }
}
