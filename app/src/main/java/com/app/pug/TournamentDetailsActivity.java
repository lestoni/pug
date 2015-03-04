package com.app.pug;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.app.pug.framework.Act;
import com.app.pug.models.FixtureItem;

/**
 * Created by MATIVO-PC on 2/24/2015, 12:18 PM
 * Project:  PUG
 * Package Name: com.app.pug
 */
public class TournamentDetailsActivity extends Act {
    private Toolbar tlb;
    private FixtureItem i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_tournaments_details);

        try {
            if(getIntent().hasExtra("fixture")){
                i = (FixtureItem) getIntent().getSerializableExtra("fixture");
            }
        }catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        tlb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tlb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tlb.setNavigationIcon(R.drawable.ic_action_previous);
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
