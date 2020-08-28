package com.nst.yourname.view.adapter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.model.pojo.AnnouncementsResponsePojo;
import com.nst.yourname.view.activity.AnnouncementAlertActivity;
import com.nst.yourname.view.activity.AnnouncementsActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.achartengine.chart.TimeChart;

public class AnnouncementsAdapter extends RecyclerView.Adapter<AnnouncementsAdapter.ViewHolder> {
    AnnouncementsActivity context;
    private boolean firstTimeFlag = true;
    public ImageView iv_divider;
    private List<AnnouncementsResponsePojo> myModelList;
    RelativeLayout rl_notification;

    public AnnouncementsAdapter(List<AnnouncementsResponsePojo> list, AnnouncementsActivity announcementsActivity) {
        this.context = announcementsActivity;
        this.myModelList = list;
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
        return new ViewHolder(LayoutInflater.from(this.context).inflate((int) R.layout.layout_announcements, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        AnnouncementsResponsePojo announcementsResponsePojo = this.myModelList.get(i);
        viewHolder.messageHeaderTV.setText(announcementsResponsePojo.getTitle());
        final String title = announcementsResponsePojo.getTitle();
        final String description = announcementsResponsePojo.getDescription();
        viewHolder.messageTV.setText(announcementsResponsePojo.getDescription());
        String str = getdataDefference(announcementsResponsePojo.getCreateDate());
        if (str.equalsIgnoreCase(AppConst.PASSWORD_UNSET)) {
            viewHolder.createdDateTV.setText("Today");
        } else if (str.equalsIgnoreCase("1")) {
            viewHolder.createdDateTV.setText("Yesterday");
        } else {
            TextView textView = viewHolder.createdDateTV;
            textView.setText(str + " days ago");
        }
        viewHolder.card_notification.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.adapter.AnnouncementsAdapter.AnonymousClass1 */

            public void onClick(View view) {
                Intent intent = new Intent(AnnouncementsAdapter.this.context, AnnouncementAlertActivity.class);
                intent.putExtra("Title", title);
                intent.putExtra("Description", description);
                AnnouncementsAdapter.this.context.startActivity(intent);
            }
        });
        viewHolder.card_notification.setOnFocusChangeListener(new OnFocusChangeAccountListener(viewHolder.card_notification));
        if (i == 0 && this.firstTimeFlag) {
            viewHolder.card_notification.requestFocus();
            this.firstTimeFlag = false;
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.myModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView card_notification;
        TextView createdDateTV;
        public View layout;
        TextView messageHeaderTV;
        TextView messageTV;

        public ViewHolder(View view) {
            super(view);
            this.layout = view;
            this.messageHeaderTV = (TextView) view.findViewById(R.id.tv_message_header);
            this.messageTV = (TextView) view.findViewById(R.id.tv_message);
            this.createdDateTV = (TextView) view.findViewById(R.id.tv_created_date);
            this.card_notification = (CardView) view.findViewById(R.id.card_notification);
            AnnouncementsAdapter.this.rl_notification = (RelativeLayout) view.findViewById(R.id.rl_notification);
            ImageView unused = AnnouncementsAdapter.this.iv_divider = (ImageView) view.findViewById(R.id.iv_divider);
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

    public String getdataDefference(String str) {
        String str2 = "";
        try {
            String format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String l = Long.toString(Math.abs(simpleDateFormat.parse(format).getTime() - simpleDateFormat.parse(str).getTime()) / TimeChart.DAY);
            try {
                Log.e("HERE", "HERE: " + l);
                return l;
            } catch (Exception e) {
                str2 = l;
                e = e;
            }
        } catch (Exception e2) {
            //e = e2;
            Log.e("DIDN'T WORK", "exception " + e2);
            return str2;
        }
        return str2;
    }
}
