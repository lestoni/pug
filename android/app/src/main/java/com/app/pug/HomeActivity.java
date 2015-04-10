package com.app.pug;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.pug.adapters.DrawerExpandableAdapter;
import com.app.pug.adapters.ScreenPagerAdapter;
import com.app.pug.framework.Act;
import com.app.pug.models.DrawerItem;
import com.app.pug.utils.BitmapFunctions;
import com.app.pug.utils.Utils;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;

public class HomeActivity extends Act implements ExpandableListView.OnChildClickListener, AdapterView.OnItemClickListener {

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private Toolbar tlb;

    private LinearLayout tabContainer;
    private ViewPager homeViewPager;
    private DrawerLayout drawerLayout;

    private boolean mUserLearnedDrawer;
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    private View drawerContainer;
    private android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;

    private RelativeLayout drawerContent;
    private RelativeLayout secondaryDrawerScreen;
    private static final int ANIMATION_DURATION = 250;

    /**
     * Number of Fragments
     */
    public static final int SCREEN_COUNT = 5;
    private boolean doubleBackToExitPressedOnce;
    private ArrayList<DrawerItem> drawerItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        initializeNavigationDrawer();

        homeViewPager = (ViewPager) findViewById(R.id.home_viewPager);
        ScreenPagerAdapter adapter = new ScreenPagerAdapter(getSupportFragmentManager());
        homeViewPager.setAdapter(adapter);
        homeViewPager.setOnPageChangeListener(pageChangeListener);
        homeViewPager.setOffscreenPageLimit(SCREEN_COUNT - 1);

        initializeTabs();

        changeTabWithFragment(0);     // or the restored position after screen rotation (if available)

