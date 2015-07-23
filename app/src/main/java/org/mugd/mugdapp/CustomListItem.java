package org.mugd.mugdapp;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class CustomListItem extends BaseAdapter {

    String [] result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater = null;

    public CustomListItem(CustomListViewShow mainActivity, String[] prgmNameList, int[] prgmImages){
        result = prgmNameList;
        context = mainActivity;
        imageId = prgmImages;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return result.length;
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
        holder.textView = (TextView) rowView.findViewById(R.id.textView1);
        holder.imageView = (ImageView) rowView.findViewById(R.id.imageView1);
        holder.textView.setText(result[position]);
        holder.imageView.setImageResource(imageId[position]);
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

}
