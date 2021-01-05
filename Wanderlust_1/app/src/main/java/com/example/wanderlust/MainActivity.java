package com.example.wanderlust;


import android.graphics.Color;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.wanderlust.Bar_1.Bar_1;
import com.example.wanderlust.Bar_4.Bar_4;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.ArrayList;

import site.gemus.openingstartanimation.OpeningStartAnimation;
import site.gemus.openingstartanimation.RedYellowBlueDrawStrategy;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener  {

    private RadioGroup rg_tab_bar;
    private RadioButton rb_channel;
    private RadioButton rb_message;
    private RadioButton rb_better;
    private RadioButton rb_setting;
    private ViewPager vpager;
    private MyFragmentPagerAdapter mAdapter;
    // 几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;

    private ArrayList<Fragment> list = new ArrayList<Fragment>();

    private Bar_1 fragment1 = null;
    private Bar_2 fragment2 = null;
    private Bar_3 fragment3 = null;
    private Bar_4 fragment4 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        bindViews();
        rb_channel.setChecked(true);

        requestPermission();
    }

    private void initData() {
        fragment1 = new Bar_1();
        fragment2 = new Bar_2();
        fragment3 = new Bar_3();
        fragment4 = new Bar_4();
        list.add(fragment1);
        list.add(fragment2);
        list.add(fragment3);
        list.add(fragment4);
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),list);
        //添加动画 
        OpeningStartAnimation openingStartAnimation = new OpeningStartAnimation.Builder(this)
                .setAppIcon(this.getResources().getDrawable(R.drawable.icon_map))
                .setDrawStategy(new RedYellowBlueDrawStrategy())
                .setAnimationInterval(2000)
                .setAnimationFinishTime(2000)
                .setAppStatement("欢迎使用浪迹 Wanderlust") //设置一句话描述
                .setColorOfAppStatement(Color.parseColor("#87a7d6"))
                .setColorOfAppIcon(Color.parseColor("#87a7d6"))
                .setColorOfAppName(Color.parseColor("#87a7d6"))
                .create();
        openingStartAnimation.show(this);
    }
    private void bindViews() {
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rb_channel = (RadioButton) findViewById(R.id.rb_channel);
        rb_message = (RadioButton) findViewById(R.id.rb_message);
        rb_better = (RadioButton) findViewById(R.id.rb_better);
        rb_setting = (RadioButton) findViewById(R.id.rb_setting);
        rg_tab_bar.setOnCheckedChangeListener(this);
        vpager = (ViewPager) findViewById(R.id.vpager);
        vpager.setAdapter(mAdapter);
        vpager.setCurrentItem(0);
        vpager.addOnPageChangeListener(this);
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_channel:
                vpager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.rb_message:
                vpager.setCurrentItem(PAGE_TWO);
                break;
            case R.id.rb_better:
                vpager.setCurrentItem(PAGE_THREE);
                break;
            case R.id.rb_setting:
                vpager.setCurrentItem(PAGE_FOUR);
                break;
        }
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 1) {
            fragment2.setRecyclListData();
        }
    }
    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个， 0表示什么都没做， 1 正在滑动， 2滑动完毕
        if (state == 2) {
            switch (vpager.getCurrentItem()) {
                case PAGE_ONE:
                    rb_channel.setChecked(true);
                    break;
                case PAGE_TWO:
                    rb_message.setChecked(true);
                    break;
                case PAGE_THREE:
                    rb_better.setChecked(true);
                    break;
                case PAGE_FOUR:
                    rb_setting.setChecked(true);
                    break;
            }
        }
    }


    //获取相机相关权限
    private void requestPermission() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.ACCESS_FINE_LOCATION,Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(data -> {

                })
                .start();
    }
}
