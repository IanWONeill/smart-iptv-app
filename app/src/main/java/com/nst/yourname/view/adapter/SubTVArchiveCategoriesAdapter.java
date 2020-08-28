package com.nst.yourname.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.nst.yourname.model.pojo.XMLTVProgrammePojo;
import com.nst.yourname.view.fragment.SubTVArchiveFragment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubTVArchiveCategoriesAdapter extends FragmentPagerAdapter {
    private String[] LiveStreamCategoriesIds;
    private Context context;
    private List<String> liveStreamCategoriesNames;
    private final ArrayList<XMLTVProgrammePojo> liveStreamEpgCallback;
    final int liveStreamTotalCategories;
    private FragmentManager mFragmentManager;
    private Map<Integer, String> mFragmentTags = new HashMap();
    private final String opened_channel_duration;
    private final String opened_channel_icon;
    private final String opened_channel_id;
    private final String opened_channel_name;
    private final String opened_num;
    private final String opened_stream_id;

    public SubTVArchiveCategoriesAdapter(List<String> list, ArrayList<XMLTVProgrammePojo> arrayList, String str, String str2, String str3, String str4, String str5, String str6, FragmentManager fragmentManager, Context context2) {
        super(fragmentManager);
        this.mFragmentManager = fragmentManager;
        this.liveStreamTotalCategories = list.size();
        this.liveStreamCategoriesNames = list;
        this.liveStreamEpgCallback = arrayList;
        this.opened_stream_id = str;
        this.opened_num = str2;
        this.opened_channel_name = str3;
        this.opened_channel_icon = str4;
        this.opened_channel_id = str5;
        this.opened_channel_duration = str6;
        this.context = context2;
    }

    @Override // android.support.v4.view.PagerAdapter
    public int getCount() {
        return this.liveStreamTotalCategories;
    }

    @Override // android.support.v4.app.FragmentPagerAdapter
    public Fragment getItem(int i) {
        return SubTVArchiveFragment.newInstance(this.liveStreamCategoriesNames.get(i), this.liveStreamEpgCallback, this.opened_stream_id, this.opened_num, this.opened_channel_name, this.opened_channel_icon, this.opened_channel_id, this.opened_channel_duration);
    }

    @Override // android.support.v4.view.PagerAdapter
    public CharSequence getPageTitle(int i) {
        return this.liveStreamCategoriesNames.get(i);
    }
}
