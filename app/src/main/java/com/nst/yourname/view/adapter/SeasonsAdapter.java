package com.nst.yourname.view.adapter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.nst.yourname.model.EpisodesUsingSinglton;
import com.nst.yourname.model.callback.GetEpisdoeDetailsCallback;
import com.nst.yourname.model.callback.SeasonsDetailCallback;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.SeriesStreamsDatabaseHandler;
import com.nst.yourname.view.activity.EpisodeDetailActivity;
import java.util.ArrayList;
import java.util.List;

public class SeasonsAdapter extends RecyclerView.Adapter<SeasonsAdapter.MyViewHolder> {
    public List<SeasonsDetailCallback> completeList;
    public Context context;
    private DatabaseHandler dbHandeler;
    public List<GetEpisdoeDetailsCallback> episodeList;
    public List<SeasonsDetailCallback> filterList;
    private LiveStreamDBHandler liveStreamDBHandler;
    public List<SeasonsDetailCallback> moviesListl;
    private String seriesCover;
    private String seriesName;
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

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: java.util.List<com.nst.yourname.model.callback.SeasonsDetailCallback>} */
    /* JADX WARNING: Multi-variable type inference failed */
    public SeasonsAdapter(List<GetEpisdoeDetailsCallback> list, List<SeasonsDetailCallback> list2, Context context2, String str, String str2) {
        this.seriesName = "";
        this.seriesCover = "";
        this.filterList = new ArrayList();
        this.filterList.addAll(list2);
        this.completeList = list2;
        this.moviesListl = list2;
        this.context = context2;
        this.liveStreamDBHandler = new LiveStreamDBHandler(context2);
        this.dbHandeler = new DatabaseHandler(context2);
        this.seriesStreamsDatabaseHandler = new SeriesStreamsDatabaseHandler(context2);
        this.episodeList = list;
        this.seriesName = str;
        this.seriesCover = str2;
    }

    public SeasonsAdapter() {
        this.seriesName = "";
        this.seriesCover = "";
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

    @SuppressLint({"SetTextI18n"})
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final String str;
        SeasonsDetailCallback seasonsDetailCallback = this.moviesListl.get(i);
        String name = seasonsDetailCallback.getName();
        String coverBig = seasonsDetailCallback.getCoverBig();
        seasonsDetailCallback.getEpisodeCount().intValue();
        final int intValue = seasonsDetailCallback.getSeasonNumber().intValue();
        seasonsDetailCallback.getCoverBig();
        Bundle bundle = new Bundle();
        if (!seasonsDetailCallback.getCoverBig().equals("") || !seasonsDetailCallback.getCoverBig().isEmpty()) {
            str = seasonsDetailCallback.getCoverBig();
        } else {
            str = this.seriesCover;
        }
        bundle.putString(AppConst.CATEGORY_ID, coverBig);
        bundle.putString(AppConst.CATEGORY_NAME, name);
        if (name != null && !name.equals("") && !name.isEmpty()) {
            myViewHolder.tvMovieCategoryName.setText(name);
        }
        if (!(this.context == null || name == null || !name.equals("") || !name.isEmpty() || intValue == -1)) {
            TextView textView = myViewHolder.tvMovieCategoryName;
            textView.setText(this.context.getResources().getString(R.string.season_number) + " " + String.valueOf(intValue));
        }
        myViewHolder.rlOuter.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.adapter.SeasonsAdapter.AnonymousClass1 */

            public void onClick(View view) {
                EpisodesUsingSinglton.getInstance().setEpisodeList(SeasonsAdapter.this.episodeList);
                Intent intent = new Intent(SeasonsAdapter.this.context, EpisodeDetailActivity.class);
                intent.putExtra(AppConst.SEASON_NUMBER, intValue);
                intent.putExtra(AppConst.SEASON_COVER_BIG, str);
                SeasonsAdapter.this.context.startActivity(intent);
            }
        });
        myViewHolder.rlOuter.setOnFocusChangeListener(new OnFocusChangeAccountListener(myViewHolder.rlOuter));
    }

    public List<GetEpisdoeDetailsCallback> getEpisodeList() {
        return this.episodeList;
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
            /* class com.nst.yourname.view.adapter.SeasonsAdapter.AnonymousClass2 */

            public void run() {
                List unused = SeasonsAdapter.this.filterList = new ArrayList();
                SeasonsAdapter.this.text_size = str.length();
                if (SeasonsAdapter.this.filterList != null) {
                    SeasonsAdapter.this.filterList.clear();
                }
                if (TextUtils.isEmpty(str)) {
                    SeasonsAdapter.this.filterList.addAll(SeasonsAdapter.this.completeList);
                } else {
                    if ((SeasonsAdapter.this.moviesListl != null && SeasonsAdapter.this.moviesListl.size() == 0) || SeasonsAdapter.this.text_last_size > SeasonsAdapter.this.text_size) {
                        List unused2 = SeasonsAdapter.this.moviesListl = SeasonsAdapter.this.completeList;
                    }
                    if (SeasonsAdapter.this.moviesListl != null) {
                        for (SeasonsDetailCallback seasonsDetailCallback : SeasonsAdapter.this.moviesListl) {
                            if (seasonsDetailCallback.getName().toLowerCase().contains(str.toLowerCase()) || seasonsDetailCallback.getSeasonNumber().toString().toLowerCase().contains(str.toLowerCase())) {
                                SeasonsAdapter.this.filterList.add(seasonsDetailCallback);
                            }
                        }
                    }
                }
                ((Activity) SeasonsAdapter.this.context).runOnUiThread(new Runnable() {
                    /* class com.nst.yourname.view.adapter.SeasonsAdapter.AnonymousClass2.AnonymousClass1 */

                    public void run() {
                        if (TextUtils.isEmpty(str)) {
                            List unused = SeasonsAdapter.this.moviesListl = SeasonsAdapter.this.completeList;
                        } else if (!SeasonsAdapter.this.filterList.isEmpty() || SeasonsAdapter.this.filterList.isEmpty()) {
                            List unused2 = SeasonsAdapter.this.moviesListl = SeasonsAdapter.this.filterList;
                        }
                        if (SeasonsAdapter.this.moviesListl != null && SeasonsAdapter.this.moviesListl.size() == 0) {
                            textView.setVisibility(0);
                            textView.setText(SeasonsAdapter.this.context.getResources().getString(R.string.no_record_found));
                        }
                        SeasonsAdapter.this.text_last_size = SeasonsAdapter.this.text_size;
                        SeasonsAdapter.this.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
