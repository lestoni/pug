package com.app.pug.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.pug.fragments.ScreenFixtureFragment;

public class TournamentPagerAdapter extends FragmentPagerAdapter {
    public TournamentPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ScreenFixtureFragment.newInstance();

            default:
                return ScreenFixtureFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 1;
    }
}
