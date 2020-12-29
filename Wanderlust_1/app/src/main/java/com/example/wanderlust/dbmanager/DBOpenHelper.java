package com.example.wanderlust.dbmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;//版本
    private static final String DBNAME = "wanderlust_db.db";//数据库名字
    //创建数据库
    public DBOpenHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    /***
     * 1.日程内容
     * 2.图片地址
     * 3.日程类型
     * 4.发布日志位置
     * 5.发布日期的日期
     * 根据发布日期查询
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists u_schedule(_id integer primary key,content varchar(20),imgUrl varchar(120),type varchar(20),location varchar(120),schedule_date varchar(60))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
