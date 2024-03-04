package com.example.quanlychitieu.ui.holders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.base.BaseHolder;
import com.example.quanlychitieu.database.AppDatabase;
import com.example.quanlychitieu.databinding.ItemEarnPayDayBinding;
import com.example.quanlychitieu.interfaces.ItemDayMoneyActionListener;
import com.example.quanlychitieu.models.DayMoney;
import com.example.quanlychitieu.models.MoneyData;
import com.example.quanlychitieu.models.MoneyType;
import com.example.quanlychitieu.ui.activity.DaySpendingActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItemEarnPayDayHolder extends BaseHolder<ItemEarnPayDayBinding, DayMoney> {

    private ItemDayMoneyActionListener listener;

    public void setListener(ItemDayMoneyActionListener listener) {
        this.listener = listener;
    }

    public ItemEarnPayDayHolder(Context context, @NonNull ItemEarnPayDayBinding binding) {
        super(context, binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void load(DayMoney data) {
        this.binding.dateTime.setText(data.getDateString());
        updateState(data);
        this.binding.showDetails.setOnClickListener(view -> {
            if (this.listener != null) {
                this.listener.onShowDetailsClick(data);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateState(DayMoney dayMoney) {
        List<MoneyData> moneyDataIns = new ArrayList<>();
        List<MoneyData> moneyDataOuts = new ArrayList<>();

        for (MoneyData moneyData : AppDatabase.getInstance().moneyData().getByDate(dayMoney.id)) {
            if (moneyData.getMoneyType().equals(MoneyType.IN)) {
                moneyDataIns.add(moneyData);
            }
            else {
                moneyDataOuts.add(moneyData);
            }
        }

        float moneyInDay = 0f;
        float moneyOutDay = 0f;

        for (MoneyData dataIn : moneyDataIns) {
            moneyInDay += dataIn.getMoney();
        }
        for (MoneyData dataOut : moneyDataOuts) {
            moneyOutDay += dataOut.getMoney();
        }
        float outMoney = moneyInDay - moneyOutDay;
        this.binding.emoji.setImageResource(
                outMoney >= 0 ? R.drawable.good : R.drawable.bad
        );
        this.binding.totalMoneyDay.setText("" + outMoney);
        this.binding.moneyEarnDay.setText("" + moneyInDay);
        this.binding.moneyPayDayh.setText("" + moneyOutDay);
    }

}
