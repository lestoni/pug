package com.app.pug.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.pug.R;
import com.app.pug.models.HomeListItem;

import java.util.List;

public class HomeListAdapter extends ArrayAdapter<HomeListItem> {

    private static final String TAG = "HomeListAdapter";
    private Context context;
    private List<HomeListItem> drawerItemList;
    private int layoutResID;
    private DrawerItemHolder d;

    /**
     * Constructor of the HomeListAdapter
     *
     * @param context   Context
     * @param resource  Layout Respurce ID
     * @param listItems List of Home Items
     */
    public HomeListAdapter(Context context, int resource, List<HomeListItem> listItems) {
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
    public HomeListItem getItem(int position) {
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

        HomeListItem i = getItem(position);
        d.name.setText(i.getName());

        if (!i.getRole().equals("")) {
            d.role.setText(i.getRole());
        } else d.role.setVisibility(View.GONE);

        if (!i.getLocation().equals("")) {
            d.location.setText(i.getLocation());
        } else {
            d.location.setVisibility(View.GONE);
        }

        if (i.getPlays() > 0) {
            d.plays.setText(i.getPlays() + " Plays");
        } else {
            d.plays.setVisibility(View.GONE);
        }

        if (i.getFollowers() > 0) {
            d.followers.setText(i.getFollowers() + " Followers");
        } else {
            d.followers.setVisibility(View.GONE);
        }

        if (i.getFollowing() > 0) {
            d.following.setText(i.getFollowing() + " Following");
        } else {
            d.following.setVisibility(View.GONE);
        }

        d.icon.setImageResource(i.getIcon());

        return v;
    }

    private static class DrawerItemHolder {
        TextView name, role, location, plays, followers, following;
        ImageView icon;

        public DrawerItemHolder(View v) {
            name = (TextView) v.findViewById(R.id.homeListItemName);
            role = (TextView) v.findViewById(R.id.homeListItemRole);
            location = (TextView) v.findViewById(R.id.homeListItemLocation);
            plays = (TextView) v.findViewById(R.id.homeListItemPlays);
            followers = (TextView) v.findViewById(R.id.homeListItemFollowers);
            following = (TextView) v.findViewById(R.id.homeListItemFollowing);

            icon = (ImageView) v.findViewById(R.id.homeListItemImage);
        }
    }
}
