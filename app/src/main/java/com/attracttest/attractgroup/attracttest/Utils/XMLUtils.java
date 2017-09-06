package com.attracttest.attractgroup.attracttest.Utils;

import android.util.Log;

import com.attracttest.attractgroup.attracttest.Rss.RssItem;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class XMLUtils {

    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    private RssItem rssItem;
    private ArrayList<RssItem> rssItems;

    private String title = "title";
    private String description = "description";

    private  List<String> titles;
    private  List<String> descriptions;

    public volatile boolean parsingComplete = true;

    public XMLUtils(String url) {
        this.urlString = url;
    }

    public ArrayList<RssItem> getRssItems() {return rssItems;}

    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text=null;
        rssItems = new ArrayList<>();
        titles = new ArrayList<>();
        descriptions = new ArrayList<>();

        try {
            String name = null;
            while ((event = myParser.getEventType()) != XmlPullParser.END_DOCUMENT) {


                switch (event){
                    case XmlPullParser.START_TAG:
                        name = myParser.getName();
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:

                        if(name.equals("title")){
                            title = text;
                            titles.add(title);

                        }

                        else if(name.equals("description")){
                            description = text;
                            descriptions.add(description);

                        }

                        else{

                        }

                        break;
                }

                myParser.next();
            }

            parsingComplete = false;
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        //Convert String arrays into rss
        for (int i = 0; i < titles.size(); i++){
            rssItem = new RssItem(titles.get(i), descriptions.get(i));
            rssItems.add(rssItem);
        }

    }


    public void fetchXML() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);

                    // Starts the query
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();

                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(stream, null);

                    parseXMLAndStoreIt(myparser);
                    stream.close();
                } catch (Exception e) {
                }
            }
        });
        thread.start();
    }
}