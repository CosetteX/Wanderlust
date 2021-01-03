package com.example.wanderlust.Bar_1;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanderlust.dbmanager.DatabaseHelper;
import com.example.wanderlust.R;
import com.example.wanderlust.utils.StringUtils;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.os.FileUtils.copy;

public class Bar_1_Display extends Activity {

    TextView textView = null;
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
        //this.deleteDatabase("wanderlust_db1");
        //this.deleteDatabase("wanderlust_db.db");

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        refreshItems();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.bar_1_disp_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Bar_1_ItemAdapter adapter = new Bar_1_ItemAdapter(itemList);
        recyclerView.setAdapter(adapter);
    }

    private void refreshItems() {
        itemList.clear();
        Log.e("1.1",city.replace("_",""));
        Cursor cursor = db.query("Event", new String[]{"imgUrl","content", "date", "time"}, "location=?", new String[]{city.replace("_","")}, null, null, "date ,time");
        Long curDate = null;
        String curTime = "";
        String curContent = "";
        String curImgUrl = "";
        while (cursor.moveToNext()) {
            curDate = cursor.getLong(cursor.getColumnIndex("date"));
            curTime = cursor.getString(cursor.getColumnIndex("time"));
            curContent = cursor.getString(cursor.getColumnIndex("content"));
            curImgUrl = cursor.getString(cursor.getColumnIndex("imgUrl"));
            getPic(curContent,curDate,curTime,curImgUrl);
        }
        cursor.close();
    }

    private void getPic(String text, Long theDate, String theTime, String theImgUrl){
        Bar_1_Item item = new Bar_1_Item(text, StringUtils.getStringDate(theDate),theTime); // item(text,pictires)
        if(theImgUrl!=null){
            String [] urlList = theImgUrl.split(",");
            for (String u:urlList) {
                Bitmap bmp = null;
                try{
                    URL url = new URL("file://"+u);
                    InputStream in = url.openStream();
                    bmp = BitmapFactory.decodeStream(in);
                }
                catch (Exception e){
                    Log.e("BMP",e.toString());
                }
                item.addImg(bmp);
            }

        }
        itemList.add(item);
    }


}
