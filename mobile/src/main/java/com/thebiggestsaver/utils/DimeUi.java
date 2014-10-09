package com.thebiggestsaver.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;

import com.thebiggestsaver.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patriciaestridge on 9/29/14.
 */
public class DimeUi
{

    public enum FillOrNot {
        fill, notfill
    }
    public static List<StateListDrawable> buildDrawableStateList(Context context, List<Integer> iconDrawableResource, int color)
    {
        List<StateListDrawable> iconsForSavingsRecord = new ArrayList<StateListDrawable>();
        for (Integer icon : iconDrawableResource)
        {
            StateListDrawable stateListDrawable = new StateListDrawable();

            stateListDrawable.addState(new int[]{android.R.attr.state_pressed},
                    buildLayerDrawable(icon, color, FillOrNot.fill, context));

            stateListDrawable.addState(new int[]{},
                    buildLayerDrawable(icon, color, FillOrNot.notfill, context));

            iconsForSavingsRecord.add(stateListDrawable);
        }
        return iconsForSavingsRecord;

    }

    public static LayerDrawable buildLayerDrawable(int iconDrawable, int color, FillOrNot fillOrNot, Context context)
    {
        Drawable imageDrawable;
        Drawable imageShape;
        int width = 0;

        if (fillOrNot == FillOrNot.notfill)
        {
            BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(iconDrawable);
            width = drawable.getIntrinsicWidth()/2;

            imageDrawable = tintBitmap(drawable, color, context);

        } else
        {
            imageDrawable = context.getResources().getDrawable(iconDrawable);
        }

        imageDrawable.setBounds(width,width,width,width);

        if (fillOrNot == FillOrNot.notfill)
        {
            imageShape = context.getResources().getDrawable(R.drawable.backing_circle);
        } else {
            BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(R.drawable.backing_circle);
            imageShape = tintBitmap(drawable, color, context);
        }

        LayerDrawable imageLayout = new LayerDrawable(new Drawable[]
                {
                        imageShape, imageDrawable
                });

        return imageLayout;
    }

    public static BitmapDrawable tintBitmap(BitmapDrawable drawableToTint, int color, Context context)
    {
        Bitmap oldBitMap = ((BitmapDrawable) drawableToTint).getBitmap();

        Paint newPaint = new Paint();

        newPaint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));

        Bitmap newTintedBitmap = Bitmap.createBitmap(oldBitMap.getWidth(), oldBitMap.getHeight(), Bitmap.Config.ARGB_8888);

        //cache the bitmap
        Canvas canvas = new Canvas(newTintedBitmap);
        canvas.drawBitmap(oldBitMap, 0, 0, newPaint);

        return new BitmapDrawable(context.getResources(), newTintedBitmap);
    }

    public static ShapeDrawable buildShapeDrawable(int color, Paint.Style style, float widtht)
    {
        int circleWidth =100;

        ShapeDrawable circle= new ShapeDrawable( new OvalShape());
        circle.setIntrinsicHeight(circleWidth);
        circle.setIntrinsicWidth( circleWidth);

        circle.getPaint().setColor(color);
        circle.getPaint().setStyle(style);
        circle.getPaint().setStrokeWidth(7.0f);
        circle.getPaint().setShadowLayer(100.0f, 100.0f, 100.0f, 0xFF000000);

        return circle;
    }
}
