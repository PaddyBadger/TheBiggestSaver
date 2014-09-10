package com.thebiggestsaver.activities;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.thebiggestsaver.R;
import com.thebiggestsaver.actionbars.BackBar;
import com.thebiggestsaver.adapters.SavingListAdapter;
import com.thebiggestsaver.fragments.SavingTypeFragment;
import com.thebiggestsaver.models.SavingsRecord;
import com.thebiggestsaver.models.SavingsType;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class SavingActivity extends FragmentActivity {

    private final int NUM_CARDS = 32;
    public SavingListAdapter savingListAdapter;
    public RecyclerView recyclerView;
    private static String ARG_NUMBER = "position";
    private static String SAVINGS_RECORDS = "savings_reocords";
    public ArrayList<SavingsType> savingsList = new ArrayList<SavingsType>();
    ViewPager savingViewPager;
    public List<SavingsType> savings;
    public ArrayList<SavingsRecord> savingsRecords = new ArrayList<SavingsRecord>();

    public static void getLaunchIntent(Context context)
    {
        Intent i = new Intent(context, SavingActivity.class);
        context.startActivity(i);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_phone);
        ButterKnife.inject(this);

        recyclerView = (RecyclerView) findViewById(R.id.signup_list);
        savings = new ArrayList<SavingsType>();

        new BackBar(this, "Where Can You Save?");

        savingListAdapter = new SavingListAdapter(this, savingsRecords, R.layout.savings_recycler_view);
        recyclerView.setAdapter(savingListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        setTopPager();
    }

    public void setTopPager()
    {
        SavingPagerAdapter savingPagerAdapter = new SavingPagerAdapter(getSupportFragmentManager());
        savingViewPager = (ViewPager) findViewById(R.id.savings_pager);
        savingViewPager.setAdapter(savingPagerAdapter);
    }

    public class SavingPagerAdapter extends FragmentStatePagerAdapter
    {
        public SavingPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        public Fragment getItem(int position)
        {
            Fragment fragment = new SavingTypeFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_NUMBER, position);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount()
        {
            return NUM_CARDS;
        }
    }
}
