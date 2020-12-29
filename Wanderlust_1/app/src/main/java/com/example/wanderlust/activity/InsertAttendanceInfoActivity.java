package com.example.wanderlust.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.wanderlust.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

//添加日程页面
public class InsertAttendanceInfoActivity extends FragmentActivity implements View.OnClickListener {

    private String currentDay; ///当前选中的日期

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_attendance_info);
        initView();
        currentDay = getIntent().getStringExtra("currentDay");
    }

    private void initView() {
        TextView tvBack = findViewById(R.id.tv_back);
        tvBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();//销毁当前页面
                break;
        }
    }
}
