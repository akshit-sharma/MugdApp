package org.mugd.mugdapp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceException;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 *  Async for Event Loading
 *
 *  This file downloads and update events in background
 *
 * @author Akshit
 *
 */
public class AzureMobileServiceInteraction extends AsyncTask<Void, Void, List<Events>>{

    private Context context;

    private MobileServiceClient mClient;

    private MobileServiceTable<Events> mEventsTable;

    public List<Events> eventsList;

    public AzureMobileServiceInteraction(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute(){
            super.onPreExecute();

        eventsList = new ArrayList<Events>();

        try {
            mClient = new MobileServiceClient(
                Authorization.url,
                Authorization.key,
                context
            );

            mEventsTable = mClient.getTable(Events.class);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected List<Events> doInBackground(Void... params) {
            try{
                final MobileServiceList<Events> result = mEventsTable
                        .orderBy("__createdAt", QueryOrder.Descending)
                        .execute().    get();
                Log.v("AMSI", "Running background task");
                eventsList.clear();
                for(Events item : result){
                    eventsList.add(item);
                 //   Log.v("AMSI_date", " " + item.Date);
                }
                ClientDatabaseInteraction cbi;
                cbi = new ClientDatabaseInteraction(context);
               // Log.v("AMSI", "Values are");
                cbi.clearPrevious();
                for(Events item : eventsList){
                    cbi.onCreate(cbi.getWritableDatabase());
                    cbi.insertCommand("Events", item);
                  //  Log.v("AMSI"," "+item);
                  //  Log.v("AMSI_id", " " + item.id);
                  //  Log.v("AMSI_date", " " + item.Date);
                  //  Log.v("AMSI_imageUri", " " + item.imageUri);
                }
                Log.v("AMSI", "Finished background task");
                cbi.closeDB();

            }catch (Exception exception){
                Log.e("AMSI", "Exception starting");
                Log.e("AMSI", exception.getMessage());
                Log.e("AMSI", "Exception ending");
            }
            return eventsList;
    }

    @Override
    protected void onPostExecute(List<Events> result){
        super.onPostExecute(result);

    }

}
