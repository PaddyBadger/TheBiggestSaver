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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patriciaestridge on 9/29/14.
 */
public class DimeUi
{

    public static List<StateListDrawable> buildDrawableStateList(Context context, List<Integer> iconDrawableResource, int color)
    {
        List<StateListDrawable> iconsForSavingsRecord = new ArrayList<StateListDrawable>();
        for (Integer icon : iconDrawableResource)
        {
            StateListDrawable stateListDrawable = new StateListDrawable();

            stateListDrawable.addState(new int[]{android.R.attr.state_pressed},
                    buildLayerDrawable(icon, color, Paint.Style.FILL, context));

            stateListDrawable.addState(new int[]{},
                    buildLayerDrawable(icon, color, Paint.Style.STROKE, context));

            iconsForSavingsRecord.add(stateListDrawable);
        }
        return iconsForSavingsRecord;

    }

    public static LayerDrawable buildLayerDrawable(int iconDrawable, int color, Paint.Style style, Context context)
    {
        Drawable imageDrawable;
        ShapeDrawable imageShape;
        int width = 0;
        int height = 0;

        if (style == Paint.Style.STROKE)
        {
            BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(iconDrawable);
            width = drawable.getIntrinsicWidth();
            height = drawable.getIntrinsicHeight();

            imageDrawable = tintBitmap(drawable, color, context);
        } else
        {
            imageDrawable = context.getResources().getDrawable(iconDrawable);
        }

        imageShape = buildShapeDrawable(color, style, width, height);

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

    public static ShapeDrawable buildShapeDrawable(int color, Paint.Style style, int width, int height)
    {
        int circleWidth = width *3;

        ShapeDrawable circle= new ShapeDrawable( new OvalShape());
        circle.setIntrinsicHeight(circleWidth);
        circle.setIntrinsicWidth( circleWidth);

        circle.getPaint().setColor(color);
        circle.getPaint().setStyle(style);
        circle.getPaint().setStrokeWidth(8.0f);
        circle.getPaint().setShadowLayer(10.0f, 10.0f, 10.0f, 0xFF000000);

        return circle;
    }
}
