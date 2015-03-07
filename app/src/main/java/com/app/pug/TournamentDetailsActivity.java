package com.app.pug;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.pug.framework.Act;
import com.app.pug.models.FixtureItem;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by MATIVO-PC on 2/24/2015, 12:18 PM
 * Project:  PUG
 * Package Name: com.app.pug
 */
public class TournamentDetailsActivity extends Act {
    private Toolbar tlb;
    private FixtureItem i;
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_tournaments_details);

        createMapView();
        addMarker();

        try {
            if(getIntent().hasExtra("fixture")){
                i = (FixtureItem) getIntent().getSerializableExtra("fixture");
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
}
