package com.nst.yourname.view.adapter;

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
import com.nst.yourname.model.callback.SeriesDBModel;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.view.activity.SeriesDetailActivity;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class SeriesStreamsAdapter extends RecyclerView.Adapter<SeriesStreamsAdapter.MyViewHolder> {
    private static SharedPreferences loginPreferencesSharedPref_time_format;
    public List<SeriesDBModel> completeList;
    public Context context;
    public List<SeriesDBModel> dataSet;
    public DatabaseHandler database;
    public List<SeriesDBModel> filterList = new ArrayList();
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

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: java.util.List<com.nst.yourname.model.callback.SeriesDBModel>} */
    /* JADX WARNING: Multi-variable type inference failed */
    public SeriesStreamsAdapter(List<SeriesDBModel> list, Context context2) {
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
            this.myViewHolder = new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate((int) R.layout.live_streams_layout, viewGroup, false));
            return this.myViewHolder;
        }
        this.myViewHolder = new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate((int) R.layout.live_streams_linear_layout, viewGroup, false));
        return this.myViewHolder;
    }

    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder2, int i) {
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
        final int i2;
        final String str11;
        MyViewHolder myViewHolder3;
        MyViewHolder myViewHolder4 = myViewHolder2;
        int i3 = i;
        if (this.context != null) {
            String str12 = "";
            String str13 = "";
            String str14 = "";
            String str15 = "";
            String str16 = "";
            String str17 = "";
            String str18 = "";
            String str19 = "";
            String str20 = "";
            String str21 = "";
            String str22 = "";
            int i4 = -1;
            if (this.dataSet != null) {
                SeriesDBModel seriesDBModel = this.dataSet.get(i3);
                if (seriesDBModel.getNum() != null) {
                    str22 = seriesDBModel.getNum();
                }
                if (seriesDBModel.getName() != null) {
                    str12 = seriesDBModel.getName();
                }
                if (seriesDBModel.getStreamType() != null) {
                    str13 = seriesDBModel.getStreamType();
                }
                str8 = str12;
                if (seriesDBModel.getseriesID() != -1) {
                    i4 = seriesDBModel.getseriesID();
                }
                if (seriesDBModel.getcover() != null) {
                    str14 = seriesDBModel.getcover();
                }
                if (seriesDBModel.getplot() != null) {
                    str15 = seriesDBModel.getplot();
                }
                if (seriesDBModel.getcast() != null) {
                    str16 = seriesDBModel.getcast();
                }
                if (seriesDBModel.getdirector() != null) {
                    str17 = seriesDBModel.getdirector();
                }
                if (seriesDBModel.getgenre() != null) {
                    str18 = seriesDBModel.getgenre();
                }
                if (seriesDBModel.getreleaseDate() != null) {
                    str19 = seriesDBModel.getreleaseDate();
                }
                if (seriesDBModel.getlast_modified() != null) {
                    str20 = seriesDBModel.getlast_modified();
                }
                if (seriesDBModel.getrating() != null) {
                    str21 = seriesDBModel.getrating();
                }
                if (seriesDBModel.getCategoryId() != null) {
                    String categoryId = seriesDBModel.getCategoryId();
                    i2 = i4;
                    str9 = str13;
                    str10 = str14;
                    str7 = str15;
                    str6 = str16;
                    str5 = str17;
                    str4 = str18;
                    str3 = str19;
                    str2 = str20;
                    str = str21;
                    str11 = categoryId;
                } else {
                    str9 = str13;
                    str10 = str14;
                    str7 = str15;
                    str6 = str16;
                    str5 = str17;
                    str4 = str18;
                    str3 = str19;
                    str2 = str20;
                    str = str21;
                    str11 = "";
                    i2 = i4;
                }
            } else {
                str8 = str12;
                str9 = str13;
                str10 = str14;
                str7 = str15;
                str6 = str16;
                str5 = str17;
                str4 = str18;
                str3 = str19;
                str2 = str20;
                str = str21;
                str11 = "";
                i2 = -1;
            }
            this.loginPreferencesSharedPref = this.context.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_PLAYER, 0);
            myViewHolder4.tvTime.setText("");
            myViewHolder4.progressBar.setVisibility(8);
            myViewHolder4.tvCurrentLive.setText("");
            myViewHolder4.tvChannelName.setText(this.dataSet.get(i3).getName());
            myViewHolder4.ivChannelLogo.setImageDrawable(null);
            if (str10 != null && !str10.equals("")) {
                Picasso.with(this.context).load(str10).placeholder((int) R.drawable.noposter).into(myViewHolder4.ivChannelLogo);
            } else if (Build.VERSION.SDK_INT >= 21) {
                myViewHolder4.ivChannelLogo.setImageDrawable(this.context.getResources().getDrawable(R.drawable.noposter, null));
            } else {
                myViewHolder4.ivChannelLogo.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.noposter));
            }
            String finalStr2 = str22;
            myViewHolder4.cardView.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.SeriesStreamsAdapter.AnonymousClass1 */

                public void onClick(View view) {
                    SeriesStreamsAdapter.this.startSeriesViewActivitit(finalStr2, str8, str9, i2, str10, str7, str6, str5, str4, str3, str2, str, str11);
                }
            });
            String finalStr21 = str22;
            myViewHolder2.rlMovieImage.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.SeriesStreamsAdapter.AnonymousClass2 */

                public void onClick(View view) {
                    SeriesStreamsAdapter.this.startSeriesViewActivitit(finalStr21, str8, str9, i2, str10, str7, str6, str5, str4, str3, str2, str, str11);
                }
            });
            String finalStr22 = str22;
            myViewHolder2.rlStreamsLayout.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.SeriesStreamsAdapter.AnonymousClass3 */

                public void onClick(View view) {
                    SeriesStreamsAdapter.this.startSeriesViewActivitit(finalStr22, str8, str9, i2, str10, str7, str6, str5, str4, str3, str2, str, str11);
                }
            });
            final String str23 = str11;
            final int i5 = i2;
            ArrayList<FavouriteDBModel> checkFavourite = this.database.checkFavourite(i5, str23, "series", SharepreferenceDBHandler.getUserID(this.context));
            if (checkFavourite == null || checkFavourite.size() <= 0) {
                myViewHolder3 = myViewHolder2;
                myViewHolder3.ivFavourite.setVisibility(4);
            } else {
                myViewHolder3 = myViewHolder2;
                myViewHolder3.ivFavourite.setVisibility(0);
            }
            String finalStr24 = str22;
            myViewHolder3.rlStreamsLayout.setOnLongClickListener(new View.OnLongClickListener() {
                /* class com.nst.yourname.view.adapter.SeriesStreamsAdapter.AnonymousClass4 */

                public boolean onLongClick(View view) {
                    SeriesStreamsAdapter.this.popmenu(myViewHolder2, i5, str23, str8, finalStr24);
                    return true;
                }
            });
            String finalStr23 = str22;
            myViewHolder3.rlMovieImage.setOnLongClickListener(new View.OnLongClickListener() {
                /* class com.nst.yourname.view.adapter.SeriesStreamsAdapter.AnonymousClass5 */

                public boolean onLongClick(View view) {
                    SeriesStreamsAdapter.this.popmenu(myViewHolder2, i5, str23, str8, finalStr23);
                    return true;
                }
            });
            String finalStr25 = str22;
            myViewHolder3.llMenu.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.SeriesStreamsAdapter.AnonymousClass6 */

                public void onClick(View view) {
                    SeriesStreamsAdapter.this.popmenu(myViewHolder2, i5, str23, str8, finalStr25);
                }
            });
        }
    }

    public void startSeriesViewActivitit(String str, String str2, String str3, int i, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12) {
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
            this.context.startActivity(intent);
        }
    }

    public void popmenu(final MyViewHolder myViewHolder2, final int i, final String str, final String str2, final String str3) {
        PopupMenu popupMenu = new PopupMenu(this.context, myViewHolder2.tvStreamOptions);
        popupMenu.inflate(R.menu.menu_card_series_streams);
        if (this.database.checkFavourite(i, str, "series", SharepreferenceDBHandler.getUserID(this.context)).size() > 0) {
            popupMenu.getMenu().getItem(2).setVisible(false);
        } else {
            popupMenu.getMenu().getItem(1).setVisible(false);
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            /* class com.nst.yourname.view.adapter.SeriesStreamsAdapter.AnonymousClass7 */

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
                myViewHolder2.cardView.performClick();
            }

            private void addToFavourite() {
                FavouriteDBModel favouriteDBModel = new FavouriteDBModel();
                favouriteDBModel.setCategoryID(str);
                favouriteDBModel.setStreamID(i);
                favouriteDBModel.setName(str2);
                favouriteDBModel.setNum(str3);
                favouriteDBModel.setUserID(SharepreferenceDBHandler.getUserID(SeriesStreamsAdapter.this.context));
                SeriesStreamsAdapter.this.database.addToFavourite(favouriteDBModel, "series");
                myViewHolder2.ivFavourite.setVisibility(0);
            }

            private void removeFromFavourite() {
                SeriesStreamsAdapter.this.database.deleteFavourite(i, str, "series", str2, SharepreferenceDBHandler.getUserID(SeriesStreamsAdapter.this.context));
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
            /* class com.nst.yourname.view.adapter.SeriesStreamsAdapter.AnonymousClass8 */

            public void run() {
                List unused = SeriesStreamsAdapter.this.filterList = new ArrayList();
                SeriesStreamsAdapter.this.text_size = str.length();
                if (SeriesStreamsAdapter.this.filterList != null) {
                    SeriesStreamsAdapter.this.filterList.clear();
                }
                if (TextUtils.isEmpty(str)) {
                    SeriesStreamsAdapter.this.filterList.addAll(SeriesStreamsAdapter.this.completeList);
                } else {
                    if ((SeriesStreamsAdapter.this.dataSet != null && SeriesStreamsAdapter.this.dataSet.size() == 0) || SeriesStreamsAdapter.this.text_last_size > SeriesStreamsAdapter.this.text_size) {
                        List unused2 = SeriesStreamsAdapter.this.dataSet = SeriesStreamsAdapter.this.completeList;
                    }
                    if (SeriesStreamsAdapter.this.dataSet != null) {
                        for (SeriesDBModel seriesDBModel : SeriesStreamsAdapter.this.dataSet) {
                            if (seriesDBModel.getName() != null && seriesDBModel.getName().toLowerCase().contains(str.toLowerCase())) {
                                SeriesStreamsAdapter.this.filterList.add(seriesDBModel);
                            }
                        }
                    }
                }
                ((Activity) SeriesStreamsAdapter.this.context).runOnUiThread(new Runnable() {
                    /* class com.nst.yourname.view.adapter.SeriesStreamsAdapter.AnonymousClass8.AnonymousClass1 */

                    public void run() {
                        if (TextUtils.isEmpty(str)) {
                            List unused = SeriesStreamsAdapter.this.dataSet = SeriesStreamsAdapter.this.completeList;
                        } else if (!SeriesStreamsAdapter.this.filterList.isEmpty() || SeriesStreamsAdapter.this.filterList.isEmpty()) {
                            List unused2 = SeriesStreamsAdapter.this.dataSet = SeriesStreamsAdapter.this.filterList;
                        }
                        if (SeriesStreamsAdapter.this.dataSet != null && SeriesStreamsAdapter.this.dataSet.size() == 0) {
                            textView.setVisibility(0);
                        }
                        SeriesStreamsAdapter.this.text_last_size = SeriesStreamsAdapter.this.text_size;
                        SeriesStreamsAdapter.this.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
