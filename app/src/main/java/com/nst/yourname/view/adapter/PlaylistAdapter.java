package com.nst.yourname.view.adapter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
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
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.RecentWatchDBHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.model.pojo.ExternalPlayerModelClass;
import com.nst.yourname.view.activity.VodActivityNewFlowSubCategories;
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
public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.MyViewHolder> {
    private static String uk;
    private static String una;
    private int AdapterPosition = 0;
    private String catIDforRecent = "";
    public List<LiveStreamsDBModel> completeList;
    public Context context;
    public List<LiveStreamsDBModel> dataSet;
    private DateFormat df;
    private Date dt;
    private SharedPreferences.Editor editor;
    private ArrayList<ExternalPlayerModelClass> externalPlayerList;
    public List<LiveStreamsDBModel> filterList;
    private Boolean focused = false;
    private SimpleDateFormat fr;
    public boolean isRecentWatch = true;
    LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesSharedPref;
    private SharedPreferences pref;
    private ProgressDialog progressDialog;
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
    }*/

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

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v9, resolved type: java.util.List<com.nst.yourname.model.LiveStreamsDBModel>} */
    /* JADX WARNING: Multi-variable type inference failed */
    public PlaylistAdapter(ArrayList<LiveStreamsDBModel> arrayList, Context context2, boolean z) {
        this.dataSet = arrayList;
        this.context = context2;
        this.ukd = com.nst.yourname.miscelleneious.common.Utils.ukde(FileMediaDataSource.apn());
        una = context2.getApplicationContext().getPackageName();
        this.filterList = new ArrayList();
        uk = getApplicationName(context2);
        this.unad = com.nst.yourname.miscelleneious.common.Utils.ukde(MeasureHelper.pnm());
        this.filterList.addAll(arrayList);
        this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        this.completeList = arrayList;
        this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
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
    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        this.pref = this.context.getSharedPreferences("listgridview", 0);
        this.editor = this.pref.edit();
        AppConst.LIVE_FLAG_PLAYLIST = this.pref.getInt("playlist", 0);
        if (AppConst.LIVE_FLAG_PLAYLIST == 1) {
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

    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, @SuppressLint({"RecyclerView"}) int i) {
        MyViewHolder myViewHolder2 = myViewHolder;
        int i2 = i;
        try {
            if (this.context != null) {
                this.AdapterPosition = i2;
                this.loginPreferencesSharedPref = this.context.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_PLAYER, 0);
                final String string = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SELECTED_PLAYER, "");
                if (i2 == 0 && myViewHolder2.Movie != null && !this.focused.booleanValue()) {
                    this.focused = true;
                    myViewHolder2.Movie.requestFocus();
                }
                final String categoryId = this.dataSet.get(i2).getCategoryId();
                myViewHolder2.MovieName.setText(this.dataSet.get(i2).getName());
                String streamIcon = this.dataSet.get(i2).getStreamIcon();
                final String num = this.dataSet.get(i2).getNum();
                final String name = this.dataSet.get(i2).getName();
                final String url = this.dataSet.get(i2).getUrl();
                if (!this.isRecentWatch) {
                    myViewHolder2.recentWatchIV.setVisibility(0);
                }
                myViewHolder2.MovieImage.setImageDrawable(null);
                if (streamIcon != null && !streamIcon.equals("") && !streamIcon.isEmpty()) {
                    Picasso.with(this.context).load(this.dataSet.get(i2).getStreamIcon()).error((int) R.drawable.noposter).placeholder((int) R.drawable.noposter).into(myViewHolder2.MovieImage);
                } else if (Build.VERSION.SDK_INT >= 21) {
                    myViewHolder2.MovieImage.setImageDrawable(this.context.getResources().getDrawable(R.drawable.noposter, null));
                } else {
                    myViewHolder2.MovieImage.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.noposter));
                }
                if (this.liveStreamDBHandler.checkFavourite(url, SharepreferenceDBHandler.getUserID(this.context)).size() > 0) {
                    myViewHolder2.ivFavourite.setVisibility(0);
                } else {
                    myViewHolder2.ivFavourite.setVisibility(4);
                }
                myViewHolder2.cardView.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.adapter.PlaylistAdapter.AnonymousClass1 */

                    public void onClick(View view) {
                        PlaylistAdapter.this.startViewDeatilsActivity(url, name, string, num);
                    }
                });
                myViewHolder2.MovieImage.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.adapter.PlaylistAdapter.AnonymousClass2 */

                    public void onClick(View view) {
                        PlaylistAdapter.this.startViewDeatilsActivity(url, name, string, num);
                    }
                });
                myViewHolder2.Movie.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.adapter.PlaylistAdapter.AnonymousClass3 */

                    public void onClick(View view) {
                        PlaylistAdapter.this.startViewDeatilsActivity(url, name, string, num);
                    }
                });
                myViewHolder2.Movie.setOnFocusChangeListener(new OnFocusChangeAccountListener(myViewHolder2.Movie));
                myViewHolder2.Movie.setOnLongClickListener(new View.OnLongClickListener() {
                    /* class com.nst.yourname.view.adapter.PlaylistAdapter.AnonymousClass4 */

                    public boolean onLongClick(View view) {
                        PlaylistAdapter.this.popmenu(myViewHolder, categoryId, name, string, url, num);
                        return true;
                    }
                });
                myViewHolder2.MovieImage.setOnLongClickListener(new View.OnLongClickListener() {
                    /* class com.nst.yourname.view.adapter.PlaylistAdapter.AnonymousClass5 */

                    public boolean onLongClick(View view) {
                        PlaylistAdapter.this.popmenu(myViewHolder, categoryId, name, string, url, num);
                        return true;
                    }
                });
                myViewHolder2.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    /* class com.nst.yourname.view.adapter.PlaylistAdapter.AnonymousClass6 */

                    public boolean onLongClick(View view) {
                        PlaylistAdapter.this.popmenu(myViewHolder, categoryId, name, string, url, num);
                        return true;
                    }
                });
                myViewHolder2.llMenu.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.adapter.PlaylistAdapter.AnonymousClass7 */

                    public void onClick(View view) {
                        PlaylistAdapter.this.popmenu(myViewHolder, categoryId, name, string, url, num);
                    }
                });
            }
        } catch (Exception unused) {
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

    public void startViewDeatilsActivity(String str, String str2, String str3, String str4) {
        com.nst.yourname.miscelleneious.common.Utils.playAllWithPlayerVOD(this.context, str3, -1, AppConst.EVENT_TYPE_MOVIE, "", str4, str2, str);
    }

    public void popmenu(final MyViewHolder myViewHolder, final String str, final String str2, final String str3, final String str4, final String str5) {
        if (this.context != null) {
            PopupMenu popupMenu = new PopupMenu(this.context, myViewHolder.tvStreamOptions);
            popupMenu.inflate(R.menu.menu_card_vod_playlist);
            ArrayList<FavouriteM3UModel> checkFavourite = this.liveStreamDBHandler.checkFavourite(str4, SharepreferenceDBHandler.getUserID(this.context));
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
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                /* class com.nst.yourname.view.adapter.PlaylistAdapter.AnonymousClass8 */

                @Override // android.support.v7.widget.PopupMenu.OnMenuItemClickListener
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.menu_view_details:
                            PlaylistAdapter.this.startViewDeatilsActivity(str2, str3, str, str4);
                            return false;
                        case R.id.nav_add_to_fav:
                            addToFavourite();
                            return false;
                        case R.id.nav_delete_from_recentwatch:
                            if (PlaylistAdapter.this.vodActivityNewFlowSubCategoriesObj != null) {
                                return false;
                            }
                            boolean unused = PlaylistAdapter.this.isRecentWatch;
                            return false;
                        case R.id.nav_play:
                            if (!PlaylistAdapter.this.rq.booleanValue()) {
                                return false;
                            }
                            playMovie();
                            return false;
                        case R.id.nav_remove_from_fav:
                            removeFromFavourite();
                            return false;
                        default:
                            return false;
                    }
                }

                private void playMovie() {
                    com.nst.yourname.miscelleneious.common.Utils.playWithPlayerVOD(PlaylistAdapter.this.context, str3, -1, AppConst.EVENT_TYPE_MOVIE, "", str5, str2, str4);
                }

                private void addToFavourite() {
                    FavouriteM3UModel favouriteM3UModel = new FavouriteM3UModel();
                    favouriteM3UModel.setUrl(str4);
                    favouriteM3UModel.setUserID(SharepreferenceDBHandler.getUserID(PlaylistAdapter.this.context));
                    favouriteM3UModel.setName(str2);
                    favouriteM3UModel.setCategoryID(str);
                    PlaylistAdapter.this.liveStreamDBHandler.addToFavourite(favouriteM3UModel);
                    myViewHolder.ivFavourite.setVisibility(0);
                }

                private void removeFromFavourite() {
                    PlaylistAdapter.this.liveStreamDBHandler.deleteFavourite(str4, SharepreferenceDBHandler.getUserID(PlaylistAdapter.this.context));
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
            /* class com.nst.yourname.view.adapter.PlaylistAdapter.AnonymousClass9 */

            public void run() {
                try {
                    List unused = PlaylistAdapter.this.filterList = new ArrayList();
                    PlaylistAdapter.this.text_size = str.length();
                    if (PlaylistAdapter.this.filterList != null) {
                        PlaylistAdapter.this.filterList.clear();
                    }
                    if (TextUtils.isEmpty(str)) {
                        PlaylistAdapter.this.filterList.addAll(PlaylistAdapter.this.completeList);
                    } else {
                        if (PlaylistAdapter.this.dataSet.size() == 0 || PlaylistAdapter.this.text_last_size > PlaylistAdapter.this.text_size) {
                            List unused2 = PlaylistAdapter.this.dataSet = PlaylistAdapter.this.completeList;
                        }
                        for (LiveStreamsDBModel liveStreamsDBModel : PlaylistAdapter.this.dataSet) {
                            if (liveStreamsDBModel.getName() != null && liveStreamsDBModel.getName().toLowerCase().contains(str.toLowerCase())) {
                                PlaylistAdapter.this.filterList.add(liveStreamsDBModel);
                            }
                        }
                    }
                    ((Activity) PlaylistAdapter.this.context).runOnUiThread(new Runnable() {
                        /* class com.nst.yourname.view.adapter.PlaylistAdapter.AnonymousClass9.AnonymousClass1 */

                        public void run() {
                            if (TextUtils.isEmpty(str)) {
                                List unused = PlaylistAdapter.this.dataSet = PlaylistAdapter.this.completeList;
                            } else if (!PlaylistAdapter.this.filterList.isEmpty() || PlaylistAdapter.this.filterList.isEmpty()) {
                                List unused2 = PlaylistAdapter.this.dataSet = PlaylistAdapter.this.filterList;
                            }
                            if (PlaylistAdapter.this.dataSet.size() == 0) {
                                textView.setVisibility(0);
                            }
                            PlaylistAdapter.this.text_last_size = PlaylistAdapter.this.text_size;
                            PlaylistAdapter.this.notifyDataSetChanged();
                        }
                    });
                } catch (Exception unused3) {
                }
            }
        }).start();
    }
}
