package org.mugd.mugd;

/**
 * Created by Adish Jain on 30-07-2015.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatArrayAdapter extends ArrayAdapter {

    private final static String TAG = "ChatArrayAdapter";

    private TextView chatText;
    private static List chatMessageList = new ArrayList();
    private LinearLayout singleMessageContainer;

    public void add(ChatPublic object) {
        Log.v(TAG, "Adding message");
        chatMessageList.add(object);
        super.add(object);
        Log.v(TAG, "Total Message in chatMessageList " + getCount());
    }
    public static void addMessage(ChatPublic object) {
    //    Log.v(TAG, "Adding message");
        chatMessageList.add(object);
    //    Log.v(TAG, "Total Message in chatMessageList "+chatMessageList.size());
    }

    public static void clearMessage(){
        chatMessageList = new ArrayList();
    }

    public ChatArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public int getCount() {
        return this.chatMessageList.size();
    }

    public ChatPublic getItem(int index) {
        return (ChatPublic) this.chatMessageList.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.activity_chat_singlemessage, parent, false);
        }
        singleMessageContainer = (LinearLayout) row.findViewById(R.id.singleMessageContainer);
        ChatPublic chatPublicObj = getItem(position);
        chatText = (TextView) row.findViewById(R.id.singleMessage);
        chatText.setText("  "+chatPublicObj.Message+"  ");
      //  chatText.setBackgroundResource(chatMessageObj.left ? R.drawable.bubble_left : R.drawable.bubble_right);
      //  singleMessageContainer.setGravity(chatPublicObj.left ? Gravity.LEFT : Gravity.RIGHT);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        if(chatPublicObj.Name!=null) {
            if (chatPublicObj.Name.contentEquals(MainActivity.tmDevice)) {
                singleMessageContainer.setBackgroundColor(Color.parseColor("#3976d6"));
                singleMessageContainer.setBackgroundResource(R.drawable.messageshape);


                layoutParams.gravity = RelativeLayout.ALIGN_PARENT_RIGHT;

                chatText.setLayoutParams(layoutParams);

            }
            else {
                singleMessageContainer.setBackgroundColor(Color.parseColor("#79b0cf"));
                //singleMessageContainer.setGravity(Gravity.LEFT);
                singleMessageContainer.setBackgroundResource(R.drawable.received_message_shape);
                //chatText.setGravity(Gravity.LEFT);
                layoutParams.gravity = RelativeLayout.ALIGN_PARENT_LEFT;

                chatText.setLayoutParams(layoutParams);

            }
        }

        return row;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }


}



