package com.app.pug.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.pug.R;
import com.app.pug.framework.Screen;
import com.app.pug.models.OpenGameItem;
import com.app.pug.util.FindOpenListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MATIVO-PC on 2/23/2015, 3:44 PM
 * Project:  PUG
 * Package Name: com.app.pug.fragments
 */
public class FindOpenGameFragment extends Screen {
    private View v;
    private final static String TAG = "FindOpenGameFragment";
    private ListView findGameScrollView;
    private List<OpenGameItem> items = new ArrayList<OpenGameItem>();
    private FindOpenListAdapter adap;

    public static FindOpenGameFragment newInstance() {
        return new FindOpenGameFragment();
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
        v = inflater.inflate(R.layout.screen_find_open_game, container, false);

        init();
        return v;
    }

    private void init() {
        findGameScrollView = (ListView) v.findViewById(R.id.findGameScrollView);

        items.add(new OpenGameItem(0, "17th Street Playground. ", "E174 St. & Bronx River Ave.", "17:00 March 01", 5, 7, 1, "Available"));
        items.add(new OpenGameItem(0, "Capt. Rivera Playground. ", "Forest Ave. & E156 St.", "11:00 April 19", 7, 10, 5, "Full"));
        items.add(new OpenGameItem(0, "Adventure Playground", "Berkeley Marina, UC Berkeley", "14:30 March 20", 5, 5, 7, "Available"));
        items.add(new OpenGameItem(0, "Imagination Playground", " Burling Slip, Seaport in Lower Manhattan", "18:45 Feb 28", 2, 3, 3, "Available"));
        items.add(new OpenGameItem(0, "Clemyjontri Playground", "Across river, Clemyjontri Park", "16:45 Feb 26, 2015", 1, 15, 9, "Full"));

        items.add(new OpenGameItem(0, "Woodland Discovery Playground", " Beale Street, Shelby Farms Park", "11:00 March 18", 1, 15, 9, "Full"));
        items.add(new OpenGameItem(0, "Harmony Park Musical Playground ", "Downtown Moab, near Mill Creek Parkway", "13:30 April 10", 1, 15, 9, "Full"));

        adap = new FindOpenListAdapter(getActivity(), R.layout.open_game_item_layout, items);
        findGameScrollView.setAdapter(adap);
    }
}
