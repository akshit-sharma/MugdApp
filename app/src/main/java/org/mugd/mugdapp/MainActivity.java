package org.mugd.mugdapp;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

   private Button chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chat = (Button)findViewById(R.id.button4);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChat(view);

            }
        });
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

    public void showListView(View view){
        Intent intent = new Intent(this,CustomListViewShow.class);
        startActivity(intent);
    }

    public void showNavDrawer(View view){
        Intent intent = new Intent(this,NavDrawerShow.class);
        startActivity(intent);
    }

    public void azureMobileService(View view){
        Intent intent = new Intent(this,AzureMobileService.class);
        startActivity(intent);
    }
    public void showChat(View view){
        Intent intent = new Intent(this,ChatBubbleActivity.class);
        startActivity(intent);
    }


}
