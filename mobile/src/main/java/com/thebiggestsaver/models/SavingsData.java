package com.thebiggestsaver.models;

/**
 * Created by patriciaestridge on 10/11/14.
 */
public class SavingsData
{
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

    // Date in millis
    private long date;
    private int amount;

}
