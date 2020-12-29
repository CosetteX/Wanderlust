package com.example.wanderlust.dbmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.wanderlust.items.Bar_2_Item_new;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDao {
    private DBOpenHelper helper;
    //写入 ，不然会是出错，是空指针
    public ScheduleDao(Context context){
        helper = new DBOpenHelper(context);
    }
    public void add(Bar_2_Item_new bar2Itemnew) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "Insert into u_schedule(_id,content,imgUrl,type,location,schedule_date) values (?,?,?,?,?,?)";
        db.execSQL(sql,new Object[]
                {
                        bar2Itemnew.get_id(), bar2Itemnew.getScheduleContent(), bar2Itemnew.getScheduleImgUrl()
                        , bar2Itemnew.getScheduleType(), bar2Itemnew.getScheduleLocation(), bar2Itemnew.getScheduleDate()
                });
        db.close();
    }

    //查询
    public List<Bar_2_Item_new> queryScheduleList() {
        SQLiteDatabase db = helper.getWritableDatabase();
        List<Bar_2_Item_new> bar2Itemnews = new ArrayList<Bar_2_Item_new>();
        String sql = "select * from u_schedule";
        Cursor cursor = db.rawQuery(sql, new String[]{

        });
        db.execSQL("create table if not exists u_schedule(_id integer primary key,content varchar(20),imgUrl varchar(120),type varchar(20),location varchar(120),schedule_date varchar(60))");
        while(cursor.moveToNext()){
            bar2Itemnews.add(new Bar_2_Item_new(cursor.getLong(cursor.getColumnIndex("_id")),
                            cursor.getString(cursor.getColumnIndex("content")),
                            cursor.getString(cursor.getColumnIndex("imgUrl")),
                            cursor.getString(cursor.getColumnIndex("type")),
                            cursor.getString(cursor.getColumnIndex("location")),
                            cursor.getString(cursor.getColumnIndex("schedule_date"))
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
        database.delete("u_schedule", null, null);
        database.close();
    }


    /***
     * 查询返回的数据
     * @return
     */
    public List<Bar_2_Item_new> queyrByDateScheduleList(String schedule_date) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from u_schedule where schedule_date=?", new String[]{schedule_date});
        List<Bar_2_Item_new> bar2Itemnews = new ArrayList<Bar_2_Item_new>();
        while(cursor.moveToNext()){
            bar2Itemnews.add(new Bar_2_Item_new(cursor.getLong(cursor.getColumnIndex("_id")),
                            cursor.getString(cursor.getColumnIndex("content")),
                            cursor.getString(cursor.getColumnIndex("imgUrl")),
                            cursor.getString(cursor.getColumnIndex("type")),
                            cursor.getString(cursor.getColumnIndex("location")),
                            cursor.getString(cursor.getColumnIndex("schedule_date"))
                    )
            );
        }
        db.close();
        cursor.close();
        return bar2Itemnews;
    }
}
