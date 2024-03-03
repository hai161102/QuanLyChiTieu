package com.example.quanlychitieu.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.quanlychitieu.App;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.base.BaseActivity;
import com.example.quanlychitieu.database.AppDatabase;
import com.example.quanlychitieu.databinding.ActivityMainBinding;
import com.example.quanlychitieu.interfaces.ItemDayMoneyActionListener;
import com.example.quanlychitieu.models.DayMoney;
import com.example.quanlychitieu.models.MoneyData;
import com.example.quanlychitieu.models.MoneyType;
import com.example.quanlychitieu.ui.adapters.ItemEarnPayDayAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    protected ItemEarnPayDayAdapter itemEarnPayDayAdapter;
    public static final int ADD_ACTIVITY_REQUEST_CODE = 1;
    public static final int DAY_DETAILS_ACTIVITY_REQUEST_CODE = 2;

    @Override
    protected void onLoad() {

        this.itemEarnPayDayAdapter = new ItemEarnPayDayAdapter(this);
        this.itemEarnPayDayAdapter.setListener(new ItemDayMoneyActionListener() {
            @Override
            public void onShowDetailsClick(DayMoney dayMoney) {
                DaySpendingActivity.start(MainActivity.this, DAY_DETAILS_ACTIVITY_REQUEST_CODE, dayMoney.id);
            }
        });
        this.binding.rcvNotes.setAdapter(this.itemEarnPayDayAdapter);
        this.reloadView();

        binding.addBtn.setOnClickListener(v -> {
            AddActivity.start(this, ADD_ACTIVITY_REQUEST_CODE);
        });
    }
    @SuppressLint("SetTextI18n")
    private void reloadView() {
        List<DayMoney> list = AppDatabase.getInstance().dayMoney().all();
        Collections.reverse(list);
        this.updateStateMonth(list);
        if (list.size() > 0) {
            float[] currentDayData = this.updateState(list.get(0));
            this.binding.totalMoneyDay.setText("" + currentDayData[0]);
            this.binding.moneyEarnDay.setText("" + currentDayData[1]);
            this.binding.moneyPayDayh.setText("" + currentDayData[2]);
        }
        this.itemEarnPayDayAdapter.addMulti(list, true);
    }

    @SuppressLint("SetTextI18n")
    private float[] updateState(DayMoney dayMoney) {
        List<MoneyData> moneyDataIns = new ArrayList<>();
        List<MoneyData> moneyDataOuts = new ArrayList<>();

        for (MoneyData moneyData : AppDatabase.getInstance().moneyData().getByDate(dayMoney.id)) {
            if (moneyData.getMoneyType().equals(MoneyType.IN)) {
                moneyDataIns.add(moneyData);
            } else {
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
        return new float[]{
                moneyInDay - moneyOutDay,
                moneyInDay,
                moneyOutDay
        };
    }

    @SuppressLint("SetTextI18n")
    private void updateStateMonth(List<DayMoney> dayMonies) {
        List<DayMoney> monthData = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.getTime().getMonth();
        int currentYear = calendar.getTime().getYear() + 1900;
        for (DayMoney dayMoney : dayMonies) {
//            Log.d("updateStateMonth", "updateStateMonth: " + dayMoney.getCurrentYear());
            if (dayMoney.getCurrentMonth() == currentMonth
                    && dayMoney.getCurrentYear() == currentYear) {
                monthData.add(dayMoney);
            }
        }

        float moneyEarned = 0f;
        float moneyPayed = 0f;
        for (DayMoney dayMoney : monthData) {
            for (MoneyData moneyData : AppDatabase
                    .getInstance()
                    .moneyData()
                    .getByDate(dayMoney.id)) {
                if (moneyData.getMoneyType().equals(MoneyType.IN)) {
                    moneyEarned += moneyData.getMoney();
                }
                else {
                    moneyPayed += moneyData.getMoney();
                }
            }
        }

        this.binding.totalMoneyMonth.setText("" + (moneyEarned - moneyPayed));
        this.binding.moneyEarnMonth.setText("" + moneyEarned);
        this.binding.moneyPayMonth.setText("" + moneyPayed);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if (requestCode == ADD_ACTIVITY_REQUEST_CODE || requestCode == DAY_DETAILS_ACTIVITY_REQUEST_CODE) {
                this.reloadView();
            }
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }
}