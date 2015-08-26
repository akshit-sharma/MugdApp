package org.mugd.mugdapp;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.notifications.NotificationsManager;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Context context;
    public String android_id;


    private static boolean mIsInForeground = false;

    public static final String SENDER_ID = "1050";
    public static MobileServiceClient mClient;


    public boolean toastError = true;


    public static List<MenuItems> mi;

    /*
     *  for Nav Drawer
     *
     */
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    public  String tmDevice, tmSerial , androidId;

   private Button chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);


        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();
        mIsInForeground = false;

        //Registering for gcm
        NotificationsManager.handleNotifications(this, SENDER_ID, PushNotificationHandler.class);

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

        new AzureMobileServiceInteraction(this).execute();
        new AzureChatServiceInteraction(this).execute();


        mi = new LinkedList<MenuItems>();
        mi.add(new MenuItems(getDrawablefromInt(R.drawable.chat_icon), "Chat"));
        mi.add(new MenuItems(getDrawablefromInt(R.drawable.event_icon), "Events"));
        mi.add(new MenuItems(getDrawablefromInt(R.drawable.idea_icon), "Idea"));


        Fragment fragment = null;

        Class fragmentClass = MenuFragment.class;

        try{
            fragment = (Fragment) fragmentClass.newInstance();
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.mainFrag, fragment).commit();
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        mIsInForeground = false;

    }

    public static boolean isInForeground(){
        return mIsInForeground;
    }

    @Override
    protected void onResume() {
        super.onResume();

        mIsInForeground = true;

//        Intent ams = new Intent(this,AzureMobileService.class);
//        startService(ams);
//
//        Intent acs = new Intent(this,AzureChatService.class);
//        startService(acs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nav_drawer_show, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);

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


        switch (menuItem.getItemId()){
            case R.id.nav_events:
                this.openFragment("Events");
                break;

            case R.id.nav_chats:
                this.openFragment("Chat");
                break;

            case R.id.nav_idea:
                this.openFragment("Idea");
                break;

            default:
                this.openFragment("default");
        }

        // Highlight the selected item, update the title, and close the drawer
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();

    }

    private Class developerCheck(String wanted){

        Class fragmentClass = null;

        switch (wanted){
            case "Events":
                new AzureMobileServiceInteraction(this).execute();
                fragmentClass = FullEventsListFragment.class;
                break;

            case "Chat":
                new AzureChatServiceInteraction(this).execute();
                fragmentClass = PublicChatFragment.class;
                break;

            case "Idea":
                if(BuildConfig.DEBUG){

                }
                Snackbar.make(getCurrentFocus().getRootView(),"Will be added shortly",Snackbar.LENGTH_SHORT).show();

                break;

            default:
                Toast.makeText(getApplicationContext(),"default_one",Toast.LENGTH_SHORT).show();
                fragmentClass = MenuFragment.class;
        }

        return fragmentClass;

    }

    public void openFragment(String fragmentName){

        Fragment fragment = null;

        Class fragmentClass = this.developerCheck(fragmentName);

        if(fragmentClass != null) {

            try {
                fragment = (Fragment) fragmentClass.newInstance();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.mainFrag, fragment).commit();

        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    private Drawable getDrawablefromInt(int image){
        return getResources().getDrawable(image);
    }

}
