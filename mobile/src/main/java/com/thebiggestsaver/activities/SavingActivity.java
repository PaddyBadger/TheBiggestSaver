package com.thebiggestsaver.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.thebiggestsaver.R;
import com.thebiggestsaver.actionbars.BackBar;
import com.thebiggestsaver.adapters.SavingListAdapter;
import com.thebiggestsaver.fragments.SavingTypeFragment;
import com.thebiggestsaver.helpers.DatabaseHelper;
import com.thebiggestsaver.helpers.SavingsTypeHelper;
import com.thebiggestsaver.models.SavingsRecord;
import com.thebiggestsaver.models.SavingsType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class SavingActivity extends FragmentActivity {

    private final int NUM_CARDS = 32;
    public SavingListAdapter savingListAdapter;
    public RecyclerView recyclerView;
    private static String ARG_NUMBER = "position";
    private static String SAVINGS_RECORDS = "savings_records";
    public ArrayList<SavingsType> savingsList = new ArrayList<SavingsType>();
    ViewPager savingViewPager;
    public List<SavingsType> savings;
    public List<SavingsRecord> savingsRecords = new ArrayList<SavingsRecord>();
    private Context context;

    public static void getLaunchIntent(Context context) {
        Intent i = new Intent(context, SavingActivity.class);
        context.startActivity(i);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_phone);
        ButterKnife.inject(this);
        context = this;

        createRecyclerView();
        createTopPager();

        new BackBar(this, "Where Can You Save?");
    }

    private void createRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.signup_list);
        savings = new ArrayList<SavingsType>();
        savingListAdapter = new SavingListAdapter(this, savingsRecords, R.layout.savings_recycler_view);
        recyclerView.setAdapter(savingListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
    }

    public void createTopPager() {
            SavingPagerAdapter savingPagerAdapter = new SavingPagerAdapter(getSupportFragmentManager());
            savingViewPager = (ViewPager) findViewById(R.id.savings_pager);
            savingViewPager.setAdapter(savingPagerAdapter);
            checkForExistingRecords();
    }

    private void checkForExistingRecords()
    {
        try {
            Dao<SavingsRecord, ?> dao = DatabaseHelper.getInstance(context).getDao(SavingsRecord.class);
            savingsRecords = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        SavingsTypeHelper savingsTypeHelper = new SavingsTypeHelper();
        List<SavingsType> savingsTypeToAppend = savingsTypeHelper.savingsTypeHelperBuildList(context);

//        List<String> savingsGson = new ArrayList<String>();
//        for (SavingsType savingsType : savingsTypeToAppend) {
//            String typeGson = new Gson().toJson(savingsType);
//            savingsGson.add(typeGson);
//        }
//
//        savingsGson.toString();
//        Log.e("list", savingsGson.toString());

        for (SavingsRecord storedSavingsRecord : savingsRecords)
        {
            for (SavingsType savingsType : savingsTypeToAppend)
            {
                if (storedSavingsRecord.getSavingsTypeId().equals(savingsType.getId()))
                {
                    storedSavingsRecord.setSavingsType(savingsType);
                }
            }
        }

        savingListAdapter.dataSetChanged(savingsRecords);
    }


    public class SavingPagerAdapter extends FragmentStatePagerAdapter {
        public SavingPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            Fragment fragmentSavingsType = new SavingTypeFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_NUMBER, position);
            fragmentSavingsType.setArguments(args);
            return fragmentSavingsType;
        }

        @Override
        public int getCount() {
            return NUM_CARDS;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.saving, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            List<SavingsRecord> savingsList = savingListAdapter.getSavingsList();
            try
                {
                    Dao<SavingsRecord, ?> dao = DatabaseHelper.getInstance(context).getDao(SavingsRecord.class);
                    if(savingsList != null)
                    {
                        for (SavingsRecord savingsRecord: savingsList)
                        {
                            dao.createOrUpdate(savingsRecord.makeDatabaseSavingsRecord());
                        }
                    }
                }
                catch (SQLException e)
                {
                    try {
                        throw new SQLException(e);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    //TODO: Throw SQL Exception here
                }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
