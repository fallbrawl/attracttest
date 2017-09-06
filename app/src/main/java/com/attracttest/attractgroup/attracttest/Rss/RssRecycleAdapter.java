package com.attracttest.attractgroup.attracttest.Rss;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.attracttest.attractgroup.attracttest.R;

import java.util.List;

/**
 * Created by nexus on 04.09.2017.
 */
public class RssRecycleAdapter extends RecyclerView.Adapter<RssRecycleAdapter.RssViewHolder> {

    List<RssItem> news;

    public RssRecycleAdapter(List<RssItem> news){
        this.news = news;
    }

    @Override
    public RssViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_rss, parent, false);
        RssViewHolder pvh = new RssViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(RssViewHolder holder, int position) {
        holder.rssTitle.setText(news.get(position).getTitle());
        holder.rssDesc.setText(news.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public static class RssViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView rssTitle;
        TextView rssDesc;


        RssViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            rssTitle = (TextView)itemView.findViewById(R.id.suchtitle);
            rssDesc = (TextView)itemView.findViewById(R.id.desc);

        }
    }

}
