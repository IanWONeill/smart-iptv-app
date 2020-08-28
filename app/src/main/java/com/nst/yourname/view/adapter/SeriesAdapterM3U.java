package com.nst.yourname.view.adapter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.RequiresApi;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.model.FavouriteM3UModel;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.ExternalPlayerDataBase;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.RecentWatchDBHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.model.pojo.ExternalPlayerModelClass;
import com.nst.yourname.view.activity.PlayExternalPlayerActivity;
import com.nst.yourname.view.activity.SeriesActivityNewFlowSubCategoriesM3U;
import com.nst.yourname.view.activity.SeriesDetailM3UActivity;
import com.nst.yourname.view.ijkplayer.widget.media.FileMediaDataSource;
import com.nst.yourname.view.ijkplayer.widget.media.InfoHudViewHolder;
import com.nst.yourname.view.ijkplayer.widget.media.MeasureHelper;
import com.nst.yourname.view.ijkplayer.widget.media.MediaPlayerCompat;
import com.squareup.picasso.Picasso;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("all")
public class SeriesAdapterM3U extends RecyclerView.Adapter<SeriesAdapterM3U.MyViewHolder> {
    private static String uk;
    private static String una;
    private int AdapterPosition = 0;
    private String catIDforRecent = "";
    public List<LiveStreamsDBModel> completeList;
    public Context context;
    public List<LiveStreamsDBModel> dataSet;
    DatabaseHandler database;
    private DateFormat df;
    private Date dt;
    private SharedPreferences.Editor editor;
    public ArrayList<ExternalPlayerModelClass> externalPlayerList;
    public List<LiveStreamsDBModel> filterList;
    private Boolean focused = false;
    private SimpleDateFormat fr;
    public boolean isRecentWatch = true;
    LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesSharedPref;
    private SharedPreferences pref;
    RecentWatchDBHandler recentWatchDBHandler;
    public Boolean rq = true;
    private Handler seekBarHandler;
    public String seriesName;
    private SharedPreferences settingsPrefs;
    public int text_last_size;
    public int text_size;
    private String ukd;
    private String unad;
    public SeriesActivityNewFlowSubCategoriesM3U vodActivityNewFlowSubCategoriesObj;

