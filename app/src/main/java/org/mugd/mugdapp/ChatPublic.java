package org.mugd.mugdapp;

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


}
