package com.app.pug.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.pug.HomeActivity;
import com.app.pug.R;
import com.app.pug.UserActivity;
import com.app.pug.adapters.HomeListAdapter;
import com.app.pug.framework.Screen;
import com.app.pug.models.HomeListItem;
import com.app.pug.utils.PREFUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MATIVO-PC on 2/22/2015, 10:06 PM
 * Project:  PUG
 * Package Name: com.app.pug
 */
public class HomeFragment extends Screen {

    private View v;
    private ListView list;
    private HomeListAdapter adap;
    private ViewPager viewPager;
    private Toolbar toolBar;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.screen_home, container, false);

        toolBar = (Toolbar) v.findViewById(R.id.toolbar);
        ((HomeActivity)getActivity()).setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.drawable.ic_drawer);
        toolBar.setTitle(null);

        list = (ListView) v.findViewById(R.id.listHome);
        list.setOnItemClickListener(playersListener);

        managePrompt();

        testData();

        initialisePager();

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

    private AdapterView.OnItemClickListener playersListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int pos = 1;

            if((position%2) == 0){
                pos = 2;
            }

            Intent intent = new Intent(getActivity(), UserActivity.class);
            intent.putExtra("pos", pos);
            getActivity().startActivity(intent);
        }
    };

    public void managePrompt() {
        boolean isFirstUse = PREFUtils.isFirstUse(this.getActivity());
        if(isFirstUse){
            //Show the Prompt
            v.findViewById(R.id.homePrompt).setVisibility(View.VISIBLE);
            v.findViewById(R.id.promptImageClose).setOnClickListener(promptListener);
            v.findViewById(R.id.promptReviewSettings).setOnClickListener(promptListener);
        }
    }

    /**
     * OnClick Listener for the Prompt Panel
     */
    private View.OnClickListener promptListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean isClose = (view.getId() == R.id.promptImageClose);
            if(isClose) {
                //Button Close Prompt
                v.findViewById(R.id.homePrompt).setVisibility(View.GONE);

                //Change the Pref for the Prompt
                PREFUtils.setFirstUse(getActivity(), false);

            }else {
                //Button Review; Close then show the next fragment

            }
        }
    };

    private void initialisePager() {
        viewPager = (ViewPager) v.findViewById(R.id.homePager);
        viewPager.setAdapter(new ViewAdapter(5, getActivity().getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(5);
        viewPager.setPageMargin(15);
        viewPager.setClipChildren(false);

        viewPager.setOnTouchListener(mSuppressInterceptListener);

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

    private void testData() {
        List<HomeListItem> items = new ArrayList<HomeListItem>();
        items.add(new HomeListItem(R.drawable.ic_list_item_1, "Lovren James", "Shooting G", "Cleveland", 854, 42, 854));
        items.add(new HomeListItem(R.drawable.ic_list_item_2, "Mora Moore Jordan", "Small Forward", "Brooklyn, New York", 1, 214, 854));
        items.add(new HomeListItem(R.drawable.ic_list_item_3, "JohnSon Williams", "Point Guard", "Texas", 500, 2845, 125));
        items.add(new HomeListItem(R.drawable.ic_list_item_4, "Serena Peters", "", "Duke City", 452, 1200, 10));

        adap = new HomeListAdapter(getActivity(), R.layout.screen_home_list_item, items);
        list.setAdapter(adap);
    }

    private class ViewAdapter extends FragmentPagerAdapter {
        private int total;

        public ViewAdapter(int total , FragmentManager manager) {
            super(manager);
            this.total = total;
        }

        /**
         * Return the Fragment associated with a specified position.
         *
         * @param position
         */
        @Override
        public Fragment getItem(int position) {
            return HomePagerFragment.newInstance(position);
        }

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            return total;
        }
    }
}
