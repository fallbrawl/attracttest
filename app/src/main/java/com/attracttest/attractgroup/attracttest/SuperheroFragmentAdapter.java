package com.attracttest.attractgroup.attracttest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.ArrayList;


public class SuperheroFragmentAdapter extends FragmentActivity {

    private static final String TAG = "myLogs";
    private static final int PAGE_COUNT = 8;
    private static int size;
    private static int currPosition;
    private static ArrayList<SuperheroProfile> profiles;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.superhero_pager);

        Intent heroInfo = getIntent();

        // Recieving a serialized info bundle to fill in profiles page
        Bundle bundle = heroInfo.getExtras();
        profiles = (ArrayList<SuperheroProfile>) bundle.getSerializable("profile");
        size = bundle.getInt("size");
        currPosition = bundle.getInt("currentPosition");

        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(currPosition);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected, position = " + position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return SuperheroFragment.newInstance(position, profiles.get(position), currPosition);
        }

        @Override
        public int getCount() {
            return size;
        }

    }
}
