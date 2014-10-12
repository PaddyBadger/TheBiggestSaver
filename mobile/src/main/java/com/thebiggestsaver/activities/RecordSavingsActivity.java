package com.thebiggestsaver.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.thebiggestsaver.R;
import com.thebiggestsaver.actionbars.BackBar;
import com.thebiggestsaver.adapters.SavingRecorderAdapter;
import com.thebiggestsaver.helpers.DatabaseReader;
import com.thebiggestsaver.models.SavingsRecord;
import com.thebiggestsaver.utils.DimeUi;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by patriciaestridge on 10/11/14.
 */
public class RecordSavingsActivity extends FragmentActivity
{
    public List<SavingsRecord> savingsRecords = new ArrayList<SavingsRecord>();
    private Context context;
    private RecyclerView recyclerView;
    private SavingRecorderAdapter savingRecordingAdapter;
    @InjectView(R.id.savings_pager)
    ViewPager savingsPager;

    public static void getLaunchIntent(Context context)
    {
        Intent i = new Intent(context, RecordSavingsActivity.class);
        context.startActivity(i);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_phone);

        ButterKnife.inject(this);
        context = this;
        savingsPager.setVisibility(View.GONE);

        createRecyclerView();
        renderContent();

        new BackBar(this, "My Savings");
    }

    private void createRecyclerView()
    {
        recyclerView = (RecyclerView) findViewById(R.id.signup_list);
        DimeUi uiHelper = new DimeUi();
        savingRecordingAdapter = new SavingRecorderAdapter(this, savingsRecords, R.layout.savings_recorder_view, uiHelper);
        recyclerView.setAdapter(savingRecordingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
    }

    public void renderContent()
    {
        DatabaseReader reader = new DatabaseReader();
        savingsRecords = reader.checkForExistingRecords(context);
        savingRecordingAdapter.dataSetChanged(savingsRecords);
    }
}
