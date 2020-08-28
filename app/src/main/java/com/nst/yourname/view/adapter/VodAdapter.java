package com.nst.yourname.view.adapter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.common.images.WebImage;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.chromecastfeature.ChromeCastUtilClass;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.model.FavouriteDBModel;
import com.nst.yourname.model.FavouriteM3UModel;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.ExternalPlayerDataBase;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.RecentWatchDBHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.model.pojo.ExternalPlayerModelClass;
import com.nst.yourname.view.activity.PlayExternalPlayerActivity;
import com.nst.yourname.view.activity.ViewDetailsActivity;
import com.nst.yourname.view.activity.ViewDetailsTMDBActivity;
import com.nst.yourname.view.activity.VodActivityNewFlowSubCategories;
import com.nst.yourname.view.adapter.OptionExternalPlayerAdapter;
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

@SuppressWarnings("ALL")
public class VodAdapter extends RecyclerView.Adapter<VodAdapter.MyViewHolder> implements OptionExternalPlayerAdapter.ItemClickListener {
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
    private LiveStreamsDBModel liveStreamsDBModelMenu;
    private SharedPreferences loginPreferencesSharedPref;
    public CastSession mCastSession;
    private SharedPreferences pref;
    RecentWatchDBHandler recentWatchDBHandler;
    public Boolean rq = true;
    private Handler seekBarHandler;
    private SharedPreferences settingsPrefs;
    public int text_last_size;
    public int text_size;
    private String ukd;
    private String unad;
    public VodActivityNewFlowSubCategories vodActivityNewFlowSubCategoriesObj;

