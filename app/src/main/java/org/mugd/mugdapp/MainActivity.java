package org.mugd.mugdapp;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public boolean toastError = true;

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

   private Button chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    @Override
    protected void onResume() {
        super.onResume();

        Intent ams = new Intent(this,AzureMobileService.class);
        startService(ams);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void showNavDrawer(View view){
        Intent intent = new Intent(this,NavDrawerShow.class);
        startActivity(intent);
    }

    public void azureMobileService(View view){
        Intent intentAllEvents = new Intent(this,FullEventsList.class);
        startActivity(intentAllEvents);
    }
    public void showChat(){
        Intent intent = new Intent(this,ChatBubbleActivity.class);
        startActivity(intent);
    }



    /*
     *   for NavDrawers
     *
     */

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

    public void selectDrawerItem(MenuItem menuItem){
        Fragment fragment = null;

        Class fragmentClass;
        fragmentClass = null;

        switch (menuItem.getItemId()){
            case R.id.nav_events:
                if(BuildConfig.DEBUG)
                    Toast.makeText(getApplicationContext(), "Event Fragment", Toast.LENGTH_SHORT).show();
                //fragmentClass = FullEventsListFragment.class;
                fragment = new FullEventsListFragment();
                break;

            case R.id.nav_second_fragment:
                if(BuildConfig.DEBUG)
                    Toast.makeText(getApplicationContext(),"Event Activity",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,FullEventsList.class);
                startActivity(intent);
                return;

            case R.id.nav_third_fragment:
                if(BuildConfig.DEBUG)
                    Toast.makeText(getApplicationContext(),"Chat activity",Toast.LENGTH_SHORT).show();
                showChat();
                break;

            default:
                Toast.makeText(getApplicationContext(),"default_one",Toast.LENGTH_SHORT).show();
        }

        if(fragmentClass == null){
            Log.e(TAG,"FragmentClass should not be null");
        }else {

            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (fragment == null) {
                if(BuildConfig.DEBUG && toastError)
                    Toast.makeText(getApplicationContext(),"Fragment should not be null",Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Fragment should not be null");
            } else {

                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getSupportFragmentManager();

                if (fragmentManager == null) {
                    Log.e(TAG, "FragmentManager should not be null");
                }

                fragmentManager.beginTransaction().replace(R.id.mainFrag, fragment).commit();
            }
        }

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();

    }


}
