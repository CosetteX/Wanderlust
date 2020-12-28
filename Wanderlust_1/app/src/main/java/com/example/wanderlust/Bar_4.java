package com.example.wanderlust;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;


public class Bar_4 extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bar_4_setting, container, false);
        TextView textView = view.findViewById(R.id.bar_4_name);
        textView.setText("Mr.Zang");
        ImageView imageView = view.findViewById(R.id.bar_4_head);
        imageView.setImageResource(R.drawable.icon_map);
        // settings depend on functions
        /*TextView Swipe = null;
        wipe = view.findViewById(R.id.swipe_content);
        Swipe.setText("Hello");*/
        return view;
    }
}
