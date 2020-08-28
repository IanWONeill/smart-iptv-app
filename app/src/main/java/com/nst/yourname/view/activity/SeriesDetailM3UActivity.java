package com.nst.yourname.view.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.common.net.HttpHeaders;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.FavouriteM3UModel;
import com.nst.yourname.model.callback.SearchTMDBTVShowsCallback;
import com.nst.yourname.model.callback.TMDBTVShowsInfoCallback;
import com.nst.yourname.model.callback.TMDBTrailerCallback;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.DatabaseUpdatedStatusDBModel;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.presenter.SearchTVShowsPresenter;
import com.nst.yourname.presenter.VodPresenter;
import com.nst.yourname.view.ijkplayer.widget.media.FileMediaDataSource;
import com.nst.yourname.view.ijkplayer.widget.media.InfoHudViewHolder;
import com.nst.yourname.view.ijkplayer.widget.media.MeasureHelper;
import com.nst.yourname.view.ijkplayer.widget.media.MediaPlayerCompat;
import com.nst.yourname.view.interfaces.SearchTVShowsInterface;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("all")
public class SeriesDetailM3UActivity extends AppCompatActivity implements View.OnClickListener, SearchTVShowsInterface {
    static final boolean $assertionsDisabled = false;
    private static String uk;
    private static String una;
    int actionBarHeight;
    @BindView(R.id.appbar_toolbar)
    AppBarLayout appbarToolbar;
    private String backdrop = "";
    private String categoryId = "";
    public PopupWindow changeSortPopUp;
    private TextView clientNameTv;
    Button closedBT;
    private String containerExtension = "";
    public Context context;
    private DatabaseHandler database;
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelEPG = new DatabaseUpdatedStatusDBModel();
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelLive = new DatabaseUpdatedStatusDBModel();
    DateFormat df;
    Date dt;
    private String episode_name = "";
    private String episode_url = "";
    SimpleDateFormat fr;
    String fullCast;
    String fullGenre;
    @BindView(R.id.iv_favourite)
    ImageView ivFavourite;
    @BindView(R.id.iv_movie_image)
    ImageView ivMovieImage;
    private LiveStreamDBHandler liveStreamDBHandler;
    @BindView(R.id.ll_cast_box)
    LinearLayout llCastBox;
    @BindView(R.id.ll_cast_box_info)
    LinearLayout llCastBoxInfo;
    @BindView(R.id.ll_detail_left_side)
    LinearLayout llDetailLeftSide;
    @BindView(R.id.ll_detail_ProgressBar)
    LinearLayout llDetailProgressBar;
    @BindView(R.id.ll_detail_right_side)
    LinearLayout llDetailRightSide;
    @BindView(R.id.ll_director_box)
    LinearLayout llDirectorBox;
    @BindView(R.id.ll_director_box_info)
    LinearLayout llDirectorBoxInfo;
    @BindView(R.id.ll_duration_box)
    LinearLayout llDurationBox;
    @BindView(R.id.ll_duration_box_info)
    LinearLayout llDurationBoxInfo;
    @BindView(R.id.ll_genre_box)
    LinearLayout llGenreBox;
    @BindView(R.id.ll_genre_box_info)
    LinearLayout llGenreBoxInfo;
    @BindView(R.id.ll_movie_info_box)
    LinearLayout llMovieInfoBox;
    @BindView(R.id.ll_released_box)
    LinearLayout llReleasedBox;
    @BindView(R.id.ll_released_box_info)
    LinearLayout llReleasedBoxInfo;
    private SharedPreferences loginPreferencesAfterLogin;
    private SharedPreferences loginPreferencesSharedPref;
    @BindView(R.id.logo)
    ImageView logo;
    MenuItem menuItemSettings;
    Menu menuSelect;
    private String movieName = "";
    private String num = "";
    private ProgressDialog progressDialog;
    @BindView(R.id.rating)
    RatingBar ratingBar;
    @BindView(R.id.rl_account_info)
    RelativeLayout rlAccountInfo;
    @BindView(R.id.rl_transparent)
    RelativeLayout rlTransparent;
    private Boolean rq = true;
    Button savePasswordBT;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    private SearchTVShowsPresenter searchTVShowsPresenter;
    private String selectedPlayer = "";
    private String seriesID = "";
    int seriesID_final;
    private String seriesLoginType = "";
    private String seriesSeasons = "";
    private String series_categoryID = "";
    private String series_cover = "";
    private String series_director = "";
    private String series_genre = "";
    private String series_icon = "";
    private String series_name = "";
    private String series_plot = "";
    private String series_rating = "";
    private String series_releasedate = "";
    private String seriesyouTubeTrailer = "";
    private int streamId = -1;
    private String streamType = "";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /* renamed from: tv  reason: collision with root package name */
    private TypedValue f22tv;
    @BindView(R.id.tv_account_info)
    TextView tvAccountInfo;
    @BindView(R.id.tv_add_to_fav)
    TextView tvAddToFav;
    @BindView(R.id.tv_cast)
    TextView tvCast;
    @BindView(R.id.tv_cast_info)
    TextView tvCastInfo;
    TextView tvCastInfoPopUp;
    @BindView(R.id.tv_director)
    TextView tvDirector;
    @BindView(R.id.tv_director_info)
    TextView tvDirectorInfo;
    TextView tvGenreInfoPopUp;
    ImageView tvHeaderTitle;
    @BindView(R.id.tv_movie_duration)
    TextView tvMovieDuration;
    @BindView(R.id.tv_movie_duration_info)
    TextView tvMovieDurationInfo;
    @BindView(R.id.tv_movie_genere)
    TextView tvMovieGenere;
    @BindView(R.id.tv_movie_info)
    TextView tvMovieInfo;
    @BindView(R.id.tv_movie_name)
    TextView tvMovieName;
    @BindView(R.id.tv_play)
    TextView tvPlay;
    @BindView(R.id.tv_readmore)
    TextView tvReadMore;
    @BindView(R.id.tv_readmore_genre)
    TextView tvReadMoreGenre;
    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;
    @BindView(R.id.tv_release_date_info)
    TextView tvReleaseDateInfo;
    @BindView(R.id.tv_watch_trailer)
    TextView tvWatchTrailer;
    @BindView(R.id.tv_genre_info)
    TextView tv_genre_info;
    TextView tv_parental_password;
    @BindView(R.id.tv_detail_back_btn)
    TextView tvdetailbackbutton;
    @BindView(R.id.tv_detail_ProgressBar)
    ProgressBar tvdetailprogressbar;
    String ukd;
    String unad;
    private String userName = "";
    private String userPassword = "";
    private VodPresenter vodPresenter;
    private String youTubeTrailer = "";

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void atStart() {
    }

