package org.mugd.mugdapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;


public class CustomListViewShow extends AppCompatActivity {

    ListView lv;
    Context context;

    ArrayList prgmName;
    public int [] prgmImages = { R.mipmap.ic_item_list_test_image,R.mipmap.ic_item_list_test_image,R.mipmap.ic_item_list_test_image,R.mipmap.ic_item_list_test_image,R.mipmap.ic_item_list_test_image };
    public String [] prgmNameList = { "list item 1", "list item 2", "list item 3", "list item 4", "list item 5"  };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list_view_show);

        context = this;

        lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(new CustomListItem(this, prgmNameList, prgmImages));

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
