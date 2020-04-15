package com.example.trafficscotland.ui.incidents;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.trafficscotland.R;
import com.example.trafficscotland.dialog.MyDialogFragment;
import com.example.trafficscotland.timeAgo.timeAgo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class IncidentsFragment extends Fragment {

    IncidentsViewModel IncidentsViewModel;

    ArrayList<Spanned> incidents = new ArrayList<Spanned>();

    ListView ListView; EditText theFilter;

    ArrayAdapter<Spanned> ArrayAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_incidents, container, false);

        new IncidentsFragment.AsyncTask().execute();

        ListView = (ListView) root.findViewById(R.id.incidents_feed);

        theFilter = (EditText) root.findViewById(R.id.searchFilter);


        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle args = new Bundle();
                String selectedFromList = (ListView.getItemAtPosition(position).toString());

                args.putInt("count", 1);
                args.putString("title", "INCIDENTS DETAILS");
                args.putString("description", selectedFromList);




                showDialog(args);
            }
        });


            theFilter.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String str = s.toString();
                    if (!str.equals("")) {
                        (IncidentsFragment.this).ArrayAdapter.getFilter().filter(str);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


        return root;
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


    private class AsyncTask extends android.os.AsyncTask<Integer, Integer, ArrayList<Spanned>> {
        @Override
        protected ArrayList<Spanned> doInBackground(Integer... integers) {
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

                            String description, title, pubDate;

                            if (eElement.getElementsByTagName("title").item(0) != null) {
                                title = eElement.getElementsByTagName("title").item(0).getTextContent();
                            } else {
                                title = "No Title";
                            }

                            if (eElement.getElementsByTagName("description").item(0) != null) {
                                description = eElement.getElementsByTagName("description").item(0).getTextContent();
                            } else {
                                description = "No Description";
                            }

                            if (eElement.getElementsByTagName("pubDate").item(0) != null) {
                                pubDate = eElement.getElementsByTagName("pubDate").item(0).getTextContent();
                            } else {
                                pubDate = "No Data";
                            }

                            timeAgo timeAgo = new timeAgo();

                            CharSequence ago = timeAgo.timeAgo(pubDate);

                            incidents.add(Html.fromHtml("<b>" + title + "</b> -  " + ago + "<br /><div style='padding: 1px;'>" + description + "</div>"));

                        } else {
                            incidents.add(Html.fromHtml("<b>No Incidents to display</b>"));
                        }
                    }
                }else{
                    incidents.add(Html.fromHtml("<b>No Incidents to display</b>"));
                }
                return incidents;
            }
            catch (Exception e)
            {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(), "Error has Occurred", Toast.LENGTH_SHORT).show();
                    }
                });
                System.out.println("Error...." + e.getMessage());
            }

            return incidents;

        }

        protected void onPostExecute(ArrayList<Spanned> result) {

            try {


            ArrayAdapter = new ArrayAdapter<Spanned>(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    result
            );

            ListView.setAdapter(ArrayAdapter);

            }catch (Exception e){
                System.out.println("ERROR: " + e.getMessage());
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(), "Error has Occurred", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        protected void onPreExecute(String result) {
            ArrayAdapter = (ArrayAdapter<Spanned>) ListView.getAdapter();
        }


    }


}