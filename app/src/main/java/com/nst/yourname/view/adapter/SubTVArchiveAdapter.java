package com.nst.yourname.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.model.database.ExternalPlayerDataBase;
import com.nst.yourname.model.pojo.ExternalPlayerModelClass;
import com.nst.yourname.model.pojo.XMLTVProgrammePojo;
import com.nst.yourname.view.activity.PlayExternalPlayerActivity;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings("ALL")
public class SubTVArchiveAdapter extends RecyclerView.Adapter<SubTVArchiveAdapter.MyViewHolder> {
    private static SharedPreferences loginPreferencesSharedPref_time_format;
    public Context context;
    String currentFormatDateAfter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(new Date());
    private ArrayList<XMLTVProgrammePojo> dataSet;
    public ArrayList<ExternalPlayerModelClass> externalPlayerList;
    private final String getActiveLiveStreamCategoryId;
    private SharedPreferences loginPreferencesSharedPref;
    private final int nowPlaying;
    private final boolean nowPlayingFlag;
    public final String streamChannelDuration;
    public final String streamChannelID;
    public final String streamID;
    public final String streamIcon;
    public final String streamName;
    public final String streamNum;

    public void filter(String str, TextView textView) {
    }

    /*public class MyViewHolder_ViewBinding implements Unbinder {
        private MyViewHolder target;

        @UiThread
        public MyViewHolder_ViewBinding(MyViewHolder myViewHolder, View view) {
            this.target = myViewHolder;
            myViewHolder.tvDateTime = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_date_time, "field 'tvDateTime'", TextView.class);
            myViewHolder.tvChannelName = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_channel_name, "field 'tvChannelName'", TextView.class);
            myViewHolder.tvNowPlaying = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_now_playing, "field 'tvNowPlaying'", TextView.class);
            myViewHolder.rl_archive_layout = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_archive_layout, "field 'rl_archive_layout'", RelativeLayout.class);
            myViewHolder.ll_main_layout = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_main_layout, "field 'll_main_layout'", LinearLayout.class);
        }

        @Override // butterknife.Unbinder
        @CallSuper
        public void unbind() {
            MyViewHolder myViewHolder = this.target;
            if (myViewHolder != null) {
                this.target = null;
                myViewHolder.tvDateTime = null;
                myViewHolder.tvChannelName = null;
                myViewHolder.tvNowPlaying = null;
                myViewHolder.rl_archive_layout = null;
                myViewHolder.ll_main_layout = null;
                return;
            }
            throw new IllegalStateException("Bindings already cleared.");
        }
    }*/