    /*public class MyViewHolder_ViewBinding implements Unbinder {
        private MyViewHolder target;

        @UiThread
        public MyViewHolder_ViewBinding(MyViewHolder myViewHolder, View view) {
            this.target = myViewHolder;
            myViewHolder.MovieName = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_movie_name, "field 'MovieName'", TextView.class);
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
                myViewHolder.MovieName = null;
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
    }
*/
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rl_movie)
        RelativeLayout Movie;
        @BindView(R.id.iv_movie_image)
        ImageView MovieImage;
        @BindView(R.id.tv_movie_name)
        TextView MovieName;
        @BindView(R.id.card_view)
        CardView cardView;
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

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: java.util.List<com.nst.yourname.model.LiveStreamsDBModel>} */
    /* JADX WARNING: Multi-variable type inference failed */
    public VodAdapter(List<LiveStreamsDBModel> list, Context context2, boolean z, VodActivityNewFlowSubCategories vodActivityNewFlowSubCategories) {
        this.dataSet = list;
        this.context = context2;
        this.ukd = com.nst.yourname.miscelleneious.common.Utils.ukde(FileMediaDataSource.apn());
        this.filterList = new ArrayList();
        this.filterList.addAll(list);
        una = context2.getApplicationContext().getPackageName();
        this.completeList = list;
        uk = getApplicationName(context2);
        this.database = new DatabaseHandler(context2);
        this.liveStreamDBHandler = new LiveStreamDBHandler(context2);
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
        this.vodActivityNewFlowSubCategoriesObj = vodActivityNewFlowSubCategories;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v9, resolved type: java.util.List<com.nst.yourname.model.LiveStreamsDBModel>} */
    /* JADX WARNING: Multi-variable type inference failed */
    public VodAdapter(List<LiveStreamsDBModel> list, Context context2, boolean z) {
        this.dataSet = list;
        this.dataSet = list;
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
        this.liveStreamDBHandler = new LiveStreamDBHandler(context2);
        this.dt = new Date();
        this.recentWatchDBHandler = new RecentWatchDBHandler(context2);
        if (df(this.fr, this.fr.format(new Date(MediaPlayerCompat.cit(context2))), this.df.format(this.dt)) >= ((long) InfoHudViewHolder.ux()) && this.ukd != null && this.unad != null && (!uk.equals(this.ukd) || !(this.ukd == null || this.unad == null || una.equals(this.unad)))) {
            this.rq = false;
        }
        this.seekBarHandler = new Handler();
        this.isRecentWatch = z;
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
        this.editor = this.pref.edit();
        AppConst.LIVE_FLAG_VOD = this.pref.getInt(AppConst.VOD, 0);
        if (AppConst.LIVE_FLAG_VOD == 1) {
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

    /* JADX WARNING: Removed duplicated region for block: B:18:0x00c5  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0107  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0117  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0131  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x014f  */
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, @SuppressLint({"RecyclerView"}) int i) {
        int i2 = 0;
        String streamIcon;
        MyViewHolder myViewHolder2 = myViewHolder;
        int i3 = i;
        if (this.context != null) {
            this.AdapterPosition = i3;
            this.loginPreferencesSharedPref = this.context.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_PLAYER, 0);
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
                myViewHolder2.MovieName.setText(this.dataSet.get(i3).getName());
                streamIcon = this.dataSet.get(i3).getStreamIcon();
                final String name = this.dataSet.get(i3).getName();
                final String url = this.dataSet.get(i3).getUrl();
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
                if (!SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                    if (this.liveStreamDBHandler.checkFavourite(url, SharepreferenceDBHandler.getUserID(this.context)).size() > 0) {
                        myViewHolder2.ivFavourite.setVisibility(0);
                    } else {
                        myViewHolder2.ivFavourite.setVisibility(4);
                    }
                } else if (this.database.checkFavourite(i2, categoryId, AppConst.VOD, SharepreferenceDBHandler.getUserID(this.context)).size() > 0) {
                    myViewHolder2.ivFavourite.setVisibility(0);
                } else {
                    myViewHolder2.ivFavourite.setVisibility(4);
                }
                int finalI = i2;
                String finalStreamIcon13 = streamIcon;
                myViewHolder2.cardView.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.adapter.VodAdapter.AnonymousClass1 */

                    public void onClick(View view) {
                        VodAdapter.this.startViewDeatilsActivity(finalI, name, string, streamType, contaiinerExtension, categoryId, num, finalStreamIcon13, url);
                    }
                });
                int finalI1 = i2;
                String finalStreamIcon12 = streamIcon;
                myViewHolder2.MovieImage.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.adapter.VodAdapter.AnonymousClass2 */

                    public void onClick(View view) {
                        VodAdapter.this.startViewDeatilsActivity(finalI1, name, string, streamType, contaiinerExtension, categoryId, num, finalStreamIcon12, url);
                    }
                });
                int finalI2 = i2;
                String finalStreamIcon11 = streamIcon;
                myViewHolder2.Movie.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.adapter.VodAdapter.AnonymousClass3 */

                    public void onClick(View view) {
                        VodAdapter.this.startViewDeatilsActivity(finalI2, name, string, streamType, contaiinerExtension, categoryId, num, finalStreamIcon11, url);
                    }
                });
                myViewHolder2.Movie.setOnFocusChangeListener(new OnFocusChangeAccountListener(myViewHolder2.Movie));
                int finalI3 = i2;
                String finalStreamIcon10 = streamIcon;
                myViewHolder2.Movie.setOnLongClickListener(new View.OnLongClickListener() {
                    /* class com.nst.yourname.view.adapter.VodAdapter.AnonymousClass4 */

                    public boolean onLongClick(View view) {
                        if (SharepreferenceDBHandler.getCurrentAPPType(VodAdapter.this.context).equals(AppConst.TYPE_M3U)) {
                            VodAdapter.this.popmenuM3U(myViewHolder, finalI3, categoryId, name, string, streamType, contaiinerExtension, num, finalStreamIcon10, url);
                            return true;
                        }
                        VodAdapter.this.popmenu(myViewHolder, finalI3, categoryId, name, string, streamType, contaiinerExtension, num, finalStreamIcon10);
                        return true;
                    }
                });
                int finalI4 = i2;
                String finalStreamIcon9 = streamIcon;
                myViewHolder2.MovieImage.setOnLongClickListener(new View.OnLongClickListener() {
                    /* class com.nst.yourname.view.adapter.VodAdapter.AnonymousClass5 */
                    final int val$finalStreamId4;

                    {
                        this.val$finalStreamId4 = finalI4;
                    }

                    public boolean onLongClick(View view) {
                        if (SharepreferenceDBHandler.getCurrentAPPType(VodAdapter.this.context).equals(AppConst.TYPE_M3U)) {
                            VodAdapter.this.popmenuM3U(myViewHolder, this.val$finalStreamId4, categoryId, name, string, streamType, contaiinerExtension, num, finalStreamIcon9, url);
                            return true;
                        }
                        VodAdapter.this.popmenu(myViewHolder, finalI4, categoryId, name, string, streamType, contaiinerExtension, num, finalStreamIcon9);
                        return true;
                    }
                });
                int finalI5 = i2;
                String finalStreamIcon8 = streamIcon;
                myViewHolder2.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    /* class com.nst.yourname.view.adapter.VodAdapter.AnonymousClass6 */
                    final int val$finalStreamId4;

                    {
                        this.val$finalStreamId4 = finalI5;
                    }

                    public boolean onLongClick(View view) {
                        if (SharepreferenceDBHandler.getCurrentAPPType(VodAdapter.this.context).equals(AppConst.TYPE_M3U)) {
                            VodAdapter.this.popmenuM3U(myViewHolder, this.val$finalStreamId4, categoryId, name, string, streamType, contaiinerExtension, num, finalStreamIcon8, url);
                            return true;
                        }
                        VodAdapter.this.popmenu(myViewHolder, finalI5, categoryId, name, string, streamType, contaiinerExtension, num, finalStreamIcon8);
                        return true;
                    }
                });
                int finalI6 = i2;
                String finalStreamIcon7 = streamIcon;
                myViewHolder2.llMenu.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.adapter.VodAdapter.AnonymousClass7 */

                    public void onClick(View view) {
                        VodAdapter.this.popmenu(myViewHolder, finalI6, categoryId, name, string, streamType, contaiinerExtension, num, finalStreamIcon7);
                    }
                });
            }
            i2 = 0;
            final String categoryId2 = this.dataSet.get(i3).getCategoryId();
            final String contaiinerExtension2 = this.dataSet.get(i3).getContaiinerExtension();
            final String streamType2 = this.dataSet.get(i3).getStreamType();
            final String num2 = this.dataSet.get(i3).getNum();
            myViewHolder2.MovieName.setText(this.dataSet.get(i3).getName());
            streamIcon = this.dataSet.get(i3).getStreamIcon();
            final String name2 = this.dataSet.get(i3).getName();
            final String url2 = this.dataSet.get(i3).getUrl();
            if (!this.isRecentWatch) {
            }
            myViewHolder2.MovieImage.setImageDrawable(null);
            if (streamIcon == null) {
            }
            if (Build.VERSION.SDK_INT < 21) {
            }
            if (!SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            }
            int finalI7 = i2;
            String finalStreamIcon6 = streamIcon;
            myViewHolder2.cardView.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.VodAdapter.AnonymousClass1 */

                public void onClick(View view) {
                    VodAdapter.this.startViewDeatilsActivity(finalI7, name2, string, streamType2, contaiinerExtension2, categoryId2, num2, finalStreamIcon6, url2);
                }
            });
            int finalI8 = i2;
            String finalStreamIcon5 = streamIcon;
            myViewHolder2.MovieImage.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.VodAdapter.AnonymousClass2 */

                public void onClick(View view) {
                    VodAdapter.this.startViewDeatilsActivity(finalI8, name2, string, streamType2, contaiinerExtension2, categoryId2, num2, finalStreamIcon5, url2);
                }
            });
            int finalI9 = i2;
            String finalStreamIcon4 = streamIcon;
            myViewHolder2.Movie.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.VodAdapter.AnonymousClass3 */

                public void onClick(View view) {
                    VodAdapter.this.startViewDeatilsActivity(finalI9, name2, string, streamType2, contaiinerExtension2, categoryId2, num2, finalStreamIcon4, url2);
                }
            });
            myViewHolder2.Movie.setOnFocusChangeListener(new OnFocusChangeAccountListener(myViewHolder2.Movie));
            int finalI10 = i2;
            String finalStreamIcon3 = streamIcon;
            myViewHolder2.Movie.setOnLongClickListener(new View.OnLongClickListener() {
                /* class com.nst.yourname.view.adapter.VodAdapter.AnonymousClass4 */

                public boolean onLongClick(View view) {
                    if (SharepreferenceDBHandler.getCurrentAPPType(VodAdapter.this.context).equals(AppConst.TYPE_M3U)) {
                        VodAdapter.this.popmenuM3U(myViewHolder, finalI10, categoryId2, name2, string, streamType2, contaiinerExtension2, num2, finalStreamIcon3, url2);
                        return true;
                    }
                    VodAdapter.this.popmenu(myViewHolder, finalI10, categoryId2, name2, string, streamType2, contaiinerExtension2, num2, finalStreamIcon3);
                    return true;
                }
            });
            int finalI11 = i2;
            String finalStreamIcon2 = streamIcon;
            myViewHolder2.MovieImage.setOnLongClickListener(new View.OnLongClickListener() {
                /* class com.nst.yourname.view.adapter.VodAdapter.AnonymousClass5 */
                final int val$finalStreamId4;

                {
                    this.val$finalStreamId4 = finalI11;
                }

                public boolean onLongClick(View view) {
                    if (SharepreferenceDBHandler.getCurrentAPPType(VodAdapter.this.context).equals(AppConst.TYPE_M3U)) {
                        VodAdapter.this.popmenuM3U(myViewHolder, this.val$finalStreamId4, categoryId2, name2, string, streamType2, contaiinerExtension2, num2, finalStreamIcon2, url2);
                        return true;
                    }
                    VodAdapter.this.popmenu(myViewHolder, finalI11, categoryId2, name2, string, streamType2, contaiinerExtension2, num2, finalStreamIcon2);
                    return true;
                }
            });
            int finalI12 = i2;
            int finalI13 = i2;
            String finalStreamIcon = streamIcon;
            myViewHolder2.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                /* class com.nst.yourname.view.adapter.VodAdapter.AnonymousClass6 */
                final int val$finalStreamId4;

                {
                    this.val$finalStreamId4 = finalI12;
                }

                public boolean onLongClick(View view) {
                    if (SharepreferenceDBHandler.getCurrentAPPType(VodAdapter.this.context).equals(AppConst.TYPE_M3U)) {
                        VodAdapter.this.popmenuM3U(myViewHolder, this.val$finalStreamId4, categoryId2, name2, string, streamType2, contaiinerExtension2, num2, finalStreamIcon, url2);
                        return true;
                    }
                    VodAdapter.this.popmenu(myViewHolder, finalI12, categoryId2, name2, string, streamType2, contaiinerExtension2, num2, finalStreamIcon);
                    return true;
                }
            });
            int finalI14 = i2;
            String finalStreamIcon1 = streamIcon;
            myViewHolder2.llMenu.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.VodAdapter.AnonymousClass7 */

                public void onClick(View view) {
                    VodAdapter.this.popmenu(myViewHolder, finalI14, categoryId2, name2, string, streamType2, contaiinerExtension2, num2, finalStreamIcon1);
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
                Log.e("id is", "" + this.view.getTag());
                if (this.view.getTag() != null && this.view.getTag().equals("1")) {
                    performScaleXAnimation(f);
                    this.view.setBackgroundResource(R.drawable.back_btn_effect);
                } else if (this.view.getTag() == null || !this.view.getTag().equals("2")) {
                    performScaleXAnimation(f);
                } else {
                    performScaleXAnimation(f);
                    this.view.setBackgroundResource(R.drawable.logout_btn_effect);
                }
            } else if (!z) {
                if (z) {
                    f = 1.09f;
                }
                performAlphaAnimation(z);
                if (this.view.getTag() != null && this.view.getTag().equals("1")) {
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                    this.view.setBackgroundResource(R.drawable.black_button_dark);
                } else if (this.view.getTag() == null || !this.view.getTag().equals("2")) {
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                } else {
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                    this.view.setBackgroundResource(R.drawable.black_button_dark);
                }
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
        Intent intent;
        if (this.context != null) {
            if (!AppConst.M3U_LINE_ACTIVE.booleanValue() || !SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                intent = new Intent(this.context, ViewDetailsActivity.class);
            } else {
                intent = new Intent(this.context, ViewDetailsTMDBActivity.class);
            }
            intent.putExtra(AppConst.STREAM_ID, String.valueOf(i));
            intent.putExtra(AppConst.EVENT_TYPE_MOVIE, str);
            intent.putExtra("movie_icon", str7);
            intent.putExtra(AppConst.LOGIN_PREF_SELECTED_PLAYER, str2);
            intent.putExtra("streamType", str3);
            intent.putExtra("containerExtension", str4);
            intent.putExtra("categoryID", str5);
            intent.putExtra(AppConst.LOGIN_PREF_OPENED_VIDEO_ID_NUM, str6);
            intent.putExtra("videoURL", str8);
            this.context.startActivity(intent);
            return;
        }
        Log.e("Null hai context", ">>>>>>>>>>>True its Null");
    }

    public void popmenuM3U(final MyViewHolder myViewHolder, final int i, final String str, final String str2, final String str3, final String str4, final String str5, final String str6, final String str7, final String str8) {
        if (this.context != null) {
            PopupMenu popupMenu = new PopupMenu(this.context, myViewHolder.tvStreamOptions);
            popupMenu.inflate(R.menu.menu_card_vod);
            ArrayList<FavouriteM3UModel> checkFavourite = this.liveStreamDBHandler.checkFavourite(str8, SharepreferenceDBHandler.getUserID(this.context));
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
                this.mCastSession = CastContext.getSharedInstance(this.context).getSessionManager().getCurrentCastSession();
                if (this.mCastSession == null || !this.mCastSession.isConnected()) {
                    popupMenu.getMenu().getItem(7).setVisible(false);
                } else {
                    popupMenu.getMenu().getItem(7).setVisible(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.externalPlayerList = new ArrayList<>();
            this.externalPlayerList = new ExternalPlayerDataBase(this.context).getExternalPlayer();
            if (this.externalPlayerList != null && this.externalPlayerList.size() > 0) {
                for (int i2 = 0; i2 < this.externalPlayerList.size(); i2++) {
                    popupMenu.getMenu().add(0, i2, i2, this.externalPlayerList.get(i2).getAppname());
                }
            }
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                /* class com.nst.yourname.view.adapter.VodAdapter.AnonymousClass8 */

                @Override // android.support.v7.widget.PopupMenu.OnMenuItemClickListener
                public boolean onMenuItemClick(MenuItem menuItem) {
                    try {
                        if (VodAdapter.this.rq.booleanValue() && VodAdapter.this.externalPlayerList != null && VodAdapter.this.externalPlayerList.size() > 0) {
                            int i = 0;
                            while (true) {
                                if (i >= VodAdapter.this.externalPlayerList.size()) {
                                    break;
                                } else if (menuItem.getItemId() == i) {
                                    Intent intent = new Intent(VodAdapter.this.context, PlayExternalPlayerActivity.class);
                                    intent.putExtra("url", str8);
                                    intent.putExtra(AppConst.APP_NAME, ((ExternalPlayerModelClass) VodAdapter.this.externalPlayerList.get(i)).getAppname());
                                    intent.putExtra(AppConst.PACKAGE_NAME, ((ExternalPlayerModelClass) VodAdapter.this.externalPlayerList.get(i)).getPackagename());
                                    VodAdapter.this.context.startActivity(intent);
                                    break;
                                } else {
                                    i++;
                                }
                            }
                        }
                    } catch (Exception unused) {
                    }
                    switch (menuItem.getItemId()) {
                        case R.id.menu_view_details:
                            startViewDeatilsActivity(i, str2, str3, str4, str5, str, str6, str7, str8);
                            break;
                        case R.id.nav_add_to_fav:
                            addToFavourite();
                            break;
                        case R.id.nav_delete_from_recentwatch:
                            if ((VodAdapter.this.vodActivityNewFlowSubCategoriesObj != null || !VodAdapter.this.isRecentWatch) && VodAdapter.this.vodActivityNewFlowSubCategoriesObj != null) {
                                VodAdapter.this.vodActivityNewFlowSubCategoriesObj.deleteCurrentItem(i, str2, VodAdapter.this.context, VodAdapter.this.recentWatchDBHandler);
                                break;
                            }
                        case R.id.nav_play:
                            if (VodAdapter.this.rq.booleanValue()) {
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
                    if (VodAdapter.this.context != null) {
                        Intent intent = new Intent(VodAdapter.this.context, ViewDetailsTMDBActivity.class);
                        intent.putExtra(AppConst.STREAM_ID, String.valueOf(i));
                        intent.putExtra(AppConst.EVENT_TYPE_MOVIE, str);
                        intent.putExtra("movie_icon", str7);
                        intent.putExtra(AppConst.LOGIN_PREF_SELECTED_PLAYER, str2);
                        intent.putExtra("streamType", str3);
                        intent.putExtra("containerExtension", str4);
                        intent.putExtra("categoryID", str5);
                        intent.putExtra(AppConst.LOGIN_PREF_OPENED_VIDEO_ID_NUM, str6);
                        intent.putExtra("videoURL", str8);
                        VodAdapter.this.context.startActivity(intent);
                        return;
                    }
                    Log.e("Null hai context", ">>>>>>>>>>>True its Null");
                }

                private void playMovie() {
                    com.nst.yourname.miscelleneious.common.Utils.playWithPlayerVOD(VodAdapter.this.context, str3, i, str4, str5, str6, str2, str8);
                }

                private void addToFavourite() {
                    FavouriteM3UModel favouriteM3UModel = new FavouriteM3UModel();
                    favouriteM3UModel.setUrl(str8);
                    favouriteM3UModel.setUserID(SharepreferenceDBHandler.getUserID(VodAdapter.this.context));
                    favouriteM3UModel.setName(str2);
                    favouriteM3UModel.setCategoryID(str);
                    VodAdapter.this.liveStreamDBHandler.addToFavourite(favouriteM3UModel);
                    myViewHolder.ivFavourite.setVisibility(0);
                }

                private void removeFromFavourite() {
                    VodAdapter.this.liveStreamDBHandler.deleteFavourite(str8, SharepreferenceDBHandler.getUserID(VodAdapter.this.context));
                    myViewHolder.ivFavourite.setVisibility(4);
                }
            });
            popupMenu.show();
        }
    }

    public void popmenu(final MyViewHolder myViewHolder, final int i, final String str, final String str2, final String str3, final String str4, final String str5, final String str6, final String str7) {
        if (this.context != null) {
            PopupMenu popupMenu = new PopupMenu(this.context, myViewHolder.tvStreamOptions);
            popupMenu.inflate(R.menu.menu_card_vod);
            ArrayList<FavouriteDBModel> checkFavourite = this.database.checkFavourite(i, str, AppConst.VOD, SharepreferenceDBHandler.getUserID(this.context));
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
                this.mCastSession = CastContext.getSharedInstance(this.context).getSessionManager().getCurrentCastSession();
                if (this.mCastSession == null || !this.mCastSession.isConnected()) {
                    popupMenu.getMenu().getItem(7).setVisible(false);
                } else {
                    popupMenu.getMenu().getItem(7).setVisible(true);
                }
            } catch (Exception e) {
                Log.e("sdng", "" + e);
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
                /* class com.nst.yourname.view.adapter.VodAdapter.AnonymousClass9 */

                @Override // android.support.v7.widget.PopupMenu.OnMenuItemClickListener
                public boolean onMenuItemClick(MenuItem menuItem) {
                    try {
                        if (VodAdapter.this.rq.booleanValue() && VodAdapter.this.externalPlayerList != null && VodAdapter.this.externalPlayerList.size() > 0) {
                            int i = 0;
                            while (true) {
                                if (i >= VodAdapter.this.externalPlayerList.size()) {
                                    break;
                                } else if (menuItem.getItemId() == i) {
                                    String url = com.nst.yourname.miscelleneious.common.Utils.getUrl(VodAdapter.this.context, i, str5, AppConst.EVENT_TYPE_MOVIE);
                                    Intent intent = new Intent(VodAdapter.this.context, PlayExternalPlayerActivity.class);
                                    intent.putExtra("url", url);
                                    intent.putExtra(AppConst.APP_NAME, ((ExternalPlayerModelClass) VodAdapter.this.externalPlayerList.get(i)).getAppname());
                                    intent.putExtra(AppConst.PACKAGE_NAME, ((ExternalPlayerModelClass) VodAdapter.this.externalPlayerList.get(i)).getPackagename());
                                    VodAdapter.this.context.startActivity(intent);
                                    break;
                                } else {
                                    i++;
                                }
                            }
                        }
                    } catch (Exception unused) {
                    }
                    switch (menuItem.getItemId()) {
                        case R.id.menu_view_details:
                            startViewDeatilsActivity(i, str2, str3, str4, str5, str, str6);
                            break;
                        case R.id.nav_add_to_fav:
                            addToFavourite();
                            break;
                        case R.id.nav_delete_from_recentwatch:
                            if ((VodAdapter.this.vodActivityNewFlowSubCategoriesObj != null || !VodAdapter.this.isRecentWatch) && VodAdapter.this.vodActivityNewFlowSubCategoriesObj != null) {
                                VodAdapter.this.vodActivityNewFlowSubCategoriesObj.deleteCurrentItem(i, str2, VodAdapter.this.context, VodAdapter.this.recentWatchDBHandler);
                                break;
                            }
                        case R.id.nav_play:
                            if (VodAdapter.this.rq.booleanValue()) {
                                playMovie();
                                break;
                            }
                            break;
                        case R.id.nav_remove_from_fav:
                            removeFromFavourite();
                            break;
                        case R.id.play_with_cast:
                            if (VodAdapter.this.rq.booleanValue()) {
                                MediaMetadata mediaMetadata = new MediaMetadata(1);
                                if (str2 != null && !str2.isEmpty()) {
                                    mediaMetadata.putString(MediaMetadata.KEY_TITLE, str2);
                                }
                                if (str7 != null && !str7.isEmpty()) {
                                    mediaMetadata.addImage(new WebImage(Uri.parse(str7)));
                                }
                                ChromeCastUtilClass.loadRemoteMedia(new MediaInfo.Builder(com.nst.yourname.miscelleneious.common.Utils.getUrl(VodAdapter.this.context, i, str5, AppConst.EVENT_TYPE_MOVIE)).setStreamType(1).setContentType("videos/mp4").setMetadata(mediaMetadata).build(), VodAdapter.this.mCastSession, VodAdapter.this.context);
                                break;
                            }
                            break;
                    }
                    return false;
                }

                private void startViewDeatilsActivity(int i, String str, String str2, String str3, String str4, String str5, String str6) {
                    if (VodAdapter.this.context != null) {
                        Intent intent = new Intent(VodAdapter.this.context, ViewDetailsActivity.class);
                        intent.putExtra(AppConst.STREAM_ID, String.valueOf(i));
                        intent.putExtra(AppConst.EVENT_TYPE_MOVIE, str);
                        intent.putExtra(AppConst.LOGIN_PREF_SELECTED_PLAYER, str2);
                        intent.putExtra("streamType", str3);
                        intent.putExtra("containerExtension", str4);
                        intent.putExtra("categoryID", str5);
                        intent.putExtra(AppConst.LOGIN_PREF_OPENED_VIDEO_ID_NUM, str6);
                        VodAdapter.this.context.startActivity(intent);
                    }
                }

                private void playMovie() {
                    com.nst.yourname.miscelleneious.common.Utils.playWithPlayerVOD(VodAdapter.this.context, str3, i, str4, str5, str6, str2, "");
                }

                private void addToFavourite() {
                    FavouriteDBModel favouriteDBModel = new FavouriteDBModel();
                    favouriteDBModel.setCategoryID(str);
                    favouriteDBModel.setStreamID(i);
                    favouriteDBModel.setName(str2);
                    favouriteDBModel.setNum(str6);
                    favouriteDBModel.setUserID(SharepreferenceDBHandler.getUserID(VodAdapter.this.context));
                    VodAdapter.this.database.addToFavourite(favouriteDBModel, AppConst.VOD);
                    myViewHolder.ivFavourite.setVisibility(0);
                }

                private void removeFromFavourite() {
                    VodAdapter.this.database.deleteFavourite(i, str, AppConst.VOD, str2, SharepreferenceDBHandler.getUserID(VodAdapter.this.context));
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
            /* class com.nst.yourname.view.adapter.VodAdapter.AnonymousClass10 */

            public void run() {
                try {
                    List unused = VodAdapter.this.filterList = new ArrayList();
                    VodAdapter.this.text_size = str.length();
                    if (VodAdapter.this.filterList != null) {
                        VodAdapter.this.filterList.clear();
                    }
                    if (TextUtils.isEmpty(str)) {
                        VodAdapter.this.filterList.addAll(VodAdapter.this.completeList);
                    } else {
                        if (VodAdapter.this.dataSet.size() == 0 || VodAdapter.this.text_last_size > VodAdapter.this.text_size) {
                            List unused2 = VodAdapter.this.dataSet = VodAdapter.this.completeList;
                        }
                        for (LiveStreamsDBModel liveStreamsDBModel : VodAdapter.this.dataSet) {
                            if (liveStreamsDBModel.getName() != null && liveStreamsDBModel.getName().toLowerCase().contains(str.toLowerCase())) {
                                VodAdapter.this.filterList.add(liveStreamsDBModel);
                            }
                        }
                    }
                    ((Activity) VodAdapter.this.context).runOnUiThread(new Runnable() {
                        /* class com.nst.yourname.view.adapter.VodAdapter.AnonymousClass10.AnonymousClass1 */

                        public void run() {
                            if (TextUtils.isEmpty(str)) {
                                List unused = VodAdapter.this.dataSet = VodAdapter.this.completeList;
                            } else if (!VodAdapter.this.filterList.isEmpty() || VodAdapter.this.filterList.isEmpty()) {
                                List unused2 = VodAdapter.this.dataSet = VodAdapter.this.filterList;
                            }
                            if (VodAdapter.this.dataSet.size() == 0) {
                                textView.setVisibility(0);
                            }
                            VodAdapter.this.text_last_size = VodAdapter.this.text_size;
                            VodAdapter.this.notifyDataSetChanged();
                        }
                    });
                } catch (Exception unused3) {
                }
            }
        }).start();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.widget.PopupWindow.<init>(android.view.View, int, int, boolean):void}
     arg types: [android.view.View, int, int, int]
     candidates:
      ClspMth{android.widget.PopupWindow.<init>(android.content.Context, android.util.AttributeSet, int, int):void}
      ClspMth{android.widget.PopupWindow.<init>(android.view.View, int, int, boolean):void} */
    public void showAlertMenu(MyViewHolder myViewHolder, int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, final LiveStreamsDBModel liveStreamsDBModel) {
        new ArrayList();
        View inflate = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate((int) R.layout.menu_option_player, (ViewGroup) null);
        final PopupWindow popupWindow = new PopupWindow(inflate, 500, -2, true);
        popupWindow.showAtLocation(inflate, 17, 0, 0);
        inflate.setOnTouchListener(new View.OnTouchListener() {
            /* class com.nst.yourname.view.adapter.VodAdapter.AnonymousClass11 */

            public boolean onTouch(View view, MotionEvent motionEvent) {
                popupWindow.dismiss();
                return true;
            }
        });
        TextView textView = (TextView) inflate.findViewById(R.id.tv_play_with_cast);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.recyclerView);
        TextView textView2 = (TextView) inflate.findViewById(R.id.tv_play);
        ArrayList<ExternalPlayerModelClass> externalPlayer = new ExternalPlayerDataBase(this.context).getExternalPlayer();
        try {
            if (this.mCastSession != null) {
                this.mCastSession = CastContext.getSharedInstance(this.context).getSessionManager().getCurrentCastSession();
                if (this.mCastSession == null || !this.mCastSession.isConnected()) {
                    textView.setVisibility(8);
                } else {
                    textView.setVisibility(0);
                }
            }
        } catch (Exception e) {
            Log.e("sdng", "" + e);
        }
        if (externalPlayer == null || externalPlayer.size() <= 0) {
            recyclerView.setVisibility(8);
        } else {
            this.liveStreamsDBModelMenu = liveStreamsDBModel;
            recyclerView.setVisibility(0);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.context, 1, false));
            recyclerView.setAdapter(new OptionExternalPlayerAdapter(this.context, externalPlayer, this));
        }
        textView2.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.adapter.VodAdapter.AnonymousClass12 */

            public void onClick(View view) {
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.adapter.VodAdapter.AnonymousClass13 */

            public void onClick(View view) {
                if (VodAdapter.this.rq.booleanValue()) {
                    String name = liveStreamsDBModel.getName();
                    String streamIcon = liveStreamsDBModel.getStreamIcon();
                    int parseIntMinusOne = com.nst.yourname.miscelleneious.common.Utils.parseIntMinusOne(liveStreamsDBModel.getStreamId());
                    String contaiinerExtension = liveStreamsDBModel.getContaiinerExtension();
                    MediaMetadata mediaMetadata = new MediaMetadata(1);
                    if (name != null && !name.isEmpty()) {
                        mediaMetadata.putString(MediaMetadata.KEY_TITLE, name);
                    }
                    if (streamIcon != null && !streamIcon.isEmpty()) {
                        mediaMetadata.addImage(new WebImage(Uri.parse(streamIcon)));
                    }
                    ChromeCastUtilClass.loadRemoteMedia(new MediaInfo.Builder(com.nst.yourname.miscelleneious.common.Utils.getUrl(VodAdapter.this.context, parseIntMinusOne, contaiinerExtension, "series")).setStreamType(1).setContentType("videos/mp4").setMetadata(mediaMetadata).build(), VodAdapter.this.mCastSession, VodAdapter.this.context);
                }
            }
        });
    }

    @Override // com.nst.yourname.view.adapter.OptionExternalPlayerAdapter.ItemClickListener
    public void itemClicked(View view, String str, String str2) {
        if (this.liveStreamsDBModelMenu != null) {
            String url = com.nst.yourname.miscelleneious.common.Utils.getUrl(this.context, com.nst.yourname.miscelleneious.common.Utils.parseIntMinusOne(this.liveStreamsDBModelMenu.getStreamId()), this.liveStreamsDBModelMenu.getContaiinerExtension(), AppConst.EVENT_TYPE_MOVIE);
            Intent intent = new Intent(this.context, PlayExternalPlayerActivity.class);
            intent.putExtra("url", url);
            intent.putExtra(AppConst.APP_NAME, str);
            intent.putExtra(AppConst.PACKAGE_NAME, str2);
            this.context.startActivity(intent);
        }
    }
}
