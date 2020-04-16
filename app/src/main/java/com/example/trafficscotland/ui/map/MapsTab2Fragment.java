/**
 * @author Stewart McCafferty S1738575
 * @version 1.1.1
 */
package com.example.trafficscotland.ui.map;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trafficscotland.R;
import com.example.trafficscotland.timeAgo.timeAgo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class MapsTab2Fragment extends Fragment {
    EditText EditText; ListView ListView; Button Button;EditText EditText2;Button Button2;
    ArrayList<Spanned> ArrayList = new ArrayList<Spanned>();
    ArrayAdapter<Spanned>ArrayAdapter;

    ArrayList<Spanned>EmptyArrayList;


    public String VALUE,VALUE2 = null;
    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_map_tab2, container, false);

        EditText =(EditText) root.findViewById(R.id.editText);

        EditText2 =(EditText) root.findViewById(R.id.editText2);


        ListView = (ListView) root.findViewById(R.id.incidents_feed);
        Button = (Button) root.findViewById(R.id.button);
        Button2 = (Button) root.findViewById(R.id.button2);


        Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VALUE = EditText.getText().toString();
                VALUE2 = EditText2.getText().toString();

                if(!VALUE.equals("")) {
                    new MapsTab2Fragment.AsyncTask().execute();
                }else{
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), "Please enter a date!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

        Button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                EditText.setText(date);

            }
        });




        return root;
    }

    private class AsyncTask extends android.os.AsyncTask<Integer, Integer, ArrayList<Spanned>> {
        @Override
        protected ArrayList<Spanned> doInBackground(Integer... integers) {
            String urlSource = "https://trafficscotland.org/rss//feeds/roadworks.aspx";
            try {
                DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
                DocumentBuilder b = f.newDocumentBuilder();
                Document doc = b.parse(urlSource);

                doc.getDocumentElement().normalize();

                NodeList nodeList = doc.getElementsByTagName("item");

                String date = VALUE;

                ArrayList.clear();

                ArrayList.add(Html.fromHtml("<b>Showing Data for planned travel on the '"+date+"'</b></br />"));


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


                            if(description != "No Description"){
                               String data[] =  description.split("<br />");

                                String startDate = data[0]; String endDate = data[1];


                                startDate=startDate.replace("Start Date: ","");
                                startDate=startDate.replace(" -","");


                                endDate=endDate.replace("End Date: ","");
                                endDate=endDate.replace(" -","");

                                timeAgo timeAgo = new timeAgo();
                                String startDateFormatted = timeAgo.convertDate(startDate);
                                String endDateFormatted = timeAgo.convertDate(endDate);

                                SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");

                                try {
                                    Date date1 = formatter.parse(date);
                                    String date2 =  formatter.format(date1);

                                    System.out.println(date2);

                                    CharSequence ago = timeAgo.timeAgo(pubDate);

                                    if(startDateFormatted.compareTo(date2) == 0) {
                                        ArrayList.add(Html.fromHtml("<b>" + title + "</b> -  " + ago + "<br /><div style='padding: 1px;'>" + description + "</div>"));
                                    }

                                    if(startDateFormatted.compareTo(date2) < 0 && endDateFormatted.compareTo(date2) > 0 && startDateFormatted.compareTo(date2) != 0) {
                                        ArrayList.add(Html.fromHtml("<b>" + title + "</b> -  " + ago + "<br /><div style='padding: 1px;'>" + description + "</div>"));
                                    }


                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                } else {
                    ArrayList.add(Html.fromHtml("No DATA to display"));
                }
                return ArrayList;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;

        }

        protected void onPostExecute(ArrayList<Spanned> result) {

            try {


                ArrayAdapter = new ArrayAdapter<Spanned>(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        result
                );


                (MapsTab2Fragment.this).ArrayAdapter.getFilter().filter(VALUE2);

                ListView.setAdapter(ArrayAdapter);


            }catch (Exception e){
                System.out.println("ERROR: " + e.getMessage());
            }
        }

        protected void onPreExecute(String result) {
            ArrayAdapter = (android.widget.ArrayAdapter<Spanned>) ListView.getAdapter();
        }

    }


}