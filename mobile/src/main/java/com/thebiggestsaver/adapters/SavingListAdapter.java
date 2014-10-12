package com.thebiggestsaver.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.thebiggestsaver.R;
import com.thebiggestsaver.models.SavingsRecord;
import com.thebiggestsaver.utils.DimeUi;
import com.thebiggestsaver.utils.MyVolley;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

/**
 * Created by patriciaestridge on 8/21/14.
 */
public class SavingListAdapter extends RecyclerView.Adapter<SavingListAdapter.ViewHolder>
{
    public List<SavingsRecord> savings = new ArrayList<SavingsRecord>();
    private Context context;
    private int itemLayout;
    private List<StateListDrawable> iconsForSavingsRecord = new ArrayList<StateListDrawable>();
    private List<StateListDrawable> backDropForSavingsRecord = new ArrayList<StateListDrawable>();
    private DimeUi uiHelper;

    public SavingListAdapter(Context context, List<SavingsRecord> savings, int itemLayout, DimeUi uiHelper)
    {
        super();
        this.context = context;
        this.savings = savings;
        this.itemLayout = itemLayout;
        this.uiHelper = uiHelper;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position)
    {
        final SavingsRecord item = savings.get(position);

        resetInitialView(viewHolder);

        viewHolder.text.setText(item.getSavingsType().getTitle());
        viewHolder.image.setImageUrl(item.getSavingsType().getIconUrl(), MyVolley.getImageLoader());

        String colorString = item.getSavingsType().getColor();
        int colorForIcons = Color.parseColor(colorString);

        List<Integer> iconArray = new ArrayList<Integer>();
        iconArray.add(R.drawable.back);
        iconArray.add(R.drawable.delete);
        iconArray.add(R.drawable.next);
        iconArray.add(R.drawable.accept);
        iconArray.add(R.drawable.edit);

        iconsForSavingsRecord = uiHelper.buildDrawableStateList(context, iconArray, colorForIcons, DimeUi.IconOrBack.icon);

        List<Integer> backArray = new ArrayList<Integer>();
        backArray.add(R.drawable.backing_circle);
        backArray.add(R.drawable.backing_circle);
        backArray.add(R.drawable.backing_circle);
        backArray.add(R.drawable.backing_circle);

        backDropForSavingsRecord = uiHelper.buildDrawableStateList(context, iconArray, colorForIcons, DimeUi.IconOrBack.back);

        viewHolder.backIcon.setImageDrawable(iconsForSavingsRecord.get(0));
        viewHolder.acceptIcon.setImageDrawable(iconsForSavingsRecord.get(3));
        viewHolder.acceptIconBack.setImageDrawable(backDropForSavingsRecord.get(3));
        viewHolder.backIconBack.setImageDrawable(backDropForSavingsRecord.get(0));

        createNextIconView(viewHolder, position, item);
        createDeleteIconView(viewHolder, position, item);
        createColorBackground(viewHolder, item);
        createNumberPickers(viewHolder, item, position);
        backOnClick(viewHolder);
        createAcceptIconChange(viewHolder, item);
    }

