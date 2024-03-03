package com.example.quanlychitieu.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.viewpager2.widget.ViewPager2;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.base.BaseActivity;
import com.example.quanlychitieu.database.AppDatabase;
import com.example.quanlychitieu.databinding.ActivityDaySpendingBinding;
import com.example.quanlychitieu.models.DayMoney;
import com.example.quanlychitieu.models.MoneyData;
import com.example.quanlychitieu.models.MoneyType;
import com.example.quanlychitieu.ui.adapters.ViewPagerAdapter;
import com.example.quanlychitieu.ui.fragments.DaySpendingFragment;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DaySpendingActivity extends BaseActivity<ActivityDaySpendingBinding> {

    public static void start(Activity context, int requestCode, int dayMoneyId) {
        Intent starter = new Intent(context, DaySpendingActivity.class);
        starter.putExtra("dayMoneyId", dayMoneyId);
        context.startActivityForResult(starter, requestCode);
    }

    private ViewPagerAdapter<DaySpendingFragment> daySpendingFragmentViewPagerAdapter;
    private DaySpendingFragment earnFragment;
    private DaySpendingFragment payFragment;
    private DayMoney dayMoney;

    private final ViewPager2.OnPageChangeCallback onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            DaySpendingActivity.this.onPageChange(position);
        }
    };

    @SuppressLint("ResourceAsColor")
    private void onPageChange(int position) {
        ColorStateList bgColorDefault = ColorStateList.valueOf(
                getColor(R.color.white)
        );
        this.binding.earnBtn.setTextColor(
                getColor(
                        R.color.black
                )
        );
        this.binding.payBtn.setTextColor(
                getColor(R.color.black)
        );
        this.binding.earnBtn.setBackgroundTintList(bgColorDefault);
        this.binding.payBtn.setBackgroundTintList(bgColorDefault);

        switch (position) {
            case 0:
                this.binding.earnBtn.setTextColor(getColor(
                        R.color.white
                ));
                this.binding.earnBtn.setBackgroundTintList(ColorStateList.valueOf(getColor(
                        R.color.app_color
                )));
                break;
            case 1:
                this.binding.payBtn.setTextColor(getColor(
                        R.color.white
                ));
                this.binding.payBtn.setBackgroundTintList(ColorStateList.valueOf(getColor(
                        R.color.app_color
                )));
                break;
            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onLoad() {
        int dayMoneyId = getIntent().getIntExtra("dayMoneyId", 1);
        dayMoney = AppDatabase.getInstance().dayMoney().get(dayMoneyId);
        this.binding.dateTime.setText(
                new Date(dayMoney.date).toInstant().toString()
                        .split("T")[0].trim()
        );
        this.daySpendingFragmentViewPagerAdapter = new ViewPagerAdapter<>(this);
        this.binding.viewPager.setAdapter(daySpendingFragmentViewPagerAdapter);
        this.binding.viewPager.registerOnPageChangeCallback(onPageChangeCallback);
        this.reloadView();

        this.binding.earnBtn.setOnClickListener(view -> {
            this.binding.viewPager.setCurrentItem(0, true);
        });
        this.binding.payBtn.setOnClickListener(view -> {
            this.binding.viewPager.setCurrentItem(1, true);
        });
        this.binding.back.setOnClickListener(view -> this.onBackPressed());

    }

    private void reloadView() {
        List<MoneyData> moneyDataList = AppDatabase.getInstance().moneyData().getByDate(dayMoney.id);
        Collections.reverse(moneyDataList);

        earnFragment = new DaySpendingFragment(
                this,
                moneyDataList
                        .stream()
                        .filter(moneyData -> moneyData.getMoneyType().equals(MoneyType.IN))
                        .collect(Collectors.toList())
        );
        payFragment = new DaySpendingFragment(
                this,
                moneyDataList
                        .stream()
                        .filter(moneyData -> moneyData.getMoneyType().equals(MoneyType.OUT))
                        .collect(Collectors.toList())
        );

        daySpendingFragmentViewPagerAdapter.add(earnFragment);
        daySpendingFragmentViewPagerAdapter.add(payFragment);
        this.binding.viewPager.setCurrentItem(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.binding.viewPager.unregisterOnPageChangeCallback(onPageChangeCallback);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_day_spending;
    }
}
