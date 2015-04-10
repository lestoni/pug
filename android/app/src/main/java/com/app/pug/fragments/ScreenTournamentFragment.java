package com.app.pug.fragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.app.pug.HomeActivity;
import com.app.pug.R;
import com.app.pug.adapters.TournamentPagerAdapter;
import com.app.pug.framework.Screen;

/**
 * Created by MATIVO-PC on 2/23/2015, 2:04 PM
 * Project:  PUG
 * Package Name: com.app.pug.fragments
 */
public class ScreenTournamentFragment extends Screen {
    private View v;
    private final static String TAG = "ScreenTournamentFragment";
    private Toolbar toolBar;
    private ViewPager viewPager;

    public static ScreenTournamentFragment newInstance() {
        return new ScreenTournamentFragment();
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
        v = inflater.inflate(R.layout.screen_tournaments, container, false);

        toolBar = (Toolbar) v.findViewById(R.id.toolbar);
        ((HomeActivity)getActivity()).setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.drawable.ic_drawer);
        toolBar.setTitle(null);

        initViewPager();

        return v;
    }

    private void initViewPager() {
        viewPager = (ViewPager) v.findViewById(R.id.homePagerFixture);
        TournamentPagerAdapter adapter = new TournamentPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        //viewPager.setOnPageChangeListener(pageChangeListener);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setCurrentItem(1);

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
}