    private void createAcceptIconChange(final ViewHolder viewHolder, final SavingsRecord item)
    {
        viewHolder.acceptIconContainer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                viewHolder.acceptIcon.setImageDrawable(iconsForSavingsRecord.get(4));
                viewHolder.frequency.setEnabled(false);
                viewHolder.frequencyString.setEnabled(false);
                viewHolder.amountSaved.setEnabled(false);
            }
        });
    }

    private void createDeleteIconView(final ViewHolder viewHolder, int i, final SavingsRecord item)
    {
        if (i == 0)
        {
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    viewHolder.deleteIconContainer.setVisibility(View.VISIBLE);
                    viewHolder.deleteIcon.setImageDrawable(iconsForSavingsRecord.get(1));
                    viewHolder.deleteIconBack.setImageDrawable(backDropForSavingsRecord.get(1));
                    AnimationSet rollingLeft = rollingLeftCall();
                    viewHolder.deleteIconContainer.startAnimation(rollingLeft);
                }
            }, 1000);
        } else
        {
            viewHolder.deleteIcon.setImageDrawable(iconsForSavingsRecord.get(1));
            viewHolder.deleteIconBack.setImageDrawable(backDropForSavingsRecord.get(1));
            viewHolder.deleteIconContainer.setVisibility(View.VISIBLE);
        }
        viewHolder.deleteIconContainer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                remove(item);
            }
        });
    }

    private void createNextIconView(ViewHolder viewHolder, int i, SavingsRecord item)
    {
        if (i == 0)
        {
            viewHolder.nextIcon.setImageDrawable(iconsForSavingsRecord.get(2));
            viewHolder.nextIconBack.setImageDrawable(backDropForSavingsRecord.get(2));
            TranslateAnimation anim = bounceFromAbove();
            viewHolder.nextIconContainer.startAnimation(anim);
        } else
        {
            viewHolder.nextIcon.setImageDrawable(iconsForSavingsRecord.get(2));
            viewHolder.nextIconBack.setImageDrawable(backDropForSavingsRecord.get(2));
        }
        nextOnClick(viewHolder);
    }

    private void resetInitialView(ViewHolder viewHolder)
    {
        //Remove views that the recyclerView may hold onto
        viewHolder.savingsView.setVisibility(View.GONE);
        viewHolder.nextIcon.setImageDrawable(null);
        viewHolder.deleteIcon.setImageDrawable(null);
        viewHolder.backIcon.setImageDrawable(null);
        viewHolder.acceptIcon.setImageDrawable(null);
    }

    private void createColorBackground(ViewHolder viewHolder, SavingsRecord item)
    {
        String colorString = item.getSavingsType().getColor();
        int newColor = uiHelper.createColorBackground(colorString);
        viewHolder.sliderView.setBackgroundColor(newColor);
    }

    private void backOnClick(final ViewHolder viewHolder)
    {
        viewHolder.backIconContainer.setOnClickListener(new View.OnClickListener()
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
                viewHolder.text.setVisibility(View.VISIBLE);
                viewHolder.nextIconContainer.setVisibility(View.VISIBLE);
                viewHolder.deleteIconContainer.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        viewHolder.savingsView.setVisibility(View.GONE);
                        viewHolder.acceptIconContainer.setVisibility(View.GONE);
                        viewHolder.backIconContainer.setVisibility(View.GONE);
                        nextOnClick(viewHolder);
                    }
                }, 1000);
            }
        });
    }

    private void nextOnClick(final ViewHolder viewHolder)
    {
        viewHolder.nextIconContainer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                view.setVisibility(View.GONE);
                viewHolder.deleteIconContainer.setVisibility(View.GONE);
                viewHolder.text.setVisibility(View.GONE);
                viewHolder.savingsView.setVisibility(View.VISIBLE);

                TranslateAnimation anim = new TranslateAnimation(1000, 0, 0, 0);
                anim.setDuration(500);
                anim.setInterpolator(new LinearInterpolator());
                anim.setRepeatCount(0);
                anim.setFillAfter(false);
                viewHolder.savingsView.startAnimation(anim);
                viewHolder.acceptIconContainer.setVisibility(View.VISIBLE);
                viewHolder.backIconContainer.setVisibility(View.VISIBLE);
            }
        });
    }

    private void createNumberPickers(ViewHolder viewHolder, final SavingsRecord item, int position)
    {
        createMultiplierPicker(viewHolder, item);
        createAmountPicker(viewHolder, item);
        createFrequencyString(viewHolder, item);

    }

    private void createFrequencyString(ViewHolder viewHolder, final SavingsRecord item)
    {
        final String[] frequencyValuesArray = new String[]{"Daily", "Weekly", "Monthly", "Yearly"};
        viewHolder.frequencyString.setMaxValue(frequencyValuesArray.length - 1);
        viewHolder.frequencyString.setMinValue(0);
        viewHolder.frequencyString.setDisplayedValues(frequencyValuesArray);
        if (item.getFrequency()!= null)
        {
            int pickerPosition = 0;
            for (String s : frequencyValuesArray)
            {
                if (s.equals(item.getFrequency()))
                {
                    viewHolder.frequencyString.setValue(pickerPosition);
                }
                pickerPosition++;
            }
        } else {
            viewHolder.frequencyString.setValue(0);
        }
        viewHolder.frequencyString.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2)
            {
                String frequency = frequencyValuesArray[numberPicker.getValue()];
                item.setFrequency(frequency);
            }
        });
    }

    private void createAmountPicker(ViewHolder viewHolder, final SavingsRecord item)
    {
        Locale locale = context.getResources().getConfiguration().locale;
        Currency localCurrency = Currency.getInstance(locale);
        String currencySymbol = localCurrency.getSymbol(locale);

        final String[] savingsAmounts = new String[300];
        for (int i = 0; i < 300; i++)
        {
            double amount = i * 0.50;
            savingsAmounts[i] = currencySymbol + Double.toString(amount) + "0";
        }
        viewHolder.amountSaved.setMaxValue(savingsAmounts.length - 1);
        viewHolder.amountSaved.setMinValue(0);
        viewHolder.amountSaved.setDisplayedValues(savingsAmounts);
        if (item.getAmount()!= null)
        {
            int pickerPosition = 0;
            for (String s : savingsAmounts)
            {
                if (s.equals(item.getAmount()))
                {
                    viewHolder.amountSaved.setValue(pickerPosition);
                }
                pickerPosition++;
            }
        } else {
        viewHolder.amountSaved.setValue(0);
    }
        viewHolder.amountSaved.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2)
            {
                String savingsAmount = savingsAmounts[numberPicker.getValue()];
                item.setAmount(savingsAmount);

            }
        });
    }

    private void createMultiplierPicker(final ViewHolder viewHolder, final SavingsRecord item)
    {
        viewHolder.frequency.setMaxValue(25);
        viewHolder.frequency.setMinValue(0);
        if (item.getMultiplier()!= null)
        {
            viewHolder.frequency.setValue(item.getMultiplier());
        } else {
            viewHolder.frequency.setValue(0);
        }
        viewHolder.frequency.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2)
            {
                item.setMultiplier(viewHolder.frequency.getValue());
            }
        });
    }

    private TranslateAnimation bounceFromAbove()
    {
        TranslateAnimation bounceAnim = new TranslateAnimation(0, 0, -1000, 0);
        bounceAnim.setDuration(1000);
        bounceAnim.setInterpolator(new BounceInterpolator());
        bounceAnim.setRepeatCount(0);
        bounceAnim.setFillAfter(false);
        return bounceAnim;
    }

    private AnimationSet rollingLeftCall()
    {
        AnimationSet rollingLeft = new AnimationSet(true);
        rollingLeft.setFillEnabled(true);

        TranslateAnimation movingLeft = new TranslateAnimation(940, 0, 0, 0);
        movingLeft.setFillAfter(true);
        movingLeft.setDuration(2000);
        movingLeft.setRepeatCount(0);

        RotateAnimation rolling = new RotateAnimation(359, 0, Animation.RELATIVE_TO_SELF, 0.5255f,
                Animation.RELATIVE_TO_SELF, 0.5255f);
        rolling.setDuration(400);
        rolling.setInterpolator(new LinearInterpolator());
        rolling.setFillAfter(false);
        rolling.setRepeatCount(3);
        rollingLeft.addAnimation(rolling);
        rollingLeft.addAnimation(movingLeft);
        return rollingLeft;
    }

    @Override
    public int getItemCount()
    {
        return savings.size();
    }

    public void add(SavingsRecord item, int position)
    {
        savings.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(SavingsRecord item)
    {
        int position = savings.indexOf(item);
        savings.remove(position);
        notifyItemRemoved(position);
    }

    public void dataSetChanged(List<SavingsRecord> list)
    {
        this.savings = list;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public NetworkImageView image;
        public TextView text;
        public ImageView nextIcon;
        public ImageView nextIconBack;
        public RelativeLayout nextIconContainer;
        public ImageView deleteIcon;
        public ImageView deleteIconBack;
        public RelativeLayout deleteIconContainer;
        public RelativeLayout savingsView;
        public ImageView backIcon;
        public ImageView backIconBack;
        public RelativeLayout backIconContainer;
        public ImageView acceptIcon;
        public ImageView acceptIconBack;
        public RelativeLayout acceptIconContainer;
        public View sliderView;
        public NumberPicker amountSaved;
        public NumberPicker frequency;
        public NumberPicker frequencyString;

        public ViewHolder(View itemView)
        {
            super(itemView);
            image = (NetworkImageView) itemView.findViewById(R.id.saving_icon);
            text = (TextView) itemView.findViewById(R.id.saving_title);

            nextIcon = (ImageView) itemView.findViewById(R.id.next_icon);
            nextIconBack = (ImageView) itemView.findViewById(R.id.nextIconBackk);
            nextIconContainer = (RelativeLayout) itemView.findViewById(R.id.nextIconContainer);

            deleteIcon = (ImageView) itemView.findViewById(R.id.delete_icon);
            deleteIconBack = (ImageView) itemView.findViewById(R.id.delete_iconIconBack);
            deleteIconContainer = (RelativeLayout) itemView.findViewById(R.id.deleteIconContainer);

            savingsView = (RelativeLayout) itemView.findViewById(R.id.slider_second_page);
            sliderView = itemView.findViewById(R.id.slider_second_page_backdrop);
            backIcon = (ImageView) itemView.findViewById(R.id.back_icon);
            backIconBack = (ImageView) itemView.findViewById(R.id.back_iconIconBack);
            backIconContainer = (RelativeLayout) itemView.findViewById(R.id.backIconContainer);

            acceptIcon = (ImageView) itemView.findViewById(R.id.accept_icon);
            acceptIconBack = (ImageView) itemView.findViewById(R.id.acceptIconBackk);
            acceptIconContainer = (RelativeLayout) itemView.findViewById(R.id.acceptIconContainer);

            amountSaved = (NumberPicker) itemView.findViewById(R.id.money_amount);
            frequency = (NumberPicker) itemView.findViewById(R.id.numeric_frequency);
            frequencyString = (NumberPicker) itemView.findViewById(R.id.text_frequency);
        }
    }

    public List<SavingsRecord> getSavingsList()
    {
        return savings;
    }
}
