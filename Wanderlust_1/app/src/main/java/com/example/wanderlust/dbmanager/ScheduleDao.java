package com.example.wanderlust.dbmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.wanderlust.items.Bar_2_Item;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDao {
    private DatabaseHelper helper;
    //写入 ，不然会是出错，是空指针
    public ScheduleDao(Context context){
        helper = new DatabaseHelper(context);
    }
    public void add(Bar_2_Item bar2Itemnew) {
        SQLiteDatabase db = helper.getWritableDatabase();
        /*String sql = "Insert into Event(_id,content,imgUrl,type,location,schedule_date) values (?,?,?,?,?,?)";
        Log.e("Check", bar2Itemnew.get_id()+ bar2Itemnew.getScheduleContent()+ bar2Itemnew.getScheduleImgUrl()+bar2Itemnew.getScheduleType()+bar2Itemnew.getScheduleLocation()+bar2Itemnew.getScheduleDate());
        db.execSQL(sql,new Object[]
                {
                        bar2Itemnew.get_id(), bar2Itemnew.getScheduleContent(), bar2Itemnew.getScheduleImgUrl()
                        , bar2Itemnew.getScheduleType(), bar2Itemnew.getScheduleLocation(), bar2Itemnew.getScheduleDate()
                });*/
        ContentValues values = new ContentValues();
        values.put("id", bar2Itemnew.get_id());
        values.put("location", bar2Itemnew.getScheduleLocation());
        values.put("date", bar2Itemnew.getScheduleDate());
        values.put("time", bar2Itemnew.getScheduleTime());
        values.put("content",bar2Itemnew.getScheduleContent());
        values.put("imgUrl",bar2Itemnew.getScheduleImgUrl());
        values.put("type",bar2Itemnew.getScheduleType());
        values.put("undo", 0);
        db.insert("Event", null, values);
        db.close();
    }

    //查询
    public List<Bar_2_Item> queryScheduleList() {
        SQLiteDatabase db = helper.getWritableDatabase();
        List<Bar_2_Item> bar2Itemnews = new ArrayList<Bar_2_Item>();

       // String sql = "select * from Event";
        String sql = "select * from Event ORDER BY 0+date ASC";
        Cursor cursor = db.rawQuery(sql, new String[]{});
        //db.execSQL("create table if not exists Event(id integer primary key,content varchar(20),imgUrl varchar(120),type varchar(20),location varchar(120),schedule_date varchar(60))");

        while(cursor.moveToNext()){
            bar2Itemnews.add(new Bar_2_Item(cursor.getLong(cursor.getColumnIndex("id")),
                            cursor.getString(cursor.getColumnIndex("content")),
                            cursor.getString(cursor.getColumnIndex("imgUrl")),
                            cursor.getString(cursor.getColumnIndex("type")),
                            cursor.getString(cursor.getColumnIndex("location")),
                           // cursor.getString(cursor.getColumnIndex("date")),
                            cursor.getLong(cursor.getColumnIndex("date")),
                            cursor.getString(cursor.getColumnIndex("time"))
                    )
            );
        }
        return bar2Itemnews;
    }


    /***
     * 清空数据
     */
    public void deleteScheduleData() {
        SQLiteDatabase database = helper.getWritableDatabase();
        database.delete("Event", null, null);
        database.close();
    }


    /***
     * 查询返回的数据
     * @return
     */
    public List<Bar_2_Item> queyrByDateScheduleList(String schedule_date) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Event where date=?", new String[]{schedule_date});
        List<Bar_2_Item> bar2Itemnews = new ArrayList<Bar_2_Item>();
        while(cursor.moveToNext()){
            bar2Itemnews.add(new Bar_2_Item(cursor.getLong(cursor.getColumnIndex("id")),
                            cursor.getString(cursor.getColumnIndex("content")),
                            cursor.getString(cursor.getColumnIndex("imgUrl")),
                            cursor.getString(cursor.getColumnIndex("type")),
                            cursor.getString(cursor.getColumnIndex("location")),
                            //cursor.getString(cursor.getColumnIndex("date")),
                            cursor.getLong(cursor.getColumnIndex("date")),
                            cursor.getString(cursor.getColumnIndex("time"))
                    )
            );
        }
        db.close();
        cursor.close();
        return bar2Itemnews;
    }
}
