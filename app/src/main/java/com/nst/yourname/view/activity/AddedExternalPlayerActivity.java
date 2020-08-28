package com.nst.yourname.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.database.ExternalPlayerDataBase;
import com.nst.yourname.model.pojo.ExternalPlayerModelClass;
import com.nst.yourname.view.adapter.AddedExternalPlayerAdapter;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class AddedExternalPlayerActivity extends AppCompatActivity {
    public Context context;
    private ExternalPlayerDataBase externalPlayerDataBase;
    public ArrayList<ExternalPlayerModelClass> externalPlayerList;
    @BindView(R.id.ll_add_player)
    LinearLayout ll_add_player;
    @BindView(R.id.ll_no_data_found)
    LinearLayout ll_no_data_found;
    @BindView(R.id.ll_progressbar)
    LinearLayout ll_progressbar;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_added_external_player);
        this.context = this;
        ButterKnife.bind(this);
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.AddedExternalPlayerActivity.AnonymousClass1 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(AddedExternalPlayerActivity.this.context);
            }
        });
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        updateData();
    }

    public void updateData() {
        new AsyntaskClass().execute(new Boolean[0]);
    }

    @SuppressLint({"StaticFieldLeak"})
    private class AsyntaskClass extends AsyncTask<Boolean, Void, Boolean> {
        private AsyntaskClass() {
        }

        public void onPreExecute() {
            super.onPreExecute();
            AddedExternalPlayerActivity.this.ll_no_data_found.setVisibility(8);
            AddedExternalPlayerActivity.this.recyclerView.setVisibility(8);
            AddedExternalPlayerActivity.this.ll_progressbar.setVisibility(0);
        }

        public Boolean doInBackground(Boolean... boolArr) {
            return AddedExternalPlayerActivity.this.initcontrol();
        }

        public void onPostExecute(Boolean bool) {
            super.onPostExecute((Boolean) bool);
            AddedExternalPlayerActivity.this.ll_progressbar.setVisibility(8);
            if (bool.booleanValue()) {
                AddedExternalPlayerActivity.this.onfinish(true);
                AddedExternalPlayerActivity.this.recyclerView.setLayoutManager(new LinearLayoutManager(AddedExternalPlayerActivity.this.context, 1, false));
                AddedExternalPlayerActivity.this.recyclerView.setAdapter(new AddedExternalPlayerAdapter(AddedExternalPlayerActivity.this.context, AddedExternalPlayerActivity.this.externalPlayerList, AddedExternalPlayerActivity.this));
                return;
            }
            AddedExternalPlayerActivity.this.onfinish(false);
        }
    }

    public Boolean initcontrol() {
        this.externalPlayerDataBase = new ExternalPlayerDataBase(this.context);
        this.externalPlayerList = this.externalPlayerDataBase.getExternalPlayer();
        if (this.externalPlayerList != null && this.externalPlayerList.size() > 0) {
            for (int i = 0; i < this.externalPlayerList.size(); i++) {
                String appname = this.externalPlayerList.get(i).getAppname();
                if (!Utils.appInstalledOrNot(this.externalPlayerList.get(i).getPackagename(), this.context)) {
                    this.externalPlayerDataBase.removePlayer(appname);
                }
            }
        }
        this.externalPlayerList = new ArrayList<>();
        this.externalPlayerList = this.externalPlayerDataBase.getExternalPlayer();
        if (this.externalPlayerList == null || this.externalPlayerList.size() <= 0) {
            return false;
        }
        return true;
    }

    public void onfinish(Boolean bool) {
        if (bool.booleanValue()) {
            this.ll_add_player.setVisibility(0);
            this.ll_no_data_found.setVisibility(8);
            this.recyclerView.setVisibility(0);
            return;
        }
        this.ll_no_data_found.setVisibility(0);
        this.ll_add_player.setVisibility(8);
        this.recyclerView.setVisibility(8);
    }

    @OnClick({R.id.iv_add_player, R.id.ll_add_player, R.id.tv_add_player, R.id.ll_no_data_found})
    public void onclick(View view) {
        int id = view.getId();
        if (id == R.id.iv_add_player || id == R.id.ll_add_player || id == R.id.ll_no_data_found || id == R.id.tv_add_player) {
            startActivity(new Intent(this, ExternalPlayerActivity.class));
        }
    }
}
