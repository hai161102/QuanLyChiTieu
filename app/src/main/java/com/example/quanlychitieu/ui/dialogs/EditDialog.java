package com.example.quanlychitieu.ui.dialogs;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlychitieu.App;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.base.BaseDialog;
import com.example.quanlychitieu.databinding.DialogEditBinding;
import com.example.quanlychitieu.interfaces.DialogActionListener;
import com.example.quanlychitieu.models.MoneyData;

public class EditDialog extends BaseDialog<DialogEditBinding> {

    private MoneyData moneyData;
    private DialogActionListener listener;
    public void setListener(DialogActionListener listener) {
        this.listener = listener;
    }

    public EditDialog(@NonNull Context context) {
        super(context);
    }

    public EditDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public EditDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public MoneyData getMoneyData() {
        return moneyData;
    }

    public void setMoneyData(MoneyData moneyData) {
        this.moneyData = moneyData;
        if (this.loaded) {
            this.binding.editTitle.setText(this.moneyData.getDescription());
            this.binding.editMoney.setText(String.valueOf(this.moneyData.getMoney()));
        }
        else {
            this.onDialogLoaded = () -> {
                this.setMoneyData(moneyData);
            };
        }
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        this.binding.btnEdit.setOnClickListener(v -> {
            if (this.binding.editMoney.length() <= 0
                    || this.binding.editTitle.length() <= 0) {
                App.ToastErrorNullInfo(this.getContext());
                return;
            }
            if (this.listener != null) this.listener.onEdit(
                    Float.parseFloat(this.binding.editMoney.getText().toString()),
                    this.binding.editTitle.getText().toString()
            );
            this.dismiss();
        });

    }

    @Override
    public void dismiss() {
        if (this.listener != null) this.listener.onCancel();
        super.dismiss();
    }

    @Override
    protected int layoutId() {
        return R.layout.dialog_edit;
    }
}
