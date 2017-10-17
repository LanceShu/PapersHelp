package com.example.race.papershelp.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lance on 2017/10/15.
 */

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private Context context;

    public static final String CREATE_USER = "create table User("
            +"id integer primary key autoincrement,"
            +"uPhone text,"
            +"uPass text)";

    public static final String CREATE_APPLY = "create table Apply("
            +"id integer primary key autoincrement,"
            +"aUser text,"
            +"aName text,"
            +"aPhone text,"
            +"aIdentity text,"
            +"aMail text)";

    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_APPLY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists User");
        db.execSQL("drop table if exists Apply");
    }
}
