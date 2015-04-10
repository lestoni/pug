package com.app.pug.fragments;

import android.os.Bundle;
import android.support.v7.widget.CardView;
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
public class HomePagerFragment extends Screen {
    private View v;

    private final static String TAG = "ScreenDetailsFragment";

    public static HomePagerFragment newInstance(int position) {
        Bundle bun = new Bundle();
        bun.putInt("background", position);
        HomePagerFragment home = new HomePagerFragment();
        home.setArguments(bun);
        return home;
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
        v = inflater.inflate(R.layout.screen_home_pager_item, container, false);

        int background = getArguments().getInt("background");
        CardView root = (CardView) v;
        int back = getBackground(background);

        root.setCardBackgroundColor(back);
        v.findViewById(R.id.pagerRoot).setBackgroundColor(back);
        return v;
    }

    public int getBackground(int pos) {
        int position = pos + 1;
        int mod = position % 3;

        int color = (R.color.pager_1);
        if (mod == 1) {
            color = (R.color.pager_1);
        } else if (mod == 2) {
            color = (R.color.pager_2);
        } else if (mod == 0) {
            color = (R.color.pager_3);
        }
        return getResources().getColor(color);
    }
}
