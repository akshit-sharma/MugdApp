package org.mugd.mugd;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class AzureMobileService extends Service {

    private static final String TAG = "AMS";

    private boolean toastDebug = false;

    private AzureMobileServiceInteraction amsi;
    private Context context;

    private boolean done;

    public AzureMobileService(){
        this.context = this;
    }

    public AzureMobileService(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG && toastDebug){
            Toast.makeText(getApplicationContext(),"AMSI service started",Toast.LENGTH_SHORT).show();
        }
        Log.i(TAG,"AMSI service started");
        done = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(BuildConfig.DEBUG && toastDebug){
            Toast.makeText(getApplicationContext(),"AMSI service startCommand",Toast.LENGTH_SHORT).show();
        }
        Log.i(TAG, "AMSI service startCommand");
        if(!done) {
            done = true;
            amsi = new AzureMobileServiceInteraction(context);
            amsi.execute();
            if (BuildConfig.DEBUG && toastDebug) {
                Toast.makeText(getApplicationContext(), "AMSI synced", Toast.LENGTH_SHORT).show();
            }
            Log.i(TAG, "AMSI synced");
            done = false;
        }
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
