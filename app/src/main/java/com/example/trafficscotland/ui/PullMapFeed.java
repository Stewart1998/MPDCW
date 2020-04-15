package com.example.trafficscotland.ui;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class PullMapFeed {

    private String title, description, lat, long2;

    public PullMapFeed(String title, String description, String lat, String long2) {
        this.title = new String(title);
        this.description = new String(description);
        this.lat = new String(lat);
        this.long2 = new String(long2);
    }

    public PullMapFeed(){
        String urlSource = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";
        try
        {
            DocumentBuilderFactory f =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder b =
                    f.newDocumentBuilder();
            Document doc =
                    b.parse(urlSource);

            doc.getDocumentElement().normalize();

            NodeList nodeList =
                    doc.getElementsByTagName("item");

            for (int itr = 0; itr < nodeList.getLength(); itr++)
            {
                Node node = nodeList.item(itr);
                if (node.getNodeType() == Node.ELEMENT_NODE && node != null)
                {
                    Element eElement = (Element) node;

                    if(eElement.getElementsByTagName("title").item(0) != null) {
                        title = eElement.getElementsByTagName("title").item(0).getTextContent();
                    }else{
                        title = "No Title";
                    }

                    if(eElement.getElementsByTagName("description").item(0) != null) {
                        description = eElement.getElementsByTagName("description").item(0).getTextContent();
                    }else{
                        description = "No Description";
                    }

                    //set values

                    System.out.println("TITLE......." + title);

                    setDescription(description);
                    setTitle(title);

                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Controller Error: " + e.getMessage());
        }

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong2() {
        return long2;
    }

    public void setLong2(String long2) {
        this.long2 = long2;
    }
}
