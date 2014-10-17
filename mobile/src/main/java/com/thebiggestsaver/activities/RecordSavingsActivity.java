package com.thebiggestsaver.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.thebiggestsaver.R;
import com.thebiggestsaver.actionbars.BackBar;
import com.thebiggestsaver.adapters.SavingRecorderAdapter;
import com.thebiggestsaver.helpers.DatabaseHelper;
import com.thebiggestsaver.helpers.DatabaseReader;
import com.thebiggestsaver.models.SavingsData;
import com.thebiggestsaver.models.SavingsRecord;
import com.thebiggestsaver.utils.DimeUi;
import java.sql.SQLException;
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
    @InjectView(R.id.amount_saved)
    TextView amountSaved;
    @InjectView(R.id.days)
    TextView days;
    @InjectView(R.id.item_number)
    TextView itemNumber;

    public static void getLaunchIntent(Context context)
    {
        Intent i = new Intent(context, RecordSavingsActivity.class);
        context.startActivity(i);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving);

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

    private void renderContent()
    {
        DatabaseReader reader = new DatabaseReader();
        savingsRecords = reader.checkForExistingRecords(context);
        savingRecordingAdapter.dataSetChanged(savingsRecords);
        calculateSavings(savingsRecords);
    }

    private void calculateSavings(List<SavingsRecord> savingsRecords)
    {
        int intItemNumber = 0;
        int intDays = 0;
        double intAmountSaved = 0;

        for (SavingsRecord savingsRecord : savingsRecords)
        {
            if (savingsRecord.getSavingsData()!=null)
            {
                List<SavingsData> sd = savingsRecord.getSavingsData();
                if (sd.size()>intDays)
                {
                    intDays = sd.size();
                }
                for (SavingsData savingsData : sd)
                {
                    intItemNumber += savingsData.getAmount();
                }
            }
            String toParseToDouble = savingsRecord.getAmount().replace("$", "");
            intAmountSaved += intItemNumber * Double.parseDouble(toParseToDouble);
        }

        days.setText(intDays + " Days");
        amountSaved.setText("$" + intAmountSaved);
        itemNumber.setText(intItemNumber + " Savings");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.saving, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {

            List<SavingsRecord> savingsList = savingRecordingAdapter.getSavingsList();
            for (SavingsRecord savingsRecord : savingsList)
            {
                Gson gson = new Gson();
                String savingsData = gson.toJson(savingsRecord.getSavingsData());
                savingsRecord.setJsonOfTodaysSavings(savingsData);
            }

            try
            {
                Dao<SavingsRecord, ?> dao = DatabaseHelper.getInstance(context).getDao(SavingsRecord.class);
                if (savingsList != null)
                {
                    for (SavingsRecord savingsRecord : savingsList)
                    {
                        dao.createOrUpdate(savingsRecord.makeDatabaseSavingsRecord());
                    }
                }
            } catch (SQLException e)
            {
                e.printStackTrace();
                Toast.makeText(context, "There seems to be a bug, please fix me", Toast.LENGTH_SHORT).show();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
