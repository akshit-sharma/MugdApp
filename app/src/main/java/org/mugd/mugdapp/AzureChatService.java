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
public class AzureChatService extends AsyncTask<Void, Void, List<ChatPublic>>{


    private Context context;

    private MobileServiceClient mClient;

    private MobileServiceTable<ChatPublic> mChatTable;

    public List<ChatPublic> chatList;

    public AzureChatService(Context context){
        this.context = context;
    }

    protected void onPreExecute(){
        super.onPreExecute();

        chatList = new ArrayList<ChatPublic>();

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
    protected List<ChatPublic> doInBackground(Void... voids) {
        try{
            final MobileServiceList<ChatPublic> result = mChatTable
                    .orderBy("__createdAt", QueryOrder.Descending)
                    .execute().    get();
            Log.v("AMSI", "Running background task");
            chatList.clear();
            for(ChatPublic item : result){
                chatList.add(item);
                Log.v("AMSI_date", " " + item.CreatedAt());
            }

        }catch (Exception exception){
            Log.e("AMSI", "Exception starting");
            Log.e("AMSI", exception.getMessage());
            Log.e("AMSI", "Exception ending");
        }

        return chatList;
    }


    @Override
    protected void onPostExecute(List<ChatPublic> result){
        super.onPostExecute(result);
        ChatArrayAdapter chatArrayAdapter;
        chatArrayAdapter = new ChatArrayAdapter(context, R.layout.activity_chat_singlemessage);
        for(ChatPublic item : result) {
            chatArrayAdapter.add(item);
        }
    }
}
