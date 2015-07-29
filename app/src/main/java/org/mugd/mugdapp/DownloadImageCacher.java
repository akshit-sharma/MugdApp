package org.mugd.mugdapp;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by akshi_000 on 28-07-2015.
 *
 *
 *
 */

public class DownloadImageCacher {

    private static ArrayList<ImageCache> imageCaches;
    private static ArrayList<String> imageCacheUrls;

    private final static String TAG = "DownloadImageCacher";

    static {
        imageCaches = new ArrayList<>();
        imageCacheUrls = new ArrayList<>();
    }

    public boolean isCached(String url){

        Log.i(TAG,"isCached called");

        boolean cached = false;

        if(imageCacheUrls.contains(url)){
            cached = true;
        }

        return  cached;
    }

    public Bitmap getCached(String url){

        Log.i(TAG,"getCached called");

        Bitmap image = null;

        for(ImageCache cached : imageCaches){
            if(cached.url.equals(url)){
                image = cached.image;
            }
        }

        return image;
    }

    public void putCached(Bitmap image,String url, String id){

        Log.i(TAG,"putCached called");

        if(!isCached(url)){
            imageCaches.add(new ImageCache(image,url,id));
            imageCacheUrls.add(url);
        }
    }

    public void putCached(Bitmap image,String url){

        Log.i(TAG, "putCached called");

        if(!isCached(url)){
            imageCaches.add(new ImageCache(image,url));
            imageCacheUrls.add(url);
        }
    }


    private class ImageCache{
        Bitmap image;
        String url;
        String id;

        ImageCache(Bitmap image, String url, String id){
            this(image,url);
            this.id = id;
        }

        ImageCache(Bitmap image, String url){
            this.image = image;
            this.url = url;
        }

    }

}
