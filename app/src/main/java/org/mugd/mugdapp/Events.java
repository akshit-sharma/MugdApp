package org.mugd.mugdapp;

import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by akshi_000 on 24-07-2015.
 *
 * Used to store data from Events Table
 *
 */
public class Events {

    String id;
    Date Date;
    String Title;
    String Desc;
    String image;
    String type;
    String college;
    String issueBy;
    String containerName;
    String resourceName;
    String sasQueryString;
    String imageUri;
    Date __createdAt;
    Date __updatedAt;
    String __version;
//    Date __deleted;

    static boolean small;
    private static int smallLimit = 15;
    
    private static final String TAG = "Events";

    static HashMap<String,Class> fieldsMaped;

    static {
        fieldsMaped = new HashMap();

        fieldsMaped.put("id",String.class);
        fieldsMaped.put("Date",Date.class);
        fieldsMaped.put("Title",String.class);
        fieldsMaped.put("Desc",String.class);
        fieldsMaped.put("image",String.class);
        fieldsMaped.put("type",String.class);
        fieldsMaped.put("college",String.class);
        fieldsMaped.put("issueBy",String.class);
        fieldsMaped.put("containerName",String.class);
        fieldsMaped.put("resourceName",String.class);
        fieldsMaped.put("sasQueryString",String.class);
        fieldsMaped.put("imageUri",String.class);
        fieldsMaped.put("__createdAt",Date.class);
        fieldsMaped.put("__updatedAt",Date.class);
        fieldsMaped.put("__version",String.class);
    }


    /*
    public static <T extends Object> ArrayList<T> particularFieldAsArray(String field, ArrayList<T> allList
            , Class<T> type){

            ArrayList<T> list = allList;




            //return type.cast();
            return null;
    }
    */

    public static String[] particularFieldAsStringArray(String field,ArrayList<Events> allEvents, boolean small) {
        Events.small = small;

        String[] displayTerms;
        ArrayList allItems;

        allItems = particularFieldAsArray(field, allEvents);

        if (allItems == null) {
            Log.e(TAG, "value of allItems is null !!");

        } else {

            displayTerms = new String[allItems.size()];

            Iterator<String> iterator = allItems.iterator();

            int index = 0;
            while (iterator.hasNext()) {
                displayTerms[index++] = iterator.next();
            }

            if (index != allItems.size()) {
                Log.e(TAG, "Something is wrong in converting to String Array");
            }

            return  displayTerms;
        }

        return null;

    }

    public static String[] particularFieldAsStringArray(String field,ArrayList<Events> allEvents) {

        return particularFieldAsStringArray(field,allEvents,false);
    }

    public static ArrayList particularFieldAsArray(String field,ArrayList<Events> allEvents){

        Class type = fieldsMaped.get(field);

        Log.v(TAG,"name is "+fieldsMaped.get(field).getName());
        Log.v(TAG,"class is "+fieldsMaped.get(field).getClass());

        if(fieldsMaped.get(field).getName().toString().contains("String")) {
            Log.v(TAG, "String class detected");
            ArrayList<String> list = new ArrayList<>();

            for (Object eventObject : allEvents.toArray()) {
                if (eventObject instanceof Events) {
                    Events events = (Events) eventObject;
                    list.add((String)extract(events, field,Events.small));
                    Log.v(TAG, "Added " + extract(events, field,Events.small) + " for " + field);
                }
            }

            return list;
        }
        if(fieldsMaped.get(field).getName().toString().contains("Date")) {
            Log.v(TAG, "Date class detected");
            ArrayList<String> list = new ArrayList<>();

            for (Object eventObject : allEvents.toArray()) {
                if (eventObject instanceof Events) {
                    Events events = (Events) eventObject;
                    String dateValue = extract(events, field, true);
                    list.add(dateValue);
                    Log.v(TAG, "Added " + extract(events, field,true) + " for " + field);
                }
            }

            return list;
        }

        Log.e(TAG, "This should not appear !!!");

        return null;

    }



    public static String extract(Events event, String field, boolean small){

        Events.small = small;

        SimpleDateFormat formatter;

        if(Events.small){
            formatter = new SimpleDateFormat("EEE, MMM d");
        }else {
            formatter = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        }

        Object value;
        String returnValue= null;

        switch (field){
            case "id" : returnValue = event.id;
                            break;
            case "Date" : returnValue = formatter.format(event.Date);
                           break;

            case "Title" : returnValue = event.Title;
                            break;
            case "Desc" :
                if(!small) {
                    returnValue = event.Desc;
                }
                else {
                    if(event.Desc.length()>smallLimit) {
                        int spaceIndex = event.Desc.substring(0, smallLimit).lastIndexOf(" ");
                        if (spaceIndex != -1 && ((spaceIndex-smallLimit)<5)) {
                            returnValue = event.Desc.substring(0, spaceIndex) + " ...";
                        } else {
                            returnValue = event.Desc.substring(0, smallLimit) + "...";
                        }
                    }
                    else {
                        returnValue = event.Desc;
                    }
                }
                break;
            case "image" : returnValue = event.image;
                break;
            case "type" : returnValue = event.type;
                break;
            case "college" :
                if(!small) {
                    returnValue = event.college;
                }
                else {
                    if(event.college.length()>smallLimit){
                        int smallLimit = Events.smallLimit-8;
                        int comma = event.college.indexOf(",");
                        if ((comma < smallLimit) && (comma != -1)) {
                            returnValue = event.college.substring(0, comma + 1);
                        }
                        else {
                            returnValue = event.college.substring(0, smallLimit) + "...";
                        }
                    }else {
                        returnValue = event.college;
                    }
                }
                break;
            case "issueBy" : returnValue = event.issueBy;
                break;
            case "containerName" : returnValue = event.containerName;
                break;
            case "resourceName" : returnValue = event.resourceName;
                break;
            case "sasQueryString" : returnValue = event.sasQueryString;
                break;
            case "imageUri" : returnValue = event.imageUri;
                break;
            case "__createdAt" : returnValue = formatter.format(event.__createdAt);
                break;
            case "__updatedAt" : returnValue = formatter.format(event.__updatedAt);
                break;
            case "__version" : returnValue = event.__version;
                break;
        }

        Log.v(TAG,"returnValue is "+returnValue);
        
        return returnValue;

    }

    public static String extract(Events event, String field){

        return extract(event, field, false);

    }

}
