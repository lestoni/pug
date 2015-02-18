package com.app.pug;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.app.pug.utils.BitmapFunctions;


public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        cropProfileImage();
    }

    private void cropProfileImage() {
       ImageView i = (ImageView) findViewById(R.id.imageDrawer);
       int size = BitmapFunctions.convertDpToPixel(getResources().getDimension(R.dimen.dimen_120), this);
       i.setImageBitmap(BitmapFunctions.getRoundedShape(R.drawable.ic_background_, this, size, size));
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
