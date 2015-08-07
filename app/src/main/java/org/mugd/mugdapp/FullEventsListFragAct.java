package org.mugd.mugdapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class FullEventsListFragAct extends FragmentActivity{

    private static final String TAG = "FullEventsListFragAct";

    RecyclerView rv;
    static List<Events> eventsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        refreshEntries();

        setContentView(R.layout.fragment_full_events_list2);

        rv = (RecyclerView) findViewById(R.id.rv3);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);

        refreshList();

    }

    private void refreshEntries(){
        ClientDatabaseInteraction cbi = new ClientDatabaseInteraction(getApplicationContext());
        eventsList = cbi.initialiseEvents();
        cbi.closeDB();
    }

    private void refreshList(){
        ShowAllEventsAdapter adapter = new ShowAllEventsAdapter(this,FullEventsListFragAct.eventsList);
        rv.setAdapter(adapter);
    }
}