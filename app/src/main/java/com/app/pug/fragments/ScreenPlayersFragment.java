package com.app.pug.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.pug.R;
import com.app.pug.adapters.PlayerListAdapter;
import com.app.pug.framework.Screen;
import com.app.pug.models.PlayerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MATIVO-PC on 2/23/2015, 2:04 PM
 * Project:  PUG
 * Package Name: com.app.pug.fragments
 */
public class ScreenPlayersFragment extends Screen {
    private View v;
    private final static String TAG = "ScreenPlayersFragment";
    private ListView list;
    private List<PlayerItem> items = new ArrayList<PlayerItem>();
    private PlayerListAdapter adap;

    public static ScreenPlayersFragment newInstance() {
        return new ScreenPlayersFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.screen_game_players, container, false);

        list = (ListView) v.findViewById(R.id.players_list);

        init();

        return v;
    }

    private void init() {
        items.add(new PlayerItem(0, "Johnson Williams", "Point Guard", "joined", "30 mins"));
        items.add(new PlayerItem(0, "Christina Jones", "Small Forward", "joined", "1 hr"));
        items.add(new PlayerItem(0, "Keisha Madison", "Shooting Guard", "joined", "2 hr"));
        items.add(new PlayerItem(0, "Fredrick Hammond", "Small Forward", "joined", "3 hr"));

        adap = new PlayerListAdapter(getActivity(), R.layout.screen_game_player_item, items);
        list.setAdapter(adap);
    }
}
