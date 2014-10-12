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

            savingType.setIconUrl("https://raw.githubusercontent.com/PaddyBadger/TheBiggestSaver/master/mobile/src/main/res/drawable-xhdpi/" + iconString + ".png");

            int colorInt = context.getResources().getIdentifier(iconString, "color", context.getPackageName());
            String colorString = context.getResources().getString(colorInt);
            savingType.setColor(colorString);

            savingsTypeList.add(savingType);
        }
        return savingsTypeList;
    }
}
