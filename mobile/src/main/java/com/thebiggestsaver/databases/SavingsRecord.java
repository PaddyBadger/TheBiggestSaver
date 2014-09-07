package com.thebiggestsaver.databases;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;
import java.util.Map;

/**
 * Created by patriciaestridge on 9/6/14.
 */
@DatabaseTable
public class SavingsRecord {

    @DatabaseField(id = true)
    public String id;
    @DatabaseField
    public String savingsTypeId;
    @DatabaseField
    public Map<Date, Integer> todaysSavings;
    @DatabaseField
    public float amountSavedEachTime;
    @DatabaseField
    public String dailyWeelklyMonthly;
    @DatabaseField
    public Integer expectedFrequency;
    @DatabaseField
    public String title;
}
