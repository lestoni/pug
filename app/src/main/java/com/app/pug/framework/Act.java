package com.app.pug.framework;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.app.pug.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public abstract class Act extends ActionBarActivity {
    public static final String TAG = "PUG - APP";
    private Screen currentScreen;

    /**
     * Show a toast message to the screen
     */
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Show toast message using int resource
     */
    public void toast(int id) {
        toast(getResources().getString(id));
    }

    /**
     * Switch screens
     *
     * @param current    Current Fragment
     * @param newScreen  Fragment To launch
     * @param addToStack whether to add the current fragment to the stack
     */
    public void switchScreen(Screen current, Screen newScreen, boolean addToStack) {
        try {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            if (addToStack) trans.addToBackStack(null);
            newScreen.setHasOptionsMenu(true);
            trans.replace(R.id.container, newScreen);
            currentScreen = current;
            trans.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Switch screens from a Fragment Activity
     *
     * @param newScreen Fragment To launch
     */
    public void switchScreen(Screen newScreen) {
        try {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            newScreen.setHasOptionsMenu(true);
            trans.replace(R.id.container, newScreen);
            trans.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Get the current Screen
     */
    public Screen getCurrentScreen() {
        return currentScreen;
    }

    /**
     * Get the current screen name
     */
    public String getCurrentScreenName() {
        return currentScreen.getClass().getSimpleName();
    }

    /**
     * Switch screens (put the current screen on the stack by default)
     *
     * @param current   The current fragment
     * @param newScreen The Fragment to launch
     */

    public void switchScreen(Screen current, Screen newScreen) {
        switchScreen(current, newScreen, true);
    }

    /**
     * Read a Bitmap from a given directory
     *
     * @param context Context
     * @param dir     Directory where it's stored
     * @param name    Name of the file
     * @return Bitmap
     */
    private static Bitmap loadImageFromStorage(Context context, String dir, String name) {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir(dir, Context.MODE_PRIVATE);
        try {
            File f = new File(directory, name + ".png");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
