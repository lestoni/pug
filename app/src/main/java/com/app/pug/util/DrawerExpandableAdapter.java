package com.app.pug.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.pug.R;
import com.app.pug.models.DrawerItem;

import java.util.List;

public class DrawerExpandableAdapter extends BaseExpandableListAdapter {

    private static final String TAG = "DrawerExpandableAdapter";
    private final LayoutInflater inflater;
    private Context context;
    private List<DrawerItem> drawerItemList;

    private int groupResEx = R.layout.drawer_expandable_item_layout;
    private int groupResNotification = R.layout.drawer_expandable_item_num_layout;

    private int childRes;
    private DrawerItemHolder d;

    public DrawerExpandableAdapter(Context context, int childRes, List<DrawerItem> drawerItemList) {
        this.drawerItemList = drawerItemList;
        this.context = context;
        this.childRes = childRes;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * Gets the number of groups.
     *
     * @return the number of groups
     */
    @Override
    public int getGroupCount() {
        return drawerItemList.size();
    }

    /**
     * Gets the number of children in a specified group.
     *
     * @param groupPosition the position of the group for which the children
     *                      count should be returned
     * @return the children count in the specified group
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        String[] childs = drawerItemList.get(groupPosition).getSubItems();
        if (childs == null) return 0;
        else return childs.length;
    }

    /**
     * Gets the data associated with the given group.
     *
     * @param groupPosition the position of the group
     * @return the data child for the specified group
     */
    @Override
    public DrawerItem getGroup(int groupPosition) {
        return drawerItemList.get(groupPosition);
    }

    /**
     * Gets the data associated with the given child within the given group.
     *
     * @param groupPosition the position of the group that the child resides in
     * @param childPosition the position of the child with respect to other
     *                      children in the group
     * @return the data of the child
     */
    @Override
    public String getChild(int groupPosition, int childPosition) {
        String[] childs = drawerItemList.get(groupPosition).getSubItems();
        if (childs == null) return null;
        else {
            if (childs.length > childPosition)
                return childs[childPosition];
            else return null;
        }
    }

    /**
     * Gets the ID for the group at the given position. This group ID must be
     * unique across groups. The combined ID (see
     * {@link #getCombinedGroupId(long)}) must be unique across ALL items
     * (groups and all children).
     *
     * @param groupPosition the position of the group for which the ID is wanted
     * @return the ID associated with the group
     */
    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    /**
     * Gets the ID for the given child within the given group. This ID must be
     * unique across all children within the group. The combined ID (see
     * {@link #getCombinedChildId(long, long)}) must be unique across ALL items
     * (groups and all children).
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child within the group for which
     *                      the ID is wanted
     * @return the ID associated with the child
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    /**
     * Indicates whether the child and group IDs are stable across changes to the
     * underlying data.
     *
     * @return whether or not the same ID always refers to the same object
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Gets a View that displays the given group. This View is only for the
     * group--the Views for the group's children will be fetched using
     * {@link #getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)}.
     *
     * @param groupPosition the position of the group for which the View is
     *                      returned
     * @param isExpanded    whether the group is expanded or collapsed
     * @param convertView   the old view to reuse, if possible. You should check
     *                      that this view is non-null and of an appropriate type before
     *                      using. If it is not possible to convert this view to display
     *                      the correct data, this method can create a new view. It is not
     *                      guaranteed that the convertView will have been previously
     *                      created by
     *                      {@link #getGroupView(int, boolean, android.view.View, android.view.ViewGroup)}.
     * @param parent        the parent that this view will eventually be attached to
     * @return the View corresponding to the group at the specified position
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v = convertView;
        DrawerItemHolder d;
        DrawerItem i = getGroup(groupPosition);

        if(i.getType() != DrawerItem.TYPE.NOTIFICATION) {
            //if (v == null) {
                v = inflater.inflate(groupResEx, parent, false);
                d = new DrawerItemHolder(v, i.getType());
                v.setTag(d);
           // }

            d.itemName.setText(i.getTitle());
            d.icon.setImageResource(i.getIcon());

            try {
                if (getChildrenCount(groupPosition) > 0) {
                    if (isExpanded) {
                        d.iconToggle.setImageResource(R.drawable.ic_action_collapse);
                    } else {
                        d.iconToggle.setImageResource(R.drawable.ic_action_expand);
                    }
                }
            }catch(Exception ex){ex.printStackTrace(); }
        }else {
            //if (v == null) {
                v = inflater.inflate(groupResNotification, parent, false);
                d = new DrawerItemHolder(v, i.getType());
                v.setTag(d);
            /*} else {
                d = (DrawerItemHolder) v.getTag();
            }*/

            d.itemName.setText(i.getTitle());
            d.icon.setImageResource(i.getIcon());
            d.itemnotnum.setText(i.getNotifications()+"");
        }

        return v;
    }

    /**
     * Gets a View that displays the data for the given child within the given
     * group.
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child (for which the View is
     *                      returned) within the group
     * @param isLastChild   Whether the child is the last child within the group
     * @param convertView   the old view to reuse, if possible. You should check
     *                      that this view is non-null and of an appropriate type before
     *                      using. If it is not possible to convert this view to display
     *                      the correct data, this method can create a new view. It is not
     *                      guaranteed that the convertView will have been previously
     *                      created by
     *                      {@link #getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)}.
     * @param parent        the parent that this view will eventually be attached to
     * @return the View corresponding to the child at the specified position
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v = convertView;
        DrawerChildItemHolder dc;

        if (v == null) {
            v = inflater.inflate(childRes, parent, false);
            //v = View.inflate(context, childRes, parent);
            dc = new DrawerChildItemHolder(v);
            v.setTag(dc);
        } else {
            dc = (DrawerChildItemHolder) v.getTag();
        }

        String s = getChild(groupPosition, childPosition);
        if (s != null) {
            dc.itemName.setText(s);
        }

        return v;
    }

    /**
     * Whether the child at the specified position is selectable.
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child within the group
     * @return whether the child is selectable.
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public static class DrawerItemHolder {
        public static TextView itemName, itemnotnum;
        ImageView icon, iconToggle;

        public DrawerItemHolder(View v, DrawerItem.TYPE type) {
            itemName = (TextView) v.findViewById(R.id.textDrawerItem);
            icon = (ImageView) v.findViewById(R.id.imageIconDrawerItem);

            if(type == DrawerItem.TYPE.EXPANDABLE || type == DrawerItem.TYPE.GALLERY || type == DrawerItem.TYPE.NORMAL) {
                iconToggle = (ImageView) v.findViewById(R.id.imageToggleDrawerItem);
                if(type == DrawerItem.TYPE.NORMAL) {
                    iconToggle.setVisibility(View.GONE);
                }else if(type == DrawerItem.TYPE.GALLERY) {
                    iconToggle.setImageResource(R.drawable.ic_action_next_item);
                }
            }else if(type == DrawerItem.TYPE.NOTIFICATION) {
                itemnotnum = (TextView) v.findViewById(R.id.textDrawerNotiNumber);
            }
        }
    }

    public static class DrawerChildItemHolder {
        public static TextView itemName;

        public DrawerChildItemHolder(View v) {
            itemName = (TextView) v.findViewById(R.id.textDrawerSubItem);
        }
    }
}
