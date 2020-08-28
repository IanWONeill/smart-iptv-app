package com.nst.yourname.view.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.view.adapter.ParentalControlPagerAdapter;
import java.util.ArrayList;

public class ParentalCotrolFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context context;
    Typeface fontOPenSansBold;
    Typeface fontOPenSansRegular;
    @BindView(R.id.iv_line)
    ImageView ivLine;
    @BindView(R.id.line_below_tabs)
    View lineBelowTabs;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    private FragmentActivity myContext;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.rl_my_invoices)
    RelativeLayout rlMyInvoices;
    private SearchView searchView;
    ArrayList<Integer> tabInvoicesTotalCount = new ArrayList<>();
    @BindView(R.id.tab_layout_invoices)
    TabLayout tabLayoutInvoices;
    private Toolbar toolbar;
    @BindView(R.id.tv_my_invoices)
    TextView tvMyInvoices;
    Unbinder unbinder;
    @BindView(R.id.view_line_my_invoices)
    View viewLineMyInvoices;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static ParentalCotrolFragment newInstance(String str, String str2) {
        ParentalCotrolFragment parentalCotrolFragment = new ParentalCotrolFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, str);
        bundle.putString(ARG_PARAM2, str2);
        parentalCotrolFragment.setArguments(bundle);
        return parentalCotrolFragment;
    }

    @Override // android.support.v4.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.mParam1 = getArguments().getString(ARG_PARAM1);
            this.mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [?, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate((int) R.layout.fragment_parental_cotrol, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        initialize();
        return inflate;
    }

    private void initialize() {
        this.context = getContext();
        getActivity().getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        setInvoicesTab();
    }

    private void setInvoicesTab() {
        String currentAPPType = SharepreferenceDBHandler.getCurrentAPPType(this.context);
        if (currentAPPType.equals(AppConst.TYPE_M3U)) {
            this.tabLayoutInvoices.addTab(this.tabLayoutInvoices.newTab().setText("ALL"));
        } else if (Utils.getIsXtream1_06(this.context)) {
            this.tabLayoutInvoices.addTab(this.tabLayoutInvoices.newTab().setText(getResources().getString(R.string.categories)));
            this.tabLayoutInvoices.addTab(this.tabLayoutInvoices.newTab().setText(getResources().getString(R.string.vod_categories)));
            this.tabLayoutInvoices.addTab(this.tabLayoutInvoices.newTab().setText(getResources().getString(R.string.series_categories)));
        } else {
            this.tabLayoutInvoices.addTab(this.tabLayoutInvoices.newTab().setText(getResources().getString(R.string.categories)));
            this.tabLayoutInvoices.addTab(this.tabLayoutInvoices.newTab().setText(getResources().getString(R.string.vod_categories)));
            this.tabLayoutInvoices.addTab(this.tabLayoutInvoices.newTab().setText(getResources().getString(R.string.series_categories)));
        }
        this.tabLayoutInvoices.addTab(this.tabLayoutInvoices.newTab().setText(getResources().getString(R.string.update_password_heading)));
        this.tabLayoutInvoices.setTabGravity(0);
        final ParentalControlPagerAdapter parentalControlPagerAdapter = new ParentalControlPagerAdapter(getChildFragmentManager(), this.tabLayoutInvoices.getTabCount(), getContext(), this.tabInvoicesTotalCount, currentAPPType);
        this.pager.setAdapter(parentalControlPagerAdapter);
        this.tabLayoutInvoices.setupWithViewPager(this.pager);
        for (int i = 0; i < this.tabLayoutInvoices.getTabCount(); i++) {
            this.tabLayoutInvoices.getTabAt(i).setCustomView(parentalControlPagerAdapter.getTabView(i));
        }
        View customView = this.tabLayoutInvoices.getTabAt(0).getCustomView();
        View customView2 = this.tabLayoutInvoices.getTabAt(1).getCustomView();
        parentalControlPagerAdapter.setDefaultOpningViewTab(customView, this.fontOPenSansBold);
        parentalControlPagerAdapter.setSecondViewTab(customView2, this.fontOPenSansBold);
        this.pager.setCurrentItem(0);
        this.pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.tabLayoutInvoices));
        this.tabLayoutInvoices.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            /* class com.nst.yourname.view.fragment.ParentalCotrolFragment.AnonymousClass1 */

            @Override // android.support.design.widget.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
            }

            @Override // android.support.design.widget.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                ParentalCotrolFragment.this.pager.setCurrentItem(tab.getPosition());
                int position = tab.getPosition();
                View customView = tab.getCustomView();
                switch (position) {
                    case 0:
                        parentalControlPagerAdapter.selectPaidTabView(customView, ParentalCotrolFragment.this.fontOPenSansBold, position);
                        return;
                    case 1:
                        parentalControlPagerAdapter.selectVODCatTabView(customView, ParentalCotrolFragment.this.fontOPenSansBold, position);
                        return;
                    case 2:
                        parentalControlPagerAdapter.selectVODCatTabView(customView, ParentalCotrolFragment.this.fontOPenSansBold, position);
                        return;
                    case 3:
                        parentalControlPagerAdapter.selectUnpaidTabView(customView, ParentalCotrolFragment.this.fontOPenSansBold);
                        return;
                    default:
                        return;
                }
            }

            @Override // android.support.design.widget.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                View customView = tab.getCustomView();
                switch (position) {
                    case 0:
                        parentalControlPagerAdapter.unselectPaidTabView(customView, ParentalCotrolFragment.this.fontOPenSansRegular);
                        return;
                    case 1:
                        parentalControlPagerAdapter.unSelectVODCatTabView(customView, ParentalCotrolFragment.this.fontOPenSansRegular);
                        return;
                    case 2:
                        parentalControlPagerAdapter.unSelectVODCatTabView(customView, ParentalCotrolFragment.this.fontOPenSansRegular);
                        return;
                    case 3:
                        parentalControlPagerAdapter.unselectUnpaidTabView(customView, ParentalCotrolFragment.this.fontOPenSansRegular);
                        return;
                    default:
                        return;
                }
            }
        });
    }

    public void onButtonPressed(Uri uri) {
        if (this.mListener != null) {
            this.mListener.onFragmentInteraction(uri);
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onAttach(Context context2) {
        super.onAttach(context2);
        if (context2 instanceof OnFragmentInteractionListener) {
            this.mListener = (OnFragmentInteractionListener) context2;
            return;
        }
        throw new RuntimeException(context2.toString() + " must implement OnFragmentInteractionListener");
    }

    @Override // android.support.v4.app.Fragment
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }
}
