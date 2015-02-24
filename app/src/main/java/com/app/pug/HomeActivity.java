package com.app.pug;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.app.pug.adapters.ScreenPagerAdapter;
import com.app.pug.fragments.NavigationDrawerFragment;
import com.app.pug.framework.Act;
import com.app.pug.models.DrawerItem;
import com.app.pug.util.DrawerAdapter;
import com.app.pug.utils.BitmapFunctions;
import com.app.pug.utils.Utils;

import java.util.ArrayList;


public class HomeActivity extends Act {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private Toolbar tlb;

    private LinearLayout tabContainer;
    private ViewPager homeViewPager;
    private DrawerLayout drawerLayout;

    /**
     * Number of Fragments
     */
    public static final int SCREEN_COUNT = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//
        tlb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tlb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        initializeNavigationDrawer();

        homeViewPager = (ViewPager) findViewById(R.id.home_viewPager);
        ScreenPagerAdapter adapter = new ScreenPagerAdapter(getSupportFragmentManager());
        homeViewPager.setAdapter(adapter);
        homeViewPager.setOnPageChangeListener(pageChangeListener);
        homeViewPager.setOffscreenPageLimit(SCREEN_COUNT - 1);

        initializeTabs();
        changeTabWithFragment(0);     // or the restored position after screen rotation (if available)
    }


/*    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.container, GameScreenFragment.newInstance()).commit();
        *//*
        fragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance())
                .commit();
        *//*
    }*/

    /**
     * Lookup and initialize the navigation drawer and its views
     */
    public void initializeNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        RelativeLayout drawerRoot = (RelativeLayout) findViewById(R.id.navigation_drawer_content);
        final ImageView imageDrawer = (ImageView) findViewById(R.id.imageDrawer);

        // load and display the image off the UI thread
        Utils.runTask(this, new Utils.ResultTask<Bitmap>() {
            @Override
            public Bitmap run() throws Exception {
                int size = BitmapFunctions.convertDpToPixel(getResources().getDimension(R.dimen.dimen_120), getApplicationContext());
                return BitmapFunctions.getRoundedShape(R.drawable.ic_background_, getApplicationContext(), size, size);
            }

            @Override
            public void onFinish(Bitmap result) {
                if (result != null) {
                    imageDrawer.setImageBitmap(result);
                }
            }
        }, "LoadImageDrawerTask");

        ListView drawerListView = (ListView) drawerRoot.findViewById(R.id.listDrawer);
        ArrayList<DrawerItem> drawerItemsList = new ArrayList<DrawerItem>();
        drawerItemsList.add(new DrawerItem(R.drawable.ic_item_profile, "View Profile", false, null));
        drawerItemsList.add(new DrawerItem(R.drawable.ic_item_my_game, "My Game", true, new String[]{"Tournaments", "Teams", "Skills", "Training"}));
        drawerItemsList.add(new DrawerItem(R.drawable.ic_item_gallery, "Gallery", false, null));
        drawerItemsList.add(new DrawerItem(R.drawable.ic_item_inbox, "Inbox", false, null));

        DrawerAdapter adapter = new DrawerAdapter(this, R.layout.drawer_item_layout, R.layout.drawer_expanded_item_layout, drawerItemsList);
        drawerListView.setAdapter(adapter);
    }

    public void initializeTabs() {
        tabContainer = (LinearLayout) findViewById(R.id.navigation_tab_container);
        for (int i = 0; i < tabContainer.getChildCount(); i++) {
            final View imageView = tabContainer.getChildAt(i);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = tabContainer.indexOfChild(imageView);
                    changeTabWithFragment(position);
                }
            });
        }
    }

    private void changeTabIcon(int position) {
        for (int i = 0; i < tabContainer.getChildCount(); i++) {
            ImageView imageView = (ImageView) tabContainer.getChildAt(i);
            imageView.setImageResource(getImageResource(i, i == position));
        }
    }

    private void changeTabWithFragment(int position) {
        homeViewPager.setCurrentItem(position, true);
        changeTabIcon(position);
    }

    private int getImageResource(int position, boolean active) {
        switch (position) {
            case 0: return active ? R.drawable.tab_icon_home_active : R.drawable.tab_icon_home_inactive;
            case 1: return active ? R.drawable.tab_icon_game_active : R.drawable.tab_icon_game_inactive;
            case 2: return active ? R.drawable.tab_icon_achievements_active : R.drawable.tab_icon_achievements_inactive;
            case 3: return active ? R.drawable.tab_icon_profile_active : R.drawable.tab_icon_profile_inactive;
            default : return active ? R.drawable.tab_icon_settings_active : R.drawable.tab_icon_settings_inactive;
        }
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override public void onPageScrollStateChanged(int arg0) {}
        @Override public void onPageScrolled(int arg0, float arg1, int arg2) {}

        @Override
        public void onPageSelected(int position) {
            changeTabWithFragment(position);
        }
    };


    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    /**
     * Remove each screen as the user clicks the back button
     */
    @Override
    public void onBackPressed() {
        /**
         * Close the drawer first
         */
        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.START);
            return;
        }

        int currentItem = homeViewPager.getCurrentItem();
        if (currentItem == 0) {
            super.onBackPressed();
        } else {
            homeViewPager.setCurrentItem(currentItem - 1);
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            //getMenuInflater().inflate(R.menu.home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
