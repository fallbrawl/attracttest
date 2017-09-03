package com.attracttest.attractgroup.attracttest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nexus on 02.09.2017.
 */
public class SuperheroAdapter extends ArrayAdapter<SuperheroProfile> implements Filterable{
    private MainActivity activity;
    private List<SuperheroProfile> heroArrayList;
    private List<SuperheroProfile> filteredHeroArrayList;

    public SuperheroAdapter(Context context, List<SuperheroProfile> profiles) {
        // Initializing the ArrayAdapter's internal storage for the context and the list.
        super(context, 0, profiles);
        filteredHeroArrayList = profiles;
    }

    @Override
    public int getCount() {
        return filteredHeroArrayList.size();
    }

    @Override
    public SuperheroProfile getItem(int i) {
        return filteredHeroArrayList.get(i);
    }


    private class DownloadImageTask extends AsyncTask<ViewHolder, Void, ViewHolder> {

        protected ViewHolder doInBackground(ViewHolder... urls) {
            ViewHolder viewHolder = urls[0];

            try {
                URL imageURL = new URL(viewHolder.imageURL);

                InputStream in = imageURL.openStream();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.inSampleSize = 2;
                viewHolder.bitmap = BitmapFactory.decodeStream(in, null, options);



            } catch (IOException e) {
                Log.e("Error!", "Failed to download image!");
                viewHolder.bitmap = null;
            }
            return viewHolder;
        }

        protected void onPostExecute(ViewHolder result) {
            if (result.bitmap == null) {
                result.thumbnailView.setImageResource(R.mipmap.ic_launcher);
            }
            else {

                result.thumbnailView.setImageBitmap(Bitmap.createScaledBitmap(result.bitmap,120 , 90, false));
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listSuperheroesView = convertView;
        ViewHolder holder = null;


        if (listSuperheroesView == null) {
            listSuperheroesView = LayoutInflater.from(getContext()).inflate(
                    R.layout.superhero_item, parent, false);

            holder = new ViewHolder();

            // Find the TextView in the superhero_item.xml layout with the superhero's name
            holder.nameTextView = (TextView) listSuperheroesView.findViewById(R.id.list_item_superhero_name);
            // Find the TextView in the superhero_item.xml layout with the date
            holder.dateTextView = (TextView) listSuperheroesView.findViewById(R.id.list_item_date);
            // Find the ImageView in the superhero_item.xml layout with the ID
            holder.thumbnailView = (ImageView) listSuperheroesView.findViewById(R.id.list_item_thumbnail);

            listSuperheroesView.setTag(holder);
        }

        holder = (ViewHolder) listSuperheroesView.getTag();

        // Get the {@link SuperheroProfile} object located at this position in the list
        SuperheroProfile currentProfile = getItem(position);

        // Get the user's name from the current SuperheroProfile object and
        // set this text on the name TextView

        holder.nameTextView.setText(currentProfile.getName());

        // Get the date from the current SuperheroProfile object and
        // set this text on the number TextView
        holder.dateTextView.setText(currentProfile.getDate());

        holder.imageURL = currentProfile.getImageURL();

        // Get the image Bitmap from the current SuperheroProfile object
        // using the AsyncTask and
        // set the image

        //TODO: extract image downloading into separate class to avoid code duplication

        new DownloadImageTask().execute(holder);
        return listSuperheroesView;

    }

    private static class ViewHolder {

        TextView nameTextView;
        TextView dateTextView;
        ImageView thumbnailView;
        String imageURL;
        Bitmap bitmap;

    }

    /**
     * Custom filter for friend list
     * Filter content in friend list according to the search text
     */
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<SuperheroProfile> results = new ArrayList<>();
                if (heroArrayList == null)
                    heroArrayList = filteredHeroArrayList;
                if (constraint != null) {
                    if (heroArrayList != null && heroArrayList.size() > 0) {
                        for (final SuperheroProfile g : heroArrayList) {
                            if (g.getName().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                filteredHeroArrayList = (ArrayList<SuperheroProfile>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
