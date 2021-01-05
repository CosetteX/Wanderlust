package com.example.wanderlust.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanderlust.R;
import com.example.wanderlust.adapter.ImagePickerAdapter;
import com.example.wanderlust.adapter.ImagePickerAdapter2;
import com.example.wanderlust.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDetailActivity extends FragmentActivity {
    private ImagePickerAdapter2 imageGoodsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_detail);

        long strDate = getIntent().getLongExtra("strDate",0);
        String strLocation =  getIntent().getStringExtra("strLocation");
        String strType = getIntent().getStringExtra("strType");
        String strUlr = getIntent().getStringExtra("strUlr");
        String strContent =getIntent().getStringExtra("strContent");
        String strTime=getIntent().getStringExtra("strTime");


        TextView tvContent = findViewById(R.id.tv_content);
        TextView tvDate = findViewById(R.id.tv_date);
        TextView tvStatus = findViewById(R.id.tv_status);
        TextView tvCity = findViewById(R.id.tv_city);
        TextView tvType = findViewById(R.id.tv_type);
        TextView tvTime=findViewById(R.id.tv_time);

        tvContent.setText(strContent);
        tvDate.setText(StringUtils.getStringDate(strDate));
        tvCity.setText(strLocation);
        tvType.setText(strType);
        tvTime.setText(strTime);

        int value = StringUtils.compareDate(StringUtils.getCurrentTime(), StringUtils.getStringDate(strDate));
        if (value == -1) {
            //当前时间小于创建时间  过期了
            tvStatus.setText("已过期");
        } else {
            //当前时间大于创建时间  待办状态
            tvStatus.setText("待办");
        }

        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText("日程详情");
        findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        List<String> mList1= new ArrayList<String>();
        if(strUlr!=null)
            mList1 = StringUtils.stringsToList(strUlr);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(manager);
        imageGoodsAdapter = new ImagePickerAdapter2(this, mList1, 9);
        recyclerView.setAdapter(imageGoodsAdapter);
    }
}
