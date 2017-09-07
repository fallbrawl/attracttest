package com.attracttest.attractgroup.attracttest;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.attracttest.attractgroup.attracttest.Rss.CardViewActivity;
import com.attracttest.attractgroup.attracttest.Rss.RecycleViewActivity;
import com.attracttest.attractgroup.attracttest.Utils.NetworkUtils;
import com.attracttest.attractgroup.attracttest.Utils.UtilsJson;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ListView listView;
    private SearchView searchView;
    private SuperheroAdapter statusesAdapter;
    protected DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    //Request API URL
    final static String heroURL = "http://test.php-cd.attractgroup.com/test.json";

    //Array init for the List of superhero profiles
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

        public ParseJsonTask(Context context) {
            this.context = context;
        }

        @Override
        protected ArrayList<SuperheroProfile> doInBackground(String... params) {


            try {
                superheroProfiles = UtilsJson.fetchSuperheroProfileData(params[0]);

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
        }
    }

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
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked

        Class fragmentClass;

        switch (menuItem.getItemId()) {
            case R.id.rss1:
                Intent rssChannel1Activity = new Intent(MainActivity.this, RecycleViewActivity.class);
                startActivity(rssChannel1Activity);
                break;
            default:
                fragmentClass = MainActivity.class;
        }


        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        listView = findViewById(R.id.list_for_superhero_profiles);
        searchView = findViewById(R.id.search);

        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        // Find our drawer view
        nvDrawer = findViewById(R.id.nvView);

        // Setup drawer view
        setupDrawerContent(nvDrawer);

        //Checkin for the network availability, or showin toast
        if (NetworkUtils.isNetworkAvailable(this)) {

            new ParseJsonTask(this).execute(heroURL);

            listView.setTextFilterEnabled(false);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

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

        } else {Toast.makeText(this, "Enable network pls!", Toast.LENGTH_LONG).show();
            Log.e("NO INTERNETO:", "OH!");}
    }
}
