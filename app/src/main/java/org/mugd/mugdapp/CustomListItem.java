package org.mugd.mugdapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomListItem extends BaseAdapter {

    Context context;

    String [] title;
    String [] imageId;
    String [] smallDescription;
    String [] venue;
    String [] datetime;

    private static LayoutInflater inflater = null;

    public CustomListItem(CustomListViewShow mainActivity, String[] prgmNameList){
        title = prgmNameList;
        context = mainActivity;
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
            , String[] datetime){
        this(mainActivity,prgmNameList, smallDescription, venue, datetime);
        this.imageId = prgmImages;
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
        rowView = inflater.inflate(R.layout.activity_custom_list_item, null);
        //holder.textView = (TextView) rowView.findViewById(R.id.title);
         //holder.textView.setText(title[position]);
        display(position,title,R.id.title,rowView);

        //holder.imageView = (ImageView) rowView.findViewById(R.id.smallImage);
        //holder.imageView.setImageResource(Integer.parseInt(imageId[position]));
        display(position,imageId,R.id.smallImage,rowView);

        display(position,smallDescription,R.id.smallDesc,rowView);

        display(position,venue,R.id.venue,rowView);

        display(position,datetime,R.id.datetime,rowView);



        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("CustomListItem",view.toString()+" is clicked");
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
            Log.v("CustomListItem","ImageView detected");

                if (isInt(value.toString())) {
                    ((ImageView) displaySpace).setImageResource(Integer.parseInt(value.toString()));
                    everythingOK = true;
                }else{
                    int loader = R.drawable.abc_spinner_mtrl_am_alpha;
                    ImageView image = (ImageView) displaySpace;
                    String image_url = value.toString();
                    Log.v("CustomListItem","Image url : "+image_url);
                    ImageLoader imgLoader = new ImageLoader(context);
                    imgLoader.DisplayImage(image_url,loader,image);
                    Log.v("CustomListItem","Image should be displayed");
                    everythingOK = true;
                }
        }

        return everythingOK;

    }

}
