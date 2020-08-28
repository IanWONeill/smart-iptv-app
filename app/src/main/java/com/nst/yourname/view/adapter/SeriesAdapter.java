package com.nst.yourname.view.adapter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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
import com.nst.yourname.model.FavouriteDBModel;
import com.nst.yourname.model.callback.SeriesDBModel;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.view.activity.SeriesDetailActivity;
import com.nst.yourname.view.activity.ViewDetailsActivity;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.MyViewHolder> {
    public List<SeriesDBModel> completeList;
    public Context context;
    public List<SeriesDBModel> dataSet;
    DatabaseHandler database;
    private SharedPreferences.Editor editor;
    public List<SeriesDBModel> filterList;
    private Boolean focused = false;
    private SharedPreferences loginPreferencesSharedPref;
    private SharedPreferences pref;
    private SharedPreferences settingsPrefs;
    public int text_last_size;
    public int text_size;

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
        @BindView(R.id.tv_streamOptions)
        TextView tvStreamOptions;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            setIsRecyclable(false);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: java.util.List<com.nst.yourname.model.callback.SeriesDBModel>} */
    /* JADX WARNING: Multi-variable type inference failed */
    public SeriesAdapter(List<SeriesDBModel> list, Context context2) {
        this.dataSet = list;
        this.context = context2;
        this.filterList = new ArrayList();
        this.filterList.addAll(list);
        this.completeList = list;
        this.database = new DatabaseHandler(context2);
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
        AppConst.LIVE_FLAG_SERIES = this.pref.getInt("series", 0);
        if (AppConst.LIVE_FLAG_SERIES == 1) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate((int) R.layout.vod_linear_layout, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate((int) R.layout.vod_grid_layout, viewGroup, false);
        }
        return new MyViewHolder(view);
    }

    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        final String str;
        final String str2;
        final String str3;
        final String str4;
        final String str5;
        final String str6;
        final String str7;
        final String str8;
        final String str9;
        final String str10;
        final String str11;
        final String str12;
        final int i2;
        final String str13;
        MyViewHolder myViewHolder2 = myViewHolder;
        int i3 = i;
        if (this.context != null) {
            String str14 = "";
            String str15 = "";
            String str16 = "";
            String str17 = "";
            String str18 = "";
            String str19 = "";
            String str20 = "";
            String str21 = "";
            String str22 = "";
            String str23 = "";
            String str24 = "";
            String str25 = "";
            String str26 = "";
            String str27 = "";
            String str28 = "";
            int i4 = -1;
            if (this.dataSet == null || this.dataSet.size() <= 0) {
                str11 = str14;
                str12 = str16;
                str10 = str17;
                str9 = str18;
                str8 = str19;
                str7 = str20;
                str6 = str21;
                str5 = str22;
                str13 = str23;
                str4 = str24;
                str3 = str25;
                str2 = str26;
                str = "";
                i2 = -1;
            } else {
                SeriesDBModel seriesDBModel = this.dataSet.get(i3);
                if (seriesDBModel.getNum() != null) {
                    str27 = seriesDBModel.getNum();
                }
                if (seriesDBModel.getName() != null) {
                    str28 = seriesDBModel.getName();
                }
                if (seriesDBModel.getStreamType() != null) {
                    str14 = seriesDBModel.getStreamType();
                }
                str11 = str14;
                if (seriesDBModel.getseriesID() != -1) {
                    i4 = seriesDBModel.getseriesID();
                }
                if (seriesDBModel.getcover() != null) {
                    str15 = seriesDBModel.getcover();
                }
                if (seriesDBModel.getplot() != null) {
                    str16 = seriesDBModel.getplot();
                }
                if (seriesDBModel.getcast() != null) {
                    str17 = seriesDBModel.getcast();
                }
                if (seriesDBModel.getdirector() != null) {
                    str18 = seriesDBModel.getdirector();
                }
                if (seriesDBModel.getgenre() != null) {
                    str19 = seriesDBModel.getgenre();
                }
                if (seriesDBModel.getreleaseDate() != null) {
                    str20 = seriesDBModel.getreleaseDate();
                }
                if (seriesDBModel.getlast_modified() != null) {
                    str21 = seriesDBModel.getlast_modified();
                }
                if (seriesDBModel.getrating() != null) {
                    str22 = seriesDBModel.getrating();
                }
                if (seriesDBModel.getCategoryId() != null) {
                    str23 = seriesDBModel.getCategoryId();
                }
                if (seriesDBModel.getYouTubeTrailer() != null) {
                    str24 = seriesDBModel.getYouTubeTrailer();
                }
                if (seriesDBModel.getBackdrop() != null) {
                    str25 = seriesDBModel.getBackdrop();
                }
                if (seriesDBModel.getSeasons() != null) {
                    str26 = seriesDBModel.getSeasons();
                }
                if (seriesDBModel.getLoginType() != null) {
                    str = seriesDBModel.getLoginType();
                    str12 = str16;
                    str10 = str17;
                    str9 = str18;
                    str8 = str19;
                    str7 = str20;
                    str6 = str21;
                    str5 = str22;
                    str13 = str23;
                    str4 = str24;
                    str3 = str25;
                    str2 = str26;
                } else {
                    str12 = str16;
                    str10 = str17;
                    str9 = str18;
                    str8 = str19;
                    str7 = str20;
                    str6 = str21;
                    str5 = str22;
                    str13 = str23;
                    str4 = str24;
                    str3 = str25;
                    str2 = str26;
                    str = "";
                }
                i2 = i4;
            }
            final String str29 = str15;
            this.loginPreferencesSharedPref = this.context.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_PLAYER, 0);
            this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SELECTED_PLAYER, "");
            if (i3 == 0 && myViewHolder2.Movie != null && !this.focused.booleanValue()) {
                this.focused = true;
                myViewHolder2.Movie.requestFocus();
            }
            this.pref = this.context.getSharedPreferences("listgridview", 0);
            this.editor = this.pref.edit();
            AppConst.LIVE_FLAG_SERIES = this.pref.getInt("series", 0);
            myViewHolder2.MovieName.setText(this.dataSet.get(i3).getName());
            myViewHolder2.MovieImage.setImageDrawable(null);
            if (str29 != null && !str29.equals("")) {
                Picasso.with(this.context).load(str29).placeholder((int) R.drawable.noposter).into(myViewHolder2.MovieImage);
            } else if (Build.VERSION.SDK_INT >= 21) {
                myViewHolder2.MovieImage.setImageDrawable(this.context.getResources().getDrawable(R.drawable.noposter, null));
            } else {
                myViewHolder2.MovieImage.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.noposter));
            }
            final String replace = str28.trim().replace("'", " ");
            if (this.database.checkFavourite(i2, str13, "series", SharepreferenceDBHandler.getUserID(this.context)).size() > 0) {
                myViewHolder2.ivFavourite.setVisibility(0);
            } else {
                myViewHolder2.ivFavourite.setVisibility(4);
            }
            String finalStr2 = str27;
            myViewHolder2.cardView.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.SeriesAdapter.AnonymousClass1 */

                public void onClick(View view) {
                    SeriesAdapter.this.startSeriesViewActivitit(finalStr2, replace, str11, i2, str29, str12, str10, str9, str8, str7, str6, str5, str13, str4, str3, str2, str);
                }
            });
            String finalStr21 = str27;
            myViewHolder.MovieImage.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.SeriesAdapter.AnonymousClass2 */

                public void onClick(View view) {
                    SeriesAdapter.this.startSeriesViewActivitit(finalStr21, replace, str11, i2, str29, str12, str10, str9, str8, str7, str6, str5, str13, str4, str3, str2, str);
                }
            });
            String finalStr22 = str27;
            myViewHolder.Movie.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.SeriesAdapter.AnonymousClass3 */

                public void onClick(View view) {
                    SeriesAdapter.this.startSeriesViewActivitit(finalStr22, replace, str11, i2, str29, str12, str10, str9, str8, str7, str6, str5, str13, str4, str3, str2, str);
                }
            });
            myViewHolder.Movie.setOnFocusChangeListener(new OnFocusChangeAccountListener(myViewHolder.Movie));
            String finalStr23 = str27;
            myViewHolder.Movie.setOnLongClickListener(new View.OnLongClickListener() {
                /* class com.nst.yourname.view.adapter.SeriesAdapter.AnonymousClass4 */

                public boolean onLongClick(View view) {
                    SeriesAdapter.this.popmenu(myViewHolder, i2, str13, replace, finalStr23, str);
                    return true;
                }
            });
            String finalStr24 = str27;
            myViewHolder.MovieImage.setOnLongClickListener(new View.OnLongClickListener() {
                /* class com.nst.yourname.view.adapter.SeriesAdapter.AnonymousClass5 */

                public boolean onLongClick(View view) {
                    SeriesAdapter.this.popmenu(myViewHolder, i2, str13, replace, finalStr24, str);
                    return true;
                }
            });
            String finalStr25 = str27;
            myViewHolder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                /* class com.nst.yourname.view.adapter.SeriesAdapter.AnonymousClass6 */

                public boolean onLongClick(View view) {
                    SeriesAdapter.this.popmenu(myViewHolder, i2, str13, replace, finalStr25, str);
                    return true;
                }
            });
            String finalStr26 = str27;
            myViewHolder.llMenu.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.SeriesAdapter.AnonymousClass7 */

                public void onClick(View view) {
                    SeriesAdapter.this.startSeriesViewActivitit(finalStr26, replace, str11, i2, str29, str12, str10, str9, str8, str7, str6, str5, str13, str4, str3, str2, str);
                }
            });
        }
    }

    public void startSeriesViewActivitit(String str, String str2, String str3, int i, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16) {
        if (this.context != null) {
            Intent intent = new Intent(this.context, SeriesDetailActivity.class);
            intent.putExtra(AppConst.SERIES_NUM, str);
            intent.putExtra(AppConst.SERIES_NAME, str2);
            intent.putExtra(AppConst.SERIES_STREAM_TYPE, str3);
            intent.putExtra(AppConst.SERIES_SERIES_ID, String.valueOf(i));
            intent.putExtra(AppConst.SERIES_COVER, str4);
            intent.putExtra(AppConst.SERIES_PLOT, str5);
            intent.putExtra(AppConst.SERIES_CAST, str6);
            intent.putExtra(AppConst.SERIES_DIRECTOR, str7);
            intent.putExtra(AppConst.SERIES_GENERE, str8);
            intent.putExtra(AppConst.SERIES_RELEASE_DATE, str9);
            intent.putExtra(AppConst.SERIES_LAST_MODIFIED, str10);
            intent.putExtra(AppConst.SERIES_RATING, str11);
            intent.putExtra(AppConst.SERIES_CATEGORY_ID, str12);
            intent.putExtra(AppConst.SERIES_YOU_TUBE_TRAILER, str13);
            intent.putExtra(AppConst.SERIES_BACKDROP, str14);
            this.context.startActivity(intent);
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

    private void startViewDeatilsActivity(int i, String str, String str2, String str3, String str4, String str5, String str6) {
        if (this.context != null) {
            Intent intent = new Intent(this.context, ViewDetailsActivity.class);
            intent.putExtra(AppConst.STREAM_ID, String.valueOf(i));
            intent.putExtra(AppConst.EVENT_TYPE_MOVIE, str);
            intent.putExtra(AppConst.LOGIN_PREF_SELECTED_PLAYER, str2);
            intent.putExtra("streamType", str3);
            intent.putExtra("containerExtension", str4);
            intent.putExtra("categoryID", str5);
            intent.putExtra(AppConst.LOGIN_PREF_OPENED_VIDEO_ID_NUM, str6);
            this.context.startActivity(intent);
            return;
        }
        Log.e("Null hai context", ">>>>>>>>>>>True its Null");
    }

    public void popmenu(final MyViewHolder myViewHolder, final int i, final String str, final String str2, final String str3, String str4) {
        PopupMenu popupMenu = new PopupMenu(this.context, myViewHolder.tvStreamOptions);
        popupMenu.inflate(R.menu.menu_card_series_streams);
        if (this.database.checkFavourite(i, str, "series", SharepreferenceDBHandler.getUserID(this.context)).size() > 0) {
            popupMenu.getMenu().getItem(1).setVisible(false);
        } else {
            popupMenu.getMenu().getItem(2).setVisible(false);
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            /* class com.nst.yourname.view.adapter.SeriesAdapter.AnonymousClass8 */

            @Override // android.support.v7.widget.PopupMenu.OnMenuItemClickListener
            public boolean onMenuItemClick(MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.menu_series_details) {
                    Seriesinfo();
                    return false;
                } else if (itemId == R.id.nav_add_to_fav) {
                    addToFavourite();
                    return false;
                } else if (itemId != R.id.nav_remove_from_fav) {
                    return false;
                } else {
                    removeFromFavourite();
                    return false;
                }
            }

            private void Seriesinfo() {
                myViewHolder.cardView.performClick();
            }

            private void addToFavourite() {
                FavouriteDBModel favouriteDBModel = new FavouriteDBModel();
                favouriteDBModel.setCategoryID(str);
                favouriteDBModel.setStreamID(i);
                favouriteDBModel.setName(str2);
                favouriteDBModel.setNum(str3);
                favouriteDBModel.setUserID(SharepreferenceDBHandler.getUserID(SeriesAdapter.this.context));
                SeriesAdapter.this.database.addToFavourite(favouriteDBModel, "series");
                myViewHolder.ivFavourite.setVisibility(0);
            }

            private void removeFromFavourite() {
                SeriesAdapter.this.database.deleteFavourite(i, str, "series", str2, SharepreferenceDBHandler.getUserID(SeriesAdapter.this.context));
                myViewHolder.ivFavourite.setVisibility(4);
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
            /* class com.nst.yourname.view.adapter.SeriesAdapter.AnonymousClass9 */

            public void run() {
                List unused = SeriesAdapter.this.filterList = new ArrayList();
                SeriesAdapter.this.text_size = str.length();
                if (SeriesAdapter.this.filterList != null) {
                    SeriesAdapter.this.filterList.clear();
                }
                if (TextUtils.isEmpty(str)) {
                    SeriesAdapter.this.filterList.addAll(SeriesAdapter.this.completeList);
                } else {
                    if (SeriesAdapter.this.dataSet.size() == 0 || SeriesAdapter.this.text_last_size > SeriesAdapter.this.text_size) {
                        List unused2 = SeriesAdapter.this.dataSet = SeriesAdapter.this.completeList;
                    }
                    for (SeriesDBModel seriesDBModel : SeriesAdapter.this.dataSet) {
                        if (seriesDBModel.getName().toLowerCase().contains(str.toLowerCase())) {
                            SeriesAdapter.this.filterList.add(seriesDBModel);
                        }
                    }
                }
                ((Activity) SeriesAdapter.this.context).runOnUiThread(new Runnable() {
                    /* class com.nst.yourname.view.adapter.SeriesAdapter.AnonymousClass9.AnonymousClass1 */

                    public void run() {
                        if (TextUtils.isEmpty(str)) {
                            List unused = SeriesAdapter.this.dataSet = SeriesAdapter.this.completeList;
                        } else if (!SeriesAdapter.this.filterList.isEmpty() || SeriesAdapter.this.filterList.isEmpty()) {
                            List unused2 = SeriesAdapter.this.dataSet = SeriesAdapter.this.filterList;
                        }
                        if (SeriesAdapter.this.dataSet.size() == 0) {
                            textView.setVisibility(0);
                        }
                        SeriesAdapter.this.text_last_size = SeriesAdapter.this.text_size;
                        SeriesAdapter.this.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
