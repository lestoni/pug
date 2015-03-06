package com.app.pug.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.pug.R;
import com.app.pug.models.UpcomingPlayedItem;

import java.util.ArrayList;
import java.util.Locale;

public class UpcomingPlayedAdapter extends ArrayAdapter<UpcomingPlayedItem> {

    private Context context;
    private ArrayList<UpcomingPlayedItem> items;

    public UpcomingPlayedAdapter(Context context, int resource, ArrayList<UpcomingPlayedItem> items) {
        super(context, resource, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View root = convertView;
        Holder holder;
        if (root == null) {
            root = LayoutInflater.from(context).inflate(R.layout.upcoming_played_item_layout, parent, false);
            holder = new Holder();

            holder.dateTime = (TextView) root.findViewById(R.id.game_item_date_time);
            holder.location = (TextView) root.findViewById(R.id.game_item_location);
            holder.joined = (TextView) root.findViewById(R.id.position_count_joined);
            holder.left = (TextView) root.findViewById(R.id.position_count_left);
            holder.playerHorizontalScrollView = (HorizontalScrollView) root.findViewById(R.id.player_item_horizontalscrollview);

            root.setTag(holder);
        } else {
            holder = (Holder) root.getTag();
        }
        UpcomingPlayedItem playerItem = items.get(position);
        holder.dateTime.setText(playerItem.dateTime);
        holder.location.setText(playerItem.location);
        holder.joined.setText(String.format(Locale.getDefault(), "%d joined", playerItem.joined));
        int left = playerItem.left;
        holder.left.setText(String.format(Locale.getDefault(), "(%s)", left == 0 ? "Full" : String.format(Locale.getDefault(), "%d spot%s left",
                left, left == 1 ? "" : "s")));

//        holder.playerHorizontalScrollView.removeAllViews();
//        for (ImageView drawable : playerItem.images) {
//            holder.playerHorizontalScrollView.addView(drawable);
//        }

        return root;
    }

    private class Holder {
        TextView dateTime;
        TextView location;
        TextView joined;
        TextView left;
        HorizontalScrollView playerHorizontalScrollView;
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
