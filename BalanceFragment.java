package com.valeriipopov.wallety.mainActivityPack;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valeriipopov.wallety.Item;
import com.valeriipopov.wallety.R;
import com.valeriipopov.wallety.data.DataBaseDbHelper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;



public class BalanceFragment extends Fragment {

    private TextView mSumExpence;
    private TextView mSumIncome;
    private TextView mBalance;
    private DataBaseDbHelper mDataBaseDbHelper;
    private int mTotalExpense;
    private int mTotalIncome;
    private int mBalanceValue;
    private SwipeRefreshLayout mRefreshLayout;
    private List <Integer> mColorsList = new ArrayList<>();

    private ArrayList<PieEntry> mPieChartValues;
    private PieDataSet mPieChartdataSet;
    private PieData mPieChartdata;
    private PieChart mPieChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDataBaseDbHelper = new DataBaseDbHelper(getContext());
        mColorsList.add(getResources().getColor(R.color.colorExpense));
        mColorsList.add(getResources().getColor(R.color.colorIncome));

        mBalance = view.findViewById(R.id.sum_balance);
        mSumExpence = view.findViewById(R.id.sum_expense);
        mSumIncome = view.findViewById(R.id.sum_income);

        mRefreshLayout = view.findViewById(R.id.refresh);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadItemsBalance();
                mRefreshLayout.setRefreshing(false);
            }
        });


        Legend legend = mPieChart.getLegend();
        legend.setEnabled(false);
        loadItemsBalance();
    }

    private void loadItemsBalance(){

        mTotalExpense = mDataBaseDbHelper.loadTotalValuesForBalance(Item.TYPE_EXPENSE);
        mTotalIncome = mDataBaseDbHelper.loadTotalValuesForBalance(Item.TYPE_INCOME);
        mBalanceValue = mTotalIncome - mTotalExpense;
        mBalance.setText(NumberFormat.getCurrencyInstance().format(mBalanceValue));
        mSumExpence.setText(NumberFormat.getCurrencyInstance().format(mTotalExpense));
        mSumIncome.setText(NumberFormat.getCurrencyInstance().format(mTotalIncome));
        if (mTotalIncome + mTotalExpense != 0){
            mPieChartValues.clear();
            mPieChartValues.add(new PieEntry((float) (mTotalExpense/ 100), getResources().getText(R.string.tab_expense).toString() + ", %"));
            mPieChartValues.add(new PieEntry((float) (mTotalIncome/ 100), getResources().getText(R.string.tab_income).toString() + ", %"));
            mPieChart.setData(mPieChartdata);
        }
    }
}
