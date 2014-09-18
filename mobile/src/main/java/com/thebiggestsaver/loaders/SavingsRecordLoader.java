package com.thebiggestsaver.loaders;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.thebiggestsaver.helpers.DatabaseHelper;
import com.thebiggestsaver.helpers.SavingsTypeHelper;
import com.thebiggestsaver.models.SavingsRecord;
import com.thebiggestsaver.models.SavingsType;
import java.sql.SQLException;
import java.util.List;

public class SavingsRecordLoader extends BaseAsyncLoader<List<SavingsRecord>>
{
    private Context context;
    public List<SavingsRecord> savingsRecordList;

    public SavingsRecordLoader(Context context)
    {
        super(context);
    }

    @Override
    protected String getBroadcastString() {
        return null;
    }

    @Override
    public List<SavingsRecord> loadInBackground()
    {
        try {
            Dao<SavingsRecord, ?> dao = DatabaseHelper.getInstance(context).getDao(SavingsRecord.class);

            List<SavingsRecord> storedSavingsRecords = dao.queryForAll();

            SavingsTypeHelper savingsTypeHelper = new SavingsTypeHelper();
            List<SavingsType> savingsTypeToAppend = savingsTypeHelper.savingsTypeHelperBuildList(context);

            for (SavingsRecord storedSavingsRecord : storedSavingsRecords)
            {

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
