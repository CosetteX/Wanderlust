package com.example.wanderlust;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.wanderlust.dbmanager.DatabaseHelper;

public class Bar_4_Report extends FragmentActivity {

    TextView text_1 = null;
    TextView text_2 = null;
    TextView text_3 = null;
    TextView text_4 = null;
    TextView text_5 = null;

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bar_4_report);
        text_1 = findViewById(R.id.text_1);
        text_2 = findViewById(R.id.text_2);
        text_3 = findViewById(R.id.text_3);
        text_4 = findViewById(R.id.text_4);
        text_5 = findViewById(R.id.text_5);
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        handle_1();
        handle_2();
        handle_3();
        handle_4();
        handle_5();
    }

    private void handle_1(){
        String query ="SELECT DISTINCT location FROM Event";
        Cursor cursor = db.rawQuery(query, null);
        int cnt = cursor.getCount();
        text_1.setText(text_1.getText().toString().replace("$",cnt+""));
    }

    private void handle_2(){
        String query ="SELECT location, count(*) AS cnt FROM Event GROUP BY location ORDER BY cnt DESC";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToNext();
        String city = cursor.getString(cursor.getColumnIndex("location"));
        text_2.setText(text_2.getText().toString().replace("$",city));
    }

    private void handle_3(){
        String query ="SELECT DISTINCT province, city FROM Flag";
        Cursor cursor = db.rawQuery(query, null);
        int cnt = cursor.getCount();
        text_3.setText(text_3.getText().toString().replace("$",cnt+""));
    }

    private void handle_4(){
        String query ="SELECT DISTINCT pname FROM ProvinceColor";
        Cursor cursor = db.rawQuery(query, null);
        int cnt = cursor.getCount();
        float percentage = ((float)cnt / 34.0f)*100.0f;
        String Spercentage = percentage + "";
        if(Spercentage.length()>5)
            Spercentage = Spercentage.substring(0,5);
        text_4.setText(text_4.getText().toString().replace("$",Spercentage));
    }

    private void handle_5(){
        String query ="SELECT id FROM Event";
        Cursor cursor = db.rawQuery(query, null);
        int cnt = cursor.getCount();
        text_5.setText(text_5.getText().toString().replace("$",cnt+""));
    }

}
