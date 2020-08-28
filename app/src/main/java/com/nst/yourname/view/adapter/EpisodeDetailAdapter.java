package com.nst.yourname.view.adapter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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
import com.nst.yourname.model.EpisodesUsingSinglton;
import com.nst.yourname.model.callback.GetEpisdoeDetailsCallback;
import com.nst.yourname.model.callback.SeasonsDetailCallback;
import com.nst.yourname.model.callback.SeriesDBModel;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.ExternalPlayerDataBase;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.SeriesRecentWatchDatabase;
import com.nst.yourname.model.pojo.ExternalPlayerModelClass;
import com.nst.yourname.view.activity.EpisodeDetailActivity;
import com.nst.yourname.view.activity.PlayExternalPlayerActivity;
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

@SuppressWarnings("all")
public class EpisodeDetailAdapter extends RecyclerView.Adapter<EpisodeDetailAdapter.MyViewHolder> implements OptionExternalPlayerAdapter.ItemClickListener {
    static final boolean $assertionsDisabled = false;
    private static final int DOWNLOAD_REQUEST_CODE = 101;
    private static SharedPreferences loginPreferencesSharedPref_time_format;
    private static String uk;
    private static String una;
    public String action;
    EpisodeDetailActivity activity;
    private AlertDialog alertDialog;
    public List<GetEpisdoeDetailsCallback> completeList;
    public Context context;
    public List<GetEpisdoeDetailsCallback> dataSet;
    private List<SeriesDBModel> dataSet1;
    private DatabaseHandler database;
    private DateFormat df;
    String downloadMoviename = "";
    String downloadcontainerExtension = "mp4";
    int downloadstreamId = 0;
    private Date dt;
    private SharedPreferences.Editor editor;
    public EpisodeItemClickListener episodeItemClickListener;
    public ArrayList<ExternalPlayerModelClass> externalPlayerList;
    public List<GetEpisdoeDetailsCallback> filterList;
    private SimpleDateFormat fr;
    private GetEpisdoeDetailsCallback getEpisdoeDetailsMenu;
    private boolean isRecentWatch = true;
    private LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesSharedPref;
    public CastSession mCastSession;
    private List<SeasonsDetailCallback> moviesListl;
    MyViewHolder myViewHolder;
    private SharedPreferences pref;
    private SimpleDateFormat programTimeFormat;
    public Boolean rq = true;
    private String seriesCover;
    public int text_last_size;
    public int text_size;
    private String ukd;
    private String unad;

    public interface EpisodeItemClickListener {
        void episodeitemClicked();
    }

