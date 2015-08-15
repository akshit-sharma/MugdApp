package org.mugd.mugdapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

public class AzureChatService extends Service {

    private static final String TAG = "ACS";

    private boolean toastDebug = false;

    private AzureChatServiceInteraction acsi;
    private Context context;

    private boolean done;

    public AzureChatService(){
        this.context = this;
    }

    public AzureChatService(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG && toastDebug){
            Toast.makeText(getApplicationContext(),"ACSI service started",Toast.LENGTH_SHORT).show();
        }
        Log.i(TAG,"ACSI service started");
        done = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(BuildConfig.DEBUG && toastDebug){
            Toast.makeText(getApplicationContext(),"ACSI service startCommand",Toast.LENGTH_SHORT).show();
        }
        Log.i(TAG, "ACSI service startCommand");
        if(!done) {
            done = true;
            acsi = new AzureChatServiceInteraction(context);
            acsi.execute();
            if (BuildConfig.DEBUG && toastDebug) {
                Toast.makeText(getApplicationContext(), "ACSI synced", Toast.LENGTH_SHORT).show();
            }
            Log.i(TAG, "ACSI synced");
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
