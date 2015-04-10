package com.app.pug.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.app.pug.R;
import com.app.pug.adapters.UpcomingPlayedAdapter;
import com.app.pug.models.UpcomingPlayedItem;

import java.util.ArrayList;
import java.util.Random;

public class UpcomingGamesFragment extends Fragment {

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.screen_upcoming_games, container, false);
        listView = (ListView) root.findViewById(R.id.upcoming_screen_listview);
        ArrayList<UpcomingPlayedItem> playedItems = new ArrayList<UpcomingPlayedItem>();
        for (int i = 0; i < 10; i++) {
            UpcomingPlayedItem item = new UpcomingPlayedItem();
            item.dateTime = "March 6, 2015 at 3:40PM";
            item.location = "Captain Rivera Playground";
            int joined = new Random().nextInt(5) + 5;
            item.joined = joined;
            item.left = 10 - joined;
            for (int j = joined; j < 5; j++) {
                ImageView imageView = new ImageView(getActivity());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                int margin = (int) getResources().getDimension(R.dimen.dimen_10);
                params.setMargins(margin, margin, margin, margin);
                imageView.setImageResource(R.drawable.game_item_player_icon);
            }
            playedItems.add(item);
        }
        UpcomingPlayedAdapter adapter = new UpcomingPlayedAdapter(getActivity(), R.layout.upcoming_played_item_layout, playedItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        return root;
    }
}
