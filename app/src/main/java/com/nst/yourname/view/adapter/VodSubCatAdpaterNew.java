package com.nst.yourname.view.adapter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.view.activity.VodActivityLayout;
import com.nst.yourname.view.activity.VodActivityNewFlowSecondSubCategories;
import java.util.ArrayList;
import java.util.List;

public class VodSubCatAdpaterNew extends RecyclerView.Adapter<VodSubCatAdpaterNew.MyViewHolder> {
    public List<LiveStreamCategoryIdDBModel> completeList;
    public Context context;
    public List<LiveStreamCategoryIdDBModel> filterList;
    public LiveStreamDBHandler liveStreamDBHandler;
    public List<LiveStreamCategoryIdDBModel> moviesListl;
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

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: java.util.List<com.nst.yourname.model.LiveStreamCategoryIdDBModel>} */
    /* JADX WARNING: Multi-variable type inference failed */
    public VodSubCatAdpaterNew(List<LiveStreamCategoryIdDBModel> list, Context context2, LiveStreamDBHandler liveStreamDBHandler2) {
        this.filterList = new ArrayList();
        this.filterList.addAll(list);
        this.completeList = list;
        this.moviesListl = list;
        this.context = context2;
        this.liveStreamDBHandler = liveStreamDBHandler2;
    }

    public VodSubCatAdpaterNew() {
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
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate((int) R.layout.layout_vod_new_flow_list_item, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = this.moviesListl.get(i);
        final String liveStreamCategoryName = liveStreamCategoryIdDBModel.getLiveStreamCategoryName();
        final String liveStreamCategoryID = liveStreamCategoryIdDBModel.getLiveStreamCategoryID();
        Bundle bundle = new Bundle();
        bundle.putString(AppConst.CATEGORY_ID, liveStreamCategoryID);
        bundle.putString(AppConst.CATEGORY_NAME, liveStreamCategoryName);
        if (liveStreamCategoryName != null && !liveStreamCategoryName.equals("") && !liveStreamCategoryName.isEmpty()) {
            myViewHolder.tvMovieCategoryName.setText(liveStreamCategoryName);
        }
        int subCatMovieCount = this.liveStreamDBHandler.getSubCatMovieCount(liveStreamCategoryIdDBModel.getLiveStreamCategoryID(), AppConst.EVENT_TYPE_MOVIE);
        if (subCatMovieCount == 0 || subCatMovieCount == -1) {
            myViewHolder.tvXubCount.setText("");
        } else {
            myViewHolder.tvXubCount.setText(String.valueOf(subCatMovieCount));
        }
        myViewHolder.rlOuter.setOnFocusChangeListener(new OnFocusChangeAccountListener(myViewHolder.rlOuter));
        myViewHolder.rlOuter.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.adapter.VodSubCatAdpaterNew.AnonymousClass1 */

            public void onClick(View view) {
                new VodActivityLayout().progressBar(myViewHolder.pbPagingLoader);
                if (myViewHolder.pbPagingLoader != null) {
                    myViewHolder.pbPagingLoader.getIndeterminateDrawable().setColorFilter(-16777216, PorterDuff.Mode.SRC_IN);
                    myViewHolder.pbPagingLoader.setVisibility(0);
                }
                if (VodSubCatAdpaterNew.this.liveStreamDBHandler.getAllMovieCategoriesHavingParentIdNotZero(liveStreamCategoryID).size() > 0) {
                    Intent intent = new Intent(VodSubCatAdpaterNew.this.context, VodActivityNewFlowSecondSubCategories.class);
                    intent.putExtra(AppConst.CATEGORY_ID, liveStreamCategoryID);
                    intent.putExtra(AppConst.CATEGORY_NAME, liveStreamCategoryName);
                    VodSubCatAdpaterNew.this.context.startActivity(intent);
                    return;
                }
                Intent intent2 = new Intent(VodSubCatAdpaterNew.this.context, VodActivityLayout.class);
                intent2.putExtra(AppConst.CATEGORY_ID, liveStreamCategoryID);
                intent2.putExtra(AppConst.CATEGORY_NAME, liveStreamCategoryName);
                VodSubCatAdpaterNew.this.context.startActivity(intent2);
            }
        });
        if (this.moviesListl.size() != 0) {
            myViewHolder.rlOuter.setVisibility(0);
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
            /* class com.nst.yourname.view.adapter.VodSubCatAdpaterNew.AnonymousClass2 */

            public void run() {
                List unused = VodSubCatAdpaterNew.this.filterList = new ArrayList();
                int unused2 = VodSubCatAdpaterNew.this.text_size = str.length();
                if (VodSubCatAdpaterNew.this.filterList != null) {
                    VodSubCatAdpaterNew.this.filterList.clear();
                }
                if (TextUtils.isEmpty(str)) {
                    VodSubCatAdpaterNew.this.filterList.addAll(VodSubCatAdpaterNew.this.completeList);
                } else {
                    if ((VodSubCatAdpaterNew.this.moviesListl != null && VodSubCatAdpaterNew.this.moviesListl.size() == 0) || VodSubCatAdpaterNew.this.text_last_size > VodSubCatAdpaterNew.this.text_size) {
                        List unused3 = VodSubCatAdpaterNew.this.moviesListl = VodSubCatAdpaterNew.this.completeList;
                    }
                    if (VodSubCatAdpaterNew.this.moviesListl != null) {
                        for (LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel : VodSubCatAdpaterNew.this.moviesListl) {
                            if (liveStreamCategoryIdDBModel.getLiveStreamCategoryName() != null && liveStreamCategoryIdDBModel.getLiveStreamCategoryName().toLowerCase().contains(str.toLowerCase())) {
                                VodSubCatAdpaterNew.this.filterList.add(liveStreamCategoryIdDBModel);
                            }
                        }
                    }
                }
                ((Activity) VodSubCatAdpaterNew.this.context).runOnUiThread(new Runnable() {
                    /* class com.nst.yourname.view.adapter.VodSubCatAdpaterNew.AnonymousClass2.AnonymousClass1 */

                    public void run() {
                        if (TextUtils.isEmpty(str)) {
                            List unused = VodSubCatAdpaterNew.this.moviesListl = VodSubCatAdpaterNew.this.completeList;
                        } else if (!VodSubCatAdpaterNew.this.filterList.isEmpty() || VodSubCatAdpaterNew.this.filterList.isEmpty()) {
                            List unused2 = VodSubCatAdpaterNew.this.moviesListl = VodSubCatAdpaterNew.this.filterList;
                        }
                        if (VodSubCatAdpaterNew.this.moviesListl != null && VodSubCatAdpaterNew.this.moviesListl.size() == 0) {
                            textView.setVisibility(0);
                        }
                        int unused3 = VodSubCatAdpaterNew.this.text_last_size = VodSubCatAdpaterNew.this.text_size;
                        VodSubCatAdpaterNew.this.notifyDataSetChanged();
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
