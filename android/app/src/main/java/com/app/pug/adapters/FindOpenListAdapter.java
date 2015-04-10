package com.app.pug.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.pug.R;
import com.app.pug.models.OpenGameItem;

import java.util.List;

public class FindOpenListAdapter extends ArrayAdapter<OpenGameItem> {
    private static final String TAG = "FindOpenListAdapter";
    private Context context;
    private List<OpenGameItem> drawerItemList;
    private int layoutResID;
    private DrawerItemHolder d;

    /**
     * Constructor of the HomeListAdapter
     *
     * @param context   Context
     * @param resource  Layout Resource ID
     * @param listItems List of Home Items
     */
    public FindOpenListAdapter(Context context, int resource, List<OpenGameItem> listItems) {
        super(context, 0, listItems);
        this.context = context;
        this.drawerItemList = listItems;
        this.layoutResID = resource;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return drawerItemList.size();
    }

    @Override
    public OpenGameItem getItem(int position) {
        return drawerItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    /**
     * changes the view shown on the navigation list.
     */
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }

    /**
     * changes the view shown on the actionbar.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Initialise view holder and set the data required

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            v = inflater.inflate(layoutResID, parent, false);
            d = new DrawerItemHolder(v);
            v.setTag(d);
        } else {
            d = (DrawerItemHolder) v.getTag();
        }

        OpenGameItem i = getItem(position);
        d.name.setText(i.getName());
        d.location.setText(i.getLocation());
        d.date.setText(i.getDate());
        d.aside.setText(i.getAside() + " Aside");
        d.mins.setText(i.getMins() + " Mins");

        d.cost.setText((i.getAmount() == 0) ? "Free" : "$" + i.getAmount());
        d.status.setText(i.getStatus());

        if (i.getStatus().equalsIgnoreCase("Available"))
            d.icon_status.setImageResource(R.drawable.game_item_status_icon_available);
        else if (i.getStatus().equalsIgnoreCase("Full")) {
            d.icon_status.setImageResource(R.drawable.game_item_status_icon_full);
        }
        return v;
    }

    private static class DrawerItemHolder {
        TextView name, location, date, aside, mins, cost, status;
        ImageView image, icon_status;

        public DrawerItemHolder(View v) {
            name = (TextView) v.findViewById(R.id.info_container_area);
            location = (TextView) v.findViewById(R.id.info_container_location);
            date = (TextView) v.findViewById(R.id.info_container_label_timedate);
            aside = (TextView) v.findViewById(R.id.info_container_label_aside);
            mins = (TextView) v.findViewById(R.id.info_container_label_mins);
            cost = (TextView) v.findViewById(R.id.info_container_label_cost);

            status = (TextView) v.findViewById(R.id.game_item_status_label);
            icon_status = (ImageView) v.findViewById(R.id.game_item_status_icon);

            image = (ImageView) v.findViewById(R.id.game_item_image);

        }
    }
}
