package com.nst.yourname.view.adapter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.model.LiveStreamCategoryIdDBModel;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.M3UCategoriesModel;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.SeriesRecentWatchDatabase;
import com.nst.yourname.model.database.SeriesStreamsDatabaseHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.view.activity.SeriesActivitNewFlowSubCat;
import com.nst.yourname.view.activity.SeriesActivityNewFlowSubCategoriesM3U;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("ALL")
public class SeriesAdapterNewFlow extends RecyclerView.Adapter<SeriesAdapterNewFlow.MyViewHolder> {
    private int allCount = 0;
    public List<LiveStreamCategoryIdDBModel> completeList;
    public Context context;
    private DatabaseHandler dbHandeler;
    public List<LiveStreamCategoryIdDBModel> filterList = new ArrayList();
    public int focusedItem = 0;
    public LiveStreamDBHandler liveStreamDBHandler;
    public List<LiveStreamCategoryIdDBModel> moviesListl;
    public ProgressDialog progressDialog;
    private String selected_language = "";
    private SeriesRecentWatchDatabase seriesRecentWatchDatabase;
    private SeriesStreamsDatabaseHandler seriesStreamsDatabaseHandler;
    public int text_last_size;
    public int text_size;

    /*public class MyViewHolder_ViewBinding implements Unbinder {
        private MyViewHolder target;

        @UiThread
        public MyViewHolder_ViewBinding(MyViewHolder myViewHolder, View view) {
            this.target = myViewHolder;
            myViewHolder.ivTvIcon = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_tv_icon, "field 'ivTvIcon'", ImageView.class);
            myViewHolder.tvMovieCategoryName = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_movie_category_name, "field 'tvMovieCategoryName'", TextView.class);
            myViewHolder.ivForawardArrow = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_foraward_arrow, "field 'ivForawardArrow'", ImageView.class);
            myViewHolder.pbPagingLoader = (ProgressBar) Utils.findRequiredViewAsType(view, R.id.pb_paging_loader, "field 'pbPagingLoader'", ProgressBar.class);
            myViewHolder.rlListOfCategories = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_list_of_categories, "field 'rlListOfCategories'", RelativeLayout.class);
            myViewHolder.rlOuter = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_outer, "field 'rlOuter'", RelativeLayout.class);
            myViewHolder.tvXubCount = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_sub_cat_count, "field 'tvXubCount'", TextView.class);
        }

        @Override // butterknife.Unbinder
        @CallSuper
        public void unbind() {
            MyViewHolder myViewHolder = this.target;
            if (myViewHolder != null) {
                this.target = null;
                myViewHolder.ivTvIcon = null;
                myViewHolder.tvMovieCategoryName = null;
                myViewHolder.ivForawardArrow = null;
                myViewHolder.pbPagingLoader = null;
                myViewHolder.rlListOfCategories = null;
                myViewHolder.rlOuter = null;
                myViewHolder.tvXubCount = null;
                return;
            }
            throw new IllegalStateException("Bindings already cleared.");
        }
    }*/

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setOnKeyListener(new View.OnKeyListener() {
            /* class com.nst.yourname.view.adapter.SeriesAdapterNewFlow.AnonymousClass1 */

            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (keyEvent.getAction() != 0) {
                    return false;
                }
                if (i == 20) {
                    return SeriesAdapterNewFlow.this.tryMoveSelection(layoutManager, 1);
                }
                if (i == 19) {
                    return SeriesAdapterNewFlow.this.tryMoveSelection(layoutManager, -1);
                }
                return false;
            }
        });
    }

    public boolean tryMoveSelection(RecyclerView.LayoutManager layoutManager, int i) {
        int i2 = this.focusedItem + i;
        if (i2 < 0 || i2 >= getItemCount()) {
            return false;
        }
        notifyItemChanged(this.focusedItem);
        this.focusedItem = i2;
        notifyItemChanged(this.focusedItem);
        layoutManager.scrollToPosition(this.focusedItem);
        return true;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_foraward_arrow)
        ImageView ivForawardArrow;
        @BindView(R.id.iv_tv_icon)
        ImageView ivTvIcon;
        @BindView(R.id.pb_paging_loader)
        ProgressBar pbPagingLoader;
        @BindView(R.id.rl_list_of_categories)
        RelativeLayout rlListOfCategories;
        @BindView(R.id.rl_outer)
        RelativeLayout rlOuter;
        @BindView(R.id.tv_movie_category_name)
        TextView tvMovieCategoryName;
        @BindView(R.id.tv_sub_cat_count)
        TextView tvXubCount;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            setIsRecyclable(false);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: java.util.List<com.nst.yourname.model.LiveStreamCategoryIdDBModel>} */
    /* JADX WARNING: Multi-variable type inference failed */
    public SeriesAdapterNewFlow(List<LiveStreamCategoryIdDBModel> list, Context context2) {
        this.filterList.addAll(list);
        this.completeList = list;
        this.moviesListl = list;
        this.context = context2;
        this.liveStreamDBHandler = new LiveStreamDBHandler(context2);
        this.dbHandeler = new DatabaseHandler(context2);
        this.seriesStreamsDatabaseHandler = new SeriesStreamsDatabaseHandler(context2);
        this.seriesRecentWatchDatabase = new SeriesRecentWatchDatabase(context2);
        String str = SharepreferenceDBHandler.getseriesActivitynewFlowSort(context2);
        if (str.equals("1")) {
            Collections.sort(list, new Comparator<LiveStreamCategoryIdDBModel>() {
                /* class com.nst.yourname.view.adapter.SeriesAdapterNewFlow.AnonymousClass2 */

                public int compare(LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel, LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel2) {
                    return liveStreamCategoryIdDBModel.getLiveStreamCategoryName().compareTo(liveStreamCategoryIdDBModel2.getLiveStreamCategoryName());
                }
            });
        }
        if (str.equals("2")) {
            Collections.sort(list, new Comparator<LiveStreamCategoryIdDBModel>() {
                /* class com.nst.yourname.view.adapter.SeriesAdapterNewFlow.AnonymousClass3 */

                public int compare(LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel, LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel2) {
                    return liveStreamCategoryIdDBModel2.getLiveStreamCategoryName().compareTo(liveStreamCategoryIdDBModel.getLiveStreamCategoryName());
                }
            });
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
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate((int) R.layout.layout_vod_new_flow_list_item, viewGroup, false);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.iv_foraward_arrow);
        if (this.selected_language.equalsIgnoreCase("Arabic")) {
            imageView.setImageResource(R.drawable.left_icon_cat);
        }
        return new MyViewHolder(inflate);
    }

    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = this.moviesListl.get(i);
        final String liveStreamCategoryName = liveStreamCategoryIdDBModel.getLiveStreamCategoryName();
        final String liveStreamCategoryID = liveStreamCategoryIdDBModel.getLiveStreamCategoryID();
        Bundle bundle = new Bundle();
        bundle.putString(AppConst.CATEGORY_ID, liveStreamCategoryID);
        bundle.putString(AppConst.CATEGORY_NAME, liveStreamCategoryName);
        if (liveStreamCategoryName != null && !liveStreamCategoryName.equals("") && !liveStreamCategoryName.isEmpty()) {
            myViewHolder.tvMovieCategoryName.setText(liveStreamCategoryName);
        }
        myViewHolder.rlOuter.setOnFocusChangeListener(new OnFocusChangeAccountListener(myViewHolder.rlOuter));
        if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            if (liveStreamCategoryIdDBModel.getLiveStreamCategoryID().equals("-1")) {
                runAsyncTask(myViewHolder);
            } else {
                myViewHolder.tvXubCount.setText(String.valueOf(liveStreamCategoryIdDBModel.getLiveStreamCounter()));
            }
            if (i == 0) {
                this.allCount = liveStreamCategoryIdDBModel.getLiveStreamCounter();
            }
            if (!liveStreamCategoryID.equals("-1") && !liveStreamCategoryID.equals(AppConst.PASSWORD_UNSET)) {
                myViewHolder.rlOuter.setOnLongClickListener(new View.OnLongClickListener() {
                    /* class com.nst.yourname.view.adapter.SeriesAdapterNewFlow.AnonymousClass4 */

                    public boolean onLongClick(View view) {
                        SeriesAdapterNewFlow.this.popmenu(myViewHolder, liveStreamCategoryID, i);
                        return true;
                    }
                });
            }
        } else {
            int seriesCount = this.seriesStreamsDatabaseHandler.getSeriesCount(liveStreamCategoryIdDBModel.getLiveStreamCategoryID());
            if (seriesCount == 0 || seriesCount == -1) {
                myViewHolder.tvXubCount.setText("");
            } else {
                myViewHolder.tvXubCount.setText(String.valueOf(seriesCount));
            }
            if (liveStreamCategoryIdDBModel.getLiveStreamCategoryID().equalsIgnoreCase(AppConst.PASSWORD_UNSET)) {
                int allSeriesStreamCount = this.seriesStreamsDatabaseHandler.getAllSeriesStreamCount();
                if (allSeriesStreamCount == 0 || allSeriesStreamCount == -1) {
                    myViewHolder.tvXubCount.setText("");
                } else {
                    myViewHolder.tvXubCount.setText(String.valueOf(allSeriesStreamCount));
                }
            } else if (liveStreamCategoryIdDBModel.getLiveStreamCategoryID().equalsIgnoreCase("-1")) {
                int favouriteCount = this.dbHandeler.getFavouriteCount("series", SharepreferenceDBHandler.getUserID(this.context));
                if (favouriteCount == 0 || favouriteCount == -1) {
                    myViewHolder.tvXubCount.setText(AppConst.PASSWORD_UNSET);
                } else {
                    myViewHolder.tvXubCount.setText(String.valueOf(favouriteCount));
                }
            } else if (liveStreamCategoryIdDBModel.getLiveStreamCategoryID().equalsIgnoreCase("-4")) {
                int seriesRecentwatchmCount = this.seriesRecentWatchDatabase.getSeriesRecentwatchmCount();
                if (seriesRecentwatchmCount == 0 || seriesRecentwatchmCount == -1) {
                    myViewHolder.tvXubCount.setText(AppConst.PASSWORD_UNSET);
                } else {
                    myViewHolder.tvXubCount.setText(String.valueOf(seriesRecentwatchmCount));
                }
            }
        }
        if (this.context != null && (this.context.getResources().getConfiguration().screenLayout & 15) == 3 && i == this.focusedItem) {
            myViewHolder.rlOuter.requestFocus();
            performScaleXAnimation(1.09f, myViewHolder.rlOuter);
            performScaleYAnimation(1.09f, myViewHolder.rlOuter);
            myViewHolder.rlOuter.setBackgroundResource(R.drawable.shape_list_categories_focused);
        }
        myViewHolder.rlOuter.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.adapter.SeriesAdapterNewFlow.AnonymousClass5 */

            public void onClick(View view) {
                int unused = SeriesAdapterNewFlow.this.focusedItem = myViewHolder.getLayoutPosition();
                if (SharepreferenceDBHandler.getCurrentAPPType(SeriesAdapterNewFlow.this.context).equals(AppConst.TYPE_M3U)) {
                    Intent intent = new Intent(SeriesAdapterNewFlow.this.context, SeriesActivityNewFlowSubCategoriesM3U.class);
                    intent.putExtra(AppConst.CATEGORY_ID, liveStreamCategoryID);
                    intent.putExtra(AppConst.CATEGORY_NAME, liveStreamCategoryName);
                    SeriesAdapterNewFlow.this.context.startActivity(intent);
                    return;
                }
                Intent intent2 = new Intent(SeriesAdapterNewFlow.this.context, SeriesActivitNewFlowSubCat.class);
                intent2.putExtra(AppConst.CATEGORY_ID, liveStreamCategoryID);
                intent2.putExtra(AppConst.CATEGORY_NAME, liveStreamCategoryName);
                SeriesAdapterNewFlow.this.context.startActivity(intent2);
            }
        });
    }

    private void runAsyncTask(MyViewHolder myViewHolder) {
        new SeriesAdapterNewFlowAsync(myViewHolder).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, myViewHolder);
    }

    private void performScaleXAnimation(float f, RelativeLayout relativeLayout) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(relativeLayout, "scaleX", f);
        ofFloat.setDuration(150L);
        ofFloat.start();
    }

    private void performScaleYAnimation(float f, RelativeLayout relativeLayout) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(relativeLayout, "scaleY", f);
        ofFloat.setDuration(150L);
        ofFloat.start();
    }

    public void popmenu(MyViewHolder myViewHolder, final String str, final int i) {
        if (this.context != null) {
            PopupMenu popupMenu = new PopupMenu(this.context, myViewHolder.rlOuter);
            popupMenu.inflate(R.menu.menu_move_categories);
            popupMenu.getMenu().getItem(0).setVisible(true);
            popupMenu.getMenu().getItem(1).setVisible(true);
            popupMenu.getMenu().getItem(2).setVisible(false);
            popupMenu.getMenu().getItem(3).setVisible(false);
            popupMenu.getMenu().getItem(4).setVisible(false);
            popupMenu.getMenu().getItem(5).setVisible(true);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                /* class com.nst.yourname.view.adapter.SeriesAdapterNewFlow.AnonymousClass6 */

                public boolean onMenuItemClick(MenuItem menuItem) {
                    int itemId = menuItem.getItemId();
                    if (itemId != R.id.nav_remove_from_series) {
                        switch (itemId) {
                            case R.id.nav_move_to_live:
                                new AsyncTaskForMovingRecords().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "live_move");
                                break;
                            case R.id.nav_move_to_movie:
                                new AsyncTaskForMovingRecords().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "movie_move");
                                break;
                        }
                    } else {
                        new AsyncTaskForMovingRecords().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "series_remove");
                    }
                    return false;
                }

                public String moveToLive() {
                    ArrayList<LiveStreamsDBModel> allM3UWithCategoryId = SeriesAdapterNewFlow.this.liveStreamDBHandler.getAllM3UWithCategoryId(str, false);
                    if (str.equals(AppConst.PASSWORD_UNSET)) {
                        ArrayList<LiveStreamCategoryIdDBModel> allm3uCategories = SeriesAdapterNewFlow.this.liveStreamDBHandler.getAllm3uCategories();
                        if (allM3UWithCategoryId == null || allM3UWithCategoryId.size() <= 0) {
                            return "live_move";
                        }
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, "live");
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.addMultipleCategoriesM3U(allm3uCategories, "live");
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "live");
                        return "live_move";
                    }
                    if (!SeriesAdapterNewFlow.this.liveStreamDBHandler.checkCategoryExistsM3U(str, "live")) {
                        M3UCategoriesModel m3UCategoriesModel = new M3UCategoriesModel();
                        if (!str.equals("")) {
                            m3UCategoriesModel = SeriesAdapterNewFlow.this.liveStreamDBHandler.getCatByCatIDALLM3U(str);
                        }
                        if (allM3UWithCategoryId != null && allM3UWithCategoryId.size() > 0) {
                            SeriesAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                            SeriesAdapterNewFlow.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                            SeriesAdapterNewFlow.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                            SeriesAdapterNewFlow.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                            SeriesAdapterNewFlow.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                            SeriesAdapterNewFlow.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, "live");
                            if (!str.equals("")) {
                                SeriesAdapterNewFlow.this.liveStreamDBHandler.addLiveCategoriesM3U(m3UCategoriesModel);
                            }
                            SeriesAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "live");
                        }
                    } else if (allM3UWithCategoryId != null && allM3UWithCategoryId.size() > 0) {
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, "live");
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "live");
                    }
                    SeriesAdapterNewFlow.this.notifyAdapter(i);
                    return "live_move";
                }

                public String moveToMovie() {
                    ArrayList<LiveStreamsDBModel> allM3UWithCategoryId = SeriesAdapterNewFlow.this.liveStreamDBHandler.getAllM3UWithCategoryId(str, false);
                    if (str.equals(AppConst.PASSWORD_UNSET)) {
                        ArrayList<LiveStreamCategoryIdDBModel> allm3uCategories = SeriesAdapterNewFlow.this.liveStreamDBHandler.getAllm3uCategories();
                        if (allM3UWithCategoryId == null || allM3UWithCategoryId.size() <= 0) {
                            return "movie_move";
                        }
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, AppConst.EVENT_TYPE_MOVIE);
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.addMultipleCategoriesM3U(allm3uCategories, AppConst.EVENT_TYPE_MOVIE);
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, AppConst.EVENT_TYPE_MOVIE);
                        return "movie_move";
                    }
                    if (SeriesAdapterNewFlow.this.liveStreamDBHandler.checkCategoryExistsM3U(str, AppConst.EVENT_TYPE_MOVIE)) {
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, AppConst.EVENT_TYPE_MOVIE);
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, AppConst.EVENT_TYPE_MOVIE);
                    } else {
                        M3UCategoriesModel m3UCategoriesModel = new M3UCategoriesModel();
                        if (!str.equals("")) {
                            m3UCategoriesModel = SeriesAdapterNewFlow.this.liveStreamDBHandler.getCatByCatIDALLM3U(str);
                        }
                        if (allM3UWithCategoryId != null && allM3UWithCategoryId.size() > 0) {
                            SeriesAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                            SeriesAdapterNewFlow.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                            SeriesAdapterNewFlow.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                            SeriesAdapterNewFlow.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                            SeriesAdapterNewFlow.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                            SeriesAdapterNewFlow.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, AppConst.EVENT_TYPE_MOVIE);
                            if (!str.equals("")) {
                                SeriesAdapterNewFlow.this.liveStreamDBHandler.addMovieCategoriesM3U(m3UCategoriesModel);
                            }
                            SeriesAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, AppConst.EVENT_TYPE_MOVIE);
                        }
                    }
                    SeriesAdapterNewFlow.this.notifyAdapter(i);
                    return "movie_move";
                }

                public String removefromSeries() {
                    SeriesAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                    SeriesAdapterNewFlow.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                    if (!str.equals("")) {
                        SeriesAdapterNewFlow.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                    }
                    SeriesAdapterNewFlow.this.notifyAdapter(i);
                    return "series_remove";
                }

                @SuppressLint({"StaticFieldLeak"})
                /* renamed from: com.nst.yourname.view.adapter.SeriesAdapterNewFlow$6$AsyncTaskForMovingRecords */
                class AsyncTaskForMovingRecords extends AsyncTask<String, Void, String> {
                    AsyncTaskForMovingRecords() {
                    }

                    public void onPreExecute() {
                        super.onPreExecute();
                        showProgressDialogBox();
                    }

                    /* JADX WARNING: Removed duplicated region for block: B:20:0x0039 A[Catch:{ Exception -> 0x0051 }] */
                    /* JADX WARNING: Removed duplicated region for block: B:21:0x003a A[Catch:{ Exception -> 0x0051 }] */
                    /* JADX WARNING: Removed duplicated region for block: B:23:0x0041 A[Catch:{ Exception -> 0x0051 }] */
                    /* JADX WARNING: Removed duplicated region for block: B:25:0x0048 A[Catch:{ Exception -> 0x0051 }] */
                    public String doInBackground(String... strArr) {
                        char c = 0;
                        //try {
                            String str = strArr[0];
                            int hashCode = str.hashCode();
                            if (hashCode != -1829653632) {
                                if (hashCode != 1008793412) {
                                    if (hashCode == 1087050572) {
                                        if (str.equals("series_remove")) {
                                            c = 2;
                                            switch (c) {
                                                case 0:
                                                    return moveToLive();
                                                case 1:
                                                    return moveToMovie();
                                                case 2:
                                                    return removefromSeries();
                                                default:
                                                    return null;
                                            }
                                        }
                                    }
                                } else if (str.equals("live_move")) {
                                    switch (c) {
                                    }
                                }
                            } else if (str.equals("movie_move")) {
                                c = 1;
                                switch (c) {
                                }
                            }
                            c = 65535;
                            switch (c) {
                            }
                        /*} catch (Exception unused) {
                            return "error";
                        }*/
                        //ToDo: return statement...
                        return str;
                    }

                    /* JADX WARNING: Removed duplicated region for block: B:17:0x003f  */
                    /* JADX WARNING: Removed duplicated region for block: B:18:0x005e  */
                    /* JADX WARNING: Removed duplicated region for block: B:19:0x007d  */
                    public void onPostExecute(String str) {
                        char c;
                        super.onPostExecute((String) str);
                        hideProgressDialogBox();
                        int hashCode = str.hashCode();
                        if (hashCode != -1829653632) {
                            if (hashCode != 1008793412) {
                                if (hashCode == 1087050572 && str.equals("series_remove")) {
                                    c = 2;
                                    switch (c) {
                                        case 0:
                                            com.nst.yourname.miscelleneious.common.Utils.showToastLong(SeriesAdapterNewFlow.this.context, SeriesAdapterNewFlow.this.context.getResources().getString(R.string.added_to_live));
                                            break;
                                        case 1:
                                            com.nst.yourname.miscelleneious.common.Utils.showToastLong(SeriesAdapterNewFlow.this.context, SeriesAdapterNewFlow.this.context.getResources().getString(R.string.added_to_movies));
                                            break;
                                        case 2:
                                            com.nst.yourname.miscelleneious.common.Utils.showToastLong(SeriesAdapterNewFlow.this.context, SeriesAdapterNewFlow.this.context.getResources().getString(R.string.removed_from_series));
                                            break;
                                    }
                                    SeriesAdapterNewFlow.this.notifyDataSetChanged();
                                }
                            } else if (str.equals("live_move")) {
                                c = 0;
                                switch (c) {
                                }
                                SeriesAdapterNewFlow.this.notifyDataSetChanged();
                            }
                        } else if (str.equals("movie_move")) {
                            c = 1;
                            switch (c) {
                            }
                            SeriesAdapterNewFlow.this.notifyDataSetChanged();
                        }
                        c = 65535;
                        switch (c) {
                        }
                        SeriesAdapterNewFlow.this.notifyDataSetChanged();
                    }
                }

                public void showProgressDialogBox() {
                    if (SeriesAdapterNewFlow.this.context != null) {
                        ProgressDialog unused = SeriesAdapterNewFlow.this.progressDialog = new ProgressDialog(SeriesAdapterNewFlow.this.context);
                        SeriesAdapterNewFlow.this.progressDialog.setMessage(SeriesAdapterNewFlow.this.context.getResources().getString(R.string.please_wait));
                        SeriesAdapterNewFlow.this.progressDialog.setCanceledOnTouchOutside(false);
                        SeriesAdapterNewFlow.this.progressDialog.setCancelable(false);
                        SeriesAdapterNewFlow.this.progressDialog.setProgressStyle(0);
                        SeriesAdapterNewFlow.this.progressDialog.show();
                    }
                }

                public void hideProgressDialogBox() {
                    if (SeriesAdapterNewFlow.this.context != null && SeriesAdapterNewFlow.this.progressDialog != null) {
                        SeriesAdapterNewFlow.this.progressDialog.dismiss();
                    }
                }
            });
            popupMenu.show();
        }
    }

    public void notifyAdapter(int i) {
        if (this.moviesListl != null && this.moviesListl.size() > 0) {
            this.allCount -= this.moviesListl.get(i).getLiveStreamCounter();
            this.moviesListl.remove(i);
            LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
            liveStreamCategoryIdDBModel.setLiveStreamCategoryID(AppConst.PASSWORD_UNSET);
            liveStreamCategoryIdDBModel.setLiveStreamCounter(this.allCount);
            liveStreamCategoryIdDBModel.setLiveStreamCategoryName(this.context.getResources().getString(R.string.all));
            this.moviesListl.set(0, liveStreamCategoryIdDBModel);
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.moviesListl.size();
    }

    public void setVisibiltygone(ProgressBar progressBar) {
        if (progressBar != null) {
            progressBar.setVisibility(8);
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
                    f = 1.09f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                Log.e("id is", "" + this.view.getTag());
                this.view.setBackgroundResource(R.drawable.shape_list_categories_focused);
            } else if (!z) {
                if (z) {
                    f = 1.09f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                performAlphaAnimation(z);
                this.view.setBackgroundResource(R.drawable.shape_list_categories);
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

    public void filter(final String str, final TextView textView) {
        new Thread(new Runnable() {
            /* class com.nst.yourname.view.adapter.SeriesAdapterNewFlow.AnonymousClass7 */

            public void run() {
                List unused = SeriesAdapterNewFlow.this.filterList = new ArrayList();
                SeriesAdapterNewFlow.this.text_size = str.length();
                if (SeriesAdapterNewFlow.this.filterList != null) {
                    SeriesAdapterNewFlow.this.filterList.clear();
                }
                if (TextUtils.isEmpty(str)) {
                    SeriesAdapterNewFlow.this.filterList.addAll(SeriesAdapterNewFlow.this.completeList);
                } else {
                    if ((SeriesAdapterNewFlow.this.moviesListl != null && SeriesAdapterNewFlow.this.moviesListl.size() == 0) || SeriesAdapterNewFlow.this.text_last_size > SeriesAdapterNewFlow.this.text_size) {
                        List unused2 = SeriesAdapterNewFlow.this.moviesListl = SeriesAdapterNewFlow.this.completeList;
                    }
                    if (SeriesAdapterNewFlow.this.moviesListl != null) {
                        for (LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel : SeriesAdapterNewFlow.this.moviesListl) {
                            if (liveStreamCategoryIdDBModel.getLiveStreamCategoryName().toLowerCase().contains(str.toLowerCase())) {
                                SeriesAdapterNewFlow.this.filterList.add(liveStreamCategoryIdDBModel);
                            }
                        }
                    }
                }
                ((Activity) SeriesAdapterNewFlow.this.context).runOnUiThread(new Runnable() {
                    /* class com.nst.yourname.view.adapter.SeriesAdapterNewFlow.AnonymousClass7.AnonymousClass1 */

                    public void run() {
                        if (TextUtils.isEmpty(str)) {
                            List unused = SeriesAdapterNewFlow.this.moviesListl = SeriesAdapterNewFlow.this.completeList;
                        } else if (!SeriesAdapterNewFlow.this.filterList.isEmpty() || SeriesAdapterNewFlow.this.filterList.isEmpty()) {
                            List unused2 = SeriesAdapterNewFlow.this.moviesListl = SeriesAdapterNewFlow.this.filterList;
                        }
                        if (SeriesAdapterNewFlow.this.moviesListl != null && SeriesAdapterNewFlow.this.moviesListl.size() == 0) {
                            textView.setVisibility(0);
                            textView.setText(SeriesAdapterNewFlow.this.context.getResources().getString(R.string.no_record_found));
                        }
                        SeriesAdapterNewFlow.this.text_last_size = SeriesAdapterNewFlow.this.text_size;
                        SeriesAdapterNewFlow.this.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    @SuppressLint({"StaticFieldLeak"})
    private class SeriesAdapterNewFlowAsync extends AsyncTask<MyViewHolder, Void, Integer> {
        private MyViewHolder MyViewHolder;

        SeriesAdapterNewFlowAsync(MyViewHolder myViewHolder) {
            this.MyViewHolder = myViewHolder;
        }

        public void onPreExecute() {
            super.onPreExecute();
            this.MyViewHolder.tvXubCount.setVisibility(8);
        }

        public Integer doInBackground(MyViewHolder... myViewHolderArr) {
            try {
                return Integer.valueOf(SeriesAdapterNewFlow.this.liveStreamDBHandler.getFavouriteCountM3U("series"));
            } catch (Exception unused) {
                return 0;
            }
        }

        public void onPostExecute(Integer num) {
            super.onPostExecute((Integer) num);
            if (num.intValue() == 0 || num.intValue() == -1) {
                this.MyViewHolder.tvXubCount.setText(AppConst.PASSWORD_UNSET);
                this.MyViewHolder.tvXubCount.setVisibility(0);
                return;
            }
            this.MyViewHolder.tvXubCount.setText(String.valueOf(num));
            this.MyViewHolder.tvXubCount.setVisibility(0);
        }
    }
}
