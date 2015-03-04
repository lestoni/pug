package com.app.pug.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.fab.FloatingActionButton;
import com.app.fab.ScrollDirectionListener;
import com.app.pug.R;
import com.app.pug.framework.Screen;
import com.app.pug.models.UserListItem;
import com.app.pug.util.UserListAdapter;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by MATIVO-PC on 2/23/2015, 3:44 PM
 * Project:  PUG
 * Package Name: com.app.pug.fragments
 */
public class ScreenUserFollowingFragment extends Screen {
    private View v;
    private final static String TAG = "ScreenUserFollowingFragment";
    private ViewPager viewPager;
    private ListView list;
    private UserListAdapter adap;
    private FloatingActionButton fab;

    public static ScreenUserFollowingFragment newInstance() {
        return new ScreenUserFollowingFragment();
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

        v = inflater.inflate(R.layout.screen_user_following, container, false);

        list = (ListView) v.findViewById(R.id.list);

        testData();

        initialisePager();

        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.attachToListView(list, new ScrollDirectionListener() {
            @Override
            public void onScrollDown() {

                Log.d("ListViewFragment", "onScrollDown()");
            }

            @Override
            public void onScrollUp() {

                Log.d("ListViewFragment", "onScrollUp()");
            }
        });

        return v;
    }

    private void testData() {
        List<UserListItem> items = new ArrayList<UserListItem>();
        items.add(new UserListItem(R.drawable.ic_list_item_1, getHtml("Lebron James rated on John sons Skill at ", ""), "5 mins ago" ));
        items.add(new UserListItem(R.drawable.ic_list_item_2, getHtml("", " has been added to Lebron James favourite courts. "), "A few secs ago" ));
        items.add(new UserListItem(R.drawable.ic_list_item_3, getHtml("Lebron James created a new game at ", "for April 30th Saturday 14:00 hrs"), "1 hour ago" ));
        items.add(new UserListItem(R.drawable.ic_list_item_4, getHtml("", " has been booked for a tournament on April 19th 2015 at 09:30 hrs"), "2 days ago"));

        adap = new UserListAdapter(getActivity(), R.layout.screen_user_following_list_item, items);
        list.setAdapter(adap);
    }

    private android.text.Spanned getHtml(String part, String part2) {
        String html = part + " <font color=\"#f5ad1e\">14th Street Playground</font> "+part2;
        return Html.fromHtml(html);
    }

    private void initialisePager() {
        viewPager = (ViewPager) v.findViewById(R.id.pager);
        viewPager.setAdapter(new PagerAdapter(getActivity().getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPageMargin(15);

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
