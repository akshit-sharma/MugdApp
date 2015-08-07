package org.mugd.mugdapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;

/**
 * Created by akshi_000 on 28-07-2015.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    private final String TAG = "DownloadImageTask";

    ProgressBar pbImage;
    ImageView bmImage;


    public DownloadImageTask(ImageView bmImage,ProgressBar pbImage) {
        this(bmImage);
        this.pbImage = pbImage;
    }

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
        this.pbImage = null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        bmImage.setVisibility(View.INVISIBLE);
        if(pbImage!=null)
            pbImage.setVisibility(View.VISIBLE);
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        DownloadImageCacher dic = new DownloadImageCacher();
        if(dic.isCached(urldisplay)) {
            Log.i(TAG,"is already cached "+urldisplay);
            mIcon11 = dic.getCached(urldisplay);
        }else{
            Log.i(TAG,"is not cached "+urldisplay);
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                dic.putCached(mIcon11,urldisplay);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
        bmImage.setVisibility(View.VISIBLE);
        if(pbImage!=null)
            pbImage.setVisibility(View.INVISIBLE);
    }
}