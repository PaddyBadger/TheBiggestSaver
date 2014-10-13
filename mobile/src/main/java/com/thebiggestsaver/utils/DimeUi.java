package com.thebiggestsaver.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

import com.thebiggestsaver.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by patriciaestridge on 9/29/14.
 */
public class DimeUi
{
    public enum FillOrNot {
        fill, notfill
    }

    public enum  IconOrBack {
        icon, back
    }

    public List<StateListDrawable> buildDrawableStateList(Context context, List<Integer> iconDrawableResource, int color, IconOrBack iconOrBack)
    {
        List<StateListDrawable> iconsForSavingsRecord = new ArrayList<StateListDrawable>();
        for (Integer icon : iconDrawableResource)
        {
            StateListDrawable stateListDrawable = new StateListDrawable();

            stateListDrawable.addState(new int[]{android.R.attr.state_pressed},
                    buildDrawable(icon, color, FillOrNot.fill, context, iconOrBack));

            stateListDrawable.addState(new int[]{},
                    buildDrawable(icon, color, FillOrNot.notfill, context, iconOrBack));

            iconsForSavingsRecord.add(stateListDrawable);
        }
        return iconsForSavingsRecord;
    }

    public List<StateListDrawable> buildCheckableIconList(Context context, List<Integer> icons, int color)
    {
        List<StateListDrawable> iconsForRecording = new ArrayList<StateListDrawable>();
        for (Integer icon : icons)
        {
            StateListDrawable drawableStates = new StateListDrawable();
            drawableStates.addState(new int[]{android.R.attr.state_checked},
                    buildDrawable(icon, color, FillOrNot.notfill, context, IconOrBack.icon));

            drawableStates.addState(new int[]{},
                    buildDrawable(icon, color, FillOrNot.notfill, context, IconOrBack.icon));
            iconsForRecording.add(drawableStates);
        }
        return iconsForRecording;
    }

    public Drawable buildDrawable(int iconDrawable, int color, FillOrNot fillOrNot, Context context, IconOrBack iconOrBack)
    {
        Drawable imageDrawable;

        if (iconOrBack == IconOrBack.icon)
        {
            if (fillOrNot == FillOrNot.notfill)
            {
                BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(iconDrawable);
                imageDrawable = tintBitmap(drawable, color, context);

            } else
            {
                imageDrawable = context.getResources().getDrawable(iconDrawable);
            }
        } else {
            if (fillOrNot == FillOrNot.notfill)
            {
                imageDrawable = context.getResources().getDrawable(R.drawable.backing_circle);

            } else {
                BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(R.drawable.backing_circle);
                imageDrawable = tintBitmap(drawable, color, context);
            }
        }
        return imageDrawable;
    }

    public BitmapDrawable tintBitmap(BitmapDrawable drawableToTint, int color, Context context)
    {
        Bitmap oldBitMap = drawableToTint.getBitmap();

        Paint newPaint = new Paint();

        newPaint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));

        Bitmap newTintedBitmap = Bitmap.createBitmap(oldBitMap.getWidth(), oldBitMap.getHeight(), Bitmap.Config.ARGB_8888);

        //cache the bitmap
        Canvas canvas = new Canvas(newTintedBitmap);
        canvas.drawBitmap(oldBitMap, 0, 0, newPaint);

        return new BitmapDrawable(context.getResources(), newTintedBitmap);
    }

    public int createColorBackground(String colorString)
    {
        String[] colorStringArray = colorString.split("");
        StringBuilder fiftyOpacity = new StringBuilder();
        for (int j = 0; j < colorStringArray.length; j++)
        {
            if (j == 2)
            {
                colorStringArray[j] = "B";
            }
            if (j == 3)
            {
                colorStringArray[j] = "3";
            }
            if (j > 0)
            {
                fiftyOpacity.append(colorStringArray[j]);
            }
        }
        colorString = fiftyOpacity.toString();
        int newColor = Color.parseColor(colorString);
        return newColor;
    }

    public String randomColor(Context context)
    {
        String[] stringArray = context.getResources().getStringArray(R.array.color_array);
        int idx = new Random().nextInt(stringArray.length);
        String colorString = stringArray[idx];
        return colorString;
    }
}
