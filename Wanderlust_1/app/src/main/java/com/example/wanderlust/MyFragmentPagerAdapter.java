package com.example.wanderlust;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.wanderlust.Bar_1.Bar_1;

import java.util.ArrayList;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private Bar_1 fragment1 = null;
    private Bar_2 fragment2 = null;
    private Bar_3 fragment3 = null;
    private Bar_4 fragment4 = null;
    private ArrayList<Fragment> list = new ArrayList<Fragment>();

    public MyFragmentPagerAdapter(FragmentManager manager){
        super(manager);
        fragment1 = new Bar_1();
        fragment2 = new Bar_2();
        fragment3 = new Bar_3();
        fragment4 = new Bar_4();
        list.add(fragment1);
        list.add(fragment2);
        list.add(fragment3);
        list.add(fragment4);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }


}
