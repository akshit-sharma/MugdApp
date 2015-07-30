package org.mugd.mugdapp;

import android.graphics.Point;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class EventShow extends FragmentActivity{

    private final String TAG = "EventShow";

    ImageView imageViewer;
    String eventID;
    static Events event;

    EventFragmentPagerAdapter eventFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_show);
        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            eventID = bundle.getString("ID",null);
            if(eventID == null){
                Log.e(TAG,"eventID is null !!");
            }
            if(event == null){
                Log.e(TAG,"event is null !!");
            }
            /*
            if(!event.id.equals(eventID)){
                Log.e(TAG,"Id of event and eventID is different !!");
                Log.e(TAG,"EventID : "+eventID);
                Log.e(TAG,"ID of event : "+event.id);
            }
            */

            eventID = event.id;


            Log.v(TAG, "everything OK in onCreate so far");

            setImage();
            ((TextView) findViewById(R.id.eventTitle)).setText(event.Title);

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;

            int minside;

            if(width<height)
                minside = width;
            else
                minside = height;

            int orien =  getResources().getConfiguration().orientation;

            if(orien==getResources().getConfiguration().ORIENTATION_LANDSCAPE){
                minside /= 5;
            }else{
                minside /= 2;
            }

            imageViewer = (ImageView) findViewById(R.id.eventImage);
            imageViewer.setAdjustViewBounds(true);
            imageViewer.setMaxHeight(minside);
            imageViewer.setMaxWidth(minside);

            Log.v(TAG, "Width is " + width);
            Log.v(TAG, "Height is " + height);
            Log.v(TAG, "minside is "+minside);


            ViewPager pager = (ViewPager) findViewById(R.id.eventViewPager);
            FragmentManager fm = getSupportFragmentManager();
            eventFragmentPagerAdapter = new EventFragmentPagerAdapter(fm);
            pager.setAdapter(eventFragmentPagerAdapter);

        }

    }

    private void setImage(){

        Log.v(TAG, "Image url : " + event.imageUri);

        ImageView imageViewer = (ImageView) findViewById(R.id.eventImage);

        new DownloadImageTask(imageViewer).execute(event.imageUri);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_show, menu);
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


}
