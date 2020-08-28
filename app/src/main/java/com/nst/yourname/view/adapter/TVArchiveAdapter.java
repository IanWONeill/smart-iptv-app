package com.nst.yourname.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.model.FavouriteDBModel;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.model.pojo.XMLTVProgrammePojo;
import com.nst.yourname.view.activity.SubTVArchiveActivity;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class TVArchiveAdapter extends RecyclerView.Adapter<TVArchiveAdapter.MyViewHolder> {
    private static SharedPreferences loginPreferencesSharedPref_time_format;
    public List<LiveStreamsDBModel> completeList;
    public Context context;
    public List<LiveStreamsDBModel> dataSet;
    private DatabaseHandler database;
    private SharedPreferences.Editor editor;
    public List<LiveStreamsDBModel> filterList = new ArrayList();
    private LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesSharedPref;
    MyViewHolder myViewHolder;
    private SharedPreferences pref;
    private SimpleDateFormat programTimeFormat;

    /*public class MyViewHolder_ViewBinding implements Unbinder {
        private MyViewHolder target;

        @UiThread
        public MyViewHolder_ViewBinding(MyViewHolder myViewHolder, View view) {
            this.target = myViewHolder;
            myViewHolder.ivChannelLogo = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_channel_logo, "field 'ivChannelLogo'", ImageView.class);
            myViewHolder.tvChannelName = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_movie_name, "field 'tvChannelName'", TextView.class);
            myViewHolder.cardView = (CardView) Utils.findRequiredViewAsType(view, R.id.card_view, "field 'cardView'", CardView.class);
            myViewHolder.tvStreamOptions = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_streamOptions, "field 'tvStreamOptions'", TextView.class);
            myViewHolder.ivFavourite = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_favourite, "field 'ivFavourite'", ImageView.class);
            myViewHolder.rlStreamsLayout = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_streams_layout, "field 'rlStreamsLayout'", RelativeLayout.class);
            myViewHolder.rlChannelBottom = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_channel_bottom, "field 'rlChannelBottom'", RelativeLayout.class);
            myViewHolder.llMenu = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_menu, "field 'llMenu'", LinearLayout.class);
            myViewHolder.progressBar = (ProgressBar) Utils.findRequiredViewAsType(view, R.id.progressBar, "field 'progressBar'", ProgressBar.class);
            myViewHolder.tvCurrentLive = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_current_live, "field 'tvCurrentLive'", TextView.class);
            myViewHolder.tvTime = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_time, "field 'tvTime'", TextView.class);
            myViewHolder.rlMovieImage = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_movie_image, "field 'rlMovieImage'", RelativeLayout.class);
        }

        @Override // butterknife.Unbinder
        @CallSuper
        public void unbind() {
            MyViewHolder myViewHolder = this.target;
            if (myViewHolder != null) {
                this.target = null;
                myViewHolder.ivChannelLogo = null;
                myViewHolder.tvChannelName = null;
                myViewHolder.cardView = null;
                myViewHolder.tvStreamOptions = null;
                myViewHolder.ivFavourite = null;
                myViewHolder.rlStreamsLayout = null;
                myViewHolder.rlChannelBottom = null;
                myViewHolder.llMenu = null;
                myViewHolder.progressBar = null;
                myViewHolder.tvCurrentLive = null;
                myViewHolder.tvTime = null;
                myViewHolder.rlMovieImage = null;
                return;
            }
            throw new IllegalStateException("Bindings already cleared.");
        }
    }*/

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: java.util.List<com.nst.yourname.model.LiveStreamsDBModel>} */
    /* JADX WARNING: Multi-variable type inference failed */
    public TVArchiveAdapter(List<LiveStreamsDBModel> list, Context context2) {
        this.dataSet = list;
        this.context = context2;
        this.filterList.addAll(list);
        this.completeList = list;
        this.database = new DatabaseHandler(context2);
        this.liveStreamDBHandler = new LiveStreamDBHandler(context2);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.iv_channel_logo)
        ImageView ivChannelLogo;
        @BindView(R.id.iv_favourite)
        ImageView ivFavourite;
        @BindView(R.id.ll_menu)
        LinearLayout llMenu;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @BindView(R.id.rl_channel_bottom)
        RelativeLayout rlChannelBottom;
        @BindView(R.id.rl_movie_image)
        RelativeLayout rlMovieImage;
        @BindView(R.id.rl_streams_layout)
        RelativeLayout rlStreamsLayout;
        @BindView(R.id.tv_movie_name)
        TextView tvChannelName;
        @BindView(R.id.tv_current_live)
        TextView tvCurrentLive;
        @BindView(R.id.tv_streamOptions)
        TextView tvStreamOptions;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            setIsRecyclable(false);
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
        this.pref = this.context.getSharedPreferences("listgridview", 0);
        AppConst.LIVE_FLAG = this.pref.getInt(AppConst.LIVE_STREAM, 0);
        if (AppConst.LIVE_FLAG == 1) {
            this.myViewHolder = new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate((int) R.layout.live_streams_grid_layout, viewGroup, false));
            return this.myViewHolder;
        }
        this.myViewHolder = new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate((int) R.layout.live_streams_linear_layout, viewGroup, false));
        return this.myViewHolder;
    }

    /* JADX WARNING: Removed duplicated region for block: B:41:0x01cf  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x01df  */
    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder2, int i) {
        int i2;
        //ToDo: initialized...
        int i3 = 0;
        String streamIcon;
        ArrayList<FavouriteDBModel> checkFavourite;
        ArrayList<XMLTVProgrammePojo> epg;
        int percentageLeft;
        MyViewHolder myViewHolder3 = myViewHolder2;
        int i4 = i;
        if (this.context != null) {
            this.loginPreferencesSharedPref = this.context.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_PLAYER, 0);
            try {
                i2 = Integer.parseInt(this.dataSet.get(i4).getStreamId().trim());
            } catch (NumberFormatException unused) {
                i2 = -1;
            }
            String categoryId = this.dataSet.get(i4).getCategoryId();
            String epgChannelId = this.dataSet.get(i4).getEpgChannelId();
            myViewHolder3.tvTime.setText("");
            myViewHolder3.progressBar.setVisibility(8);
            myViewHolder3.tvCurrentLive.setText("");
            if (epgChannelId != null && !epgChannelId.equals("") && this.liveStreamDBHandler != null && (epg = this.liveStreamDBHandler.getEPG(epgChannelId)) != null) {
                int i5 = 0;
                while (true) {
                    if (i5 >= epg.size()) {
                        break;
                    }
                    String start = epg.get(i5).getStart();
                    String stop = epg.get(i5).getStop();
                    String title = epg.get(i5).getTitle();
                    epg.get(i5).getDesc();
                    Long valueOf = Long.valueOf(com.nst.yourname.miscelleneious.common.Utils.epgTimeConverter(start));
                    Long valueOf2 = Long.valueOf(com.nst.yourname.miscelleneious.common.Utils.epgTimeConverter(stop));
                    i3 = i2;
                    if (!com.nst.yourname.miscelleneious.common.Utils.isEventVisible(valueOf.longValue(), valueOf2.longValue(), this.context) || (percentageLeft = com.nst.yourname.miscelleneious.common.Utils.getPercentageLeft(valueOf.longValue(), valueOf2.longValue(), this.context)) == 0) {
                        i5++;
                        i2 = i3;
                    } else {
                        int i6 = 100 - percentageLeft;
                        if (i6 == 0 || title.equals("")) {
                            myViewHolder3.tvTime.setVisibility(8);
                            myViewHolder3.progressBar.setVisibility(8);
                            myViewHolder3.tvCurrentLive.setVisibility(8);
                        } else {
                            if (AppConst.LIVE_FLAG == 0) {
                                myViewHolder3.tvTime.setVisibility(0);
                                loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
                                this.programTimeFormat = new SimpleDateFormat(loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, ""));
                                TextView textView = myViewHolder3.tvTime;
                                textView.setText(this.programTimeFormat.format(valueOf) + " - " + this.programTimeFormat.format(valueOf2));
                            }
                            myViewHolder3.progressBar.setVisibility(0);
                            myViewHolder3.progressBar.setProgress(i6);
                            myViewHolder3.tvCurrentLive.setVisibility(0);
                            myViewHolder3.tvCurrentLive.setText(title);
                        }
                    }
                }
                final String num = this.dataSet.get(i4).getNum();
                final String name = this.dataSet.get(i4).getName();
                myViewHolder3.tvChannelName.setText(this.dataSet.get(i4).getName());
                streamIcon = this.dataSet.get(i4).getStreamIcon();
                final String epgChannelId2 = this.dataSet.get(i4).getEpgChannelId();
                final String tvArchiveDuration = this.dataSet.get(i4).getTvArchiveDuration();
                myViewHolder3.ivChannelLogo.setImageDrawable(null);
                if (streamIcon == null && !streamIcon.equals("")) {
                    Picasso.with(this.context).load(streamIcon).placeholder((int) R.drawable.logo_placeholder_white).into(myViewHolder3.ivChannelLogo);
                } else if (Build.VERSION.SDK_INT < 21) {
                    myViewHolder3.ivChannelLogo.setImageDrawable(this.context.getResources().getDrawable(R.drawable.logo_placeholder_white, null));
                } else {
                    myViewHolder3.ivChannelLogo.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.logo_placeholder_white));
                }
                int finalI = i3;
                String finalStreamIcon5 = streamIcon;
                myViewHolder3.cardView.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.adapter.TVArchiveAdapter.AnonymousClass1 */

                    public void onClick(View view) {
                        Intent intent = new Intent(TVArchiveAdapter.this.context, SubTVArchiveActivity.class);
                        intent.putExtra("OPENED_CHANNEL_ID", epgChannelId2);
                        intent.putExtra("OPENED_STREAM_ID", finalI);
                        intent.putExtra("OPENED_NUM", num);
                        intent.putExtra("OPENED_NAME", name);
                        intent.putExtra("OPENED_STREAM_ICON", finalStreamIcon5);
                        intent.putExtra("OPENED_ARCHIVE_DURATION", tvArchiveDuration);
                        TVArchiveAdapter.this.context.startActivity(intent);
                    }
                });
                int finalI1 = i3;
                String finalStreamIcon4 = streamIcon;
                myViewHolder3.rlMovieImage.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.adapter.TVArchiveAdapter.AnonymousClass2 */

                    public void onClick(View view) {
                        Intent intent = new Intent(TVArchiveAdapter.this.context, SubTVArchiveActivity.class);
                        intent.putExtra("OPENED_CHANNEL_ID", epgChannelId2);
                        intent.putExtra("OPENED_STREAM_ID", finalI1);
                        intent.putExtra("OPENED_NUM", num);
                        intent.putExtra("OPENED_NAME", name);
                        intent.putExtra("OPENED_STREAM_ICON", finalStreamIcon4);
                        intent.putExtra("OPENED_ARCHIVE_DURATION", tvArchiveDuration);
                        TVArchiveAdapter.this.context.startActivity(intent);
                    }
                });
                int finalI2 = i3;
                String finalStreamIcon3 = streamIcon;
                myViewHolder3.rlStreamsLayout.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.adapter.TVArchiveAdapter.AnonymousClass3 */

                    public void onClick(View view) {
                        Intent intent = new Intent(TVArchiveAdapter.this.context, SubTVArchiveActivity.class);
                        intent.putExtra("OPENED_CHANNEL_ID", epgChannelId2);
                        intent.putExtra("OPENED_STREAM_ID", finalI2);
                        intent.putExtra("OPENED_NUM", num);
                        intent.putExtra("OPENED_NAME", name);
                        intent.putExtra("OPENED_STREAM_ICON", finalStreamIcon3);
                        intent.putExtra("OPENED_ARCHIVE_DURATION", tvArchiveDuration);
                        TVArchiveAdapter.this.context.startActivity(intent);
                    }
                });
                checkFavourite = this.database.checkFavourite(i3, categoryId, "live", SharepreferenceDBHandler.getUserID(this.context));
                if (checkFavourite != null || checkFavourite.size() <= 0) {
                    myViewHolder3.ivFavourite.setVisibility(4);
                } else {
                    myViewHolder3.ivFavourite.setVisibility(0);
                    return;
                }
            }
            i3 = i2;
            final String num2 = this.dataSet.get(i4).getNum();
            final String name2 = this.dataSet.get(i4).getName();
            myViewHolder3.tvChannelName.setText(this.dataSet.get(i4).getName());
            streamIcon = this.dataSet.get(i4).getStreamIcon();
            final String epgChannelId22 = this.dataSet.get(i4).getEpgChannelId();
            final String tvArchiveDuration2 = this.dataSet.get(i4).getTvArchiveDuration();
            myViewHolder3.ivChannelLogo.setImageDrawable(null);
            if (streamIcon == null) {
            }
            if (Build.VERSION.SDK_INT < 21) {
            }
            int finalI3 = i3;
            String finalStreamIcon = streamIcon;
            myViewHolder3.cardView.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.TVArchiveAdapter.AnonymousClass1 */

                public void onClick(View view) {
                    Intent intent = new Intent(TVArchiveAdapter.this.context, SubTVArchiveActivity.class);
                    intent.putExtra("OPENED_CHANNEL_ID", epgChannelId22);
                    intent.putExtra("OPENED_STREAM_ID", finalI3);
                    intent.putExtra("OPENED_NUM", num2);
                    intent.putExtra("OPENED_NAME", name2);
                    intent.putExtra("OPENED_STREAM_ICON", finalStreamIcon);
                    intent.putExtra("OPENED_ARCHIVE_DURATION", tvArchiveDuration2);
                    TVArchiveAdapter.this.context.startActivity(intent);
                }
            });
            String finalStreamIcon1 = streamIcon;
            int finalI4 = i3;
            myViewHolder3.rlMovieImage.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.TVArchiveAdapter.AnonymousClass2 */

                public void onClick(View view) {
                    Intent intent = new Intent(TVArchiveAdapter.this.context, SubTVArchiveActivity.class);
                    intent.putExtra("OPENED_CHANNEL_ID", epgChannelId22);
                    intent.putExtra("OPENED_STREAM_ID", finalI4);
                    intent.putExtra("OPENED_NUM", num2);
                    intent.putExtra("OPENED_NAME", name2);
                    intent.putExtra("OPENED_STREAM_ICON", finalStreamIcon1);
                    intent.putExtra("OPENED_ARCHIVE_DURATION", tvArchiveDuration2);
                    TVArchiveAdapter.this.context.startActivity(intent);
                }
            });
            int finalI5 = i3;
            String finalStreamIcon2 = streamIcon;
            myViewHolder3.rlStreamsLayout.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.TVArchiveAdapter.AnonymousClass3 */

                public void onClick(View view) {
                    Intent intent = new Intent(TVArchiveAdapter.this.context, SubTVArchiveActivity.class);
                    intent.putExtra("OPENED_CHANNEL_ID", epgChannelId22);
                    intent.putExtra("OPENED_STREAM_ID", finalI5);
                    intent.putExtra("OPENED_NUM", num2);
                    intent.putExtra("OPENED_NAME", name2);
                    intent.putExtra("OPENED_STREAM_ICON", finalStreamIcon2);
                    intent.putExtra("OPENED_ARCHIVE_DURATION", tvArchiveDuration2);
                    TVArchiveAdapter.this.context.startActivity(intent);
                }
            });
            checkFavourite = this.database.checkFavourite(i3, categoryId, "live", SharepreferenceDBHandler.getUserID(this.context));
            if (checkFavourite != null) {
            }
            myViewHolder3.ivFavourite.setVisibility(4);
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.dataSet.size();
    }

    public void filter(final String str, final TextView textView) {
        new Thread(new Runnable() {
            /* class com.nst.yourname.view.adapter.TVArchiveAdapter.AnonymousClass4 */

            public void run() {
                List unused = TVArchiveAdapter.this.filterList = new ArrayList();
                if (TVArchiveAdapter.this.filterList != null) {
                    TVArchiveAdapter.this.filterList.clear();
                }
                if (TextUtils.isEmpty(str)) {
                    TVArchiveAdapter.this.filterList.addAll(TVArchiveAdapter.this.completeList);
                } else {
                    for (LiveStreamsDBModel liveStreamsDBModel : TVArchiveAdapter.this.dataSet) {
                        if (liveStreamsDBModel.getName().toLowerCase().contains(str.toLowerCase())) {
                            TVArchiveAdapter.this.filterList.add(liveStreamsDBModel);
                        }
                    }
                }
                ((Activity) TVArchiveAdapter.this.context).runOnUiThread(new Runnable() {
                    /* class com.nst.yourname.view.adapter.TVArchiveAdapter.AnonymousClass4.AnonymousClass1 */

                    public void run() {
                        if (TextUtils.isEmpty(str)) {
                            List unused = TVArchiveAdapter.this.dataSet = TVArchiveAdapter.this.completeList;
                        } else if (!TVArchiveAdapter.this.filterList.isEmpty() || TVArchiveAdapter.this.filterList.isEmpty()) {
                            List unused2 = TVArchiveAdapter.this.dataSet = TVArchiveAdapter.this.filterList;
                        }
                        if (TVArchiveAdapter.this.dataSet.size() == 0) {
                            textView.setVisibility(0);
                        }
                        TVArchiveAdapter.this.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
