package com.fixnowitdeveloper.bookflix.AdapterUtil;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.fixnowitdeveloper.bookflix.ObjectUtil.PagerObject;

import java.util.ArrayList;

public class HomePagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<PagerObject> fragmentArrayList = new ArrayList<>();

    public HomePagerAdapter(FragmentManager fm, ArrayList<PagerObject> fragmentArrayList) {
        super(fm);
        this.fragmentArrayList = fragmentArrayList;
    }


    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentArrayList.get(position).getTitle();
    }
}
