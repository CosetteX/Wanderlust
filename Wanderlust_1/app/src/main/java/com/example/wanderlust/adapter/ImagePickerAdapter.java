package com.example.wanderlust.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wanderlust.R;
import com.example.wanderlust.widget.RoundedImage.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class ImagePickerAdapter extends RecyclerView.Adapter<ImagePickerAdapter.SelectedPicViewHolder> {
    private int maxImgCount;
    private Context mContext;
    private List<String> mData;
    private LayoutInflater mInflater;
    private OnRecyclerViewItemClickListener listener;
    private OnDelClickListener delListener;
    private boolean isAdded;   //是否额外添加了最后一个图片

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }
    public interface OnDelClickListener {
        void onDelClick(int position);
    }

    public void setOnDelClickListener(OnDelClickListener listener) {
        this.delListener = listener;
    }
    public void setImages(List<String> data) {
        mData = new ArrayList<>(data);
        if (getItemCount() < maxImgCount) {
            mData.add("");
            isAdded = true;
        } else {
            isAdded = false;
        }
        notifyDataSetChanged();
    }

    public List<String> getImages() {
        //由于图片未选满时，最后一张显示添加图片，因此这个方法返回真正的已选图片
        if (isAdded) return new ArrayList<>(mData.subList(0, mData.size() - 1));
        else return mData;
    }

    public ImagePickerAdapter(Context mContext, List<String> data, int maxImgCount) {
        this.mContext = mContext;
        this.maxImgCount = maxImgCount;
        this.mInflater = LayoutInflater.from(mContext);
        setImages(data);
    }

    @Override
    public SelectedPicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectedPicViewHolder(mInflater.inflate(R.layout.image_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(SelectedPicViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class SelectedPicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RelativeLayout image_item_rl;
        private ImageView image_item_del_iv;
        private LinearLayout add_img_ll;
        private RoundedImageView iv_img;
        private int clickPosition;

        public SelectedPicViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.image_item_iv);
            add_img_ll = itemView.findViewById(R.id.image_item_add_ll);
            image_item_del_iv=itemView.findViewById(R.id.image_item_del_iv);
            image_item_rl=itemView.findViewById(R.id.image_item_rl);
        }

        public void bind(int position) {
            //设置条目的点击事件
            itemView.setOnClickListener(this);
            //根据条目位置设置图片
            String item = mData.get(position);
            if (isAdded && position == getItemCount() - 1) {
                add_img_ll.setVisibility(View.VISIBLE);
                image_item_rl.setVisibility(View.GONE);
                clickPosition = -1;

            } else {
                add_img_ll.setVisibility(View.GONE);
                image_item_rl.setVisibility(View.VISIBLE);
                //Glide图片加载
                Glide.with(mContext).load(item).into(iv_img);
                Log.d("Dong", "--> " + item);
                clickPosition = position;
                image_item_del_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delListener.onDelClick(clickPosition);
                    }
                });
            }
        }

        @Override
        public void onClick(View v) {
            if (listener != null) listener.onItemClick(v, clickPosition);
        }
    }
}