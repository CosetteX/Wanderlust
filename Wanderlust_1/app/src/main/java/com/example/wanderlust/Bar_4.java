package com.example.wanderlust;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wanderlust.Bar_1.Bar_1_Color;
import com.example.wanderlust.dbmanager.DatabaseHelper;
import com.luck.picture.lib.PictureSelector;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.io.InputStream;
import java.net.URL;


public class Bar_4 extends Fragment {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    TextView usr_name = null;
    ImageView usr_head = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bar_4_setting, container, false);
        dbHelper = new DatabaseHelper(getContext());
        db = dbHelper.getWritableDatabase();
        // If empty
        Cursor cursor =db.query("Setting",new String[]{"name"},null,null,null,null,null);
        if(cursor.getCount()==0){
            ContentValues values = new ContentValues();
            values.put("name", "Bruce Wane");
            values.put("url", "");
            values.put("password", "123456");
            values.put("color", 0x00ffff);
            db.insert("Setting",null,values);
        }
        // Get
        cursor =db.query("Setting",new String[]{"name,url"},null,null,null,null,null);
        String curName = null;
        String curUrl = "";
        while (cursor.moveToNext()) {
            curName = cursor.getString(cursor.getColumnIndex("name"));
            curUrl = cursor.getString(cursor.getColumnIndex("url"));
        }
        // usr_name
        usr_name = view.findViewById(R.id.name_1);
        usr_name.setText(curName);

        // usr head
        usr_head =view.findViewById(R.id.usr_1);
        if(!curUrl.equals("")){
            try{
                URL url = new URL("file://"+curUrl);
                InputStream in = url.openStream();
                Bitmap bmp = BitmapFactory.decodeStream(in);
                usr_head.setImageBitmap(bmp);
            }
            catch (Exception e){}
        }


        // layout usr
        RelativeLayout layout_usr = view.findViewById(R.id.Head);
        layout_usr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Bar_4_Usr.class);
                intent.putExtra("name", usr_name.getText().toString());
                startActivityForResult(intent,1); // 1: usr
            }
        });



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            String res  = data.getStringExtra("name");
            usr_name.setText(res);
            // db
            ContentValues values = new ContentValues();
            values.put("name", res);
            db.update("Setting",values,null,null);
        }
        else if (requestCode == 1 && resultCode == 2) {
            String res = data.getStringExtra("url");
            Log.e("photo","ok");
            // cur
            try{
                URL url = new URL("file://"+res);
                InputStream in = url.openStream();
                Bitmap bmp = BitmapFactory.decodeStream(in);
                usr_head.setImageBitmap(bmp);
            }
            catch (Exception e){}
            // db
            ContentValues values = new ContentValues();
            values.put("url", res);
            db.update("Setting",values,null,null);
        }
    }
}
