package com.example.wanderlust.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wanderlust.R;
import com.example.wanderlust.items.Bar_2_Item;
import com.example.wanderlust.utils.StringUtils;

import java.util.List;

public class ScheduleListAdapter extends BaseQuickAdapter<Bar_2_Item, BaseViewHolder> {

    public ScheduleListAdapter(@Nullable List<Bar_2_Item> data) {
        super(R.layout.item_schedule_list,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Bar_2_Item item) {


        helper.setText(R.id.tv_content, item.getScheduleContent())
                .setText(R.id.tv_date, item.getScheduleDate())
                .setText(R.id.tv_location, item.getScheduleLocation())
                .setText(R.id.tv_type, item.getScheduleType());
        int value = StringUtils.compareDate(StringUtils.getCurrentTime(),  item.getScheduleDate());
        if (value == -1) {
            //当前时间小于创建时间  过期了
            helper.setText(R.id.tv_status, "已过期");
        } else {
            //当前时间大于创建时间  待办状态
            helper.setText(R.id.tv_status, "待办");
        }
    }
}
