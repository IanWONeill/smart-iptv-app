package com.nst.yourname.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.model.database.SharepreferenceDBHandler;

@SuppressWarnings("ALL")
public class RoutingActivity extends AppCompatActivity {
    @BindView(R.id.bt_login_with_api)
    Button btLoginWithApi;
    @BindView(R.id.bt_login_with_m3u)
    Button btLoginWithM3u;
    private Context context = this;
    private SharedPreferences loginPreferencesRemember;
    private Boolean saveLogin = false;

    public void initialize() {
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_routing);
        if (AppConst.M3U_LINE_ACTIVE.booleanValue() && AppConst.M3U_API_SINGLE_USER.booleanValue()) {
            this.loginPreferencesRemember = getSharedPreferences(AppConst.SHARED_PREFERENCE_REMEBER_ME, 0);
            this.saveLogin = Boolean.valueOf(this.loginPreferencesRemember.getBoolean(AppConst.PREF_SAVE_LOGIN, false));
        }
        if (this.saveLogin.booleanValue()) {
            dashboardRedirect();
            return;
        }
        ButterKnife.bind(this);
        hideFooterIcons();
        initialize();
    }

    public void dashboardRedirect() {
        Intent intent = new Intent(this, NewDashboardActivity.class);
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        startActivity(intent);
        finish();
    }

    public void hideFooterIcons() {
        ((ConstraintLayout) findViewById(R.id.main_layout)).setSystemUiVisibility(4871);
    }

    @OnClick({R.id.bt_login_with_m3u, R.id.bt_login_with_api})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login_with_api:
                SharepreferenceDBHandler.setCurrentAPPType(AppConst.TYPE_API, this.context);
                startActivity(new Intent(this, LoginActivity.class));
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                return;
            case R.id.bt_login_with_m3u:
                SharepreferenceDBHandler.setCurrentAPPType(AppConst.TYPE_M3U, this.context);
                startActivity(new Intent(this, LoginM3uActivity.class));
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                return;
            default:
                return;
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        hideFooterIcons();
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        super.onBackPressed();
    }
}
