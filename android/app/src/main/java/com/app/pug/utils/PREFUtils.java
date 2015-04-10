package com.app.pug.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MATIVO-PC on 3/2/2015, 2:53 PM
 * Project:  PUG
 * Package Name: com.app.pug.utils
 */
public class PREFUtils {
    public static final String PREFS_NAME = "PUGPrefsFile";
    //--------------PREFERENCES KEYS------------------------------
    public static String FIRST_USER = "isFirstUse";

    /**
     * Set that this is the first Time the user is accessing the app.
     * @param context Context
     * @param b true if for the first time, false for subsequent access.
     */
    public static void setFirstUse(Context context, boolean b) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(FIRST_USER, b);
        editor.commit();
    }

    /**
     * Checks if this is the first time the user has accessed the app
     * @param context Context
     * @return boolean true if its the first time, false for subsequent access.
     */
    public static boolean isFirstUse(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        return settings.getBoolean(FIRST_USER, true);
    }

}
