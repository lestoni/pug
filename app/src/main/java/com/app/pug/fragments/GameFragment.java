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
public class GameFragment extends Screen implements View.OnClickListener, ViewPager.OnPageChangeListener {
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

        viewPager.setOnPageChangeListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_game_details:
                /**
                 * Change the textColor and background to indicate that the tab has been clicked
                 * Select the corresponding.
                 */
                tabDetails.setTextColor(getResources().getColor(R.color.black));
                tabDetails.setBackgroundResource(R.drawable.game_screen_tab_item_background_active);
                tabPlayers.setTextColor(getResources().getColor(R.color.light_gray));
                tabPlayers.setBackgroundResource(R.drawable.game_screen_tab_item_background_inactive);
                setIndicatorPadding();

                viewPager.setCurrentItem(0, true);
                break;
            case R.id.tab_players:
                tabPlayers.setTextColor(getResources().getColor(R.color.black));
                tabPlayers.setBackgroundResource(R.drawable.game_screen_tab_item_background_active);
                tabDetails.setTextColor(getResources().getColor(R.color.light_gray));
                tabDetails.setBackgroundResource(R.drawable.game_screen_tab_item_background_inactive);
                setIndicatorPadding();

                viewPager.setCurrentItem(1, true);
                break;
        }
    }

    private void setIndicatorPadding() {
        int pixels_20 = BitmapFunctions.convertDpToPixel(20, getActivity());

        tabDetails.setPadding(0, pixels_20 + 1, 0, pixels_20 + 1);
        tabPlayers.setPadding(0, pixels_20 + 1, 0, pixels_20 + 1);

    }

    /**
     * This method will be invoked when the current page is scrolled, either as part
     * of a programmatically initiated smooth scroll or a user initiated touch scroll.
     *
     * @param position             Position index of the first page currently being displayed.
     *                             Page position+1 will be visible if positionOffset is nonzero.
     * @param positionOffset       Value from [0, 1) indicating the offset from the page at position.
     * @param positionOffsetPixels Value in pixels indicating the offset from position.
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * This method will be invoked when a new page becomes selected. Animation is not
     * necessarily complete.
     *
     * @param position Position index of the new selected page.
     */
    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                //Select the Game Details Tab
                onClick(tabDetails);
                break;
            case 1:
                //Select the Game Players
                onClick(tabPlayers);
                break;
        }
    }

    /**
     * Called when the scroll state changes. Useful for discovering when the user
     * begins dragging, when the pager is automatically settling to the current page,
     * or when it is fully stopped/idle.
     *
     * @param state The new scroll state.
     * @see android.support.v4.view.ViewPager#SCROLL_STATE_IDLE
     * @see android.support.v4.view.ViewPager#SCROLL_STATE_DRAGGING
     * @see android.support.v4.view.ViewPager#SCROLL_STATE_SETTLING
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class ViewAdapter extends FragmentPagerAdapter {

        private final FragmentManager manager;
        private ScreenPlayersFragment screenPlayersFragment;
        private ScreenDetailsFragment screenDetailsFragment;

        public ViewAdapter(FragmentManager manager) {
            super(manager);
            this.manager = manager;

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
                    if (screenDetailsFragment == null)
                        screenDetailsFragment = new ScreenDetailsFragment();
                    return screenDetailsFragment;
                case 1:
                    if (screenPlayersFragment == null)
                        screenPlayersFragment = new ScreenPlayersFragment();
                    return screenPlayersFragment;
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
