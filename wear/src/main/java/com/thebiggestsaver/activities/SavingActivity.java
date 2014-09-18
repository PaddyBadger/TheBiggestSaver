package com.thebiggestsaver.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thebiggestsaver.R;
import com.thebiggestsaver.adapters.SavingAdapter;
import com.thebiggestsaver.models.Savings;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class SavingActivity extends Activity {

    private View rootView;
    private SavingAdapter feedAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Savings> savingsList = new ArrayList<Savings>();

    public static void getLaunchIntent(Context context)
    {
        Intent i = new Intent(context, SavingActivity.class);
        context.startActivity(i);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_phone);
        ButterKnife.inject(this, rootView);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.signup_list);
        List<Savings> savings = new ArrayList<Savings>();

        feedAdapter = new SavingAdapter(this, savings, R.layout.savings_pager);
        recyclerView.setAdapter(feedAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        Savings bike = new Savings();
        Bitmap bikeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.pink_bike);
        bike.setIcon(bikeIcon);
        bike.setTitle("Biking to work");

        Savings coffee = new Savings();
        Bitmap coffeeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_coffee);
        coffee.setIcon(coffeeIcon);
        coffee.setTitle("Buy less takaway coffee");

        savingsList.add(bike);
        savingsList.add(coffee);
    }
}
