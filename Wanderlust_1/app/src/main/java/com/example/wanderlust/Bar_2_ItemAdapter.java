package com.example.wanderlust;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Bar_2_ItemAdapter extends RecyclerView.Adapter<Bar_2_ItemAdapter.ViewHolder> {
    private List<Bar_2_Item> itemList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        NineGridLayout nineGridLayout;
        TextView info = null;
        TextView text = null;
        public ViewHolder(View view) {
            super(view);
            info = view.findViewById(R.id.bar_1_disp_item_info);
            text = view.findViewById(R.id.bar_1_disp_item_text);
            nineGridLayout = view.findViewById(R.id.bar_1_Nine);
        }
    }
    public Bar_2_ItemAdapter(List<Bar_2_Item> itemList) {
        this.itemList = itemList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bar_1_recyclerview_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Bar_2_Item item = itemList.get(position);
        holder.info.setText(item.getInfo());
        holder.text.setText(item.getName());
        holder.nineGridLayout.setBmp(item.getBmps());
        holder.nineGridLayout.refresh();
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }
}