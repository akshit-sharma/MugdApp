package org.mugd.mugdapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class CustomListViewShow extends AppCompatActivity {

    ListView lv;
    Context context;

    public static List<Events> alternative;

    ArrayList prgmName;
    public int [] prgmImages = { R.mipmap.ic_item_list_test_image,R.mipmap.ic_item_list_test_image,R.mipmap.ic_item_list_test_image,R.mipmap.ic_item_list_test_image,R.mipmap.ic_item_list_test_image };
    public String [] prgmNameList = { "list item 1", "list item 2", "list item 3", "list item 4", "list item 5"  };
    public  String [] prgmImagesTesting;

    ArrayList<Events> events;
    boolean isEvent;
    private boolean doubleBackToExitPressedOnce;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list_view_show);

        context = this;
        lv = (ListView) findViewById(R.id.listView);

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            if(isEvent = bundle.getBoolean("Events",false))
                if(alternative instanceof ArrayList)
                    events = (ArrayList) alternative;

        }else {
            prgmImagesTesting = new String[prgmImages.length];

            for (int i = 0; i < prgmImagesTesting.length; i++) {
                prgmImagesTesting[i] = prgmImages[i] + "";
            }
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(isEvent){

            lv.setAdapter(new CustomListItem(this,
                    Events.particularFieldAsStringArray("Title",events),
                    Events.particularFieldAsStringArray("Desc",events),
                    Events.particularFieldAsStringArray("college",events),
                    Events.particularFieldAsStringArray("Date",events)
                    ));
        }
        else{
            lv.setAdapter(new CustomListItem(this, prgmNameList, prgmImagesTesting));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_custom_list_view_show, menu);
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
