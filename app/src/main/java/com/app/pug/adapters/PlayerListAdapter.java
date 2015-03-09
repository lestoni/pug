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
import com.app.pug.models.PlayerItem;

import java.util.List;

public class PlayerListAdapter extends ArrayAdapter<PlayerItem> {

    private static final String TAG = "PlayerListAdapter";
    private Context context;
    private List<PlayerItem> drawerItemList;
    private int layoutResID;
    private DrawerItemHolder d;

    /**
     * Constructor of the HomeListAdapter
     *
     * @param context   Context
     * @param resource  Layout Resource ID
     * @param listItems List of Home Items
     */
    public PlayerListAdapter(Context context, int resource, List<PlayerItem> listItems) {
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
    public PlayerItem getItem(int position) {
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

        PlayerItem i = getItem(position);
        d.name.setText(i.getName());
        d.joined.setText(i.getJoined());
        d.role.setText(i.getRole());
        d.time.setText(i.getTime());
        //d.icon.setText(i.getJoined());
        return v;
    }

    private static class DrawerItemHolder {
        TextView name, role, joined, time;
        ImageView icon;

        public DrawerItemHolder(View v) {
            name = (TextView) v.findViewById(R.id.player_item_label_name);
            role = (TextView) v.findViewById(R.id.player_item_label_position);
            joined = (TextView) v.findViewById(R.id.joined_label);
            time = (TextView) v.findViewById(R.id.hour_label);
            icon = (ImageView) v.findViewById(R.id.player_item_image);
        }
    }
}
