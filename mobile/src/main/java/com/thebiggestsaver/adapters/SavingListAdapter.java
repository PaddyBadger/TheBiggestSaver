package com.thebiggestsaver.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.graphics.Palette;
import android.support.v7.graphics.PaletteItem;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thebiggestsaver.R;
import com.thebiggestsaver.models.SavingsRecord;
import com.thebiggestsaver.models.SavingsType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patriciaestridge on 8/21/14.
 */
public class SavingListAdapter extends RecyclerView.Adapter<SavingListAdapter.ViewHolder> {
    public ArrayList<SavingsRecord> savings = new ArrayList<SavingsRecord>();
    private Context context;
    private int itemLayout;

    public SavingListAdapter(Context context, ArrayList<SavingsRecord> savings, int itemLayout)
    {
        super();
        this.context = context;
        this.savings = savings;
        this.itemLayout = itemLayout;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        SavingsRecord item = savings.get(i);
        viewHolder.text.setText(item.getTitle());
        Drawable icon = item.getSavingsType().getIcon();
        viewHolder.image.setImageDrawable(icon);
        final Drawable nextIcon = item.getSavingsType().getNext();
        viewHolder.nextIcon.setImageDrawable(nextIcon);
        final Drawable deleteIcon = item.getSavingsType().getDelete();

        if (i == 0)
        {
            TranslateAnimation anim=new TranslateAnimation(0,0,-1000,0);
            anim.setDuration(1000);
            anim.setInterpolator(new BounceInterpolator());
            anim.setRepeatCount(0);
            anim.setFillAfter(true);
            viewHolder.nextIcon.startAnimation(anim);
        }

        viewHolder.nextIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        if (i == 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    viewHolder.deleteIcon.setImageDrawable(deleteIcon);

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
                    rolling.setFillAfter(true);
                    rolling.setRepeatCount(3);

                    rollingLeft.addAnimation(rolling);
                    rollingLeft.addAnimation(movingLeft);
                    viewHolder.deleteIcon.startAnimation(rollingLeft);

                }
            }, 1000);
        } else {
            viewHolder.deleteIcon.setImageDrawable(nextIcon);
        }



//        Palette p = Palette.generate(icon, 24);
//        final PaletteItem darkVibrantColor = p.getDarkVibrantColor();
//        viewHolder.text.setBackgroundColor(darkVibrantColor.getRgb());

        viewHolder.itemView.setTag(item);

        viewHolder.image.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                Intent intent = new Intent(context, SecondActivity.class);
//                context.startActivity(intent);
            }
        });
    }

    @Override public int getItemCount() {
        return savings.size();
    }

    public void add(SavingsRecord item, int position) {
        savings.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(SavingsRecord item) {
        int position = savings.indexOf(item);
        savings.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView text;
        public ImageView nextIcon;
        public ImageView deleteIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.saving_icon);
            text = (TextView) itemView.findViewById(R.id.saving_title);
            nextIcon = (ImageView)  itemView.findViewById(R.id.next_icon);
            deleteIcon = (ImageView) itemView.findViewById(R.id.delete_icon);

        }
    }

    public ArrayList<SavingsRecord> getSavingsList()
    {
    return savings;
    }
}
