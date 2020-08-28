package com.nst.yourname.view.adapter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import com.nst.yourname.view.activity.PlaylistActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import tourguide.tourguide.Overlay;
import tourguide.tourguide.Pointer;
import tourguide.tourguide.ToolTip;
import tourguide.tourguide.TourGuide;

@SuppressWarnings("all")
public class PlaylistsCategoriesAdapter extends RecyclerView.Adapter<PlaylistsCategoriesAdapter.MyViewHolder> {
    private Activity activity;
    int allCount = 0;
    public List<LiveStreamCategoryIdDBModel> completeList;
    public Context context;
    private DatabaseHandler dbHandeler;
    public List<LiveStreamCategoryIdDBModel> filterList = new ArrayList();
    private boolean firstTimeFlag = true;
    public int focusedItem = 0;
    public LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesSharedPref;
    public TourGuide mTourGuideHandler;
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
            /* class com.nst.yourname.view.adapter.PlaylistsCategoriesAdapter.AnonymousClass1 */

            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (keyEvent.getAction() != 0) {
                    return false;
                }
                if (i == 20) {
                    return PlaylistsCategoriesAdapter.this.tryMoveSelection(layoutManager, 1);
                }
                if (i == 19) {
                    return PlaylistsCategoriesAdapter.this.tryMoveSelection(layoutManager, -1);
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
    public PlaylistsCategoriesAdapter(List<LiveStreamCategoryIdDBModel> list, Context context2, Activity activity2) {
        this.filterList.addAll(list);
        this.completeList = list;
        this.moviesListl = list;
        this.context = context2;
        this.activity = activity2;
        this.liveStreamDBHandler = new LiveStreamDBHandler(context2);
        this.dbHandeler = new DatabaseHandler(context2);
        String playListCategorySort = SharepreferenceDBHandler.getPlayListCategorySort(context2);
        if (playListCategorySort.equals("2")) {
            Collections.sort(list, new Comparator<LiveStreamCategoryIdDBModel>() {
                /* class com.nst.yourname.view.adapter.PlaylistsCategoriesAdapter.AnonymousClass2 */

                public int compare(LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel, LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel2) {
                    return liveStreamCategoryIdDBModel.getLiveStreamCategoryName().compareTo(liveStreamCategoryIdDBModel2.getLiveStreamCategoryName());
                }
            });
        }
        if (playListCategorySort.equals("3")) {
            Collections.sort(list, new Comparator<LiveStreamCategoryIdDBModel>() {
                /* class com.nst.yourname.view.adapter.PlaylistsCategoriesAdapter.AnonymousClass3 */

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

    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, @SuppressLint({"RecyclerView"}) final int i) {
        try {
            LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = this.moviesListl.get(i);
            final String liveStreamCategoryName = liveStreamCategoryIdDBModel.getLiveStreamCategoryName();
            final String liveStreamCategoryID = liveStreamCategoryIdDBModel.getLiveStreamCategoryID();
            Bundle bundle = new Bundle();
            bundle.putString(AppConst.CATEGORY_ID, liveStreamCategoryID);
            bundle.putString(AppConst.CATEGORY_NAME, liveStreamCategoryName);
            if (liveStreamCategoryName != null && !liveStreamCategoryName.equals("") && !liveStreamCategoryName.isEmpty()) {
                myViewHolder.tvMovieCategoryName.setText(liveStreamCategoryName);
            }
            final int liveStreamCounter = liveStreamCategoryIdDBModel.getLiveStreamCounter();
            if (liveStreamCounter == 0 || liveStreamCounter == -1) {
                myViewHolder.tvXubCount.setText(AppConst.PASSWORD_UNSET);
            } else {
                myViewHolder.tvXubCount.setText(String.valueOf(liveStreamCounter));
            }
            if (liveStreamCategoryID.equals(AppConst.PASSWORD_UNSET)) {
                this.allCount = liveStreamCounter;
            }
            if (liveStreamCategoryIdDBModel.getLiveStreamCategoryID() != null && liveStreamCategoryIdDBModel.getLiveStreamCategoryID().equals("-1")) {
                runAsyncTask(myViewHolder);
            }
            if (this.context != null && (this.context.getResources().getConfiguration().screenLayout & 15) == 3 && i == this.focusedItem) {
                myViewHolder.rlOuter.requestFocus();
                performScaleXAnimation(1.09f, myViewHolder.rlOuter);
                performScaleYAnimation(1.09f, myViewHolder.rlOuter);
                myViewHolder.rlOuter.setBackgroundResource(R.drawable.shape_list_categories_focused);
            }
            myViewHolder.rlOuter.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.PlaylistsCategoriesAdapter.AnonymousClass4 */

                public void onClick(View view) {
                    if (PlaylistsCategoriesAdapter.this.mTourGuideHandler != null) {
                        PlaylistsCategoriesAdapter.this.mTourGuideHandler.cleanUp();
                    }
                    int unused = PlaylistsCategoriesAdapter.this.focusedItem = myViewHolder.getLayoutPosition();
                    new PlaylistActivity().progressBar(myViewHolder.pbPagingLoader);
                    if (myViewHolder.pbPagingLoader != null) {
                        myViewHolder.pbPagingLoader.getIndeterminateDrawable().setColorFilter(-16777216, PorterDuff.Mode.SRC_IN);
                        myViewHolder.pbPagingLoader.setVisibility(0);
                    }
                    Intent intent = new Intent(PlaylistsCategoriesAdapter.this.context, PlaylistActivity.class);
                    intent.putExtra(AppConst.CATEGORY_ID, liveStreamCategoryID);
                    intent.putExtra(AppConst.CATEGORY_NAME, liveStreamCategoryName);
                    PlaylistsCategoriesAdapter.this.context.startActivity(intent);
                }
            });
            if (!liveStreamCategoryID.equals("-1") && !liveStreamCategoryID.equals(AppConst.PASSWORD_UNSET)) {
                myViewHolder.rlOuter.setOnLongClickListener(new View.OnLongClickListener() {
                    /* class com.nst.yourname.view.adapter.PlaylistsCategoriesAdapter.AnonymousClass5 */

                    public boolean onLongClick(View view) {
                        if (PlaylistsCategoriesAdapter.this.mTourGuideHandler != null) {
                            PlaylistsCategoriesAdapter.this.mTourGuideHandler.cleanUp();
                        }
                        PlaylistsCategoriesAdapter.this.popmenu(myViewHolder, liveStreamCategoryID, liveStreamCounter, i);
                        return true;
                    }
                });
            }
            if (i == 2 && SharepreferenceDBHandler.getTooltip(this.context) == 0) {
                tooltip(myViewHolder.rlOuter);
                myViewHolder.rlOuter.performLongClick();
                SharepreferenceDBHandler.setTooltip(1, this.context);
            }
            myViewHolder.rlOuter.setOnFocusChangeListener(new OnFocusChangeAccountListener(myViewHolder.rlOuter));
            if (i == 0 && this.firstTimeFlag) {
                myViewHolder.rlOuter.requestFocus();
                this.firstTimeFlag = false;
            }
        } catch (Exception unused) {
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

    private void tooltip(RelativeLayout relativeLayout) {
        this.mTourGuideHandler = TourGuide.init(this.activity);
        //ToDo: TourGuide...
        //this.mTourGuideHandler.with(TourGuide.Technique.HORIZONTAL_RIGHT).setPointer(new Pointer()).m4setToolTip(new ToolTip().m3setTitle(this.context.getResources().getString(R.string.long_press_on_any_cat)).setBackgroundColor(Color.parseColor("#e54d26")).setShadow(true).setGravity(53)).playOn(relativeLayout).setOverlay(new Overlay());
    }

    private void runAsyncTask(MyViewHolder myViewHolder) {
        new PlaylistsCategoriesAdapterAsync(myViewHolder).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, myViewHolder);
    }

    public void popmenu(MyViewHolder myViewHolder, final String str, int i, final int i2) {
        if (this.context != null) {
            PopupMenu popupMenu = new PopupMenu(this.context, myViewHolder.rlOuter);
            popupMenu.inflate(R.menu.menu_move_categories);
            int countMovedItems = this.liveStreamDBHandler.getCountMovedItems(str, "live");
            int countMovedItems2 = this.liveStreamDBHandler.getCountMovedItems(str, AppConst.EVENT_TYPE_MOVIE);
            int countMovedItems3 = this.liveStreamDBHandler.getCountMovedItems(str, "series");
            if (countMovedItems == i) {
                popupMenu.getMenu().getItem(0).setVisible(false);
                popupMenu.getMenu().getItem(1).setVisible(false);
                popupMenu.getMenu().getItem(2).setVisible(false);
                popupMenu.getMenu().getItem(3).setVisible(true);
                popupMenu.getMenu().getItem(4).setVisible(false);
                popupMenu.getMenu().getItem(5).setVisible(false);
            }
            if (countMovedItems2 == i) {
                popupMenu.getMenu().getItem(0).setVisible(false);
                popupMenu.getMenu().getItem(1).setVisible(false);
                popupMenu.getMenu().getItem(2).setVisible(false);
                popupMenu.getMenu().getItem(3).setVisible(false);
                popupMenu.getMenu().getItem(4).setVisible(true);
                popupMenu.getMenu().getItem(5).setVisible(false);
            }
            if (countMovedItems3 == i) {
                popupMenu.getMenu().getItem(0).setVisible(false);
                popupMenu.getMenu().getItem(1).setVisible(false);
                popupMenu.getMenu().getItem(2).setVisible(false);
                popupMenu.getMenu().getItem(3).setVisible(false);
                popupMenu.getMenu().getItem(4).setVisible(false);
                popupMenu.getMenu().getItem(5).setVisible(true);
            }
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                /* class com.nst.yourname.view.adapter.PlaylistsCategoriesAdapter.AnonymousClass6 */

                public boolean onMenuItemClick(MenuItem menuItem) {
                    int itemId = menuItem.getItemId();
                    switch (itemId) {
                        case R.id.nav_move_to_live:
                            new AsyncTaskForMovingRecords().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "live_move");
                            break;
                        case R.id.nav_move_to_movie:
                            new AsyncTaskForMovingRecords().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "movie_move");
                            break;
                        case R.id.nav_move_to_series:
                            new AsyncTaskForMovingRecords().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "series_move");
                            break;
                        default:
                            switch (itemId) {
                                case R.id.nav_remove_from_live:
                                    new AsyncTaskForMovingRecords().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "live_remove");
                                    break;
                                case R.id.nav_remove_from_movies:
                                    new AsyncTaskForMovingRecords().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "movie_remove");
                                    break;
                                case R.id.nav_remove_from_series:
                                    new AsyncTaskForMovingRecords().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "series_remove");
                                    break;
                            }
                    }
                    return false;
                }

                public String moveToLive() {
                    ArrayList<LiveStreamsDBModel> allM3UWithCategoryId = PlaylistsCategoriesAdapter.this.liveStreamDBHandler.getAllM3UWithCategoryId(str, true);
                    if (str.equals(AppConst.PASSWORD_UNSET)) {
                        ArrayList<LiveStreamCategoryIdDBModel> allm3uCategories = PlaylistsCategoriesAdapter.this.liveStreamDBHandler.getAllm3uCategories();
                        if (allM3UWithCategoryId == null || allM3UWithCategoryId.size() <= 0) {
                            return "live_move";
                        }
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, "live");
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.addMultipleCategoriesM3U(allm3uCategories, "live");
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "live");
                        return "live_move";
                    }
                    if (!PlaylistsCategoriesAdapter.this.liveStreamDBHandler.checkCategoryExistsM3U(str, "live")) {
                        M3UCategoriesModel m3UCategoriesModel = new M3UCategoriesModel();
                        if (!str.equals("")) {
                            m3UCategoriesModel = PlaylistsCategoriesAdapter.this.liveStreamDBHandler.getCatByCatIDALLM3U(str);
                        }
                        if (allM3UWithCategoryId != null && allM3UWithCategoryId.size() > 0) {
                            PlaylistsCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                            PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                            PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                            PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                            PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                            PlaylistsCategoriesAdapter.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, "live");
                            if (!str.equals("")) {
                                PlaylistsCategoriesAdapter.this.liveStreamDBHandler.addLiveCategoriesM3U(m3UCategoriesModel);
                            }
                            PlaylistsCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "live");
                        }
                    } else if (allM3UWithCategoryId != null && allM3UWithCategoryId.size() > 0) {
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, "live");
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "live");
                    }
                    PlaylistsCategoriesAdapter.this.notifyAdapter(i2);
                    return "live_move";
                }

                public String moveToMovie() {
                    ArrayList<LiveStreamsDBModel> allM3UWithCategoryId = PlaylistsCategoriesAdapter.this.liveStreamDBHandler.getAllM3UWithCategoryId(str, true);
                    if (str.equals(AppConst.PASSWORD_UNSET)) {
                        ArrayList<LiveStreamCategoryIdDBModel> allm3uCategories = PlaylistsCategoriesAdapter.this.liveStreamDBHandler.getAllm3uCategories();
                        if (allM3UWithCategoryId == null || allM3UWithCategoryId.size() <= 0) {
                            return "movie_move";
                        }
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, AppConst.EVENT_TYPE_MOVIE);
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.addMultipleCategoriesM3U(allm3uCategories, AppConst.EVENT_TYPE_MOVIE);
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, AppConst.EVENT_TYPE_MOVIE);
                        return "movie_move";
                    }
                    if (PlaylistsCategoriesAdapter.this.liveStreamDBHandler.checkCategoryExistsM3U(str, AppConst.EVENT_TYPE_MOVIE)) {
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, AppConst.EVENT_TYPE_MOVIE);
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, AppConst.EVENT_TYPE_MOVIE);
                    } else {
                        M3UCategoriesModel m3UCategoriesModel = new M3UCategoriesModel();
                        if (!str.equals("")) {
                            m3UCategoriesModel = PlaylistsCategoriesAdapter.this.liveStreamDBHandler.getCatByCatIDALLM3U(str);
                        }
                        if (allM3UWithCategoryId != null && allM3UWithCategoryId.size() > 0) {
                            PlaylistsCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                            PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                            PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                            PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                            PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                            PlaylistsCategoriesAdapter.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, AppConst.EVENT_TYPE_MOVIE);
                            if (!str.equals("")) {
                                PlaylistsCategoriesAdapter.this.liveStreamDBHandler.addMovieCategoriesM3U(m3UCategoriesModel);
                            }
                            PlaylistsCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, AppConst.EVENT_TYPE_MOVIE);
                        }
                    }
                    PlaylistsCategoriesAdapter.this.notifyAdapter(i2);
                    return "movie_move";
                }

                public String moveToSeries() {
                    ArrayList<LiveStreamsDBModel> allM3UWithCategoryId = PlaylistsCategoriesAdapter.this.liveStreamDBHandler.getAllM3UWithCategoryId(str, true);
                    if (str.equals(AppConst.PASSWORD_UNSET)) {
                        ArrayList<LiveStreamCategoryIdDBModel> allm3uCategories = PlaylistsCategoriesAdapter.this.liveStreamDBHandler.getAllm3uCategories();
                        if (allM3UWithCategoryId == null || allM3UWithCategoryId.size() <= 0) {
                            return "series_move";
                        }
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, "series");
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.addMultipleCategoriesM3U(allm3uCategories, "series");
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "series");
                        return "series_move";
                    }
                    if (PlaylistsCategoriesAdapter.this.liveStreamDBHandler.checkCategoryExistsM3U(str, "series")) {
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, "series");
                        PlaylistsCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "series");
                    } else {
                        M3UCategoriesModel m3UCategoriesModel = new M3UCategoriesModel();
                        if (!str.equals("")) {
                            m3UCategoriesModel = PlaylistsCategoriesAdapter.this.liveStreamDBHandler.getCatByCatIDALLM3U(str);
                        }
                        if (allM3UWithCategoryId != null && allM3UWithCategoryId.size() > 0) {
                            PlaylistsCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                            PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                            PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                            PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                            PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                            PlaylistsCategoriesAdapter.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, "series");
                            if (!str.equals("")) {
                                PlaylistsCategoriesAdapter.this.liveStreamDBHandler.addSeriesCategoriesM3U(m3UCategoriesModel);
                            }
                            PlaylistsCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "series");
                        }
                    }
                    PlaylistsCategoriesAdapter.this.notifyAdapter(i2);
                    return "series_move";
                }

                public String removefromLive() {
                    PlaylistsCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                    PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                    PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                    return "live_remove";
                }

                public String removefromMovie() {
                    PlaylistsCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                    PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                    PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                    return "movie_remove";
                }

                public String removefromSeries() {
                    PlaylistsCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                    PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                    PlaylistsCategoriesAdapter.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                    return "series_remove";
                }

                @SuppressLint({"StaticFieldLeak"})
                /* renamed from: com.nst.yourname.view.adapter.PlaylistsCategoriesAdapter$6$AsyncTaskForMovingRecords */
                class AsyncTaskForMovingRecords extends AsyncTask<String, Void, String> {
                    AsyncTaskForMovingRecords() {
                    }

                    public void onPreExecute() {
                        super.onPreExecute();
                        showProgressDialogBox();
                    }

                    public String doInBackground(String... strArr) {
                        char c = 0;
                        try {
                            String str = strArr[0];
                            switch (str.hashCode()) {
                                case -1911854951:
                                    if (str.equals("series_move")) {
                                        c = 2;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1829653632:
                                    if (str.equals("movie_move")) {
                                        c = 1;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1521860493:
                                    if (str.equals("movie_remove")) {
                                        c = 4;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1078484169:
                                    if (str.equals("live_remove")) {
                                        c = 3;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1008793412:
                                    if (str.equals("live_move")) {
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1087050572:
                                    if (str.equals("series_remove")) {
                                        c = 5;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                default:
                                    c = 65535;
                                    break;
                            }
                            switch (c) {
                                case 0:
                                    return moveToLive();
                                case 1:
                                    return moveToMovie();
                                case 2:
                                    return moveToSeries();
                                case 3:
                                    return removefromLive();
                                case 4:
                                    return removefromMovie();
                                case 5:
                                    return removefromSeries();
                                default:
                                    return null;
                            }
                        } catch (Exception unused) {
                            return "error";
                        }
                    }

                    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
                    public void onPostExecute(String str) {
                        char c;
                        super.onPostExecute((String) str);
                        hideProgressDialogBox();
                        switch (str.hashCode()) {
                            case -1911854951:
                                if (str.equals("series_move")) {
                                    c = 2;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1829653632:
                                if (str.equals("movie_move")) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1521860493:
                                if (str.equals("movie_remove")) {
                                    c = 4;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1078484169:
                                if (str.equals("live_remove")) {
                                    c = 3;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1008793412:
                                if (str.equals("live_move")) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1087050572:
                                if (str.equals("series_remove")) {
                                    c = 5;
                                    break;
                                }
                                c = 65535;
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                        switch (c) {
                            case 0:
                                com.nst.yourname.miscelleneious.common.Utils.showToastLong(PlaylistsCategoriesAdapter.this.context, PlaylistsCategoriesAdapter.this.context.getResources().getString(R.string.added_to_live));
                                break;
                            case 1:
                                com.nst.yourname.miscelleneious.common.Utils.showToastLong(PlaylistsCategoriesAdapter.this.context, PlaylistsCategoriesAdapter.this.context.getResources().getString(R.string.added_to_movies));
                                break;
                            case 2:
                                com.nst.yourname.miscelleneious.common.Utils.showToastLong(PlaylistsCategoriesAdapter.this.context, PlaylistsCategoriesAdapter.this.context.getResources().getString(R.string.added_to_series));
                                break;
                            case 3:
                                com.nst.yourname.miscelleneious.common.Utils.showToastLong(PlaylistsCategoriesAdapter.this.context, PlaylistsCategoriesAdapter.this.context.getResources().getString(R.string.removed_from_live));
                                break;
                            case 4:
                                com.nst.yourname.miscelleneious.common.Utils.showToastLong(PlaylistsCategoriesAdapter.this.context, PlaylistsCategoriesAdapter.this.context.getResources().getString(R.string.removed_from_movies));
                                break;
                            case 5:
                                com.nst.yourname.miscelleneious.common.Utils.showToastLong(PlaylistsCategoriesAdapter.this.context, PlaylistsCategoriesAdapter.this.context.getResources().getString(R.string.removed_from_series));
                                break;
                        }
                        PlaylistsCategoriesAdapter.this.notifyDataSetChanged();
                    }
                }

                public void showProgressDialogBox() {
                    if (PlaylistsCategoriesAdapter.this.context != null) {
                        ProgressDialog unused = PlaylistsCategoriesAdapter.this.progressDialog = new ProgressDialog(PlaylistsCategoriesAdapter.this.context);
                        PlaylistsCategoriesAdapter.this.progressDialog.setMessage(PlaylistsCategoriesAdapter.this.context.getResources().getString(R.string.please_wait));
                        PlaylistsCategoriesAdapter.this.progressDialog.setCanceledOnTouchOutside(false);
                        PlaylistsCategoriesAdapter.this.progressDialog.setCancelable(false);
                        PlaylistsCategoriesAdapter.this.progressDialog.setProgressStyle(0);
                        PlaylistsCategoriesAdapter.this.progressDialog.show();
                    }
                }

                public void hideProgressDialogBox() {
                    if (PlaylistsCategoriesAdapter.this.context != null && PlaylistsCategoriesAdapter.this.progressDialog != null) {
                        PlaylistsCategoriesAdapter.this.progressDialog.dismiss();
                    }
                }
            });
            popupMenu.show();
            popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                /* class com.nst.yourname.view.adapter.PlaylistsCategoriesAdapter.AnonymousClass7 */

                public void onDismiss(PopupMenu popupMenu) {
                    if (PlaylistsCategoriesAdapter.this.mTourGuideHandler != null) {
                        PlaylistsCategoriesAdapter.this.mTourGuideHandler.cleanUp();
                    }
                }
            });
        }
    }

    public void notifyAdapter(int i) {
        int i2;
        int i3;
        int i4;
        if (this.moviesListl != null && this.moviesListl.size() > 0) {
            this.allCount -= this.moviesListl.get(i).getLiveStreamCounter();
            boolean z = false;
            if (this.completeList == null || this.completeList.size() <= 0) {
                i4 = 0;
                i3 = -1;
                i2 = -1;
            } else {
                int i5 = -1;
                i4 = 0;
                i3 = -1;
                i2 = -1;
                for (LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel : this.completeList) {
                    i5++;
                    if (liveStreamCategoryIdDBModel.getLiveStreamCategoryID().equals(AppConst.PASSWORD_UNSET)) {
                        i4 = i5;
                    }
                    if (liveStreamCategoryIdDBModel.getLiveStreamCategoryName().equals(this.moviesListl.get(i).getLiveStreamCategoryName())) {
                        i3 = i5;
                    } else {
                        i2++;
                    }
                }
            }
            if (!(this.moviesListl == null || this.completeList == null || this.completeList.size() != this.moviesListl.size())) {
                z = true;
            }
            LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel2 = new LiveStreamCategoryIdDBModel();
            liveStreamCategoryIdDBModel2.setLiveStreamCategoryID(AppConst.PASSWORD_UNSET);
            liveStreamCategoryIdDBModel2.setLiveStreamCounter(this.allCount);
            liveStreamCategoryIdDBModel2.setLiveStreamCategoryName(this.activity.getResources().getString(R.string.all));
            this.completeList.set(i4, liveStreamCategoryIdDBModel2);
            this.moviesListl.remove(i);
            if (!z && i3 != -1) {
                try {
                    if (i2 != this.completeList.size()) {
                        this.completeList.remove(i3);
                    }
                } catch (Exception unused) {
                }
            }
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
            /* class com.nst.yourname.view.adapter.PlaylistsCategoriesAdapter.AnonymousClass8 */

            public void run() {
                List unused = PlaylistsCategoriesAdapter.this.filterList = new ArrayList();
                int unused2 = PlaylistsCategoriesAdapter.this.text_size = str.length();
                if (PlaylistsCategoriesAdapter.this.filterList != null) {
                    PlaylistsCategoriesAdapter.this.filterList.clear();
                }
                if (TextUtils.isEmpty(str)) {
                    PlaylistsCategoriesAdapter.this.filterList.addAll(PlaylistsCategoriesAdapter.this.completeList);
                } else {
                    if ((PlaylistsCategoriesAdapter.this.moviesListl != null && PlaylistsCategoriesAdapter.this.moviesListl.size() == 0) || PlaylistsCategoriesAdapter.this.text_last_size > PlaylistsCategoriesAdapter.this.text_size) {
                        List unused3 = PlaylistsCategoriesAdapter.this.moviesListl = PlaylistsCategoriesAdapter.this.completeList;
                    }
                    if (PlaylistsCategoriesAdapter.this.moviesListl != null) {
                        for (LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel : PlaylistsCategoriesAdapter.this.moviesListl) {
                            if (liveStreamCategoryIdDBModel.getLiveStreamCategoryName().toLowerCase().contains(str.toLowerCase())) {
                                PlaylistsCategoriesAdapter.this.filterList.add(liveStreamCategoryIdDBModel);
                            }
                        }
                    }
                }
                ((Activity) PlaylistsCategoriesAdapter.this.context).runOnUiThread(new Runnable() {
                    /* class com.nst.yourname.view.adapter.PlaylistsCategoriesAdapter.AnonymousClass8.AnonymousClass1 */

                    public void run() {
                        if (TextUtils.isEmpty(str)) {
                            List unused = PlaylistsCategoriesAdapter.this.moviesListl = PlaylistsCategoriesAdapter.this.completeList;
                        } else if (!PlaylistsCategoriesAdapter.this.filterList.isEmpty() || PlaylistsCategoriesAdapter.this.filterList.isEmpty()) {
                            List unused2 = PlaylistsCategoriesAdapter.this.moviesListl = PlaylistsCategoriesAdapter.this.filterList;
                        }
                        if (PlaylistsCategoriesAdapter.this.moviesListl != null && PlaylistsCategoriesAdapter.this.moviesListl.size() == 0) {
                            textView.setVisibility(0);
                        }
                        int unused3 = PlaylistsCategoriesAdapter.this.text_last_size = PlaylistsCategoriesAdapter.this.text_size;
                        PlaylistsCategoriesAdapter.this.notifyDataSetChanged();
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
    private class PlaylistsCategoriesAdapterAsync extends AsyncTask<MyViewHolder, Void, Integer> {
        private MyViewHolder MyViewHolder;

        PlaylistsCategoriesAdapterAsync(MyViewHolder myViewHolder) {
            this.MyViewHolder = myViewHolder;
        }

        public void onPreExecute() {
            super.onPreExecute();
            this.MyViewHolder.tvXubCount.setVisibility(8);
        }

        public Integer doInBackground(MyViewHolder... myViewHolderArr) {
            try {
                return Integer.valueOf(PlaylistsCategoriesAdapter.this.liveStreamDBHandler.getFavouriteCount(SharepreferenceDBHandler.getUserID(PlaylistsCategoriesAdapter.this.context)));
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
