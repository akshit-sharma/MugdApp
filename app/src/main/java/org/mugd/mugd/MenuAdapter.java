package org.mugd.mugd;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by akshit on 22-08-2015.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuHolder> {

    private static final String TAG = "MenuAdapter";

    Activity callingActivity;
    List<MenuItems> list;


    @Override
    public MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_adapter, parent, false);
        MenuHolder mh = new MenuHolder(callingActivity,v);
        return mh;
    }

    @Override
    public void onBindViewHolder(MenuHolder holder, int i) {
        holder.menuIcon.setImageDrawable(list.get(i).imageId);
        holder.menuText.setText(list.get(i).text);
        holder.menuItem = list.get(i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    MenuAdapter(Activity activity, List<MenuItems> list){
        this.callingActivity = activity;
        this.list = list;
    }

    static class MenuHolder extends  RecyclerView.ViewHolder{

        private static final String TAG = "MenuHolder";

        Activity callingActivity;

        CardView cv;

        MenuItems menuItem;

        ImageView menuIcon;
        TextView menuText;

        public MenuHolder(final Activity callingActivity,View itemView) {
            super(itemView);
            this.callingActivity = callingActivity;

//            DisplayMetrics displaymetrics = new DisplayMetrics();
//            callingActivity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//            int height = displaymetrics.heightPixels;
//            int width = displaymetrics.widthPixels;

            cv = (CardView) itemView.findViewById(R.id.mv);

            menuIcon = (ImageView) itemView.findViewById(R.id.mvImage);
            menuText = (TextView) itemView.findViewById(R.id.mvText);

            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity mainActivity = (MainActivity) callingActivity;
                    mainActivity.openFragment(menuText.getText().toString());
                }
            });

        }
    }

}
