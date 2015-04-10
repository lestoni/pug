package com.app.pug.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.app.pug.HomeActivity;
import com.app.pug.R;

/**
 * Temporary screen for non available screens.
 */
public class EmptyFragment extends Fragment {

    private Toolbar toolBar;
    private View v;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =  inflater.inflate(R.layout.screen_empty, container, false);

        toolBar = (Toolbar) v.findViewById(R.id.toolbar);
        ((HomeActivity)getActivity()).setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.drawable.ic_drawer);
        toolBar.setTitle(null);
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
}
