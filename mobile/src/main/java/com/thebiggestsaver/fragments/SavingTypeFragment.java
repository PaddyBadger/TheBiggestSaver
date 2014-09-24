package com.thebiggestsaver.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thebiggestsaver.R;
import com.thebiggestsaver.helpers.SavingsTypeHelper;
import com.thebiggestsaver.models.SavingsType;

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


        return rootView;
    }
}
