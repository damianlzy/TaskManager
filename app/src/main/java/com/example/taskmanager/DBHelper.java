package com.example.taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by 15031759 on 26/5/2017.
 */

public class DBHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_TASK = "task";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTableSql = "CREATE TABLE " + TABLE_TASK + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT ) ";
        db.execSQL(createNoteTableSql);
        Log.i("info", "created tables");


        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, "Buy Milk");
        values.put(COLUMN_DESCRIPTION, "Low fat");
        db.insert(TABLE_TASK, null, values);

        ContentValues values2 = new ContentValues();
        values2.put(COLUMN_NAME, "Post Letters");
        values2.put(COLUMN_DESCRIPTION, "Get stamps from car");
        db.insert(TABLE_TASK, null, values2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE " + TABLE_TASK + " ADD COLUMN module_name  TEXT ");
    }


    public long insertTask(String name, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, desc);
        long result = db.insert(TABLE_TASK, null, values);
        db.close();
        Log.d("SQL Insert",""+ result); //id returned, shouldn’t be -1
        return result;
    }

    public ArrayList<String> getAllTasks(){
        ArrayList<String> allTasks = new ArrayList<String>();

        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_NAME + "," + COLUMN_DESCRIPTION + " FROM " + TABLE_TASK;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String desc = cursor.getString(2);
                allTasks.add(id + " " + name + "\n" + desc);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return allTasks;
    }

}
