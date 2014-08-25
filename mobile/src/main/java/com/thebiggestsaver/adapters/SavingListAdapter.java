package com.thebiggestsaver.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.support.v7.graphics.PaletteItem;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thebiggestsaver.R;
import com.thebiggestsaver.models.Savings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patriciaestridge on 8/21/14.
 */
public class SavingListAdapter extends RecyclerView.Adapter<SavingListAdapter.ViewHolder> {
    public List<Savings> savings = new ArrayList<Savings>();
    private Context context;
    private int itemLayout;

    public SavingListAdapter(Context context, List<Savings> savings, int itemLayout)
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
        Savings item = savings.get(i);
        viewHolder.text.setText(item.getTitle());
        Bitmap icon = item.getIcon();
        viewHolder.image.setImageBitmap(icon);

        Palette p = Palette.generate(icon, 24);
        final PaletteItem darkVibrantColor = p.getDarkVibrantColor();
        viewHolder.text.setBackgroundColor(darkVibrantColor.getRgb());

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

    public void add(Savings item, int position) {
        savings.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Savings item) {
        int position = savings.indexOf(item);
        savings.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            text = (TextView) itemView.findViewById(R.id.title);

        }
    }
}
