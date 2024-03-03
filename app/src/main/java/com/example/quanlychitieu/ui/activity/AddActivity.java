package com.example.quanlychitieu.ui.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.DatePicker;

import androidx.annotation.RequiresApi;

import com.example.quanlychitieu.App;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.base.BaseActivity;
import com.example.quanlychitieu.database.AppDatabase;
import com.example.quanlychitieu.databinding.ActivityAddBinding;
import com.example.quanlychitieu.models.DayMoney;
import com.example.quanlychitieu.models.MoneyData;
import com.example.quanlychitieu.models.MoneyType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddActivity extends BaseActivity<ActivityAddBinding> {

    protected MoneyData moneyDataIn;
    protected MoneyData moneyDataOut;
    protected DayMoney dayMoney;

    public static void start(Activity context, int requestCode) {
        Intent starter = new Intent(context, AddActivity.class);
        context.startActivityForResult(starter, requestCode);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onLoad() {
        this.moneyDataIn = new MoneyData();
        this.moneyDataIn.setMoneyType(MoneyType.IN);

        this.moneyDataOut = new MoneyData();
        this.moneyDataOut.setMoneyType(MoneyType.OUT);

        Date date = new Date();
        updateDayMoney(date);
        this.binding.dateTimeCurrent.setText(date.toInstant().toString().split("T")[0]);
        this.binding.dateTimeCurrent.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (datePicker, i, i1, i2) -> {
                        date.setYear(i - 1900);
                        date.setMonth(i1);
                        date.setDate(i2);
                        this.binding.dateTimeCurrent.setText(
                                date.toInstant().toString().split("T")[0]
                        );
                        updateDayMoney(date);
                    }, date.getYear() + 1900, date.getMonth(), date.getDate());
            datePickerDialog.show();
        });

        this.updateState();

        this.binding.addBtnIn.setOnClickListener(v -> {
            if (this.binding.soTienIn.getText().length() <= 0
                    || this.binding.typeInputIn.getText().length() <= 0) {
                App.ToastErrorNullInfo(this);
                return;
            }
            this.moneyDataIn.setDateId(dayMoney.id);
            this.moneyDataIn.setMoney(Float.parseFloat(this.binding.soTienIn.getText().toString()));
            this.moneyDataIn.setDescription(this.binding.typeInputIn.getText().toString());
            AppDatabase.getInstance().moneyData().insert(moneyDataIn);
            updateState();
            clearTextData(MoneyType.IN);
        });

        this.binding.addBtnOut.setOnClickListener(v -> {
            if (this.binding.soTienChi.getText().length() <= 0
                    || this.binding.typeInputOut.getText().length() <= 0) {
                App.ToastErrorNullInfo(this);
                return;
            }
            this.moneyDataOut.setDateId(dayMoney.id);
            this.moneyDataOut.setMoney(Float.parseFloat(this.binding.soTienChi.getText().toString()));
            this.moneyDataOut.setDescription(this.binding.typeInputOut.getText().toString());
            AppDatabase.getInstance().moneyData().insert(moneyDataOut);
            updateState();
            clearTextData(MoneyType.OUT);
        });

        this.binding.back.setOnClickListener(view -> {
            this.onBackPressed();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateDayMoney(Date date) {
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
    }

    private void updateState() {
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

        String textInfo = ""
                + "Tiền thu: " + moneyInDay
                + "\nTiền chi: " + moneyOutDay
                + "\nTổng kết: " + (moneyInDay - moneyOutDay);
        binding.dayInfo.setText(textInfo);

    }

    private void clearTextData(MoneyType moneyType) {
        switch (moneyType) {
            case IN:
                this.binding.soTienIn.setText("");
                this.binding.typeInputIn.setText("");
                break;
            case OUT:
                this.binding.soTienChi.setText("");
                this.binding.typeInputOut.setText("");
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_add;
    }
}
