package com.example.wanderlust.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wanderlust.R;
import com.example.wanderlust.adapter.ScheduleListAdapter;
import com.example.wanderlust.dbmanager.ScheduleDao;
import com.example.wanderlust.items.Bar_2_Item;
import com.example.wanderlust.utils.BaseDialog;
import com.example.wanderlust.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends FragmentActivity implements View.OnClickListener {

    private EditText etSearch;
    private ScheduleDao scheduleDao;
    private RecyclerView recyclerView;
    private ScheduleListAdapter adapter;
    private List<Bar_2_Item> bar2ItemList = new ArrayList<>();
    private List<Bar_2_Item> listAll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        etSearch = findViewById(R.id.et_search);
        TextView tvSearch = findViewById(R.id.tv_search);
        tvSearch.setOnClickListener(this);

        scheduleDao = new ScheduleDao(this);
        listAll = scheduleDao.queryScheduleList();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new ScheduleListAdapter(bar2ItemList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bar_2_Item bar2Item = (Bar_2_Item) adapter.getItem(position);
                if (bar2Item != null) {
                    //String strDate = bar2Item.getScheduleDate();
                    Long strDate=bar2Item.getScheduleDate();
                    String strLocation = bar2Item.getScheduleLocation();
                    String strType = bar2Item.getScheduleType();
                    String strUlr = bar2Item.getScheduleImgUrl();
                    String strContent = bar2Item.getScheduleContent();
                    startActivity(new Intent(SearchActivity.this, ScheduleDetailActivity.class)
                            .putExtra("strDate", strDate)
                            .putExtra("strLocation", strLocation)
                            .putExtra("strType", strType)
                            .putExtra("strUlr", strUlr)
                            .putExtra("strContent", strContent));
                }
            }
        });

        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                showDeleteAndEditDialog(position);
                return false;
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() <= 0) {
                    bar2ItemList.clear();
                    adapter.setNewData(null);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
                //搜索
                filterData();
                break;
        }
    }


    private void filterData() {
        bar2ItemList.clear();
        String search = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(search)) {
            Toast.makeText(SearchActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = 0; i < listAll.size(); i++) {
            Bar_2_Item bean = listAll.get(i);
            if (StringUtils.getStringDate(bean.getScheduleDate()).contains(search) || bean.getScheduleLocation().contains(search)) {
                bar2ItemList.add(bean);
            }
        }
        adapter.setNewData(bar2ItemList);
    }



    private void showDeleteAndEditDialog(int position) {
        Dialog dialog = BaseDialog.showDialog(SearchActivity.this, R.layout.dialog_delete_schedule);
        TextView tvDelete= dialog.findViewById(R.id.tv_delete);
        TextView tvEdit = dialog.findViewById(R.id.tv_edit);
        TextView tvCancel = dialog.findViewById(R.id.cancel_tv);
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long _id = bar2ItemList.get(position).get_id();
                //删除数据
                scheduleDao.deleteScheduleData(_id);
                //查询数据
                bar2ItemList = scheduleDao.queryScheduleList();

                Log.d("Dong", "-- >> " + bar2ItemList.size());
                adapter.setNewData(bar2ItemList);

                dialog.dismiss();

            }
        });

        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //编辑日程
                Bar_2_Item bar2Item = bar2ItemList.get(position);
                if (bar2Item != null) {
                    long id = bar2Item.get_id();
                    Long strDate = bar2Item.getScheduleDate();
                    String strLocation = bar2Item.getScheduleLocation();
                    String strType = bar2Item.getScheduleType();
                    String strUlr = bar2Item.getScheduleImgUrl();
                    String strContent = bar2Item.getScheduleContent();
                    startActivity(new Intent(SearchActivity.this, EditScheduleActivity.class)
                            .putExtra("strDate", strDate)
                            .putExtra("strLocation", strLocation)
                            .putExtra("strType", strType)
                            .putExtra("strUlr", strUlr)
                            .putExtra("strContent", strContent)
                            .putExtra("_id",id));
                }
                dialog.dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
