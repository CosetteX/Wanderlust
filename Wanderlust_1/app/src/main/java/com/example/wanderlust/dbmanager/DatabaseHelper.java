package com.example.wanderlust.dbmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    static String NAME = "wanderlust_db";
    static int VERSION = 1;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists Event (id varchar(20) PRIMARY KEY,location varchar(20),date varchar(10), time varchar(10),content varchar(100), imgUrl varchar(1000), undo int, type varchar(20))";
        db.execSQL(sql);
        sql = "create table if not exists Travel (id varchar(20) PRIMARY KEY, date_s date, time_s varchar(10), source varchar(20),date_d date, time_d varchar(10), destination varchar(20), transportation varchar(20))";
        db.execSQL(sql);
        // Flag
        sql = "create table if not exists Flag (id varchar(20) PRIMARY KEY,province varchar(20),city varchar(20),content varchar(100),top int)";
        db.execSQL(sql);
        // Info
        sql = "create table if not exists Setting (name varchar(50) PRIMARY KEY, url varchar(200), color int, password varchar(100))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}