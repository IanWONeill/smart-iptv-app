package com.nst.yourname.view.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.DirectoryChooserDialog;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.view.adapter.RecordingAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

public class RecordingActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String JSON = "";
    public static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int RECORDING_REQUEST_CODE = 101;
    int actionBarHeight;
    @BindView(R.id.appbar_toolbar)
    AppBarLayout appbarToolbar;
    @BindView(R.id.bt_change)
    Button btBrowse;
    TextView clientNameTv;
    public Context context;
    ArrayList<File> dataItems = new ArrayList<>();
    @BindView(R.id.date)
    TextView date;
    private SharedPreferences loginPreferencesAfterLogin;
    public SharedPreferences loginPreferencesRecordingDir;
    public SharedPreferences.Editor loginPreferencesRecordingDirEditor;
    @BindView(R.id.logo)
    ImageView logo;
    private RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;
    private RecordingAdapter recordingAdapter;
    @BindView(R.id.my_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.rl_recording_dir_change)
    LinearLayout rlRecordingDirChange;
    Boolean sentToSettings = false;
    @BindView(R.id.tv_recording_dir_change)
    TextView textViewRecordingDir;
    @BindView(R.id.time)
    TextView time;

    /* renamed from: tv  reason: collision with root package name */
    TypedValue f15tv;
    @BindView(R.id.tv_no_record_found)
    TextView tvNoRecordFound;
    @BindView(R.id.tv_no_record_found_dontaskmeagain)
    TextView tv_no_record_found_dontaskmeagain;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_recording);
        ButterKnife.bind(this);
        changeStatusBarColor();
        focusInitialize();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        if (this.appbarToolbar != null) {
            this.appbarToolbar.setBackground(getResources().getDrawable(R.drawable.layout_background_tv));
        }
        getWindow().setFlags(1024, 1024);
        this.context = this;
        isStoragePermissionGranted();
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.RecordingActivity.AnonymousClass1 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(RecordingActivity.this.context);
            }
        });
        new Thread(new CountDownRunner()).start();
    }

    private void focusInitialize() {
        if (this.btBrowse != null) {
            this.btBrowse.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.btBrowse));
            this.btBrowse.requestFocus();
            this.btBrowse.requestFocusFromTouch();
            this.btBrowse.setBackgroundResource(R.drawable.back_btn_effect);
        }
        if (this.tv_no_record_found_dontaskmeagain != null) {
            this.tv_no_record_found_dontaskmeagain.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.tv_no_record_found_dontaskmeagain));
        }
    }

    public void isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT < 23) {
            Log.v("TAG", "Permission is granted");
            this.tv_no_record_found_dontaskmeagain.setVisibility(8);
            this.tvNoRecordFound.setVisibility(8);
            initialize();
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGEandroid.permission.READ_EXTERNAL_STORAGE") == 0) {
            Log.v("TAG", "Permission is granted");
            this.tv_no_record_found_dontaskmeagain.setVisibility(8);
            this.tvNoRecordFound.setVisibility(8);
            initialize();
        } else {
            Log.v("TAG", "Permission is revoked");
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 101);
        }
    }

    @Override // android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback, android.support.v4.app.FragmentActivity
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (i == 101) {
            if (iArr.length > 0 && iArr[0] == 0) {
                initialize();
            } else if (Build.VERSION.SDK_INT < 23 || shouldShowRequestPermissionRationale(strArr[0])) {
                this.tvNoRecordFound.setText(getResources().getString(R.string.access_denied));
                this.tvNoRecordFound.setVisibility(0);
                this.tv_no_record_found_dontaskmeagain.setVisibility(8);
                this.tv_no_record_found_dontaskmeagain.clearFocus();
            } else {
                this.tv_no_record_found_dontaskmeagain.setText(getResources().getString(R.string.dontaskmeagain_access_denied));
                this.tv_no_record_found_dontaskmeagain.setVisibility(0);
                this.tv_no_record_found_dontaskmeagain.requestFocus();
                this.tv_no_record_found_dontaskmeagain.isFocusableInTouchMode();
            }
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 101) {
            isStoragePermissionGranted();
        }
    }

    @OnClick({R.id.tv_no_record_found})
    public void NoRecordingfound() {
        isStoragePermissionGranted();
    }

    @OnClick({R.id.tv_no_record_found_dontaskmeagain})
    public void HandleDontAsk() {
        Toast.makeText(this, this.context.getResources().getString(R.string.grant_the_permission), 1).show();
        this.sentToSettings = true;
        try {
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", getPackageName(), null));
            startActivityForResult(intent, 101);
        } catch (Exception unused) {
        }
    }

    @OnClick({R.id.bt_change})
    public void onViewClicked() {
        final String[] strArr = {""};
        new DirectoryChooserDialog(this.context, new DirectoryChooserDialog.ChosenDirectoryListener() {
            /* class com.nst.yourname.view.activity.RecordingActivity.AnonymousClass2 */

            @Override // com.nst.yourname.miscelleneious.DirectoryChooserDialog.ChosenDirectoryListener
            public void onChosenDir(String str) {
                strArr[0] = str;
                SharedPreferences.Editor unused = RecordingActivity.this.loginPreferencesRecordingDirEditor = RecordingActivity.this.loginPreferencesRecordingDir.edit();
                RecordingActivity.this.loginPreferencesRecordingDirEditor.putString(AppConst.LOGIN_PREF_RECORDING_DIR, str);
                RecordingActivity.this.loginPreferencesRecordingDirEditor.apply();
                TextView textView = RecordingActivity.this.textViewRecordingDir;
                textView.setText(RecordingActivity.this.context.getResources().getString(R.string.your_current_directory_path) + str);
                RecordingActivity.this.initialize();
                Context access$000 = RecordingActivity.this.context;
                Toast.makeText(access$000, RecordingActivity.this.context.getResources().getString(R.string.choose_directory) + str, 1).show();
            }
        }).chooseDirectory("");
    }

    class CountDownRunner implements Runnable {
        CountDownRunner() {
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    RecordingActivity.this.doWork();
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
            /* class com.nst.yourname.view.activity.RecordingActivity.AnonymousClass3 */

            public void run() {
                try {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(RecordingActivity.this.context);
                    String date2 = Utils.getDate(date);
                    if (RecordingActivity.this.time != null) {
                        RecordingActivity.this.time.setText(time);
                    }
                    if (RecordingActivity.this.date != null) {
                        RecordingActivity.this.date.setText(date2);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    public void initialize() {
        if (this.context != null) {
            this.tv_no_record_found_dontaskmeagain.setVisibility(8);
            this.tvNoRecordFound.setVisibility(8);
            this.rlRecordingDirChange.setVisibility(0);
            this.loginPreferencesRecordingDir = this.context.getSharedPreferences(AppConst.LOGIN_PREF_RECORDING_DIR, 0);
            String string = this.loginPreferencesRecordingDir.getString(AppConst.LOGIN_PREF_RECORDING_DIR, Environment.getExternalStorageDirectory().toString() + "/" + AppConst.RECORDING_DIRECTORY);
            this.textViewRecordingDir.setText(getResources().getString(R.string.your_current_recording_path) + string);
            File[] recordedFiles = Utils.getRecordedFiles(this.context);
            if (recordedFiles == null || recordedFiles.length <= 0) {
                this.dataItems.clear();
                this.mLayoutManager = new LinearLayoutManager(this.context);
                this.recyclerView.setLayoutManager(this.mLayoutManager);
                this.recordingAdapter = new RecordingAdapter(this, this.dataItems);
                this.recyclerView.setItemAnimator(new DefaultItemAnimator());
                this.recyclerView.setAdapter(this.recordingAdapter);
                this.tvNoRecordFound.setVisibility(0);
                this.tvNoRecordFound.setText(getResources().getString(R.string.no_recording_found));
                return;
            }
            this.dataItems.clear();
            for (File file : recordedFiles) {
                if (file.toString().endsWith(".ts")) {
                    this.dataItems.addAll(Arrays.asList(file));
                }
            }
            if (this.dataItems.size() > 0) {
                this.rlRecordingDirChange.setVisibility(0);
                this.tvNoRecordFound.setVisibility(8);
                this.tv_no_record_found_dontaskmeagain.setFocusable(false);
            } else {
                this.tvNoRecordFound.setVisibility(0);
                this.tvNoRecordFound.setText(getResources().getString(R.string.no_recording_found));
                this.tvNoRecordFound.setClickable(false);
                this.rlRecordingDirChange.setVisibility(0);
            }
            this.mLayoutManager = new LinearLayoutManager(this.context);
            Collections.reverse(this.dataItems);
            this.recyclerView.setLayoutManager(this.mLayoutManager);
            this.recordingAdapter = new RecordingAdapter(this, this.dataItems);
            this.recyclerView.setItemAnimator(new DefaultItemAnimator());
            this.recyclerView.setAdapter(this.recordingAdapter);
        }
    }

    public void showDeleteRecording(File file, RecordingActivity recordingActivity) {
        if (recordingActivity != null && this.recordingAdapter != null && this.dataItems != null && this.tvNoRecordFound != null) {
            new Utils().showDeleteRecordingPopUp(recordingActivity, file, this.recordingAdapter, this.dataItems, this.tvNoRecordFound);
        }
    }

    private void changeStatusBarColor() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= 19) {
            window.clearFlags(67108864);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(Integer.MIN_VALUE);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        Utils.appResume(this.context);
        getWindow().setFlags(1024, 1024);
        this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        if (this.loginPreferencesAfterLogin.getString("username", "").equals("") && this.loginPreferencesAfterLogin.getString("password", "").equals("")) {
            startActivity(new Intent(this, LoginActivity.class));
        } else if (this.context != null) {
            onFinish();
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.tv_header_title) {
            startActivity(new Intent(this, NewDashboardActivity.class));
        }
    }

    public void onFinish() {
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(4);
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
                if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("3"))) {
                    performScaleXAnimation(1.0f);
                    performScaleYAnimation(1.0f);
                    view2.setBackgroundResource(R.drawable.back_btn_effect);
                }
                if (this.view != null && this.view.getTag().equals("1")) {
                    this.view.setBackgroundResource(R.drawable.back_btn_effect);
                } else if (this.view == null || !this.view.getTag().equals("2")) {
                    this.view.setBackgroundResource(R.drawable.shape_checkbox_focused);
                } else {
                    this.view.setBackgroundResource(R.drawable.logout_btn_effect);
                }
            } else if (!z) {
                performScaleXAnimation(1.0f);
                performScaleYAnimation(1.0f);
                performAlphaAnimation(z);
                if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("3"))) {
                    performScaleXAnimation(1.0f);
                    performScaleYAnimation(1.0f);
                    view2.setBackgroundResource(R.drawable.black_button_dark);
                }
                if (this.view != null && this.view.getTag().equals("1")) {
                    this.view.setBackgroundResource(R.drawable.black_button_dark);
                } else if (this.view == null || !this.view.getTag().equals("2")) {
                    this.view.setBackgroundResource(R.color.transparent);
                } else {
                    this.view.setBackgroundResource(R.drawable.black_button_dark);
                }
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
