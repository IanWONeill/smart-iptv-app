package com.nst.yourname.view.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.database.DatabaseUpdatedStatusDBModel;
import com.nst.yourname.model.database.ExternalPlayerDataBase;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.model.pojo.ExternalPlayerModelClass;
import com.nst.yourname.presenter.XMLTVPresenter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ALL")
public class PlayerSelectionActivity extends AppCompatActivity implements View.OnClickListener {
    static final boolean $assertionsDisabled = false;
    private int actionBarHeight;
    @BindView(R.id.appbar_toolbar)
    AppBarLayout appbarToolbar;
    @BindView(R.id.btn_back_playerselection)
    Button btnBackPlayerselection;
    @BindView(R.id.btn_reset_player_selection)
    Button btn_reset_player_selection;
    private TextView clientNameTv;
    public Context context;
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelEPG = new DatabaseUpdatedStatusDBModel();
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelLive = new DatabaseUpdatedStatusDBModel();
    @BindView(R.id.date)
    TextView date;
    ArrayList<ExternalPlayerModelClass> externalPlayerArrayList = new ArrayList<>();
    ArrayList<ExternalPlayerModelClass> externalPlayerArrayListNew = new ArrayList<>();
    private ExternalPlayerDataBase externalPlayerDataBase;
    @BindView(R.id.iv_add_player)
    ImageView iv_add_player;
    public int lastPositonCatchUp = 0;
    public int lastPositonEPG = 0;
    public int lastPositonLive = 0;
    public int lastPositonRecording = 0;
    public int lastPositonSeries = 0;
    public int lastPositonVOD = 0;
    private LiveStreamDBHandler liveStreamDBHandler;
    @BindView(R.id.ll_catchup_player)
    LinearLayout ll_catchup_player;
    @BindView(R.id.ll_series_player)
    LinearLayout ll_series_player;
    private SharedPreferences loginPreferencesAfterLogin;
    private SharedPreferences.Editor loginPrefsEditor;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.rl_settings)
    RelativeLayout rlSettings;
    @BindView(R.id.separator)
    TextView separator;
    @BindView(R.id.separator_second)
    TextView separatorSecond;
    @BindView(R.id.sp_catchup)
    Spinner spCatchup;
    @BindView(R.id.sp_epg)
    Spinner spEpg;
    @BindView(R.id.sp_live)
    Spinner spLive;
    @BindView(R.id.sp_recordings)
    Spinner spRecordings;
    @BindView(R.id.sp_series)
    Spinner spSeries;
    @BindView(R.id.sp_vod)
    Spinner spVod;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.textView_second)
    TextView textViewSecond;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /* renamed from: tv  reason: collision with root package name */
    private TypedValue f11tv;
    private String userName = "";
    private String userPassword = "";
    private XMLTVPresenter xmltvPresenter;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_player_selection);
        ButterKnife.bind(this);
        this.context = this;
        focusInitialize();
        changeStatusBarColor();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getWindow().setFlags(1024, 1024);
        new Thread(new CountDownRunner()).start();
        if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            this.ll_catchup_player.setVisibility(8);
        } else if (Utils.getIsXtream1_06(this.context)) {
            this.ll_catchup_player.setVisibility(8);
            this.ll_series_player.setVisibility(0);
        } else {
            this.ll_catchup_player.setVisibility(8);
            this.ll_series_player.setVisibility(0);
        }
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.PlayerSelectionActivity.AnonymousClass1 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(PlayerSelectionActivity.this.context);
            }
        });
    }

    private void addPlayerListToCategories() {
        ArrayList arrayList = new ArrayList();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator<ExternalPlayerModelClass> it = this.externalPlayerArrayList.iterator();
        while (it.hasNext()) {
            ExternalPlayerModelClass next = it.next();
            arrayList.add(next.getAppname());
            linkedHashMap.put(next.getAppname(), next.getPackagename());
        }
        setLivePlayer(this.externalPlayerArrayList, linkedHashMap, arrayList);
        setVODPlayer(this.externalPlayerArrayList, linkedHashMap, arrayList);
        setSeriesPlayer(this.externalPlayerArrayList, linkedHashMap, arrayList);
        setCatchUpPlayer(this.externalPlayerArrayList, linkedHashMap, arrayList);
        setRecordingsPlayer(this.externalPlayerArrayList, linkedHashMap, arrayList);
        setEPGPlayer(this.externalPlayerArrayList, linkedHashMap, arrayList);
    }

    private void setEPGPlayer(ArrayList<ExternalPlayerModelClass> arrayList, final LinkedHashMap<String, String> linkedHashMap, final List<String> list) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, 17367043, list);
        arrayAdapter.setDropDownViewResource(17367049);
        this.spEpg.setAdapter((SpinnerAdapter) arrayAdapter);
        this.spEpg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /* class com.nst.yourname.view.activity.PlayerSelectionActivity.AnonymousClass2 */

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            @SuppressLint({"NewApi"})
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                new SharepreferenceDBHandler();
                if (PlayerSelectionActivity.this.lastPositonEPG != i) {
                    int unused = PlayerSelectionActivity.this.lastPositonEPG = i;
                    PlayerSelectionActivity.this.spEpg.setSelection(i);
                    if (linkedHashMap.containsKey(list.get(i))) {
                        String str = (String) linkedHashMap.get(list.get(i));
                        String str2 = (String) PlayerSelectionActivity.getKeyByValue(linkedHashMap, str);
                        if (str != null && !str.isEmpty() && !str.equals("") && PlayerSelectionActivity.this.context != null && str2 != null && !str2.isEmpty() && !str2.equals("")) {
                            SharepreferenceDBHandler.setEPGPlayer(str, str2, PlayerSelectionActivity.this.context);
                            return;
                        }
                        return;
                    }
                    return;
                }
                String ePGPlayerPKGName = SharepreferenceDBHandler.getEPGPlayerPKGName(PlayerSelectionActivity.this.context);
                String ePGPlayerAppName = SharepreferenceDBHandler.getEPGPlayerAppName(PlayerSelectionActivity.this.context);
                if (ePGPlayerPKGName != null && !ePGPlayerPKGName.isEmpty() && !ePGPlayerPKGName.equals("")) {
                    PlayerSelectionActivity.this.spEpg.setSelection(PlayerSelectionActivity.this.getItemPostion(list, ePGPlayerPKGName, ePGPlayerAppName));
                }
            }
        });
    }

    @SuppressLint({"NewApi"})
    @RequiresApi(api = 19)
    public static <T, E> T getKeyByValue(Map<T, E> map, E e) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(e, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public int getItemPostion(List<String> list, String str, String str2) {
        int i = 0;
        for (String str3 : list) {
            if (str3.equals(str2)) {
                return i;
            }
            i++;
        }
        return 0;
    }

    private void setRecordingsPlayer(ArrayList<ExternalPlayerModelClass> arrayList, final LinkedHashMap<String, String> linkedHashMap, final List<String> list) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, 17367043, list);
        arrayAdapter.setDropDownViewResource(17367049);
        this.spRecordings.setAdapter((SpinnerAdapter) arrayAdapter);
        this.spRecordings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /* class com.nst.yourname.view.activity.PlayerSelectionActivity.AnonymousClass3 */

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            @RequiresApi(api = 19)
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                new SharepreferenceDBHandler();
                if (PlayerSelectionActivity.this.lastPositonRecording != i) {
                    int unused = PlayerSelectionActivity.this.lastPositonRecording = i;
                    PlayerSelectionActivity.this.spRecordings.setSelection(i);
                    if (linkedHashMap.containsKey(list.get(i))) {
                        String str = (String) linkedHashMap.get(list.get(i));
                        String str2 = (String) PlayerSelectionActivity.getKeyByValue(linkedHashMap, str);
                        if (str != null && !str.isEmpty() && !str.equals("") && PlayerSelectionActivity.this.context != null && str2 != null && !str2.isEmpty() && !str2.equals("")) {
                            SharepreferenceDBHandler.setRecordingsPlayer(str, str2, PlayerSelectionActivity.this.context);
                            return;
                        }
                        return;
                    }
                    return;
                }
                String recordingsPlayerPkgName = SharepreferenceDBHandler.getRecordingsPlayerPkgName(PlayerSelectionActivity.this.context);
                String recordingsPlayerAppName = SharepreferenceDBHandler.getRecordingsPlayerAppName(PlayerSelectionActivity.this.context);
                if (recordingsPlayerPkgName != null && !recordingsPlayerPkgName.isEmpty() && !recordingsPlayerPkgName.equals("")) {
                    PlayerSelectionActivity.this.spRecordings.setSelection(PlayerSelectionActivity.this.getItemPostion(list, recordingsPlayerPkgName, recordingsPlayerAppName));
                }
            }
        });
    }

    private void setCatchUpPlayer(ArrayList<ExternalPlayerModelClass> arrayList, final HashMap<String, String> hashMap, final List<String> list) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, 17367043, list);
        arrayAdapter.setDropDownViewResource(17367049);
        this.spCatchup.setAdapter((SpinnerAdapter) arrayAdapter);
        this.spCatchup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /* class com.nst.yourname.view.activity.PlayerSelectionActivity.AnonymousClass4 */

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            @TargetApi(19)
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                new SharepreferenceDBHandler();
                if (PlayerSelectionActivity.this.lastPositonCatchUp != i) {
                    int unused = PlayerSelectionActivity.this.lastPositonCatchUp = i;
                    PlayerSelectionActivity.this.spCatchup.setSelection(i);
                    if (hashMap.containsKey(list.get(i))) {
                        String str = (String) hashMap.get(list.get(i));
                        String str2 = (String) PlayerSelectionActivity.getKeyByValue(hashMap, str);
                        if (str != null && !str.isEmpty() && !str.equals("") && PlayerSelectionActivity.this.context != null && str2 != null && !str2.isEmpty() && !str2.equals("")) {
                            SharepreferenceDBHandler.setCatchUpPlayer(str, str2, PlayerSelectionActivity.this.context);
                            return;
                        }
                        return;
                    }
                    return;
                }
                String catchUpPlayerPkgName = SharepreferenceDBHandler.getCatchUpPlayerPkgName(PlayerSelectionActivity.this.context);
                String catchUpPlayerAppName = SharepreferenceDBHandler.getCatchUpPlayerAppName(PlayerSelectionActivity.this.context);
                if (catchUpPlayerPkgName != null && !catchUpPlayerPkgName.isEmpty() && !catchUpPlayerPkgName.equals("")) {
                    PlayerSelectionActivity.this.spCatchup.setSelection(PlayerSelectionActivity.this.getItemPostion(list, catchUpPlayerPkgName, catchUpPlayerAppName));
                }
            }
        });
    }

    private void setSeriesPlayer(ArrayList<ExternalPlayerModelClass> arrayList, final LinkedHashMap<String, String> linkedHashMap, final List<String> list) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, 17367043, list);
        arrayAdapter.setDropDownViewResource(17367049);
        this.spSeries.setAdapter((SpinnerAdapter) arrayAdapter);
        this.spSeries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /* class com.nst.yourname.view.activity.PlayerSelectionActivity.AnonymousClass5 */

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            @RequiresApi(api = 19)
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                new SharepreferenceDBHandler();
                if (PlayerSelectionActivity.this.lastPositonSeries != i) {
                    int unused = PlayerSelectionActivity.this.lastPositonSeries = i;
                    PlayerSelectionActivity.this.spSeries.setSelection(i);
                    if (linkedHashMap.containsKey(list.get(i))) {
                        String str = (String) linkedHashMap.get(list.get(i));
                        String str2 = (String) PlayerSelectionActivity.getKeyByValue(linkedHashMap, str);
                        if (str != null && !str.isEmpty() && !str.equals("") && PlayerSelectionActivity.this.context != null && str2 != null && !str2.isEmpty() && !str2.equals("")) {
                            SharepreferenceDBHandler.setSeriesPlayer(str, str2, PlayerSelectionActivity.this.context);
                            return;
                        }
                        return;
                    }
                    return;
                }
                String seriesPlayerPkgName = SharepreferenceDBHandler.getSeriesPlayerPkgName(PlayerSelectionActivity.this.context);
                String seriesPlayerAppName = SharepreferenceDBHandler.getSeriesPlayerAppName(PlayerSelectionActivity.this.context);
                if (seriesPlayerPkgName != null && !seriesPlayerPkgName.isEmpty() && !seriesPlayerPkgName.equals("")) {
                    PlayerSelectionActivity.this.spSeries.setSelection(PlayerSelectionActivity.this.getItemPostion(list, seriesPlayerPkgName, seriesPlayerAppName));
                }
            }
        });
    }

    private void setVODPlayer(ArrayList<ExternalPlayerModelClass> arrayList, final LinkedHashMap<String, String> linkedHashMap, final List<String> list) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, 17367043, list);
        arrayAdapter.setDropDownViewResource(17367049);
        this.spVod.setAdapter((SpinnerAdapter) arrayAdapter);
        this.spVod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /* class com.nst.yourname.view.activity.PlayerSelectionActivity.AnonymousClass6 */

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            @RequiresApi(api = 19)
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                new SharepreferenceDBHandler();
                if (PlayerSelectionActivity.this.lastPositonVOD != i) {
                    int unused = PlayerSelectionActivity.this.lastPositonVOD = i;
                    PlayerSelectionActivity.this.spVod.setSelection(i);
                    if (linkedHashMap.containsKey(list.get(i))) {
                        String str = (String) linkedHashMap.get(list.get(i));
                        String str2 = (String) PlayerSelectionActivity.getKeyByValue(linkedHashMap, str);
                        if (str != null && !str.isEmpty() && !str.equals("") && PlayerSelectionActivity.this.context != null && str2 != null && !str2.isEmpty() && !str2.equals("")) {
                            SharepreferenceDBHandler.setVODPlayer(str, str2, PlayerSelectionActivity.this.context);
                            return;
                        }
                        return;
                    }
                    return;
                }
                String vODPlayerPkgName = SharepreferenceDBHandler.getVODPlayerPkgName(PlayerSelectionActivity.this.context);
                String vODPlayerAppName = SharepreferenceDBHandler.getVODPlayerAppName(PlayerSelectionActivity.this.context);
                if (vODPlayerPkgName != null && !vODPlayerPkgName.isEmpty() && !vODPlayerPkgName.equals("")) {
                    PlayerSelectionActivity.this.spVod.setSelection(PlayerSelectionActivity.this.getItemPostion(list, vODPlayerPkgName, vODPlayerAppName));
                }
            }
        });
    }

    private void setLivePlayer(ArrayList<ExternalPlayerModelClass> arrayList, final LinkedHashMap<String, String> linkedHashMap, final List<String> list) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, 17367043, list);
        arrayAdapter.setDropDownViewResource(17367049);
        this.spLive.setAdapter((SpinnerAdapter) arrayAdapter);
        this.spLive.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /* class com.nst.yourname.view.activity.PlayerSelectionActivity.AnonymousClass7 */

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            @RequiresApi(api = 19)
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                new SharepreferenceDBHandler();
                if (PlayerSelectionActivity.this.lastPositonLive != i) {
                    int unused = PlayerSelectionActivity.this.lastPositonLive = i;
                    PlayerSelectionActivity.this.spLive.setSelection(i);
                    if (linkedHashMap.containsKey(list.get(i))) {
                        String str = (String) linkedHashMap.get(list.get(i));
                        String str2 = (String) PlayerSelectionActivity.getKeyByValue(linkedHashMap, str);
                        if (str != null && !str.isEmpty() && !str.equals("") && PlayerSelectionActivity.this.context != null && str2 != null && !str2.isEmpty() && !str2.equals("")) {
                            SharepreferenceDBHandler.setLivePlayer(str, str2, PlayerSelectionActivity.this.context);
                            return;
                        }
                        return;
                    }
                    return;
                }
                String livePlayerPkgName = SharepreferenceDBHandler.getLivePlayerPkgName(PlayerSelectionActivity.this.context);
                String livePlayerAppName = SharepreferenceDBHandler.getLivePlayerAppName(PlayerSelectionActivity.this.context);
                if (livePlayerPkgName != null && !livePlayerPkgName.isEmpty() && !livePlayerPkgName.equals("")) {
                    PlayerSelectionActivity.this.spLive.setSelection(PlayerSelectionActivity.this.getItemPostion(list, livePlayerPkgName, livePlayerAppName));
                }
            }
        });
    }

    class CountDownRunner implements Runnable {
        CountDownRunner() {
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    PlayerSelectionActivity.this.doWork();
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
            /* class com.nst.yourname.view.activity.PlayerSelectionActivity.AnonymousClass8 */

            public void run() {
                try {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(PlayerSelectionActivity.this.context);
                    String date2 = Utils.getDate(date);
                    if (PlayerSelectionActivity.this.time != null) {
                        PlayerSelectionActivity.this.time.setText(time);
                    }
                    if (PlayerSelectionActivity.this.date != null) {
                        PlayerSelectionActivity.this.date.setText(date2);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    private void focusInitialize() {
        if (this.btnBackPlayerselection != null) {
            this.btnBackPlayerselection.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.btnBackPlayerselection));
        }
        this.btn_reset_player_selection.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.btn_reset_player_selection));
        this.btn_reset_player_selection.requestFocus();
        this.btn_reset_player_selection.requestFocusFromTouch();
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
                    f = 1.05f;
                }
                Log.e("id is", "" + this.view.getTag());
                if (this.view.getTag().equals("1")) {
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                    this.view.setBackgroundResource(R.drawable.back_btn_effect);
                } else if (this.view.getTag().equals("2")) {
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                    this.view.setBackgroundResource(R.drawable.logout_btn_effect);
                } else {
                    performScaleXAnimation(1.12f);
                    performScaleYAnimation(1.12f);
                }
            } else if (!z) {
                if (z) {
                    f = 1.09f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                if (this.view.getTag().equals("1")) {
                    this.view.setBackgroundResource(R.drawable.black_button_dark);
                } else if (this.view.getTag().equals("2")) {
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

    @OnClick({R.id.iv_add_player, R.id.ll_add_player, R.id.tv_add_player, R.id.btn_back_playerselection, R.id.iv_reset_player, R.id.ll_reset_player, R.id.tv_reset_player, R.id.btn_reset_player_selection})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.btn_back_playerselection:
                onBackPressed();
                return;
            case R.id.btn_reset_player_selection:
                showConfirmationPlayerResetPopup();
                return;
            case R.id.iv_add_player:
            case R.id.ll_add_player:
            case R.id.tv_add_player:
                startActivity(new Intent(this, ExternalPlayerActivity.class));
                return;
            case R.id.iv_reset_player:
            case R.id.ll_reset_player:
            case R.id.tv_reset_player:
                showConfirmationPlayerResetPopup();
                return;
            default:
                return;
        }
    }

    public void setDefaulatforAllPlayer() {
        setDeafltforLivePlayer();
        setDefaultVODPlayer();
        setSeriesDefaultPlayer();
        setCatchUpDefaultPlayer();
        setRecordingsDefaultPlayer();
        setEPGDefaultPlayer();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void
     arg types: [android.widget.Button, com.nst.yourname.view.activity.PlayerSelectionActivity]
     candidates:
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.content.Context):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.CheckAppupdateActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.LoginActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.MultiUserActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.NewDashboardActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RateUsActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RecordingActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.SplashActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.VodActivityNewFlowSubCategories):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void */
    @SuppressLint({"RtlHardcoded"})
    private void showConfirmationPlayerResetPopup() {
        try {
            View inflate = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate((int) R.layout.playera_add_alert_box, (RelativeLayout) findViewById(R.id.rl_outer));
            final PopupWindow popupWindow = new PopupWindow(this.context);
            popupWindow.setContentView(inflate);
            popupWindow.setWidth(-1);
            popupWindow.setHeight(-1);
            popupWindow.setFocusable(true);
            popupWindow.showAtLocation(inflate, 17, 0, 0);
            Button button = (Button) inflate.findViewById(R.id.btn_no);
            Button button2 = (Button) inflate.findViewById(R.id.btn_yes);
            button2.setText("YES");
            ((TextView) inflate.findViewById(R.id.tv_description)).setText(getResources().getString(R.string.are_you_sure_you_want_reset_player));
            button.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) button, (Activity) this));
            button2.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) button2, (Activity) this));
            button.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.PlayerSelectionActivity.AnonymousClass9 */

                public void onClick(View view) {
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.PlayerSelectionActivity.AnonymousClass10 */

                public void onClick(View view) {
                    PlayerSelectionActivity.this.setDefaulatforAllPlayer();
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                }
            });
        } catch (Exception unused) {
        }
    }

    private void setEPGDefaultPlayer() {
        new SharepreferenceDBHandler();
        SharepreferenceDBHandler.setEPGPlayer("default", "Default Player", this.context);
        this.spEpg.setSelection(0);
    }

    private void setDeafltforLivePlayer() {
        new SharepreferenceDBHandler();
        SharepreferenceDBHandler.setLivePlayer("default", "Default Player", this.context);
        this.spLive.setSelection(0);
    }

    private void setRecordingsDefaultPlayer() {
        new SharepreferenceDBHandler();
        SharepreferenceDBHandler.setRecordingsPlayer("default", "Default Player", this.context);
        this.spRecordings.setSelection(0);
    }

    private void setCatchUpDefaultPlayer() {
        new SharepreferenceDBHandler();
        SharepreferenceDBHandler.setCatchUpPlayer("default", "Default Player", this.context);
        this.spCatchup.setSelection(0);
    }

    private void setSeriesDefaultPlayer() {
        new SharepreferenceDBHandler();
        SharepreferenceDBHandler.setSeriesPlayer("default", "Default Player", this.context);
        this.spSeries.setSelection(0);
    }

    private void setDefaultVODPlayer() {
        new SharepreferenceDBHandler();
        SharepreferenceDBHandler.setVODPlayer("default", "Default Player", this.context);
        this.spVod.setSelection(0);
    }

    private void GetCurrentDateTime() {
        String date2 = Calendar.getInstance().getTime().toString();
        String time2 = Utils.getTime(this.context);
        String date3 = Utils.getDate(date2);
        if (this.time != null) {
            this.time.setText(time2);
        }
        if (this.date != null) {
            this.date.setText(date3);
        }
    }

    private void initialize() {
        if (this.externalPlayerArrayList != null) {
            this.externalPlayerArrayList.clear();
        }
        this.context = this;
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        if (this.context != null) {
            this.externalPlayerDataBase = new ExternalPlayerDataBase(this.context);
            ExternalPlayerModelClass externalPlayerModelClass = new ExternalPlayerModelClass();
            externalPlayerModelClass.setAppicon("");
            externalPlayerModelClass.setAppname("Built-in Player");
            externalPlayerModelClass.setPackagename("default");
            this.externalPlayerArrayList.add(externalPlayerModelClass);
            this.externalPlayerArrayListNew = this.externalPlayerDataBase.getExternalPlayer();
            this.externalPlayerArrayList.addAll(1, this.externalPlayerArrayListNew);
        }
        this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_PLAYER, 0);
        this.loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_SELECTED_PLAYER, "");
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

    public void startTvGuideActivity() {
        startActivity(new Intent(this, NewEPGActivity.class));
        finish();
    }

    public void startImportTvGuideActivity() {
        startActivity(new Intent(this, ImportEPGActivity.class));
        finish();
    }

    public static long getDateDiff(SimpleDateFormat simpleDateFormat, String str, String str2) {
        try {
            return TimeUnit.DAYS.convert(simpleDateFormat.parse(str2).getTime() - simpleDateFormat.parse(str).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getUserName() {
        if (this.context == null) {
            return this.userName;
        }
        this.loginPreferencesAfterLogin = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        return this.loginPreferencesAfterLogin.getString("username", "");
    }

    public String getUserPassword() {
        if (this.context == null) {
            return this.userPassword;
        }
        this.loginPreferencesAfterLogin = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        return this.loginPreferencesAfterLogin.getString("password", "");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.toolbar.inflateMenu(R.menu.menu_text_icon);
        TypedValue typedValue = new TypedValue();
        if (getTheme().resolveAttribute(16843499, typedValue, true)) {
            TypedValue.complexToDimensionPixelSize(typedValue.data, getResources().getDisplayMetrics());
        }
        for (int i = 0; i < this.toolbar.getChildCount(); i++) {
            if (this.toolbar.getChildAt(i) instanceof ActionMenuView) {
                ((Toolbar.LayoutParams) this.toolbar.getChildAt(i).getLayoutParams()).gravity = 16;
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.nav_home) {
            startActivity(new Intent(this, NewDashboardActivity.class));
            finish();
        }
        if (itemId == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            finish();
        }
        if (itemId == R.id.action_logout && this.context != null) {
            new AlertDialog.Builder(this.context, R.style.AlertDialogCustom).setTitle(getResources().getString(R.string.logout_title)).setMessage(getResources().getString(R.string.logout_message)).setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.PlayerSelectionActivity.AnonymousClass12 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.logoutUser(PlayerSelectionActivity.this.context);
                }
            }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.PlayerSelectionActivity.AnonymousClass11 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
        if (itemId == R.id.menu_load_channels_vod) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(this.context.getResources().getString(R.string.confirm_to_refresh));
            builder.setMessage(this.context.getResources().getString(R.string.do_you_want_toproceed));
            builder.setIcon((int) R.drawable.questionmark);
            builder.setPositiveButton(this.context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.PlayerSelectionActivity.AnonymousClass13 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadChannelsAndVod(PlayerSelectionActivity.this.context);
                }
            });
            builder.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.PlayerSelectionActivity.AnonymousClass14 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
        }
        if (itemId == R.id.menu_load_tv_guide) {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            builder2.setTitle(this.context.getResources().getString(R.string.confirm_to_refresh));
            builder2.setMessage(this.context.getResources().getString(R.string.do_you_want_toproceed));
            builder2.setIcon((int) R.drawable.questionmark);
            builder2.setPositiveButton(this.context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.PlayerSelectionActivity.AnonymousClass15 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadTvGuid(PlayerSelectionActivity.this.context);
                }
            });
            builder2.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.PlayerSelectionActivity.AnonymousClass16 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder2.show();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        initialize();
        addPlayerListToCategories();
        Utils.appResume(this.context);
        getWindow().setFlags(1024, 1024);
        this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        if (!this.loginPreferencesAfterLogin.getString("username", "").equals("") || !this.loginPreferencesAfterLogin.getString("password", "").equals("")) {
            Context context2 = this.context;
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.tv_header_title) {
            startActivity(new Intent(this, NewDashboardActivity.class));
        }
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onStop() {
        super.onStop();
        this.lastPositonLive = 0;
        this.lastPositonVOD = 0;
        this.lastPositonSeries = 0;
        this.lastPositonCatchUp = 0;
        this.lastPositonRecording = 0;
        this.lastPositonEPG = 0;
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onDestroy() {
        super.onDestroy();
    }
}
