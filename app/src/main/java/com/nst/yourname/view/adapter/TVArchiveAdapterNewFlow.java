package com.nst.yourname.view.adapter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.PasswordStatusDBModel;
import com.nst.yourname.view.activity.TVArchiveActivityLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("ALL")
public class TVArchiveAdapterNewFlow extends RecyclerView.Adapter<TVArchiveAdapterNewFlow.MyViewHolder> {
    private int adapterPosition;
    private ArrayList<PasswordStatusDBModel> categoryWithPasword;
    public List<LiveStreamCategoryIdDBModel> completeList;
    public Context context;
    private DatabaseHandler dbHandeler;
    public List<LiveStreamCategoryIdDBModel> filterList = new ArrayList();
    private boolean firstTimeFlag = true;
    public int focusedItem = 0;
    private ArrayList<String> listPassword = new ArrayList<>();
    private ArrayList<LiveStreamsDBModel> liveListDetailAvailable;
    private ArrayList<LiveStreamsDBModel> liveListDetailUnlcked;
    private ArrayList<LiveStreamsDBModel> liveListDetailUnlckedDetail;
    private LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesSharedPref;
    public List<LiveStreamCategoryIdDBModel> moviesListl;
    private String selected_language = "";
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
                return;
            }
            throw new IllegalStateException("Bindings already cleared.");
        }
    }*/

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setOnKeyListener(new View.OnKeyListener() {
            /* class com.nst.yourname.view.adapter.TVArchiveAdapterNewFlow.AnonymousClass1 */

            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (keyEvent.getAction() != 0) {
                    return false;
                }
                if (i == 20) {
                    return TVArchiveAdapterNewFlow.this.tryMoveSelection(layoutManager, 1);
                }
                if (i == 19) {
                    return TVArchiveAdapterNewFlow.this.tryMoveSelection(layoutManager, -1);
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

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: java.util.List<com.nst.yourname.model.LiveStreamCategoryIdDBModel>} */
    /* JADX WARNING: Multi-variable type inference failed */
    public TVArchiveAdapterNewFlow(List<LiveStreamCategoryIdDBModel> list, Context context2) {
        this.filterList.addAll(list);
        this.completeList = list;
        this.moviesListl = list;
        this.context = context2;
        this.liveStreamDBHandler = new LiveStreamDBHandler(context2);
        this.dbHandeler = new DatabaseHandler(context2);
        this.selected_language = context2.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_LANGUAGE, 0).getString(AppConst.LOGIN_PREF_SELECTED_LANGUAGE, "");
        String string = context2.getSharedPreferences(AppConst.LOGIN_PREF_SORT_CATCH, 0).getString(AppConst.LOGIN_PREF_SORT, "");
        if (string.equals("1")) {
            Collections.sort(list, new Comparator<LiveStreamCategoryIdDBModel>() {
                /* class com.nst.yourname.view.adapter.TVArchiveAdapterNewFlow.AnonymousClass2 */

                public int compare(LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel, LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel2) {
                    return liveStreamCategoryIdDBModel.getLiveStreamCategoryName().compareTo(liveStreamCategoryIdDBModel2.getLiveStreamCategoryName());
                }
            });
        }
        if (string.equals("2")) {
            Collections.sort(list, new Comparator<LiveStreamCategoryIdDBModel>() {
                /* class com.nst.yourname.view.adapter.TVArchiveAdapterNewFlow.AnonymousClass3 */

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
        if (this.selected_language.equalsIgnoreCase("Arabic")) {
            imageView.setImageResource(R.drawable.left_icon_cat);
        }
        return new MyViewHolder(inflate);
    }

    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
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
            String liveStreamCategoryID2 = liveStreamCategoryIdDBModel.getLiveStreamCategoryID();
            char c = 65535;
            if (liveStreamCategoryID2.hashCode() == 48) {
                if (liveStreamCategoryID2.equals(AppConst.PASSWORD_UNSET)) {
                    c = 0;
                }
            }
            if (c != 0) {
                myViewHolder.tvXubCount.setText(String.valueOf(liveStreamCategoryIdDBModel.getLiveStreamCounter()));
            } else {
                ArrayList<LiveStreamsDBModel> allLiveStreamsArchive = this.liveStreamDBHandler.getAllLiveStreamsArchive(AppConst.PASSWORD_UNSET);
                if (allLiveStreamsArchive == null || allLiveStreamsArchive.size() == 0) {
                    myViewHolder.tvXubCount.setText("");
                } else {
                    myViewHolder.tvXubCount.setText(String.valueOf(allLiveStreamsArchive.size()));
                }
            }
            if (this.context != null && (this.context.getResources().getConfiguration().screenLayout & 15) == 3 && i == this.focusedItem) {
                myViewHolder.rlOuter.requestFocus();
                performScaleXAnimation(1.09f, myViewHolder.rlOuter);
                performScaleYAnimation(1.09f, myViewHolder.rlOuter);
                myViewHolder.rlOuter.setBackgroundResource(R.drawable.shape_list_categories_focused);
            }
            myViewHolder.rlOuter.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.TVArchiveAdapterNewFlow.AnonymousClass4 */

                public void onClick(View view) {
                    int unused = TVArchiveAdapterNewFlow.this.focusedItem = myViewHolder.getLayoutPosition();
                    Intent intent = new Intent(TVArchiveAdapterNewFlow.this.context, TVArchiveActivityLayout.class);
                    intent.putExtra(AppConst.CATEGORY_ID, liveStreamCategoryID);
                    intent.putExtra(AppConst.CATEGORY_NAME, liveStreamCategoryName);
                    TVArchiveAdapterNewFlow.this.context.startActivity(intent);
                }
            });
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
            /* class com.nst.yourname.view.adapter.TVArchiveAdapterNewFlow.AnonymousClass5 */

            public void run() {
                List unused = TVArchiveAdapterNewFlow.this.filterList = new ArrayList();
                int unused2 = TVArchiveAdapterNewFlow.this.text_size = str.length();
                if (TVArchiveAdapterNewFlow.this.filterList != null) {
                    TVArchiveAdapterNewFlow.this.filterList.clear();
                }
                if (TextUtils.isEmpty(str)) {
                    TVArchiveAdapterNewFlow.this.filterList.addAll(TVArchiveAdapterNewFlow.this.completeList);
                } else {
                    if ((TVArchiveAdapterNewFlow.this.moviesListl != null && TVArchiveAdapterNewFlow.this.moviesListl.size() == 0) || TVArchiveAdapterNewFlow.this.text_last_size > TVArchiveAdapterNewFlow.this.text_size) {
                        List unused3 = TVArchiveAdapterNewFlow.this.moviesListl = TVArchiveAdapterNewFlow.this.completeList;
                    }
                    if (TVArchiveAdapterNewFlow.this.moviesListl != null) {
                        for (LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel : TVArchiveAdapterNewFlow.this.moviesListl) {
                            if (liveStreamCategoryIdDBModel.getLiveStreamCategoryName().toLowerCase().contains(str.toLowerCase())) {
                                TVArchiveAdapterNewFlow.this.filterList.add(liveStreamCategoryIdDBModel);
                            }
                        }
                    }
                }
                ((Activity) TVArchiveAdapterNewFlow.this.context).runOnUiThread(new Runnable() {
                    /* class com.nst.yourname.view.adapter.TVArchiveAdapterNewFlow.AnonymousClass5.AnonymousClass1 */

                    public void run() {
                        if (TextUtils.isEmpty(str)) {
                            List unused = TVArchiveAdapterNewFlow.this.moviesListl = TVArchiveAdapterNewFlow.this.completeList;
                        } else if (!TVArchiveAdapterNewFlow.this.filterList.isEmpty() || TVArchiveAdapterNewFlow.this.filterList.isEmpty()) {
                            List unused2 = TVArchiveAdapterNewFlow.this.moviesListl = TVArchiveAdapterNewFlow.this.filterList;
                        }
                        if (TVArchiveAdapterNewFlow.this.moviesListl != null && TVArchiveAdapterNewFlow.this.moviesListl.size() == 0) {
                            textView.setVisibility(0);
                        }
                        int unused3 = TVArchiveAdapterNewFlow.this.text_last_size = TVArchiveAdapterNewFlow.this.text_size;
                        TVArchiveAdapterNewFlow.this.notifyDataSetChanged();
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
}
