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
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.FavouriteDBModel;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.DatabaseUpdatedStatusDBModel;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.presenter.VodPresenter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ALL")
public class SeriesDetailActivity extends AppCompatActivity implements View.OnClickListener {
    static final boolean $assertionsDisabled = false;
    private static final int DOWNLOAD_REQUEST_CODE = 101;
    int actionBarHeight;
    AlertDialog alertDialog;
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
    Button savePasswordBT;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    private String selectedPlayer = "";
    private String seriesID = "";
    int seriesID_final;
    private String series_categoryID = "";
    private String series_cover = "";
    private String series_director = "";
    private String series_genre = "";
    private String series_plot = "";
    private String series_rating = "";
    private String series_releasedate = "";
    private String series_series_name = "";
    private String seriesyouTubeTrailer = "";
    private int streamId = -1;
    private String streamType = "";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /* renamed from: tv  reason: collision with root package name */
    private TypedValue f21tv;
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
    private String userName = "";
    private String userPassword = "";
    private VodPresenter vodPresenter;
    private String youTubeTrailer = "";

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_series_detail);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        if (this.appbarToolbar != null) {
            this.appbarToolbar.setBackground(getResources().getDrawable(R.drawable.movie_info_bg));
        }
        if (this.tvPlay != null) {
            this.tvPlay.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.tvPlay));
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
        getWindow().setFlags(1024, 1024);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        changeStatusBarColor();
        initialize();
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.SeriesDetailActivity.AnonymousClass1 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(SeriesDetailActivity.this.context);
            }
        });
        if (this.tvHeaderTitle != null) {
            this.tvHeaderTitle.setOnClickListener(this);
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
                    SeriesDetailActivity.this.savePasswordBT.setBackgroundResource(R.drawable.back_btn_effect);
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
                    SeriesDetailActivity.this.savePasswordBT.setBackgroundResource(R.drawable.black_button_dark);
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

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity
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
        if (this.tvdetailprogressbar != null) {
            this.tvdetailprogressbar.setVisibility(0);
        }
        this.context = this;
        this.database = new DatabaseHandler(this.context);
        this.tvWatchTrailer.requestFocus();
        this.tvWatchTrailer.setFocusable(true);
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
        this.tvdetailprogressbar.setVisibility(8);
        this.scrollView.setVisibility(0);
        Intent intent = getIntent();
        if (intent != null) {
            this.series_series_name = intent.getStringExtra(AppConst.SERIES_NAME);
            this.series_plot = intent.getStringExtra(AppConst.SERIES_PLOT);
            this.series_rating = intent.getStringExtra(AppConst.SERIES_RATING);
            this.series_director = intent.getStringExtra(AppConst.SERIES_DIRECTOR);
            this.series_cover = intent.getStringExtra(AppConst.SERIES_COVER);
            this.series_releasedate = intent.getStringExtra(AppConst.SERIES_RELEASE_DATE);
            this.series_genre = intent.getStringExtra(AppConst.SERIES_GENERE);
            this.num = intent.getStringExtra(AppConst.SERIES_NUM);
            this.series_categoryID = intent.getStringExtra(AppConst.SERIES_CATEGORY_ID);
            this.seriesID = intent.getStringExtra(AppConst.SERIES_SERIES_ID);
            this.seriesyouTubeTrailer = intent.getStringExtra(AppConst.SERIES_YOU_TUBE_TRAILER);
            try {
                this.seriesID_final = Integer.parseInt(this.seriesID);
            } catch (NumberFormatException unused) {
                this.seriesID_final = -1;
            }
            this.fullCast = this.series_plot;
            this.fullGenre = this.series_genre;
            this.backdrop = intent.getStringExtra(AppConst.SERIES_BACKDROP);
            String str3 = "";
            ArrayList arrayList = null;
            boolean z = true;
            if (this.backdrop != null && !this.backdrop.isEmpty()) {
                str3 = this.backdrop.substring(1, this.backdrop.length() - 1);
            }
            if (str3 != null && !str3.isEmpty()) {
                arrayList = new ArrayList(Arrays.asList(str3.split(",")));
            }
            if (!(this.appbarToolbar == null || arrayList == null || arrayList.size() <= 0)) {
                Glide.with(getApplicationContext()).load((String) arrayList.get(new Random().nextInt(arrayList.size()))).asBitmap().into(new SimpleTarget<Bitmap>() {
                    /* class com.nst.yourname.view.activity.SeriesDetailActivity.AnonymousClass2 */

                    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                        SeriesDetailActivity.this.appbarToolbar.setBackground(new BitmapDrawable(bitmap));
                        SeriesDetailActivity.this.rlTransparent.setBackgroundColor(SeriesDetailActivity.this.getResources().getColor(R.color.trasparent_black));
                        SeriesDetailActivity.this.toolbar.setBackgroundColor(SeriesDetailActivity.this.getResources().getColor(R.color.trasparent_black));
                    }
                });
            }
            this.tvdetailprogressbar.setVisibility(8);
            if (!(context2 == null || this.series_cover == null || this.series_cover.isEmpty())) {
                Glide.with(context2).load(this.series_cover).asBitmap().into(new SimpleTarget<Bitmap>() {
                    /* class com.nst.yourname.view.activity.SeriesDetailActivity.AnonymousClass3 */

                    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                        SeriesDetailActivity.this.ivMovieImage.setBackground(new BitmapDrawable(bitmap));
                    }
                });
            }
            if (this.tvWatchTrailer != null && this.seriesyouTubeTrailer != null && this.seriesyouTubeTrailer.isEmpty() && this.seriesyouTubeTrailer.equals("")) {
                this.tvWatchTrailer.setVisibility(8);
                if (this.tvPlay != null) {
                    this.tvPlay.requestFocus();
                    this.tvPlay.setFocusableInTouchMode(true);
                }
            }
            if (!(this.movieName == null || this.tvMovieName == null)) {
                this.tvMovieName.setText(this.series_series_name);
            }
            if (this.database.checkFavourite(this.seriesID_final, this.series_categoryID, "series", SharepreferenceDBHandler.getUserID(context2)).size() > 0) {
                this.tvdetailbackbutton.setText(getResources().getString(R.string.remove_from_favourite));
                this.ivFavourite.setVisibility(0);
            } else {
                this.tvdetailbackbutton.setText(getResources().getString(R.string.add_to_favourite));
                this.ivFavourite.setVisibility(4);
            }
            if (this.llReleasedBox == null || this.llReleasedBoxInfo == null || this.tvReleaseDateInfo == null || this.series_releasedate == null || this.series_releasedate.isEmpty() || this.series_releasedate.equals("n/A")) {
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
                this.tvReleaseDateInfo.setText(this.series_releasedate);
            }
            if (this.tvDirectorInfo == null || this.llDirectorBoxInfo == null || this.llDirectorBox == null || this.series_director == null || this.series_director.isEmpty() || this.series_director.equals("n/A")) {
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
                this.tvDirectorInfo.setText(this.series_director);
            }
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
                }
            } else {
                this.llCastBox.setVisibility(0);
                this.llCastBoxInfo.setVisibility(0);
                if (this.series_plot.length() > 150) {
                    this.tvCastInfo.setText(this.series_plot);
                    this.tvReadMore.setVisibility(0);
                } else {
                    this.tvCastInfo.setText(this.series_plot);
                    this.tvReadMore.setVisibility(8);
                }
            }
            if (this.ratingBar != null && this.series_rating != null && !this.series_rating.isEmpty() && !this.series_rating.equals("n/A")) {
                this.ratingBar.setVisibility(0);
                try {
                    this.ratingBar.setRating(Float.parseFloat(this.series_rating) / 2.0f);
                } catch (NumberFormatException unused2) {
                    this.ratingBar.setRating(0.0f);
                }
            }
            if (this.llGenreBox == null || this.llGenreBoxInfo == null || this.tv_genre_info == null || this.series_genre == null || this.series_genre.isEmpty()) {
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
                    return;
                }
                return;
            }
            this.llGenreBox.setVisibility(0);
            this.llGenreBoxInfo.setVisibility(0);
            if (this.series_genre.length() <= 40) {
                z = false;
            }
            if (z) {
                this.tv_genre_info.setText(this.series_genre);
                this.tvReadMoreGenre.setVisibility(0);
                return;
            }
            this.tv_genre_info.setText(this.series_genre);
            this.tvReadMoreGenre.setVisibility(8);
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
                /* class com.nst.yourname.view.activity.SeriesDetailActivity.AnonymousClass5 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.logoutUser(SeriesDetailActivity.this.context);
                }
            }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeriesDetailActivity.AnonymousClass4 */

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
                /* class com.nst.yourname.view.activity.SeriesDetailActivity.AnonymousClass6 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadChannelsAndVod(SeriesDetailActivity.this.context);
                }
            });
            builder.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeriesDetailActivity.AnonymousClass7 */

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
                /* class com.nst.yourname.view.activity.SeriesDetailActivity.AnonymousClass8 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadTvGuid(SeriesDetailActivity.this.context);
                }
            });
            builder2.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeriesDetailActivity.AnonymousClass9 */

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

    public void onFinish() {
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
    }

    public void onFailed(String str) {
        if (this.tvdetailprogressbar != null) {
            this.tvdetailprogressbar.setVisibility(8);
        }
    }

    @OnClick({R.id.tv_play, R.id.tv_add_to_fav, R.id.tv_detail_back_btn, R.id.tv_readmore, R.id.tv_readmore_genre, R.id.tv_watch_trailer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add_to_fav:
                startActivity(new Intent(this, SeasonsActivitiy.class).putExtra(AppConst.SERIES_SERIES_ID, this.seriesID).putExtra(AppConst.SERIES_COVER, this.series_cover).putExtra(AppConst.SERIES_NAME, this.series_series_name));
                return;
            case R.id.tv_detail_back_btn:
                if (this.database.checkFavourite(this.seriesID_final, this.series_categoryID, "series", SharepreferenceDBHandler.getUserID(this.context)).size() > 0) {
                    removeFromFavourite();
                    return;
                } else {
                    addToFavourite();
                    return;
                }
            case R.id.tv_play:
                startActivity(new Intent(this, EpisodeDetailActivity.class).putExtra(AppConst.SERIES_SERIES_ID, this.seriesID).putExtra(AppConst.SERIES_COVER, this.series_cover).putExtra(AppConst.SERIES_NAME, this.series_series_name).putExtra(AppConst.SERIES_CATEGORY_ID, this.series_categoryID));
                return;
            case R.id.tv_readmore:
                showCastPopUp(this);
                return;
            case R.id.tv_readmore_genre:
                showGenrePopUp(this);
                return;
            case R.id.tv_watch_trailer:
                if (this.seriesyouTubeTrailer == null || this.seriesyouTubeTrailer.isEmpty()) {
                    passwordConfirmationPopUp(this);
                    return;
                } else {
                    startActivity(new Intent(this, YouTubePlayerActivity.class).putExtra(AppConst.YOUTUBE_TRAILER, this.seriesyouTubeTrailer));
                    return;
                }
            default:
                return;
        }
    }

    private void addToFavourite() {
        FavouriteDBModel favouriteDBModel = new FavouriteDBModel();
        favouriteDBModel.setCategoryID(this.series_categoryID);
        favouriteDBModel.setStreamID(this.seriesID_final);
        favouriteDBModel.setName(this.series_series_name);
        favouriteDBModel.setNum(this.num);
        favouriteDBModel.setUserID(SharepreferenceDBHandler.getUserID(this.context));
        this.database.addToFavourite(favouriteDBModel, "series");
        this.tvdetailbackbutton.setText(getResources().getString(R.string.remove_from_favourite));
        this.ivFavourite.setVisibility(0);
    }

    private void removeFromFavourite() {
        this.database.deleteFavourite(this.seriesID_final, this.series_categoryID, "series", this.series_series_name, SharepreferenceDBHandler.getUserID(this.context));
        this.tvdetailbackbutton.setText(getResources().getString(R.string.add_to_favourite));
        this.ivFavourite.setVisibility(4);
    }

    private void showCastPopUp(SeriesDetailActivity seriesDetailActivity) {
        View inflate = ((LayoutInflater) seriesDetailActivity.getSystemService("layout_inflater")).inflate((int) R.layout.layout_cast_details, (RelativeLayout) seriesDetailActivity.findViewById(R.id.rl_password_verification));
        this.tvCastInfoPopUp = (TextView) inflate.findViewById(R.id.tv_casts_info_popup);
        this.tv_parental_password = (TextView) inflate.findViewById(R.id.tv_parental_password);
        this.tv_parental_password.setText("Plot");
        this.tvCastInfoPopUp.setText(this.fullCast);
        this.changeSortPopUp = new PopupWindow(seriesDetailActivity);
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
            /* class com.nst.yourname.view.activity.SeriesDetailActivity.AnonymousClass10 */

            public void onClick(View view) {
                SeriesDetailActivity.this.changeSortPopUp.dismiss();
            }
        });
    }

    private void showGenrePopUp(SeriesDetailActivity seriesDetailActivity) {
        View inflate = ((LayoutInflater) seriesDetailActivity.getSystemService("layout_inflater")).inflate((int) R.layout.layout_genre_details, (RelativeLayout) seriesDetailActivity.findViewById(R.id.rl_password_verification));
        this.tvGenreInfoPopUp = (TextView) inflate.findViewById(R.id.tv_genre_info_popup);
        this.tvGenreInfoPopUp.setText(this.fullGenre);
        this.changeSortPopUp = new PopupWindow(seriesDetailActivity);
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
            /* class com.nst.yourname.view.activity.SeriesDetailActivity.AnonymousClass11 */

            public void onClick(View view) {
                SeriesDetailActivity.this.changeSortPopUp.dismiss();
            }
        });
    }

    private void passwordConfirmationPopUp(SeriesDetailActivity seriesDetailActivity) {
        View inflate = ((LayoutInflater) seriesDetailActivity.getSystemService("layout_inflater")).inflate((int) R.layout.layout_movie_trailer, (RelativeLayout) seriesDetailActivity.findViewById(R.id.rl_password_verification));
        this.changeSortPopUp = new PopupWindow(seriesDetailActivity);
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
            /* class com.nst.yourname.view.activity.SeriesDetailActivity.AnonymousClass12 */

            public void onClick(View view) {
                SeriesDetailActivity.this.changeSortPopUp.dismiss();
            }
        });
        this.savePasswordBT.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.SeriesDetailActivity.AnonymousClass13 */

            public void onClick(View view) {
                SeriesDetailActivity.this.changeSortPopUp.dismiss();
            }
        });
    }
}
