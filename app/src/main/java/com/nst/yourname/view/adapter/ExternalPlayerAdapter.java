package com.nst.yourname.view.adapter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nst.yourname.R;
import java.util.List;

public class ExternalPlayerAdapter extends RecyclerView.Adapter<ExternalPlayerAdapter.ViewHolder> {
    public List<ApplicationInfo> appInfoList;
    private Context context;
    ItemClickListener mClickListener;
    PackageManager packageManager;

    public interface ItemClickListener {
        void itemClicked(View view, String str, String str2);
    }

    /*public class ViewHolder_ViewBinding implements Unbinder {
        private ViewHolder target;

        @UiThread
        public ViewHolder_ViewBinding(ViewHolder viewHolder, View view) {
            this.target = viewHolder;
            viewHolder.tv_packagename = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_packagename, "field 'tv_packagename'", TextView.class);
            viewHolder.tv_appname = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_appname, "field 'tv_appname'", TextView.class);
            viewHolder.iv_app_logo = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_app_logo, "field 'iv_app_logo'", ImageView.class);
            viewHolder.ll_outer = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_outer, "field 'll_outer'", LinearLayout.class);
        }

        @Override // butterknife.Unbinder
        @CallSuper
        public void unbind() {
            ViewHolder viewHolder = this.target;
            if (viewHolder != null) {
                this.target = null;
                viewHolder.tv_packagename = null;
                viewHolder.tv_appname = null;
                viewHolder.iv_app_logo = null;
                viewHolder.ll_outer = null;
                return;
            }
            throw new IllegalStateException("Bindings already cleared.");
        }
    }*/

    public ExternalPlayerAdapter(Context context2, List<ApplicationInfo> list, ItemClickListener itemClickListener) {
        this.context = context2;
        this.appInfoList = list;
        this.packageManager = context2.getPackageManager();
        this.mClickListener = itemClickListener;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [?, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    @Override // android.support.v7.widget.RecyclerView.Adapter
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate((int) R.layout.custom_externalplayer_layout, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tv_appname.setText(this.appInfoList.get(i).loadLabel(this.packageManager));
        viewHolder.tv_packagename.setText(this.appInfoList.get(i).packageName);
        viewHolder.iv_app_logo.setImageDrawable(this.appInfoList.get(i).loadIcon(this.packageManager));
        viewHolder.ll_outer.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.adapter.ExternalPlayerAdapter.AnonymousClass1 */

            public void onClick(View view) {
                ExternalPlayerAdapter.this.mClickListener.itemClicked(view, ((ApplicationInfo) ExternalPlayerAdapter.this.appInfoList.get(i)).loadLabel(ExternalPlayerAdapter.this.packageManager).toString(), ((ApplicationInfo) ExternalPlayerAdapter.this.appInfoList.get(i)).packageName);
            }
        });
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.appInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_app_logo)
        ImageView iv_app_logo;
        @BindView(R.id.ll_outer)
        LinearLayout ll_outer;
        @BindView(R.id.tv_appname)
        TextView tv_appname;
        @BindView(R.id.tv_packagename)
        TextView tv_packagename;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
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
                performScaleXAnimation(1.0f);
                performScaleYAnimation(1.0f);
                this.view.setBackgroundResource(R.drawable.shape_checkbox_focused);
            } else if (!z) {
                performScaleXAnimation(1.0f);
                performScaleYAnimation(1.0f);
                performAlphaAnimation(z);
                this.view.setBackgroundResource(R.color.transparent);
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

    private class focusChange implements View.OnFocusChangeListener {
        final View view;

        public focusChange(View view2) {
            this.view = view2;
        }

        @SuppressLint({"ResourceType"})
        public void onFocusChange(View view2, boolean z) {
            float f = 1.0f;
            if (z) {
                if (z) {
                    f = 1.01f;
                }
                performXAnimation(f);
                performYAnimation(f);
                Log.e("id is", "" + this.view.getTag());
                this.view.setBackgroundResource(R.drawable.shape_list_multidns_focused);
            } else if (!z) {
                if (z) {
                    f = 1.01f;
                }
                performXAnimation(f);
                performYAnimation(f);
                performAlphaAnimation(z);
                this.view.setBackgroundResource(R.drawable.shape_list_multidns);
            }
        }

        private void performAlphaAnimation(boolean z) {
            if (z) {
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "alpha", z ? 0.6f : 0.5f);
                ofFloat.setDuration(150L);
                ofFloat.start();
            }
        }

        private void performYAnimation(float f) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "scaleY", f);
            ofFloat.setDuration(150L);
            ofFloat.start();
        }

        private void performXAnimation(float f) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "scaleX", f);
            ofFloat.setDuration(150L);
            ofFloat.start();
        }
    }
}
