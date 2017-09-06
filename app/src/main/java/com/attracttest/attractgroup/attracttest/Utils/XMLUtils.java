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
    private String title = "title";
    private String description = "description";
    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    private RssItem rssItem;
    private List<RssItem> rssItems;

    public volatile boolean parsingComplete = true;

    public XMLUtils(String url) {
        this.urlString = url;
    }

    public List<RssItem> getRssItems() {return rssItems;}

    public void parseXMLAndStoreIt(XmlPullParser myParser) {

        int event;
        String text = null;
        rssItems = new ArrayList<>();

        try {

            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();

                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:

                        if (name.equals("title")) {
                            title = text;
                        }

                        else if (name.equals("description")) {
                            description = text;
                        }

                        //Thread.sleep(300);
                        rssItems.add(new RssItem(title, description));

                        break;
                }
                Log.e("title", title);
                Log.e("desc", description);

                //Log.e("insidious", String.valueOf(myMap.size()));
                event = myParser.next();
            }

            parsingComplete = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("insidious", String.valueOf(rssItems.size()));

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