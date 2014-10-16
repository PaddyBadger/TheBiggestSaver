package com.thebiggestsaver.helpers;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.dao.Dao;
import com.thebiggestsaver.models.SavingsData;
import com.thebiggestsaver.models.SavingsRecord;
import com.thebiggestsaver.models.SavingsType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by patriciaestridge on 10/11/14.
 */
public class DatabaseReader
{
    private List<SavingsRecord> savingsRecords = new ArrayList<SavingsRecord>();

    public List<SavingsRecord> checkForExistingRecords(Context context)
    {
        try {
            Dao<SavingsRecord, ?> dao = DatabaseHelper.getInstance(context).getDao(SavingsRecord.class);
            savingsRecords = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        SavingsTypeHelper savingsTypeHelper = new SavingsTypeHelper();
        List<SavingsType> savingsTypeToAppend = savingsTypeHelper.savingsTypeHelperBuildList(context);

        for (SavingsRecord storedSavingsRecord : savingsRecords)
        {
            for (SavingsType savingsType : savingsTypeToAppend)
            {
                if (storedSavingsRecord.getSavingsTypeId().equals(savingsType.getId()))
                {
                    storedSavingsRecord.setSavingsType(savingsType);
                }
            }

            Gson gson = new Gson();
            Type type = new TypeToken<List<SavingsData>>(){}.getType();
            List<SavingsData> data = gson.fromJson(storedSavingsRecord.getJsonOfTodaysSavings(), type);
            storedSavingsRecord.setSavingsData(data);
        }
        return savingsRecords;
    }
}
