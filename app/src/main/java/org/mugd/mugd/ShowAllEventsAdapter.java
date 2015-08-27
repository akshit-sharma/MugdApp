package org.mugd.mugd;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.List;


public class ShowAllEventsAdapter extends RecyclerView.Adapter<ShowAllEventsAdapter.AllEventsViewHolder>{

    private static final String TAG = "ShowAllEventsAdapter";

    List<Events> events;
    Activity callingActivity;


    @Override
    public AllEventsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.i(TAG,"onCreateViewHolder called");
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_show_all_events_adapter, viewGroup, false);
        AllEventsViewHolder aevh = new AllEventsViewHolder(callingActivity,v);
        Log.i(TAG,"onCreateViewHolder returned");
        return aevh;
    }

    @Override
    public void onBindViewHolder(AllEventsViewHolder holder, int i) {
        Log.i(TAG,"onBindViewHolder called");
        new DownloadImageTask(holder.eventImage,holder.eventImageProgressBar).execute(events.get(i).imageUri);
        holder.eventTitle.setText(events.get(i).Title);
        holder.eventSmallDec.setText(Events.extract(events.get(i),"Desc",true));
        holder.eventVenue.setText(Events.extract(events.get(i),"college",true));
        holder.eventTime.setText(Events.extract(events.get(i),"Date",true));
        holder.event = events.get(i);
        Log.i(TAG,"onBindViewHolder finished");
    }

    @Override
    public int getItemCount() {
        Log.i(TAG,"getItemCount "+events.size()+" called");
        if(events == null)
            Log.e(TAG,"Why is events null !!");
        return events.size();
    }

    ShowAllEventsAdapter(Activity callingActivity,List<Events> events){
        Log.i(TAG,"constructor called");
        this.callingActivity = callingActivity;
        this.events = events;
        Log.i(TAG,"constructor finished");
    }

    public static class AllEventsViewHolder extends RecyclerView.ViewHolder{

        private static String TAG = "AllEventsViewHolder";
        Activity callingActivity;
        CardView cv;
        Events event;
        ImageView eventImage;
        ProgressBar eventImageProgressBar;
        TextView eventTitle;
        TextView eventSmallDec;
        TextView eventVenue;
        TextView eventTime;

        AllEventsViewHolder(final Activity callingActivity,View itemView){
            super(itemView);
            Log.i(TAG, " constructor called ");
            this.callingActivity = callingActivity;
            cv = (CardView) itemView.findViewById(R.id.cv);
            eventImage = (ImageView) itemView.findViewById(R.id.eventImage);
            eventImageProgressBar = (ProgressBar) itemView.findViewById(R.id.eventImageProgressBar);
            eventTitle = (TextView) itemView.findViewById(R.id.eventTitle);
            eventSmallDec = (TextView) itemView.findViewById(R.id.eventSmallDesc);
            eventVenue = (TextView) itemView.findViewById(R.id.eventVenue);
            eventTime = (TextView) itemView.findViewById(R.id.eventETA);

            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "CV Clicked !!");

                        Pair<View, String> pair = new Pair<View, String>(eventImage,"eventImageTransition");

                    if(Build.VERSION.SDK_INT > 21) {

                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(callingActivity, v, "eventImageTransition");

                        Intent intent = new Intent(callingActivity, EventDetails.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        EventDetails.event = event;
                        callingActivity.startActivity(intent, optionsCompat.toBundle());
                    }else{
                        Intent intent = new Intent(callingActivity, EventDetails.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        EventDetails.event = event;
                        callingActivity.startActivity(intent);
                    }


//                        Intent intent = new Intent(cv.getContext().getApplicationContext(),EventDetails.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        EventDetails.event = event;
//                        cv.getContext().getApplicationContext().startActivity(intent);


                }
            });
            Log.i(TAG, " constructor ended ");
        }

    }

}