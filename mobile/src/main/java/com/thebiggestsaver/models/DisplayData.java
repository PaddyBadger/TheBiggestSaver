package com.thebiggestsaver.models;

/**
 * Created by patriciaestridge on 10/13/14.
 */

public class DisplayData
{
    private String color;
    private boolean isChecked;

    public boolean isChecked()
    {
        return isChecked;
    }

    public void setChecked(boolean isChecked)
    {
        this.isChecked = isChecked;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }
}
