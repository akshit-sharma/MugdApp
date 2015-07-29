package org.mugd.mugdapp;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;


public class CustomListItem extends BaseAdapter {

    Context context;

    String [] title;
    String [] imageId;
    String [] smallDescription;
    String [] venue;
    String [] datetime;
    String [] id;

    private HashMap<View,String> viewEvent;

    private static LayoutInflater inflater = null;

    public CustomListItem(CustomListViewShow mainActivity, String[] prgmNameList){
        title = prgmNameList;
        context = mainActivity;
        viewEvent = new HashMap();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageId = null;
        smallDescription = null;
        venue = null;
        datetime = null;
    }

    public CustomListItem(CustomListViewShow mainActivity, String[] prgmNameList, String[] prgmImages){
        this(mainActivity,prgmNameList);
        imageId = prgmImages;
    }

    public CustomListItem(CustomListViewShow mainActivity, String[] prgmNameList
            , String[] smallDescription,String[] venue, String[] datetime){
        this(mainActivity,prgmNameList);
        this.smallDescription = smallDescription;
        this.venue = venue;
        this.datetime = datetime;
    }

    public CustomListItem(CustomListViewShow mainActivity, String[] prgmNameList
            , String[] prgmImages, String[] smallDescription,String[] venue
            , String[] datetime, String[] id){
        this(mainActivity,prgmNameList, smallDescription, venue, datetime);
        this.imageId = prgmImages;
        this.id = id;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        ProgressBar pbView;
        rowView = inflater.inflate(R.layout.activity_custom_list_item, null);
        //holder.textView = (TextView) rowView.findViewById(R.id.title);
         //holder.textView.setText(title[position]);
        display(position,title,R.id.title,rowView);

        //holder.imageView = (ImageView) rowView.findViewById(R.id.smallImage);
        //holder.imageView.setImageResource(Integer.parseInt(imageId[position]));
        pbView = (ProgressBar) rowView.findViewById(R.id.downloadingImage);

        display(position,imageId,R.id.smallImage,rowView,pbView);

        display(position,smallDescription,R.id.smallDesc,rowView);

        display(position,venue,R.id.venue,rowView);

        display(position, datetime, R.id.datetime, rowView);

        viewEvent.put(rowView,id[position]);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id;
                Events event;

                Log.i("CustomListItem","clicked : "+view.toString());

                id = viewEvent.get(view);

                Iterator<Events> iterator = CustomListViewShow.alternative.iterator();
                while (iterator.hasNext()){
                    event = iterator.next();
                    if(id.equals(event.id)){
                        EventShow.event = event;
                    }
                }

                Intent intentEventShow = new Intent(context,EventShow.class);
                Bundle bundle = new Bundle();
                bundle.putString("ID",id);
                intentEventShow.putExtras(bundle);
                context.startActivity(intentEventShow);

                Log.i("CustomListItem","should open : "+id);
            }
        });
        return rowView;
    }

    public class Holder{
        TextView textView;
        ImageView imageView;
    }

    private boolean display(int position,Object[] value, int displayViewID, View inflatedView){
        if(value==null)
            return Boolean.parseBoolean(null);
        return display(value[position],inflatedView.findViewById(displayViewID));
    }

    private boolean display(int position,Object[] value, int displayViewID, View inflatedView, ProgressBar pbView){
        if(value==null)
            return Boolean.parseBoolean(null);
        return display(value[position],(ImageView) inflatedView.findViewById(displayViewID), pbView);
    }

    private boolean isInt(String item){
        boolean flag;

        flag = false;

        try {
            Integer.parseInt(item);
            flag = true;
        }catch (NumberFormatException nfe){

        }

        return flag;
    }

    private boolean display(Object value, View displaySpace){

        boolean everythingOK;
        everythingOK = false;

        if(value==null)
            return everythingOK;

        if(displaySpace instanceof TextView){
            ((TextView) displaySpace).setText(value.toString());
            everythingOK = true;
        }else if(displaySpace instanceof ImageView){
            display(value,(ImageView) displaySpace,null);
        }

        return everythingOK;

    }

    private boolean display(Object value, ImageView displaySpace,ProgressBar pbView) {
        Log.v("CustomListItem","ImageView detected");
        boolean everythingOK = false;

        if (isInt(value.toString())) {
            displaySpace.setImageResource(Integer.parseInt(value.toString()));
            everythingOK = true;
        }else{
            ImageView image = displaySpace;
            String image_url = value.toString();
            new DownloadImageTask(image,pbView).execute(image_url);
            Log.v("CustomListItem", "Image should be displayed");
            everythingOK = true;
        }

        return everythingOK;
    }

}
