package com.nst.yourname.WHMCSClientapp.adapters;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.nst.yourname.WHMCSClientapp.activities.ViewTicketActivity;
import com.nst.yourname.WHMCSClientapp.modelclassess.TicketModelClass;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyViewHolder> {
    public List<TicketModelClass.Tickets.Ticket> TicketList;
    public Context context;

    /*public class MyViewHolder_ViewBinding implements Unbinder {
        private MyViewHolder target;

        @UiThread
        public MyViewHolder_ViewBinding(MyViewHolder myViewHolder, View view) {
            this.target = myViewHolder;
            myViewHolder.tvDepartmentName = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_department_name, "field 'tvDepartmentName'", TextView.class);
            myViewHolder.tvStatus = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_status, "field 'tvStatus'", TextView.class);
            myViewHolder.tvStatusValue = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_status_value, "field 'tvStatusValue'", TextView.class);
            myViewHolder.tvLastUpdated = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_last_updated, "field 'tvLastUpdated'", TextView.class);
            myViewHolder.tvLastUpdatedValue = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_last_updated_value, "field 'tvLastUpdatedValue'", TextView.class);
            myViewHolder.llOuter = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_outer, "field 'llOuter'", LinearLayout.class);
        }

        @Override // butterknife.Unbinder
        @CallSuper
        public void unbind() {
            MyViewHolder myViewHolder = this.target;
            if (myViewHolder != null) {
                this.target = null;
                myViewHolder.tvDepartmentName = null;
                myViewHolder.tvStatus = null;
                myViewHolder.tvStatusValue = null;
                myViewHolder.tvLastUpdated = null;
                myViewHolder.tvLastUpdatedValue = null;
                myViewHolder.llOuter = null;
                return;
            }
            throw new IllegalStateException("Bindings already cleared.");
        }
    }*/

    public TicketAdapter(List<TicketModelClass.Tickets.Ticket> list, Context context2) {
        this.TicketList = list;
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate((int) R.layout.ticket_list, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        if (this.TicketList != null && this.TicketList.size() > 0) {
            String status = this.TicketList.get(i).getStatus();
            if (!status.isEmpty() && status.equalsIgnoreCase("open")) {
                myViewHolder.tvStatusValue.setText(status);
                myViewHolder.tvStatusValue.setTextColor(Color.parseColor("#779500"));
            } else if (!status.isEmpty() && status.equalsIgnoreCase("customer-reply")) {
                myViewHolder.tvStatusValue.setText(status);
                myViewHolder.tvStatusValue.setTextColor(Color.parseColor("#ff6600"));
            } else if (!status.isEmpty() && status.equalsIgnoreCase("closed")) {
                myViewHolder.tvStatusValue.setText(status);
                myViewHolder.tvStatusValue.setTextColor(Color.parseColor("#888888"));
            } else if (!status.isEmpty() && status.equalsIgnoreCase("answered")) {
                myViewHolder.tvStatusValue.setText(status);
                myViewHolder.tvStatusValue.setTextColor(Color.parseColor("#000000"));
            }
            String tid = this.TicketList.get(i).getTid();
            String subject = this.TicketList.get(i).getSubject();
            if (tid == null || tid.equalsIgnoreCase("")) {
                tid = (subject == null || subject.equalsIgnoreCase("")) ? "" : subject;
            } else if (subject != null && !subject.equalsIgnoreCase("")) {
                tid = "#" + tid + "-" + subject;
            }
            if (tid == null || tid.equalsIgnoreCase("")) {
                myViewHolder.tvDepartmentName.setText(this.context.getResources().getString(R.string.N_A));
            } else {
                myViewHolder.tvDepartmentName.setText(tid);
            }
            String lastreply = this.TicketList.get(i).getLastreply();
            if (lastreply == null || lastreply.equalsIgnoreCase("")) {
                myViewHolder.tvLastUpdatedValue.setVisibility(8);
            } else {
                myViewHolder.tvLastUpdatedValue.setText(lastreply);
            }
            myViewHolder.llOuter.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.WHMCSClientapp.adapters.TicketAdapter.AnonymousClass1 */

                public void onClick(View view) {
                    Intent intent = new Intent(TicketAdapter.this.context, ViewTicketActivity.class);
                    intent.putExtra("Title", ((TicketModelClass.Tickets.Ticket) TicketAdapter.this.TicketList.get(i)).getTid() + "-" + ((TicketModelClass.Tickets.Ticket) TicketAdapter.this.TicketList.get(i)).getSubject());
                    intent.putExtra("ticketid", ((TicketModelClass.Tickets.Ticket) TicketAdapter.this.TicketList.get(myViewHolder.getAdapterPosition())).getId());
                    TicketAdapter.this.context.startActivity(intent);
                }
            });
        }
        myViewHolder.llOuter.setOnFocusChangeListener(new OnFocusChangeAccountListener(myViewHolder.llOuter));
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.TicketList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_outer)
        LinearLayout llOuter;
        @BindView(R.id.tv_department_name)
        TextView tvDepartmentName;
        @BindView(R.id.tv_last_updated)
        TextView tvLastUpdated;
        @BindView(R.id.tv_last_updated_value)
        TextView tvLastUpdatedValue;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_status_value)
        TextView tvStatusValue;

        public MyViewHolder(View view) {
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
