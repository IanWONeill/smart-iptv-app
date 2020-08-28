package com.nst.yourname.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.view.fragment.ParentalControlCategoriesFragment;
import com.nst.yourname.view.fragment.ParentalControlM3UFragment;
import com.nst.yourname.view.fragment.ParentalControlSeriesCatFragment;
import com.nst.yourname.view.fragment.ParentalControlSettingFragment;
import com.nst.yourname.view.fragment.ParentalControlVODCatFragment;
import java.util.ArrayList;

public class ParentalControlPagerAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private String currentAPPType;
    boolean flag = false;
    private int mNumOfTabs = 4;
    private ArrayList<Integer> tabServicesTotalCount = new ArrayList<>();
    private String[] tabTitles = new String[4];

    public ParentalControlPagerAdapter(FragmentManager fragmentManager, int i, Context context2, ArrayList<Integer> arrayList, String str) {
        super(fragmentManager);
        this.mNumOfTabs = i;
        this.context = context2;
        this.currentAPPType = str;
        this.tabServicesTotalCount = arrayList;
        if (str.equals(AppConst.TYPE_M3U)) {
            this.tabTitles[0] = "ALL";
            this.tabTitles[1] = this.context.getResources().getString(R.string.update_password_heading);
        } else if (Utils.getIsXtream1_06(this.context)) {
            this.tabTitles[0] = this.context.getResources().getString(R.string.categories);
            this.tabTitles[1] = this.context.getResources().getString(R.string.vod_categories);
            this.tabTitles[2] = this.context.getResources().getString(R.string.series_categories);
            this.tabTitles[3] = this.context.getResources().getString(R.string.update_password_heading);
        } else {
            this.tabTitles[0] = this.context.getResources().getString(R.string.categories);
            this.tabTitles[1] = this.context.getResources().getString(R.string.vod_categories);
            this.tabTitles[2] = this.context.getResources().getString(R.string.series_categories);
            this.tabTitles[3] = this.context.getResources().getString(R.string.update_password_heading);
        }
    }

    @Override // android.support.v4.app.FragmentStatePagerAdapter
    public Fragment getItem(int i) {
        if (this.currentAPPType.equals(AppConst.TYPE_M3U)) {
            switch (i) {
                case 0:
                    return new ParentalControlM3UFragment();
                case 1:
                    return new ParentalControlSettingFragment();
                default:
                    return null;
            }
        } else if (Utils.getIsXtream1_06(this.context)) {
            switch (i) {
                case 0:
                    return new ParentalControlCategoriesFragment();
                case 1:
                    return new ParentalControlVODCatFragment();
                case 2:
                    return new ParentalControlSeriesCatFragment();
                case 3:
                    return new ParentalControlSettingFragment();
                default:
                    return null;
            }
        } else {
            switch (i) {
                case 0:
                    return new ParentalControlCategoriesFragment();
                case 1:
                    return new ParentalControlVODCatFragment();
                case 2:
                    return new ParentalControlSeriesCatFragment();
                case 3:
                    return new ParentalControlSettingFragment();
                default:
                    return null;
            }
        }
    }

    public View getTabView(int i) {
        View inflate = LayoutInflater.from(this.context).inflate((int) R.layout.tablayout_invoices, (ViewGroup) null);
        ((TextView) inflate.findViewById(R.id.tv_tab_service_name)).setText(this.tabTitles[i]);
        return inflate;
    }

    @Override // android.support.v4.view.PagerAdapter
    public int getCount() {
        return this.mNumOfTabs;
    }

    public void selectPaidTabView(View view, Typeface typeface, int i) {
        if (view != null) {
            TextView textView = (TextView) view.findViewById(R.id.tv_tab_service_name);
            textView.setTypeface(typeface);
            textView.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    public void selectUnpaidTabView(View view, Typeface typeface) {
        if (view != null) {
            TextView textView = (TextView) view.findViewById(R.id.tv_tab_service_name);
            textView.setTypeface(typeface);
            textView.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    public void unselectPaidTabView(View view, Typeface typeface) {
        if (view != null) {
            TextView textView = (TextView) view.findViewById(R.id.tv_tab_service_name);
            textView.setTypeface(typeface);
            textView.setTextColor(Color.parseColor("#000000"));
        }
    }

    public void unselectUnpaidTabView(View view, Typeface typeface) {
        if (view != null) {
            TextView textView = (TextView) view.findViewById(R.id.tv_tab_service_name);
            textView.setTypeface(typeface);
            textView.setTextColor(Color.parseColor("#000000"));
        }
    }

    public void setDefaultOpningViewTab(View view, Typeface typeface) {
        if (view != null) {
            TextView textView = (TextView) view.findViewById(R.id.tv_tab_service_name);
            textView.setTypeface(typeface);
            textView.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    public void setSecondViewTab(View view, Typeface typeface) {
        if (view != null) {
            TextView textView = (TextView) view.findViewById(R.id.tv_tab_service_name);
            textView.setTypeface(typeface);
            textView.setTextColor(Color.parseColor("#000000"));
        }
    }

    public void selectVODCatTabView(View view, Typeface typeface, int i) {
        if (view != null) {
            TextView textView = (TextView) view.findViewById(R.id.tv_tab_service_name);
            textView.setTypeface(typeface);
            textView.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    public void unSelectVODCatTabView(View view, Typeface typeface) {
        if (view != null) {
            TextView textView = (TextView) view.findViewById(R.id.tv_tab_service_name);
            textView.setTypeface(typeface);
            textView.setTextColor(Color.parseColor("#000000"));
        }
    }
}
