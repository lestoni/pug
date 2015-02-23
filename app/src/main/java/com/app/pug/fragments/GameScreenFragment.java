package com.app.pug.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.pug.R;
import com.app.pug.framework.Screen;
import com.app.pug.utils.BitmapFunctions;

/**
 * Created by MATIVO-PC on 2/23/2015, 2:04 PM
 * Project:  PUG
 * Package Name: com.app.pug.fragments
 */
public class GameScreenFragment extends Screen implements View.OnClickListener {
    private View v;
    private final static String TAG = "GameScreenFragment";
    private TextView find_open_games_button, create_new_game_button;
    private FragmentManager mFragmentMgr;
    private Screen screen;

    public static GameScreenFragment newInstance() {
        return new GameScreenFragment();
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
        mFragmentMgr = getChildFragmentManager();
        v = inflater.inflate(R.layout.screen_find_create_game, container, false);

        toggleFindCreate();
        return v;
    }

    private void toggleFindCreate() {
        if (create_new_game_button == null)
            create_new_game_button = (TextView) v.findViewById(R.id.create_new_game_button);
        if (find_open_games_button == null)
            find_open_games_button = (TextView) v.findViewById(R.id.find_open_games_button);

        create_new_game_button.setOnClickListener(this);
        find_open_games_button.setOnClickListener(this);

        /**
         * I think the default view should be Find Open Games.
         */
        launchFindOpenGameFragment();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_new_game_button:
                /**
                 * Un select find_open_games_button by changing its background, later on we should add an animation.
                 */
                find_open_games_button.setBackgroundResource(R.drawable.game_button_left_normal);
                create_new_game_button.setBackgroundResource(R.drawable.game_button_right_selected);
                setButtonPadding();

                launchCreateGameFragment();
                break;
            case R.id.find_open_games_button:
                /**
                 * Un select create_new_game_button by changing its background, later on we should add an animation.
                 */
                create_new_game_button.setBackgroundResource(R.drawable.game_button_right_normal);
                find_open_games_button.setBackgroundResource(R.drawable.game_button_left_selected);
                setButtonPadding();

                launchFindOpenGameFragment();
                break;
        }
    }

    private void setButtonPadding() {
        int pixels_10 = BitmapFunctions.convertDpToPixel(10, getActivity());
        int pixels_15 = BitmapFunctions.convertDpToPixel(15, getActivity());

        find_open_games_button.setPadding(pixels_10, pixels_15 + 1, pixels_10, pixels_15 + 1);
        create_new_game_button.setPadding(pixels_10, pixels_15 + 1, pixels_10, pixels_15 + 1);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        try {
            mFragmentMgr = getFragmentManager();
            FragmentTransaction mTransaction = mFragmentMgr.beginTransaction();
            mTransaction.remove(screen);
            mTransaction.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void launchFindOpenGameFragment() {
        screen = FindOpenGameFragment.newInstance();
        mFragmentMgr.beginTransaction()
                .replace(R.id.game_screen_container, screen)
                .commit();
    }

    private void launchCreateGameFragment() {
        screen = CreateGameFragment.newInstance();
        mFragmentMgr.beginTransaction()
                .replace(R.id.game_screen_container, screen)
                .commit();
    }
}
