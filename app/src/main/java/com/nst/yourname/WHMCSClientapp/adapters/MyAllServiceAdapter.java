package com.nst.yourname.WHMCSClientapp.adapters;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nst.yourname.R;
import com.nst.yourname.WHMCSClientapp.Clientdatabase.ClientSharepreferenceHandler;
import com.nst.yourname.WHMCSClientapp.activities.ShowserviceInformationActivity;
import com.nst.yourname.WHMCSClientapp.modelclassess.ActiveServiceModelClass;
import java.util.ArrayList;

public class MyAllServiceAdapter extends RecyclerView.Adapter<MyAllServiceAdapter.ViewHolder> {
    ArrayList<ActiveServiceModelClass> allServiceList;
    Context context;

    public MyAllServiceAdapter(Context context2, ArrayList<ActiveServiceModelClass> arrayList) {
        this.context = context2;
        this.allServiceList = arrayList;
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
        return new ViewHolder(LayoutInflater.from(this.context).inflate((int) R.layout.all_services_adapter_custom_list_layout, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        String name = this.allServiceList.get(i).getName();
        if (name == null || name.equalsIgnoreCase("")) {
            viewHolder.tv_title.setText(this.context.getResources().getString(R.string.N_A));
        } else {
            viewHolder.tv_title.setText(this.allServiceList.get(i).getName());
        }
        String firstpaymentamount = this.allServiceList.get(i).getFirstpaymentamount();
        String recurringamount = this.allServiceList.get(i).getRecurringamount();
        String billingcycle = this.allServiceList.get(i).getBillingcycle();
        if (billingcycle == null || billingcycle.isEmpty() || (!billingcycle.contains("Free Account") && !billingcycle.contains("One Time"))) {
            if (recurringamount == null || recurringamount.equalsIgnoreCase("")) {
                viewHolder.tv_pricing.setText(this.context.getResources().getString(R.string.N_A));
            } else {
                TextView textView = viewHolder.tv_pricing;
                textView.setText(ClientSharepreferenceHandler.getUserPrefix(this.context) + recurringamount + ClientSharepreferenceHandler.getUserSuffix(this.context));
            }
        } else if (firstpaymentamount == null || firstpaymentamount.equalsIgnoreCase("")) {
            viewHolder.tv_pricing.setText(this.context.getResources().getString(R.string.N_A));
        } else {
            TextView textView2 = viewHolder.tv_pricing;
            textView2.setText(ClientSharepreferenceHandler.getUserPrefix(this.context) + firstpaymentamount + ClientSharepreferenceHandler.getUserSuffix(this.context));
        }
        String nextduedate = this.allServiceList.get(i).getNextduedate();
        if (nextduedate == null || nextduedate.equalsIgnoreCase("") || nextduedate.equalsIgnoreCase("0000-00-00")) {
            viewHolder.tv_next_due_date.setText(this.context.getResources().getString(R.string.N_A));
        } else {
            viewHolder.tv_next_due_date.setText(nextduedate);
        }
        viewHolder.ll_outer.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.WHMCSClientapp.adapters.MyAllServiceAdapter.AnonymousClass1 */

            public void onClick(View view) {
                Intent intent = new Intent(MyAllServiceAdapter.this.context, ShowserviceInformationActivity.class);
                intent.putExtra("product", MyAllServiceAdapter.this.allServiceList.get(viewHolder.getAdapterPosition()).getName());
                intent.putExtra(NotificationCompat.CATEGORY_STATUS, MyAllServiceAdapter.this.allServiceList.get(viewHolder.getAdapterPosition()).getStatus());
                intent.putExtra("Registration_date", MyAllServiceAdapter.this.allServiceList.get(viewHolder.getAdapterPosition()).getRegdate());
                intent.putExtra("next_due_date", MyAllServiceAdapter.this.allServiceList.get(viewHolder.getAdapterPosition()).getNextduedate());
                intent.putExtra("recurring_amount", MyAllServiceAdapter.this.allServiceList.get(viewHolder.getAdapterPosition()).getRecurringamount());
                intent.putExtra("billing_cycle", MyAllServiceAdapter.this.allServiceList.get(viewHolder.getAdapterPosition()).getBillingcycle());
                intent.putExtra("payment_method", MyAllServiceAdapter.this.allServiceList.get(viewHolder.getAdapterPosition()).getPaymentmethodname());
                intent.putExtra("first_time_payment", MyAllServiceAdapter.this.allServiceList.get(viewHolder.getAdapterPosition()).getFirstpaymentamount());
                MyAllServiceAdapter.this.context.startActivity(intent);
            }
        });
        viewHolder.ll_outer.setOnFocusChangeListener(new OnFocusChangeAccountListener(viewHolder.ll_outer));
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.allServiceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_outer;
        TextView tv_next_due_date;
        TextView tv_pricing;
        TextView tv_title;

        public ViewHolder(View view) {
            super(view);
            this.tv_title = (TextView) view.findViewById(R.id.tv_title);
            this.tv_pricing = (TextView) view.findViewById(R.id.tv_pricing);
            this.tv_next_due_date = (TextView) view.findViewById(R.id.tv_next_due_date);
            this.ll_outer = (LinearLayout) view.findViewById(R.id.ll_outer);
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
                    f = 1.04f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                Log.e("id is", "" + this.view.getTag());
                this.view.setBackgroundResource(R.drawable.shape_list_multidns_focused);
            } else if (!z) {
                if (z) {
                    f = 1.04f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                performAlphaAnimation(z);
                this.view.setBackgroundResource(R.drawable.shape_list_multidns);
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
