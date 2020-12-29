package com.example.wanderlust;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wanderlust.activity.ScheduleDetailActivity;
import com.example.wanderlust.adapter.ScheduleListAdapter;
import com.example.wanderlust.items.Bar_2_Item;
import com.example.wanderlust.dbmanager.ScheduleDao;

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


        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bar_2_Item bar2Item = (Bar_2_Item) adapter.getItem(position);
                if (bar2Item != null) {
                    String strDate = bar2Item.getScheduleDate();
                    String strLocation = bar2Item.getScheduleLocation();
                    String strType = bar2Item.getScheduleType();
                    String strUlr = bar2Item.getScheduleImgUrl();
                    String strContent = bar2Item.getScheduleContent();
                    startActivity(new Intent(getActivity(), ScheduleDetailActivity.class)
                            .putExtra("strDate", strDate)
                            .putExtra("strLocation", strLocation)
                            .putExtra("strType", strType)
                            .putExtra("strUlr", strUlr)
                            .putExtra("strContent", strContent));
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //查询所有的日程
        bar2ItemList = scheduleDao.queryScheduleList();
        adapter.setNewData(bar2ItemList);
    }
}
