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

    static HashMap<String,Class> fieldsMaped;

    static {
        fieldsMaped = new HashMap();

        fieldsMaped.put("id",String.class);
        fieldsMaped.put("date",Date.class);
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

    public static String[] particularFieldAsStringArray(String field,ArrayList<Events> allEvents) {
        String[] displayTerms;
        ArrayList allItems;

        allItems = particularFieldAsArray(field, allEvents);

        if (allItems == null) {
            Log.e("EventsStatic", "value of allItems is null !!");

        } else {

            displayTerms = new String[allItems.size()];

            Iterator<String> iterator = allItems.iterator();

            int index = 0;
            while (iterator.hasNext()) {
                displayTerms[index++] = iterator.next();
            }

            if (index != allItems.size()) {
                Log.e("Events", "Something is wrong in converting to String Array");
            }

            return  displayTerms;
        }

        return null;

    }

    public static ArrayList particularFieldAsArray(String field,ArrayList<Events> allEvents){

        Class type = fieldsMaped.get(field);

        Log.v("EventsStatic","name is "+fieldsMaped.get(field).getName());
        Log.v("EventsStatic","class is "+fieldsMaped.get(field).getClass());

        if(fieldsMaped.get(field).getName().toString().contains("String")) {
            Log.v("EventsStatic", "String class detected");
            ArrayList<String> list = new ArrayList<>();

            for (Object eventObject : allEvents.toArray()) {
                if (eventObject instanceof Events) {
                    Events events = (Events) eventObject;
                    list.add((String)extract(events, field));
                    Log.v("EventsStatic", "Added " + extract(events, field) + " for " + field);
                }
            }

            return list;
        }
        if(fieldsMaped.get(field).getName().toString().contains("Date")) {
            Log.v("EventsStatic", "Date class detected");
            ArrayList<String> list = new ArrayList<>();
            SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMM d");

            for (Object eventObject : allEvents.toArray()) {
                if (eventObject instanceof Events) {
                    Events events = (Events) eventObject;
                    String dateValue = formatter.format((Date) extract(events, field));
                    list.add(dateValue);
                    Log.v("EventsStatic", "Added " + extract(events, field) + " for " + field);
                }
            }

            return list;
        }

        Log.e("EventsStatic", "This should not appear !!!");

        return null;

    }

    private static Object extract(Events event, String field){

        Object value;

        switch (field){
            case "id" : return event.id;
            case "date" : return event.Date;
            case "Title" : return event.Title;
            case "Desc" : return event.Desc;
            case "image" : return event.image;
            case "type" : return event.type;
            case "college" : return event.college;
            case "issueBy" : return event.issueBy;
            case "containerName" : return event.containerName;
            case "resourceName" : return event.resourceName;
            case "sasQueryString" : return event.sasQueryString;
            case "imageUri" : return event.imageUri;
            case "__createdAt" : return event.__createdAt;
            case "__updatedAt" : return event.__updatedAt;
            case "__version" : return event.__version;
            default: return null;
        }

    }

}
