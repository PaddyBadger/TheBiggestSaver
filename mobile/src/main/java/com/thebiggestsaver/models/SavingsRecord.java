package com.thebiggestsaver.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Map;

/**
 * Created by patriciaestridge on 9/1/14.
 */
public class SavingsRecord {

    private SavingsType savingsType;
    private String id;
    private Map<Date, Integer> todaysSavings;
    private float amountSavedEachTime;
    private String dailyWeelklyMonthly;
    private Integer expectedFrequency;
    private String title;

    public SavingsType getSavingsType() {
        return savingsType;
    }

    public void setSavingsType(SavingsType savingsType) {
        this.savingsType = savingsType;
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
}
