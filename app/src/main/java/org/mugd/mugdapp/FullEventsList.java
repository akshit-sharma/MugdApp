package org.mugd.mugdapp;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;


public class FullEventsList extends AppCompatActivity {

    RecyclerView rv;
    static List<Events> eventsList;

    /*
     *  for Nav Drawer
     *
     */
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >= 21) {
            getWindow().setSharedElementExitTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_element_transition_events));
        }

        setContentView(R.layout.activity_full_events_list);

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);

/*
        rv.setOnClickListener(new RecyclerView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v.getRootView(), "Work Na " + v.toString(), Snackbar.LENGTH_SHORT).show();

            }
        });
*/
     /*
     *  for Nav Drawer
     *
     */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.navmainDrawer);
        drawerToggle = setupDrawerToggle();
        mDrawer.setDrawerListener(drawerToggle);

        final ActionBar ab = getSupportActionBar();
        if(ab!=null)
            ab.setDisplayHomeAsUpEnabled(true);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

    }

    private void refreshList(){
//        ClientDatabaseInteraction cbi = new ClientDatabaseInteraction(this);
//        eventsList = cbi.initialiseEvents();
//        cbi.closeDB();

        ShowAllEventsAdapter adapter = new ShowAllEventsAdapter(this,eventsList);
        rv.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.refreshList();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_full_events_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     *   for NavDrawers
     *
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this,MainActivity.class);
     //   i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }


    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                }
        );
    }

    public void selectDrawerItem(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_events:
                if (BuildConfig.DEBUG)
                    Toast.makeText(getApplicationContext(), "Event Activity", Toast.LENGTH_SHORT).show();
                Intent eventIntent = new Intent(this, FullEventsList.class);
                startActivity(eventIntent);
                break;

            case R.id.nav_chats:
                if (BuildConfig.DEBUG)
                    Toast.makeText(getApplicationContext(), "Chat activity", Toast.LENGTH_SHORT).show();
                Intent chatIntent = new Intent(this, ChatBubbleActivity.class);
                startActivity(chatIntent);
                return;

            default:
                Toast.makeText(getApplicationContext(), "default_one", Toast.LENGTH_SHORT).show();
        }

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();

    }
}
