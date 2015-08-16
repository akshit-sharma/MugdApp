package org.mugd.mugdapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;


public class FullEventsListFragment extends Fragment {

    private final static String TAG = "FullEventsListFragment";


    private Activity activity;

    private LinearLayout ll;
    private FragmentActivity fa;

    RecyclerView rv;
    static List<Events> eventsList;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity==null){
            Log.e(TAG,"Why is Activity NULL !! ");
        }else {
            this.activity = activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        if(Build.VERSION.SDK_INT >= 21) {
            getWindow().setSharedElementExitTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_element_transition_events));
        }
        */
        refreshEntries();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myFragmentView = inflater.inflate(R.layout.fragment_full_events_list, container, false);

        rv = (RecyclerView) myFragmentView.findViewById(R.id.rv2);

        if(rv == null){
            Log.e(TAG,"rv shouldnot be null");
        }

        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(activity.getApplicationContext());
        rv.setLayoutManager(llm);

        refreshList();

        return myFragmentView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void refreshEntries(){
        Log.i(TAG,"Entries refreshed");

        ClientDatabaseInteraction cbi = new ClientDatabaseInteraction(activity.getApplicationContext());
        eventsList = cbi.initialiseEvents();
        cbi.closeDB();

        /*
        eventsList = new ArrayList<>();
        Events events = new Events();
        events.imageUri = "faaltu";
        events.Title = "Title";
        events.Desc = "ABc";
        events.Date = new Date();
        events.college = "Venue";
        eventsList.add(events);
        */
    }

    private void refreshList(){
        Log.i(TAG,"List refreshed");
        ShowAllEventsAdapter adapter = new ShowAllEventsAdapter(activity,eventsList);
        rv.setAdapter(adapter);
    }

}
