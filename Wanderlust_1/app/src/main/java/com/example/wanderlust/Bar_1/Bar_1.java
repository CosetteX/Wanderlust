package com.example.wanderlust.Bar_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.wanderlust.R;

public class Bar_1 extends Fragment {
    Bar_1_Map bar_1_map;
    Bar_1_Selector bar_1_selector;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        bar_1_selector = new Bar_1_Selector();
        bar_1_map = new Bar_1_Map(bar_1_selector);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.bar_1_map_fragment_dy, bar_1_map);
        transaction.replace(R.id.bar_1_ctrl_fragment_dy, bar_1_selector);
        transaction.commit();
        View view = inflater.inflate(R.layout.bar_1_dynamic, container, false);
        return view;
    }


}
