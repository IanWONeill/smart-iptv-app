package com.nst.yourname.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import com.nst.yourname.model.LiveStreamCategoryIdDBModel;
import com.nst.yourname.view.fragment.TVArchiveFragment;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TVArchiveCategoriesAdapter extends FragmentPagerAdapter {
    private String[] LiveStreamCategoriesIds;
    private Context context;
    private String[] liveStreamCategoriesNames;
    final int liveStreamTotalCategories;
    private FragmentManager mFragmentManager;
    private Map<Integer, String> mFragmentTags = new HashMap();

    public TVArchiveCategoriesAdapter(List<LiveStreamCategoryIdDBModel> list, FragmentManager fragmentManager, Context context2) {
        super(fragmentManager);
        this.mFragmentManager = fragmentManager;
        this.liveStreamTotalCategories = list.size();
        this.liveStreamCategoriesNames = new String[this.liveStreamTotalCategories];
        this.LiveStreamCategoriesIds = new String[this.liveStreamTotalCategories];
        this.context = context2;
        for (int i = 0; i < this.liveStreamTotalCategories; i++) {
            String liveStreamCategoryName = list.get(i).getLiveStreamCategoryName();
            String liveStreamCategoryID = list.get(i).getLiveStreamCategoryID();
            this.liveStreamCategoriesNames[i] = liveStreamCategoryName;
            this.LiveStreamCategoriesIds[i] = liveStreamCategoryID;
        }
    }

    @Override // android.support.v4.view.PagerAdapter
    public int getCount() {
        return this.liveStreamTotalCategories;
    }

    @Override // android.support.v4.app.FragmentPagerAdapter
    public Fragment getItem(int i) {
        return TVArchiveFragment.newInstance(this.LiveStreamCategoriesIds[i]);
    }

    @Override // android.support.v4.view.PagerAdapter
    public CharSequence getPageTitle(int i) {
        return this.liveStreamCategoriesNames[i];
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

    public TVArchiveFragment getFragment(int i) {
        String str = this.mFragmentTags.get(Integer.valueOf(i));
        if (str == null) {
            return null;
        }
        return (TVArchiveFragment) this.mFragmentManager.findFragmentByTag(str);
    }
}
