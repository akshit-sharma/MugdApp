package org.mugd.mugd;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;

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

    private static final String TAG = "AMSi";

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
                Log.v(TAG, "Running background task");
                //eventsList.clear();
                FullEventsListFragment.clearAllEvent();
                for(Events item : result){
                    FullEventsListFragment.addEvent(item);
                 //   Log.v(TAG, "date " + item.Date);
                }


                /*
                ClientDatabaseInteraction cbi;
                cbi = new ClientDatabaseInteraction(context);
               // Log.v(TAG, "Values are");
                cbi.clearPrevious();
                for(Events item : eventsList){
                    cbi.onCreate(cbi.getWritableDatabase());
                    cbi.insertCommand("Events", item);
                  //  Log.v(TAG," "+item);
                  //  Log.v(TAG, "id " + item.id);
                  //  Log.v(TAG, "date " + item.Date);
                  //  Log.v(TAG, "imageUri " + item.imageUri);
                }
                Log.v(TAG, "Finished background task");
                cbi.closeDB();
                */
            }catch (Exception exception){
                Log.e(TAG, "Exception starting");
                Log.e(TAG, exception.getMessage());
                Log.e(TAG, "Exception ending");
            }
            return eventsList;
    }

    @Override
    protected void onPostExecute(List<Events> result){
        super.onPostExecute(result);
        Log.v(TAG,"PostExecute function");
    }

}
