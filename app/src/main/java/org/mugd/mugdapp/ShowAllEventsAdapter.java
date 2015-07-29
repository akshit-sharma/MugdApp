package org.mugd.mugdapp;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;


public class ShowAllEventsAdapter extends RecyclerView.Adapter<ShowAllEventsAdapter.AllEventsViewHolder>{

    List<Events> events;

    

    @Override
    public AllEventsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_show_all_events_adapter, viewGroup, false);
        AllEventsViewHolder aevh = new AllEventsViewHolder(v);
        return aevh;
    }

    @Override
    public void onBindViewHolder(AllEventsViewHolder holder, int i) {
        new DownloadImageTask(holder.eventImage,holder.eventImageProgressBar).execute(events.get(i).imageUri);
        holder.eventTitle.setText(events.get(i).Title);
        holder.eventSmallDec.setText(Events.extract(events.get(i),"Desc",true));
        holder.eventVenue.setText(Events.extract(events.get(i),"college",true));
        holder.eventTime.setText(Events.extract(events.get(i),"Date",true));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    ShowAllEventsAdapter(List<Events> events){
        this.events = events;
    }

    public static class AllEventsViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        ImageView eventImage;
        ProgressBar eventImageProgressBar;
        TextView eventTitle;
        TextView eventSmallDec;
        TextView eventVenue;
        TextView eventTime;

        AllEventsViewHolder(View itemView){
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            eventImage = (ImageView) itemView.findViewById(R.id.eventImage);
            eventImageProgressBar = (ProgressBar) itemView.findViewById(R.id.eventImageProgressBar);
            eventTitle = (TextView) itemView.findViewById(R.id.eventTitle);
            eventSmallDec = (TextView) itemView.findViewById(R.id.eventSmallDesc);
            eventVenue = (TextView) itemView.findViewById(R.id.eventVenue);
            eventTime = (TextView) itemView.findViewById(R.id.eventETA);
        }

    }

}