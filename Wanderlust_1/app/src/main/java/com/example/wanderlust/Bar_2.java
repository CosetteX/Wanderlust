package com.example.wanderlust;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wanderlust.activity.ScheduleDetailActivity;

import com.example.wanderlust.activity.InsertAttendanceInfoActivity;
import com.example.wanderlust.adapter.ScheduleListAdapter;
import com.example.wanderlust.items.Bar_2_Item;
import com.example.wanderlust.dbmanager.ScheduleDao;
import com.example.wanderlust.utils.StringUtils;
import com.necer.calendar.BaseCalendar;
import com.necer.calendar.Miui9Calendar;
import com.necer.listener.OnCalendarChangedListener;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class Bar_2 extends Fragment implements View.OnClickListener {

    Miui9Calendar myCalendar;
    RecyclerView recyclerView;
    ImageView image;
    TextView date;
    ImageView today;
    ImageView back;

    private boolean isResumeFirst = false;

    private LocalDate selectedDate;
    private String mDate;//当前日期
    private Button btnAddItem;
    private ScheduleDao scheduleDao;
    private List<Bar_2_Item> bar2ItemnewList = new ArrayList<>();
    private ScheduleListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bar_2, container, false);
        myCalendar = view.findViewById(R.id.myCalendar);
        image = view.findViewById(R.id.img);
        recyclerView = view.findViewById(R.id.recyclerView);
        date = view.findViewById(R.id.item_date);
        today = view.findViewById(R.id.bar_today);
        btnAddItem = view.findViewById(R.id.bar_2_additem);

        scheduleDao = new ScheduleDao(getActivity());


        btnAddItem.setOnClickListener(this);
        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCalendar.toToday();
            }
        });

        selectedDate = myCalendar.getAllSelectDateList().get(0);
        mDate = localDate2DateString(selectedDate);
        date.setText(mDate);
        myCalendar.setOnCalendarChangedListener(new OnCalendarChangedListener() {
            @Override
            public void onCalendarChange(BaseCalendar baseCalendar, int year, int month, LocalDate localDate) {
                int mothOrDay = localDate.getDayOfMonth();
                int years = localDate.getYear();
                int months = localDate.getMonthOfYear();
                mDate = years + "-" + months + "-" + mothOrDay;
                date.setText(mDate);
                setRecyclListData();
                Log.d("Dong", "--->>>>>>>>>" + years + "-" + months +"-"+ mothOrDay);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new ScheduleListAdapter(bar2ItemnewList);
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
        return view;
    }

    public String localDate2DateString(LocalDate localDate){
        String year,month,day;
        year=localDate.getValue(0)+"";
        month=localDate.getValue(1)+"";
        day=localDate.getValue(2)+"";
        return year+"-"+month+"-"+day;
    }

    @Override
    public void onResume() {
        super.onResume();
        //查询当前日期的列表内容
        setRecyclListData();
    }

    public void setRecyclListData() {
       // bar2ItemnewList = scheduleDao.queyrByDateScheduleList(mDate);
       bar2ItemnewList=scheduleDao.queyrByDateScheduleList(String.valueOf(StringUtils.parse(mDate).getTime()));
        adapter.setNewData(bar2ItemnewList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_2_additem:
                startActivity(new Intent(getActivity(), InsertAttendanceInfoActivity.class).putExtra("currentDay",mDate));
                break;
        }
    }
}
