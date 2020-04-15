package com.example.trafficscotland.SectionsPageAdaptor;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionsPageAdaptor extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentLust = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public SectionsPageAdaptor(FragmentManager childFragmentManager) {
        super(childFragmentManager);
    }

    public void addFragment(Fragment Fragment, String title){
        mFragmentLust.add(Fragment);
        mFragmentTitleList.add(title);
    }

    public SectionsPageAdaptor(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentLust.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentLust.size();
    }
}
