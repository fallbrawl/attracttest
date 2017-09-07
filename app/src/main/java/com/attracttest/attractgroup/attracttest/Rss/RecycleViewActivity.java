package com.attracttest.attractgroup.attracttest.Rss;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.attracttest.attractgroup.attracttest.MainActivity;
import com.attracttest.attractgroup.attracttest.R;
import com.attracttest.attractgroup.attracttest.Utils.NetworkUtils;
import com.attracttest.attractgroup.attracttest.Utils.XMLUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nexus on 06.09.2017.
 */
public class RecycleViewActivity extends MainActivity {
    private RecyclerView rv;
    private XMLUtils parseXML;

    //Request URL for RSS feed
    final static String rssURL = "https://www.macworld.com/index.rss";

    //Array init for the List of RSS items
    private ArrayList<RssItem> rssItems = new ArrayList<>();

    //Task for retrieving array of {@link RssItem}s
    private class ParseRss extends AsyncTask<String, Void, List<RssItem>> {

        @Override
        protected ArrayList<RssItem> doInBackground(String... params) {

            parseXML = new XMLUtils(params[0]);
            parseXML.fetchXML();

            while (parseXML.parsingComplete);

            return parseXML.getRssItems();
        }

        @Override
        protected void onPostExecute(List<RssItem> contains) {
            //Settin the Cardview with rss's
            rv = findViewById(R.id.rv);
            Log.e("staty", String.valueOf(contains.size()));
            RVAdapter adapter = new RVAdapter(contains);
            rv.setAdapter(adapter);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recycler_rss);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(llm);

        if (NetworkUtils.isNetworkAvailable(this)) {
            new ParseRss().execute(rssURL);
        }

    }

}
