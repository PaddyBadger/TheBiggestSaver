package com.thebiggestsaver.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.thebiggestsaver.R;
import com.thebiggestsaver.models.Savings;

import java.util.List;

/**
 * Created by patriciaestridge on 8/23/14.
 */
public class SavingPagerAdapter extends PagerAdapter {
    Context mContext;
    List<Savings> savings;
    View view;

    public SavingPagerAdapter(Context context, List<Savings> savings)
    {
        this.mContext = context;
        this.savings = savings;
    }
    @Override
    public int getCount() {
        return savings.size();
    }

    @Override
    public Object instantiateItem(View container, int position) {

        view = LayoutInflater.from(mContext).inflate(R.layout.savings_pager, null);

        TextView savingText = (TextView) view.findViewById(R.id.saving_title);
        savingText.setText(savings.get(position).getTitle());
        ((ViewPager) container).addView(view, 0);
        return view;
    }


    @Override
    public boolean isViewFromObject(View view, Object o) {
        return false;
    }
}