        initDrawer();
    }

    public void openDrawer() {
        drawerLayout.openDrawer(drawerContainer);
    }

    public Toolbar getToolBar() {
        return tlb;
    }

    /**
     * Set the current screen of the view pager
     * @param position
     */
    public void setCurrentItem(int position) {
        if (position < 0 || position >= SCREEN_COUNT) return;
        homeViewPager.setCurrentItem(position);
    }

    /**
     * Manage the Navigation Drawer
     * We will maintain a preference, so that the navigation opens automatically the first time.
     */
    private void initDrawer() {
        drawerContainer = findViewById(R.id.navigation_drawer_root);

        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(
                this,                    /* host Activity */
                drawerLayout,                    /* DrawerLayout object */
                tlb,                            /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
                closeSecondaryDrawerScreen();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(HomeActivity.this);
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer) {
            drawerLayout.openDrawer(drawerContainer);
        }

        // Defer code dependent on restoration of previous instance state.
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        drawerLayout.setDrawerListener(mDrawerToggle);
    }

    /**
     * Lookup and initialize the navigation drawer and its views
     */
    public void initializeNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final RelativeLayout drawerRoot = (RelativeLayout) findViewById(R.id.navigation_drawer_root);
        final ImageView imageDrawer = (ImageView) findViewById(R.id.imageDrawer);

        // load and display the image off the UI thread
        Utils.runTask(this, new Utils.ResultTask<Bitmap>() {
            @Override
            public Bitmap run() throws Exception {
                int size = BitmapFunctions.convertDpToPixel(getResources().getDimension(R.dimen.dimen_120), getApplicationContext());
                return BitmapFunctions.getRoundedShape(R.drawable.ic_list_item_4, getApplicationContext(), size, size);
            }

            @Override
            public void onFinish(Bitmap result) {
                if (result != null) {
                    imageDrawer.setImageBitmap(result);
                }
            }
        }, "LoadImageDrawerTask");

        ExpandableListView drawerListView = (ExpandableListView) drawerRoot.findViewById(R.id.listDrawer);
        drawerItemsList = new ArrayList<>();
        drawerItemsList.add(new DrawerItem(R.drawable.ic_item_my_game, "My Plays", new String[]{"Tournaments", "Teams", "Skills", "Training"}, 0, DrawerItem.TYPE.EXPANDABLE));
        drawerItemsList.add(new DrawerItem(R.drawable.ic_item_gallery, "Gallery", null, 0, DrawerItem.TYPE.GALLERY));
        drawerItemsList.add(new DrawerItem(R.drawable.ic_item_inbox, "Inbox", null, 2, DrawerItem.TYPE.NOTIFICATION));
        drawerItemsList.add(new DrawerItem(R.drawable.ic_notifications, "Notifications", null, 21, DrawerItem.TYPE.NOTIFICATION));
        drawerItemsList.add(new DrawerItem(R.drawable.ic_item_profile, "Friends", null, 0, DrawerItem.TYPE.NORMAL));

        DrawerExpandableAdapter adapter = new DrawerExpandableAdapter(this, R.layout.drawer_sub_item_layout, drawerItemsList);
        drawerListView.setAdapter(adapter);

        drawerListView.setOnChildClickListener(this);
        drawerListView.setOnItemClickListener(this);

        // initialize the inner secondary drawer screen
        secondaryDrawerScreen = (RelativeLayout) findViewById(R.id.drawer_secondary_screen_container);

        final LinearLayout trueBalerToggle = (LinearLayout) findViewById(R.id.drawer_true_baller_container);
        drawerContent = (RelativeLayout) findViewById(R.id.navigation_drawer_content);
        trueBalerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openSecondaryDrawerScreen();
            }
        });

        RelativeLayout drawerLogoContainer = (RelativeLayout) findViewById(R.id.drawer_logo_container);
        drawerLogoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSecondaryDrawerScreen();
            }
        });
    }

    /**
     * Opens the secondary screen (drawer_secondary_screen.xml)
     */
    private void openSecondaryDrawerScreen() {
        boolean firstTime = secondaryDrawerScreen.getVisibility() != View.VISIBLE;
        if (firstTime) secondaryDrawerScreen.setVisibility(View.VISIBLE); // set the screen to be visible
        int width = drawerContent.getWidth();                              // get the drawer content width
        float x = secondaryDrawerScreen.getX();                             // get the secondary screen's x position
        ViewHelper.setX(secondaryDrawerScreen, x - width);                  // set it so that it is exactly to the left of the navigation drawer content

        /*
            Animate the secondary drawer.
            If it is the first time the secondary drawer was open, avoid negative over-translation on the x axis
         */
        ObjectAnimator
                .ofFloat(secondaryDrawerScreen, "x", x - (firstTime? width : 0) , (firstTime ? x : x + width))
                .setDuration(ANIMATION_DURATION)
                .start();
        /*
            Hide the navigation drawer content to the right
         */
        ObjectAnimator
                .ofFloat(drawerContent, "x", width)
                .setDuration(ANIMATION_DURATION)
                .start();
    }

    /**
     * Closes the secondary drawer screen (if it open)
     */
    private void closeSecondaryDrawerScreen() {
        int width = drawerContent.getWidth();
        float secondaryDrawerScreenX = secondaryDrawerScreen.getX();
        float drawerContentX = drawerContent.getX();
        // avoid animation if the navigation drawer's x position is already at 0 (or near 0)
        if (drawerContentX < 1 && drawerContentX > -1) return;
        // hide the content drawer screen (move it left to its original position)
        ObjectAnimator
                .ofFloat(secondaryDrawerScreen, "x", secondaryDrawerScreenX, secondaryDrawerScreenX - width)
                .setDuration(ANIMATION_DURATION)
                .start();
        // hide the secondary drawer screen (move it left to the side of the display) but don't hide it
        ObjectAnimator
                .ofFloat(drawerContent, "x", drawerContentX, drawerContentX - width)
                .setDuration(ANIMATION_DURATION)
                .start();
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
            case 0:
                return active ? R.drawable.tab_icon_home_active : R.drawable.tab_icon_home_inactive;
            case 1:
                return active ? R.drawable.tab_icon_game_active : R.drawable.tab_icon_game_inactive;
            case 2:
                return active ? R.drawable.tab_icon_achievements_active : R.drawable.tab_icon_achievements_inactive;
            case 3:
                return active ? R.drawable.tab_icon_profile_active : R.drawable.tab_icon_profile_inactive;
            default:
                return active ? R.drawable.tab_icon_settings_active : R.drawable.tab_icon_settings_inactive;
        }
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

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
            //How about we implement a double click Back Button to close?
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        } else {
            homeViewPager.setCurrentItem(currentItem - 1);
        }
    }

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

    /**
     * Callback method to be invoked when a child in this expandable list has
     * been clicked.
     *
     * @param parent        The ExpandableListView where the click happened
     * @param v             The view within the expandable list/ListView that was clicked
     * @param groupPosition The group position that contains the child that
     *                      was clicked
     * @param childPosition The child position within the group
     * @param id            The row id of the child that was clicked
     * @return True if the click was handled
     */
    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        DrawerExpandableAdapter.DrawerChildItemHolder dci = (DrawerExpandableAdapter.DrawerChildItemHolder) v.getTag();
        if (dci != null) {
            String s = dci.itemName.getText().toString();
            if (!s.equals("")) {
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p/>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DrawerExpandableAdapter.DrawerItemHolder d = (DrawerExpandableAdapter.DrawerItemHolder) view.getTag();
        if(d != null) {
            String s = d.itemName.getText().toString();
            if(!s.equals("")){
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
