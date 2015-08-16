package org.mugd.mugdapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


public class PublicChatFragment extends Fragment {

    private static final String TAG = "PublicChatFragment";

    private Activity activity;

    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private Button buttonSend;

    public PublicChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v(TAG, "onCreate called");

        chatArrayAdapter = new ChatArrayAdapter(activity.getApplicationContext(), R.layout.activity_chat_singlemessage);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.v(TAG,"onCreateView called");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_public_chat, container, false);
        buttonSend = (Button) view.findViewById(R.id.chatButton);
        listView = (ListView) view.findViewById(R.id.chatList);
        chatText = (EditText) view.findViewById(R.id.chatBox);

        this.setListeners();
        this.listConfig();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Log.v(TAG, "onAttach called");

        if (activity==null){
            Log.e(TAG, "Why is Activity NULL !! ");
        }else {
            this.activity = activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.v(TAG, "onDetach called");

    }

    private void setListeners(){

        chatText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendChatMessage();
            }
        });

    }

    private void listConfig(){

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });

    }

    private boolean sendChatMessage(){
        // chatArrayAdapter.add(new ChatPublic(chatText.getText().toString(),getApplicationContext()));

        // TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        //String a =  telephonyManager.getDeviceId();
        String a = "hello";
        ChatPublic chatpublic = new ChatPublic(chatText.getText().toString(),activity,a);
        AzureChatServiceInteraction acsi = new AzureChatServiceInteraction(activity);
        acsi.execute(chatpublic);
        chatText.setText("");
//        side = !side;
        return true;
    }

}
