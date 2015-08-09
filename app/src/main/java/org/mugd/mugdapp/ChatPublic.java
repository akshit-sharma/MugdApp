package org.mugd.mugdapp;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Adish Jain on 04-08-2015.
 */
public class ChatPublic {
    public String Name;
    public String Message;
    public String Id;
    public Date __createdAt;
    public String CreatedAt() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(__createdAt);
    }
    public ChatPublic(String Message , Context context){
        WifiManager manager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();
        this.Name = address;
        this.Message = Message;

    }


}
