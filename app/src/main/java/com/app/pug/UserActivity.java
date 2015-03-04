package com.app.pug;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.app.pug.fragments.ScreenUserFollowingFragment;
import com.app.pug.fragments.ScreenUserNotFollowingFragment;
import com.app.pug.framework.Act;

/**
 * Created by MATIVO-PC on 2/24/2015, 12:18 PM
 * Project:  PUG
 * Package Name: com.app.pug
 */
public class UserActivity extends Act {
    private Toolbar tlb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        tlb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tlb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tlb.setNavigationIcon(R.drawable.ic_action_previous);

        FragmentManager fragmentManager = getSupportFragmentManager();

        int pos = getIntent().getIntExtra("pos", 1);

        if(pos == 1) {
            fragmentManager.beginTransaction()
                    .replace(R.id.gameContent, ScreenUserFollowingFragment.newInstance())
                    .commit();
        }else {
            fragmentManager.beginTransaction()
                    .replace(R.id.gameContent, ScreenUserNotFollowingFragment.newInstance())
                    .commit();
        }


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
