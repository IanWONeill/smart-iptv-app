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
import com.nst.yourname.model.database.RecentWatchDBHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.view.activity.VodActivityNewFlowSubCategories;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("ALL")
public class VodAdapterNewFlow extends RecyclerView.Adapter<VodAdapterNewFlow.MyViewHolder> {
    private int allCount;
    public List<LiveStreamCategoryIdDBModel> completeList;
    public Context context;
    public DatabaseHandler dbHandeler;
    public List<LiveStreamCategoryIdDBModel> filterList;
    public int focusedItem;
    public LiveStreamDBHandler liveStreamDBHandler;
    public List<LiveStreamCategoryIdDBModel> moviesListl;
    public ProgressDialog progressDialog;
    private RecentWatchDBHandler recentWatchDBHandler;
    private String selected_language;
    public int text_last_size;
    public int text_size;
    private int userIdReferred;

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
            /* class com.nst.yourname.view.adapter.VodAdapterNewFlow.AnonymousClass1 */

            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (keyEvent.getAction() != 0) {
                    return false;
                }
                if (i == 20) {
                    return VodAdapterNewFlow.this.tryMoveSelection(layoutManager, 1);
                }
                if (i == 19) {
                    return VodAdapterNewFlow.this.tryMoveSelection(layoutManager, -1);
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

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: java.util.List<com.nst.yourname.model.LiveStreamCategoryIdDBModel>} */
    /* JADX WARNING: Multi-variable type inference failed */
    public VodAdapterNewFlow(List<LiveStreamCategoryIdDBModel> list, Context context2) {
        this.userIdReferred = -1;
        this.selected_language = "";
        this.allCount = 0;
        this.focusedItem = 0;
        this.filterList = new ArrayList();
        this.filterList.addAll(list);
        this.completeList = list;
        this.moviesListl = list;
        this.context = context2;
        this.liveStreamDBHandler = new LiveStreamDBHandler(context2);
        this.dbHandeler = new DatabaseHandler(context2);
        this.userIdReferred = SharepreferenceDBHandler.getUserID(context2);
        this.recentWatchDBHandler = new RecentWatchDBHandler(context2);
        String vodActivitynewFlowSort = SharepreferenceDBHandler.getVodActivitynewFlowSort(context2);
        if (vodActivitynewFlowSort.equals("1")) {
            Collections.sort(list, new Comparator<LiveStreamCategoryIdDBModel>() {
                /* class com.nst.yourname.view.adapter.VodAdapterNewFlow.AnonymousClass2 */

                public int compare(LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel, LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel2) {
                    return liveStreamCategoryIdDBModel.getLiveStreamCategoryName().compareTo(liveStreamCategoryIdDBModel2.getLiveStreamCategoryName());
                }
            });
        }
        if (vodActivitynewFlowSort.equals("2")) {
            Collections.sort(list, new Comparator<LiveStreamCategoryIdDBModel>() {
                /* class com.nst.yourname.view.adapter.VodAdapterNewFlow.AnonymousClass3 */

                public int compare(LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel, LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel2) {
                    return liveStreamCategoryIdDBModel2.getLiveStreamCategoryName().compareTo(liveStreamCategoryIdDBModel.getLiveStreamCategoryName());
                }
            });
        }
    }

