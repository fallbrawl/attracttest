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
    static final String TAG = "myLogs";
    static final int PAGE_COUNT = 8;
    static int size;
    static int id;
    static ArrayList<SuperheroProfile> profiles;
    ViewPager pager;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.superhero_pager);

        Intent heroInfo = getIntent();

        // Recieving a serialized info bundle to fill in profiles page
        Bundle bundle = heroInfo.getExtras();
        profiles = (ArrayList<SuperheroProfile>) bundle.getSerializable("profile");
        size = bundle.getInt("size");
        id = bundle.getInt("currentId");

        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(id);

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
            return SuperheroFragment.newInstance(position, profiles.get(position), id);
        }

        @Override
        public int getCount() {
            return size;
        }

    }
}
