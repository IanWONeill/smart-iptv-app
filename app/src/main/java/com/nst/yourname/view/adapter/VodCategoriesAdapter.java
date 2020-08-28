package com.nst.yourname.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import com.nst.yourname.model.LiveStreamCategoryIdDBModel;
import com.nst.yourname.view.fragment.VodFragment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VodCategoriesAdapter extends FragmentPagerAdapter {
    private Context context;
    final int liveStreamTotalCategories;
    private FragmentManager mFragmentManager;
    private Map<Integer, String> mFragmentTags = new HashMap();
    private ArrayList<LiveStreamCategoryIdDBModel> subCategoryList;
    private String[] vodCategoriesIds;
    private String[] vodCategoriesNames;

    public VodCategoriesAdapter(List<LiveStreamCategoryIdDBModel> list, FragmentManager fragmentManager, Context context2) {
        super(fragmentManager);
        this.mFragmentManager = fragmentManager;
        this.liveStreamTotalCategories = list.size();
        this.vodCategoriesNames = new String[this.liveStreamTotalCategories];
        this.vodCategoriesIds = new String[this.liveStreamTotalCategories];
        this.context = context2;
        this.subCategoryList = this.subCategoryList;
        for (int i = 0; i < this.liveStreamTotalCategories; i++) {
            String liveStreamCategoryName = list.get(i).getLiveStreamCategoryName();
            String liveStreamCategoryID = list.get(i).getLiveStreamCategoryID();
            this.vodCategoriesNames[i] = liveStreamCategoryName;
            this.vodCategoriesIds[i] = liveStreamCategoryID;
        }
    }

    @Override // android.support.v4.view.PagerAdapter
    public int getCount() {
        return this.liveStreamTotalCategories;
    }

    @Override // android.support.v4.app.FragmentPagerAdapter
    public Fragment getItem(int i) {
        return VodFragment.newInstance(this.vodCategoriesIds[i], this.vodCategoriesNames[i]);
    }

    @Override // android.support.v4.view.PagerAdapter
    public CharSequence getPageTitle(int i) {
        return this.vodCategoriesNames[i];
    }

    @Override // android.support.v4.app.FragmentPagerAdapter, android.support.v4.view.PagerAdapter
    @NonNull
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        Object instantiateItem = super.instantiateItem(viewGroup, i);
        if (instantiateItem instanceof Fragment) {
            this.mFragmentTags.put(Integer.valueOf(i), ((Fragment) instantiateItem).getTag());
        }
        return instantiateItem;
    }

    public VodFragment getFragment(int i) {
        String str = this.mFragmentTags.get(Integer.valueOf(i));
        if (str == null) {
            return null;
        }
        return (VodFragment) this.mFragmentManager.findFragmentByTag(str);
    }
}
