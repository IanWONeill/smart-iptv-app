package com.nst.yourname.view.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.nst.yourname.model.database.PasswordStatusDBModel;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.view.activity.ParentalControlActivitity;
import java.util.ArrayList;
import java.util.Iterator;

public class ParentalControlVODCatAdapter extends RecyclerView.Adapter<ParentalControlVODCatAdapter.ViewHolder> {
    public ArrayList<LiveStreamCategoryIdDBModel> arrayList;
    public ArrayList<LiveStreamCategoryIdDBModel> completeList;
    public Context context;
    private ParentalControlActivitity dashboardActivity;
    public ArrayList<LiveStreamCategoryIdDBModel> filterList;
    private Typeface fontOPenSansBold;
    public LiveStreamDBHandler liveStreamDBHandler;
    public PasswordStatusDBModel passwordStatusDBModel;
    private SharedPreferences preferencesIPTV;
    public String username = "";
    private ViewHolder vh;

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return 0;
    }

    /*public class ViewHolder_ViewBinding implements Unbinder {
        private ViewHolder target;

        @UiThread
        public ViewHolder_ViewBinding(ViewHolder viewHolder, View view) {
            this.target = viewHolder;
            viewHolder.categoryNameTV = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_category_name, "field 'categoryNameTV'", TextView.class);
            viewHolder.categoryRL = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_category, "field 'categoryRL'", RelativeLayout.class);
            viewHolder.categoryRL1 = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_category1, "field 'categoryRL1'", RelativeLayout.class);
            viewHolder.lockIV = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_lock_staus, "field 'lockIV'", ImageView.class);
        }

        @Override // butterknife.Unbinder
        @CallSuper
        public void unbind() {
            ViewHolder viewHolder = this.target;
            if (viewHolder != null) {
                this.target = null;
                viewHolder.categoryNameTV = null;
                viewHolder.categoryRL = null;
                viewHolder.categoryRL1 = null;
                viewHolder.lockIV = null;
                return;
            }
            throw new IllegalStateException("Bindings already cleared.");
        }
    }*/

    public ParentalControlVODCatAdapter(ArrayList<LiveStreamCategoryIdDBModel> arrayList2, Context context2, ParentalControlActivitity parentalControlActivitity, Typeface typeface) {
        this.arrayList = arrayList2;
        this.context = context2;
        this.dashboardActivity = parentalControlActivitity;
        this.fontOPenSansBold = typeface;
        this.completeList = arrayList2;
        if (context2 != null) {
            this.preferencesIPTV = context2.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
            this.username = this.preferencesIPTV.getString("username", "");
            this.liveStreamDBHandler = new LiveStreamDBHandler(context2);
            this.passwordStatusDBModel = new PasswordStatusDBModel();
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.vh = new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate((int) R.layout.layout_live_category_list_item, viewGroup, false));
        return this.vh;
    }

    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        if (this.arrayList != null) {
            LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = this.arrayList.get(i);
            final String liveStreamCategoryID = liveStreamCategoryIdDBModel.getLiveStreamCategoryID();
            final String liveStreamCategoryName = liveStreamCategoryIdDBModel.getLiveStreamCategoryName();
            setLockStatus(viewHolder, liveStreamCategoryID);
            viewHolder.categoryNameTV.setText(liveStreamCategoryIdDBModel.getLiveStreamCategoryName());
            viewHolder.categoryRL.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.ParentalControlVODCatAdapter.AnonymousClass1 */

                public void onClick(View view) {
                    PasswordStatusDBModel unused = ParentalControlVODCatAdapter.this.passwordStatusDBModel = ParentalControlVODCatAdapter.this.liveStreamDBHandler.getPasswordStatus(ParentalControlVODCatAdapter.this.username, liveStreamCategoryID, SharepreferenceDBHandler.getUserID(ParentalControlVODCatAdapter.this.context));
                    if (ParentalControlVODCatAdapter.this.passwordStatusDBModel != null && ParentalControlVODCatAdapter.this.passwordStatusDBModel.getPasswordStatus() != null && ParentalControlVODCatAdapter.this.passwordStatusDBModel.getPasswordStatus().equals("1")) {
                        viewHolder.lockIV.setImageResource(R.drawable.lock_open);
                        ParentalControlVODCatAdapter.this.liveStreamDBHandler.updatePasswordStatus(ParentalControlVODCatAdapter.this.username, liveStreamCategoryID, AppConst.PASSWORD_UNSET, SharepreferenceDBHandler.getUserID(ParentalControlVODCatAdapter.this.context));
                        if (ParentalControlVODCatAdapter.this.context != null) {
                            Context access$200 = ParentalControlVODCatAdapter.this.context;
                            com.nst.yourname.miscelleneious.common.Utils.showToast(access$200, ParentalControlVODCatAdapter.this.context.getResources().getString(R.string.unlocked) + " " + liveStreamCategoryName);
                        }
                    } else if (ParentalControlVODCatAdapter.this.passwordStatusDBModel != null && ParentalControlVODCatAdapter.this.passwordStatusDBModel.getPasswordStatus() != null && ParentalControlVODCatAdapter.this.passwordStatusDBModel.getPasswordStatus().equals(AppConst.PASSWORD_UNSET)) {
                        viewHolder.lockIV.setImageResource(R.drawable.lock);
                        ParentalControlVODCatAdapter.this.liveStreamDBHandler.updatePasswordStatus(ParentalControlVODCatAdapter.this.username, liveStreamCategoryID, "1", SharepreferenceDBHandler.getUserID(ParentalControlVODCatAdapter.this.context));
                        if (ParentalControlVODCatAdapter.this.context != null) {
                            Context access$2002 = ParentalControlVODCatAdapter.this.context;
                            com.nst.yourname.miscelleneious.common.Utils.showToast(access$2002, ParentalControlVODCatAdapter.this.context.getResources().getString(R.string.locked) + " " + liveStreamCategoryName);
                        }
                    } else if (ParentalControlVODCatAdapter.this.passwordStatusDBModel != null) {
                        ParentalControlVODCatAdapter.this.passwordStatusDBModel.setPasswordStatusCategoryId(liveStreamCategoryID);
                        ParentalControlVODCatAdapter.this.passwordStatusDBModel.setPasswordStatusUserDetail(ParentalControlVODCatAdapter.this.username);
                        ParentalControlVODCatAdapter.this.passwordStatusDBModel.setPasswordStatus("1");
                        ParentalControlVODCatAdapter.this.passwordStatusDBModel.setUserID(SharepreferenceDBHandler.getUserID(ParentalControlVODCatAdapter.this.context));
                        ParentalControlVODCatAdapter.this.liveStreamDBHandler.addPasswordStatus(ParentalControlVODCatAdapter.this.passwordStatusDBModel);
                        viewHolder.lockIV.setImageResource(R.drawable.lock);
                        if (ParentalControlVODCatAdapter.this.context != null) {
                            Context access$2003 = ParentalControlVODCatAdapter.this.context;
                            com.nst.yourname.miscelleneious.common.Utils.showToast(access$2003, ParentalControlVODCatAdapter.this.context.getResources().getString(R.string.locked) + " " + liveStreamCategoryName);
                        }
                    }
                }
            });
        }
        viewHolder.categoryRL1.setOnKeyListener(new View.OnKeyListener() {
            /* class com.nst.yourname.view.adapter.ParentalControlVODCatAdapter.AnonymousClass2 */

            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() != 0) {
                    return false;
                }
                if (i != 23 && i != 66) {
                    return false;
                }
                viewHolder.categoryRL.performClick();
                return true;
            }
        });
    }

    private void setLockStatus(ViewHolder viewHolder, String str) {
        this.liveStreamDBHandler.getAllPasswordStatus(SharepreferenceDBHandler.getUserID(this.context));
        this.passwordStatusDBModel = this.liveStreamDBHandler.getPasswordStatus(this.username, str, SharepreferenceDBHandler.getUserID(this.context));
        if (Build.VERSION.SDK_INT <= 21) {
            viewHolder.lockIV.setImageResource(R.drawable.lock_open);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            viewHolder.lockIV.setImageDrawable(this.context.getResources().getDrawable(R.drawable.lock_open, null));
        }
        if (this.passwordStatusDBModel != null && this.passwordStatusDBModel.getPasswordStatus() != null && this.passwordStatusDBModel.getPasswordStatus().equals("1")) {
            if (Build.VERSION.SDK_INT <= 21) {
                viewHolder.lockIV.setImageResource(R.drawable.lock);
            }
            if (Build.VERSION.SDK_INT >= 21) {
                viewHolder.lockIV.setImageDrawable(this.context.getResources().getDrawable(R.drawable.lock, null));
            }
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_category_name)
        TextView categoryNameTV;
        @BindView(R.id.rl_category)
        RelativeLayout categoryRL;
        @BindView(R.id.rl_category1)
        RelativeLayout categoryRL1;
        @BindView(R.id.iv_lock_staus)
        ImageView lockIV;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void filter(final String str, final TextView textView, ProgressDialog progressDialog) {
        new Thread(new Runnable() {
            /* class com.nst.yourname.view.adapter.ParentalControlVODCatAdapter.AnonymousClass3 */

            public void run() {
                ArrayList unused = ParentalControlVODCatAdapter.this.filterList = new ArrayList();
                if (ParentalControlVODCatAdapter.this.filterList != null) {
                    ParentalControlVODCatAdapter.this.filterList.clear();
                }
                if (TextUtils.isEmpty(str)) {
                    ParentalControlVODCatAdapter.this.filterList.addAll(ParentalControlVODCatAdapter.this.completeList);
                } else {
                    Iterator it = ParentalControlVODCatAdapter.this.completeList.iterator();
                    while (it.hasNext()) {
                        LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = (LiveStreamCategoryIdDBModel) it.next();
                        if (liveStreamCategoryIdDBModel.getLiveStreamCategoryName().toLowerCase().contains(str.toLowerCase())) {
                            ParentalControlVODCatAdapter.this.filterList.add(liveStreamCategoryIdDBModel);
                        }
                    }
                }
                ((Activity) ParentalControlVODCatAdapter.this.context).runOnUiThread(new Runnable() {
                    /* class com.nst.yourname.view.adapter.ParentalControlVODCatAdapter.AnonymousClass3.AnonymousClass1 */

                    public void run() {
                        if (TextUtils.isEmpty(str)) {
                            ArrayList unused = ParentalControlVODCatAdapter.this.arrayList = ParentalControlVODCatAdapter.this.completeList;
                            textView.setVisibility(4);
                        } else if (ParentalControlVODCatAdapter.this.filterList.size() == 0) {
                            ArrayList unused2 = ParentalControlVODCatAdapter.this.arrayList = ParentalControlVODCatAdapter.this.filterList;
                            textView.setVisibility(0);
                            if (ParentalControlVODCatAdapter.this.context != null) {
                                textView.setText(ParentalControlVODCatAdapter.this.context.getResources().getString(R.string.no_record_found));
                            }
                        } else if (!ParentalControlVODCatAdapter.this.filterList.isEmpty() || ParentalControlVODCatAdapter.this.filterList.isEmpty()) {
                            ArrayList unused3 = ParentalControlVODCatAdapter.this.arrayList = ParentalControlVODCatAdapter.this.filterList;
                            textView.setVisibility(4);
                        }
                        ParentalControlVODCatAdapter.this.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
