package com.thebiggestsaver.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thebiggestsaver.R;
import com.thebiggestsaver.activities.SavingActivity;
import com.thebiggestsaver.helpers.SavingsTypeHelper;
import com.thebiggestsaver.models.SavingsRecord;
import com.thebiggestsaver.models.SavingsType;

import java.awt.font.TextAttribute;
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

        ImageView addIcon = (ImageView) rootView.findViewById(R.id.addIcon);
        addIcon.setImageDrawable(savingsTypeList.get(position).getAdd());

        ImageView icon = (ImageView) rootView.findViewById(R.id.saving_icon);
        icon.setImageDrawable(savingsTypeList.get(position).getIcon());


        addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SavingsRecord savingRecord = new SavingsRecord();
                savingRecord.setTitle(savingsTypeList.get(position).getTitle());
                savingRecord.setSavingsType(savingsTypeList.get(position));
                savingRecord.setId(savingsTypeList.get(position).getTitle());
                ((SavingActivity)getActivity()).savingListAdapter.add(savingRecord, 0);
                ((SavingActivity)getActivity()).recyclerView.scrollToPosition(0);
            }
        });

        return rootView;
    }
}
