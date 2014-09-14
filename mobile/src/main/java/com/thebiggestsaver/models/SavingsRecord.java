package com.thebiggestsaver.models;

import android.content.ContentValues;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONObject;

import java.util.Date;
import java.util.Map;

/**
 * Created by patriciaestridge on 9/1/14.
 */
@DatabaseTable
public class SavingsRecord {

    private SavingsType savingsType;

    @DatabaseField
    private String savingsTypeId;
    @DatabaseField(id = true)
    private String id;
    private Map<Date, Integer> todaysSavings;
    @DatabaseField
    private float amountSavedEachTime;
    @DatabaseField
    private String dailyWeelklyMonthly;
    @DatabaseField
    private Integer expectedFrequency;
    @DatabaseField
    private String title;
    @DatabaseField
    private String jsonOfTodaysSavings;

    public String getJsonOfTodaysSavings() {
        return jsonOfTodaysSavings;
    }

    public void setJsonOfTodaysSavings(String jsonOfTodaysSavings) {
        jsonOfTodaysSavings = new JSONObject(todaysSavings).toString();
        this.jsonOfTodaysSavings = jsonOfTodaysSavings;
    }

    public SavingsType getSavingsType() {
        return savingsType;
    }

    public void setSavingsType(SavingsType savingsType) {
        this.savingsType = savingsType;
    }

    public String getSavingsTypeId() {
        return savingsTypeId;
    }

    public void setSavingsTypeId(String savingsTypeString) {
        this.savingsTypeId = savingsTypeString;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<Date, Integer> getTodaysSavings() {
        return todaysSavings;
    }

    public void setTodaysSavings(Map<Date, Integer> todaysSavings) {
        this.todaysSavings = todaysSavings;
    }

    public float getAmountSavedEachTime() {
        return amountSavedEachTime;
    }

    public void setAmountSavedEachTime(float amountSavedEachTime) {
        this.amountSavedEachTime = amountSavedEachTime;
    }

    public String getDailyWeelklyMonthly() {
        return dailyWeelklyMonthly;
    }

    public void setDailyWeelklyMonthly(String dailyWeelklyMonthly) {
        this.dailyWeelklyMonthly = dailyWeelklyMonthly;
    }

    public Integer getExpectedFrequency() {
        return expectedFrequency;
    }

    public void setExpectedFrequency(Integer expectedFrequency) {
        this.expectedFrequency = expectedFrequency;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SavingsRecord makeDatabaseSavingsRecord(){
        SavingsRecord databaseSavingsRecord = new SavingsRecord();
        databaseSavingsRecord.id = id;
        databaseSavingsRecord.savingsTypeId = savingsTypeId;
        databaseSavingsRecord.jsonOfTodaysSavings = jsonOfTodaysSavings;
        databaseSavingsRecord.amountSavedEachTime = amountSavedEachTime;
        databaseSavingsRecord.expectedFrequency = expectedFrequency;
        databaseSavingsRecord.dailyWeelklyMonthly = dailyWeelklyMonthly;
        databaseSavingsRecord.title = title;

        return databaseSavingsRecord;
    }
}
