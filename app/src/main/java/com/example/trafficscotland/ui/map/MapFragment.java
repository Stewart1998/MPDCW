package com.example.trafficscotland.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.trafficscotland.R;
import com.example.trafficscotland.SectionsPageAdaptor.SectionsPageAdaptor;
import com.google.android.material.tabs.TabLayout;

public class MapFragment extends Fragment {

    private ViewPager ViewPager;

    private SectionsPageAdaptor SectionsPageAdaptor;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        SectionsPageAdaptor = new SectionsPageAdaptor(getChildFragmentManager());

        ViewPager = (ViewPager) root.findViewById(R.id.view_pager);
        setupViewPager(ViewPager);

        TabLayout TabLayout = (TabLayout) root.findViewById(R.id.tabs);
        TabLayout.setupWithViewPager(ViewPager);

        return root;
    }

    private void setupViewPager(ViewPager ViewPager){
        SectionsPageAdaptor adaptor = new SectionsPageAdaptor(getChildFragmentManager());
        adaptor.addFragment(new MapTab1Fragment(), "Map View");
        adaptor.addFragment(new MapsTab2Fragment(), "Journey Planner");
        adaptor.addFragment(new MapsTab2Fragment(), "Public Transit");
        ViewPager.setAdapter(adaptor);
    }
}