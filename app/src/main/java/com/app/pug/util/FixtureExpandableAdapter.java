package com.app.pug.util;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.pug.R;
import com.app.pug.models.FixtureItem;
import com.app.pug.models.FixtureModel;

import java.util.List;

public class FixtureExpandableAdapter extends BaseExpandableListAdapter {

    private static final String TAG = "FixtureExpandableAdapter";
    private final LayoutInflater inflater;
    private Context context;
    private List<FixtureModel> modelListModel;

    private int groupRes = R.layout.screen_fixture_group_item;
    private int childRes = R.layout.screen_fixture_child_item;

    public FixtureExpandableAdapter(Context context, List<FixtureModel> modelList) {
        this.modelListModel = modelList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    private android.text.Spanned getHtml(String joined, String spotsLeft) {
        String html = "<font color=\"#85d2c5\">"+joined+" joined</font> ("+spotsLeft+" spots left)";
        return Html.fromHtml(html);
    }

    /**
     * Gets the number of groups.
     *
     * @return the number of groups
     */
    @Override
    public int getGroupCount() {
        return modelListModel.size();
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
        FixtureItem[] childs = modelListModel.get(groupPosition).getItems();
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
    public FixtureModel getGroup(int groupPosition) {
        return modelListModel.get(groupPosition);
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
    public FixtureItem getChild(int groupPosition, int childPosition) {
        FixtureItem[] childs = modelListModel.get(groupPosition).getItems();
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
        return groupPosition;
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
        ItemHolder d;
        FixtureModel i = getGroup(groupPosition);

        //if (v == null) {
            v = inflater.inflate(groupRes, parent, false);
            d = new ItemHolder(v);
            v.setTag(d);/*
        } else {
            d = (ItemHolder) v.getTag();
        }*/

        d.time.setText(i.getTime());

        try {
            if (getChildrenCount(groupPosition) > 0) {
                    d.iconToggle.setImageResource(R.drawable.ic_action_expand);
            }else {
                d.iconToggle.setVisibility(View.GONE);
            }
        }catch(Exception ex){ex.printStackTrace(); }

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
        ChildItemHolder c;
        FixtureItem i = getChild(groupPosition, childPosition);

        if (v == null) {
            v = inflater.inflate(childRes, parent, false);
            c = new ChildItemHolder(v);
            v.setTag(c);
        } else {
            c = (ChildItemHolder) v.getTag();
        }

        c.team_left.setText(i.getTeam1());
        c.team_right.setText(i.getTeam2());
        c.time.setText(i.getTime());
        c.playground.setText(i.getPlayground());
        c.capacity.setText(getHtml(i.getNumJoined()+"", i.getNumSpotsLeft()+""));

        c.team_l.setImageResource(i.getIcon_team_1());
        c.team_r.setImageResource(i.getIcon_team_2());

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

    public static class ItemHolder {
        public static TextView time;
        ImageView iconToggle;

        public ItemHolder(View v) {
            time = (TextView) v.findViewById(R.id.fixture_group_title);
            iconToggle = (ImageView) v.findViewById(R.id.fixture_group_toggle);
        }
    }

    public static class ChildItemHolder {
        TextView team_left, team_right, time, playground, capacity;
        ImageView team_l, team_r;

        public ChildItemHolder(View v) {
            team_left = (TextView) v.findViewById(R.id.fix_team_left);
            team_right = (TextView) v.findViewById(R.id.fix_team_right);
            time = (TextView) v.findViewById(R.id.fix_child_time);
            playground = (TextView) v.findViewById(R.id.fix_venue);
            capacity = (TextView) v.findViewById(R.id.fix_capacity);

            team_l = (ImageView) v.findViewById(R.id.fix_team_image_left);
            team_r = (ImageView) v.findViewById(R.id.fix_team_image_right);
        }
    }
}
