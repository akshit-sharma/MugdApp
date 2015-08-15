package org.mugd.mugdapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class EventsFragment extends Fragment {

    RecyclerView rv;
    static List<Events> eventsList;

    public EventsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void refreshList(){
        ClientDatabaseInteraction cbi = new ClientDatabaseInteraction(this);
        eventsList = cbi.initialiseEvents();
        cbi.closeDB();

        ShowAllEventsAdapter adapter = new ShowAllEventsAdapter(this,eventsList);
        rv.setAdapter(adapter);
    }

}
