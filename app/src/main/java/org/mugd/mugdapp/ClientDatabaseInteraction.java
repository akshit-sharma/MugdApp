package org.mugd.mugdapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by akshi_000 on 27-07-2015.
 *
 *
 *
 */
public class ClientDatabaseInteraction extends SQLiteOpenHelper{

    private static final String TAG = "ClientDatabase";

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "MugdLoacal";

    private static final String[] TABLE_NAMES = {"Events"};

    private SQLiteDatabase db;


    private HashMap<String,Class> getDataClass(String TABLE_NAME){
        HashMap<String,Class> fieldsMaped = null;
        switch (TABLE_NAME){
            case "Events" : return Events.fieldsMaped;
        }
        return fieldsMaped;
    }

    public ClientDatabaseInteraction(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public ClientDatabaseInteraction(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private void createTable(SQLiteDatabase db, String TABLE_NAME){
        HashMap<String,Class> fieldsMaped;
        HashMap<String,String> columnsSchema;
        StringBuilder createTableCommand;

        int columnCount = 0;

        createTableCommand = new StringBuilder();

        columnsSchema = new HashMap<>();
        fieldsMaped = getDataClass(TABLE_NAME);
        createTableCommand.append("CREATE TABLE "+TABLE_NAME+"(");

        for(String field_name : fieldsMaped.keySet()){
            if(columnCount!=0){
                createTableCommand.append(",");
            }
            columnCount++;
            if(fieldsMaped.get(field_name).getName().toString().contains("Date")) {
                columnsSchema.put(field_name,"DATETIME");
                createTableCommand.append(field_name + " DATETIME");
            }
            if(fieldsMaped.get(field_name).getName().toString().contains("String")) {
                columnsSchema.put(field_name,"TEXT");
                createTableCommand.append(field_name+" TEXT");
            }
        }
        createTableCommand.append(")");
        Log.v(TAG, "Create Table query is");
        Log.v(TAG,createTableCommand.toString());
        db.execSQL(createTableCommand.toString());
    }

    private void createTables(SQLiteDatabase db) {
        for(String TABLE_NAME : TABLE_NAMES){
            createTable(db, TABLE_NAME);
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db){
        createTables(db);
    }

    private void dropTable(SQLiteDatabase db,String TABLE_NAME){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private void dropTables(SQLiteDatabase db){
        for(String TABLE_NAME : TABLE_NAMES){
            dropTable(db,TABLE_NAME);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTables(db);
        onCreate(db);
    }

    private String getExtractedData(Object data,String column,String Type){
        Object value;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        value = null;

        if(data instanceof Events) {
            value = Events.extract((Events) data, column);
        }

        if(Type.contains("String")){

        }else if(Type.contains("Date")){
            value = (Date) value;
        }
        return value.toString();
    }


    public void insertCommand(String TABLE_NAME,Object data){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        HashMap<String,Class> columns = getDataClass(TABLE_NAME);
        String columnValue;
        String columnType;

        Log.v(TAG,"Table : "+TABLE_NAME);

        for(String column : columns.keySet()) {
            columnType = columns.get(column).getName().toString();
            columnValue = getExtractedData(data, column, columnType);
            Log.v(TAG,"Column: "+column+" Value: "+values);
            values.put(column,columnValue);
        }

        db.insert(TABLE_NAME,null,values);

    }

    public void insertCommand(String TABLE_NAME,Object[] datas){
        for(Object data:datas)
            insertCommand(TABLE_NAME,data);
    }
    public void insertCommand(String TABLE_NAME,List datas){
        for(Object data:datas)
            insertCommand(TABLE_NAME,data);
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }


    }

}
