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
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.view.activity.ViewDetailsActivity;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class SubCategoriesChildAdapter extends RecyclerView.Adapter<SubCategoriesChildAdapter.MyViewHolder> {
    public List<LiveStreamsDBModel> completeList;
    public Context context;
    public List<LiveStreamsDBModel> dataSet;
    DatabaseHandler database;
    private SharedPreferences.Editor editor;
    public List<LiveStreamsDBModel> filterList = new ArrayList();
    public LiveStreamsDBModel liveStreamsDBModel;
    private SharedPreferences loginPreferencesSharedPref;

    /*public class MyViewHolder_ViewBinding implements Unbinder {
        private MyViewHolder target;

        @UiThread
        public MyViewHolder_ViewBinding(MyViewHolder myViewHolder, View view) {
            this.target = myViewHolder;
            myViewHolder.MovieName = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_movie_name, "field 'MovieName'", TextView.class);
            myViewHolder.Movie = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_movie, "field 'Movie'", RelativeLayout.class);
            myViewHolder.movieNameTV = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_movie_name1, "field 'movieNameTV'", TextView.class);
            myViewHolder.MovieImage = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_movie_image, "field 'MovieImage'", ImageView.class);
            myViewHolder.cardView = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.card_view, "field 'cardView'", RelativeLayout.class);
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
                myViewHolder.movieNameTV = null;
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
        RelativeLayout cardView;
        @BindView(R.id.iv_favourite)
        ImageView ivFavourite;
        @BindView(R.id.ll_menu)
        LinearLayout llMenu;
        @BindView(R.id.tv_movie_name1)
        TextView movieNameTV;
        @BindView(R.id.tv_streamOptions)
        TextView tvStreamOptions;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            setIsRecyclable(false);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: java.util.List<com.nst.yourname.model.LiveStreamsDBModel>} */
    /* JADX WARNING: Multi-variable type inference failed */
    public SubCategoriesChildAdapter(List<LiveStreamsDBModel> list, Context context2) {
        this.dataSet = list;
        this.context = context2;
        this.filterList.addAll(list);
        this.completeList = list;
        this.database = new DatabaseHandler(context2);
        this.liveStreamsDBModel = this.liveStreamsDBModel;
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
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate((int) R.layout.layout_subcateories_cihild_list_item, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        int i2;
        MyViewHolder myViewHolder2 = myViewHolder;
        int i3 = i;
        if (this.context != null) {
            Context context2 = this.context;
            Context context3 = this.context;
            this.loginPreferencesSharedPref = context2.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_PLAYER, 0);
            final String string = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SELECTED_PLAYER, "");
            try {
                i2 = Integer.parseInt(this.dataSet.get(i3).getStreamId());
            } catch (NumberFormatException unused) {
                i2 = -1;
            }
            final String categoryId = this.dataSet.get(i3).getCategoryId();
            final String contaiinerExtension = this.dataSet.get(i3).getContaiinerExtension();
            final String streamType = this.dataSet.get(i3).getStreamType();
            final String num = this.dataSet.get(i3).getNum();
            myViewHolder2.MovieName.setText(this.dataSet.get(i3).getName());
            myViewHolder2.movieNameTV.setText(this.dataSet.get(i3).getName());
            String streamIcon = this.dataSet.get(i3).getStreamIcon();
            final String name = this.dataSet.get(i3).getName();
            myViewHolder2.MovieImage.setImageDrawable(null);
            if (streamIcon != null && !streamIcon.equals("")) {
                Picasso.with(this.context).load(this.dataSet.get(i3).getStreamIcon()).placeholder((int) R.drawable.noposter).into(myViewHolder2.MovieImage);
            } else if (Build.VERSION.SDK_INT >= 21) {
                myViewHolder2.MovieImage.setImageDrawable(this.context.getResources().getDrawable(R.drawable.noposter, null));
            } else {
                myViewHolder2.MovieImage.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.noposter));
            }
            if (this.database.checkFavourite(i2, categoryId, AppConst.VOD, SharepreferenceDBHandler.getUserID(this.context)).size() > 0) {
                myViewHolder2.ivFavourite.setVisibility(0);
            } else {
                myViewHolder2.ivFavourite.setVisibility(4);
            }
            int finalI5 = i2;
            myViewHolder2.cardView.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.SubCategoriesChildAdapter.AnonymousClass1 */

                public void onClick(View view) {
                    com.nst.yourname.miscelleneious.common.Utils.playWithPlayerVOD(SubCategoriesChildAdapter.this.context, string, finalI5, streamType, contaiinerExtension, num, name, "");
                }
            });
            int finalI = i2;
            myViewHolder2.MovieImage.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.SubCategoriesChildAdapter.AnonymousClass2 */

                public void onClick(View view) {
                    SubCategoriesChildAdapter.this.startViewDeatilsActivity(finalI, name, string, streamType, contaiinerExtension, categoryId, num);
                }
            });
            int finalI1 = i2;
            myViewHolder2.Movie.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.SubCategoriesChildAdapter.AnonymousClass3 */

                public void onClick(View view) {
                    SubCategoriesChildAdapter.this.startViewDeatilsActivity(finalI1, name, string, streamType, contaiinerExtension, categoryId, num);
                }
            });
            int finalI2 = i2;
            myViewHolder2.Movie.setOnLongClickListener(new View.OnLongClickListener() {
                /* class com.nst.yourname.view.adapter.SubCategoriesChildAdapter.AnonymousClass4 */

                public boolean onLongClick(View view) {
                    SubCategoriesChildAdapter.this.popmenu(myViewHolder, finalI2, categoryId, name, string, streamType, contaiinerExtension, num);
                    return true;
                }
            });
            int finalI3 = i2;
            myViewHolder2.MovieImage.setOnLongClickListener(new View.OnLongClickListener() {
                /* class com.nst.yourname.view.adapter.SubCategoriesChildAdapter.AnonymousClass5 */

                public boolean onLongClick(View view) {
                    SubCategoriesChildAdapter.this.popmenu(myViewHolder, finalI3, categoryId, name, string, streamType, contaiinerExtension, num);
                    return true;
                }
            });
            int finalI4 = i2;
            myViewHolder2.llMenu.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.SubCategoriesChildAdapter.AnonymousClass6 */

                public void onClick(View view) {
                    SubCategoriesChildAdapter.this.popmenu(myViewHolder, finalI4, categoryId, name, string, streamType, contaiinerExtension, num);
                }
            });
        }
    }

    public void startViewDeatilsActivity(int i, String str, String str2, String str3, String str4, String str5, String str6) {
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
        }
    }

    public void popmenu(final MyViewHolder myViewHolder, final int i, final String str, final String str2, final String str3, final String str4, final String str5, final String str6) {
        PopupMenu popupMenu = new PopupMenu(this.context, myViewHolder.tvStreamOptions);
        popupMenu.inflate(R.menu.menu_card_vod);
        if (this.database.checkFavourite(i, str, AppConst.VOD, SharepreferenceDBHandler.getUserID(this.context)).size() > 0) {
            popupMenu.getMenu().getItem(4).setVisible(true);
        } else {
            popupMenu.getMenu().getItem(3).setVisible(true);
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            /* class com.nst.yourname.view.adapter.SubCategoriesChildAdapter.AnonymousClass7 */

            @Override // android.support.v7.widget.PopupMenu.OnMenuItemClickListener
            public boolean onMenuItemClick(MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.menu_view_details) {
                    startViewDeatilsActivity(i, str2, str3, str4, str5, str, str6);
                    return false;
                } else if (itemId == R.id.nav_add_to_fav) {
                    addToFavourite();
                    return false;
                } else if (itemId == R.id.nav_play) {
                    playMovie();
                    return false;
                } else if (itemId != R.id.nav_remove_from_fav) {
                    return false;
                } else {
                    removeFromFavourite();
                    return false;
                }
            }

            private void startViewDeatilsActivity(int i, String str, String str2, String str3, String str4, String str5, String str6) {
                if (SubCategoriesChildAdapter.this.context != null) {
                    Intent intent = new Intent(SubCategoriesChildAdapter.this.context, ViewDetailsActivity.class);
                    intent.putExtra(AppConst.STREAM_ID, String.valueOf(i));
                    intent.putExtra(AppConst.EVENT_TYPE_MOVIE, str);
                    intent.putExtra(AppConst.LOGIN_PREF_SELECTED_PLAYER, str2);
                    intent.putExtra("streamType", str3);
                    intent.putExtra("containerExtension", str4);
                    intent.putExtra("categoryID", str5);
                    intent.putExtra(AppConst.LOGIN_PREF_OPENED_VIDEO_ID_NUM, str6);
                    SubCategoriesChildAdapter.this.context.startActivity(intent);
                }
            }

            private void playMovie() {
                myViewHolder.cardView.performClick();
            }

            private void addToFavourite() {
                FavouriteDBModel favouriteDBModel = new FavouriteDBModel();
                favouriteDBModel.setCategoryID(str);
                favouriteDBModel.setStreamID(i);
                SubCategoriesChildAdapter.this.liveStreamsDBModel.setName(str2);
                SubCategoriesChildAdapter.this.liveStreamsDBModel.setNum(str6);
                favouriteDBModel.setUserID(SharepreferenceDBHandler.getUserID(SubCategoriesChildAdapter.this.context));
                SubCategoriesChildAdapter.this.database.addToFavourite(favouriteDBModel, AppConst.VOD);
                myViewHolder.ivFavourite.setVisibility(0);
            }

            private void removeFromFavourite() {
                SubCategoriesChildAdapter.this.database.deleteFavourite(i, str, AppConst.VOD, str2, SharepreferenceDBHandler.getUserID(SubCategoriesChildAdapter.this.context));
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
            /* class com.nst.yourname.view.adapter.SubCategoriesChildAdapter.AnonymousClass8 */

            public void run() {
                List unused = SubCategoriesChildAdapter.this.filterList = new ArrayList();
                if (SubCategoriesChildAdapter.this.filterList != null) {
                    SubCategoriesChildAdapter.this.filterList.clear();
                }
                if (TextUtils.isEmpty(str)) {
                    SubCategoriesChildAdapter.this.filterList.addAll(SubCategoriesChildAdapter.this.completeList);
                } else {
                    for (LiveStreamsDBModel liveStreamsDBModel : SubCategoriesChildAdapter.this.dataSet) {
                        if (liveStreamsDBModel.getName().toLowerCase().contains(str.toLowerCase())) {
                            SubCategoriesChildAdapter.this.filterList.add(liveStreamsDBModel);
                        }
                    }
                }
                ((Activity) SubCategoriesChildAdapter.this.context).runOnUiThread(new Runnable() {
                    /* class com.nst.yourname.view.adapter.SubCategoriesChildAdapter.AnonymousClass8.AnonymousClass1 */

                    public void run() {
                        if (TextUtils.isEmpty(str)) {
                            List unused = SubCategoriesChildAdapter.this.dataSet = SubCategoriesChildAdapter.this.completeList;
                        } else if (!SubCategoriesChildAdapter.this.filterList.isEmpty() || SubCategoriesChildAdapter.this.filterList.isEmpty()) {
                            List unused2 = SubCategoriesChildAdapter.this.dataSet = SubCategoriesChildAdapter.this.filterList;
                        }
                        if (SubCategoriesChildAdapter.this.dataSet.size() == 0) {
                            textView.setVisibility(0);
                        }
                        SubCategoriesChildAdapter.this.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
