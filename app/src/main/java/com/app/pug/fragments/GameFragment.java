package com.app.pug.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.pug.R;
import com.app.pug.framework.Screen;
import com.app.pug.utils.BitmapFunctions;

/**
 * Created by MATIVO-PC on 2/23/2015, 2:04 PM
 * Project:  PUG
 * Package Name: com.app.pug.fragments
 */
public class GameFragment extends Screen implements View.OnClickListener {
    private View v;
    private final static String TAG = "GameFragment";
    private FragmentManager mFragmentMgr;
    private ViewPager viewPager;
    private TextView tabDetails;
    private TextView tabPlayers;

    public static GameFragment newInstance() {
        return new GameFragment();
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
        v = inflater.inflate(R.layout.screen_game, container, false);
        mFragmentMgr = getChildFragmentManager();

        init();
        return v;
    }

    private void init() {
        viewPager = (ViewPager) v.findViewById(R.id.homePager);
        viewPager.setAdapter(new ViewAdapter(getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(5);
        viewPager.setPageMargin(15);

        tabDetails = (TextView) v.findViewById(R.id.tab_game_details);
        tabPlayers = (TextView) v.findViewById(R.id.tab_players);
        tabDetails.setOnClickListener(this);
        tabPlayers.setOnClickListener(this);

        viewPager.setOnPageChangeListener(pageChangeListener);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        //Determine if tab Details is selected
        boolean isTabDetails = v.getId() == R.id.tab_game_details;

        //Change text color
        tabDetails.setTextColor(getResources().getColor(isTabDetails ? R.color.black : R.color.light_gray));
        tabPlayers.setTextColor(getResources().getColor(isTabDetails ? R.color.light_gray : R.color.black));

        //Change Backgrounds
        tabDetails.setBackgroundResource(isTabDetails ? R.drawable.game_screen_tab_item_background_active : R.drawable.game_screen_tab_item_background_inactive);
        tabPlayers.setBackgroundResource(isTabDetails ? R.drawable.game_screen_tab_item_background_inactive : R.drawable.game_screen_tab_item_background_active);

        setIndicatorPadding();
        if (isTabDetails) {
            viewPager.setCurrentItem(0, true);
        } else {
            viewPager.setCurrentItem(1, true);
        }
    }

    private void setIndicatorPadding() {
        int pixels_20 = BitmapFunctions.convertDpToPixel(20, getActivity());

        tabDetails.setPadding(0, pixels_20 + 1, 0, pixels_20 + 1);
        tabPlayers.setPadding(0, pixels_20 + 1, 0, pixels_20 + 1);
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                onClick(tabDetails);
            } else if (position == 1) {
                onClick(tabPlayers);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private class ViewAdapter extends FragmentPagerAdapter {
        public ViewAdapter(FragmentManager manager) {
            super(manager);
        }

        /**
         * Return the Fragment associated with a specified position.
         *
         * @param position
         */
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ScreenDetailsFragment();
                case 1:
                    return new ScreenPlayersFragment();
            }
            return null;
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
