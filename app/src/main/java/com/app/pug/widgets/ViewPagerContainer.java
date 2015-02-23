package com.app.pug.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by MATIVO-PC on 2/23/2015, 11:15 AM
 * Project:  PUG
 * Package Name: com.app.pug.widgets
 */
public class ViewPagerContainer extends FrameLayout {
    private ViewPager mPager;

    public ViewPagerContainer(Context context) {
        super(context);
        init();
    }

    public ViewPagerContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewPagerContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setClipChildren(false);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onFinishInflate() {
        mPager = (ViewPager) getChildAt(0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mPager.dispatchTouchEvent(ev);
    }
}
