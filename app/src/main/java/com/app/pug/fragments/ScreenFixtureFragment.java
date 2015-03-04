package com.app.pug.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.app.pug.R;
import com.app.pug.framework.Screen;
import com.app.pug.models.FixtureItem;
import com.app.pug.models.FixtureModel;
import com.app.pug.util.FixtureExpandableAdapter;

import java.util.ArrayList;

/**
 * Created by MATIVO-PC on 2/23/2015, 2:04 PM
 * Project:  PUG
 * Package Name: com.app.pug.fragments
 */
public class ScreenFixtureFragment extends Screen {
    private View v;
    private final static String TAG = "ScreenFixtureFragment";

    public static ScreenFixtureFragment newInstance() {
        return new ScreenFixtureFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,   Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.screen_fixture, container, false);

        initList();
        return v;
    }

    private void initList() {
        FixtureItem f1 = new FixtureItem("Team Hawk", "Shark Attack", "5:00 PM", "Captain Rivera Playground", R.drawable.ic_team_3, R.drawable.ic_team_4, 7, 3);
        FixtureModel m1 = new FixtureModel("Tomorrow", f1);

        FixtureItem f2 = new FixtureItem("Spinners", "Team Hawk", "2:30 PM", "174th Street Playground", R.drawable.ic_team_1, R.drawable.ic_team_3, 10, 0);
        FixtureItem f3 = new FixtureItem("Team Uptown", "Team Downtown", "5:00 PM", "World's fair Playground", R.drawable.ic_team_1, R.drawable.ic_team_2, 8, 2);
        FixtureModel m2 = new FixtureModel("March 21, 2015", f2, f3);

        ArrayList<FixtureModel> models = new ArrayList<>();
        models.add(m1);
        models.add(m2);

        FixtureExpandableAdapter ad = new FixtureExpandableAdapter(getActivity(), models);
        ExpandableListView list = (ExpandableListView) v.findViewById(R.id.listFixture);
        list.setAdapter(ad);

        int count = ad.getGroupCount();
        for(int i = 0; i < count; i++) {
            list.expandGroup(i);
        }

    }
}
