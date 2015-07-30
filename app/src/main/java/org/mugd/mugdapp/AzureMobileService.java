package org.mugd.mugdapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

public class AzureMobileService extends Service {

    private static final String TAG = "AMS";

    private AzureMobileServiceInteraction amsi;
    private Context context;

    public AzureMobileService(){
        this.context = this;
    }

    public AzureMobileService(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG){
            Toast.makeText(getApplicationContext(),"AMSI service started",Toast.LENGTH_SHORT).show();
        }
        Log.i(TAG,"AMSI service started");
        amsi = new AzureMobileServiceInteraction(context);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(BuildConfig.DEBUG){
            Toast.makeText(getApplicationContext(),"AMSI service startCommand",Toast.LENGTH_SHORT).show();
        }
        Log.i(TAG,"AMSI service startCommand");
        amsi.execute();
        if(BuildConfig.DEBUG){
            Toast.makeText(getApplicationContext(),"AMSI synced",Toast.LENGTH_SHORT).show();
        }
        Log.i(TAG, "AMSI synced");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
