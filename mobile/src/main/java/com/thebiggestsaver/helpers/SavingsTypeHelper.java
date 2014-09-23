package com.thebiggestsaver.helpers;

import android.content.Context;

import com.thebiggestsaver.R;
import com.thebiggestsaver.models.SavingsType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by patriciaestridge on 8/24/14.
 */
public class SavingsTypeHelper
{
    public ArrayList<SavingsType> savingsTypeList = new ArrayList<SavingsType>();
    private String[] savingsTypeArray;
    private String[] savingsTypeArrayShortTitles;

    public List<SavingsType> savingsTypeHelperBuildList(Context context)
    {
       savingsTypeArray = context.getResources().getStringArray(R.array.types_of_saving);
       savingsTypeArrayShortTitles = context.getResources().getStringArray(R.array.types_of_saving_short_titles);

        for (int position = 0; position < savingsTypeArray.length; position++) {
            SavingsType savingType = new SavingsType();
            savingType.setTitle(savingsTypeArray[position]);

            String iconString = savingsTypeArrayShortTitles[position];
            savingType.setId(savingsTypeArrayShortTitles[position]);

            int iconId = context.getResources().getIdentifier(iconString, "drawable", context.getPackageName());
            savingType.setIcon(context.getResources().getDrawable(iconId));

//            String addDrawable = savingsTypeArrayShortTitles[position]+"add";
//            int drawableId = context.getResources().getIdentifier(addDrawable, "drawable", context.getPackageName());
//            savingType.setAdd(context.getResources().getDrawable(drawableId));
//
//            String nextDrawable = savingsTypeArrayShortTitles[position]+"next";
//            int drawableNextId = context.getResources().getIdentifier(nextDrawable, "drawable", context.getPackageName());
//            savingType.setNext(context.getResources().getDrawable(drawableNextId));
//
//            String backDrawable = savingsTypeArrayShortTitles[position]+"back";
//            int drawableBackId = context.getResources().getIdentifier(backDrawable, "drawable", context.getPackageName());
//            savingType.setBack(context.getResources().getDrawable(drawableBackId));
//
//            String acceptDrawable = savingsTypeArrayShortTitles[position]+"accept_circle";
//            int drawableAcceptId = context.getResources().getIdentifier(acceptDrawable, "drawable", context.getPackageName());
//            savingType.setAccept(context.getResources().getDrawable(drawableAcceptId));
//
//            String deleteDrawable = savingsTypeArrayShortTitles[position]+"delete_circle";
//            int drawableDeleteId = context.getResources().getIdentifier(deleteDrawable, "drawable", context.getPackageName());
//            savingType.setDelete(context.getResources().getDrawable(drawableDeleteId));
//
//            String editDrawable = savingsTypeArrayShortTitles[position]+"edit";
//            int drawableEditId = context.getResources().getIdentifier(editDrawable, "drawable", context.getPackageName());
//            savingType.setEdit(context.getResources().getDrawable(drawableEditId));

            savingsTypeList.add(savingType);
        }
        return savingsTypeList;
    }
}
