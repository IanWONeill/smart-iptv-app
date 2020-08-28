package com.nst.yourname.view.adapter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("all")
public class LiveAdapterNewFlow extends RecyclerView.Adapter<LiveAdapterNewFlow.MyViewHolder> {
    private AsyncTask AsyncTaskLiveAdapterNewFlow = null;
    private int allCount = 0;
    public List<LiveStreamCategoryIdDBModel> completeList;
    public Context context;
    public DatabaseHandler dbHandeler;
    public List<LiveStreamCategoryIdDBModel> filterList = new ArrayList();
    private boolean firstTimeFlag = true;
    public int focusedItem = 0;
    public LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesSharedPref;
    public List<LiveStreamCategoryIdDBModel> moviesListl;
    public ProgressDialog progressDialog;
    public int text_last_size;
    public int text_size;

    /*public class MyViewHolder_ViewBinding implements Unbinder {
        private MyViewHolder target;

        @UiThread
        public MyViewHolder_ViewBinding(MyViewHolder myViewHolder, View view) {
            this.target = myViewHolder;
            myViewHolder.tvMovieCategoryName = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_movie_category_name, "field 'tvMovieCategoryName'", TextView.class);
            myViewHolder.pbPagingLoader = (ProgressBar) Utils.findRequiredViewAsType(view, R.id.pb_paging_loader, "field 'pbPagingLoader'", ProgressBar.class);
            myViewHolder.rlOuter = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_outer, "field 'rlOuter'", RelativeLayout.class);
            myViewHolder.rlListOfCategories = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_list_of_categories, "field 'rlListOfCategories'", RelativeLayout.class);
            myViewHolder.testing = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.testing, "field 'testing'", RelativeLayout.class);
            myViewHolder.tvXubCount = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_sub_cat_count, "field 'tvXubCount'", TextView.class);
            myViewHolder.iv_foraward_arrow = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_foraward_arrow, "field 'iv_foraward_arrow'", ImageView.class);
        }

        @Override // butterknife.Unbinder
        @CallSuper
        public void unbind() {
            MyViewHolder myViewHolder = this.target;
            if (myViewHolder != null) {
                this.target = null;
                myViewHolder.tvMovieCategoryName = null;
                myViewHolder.pbPagingLoader = null;
                myViewHolder.rlOuter = null;
                myViewHolder.rlListOfCategories = null;
                myViewHolder.testing = null;
                myViewHolder.tvXubCount = null;
                myViewHolder.iv_foraward_arrow = null;
                return;
            }
            throw new IllegalStateException("Bindings already cleared.");
        }
    }*/

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setOnKeyListener(new View.OnKeyListener() {
            /* class com.nst.yourname.view.adapter.LiveAdapterNewFlow.AnonymousClass1 */

            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (keyEvent.getAction() != 0) {
                    return false;
                }
                if (i == 20) {
                    return LiveAdapterNewFlow.this.tryMoveSelection(layoutManager, 1);
                }
                if (i == 19) {
                    return LiveAdapterNewFlow.this.tryMoveSelection(layoutManager, -1);
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
        ImageView iv_foraward_arrow;
        @BindView(R.id.pb_paging_loader)
        ProgressBar pbPagingLoader;
        @BindView(R.id.rl_list_of_categories)
        RelativeLayout rlListOfCategories;
        @BindView(R.id.rl_outer)
        RelativeLayout rlOuter;
        @BindView(R.id.testing)
        RelativeLayout testing;
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
    public LiveAdapterNewFlow(List<LiveStreamCategoryIdDBModel> list, Context context2) {
        this.filterList.addAll(list);
        this.completeList = list;
        this.moviesListl = list;
        this.context = context2;
        this.liveStreamDBHandler = new LiveStreamDBHandler(context2);
        this.dbHandeler = new DatabaseHandler(context2);
        String liveActivitynewFlowSort = SharepreferenceDBHandler.getLiveActivitynewFlowSort(context2);
        if (liveActivitynewFlowSort.equals("1")) {
            Collections.sort(list, new Comparator<LiveStreamCategoryIdDBModel>() {
                /* class com.nst.yourname.view.adapter.LiveAdapterNewFlow.AnonymousClass2 */

                public int compare(LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel, LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel2) {
                    return liveStreamCategoryIdDBModel.getLiveStreamCategoryName().compareTo(liveStreamCategoryIdDBModel2.getLiveStreamCategoryName());
                }
            });
        }
        if (liveActivitynewFlowSort.equals("2")) {
            Collections.sort(list, new Comparator<LiveStreamCategoryIdDBModel>() {
                /* class com.nst.yourname.view.adapter.LiveAdapterNewFlow.AnonymousClass3 */

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
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate((int) R.layout.layout_live_new_flow_list_item, viewGroup, false);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.iv_foraward_arrow);
        if (SharepreferenceDBHandler.getSelectedLanguage(this.context).equalsIgnoreCase("Arabic")) {
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
        if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            if (liveStreamCategoryIdDBModel.getLiveStreamCategoryID().equals("-1")) {
                runAsyncTask(myViewHolder);
            } else {
                myViewHolder.tvXubCount.setText(String.valueOf(liveStreamCategoryIdDBModel.getLiveStreamCounter()));
            }
            if (liveStreamCategoryID.equals(AppConst.PASSWORD_UNSET)) {
                this.allCount = liveStreamCategoryIdDBModel.getLiveStreamCounter();
            }
            if (!liveStreamCategoryID.equals("-1") && !liveStreamCategoryID.equals(AppConst.PASSWORD_UNSET)) {
                myViewHolder.rlOuter.setOnLongClickListener(new View.OnLongClickListener() {
                    /* class com.nst.yourname.view.adapter.LiveAdapterNewFlow.AnonymousClass4 */

                    public boolean onLongClick(View view) {
                        LiveAdapterNewFlow.this.popmenu(myViewHolder, liveStreamCategoryID, i);
                        return true;
                    }
                });
            }
        } else {
            if (liveStreamCategoryIdDBModel.getLiveStreamCategoryID().equals("-1")) {
                runAsyncTask(myViewHolder);
            } else {
                myViewHolder.tvXubCount.setText(String.valueOf(liveStreamCategoryIdDBModel.getLiveStreamCounter()));
            }
            if (liveStreamCategoryID.equals(AppConst.PASSWORD_UNSET)) {
                int streamsCount = this.liveStreamDBHandler.getStreamsCount("live");
                if (streamsCount == 0 || streamsCount == -1) {
                    myViewHolder.tvXubCount.setText("");
                } else {
                    myViewHolder.tvXubCount.setText(String.valueOf(streamsCount));
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
            /* class com.nst.yourname.view.adapter.LiveAdapterNewFlow.AnonymousClass5 */

            public void onClick(View view) {
                int unused = LiveAdapterNewFlow.this.focusedItem = myViewHolder.getLayoutPosition();
                com.nst.yourname.miscelleneious.common.Utils.playWithVlcPlayer(LiveAdapterNewFlow.this.context, "Built-in Player ( Default )", -1, "live", AppConst.PASSWORD_UNSET, "", "", "", liveStreamCategoryID, "", liveStreamCategoryName);
            }
        });
        myViewHolder.rlOuter.setOnFocusChangeListener(new OnFocusChangeAccountListener(myViewHolder.rlOuter));
    }

    private void runAsyncTask(MyViewHolder myViewHolder) {
        new LiveAdapterNewFlowAsync(myViewHolder).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, myViewHolder);
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
            popupMenu.getMenu().getItem(0).setVisible(false);
            popupMenu.getMenu().getItem(1).setVisible(true);
            popupMenu.getMenu().getItem(2).setVisible(true);
            popupMenu.getMenu().getItem(3).setVisible(true);
            popupMenu.getMenu().getItem(4).setVisible(false);
            popupMenu.getMenu().getItem(5).setVisible(false);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                /* class com.nst.yourname.view.adapter.LiveAdapterNewFlow.AnonymousClass6 */

                public boolean onMenuItemClick(MenuItem menuItem) {
                    int itemId = menuItem.getItemId();
                    if (itemId != R.id.nav_remove_from_live) {
                        switch (itemId) {
                            case R.id.nav_move_to_movie:
                                new AsyncTaskForMovingRecords().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "movie_move");
                                break;
                            case R.id.nav_move_to_series:
                                new AsyncTaskForMovingRecords().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "series_move");
                                break;
                        }
                    } else {
                        new AsyncTaskForMovingRecords().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "live_remove");
                    }
                    return false;
                }

                public String moveToMovie() {
                    ArrayList<LiveStreamsDBModel> allM3UWithCategoryId = LiveAdapterNewFlow.this.liveStreamDBHandler.getAllM3UWithCategoryId(str, false);
                    if (str.equals(AppConst.PASSWORD_UNSET)) {
                        ArrayList<LiveStreamCategoryIdDBModel> allm3uCategories = LiveAdapterNewFlow.this.liveStreamDBHandler.getAllm3uCategories();
                        if (allM3UWithCategoryId == null || allM3UWithCategoryId.size() <= 0) {
                            return "movie_move";
                        }
                        LiveAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                        LiveAdapterNewFlow.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                        LiveAdapterNewFlow.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                        LiveAdapterNewFlow.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                        LiveAdapterNewFlow.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                        LiveAdapterNewFlow.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, AppConst.EVENT_TYPE_MOVIE);
                        LiveAdapterNewFlow.this.liveStreamDBHandler.addMultipleCategoriesM3U(allm3uCategories, AppConst.EVENT_TYPE_MOVIE);
                        LiveAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, AppConst.EVENT_TYPE_MOVIE);
                        return "movie_move";
                    }
                    if (LiveAdapterNewFlow.this.liveStreamDBHandler.checkCategoryExistsM3U(str, AppConst.EVENT_TYPE_MOVIE)) {
                        LiveAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                        LiveAdapterNewFlow.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                        LiveAdapterNewFlow.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                        LiveAdapterNewFlow.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                        LiveAdapterNewFlow.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, AppConst.EVENT_TYPE_MOVIE);
                        LiveAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, AppConst.EVENT_TYPE_MOVIE);
                    } else {
                        M3UCategoriesModel m3UCategoriesModel = new M3UCategoriesModel();
                        if (!str.equals("")) {
                            m3UCategoriesModel = LiveAdapterNewFlow.this.liveStreamDBHandler.getCatByCatIDALLM3U(str);
                        }
                        if (allM3UWithCategoryId != null && allM3UWithCategoryId.size() > 0) {
                            LiveAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                            LiveAdapterNewFlow.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                            LiveAdapterNewFlow.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                            LiveAdapterNewFlow.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                            LiveAdapterNewFlow.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                            LiveAdapterNewFlow.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, AppConst.EVENT_TYPE_MOVIE);
                            if (!str.equals("")) {
                                LiveAdapterNewFlow.this.liveStreamDBHandler.addMovieCategoriesM3U(m3UCategoriesModel);
                            }
                            LiveAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, AppConst.EVENT_TYPE_MOVIE);
                        }
                    }
                    LiveAdapterNewFlow.this.notifyAdapter(i);
                    return "movie_move";
                }

                public String moveToSeries() {
                    ArrayList<LiveStreamsDBModel> allM3UWithCategoryId = LiveAdapterNewFlow.this.liveStreamDBHandler.getAllM3UWithCategoryId(str, false);
                    if (str.equals(AppConst.PASSWORD_UNSET)) {
                        ArrayList<LiveStreamCategoryIdDBModel> allm3uCategories = LiveAdapterNewFlow.this.liveStreamDBHandler.getAllm3uCategories();
                        if (allM3UWithCategoryId == null || allM3UWithCategoryId.size() <= 0) {
                            return "series_move";
                        }
                        LiveAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                        LiveAdapterNewFlow.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                        LiveAdapterNewFlow.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                        LiveAdapterNewFlow.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                        LiveAdapterNewFlow.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                        LiveAdapterNewFlow.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, "series");
                        LiveAdapterNewFlow.this.liveStreamDBHandler.addMultipleCategoriesM3U(allm3uCategories, "series");
                        LiveAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "series");
                        return "series_move";
                    }
                    if (LiveAdapterNewFlow.this.liveStreamDBHandler.checkCategoryExistsM3U(str, "series")) {
                        LiveAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                        LiveAdapterNewFlow.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                        LiveAdapterNewFlow.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                        LiveAdapterNewFlow.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                        LiveAdapterNewFlow.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, "series");
                        LiveAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "series");
                    } else {
                        M3UCategoriesModel m3UCategoriesModel = new M3UCategoriesModel();
                        if (!str.equals("")) {
                            m3UCategoriesModel = LiveAdapterNewFlow.this.liveStreamDBHandler.getCatByCatIDALLM3U(str);
                        }
                        if (allM3UWithCategoryId != null && allM3UWithCategoryId.size() > 0) {
                            LiveAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                            LiveAdapterNewFlow.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                            LiveAdapterNewFlow.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                            LiveAdapterNewFlow.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                            LiveAdapterNewFlow.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                            LiveAdapterNewFlow.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, "series");
                            if (!str.equals("")) {
                                LiveAdapterNewFlow.this.liveStreamDBHandler.addSeriesCategoriesM3U(m3UCategoriesModel);
                            }
                            LiveAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "series");
                        }
                    }
                    LiveAdapterNewFlow.this.notifyAdapter(i);
                    return "series_move";
                }

                public String removefromLive() {
                    LiveAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                    LiveAdapterNewFlow.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                    if (!str.equals("")) {
                        LiveAdapterNewFlow.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                    }
                    LiveAdapterNewFlow.this.notifyAdapter(i);
                    return "live_remove";
                }

                @SuppressLint({"StaticFieldLeak"})
                /* renamed from: com.nst.yourname.view.adapter.LiveAdapterNewFlow$6$AsyncTaskForMovingRecords */
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
                            if (hashCode != -1911854951) {
                                if (hashCode != -1829653632) {
                                    if (hashCode == -1078484169) {
                                        if (str.equals("live_remove")) {
                                            c = 2;
                                            switch (c) {
                                                case 0:
                                                    return moveToMovie();
                                                case 1:
                                                    return moveToSeries();
                                                case 2:
                                                    return removefromLive();
                                                default:
                                                    return null;
                                            }
                                        }
                                    }
                                } else if (str.equals("movie_move")) {
                                    switch (c) {
                                    }
                                }
                            } else if (str.equals("series_move")) {
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
                        if (hashCode != -1911854951) {
                            if (hashCode != -1829653632) {
                                if (hashCode == -1078484169 && str.equals("live_remove")) {
                                    c = 2;
                                    switch (c) {
                                        case 0:
                                            com.nst.yourname.miscelleneious.common.Utils.showToastLong(LiveAdapterNewFlow.this.context, LiveAdapterNewFlow.this.context.getResources().getString(R.string.added_to_movies));
                                            break;
                                        case 1:
                                            com.nst.yourname.miscelleneious.common.Utils.showToastLong(LiveAdapterNewFlow.this.context, LiveAdapterNewFlow.this.context.getResources().getString(R.string.added_to_series));
                                            break;
                                        case 2:
                                            com.nst.yourname.miscelleneious.common.Utils.showToastLong(LiveAdapterNewFlow.this.context, LiveAdapterNewFlow.this.context.getResources().getString(R.string.removed_from_live));
                                            break;
                                    }
                                    LiveAdapterNewFlow.this.notifyDataSetChanged();
                                }
                            } else if (str.equals("movie_move")) {
                                c = 0;
                                switch (c) {
                                }
                                LiveAdapterNewFlow.this.notifyDataSetChanged();
                            }
                        } else if (str.equals("series_move")) {
                            c = 1;
                            switch (c) {
                            }
                            LiveAdapterNewFlow.this.notifyDataSetChanged();
                        }
                        c = 65535;
                        switch (c) {
                        }
                        LiveAdapterNewFlow.this.notifyDataSetChanged();
                    }
                }

                public void showProgressDialogBox() {
                    if (LiveAdapterNewFlow.this.context != null) {
                        ProgressDialog unused = LiveAdapterNewFlow.this.progressDialog = new ProgressDialog(LiveAdapterNewFlow.this.context);
                        LiveAdapterNewFlow.this.progressDialog.setMessage(LiveAdapterNewFlow.this.context.getResources().getString(R.string.please_wait));
                        LiveAdapterNewFlow.this.progressDialog.setCanceledOnTouchOutside(false);
                        LiveAdapterNewFlow.this.progressDialog.setCancelable(false);
                        LiveAdapterNewFlow.this.progressDialog.setProgressStyle(0);
                        LiveAdapterNewFlow.this.progressDialog.show();
                    }
                }

                public void hideProgressDialogBox() {
                    if (LiveAdapterNewFlow.this.context != null && LiveAdapterNewFlow.this.progressDialog != null) {
                        LiveAdapterNewFlow.this.progressDialog.dismiss();
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

    public void filter(final String str, final TextView textView) {
        new Thread(new Runnable() {
            /* class com.nst.yourname.view.adapter.LiveAdapterNewFlow.AnonymousClass7 */

            public void run() {
                List unused = LiveAdapterNewFlow.this.filterList = new ArrayList();
                int unused2 = LiveAdapterNewFlow.this.text_size = str.length();
                if (LiveAdapterNewFlow.this.filterList != null) {
                    LiveAdapterNewFlow.this.filterList.clear();
                }
                if (TextUtils.isEmpty(str)) {
                    LiveAdapterNewFlow.this.filterList.addAll(LiveAdapterNewFlow.this.completeList);
                } else {
                    if ((LiveAdapterNewFlow.this.moviesListl != null && LiveAdapterNewFlow.this.moviesListl.size() == 0) || LiveAdapterNewFlow.this.text_last_size > LiveAdapterNewFlow.this.text_size) {
                        List unused3 = LiveAdapterNewFlow.this.moviesListl = LiveAdapterNewFlow.this.completeList;
                    }
                    if (LiveAdapterNewFlow.this.moviesListl != null) {
                        for (LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel : LiveAdapterNewFlow.this.moviesListl) {
                            if (liveStreamCategoryIdDBModel.getLiveStreamCategoryName().toLowerCase().contains(str.toLowerCase())) {
                                LiveAdapterNewFlow.this.filterList.add(liveStreamCategoryIdDBModel);
                            }
                        }
                    }
                }
                ((Activity) LiveAdapterNewFlow.this.context).runOnUiThread(new Runnable() {
                    /* class com.nst.yourname.view.adapter.LiveAdapterNewFlow.AnonymousClass7.AnonymousClass1 */

                    public void run() {
                        if (TextUtils.isEmpty(str)) {
                            List unused = LiveAdapterNewFlow.this.moviesListl = LiveAdapterNewFlow.this.completeList;
                        } else if (!LiveAdapterNewFlow.this.filterList.isEmpty() || LiveAdapterNewFlow.this.filterList.isEmpty()) {
                            List unused2 = LiveAdapterNewFlow.this.moviesListl = LiveAdapterNewFlow.this.filterList;
                        }
                        if (LiveAdapterNewFlow.this.moviesListl != null && LiveAdapterNewFlow.this.moviesListl.size() == 0) {
                            textView.setVisibility(0);
                        }
                        int unused3 = LiveAdapterNewFlow.this.text_last_size = LiveAdapterNewFlow.this.text_size;
                        LiveAdapterNewFlow.this.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    private class OnFocusChangeAccountListener implements View.OnFocusChangeListener {
        private final View view;

        public OnFocusChangeAccountListener(View view2) {
            this.view = view2;
        }

        @SuppressLint({"ResourceType"})
        public void onFocusChange(View view2, boolean z) {
            if (z) {
                performScaleXAnimation(1.09f);
                performScaleYAnimation(1.09f);
                Log.e("id is", "" + this.view.getTag());
                this.view.setBackgroundResource(R.drawable.shape_list_categories_focused);
            } else if (!z) {
                performScaleXAnimation(1.0f);
                performScaleYAnimation(1.0f);
                performAlphaAnimation(false);
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

    @SuppressLint({"StaticFieldLeak"})
    private class LiveAdapterNewFlowAsync extends AsyncTask<MyViewHolder, Void, Integer> {
        private MyViewHolder MyViewHolder;

        LiveAdapterNewFlowAsync(MyViewHolder myViewHolder) {
            this.MyViewHolder = myViewHolder;
        }

        public void onPreExecute() {
            super.onPreExecute();
            this.MyViewHolder.tvXubCount.setVisibility(8);
        }

        public Integer doInBackground(MyViewHolder... myViewHolderArr) {
            try {
                if (SharepreferenceDBHandler.getCurrentAPPType(LiveAdapterNewFlow.this.context).equals(AppConst.TYPE_M3U)) {
                    return Integer.valueOf(LiveAdapterNewFlow.this.liveStreamDBHandler.getFavouriteCountM3U("live"));
                }
                return Integer.valueOf(LiveAdapterNewFlow.this.dbHandeler.getFavouriteCount("live", SharepreferenceDBHandler.getUserID(LiveAdapterNewFlow.this.context)));
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
