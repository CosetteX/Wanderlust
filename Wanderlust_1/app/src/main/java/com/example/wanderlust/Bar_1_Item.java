package com.example.wanderlust;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Bar_1_Item {
    private String name;
    private String date;
    private String time;
    private List<Bitmap> bmps = null;

    public Bar_1_Item(String name){
        this.name = name;
        bmps = new ArrayList<Bitmap>();
    }

    public Bar_1_Item(String name,String date,String time){
        this.name = name;
        this.date = date;
        this.time = time;
        bmps = new ArrayList<Bitmap>();
    }

    public void addImg(Bitmap b){
        if(bmps.size()<9) bmps.add(b);
    }

    public Bitmap getImg(int i) {
        if(i<bmps.size()) return bmps.get(i);
        else return null;
    }

    public List<Bitmap> getBmps() {
        return bmps;
    }

    public int getCnt() {
        return bmps.size();
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return date+" "+time;
    }

}
