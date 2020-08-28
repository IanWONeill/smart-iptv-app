package com.nst.yourname.WHMCSClientapp.adapters;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nst.yourname.R;
import com.nst.yourname.WHMCSClientapp.modelclassess.TickedMessageModelClass;
import java.util.List;

public class TicketMessageAdapter extends RecyclerView.Adapter<TicketMessageAdapter.ViewHolder> {
    Context context;
    List<TickedMessageModelClass.Replies.Reply> ticketMessageList;

    public TicketMessageAdapter(Context context2, List<TickedMessageModelClass.Replies.Reply> list) {
        this.context = context2;
        this.ticketMessageList = list;
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
        return new ViewHolder(LayoutInflater.from(this.context).inflate((int) R.layout.ticket_message_custom_layout, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String name = this.ticketMessageList.get(i).getName();
        if (name == null || name.equalsIgnoreCase("")) {
            viewHolder.iv_client_image.setVisibility(8);
            viewHolder.iv_admin_image.setVisibility(0);
            viewHolder.tv_title.setText(this.ticketMessageList.get(i).getAdmin());
            viewHolder.tv_message.setText(this.ticketMessageList.get(i).getMessage());
            viewHolder.tv_date_time.setText(this.ticketMessageList.get(i).getDate());
            viewHolder.ll_outer.setBackgroundColor(Color.parseColor("#a5b0c2"));
            viewHolder.card_outer.setCardBackgroundColor(Color.parseColor("#a5b0c2"));
        } else {
            viewHolder.iv_client_image.setVisibility(0);
            viewHolder.iv_admin_image.setVisibility(8);
            viewHolder.tv_title.setText(name);
            viewHolder.tv_message.setText(this.ticketMessageList.get(i).getMessage());
            viewHolder.tv_date_time.setText(this.ticketMessageList.get(i).getDate());
            viewHolder.ll_outer.setBackgroundColor(Color.parseColor("#eceef2"));
            viewHolder.card_outer.setCardBackgroundColor(Color.parseColor("#eceef2"));
        }
        viewHolder.ll_outer.setOnFocusChangeListener(new OnFocusChangeAccountListener(viewHolder.ll_outer));
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.ticketMessageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView card_outer;
        ImageView iv_admin_image;
        ImageView iv_client_image;
        LinearLayout ll_outer;
        TextView tv_date_time;
        TextView tv_message;
        TextView tv_title;

        public ViewHolder(View view) {
            super(view);
            this.tv_date_time = (TextView) view.findViewById(R.id.tv_date_time);
            this.tv_message = (TextView) view.findViewById(R.id.tv_message);
            this.tv_title = (TextView) view.findViewById(R.id.tv_title);
            this.iv_admin_image = (ImageView) view.findViewById(R.id.iv_admin_image);
            this.iv_client_image = (ImageView) view.findViewById(R.id.iv_client_image);
            this.ll_outer = (LinearLayout) view.findViewById(R.id.ll_outer);
            this.card_outer = (CardView) view.findViewById(R.id.card_outer);
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
                performScaleXAnimation(1.0f);
                performScaleYAnimation(1.0f);
                Log.e("id is", "" + this.view.getTag());
                this.view.setBackgroundResource(R.drawable.shape_checkbox_focused);
            } else if (!z) {
                if (z) {
                    f = 1.01f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                performAlphaAnimation(z);
                this.view.setBackgroundResource(R.drawable.shape_view_message_blank);
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
