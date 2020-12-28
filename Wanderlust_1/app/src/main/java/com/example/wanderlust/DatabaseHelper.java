package com.example.wanderlust;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists Event (id varchar(20) PRIMARY KEY,location varchar(20),date_ date, time_ varchar(10), undo int, type varchar(20))";
        db.execSQL(sql);
        sql = "create table if not exists Picture (id varchar(20), picture BLOB, sequence int)";
        db.execSQL(sql);
        sql = "create table if not exists Text (id varchar(20) PRIMARY KEY, text varchar(200))";
        db.execSQL(sql);
        sql = "create table if not exists Travel (id varchar(20) PRIMARY KEY, date_s date, time_s varchar(10), source varchar(20),date_d date, time_d varchar(10), destination varchar(20), transportation varchar(20))";
        db.execSQL(sql);
        // Add a fid count
        sql = "create table if not exists CountID (id int, fid int)";
        db.execSQL(sql);
        // Flag
        sql = "create table if not exists Flag (id varchar(20) PRIMARY KEY,province varchar(20),city varchar(20),content varchar(100),top int)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}