package com.thebiggestsaver.fragments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thebiggestsaver.R;
import com.thebiggestsaver.activities.SavingActivity;
import com.thebiggestsaver.helpers.SavingsTypeHelper;
import com.thebiggestsaver.models.SavingsRecord;
import com.thebiggestsaver.models.SavingsType;
import com.thebiggestsaver.utils.DimeUi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patriciaestridge on 8/29/14.
 */
public class SavingTypeFragment extends Fragment {
    private static String ARG_NUMBER = "position";
    public List<SavingsType> savingsTypeList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.savings_pager, container, false);
        Bundle args = getArguments();
        final int position = args.getInt(ARG_NUMBER);

        SavingsTypeHelper savingsTypeHelper = new SavingsTypeHelper();
        savingsTypeList = savingsTypeHelper.savingsTypeHelperBuildList(getActivity().getApplicationContext());

        final TextView title = (TextView) rootView.findViewById(R.id.saving_title);
        title.setText(savingsTypeList.get(position).getTitle());

        List<Integer> addIcon = new ArrayList<Integer>();
        addIcon.add(R.drawable.add);

        String colorString = savingsTypeList.get(position).getColor();
        int colorForIcons = Color.parseColor(colorString);

        DimeUi uiHelper = new DimeUi();

        List<StateListDrawable> stateListIcons = uiHelper.buildDrawableStateList(getActivity(), addIcon, colorForIcons, DimeUi.IconOrBack.icon);
        List<StateListDrawable> stateListBackground = uiHelper.buildDrawableStateList(getActivity(), addIcon, colorForIcons, DimeUi.IconOrBack.back);

        RelativeLayout addIconView = (RelativeLayout) rootView.findViewById(R.id.addIconContainer);
        ImageView addIconDrawable = (ImageView) rootView.findViewById(R.id.addIcon);
        addIconDrawable.setImageDrawable(stateListIcons.get(0));
        ImageView addIconBack = (ImageView) rootView.findViewById(R.id.addIconBack);
        addIconBack.setImageDrawable(stateListBackground.get(0));

        String iconString = savingsTypeList.get(position).getIconString();
        int iconId = getActivity().getResources().getIdentifier(iconString, "drawable", getActivity().getPackageName());

        Drawable drawable = getActivity().getResources().getDrawable(iconId);
        ColorStateList stateList = new ColorStateList(
                new int[][] {new int[] {},},
                             new int[] {colorForIcons,
                });
        drawable.setTint(stateList, PorterDuff.Mode.MULTIPLY);

        ImageView icon = (ImageView) rootView.findViewById(R.id.saving_icon);
        icon.setImageDrawable(drawable);

        addIconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SavingsRecord savingRecord = new SavingsRecord();
                savingRecord.setTitle(savingsTypeList.get(position).getTitle());
                savingRecord.setSavingsTypeId(savingsTypeList.get(position).getId());
                savingRecord.setSavingsType(savingsTypeList.get(position));
                savingRecord.setId(savingsTypeList.get(position).getTitle());
                ((SavingActivity)getActivity()).savingListAdapter.add(savingRecord, 0);
                ((SavingActivity)getActivity()).recyclerView.scrollToPosition(0);
            }
        });
        return rootView;
    }
}