    public SubTVArchiveAdapter(ArrayList arrayList, int i, boolean z, String str, String str2, String str3, String str4, String str5, String str6, String str7, Context context2) {
        this.dataSet = arrayList;
        this.context = context2;
        this.nowPlaying = i;
        this.getActiveLiveStreamCategoryId = str;
        this.nowPlayingFlag = z;
        this.streamID = str2;
        this.streamNum = str3;
        this.streamName = str4;
        this.streamIcon = str5;
        this.streamChannelID = str6;
        this.streamChannelDuration = str7;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_main_layout)
        LinearLayout ll_main_layout;
        @BindView(R.id.rl_archive_layout)
        RelativeLayout rl_archive_layout;
        @BindView(R.id.tv_channel_name)
        TextView tvChannelName;
        @BindView(R.id.tv_date_time)
        TextView tvDateTime;
        @BindView(R.id.tv_now_playing)
        TextView tvNowPlaying;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
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
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate((int) R.layout.live_streams_epg_layout, viewGroup, false);
        if (inflate != null) {
            inflate.setFocusable(true);
        }
        return new MyViewHolder(inflate);
    }

    @SuppressLint({"SetTextI18n"})
    @RequiresApi(api = 19)
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        if (this.context != null) {
            this.loginPreferencesSharedPref = this.context.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_PLAYER, 0);
            final String string = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SELECTED_PLAYER, "");
            String start = this.dataSet.get(i).getStart();
            String[] split = start.split("\\s+");
            String stop = this.dataSet.get(i).getStop();
            String[] split2 = stop.split("\\s+");
            DateFormat.getInstance();
            final String valueOf = String.valueOf(datediffernce(start, stop));
            loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
            loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, "");
            final String startDateFormated = startDateFormated(start);
            String str = new String(Base64.decode(this.dataSet.get(i).getTitle(), 0), StandardCharsets.UTF_8);
            TextView textView = myViewHolder.tvDateTime;
            textView.setText(split[1].substring(0, split[1].length() - 3) + " - " + split2[1].substring(0, split[1].length() - 3));
            myViewHolder.tvChannelName.setText(str);
            if (this.currentFormatDateAfter == null || !this.currentFormatDateAfter.equals(this.getActiveLiveStreamCategoryId)) {
                myViewHolder.rl_archive_layout.setBackgroundColor(this.context.getResources().getColor(R.color.tv_archive));
            } else if (i != this.nowPlaying || !this.nowPlayingFlag) {
                myViewHolder.rl_archive_layout.setBackgroundColor(this.context.getResources().getColor(R.color.tv_archive));
            } else {
                myViewHolder.rl_archive_layout.setBackgroundColor(this.context.getResources().getColor(R.color.active_green));
            }
            myViewHolder.rl_archive_layout.setOnLongClickListener(new View.OnLongClickListener() {
                /* class com.nst.yourname.view.adapter.SubTVArchiveAdapter.AnonymousClass1 */

                public boolean onLongClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(SubTVArchiveAdapter.this.context, view);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_players_selection, popupMenu.getMenu());
                    ArrayList unused = SubTVArchiveAdapter.this.externalPlayerList = new ArrayList();
                    ArrayList unused2 = SubTVArchiveAdapter.this.externalPlayerList = new ExternalPlayerDataBase(SubTVArchiveAdapter.this.context).getExternalPlayer();
                    if (SubTVArchiveAdapter.this.externalPlayerList != null && SubTVArchiveAdapter.this.externalPlayerList.size() > 0) {
                        for (int i = 0; i < SubTVArchiveAdapter.this.externalPlayerList.size(); i++) {
                            popupMenu.getMenu().add(0, i, i, ((ExternalPlayerModelClass) SubTVArchiveAdapter.this.externalPlayerList.get(i)).getAppname());
                        }
                    }
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        /* class com.nst.yourname.view.adapter.SubTVArchiveAdapter.AnonymousClass1.AnonymousClass1 */

                        public boolean onMenuItemClick(MenuItem menuItem) {
                            int i;
                            try {
                                i = Integer.parseInt(SubTVArchiveAdapter.this.streamID);
                            } catch (NumberFormatException unused) {
                                i = -1;
                            }
                            try {
                                if (SubTVArchiveAdapter.this.externalPlayerList != null && SubTVArchiveAdapter.this.externalPlayerList.size() > 0) {
                                    int i2 = 0;
                                    while (true) {
                                        if (i2 >= SubTVArchiveAdapter.this.externalPlayerList.size()) {
                                            break;
                                        } else if (menuItem.getItemId() == i2) {
                                            String timeshiftUrl = com.nst.yourname.miscelleneious.common.Utils.getTimeshiftUrl(SubTVArchiveAdapter.this.context, i, startDateFormated, valueOf);
                                            Intent intent = new Intent(SubTVArchiveAdapter.this.context, PlayExternalPlayerActivity.class);
                                            intent.putExtra("url", timeshiftUrl);
                                            intent.putExtra(AppConst.APP_NAME, ((ExternalPlayerModelClass) SubTVArchiveAdapter.this.externalPlayerList.get(i2)).getAppname());
                                            intent.putExtra(AppConst.PACKAGE_NAME, ((ExternalPlayerModelClass) SubTVArchiveAdapter.this.externalPlayerList.get(i2)).getPackagename());
                                            SubTVArchiveAdapter.this.context.startActivity(intent);
                                            break;
                                        } else {
                                            i2++;
                                        }
                                    }
                                }
                            } catch (Exception unused2) {
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                    return false;
                }
            });
            myViewHolder.ll_main_layout.setOnLongClickListener(new View.OnLongClickListener() {
                /* class com.nst.yourname.view.adapter.SubTVArchiveAdapter.AnonymousClass2 */

                public boolean onLongClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(SubTVArchiveAdapter.this.context, view);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_players_selection, popupMenu.getMenu());
                    ArrayList unused = SubTVArchiveAdapter.this.externalPlayerList = new ArrayList();
                    ArrayList unused2 = SubTVArchiveAdapter.this.externalPlayerList = new ExternalPlayerDataBase(SubTVArchiveAdapter.this.context).getExternalPlayer();
                    if (SubTVArchiveAdapter.this.externalPlayerList != null && SubTVArchiveAdapter.this.externalPlayerList.size() > 0) {
                        for (int i = 0; i < SubTVArchiveAdapter.this.externalPlayerList.size(); i++) {
                            popupMenu.getMenu().add(0, i, i, ((ExternalPlayerModelClass) SubTVArchiveAdapter.this.externalPlayerList.get(i)).getAppname());
                        }
                    }
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        /* class com.nst.yourname.view.adapter.SubTVArchiveAdapter.AnonymousClass2.AnonymousClass1 */

                        public boolean onMenuItemClick(MenuItem menuItem) {
                            int i;
                            try {
                                i = Integer.parseInt(SubTVArchiveAdapter.this.streamID);
                            } catch (NumberFormatException unused) {
                                i = -1;
                            }
                            try {
                                if (SubTVArchiveAdapter.this.externalPlayerList != null && SubTVArchiveAdapter.this.externalPlayerList.size() > 0) {
                                    int i2 = 0;
                                    while (true) {
                                        if (i2 >= SubTVArchiveAdapter.this.externalPlayerList.size()) {
                                            break;
                                        } else if (menuItem.getItemId() == i2) {
                                            String timeshiftUrl = com.nst.yourname.miscelleneious.common.Utils.getTimeshiftUrl(SubTVArchiveAdapter.this.context, i, startDateFormated, valueOf);
                                            Intent intent = new Intent(SubTVArchiveAdapter.this.context, PlayExternalPlayerActivity.class);
                                            intent.putExtra("url", timeshiftUrl);
                                            intent.putExtra(AppConst.APP_NAME, ((ExternalPlayerModelClass) SubTVArchiveAdapter.this.externalPlayerList.get(i2)).getAppname());
                                            intent.putExtra(AppConst.PACKAGE_NAME, ((ExternalPlayerModelClass) SubTVArchiveAdapter.this.externalPlayerList.get(i2)).getPackagename());
                                            SubTVArchiveAdapter.this.context.startActivity(intent);
                                            break;
                                        } else {
                                            i2++;
                                        }
                                    }
                                }
                            } catch (Exception unused2) {
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                    return true;
                }
            });
            myViewHolder.rl_archive_layout.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.SubTVArchiveAdapter.AnonymousClass3 */

                public void onClick(View view) {
                    int i;
                    try {
                        i = Integer.parseInt(SubTVArchiveAdapter.this.streamID);
                    } catch (NumberFormatException unused) {
                        i = -1;
                    }
                    try {
                        com.nst.yourname.miscelleneious.common.Utils.playWithPlayerArchive(SubTVArchiveAdapter.this.context, string, i, SubTVArchiveAdapter.this.streamNum, SubTVArchiveAdapter.this.streamName, SubTVArchiveAdapter.this.streamChannelID, SubTVArchiveAdapter.this.streamIcon, startDateFormated, SubTVArchiveAdapter.this.streamChannelDuration, valueOf);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            myViewHolder.ll_main_layout.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.SubTVArchiveAdapter.AnonymousClass4 */

                public void onClick(View view) {
                    int i;
                    try {
                        i = Integer.parseInt(SubTVArchiveAdapter.this.streamID);
                    } catch (NumberFormatException unused) {
                        i = -1;
                    }
                    com.nst.yourname.miscelleneious.common.Utils.playWithPlayerArchive(SubTVArchiveAdapter.this.context, string, i, SubTVArchiveAdapter.this.streamNum, SubTVArchiveAdapter.this.streamName, SubTVArchiveAdapter.this.streamChannelID, SubTVArchiveAdapter.this.streamIcon, startDateFormated, SubTVArchiveAdapter.this.streamChannelDuration, valueOf);
                }
            });
        }
    }

    private String startDateFormated(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        new Date(System.currentTimeMillis());
        try {
            return new SimpleDateFormat("yyyy-MM-dd:HH-mm", Locale.US).format(simpleDateFormat.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private long datediffernce(String str, String str2) {
        long j;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        new Date(System.currentTimeMillis());
        new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            j = simpleDateFormat.parse(str2).getTime() - simpleDateFormat.parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            j = -1;
        }
        long j2 = j / 1000;
        long j3 = j2 / 60;
        long j4 = j2 % 60;
        return j3;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.dataSet.size();
    }
}
