package com.example.wanderlust;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

public class Bar_1_Flag_Item extends SwipeMenuLayout{

    private String id;
    private String content;
    private String city;
    private int top;
    DatabaseHelper dbHelper = null;
    SQLiteDatabase db = null;

    void init(){
        dbHelper = new DatabaseHelper(getContext(), "wanderlust_db", null, 1);
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Flag", new String[]{"top"}, "id=?", new String[]{id}, null, null, null);
        while (cursor.moveToNext()) {
            top =  cursor.getInt(cursor.getColumnIndex("top"));
        }
        cursor.close();
    }

    Bar_1_Flag_Item(Context context,String id, String city, String content){
        super(context,null);
        this.id = id;
        this.city = city;
        this.content = content;
        init();
    }

    public Bar_1_Flag_Item(Context context) {
        super(context,null);
    }
    public Bar_1_Flag_Item(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public Bar_1_Flag_Item(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public String getContent() {
        return content;
    }

    public String getCity() {
        return city;
    }

    public String getFlagId() {
        return id;
    }

    public int getCurTop() {
        return top;
    }

    public void changeTop(){
        top = 1 - top;
        ContentValues values = new ContentValues();
        values.put("top", top);
        db.update("Flag", values, "id=?",new String[]{id});
    }

    public void deleteItem(){
        db.delete("Flag", "id=?", new String[]{id});
        db.close();
    }



}
