package com.example.trafficscotland.googleMaps.clustering;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Dimitar Danailov on 6/3/15.
 * email: dimityr.danailov@gmail.com
 *
 * Documentation: https://developers.google.com/maps/documentation/android/utility/marker-clustering
 */
public class AppClusterItem implements ClusterItem {

    private final LatLng mPosition;
    private final String mTitle;
    private final String mSnippet;

    public AppClusterItem(double latitude, double longitude, String title, String snippet) {
        mPosition = new LatLng(latitude, longitude);
        mTitle = title;
        mSnippet = snippet;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }


    public String getTitle() {
        return mTitle;
    }


    public String getSnippet() {
        return mSnippet;
    }
}
