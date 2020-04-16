package com.example.trafficscotland.ui.map;

import android.os.Bundle;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;

import com.example.trafficscotland.R;

import java.util.ArrayList;

public class MarkerInfoFragment extends Fragment {

    ArrayList<Spanned> ArrayList = new ArrayList<Spanned>();

    android.widget.ListView ListView;

    ArrayAdapter<Spanned> ArrayAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);

        return view;

    }



}

