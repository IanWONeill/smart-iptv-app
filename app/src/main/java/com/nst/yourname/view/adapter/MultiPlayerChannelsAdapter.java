package com.nst.yourname.view.adapter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.common.collect.ComparisonChain;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.model.pojo.XMLTVProgrammePojo;
import com.nst.yourname.view.interfaces.MultiPlayerInterface;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MultiPlayerChannelsAdapter extends RecyclerView.Adapter<MultiPlayerChannelsAdapter.MyViewHolder> {
    private static SharedPreferences loginPreferencesSharedPref_time_format;
    private int adapterPosition;
    public String allowedFormat;
    public PopupWindow categoriesPopUpWindow;
    public PopupWindow channelsPopUpWindow;
    public List<LiveStreamsDBModel> completeList;
    public Context context;
    public List<LiveStreamsDBModel> filterList = new ArrayList();
    private boolean firstTimeFlag = true;
    private LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesSharedPref;
    private SharedPreferences loginPreferencesSharedPref_allowed_format;
    public String mFilePath;
    private String mFilePath1 = "";
    public List<LiveStreamsDBModel> moviesListl;
    public MultiPlayerInterface multiPlayerInterface;
    private SimpleDateFormat programTimeFormat;
    private String selected_language = "";
    public int text_last_size;
    public int text_size;

    /*public class MyViewHolder_ViewBinding implements Unbinder {
        private MyViewHolder target;

        @UiThread
        public MyViewHolder_ViewBinding(MyViewHolder myViewHolder, View view) {
            this.target = myViewHolder;
            myViewHolder.tvMovieCategoryName = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_movie_category_name, "field 'tvMovieCategoryName'", TextView.class);
            myViewHolder.pbPagingLoader = (ProgressBar) Utils.findRequiredViewAsType(view, R.id.pb_paging_loader, "field 'pbPagingLoader'", ProgressBar.class);
            myViewHolder.rlOuter = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_outer, "field 'rlOuter'", RelativeLayout.class);
            myViewHolder.rlListOfCategories = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_list_of_categories, "field 'rlListOfCategories'", RelativeLayout.class);
            myViewHolder.testing = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.testing, "field 'testing'", RelativeLayout.class);
            myViewHolder.tvChannelId = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_channel_id, "field 'tvChannelId'", TextView.class);
            myViewHolder.tvTime = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_time, "field 'tvTime'", TextView.class);
            myViewHolder.progressBar = (ProgressBar) Utils.findRequiredViewAsType(view, R.id.progressBar, "field 'progressBar'", ProgressBar.class);
            myViewHolder.tvCurrentLive = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_current_live, "field 'tvCurrentLive'", TextView.class);
            myViewHolder.ivChannelLogo = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_tv_icon, "field 'ivChannelLogo'", ImageView.class);
        }

        @Override // butterknife.Unbinder
        @CallSuper
        public void unbind() {
            MyViewHolder myViewHolder = this.target;
            if (myViewHolder != null) {
                this.target = null;
                myViewHolder.tvMovieCategoryName = null;
                myViewHolder.pbPagingLoader = null;
                myViewHolder.rlOuter = null;
                myViewHolder.rlListOfCategories = null;
                myViewHolder.testing = null;
                myViewHolder.tvChannelId = null;
                myViewHolder.tvTime = null;
                myViewHolder.progressBar = null;
                myViewHolder.tvCurrentLive = null;
                myViewHolder.ivChannelLogo = null;
                return;
            }
            throw new IllegalStateException("Bindings already cleared.");
        }
    }*/

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_tv_icon)
        ImageView ivChannelLogo;
        @BindView(R.id.pb_paging_loader)
        ProgressBar pbPagingLoader;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @BindView(R.id.rl_list_of_categories)
        RelativeLayout rlListOfCategories;
        @BindView(R.id.rl_outer)
        RelativeLayout rlOuter;
        @BindView(R.id.testing)
        RelativeLayout testing;
        @BindView(R.id.tv_channel_id)
        TextView tvChannelId;
        @BindView(R.id.tv_current_live)
        TextView tvCurrentLive;
        @BindView(R.id.tv_movie_category_name)
        TextView tvMovieCategoryName;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            setIsRecyclable(false);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: java.util.List<com.nst.yourname.model.LiveStreamsDBModel>} */
    /* JADX WARNING: Multi-variable type inference failed */
    public MultiPlayerChannelsAdapter(List<LiveStreamsDBModel> list, Context context2, PopupWindow popupWindow, MultiPlayerInterface multiPlayerInterface2, PopupWindow popupWindow2) {
        this.filterList.addAll(list);
        this.completeList = list;
        this.multiPlayerInterface = multiPlayerInterface2;
        this.moviesListl = list;
        this.context = context2;
        this.liveStreamDBHandler = new LiveStreamDBHandler(context2);
        this.selected_language = context2.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_LANGUAGE, 0).getString(AppConst.LOGIN_PREF_SELECTED_LANGUAGE, "");
        Collections.sort(this.moviesListl, new Comparator<LiveStreamsDBModel>() {
            /* class com.nst.yourname.view.adapter.MultiPlayerChannelsAdapter.AnonymousClass1 */

            public int compare(LiveStreamsDBModel liveStreamsDBModel, LiveStreamsDBModel liveStreamsDBModel2) {
                return ComparisonChain.start().compare(liveStreamsDBModel.getIdAuto(), liveStreamsDBModel2.getIdAuto()).result();
            }
        });
        this.channelsPopUpWindow = popupWindow;
        this.categoriesPopUpWindow = popupWindow2;
        if (!SharepreferenceDBHandler.getCurrentAPPType(context2).equals(AppConst.TYPE_M3U)) {
            playerdata();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0109, code lost:
        if (r4.equals(com.nst.yourname.miscelleneious.common.AppConst.HTTP) != false) goto L_0x010d;
     */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0110  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0133  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x014e  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0169  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x018c  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x01b8  */
    private void playerdata() {
        char c = 0;
        this.loginPreferencesSharedPref = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        this.loginPreferencesSharedPref_allowed_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_ALLOWED_FORMAT, 0);
        String string = this.loginPreferencesSharedPref.getString("username", "");
        String string2 = this.loginPreferencesSharedPref.getString("password", "");
        this.allowedFormat = this.loginPreferencesSharedPref_allowed_format.getString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "");
        if (this.allowedFormat != null && !this.allowedFormat.isEmpty() && !this.allowedFormat.equals("") && this.allowedFormat.equals("default")) {
            this.allowedFormat = "";
        } else if (this.allowedFormat != null && !this.allowedFormat.isEmpty() && !this.allowedFormat.equals("") && this.allowedFormat.equals("ts")) {
            this.allowedFormat = ".ts";
        } else if (this.allowedFormat == null || this.allowedFormat.isEmpty() || this.allowedFormat.equals("") || !this.allowedFormat.equals("m3u8")) {
            this.allowedFormat = "";
        } else {
            this.allowedFormat = ".m3u8";
        }
        String string3 = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_URL, "");
        String string4 = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_PROTOCOL, "");
        String string5 = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_HTTPS_PORT, "");
        String string6 = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_PORT, "");
        String string7 = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_RTMP_PORT, "");
        int hashCode = string4.hashCode();
        if (hashCode != 3213448) {
            if (hashCode != 3504631) {
                if (hashCode == 99617003 && string4.equals(AppConst.HTTPS)) {
                    c = 1;
                    switch (c) {
                        case 0:
                            if (!string3.startsWith("http://")) {
                                string3 = "http://" + string3;
                                break;
                            }
                            break;
                        case 1:
                            if (!string3.startsWith("https://")) {
                                string3 = "https://" + string3;
                            }
                            string6 = string5;
                            break;
                        case 2:
                            if (!string3.startsWith("rmtp://")) {
                                string3 = "rmtp://" + string3;
                            }
                            string6 = string7;
                            break;
                        default:
                            if (!string3.startsWith("http://") && !string3.startsWith("https://")) {
                                string3 = "http://" + string3;
                                break;
                            }
                    }
                    if (this.allowedFormat.equals("")) {
                        this.mFilePath1 = string3 + ":" + string6 + "/" + string + "/" + string2 + "/";
                    } else {
                        this.mFilePath1 = string3 + ":" + string6 + "/live/" + string + "/" + string2 + "/";
                    }
                    this.mFilePath = com.nst.yourname.miscelleneious.common.Utils.getFormattedUrl(this.mFilePath1);
                }
            } else if (string4.equals(AppConst.RMTP)) {
                c = 2;
                switch (c) {
                }
                if (this.allowedFormat.equals("")) {
                }
                this.mFilePath = com.nst.yourname.miscelleneious.common.Utils.getFormattedUrl(this.mFilePath1);
            }
        }
        c = 65535;
        switch (c) {
        }
        if (this.allowedFormat.equals("")) {
        }
        this.mFilePath = com.nst.yourname.miscelleneious.common.Utils.getFormattedUrl(this.mFilePath1);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [?, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    @Override // android.support.v7.widget.RecyclerView.Adapter
    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate((int) R.layout.layout_channels_on_video, viewGroup, false);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.iv_foraward_arrow);
        if (this.selected_language.equalsIgnoreCase("Arabic")) {
            imageView.setImageResource(R.drawable.left_icon_cat);
        }
        return new MyViewHolder(inflate);
    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        int percentageLeft;
        MyViewHolder myViewHolder2 = myViewHolder;
        int i2 = i;
        final LiveStreamsDBModel liveStreamsDBModel = this.moviesListl.get(i2);
        String name = liveStreamsDBModel.getName();
        liveStreamsDBModel.getStreamId();
        String num = liveStreamsDBModel.getNum();
        String epgChannelId = liveStreamsDBModel.getEpgChannelId();
        String streamIcon = liveStreamsDBModel.getStreamIcon();
        if (name != null && !name.equals("") && !name.isEmpty()) {
            myViewHolder2.tvMovieCategoryName.setText(name);
        }
        if (myViewHolder2.tvChannelId != null) {
            myViewHolder2.tvChannelId.setText(num);
        }
        myViewHolder2.tvTime.setText("");
        myViewHolder2.progressBar.setVisibility(8);
        myViewHolder2.tvCurrentLive.setText("");
        if (!(epgChannelId == null || epgChannelId.equals("") || this.liveStreamDBHandler == null)) {
            ArrayList<XMLTVProgrammePojo> epg = this.liveStreamDBHandler.getEPG(epgChannelId);
            if (epg != null) {
                int i3 = 0;
                while (true) {
                    if (i3 >= epg.size()) {
                        break;
                    }
                    String start = epg.get(i3).getStart();
                    String stop = epg.get(i3).getStop();
                    String title = epg.get(i3).getTitle();
                    epg.get(i3).getDesc();
                    Long valueOf = Long.valueOf(com.nst.yourname.miscelleneious.common.Utils.epgTimeConverter(start));
                    Long valueOf2 = Long.valueOf(com.nst.yourname.miscelleneious.common.Utils.epgTimeConverter(stop));
                    if (!com.nst.yourname.miscelleneious.common.Utils.isEventVisible(valueOf.longValue(), valueOf2.longValue(), this.context) || (percentageLeft = com.nst.yourname.miscelleneious.common.Utils.getPercentageLeft(valueOf.longValue(), valueOf2.longValue(), this.context)) == 0) {
                        i3++;
                    } else {
                        int i4 = 100 - percentageLeft;
                        if (i4 == 0 || title == null || title.equals("")) {
                            myViewHolder2.tvTime.setVisibility(8);
                            myViewHolder2.progressBar.setVisibility(8);
                            myViewHolder2.tvCurrentLive.setVisibility(8);
                        } else {
                            if (AppConst.LIVE_FLAG == 0) {
                                myViewHolder2.tvTime.setVisibility(0);
                                loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
                                this.programTimeFormat = new SimpleDateFormat(loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, ""));
                                TextView textView = myViewHolder2.tvTime;
                                textView.setText(this.programTimeFormat.format(valueOf) + " - " + this.programTimeFormat.format(valueOf2));
                            }
                            myViewHolder2.progressBar.setVisibility(0);
                            myViewHolder2.progressBar.setProgress(i4);
                            myViewHolder2.tvCurrentLive.setVisibility(0);
                            myViewHolder2.tvCurrentLive.setText(title);
                        }
                    }
                }
            }
            myViewHolder2.ivChannelLogo.setImageDrawable(null);
            if (streamIcon != null && !streamIcon.equals("")) {
                Picasso.with(this.context).load(streamIcon).placeholder((int) R.drawable.tv_icon).into(myViewHolder2.ivChannelLogo);
            } else if (Build.VERSION.SDK_INT >= 21) {
                myViewHolder2.ivChannelLogo.setImageDrawable(this.context.getResources().getDrawable(R.drawable.tv_icon, null));
            } else {
                myViewHolder2.ivChannelLogo.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.tv_icon));
            }
        }
        myViewHolder2.rlOuter.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.adapter.MultiPlayerChannelsAdapter.AnonymousClass2 */

            public void onClick(View view) {
                if (MultiPlayerChannelsAdapter.this.channelsPopUpWindow != null) {
                    MultiPlayerChannelsAdapter.this.channelsPopUpWindow.dismiss();
                }
                if (MultiPlayerChannelsAdapter.this.categoriesPopUpWindow != null) {
                    MultiPlayerChannelsAdapter.this.categoriesPopUpWindow.dismiss();
                }
                if (MultiPlayerChannelsAdapter.this.multiPlayerInterface == null) {
                    return;
                }
                if (SharepreferenceDBHandler.getCurrentAPPType(MultiPlayerChannelsAdapter.this.context).equals(AppConst.TYPE_M3U)) {
                    MultiPlayerChannelsAdapter.this.multiPlayerInterface.selectedChannelDetails(String.valueOf(Uri.parse(liveStreamsDBModel.getUrl())), liveStreamsDBModel.getNum());
                    return;
                }
                MultiPlayerInterface access$200 = MultiPlayerChannelsAdapter.this.multiPlayerInterface;
                access$200.selectedChannelDetails(String.valueOf(Uri.parse(MultiPlayerChannelsAdapter.this.mFilePath + liveStreamsDBModel.getStreamId() + MultiPlayerChannelsAdapter.this.allowedFormat)), liveStreamsDBModel.getNum());
            }
        });
        myViewHolder2.rlListOfCategories.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.adapter.MultiPlayerChannelsAdapter.AnonymousClass3 */

            public void onClick(View view) {
                if (MultiPlayerChannelsAdapter.this.channelsPopUpWindow != null) {
                    MultiPlayerChannelsAdapter.this.channelsPopUpWindow.dismiss();
                }
                if (MultiPlayerChannelsAdapter.this.categoriesPopUpWindow != null) {
                    MultiPlayerChannelsAdapter.this.categoriesPopUpWindow.dismiss();
                }
                if (SharepreferenceDBHandler.getCurrentAPPType(MultiPlayerChannelsAdapter.this.context).equals(AppConst.TYPE_M3U)) {
                    MultiPlayerChannelsAdapter.this.multiPlayerInterface.selectedChannelDetails(String.valueOf(Uri.parse(liveStreamsDBModel.getUrl())), liveStreamsDBModel.getNum());
                    return;
                }
                MultiPlayerInterface access$200 = MultiPlayerChannelsAdapter.this.multiPlayerInterface;
                access$200.selectedChannelDetails(String.valueOf(Uri.parse(MultiPlayerChannelsAdapter.this.mFilePath + liveStreamsDBModel.getStreamId() + MultiPlayerChannelsAdapter.this.allowedFormat)), liveStreamsDBModel.getNum());
            }
        });
        myViewHolder2.rlOuter.setOnFocusChangeListener(new OnFocusChangeAccountListener(myViewHolder2.rlOuter));
        if (i2 == 0 && this.firstTimeFlag) {
            myViewHolder2.rlOuter.requestFocus();
            this.firstTimeFlag = false;
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.moviesListl.size();
    }

    public void setVisibiltygone(ProgressBar progressBar) {
        if (progressBar != null) {
            progressBar.setVisibility(8);
        }
    }

    public void filter(final String str, final TextView textView) {
        new Thread(new Runnable() {
            /* class com.nst.yourname.view.adapter.MultiPlayerChannelsAdapter.AnonymousClass4 */

            public void run() {
                List unused = MultiPlayerChannelsAdapter.this.filterList = new ArrayList();
                int unused2 = MultiPlayerChannelsAdapter.this.text_size = str.length();
                if (MultiPlayerChannelsAdapter.this.filterList != null) {
                    MultiPlayerChannelsAdapter.this.filterList.clear();
                }
                if (TextUtils.isEmpty(str)) {
                    MultiPlayerChannelsAdapter.this.filterList.addAll(MultiPlayerChannelsAdapter.this.completeList);
                } else {
                    if ((MultiPlayerChannelsAdapter.this.moviesListl != null && MultiPlayerChannelsAdapter.this.moviesListl.size() == 0) || MultiPlayerChannelsAdapter.this.text_last_size > MultiPlayerChannelsAdapter.this.text_size) {
                        List unused3 = MultiPlayerChannelsAdapter.this.moviesListl = MultiPlayerChannelsAdapter.this.completeList;
                    }
                    if (MultiPlayerChannelsAdapter.this.moviesListl != null) {
                        for (LiveStreamsDBModel liveStreamsDBModel : MultiPlayerChannelsAdapter.this.moviesListl) {
                            if (liveStreamsDBModel.getName() != null && liveStreamsDBModel.getName().toLowerCase().contains(str.toLowerCase())) {
                                MultiPlayerChannelsAdapter.this.filterList.add(liveStreamsDBModel);
                            }
                        }
                    }
                }
                ((Activity) MultiPlayerChannelsAdapter.this.context).runOnUiThread(new Runnable() {
                    /* class com.nst.yourname.view.adapter.MultiPlayerChannelsAdapter.AnonymousClass4.AnonymousClass1 */

                    public void run() {
                        if (TextUtils.isEmpty(str)) {
                            List unused = MultiPlayerChannelsAdapter.this.moviesListl = MultiPlayerChannelsAdapter.this.completeList;
                        } else if (!MultiPlayerChannelsAdapter.this.filterList.isEmpty() || MultiPlayerChannelsAdapter.this.filterList.isEmpty()) {
                            List unused2 = MultiPlayerChannelsAdapter.this.moviesListl = MultiPlayerChannelsAdapter.this.filterList;
                        }
                        if (MultiPlayerChannelsAdapter.this.moviesListl != null && MultiPlayerChannelsAdapter.this.moviesListl.size() == 0) {
                            textView.setVisibility(0);
                        }
                        int unused3 = MultiPlayerChannelsAdapter.this.text_last_size = MultiPlayerChannelsAdapter.this.text_size;
                        MultiPlayerChannelsAdapter.this.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    private class OnFocusChangeAccountListener implements View.OnFocusChangeListener {
        private final View view;

        public OnFocusChangeAccountListener(View view2) {
            this.view = view2;
        }

        @SuppressLint({"ResourceType"})
        public void onFocusChange(View view2, boolean z) {
            float f = 1.0f;
            if (z) {
                if (z) {
                    f = 1.09f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                Log.e("id is", "" + this.view.getTag());
                this.view.setBackgroundResource(R.drawable.shape_list_categories_focused);
            } else if (!z) {
                if (z) {
                    f = 1.09f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                performAlphaAnimation(z);
                this.view.setBackgroundResource(R.drawable.shape_list_categories);
            }
        }

        private void performScaleXAnimation(float f) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "scaleX", f);
            ofFloat.setDuration(150L);
            ofFloat.start();
        }

        private void performScaleYAnimation(float f) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "scaleY", f);
            ofFloat.setDuration(150L);
            ofFloat.start();
        }

        private void performAlphaAnimation(boolean z) {
            if (z) {
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "alpha", z ? 0.6f : 0.5f);
                ofFloat.setDuration(150L);
                ofFloat.start();
            }
        }
    }
}
