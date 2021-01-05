package com.example.wanderlust;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wanderlust.activity.EditScheduleActivity;
import com.example.wanderlust.activity.ScheduleDetailActivity;
import com.example.wanderlust.activity.SearchActivity;
import com.example.wanderlust.adapter.ScheduleListAdapter;
import com.example.wanderlust.items.Bar_2_Item;
import com.example.wanderlust.dbmanager.ScheduleDao;
import com.example.wanderlust.utils.BaseDialog;

import java.util.ArrayList;
import java.util.List;

public class Bar_3 extends Fragment {
    private ScheduleListAdapter adapter;
    private RecyclerView recyclerView;
    private List<Bar_2_Item> bar2ItemList = new ArrayList<>();
    private ScheduleDao scheduleDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bar_3, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        scheduleDao = new ScheduleDao(getActivity());
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new ScheduleListAdapter(bar2ItemList);
        recyclerView.setAdapter(adapter);

        TextView etSearch = view.findViewById(R.id.et_search);
        TextView tvSearch = view.findViewById(R.id.tv_search);

        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });

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
                    String strTime=bar2Item.getScheduleTime();
                    startActivity(new Intent(getActivity(), ScheduleDetailActivity.class)
                            .putExtra("strDate", strDate)
                            .putExtra("strLocation", strLocation)
                            .putExtra("strType", strType)
                            .putExtra("strUlr", strUlr)
                            .putExtra("strContent", strContent)
                            .putExtra("strTime",strTime));
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

    }

    private void showDeleteAndEditDialog(int position) {
        Dialog dialog = BaseDialog.showDialog(getActivity(), R.layout.dialog_delete_schedule);
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

                if (bar2ItemList.isEmpty()) {
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_empty, null);
                    adapter.setEmptyView(view);
                }
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
                    String strTime=bar2Item.getScheduleTime();
                    startActivity(new Intent(getActivity(), EditScheduleActivity.class)
                            .putExtra("strDate", strDate)
                            .putExtra("strLocation", strLocation)
                            .putExtra("strType", strType)
                            .putExtra("strUlr", strUlr)
                            .putExtra("strContent", strContent)
                            .putExtra("strTime",strTime)
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

    @Override
    public void onResume() {
        super.onResume();
        //查询所有的日程
        bar2ItemList = scheduleDao.queryScheduleList();
        adapter.setNewData(bar2ItemList);
        if (bar2ItemList.isEmpty()) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_empty, null);
            adapter.setEmptyView(view);
        }
    }
}
