package org.mugd.mugdapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class FullEventsListFragment extends Fragment {

    private final static String TAG = "FELF";

    private View myFragmentView;
    private Activity activity;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_full_events_list, container, false);
        return myFragmentView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rv = (RecyclerView) myFragmentView.findViewById(R.id.rv2);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(activity.getApplicationContext());
        rv.setLayoutManager(llm);

    }

    @Override
    public void onResume() {
        super.onResume();

        refreshList();

    }

    private void refreshList(){
        ClientDatabaseInteraction cbi = new ClientDatabaseInteraction(activity.getApplicationContext());
        eventsList = cbi.initialiseEvents();
        cbi.closeDB();

        ShowAllEventsAdapter adapter = new ShowAllEventsAdapter(activity,eventsList);
        rv.setAdapter(adapter);
    }

}
