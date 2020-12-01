package com.example.wanderlust_0;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Bar_1_Color extends Activity {

    SeekBar seekbarR, seekbarG, seekbarB;
    TextView resColorView;
    int resValue;
    Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
        
    }


}
