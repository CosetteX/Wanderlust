package com.example.wanderlust;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Bar_1_Map extends Fragment {


    Bar_1_Selector c;
    Bar_1_Map(Bar_1_Selector c){
        super();
        this.c=c;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = new MapView(this.getActivity(),c);
        return view;
    }
}
