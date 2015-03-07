package com.app.pug.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.app.pug.HomeActivity;
import com.app.pug.R;
import com.app.pug.UpcomingPlayedGamesActivity;
import com.app.pug.framework.Screen;
import com.app.pug.models.DrawerItem;
import com.app.pug.util.DrawerExpandableAdapter;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by MATIVO-PC on 2/23/2015, 2:04 PM
 * Project:  PUG
 * Package Name: com.app.pug.fragments
 */
public class ProfileScreenFragment extends Screen {
    private View v;
    private final static String TAG = "ProfileScreenFragment";
    private ViewPager viewPager;

    private Toolbar toolBar;

    public static ProfileScreenFragment newInstance() {
        return new ProfileScreenFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.screen_profile, container, false);

        initialisePager();

        setUpList();

        toolBar = (Toolbar) v.findViewById(R.id.toolbarProfile);
        ((HomeActivity)getActivity()).setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.drawable.ic_drawer);
        toolBar.setTitle(null);

        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //implement action
                ((HomeActivity)getActivity()).openDrawer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpList() {
        ExpandableListView drawerListView = (ExpandableListView) v.findViewById(R.id.listDrawer);
        ArrayList<DrawerItem> drawerItemsList = new ArrayList<>();

        drawerItemsList.add(new DrawerItem(R.drawable.ic_item_my_game, "My Plays", new String[]{"Games", "Tournaments", "Teams", "Skills", "Training"}, 0, DrawerItem.TYPE.EXPANDABLE));
        drawerItemsList.add(new DrawerItem(R.drawable.ic_item_gallery, "Gallery", null, 0, DrawerItem.TYPE.GALLERY));
        drawerItemsList.add(new DrawerItem(R.drawable.ic_item_inbox, "Inbox", null, 2, DrawerItem.TYPE.NOTIFICATION));
        drawerItemsList.add(new DrawerItem(R.drawable.ic_notifications, "Notifications", null, 21, DrawerItem.TYPE.NOTIFICATION));

        DrawerExpandableAdapter adapter = new DrawerExpandableAdapter(getActivity(), R.layout.drawer_sub_item_layout, drawerItemsList);
        drawerListView.setAdapter(adapter);

        // register listener to handle child clicks
        drawerListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (groupPosition == 0) {
                    if (childPosition == 0) {
                        startActivity(new Intent(getActivity(), UpcomingPlayedGamesActivity.class));
                    } else if (childPosition == 1) {
                        ((HomeActivity)getActivity()).setCurrentItem(2);
                    }
                }
                return true;
            }
        });
    }

    private void initialisePager() {
        viewPager = (ViewPager) v.findViewById(R.id.pager);
        viewPager.setAdapter(new PagerAdapter(getActivity().getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPageMargin(15);

        viewPager.setOnTouchListener(mSuppressInterceptListener);

        CircleIndicator customIndicator = (CircleIndicator) v.findViewById(R.id.indicator_custom);
        customIndicator.setViewPager(viewPager);
        customIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int i, float v, int i2) {

            }

            @Override public void onPageSelected(int i) {
                Log.d("OnPageChangeListener", "Current selected = " + i);
            }

            @Override public void onPageScrollStateChanged(int i) {

            }
        });
    }

    /**
     * When we have nested View Pagers like in this case,
     * Disallow the parent from intercepting touch events on the child
     */
    private View.OnTouchListener mSuppressInterceptListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN && v instanceof ViewGroup ) {
                if(viewPager.getCurrentItem() < viewPager.getChildCount() - 1) ((ViewGroup) v).requestDisallowInterceptTouchEvent(true);
                else if(viewPager.getCurrentItem() == viewPager.getChildCount() - 1) ((ViewGroup) v).requestDisallowInterceptTouchEvent(false);
            }
            return false;
        }
    };
    private class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager manager) {
            super(manager);
        }

        /**
         * Return the Fragment associated with a specified position.
         *
         * @param position
         */
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return ScreenUserProfileFragment.newInstance();
                default: return ScreenUserProfileFragment.newInstance();
            }
        }

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            return 2;
        }
    }
}
