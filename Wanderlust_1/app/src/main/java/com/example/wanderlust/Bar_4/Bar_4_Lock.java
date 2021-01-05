package com.example.wanderlust.Bar_4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.wanderlust.R;


public class Bar_4_Lock extends Activity {

    EditText old = null;
    EditText newPwd = null;
    EditText repeatPwd = null;
    Button btn = null;
    String pwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        pwd = intent.getStringExtra("pwd");
        setContentView(R.layout.bar_4_lock);
        old = findViewById(R.id.bar_4_old);
        newPwd = findViewById(R.id.bar_4_new1);
        repeatPwd = findViewById(R.id.bar_4_new2);
        btn = findViewById(R.id.bar_4_modify);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPwd = old.getText().toString();
                if(oldPwd.equals(pwd)){
                    if(newPwd.getText().toString().equals(repeatPwd.getText().toString())){
                        Intent i = new Intent();
                        i.putExtra("pwd", newPwd.getText().toString());
                        setResult(1, i);
                        finish();
                    }
                    else{
                        Toast.makeText(getBaseContext(),"Not same!",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getBaseContext(),"Wrong pssword",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



}
