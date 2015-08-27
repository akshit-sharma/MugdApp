package org.mugd.mugd;

import android.content.Context;
import android.util.Log;

import java.util.Date;

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
        Log.d("Name : ", ""+name);
        Log.d("pubName(ChatPublic : )", Name);
          this.Message = Message;
          this.CreatedAt = new Date();

    }


}
