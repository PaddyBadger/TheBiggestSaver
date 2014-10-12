package com.thebiggestsaver.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thebiggestsaver.R;
import com.thebiggestsaver.models.SavingsData;
import com.thebiggestsaver.models.SavingsRecord;
import com.thebiggestsaver.utils.DimeUi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patriciaestridge on 10/11/14.
 */
public class SavingRecorderAdapter extends RecyclerView.Adapter<SavingRecorderAdapter.ViewHolder>
{

    private Context context;
    private List<SavingsRecord> savings;
    private int itemLayout;
    private DimeUi uiHelper;
    private List<StateListDrawable> iconsForSavingsRecord = new ArrayList<StateListDrawable>();
    private List<StateListDrawable> backDropForSavingsRecord = new ArrayList<StateListDrawable>();

    public SavingRecorderAdapter(Context context, List<SavingsRecord> savings, int itemLayout, DimeUi uiHelper)
    {
        super();
        this.context = context;
        this.savings = savings;
        this.itemLayout = itemLayout;
        this.uiHelper = uiHelper;
    }

    @Override
    public SavingRecorderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SavingRecorderAdapter.ViewHolder viewHolder, int position)
    {
        final SavingsRecord item = savings.get(position);

        String colorString = item.getSavingsType().getColor();
        int colorForIcons = Color.parseColor(colorString);

        createNextandBackViews(viewHolder, item, colorForIcons);
        //Set 3 clickable icons, or if there are more than 3, more. Consider how far back in time someone can alter data?
        //Also, consider the impact that the user's suggested frequency will have for this.
        createInitialSavingsIcons(viewHolder, item, colorForIcons);
    }

    public void createNextandBackViews(ViewHolder viewHolder, SavingsRecord item, int colorForIcons)
    {
        List<Integer> iconArray = new ArrayList<Integer>();
        iconArray.add(R.drawable.back);
        iconArray.add(R.drawable.next);

        iconsForSavingsRecord = uiHelper.buildDrawableStateList(context, iconArray, colorForIcons, DimeUi.IconOrBack.icon);

        List<Integer> backArray = new ArrayList<Integer>();
        backArray.add(R.drawable.backing_circle);
        backArray.add(R.drawable.backing_circle);

        backDropForSavingsRecord = uiHelper.buildDrawableStateList(context, iconArray, colorForIcons, DimeUi.IconOrBack.back);

        viewHolder.nextIcon.setImageDrawable(iconsForSavingsRecord.get(1));
        viewHolder.nextIconBack.setImageDrawable(backDropForSavingsRecord.get(1));
        viewHolder.backIcon.setImageDrawable(iconsForSavingsRecord.get(0));
        viewHolder.backBack.setImageDrawable(backDropForSavingsRecord.get(0));

        onNextClicked(viewHolder);
    }

    private void onNextClicked(final ViewHolder viewHolder)
    {
        viewHolder.nextContainer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                view.setVisibility(View.GONE);

                viewHolder.summaryTextOnIcons.setVisibility(View.GONE);
                viewHolder.savingsView.setVisibility(View.VISIBLE);

                TranslateAnimation anim = new TranslateAnimation(1000, 0, 0, 0);
                anim.setDuration(500);
                anim.setInterpolator(new LinearInterpolator());
                anim.setRepeatCount(0);
                anim.setFillAfter(false);
                viewHolder.savingsView.startAnimation(anim);
                viewHolder.backContainer.setVisibility(View.VISIBLE);
                onBackClicked(viewHolder);
            }
        });
    }

    private void onBackClicked(final ViewHolder viewHolder)
    {
        viewHolder.backContainer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View view)
            {

                TranslateAnimation anim = new TranslateAnimation(0, 1200, 0, 0);
                anim.setDuration(1000);
                anim.setInterpolator(new LinearInterpolator());
                anim.setRepeatCount(0);
                anim.setFillAfter(false);

                viewHolder.savingsView.startAnimation(anim);
                viewHolder.summaryTextOnIcons.setVisibility(View.VISIBLE);
                viewHolder.nextContainer.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        viewHolder.savingsView.setVisibility(View.GONE);
                        viewHolder.backContainer.setVisibility(View.GONE);
                        onNextClicked(viewHolder);
                    }
                }, 1000);
            }
        });
    }

    public void createInitialSavingsIcons(ViewHolder viewHolder, SavingsRecord item, int colorForIcons)
    {
        viewHolder.iconHolder.removeAllViews();
        View rowOfThree = LayoutInflater.from(context).inflate(R.layout.icon_group, viewHolder.iconHolder, false);
        viewHolder.iconHolder.addView(rowOfThree);

        String iconString = item.getSavingsTypeId();
        int iconId = context.getResources().getIdentifier(iconString, "drawable", context.getPackageName());

        List<Integer> iconsForState = new ArrayList<Integer>();
        iconsForState.add(iconId);
        iconsForState.add(iconId);
        iconsForState.add(iconId);

        List<Boolean> checkedOrNot = new ArrayList<Boolean>();

        if (item.getSavingsData() != null)
        {
            SavingsData[] savingsData = item.getSavingsData();

        }
        else if (item.getMultiplier()!=null)
        {
            int numberOfSavingsRecorded = item.getMultiplier();
            for (int i = 0; i < numberOfSavingsRecorded; i++)
            {
                checkedOrNot.add(false);
            }
        }
        else
        {

        }


        final Drawable drawable = context.getResources().getDrawable(iconId);
        ColorStateList statePrePressed = new ColorStateList(
                new int[][] {new int[] {},},
                             new int[] {context.getResources().getColor(R.color.grey_text)});
        final ColorStateList postPressed = new ColorStateList(
                new int[][] {new int[] {},},
                new int[] {colorForIcons});

        drawable.setTint(statePrePressed, PorterDuff.Mode.MULTIPLY);

        List<ImageView> iconImageViews = new ArrayList<ImageView>();
        iconImageViews.add((ImageView) rowOfThree.findViewById(R.id.saving_icon_one));
        iconImageViews.add((ImageView) rowOfThree.findViewById(R.id.saving_icon_two));
        iconImageViews.add((ImageView) rowOfThree.findViewById(R.id.saving_icon_three));

        for (ImageView iconImageView : iconImageViews)
        {
            iconImageView.setImageDrawable(drawable);
            iconImageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {

                    drawable.setTint(postPressed, PorterDuff.Mode.MULTIPLY);
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return savings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView summaryTextOnIcons;
        public TextView summaryTextOnData;
        public RelativeLayout savingsView;
        public View secondPageBackDrop;
        public RelativeLayout nextContainer;
        public ImageView nextIcon;
        public ImageView nextIconBack;
        public RelativeLayout backContainer;
        public ImageView backIcon;
        public ImageView backBack;
        public LinearLayout iconHolder;
        public TextView amountSaved;
        public TextView timesSaved;
        public TextView daysSaved;

        public ViewHolder(View itemView)
        {
            super(itemView);
            summaryTextOnIcons = (TextView) itemView.findViewById(R.id.saving_detail);
            summaryTextOnData = (TextView) itemView.findViewById(R.id.summary_title);
            iconHolder = (LinearLayout) itemView.findViewById(R.id.recorder_icon_holder_vertical);
            nextContainer = (RelativeLayout) itemView.findViewById(R.id.nextIconContainer);
            nextIcon = (ImageView) itemView.findViewById(R.id.next_icon);
            nextIconBack = (ImageView) itemView.findViewById(R.id.nextIconBackk);

            savingsView = (RelativeLayout) itemView.findViewById(R.id.slider_second_page);
            secondPageBackDrop = itemView.findViewById(R.id.slider_second_page_backdrop);

            amountSaved = (TextView) itemView.findViewById(R.id.amount_saved);
            timesSaved = (TextView) itemView.findViewById(R.id.times_saved);
            daysSaved = (TextView) itemView.findViewById(R.id.over_time_period);

            backContainer = (RelativeLayout) itemView.findViewById(R.id.backIconContainer);
            backIcon = (ImageView) itemView.findViewById(R.id.back_icon);
            backBack = (ImageView) itemView.findViewById(R.id.back_iconIconBack);
        }
    }

    public void dataSetChanged(List<SavingsRecord> list)
    {
        this.savings = list;
        notifyDataSetChanged();
    }
}
