package com.app.pug;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import com.app.pug.models.DrawerItem;
import com.app.pug.utils.BitmapFunctions;
import com.app.pug.utils.DrawerAdapter;

import java.util.ArrayList;


public class LoginActivity extends ActionBarActivity {

    private ArrayList<DrawerItem> drawerItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        setContentView(R.layout.drawer_layout);

        cropProfileImage();
        testDrawer();
    }

    private void cropProfileImage() {
       ImageView i = (ImageView) findViewById(R.id.imageDrawer);
       int size = BitmapFunctions.convertDpToPixel(getResources().getDimension(R.dimen.dimen_120), this);
       i.setImageBitmap(BitmapFunctions.getRoundedShape(R.drawable.ic_background_, this, size, size));
    }

    private void testDrawer() {
        /**
         * Create Dummy Drawer Items
         */
        drawerItemsList = new ArrayList<DrawerItem>();
        drawerItemsList.add(new DrawerItem(R.drawable.ic_item_profile, "View Profile", false, null));
        drawerItemsList.add(new DrawerItem(R.drawable.ic_item_my_game, "My Game", true, new String[]{"Tournaments", "Teams", "Skills", "Training"}));
        drawerItemsList.add(new DrawerItem(R.drawable.ic_item_gallery, "Gallery", false, null));
        drawerItemsList.add(new DrawerItem(R.drawable.ic_item_inbox, "Inbox", false, null));

        ListView list = (ListView) findViewById(R.id.listDrawer);
        DrawerAdapter a = new DrawerAdapter(this, R.layout.drawer_item_layout, R.layout.drawer_expanded_item_layout, drawerItemsList);
        list.setAdapter(a);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