    public VodAdapterNewFlow() {
        this.userIdReferred = -1;
        this.selected_language = "";
        this.allCount = 0;
        this.focusedItem = 0;
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

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00a5, code lost:
        if (r1.equals("-4") == false) goto L_0x00b2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x012d, code lost:
        if (r10.equals("-3") != false) goto L_0x0145;
     */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00b6  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00c4  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00e4  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00e9  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0148  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0156  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0176  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0194  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x01b2  */
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
        char c = 3;
        if (this.context != null && (this.context.getResources().getConfiguration().screenLayout & 15) == 3 && i == this.focusedItem) {
            myViewHolder.rlOuter.requestFocus();
            performScaleXAnimation(1.09f, myViewHolder.rlOuter);
            performScaleYAnimation(1.09f, myViewHolder.rlOuter);
            myViewHolder.rlOuter.setBackgroundResource(R.drawable.shape_list_categories_focused);
        }
        myViewHolder.rlOuter.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.adapter.VodAdapterNewFlow.AnonymousClass4 */

            public void onClick(View view) {
                int unused = VodAdapterNewFlow.this.focusedItem = myViewHolder.getLayoutPosition();
                Intent intent = new Intent(VodAdapterNewFlow.this.context, VodActivityNewFlowSubCategories.class);
                intent.putExtra(AppConst.CATEGORY_ID, liveStreamCategoryID);
                intent.putExtra(AppConst.CATEGORY_NAME, liveStreamCategoryName);
                VodAdapterNewFlow.this.context.startActivity(intent);
            }
        });
        myViewHolder.rlOuter.setOnFocusChangeListener(new OnFocusChangeAccountListener(myViewHolder.rlOuter));
        char c2 = 1;
        if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            String liveStreamCategoryID2 = liveStreamCategoryIdDBModel.getLiveStreamCategoryID();
            int hashCode = liveStreamCategoryID2.hashCode();
            if (hashCode != 1444) {
                if (hashCode == 1447) {
                }
            } else if (liveStreamCategoryID2.equals("-1")) {
                c2 = 0;
                switch (c2) {
                    case 0:
                        runAsyncTask(myViewHolder);
                        break;
                    case 1:
                        int recentwatchCount = this.liveStreamDBHandler.getRecentwatchCount(this.userIdReferred, AppConst.EVENT_TYPE_MOVIE);
                        if (recentwatchCount != 0 && recentwatchCount != -1) {
                            myViewHolder.tvXubCount.setText(String.valueOf(recentwatchCount));
                            break;
                        } else {
                            myViewHolder.tvXubCount.setText(AppConst.PASSWORD_UNSET);
                            break;
                        }
                    default:
                        myViewHolder.tvXubCount.setText(String.valueOf(liveStreamCategoryIdDBModel.getLiveStreamCounter()));
                        break;
                }
                if (i == 0) {
                    this.allCount = liveStreamCategoryIdDBModel.getLiveStreamCounter();
                }
                if (!liveStreamCategoryID.equals("-1") && !liveStreamCategoryID.equals(AppConst.PASSWORD_UNSET)) {
                    myViewHolder.rlOuter.setOnLongClickListener(new View.OnLongClickListener() {
                        /* class com.nst.yourname.view.adapter.VodAdapterNewFlow.AnonymousClass5 */

                        public boolean onLongClick(View view) {
                            VodAdapterNewFlow.this.popmenu(myViewHolder, liveStreamCategoryID, i);
                            return true;
                        }
                    });
                    return;
                }
                return;
            }
            c2 = 65535;
            switch (c2) {
            }
            if (i == 0) {
            }
            if (!liveStreamCategoryID.equals("-1")) {
                return;
            }
            return;
        }
        String liveStreamCategoryID3 = liveStreamCategoryIdDBModel.getLiveStreamCategoryID();
        int hashCode2 = liveStreamCategoryID3.hashCode();
        if (hashCode2 != 48) {
            if (hashCode2 != 1444) {
                switch (hashCode2) {
                    case 1447:
                        if (liveStreamCategoryID3.equals("-4")) {
                            c = 1;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        runAsyncTask(myViewHolder);
                        return;
                    case 1:
                        int liveStreamsCount = this.recentWatchDBHandler.getLiveStreamsCount(this.userIdReferred);
                        if (liveStreamsCount == 0 || liveStreamsCount == -1) {
                            myViewHolder.tvXubCount.setText(AppConst.PASSWORD_UNSET);
                            return;
                        } else {
                            myViewHolder.tvXubCount.setText(String.valueOf(liveStreamsCount));
                            return;
                        }
                    case 2:
                        int streamsCount = this.liveStreamDBHandler.getStreamsCount(AppConst.EVENT_TYPE_MOVIE);
                        if (streamsCount == 0 || streamsCount == -1) {
                            myViewHolder.tvXubCount.setText("");
                            return;
                        } else {
                            myViewHolder.tvXubCount.setText(String.valueOf(streamsCount));
                            return;
                        }
                    case 3:
                        int uncatCount = this.liveStreamDBHandler.getUncatCount("-3", AppConst.EVENT_TYPE_MOVIE);
                        if (uncatCount == 0 || uncatCount == -1) {
                            myViewHolder.tvXubCount.setText(AppConst.PASSWORD_UNSET);
                            return;
                        } else {
                            myViewHolder.tvXubCount.setText(String.valueOf(uncatCount));
                            return;
                        }
                    default:
                        myViewHolder.tvXubCount.setText(String.valueOf(liveStreamCategoryIdDBModel.getLiveStreamCounter()));
                        return;
                }
            } else if (liveStreamCategoryID3.equals("-1")) {
                c = 0;
                switch (c) {
                }
            }
        } else if (liveStreamCategoryID3.equals(AppConst.PASSWORD_UNSET)) {
            c = 2;
            switch (c) {
            }
        }
        c = 65535;
        switch (c) {
        }
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

    private void runAsyncTask(MyViewHolder myViewHolder) {
        new VODAdapterNewFlowAsync(myViewHolder).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, myViewHolder);
    }

    public void popmenu(MyViewHolder myViewHolder, final String str, final int i) {
        if (this.context != null) {
            PopupMenu popupMenu = new PopupMenu(this.context, myViewHolder.rlOuter);
            popupMenu.inflate(R.menu.menu_move_categories);
            popupMenu.getMenu().getItem(0).setVisible(true);
            popupMenu.getMenu().getItem(1).setVisible(false);
            popupMenu.getMenu().getItem(2).setVisible(true);
            popupMenu.getMenu().getItem(3).setVisible(false);
            popupMenu.getMenu().getItem(4).setVisible(true);
            popupMenu.getMenu().getItem(5).setVisible(false);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                /* class com.nst.yourname.view.adapter.VodAdapterNewFlow.AnonymousClass6 */

                public boolean onMenuItemClick(MenuItem menuItem) {
                    int itemId = menuItem.getItemId();
                    if (itemId == R.id.nav_move_to_live) {
                        new AsyncTaskForMovingRecords().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "live_move");
                    } else if (itemId == R.id.nav_move_to_series) {
                        new AsyncTaskForMovingRecords().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "series_move");
                    } else if (itemId == R.id.nav_remove_from_movies) {
                        new AsyncTaskForMovingRecords().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "movie_remove");
                    }
                    return false;
                }

                public String moveToLive() {
                    ArrayList<LiveStreamsDBModel> allM3UWithCategoryId = VodAdapterNewFlow.this.liveStreamDBHandler.getAllM3UWithCategoryId(str, false);
                    if (str.equals(AppConst.PASSWORD_UNSET)) {
                        ArrayList<LiveStreamCategoryIdDBModel> allm3uCategories = VodAdapterNewFlow.this.liveStreamDBHandler.getAllm3uCategories();
                        if (allM3UWithCategoryId == null || allM3UWithCategoryId.size() <= 0) {
                            return "live_move";
                        }
                        VodAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                        VodAdapterNewFlow.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                        VodAdapterNewFlow.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                        VodAdapterNewFlow.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                        VodAdapterNewFlow.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                        VodAdapterNewFlow.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, "live");
                        VodAdapterNewFlow.this.liveStreamDBHandler.addMultipleCategoriesM3U(allm3uCategories, "live");
                        VodAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "live");
                        return "live_move";
                    }
                    if (!VodAdapterNewFlow.this.liveStreamDBHandler.checkCategoryExistsM3U(str, "live")) {
                        M3UCategoriesModel m3UCategoriesModel = new M3UCategoriesModel();
                        if (!str.equals("")) {
                            m3UCategoriesModel = VodAdapterNewFlow.this.liveStreamDBHandler.getCatByCatIDALLM3U(str);
                        }
                        if (allM3UWithCategoryId != null && allM3UWithCategoryId.size() > 0) {
                            VodAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                            VodAdapterNewFlow.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                            VodAdapterNewFlow.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                            VodAdapterNewFlow.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                            VodAdapterNewFlow.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                            VodAdapterNewFlow.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, "live");
                            if (!str.equals("")) {
                                VodAdapterNewFlow.this.liveStreamDBHandler.addLiveCategoriesM3U(m3UCategoriesModel);
                            }
                            VodAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "live");
                        }
                    } else if (allM3UWithCategoryId != null && allM3UWithCategoryId.size() > 0) {
                        VodAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                        VodAdapterNewFlow.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                        VodAdapterNewFlow.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                        VodAdapterNewFlow.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                        VodAdapterNewFlow.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, "live");
                        VodAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "live");
                    }
                    VodAdapterNewFlow.this.notifyAdapter(i);
                    return "live_move";
                }

                public String moveToSeries() {
                    ArrayList<LiveStreamsDBModel> allM3UWithCategoryId = VodAdapterNewFlow.this.liveStreamDBHandler.getAllM3UWithCategoryId(str, false);
                    if (str.equals(AppConst.PASSWORD_UNSET)) {
                        ArrayList<LiveStreamCategoryIdDBModel> allm3uCategories = VodAdapterNewFlow.this.liveStreamDBHandler.getAllm3uCategories();
                        if (allM3UWithCategoryId == null || allM3UWithCategoryId.size() <= 0) {
                            return "series_move";
                        }
                        VodAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                        VodAdapterNewFlow.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                        VodAdapterNewFlow.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                        VodAdapterNewFlow.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                        VodAdapterNewFlow.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                        VodAdapterNewFlow.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, "series");
                        VodAdapterNewFlow.this.liveStreamDBHandler.addMultipleCategoriesM3U(allm3uCategories, "series");
                        VodAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "series");
                        return "series_move";
                    }
                    if (VodAdapterNewFlow.this.liveStreamDBHandler.checkCategoryExistsM3U(str, "series")) {
                        VodAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                        VodAdapterNewFlow.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                        VodAdapterNewFlow.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                        VodAdapterNewFlow.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                        VodAdapterNewFlow.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, "series");
                        VodAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "series");
                    } else {
                        M3UCategoriesModel m3UCategoriesModel = new M3UCategoriesModel();
                        if (!str.equals("")) {
                            m3UCategoriesModel = VodAdapterNewFlow.this.liveStreamDBHandler.getCatByCatIDALLM3U(str);
                        }
                        if (allM3UWithCategoryId != null && allM3UWithCategoryId.size() > 0) {
                            VodAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                            VodAdapterNewFlow.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                            VodAdapterNewFlow.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                            VodAdapterNewFlow.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                            VodAdapterNewFlow.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                            VodAdapterNewFlow.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, "series");
                            if (!str.equals("")) {
                                VodAdapterNewFlow.this.liveStreamDBHandler.addSeriesCategoriesM3U(m3UCategoriesModel);
                            }
                            VodAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "series");
                        }
                    }
                    VodAdapterNewFlow.this.notifyAdapter(i);
                    return "series_move";
                }

                public String removefromMovie() {
                    VodAdapterNewFlow.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                    VodAdapterNewFlow.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                    if (!str.equals("")) {
                        VodAdapterNewFlow.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                    }
                    VodAdapterNewFlow.this.notifyAdapter(i);
                    return "movie_remove";
                }

                @SuppressLint({"StaticFieldLeak"})
                /* renamed from: com.nst.yourname.view.adapter.VodAdapterNewFlow$6$AsyncTaskForMovingRecords */
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
                                if (hashCode != -1521860493) {
                                    if (hashCode == 1008793412) {
                                        if (str.equals("live_move")) {
                                            switch (c) {
                                                case 0:
                                                    return moveToLive();
                                                case 1:
                                                    return moveToSeries();
                                                case 2:
                                                    return removefromMovie();
                                                default:
                                                    return null;
                                            }
                                        }
                                    }
                                } else if (str.equals("movie_remove")) {
                                    c = 2;
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
                            if (hashCode != -1521860493) {
                                if (hashCode == 1008793412 && str.equals("live_move")) {
                                    c = 0;
                                    switch (c) {
                                        case 0:
                                            com.nst.yourname.miscelleneious.common.Utils.showToastLong(VodAdapterNewFlow.this.context, VodAdapterNewFlow.this.context.getResources().getString(R.string.added_to_live));
                                            break;
                                        case 1:
                                            com.nst.yourname.miscelleneious.common.Utils.showToastLong(VodAdapterNewFlow.this.context, VodAdapterNewFlow.this.context.getResources().getString(R.string.added_to_series));
                                            break;
                                        case 2:
                                            com.nst.yourname.miscelleneious.common.Utils.showToastLong(VodAdapterNewFlow.this.context, VodAdapterNewFlow.this.context.getResources().getString(R.string.removed_from_movies));
                                            break;
                                    }
                                    VodAdapterNewFlow.this.notifyDataSetChanged();
                                }
                            } else if (str.equals("movie_remove")) {
                                c = 2;
                                switch (c) {
                                }
                                VodAdapterNewFlow.this.notifyDataSetChanged();
                            }
                        } else if (str.equals("series_move")) {
                            c = 1;
                            switch (c) {
                            }
                            VodAdapterNewFlow.this.notifyDataSetChanged();
                        }
                        c = 65535;
                        switch (c) {
                        }
                        VodAdapterNewFlow.this.notifyDataSetChanged();
                    }
                }

                public void showProgressDialogBox() {
                    if (VodAdapterNewFlow.this.context != null) {
                        ProgressDialog unused = VodAdapterNewFlow.this.progressDialog = new ProgressDialog(VodAdapterNewFlow.this.context);
                        VodAdapterNewFlow.this.progressDialog.setMessage(VodAdapterNewFlow.this.context.getResources().getString(R.string.please_wait));
                        VodAdapterNewFlow.this.progressDialog.setCanceledOnTouchOutside(false);
                        VodAdapterNewFlow.this.progressDialog.setCancelable(false);
                        VodAdapterNewFlow.this.progressDialog.setProgressStyle(0);
                        VodAdapterNewFlow.this.progressDialog.show();
                    }
                }

                public void hideProgressDialogBox() {
                    if (VodAdapterNewFlow.this.context != null && VodAdapterNewFlow.this.progressDialog != null) {
                        VodAdapterNewFlow.this.progressDialog.dismiss();
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

    public void filter(final String str, final TextView textView) {
        new Thread(new Runnable() {
            /* class com.nst.yourname.view.adapter.VodAdapterNewFlow.AnonymousClass7 */

            public void run() {
                List unused = VodAdapterNewFlow.this.filterList = new ArrayList();
                VodAdapterNewFlow.this.text_size = str.length();
                if (VodAdapterNewFlow.this.filterList != null) {
                    VodAdapterNewFlow.this.filterList.clear();
                }
                if (TextUtils.isEmpty(str)) {
                    VodAdapterNewFlow.this.filterList.addAll(VodAdapterNewFlow.this.completeList);
                } else {
                    if ((VodAdapterNewFlow.this.moviesListl != null && VodAdapterNewFlow.this.moviesListl.size() == 0) || VodAdapterNewFlow.this.text_last_size > VodAdapterNewFlow.this.text_size) {
                        List unused2 = VodAdapterNewFlow.this.moviesListl = VodAdapterNewFlow.this.completeList;
                    }
                    if (VodAdapterNewFlow.this.moviesListl != null) {
                        for (LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel : VodAdapterNewFlow.this.moviesListl) {
                            if (liveStreamCategoryIdDBModel.getLiveStreamCategoryName().toLowerCase().contains(str.toLowerCase())) {
                                VodAdapterNewFlow.this.filterList.add(liveStreamCategoryIdDBModel);
                            }
                        }
                    }
                }
                ((Activity) VodAdapterNewFlow.this.context).runOnUiThread(new Runnable() {
                    /* class com.nst.yourname.view.adapter.VodAdapterNewFlow.AnonymousClass7.AnonymousClass1 */

                    public void run() {
                        if (TextUtils.isEmpty(str)) {
                            List unused = VodAdapterNewFlow.this.moviesListl = VodAdapterNewFlow.this.completeList;
                        } else if (!VodAdapterNewFlow.this.filterList.isEmpty() || VodAdapterNewFlow.this.filterList.isEmpty()) {
                            List unused2 = VodAdapterNewFlow.this.moviesListl = VodAdapterNewFlow.this.filterList;
                        }
                        if (VodAdapterNewFlow.this.moviesListl != null && VodAdapterNewFlow.this.moviesListl.size() == 0) {
                            textView.setVisibility(0);
                            textView.setText(VodAdapterNewFlow.this.context.getResources().getString(R.string.no_record_found));
                        }
                        VodAdapterNewFlow.this.text_last_size = VodAdapterNewFlow.this.text_size;
                        VodAdapterNewFlow.this.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    @SuppressLint({"StaticFieldLeak"})
    private class VODAdapterNewFlowAsync extends AsyncTask<MyViewHolder, Void, Integer> {
        private MyViewHolder MyViewHolder;

        VODAdapterNewFlowAsync(MyViewHolder myViewHolder) {
            this.MyViewHolder = myViewHolder;
        }

        public void onPreExecute() {
            super.onPreExecute();
            this.MyViewHolder.tvXubCount.setVisibility(8);
        }

        public Integer doInBackground(MyViewHolder... myViewHolderArr) {
            try {
                if (SharepreferenceDBHandler.getCurrentAPPType(VodAdapterNewFlow.this.context).equals(AppConst.TYPE_M3U)) {
                    return Integer.valueOf(VodAdapterNewFlow.this.liveStreamDBHandler.getFavouriteCountM3U(AppConst.EVENT_TYPE_MOVIE));
                }
                return Integer.valueOf(VodAdapterNewFlow.this.dbHandeler.getFavouriteCount(AppConst.VOD, SharepreferenceDBHandler.getUserID(VodAdapterNewFlow.this.context)));
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
