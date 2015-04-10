package com.app.pug.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.pug.HomeActivity;
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
    private Toolbar toolBar;

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

        toolBar = (Toolbar) v.findViewById(R.id.toolbar);
        ((HomeActivity)getActivity()).setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.drawable.ic_drawer);
        toolBar.setTitle(null);

        toggleFindCreate();
        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //implement action
                ((HomeActivity)getActivity()).openDrawer();
                return true;
        }
        return super.onOptionsItemSelected(item);
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

        boolean isFindButton = v.getId() == R.id.find_open_games_button;    // determine if find button was clicked

        // change text color accordingly
        find_open_games_button.setTextColor(getResources().getColor(isFindButton ? R.color.black : R.color.gray));
        create_new_game_button.setTextColor(getResources().getColor(isFindButton ? R.color.gray : R.color.black));

        // change backgrounds accordingly
        find_open_games_button.setBackgroundResource(isFindButton ? R.drawable.game_button_left_selected : R.drawable.game_button_left_normal);
        create_new_game_button.setBackgroundResource(isFindButton ? R.drawable.game_button_right_normal : R.drawable.game_button_right_selected);

        // change the icons accordingly
        find_open_games_button.setCompoundDrawablesWithIntrinsicBounds(isFindButton ?
                        R.drawable.game_button_left_selected_icon : R.drawable.game_button_left_normal_icon,
                0, 0, 0);

        create_new_game_button.setCompoundDrawablesWithIntrinsicBounds(isFindButton ?
                        R.drawable.game_button_right_normal_icon : R.drawable.game_button_right_selected_icon,
                0, 0, 0);

        setButtonPadding();
        boolean someValue = isFindButton ? launchFindOpenGameFragment() : launchCreateGameFragment();
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

    private boolean launchFindOpenGameFragment() {
        screen = FindOpenGameFragment.newInstance();
        mFragmentMgr.beginTransaction()
                .replace(R.id.game_screen_container, screen)
                .commit();
        return true;
    }

    private boolean launchCreateGameFragment() {
        screen = CreateGameFragment.newInstance();
        mFragmentMgr.beginTransaction()
                .replace(R.id.game_screen_container, screen)
                .commit();
        return true;
    }

}
