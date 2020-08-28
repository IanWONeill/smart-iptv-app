package com.nst.yourname.view.adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nst.yourname.R;
import com.nst.yourname.model.pojo.ExternalPlayerModelClass;
import java.util.List;

public class OptionExternalPlayerAdapter extends RecyclerView.Adapter<OptionExternalPlayerAdapter.ViewHolder> {
    public List<ExternalPlayerModelClass> appInfoList;
    private Context context;
    public ItemClickListener itemClickListener;
    PackageManager packageManager;

    public interface ItemClickListener {
        void itemClicked(View view, String str, String str2);
    }

    /*public class ViewHolder_ViewBinding implements Unbinder {
        private ViewHolder target;

        @UiThread
        public ViewHolder_ViewBinding(ViewHolder viewHolder, View view) {
            this.target = viewHolder;
            viewHolder.tv_appname = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_appname, "field 'tv_appname'", TextView.class);
            viewHolder.ll_outer = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_outer, "field 'll_outer'", LinearLayout.class);
        }

        @Override // butterknife.Unbinder
        @CallSuper
        public void unbind() {
            ViewHolder viewHolder = this.target;
            if (viewHolder != null) {
                this.target = null;
                viewHolder.tv_appname = null;
                viewHolder.ll_outer = null;
                return;
            }
            throw new IllegalStateException("Bindings already cleared.");
        }
    }*/

    public OptionExternalPlayerAdapter(Context context2, List<ExternalPlayerModelClass> list, ItemClickListener itemClickListener2) {
        this.context = context2;
        this.appInfoList = list;
        this.packageManager = context2.getPackageManager();
        this.itemClickListener = itemClickListener2;
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
        return new ViewHolder(LayoutInflater.from(this.context).inflate((int) R.layout.custom_option_externalplayer_layout, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tv_appname.setText(this.appInfoList.get(i).getAppname());
        viewHolder.ll_outer.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.adapter.OptionExternalPlayerAdapter.AnonymousClass1 */

            public void onClick(View view) {
                OptionExternalPlayerAdapter.this.itemClickListener.itemClicked(view, ((ExternalPlayerModelClass) OptionExternalPlayerAdapter.this.appInfoList.get(i)).getAppname(), ((ExternalPlayerModelClass) OptionExternalPlayerAdapter.this.appInfoList.get(i)).getPackagename());
            }
        });
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.appInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_outer)
        LinearLayout ll_outer;
        @BindView(R.id.tv_appname)
        TextView tv_appname;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
