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
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.nst.yourname.view.activity.ViewDetailsActivity;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class LiveStreamsAdapter extends RecyclerView.Adapter<LiveStreamsAdapter.MyViewHolder> {
    private static SharedPreferences loginPreferencesSharedPref_time_format;
    public List<LiveStreamsDBModel> completeList;
    public Context context;
    public List<LiveStreamsDBModel> dataSet;
    public DatabaseHandler database;
    private SharedPreferences.Editor editor;
    public List<LiveStreamsDBModel> filterList = new ArrayList();
    private LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesSharedPref;
    MyViewHolder myViewHolder;
    private SharedPreferences pref;
    private SimpleDateFormat programTimeFormat;
    public int text_last_size;
    public int text_size;

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
    public LiveStreamsAdapter(List<LiveStreamsDBModel> list, Context context2) {
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
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        this.pref = this.context.getSharedPreferences("listgridview", 0);
        AppConst.LIVE_FLAG = this.pref.getInt(AppConst.LIVE_STREAM, 0);
        if (AppConst.LIVE_FLAG == 1) {
            this.myViewHolder = new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate((int) R.layout.live_streams_grid_layout, viewGroup, false));
            return this.myViewHolder;
        }
        this.myViewHolder = new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate((int) R.layout.live_streams_linear_layout, viewGroup, false));
        return this.myViewHolder;
    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder2, int i) {
        int i2;
        ArrayList<XMLTVProgrammePojo> epg;
        int percentageLeft;
        MyViewHolder myViewHolder3 = myViewHolder2;
        int i3 = i;
        if (this.context != null) {
            this.loginPreferencesSharedPref = this.context.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_PLAYER, 0);
            this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SELECTED_PLAYER, "");
            try {
                i2 = Integer.parseInt(this.dataSet.get(i3).getStreamId().trim());
            } catch (NumberFormatException unused) {
                i2 = -1;
            }
            final String categoryId = this.dataSet.get(i3).getCategoryId();
            this.dataSet.get(i3).getStreamType();
            String epgChannelId = this.dataSet.get(i3).getEpgChannelId();
            final String num = this.dataSet.get(i3).getNum();
            myViewHolder3.tvTime.setText("");
            myViewHolder3.progressBar.setVisibility(8);
            myViewHolder3.tvCurrentLive.setText("");
            if (epgChannelId != null && !epgChannelId.equals("") && this.liveStreamDBHandler != null && (epg = this.liveStreamDBHandler.getEPG(epgChannelId)) != null) {
                int i4 = 0;
                while (true) {
                    if (i4 >= epg.size()) {
                        break;
                    }
                    String start = epg.get(i4).getStart();
                    String stop = epg.get(i4).getStop();
                    String title = epg.get(i4).getTitle();
                    epg.get(i4).getDesc();
                    Long valueOf = Long.valueOf(com.nst.yourname.miscelleneious.common.Utils.epgTimeConverter(start));
                    Long valueOf2 = Long.valueOf(com.nst.yourname.miscelleneious.common.Utils.epgTimeConverter(stop));
                    if (!com.nst.yourname.miscelleneious.common.Utils.isEventVisible(valueOf.longValue(), valueOf2.longValue(), this.context) || (percentageLeft = com.nst.yourname.miscelleneious.common.Utils.getPercentageLeft(valueOf.longValue(), valueOf2.longValue(), this.context)) == 0) {
                        i4++;
                    } else {
                        int i5 = 100 - percentageLeft;
                        if (i5 == 0 || title.equals("")) {
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
                            myViewHolder3.progressBar.setProgress(i5);
                            myViewHolder3.tvCurrentLive.setVisibility(0);
                            myViewHolder3.tvCurrentLive.setText(title);
                        }
                    }
                }
            }
            this.dataSet.get(i3).getNum();
            final String name = this.dataSet.get(i3).getName();
            myViewHolder3.tvChannelName.setText(this.dataSet.get(i3).getName());
            String streamIcon = this.dataSet.get(i3).getStreamIcon();
            this.dataSet.get(i3).getEpgChannelId();
            myViewHolder3.ivChannelLogo.setImageDrawable(null);
            if (streamIcon != null && !streamIcon.equals("")) {
                Picasso.with(this.context).load(streamIcon).placeholder((int) R.drawable.logo_placeholder_white).into(myViewHolder3.ivChannelLogo);
            } else if (Build.VERSION.SDK_INT >= 21) {
                myViewHolder3.ivChannelLogo.setImageDrawable(this.context.getResources().getDrawable(R.drawable.logo_placeholder_white, null));
            } else {
                myViewHolder3.ivChannelLogo.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.logo_placeholder_white));
            }
            myViewHolder3.cardView.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.LiveStreamsAdapter.AnonymousClass1 */

                public void onClick(View view) {
                }
            });
            myViewHolder3.rlMovieImage.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.LiveStreamsAdapter.AnonymousClass2 */

                public void onClick(View view) {
                }
            });
            myViewHolder3.rlStreamsLayout.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.LiveStreamsAdapter.AnonymousClass3 */

                public void onClick(View view) {
                }
            });
            ArrayList<FavouriteDBModel> checkFavourite = this.database.checkFavourite(i2, categoryId, "live", SharepreferenceDBHandler.getUserID(this.context));
            if (checkFavourite == null || checkFavourite.size() <= 0) {
                myViewHolder3.ivFavourite.setVisibility(4);
            } else {
                myViewHolder3.ivFavourite.setVisibility(0);
            }
            int finalI = i2;
            myViewHolder3.rlStreamsLayout.setOnLongClickListener(new View.OnLongClickListener() {
                /* class com.nst.yourname.view.adapter.LiveStreamsAdapter.AnonymousClass4 */

                public boolean onLongClick(View view) {
                    LiveStreamsAdapter.this.popmenu(myViewHolder2, finalI, categoryId, name, num);
                    return true;
                }
            });
            int finalI1 = i2;
            myViewHolder3.rlMovieImage.setOnLongClickListener(new View.OnLongClickListener() {
                /* class com.nst.yourname.view.adapter.LiveStreamsAdapter.AnonymousClass5 */

                public boolean onLongClick(View view) {
                    LiveStreamsAdapter.this.popmenu(myViewHolder2, finalI1, categoryId, name, num);
                    return true;
                }
            });
            int finalI2 = i2;
            myViewHolder3.llMenu.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.LiveStreamsAdapter.AnonymousClass6 */

                public void onClick(View view) {
                    LiveStreamsAdapter.this.popmenu(myViewHolder2, finalI2, categoryId, name, num);
                }
            });
        }
    }

    public void popmenu(final MyViewHolder myViewHolder2, final int i, final String str, final String str2, final String str3) {
        PopupMenu popupMenu = new PopupMenu(this.context, myViewHolder2.tvStreamOptions);
        popupMenu.inflate(R.menu.menu_card_live_streams);
        if (this.database.checkFavourite(i, str, "live", SharepreferenceDBHandler.getUserID(this.context)).size() > 0) {
            popupMenu.getMenu().getItem(2).setVisible(true);
        } else {
            popupMenu.getMenu().getItem(1).setVisible(true);
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            /* class com.nst.yourname.view.adapter.LiveStreamsAdapter.AnonymousClass7 */

            @Override // android.support.v7.widget.PopupMenu.OnMenuItemClickListener
            public boolean onMenuItemClick(MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.nav_add_to_fav) {
                    addToFavourite();
                    return false;
                } else if (itemId == R.id.nav_play) {
                    playChannel();
                    return false;
                } else if (itemId != R.id.nav_remove_from_fav) {
                    return false;
                } else {
                    removeFromFavourite();
                    return false;
                }
            }

            private void startViewDeatilsActivity() {
                if (LiveStreamsAdapter.this.context != null) {
                    LiveStreamsAdapter.this.context.startActivity(new Intent(LiveStreamsAdapter.this.context, ViewDetailsActivity.class));
                }
            }

            private void playChannel() {
                myViewHolder2.cardView.performClick();
            }

            private void addToFavourite() {
                FavouriteDBModel favouriteDBModel = new FavouriteDBModel();
                favouriteDBModel.setCategoryID(str);
                favouriteDBModel.setStreamID(i);
                favouriteDBModel.setName(str2);
                favouriteDBModel.setNum(str3);
                favouriteDBModel.setUserID(SharepreferenceDBHandler.getUserID(LiveStreamsAdapter.this.context));
                LiveStreamsAdapter.this.database.addToFavourite(favouriteDBModel, "live");
                myViewHolder2.ivFavourite.setVisibility(0);
            }

            private void removeFromFavourite() {
                LiveStreamsAdapter.this.database.deleteFavourite(i, str, "live", str2, SharepreferenceDBHandler.getUserID(LiveStreamsAdapter.this.context));
                myViewHolder2.ivFavourite.setVisibility(4);
            }
        });
        popupMenu.show();
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.dataSet.size();
    }

    public void filter(final String str, final TextView textView) {
        new Thread(new Runnable() {
            /* class com.nst.yourname.view.adapter.LiveStreamsAdapter.AnonymousClass8 */

            public void run() {
                List unused = LiveStreamsAdapter.this.filterList = new ArrayList();
                LiveStreamsAdapter.this.text_size = str.length();
                if (LiveStreamsAdapter.this.filterList != null) {
                    LiveStreamsAdapter.this.filterList.clear();
                }
                if (TextUtils.isEmpty(str)) {
                    LiveStreamsAdapter.this.filterList.addAll(LiveStreamsAdapter.this.completeList);
                } else {
                    if ((LiveStreamsAdapter.this.dataSet != null && LiveStreamsAdapter.this.dataSet.size() == 0) || LiveStreamsAdapter.this.text_last_size > LiveStreamsAdapter.this.text_size) {
                        List unused2 = LiveStreamsAdapter.this.dataSet = LiveStreamsAdapter.this.completeList;
                    }
                    if (LiveStreamsAdapter.this.dataSet != null) {
                        for (LiveStreamsDBModel liveStreamsDBModel : LiveStreamsAdapter.this.dataSet) {
                            if (liveStreamsDBModel.getName() != null && liveStreamsDBModel.getName().toLowerCase().contains(str.toLowerCase())) {
                                LiveStreamsAdapter.this.filterList.add(liveStreamsDBModel);
                            }
                        }
                    }
                }
                ((Activity) LiveStreamsAdapter.this.context).runOnUiThread(new Runnable() {
                    /* class com.nst.yourname.view.adapter.LiveStreamsAdapter.AnonymousClass8.AnonymousClass1 */

                    public void run() {
                        if (TextUtils.isEmpty(str)) {
                            List unused = LiveStreamsAdapter.this.dataSet = LiveStreamsAdapter.this.completeList;
                        } else if (!LiveStreamsAdapter.this.filterList.isEmpty() || LiveStreamsAdapter.this.filterList.isEmpty()) {
                            List unused2 = LiveStreamsAdapter.this.dataSet = LiveStreamsAdapter.this.filterList;
                        }
                        if (LiveStreamsAdapter.this.dataSet != null && LiveStreamsAdapter.this.dataSet.size() == 0) {
                            textView.setVisibility(0);
                        }
                        LiveStreamsAdapter.this.text_last_size = LiveStreamsAdapter.this.text_size;
                        LiveStreamsAdapter.this.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
