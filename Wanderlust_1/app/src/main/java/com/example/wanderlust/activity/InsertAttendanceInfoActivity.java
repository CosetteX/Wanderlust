package com.example.wanderlust.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wanderlust.R;
import com.example.wanderlust.adapter.ImagePickerAdapter;
import com.example.wanderlust.items.Bar_2_Item;
import com.example.wanderlust.items.WheelViewDataBean;
import com.example.wanderlust.dbmanager.ScheduleDao;
import com.example.wanderlust.utils.BaseDialog;
import com.example.wanderlust.utils.GlideEngine;
import com.example.wanderlust.utils.LocationUtils;
import com.example.wanderlust.utils.StringUtils;
import com.example.wanderlust.widget.IWheelViewSelectedListener;
import com.example.wanderlust.widget.MyOnClickListener;
/*
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
 */
import com.lljjcoder.citypickerview.widget.CityPicker;
//import com.lljjcoder.citypickerview.widget.CityPicker;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//添加日程页面
public class InsertAttendanceInfoActivity extends FragmentActivity implements View.OnClickListener, IWheelViewSelectedListener, MyOnClickListener {

    private String currentDay; ///当前选中的日期
    private ImagePickerAdapter imageGoodsAdapter;
    private List<String> mList1 = new ArrayList<>();
    private List<WheelViewDataBean> wheelViewDataBeans = new ArrayList<>();
    private BaseDialog baseDialog;
    private Dialog wheelViewDialog;
    private TextView tvChooseType;
    private EditText etContent;
    private TextView tvChooseCity;
    //private CityPickerView mPicker = new CityPickerView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_attendance_info);
        initView();
        currentDay = getIntent().getStringExtra("currentDay");

        //mPicker.init(this);

        initWheelViewData();
        initWheelDialog();

        ScheduleDao dao = new ScheduleDao(InsertAttendanceInfoActivity.this);
        List<Bar_2_Item> bar2ItemnewList =  dao.queyrByDateScheduleList("2020-12-29");

        Log.d("Dong", "---<> " + bar2ItemnewList.size());

        //获取当前定位信息
        String location= LocationUtils.getInstance().getLocations(this);
        tvChooseCity.setText(location);

    }

    private void initView() {
        TextView tvBack = findViewById(R.id.tv_back);
        tvBack.setOnClickListener(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(manager);
        imageGoodsAdapter = new ImagePickerAdapter(this, mList1, 9);
        recyclerView.setAdapter(imageGoodsAdapter);

        tvChooseType = findViewById(R.id.tv_choose_type);
        tvChooseType.setOnClickListener(this);
        Button btnCommit = findViewById(R.id.btn_commit);
        btnCommit.setOnClickListener(this);
        etContent = findViewById(R.id.et_content);
        tvChooseCity = findViewById(R.id.tv_choose_city);
        tvChooseCity.setOnClickListener(this);

        imageGoodsAdapter.setOnItemClickListener(new ImagePickerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //打开相册
                if (position == -1) {
                    showCameraDialog();
                } else {
//                startActivity(new Intent(mContext, ImagesPreviewActivity.class).putExtra("pos", position)
//                        .putExtra("images", (Serializable) imageGoodsAdapter.getImages()));
                }
            }
        });

        imageGoodsAdapter.setOnDelClickListener(position -> {
            mList1.remove(position);
            imageGoodsAdapter.setImages(mList1);
        });
    }

    private void showCameraDialog() {
        BaseDialog baseDialog = BaseDialog.getInstance();
        if (baseDialog != null) {
            final Dialog dialog = baseDialog.showDialog(this, R.layout.photo_dialog_layout);
            dialog.findViewById(R.id.take_photo_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    PictureSelector.create(InsertAttendanceInfoActivity.this)
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
                    PictureSelector.create(InsertAttendanceInfoActivity.this)
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
            mList1.add(selectUrl);
            imageGoodsAdapter.setImages(mList1);
        }
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
        }
    }

    private void chooseCity() {
       /* CityConfig cityConfig = new CityConfig.Builder()
                .province("北京") //设置默认显示省份
                .build();
        mPicker.setConfig(cityConfig);
        //监听选择点击事件及返回结果
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                //省份
                if (province != null && city != null && district != null) {
                    tvChooseCity.setText(province.getName() + city.getName());
                }
            }
            @Override
            public void onCancel() {

            }
        });
        //显示
        mPicker.showCityPicker();

        */
        CityPicker cityPicker=new cityPicker.Builder(InsertAttendanceInfoActivity.this)
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

    private void commitSchedule() {
        String strContent = etContent.getText().toString().trim();
        String strType = tvChooseType.getText().toString().trim();
        String strCity = tvChooseCity.getText().toString().trim();
        if (TextUtils.isEmpty(strContent)) {
            Toast.makeText(InsertAttendanceInfoActivity.this, "日程内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

       /* if (mList1.isEmpty()) {
            Toast.makeText(InsertAttendanceInfoActivity.this, "日程图片不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        */

        if (TextUtils.isEmpty(strType)||"请选择".equals(strType)) {
            Toast.makeText(InsertAttendanceInfoActivity.this, "请选择日程类型", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(strCity)||"请选择".equals(strCity)) {
            Toast.makeText(InsertAttendanceInfoActivity.this, "请选择城市", Toast.LENGTH_SHORT).show();
            return;
        }

        Bar_2_Item bar2Itemnew =
               new Bar_2_Item(System.currentTimeMillis(),strContent,StringUtils.listToString(mList1,","),strType,strCity,StringUtils.parse(currentDay).getTime(),"21:00");
               // new Bar_2_Item(System.currentTimeMillis(),strContent, StringUtils.listToString(mList1,","),strType,strCity,currentDay,"21:21");
        ScheduleDao dao = new ScheduleDao(InsertAttendanceInfoActivity.this);
        dao.add(bar2Itemnew);

        finish();
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


    private void initWheelDialog() {
        baseDialog = BaseDialog.getInstance();
        if (baseDialog != null) {
            baseDialog.setOnWheelViewSelectedListener(this);
            baseDialog.setOnClickListener(this);
        }
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
}