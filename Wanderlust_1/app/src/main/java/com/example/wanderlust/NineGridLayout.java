package com.example.wanderlust;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class NineGridLayout extends GridLayout {

    private List<Bitmap> bmps;
    List<Dialog> dialog;
    Dialog dialogTmp;
    ImageView dialogView;

    public NineGridLayout(Context context) {
        super(context,null);
    }
    public NineGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NineGridLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }
//
//
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//
//    }
    public void setBmp(List<Bitmap> b){
        this.bmps = b;
    }

    public void refresh(){
        int margin = 10;
        int width = 300;
        int height = 300;

        if(bmps.size()==0) return;
        if(bmps.size()==1){
            this.setColumnCount(bmps.size());
            height = width * bmps.get(0).getHeight()/bmps.get(0).getWidth();
            margin=0;
        }
        else if(bmps.size()==2 || bmps.size()==4){
            this.setColumnCount(2);
            height = 400;
        }
        else{
            this.setColumnCount(3);
        }

        // new for 9
        dialog = new ArrayList<Dialog>();
        for(int k=0;k<bmps.size();++k){
            dialogTmp = new Dialog(getContext(),R.style.FullDialog);
            dialogTmp.setCancelable(true);
            dialogView = new ImageView(getContext());
            dialogView.setImageBitmap(bmps.get(k));
            dialogTmp.setContentView(dialogView);
            dialogView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("msg","clicked!!");
                    dialogTmp.cancel();
                }
            });
            dialogTmp.getWindow().setBackgroundDrawableResource(android.R.color.black);
            dialog.add(dialogTmp);
        }

        for(int k=0;k<bmps.size();k++){
            ImageView img = new ImageView(getContext());
            img.setImageBitmap(bmps.get(k));
            GridLayout.LayoutParams parem = new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f),  GridLayout.spec(GridLayout.UNDEFINED, 1f));
            parem.topMargin=margin;
            parem.rightMargin=margin;
            parem.leftMargin=margin;
            parem.bottomMargin=margin;
            parem.height=height;
            parem.width=width;
            img.setLayoutParams(parem);
            switch (k){
                case 0:
                    img.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            dialog.get(0).show();
                        }
                    });
                    break;
                case 1:
                    img.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            dialog.get(1).show();
                        }
                    });
                    break;
                case 2:
                    img.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            dialog.get(2).show();
                        }
                    });
                    break;
                case 3:
                    img.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            dialog.get(3).show();
                        }
                    });
                    break;
                case 4:
                    img.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            dialog.get(4).show();
                        }
                    });
                    break;
                case 5:
                    img.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            dialog.get(5).show();
                        }
                    });
                    break;
                case 6:
                    img.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            dialog.get(6).show();
                        }
                    });
                case 7:
                    img.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            dialog.get(7).show();
                        }
                    });
                    break;
                case 8:
                    img.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            dialog.get(8).show();
                        }
                    });
                    break;
                    default: break;

            }
            this.addView(img);
        }
    }



}
