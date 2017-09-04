package com.attracttest.attractgroup.attracttest;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.attracttest.attractgroup.attracttest.Utils.NetworkUtils;
import com.attracttest.attractgroup.attracttest.Utils.UtilsJson;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ListView listView;
    private SearchView searchView;
    private SuperheroAdapter statusesAdapter;

    //Request API URL
    final static String URL = "http://test.php-cd.attractgroup.com/test.json";

    //Array init for the Listview
    private ArrayList<SuperheroProfile> superheroProfiles = new ArrayList<>();

    //Number of heroes for sendin into fragments adapter
    int numberOfHeroes;

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        statusesAdapter.getFilter().filter(newText);
        return true;
    }

    //Task for retrieving array of {@link SuperheroProfile}s
    private class ParseJsonTask extends AsyncTask<String, Void, ArrayList<SuperheroProfile>> {
        private Context context;
        public ParseJsonTask(Context context){
            this.context = context;
        }

        @Override
        protected ArrayList<SuperheroProfile> doInBackground(String... params) {


            try {
                superheroProfiles =  UtilsJson.fetchSuperheroProfileData(params[0]);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

            return superheroProfiles;
        }

        @Override
        protected void onPostExecute(ArrayList<SuperheroProfile> contains) {
            // Create an {@link ProfileAdapter}, whose data source is a list of
            // {@link Profile}s. The adapter knows how to create list item views for each item
            // in the list.
            numberOfHeroes = contains.size();
            statusesAdapter = new SuperheroAdapter(context, contains);

            // Get a reference to the ListView, and attach the adapter to the listView.

            listView.setAdapter(statusesAdapter);
        }}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.searchbar, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search here!");

        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list_for_superhero_profiles);
        searchView = findViewById(R.id.search);

        //Checkin for the network availability, or showin toast
        if (NetworkUtils.isNetworkAvailable(this)){
            new ParseJsonTask(this).execute(URL);

            listView.setTextFilterEnabled(false);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    // Get the {@link SuperheroProfile} object at the given position the user clicked on
                    SuperheroProfile profile = superheroProfiles.get(position);

                    Intent profileFragmentIntent = new Intent(MainActivity.this, SuperheroFragmentAdapter.class);
                    profileFragmentIntent.putExtra("frag_position", position);

                    //Passing serialized object to the new fragment
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("profile", superheroProfiles);
                    bundle.putInt("size", numberOfHeroes);
                    bundle.putInt("currentPosition", position);

                    profileFragmentIntent.putExtras(bundle);
                    startActivity(profileFragmentIntent);
                }
            });

        }
        else Toast.makeText(this,"Enable network pls!", Toast.LENGTH_LONG).show();
    }
}
