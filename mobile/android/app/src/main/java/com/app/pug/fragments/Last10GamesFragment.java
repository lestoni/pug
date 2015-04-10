package com.app.pug.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.pug.R;
import com.app.pug.adapters.GameSkillAdapter;
import com.app.pug.models.GameSkillAdapterItem;

import java.util.ArrayList;

public class Last10GamesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.screen_last_10_games, container, false);
        ListView progressListView = (ListView) root.findViewById(R.id.last_10_games_listview);

        ArrayList<GameSkillAdapterItem> items = new ArrayList<GameSkillAdapterItem>();

        items.add(new GameSkillAdapterItem("Basketball IQ", 66));
        items.add(new GameSkillAdapterItem("Ball Handling", 72));
        items.add(new GameSkillAdapterItem("Jump Shot", 56));
        items.add(new GameSkillAdapterItem("Passing Assist", 63));
        items.add(new GameSkillAdapterItem("Offense", 45));
        items.add(new GameSkillAdapterItem("Defense", 65));
        items.add(new GameSkillAdapterItem("Rebound", 66));
        items.add(new GameSkillAdapterItem("Moves", 58));

        GameSkillAdapter adapter = new GameSkillAdapter(getActivity(), R.layout.game_skill_item_layout, items);
        progressListView.setAdapter(adapter);
        return root;
    }
}
