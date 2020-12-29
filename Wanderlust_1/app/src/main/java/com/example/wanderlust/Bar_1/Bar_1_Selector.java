package com.example.wanderlust.Bar_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wanderlust.Map.MapView;
import com.example.wanderlust.R;

import java.lang.reflect.Field;

public class Bar_1_Selector extends Fragment {
    ArrayAdapter adapter;
    TextView textview;
    Spinner spinner;
    Button btn_confirm;
    Button btn_color;
    Button btn_flag;
    String name = "浙江省";
    String chosenCity = null;

    MapView map;
    public void setMap(MapView m){
        this.map = m;
    }

    // onCreate
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bar_1_sel, container, false);
        spinner = (Spinner)view.findViewById(R.id.bar_1_spinner);
        textview = (TextView)view.findViewById(R.id.bar_1_txt);
        btn_confirm = (Button)view.findViewById(R.id.bar_1_btn);
        btn_color = (Button)view.findViewById(R.id.bar_1_choose_color);
        btn_flag = (Button) view.findViewById(R.id.bar_1_flag_btn);

        // Spinner
        int resId = getResId(name,R.array.class);
        adapter = ArrayAdapter.createFromResource(getActivity(),resId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> argO, View argl, int arg2, long arg3) {
                textview.setText("你选择的城市是:" + adapter.getItem(arg2));
                chosenCity = name+"_"+adapter.getItem(arg2);
                argO.setVisibility(View.VISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> argO) {
                // TODO Auto-generated method stub
                textview.setText("NONE");
                argO.setVisibility(View.VISIBLE);
            }
        });

        // Button 1
        btn_confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Bar_1_Display.class);
                intent.putExtra("message", chosenCity);
                startActivity(intent);
            }
        });

        // Button 2
        btn_color.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent_color = new Intent(getContext(), Bar_1_Color.class);
                startActivityForResult(intent_color,1); //1:color
            }
        });

        // Button 3 flag
        btn_flag.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent_flag = new Intent(getContext(), Bar_1_Flag.class);
                intent_flag.putExtra("message", chosenCity);
                startActivity(intent_flag);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            int colorInt  = data.getIntExtra("color",0);
            setColor(name,colorInt);
        }
    }

    // renew the chosen information
    public void renewAdapter(String n){
        name = n;
        int resId = getResId(name,R.array.class);
        if(resId==-1) return;
        adapter = ArrayAdapter.createFromResource(getActivity(),resId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    // function for get resource ID by name
    public int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    //
    public void setColor(String province, int color){
        System.out.println(province);
        map.setProvinceColor(province,color);
    }

}
