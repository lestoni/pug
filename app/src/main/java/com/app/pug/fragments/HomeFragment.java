package com.app.pug.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.pug.R;
import com.app.pug.framework.Screen;
import com.app.pug.models.HomeListItem;
import com.app.pug.util.HomeListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MATIVO-PC on 2/22/2015, 10:06 PM
 * Project:  PUG
 * Package Name: com.app.pug
 */
public class HomeFragment extends Screen {

    private View v;
    private ListView list;
    private HomeListAdapter adap;
    private ViewPager viewPager;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_home, container, false);

        list = (ListView) v.findViewById(R.id.listHome);

        testData();

        initialisePager();

        return v;
    }

    private void initialisePager() {
        viewPager = (ViewPager) v.findViewById(R.id.homePager);
        viewPager.setAdapter(new ViewAdapter(5, getActivity()));
        viewPager.setOffscreenPageLimit(5);
        viewPager.setPageMargin(15);
        viewPager.setClipChildren(false);
    }

    private void testData() {
        List<HomeListItem> items = new ArrayList<HomeListItem>();
        items.add(new HomeListItem(R.drawable.ic_list_item_1, "Lovren James", "Shooting G", "Cleverand", 854, 42, 854));
        items.add(new HomeListItem(R.drawable.ic_list_item_2, "Mora Moore Jordan", "Small Forward", "Brooklyn, New York", 01, 214, 854));
        items.add(new HomeListItem(R.drawable.ic_list_item_3, "JohnSon Williams", "Point Guard", "Texas", 500, 2845, 125));
        items.add(new HomeListItem(R.drawable.ic_list_item_4, "Serena Peters", "", "Duke City", 452, 1200, 10));

        adap = new HomeListAdapter(getActivity(), R.layout.home_list_items, items);
        list.setAdapter(adap);
    }

    /**
     * --------------------VIEW PAGER ADAPTER ------------------------------
     */
    private class ViewAdapter extends PagerAdapter {
        private int numPages;
        private Context context;

        public ViewAdapter(int numPages, Context context) {
            this.numPages = numPages;
            this.context = context;
        }

        @Override
        public int getCount() {
            return numPages;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.e(HomeFragment.class.getName(), position + "");
            CardView root = (CardView) LayoutInflater.from(context).inflate(R.layout.home_pager_items, null);
            int back = getBackground(position);

            root.setCardBackgroundColor(back);
            root.findViewById(R.id.pagerRoot).setBackgroundColor(back);
            ((ViewPager) container).addView(root, 0);
            return root;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        public int getBackground(int pos) {
            int position = pos + 1;
            int mod = position % 3;
            int color = (R.color.pager_1);
            if (mod == 1) {
                color = (R.color.pager_1);
            } else if (mod == 2) {
                color = (R.color.pager_2);
            } else if (mod == 0) {
                color = (R.color.pager_3);
            }
            return color;
        }
    }

}
