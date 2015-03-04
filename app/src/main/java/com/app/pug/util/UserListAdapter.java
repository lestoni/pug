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
import com.app.pug.models.UserListItem;

import java.util.List;

public class UserListAdapter extends ArrayAdapter<UserListItem> {

    private static final String TAG = "UserListAdapter";
    private Context context;
    private List<UserListItem> drawerItemList;
    private int layoutResID;
    private DrawerItemHolder d;

    /**
     * Constructor of the HomeListAdapter
     *
     * @param context   Context
     * @param resource  Layout Resource ID
     * @param listItems List of Home Items
     */
    public UserListAdapter(Context context, int resource, List<UserListItem> listItems) {
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
    public UserListItem getItem(int position) {
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

        UserListItem i = getItem(position);
        d.content.setText(i.getText());
        d.time.setText(i.getTime());

        d.icon.setImageResource(i.getIcon());

        return v;
    }

    private static class DrawerItemHolder {
        TextView content, time;
        ImageView icon;

        public DrawerItemHolder(View v) {
            content = (TextView) v.findViewById(R.id.itemContent);
            time = (TextView) v.findViewById(R.id.itemTime);
            icon = (ImageView) v.findViewById(R.id.itemImage);
        }
    }
}
