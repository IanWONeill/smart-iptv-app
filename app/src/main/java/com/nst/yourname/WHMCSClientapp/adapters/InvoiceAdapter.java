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

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.ViewHolder> {
    public Context context;
    private List<InvoicesModelClass.Invoices.Invoice> invoicesModelClasses;

    /*public class ViewHolder_ViewBinding implements Unbinder {
        private ViewHolder target;

        @UiThread
        public ViewHolder_ViewBinding(ViewHolder viewHolder, View view) {
            this.target = viewHolder;
            viewHolder.iv_arrow_downward = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_arrow_downward, "field 'iv_arrow_downward'", ImageView.class);
            viewHolder.layoutFocus = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.layout, "field 'layoutFocus'", LinearLayout.class);
            viewHolder.invoices_l = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_invoices_, "field 'invoices_l'", RelativeLayout.class);
        }

        @Override // butterknife.Unbinder
        @CallSuper
        public void unbind() {
            ViewHolder viewHolder = this.target;
            if (viewHolder != null) {
                this.target = null;
                viewHolder.iv_arrow_downward = null;
                viewHolder.layoutFocus = null;
                viewHolder.invoices_l = null;
                return;
            }
            throw new IllegalStateException("Bindings already cleared.");
        }
    }*/

    public InvoiceAdapter(Context context2, List<InvoicesModelClass.Invoices.Invoice> list) {
        this.invoicesModelClasses = list;
        this.context = context2;
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
        String date = this.invoicesModelClasses.get(i).getDate();
        if (date == null || date.equalsIgnoreCase("")) {
            viewHolder.tv_invoice_date_value.setText((int) R.string.N_A);
        } else {
            viewHolder.tv_invoice_date_value.setText(date);
        }
        String duedate = this.invoicesModelClasses.get(i).getDuedate();
        if (duedate == null || duedate.equalsIgnoreCase("")) {
            viewHolder.tv_invoice_due_date_value.setText((int) R.string.N_A);
        } else {
            viewHolder.tv_invoice_due_date_value.setText(duedate);
        }
        String total = this.invoicesModelClasses.get(i).getTotal();
        if (total == null || total.equalsIgnoreCase("")) {
            viewHolder.tv_invoice_total_value.setText((int) R.string.N_A);
        } else {
            TextView textView = viewHolder.tv_invoice_total_value;
            textView.setText(ClientSharepreferenceHandler.getUserPrefix(this.context) + total + ClientSharepreferenceHandler.getUserSuffix(this.context));
        }
        final String id = this.invoicesModelClasses.get(i).getId();
        final String status = this.invoicesModelClasses.get(i).getStatus();
        viewHolder.layoutFocus.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.WHMCSClientapp.adapters.InvoiceAdapter.AnonymousClass1 */

            public void onClick(View view) {
                Intent intent = new Intent(InvoiceAdapter.this.context, BuyNowActivity.class);
                intent.setAction(AppConst.ACTION_INVOICE);
                intent.putExtra("invoice_id", id);
                intent.putExtra(NotificationCompat.CATEGORY_STATUS, status);
                InvoiceAdapter.this.context.startActivity(intent);
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

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.invoicesModelClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView card_layout;
        @BindView(R.id.rl_invoices_)
        RelativeLayout invoices_l;
        @BindView(R.id.iv_arrow_downward)
        ImageView iv_arrow_downward;
        @BindView(R.id.layout)
        LinearLayout layoutFocus;
        TextView tv_invoice_date_value;
        TextView tv_invoice_due_date_value;
        TextView tv_invoice_total_value;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.tv_invoice_date_value = (TextView) view.findViewById(R.id.tv_invoice_date_value);
            this.tv_invoice_due_date_value = (TextView) view.findViewById(R.id.tv_invoice_due_date_value);
            this.tv_invoice_total_value = (TextView) view.findViewById(R.id.tv_invoice_total_value);
            this.layoutFocus = (LinearLayout) view.findViewById(R.id.layout);
            this.card_layout = (CardView) view.findViewById(R.id.card_layout);
        }
    }
}
