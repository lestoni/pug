package com.app.pug;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.app.pug.fragments.GameFragment;
import com.app.pug.framework.Act;

/**
 * Created by MATIVO-PC on 2/24/2015, 12:18 PM
 * Project:  PUG
 * Package Name: com.app.pug
 */
public class GameActivity extends Act {
    private Toolbar tlb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tlb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tlb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.gameContent, GameFragment.newInstance())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //implement action

                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
