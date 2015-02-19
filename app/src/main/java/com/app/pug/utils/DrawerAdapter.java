package com.app.pug.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.app.pug.R;
import com.app.pug.models.DrawerItem;

import java.util.List;

public class DrawerAdapter extends ArrayAdapter<DrawerItem> {
	
	private static final String TAG = "DrawerAdapter";
	private Context context;
	private List<DrawerItem> drawerItemList;
	private int layoutResID;
    private int layoutExpandable;
    private DrawerItemHolder d;


    public DrawerAdapter(Context context, int resource, int resourceExpandable, List<DrawerItem> listItems) {
		super(context, 0, listItems);
		this.context = context;
		this.drawerItemList = listItems;
		this.layoutResID = resource;
        this.layoutExpandable = resourceExpandable;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return drawerItemList.size();
	}
	
	@Override
	public DrawerItem getItem(int position) {
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
		
		if(v == null) {
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            if(getItem(position).isExpandable()) {
                v = inflater.inflate(layoutExpandable, parent, false);
                d = new DrawerItemHolder(v, true);
            }else {
                v = inflater.inflate(layoutResID, parent, false);
                d = new DrawerItemHolder(v, false);
            }
			v.setTag(d);
		}else {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            if(getItem(position).isExpandable()) {
                v = inflater.inflate(layoutExpandable, parent, false);
                d = new DrawerItemHolder(v, true);
            }else {
                v = inflater.inflate(layoutResID, parent, false);
                d = new DrawerItemHolder(v, false);
            }
                //d = (DrawerItemHolder) v.getTag();

		}
		
		DrawerItem i = getItem(position);
		d.itemName.setText(i.getTitle());
		d.icon.setImageResource(i.getIcon());

        if(i.isExpandable() && i.getSubItems() != null){
            /**
             * We create the sub items
             */
            ArrayAdapter<String> r = new ArrayAdapter<String>(context, R.layout.drawer_sub_item_layout, R.id.textDrawerSubItem, i.getSubItems());
            d.listView.setAdapter(r);
            int subItemsCount = i.getSubItems().length;

            int itemHeight = BitmapFunctions.convertDpToPixel(48, context);
            int margin = BitmapFunctions.convertDpToPixel(72, context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (subItemsCount * itemHeight)+2);
            params.setMargins(margin, 0, 0 , 0);

            d.listView.setLayoutParams(params);
        }
		return v;
	}
	
	private static class DrawerItemHolder{
		TextView itemName;
		ImageView icon, iconToggle;
        ListView listView;
        View toggle;

        public DrawerItemHolder(View v, boolean isExpandable){
            itemName = (TextView) v.findViewById(R.id.textDrawerItem);
            icon = (ImageView) v.findViewById(R.id.imageIconDrawerItem);

            // Only available in the expandable view
            if(isExpandable) {
                iconToggle = (ImageView) v.findViewById(R.id.imageToggleDrawerItem);
                listView = (ListView) v.findViewById(R.id.listDrawerItemExpanded);
                toggle = v.findViewById(R.id.toggleDrawerItem);
            }
        }
	}
}