    /*public class MyViewHolder_ViewBinding implements Unbinder {
        private MyViewHolder target;

        @UiThread
        public MyViewHolder_ViewBinding(MyViewHolder myViewHolder, View view) {
            this.target = myViewHolder;
            myViewHolder.MovieName = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_movie_name, "field 'MovieName'", TextView.class);
            myViewHolder.Movie = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_movie, "field 'Movie'", RelativeLayout.class);
            myViewHolder.MovieImage = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_movie_image, "field 'MovieImage'", ImageView.class);
            myViewHolder.cardView = (CardView) Utils.findRequiredViewAsType(view, R.id.card_view, "field 'cardView'", CardView.class);
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
                myViewHolder.ivFavourite = null;
                myViewHolder.llMenu = null;
                myViewHolder.recentWatchIV = null;
                return;
            }
            throw new IllegalStateException("Bindings already cleared.");
        }
    }*/

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v6, resolved type: java.util.List<com.nst.yourname.model.callback.GetEpisdoeDetailsCallback>} */
    /* JADX WARNING: Multi-variable type inference failed */
    public EpisodeDetailAdapter(List<GetEpisdoeDetailsCallback> list, Context context2, String str, String str2, EpisodeItemClickListener episodeItemClickListener2, List<SeriesDBModel> list2) {
        this.dataSet = list;
        this.context = context2;
        this.ukd = com.nst.yourname.miscelleneious.common.Utils.ukde(FileMediaDataSource.apn());
        una = context2.getApplicationContext().getPackageName();
        this.filterList = new ArrayList();
        this.filterList.addAll(list);
        uk = getApplicationName(context2);
        this.unad = com.nst.yourname.miscelleneious.common.Utils.ukde(MeasureHelper.pnm());
        this.completeList = list;
        this.database = new DatabaseHandler(context2);
        this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        this.dt = new Date();
        this.action = str2;
        this.episodeItemClickListener = episodeItemClickListener2;
        this.isRecentWatch = this.isRecentWatch;
        this.liveStreamDBHandler = new LiveStreamDBHandler(context2);
        this.seriesCover = str;
        this.dataSet1 = list2;
        EpisodesUsingSinglton.getInstance().setEpisodeList(list);
        if (df(this.fr, this.fr.format(new Date(MediaPlayerCompat.cit(context2))), this.df.format(this.dt)) >= ((long) InfoHudViewHolder.ux()) && this.ukd != null && this.unad != null) {
            if (!uk.equals(this.ukd) || !(this.ukd == null || this.unad == null || una.equals(this.unad))) {
                this.rq = false;
            }
        }
    }

    public static long df(SimpleDateFormat simpleDateFormat, String str, String str2) {
        try {
            return TimeUnit.DAYS.convert(simpleDateFormat.parse(str2).getTime() - simpleDateFormat.parse(str).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

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
        View view;
        if (this.action == null || !this.action.equalsIgnoreCase(AppConst.ACTION_COME_FROM_SERIES_DIRECT)) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate((int) R.layout.vod_grid_layout, viewGroup, false);
        } else {
            this.pref = this.context.getSharedPreferences("listgridview", 0);
            AppConst.LIVE_FLAG_SERIES = this.pref.getInt("series", 0);
            if (AppConst.LIVE_FLAG_SERIES == 1) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate((int) R.layout.vod_linear_layout, viewGroup, false);
            } else {
                view = LayoutInflater.from(viewGroup.getContext()).inflate((int) R.layout.vod_grid_layout, viewGroup, false);
            }
        }
        this.myViewHolder = new MyViewHolder(view);
        return this.myViewHolder;
    }

    public static String getApplicationName(Context context2) {
        return String.valueOf(context2.getApplicationInfo().loadLabel(context2.getPackageManager()));
    }

    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder2, final int i) {
        final int i2;
        int i3;
        final MyViewHolder myViewHolder3 = myViewHolder2;
        int i4 = i;
        if (this.context != null) {
            this.loginPreferencesSharedPref = this.context.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_PLAYER, 0);
            final String string = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SELECTED_PLAYER, "");
            if (this.dataSet1 != null && this.dataSet1.size() > 0) {
                SeriesDBModel seriesDBModel = this.dataSet1.get(i4);
                if (seriesDBModel.getcover() != null) {
                    seriesDBModel.getcover();
                }
            }
            myViewHolder3.ivFavourite.setVisibility(8);
            if (this.dataSet.get(i4) != null) {
                if (this.dataSet.get(i4).getId() != null) {
                    try {
                        i3 = Integer.parseInt(this.dataSet.get(i4).getId().trim());
                    } catch (NumberFormatException unused) {
                        i3 = -1;
                    }
                    i2 = i3;
                } else {
                    i2 = -1;
                }
                String str = "";
                if (this.dataSet.get(i4).getTitle() != null) {
                    myViewHolder3.MovieName.setText(this.dataSet.get(i4).getTitle());
                    str = this.dataSet.get(i4).getTitle();
                }
                final String str2 = str;
                if (this.dataSet.get(i4).getImage() != null && !this.dataSet.get(i4).getImage().equals("")) {
                    this.seriesCover = this.dataSet.get(i4).getImage();
                }
                final String str3 = this.seriesCover;
                myViewHolder3.MovieImage.setImageDrawable(null);
                String str4 = "";
                if (this.dataSet.get(i4).getContainerExtension() != null) {
                    str4 = this.dataSet.get(i4).getContainerExtension();
                }
                final String str5 = str4;
                if (str3 != null && !str3.equals("")) {
                    Picasso.with(this.context).load(str3).placeholder((int) R.drawable.noposter).into(myViewHolder3.MovieImage);
                } else if (Build.VERSION.SDK_INT >= 21) {
                    myViewHolder3.MovieImage.setImageDrawable(this.context.getResources().getDrawable(R.drawable.noposter, null));
                } else {
                    myViewHolder3.MovieImage.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.noposter));
                }
                if (this.action != null && this.action.equalsIgnoreCase(AppConst.ACTION_COME_FROM_SERIES_DIRECT)) {
                    myViewHolder3.recentWatchIV.setVisibility(0);
                }
                EpisodesUsingSinglton.getInstance().setEpisodeList(this.dataSet);
                myViewHolder3.cardView.setOnLongClickListener(new View.OnLongClickListener() {

                    final String val$finalContainerExtension1;
                    final int val$finalStreamId1;

                    {
                        this.val$finalStreamId1 = i2;
                        this.val$finalContainerExtension1 = str5;
                    }

                    @SuppressLint({"ResourceType"})
                    public boolean onLongClick(View view) {
                        final String id = ((GetEpisdoeDetailsCallback) EpisodeDetailAdapter.this.dataSet.get(i)).getId();
                        EpisodeDetailAdapter.this.downloadstreamId = com.nst.yourname.miscelleneious.common.Utils.parseIntZero(((GetEpisdoeDetailsCallback) EpisodeDetailAdapter.this.dataSet.get(myViewHolder2.getAdapterPosition())).getId());
                        EpisodeDetailAdapter.this.downloadcontainerExtension = ((GetEpisdoeDetailsCallback) EpisodeDetailAdapter.this.dataSet.get(myViewHolder2.getAdapterPosition())).getContainerExtension();
                        EpisodeDetailAdapter.this.downloadMoviename = ((GetEpisdoeDetailsCallback) EpisodeDetailAdapter.this.dataSet.get(myViewHolder2.getAdapterPosition())).getTitle();
                        PopupMenu popupMenu = new PopupMenu(EpisodeDetailAdapter.this.context, view);
                        popupMenu.getMenuInflater().inflate(R.menu.menu_players_selection, popupMenu.getMenu());
                        try {
                            if (EpisodeDetailAdapter.this.action != null && EpisodeDetailAdapter.this.action.equalsIgnoreCase(AppConst.ACTION_COME_FROM_SERIES_DIRECT)) {
                                popupMenu.getMenu().getItem(3).setVisible(true);
                                popupMenu.getMenu().getItem(0).setVisible(true);
                            }
                            CastSession unused = EpisodeDetailAdapter.this.mCastSession = CastContext.getSharedInstance(EpisodeDetailAdapter.this.context).getSessionManager().getCurrentCastSession();
                            if (EpisodeDetailAdapter.this.mCastSession == null || !EpisodeDetailAdapter.this.mCastSession.isConnected()) {
                                popupMenu.getMenu().getItem(2).setVisible(false);
                            } else {
                                popupMenu.getMenu().getItem(2).setVisible(true);
                            }
                        } catch (Exception e) {
                            Log.e("sdng", "" + e);
                        }
                        ArrayList unused2 = EpisodeDetailAdapter.this.externalPlayerList = new ArrayList();
                        ArrayList unused3 = EpisodeDetailAdapter.this.externalPlayerList = new ExternalPlayerDataBase(EpisodeDetailAdapter.this.context).getExternalPlayer();
                        if (EpisodeDetailAdapter.this.externalPlayerList != null && EpisodeDetailAdapter.this.externalPlayerList.size() > 0) {
                            for (int i = 0; i < EpisodeDetailAdapter.this.externalPlayerList.size(); i++) {
                                popupMenu.getMenu().add(0, i, i, ((ExternalPlayerModelClass) EpisodeDetailAdapter.this.externalPlayerList.get(i)).getAppname());
                            }
                        }
                        final ArrayList access$400 = EpisodeDetailAdapter.this.externalPlayerList;
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            /* class com.nst.yourname.view.adapter.EpisodeDetailAdapter.AnonymousClass1.AnonymousClass1 */

                            public boolean onMenuItemClick(MenuItem menuItem) {
                                try {
                                    if (EpisodeDetailAdapter.this.rq.booleanValue() && access$400 != null && access$400.size() > 0) {
                                        int i = 0;
                                        while (true) {
                                            if (i >= access$400.size()) {
                                                break;
                                            } else if (menuItem.getItemId() == i) {
                                                String url = com.nst.yourname.miscelleneious.common.Utils.getUrl(EpisodeDetailAdapter.this.context, val$finalStreamId1, val$finalContainerExtension1, "series");
                                                Intent intent = new Intent(EpisodeDetailAdapter.this.context, PlayExternalPlayerActivity.class);
                                                intent.putExtra("url", url);
                                                intent.putExtra(AppConst.APP_NAME, ((ExternalPlayerModelClass) access$400.get(i)).getAppname());
                                                intent.putExtra(AppConst.PACKAGE_NAME, ((ExternalPlayerModelClass) access$400.get(i)).getPackagename());
                                                EpisodeDetailAdapter.this.context.startActivity(intent);
                                                break;
                                            } else {
                                                i++;
                                            }
                                        }
                                    }
                                } catch (Exception unused) {
                                }
                                switch (menuItem.getItemId()) {
                                    case R.id.iv_delete:
                                        EpisodeDetailAdapter.this.deleteCurrentItem(id, i);
                                        break;
                                    case R.id.iv_download:
                                        EpisodeDetailAdapter.this.activity.checkingForDownload(EpisodeDetailAdapter.this.downloadstreamId, EpisodeDetailAdapter.this.downloadcontainerExtension, EpisodeDetailAdapter.this.downloadMoviename);
                                        break;
                                    case R.id.nav_play_with_mx:
                                        EpisodeDetailAdapter.this.rq.booleanValue();
                                        break;
                                    case R.id.nav_play_with_vlc:
                                        if (EpisodeDetailAdapter.this.rq.booleanValue()) {
                                            com.nst.yourname.miscelleneious.common.Utils.playWithPlayerSeries(EpisodeDetailAdapter.this.context, string, i2, "series", str5, String.valueOf(i), str2, null, "");
                                            break;
                                        }
                                        break;
                                    case R.id.play_with_cast:
                                        if (EpisodeDetailAdapter.this.rq.booleanValue()) {
                                            MediaMetadata mediaMetadata = new MediaMetadata(1);
                                            if (str2 != null && !str2.isEmpty()) {
                                                mediaMetadata.putString(MediaMetadata.KEY_TITLE, str2);
                                            }
                                            if (str3 != null && !str3.isEmpty()) {
                                                mediaMetadata.addImage(new WebImage(Uri.parse(str3)));
                                            }
                                            ChromeCastUtilClass.loadRemoteMedia(new MediaInfo.Builder(com.nst.yourname.miscelleneious.common.Utils.getUrl(EpisodeDetailAdapter.this.context, i2, str5, "series")).setStreamType(1).setContentType("videos/mp4").setMetadata(mediaMetadata).build(), EpisodeDetailAdapter.this.mCastSession, EpisodeDetailAdapter.this.context);
                                            break;
                                        }
                                        break;
                                }
                                return false;
                            }
                        });
                        if (EpisodeDetailAdapter.this.externalPlayerList != null && EpisodeDetailAdapter.this.externalPlayerList.size() > 0) {
                            popupMenu.show();
                        }
                        if (EpisodeDetailAdapter.this.mCastSession != null && EpisodeDetailAdapter.this.mCastSession.isConnected()) {
                            popupMenu.show();
                        }
                        if (EpisodeDetailAdapter.this.action != null && EpisodeDetailAdapter.this.action.equalsIgnoreCase(AppConst.ACTION_COME_FROM_SERIES_DIRECT)) {
                            popupMenu.show();
                        }
                        return true;
                    }
                });
                myViewHolder3.MovieImage.setOnLongClickListener(new View.OnLongClickListener() {
                    /* class com.nst.yourname.view.adapter.EpisodeDetailAdapter.AnonymousClass2 */

                    public boolean onLongClick(View view) {
                        myViewHolder3.cardView.performLongClick();
                        return true;
                    }
                });
                myViewHolder3.Movie.setOnLongClickListener(new View.OnLongClickListener() {
                    /* class com.nst.yourname.view.adapter.EpisodeDetailAdapter.AnonymousClass3 */

                    public boolean onLongClick(View view) {
                        myViewHolder3.cardView.performLongClick();
                        return true;
                    }
                });
                myViewHolder3.cardView.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.adapter.EpisodeDetailAdapter.AnonymousClass4 */

                    public void onClick(View view) {
                        if (EpisodeDetailAdapter.this.rq.booleanValue()) {
                            com.nst.yourname.miscelleneious.common.Utils.playWithPlayerSeries(EpisodeDetailAdapter.this.context, string, i2, "series", str5, String.valueOf(i), str2, null, "");
                        }
                    }
                });
                myViewHolder3.MovieImage.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.adapter.EpisodeDetailAdapter.AnonymousClass5 */

                    public void onClick(View view) {
                        if (EpisodeDetailAdapter.this.rq.booleanValue()) {
                            com.nst.yourname.miscelleneious.common.Utils.playWithPlayerSeries(EpisodeDetailAdapter.this.context, string, i2, "series", str5, String.valueOf(i), str2, null, "");
                        }
                    }
                });
                myViewHolder3.Movie.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.adapter.EpisodeDetailAdapter.AnonymousClass6 */

                    public void onClick(View view) {
                        if (EpisodeDetailAdapter.this.rq.booleanValue()) {
                            com.nst.yourname.miscelleneious.common.Utils.playWithPlayerSeries(EpisodeDetailAdapter.this.context, string, i2, "series", str5, String.valueOf(i), str2, null, "");
                        }
                    }
                });
                myViewHolder3.Movie.setOnFocusChangeListener(new OnFocusChangeAccountListener(myViewHolder3.Movie));
            }
        }
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

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.dataSet.size();
    }

    public void filter(final String str, final TextView textView) {
        new Thread(new Runnable() {
            /* class com.nst.yourname.view.adapter.EpisodeDetailAdapter.AnonymousClass7 */

            public void run() {
                List unused = EpisodeDetailAdapter.this.filterList = new ArrayList();
                EpisodeDetailAdapter.this.text_size = str.length();
                if (EpisodeDetailAdapter.this.filterList != null) {
                    EpisodeDetailAdapter.this.filterList.clear();
                }
                if (TextUtils.isEmpty(str)) {
                    EpisodeDetailAdapter.this.filterList.addAll(EpisodeDetailAdapter.this.completeList);
                } else {
                    if ((EpisodeDetailAdapter.this.dataSet != null && EpisodeDetailAdapter.this.dataSet.size() == 0) || EpisodeDetailAdapter.this.text_last_size > EpisodeDetailAdapter.this.text_size) {
                        List unused2 = EpisodeDetailAdapter.this.dataSet = EpisodeDetailAdapter.this.completeList;
                    }
                    if (EpisodeDetailAdapter.this.dataSet != null) {
                        for (GetEpisdoeDetailsCallback getEpisdoeDetailsCallback : EpisodeDetailAdapter.this.dataSet) {
                            if (getEpisdoeDetailsCallback.getTitle() != null && getEpisdoeDetailsCallback.getTitle().toLowerCase().contains(str.toLowerCase())) {
                                EpisodeDetailAdapter.this.filterList.add(getEpisdoeDetailsCallback);
                            }
                        }
                    }
                }
                ((Activity) EpisodeDetailAdapter.this.context).runOnUiThread(new Runnable() {
                    /* class com.nst.yourname.view.adapter.EpisodeDetailAdapter.AnonymousClass7.AnonymousClass1 */

                    public void run() {
                        if (TextUtils.isEmpty(str)) {
                            List unused = EpisodeDetailAdapter.this.dataSet = EpisodeDetailAdapter.this.completeList;
                        } else if (!EpisodeDetailAdapter.this.filterList.isEmpty() || EpisodeDetailAdapter.this.filterList.isEmpty()) {
                            List unused2 = EpisodeDetailAdapter.this.dataSet = EpisodeDetailAdapter.this.filterList;
                        }
                        if (EpisodeDetailAdapter.this.dataSet != null && EpisodeDetailAdapter.this.dataSet.size() == 0) {
                            textView.setVisibility(0);
                        }
                        EpisodeDetailAdapter.this.text_last_size = EpisodeDetailAdapter.this.text_size;
                        EpisodeDetailAdapter.this.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.widget.PopupWindow.<init>(android.view.View, int, int, boolean):void}
     arg types: [android.view.View, int, int, int]
     candidates:
      ClspMth{android.widget.PopupWindow.<init>(android.content.Context, android.util.AttributeSet, int, int):void}
      ClspMth{android.widget.PopupWindow.<init>(android.view.View, int, int, boolean):void} */
    public void showAlertMenu(final GetEpisdoeDetailsCallback getEpisdoeDetailsCallback) {
        new ArrayList();
        View inflate = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate((int) R.layout.menu_option_player, (ViewGroup) null);
        final PopupWindow popupWindow = new PopupWindow(inflate, 500, -2, true);
        popupWindow.showAtLocation(inflate, 17, 0, 0);
        inflate.setOnTouchListener(new View.OnTouchListener() {
            /* class com.nst.yourname.view.adapter.EpisodeDetailAdapter.AnonymousClass8 */

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
            this.getEpisdoeDetailsMenu = getEpisdoeDetailsCallback;
            recyclerView.setVisibility(0);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.context, 1, false));
            recyclerView.setAdapter(new OptionExternalPlayerAdapter(this.context, externalPlayer, this));
        }
        textView2.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.adapter.EpisodeDetailAdapter.AnonymousClass9 */

            public void onClick(View view) {
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.adapter.EpisodeDetailAdapter.AnonymousClass10 */

            public void onClick(View view) {
                if (EpisodeDetailAdapter.this.rq.booleanValue()) {
                    String title = getEpisdoeDetailsCallback.getTitle();
                    String image = getEpisdoeDetailsCallback.getImage();
                    int parseIntMinusOne = com.nst.yourname.miscelleneious.common.Utils.parseIntMinusOne(getEpisdoeDetailsCallback.getId());
                    String containerExtension = getEpisdoeDetailsCallback.getContainerExtension();
                    MediaMetadata mediaMetadata = new MediaMetadata(1);
                    if (title != null && !title.isEmpty()) {
                        mediaMetadata.putString(MediaMetadata.KEY_TITLE, title);
                    }
                    if (image != null && !image.isEmpty()) {
                        mediaMetadata.addImage(new WebImage(Uri.parse(image)));
                    }
                    ChromeCastUtilClass.loadRemoteMedia(new MediaInfo.Builder(com.nst.yourname.miscelleneious.common.Utils.getUrl(EpisodeDetailAdapter.this.context, parseIntMinusOne, containerExtension, "series")).setStreamType(1).setContentType("videos/mp4").setMetadata(mediaMetadata).build(), EpisodeDetailAdapter.this.mCastSession, EpisodeDetailAdapter.this.context);
                }
            }
        });
    }

    @Override // com.nst.yourname.view.adapter.OptionExternalPlayerAdapter.ItemClickListener
    public void itemClicked(View view, String str, String str2) {
        if (this.getEpisdoeDetailsMenu != null) {
            String url = com.nst.yourname.miscelleneious.common.Utils.getUrl(this.context, com.nst.yourname.miscelleneious.common.Utils.parseIntMinusOne(this.getEpisdoeDetailsMenu.getId()), this.getEpisdoeDetailsMenu.getContainerExtension(), "series");
            Intent intent = new Intent(this.context, PlayExternalPlayerActivity.class);
            intent.putExtra("url", url);
            intent.putExtra(AppConst.APP_NAME, str);
            intent.putExtra(AppConst.PACKAGE_NAME, str2);
            this.context.startActivity(intent);
        }
    }

    public void deleteCurrentItem(final String str, final int i) {
        try {
            View inflate = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate((int) R.layout.delete_recording_popup, (RelativeLayout) ((Activity) this.context).findViewById(R.id.rl_password_verification));
            final PopupWindow popupWindow = new PopupWindow(this.context);
            popupWindow.setContentView(inflate);
            popupWindow.setWidth(-1);
            popupWindow.setHeight(-1);
            popupWindow.setFocusable(true);
            popupWindow.showAtLocation(inflate, 17, 0, 0);
            ((TextView) inflate.findViewById(R.id.tv_delete_recording)).setText(this.context.getResources().getString(R.string.delete__episode_recent_movies));
            Button button = (Button) inflate.findViewById(R.id.bt_start_recording);
            Button button2 = (Button) inflate.findViewById(R.id.bt_close);
            if (button != null) {
                button.setOnFocusChangeListener(new OnFocusChangeAccountListener(button));
                button.requestFocus();
                button.requestFocusFromTouch();
            }
            if (button2 != null) {
                button2.setOnFocusChangeListener(new OnFocusChangeAccountListener(button2));
            }
            button2.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.EpisodeDetailAdapter.AnonymousClass11 */

                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });
            if (button != null) {
                button.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.adapter.EpisodeDetailAdapter.AnonymousClass12 */

                    public void onClick(View view) {
                        new SeriesRecentWatchDatabase(EpisodeDetailAdapter.this.context).deleteSeriesRecentwatch(str);
                        EpisodeDetailAdapter.this.dataSet.remove(i);
                        EpisodeDetailAdapter.this.notifyDataSetChanged();
                        EpisodeDetailAdapter.this.notifyItemRemoved(i);
                        if (EpisodeDetailAdapter.this.dataSet != null && EpisodeDetailAdapter.this.dataSet.size() == 0) {
                            EpisodeDetailAdapter.this.episodeItemClickListener.episodeitemClicked();
                        }
                        com.nst.yourname.miscelleneious.common.Utils.showToast(EpisodeDetailAdapter.this.context, EpisodeDetailAdapter.this.context.getResources().getString(R.string.seires_deleted_successfully));
                        popupWindow.dismiss();
                    }
                });
            }
        } catch (Exception unused) {
        }
    }
}
