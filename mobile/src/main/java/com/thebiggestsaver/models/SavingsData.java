package com.thebiggestsaver.models;

import java.util.List;

/**
 * Created by patriciaestridge on 10/11/14.
 */
public class SavingsData
{
    // Date in millis
    private long date;
    private int amount;

    public List<DisplayData> displayData;

    public long getDate()
    {
        return date;
    }

    public void setDate(long date)
    {
        this.date = date;
    }

    public int getAmount()
    {
        return amount;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public List<DisplayData> getDisplayData()
    {
        return displayData;
    }

    public void setDisplayData(List<DisplayData> displayData)
    {
        this.displayData = displayData;
    }
}
