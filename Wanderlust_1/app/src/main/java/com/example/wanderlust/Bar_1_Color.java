package com.example.wanderlust;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Bar_1_Color extends Activity implements View.OnClickListener {

    SeekBar seekbarR, seekbarG, seekbarB;
    TextView resColorView;
    int resValue;
    Button btn;
    TextView [] c = new TextView[8];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_chooser);
        seekbarR = findViewById(R.id.seekbar_R);
        seekbarG = findViewById(R.id.seekbar_G);
        seekbarB = findViewById(R.id.seekbar_B);
        btn = findViewById(R.id.color_chooser_btn);
        resColorView = findViewById(R.id.result_color);

        seekbarR.setMax(255);
        seekbarG.setMax(255);
        seekbarB.setMax(255);

        c[0] = findViewById(R.id.c1);
        c[1] = findViewById(R.id.c2);
        c[2] = findViewById(R.id.c3);
        c[3] = findViewById(R.id.c4);
        c[4] = findViewById(R.id.c5);
        c[5] = findViewById(R.id.c6);
        c[6] = findViewById(R.id.c7);
        c[7] = findViewById(R.id.c8);

        // R bar
        seekbarR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                resValue = Color.rgb(seekbarR.getProgress(), seekbarG.getProgress(), seekbarB.getProgress());
                resColorView.setBackgroundColor(resValue);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // G bar
        seekbarG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                resValue = Color.rgb(seekbarR.getProgress(), seekbarG.getProgress(), seekbarB.getProgress());
                resColorView.setBackgroundColor(resValue);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // B bar
        seekbarB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                resValue = Color.rgb(seekbarR.getProgress(), seekbarG.getProgress(), seekbarB.getProgress());
                resColorView.setBackgroundColor(resValue);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("color", resValue);
                setResult(2, i);
                finish();
            }
        });

        for(int i=0;i<8;++i){
            c[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.c1:
                resValue = ((ColorDrawable)c[0].getBackground()).getColor();
                Log.e("color","here");
                break;
            case R.id.c2:
                resValue = ((ColorDrawable)c[1].getBackground()).getColor();
                break;
            case R.id.c3:
                resValue = ((ColorDrawable)c[2].getBackground()).getColor();
                break;
            case R.id.c4:
                resValue = ((ColorDrawable)c[3].getBackground()).getColor();
                break;
            case R.id.c5:
                resValue = ((ColorDrawable)c[4].getBackground()).getColor();
                break;
            case R.id.c6:
                resValue = ((ColorDrawable)c[5].getBackground()).getColor();
                break;
            case R.id.c7:
                resValue = ((ColorDrawable)c[6].getBackground()).getColor();
                break;
            case R.id.c8:
                resValue = ((ColorDrawable)c[7].getBackground()).getColor();
                break;
        }
        resColorView.setBackgroundColor(resValue);
    }

}
