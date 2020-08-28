package com.nst.yourname.WHMCSClientapp.adapters;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nst.yourname.R;
import com.nst.yourname.WHMCSClientapp.Clientdatabase.ClientSharepreferenceHandler;
import com.nst.yourname.WHMCSClientapp.activities.BuyNowActivity;
import com.nst.yourname.WHMCSClientapp.modelclassess.InvoicesModelClass;
import com.nst.yourname.miscelleneious.common.AppConst;
import java.util.List;

public class UnpaidAdapter extends RecyclerView.Adapter<UnpaidAdapter.ViewHolder> {
    static final boolean $assertionsDisabled = false;
    Context context;
    List<InvoicesModelClass.Invoices.Invoice> list;

    /*public class ViewHolder_ViewBinding implements Unbinder {
        private ViewHolder target;

        @UiThread
        public ViewHolder_ViewBinding(ViewHolder viewHolder, View view) {
            this.target = viewHolder;
            viewHolder.iv_arrow_downward = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_arrow_downward, "field 'iv_arrow_downward'", ImageView.class);
            viewHolder.layoutFocus = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.layout, "field 'layoutFocus'", LinearLayout.class);
            viewHolder.reply_ticket = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_reply_ticket, "field 'reply_ticket'", RelativeLayout.class);
            viewHolder.ll_expandable = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_expandable, "field 'll_expandable'", LinearLayout.class);
        }

        @Override // butterknife.Unbinder
        @CallSuper
        public void unbind() {
            ViewHolder viewHolder = this.target;
            if (viewHolder != null) {
                this.target = null;
                viewHolder.iv_arrow_downward = null;
                viewHolder.layoutFocus = null;
                viewHolder.reply_ticket = null;
                viewHolder.ll_expandable = null;
                return;
            }
            throw new IllegalStateException("Bindings already cleared.");
        }
    }*/

    public UnpaidAdapter(Context context2, List<InvoicesModelClass.Invoices.Invoice> list2) {
        this.context = context2;
        this.list = list2;
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
        return new ViewHolder(LayoutInflater.from(this.context).inflate((int) R.layout.paid_list_layout, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (this.list != null && !this.list.isEmpty() && this.list.size() > 0) {
            String date = this.list.get(i).getDate();
            if (date == null || date.isEmpty() || date.equalsIgnoreCase("")) {
                viewHolder.tv_invoice_due_date_value.setText((int) R.string.N_A);
            } else {
                viewHolder.tv_invoice_date_value.setText(date);
            }
            String duedate = this.list.get(i).getDuedate();
            if (duedate == null || duedate.isEmpty() || duedate.equalsIgnoreCase("")) {
                viewHolder.tv_invoice_due_date_value.setText((int) R.string.N_A);
            } else {
                viewHolder.tv_invoice_due_date_value.setText(duedate);
            }
        }
        String total = this.list.get(i).getTotal();
        if (total == null || total.equalsIgnoreCase("") || total.isEmpty()) {
            viewHolder.tv_invoice_total_value.setText((int) R.string.N_A);
        } else {
            TextView textView = viewHolder.tv_invoice_total_value;
            textView.setText(ClientSharepreferenceHandler.getUserPrefix(this.context) + total + ClientSharepreferenceHandler.getUserSuffix(this.context));
        }
        String id = this.list.get(i).getId();
        if (id == null || id.isEmpty() || id.equalsIgnoreCase("")) {
            viewHolder.tv_invoices_no.setText(this.context.getResources().getString(R.string.my_invoices));
        } else {
            TextView textView2 = viewHolder.tv_invoices_no;
            textView2.setText("#" + id);
        }
        final String id2 = this.list.get(i).getId();
        final String status = this.list.get(i).getStatus();
        viewHolder.layoutFocus.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.WHMCSClientapp.adapters.UnpaidAdapter.AnonymousClass1 */

            public void onClick(View view) {
                Intent intent = new Intent(UnpaidAdapter.this.context, BuyNowActivity.class);
                intent.setAction(AppConst.ACTION_INVOICE);
                intent.putExtra("invoice_id", id2);
                intent.putExtra(NotificationCompat.CATEGORY_STATUS, status);
                UnpaidAdapter.this.context.startActivity(intent);
            }
        });
        viewHolder.card_layout.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.WHMCSClientapp.adapters.UnpaidAdapter.AnonymousClass2 */

            public void onClick(View view) {
                Intent intent = new Intent(UnpaidAdapter.this.context, BuyNowActivity.class);
                intent.setAction(AppConst.ACTION_INVOICE);
                intent.putExtra("invoice_id", id2);
                intent.putExtra(NotificationCompat.CATEGORY_STATUS, status);
                UnpaidAdapter.this.context.startActivity(intent);
            }
        });
        viewHolder.card_layout.setOnFocusChangeListener(new focusChange(viewHolder.card_layout));
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
                this.view.setBackgroundResource(R.drawable.shape_checkbox_focused);
            } else if (!z) {
                if (z) {
                    f = 1.01f;
                }
                performXAnimation(f);
                performYAnimation(f);
                performAlphaAnimation(z);
                this.view.setBackgroundResource(R.drawable.shape_view_message_blank);
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

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final int COUNTDOWN_RUNNING_TIME = 200;
        Animation animationDown;
        Animation animationUp;
        CardView card_layout;
        @BindView(R.id.iv_arrow_downward)
        ImageView iv_arrow_downward;
        @BindView(R.id.layout)
        LinearLayout layoutFocus;
        @BindView(R.id.ll_expandable)
        LinearLayout ll_expandable;
        @BindView(R.id.rl_reply_ticket)
        RelativeLayout reply_ticket;
        TextView tv_invoice_date_value;
        TextView tv_invoice_due_date_value;
        TextView tv_invoice_total_value;
        TextView tv_invoices_no;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.tv_invoice_date_value = (TextView) view.findViewById(R.id.tv_invoice_date_value);
            this.tv_invoice_due_date_value = (TextView) view.findViewById(R.id.tv_invoice_due_date_value);
            this.tv_invoice_total_value = (TextView) view.findViewById(R.id.tv_invoice_total_value);
            this.layoutFocus = (LinearLayout) view.findViewById(R.id.layout);
            this.ll_expandable = (LinearLayout) view.findViewById(R.id.ll_expandable);
            this.reply_ticket = (RelativeLayout) view.findViewById(R.id.rl_reply_ticket);
            this.card_layout = (CardView) view.findViewById(R.id.card_layout);
            this.tv_invoices_no = (TextView) view.findViewById(R.id.tv_invoices_no);
            this.animationUp = AnimationUtils.loadAnimation(UnpaidAdapter.this.context, R.anim.scale_up);
            this.animationDown = AnimationUtils.loadAnimation(UnpaidAdapter.this.context, R.anim.scale_down);
        }
    }
}
