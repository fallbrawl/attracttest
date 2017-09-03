package com.attracttest.attractgroup.attracttest.Utils;

import android.text.TextUtils;
import android.util.Log;

import com.attracttest.attractgroup.attracttest.SuperheroProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by nexus on 02.09.2017.
 */
public class UtilsJson {

    /**
     * Tag for the log messages
     */

    public static final String LOG_TAG = UtilsJson.class.getSimpleName();

    /**
     * Query the http://test.php-cd.attractgroup.com/test.json dataset and return an {@link SuperheroProfile} object.
     */

    public static ArrayList<SuperheroProfile> fetchSuperheroProfileData(String requestUrl) throws ExecutionException, InterruptedException {

        // Create URL object
        Log.e(LOG_TAG, "Fetchin begins!");
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Return the {@link Event}
        return extractFeatureFromJson(jsonResponse);
    }

    /**
     * Returns new URL object from the given string URL.
     */

    private static URL createUrl(String stringUrl) {
        URL url = null;
        Log.e(LOG_TAG, "Creating Url...");
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL! ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */

    private static String makeHttpRequest(URL url) throws IOException {
        Log.e(LOG_TAG, "Makin http request...");
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            Log.e(LOG_TAG, "Url is null!");
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the profiles JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */

    private static String readFromStream(InputStream inputStream) throws IOException {
        Log.e(LOG_TAG, "reading from stream...");
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return an {@link SuperheroProfile} object by parsing out information
     */

    private static ArrayList<SuperheroProfile> extractFeatureFromJson(String SuperheroProfileJSON) throws ExecutionException, InterruptedException {

        ArrayList<SuperheroProfile> SuperheroProfileList = new ArrayList<>();

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(SuperheroProfileJSON)) {
            Log.e(LOG_TAG, "Json string is empty!!!");
            return null;
        }

        try {
            JSONArray superheroProfilesArray = new JSONArray(SuperheroProfileJSON);

            // If there are results in the profiles array
            if (superheroProfilesArray.length() > 0) {
                for (int i = 0; i < superheroProfilesArray.length(); i++) {

                    // Extract out the profile's JSON
                    JSONObject superheroProfile = superheroProfilesArray.getJSONObject(i);

                    //Superhero parameters:
                    int id = superheroProfile.getInt("itemId");

                    String name = superheroProfile.getString("name");
                    String image = superheroProfile.getString("image");
                    String description = superheroProfile.getString("description");
                    Long unixDate = superheroProfile.getLong("time");

                    //Convert Unix time into normal date
                    java.util.Date normalDate = new java.util.Date((long)unixDate*1000);

                    //Format date
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    String reportDate = df.format(unixDate);

                    //Constructing a full string for debuggin' purposes:
//                    String logDebugString = String.format("%s \n %s \n %s \n %s \n %s ",
//                            name, image, description, reportDate, id);
//
//                    Log.e(LOG_TAG, logDebugString);

                    // Create a new {@link SuperheroProfile} object
                    SuperheroProfileList.add(new SuperheroProfile(id, name, description, image, reportDate ));
                }
            }

            return SuperheroProfileList;

        } catch (JSONException e) {
            Log.v("staty","\n \n \n" +SuperheroProfileJSON + "\n \n \n");
            Log.e(LOG_TAG, "Problem parsing the SuperheroProfile JSON results", e);
            return SuperheroProfileList;
        }
    }
}
