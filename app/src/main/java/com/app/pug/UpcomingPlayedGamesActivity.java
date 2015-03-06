package com.app.pug;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.pug.adapters.ScreenPagerAdapter;
import com.app.pug.fragments.PlayedGamesFragment;
import com.app.pug.fragments.UpcomingGamesFragment;

public class UpcomingPlayedGamesActivity extends ActionBarActivity {

    private static final int SCREEN_COUNT = 2;

    private LinearLayout tabContainer;
    private TextView upcomingGamesTab;
    private TextView playedGamesTab;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_played_games);

        tabContainer = (LinearLayout) findViewById(R.id.tab_container);

        upcomingGamesTab = (TextView) findViewById(R.id.upcoming_games_tab);
        upcomingGamesTab.setOnClickListener(tabListener);
        playedGamesTab = (TextView) findViewById(R.id.played_games_tab);
        playedGamesTab.setOnClickListener(tabListener);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_previous);

        viewPager = (ViewPager) findViewById(R.id.upcoming_viewpager);
        viewPager.setAdapter(new UpcomingScreenPagerAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override public void onPageScrollStateChanged(int state) {}

            @Override
            public void onPageSelected(int position) {
                changeTabBackground(position);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener tabListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = tabContainer.indexOfChild(v);
            changeTabBackground(position);
        }
    };

    private void changeTabBackground(int position) {
        for (int i = 0; i < tabContainer.getChildCount(); i++) {
            View v = tabContainer.getChildAt(i);
            int top = v.getPaddingTop();
            int bottom = v.getPaddingBottom();
            int left = v.getPaddingLeft();
            int right = v.getPaddingRight();

            if (i == position) {
                v.setBackgroundResource(R.drawable.tab_indicator_selected);
            } else {
                v.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            }
            v.setPadding(left, top, right, bottom);
        }
        viewPager.setCurrentItem(position);
    }

    private class UpcomingScreenPagerAdapter extends ScreenPagerAdapter {

        public UpcomingScreenPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return new UpcomingGamesFragment();
//            switch (position) {
//                case 0: return new UpcomingGamesFragment();
//                default : return new PlayedGamesFragment();
//            }
        }

        @Override
        public int getCount() {
            return SCREEN_COUNT;
        }
    }
}
