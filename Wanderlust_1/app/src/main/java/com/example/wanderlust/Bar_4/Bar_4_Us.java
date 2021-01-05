package com.example.wanderlust.Bar_4;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.wanderlust.R;

public class Bar_4_Us extends FragmentActivity {

    TextView email_X = null;
    TextView email_Z = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bar_4_us);
        email_X = findViewById(R.id.item_email0);
        email_X.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent data=new Intent(Intent.ACTION_SENDTO);
                data.setData(Uri.parse("mailto:"+email_X.getText().toString()));
                data.putExtra(Intent.EXTRA_SUBJECT, "请输入标题");
                data.putExtra(Intent.EXTRA_TEXT, "请输入内容");
                startActivity(data);
            }
        });

        email_Z = findViewById(R.id.item_email1);
        email_Z.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent data=new Intent(Intent.ACTION_SENDTO);
                data.setData(Uri.parse("mailto:"+email_Z.getText().toString()));
                data.putExtra(Intent.EXTRA_SUBJECT, "请输入标题");
                data.putExtra(Intent.EXTRA_TEXT, "请输入内容");
                startActivity(data);
            }
        });
    }
}
