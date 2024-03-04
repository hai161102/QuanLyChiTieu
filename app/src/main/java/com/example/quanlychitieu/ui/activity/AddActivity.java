package com.example.quanlychitieu.ui.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AddActivity extends BaseActivity<ActivityAddBinding> {

    private static String DATE_EXTRA_KEY = "DATE_EXTRA_KEY";
    protected MoneyData moneyDataIn;
    protected MoneyData moneyDataOut;
    protected DayMoney dayMoney;

    public static void start(Activity context, int requestCode) {
        Intent starter = new Intent(context, AddActivity.class);
        context.startActivityForResult(starter, requestCode);
    }

    public static void start(Activity context, int requestCode, int dateId) {
        Intent starter = new Intent(context, AddActivity.class);
        starter.putExtra(DATE_EXTRA_KEY, dateId);
        context.startActivityForResult(starter, requestCode);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onLoad() {
        this.moneyDataIn = new MoneyData();
        this.moneyDataIn.setMoneyType(MoneyType.IN);

        this.moneyDataOut = new MoneyData();
        this.moneyDataOut.setMoneyType(MoneyType.OUT);
        int dateId = this.getIntent().getIntExtra(DATE_EXTRA_KEY, 0);
        Date date = new Date();
        if (dateId != 0) {
            this.dayMoney = AppDatabase.getInstance().dayMoney().get(dateId);
            date.setTime(this.dayMoney.date);
        }
        this.updateDayMoney(date);
        this.binding.dateTimeCurrent.setText(dayMoney.getDateString());
        this.binding.dateTimeCurrent.setOnClickListener(view -> {
            if (dateId != 0) return;
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (datePicker, i, i1, i2) -> {
                        App.calendar.set(
                                i - 1900,
                                i1,
                                i2,
                                0,
                                0,
                                0);


                        if (App.calendar.get(Calendar.MILLISECOND) < date.getTime()) {
                            date.setYear(i - 1900);
                            date.setMonth(i1);
                            date.setDate(i2);
                        }
                        this.updateDayMoney(date);
                        this.binding.dateTimeCurrent.setText(dayMoney.getCurrentMonth());
                    }, date.getYear() + 1900, date.getMonth(), date.getDate());
            datePickerDialog.show();
        });

        this.updateState();

        this.binding.addBtnIn.setOnClickListener(v -> {
            if (dayMoney.id < 1) return;
            if (this.binding.soTienIn.getText().length() <= 0
                    || this.binding.typeInputIn.getText().length() <= 0) {
                App.ToastErrorNullInfo(this);
                return;
            }
            this.moneyDataIn.setDateId(this.dayMoney.id);
            this.moneyDataIn.setMoney(Float.parseFloat(this.binding.soTienIn.getText().toString()));
            this.moneyDataIn.setDescription(this.binding.typeInputIn.getText().toString());
            AppDatabase.getInstance().moneyData().insert(this.moneyDataIn);
            this.updateState();
            this.clearTextData(MoneyType.IN);
        });

        this.binding.addBtnOut.setOnClickListener(v -> {
            if (dayMoney.id < 1) return;
            if (this.binding.soTienChi.getText().length() <= 0
                    || this.binding.typeInputOut.getText().length() <= 0) {
                App.ToastErrorNullInfo(this);
                return;
            }
            this.moneyDataOut.setDateId(this.dayMoney.id);
            this.moneyDataOut.setMoney(Float.parseFloat(this.binding.soTienChi.getText().toString()));
            this.moneyDataOut.setDescription(this.binding.typeInputOut.getText().toString());
            AppDatabase.getInstance().moneyData().insert(this.moneyDataOut);
            this.updateState();
            this.clearTextData(MoneyType.OUT);
        });

        this.binding.back.setOnClickListener(view -> {
            this.backPress();
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateDayMoney(Date date) {
        this.dayMoney = null;
        for (DayMoney money : AppDatabase.getInstance().dayMoney().all()) {
            Date d = new Date(money.date);
            App.calendar.setTime(date);
            int dateOfMonth = App.calendar.get(Calendar.DAY_OF_MONTH);
            int monthOfYear = App.calendar.get(Calendar.MONTH);
            int year = App.calendar.get(Calendar.YEAR);
            App.calendar.setTime(d);
            int dateNow = App.calendar.get(Calendar.DAY_OF_MONTH);
            int monthNow = App.calendar.get(Calendar.MONTH);
            int yearNow = App.calendar.get(Calendar.YEAR);
            if (dateOfMonth == dateNow
                    && monthOfYear == monthNow
                    && year == yearNow) {
                this.dayMoney = money;
                return;
            }
        }
        if (this.dayMoney == null) {
            this.dayMoney = new DayMoney();
            this.dayMoney.date = date.getTime();
            AppDatabase.getInstance().dayMoney().insert(this.dayMoney);
            List<DayMoney> l = AppDatabase.getInstance().dayMoney().all();
            this.dayMoney = l.get(l.size() - 1);
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
        this.binding.dayInfo.setText(textInfo);

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
    protected void onBackPressHandle() {
        this.setResult(RESULT_OK);
        super.onBackPressHandle();
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_add;
    }
}
