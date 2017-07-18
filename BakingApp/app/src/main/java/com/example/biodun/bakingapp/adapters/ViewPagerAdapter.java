package com.example.biodun.bakingapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Biodun on 6/26/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    final ArrayList<Fragment> mFragment=new ArrayList<>();
    final ArrayList<String> fragmentTitles=new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager){
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }
    public void addFragment(Fragment fragment,String title){
        mFragment.add(fragment);
        fragmentTitles.add(title);
    }
}
