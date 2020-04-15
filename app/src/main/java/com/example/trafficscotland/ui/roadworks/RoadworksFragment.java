package com.example.trafficscotland.ui.roadworks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.trafficscotland.R;
import com.example.trafficscotland.SectionsPageAdaptor.SectionsPageAdaptor;
import com.google.android.material.tabs.TabLayout;

public class RoadworksFragment extends Fragment {

    private ViewPager ViewPager;

    private RoadworksViewModel RoadworksViewModel;
    private SectionsPageAdaptor SectionsPageAdaptor;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RoadworksViewModel = ViewModelProviders.of(this).get(RoadworksViewModel.class);
        View root = inflater.inflate(R.layout.fragment_roadworks, container, false);

        SectionsPageAdaptor = new SectionsPageAdaptor(getChildFragmentManager());

        ViewPager = (ViewPager) root.findViewById(R.id.view_pager);
        setupViewPager(ViewPager);

        TabLayout TabLayout = (TabLayout) root.findViewById(R.id.tabs);
        TabLayout.setupWithViewPager(ViewPager);

        return root;
    }

    private void setupViewPager(ViewPager ViewPager){
        SectionsPageAdaptor adaptor = new SectionsPageAdaptor(getChildFragmentManager());
        adaptor.addFragment(new RoadworksTab1Fragment(), "Current Roadworks");
        adaptor.addFragment(new RoadworksTab2Fragment(), "Planned Roadworks");
        ViewPager.setAdapter(adaptor);
    }
}