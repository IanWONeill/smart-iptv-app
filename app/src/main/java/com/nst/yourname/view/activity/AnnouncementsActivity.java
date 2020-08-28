package com.nst.yourname.view.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.nst.yourname.R;
import com.nst.yourname.WebServiceHandler.Globals;
import com.nst.yourname.WebServiceHandler.MainAsynListener;
import com.nst.yourname.WebServiceHandler.RavSharedPrefrences;
import com.nst.yourname.WebServiceHandler.Webservices;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.pojo.AnnouncementsResponsePojo;
import com.nst.yourname.view.adapter.AnnouncementsAdapter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;

@SuppressWarnings("ALL")
public class AnnouncementsActivity extends AppCompatActivity implements MainAsynListener<String> {
    String FirstMdkey;
    Context context;
    @BindView(R.id.date)
    TextView date;
    private RecyclerView.LayoutManager layoutManager;
    @BindView(R.id.logo)
    ImageView logo;
    private RecyclerView.Adapter mAdapter;
    @BindView(R.id.no_record)
    TextView noRecord;
    @BindView(R.id.pb_paging_loader)
    ProgressBar pbLoader;
    int random;
    @BindView(R.id.my_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.time)
    TextView time;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_announcements);
        Webservices.getWebservices = new Webservices(this);
        this.context = this;
        ButterKnife.bind(this);
        GetRandomNumber();
        getAnnouncements();
        new Thread(new CountDownRunner()).start();
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.AnnouncementsActivity.AnonymousClass1 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(AnnouncementsActivity.this.context);
            }
        });
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    public void onFinish(String str) {
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(8);
        }
        this.noRecord.setText(str);
        this.noRecord.setVisibility(0);
        this.recyclerView.setVisibility(8);
    }

    public void GetRandomNumber() {
        this.random = new Random().nextInt(8378600) + 10000;
        Globals.RandomNumber = String.valueOf(this.random);
    }

    public void getAnnouncements() {
        this.FirstMdkey = LoginActivity.md5(RavSharedPrefrences.get_clientNotificationkey(this) + "*" + RavSharedPrefrences.get_salt(this) + "-" + Globals.RandomNumber);
        Webservices.getterList = new ArrayList();
        Webservices.getterList.add(Webservices.P("action", "getAnnouncement"));
        Webservices.getterList.add(Webservices.P("sc", this.FirstMdkey));
        Webservices.getterList.add(Webservices.P("apikey", RavSharedPrefrences.get_clientNotificationkey(this)));
        Webservices.getterList.add(Webservices.P("rand_num", Globals.RandomNumber));
        Webservices.getWebservices.getAnnouncementLink(this);
    }

    public void onPostSuccess(String str, int i, boolean z) {
        if (!z) {
            onFinish(getResources().getString(R.string.no_record));
        } else if (i == 1) {
            try {
                if (str.isEmpty()) {
                    if (str.equalsIgnoreCase("")) {
                        onFinish(getResources().getString(R.string.no_record));
                        return;
                    }
                }
                Globals.jsonObj = new JSONObject(str);
                Gson gson = new Gson();
                if (Boolean.valueOf(Globals.jsonObj.getBoolean(NotificationCompat.CATEGORY_STATUS)).booleanValue()) {
                    JSONArray jSONArray = Globals.jsonObj.getJSONArray("response");
                    this.recyclerView.setHasFixedSize(true);
                    this.layoutManager = new LinearLayoutManager(this);
                    this.recyclerView.setLayoutManager(this.layoutManager);
                    List list = (List) gson.fromJson(jSONArray.toString(), new TypeToken<List<AnnouncementsResponsePojo>>() {
                        /* class com.nst.yourname.view.activity.AnnouncementsActivity.AnonymousClass2 */
                    }.getType());
                    if (!list.isEmpty()) {
                        this.mAdapter = new AnnouncementsAdapter(list, this);
                        this.recyclerView.setAdapter(this.mAdapter);
                        if (this.pbLoader != null) {
                            this.pbLoader.setVisibility(8);
                        }
                        this.noRecord.setVisibility(8);
                        return;
                    }
                    onFinish(getResources().getString(R.string.no_record));
                }
            } catch (Exception e) {
                Log.e("Get Announcements", e.getMessage());
                onFinish(getResources().getString(R.string.no_record));
            }
        }
    }

    @Override // com.nst.yourname.WebServiceHandler.MainAsynListener
    public void onPostError(int i) {
        onFinish(getResources().getString(R.string.internet_error));
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
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
                    f = 1.23f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
            } else if (!z) {
                if (z) {
                    f = 1.09f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                performAlphaAnimation(z);
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

    class CountDownRunner implements Runnable {
        CountDownRunner() {
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    AnnouncementsActivity.this.doWork();
                    Thread.sleep(1000);
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                } catch (Exception unused2) {
                }
            }
        }
    }

    public void doWork() {
        runOnUiThread(new Runnable() {
            /* class com.nst.yourname.view.activity.AnnouncementsActivity.AnonymousClass3 */

            public void run() {
                try {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(AnnouncementsActivity.this.context);
                    String date2 = Utils.getDate(date);
                    if (AnnouncementsActivity.this.time != null) {
                        AnnouncementsActivity.this.time.setText(time);
                    }
                    if (AnnouncementsActivity.this.date != null) {
                        AnnouncementsActivity.this.date.setText(date2);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }
}
