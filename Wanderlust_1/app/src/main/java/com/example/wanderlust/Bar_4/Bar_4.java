package com.example.wanderlust.Bar_4;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wanderlust.R;

import com.example.wanderlust.dbmanager.DatabaseHelper;

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

        // layout report
        RelativeLayout layout_report = view.findViewById(R.id.report);
        layout_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Bar_4_Report.class);
                startActivity(intent); // report
            }
        });
        // layout lock
        RelativeLayout layout_lock = view.findViewById(R.id.lock);
        layout_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor =db.query("Setting",new String[]{"password"},null,null,null,null,null);
                String pwd = null;
                while (cursor.moveToNext()) {
                    pwd = cursor.getString(cursor.getColumnIndex("password"));
                }
                Intent intent = new Intent(getContext(), Bar_4_Lock.class);
                intent.putExtra("pwd",pwd);
                startActivityForResult(intent,2); // lock
            }
        });

        // layout clean
        RelativeLayout layout_delete = view.findViewById(R.id.clean);
        layout_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                //builder.setIcon(R.mipmap.ic_launcher);
                builder.setIcon(R.drawable.icon_delete);
                builder.setTitle("确定清除数据？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete("Event",null,null);
                        db.delete("Flag",null,null);
                        db.delete("ProvinceColor",null,null);
                        Toast.makeText(getContext(),"数据清除完毕",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        // layout about us
        RelativeLayout layout_us = view.findViewById(R.id.about_us);
        layout_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Bar_4_Us.class);
                startActivity(intent); // about us
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
        else if (requestCode == 2&& resultCode == 1){
            Log.e("lock","--");
            String res  = data.getStringExtra("pwd");
            ContentValues values = new ContentValues();
            values.put("password", res);
            db.update("Setting",values,null,null);
        }

    }
}
