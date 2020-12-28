package com.example.wanderlust;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanderlust.todo.Test_AddInfo;

import java.util.ArrayList;
import java.util.List;

public class Bar_1_Display extends Activity {

    TextView textView = null;
    Button button = null;
    Intent intent = null;
    String city;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    int id = 0;
    Thread thread = null;
    int stop = 0;

    private List<Bar_1_Item> itemList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bar_1_disp);
        textView = findViewById(R.id.bar_1_disp_txt);
        intent = getIntent();
        city = intent.getStringExtra("message");
        textView.setText(city.split("_")[1]);

        //delete
        //this.deleteDatabase("wanderlust_db");

        dbHelper = new DatabaseHelper(this, "wanderlust_db", null, 1);
        db = dbHelper.getWritableDatabase();

        intent = new Intent(Bar_1_Display.this, Test_AddInfo.class);
        button = findViewById(R.id.disp_test_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("cityName", city);
                startActivity(intent);
                //refresh();
            }
        });

        refreshItemsNew();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.bar_1_disp_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Bar_1_ItemAdapter adapter = new Bar_1_ItemAdapter(itemList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop=1;
    }

    private void refreshItems() {
        itemList.clear();
        Cursor cursor = db.query("Event", new String[]{"id", "location", "date_", "time_"}, "location=?", new String[]{city}, null, null, "date_ ,time_");
        String row = "";
        while (cursor.moveToNext()) {
            row =   cursor.getString(cursor.getColumnIndex("id")) + "\n"
                    + cursor.getString(cursor.getColumnIndex("location")) + "\n"
                    + cursor.getString(cursor.getColumnIndex("date_"))+ "\n"
                    + cursor.getString(cursor.getColumnIndex("time_"));
            //Bar_1_Item item = new Bar_1_Item(row, R.drawable.icon_map);
            Bar_1_Item item = new Bar_1_Item(row);
            Cursor cursor2 = db.query("Picture", new String[]{"picture"}, "id=?", new String[]{cursor.getString(cursor.getColumnIndex("id"))}, null, null, null);
            while(cursor2.moveToNext()){
                Bitmap b;
                byte[] imgData = cursor2.getBlob(cursor2.getColumnIndex("picture"));
                b = BitmapFactory.decodeByteArray(imgData,0,imgData.length);
                item.addImg(b);
            }
            itemList.add(item);
        }
        cursor.close();
    }

    private void refreshItemsNew() {
        itemList.clear();
        Cursor cursor = db.query("Event", new String[]{"id","date_","time_"}, "location=?", new String[]{city}, null, null, "date_ ,time_");
        String curID = "";
        String curDate = "";
        String curTime = "";
        while (cursor.moveToNext()) {
            curID =   cursor.getString(cursor.getColumnIndex("id"));
            curDate = cursor.getString(cursor.getColumnIndex("date_"));
            curTime = cursor.getString(cursor.getColumnIndex("time_"));
            Log.e("ID",curID);
            handleID(curID,curDate,curTime);
        }
        cursor.close();
    }

    private void handleID(String theID, String theDate, String theTime){
        Cursor cursor = db.query("Text", new String[]{"text"}, "id=?", new String[]{theID}, null, null, null);
        boolean first = true;
        while(cursor.moveToNext()){
            String text = cursor.getString(cursor.getColumnIndex("text"));
            if(first){
                getPic(theID, text, theDate, theTime);
                first=false;
            }
            else{
                getPic(theID, text, "", "");
            }
        }
        cursor.close();
    }

    private void getPic(String theID, String text, String theDate, String theTime){
        Cursor cursor = db.query("Picture", new String[]{"picture"}, "id=?", new String[]{theID}, null, null, "sequence");
        Bar_1_Item item = new Bar_1_Item(text,theDate,theTime); // item(text,pictires)
        while(cursor.moveToNext()){
            Bitmap b;
            byte[] imgData = cursor.getBlob(cursor.getColumnIndex("picture"));
            b = BitmapFactory.decodeByteArray(imgData,0,imgData.length);
            item.addImg(b);
        }
        itemList.add(item);
    }


}


