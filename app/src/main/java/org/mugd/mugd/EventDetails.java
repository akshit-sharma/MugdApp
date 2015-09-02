package org.mugd.mugd;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class EventDetails extends AppCompatActivity {

    public static Events event;

    private static final String TAG = "EventDetails";

    ImageView eventImage;
    ProgressBar eventImageProgressBar;
    TextView eventTitle;
    TextView eventDescription;
    TextView eventVenue;
    TextView eventTime;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= 21) {
            getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_element_transition_events));
        }
        setContentView(R.layout.activity_event_details);

        eventImage = (ImageView) findViewById(R.id.eventImage);
        eventImageProgressBar = (ProgressBar) findViewById(R.id.eventImageProgressBar);
        eventTitle = (TextView) findViewById(R.id.eventTitle);
        eventDescription = (TextView) findViewById(R.id.eventDesc);
        eventVenue = (TextView) findViewById(R.id.eventVenue);
        eventTime = (TextView) findViewById(R.id.eventETA);

        //CardView cardView = (CardView) findViewById(R.id.cvHeader);

        //Animation slide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abc_slide_in_top);
        //cardView.startAnimation(slide);

        new DownloadImageTask(eventImage,eventImageProgressBar).execute(event.imageUri);
        eventTitle.setText(Events.extract(event, "Title"));
        eventDescription.setText(Events.extract(event,"Desc")+"\n");

        eventVenue.setText(Events.extract(event,"college"));
        eventTime.setText(Events.extract(event,"Date"));


        Log.v(TAG, "onCreate finished");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_details, menu);
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
