package com.example.quanlychitieu.ui.fragments;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.base.BaseActivity;
import com.example.quanlychitieu.base.BaseFragment;
import com.example.quanlychitieu.databinding.FragmentListMoneyDataBinding;
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

    @Override
    protected void initView() {
        itemMoneyDataAdapter = new ItemMoneyDataAdapter(this.getContext());
        this.binding.rcvMoneyData.setAdapter(itemMoneyDataAdapter);
        this.itemMoneyDataAdapter.addMulti(this.list, true);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_money_data;
    }
}
