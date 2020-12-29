package com.example.wanderlust.Bar_1;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wanderlust.R;

import java.util.List;

public class Bar_1_Flag_ItemAdapter extends RecyclerView.Adapter<Bar_1_Flag_ItemAdapter.ViewHolder> {
    private List<Bar_1_Flag_Item> itemList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title = null;
        TextView content = null;
        Button btn_delete = null;
        Button btn_top = null;
        ImageView icon = null;
        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.flag_item_title);
            content = view.findViewById(R.id.flag_item_content);
            btn_delete = view.findViewById(R.id.flag_item_delete);
            btn_top = view.findViewById(R.id.flag_item_top);
            icon = view.findViewById(R.id.flag_item_image);
        }
    }
    public Bar_1_Flag_ItemAdapter(List<Bar_1_Flag_Item> itemList) {
        this.itemList = itemList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flag, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int pos = position;
        final Bar_1_Flag_Item item = itemList.get(position);
        // Set top icon
        if(item.getCurTop()==1){
            holder.icon.setImageResource(R.drawable.icon_flag_top);
        }
        else holder.icon.setImageResource(R.drawable.icon_flag);
        // Set Text
        holder.title.setText(item.getCity());
        holder.content.setText(item.getContent());
        // Delete Button
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               item.deleteItem();
               itemList.remove((Object)item);
                notifyDataSetChanged();
            }
        });
        // Top Button
        holder.btn_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.changeTop();
                if(item.getCurTop()==1){
                    Bar_1_Flag_Item tmp = itemList.get(pos);
                    itemList.remove(pos);
                    itemList.add(0,tmp);
                }
                else{
                    Bar_1_Flag_Item tmp = itemList.get(0);
                    itemList.remove(0);
                    itemList.add(tmp);
                }
                notifyDataSetChanged();
            }
        });
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }
}