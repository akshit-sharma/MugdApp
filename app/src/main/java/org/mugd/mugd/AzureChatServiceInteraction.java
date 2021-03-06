package org.mugd.mugd;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;

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
        this.button = null;
    }
    private Button button;

    protected void onPreExecute(){
        super.onPreExecute();

        chatList = new ArrayList<ChatPublic>();

        Log.v(TAG,"connecting to AzureChatServiceInteraction");

        try {
            mClient = new MobileServiceClient(
                    Authorization.url,
                    Authorization.key,
                    context
            );

            mChatTable = mClient.getTable(ChatPublic.class);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected List<ChatPublic> doInBackground(ChatPublic... chatPublics) {
        Log.v(TAG,"Getting Messages");

            try {
                if(chatPublics.length==0) {
                    final MobileServiceList<ChatPublic> result = mChatTable
                            .orderBy("__createdAt", QueryOrder.Descending)
                            .execute().get();
                    Log.v(TAG, "Running syncing task");
                    chatList.clear();
                    for (ChatPublic cp:result){
                        chatList.add(cp);
                    }
                }
                else {
                    Log.v(TAG, "Inserting in background");

                    mChatTable.insert(chatPublics[0]);
                }

            } catch (Exception exception) {
                Log.e(TAG, "Exception starting");
                Log.e(TAG, exception.getMessage());
                Log.e(TAG, "Exception ending");
            }
            return chatList;
    }


    @Override
    protected void onPostExecute(List<ChatPublic> result){
        super.onPostExecute(result);
        if(result.size()!=0) {
            Log.v(TAG, "Setting messages");
            ChatArrayAdapter.clearMessage();
            ChatArrayAdapter chatArrayAdapter;
            chatArrayAdapter = new ChatArrayAdapter(context, R.layout.activity_chat_singlemessage);
            for (int i=0;i<result.size();) {
                //Log.v(TAG, "Adding message");

                ChatArrayAdapter.addMessage(result.get(i++));
            }

        }else {
            Log.v(TAG, "completed inserting in background");
        }
        if (button != null){
            button.setClickable(true);
        }
    }
    public AzureChatServiceInteraction(Context context, Button button){
        this(context);
        this.button = button;
    }
}
