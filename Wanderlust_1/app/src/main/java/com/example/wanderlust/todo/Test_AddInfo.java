package com.example.wanderlust.todo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.wanderlust.DatabaseHelper;
import com.example.wanderlust.R;

import java.io.ByteArrayOutputStream;

public class Test_AddInfo extends Activity {

    int id = 0;
    Button button1 = null;
    Button button2 = null;
    Button button3 = null;
    ImageView imageView = null;
    EditText Ecity,Edate,Etime,Etype,Eid,Etext;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Intent intent;
    String city;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_addinfo);
        dbHelper = new com.example.wanderlust.DatabaseHelper(this,"wanderlust_db",null,1);
        db = dbHelper.getWritableDatabase();
        id = getID();

        button1 = findViewById(R.id.test_addinfo_add);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("id", id);
                values.put("location", city);
                values.put("date_", Edate.getText().toString());
                values.put("time_", Etime.getText().toString());
                values.put("undo", 0);
                values.put("type",Etype.getText().toString());
                db.insert("Event", null, values);
                id++;
                refreshID();
            }
        });

        button2 = findViewById(R.id.test_addinfo_addtext);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("id", Eid.getText().toString());
                values.put("text", Etext.getText().toString());
                db.insert("Text", null, values);
            }
        });

        Ecity=findViewById(R.id.test_addinfo_city);
        Edate=findViewById(R.id.test_addinfo_date);
        Etime=findViewById(R.id.test_addinfo_time);
        Etype=findViewById(R.id.test_addinfo_type);
        Eid=findViewById(R.id.test_addinfo_id);
        Etext=findViewById(R.id.test_addinfo_text);

        intent = getIntent();
        city = intent.getStringExtra("cityName");


        button3 = findViewById(R.id.test_choose_photo);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 2);

                //Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //系统常量， 启动相机的关键
                //startActivityForResult(openCameraIntent, 3);
            }
        });
        imageView = findViewById(R.id.test_photo);
    }

    // 处理返回的相片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                imageView.setImageURI(uri);
                Log.e("uri",uri.toString());

                try{
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    ContentValues values = new ContentValues();
                    values.put("id", Eid.getText().toString());
                    values.put("picture", baos.toByteArray());
                    values.put("sequence",0);
                    db.insert("Picture", null, values);
                }
                catch (Exception e){
                    System.out.println(e);
                }

            }
        }
    }

    public int getID(){
        Cursor cursor = db.query("CountID", new String[]{"id"}, null,null ,null, null, null);
        int id = -1;
        while(cursor.moveToNext()){
            id = cursor.getInt(cursor.getColumnIndex("id"));
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
        values.put("id", id);
        db.update("CountID", values, null,null);
    }

}




