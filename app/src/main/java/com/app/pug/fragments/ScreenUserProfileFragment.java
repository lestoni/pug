package com.app.pug.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.pug.R;
import com.app.pug.framework.Screen;
import com.app.pug.utils.BitmapFunctions;
import com.app.pug.utils.Utils;

/**
 * Created by MATIVO-PC on 2/23/2015, 2:04 PM
 * Project:  PUG
 * Package Name: com.app.pug.fragments
 */
public class ScreenUserProfileFragment extends Screen {
    private View v;

    private final static String TAG = "ScreenUserProfileFragment";

    public static ScreenUserProfileFragment newInstance() {
        ScreenUserProfileFragment home = new ScreenUserProfileFragment();
        return home;
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
        v = inflater.inflate(R.layout.screen_user_pager, container, false);

        final ImageView imageDrawer = (ImageView) v.findViewById(R.id.pagerPlayerIcon);

        // load and display the image off the UI thread
        Utils.runTask(getActivity(), new Utils.ResultTask<Bitmap>() {
            @Override
            public Bitmap run() throws Exception {
                int size = BitmapFunctions.convertDpToPixel(getResources().getDimension(R.dimen.dimen_96), getActivity());
                return BitmapFunctions.getRoundedShape(R.drawable.ic_list_item_4, getActivity(), size, size);
            }

            @Override
            public void onFinish(Bitmap result) {
                if (result != null) {
                    imageDrawer.setImageBitmap(result);
                }
            }
        }, "LoadImageDrawerTask");

        return v;
    }
}
