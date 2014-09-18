package com.thebiggestsaver.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by patriciaestridge on 9/4/14.
 */
public class MySQLiteHelper extends SQLiteOpenHelper
{
    public static final String TABLE_SAVINGS_RECORDS = "savingsRecords";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2)
    {

    }
}
