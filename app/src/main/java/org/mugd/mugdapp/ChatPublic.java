package org.mugd.mugdapp;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Adish Jain on 04-08-2015.
 */
public class ChatPublic {

    private static final String TAG = "ChatPublic";

    public String Name;
    public String Message;
    public String Id;
    public Date CreatedAt;
    public Date __createdAt;
//    public String CreatedAt() {
//        SimpleDateFormat dateFormat = new SimpleDateFormat(
//                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//        return dateFormat.format(__createdAt);
//    }
    public ChatPublic(String Message , Context context , String name){
//        WifiManager manager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
//        WifiInfo info = manager.getConnectionInfo();
//        String address = info.getMacAddress();
//        this.Name = address;
         // TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
          this.Name = name;
          this.Message = Message;
          this.CreatedAt = new Date();

    }


}
