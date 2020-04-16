/**
 * @author Stewart McCafferty S1738575
 * @version 1.1.1
 */

package com.example.trafficscotland.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trafficscotland.BuildConfig;
import com.example.trafficscotland.R;

import java.util.ArrayList;

public class SettingsFragment extends Fragment {

   ListView ListView;

    ArrayAdapter<String>ArrayAdapter;

    ArrayList<String> ArrayList = new ArrayList<String>();


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        ListView = (ListView) root.findViewById(R.id.ListView);

        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;

        ArrayList.add("Version :" + versionName);
        ArrayList.add("Copyright © Stewart McCafferty 2020");

        ArrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                ArrayList
        );


        ListView.setAdapter(ArrayAdapter);



        return root;
    }

}