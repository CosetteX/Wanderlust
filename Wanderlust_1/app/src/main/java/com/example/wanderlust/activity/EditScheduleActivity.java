package com.example.wanderlust.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanderlust.R;
import com.example.wanderlust.adapter.ImagePickerAdapter;
import com.example.wanderlust.adapter.ImagePickerAdapter2;
import com.example.wanderlust.dbmanager.ScheduleDao;
import com.example.wanderlust.items.Bar_2_Item;
import com.example.wanderlust.items.WheelViewDataBean;
import com.example.wanderlust.utils.BaseDialog;
import com.example.wanderlust.utils.GlideEngine;
import com.example.wanderlust.utils.StringUtils;
import com.example.wanderlust.widget.IWheelViewSelectedListener;
import com.example.wanderlust.widget.MyOnClickListener;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class EditScheduleActivity extends FragmentActivity implements View.OnClickListener, IWheelViewSelectedListener, MyOnClickListener {

    private ScheduleDao scheduleDao;
    private TextView tvChooseType;
    private EditText etContent;
    private TextView tvChooseCity;
    private TextView tvChooseTime;

    private List<WheelViewDataBean> wheelViewDataBeans = new ArrayList<>();
    private BaseDialog baseDialog;
    private Dialog wheelViewDialog;
    private long id; //根据此id更新数据
    private String strUlr;
    private List<String> mList1 = new ArrayList<>();
    private ImagePickerAdapter imageGoodsAdapter;
    private long strDate;
    private String strTime;

    Calendar calendar= Calendar.getInstance(Locale.CHINA);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);
        scheduleDao = new ScheduleDao(this);
        initWheelViewData();
        initWheelDialog();

        strTime=getIntent().getStringExtra("strTime");
        strDate = getIntent().getLongExtra("strDate",0);
        String strLocation =  getIntent().getStringExtra("strLocation");
        String strType = getIntent().getStringExtra("strType");
        strUlr = getIntent().getStringExtra("strUlr");
        String strContent =getIntent().getStringExtra("strContent");
        id = getIntent().getLongExtra("_id", 0);



        tvChooseType = findViewById(R.id.tv_choose_type);
        tvChooseType.setOnClickListener(this);
        Button btnCommit = findViewById(R.id.btn_commit);
        btnCommit.setOnClickListener(this);
        etContent = findViewById(R.id.et_content);
        tvChooseCity = findViewById(R.id.tv_choose_city);
        tvChooseTime=findViewById(R.id.tv_choose_time);
        tvChooseCity.setOnClickListener(this);
        tvChooseTime.setOnClickListener(this);

        //设置数据
        tvChooseCity.setText(strLocation);
        tvChooseType.setText(strType);
        etContent.setText(strContent);
        tvChooseTime.setText(strTime);

        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText("编辑日程");
        findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        List<String> tmpList = new ArrayList<String>();
        if(strUlr!=null)
            tmpList = StringUtils.stringsToList(strUlr);
        for (int i = 0; i < tmpList.size(); i++) {
            mList1.add(tmpList.get(i));
        }
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(manager);
        imageGoodsAdapter = new ImagePickerAdapter(this, mList1, 9);
        recyclerView.setAdapter(imageGoodsAdapter);

        imageGoodsAdapter.setOnDelClickListener(new ImagePickerAdapter.OnDelClickListener() {
            @Override
            public void onDelClick(int position) {
                mList1.remove(position);
                imageGoodsAdapter.setImages(mList1);
            }
        });

        imageGoodsAdapter.setOnItemClickListener(new ImagePickerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //打开相册
                if (position == -1) {
                    showCameraDialog();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();//销毁当前页面
                break;
            case R.id.tv_choose_type:
                //选择日程类型
                showAttendanceType(wheelViewDataBeans);
                break;
            case R.id.btn_commit:
                //提交
                commitSchedule();
                break;
            case R.id.tv_choose_city:
                //选择城市
                chooseCity();
                break;
            case R.id.tv_choose_time:
                showTimePickerDialog(this,  4, tvChooseTime, calendar);
                break;


        }
    }

    /**
     * 时间选择
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public static void showTimePickerDialog(Activity activity, int themeResId, final TextView tv, Calendar calendar) {

        new TimePickerDialog( activity,themeResId,
                // 绑定监听器
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(minute<10){
                            tv.setText(hourOfDay + ":0" + minute);
                        }
                        else{
                            tv.setText(hourOfDay + ":" + minute);

                        }

                    }
                }
                // 设置初始时间
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                // true表示采用24小时制
                ,true).show();
    }


    private void commitSchedule() {
        String strContent = etContent.getText().toString().trim();
        String strType = tvChooseType.getText().toString().trim();
        String strCity = tvChooseCity.getText().toString().trim();
        String strTime = tvChooseTime.getText().toString().trim();

        if (TextUtils.isEmpty(strContent)) {
            Toast.makeText(EditScheduleActivity.this, "日程内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(strType)||"请选择".equals(strType)) {
            Toast.makeText(EditScheduleActivity.this, "请选择日程类型", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(strCity)||"请选择".equals(strCity)) {
            Toast.makeText(EditScheduleActivity.this, "请选择城市", Toast.LENGTH_SHORT).show();
            return;
        }

        scheduleDao.updateScheduleData(new Bar_2_Item(id, strContent, StringUtils.listToString(mList1, ","), strType, strCity, strDate, strTime), id);

        finish();
    }


    private void chooseCity() {
        CityPicker cityPicker=new CityPicker.Builder(EditScheduleActivity.this)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("北京市")
                .city("北京市")
                .district("朝阳区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(true)
                .build();

        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值
                tvChooseCity.setText(province.trim() + "" + city.trim() + "" + district.trim());
            }
        });
    }

    /***
     * 选择日程类型
     * @param mList
     */
    private void showAttendanceType(List<WheelViewDataBean> mList) {
        //选择批次
        if (baseDialog != null) {
            wheelViewDialog = baseDialog.showWheelViewDialog(this, mList);
        }
    }

    private void initWheelDialog() {
        baseDialog = BaseDialog.getInstance();
        if (baseDialog != null) {
            baseDialog.setOnWheelViewSelectedListener(this);
            baseDialog.setOnClickListener(this);
        }
    }

    private void initWheelViewData() {
        WheelViewDataBean dataBean = new WheelViewDataBean();
        dataBean.setName("工作");
        dataBean.setId(1);


        WheelViewDataBean dataBean2 = new WheelViewDataBean();
        dataBean2.setName("旅游");
        dataBean2.setId(2);

        WheelViewDataBean dataBean3 = new WheelViewDataBean();
        dataBean3.setName("交通");
        dataBean3.setId(3);
        wheelViewDataBeans.add(dataBean);
        wheelViewDataBeans.add(dataBean2);
        wheelViewDataBeans.add(dataBean3);
    }

    @Override
    public void onItemSelected(int position, List<?> list) {

    }

    @Override
    public void onCancel(View view) {
        //取消按钮
        if (wheelViewDialog != null) {
            wheelViewDialog.dismiss();
        }
    }

    @Override
    public void onConfirm(View view, String v) {
        WheelViewDataBean wheelViewDataBean = wheelViewDataBeans.get(Integer.valueOf(v));
        if (wheelViewDataBean != null) {
            String vName = wheelViewDataBean.getName();
            tvChooseType.setText(vName);
        }
        //确认按钮
        if (wheelViewDialog != null) {
            wheelViewDialog.dismiss();
        }
    }

    private void showCameraDialog() {
        BaseDialog baseDialog = BaseDialog.getInstance();
        if (baseDialog != null) {
            final Dialog dialog = baseDialog.showDialog(this, R.layout.photo_dialog_layout);
            dialog.findViewById(R.id.take_photo_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    PictureSelector.create(EditScheduleActivity.this)
                            .openCamera(PictureMimeType.ofImage())
                            .loadImageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                            .compress(true)
                            .minimumCompressSize(100)
                            .forResult(PictureConfig.REQUEST_CAMERA);
                }
            });
            dialog.findViewById(R.id.pick_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    PictureSelector.create(EditScheduleActivity.this)
                            .openGallery(PictureMimeType.ofImage())
                            .loadImageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                            .isWeChatStyle(true)
                            .selectionMode(PictureConfig.SINGLE)// 单选
                            .isZoomAnim(false)
                            .previewImage(false)
                            .compress(true)
                            .minimumCompressSize(100)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                }
            });
            dialog.findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String selectUrl = PictureSelector.obtainMultipleResult(data).get(0).getCompressPath();
            mList1.add(mList1.size(),selectUrl);
            imageGoodsAdapter.setImages(mList1);
        }
    }


}
