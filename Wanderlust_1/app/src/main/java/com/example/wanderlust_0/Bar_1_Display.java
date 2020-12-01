package com.example.wanderlust_0;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Bar_1_Display extends Activity {

    TextView textView = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bar_1_disp);
        textView = findViewById(R.id.bar_1_disp_txt);
        Intent intent = getIntent();
        textView.setText(intent.getStringExtra("message"));
    }


}
