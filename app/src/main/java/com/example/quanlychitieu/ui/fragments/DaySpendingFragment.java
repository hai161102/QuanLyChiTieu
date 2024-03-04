package com.example.quanlychitieu.ui.fragments;

import android.view.View;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.base.BaseActivity;
import com.example.quanlychitieu.base.BaseFragment;
import com.example.quanlychitieu.databinding.FragmentListMoneyDataBinding;
import com.example.quanlychitieu.interfaces.OnFragmentLoaded;
import com.example.quanlychitieu.models.DayMoney;
import com.example.quanlychitieu.models.MoneyData;
import com.example.quanlychitieu.ui.adapters.ItemMoneyDataAdapter;

import java.util.List;

public class DaySpendingFragment extends BaseFragment<FragmentListMoneyDataBinding> {

    private ItemMoneyDataAdapter itemMoneyDataAdapter;
    private final List<MoneyData> list;

    public DaySpendingFragment(BaseActivity<?> parent, List<MoneyData> list) {
        super(parent);
        this.list = list;

    }

    public void add(MoneyData moneyData) {
        if (this.loaded)  {
            this.itemMoneyDataAdapter.add(moneyData);
            updateUIList();
        }
        else this.onFragmentLoaded = () -> {
            add(moneyData);
        };
    }
    public void addList(List<MoneyData> list, boolean isClear) {
        if (this.loaded) {
            this.itemMoneyDataAdapter.addMulti(list, isClear);
            updateUIList();
        }
        else this.onFragmentLoaded = () -> {
            addList(list, isClear);
        };
    }

    @Override
    protected void initView() {
        this.itemMoneyDataAdapter = new ItemMoneyDataAdapter(this.getContext());
        this.binding.rcvMoneyData.setAdapter(itemMoneyDataAdapter);
        this.itemMoneyDataAdapter.addMulti(this.list, true);
    }

    private void updateUIList() {
        if (this.itemMoneyDataAdapter.getItemCount() > 0) {
            this.binding.emptyView.setVisibility(View.GONE);
            this.binding.rcvMoneyData.setVisibility(View.VISIBLE);
        }
        else {
            this.binding.rcvMoneyData.setVisibility(View.GONE);
            this.binding.emptyView.setVisibility(View.VISIBLE);

        }
    }
    @Override
    protected void initEvent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_money_data;
    }
}
