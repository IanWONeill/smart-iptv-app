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
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
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
import com.nst.yourname.model.FavouriteM3UModel;
import com.nst.yourname.model.LiveStreamCategoryIdDBModel;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.M3UCategoriesModel;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.PasswordStatusDBModel;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.view.interfaces.MultiPlayerInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("all")
public class MultiPlayerCategoriesAdapter extends RecyclerView.Adapter<MultiPlayerCategoriesAdapter.MyViewHolder> {
    static final boolean $assertionsDisabled = false;
    AsyncTask AsyncTaskLiveActivityNewFlow = null;
    private AsyncTask AsyncTaskLiveAdapterNewFlow = null;
    private AsyncTask AsyncTaskNSTIJKPlayerSkyActivity = null;
    private ArrayList<LiveStreamsDBModel> AvailableChannelsFirstOpenedCat;
    private int allCount = 0;
    public ArrayList<LiveStreamsDBModel> allStreams;
    public ArrayList<LiveStreamsDBModel> allStreams_with_cat;
    AppBarLayout appbarToolbar;
    String catID = "";
    private PopupWindow categoriesPopUpWindow;
    private ArrayList<PasswordStatusDBModel> categoryWithPasword;
    MultiPlayerChannelsAdapter channelsOnVideoAdapter;
    private PopupWindow channelsPopUpWindow;
    public List<LiveStreamCategoryIdDBModel> completeList;
    public Context context;
    private DatabaseHandler database;
    public DatabaseHandler dbHandeler;
    private ArrayList<FavouriteDBModel> favliveListDetailUnlckedDetail;
    private ArrayList<FavouriteM3UModel> favliveListDetailUnlckedDetailM3U;
    public List<LiveStreamCategoryIdDBModel> filterList = new ArrayList();
    private boolean firstTimeFlag = true;
    public int focusedItem = 0;
    FrameLayout frameLayout;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<String> listPassword = new ArrayList<>();
    public ArrayList<LiveStreamsDBModel> liveListDetailAvailableChannels;
    private ArrayList<LiveStreamsDBModel> liveListDetailUnlckedChannels;
    private ArrayList<LiveStreamsDBModel> liveListDetailUnlckedDetailChannels;
    public LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesSharedPref;
    public List<LiveStreamCategoryIdDBModel> moviesListl;
    private MultiPlayerInterface multiPlayerInterface;
    RecyclerView myRecyclerView;
    public TextView no_record;
    ProgressBar pbLoader;
    public ProgressDialog progressDialog;
    RelativeLayout rl_no_arrangement_found;
    public int text_last_size;
    public int text_size;
    private TextView tv_settings;

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
            /* class com.nst.yourname.view.adapter.MultiPlayerCategoriesAdapter.AnonymousClass1 */

            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (keyEvent.getAction() != 0) {
                    return false;
                }
                if (i == 20) {
                    return MultiPlayerCategoriesAdapter.this.tryMoveSelection(layoutManager, 1);
                }
                if (i == 19) {
                    return MultiPlayerCategoriesAdapter.this.tryMoveSelection(layoutManager, -1);
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

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: java.util.List<com.nst.yourname.model.LiveStreamCategoryIdDBModel>} */
    /* JADX WARNING: Multi-variable type inference failed */
    public MultiPlayerCategoriesAdapter(List<LiveStreamCategoryIdDBModel> list, Context context2, MultiPlayerInterface multiPlayerInterface2, PopupWindow popupWindow, int i) {
        this.filterList.addAll(list);
        this.completeList = list;
        this.moviesListl = list;
        this.context = context2;
        this.liveStreamDBHandler = new LiveStreamDBHandler(context2);
        this.dbHandeler = new DatabaseHandler(context2);
        this.liveListDetailAvailableChannels = new ArrayList<>();
        this.liveListDetailUnlckedChannels = new ArrayList<>();
        this.liveListDetailUnlckedDetailChannels = new ArrayList<>();
        this.AvailableChannelsFirstOpenedCat = new ArrayList<>();
        this.multiPlayerInterface = multiPlayerInterface2;
        this.categoriesPopUpWindow = popupWindow;
        this.database = new DatabaseHandler(context2);
        String liveActivitynewFlowSort = SharepreferenceDBHandler.getLiveActivitynewFlowSort(context2);
        if (liveActivitynewFlowSort.equals("1")) {
            Collections.sort(list, new Comparator<LiveStreamCategoryIdDBModel>() {
                /* class com.nst.yourname.view.adapter.MultiPlayerCategoriesAdapter.AnonymousClass2 */

                public int compare(LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel, LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel2) {
                    return liveStreamCategoryIdDBModel.getLiveStreamCategoryName().compareTo(liveStreamCategoryIdDBModel2.getLiveStreamCategoryName());
                }
            });
        }
        if (liveActivitynewFlowSort.equals("2")) {
            Collections.sort(list, new Comparator<LiveStreamCategoryIdDBModel>() {
                /* class com.nst.yourname.view.adapter.MultiPlayerCategoriesAdapter.AnonymousClass3 */

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
        String liveStreamCategoryName = liveStreamCategoryIdDBModel.getLiveStreamCategoryName();
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
                    /* class com.nst.yourname.view.adapter.MultiPlayerCategoriesAdapter.AnonymousClass4 */

                    public boolean onLongClick(View view) {
                        MultiPlayerCategoriesAdapter.this.popmenu(myViewHolder, liveStreamCategoryID, i);
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
            /* class com.nst.yourname.view.adapter.MultiPlayerCategoriesAdapter.AnonymousClass5 */

            public void onClick(View view) {
                int unused = MultiPlayerCategoriesAdapter.this.focusedItem = myViewHolder.getLayoutPosition();
                MultiPlayerCategoriesAdapter.this.categoriesPopup(MultiPlayerCategoriesAdapter.this.context, view, liveStreamCategoryID);
            }
        });
        myViewHolder.rlOuter.setOnFocusChangeListener(new OnFocusChangeAccountListener(myViewHolder.rlOuter));
    }

    @SuppressLint({"ResourceType"})
    public void categoriesPopup(Context context2, View view, String str) {
        this.catID = str;
        View inflate = ((LayoutInflater) context2.getSystemService("layout_inflater")).inflate((int) R.layout.categories_popup, (LinearLayout) view.findViewById(R.id.ll_root));
        this.pbLoader = (ProgressBar) inflate.findViewById(R.id.pb_loader);
        this.myRecyclerView = (RecyclerView) inflate.findViewById(R.id.my_recycler_view);
        this.rl_no_arrangement_found = (RelativeLayout) inflate.findViewById(R.id.rl_no_arrangement_found);
        this.appbarToolbar = (AppBarLayout) inflate.findViewById(R.id.appbar_toolbar);
        this.no_record = (TextView) inflate.findViewById(R.id.no_record);
        this.tv_settings = (TextView) inflate.findViewById(R.id.tv_settings);
        this.tv_settings.setText(context2.getResources().getString(R.string.live_channels));
        this.channelsPopUpWindow = new PopupWindow(context2);
        this.channelsPopUpWindow.setContentView(inflate);
        this.channelsPopUpWindow.setWidth(-1);
        this.channelsPopUpWindow.setHeight(-1);
        this.channelsPopUpWindow.setFocusable(true);
        this.channelsPopUpWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            /* class com.nst.yourname.view.adapter.MultiPlayerCategoriesAdapter.AnonymousClass6 */

            public void onDismiss() {
            }
        });
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(0);
        }
        if (this.appbarToolbar != null) {
            this.appbarToolbar.setBackground(context2.getResources().getDrawable(R.color.trasparent_light));
        }
        this.listPassword = getPasswordSetCategories();
        if (this.catID != null && !this.catID.equals("") && this.catID.equals(AppConst.PASSWORD_UNSET)) {
            allChannels();
        } else if (this.catID == null || this.catID.equals("") || !this.catID.equals("-1")) {
            allChannelsWithCat();
        } else {
            allFavourites();
        }
        this.channelsPopUpWindow.showAtLocation(inflate, 1, 0, 0);
    }

    private ArrayList<String> getPasswordSetCategories() {
        this.categoryWithPasword = this.liveStreamDBHandler.getAllPasswordStatus(SharepreferenceDBHandler.getUserID(this.context));
        if (this.categoryWithPasword != null) {
            Iterator<PasswordStatusDBModel> it = this.categoryWithPasword.iterator();
            while (it.hasNext()) {
                PasswordStatusDBModel next = it.next();
                if (next.getPasswordStatus().equals("1")) {
                    this.listPassword.add(next.getPasswordStatusCategoryId());
                }
            }
        }
        return this.listPassword;
    }

    public void allChannels() {
        checkIfAsyncTaskRunning();
        this.AsyncTaskNSTIJKPlayerSkyActivity = new MultiPlayerChannelsAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "all_channels");
    }

    public void allFavourites() {
        checkIfAsyncTaskRunning();
        this.AsyncTaskNSTIJKPlayerSkyActivity = new MultiPlayerChannelsAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "get_fav");
    }

    public void allChannelsWithCat() {
        checkIfAsyncTaskRunning();
        this.AsyncTaskNSTIJKPlayerSkyActivity = new MultiPlayerChannelsAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "all_channels_with_cat");
    }

    private void checkIfAsyncTaskRunning() {
        if (this.AsyncTaskNSTIJKPlayerSkyActivity == null || !this.AsyncTaskNSTIJKPlayerSkyActivity.getStatus().equals(AsyncTask.Status.RUNNING)) {
            SharepreferenceDBHandler.setAsyncTaskStatus(0, this.context);
            return;
        }
        SharepreferenceDBHandler.setAsyncTaskStatus(1, this.context);
        this.AsyncTaskNSTIJKPlayerSkyActivity.cancel(true);
    }

    @SuppressLint({"StaticFieldLeak"})
    class MultiPlayerChannelsAsync extends AsyncTask<String, Void, String> {
        MultiPlayerChannelsAsync() {
        }

        public void onPreExecute() {
            super.onPreExecute();
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
                if (hashCode != -74797390) {
                    if (hashCode != 47612238) {
                        if (hashCode == 613425326) {
                            if (str.equals("all_channels")) {
                                switch (c) {
                                    case 0:
                                        return MultiPlayerCategoriesAdapter.this.allChannelsAsync();
                                    case 1:
                                        return MultiPlayerCategoriesAdapter.this.allChannelsWithCatAsync();
                                    case 2:
                                        return MultiPlayerCategoriesAdapter.this.getFavouritesAsync();
                                    default:
                                        return null;
                                }
                            }
                        }
                    } else if (str.equals("all_channels_with_cat")) {
                        c = 1;
                        switch (c) {
                        }
                    }
                } else if (str.equals("get_fav")) {
                    c = 2;
                    switch (c) {
                    }
                }
                c = 65535;
                switch (c) {
                }
            /*} catch (Exception unused) {
                return "error";
            }*/
            //ToDO: return statement...
            return str;
        }

        public void onPostExecute(String str) {
            super.onPostExecute((String) str);
            try {
                if (MultiPlayerCategoriesAdapter.this.liveListDetailAvailableChannels != null && MultiPlayerCategoriesAdapter.this.liveListDetailAvailableChannels.size() != 0) {
                    MultiPlayerCategoriesAdapter.this.setChannelListAdapter(MultiPlayerCategoriesAdapter.this.liveListDetailAvailableChannels);
                } else if (MultiPlayerCategoriesAdapter.this.pbLoader != null) {
                    MultiPlayerCategoriesAdapter.this.pbLoader.setVisibility(4);
                    MultiPlayerCategoriesAdapter.this.no_record.setVisibility(0);
                }
            } catch (Exception unused) {
            }
        }
    }

    public void setChannelListAdapter(ArrayList<LiveStreamsDBModel> arrayList) {
        this.channelsOnVideoAdapter = new MultiPlayerChannelsAdapter(arrayList, this.context, this.channelsPopUpWindow, this.multiPlayerInterface, this.categoriesPopUpWindow);
        this.gridLayoutManager = new GridLayoutManager(this.context, 1);
        this.myRecyclerView.setLayoutManager(this.gridLayoutManager);
        this.myRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.myRecyclerView.setAdapter(this.channelsOnVideoAdapter);
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(4);
        }
    }

    private ArrayList<LiveStreamsDBModel> getUnlockedChannels(ArrayList<LiveStreamsDBModel> arrayList, ArrayList<String> arrayList2) {
        try {
            Iterator<LiveStreamsDBModel> it = arrayList.iterator();
            while (it.hasNext()) {
                LiveStreamsDBModel next = it.next();
                boolean z = false;
                Iterator<String> it2 = arrayList2.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    if (next.getCategoryId().equals(it2.next())) {
                        z = true;
                        break;
                    }
                }
                if (!z) {
                    this.liveListDetailUnlckedChannels.add(next);
                }
            }
        } catch (Exception unused) {
        }
        return this.liveListDetailUnlckedChannels;
    }

    public String allChannelsAsync() {
        try {
            if (!(this.liveListDetailAvailableChannels == null || this.liveListDetailUnlckedChannels == null)) {
                this.liveListDetailAvailableChannels.clear();
                this.liveListDetailUnlckedChannels.clear();
            }
            int parentalStatusCount = this.liveStreamDBHandler.getParentalStatusCount(SharepreferenceDBHandler.getUserID(this.context));
            this.allStreams = this.liveStreamDBHandler.getAllLiveStreasWithCategoryId(AppConst.PASSWORD_UNSET, "live");
            if (parentalStatusCount <= 0 || this.allStreams == null) {
                this.liveListDetailAvailableChannels = this.allStreams;
                return "all_channels";
            }
            if (this.listPassword != null) {
                this.liveListDetailUnlckedDetailChannels = getUnlockedChannels(this.allStreams, this.listPassword);
            }
            this.liveListDetailAvailableChannels = this.liveListDetailUnlckedDetailChannels;
            return "all_channels";
        } catch (Exception unused) {
            return "all_channels";
        }
    }

    public String getFavouritesAsync() {
        try {
            if (!(this.liveListDetailAvailableChannels == null || this.liveListDetailUnlckedChannels == null)) {
                this.liveListDetailAvailableChannels.clear();
                this.liveListDetailUnlckedChannels.clear();
            }
            this.allStreams_with_cat = new ArrayList<>();
            getFavourites();
            this.liveListDetailAvailableChannels = this.allStreams_with_cat;
            return "get_fav";
        } catch (Exception unused) {
            return "get_fav";
        }
    }

    public String allChannelsWithCatAsync() {
        try {
            if (!(this.liveListDetailAvailableChannels == null || this.liveListDetailUnlckedChannels == null)) {
                this.liveListDetailAvailableChannels.clear();
                this.liveListDetailUnlckedChannels.clear();
            }
            if (this.liveStreamDBHandler != null) {
                this.AvailableChannelsFirstOpenedCat = this.liveStreamDBHandler.getAllLiveStreasWithCategoryId(this.catID, "live");
            }
            this.liveListDetailAvailableChannels = this.AvailableChannelsFirstOpenedCat;
            return "all_channels_with_cat";
        } catch (Exception unused) {
            return "all_channels_with_cat";
        }
    }

    public void getFavourites() {
        try {
            if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                new ArrayList();
                if (this.liveStreamDBHandler != null) {
                    ArrayList<FavouriteM3UModel> favouriteM3U = this.liveStreamDBHandler.getFavouriteM3U("live");
                    ArrayList<LiveStreamsDBModel> arrayList = new ArrayList<>();
                    if (this.listPassword != null && this.listPassword.size() > 0 && favouriteM3U != null && favouriteM3U.size() > 0) {
                        favouriteM3U = getfavUnlovkedM3U(favouriteM3U, this.listPassword);
                    }
                    Iterator<FavouriteM3UModel> it = favouriteM3U.iterator();
                    while (it.hasNext()) {
                        FavouriteM3UModel next = it.next();
                        ArrayList<LiveStreamsDBModel> m3UFavouriteRow = this.liveStreamDBHandler.getM3UFavouriteRow(next.getCategoryID(), next.getUrl());
                        if (m3UFavouriteRow != null && m3UFavouriteRow.size() > 0) {
                            arrayList.add(m3UFavouriteRow.get(0));
                        }
                    }
                    if (arrayList.size() != 0) {
                        this.allStreams_with_cat = arrayList;
                        return;
                    }
                    return;
                }
                return;
            }
            new ArrayList();
            if (this.database != null) {
                ArrayList<FavouriteDBModel> allFavourites = this.database.getAllFavourites("live", SharepreferenceDBHandler.getUserID(this.context));
                ArrayList<LiveStreamsDBModel> arrayList2 = new ArrayList<>();
                if (this.listPassword != null && this.listPassword.size() > 0 && allFavourites != null && allFavourites.size() > 0) {
                    allFavourites = getfavUnlovked(allFavourites, this.listPassword);
                }
                Iterator<FavouriteDBModel> it2 = allFavourites.iterator();
                while (it2.hasNext()) {
                    FavouriteDBModel next2 = it2.next();
                    LiveStreamsDBModel liveStreamFavouriteRow = new LiveStreamDBHandler(this.context).getLiveStreamFavouriteRow(next2.getCategoryID(), String.valueOf(next2.getStreamID()));
                    if (liveStreamFavouriteRow != null) {
                        arrayList2.add(liveStreamFavouriteRow);
                    }
                }
                if (arrayList2.size() != 0) {
                    this.allStreams_with_cat = arrayList2;
                }
            }
        } catch (Exception unused) {
        }
    }

    private ArrayList<FavouriteDBModel> getfavUnlovked(ArrayList<FavouriteDBModel> arrayList, ArrayList<String> arrayList2) {
        try {
            this.favliveListDetailUnlckedDetail = new ArrayList<>();
            Iterator<FavouriteDBModel> it = arrayList.iterator();
            while (it.hasNext()) {
                FavouriteDBModel next = it.next();
                boolean z = false;
                Iterator<String> it2 = arrayList2.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    if (next.getCategoryID().equals(it2.next())) {
                        z = true;
                        break;
                    }
                }
                if (!z) {
                    this.favliveListDetailUnlckedDetail.add(next);
                }
            }
        } catch (Exception unused) {
        }
        return this.favliveListDetailUnlckedDetail;
    }

    private ArrayList<FavouriteM3UModel> getfavUnlovkedM3U(ArrayList<FavouriteM3UModel> arrayList, ArrayList<String> arrayList2) {
        this.favliveListDetailUnlckedDetailM3U = new ArrayList<>();
        if (arrayList == null) {
            return null;
        }
        try {
            Iterator<FavouriteM3UModel> it = arrayList.iterator();
            while (it.hasNext()) {
                FavouriteM3UModel next = it.next();
                boolean z = false;
                if (arrayList2 != null) {
                    Iterator<String> it2 = arrayList2.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        if (next.getCategoryID().equals(it2.next())) {
                            z = true;
                            break;
                        }
                    }
                    if (!z) {
                        this.favliveListDetailUnlckedDetailM3U.add(next);
                    }
                }
            }
            return this.favliveListDetailUnlckedDetailM3U;
        } catch (Exception unused) {
            return null;
        }
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
                /* class com.nst.yourname.view.adapter.MultiPlayerCategoriesAdapter.AnonymousClass7 */

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
                    ArrayList<LiveStreamsDBModel> allM3UWithCategoryId = MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.getAllM3UWithCategoryId(str, false);
                    if (str.equals(AppConst.PASSWORD_UNSET)) {
                        ArrayList<LiveStreamCategoryIdDBModel> allm3uCategories = MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.getAllm3uCategories();
                        if (allM3UWithCategoryId == null || allM3UWithCategoryId.size() <= 0) {
                            return "movie_move";
                        }
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, AppConst.EVENT_TYPE_MOVIE);
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.addMultipleCategoriesM3U(allm3uCategories, AppConst.EVENT_TYPE_MOVIE);
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, AppConst.EVENT_TYPE_MOVIE);
                        return "movie_move";
                    }
                    if (MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.checkCategoryExistsM3U(str, AppConst.EVENT_TYPE_MOVIE)) {
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, AppConst.EVENT_TYPE_MOVIE);
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, AppConst.EVENT_TYPE_MOVIE);
                    } else {
                        M3UCategoriesModel m3UCategoriesModel = new M3UCategoriesModel();
                        if (!str.equals("")) {
                            m3UCategoriesModel = MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.getCatByCatIDALLM3U(str);
                        }
                        if (allM3UWithCategoryId != null && allM3UWithCategoryId.size() > 0) {
                            MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                            MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                            MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                            MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                            MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                            MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, AppConst.EVENT_TYPE_MOVIE);
                            if (!str.equals("")) {
                                MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.addMovieCategoriesM3U(m3UCategoriesModel);
                            }
                            MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, AppConst.EVENT_TYPE_MOVIE);
                        }
                    }
                    MultiPlayerCategoriesAdapter.this.notifyAdapter(i);
                    return "movie_move";
                }

                public String moveToSeries() {
                    ArrayList<LiveStreamsDBModel> allM3UWithCategoryId = MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.getAllM3UWithCategoryId(str, false);
                    if (str.equals(AppConst.PASSWORD_UNSET)) {
                        ArrayList<LiveStreamCategoryIdDBModel> allm3uCategories = MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.getAllm3uCategories();
                        if (allM3UWithCategoryId == null || allM3UWithCategoryId.size() <= 0) {
                            return "series_move";
                        }
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, "series");
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.addMultipleCategoriesM3U(allm3uCategories, "series");
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "series");
                        return "series_move";
                    }
                    if (MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.checkCategoryExistsM3U(str, "series")) {
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, "series");
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "series");
                    } else {
                        M3UCategoriesModel m3UCategoriesModel = new M3UCategoriesModel();
                        if (!str.equals("")) {
                            m3UCategoriesModel = MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.getCatByCatIDALLM3U(str);
                        }
                        if (allM3UWithCategoryId != null && allM3UWithCategoryId.size() > 0) {
                            MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                            MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                            MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                            MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.removeMovieCategoriesM3U(str);
                            MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.removeSeriesCategoriesM3U(str);
                            MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.addAllAvailableChannelM3U(allM3UWithCategoryId, "series");
                            if (!str.equals("")) {
                                MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.addSeriesCategoriesM3U(m3UCategoriesModel);
                            }
                            MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "series");
                        }
                    }
                    MultiPlayerCategoriesAdapter.this.notifyAdapter(i);
                    return "series_move";
                }

                public String removefromLive() {
                    MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.updateMoveToStatus(str, "");
                    MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.removeAvailableChannelM3U(str);
                    if (!str.equals("")) {
                        MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.removeLiveCategoriesM3U(str);
                    }
                    MultiPlayerCategoriesAdapter.this.notifyAdapter(i);
                    return "live_remove";
                }

                @SuppressLint({"StaticFieldLeak"})
                /* renamed from: com.nst.yourname.view.adapter.MultiPlayerCategoriesAdapter$7$AsyncTaskForMovingRecords */
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
                       // try {
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
                                            com.nst.yourname.miscelleneious.common.Utils.showToastLong(MultiPlayerCategoriesAdapter.this.context, MultiPlayerCategoriesAdapter.this.context.getResources().getString(R.string.added_to_movies));
                                            break;
                                        case 1:
                                            com.nst.yourname.miscelleneious.common.Utils.showToastLong(MultiPlayerCategoriesAdapter.this.context, MultiPlayerCategoriesAdapter.this.context.getResources().getString(R.string.added_to_series));
                                            break;
                                        case 2:
                                            com.nst.yourname.miscelleneious.common.Utils.showToastLong(MultiPlayerCategoriesAdapter.this.context, MultiPlayerCategoriesAdapter.this.context.getResources().getString(R.string.removed_from_live));
                                            break;
                                    }
                                    MultiPlayerCategoriesAdapter.this.notifyDataSetChanged();
                                }
                            } else if (str.equals("movie_move")) {
                                c = 0;
                                switch (c) {
                                }
                                MultiPlayerCategoriesAdapter.this.notifyDataSetChanged();
                            }
                        } else if (str.equals("series_move")) {
                            c = 1;
                            switch (c) {
                            }
                            MultiPlayerCategoriesAdapter.this.notifyDataSetChanged();
                        }
                        c = 65535;
                        switch (c) {
                        }
                        MultiPlayerCategoriesAdapter.this.notifyDataSetChanged();
                    }
                }

                public void showProgressDialogBox() {
                    if (MultiPlayerCategoriesAdapter.this.context != null) {
                        ProgressDialog unused = MultiPlayerCategoriesAdapter.this.progressDialog = new ProgressDialog(MultiPlayerCategoriesAdapter.this.context);
                        MultiPlayerCategoriesAdapter.this.progressDialog.setMessage(MultiPlayerCategoriesAdapter.this.context.getResources().getString(R.string.please_wait));
                        MultiPlayerCategoriesAdapter.this.progressDialog.setCanceledOnTouchOutside(false);
                        MultiPlayerCategoriesAdapter.this.progressDialog.setCancelable(false);
                        MultiPlayerCategoriesAdapter.this.progressDialog.setProgressStyle(0);
                        MultiPlayerCategoriesAdapter.this.progressDialog.show();
                    }
                }

                public void hideProgressDialogBox() {
                    if (MultiPlayerCategoriesAdapter.this.context != null && MultiPlayerCategoriesAdapter.this.progressDialog != null) {
                        MultiPlayerCategoriesAdapter.this.progressDialog.dismiss();
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
            /* class com.nst.yourname.view.adapter.MultiPlayerCategoriesAdapter.AnonymousClass8 */

            public void run() {
                List unused = MultiPlayerCategoriesAdapter.this.filterList = new ArrayList();
                int unused2 = MultiPlayerCategoriesAdapter.this.text_size = str.length();
                if (MultiPlayerCategoriesAdapter.this.filterList != null) {
                    MultiPlayerCategoriesAdapter.this.filterList.clear();
                }
                if (TextUtils.isEmpty(str)) {
                    MultiPlayerCategoriesAdapter.this.filterList.addAll(MultiPlayerCategoriesAdapter.this.completeList);
                } else {
                    if ((MultiPlayerCategoriesAdapter.this.moviesListl != null && MultiPlayerCategoriesAdapter.this.moviesListl.size() == 0) || MultiPlayerCategoriesAdapter.this.text_last_size > MultiPlayerCategoriesAdapter.this.text_size) {
                        List unused3 = MultiPlayerCategoriesAdapter.this.moviesListl = MultiPlayerCategoriesAdapter.this.completeList;
                    }
                    if (MultiPlayerCategoriesAdapter.this.moviesListl != null) {
                        for (LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel : MultiPlayerCategoriesAdapter.this.moviesListl) {
                            if (liveStreamCategoryIdDBModel.getLiveStreamCategoryName().toLowerCase().contains(str.toLowerCase())) {
                                MultiPlayerCategoriesAdapter.this.filterList.add(liveStreamCategoryIdDBModel);
                            }
                        }
                    }
                }
                ((Activity) MultiPlayerCategoriesAdapter.this.context).runOnUiThread(new Runnable() {
                    /* class com.nst.yourname.view.adapter.MultiPlayerCategoriesAdapter.AnonymousClass8.AnonymousClass1 */

                    public void run() {
                        if (TextUtils.isEmpty(str)) {
                            List unused = MultiPlayerCategoriesAdapter.this.moviesListl = MultiPlayerCategoriesAdapter.this.completeList;
                        } else if (!MultiPlayerCategoriesAdapter.this.filterList.isEmpty() || MultiPlayerCategoriesAdapter.this.filterList.isEmpty()) {
                            List unused2 = MultiPlayerCategoriesAdapter.this.moviesListl = MultiPlayerCategoriesAdapter.this.filterList;
                        }
                        if (MultiPlayerCategoriesAdapter.this.moviesListl != null && MultiPlayerCategoriesAdapter.this.moviesListl.size() == 0) {
                            textView.setVisibility(0);
                        }
                        int unused3 = MultiPlayerCategoriesAdapter.this.text_last_size = MultiPlayerCategoriesAdapter.this.text_size;
                        MultiPlayerCategoriesAdapter.this.notifyDataSetChanged();
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
                if (SharepreferenceDBHandler.getCurrentAPPType(MultiPlayerCategoriesAdapter.this.context).equals(AppConst.TYPE_M3U)) {
                    return Integer.valueOf(MultiPlayerCategoriesAdapter.this.liveStreamDBHandler.getFavouriteCountM3U("live"));
                }
                return Integer.valueOf(MultiPlayerCategoriesAdapter.this.dbHandeler.getFavouriteCount("live", SharepreferenceDBHandler.getUserID(MultiPlayerCategoriesAdapter.this.context)));
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
