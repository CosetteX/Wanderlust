package com.example.wanderlust;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class Bar_1_Flag extends Activity {

    TextView textView = null;
    Button buttonAdd = null;
    EditText content = null;
    Intent intent = null;
    String city;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    int fid = 0;

    private List<Bar_1_Flag_Item> itemList = new ArrayList<Bar_1_Flag_Item>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bar_1_flag);
        textView = findViewById(R.id.bar_1_flag_title);
        content = findViewById(R.id.bar_1_flag_content);
        intent = getIntent();
        city = intent.getStringExtra("message");
        textView.setText(city.split("_")[1]);

        dbHelper = new DatabaseHelper(this, "wanderlust_db", null, 1);
        db = dbHelper.getWritableDatabase();
        fid = getID();

        // Button
        buttonAdd = findViewById(R.id.bar_1_flag_add);
        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("id", fid);
                values.put("province", city.split("_")[0]);
                values.put("city", city.split("_")[1]);
                values.put("content", content.getText().toString());
                values.put("top",0);
                db.insert("Flag", null, values);
                fid++;
                refreshID();
                refresh();
                Log.e("fid",fid+"");
            }
        });

        refresh();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.bar_1_flag_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Bar_1_Flag_ItemAdapter adapter = new Bar_1_Flag_ItemAdapter(itemList);
        recyclerView.setAdapter(adapter);
    }

    private void refresh(){
        itemList.clear();
        Cursor cursor = db.query("Flag", new String[]{"id","city","content"}, "province=?", new String[]{city.split("_")[0]}, null, null, "top desc");
        String curId = "";
        String curContent = "";
        String curCity = "";
        while (cursor.moveToNext()) {
            curId =  cursor.getString(cursor.getColumnIndex("id"));
            curContent = cursor.getString(cursor.getColumnIndex("content"));
            curCity = cursor.getString(cursor.getColumnIndex("city"));
            Bar_1_Flag_Item item = new Bar_1_Flag_Item(this,curId,curCity,curContent);
            itemList.add(item);
        }
        cursor.close();
    }

    public int getID(){
        Cursor cursor = db.query("CountID", new String[]{"fid"}, null,null ,null, null, null);
        int id = -1;
        while(cursor.moveToNext()){
            id = cursor.getInt(cursor.getColumnIndex("fid"));
        }
        cursor.close();
        if(id==-1){
            ContentValues values = new ContentValues();
            values.put("id", 0);
            values.put("fid", 0);
            db.insert("CountID",null, values);
            return 0;
        }
        return id;
    }

    public void refreshID(){
        ContentValues values = new ContentValues();
        values.put("fid", fid);
        db.update("CountID", values, null,null);
    }


}