    @Override // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_series_detail_m3u);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        this.context = this;
        if (this.appbarToolbar != null) {
            this.appbarToolbar.setBackground(getResources().getDrawable(R.drawable.movie_info_bg));
        }
        if (this.tvPlay != null) {
            this.tvPlay.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.tvPlay));
            this.tvPlay.requestFocus();
            this.tvPlay.setFocusable(true);
        }
        if (this.tvAddToFav != null) {
            this.tvAddToFav.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.tvAddToFav));
        }
        if (this.tvdetailbackbutton != null) {
            this.tvdetailbackbutton.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.tvdetailbackbutton));
        }
        if (this.tvReadMore != null) {
            this.tvReadMore.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.tvReadMore));
        }
        if (this.tvReadMoreGenre != null) {
            this.tvReadMoreGenre.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.tvReadMoreGenre));
        }
        if (this.tvWatchTrailer != null) {
            this.tvWatchTrailer.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.tvWatchTrailer));
        }
        this.ukd = Utils.ukde(FileMediaDataSource.apn());
        this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        this.dt = new Date();
        this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        una = getApplicationContext().getPackageName();
        this.unad = Utils.ukde(MeasureHelper.pnm());
        uk = getApplicationName(this.context);
        getWindow().setFlags(1024, 1024);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        changeStatusBarColor();
        SharepreferenceDBHandler.setCurrentAPPType(AppConst.TYPE_M3U, this.context);
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.SeriesDetailM3UActivity.AnonymousClass1 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(SeriesDetailM3UActivity.this.context);
            }
        });
        initialize();
        this.loginPreferencesSharedPref = this.context.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_PLAYER, 0);
        this.selectedPlayer = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SELECTED_PLAYER, "");
        if (this.tvHeaderTitle != null) {
            this.tvHeaderTitle.setOnClickListener(this);
        }
    }

    public static String getApplicationName(Context context2) {
        return String.valueOf(context2.getApplicationInfo().loadLabel(context2.getPackageManager()));
    }

    @Override // com.nst.yourname.view.interfaces.SearchTVShowsInterface
    public void getTVShowsInfo(SearchTMDBTVShowsCallback searchTMDBTVShowsCallback) {
        String str;
        if (!(searchTMDBTVShowsCallback == null || searchTMDBTVShowsCallback.getTotalResults() == null)) {
            boolean z = true;
            if (!(!searchTMDBTVShowsCallback.getTotalResults().equals(1) || searchTMDBTVShowsCallback.getResults() == null || searchTMDBTVShowsCallback.getResults().get(0) == null)) {
                int intValue = searchTMDBTVShowsCallback.getResults().get(0).getId().intValue();
                this.searchTVShowsPresenter.getTVShowsGenreAndDirector(intValue);
                this.searchTVShowsPresenter.getTrailer(intValue);
                String firstAirDate = searchTMDBTVShowsCallback.getResults().get(0).getFirstAirDate();
                Double voteAverage = searchTMDBTVShowsCallback.getResults().get(0).getVoteAverage();
                this.series_plot = searchTMDBTVShowsCallback.getResults().get(0).getOverview();
                String backdropPath = searchTMDBTVShowsCallback.getResults().get(0).getBackdropPath();
                if ((getResources().getConfiguration().screenLayout & 15) == 3) {
                    str = AppConst.TMDB_IMAGE_BASE_URL_LARGE + backdropPath;
                } else {
                    str = AppConst.TMDB_IMAGE_BASE_URL_SMALL + backdropPath;
                }
                if (!(this.appbarToolbar == null || backdropPath == null)) {
                    Glide.with(getApplicationContext()).load(str).asBitmap().into(new SimpleTarget<Bitmap>() {
                        /* class com.nst.yourname.view.activity.SeriesDetailM3UActivity.AnonymousClass2 */

                        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                            SeriesDetailM3UActivity.this.appbarToolbar.setBackground(new BitmapDrawable(bitmap));
                            SeriesDetailM3UActivity.this.rlTransparent.setBackgroundColor(SeriesDetailM3UActivity.this.getResources().getColor(R.color.trasparent_black));
                            SeriesDetailM3UActivity.this.toolbar.setBackgroundColor(SeriesDetailM3UActivity.this.getResources().getColor(R.color.trasparent_black));
                        }
                    });
                }
                if (this.llReleasedBox == null || this.llReleasedBoxInfo == null || this.tvReleaseDateInfo == null || firstAirDate == null || firstAirDate.isEmpty() || firstAirDate.equals("n/A")) {
                    if (this.llReleasedBox != null) {
                        this.llReleasedBox.setVisibility(0);
                    }
                    if (this.llReleasedBoxInfo != null) {
                        this.llReleasedBoxInfo.setVisibility(0);
                    }
                    if (this.tvReleaseDateInfo != null) {
                        this.tvReleaseDateInfo.setText("N/A");
                    }
                } else {
                    this.llReleasedBox.setVisibility(0);
                    this.llReleasedBoxInfo.setVisibility(0);
                    this.tvReleaseDateInfo.setText(firstAirDate);
                }
                if (!(this.ratingBar == null || voteAverage == null || voteAverage.equals("n/A"))) {
                    this.ratingBar.setVisibility(0);
                    try {
                        this.ratingBar.setRating(Float.parseFloat(String.valueOf(voteAverage)) / 2.0f);
                    } catch (NumberFormatException unused) {
                        this.ratingBar.setRating(0.0f);
                    }
                }
                this.fullCast = this.series_plot;
                if (this.llCastBox == null || this.llCastBoxInfo == null || this.tvCastInfo == null || this.series_plot == null || this.series_plot.isEmpty()) {
                    if (this.llCastBox != null) {
                        this.llCastBox.setVisibility(0);
                    }
                    if (this.llCastBoxInfo != null) {
                        this.llCastBoxInfo.setVisibility(0);
                    }
                    if (this.tvReadMore != null) {
                        this.tvReadMore.setVisibility(8);
                    }
                    if (this.tvCastInfo != null) {
                        this.tvCastInfo.setText("N/A");
                        return;
                    }
                    return;
                }
                this.llCastBox.setVisibility(0);
                this.llCastBoxInfo.setVisibility(0);
                if (this.series_plot.length() <= 150) {
                    z = false;
                }
                if (z) {
                    this.tvCastInfo.setText(this.series_plot);
                    this.tvReadMore.setVisibility(0);
                    return;
                }
                this.tvCastInfo.setText(this.series_plot);
                this.tvReadMore.setVisibility(8);
                return;
            }
        }
        if (!(this.episode_name == null || this.tvMovieName == null)) {
            this.tvMovieName.setText(this.episode_name);
        }
        if (this.tvMovieDurationInfo != null) {
            this.tvMovieDurationInfo.setText("N/A");
        }
        if (this.tvCastInfo != null) {
            this.tvCastInfo.setText("N/A");
        }
        if (this.tvDirectorInfo != null) {
            this.tvDirectorInfo.setText("N/A");
        }
        if (this.tvReleaseDateInfo != null) {
            this.tvReleaseDateInfo.setText("N/A");
        }
        if (this.tvReadMoreGenre != null) {
            this.tvReadMoreGenre.setVisibility(8);
        }
        if (this.tv_genre_info != null) {
            this.tv_genre_info.setText("N/A");
        }
        if (this.tvReadMore != null) {
            this.tvReadMore.setVisibility(8);
        }
        this.llDetailProgressBar.setVisibility(8);
        this.llDetailRightSide.setVisibility(0);
    }

    @Override // com.nst.yourname.view.interfaces.SearchTVShowsInterface
    public void getTVShowsGenreAndDirector(TMDBTVShowsInfoCallback tMDBTVShowsInfoCallback) {
        if (tMDBTVShowsInfoCallback == null || tMDBTVShowsInfoCallback.getCreatedBy() == null || tMDBTVShowsInfoCallback.getCreatedBy().size() <= 0) {
            if (this.llDirectorBox != null) {
                this.llDirectorBox.setVisibility(0);
            }
            if (this.llDirectorBoxInfo != null) {
                this.llDirectorBoxInfo.setVisibility(0);
            }
            if (this.tvDirectorInfo != null) {
                this.tvDirectorInfo.setText("N/A");
            }
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < tMDBTVShowsInfoCallback.getCreatedBy().size(); i++) {
                sb.append(tMDBTVShowsInfoCallback.getCreatedBy().get(i).getName());
                sb.append(",");
            }
            String substring = sb.substring(0, sb.toString().lastIndexOf(44));
            if (this.tvDirectorInfo == null || this.llDirectorBoxInfo == null || this.llDirectorBox == null || substring.isEmpty()) {
                if (this.llDirectorBox != null) {
                    this.llDirectorBox.setVisibility(0);
                }
                if (this.llDirectorBoxInfo != null) {
                    this.llDirectorBoxInfo.setVisibility(0);
                }
                if (this.tvDirectorInfo != null) {
                    this.tvDirectorInfo.setText("N/A");
                }
            } else {
                this.llDirectorBox.setVisibility(0);
                this.llDirectorBoxInfo.setVisibility(0);
                this.tvDirectorInfo.setText(substring);
            }
        }
        if (tMDBTVShowsInfoCallback == null || tMDBTVShowsInfoCallback.getGenres() == null || tMDBTVShowsInfoCallback.getGenres().size() <= 0) {
            if (this.llGenreBox != null) {
                this.llGenreBox.setVisibility(0);
            }
            if (this.llGenreBoxInfo != null) {
                this.llGenreBoxInfo.setVisibility(0);
            }
            if (this.tvReadMoreGenre != null) {
                this.tvReadMoreGenre.setVisibility(8);
            }
            if (this.tv_genre_info != null) {
                this.tv_genre_info.setText("N/A");
            }
        } else {
            StringBuilder sb2 = new StringBuilder();
            for (int i2 = 0; i2 < tMDBTVShowsInfoCallback.getGenres().size(); i2++) {
                sb2.append(tMDBTVShowsInfoCallback.getGenres().get(i2).getName());
                sb2.append(" / ");
            }
            String substring2 = sb2.substring(0, sb2.toString().lastIndexOf(47));
            this.fullGenre = substring2;
            if (this.llGenreBox == null || this.llGenreBoxInfo == null || this.tv_genre_info == null || substring2.isEmpty()) {
                if (this.llGenreBox != null) {
                    this.llGenreBox.setVisibility(0);
                }
                if (this.llGenreBoxInfo != null) {
                    this.llGenreBoxInfo.setVisibility(0);
                }
                if (this.tvReadMoreGenre != null) {
                    this.tvReadMoreGenre.setVisibility(8);
                }
                if (this.tv_genre_info != null) {
                    this.tv_genre_info.setText("N/A");
                }
            } else {
                this.llGenreBox.setVisibility(0);
                this.llGenreBoxInfo.setVisibility(0);
                if (substring2.length() > 40) {
                    this.tv_genre_info.setText(substring2);
                    this.tvReadMoreGenre.setVisibility(0);
                } else {
                    this.tv_genre_info.setText(substring2);
                    this.tvReadMoreGenre.setVisibility(8);
                }
            }
        }
        this.llDetailProgressBar.setVisibility(8);
        this.llDetailRightSide.setVisibility(0);
    }

    @Override // com.nst.yourname.view.interfaces.SearchTVShowsInterface
    public void getTrailerTVShows(TMDBTrailerCallback tMDBTrailerCallback) {
        if (tMDBTrailerCallback != null && tMDBTrailerCallback.getResults() != null && tMDBTrailerCallback.getResults().size() > 0) {
            for (int i = 0; i < tMDBTrailerCallback.getResults().size(); i++) {
                if (tMDBTrailerCallback.getResults().get(i).getType().equals(HttpHeaders.TRAILER)) {
                    this.seriesyouTubeTrailer = tMDBTrailerCallback.getResults().get(i).getKey();
                    if (this.tvWatchTrailer != null) {
                        this.tvWatchTrailer.setVisibility(0);
                        return;
                    }
                    return;
                }
            }
        }
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
                    f = 1.05f;
                }
                Log.e("id is", "" + this.view.getTag());
                if (this.view.getTag().equals("1")) {
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                    this.view.setBackgroundResource(R.drawable.back_btn_effect);
                } else if (this.view.getTag().equals("2")) {
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                    this.view.setBackgroundResource(R.drawable.logout_btn_effect);
                } else if (this.view.getTag().equals("3")) {
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                    this.view.setBackgroundResource(R.drawable.blue_btn_effect);
                } else if (this.view.getTag().equals("5")) {
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                    this.view.setBackgroundResource(R.drawable.blue_btn_effect);
                } else if (this.view == null || this.view.getTag() == null || !this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_ID)) {
                    performScaleXAnimation(1.15f);
                    performScaleYAnimation(1.15f);
                } else {
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                    SeriesDetailM3UActivity.this.savePasswordBT.setBackgroundResource(R.drawable.back_btn_effect);
                }
            } else if (!z) {
                if (z) {
                    f = 1.09f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                if (this.view.getTag().equals("1")) {
                    this.view.setBackgroundResource(R.drawable.black_button_dark);
                } else if (this.view.getTag().equals("2")) {
                    this.view.setBackgroundResource(R.drawable.black_button_dark);
                } else if (this.view.getTag().equals("3")) {
                    this.view.setBackgroundResource(R.drawable.black_button_dark);
                } else if (this.view.getTag().equals("5")) {
                    this.view.setBackgroundResource(R.drawable.black_button_dark);
                } else if (this.view.getTag() != null && this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_ID)) {
                    SeriesDetailM3UActivity.this.savePasswordBT.setBackgroundResource(R.drawable.black_button_dark);
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

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 82) {
            return super.onKeyUp(i, keyEvent);
        }
        if (this.menuSelect == null) {
            return true;
        }
        this.menuSelect.performIdentifierAction(R.id.empty, 0);
        return true;
    }

    @Override // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        boolean z = keyEvent.getAction() == 0;
        if (keyCode == 82) {
            return z ? onKeyDown(keyCode, keyEvent) : onKeyUp(keyCode, keyEvent);
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    private void initialize() {
        this.context = this;
        this.database = new DatabaseHandler(this.context);
        this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        String string = this.loginPreferencesAfterLogin.getString("username", "");
        String string2 = this.loginPreferencesAfterLogin.getString("password", "");
        if (string == null || string2 == null || string.isEmpty() || string2.isEmpty()) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            startViewingDetails(this.context, string, string2);
        }
    }

    private void startViewingDetails(Context context2, String str, String str2) {
        this.searchTVShowsPresenter = new SearchTVShowsPresenter(this, context2);
        this.scrollView.setVisibility(0);
        Intent intent = getIntent();
        if (intent != null) {
            this.episode_name = intent.getStringExtra("episode_name");
            this.series_name = intent.getStringExtra(AppConst.SERIES_NAME);
            this.series_icon = intent.getStringExtra("series_icon");
            this.episode_url = intent.getStringExtra("episode_url");
            this.num = intent.getStringExtra(AppConst.SERIES_NUM);
            this.series_categoryID = intent.getStringExtra(AppConst.SERIES_CATEGORY_ID);
            if (this.liveStreamDBHandler.checkFavourite(this.episode_url, SharepreferenceDBHandler.getUserID(context2)).size() > 0) {
                this.tvAddToFav.setText(getResources().getString(R.string.remove_from_favourite));
                this.ivFavourite.setVisibility(0);
            } else {
                this.tvAddToFav.setText(getResources().getString(R.string.add_to_favourite));
                this.ivFavourite.setVisibility(4);
            }
            if (!(context2 == null || this.series_icon == null || this.series_icon.isEmpty())) {
                Glide.with(context2).load(this.series_icon).asBitmap().into(new SimpleTarget<Bitmap>() {
                    /* class com.nst.yourname.view.activity.SeriesDetailM3UActivity.AnonymousClass3 */

                    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                        SeriesDetailM3UActivity.this.ivMovieImage.setBackground(new BitmapDrawable(bitmap));
                    }
                });
            }
            if (!(this.movieName == null || this.tvMovieName == null)) {
                this.tvMovieName.setText(this.episode_name);
            }
            this.searchTVShowsPresenter.getTVShowsInfo(this.series_name);
        }
    }

    private void changeStatusBarColor() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= 19) {
            window.clearFlags(67108864);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(Integer.MIN_VALUE);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }

    public static long getDateDiff(SimpleDateFormat simpleDateFormat, String str, String str2) {
        try {
            return TimeUnit.DAYS.convert(simpleDateFormat.parse(str2).getTime() - simpleDateFormat.parse(str).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getUserName() {
        if (this.context == null) {
            return this.userName;
        }
        this.loginPreferencesAfterLogin = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        return this.loginPreferencesAfterLogin.getString("username", "");
    }

    public String getUserPassword() {
        if (this.context == null) {
            return this.userPassword;
        }
        this.loginPreferencesAfterLogin = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        return this.loginPreferencesAfterLogin.getString("password", "");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.toolbar.inflateMenu(R.menu.menu_text_icon);
        this.menuSelect = menu;
        this.menuItemSettings = menu.getItem(1).getSubMenu().findItem(R.id.empty);
        TypedValue typedValue = new TypedValue();
        if (getTheme().resolveAttribute(16843499, typedValue, true)) {
            TypedValue.complexToDimensionPixelSize(typedValue.data, getResources().getDisplayMetrics());
        }
        for (int i = 0; i < this.toolbar.getChildCount(); i++) {
            if (this.toolbar.getChildAt(i) instanceof ActionMenuView) {
                ((Toolbar.LayoutParams) this.toolbar.getChildAt(i).getLayoutParams()).gravity = 16;
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        this.menuItemSettings = menuItem;
        int itemId = menuItem.getItemId();
        if (itemId == R.id.nav_home) {
            startActivity(new Intent(this, NewDashboardActivity.class));
            finish();
        }
        if (itemId == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            finish();
        }
        if (itemId == R.id.action_logout && this.context != null) {
            new AlertDialog.Builder(this.context, R.style.AlertDialogCustom).setTitle(getResources().getString(R.string.logout_title)).setMessage(getResources().getString(R.string.logout_message)).setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeriesDetailM3UActivity.AnonymousClass5 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.logoutUser(SeriesDetailM3UActivity.this.context);
                }
            }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeriesDetailM3UActivity.AnonymousClass4 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
        if (itemId == R.id.menu_load_channels_vod) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(this.context.getResources().getString(R.string.confirm_to_refresh));
            builder.setMessage(this.context.getResources().getString(R.string.do_you_want_toproceed));
            builder.setIcon((int) R.drawable.questionmark);
            builder.setPositiveButton(this.context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeriesDetailM3UActivity.AnonymousClass6 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadChannelsAndVod(SeriesDetailM3UActivity.this.context);
                }
            });
            builder.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeriesDetailM3UActivity.AnonymousClass7 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
        }
        if (itemId == R.id.menu_load_tv_guide) {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            builder2.setTitle(this.context.getResources().getString(R.string.confirm_to_refresh));
            builder2.setMessage(this.context.getResources().getString(R.string.do_you_want_toproceed));
            builder2.setIcon((int) R.drawable.questionmark);
            builder2.setPositiveButton(this.context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeriesDetailM3UActivity.AnonymousClass8 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadTvGuid(SeriesDetailM3UActivity.this.context);
                }
            });
            builder2.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeriesDetailM3UActivity.AnonymousClass9 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder2.show();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.tv_header_title) {
            startActivity(new Intent(this, NewDashboardActivity.class));
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        Utils.appResume(this.context);
        this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        if (this.loginPreferencesAfterLogin.getString("username", "").equals("") && this.loginPreferencesAfterLogin.getString("password", "").equals("")) {
            startActivity(new Intent(this, LoginActivity.class));
        } else if (this.context != null) {
            onFinish();
        }
    }

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void onFinish() {
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
    }

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void onFailed(String str) {
        this.llDetailProgressBar.setVisibility(8);
        this.llDetailRightSide.setVisibility(0);
    }

    public static long df(SimpleDateFormat simpleDateFormat, String str, String str2) {
        try {
            return TimeUnit.DAYS.convert(simpleDateFormat.parse(str2).getTime() - simpleDateFormat.parse(str).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @OnClick({R.id.tv_play, R.id.tv_add_to_fav, R.id.tv_detail_back_btn, R.id.tv_readmore, R.id.tv_readmore_genre, R.id.tv_watch_trailer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add_to_fav:
                if (this.liveStreamDBHandler.checkFavourite(this.episode_url, SharepreferenceDBHandler.getUserID(this.context)).size() > 0) {
                    removeFromFavourite();
                    return;
                } else {
                    addToFavourite();
                    return;
                }
            case R.id.tv_detail_back_btn:
            case R.id.tv_watch_trailer:
                if (this.seriesyouTubeTrailer == null || this.seriesyouTubeTrailer.isEmpty()) {
                    passwordConfirmationPopUp(this);
                    return;
                } else {
                    startActivity(new Intent(this, YouTubePlayerActivity.class).putExtra(AppConst.YOUTUBE_TRAILER, this.seriesyouTubeTrailer));
                    return;
                }
            case R.id.tv_play:
                Context context2 = this.context;
                Context context3 = this.context;
                this.loginPreferencesSharedPref = context2.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_PLAYER, 0);
                String string = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SELECTED_PLAYER, "");
                if (df(this.fr, this.fr.format(new Date(MediaPlayerCompat.cit(this.context))), this.df.format(this.dt)) >= ((long) InfoHudViewHolder.ux()) && this.ukd != null && this.unad != null && (!uk.equals(this.ukd) || !(this.ukd == null || this.unad == null || una.equals(this.unad)))) {
                    this.rq = false;
                }
                if (this.rq.booleanValue()) {
                    try {
                        Utils.playWithPlayerSeries(this.context, string, 0, "series", "", this.num, this.episode_name, null, this.episode_url);
                        return;
                    } catch (Exception unused) {
                        return;
                    }
                } else {
                    return;
                }
            case R.id.tv_readmore:
                showCastPopUp(this);
                return;
            case R.id.tv_readmore_genre:
                showGenrePopUp(this);
                return;
            default:
                return;
        }
    }

    private void addToFavourite() {
        FavouriteM3UModel favouriteM3UModel = new FavouriteM3UModel();
        favouriteM3UModel.setUrl(this.episode_url);
        favouriteM3UModel.setUserID(SharepreferenceDBHandler.getUserID(this.context));
        favouriteM3UModel.setName(this.episode_name);
        favouriteM3UModel.setCategoryID(this.series_categoryID);
        this.liveStreamDBHandler.addToFavourite(favouriteM3UModel);
        this.tvAddToFav.setText(getResources().getString(R.string.remove_from_favourite));
        this.ivFavourite.setVisibility(0);
    }

    private void removeFromFavourite() {
        this.liveStreamDBHandler.deleteFavourite(this.episode_url, SharepreferenceDBHandler.getUserID(this.context));
        this.tvAddToFav.setText(getResources().getString(R.string.add_to_favourite));
        this.ivFavourite.setVisibility(4);
    }

    private void showCastPopUp(SeriesDetailM3UActivity seriesDetailM3UActivity) {
        View inflate = ((LayoutInflater) seriesDetailM3UActivity.getSystemService("layout_inflater")).inflate((int) R.layout.layout_cast_details, (RelativeLayout) seriesDetailM3UActivity.findViewById(R.id.rl_password_verification));
        this.tvCastInfoPopUp = (TextView) inflate.findViewById(R.id.tv_casts_info_popup);
        this.tv_parental_password = (TextView) inflate.findViewById(R.id.tv_parental_password);
        this.tv_parental_password.setText("Plot");
        this.tvCastInfoPopUp.setText(this.fullCast);
        this.changeSortPopUp = new PopupWindow(seriesDetailM3UActivity);
        this.changeSortPopUp.setContentView(inflate);
        this.changeSortPopUp.setWidth(-1);
        this.changeSortPopUp.setHeight(-1);
        this.changeSortPopUp.setFocusable(true);
        this.changeSortPopUp.showAtLocation(inflate, 17, 0, 0);
        this.closedBT = (Button) inflate.findViewById(R.id.bt_close);
        if (this.closedBT != null) {
            this.closedBT.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.closedBT));
        }
        this.closedBT.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.SeriesDetailM3UActivity.AnonymousClass10 */

            public void onClick(View view) {
                SeriesDetailM3UActivity.this.changeSortPopUp.dismiss();
            }
        });
    }

    private void showGenrePopUp(SeriesDetailM3UActivity seriesDetailM3UActivity) {
        View inflate = ((LayoutInflater) seriesDetailM3UActivity.getSystemService("layout_inflater")).inflate((int) R.layout.layout_genre_details, (RelativeLayout) seriesDetailM3UActivity.findViewById(R.id.rl_password_verification));
        this.tvGenreInfoPopUp = (TextView) inflate.findViewById(R.id.tv_genre_info_popup);
        this.tvGenreInfoPopUp.setText(this.fullGenre);
        this.changeSortPopUp = new PopupWindow(seriesDetailM3UActivity);
        this.changeSortPopUp.setContentView(inflate);
        this.changeSortPopUp.setWidth(-1);
        this.changeSortPopUp.setHeight(-1);
        this.changeSortPopUp.setFocusable(true);
        this.changeSortPopUp.showAtLocation(inflate, 17, 0, 0);
        this.closedBT = (Button) inflate.findViewById(R.id.bt_close);
        if (this.closedBT != null) {
            this.closedBT.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.closedBT));
        }
        this.closedBT.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.SeriesDetailM3UActivity.AnonymousClass11 */

            public void onClick(View view) {
                SeriesDetailM3UActivity.this.changeSortPopUp.dismiss();
            }
        });
    }

    private void passwordConfirmationPopUp(SeriesDetailM3UActivity seriesDetailM3UActivity) {
        View inflate = ((LayoutInflater) seriesDetailM3UActivity.getSystemService("layout_inflater")).inflate((int) R.layout.layout_movie_trailer, (RelativeLayout) seriesDetailM3UActivity.findViewById(R.id.rl_password_verification));
        this.changeSortPopUp = new PopupWindow(seriesDetailM3UActivity);
        this.changeSortPopUp.setContentView(inflate);
        this.changeSortPopUp.setWidth(-1);
        this.changeSortPopUp.setHeight(-1);
        this.changeSortPopUp.setFocusable(true);
        this.changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());
        this.changeSortPopUp.showAtLocation(inflate, 17, 0, 0);
        this.savePasswordBT = (Button) inflate.findViewById(R.id.bt_save_password);
        this.closedBT = (Button) inflate.findViewById(R.id.bt_close);
        ((TextView) inflate.findViewById(R.id.et_password)).setText("Series trailer is not available");
        if (this.savePasswordBT != null) {
            this.savePasswordBT.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.savePasswordBT));
        }
        if (this.closedBT != null) {
            this.closedBT.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.closedBT));
        }
        this.closedBT.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.SeriesDetailM3UActivity.AnonymousClass12 */

            public void onClick(View view) {
                SeriesDetailM3UActivity.this.changeSortPopUp.dismiss();
            }
        });
        this.savePasswordBT.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.SeriesDetailM3UActivity.AnonymousClass13 */

            public void onClick(View view) {
                SeriesDetailM3UActivity.this.changeSortPopUp.dismiss();
            }
        });
    }
}