    /*public class MyViewHolder_ViewBinding implements Unbinder {
        private MyViewHolder target;

        @UiThread
        public MyViewHolder_ViewBinding(MyViewHolder myViewHolder, View view) {
            this.target = myViewHolder;
            myViewHolder.episodeName = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_movie_name, "field 'episodeName'", TextView.class);
            myViewHolder.Movie = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_movie, "field 'Movie'", RelativeLayout.class);
            myViewHolder.MovieImage = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_movie_image, "field 'MovieImage'", ImageView.class);
            myViewHolder.cardView = (CardView) Utils.findRequiredViewAsType(view, R.id.card_view, "field 'cardView'", CardView.class);
            myViewHolder.tvStreamOptions = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_streamOptions, "field 'tvStreamOptions'", TextView.class);
            myViewHolder.ivFavourite = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_favourite, "field 'ivFavourite'", ImageView.class);
            myViewHolder.llMenu = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_menu, "field 'llMenu'", LinearLayout.class);
            myViewHolder.recentWatchIV = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_recent_watch, "field 'recentWatchIV'", ImageView.class);
        }

        @Override // butterknife.Unbinder
        @CallSuper
        public void unbind() {
            MyViewHolder myViewHolder = this.target;
            if (myViewHolder != null) {
                this.target = null;
                myViewHolder.episodeName = null;
                myViewHolder.Movie = null;
                myViewHolder.MovieImage = null;
                myViewHolder.cardView = null;
                myViewHolder.tvStreamOptions = null;
                myViewHolder.ivFavourite = null;
                myViewHolder.llMenu = null;
                myViewHolder.recentWatchIV = null;
                return;
            }
            throw new IllegalStateException("Bindings already cleared.");
        }
    }*/

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rl_movie)
        RelativeLayout Movie;
        @BindView(R.id.iv_movie_image)
        ImageView MovieImage;
        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.tv_movie_name)
        TextView episodeName;
        @BindView(R.id.iv_favourite)
        ImageView ivFavourite;
        @BindView(R.id.ll_menu)
        LinearLayout llMenu;
        @BindView(R.id.iv_recent_watch)
        ImageView recentWatchIV;
        @BindView(R.id.tv_streamOptions)
        TextView tvStreamOptions;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            setIsRecyclable(false);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v4, resolved type: java.util.List<com.nst.yourname.model.LiveStreamsDBModel>} */
    /* JADX WARNING: Multi-variable type inference failed */
    public SeriesAdapterM3U(List<LiveStreamsDBModel> list, Context context2, boolean z, SeriesActivityNewFlowSubCategoriesM3U seriesActivityNewFlowSubCategoriesM3U, String str) {
        this.dataSet = list;
        this.seriesName = str;
        this.context = context2;
        this.ukd = com.nst.yourname.miscelleneious.common.Utils.ukde(FileMediaDataSource.apn());
        this.filterList = new ArrayList();
        this.filterList.addAll(list);
        una = context2.getApplicationContext().getPackageName();
        this.completeList = list;
        uk = getApplicationName(context2);
        this.database = new DatabaseHandler(context2);
        this.unad = com.nst.yourname.miscelleneious.common.Utils.ukde(MeasureHelper.pnm());
        this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        this.recentWatchDBHandler = new RecentWatchDBHandler(context2);
        this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        this.seekBarHandler = new Handler();
        this.dt = new Date();
        this.isRecentWatch = z;
        if (df(this.fr, this.fr.format(new Date(MediaPlayerCompat.cit(context2))), this.df.format(this.dt)) >= ((long) InfoHudViewHolder.ux()) && this.ukd != null && this.unad != null && (!uk.equals(this.ukd) || !(this.ukd == null || this.unad == null || una.equals(this.unad)))) {
            this.rq = false;
        }
        this.vodActivityNewFlowSubCategoriesObj = seriesActivityNewFlowSubCategoriesM3U;
        this.liveStreamDBHandler = new LiveStreamDBHandler(context2);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v9, resolved type: java.util.List<com.nst.yourname.model.LiveStreamsDBModel>} */
    /* JADX WARNING: Multi-variable type inference failed */
    public SeriesAdapterM3U(List<LiveStreamsDBModel> list, Context context2, boolean z, String str) {
        this.dataSet = list;
        this.seriesName = str;
        this.context = context2;
        this.ukd = com.nst.yourname.miscelleneious.common.Utils.ukde(FileMediaDataSource.apn());
        una = context2.getApplicationContext().getPackageName();
        this.filterList = new ArrayList();
        uk = getApplicationName(context2);
        this.unad = com.nst.yourname.miscelleneious.common.Utils.ukde(MeasureHelper.pnm());
        this.filterList.addAll(list);
        this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        this.completeList = list;
        this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        this.database = new DatabaseHandler(context2);
        this.dt = new Date();
        this.recentWatchDBHandler = new RecentWatchDBHandler(context2);
        if (df(this.fr, this.fr.format(new Date(MediaPlayerCompat.cit(context2))), this.df.format(this.dt)) >= ((long) InfoHudViewHolder.ux()) && this.ukd != null && this.unad != null && (!uk.equals(this.ukd) || !(this.ukd == null || this.unad == null || una.equals(this.unad)))) {
            this.rq = false;
        }
        this.seekBarHandler = new Handler();
        this.isRecentWatch = z;
        this.liveStreamDBHandler = new LiveStreamDBHandler(context2);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [?, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    @Override // android.support.v7.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        this.pref = this.context.getSharedPreferences("listgridview", 0);
        AppConst.LIVE_FLAG_SERIES = this.pref.getInt("series", 0);
        if (AppConst.LIVE_FLAG_SERIES == 1) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate((int) R.layout.vod_linear_layout, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate((int) R.layout.vod_grid_layout, viewGroup, false);
        }
        return new MyViewHolder(view);
    }

    public static long df(SimpleDateFormat simpleDateFormat, String str, String str2) {
        try {
            return TimeUnit.DAYS.convert(simpleDateFormat.parse(str2).getTime() - simpleDateFormat.parse(str).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x00c7  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0109  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0119  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0136  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x013c  */
    public void onBindViewHolder(final MyViewHolder myViewHolder, @SuppressLint({"RecyclerView"}) int i) {
        int i2 = 0;
        String streamIcon;
        String url;
        MyViewHolder myViewHolder2 = myViewHolder;
        int i3 = i;
        if (this.context != null) {
            this.AdapterPosition = i3;
            Context context2 = this.context;
            Context context3 = this.context;
            this.loginPreferencesSharedPref = context2.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_PLAYER, 0);
            final String string = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SELECTED_PLAYER, "");
            if (i3 == 0 && myViewHolder2.Movie != null && !this.focused.booleanValue()) {
                this.focused = true;
                myViewHolder2.Movie.requestFocus();
            }
            if (this.dataSet.get(i3).getStreamId() != null) {
                try {
                    i2 = Integer.parseInt(this.dataSet.get(i3).getStreamId());
                } catch (NumberFormatException unused) {
                }
                final String categoryId = this.dataSet.get(i3).getCategoryId();
                final String contaiinerExtension = this.dataSet.get(i3).getContaiinerExtension();
                final String streamType = this.dataSet.get(i3).getStreamType();
                final String num = this.dataSet.get(i3).getNum();
                myViewHolder2.episodeName.setText(this.dataSet.get(i3).getName());
                streamIcon = this.dataSet.get(i3).getStreamIcon();
                final String name = this.dataSet.get(i3).getName();
                url = this.dataSet.get(i3).getUrl();
                if (!this.isRecentWatch) {
                    myViewHolder2.recentWatchIV.setVisibility(0);
                }
                myViewHolder2.MovieImage.setImageDrawable(null);
                if (streamIcon == null && !streamIcon.equals("")) {
                    Picasso.with(this.context).load(this.dataSet.get(i3).getStreamIcon()).error((int) R.drawable.noposter).placeholder((int) R.drawable.noposter).into(myViewHolder2.MovieImage);
                } else if (Build.VERSION.SDK_INT < 21) {
                    myViewHolder2.MovieImage.setImageDrawable(this.context.getResources().getDrawable(R.drawable.noposter, null));
                } else {
                    myViewHolder2.MovieImage.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.noposter));
                }
                if (this.liveStreamDBHandler.checkFavourite(url, SharepreferenceDBHandler.getUserID(this.context)).size() <= 0) {
                    myViewHolder2.ivFavourite.setVisibility(0);
                } else {
                    myViewHolder2.ivFavourite.setVisibility(4);
                }
                String finalStreamIcon13 = streamIcon;
                String finalUrl13 = url;
                int finalI13 = i2;
                myViewHolder2.cardView.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.adapter.SeriesAdapterM3U.AnonymousClass1 */

                    public void onClick(View view) {
                        SeriesAdapterM3U.this.startViewDeatilsActivity(finalI13, name, string, streamType, contaiinerExtension, categoryId, num, finalStreamIcon13, finalUrl13);
                    }
                });
                String finalStreamIcon12 = streamIcon;
                String finalUrl12 = url;
                int finalI = i2;
                myViewHolder2.MovieImage.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.adapter.SeriesAdapterM3U.AnonymousClass2 */

                    public void onClick(View view) {
                        SeriesAdapterM3U.this.startViewDeatilsActivity(finalI, name, string, streamType, contaiinerExtension, categoryId, num, finalStreamIcon12, finalUrl12);
                    }
                });
                String finalStreamIcon11 = streamIcon;
                String finalUrl11 = url;
                int finalI1 = i2;
                myViewHolder2.Movie.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.adapter.SeriesAdapterM3U.AnonymousClass3 */

                    public void onClick(View view) {
                        SeriesAdapterM3U.this.startViewDeatilsActivity(finalI1, name, string, streamType, contaiinerExtension, categoryId, num, finalStreamIcon11, finalUrl11);
                    }
                });
                myViewHolder2.Movie.setOnFocusChangeListener(new OnFocusChangeAccountListener(myViewHolder2.Movie));
                String finalUrl10 = url;
                String finalStreamIcon10 = streamIcon;
                int finalI2 = i2;
                myViewHolder2.Movie.setOnLongClickListener(new View.OnLongClickListener() {
                    /* class com.nst.yourname.view.adapter.SeriesAdapterM3U.AnonymousClass4 */

                    public boolean onLongClick(View view) {
                        SeriesAdapterM3U.this.popmenu(myViewHolder, finalI2, categoryId, name, string, streamType, contaiinerExtension, num, finalUrl10, finalStreamIcon10);
                        return true;
                    }
                });
                String finalStreamIcon9 = streamIcon;
                String finalUrl9 = url;
                int finalI3 = i2;
                myViewHolder2.MovieImage.setOnLongClickListener(new View.OnLongClickListener() {
                    /* class com.nst.yourname.view.adapter.SeriesAdapterM3U.AnonymousClass5 */

                    public boolean onLongClick(View view) {
                        SeriesAdapterM3U.this.popmenu(myViewHolder, finalI3, categoryId, name, string, streamType, contaiinerExtension, num, finalUrl9, finalStreamIcon9);
                        return true;
                    }
                });
                String finalUrl8 = url;
                String finalStreamIcon8 = streamIcon;
                int finalI4 = i2;
                myViewHolder2.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    /* class com.nst.yourname.view.adapter.SeriesAdapterM3U.AnonymousClass6 */

                    public boolean onLongClick(View view) {
                        SeriesAdapterM3U.this.popmenu(myViewHolder, finalI4, categoryId, name, string, streamType, contaiinerExtension, num, finalUrl8, finalStreamIcon8);
                        return true;
                    }
                });
                String finalUrl7 = url;
                String finalStreamIcon7 = streamIcon;
                int finalI5 = i2;
                myViewHolder2.llMenu.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.adapter.SeriesAdapterM3U.AnonymousClass7 */

                    public void onClick(View view) {
                        SeriesAdapterM3U.this.popmenu(myViewHolder, finalI5, categoryId, name, string, streamType, contaiinerExtension, num, finalUrl7, finalStreamIcon7);
                    }
                });
            }
            i2 = 0;
            final String categoryId2 = this.dataSet.get(i3).getCategoryId();
            final String contaiinerExtension2 = this.dataSet.get(i3).getContaiinerExtension();
            final String streamType2 = this.dataSet.get(i3).getStreamType();
            final String num2 = this.dataSet.get(i3).getNum();
            myViewHolder2.episodeName.setText(this.dataSet.get(i3).getName());
            streamIcon = this.dataSet.get(i3).getStreamIcon();
            final String name2 = this.dataSet.get(i3).getName();
            url = this.dataSet.get(i3).getUrl();
            if (!this.isRecentWatch) {
            }
            myViewHolder2.MovieImage.setImageDrawable(null);
            if (streamIcon == null) {
            }
            if (Build.VERSION.SDK_INT < 21) {
            }
            if (this.liveStreamDBHandler.checkFavourite(url, SharepreferenceDBHandler.getUserID(this.context)).size() <= 0) {
            }
            String finalStreamIcon6 = streamIcon;
            String finalUrl6 = url;
            int finalI6 = i2;
            myViewHolder2.cardView.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.SeriesAdapterM3U.AnonymousClass1 */

                public void onClick(View view) {
                    SeriesAdapterM3U.this.startViewDeatilsActivity(finalI6, name2, string, streamType2, contaiinerExtension2, categoryId2, num2, finalStreamIcon6, finalUrl6);
                }
            });
            String finalStreamIcon5 = streamIcon;
            String finalUrl5 = url;
            int finalI7 = i2;
            myViewHolder2.MovieImage.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.SeriesAdapterM3U.AnonymousClass2 */

                public void onClick(View view) {
                    SeriesAdapterM3U.this.startViewDeatilsActivity(finalI7, name2, string, streamType2, contaiinerExtension2, categoryId2, num2, finalStreamIcon5, finalUrl5);
                }
            });
            String finalStreamIcon4 = streamIcon;
            String finalUrl4 = url;
            int finalI8 = i2;
            myViewHolder2.Movie.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.SeriesAdapterM3U.AnonymousClass3 */

                public void onClick(View view) {
                    SeriesAdapterM3U.this.startViewDeatilsActivity(finalI8, name2, string, streamType2, contaiinerExtension2, categoryId2, num2, finalStreamIcon4, finalUrl4);
                }
            });
            myViewHolder2.Movie.setOnFocusChangeListener(new OnFocusChangeAccountListener(myViewHolder2.Movie));
            String finalStreamIcon3 = streamIcon;
            String finalUrl3 = url;
            int finalI9 = i2;
            myViewHolder2.Movie.setOnLongClickListener(new View.OnLongClickListener() {
                /* class com.nst.yourname.view.adapter.SeriesAdapterM3U.AnonymousClass4 */

                public boolean onLongClick(View view) {
                    SeriesAdapterM3U.this.popmenu(myViewHolder, finalI9, categoryId2, name2, string, streamType2, contaiinerExtension2, num2, finalUrl3, finalStreamIcon3);
                    return true;
                }
            });
            String finalStreamIcon2 = streamIcon;
            String finalUrl2 = url;
            int finalI10 = i2;
            myViewHolder2.MovieImage.setOnLongClickListener(new View.OnLongClickListener() {
                /* class com.nst.yourname.view.adapter.SeriesAdapterM3U.AnonymousClass5 */

                public boolean onLongClick(View view) {
                    SeriesAdapterM3U.this.popmenu(myViewHolder, finalI10, categoryId2, name2, string, streamType2, contaiinerExtension2, num2, finalUrl2, finalStreamIcon2);
                    return true;
                }
            });
            String finalUrl = url;
            String finalStreamIcon1 = streamIcon;
            int finalI11 = i2;
            myViewHolder2.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                /* class com.nst.yourname.view.adapter.SeriesAdapterM3U.AnonymousClass6 */

                public boolean onLongClick(View view) {
                    SeriesAdapterM3U.this.popmenu(myViewHolder, finalI11, categoryId2, name2, string, streamType2, contaiinerExtension2, num2, finalUrl, finalStreamIcon1);
                    return true;
                }
            });
            String finalUrl1 = url;
            String finalStreamIcon = streamIcon;
            int finalI12 = i2;
            myViewHolder2.llMenu.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.SeriesAdapterM3U.AnonymousClass7 */

                public void onClick(View view) {
                    SeriesAdapterM3U.this.popmenu(myViewHolder, finalI12, categoryId2, name2, string, streamType2, contaiinerExtension2, num2, finalUrl1, finalStreamIcon);
                }
            });
        }
    }

    public static String getApplicationName(Context context2) {
        return String.valueOf(context2.getApplicationInfo().loadLabel(context2.getPackageManager()));
    }

    private class OnFocusChangeAccountListener implements View.OnFocusChangeListener {
        private final View view;

        public OnFocusChangeAccountListener(View view2) {
            this.view = view2;
        }

        @SuppressLint({"ResourceType"})
        @RequiresApi(api = 21)
        public void onFocusChange(View view2, boolean z) {
            float f = 1.0f;
            if (z) {
                if (z) {
                    f = 1.1f;
                }
                performScaleXAnimation(f);
                Log.e("id is", "" + this.view.getTag());
            } else if (!z) {
                if (z) {
                    f = 1.09f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                performAlphaAnimation(z);
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

    public void startViewDeatilsActivity(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        if (this.context != null) {
            Intent intent = new Intent(this.context, SeriesDetailM3UActivity.class);
            intent.putExtra(AppConst.SERIES_NUM, str6);
            intent.putExtra("episode_name", str);
            intent.putExtra(AppConst.SERIES_NAME, this.seriesName);
            intent.putExtra("series_icon", str7);
            intent.putExtra("episode_url", str8);
            intent.putExtra(AppConst.SERIES_CATEGORY_ID, str5);
            this.context.startActivity(intent);
            return;
        }
        Log.e("Null hai context", ">>>>>>>>>>>True its Null");
    }

    public void popmenu(final MyViewHolder myViewHolder, final int i, final String str, final String str2, final String str3, final String str4, final String str5, final String str6, final String str7, final String str8) {
        if (this.context != null) {
            PopupMenu popupMenu = new PopupMenu(this.context, myViewHolder.tvStreamOptions);
            popupMenu.inflate(R.menu.menu_card_episodes_m3u);
            ArrayList<FavouriteM3UModel> checkFavourite = this.liveStreamDBHandler.checkFavourite(str7, SharepreferenceDBHandler.getUserID(this.context));
            if (checkFavourite == null || checkFavourite.size() <= 0) {
                popupMenu.getMenu().getItem(3).setVisible(true);
            } else {
                popupMenu.getMenu().getItem(4).setVisible(true);
            }
            if (!this.isRecentWatch) {
                popupMenu.getMenu().getItem(5).setVisible(true);
            } else {
                popupMenu.getMenu().getItem(5).setVisible(false);
            }
            try {
                if (this.rq.booleanValue()) {
                    this.externalPlayerList = new ArrayList<>();
                    this.externalPlayerList = new ExternalPlayerDataBase(this.context).getExternalPlayer();
                    if (this.externalPlayerList != null && this.externalPlayerList.size() > 0) {
                        for (int i2 = 0; i2 < this.externalPlayerList.size(); i2++) {
                            popupMenu.getMenu().add(0, i2, i2, this.externalPlayerList.get(i2).getAppname());
                        }
                    }
                }
            } catch (Exception unused) {
            }
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                /* class com.nst.yourname.view.adapter.SeriesAdapterM3U.AnonymousClass8 */

                @Override // android.support.v7.widget.PopupMenu.OnMenuItemClickListener
                public boolean onMenuItemClick(MenuItem menuItem) {
                    try {
                        if (SeriesAdapterM3U.this.rq.booleanValue() && SeriesAdapterM3U.this.externalPlayerList != null && SeriesAdapterM3U.this.externalPlayerList.size() > 0) {
                            int i = 0;
                            while (true) {
                                if (i >= SeriesAdapterM3U.this.externalPlayerList.size()) {
                                    break;
                                } else if (menuItem.getItemId() == i) {
                                    Intent intent = new Intent(SeriesAdapterM3U.this.context, PlayExternalPlayerActivity.class);
                                    intent.putExtra("url", str7);
                                    intent.putExtra(AppConst.APP_NAME, ((ExternalPlayerModelClass) SeriesAdapterM3U.this.externalPlayerList.get(i)).getAppname());
                                    intent.putExtra(AppConst.PACKAGE_NAME, ((ExternalPlayerModelClass) SeriesAdapterM3U.this.externalPlayerList.get(i)).getPackagename());
                                    SeriesAdapterM3U.this.context.startActivity(intent);
                                    break;
                                } else {
                                    i++;
                                }
                            }
                        }
                    } catch (Exception unused) {
                    }
                    switch (menuItem.getItemId()) {
                        case R.id.menu_series_details:
                            startViewDeatilsActivity(i, str2, str3, str4, str5, str, str6, str8, str7);
                            break;
                        case R.id.nav_add_to_fav:
                            addToFavourite();
                            break;
                        case R.id.nav_delete_from_recentwatch:
                            if ((SeriesAdapterM3U.this.vodActivityNewFlowSubCategoriesObj != null || !SeriesAdapterM3U.this.isRecentWatch) && SeriesAdapterM3U.this.vodActivityNewFlowSubCategoriesObj != null) {
                                SeriesAdapterM3U.this.vodActivityNewFlowSubCategoriesObj.deleteCurrentItem(i, str2, SeriesAdapterM3U.this.context, SeriesAdapterM3U.this.recentWatchDBHandler);
                                break;
                            }
                        case R.id.nav_play:
                            if (SeriesAdapterM3U.this.rq.booleanValue()) {
                                playMovie();
                                break;
                            }
                            break;
                        case R.id.nav_remove_from_fav:
                            removeFromFavourite();
                            break;
                    }
                    return false;
                }

                private void startViewDeatilsActivity(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
                    if (SeriesAdapterM3U.this.context != null) {
                        Intent intent = new Intent(SeriesAdapterM3U.this.context, SeriesDetailM3UActivity.class);
                        intent.putExtra(AppConst.SERIES_NUM, str6);
                        intent.putExtra("episode_name", str);
                        intent.putExtra(AppConst.SERIES_NAME, SeriesAdapterM3U.this.seriesName);
                        intent.putExtra("series_icon", str7);
                        intent.putExtra("episode_url", str8);
                        intent.putExtra(AppConst.SERIES_CATEGORY_ID, str5);
                        SeriesAdapterM3U.this.context.startActivity(intent);
                        return;
                    }
                    Log.e("Null hai context", ">>>>>>>>>>>True its Null");
                }

                private void playMovie() {
                    com.nst.yourname.miscelleneious.common.Utils.playWithPlayerVOD(SeriesAdapterM3U.this.context, str3, i, str4, str5, str6, str2, str7);
                }

                private void addToFavourite() {
                    FavouriteM3UModel favouriteM3UModel = new FavouriteM3UModel();
                    favouriteM3UModel.setUrl(str7);
                    favouriteM3UModel.setUserID(SharepreferenceDBHandler.getUserID(SeriesAdapterM3U.this.context));
                    favouriteM3UModel.setName(str2);
                    favouriteM3UModel.setCategoryID(str);
                    SeriesAdapterM3U.this.liveStreamDBHandler.addToFavourite(favouriteM3UModel);
                    myViewHolder.ivFavourite.setVisibility(0);
                }

                private void removeFromFavourite() {
                    SeriesAdapterM3U.this.liveStreamDBHandler.deleteFavourite(str7, SharepreferenceDBHandler.getUserID(SeriesAdapterM3U.this.context));
                    myViewHolder.ivFavourite.setVisibility(4);
                }
            });
            popupMenu.show();
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.dataSet.size();
    }

    public void filter(final String str, final TextView textView) {
        new Thread(new Runnable() {
            /* class com.nst.yourname.view.adapter.SeriesAdapterM3U.AnonymousClass9 */

            public void run() {
                List unused = SeriesAdapterM3U.this.filterList = new ArrayList();
                SeriesAdapterM3U.this.text_size = str.length();
                if (SeriesAdapterM3U.this.filterList != null) {
                    SeriesAdapterM3U.this.filterList.clear();
                }
                if (TextUtils.isEmpty(str)) {
                    SeriesAdapterM3U.this.filterList.addAll(SeriesAdapterM3U.this.completeList);
                } else {
                    if (SeriesAdapterM3U.this.dataSet.size() == 0 || SeriesAdapterM3U.this.text_last_size > SeriesAdapterM3U.this.text_size) {
                        List unused2 = SeriesAdapterM3U.this.dataSet = SeriesAdapterM3U.this.completeList;
                    }
                    for (LiveStreamsDBModel liveStreamsDBModel : SeriesAdapterM3U.this.dataSet) {
                        if (liveStreamsDBModel.getName() != null && liveStreamsDBModel.getName().toLowerCase().contains(str.toLowerCase())) {
                            SeriesAdapterM3U.this.filterList.add(liveStreamsDBModel);
                        }
                    }
                }
                ((Activity) SeriesAdapterM3U.this.context).runOnUiThread(new Runnable() {
                    /* class com.nst.yourname.view.adapter.SeriesAdapterM3U.AnonymousClass9.AnonymousClass1 */

                    public void run() {
                        if (TextUtils.isEmpty(str)) {
                            List unused = SeriesAdapterM3U.this.dataSet = SeriesAdapterM3U.this.completeList;
                        } else if (!SeriesAdapterM3U.this.filterList.isEmpty() || SeriesAdapterM3U.this.filterList.isEmpty()) {
                            List unused2 = SeriesAdapterM3U.this.dataSet = SeriesAdapterM3U.this.filterList;
                        }
                        if (SeriesAdapterM3U.this.dataSet.size() == 0) {
                            textView.setVisibility(0);
                        }
                        SeriesAdapterM3U.this.text_last_size = SeriesAdapterM3U.this.text_size;
                        SeriesAdapterM3U.this.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
