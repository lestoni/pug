package com.app.pug.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.pug.R;
import com.app.pug.framework.Screen;

/**
 * Created by MATIVO-PC on 2/23/2015, 2:04 PM
 * Project:  PUG
 * Package Name: com.app.pug.fragments
 */
public class GameStatsFragment extends Screen {
    private View v;
    private final static String TAG = "GameStatsFragment";

    public static GameStatsFragment newInstance() {
        return new GameStatsFragment();
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
        v = inflater.inflate(R.layout.screen_game_details, container, false);

        return v;
    }
}
