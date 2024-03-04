package com.example.quanlychitieu.ui.holders;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.quanlychitieu.base.BaseHolder;
import com.example.quanlychitieu.database.AppDatabase;
import com.example.quanlychitieu.databinding.ItemMoneyDataBinding;
import com.example.quanlychitieu.interfaces.DialogActionListener;
import com.example.quanlychitieu.interfaces.ItemMoneyDataActionListener;
import com.example.quanlychitieu.models.MoneyData;
import com.example.quanlychitieu.ui.dialogs.EditDialog;

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
        this.binding.edit.setOnClickListener(v -> {
            EditDialog editDialog = new EditDialog(this.context);
            editDialog.setMoneyData(data);
            editDialog.setListener(new DialogActionListener() {
                @Override
                public void onEdit(float money, String title) {
                    data.setMoney(money);
                    data.setDescription(title);
                    AppDatabase.getInstance().moneyData().update(data);
                    ItemMoneyDataHolder.this.adapter.notifyItemChanged(
                            ItemMoneyDataHolder.this.getAdapterPosition()
                    );
                }

                @Override
                public void onCancel() {

                }
            });
            editDialog.show();
        });
    }

}
