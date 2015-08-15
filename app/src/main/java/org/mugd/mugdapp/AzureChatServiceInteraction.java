package org.mugd.mugdapp;

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
 * Created by Adish Jain on 07-08-2015.
 */
public class AzureChatServiceInteraction extends AsyncTask<ChatPublic, Void, List<ChatPublic>>{

    private final static String TAG = "ACSI";

    private Context context;

    private MobileServiceClient mClient;

    private MobileServiceTable<ChatPublic> mChatTable;

    public List<ChatPublic> chatList;

    public AzureChatServiceInteraction(Context context){
        this.context = context;
    }

    protected void onPreExecute(){
        super.onPreExecute();

        chatList = new ArrayList<ChatPublic>();

        Log.v(TAG,"connecting to AzureChatServiceInteraction");

        try {
            mClient = new MobileServiceClient(
                    "https://mugd-app.azure-mobile.net/",
                    "EEkrmAJgegNSaCsgIaRQDTAmbAqZRZ90",
                    context
            );

            mChatTable = mClient.getTable(ChatPublic.class);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected List<ChatPublic> doInBackground(ChatPublic... chatpublics) {
        Log.v(TAG,"Getting Messages");
        if(chatpublics[0]==null) {
            try {
                final MobileServiceList<ChatPublic> result = mChatTable
                        .orderBy("__createdAt", QueryOrder.Ascending)
                        .execute().get();
                Log.v(TAG, "Running background task");
                chatList.clear();
                for (ChatPublic item : result) {
                    chatList.add(item);
                    Log.v(TAG, "" + item.CreatedAt());
                }

            } catch (Exception exception) {
                Log.e(TAG, "Exception starting");
                Log.e(TAG, exception.getMessage());
                Log.e(TAG, "Exception ending");
            }
        }

        else {
            mChatTable.insert(chatpublics[0]);

        }
            return chatList;
    }


    @Override
    protected void onPostExecute(List<ChatPublic> result){
        super.onPostExecute(result);
        Log.v(TAG, "Setting messages");
        ChatArrayAdapter chatArrayAdapter;
        chatArrayAdapter = new ChatArrayAdapter(context, R.layout.activity_chat_singlemessage);
        for(ChatPublic item : result) {
            Log.v(TAG, "Adding message");
            ChatArrayAdapter.addMessage(item);
        }
    }
}
