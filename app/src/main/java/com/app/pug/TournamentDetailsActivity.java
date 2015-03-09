package com.app.pug;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.fab.ObservableScrollView;
import com.app.fab.ScrollDirectionListener;
import com.app.fab.ScrollViewScrollDetector;
import com.app.pug.adapters.TournamentCommentsListAdapter;
import com.app.pug.framework.Act;
import com.app.pug.models.CommentsModel;
import com.app.pug.models.FixtureItem;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MATIVO-PC on 2/24/2015, 12:18 PM
 * Project:  PUG
 * Package Name: com.app.pug
 */
public class TournamentDetailsActivity extends Act {
    private Toolbar tlb;
    private FixtureItem i;
    GoogleMap googleMap;

    private TextView tTeam1, tTeam2, tTime, tDate, tPlayground, tJoined;
    private TextView bRequestJoined;
    private ImageView iTeam1, iTeam2;
    private ExpandableListView list;
    private TournamentCommentsListAdapter adp;
    private int mScrollThreshold;
    private ObservableScrollView scrollView;
    private View vToolBar;
    private int topPadding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_tournaments_details);

        createMapView();
        addMarker();

        init();

        try {
            if(getIntent().hasExtra("fixture")){
                i = (FixtureItem) getIntent().getSerializableExtra("fixture");
                initialiseFixtureDetails();
            }
        }catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        tlb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tlb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tlb.setNavigationIcon(R.drawable.ic_action_previous);
    }

    private  void init() {
        tTeam1 = (TextView) findViewById(R.id.textTeam1Details);
        tTeam2 = (TextView) findViewById(R.id.textTeam2Details);
        tTime = (TextView) findViewById(R.id.textTimeDetails);
        tDate = (TextView) findViewById(R.id.textDateDetails);
        tPlayground = (TextView) findViewById(R.id.textPlaygroundDetails);
        tJoined = (TextView) findViewById(R.id.textJoinedDetails);

        bRequestJoined = (TextView) findViewById(R.id.btnRequestJoineDetails);

        iTeam1 = (ImageView) findViewById(R.id.imageTeam1Details);
        iTeam2 = (ImageView) findViewById(R.id.imageTeam2Details);

        list = (ExpandableListView)findViewById(R.id.listTournDetails);

        mScrollThreshold = getResources().getDimensionPixelOffset(R.dimen.scroll_threshold);
        scrollView = (ObservableScrollView) findViewById(R.id.scroll);
        vToolBar = findViewById(R.id.toolbarLayout);

        //topPadding = getResources().getDimensionPixelOffset(R.attr.actionBarSize);
    }

    private int getActionBarHeight() {
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            return TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }
        return 60;
    }

    private void initialiseFixtureDetails() {
        if(i != null){
            tTeam1.setText(i.getTeam1());
            tTeam2.setText(i.getTeam2());
            tTime.setText(i.getTime());
            tPlayground.setText(i.getPlayground());
            tJoined.setText(getHtml(i.getNumJoined() + "", i.getNumSpotsLeft() + ""));

            iTeam1.setImageResource(i.getIcon_team_1());
            iTeam2.setImageResource(i.getIcon_team_2());
        }

        List items = new ArrayList();
        List<CommentsModel> commentsModels = new ArrayList<CommentsModel>();
        commentsModels.add(new CommentsModel("Natasha", "Are we still wearing the normal blue for Team Hawk?", R.drawable.ic_list_item_1));

        items.add(commentsModels);
        adp = new TournamentCommentsListAdapter(this, items);
        list.setAdapter(adp);

        int count = adp.getGroupCount();
        for(int i = 0; i < count; i++) {
            list.expandGroup(i);
        }

        manageScroll();
    }

    private void manageScroll() {

        MScrollDetector scrollDetector = new MScrollDetector();
        scrollDetector.setListener(new ScrollDirectionListener() {
            @Override
            public void onScrollDown() {
                /*
                    Show the ActionBar here
                 */
                getSupportActionBar().show();
                scrollView.setPadding(0, getActionBarHeight(), 0, 0);
            }

            @Override
            public void onScrollUp() {
                /**
                 * Hide the actionBar
                 */
                getSupportActionBar().hide();
                scrollView.setPadding(0, 0, 0, 0);
            }
        });
        scrollDetector.setScrollThreshold(mScrollThreshold);
        scrollView.setOnScrollChangedListener(scrollDetector);
    }

    private android.text.Spanned getHtml(String joined, String spotsLeft) {
        String html = "<font color=\"#85d2c5\">"+joined+" joined</font> ("+spotsLeft+" spots left)";
        return Html.fromHtml(html);
    }

    private void createMapView(){
        /**
         * Catch the null pointer exception that
         * may be thrown when initialising the map
         */
        try {
            if(null == googleMap){
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                        R.id.map)).getMap();

                /**
                 * If the map is still null after attempted initialisation,
                 * show an error to the user
                 */
                if(null == googleMap) {
                    Toast.makeText(getApplicationContext(),
                            "Error creating map", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }
    }

    private void addMarker(){

        /** Make sure that the map has been initialised **/
        if(null != googleMap){
            googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(40.818074, -73.906815))
                            .title("Marker")
                            .draggable(true)
            );
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(40.818074, -73.906815), 13);
			googleMap.animateCamera(update);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //implement action

                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MScrollDetector extends ScrollViewScrollDetector {
        private ScrollDirectionListener mListener;

        private void setListener(ScrollDirectionListener scrollDirectionListener) {
            mListener = scrollDirectionListener;
        }

        @Override
        public void onScrollDown() {
            if (mListener != null) {
                mListener.onScrollDown();
            }
        }

        @Override
        public void onScrollUp() {
            if (mListener != null) {
                mListener.onScrollUp();
            }
        }
    }
}
