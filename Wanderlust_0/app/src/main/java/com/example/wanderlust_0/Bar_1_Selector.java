package com.example.wanderlust_0;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Field;

public class Bar_1_Selector extends Fragment {
    ArrayAdapter adapter;
    TextView textview;
    Spinner spinner;

    MapView map;
    public void setMap(MapView m){
        this.map = m;
    }

    public void renewAdapter(String name){
        name="city"+name;
        int resId = getResId(name,R.array.class);
        adapter = ArrayAdapter.createFromResource(getActivity(),resId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bar_1_sel, container, false);
        spinner = (Spinner)view.findViewById(R.id.bar_1_spinner);
        textview = (TextView)view.findViewById(R.id.bar_1_txt);

        String name = "cityZhejiang";
        int resId = getResId(name,R.array.class);
        adapter = ArrayAdapter.createFromResource(getActivity(),resId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> argO, View argl, int arg2, long arg3) {
                textview.setText("你选择的城市是:" + adapter.getItem(arg2));
                argO.setVisibility(View.VISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> argO) {
                // TODO Auto-generated method stub
                textview.setText("NONE");
                argO.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    public int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
