package com.attracttest.attractgroup.attracttest.Rss;

import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.attracttest.attractgroup.attracttest.R;

import java.util.List;

/**
 * Created by nexus on 04.09.2017.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RssViewHolder> {

    public static class RssViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView rssTitle;
        TextView rssDesc;

        RssViewHolder(View itemView) {
            super(itemView);

            cv = itemView.findViewById(R.id.cv);
            rssTitle = itemView.findViewById(R.id.rss_title);
            rssDesc = itemView.findViewById(R.id.rss_description);
        }
    }

    List<RssItem> news;

    public RVAdapter(List<RssItem> news){
        this.news = news;
    }

    @Override
    public RssViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rss_item, parent, false);
        RssViewHolder rvh = new RssViewHolder(v);
        return rvh;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(RssViewHolder holder, int position) {

        //Html.fromHtml("<h2>Title</h2><br><p>Description here</p>",
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            holder.rssTitle.setText(Html.fromHtml(news.get(position).getTitle(),Html.FROM_HTML_MODE_COMPACT ));
            holder.rssDesc.setText(Html.fromHtml(news.get(position).getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.rssTitle.setText(Html.fromHtml(news.get(position).getTitle()));
            holder.rssDesc.setText(Html.fromHtml(news.get(position).getDescription()));
        }

    }

    @Override
    public int getItemCount() {
        return news.size();
    }

}
