package com.example.quanlychitieu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.quanlychitieu.App;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.base.BaseActivity;
import com.example.quanlychitieu.database.AppDatabase;
import com.example.quanlychitieu.databinding.ActivityAddBinding;
import com.example.quanlychitieu.models.DayMoney;
import com.example.quanlychitieu.models.MoneyData;
import com.example.quanlychitieu.models.MoneyType;

import java.util.Date;

public class AddActivity extends BaseActivity<ActivityAddBinding> {

    protected MoneyData moneyDataIn;
    protected MoneyData moneyDataOut;

    protected DayMoney dayMoney;

    public static void start(Context context) {
        Intent starter = new Intent(context, AddActivity.class);
        context.startActivity(starter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onLoad() {
        this.moneyDataIn = new MoneyData();
        this.moneyDataIn.setMoneyType(MoneyType.IN);

        this.moneyDataOut = new MoneyData();
        this.moneyDataOut.setMoneyType(MoneyType.OUT);

        Date date = new Date();
        String currentDay = date.toInstant().toString().split("T")[0].trim();
        for (DayMoney money : AppDatabase.getInstance().dayMoney().all()) {
            String dateTime = new Date(money.date)
                    .toInstant()
                    .toString()
                    .split("T")[0]
                    .trim();
            if (currentDay.equals(dateTime)) {
                this.dayMoney = money;
                break;
            }
        }
        if (this.dayMoney == null) {
            this.dayMoney = new DayMoney();
            this.dayMoney.date = date.getTime();
            AppDatabase.getInstance().dayMoney().insert(this.dayMoney);
        }

        this.binding.addBtnIn.setOnClickListener(v -> {
            this.moneyDataIn.setDateId(dayMoney.id);
            this.moneyDataIn.setMoney(Float.parseFloat(this.binding.soTienIn.getText().toString()));
            this.moneyDataIn.setDescription(this.binding.typeInputIn.getText().toString());
        });

        this.binding.addBtnOut.setOnClickListener(v -> {
            this.moneyDataOut.setDateId(dayMoney.id);
            this.moneyDataOut.setMoney(Float.parseFloat(this.binding.soTienChi.getText().toString()));
            this.moneyDataOut.setDescription(this.binding.soTienChi.getText().toString());
        });



    }

    @Override
    protected int layoutId() {
        return R.layout.activity_add;
    }
}
