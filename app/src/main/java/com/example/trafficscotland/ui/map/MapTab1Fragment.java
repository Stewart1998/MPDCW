package com.example.trafficscotland.ui.map;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.trafficscotland.R;
import com.example.trafficscotland.dialog.MyDialogFragment;
import com.example.trafficscotland.googleMaps.clustering.AppClusterItem;
import com.example.trafficscotland.googleMaps.clustering.MarkerClusterRenderer;
import com.example.trafficscotland.timeAgo.timeAgo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MapTab1Fragment extends Fragment implements OnMapReadyCallback {

    private static final int TAG_CODE_PERMISSION_LOCATION =0006 ;
    private MapViewModel MapViewModel;

    private MapView MapView;

    ClusterManager<AppClusterItem> mClusterManager;


    ArrayList<String> ArrayList = new ArrayList<String>();

    // Get class name
    private static final String TAG = MapFragment.class.getName();

    // Instance of our Google Map
    private GoogleMap googleMap;

    // Google Map Zoom default zoom level
    private static final int DEFAULT_ZOOM_LEVEL = 4;

    /**
     * User should to have large zoom value to get values from server.
     * User will get information after zoom level >= {MINIMUM_ZOOM_LEVEL_SERVER_REQUEST}
     */
    private static final int MINIMUM_ZOOM_LEVEL_SERVER_REQUEST = 7;

    // Initialize to a non-valid zoom value
    private float previousZoomLevel = -1.0f;

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p/>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_tab1, container, false);
        loadGoogleMapFragment(view, savedInstanceState);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        setupMap(googleMap);
    }

    /**
     * Try to load {@Link CustomGoogleMapFragment#loadGoogleMapFragment}.
     *
     * We loaded our {@link GoogleMap} via async listener. When everything is ready we go to setup our map.
     */
    private void loadGoogleMapFragment(View view, Bundle savedInstanceState) {
        MapView = (MapView) view.findViewById(R.id.mapView);
        MapView.onCreate(savedInstanceState);

        MapView.onResume();

        MapView.getMapAsync(this);
    }

    private void setupMap(GoogleMap map) {
        Log.d(TAG, "Google Map is loaded");

        // Previous Zoom level will be equal to our Default Zoom Level
        previousZoomLevel = DEFAULT_ZOOM_LEVEL;

        // googleMap our application will use to store all information
        googleMap = map;

        // Move Google Map Camera to Default Zoom Level
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(DEFAULT_ZOOM_LEVEL));

        // The My Location button will be visible on the top right of the map.
        googleMap.setMyLocationEnabled(true);

        // Enable Zoom UI Controls
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        /*** Listeners ***/

        // Add Listener, when user change camera
        // googleMap.setOnCameraChangeListener(getCameraChangeListener());

        /*** Listeners ***/

        setUpClustering();
    }

    /**
     * We use {@link ClusterManager} to make setup of google map to support cluster markers.
     *
     *
     * Documentation: https://developers.google.com/maps/documentation/android/utility/marker-clustering
     */
    private void setUpClustering() {
        // Declare a variable for the cluster manager.

        // Position the map in UK.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), DEFAULT_ZOOM_LEVEL));

        // Initialize the manager with the context and the map.
        mClusterManager = new ClusterManager<AppClusterItem>(this.getActivity(), googleMap);
        mClusterManager.setRenderer(new MarkerClusterRenderer(this.getActivity(), googleMap, mClusterManager));


        // Point the map's listeners at the listeners implemented by the cluster manager.
        googleMap.setOnCameraChangeListener(mClusterManager);
        googleMap.setOnMarkerClickListener(mClusterManager);

        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<AppClusterItem>() {


            @Override
            public boolean onClusterClick(Cluster<AppClusterItem> cluster) {

                Log.d("cluster","clicked");

                ArrayList<String> ArrayList2 = new ArrayList<String>();

                int counter = 0;
                for (AppClusterItem item : cluster.getItems()) {
                    ArrayList2.add("<b>" + item.getTitle() + "</b><br /><br />" + item.getSnippet() + "<br /><hr />");
                    counter ++;
                }

                Bundle args = new Bundle();
                args.putStringArrayList("ArrayList2", ArrayList2);
                args.putInt("count", counter);


                showDialog(args);

                /*


                Fragment fragment = new MarkerInfoFragment();

                Bundle args = new Bundle();
                args.putStringArrayList("ArrayList2", ArrayList2);
                args.putInt("count", counter);
                fragment.setArguments(args);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.view_pager, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit(); // save the changes

                 */
                return true;
            }
        });

        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<AppClusterItem>() {
            @Override
            public boolean onClusterItemClick(AppClusterItem item) {
                Log.d("cluster item","clicked");

                Bundle args = new Bundle();
                args.putInt("count", 1);
                args.putString("title", item.getTitle());
                args.putString("description", item.getSnippet());

                showDialog(args);

                return true;
            }
        });

        // Add cluster items (markers) to the cluster manager.
        new MapTab1Fragment.GetRoadworks().execute();
        new MapTab1Fragment.GetPlannedRoadworks().execute();
        new MapTab1Fragment.GetPlannedIncedients().execute();
    }

    void showDialog(Bundle args) {

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = MyDialogFragment.newInstance(4, args);
        newFragment.show(ft, "dialog");
    }



    private class GetRoadworks extends android.os.AsyncTask<Integer, Integer, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Integer... integers) {
            String urlSource = "https://trafficscotland.org/rss//feeds/roadworks.aspx";
            try
            {
                DocumentBuilderFactory f =
                        DocumentBuilderFactory.newInstance();
                DocumentBuilder b = f.newDocumentBuilder();
                Document doc = b.parse(urlSource);

                doc.getDocumentElement().normalize();

                NodeList nodeList = doc.getElementsByTagName("item");
                if (nodeList.getLength() >= 1) {
                    for (int itr = 0; itr < nodeList.getLength(); itr++) {
                        Node node = nodeList.item(itr);
                        if (node.getNodeType() == Node.ELEMENT_NODE && node != null) {
                            Element eElement = (Element) node;

                            String georss,title, description, pubDate;

                            if (eElement.getElementsByTagName("title").item(0) != null) {
                                title = eElement.getElementsByTagName("title").item(0).getTextContent();
                            } else {
                                title = "title";
                            }

                            if (eElement.getElementsByTagName("description").item(0) != null) {
                                description = eElement.getElementsByTagName("description").item(0).getTextContent();
                            } else {
                                description = "No Title";
                            }

                            if (eElement.getElementsByTagName("georss:point").item(0) != null) {
                                georss = eElement.getElementsByTagName("georss:point").item(0).getTextContent();
                            } else {
                                georss = "No Data";
                            }


                            if (eElement.getElementsByTagName("pubDate").item(0) != null) {
                                pubDate = eElement.getElementsByTagName("pubDate").item(0).getTextContent();
                            } else {
                                pubDate = "No Data";
                            }

                            timeAgo timeAgo = new timeAgo();

                            CharSequence ago = timeAgo.timeAgo(pubDate);

                            ArrayList.add(georss + "<:>" + title + " - " + ago  + "<:>" + description);
                        }
                    }
                }
                return ArrayList;
            }
            catch (Exception e)
            {
                System.out.println("Error...." + e.getMessage());
            }

            return null;

        }

        protected void onPostExecute(ArrayList<String> result) {
            // Set some lat/lng coordinates to start with.
            double latitude = 0;
            double longitude =  0;
            try{
                for (String rsu : result) {

                    String [] data = rsu.split("<:>");

                    String latlong = data[0];
                    String title = data[1];;
                    String snippet = data[2];;


                    String [] latlong2 = latlong.split(" ");

                    latitude = Double.parseDouble(latlong2[0]);
                    longitude = Double.parseDouble(latlong2[1]);

                    AppClusterItem AppClusterItem = new AppClusterItem(latitude, longitude, title, snippet);
                    mClusterManager.addItem(AppClusterItem);
                }
            }catch (Exception e)
            {
                System.out.println("Error...." + e.getMessage());
            }
        }
    }

    private class GetPlannedRoadworks extends android.os.AsyncTask<Integer, Integer, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Integer... integers) {
            String urlSource = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
            try
            {
                DocumentBuilderFactory f =
                        DocumentBuilderFactory.newInstance();
                DocumentBuilder b = f.newDocumentBuilder();
                Document doc = b.parse(urlSource);

                doc.getDocumentElement().normalize();

                NodeList nodeList = doc.getElementsByTagName("item");
                if (nodeList.getLength() >= 1) {
                    for (int itr = 0; itr < nodeList.getLength(); itr++) {
                        Node node = nodeList.item(itr);
                        if (node.getNodeType() == Node.ELEMENT_NODE && node != null) {
                            Element eElement = (Element) node;


                            String georss,title, description, pubDate;

                            if (eElement.getElementsByTagName("title").item(0) != null) {
                                title = eElement.getElementsByTagName("title").item(0).getTextContent();
                            } else {
                                title = "title";
                            }

                            if (eElement.getElementsByTagName("description").item(0) != null) {
                                description = eElement.getElementsByTagName("description").item(0).getTextContent();
                            } else {
                                description = "No Title";
                            }

                            if (eElement.getElementsByTagName("georss:point").item(0) != null) {
                                georss = eElement.getElementsByTagName("georss:point").item(0).getTextContent();
                            } else {
                                georss = "No Data";
                            }


                            if (eElement.getElementsByTagName("pubDate").item(0) != null) {
                                pubDate = eElement.getElementsByTagName("pubDate").item(0).getTextContent();
                            } else {
                                pubDate = "No Data";
                            }

                            timeAgo timeAgo = new timeAgo();

                            CharSequence ago = timeAgo.timeAgo(pubDate);

                            ArrayList.add(georss + "<:>" + title + " - " + ago  + "<:>" + description);
                        }
                    }
                }
                return ArrayList;
            }
            catch (Exception e)
            {
                System.out.println("Error...." + e.getMessage());
            }

            return null;

        }

        protected void onPostExecute(ArrayList<String> result) {
            // Set some lat/lng coordinates to start with.
            try{
                double latitude = 0;
                double longitude = 0;

                for (String rsu : result) {

                    String[] data = rsu.split("<:>");

                    String latlong = data[0];
                    String title = data[1];
                    ;
                    String snippet = data[2];
                    ;


                    String[] latlong2 = latlong.split(" ");

                    latitude = Double.parseDouble(latlong2[0]);
                    longitude = Double.parseDouble(latlong2[1]);

                    AppClusterItem AppClusterItem = new AppClusterItem(latitude, longitude, title, snippet);
                    mClusterManager.addItem(AppClusterItem);
                }
            }catch (Exception e)
            {
                System.out.println("Error...." + e.getMessage());
            }
        }
    }

    private class GetPlannedIncedients extends android.os.AsyncTask<Integer, Integer, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Integer... integers) {
            String urlSource = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";
            try
            {
                DocumentBuilderFactory f =
                        DocumentBuilderFactory.newInstance();
                DocumentBuilder b = f.newDocumentBuilder();
                Document doc = b.parse(urlSource);

                doc.getDocumentElement().normalize();

                NodeList nodeList = doc.getElementsByTagName("item");
                if (nodeList.getLength() >= 1) {
                    for (int itr = 0; itr < nodeList.getLength(); itr++) {
                        Node node = nodeList.item(itr);
                        if (node.getNodeType() == Node.ELEMENT_NODE && node != null) {
                            Element eElement = (Element) node;


                            String georss,title, description, pubDate;

                            if (eElement.getElementsByTagName("title").item(0) != null) {
                                title = eElement.getElementsByTagName("title").item(0).getTextContent();
                            } else {
                                title = "title";
                            }

                            if (eElement.getElementsByTagName("description").item(0) != null) {
                                description = eElement.getElementsByTagName("description").item(0).getTextContent();
                            } else {
                                description = "No Title";
                            }

                            if (eElement.getElementsByTagName("georss:point").item(0) != null) {
                                georss = eElement.getElementsByTagName("georss:point").item(0).getTextContent();
                            } else {
                                georss = "No Data";
                            }


                            if (eElement.getElementsByTagName("pubDate").item(0) != null) {
                                pubDate = eElement.getElementsByTagName("pubDate").item(0).getTextContent();
                            } else {
                                pubDate = "No Data";
                            }

                            timeAgo timeAgo = new timeAgo();

                            CharSequence ago = timeAgo.timeAgo(pubDate);

                            ArrayList.add(georss + "<:>" + title + " - " + ago  + "<:>" + description);
                        }
                    }
                }
                return ArrayList;
            }
            catch (Exception e)
            {
                System.out.println("Error...." + e.getMessage());
            }

            return null;

        }

        protected void onPostExecute(ArrayList<String> result) {
            // Set some lat/lng coordinates to start with.
            double latitude = 0;
            double longitude =  0;

            try{
                for (String rsu : result) {

                    String [] data = rsu.split("<:>");

                    String latlong = data[0];
                    String title = data[1];;
                    String snippet = data[2];;


                    String [] latlong2 = latlong.split(" ");

                    latitude = Double.parseDouble(latlong2[0]);
                    longitude = Double.parseDouble(latlong2[1]);

                    AppClusterItem AppClusterItem = new AppClusterItem(latitude, longitude, title, snippet);
                    mClusterManager.addItem(AppClusterItem);
                }
            }catch (Exception e)
            {
                System.out.println("Error...." + e.getMessage());
            }
        }
    }


}

