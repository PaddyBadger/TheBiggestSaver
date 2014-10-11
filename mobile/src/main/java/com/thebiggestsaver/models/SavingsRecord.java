package com.thebiggestsaver.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by patriciaestridge on 9/1/14.
 */
@DatabaseTable
public class SavingsRecord {
    private SavingsType savingsType;
    private SavingsData[] savingsData;
    @DatabaseField
    private String title;
    @DatabaseField
    private String jsonOfTodaysSavings;
    @DatabaseField
    private String amount;
    @DatabaseField
    private String frequency;
    @DatabaseField(id = true)
    private String id;
    @DatabaseField
    private String savingsTypeId;
    @DatabaseField
    private Integer multiplier;


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSavingsTypeId() {
        return savingsTypeId;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getTitle() {
        return title;
    }

    public void setSavingsTypeId(String savingsTypeId) {
        this.savingsTypeId = savingsTypeId;
    }


    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SavingsType getSavingsType() {
        return savingsType;
    }

    public void setSavingsType(SavingsType savingsType) {
        this.savingsType = savingsType;
    }

    public Integer getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Integer multiplier) {
        this.multiplier = multiplier;
    }

    public String getJsonOfTodaysSavings() {
        return jsonOfTodaysSavings;
    }

    public void setJsonOfTodaysSavings(String jsonOfTodaysSavings) {
        this.jsonOfTodaysSavings = jsonOfTodaysSavings;
    }


    public SavingsRecord makeDatabaseSavingsRecord(){
        SavingsRecord databaseSavingsRecord = new SavingsRecord();
        databaseSavingsRecord.id = id;
        databaseSavingsRecord.savingsTypeId = savingsTypeId;
        databaseSavingsRecord.jsonOfTodaysSavings = jsonOfTodaysSavings;
        databaseSavingsRecord.amount = amount;
        databaseSavingsRecord.multiplier = multiplier;
        databaseSavingsRecord.frequency = frequency;
        databaseSavingsRecord.title = title;

        return databaseSavingsRecord;
    }
}
