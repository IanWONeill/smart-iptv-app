package com.nst.yourname.view.adapter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nst.yourname.R;
import com.nst.yourname.model.database.ExternalPlayerDataBase;
import com.nst.yourname.model.pojo.ExternalPlayerModelClass;
import com.nst.yourname.view.activity.AddedExternalPlayerActivity;
import java.util.List;

public class AddedExternalPlayerAdapter extends RecyclerView.Adapter<AddedExternalPlayerAdapter.ViewHolder> {
    static final boolean $assertionsDisabled = false;
    public AddedExternalPlayerActivity activity;
    public List<ExternalPlayerModelClass> appInfoList;
    public Context context;
    PackageManager packageManager;

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

    public AddedExternalPlayerAdapter(Context context2, List<ExternalPlayerModelClass> list, AddedExternalPlayerActivity addedExternalPlayerActivity) {
        this.context = context2;
        this.appInfoList = list;
        this.packageManager = context2.getPackageManager();
        this.activity = addedExternalPlayerActivity;
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
        viewHolder.tv_appname.setText(this.appInfoList.get(i).getAppname());
        viewHolder.tv_packagename.setText(this.appInfoList.get(i).getPackagename());
        try {
            Drawable applicationIcon = this.context.getPackageManager().getApplicationIcon(this.appInfoList.get(i).getPackagename());
            if (applicationIcon != null) {
                viewHolder.iv_app_logo.setImageDrawable(applicationIcon);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        viewHolder.ll_outer.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.adapter.AddedExternalPlayerAdapter.AnonymousClass1 */

            public void onClick(View view) {
                AddedExternalPlayerAdapter.this.popmenu(view, ((ExternalPlayerModelClass) AddedExternalPlayerAdapter.this.appInfoList.get(i)).getAppname(), i);
            }
        });
        viewHolder.ll_outer.setOnLongClickListener(new View.OnLongClickListener() {
            /* class com.nst.yourname.view.adapter.AddedExternalPlayerAdapter.AnonymousClass2 */

            public boolean onLongClick(View view) {
                AddedExternalPlayerAdapter.this.popmenu(view, ((ExternalPlayerModelClass) AddedExternalPlayerAdapter.this.appInfoList.get(i)).getAppname(), i);
                return true;
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

    public void popmenu(View view, final String str, final int i) {
        if (this.context != null) {
            final PopupMenu popupMenu = new PopupMenu(this.context, view);
            popupMenu.inflate(R.menu.menu_remove_player);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                /* class com.nst.yourname.view.adapter.AddedExternalPlayerAdapter.AnonymousClass3 */

                @Override // android.support.v7.widget.PopupMenu.OnMenuItemClickListener
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if (menuItem.getItemId() != R.id.nav_remove) {
                        return false;
                    }
                    AddedExternalPlayerAdapter.this.showConfirmationPlayerRemovePopup(str, i);
                    popupMenu.dismiss();
                    return false;
                }
            });
            popupMenu.show();
        }
    }

    @SuppressLint({"RtlHardcoded"})
    public void showConfirmationPlayerRemovePopup(final String str, final int i) {
        try {
            View inflate = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate((int) R.layout.playera_add_alert_box, (RelativeLayout) ((Activity) this.context).findViewById(R.id.rl_outer));
            final PopupWindow popupWindow = new PopupWindow(this.context);
            popupWindow.setContentView(inflate);
            popupWindow.setWidth(-1);
            popupWindow.setHeight(-1);
            popupWindow.setFocusable(true);
            popupWindow.showAtLocation(inflate, 17, 0, 0);
            Button button = (Button) inflate.findViewById(R.id.btn_no);
            Button button2 = (Button) inflate.findViewById(R.id.btn_yes);
            button2.setText(this.context.getResources().getString(R.string.yes));
            button2.setFocusable(true);
            button.setText(this.context.getResources().getString(R.string.no));
            button.setFocusable(true);
            ((TextView) inflate.findViewById(R.id.tv_description)).setText(this.context.getResources().getString(R.string.are_you_sure_you_want_to_remove_palyer));
            button.setOnFocusChangeListener(new OnFocusChangeAccountListener(button, this.context));
            button2.setOnFocusChangeListener(new OnFocusChangeAccountListener(button2, this.context));
            button2.requestFocus();
            button2.requestFocusFromTouch();
            button.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.AddedExternalPlayerAdapter.AnonymousClass4 */

                public void onClick(View view) {
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.AddedExternalPlayerAdapter.AnonymousClass5 */

                public void onClick(View view) {
                    if (new ExternalPlayerDataBase(AddedExternalPlayerAdapter.this.context).removePlayer(str) > 0) {
                        AddedExternalPlayerAdapter.this.appInfoList.remove(i);
                        AddedExternalPlayerAdapter.this.notifyDataSetChanged();
                        AddedExternalPlayerAdapter.this.notifyItemRemoved(i);
                        if (AddedExternalPlayerAdapter.this.appInfoList != null && AddedExternalPlayerAdapter.this.appInfoList.size() == 0) {
                            AddedExternalPlayerAdapter.this.activity.updateData();
                        }
                        com.nst.yourname.miscelleneious.common.Utils.showToast(AddedExternalPlayerAdapter.this.context, AddedExternalPlayerAdapter.this.context.getString(R.string.removed_external_player));
                    } else {
                        com.nst.yourname.miscelleneious.common.Utils.showToast(AddedExternalPlayerAdapter.this.context, " error on Removed player");
                    }
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                }
            });
        } catch (Exception unused) {
        }
    }

    public static class OnFocusChangeAccountListener implements View.OnFocusChangeListener {
        private Activity activity;
        private Context context;
        private View view;

        public OnFocusChangeAccountListener(View view2, Context context2) {
            this.view = view2;
            this.context = context2;
        }

        @SuppressLint({"ResourceType"})
        public void onFocusChange(View view2, boolean z) {
            float f = 1.0f;
            if (z) {
                if (z) {
                    f = 1.12f;
                }
                if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("1")) {
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                    view2.setBackgroundResource(R.drawable.back_btn_effect);
                } else if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("2")) {
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                    view2.setBackgroundResource(R.drawable.logout_btn_effect);
                } else if (this.view == null || this.view.getTag() == null || !this.view.getTag().equals("3")) {
                    view2.setBackground(this.activity.getResources().getDrawable(R.drawable.selector_checkbox));
                } else {
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                    view2.setBackgroundResource(R.drawable.blue_btn_effect);
                }
            } else if (!z) {
                performScaleXAnimation(1.0f);
                performScaleYAnimation(1.0f);
                if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("1"))) {
                    view2.setBackgroundResource(R.drawable.black_button_dark);
                }
                if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("2"))) {
                    view2.setBackgroundResource(R.drawable.black_button_dark);
                }
                if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("3")) {
                    view2.setBackgroundResource(R.drawable.black_button_dark);
                }
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
