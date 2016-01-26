package com.dipoletech.wacos.adapaters;/**
 * Created by DABBY(3pleMinds) on 10-Nov-15.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * DABBY(3pleMinds) 10-Nov-15 7:36 AM 2015 11
 * 10 07 36 Xavitech
 **/
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment =  mFragmentList.get(position);
        return fragment;
    }


    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);

    }
   // do not override this method if you do not want title
//    @Override
//    public CharSequence getPageTitle(int position) {
//
//        return mFragmentTitleList.get(position);
//    }



}
