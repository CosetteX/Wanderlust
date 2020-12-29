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
import com.example.wanderlust.items.Bar_2_Item_new;
import com.example.wanderlust.dbmanager.ScheduleDao;

import java.util.ArrayList;
import java.util.List;

public class Bar_3 extends Fragment {
    private ScheduleListAdapter adapter;
    private RecyclerView recyclerView;
    private List<Bar_2_Item_new> bar2ItemnewList = new ArrayList<>();
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
        adapter = new ScheduleListAdapter(bar2ItemnewList);
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bar_2_Item_new bar2Itemnew = (Bar_2_Item_new) adapter.getItem(position);
                if (bar2Itemnew != null) {
                    Long strDate = bar2Itemnew.getScheduleDate();
                    String strLocation = bar2Itemnew.getScheduleLocation();
                    String strType = bar2Itemnew.getScheduleType();
                    String strUlr = bar2Itemnew.getScheduleImgUrl();
                    String strContent = bar2Itemnew.getScheduleContent();
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
        bar2ItemnewList = scheduleDao.queryScheduleList();
        adapter.setNewData(bar2ItemnewList);
    }
}
