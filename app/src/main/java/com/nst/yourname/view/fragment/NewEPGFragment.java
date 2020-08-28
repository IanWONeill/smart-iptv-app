package com.nst.yourname.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.github.tcking.viewquery.ViewQuery;
import com.google.android.exoplayer2.ui.PlayerView;
//import com.google.android.exoplayer2.ui.model.PlayerSelectedSinglton;
import com.google.common.collect.Maps;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.ThreadControl;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.EpgChannelModel;
import com.nst.yourname.model.FavouriteDBModel;
import com.nst.yourname.model.FavouriteM3UModel;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.PlayerSelectedSinglton;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.DatabaseUpdatedStatusDBModel;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.PasswordStatusDBModel;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.model.pojo.XMLTVProgrammePojo;
import com.nst.yourname.view.activity.NewDashboardActivity;
import com.nst.yourname.view.activity.SettingsActivity;
import com.nst.yourname.view.ijkplayer.application.Settings;
import com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSmallEPG;
import com.nst.yourname.view.utility.epg.EPG;
import com.nst.yourname.view.utility.epg.EPGClickListener;
import com.nst.yourname.view.utility.epg.domain.EPGChannel;
import com.nst.yourname.view.utility.epg.domain.EPGEvent;
import com.nst.yourname.view.utility.epg.misc.EPGDataImpl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.joda.time.LocalDateTime;

@SuppressWarnings("ALL")
public class NewEPGFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    public static final String ACTIVE_LIVE_STREAM_CATEGORY_ID = "";
    public static final String ACTIVE_LIVE_STREAM_CATEGORY_NAME = "";
    private static boolean isClickedOnce;
    private static SharedPreferences loginPreferencesSharedPref_time_format;
    private static Settings mSettings;
    private static final int[] s_allAspectRatio = {0, 1, 2, 3, 4, 5};
    private ViewQuery $;
    int actionBarHeight;
    String allowedFormat;
    @BindView(R.id.app_video_box)
    LinearLayout app_video_box;
    @BindView(R.id.app_video_loading)
    ProgressBar app_video_loading;
    @BindView(R.id.app_video_status)
    LinearLayout app_video_status;
    @BindView(R.id.app_video_status_text)
    TextView app_video_status_text;
    public Context context;
    @BindView(R.id.current_event)
    TextView currentEvent;
    @BindView(R.id.current_event_description)
    TextView currentEventDescription;
    private TextView currentEventDescriptionTextView;
    private TextView currentEventTextView;
    @BindView(R.id.current_event_time)
    TextView currentEventTime;
    private TextView currentEventTimeTextView;
    private SimpleDateFormat currentTimeFormat;
    DatabaseHandler database;
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelEPG = new DatabaseUpdatedStatusDBModel();
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelLive = new DatabaseUpdatedStatusDBModel();
    private int devEnterCounter = 0;
    private boolean devMode = false;
    private SharedPreferences.Editor editorEPG;
    @BindView(R.id.epg)
    EPG epg;
    ArrayList<EpgChannelModel> epgChannelModelList = new ArrayList<>();
    @BindView(R.id.rl_newepg_fragment)
    RelativeLayout epgFragment;
    @BindView(R.id.ll_epg_view)
    LinearLayout epgView;
    LiveStreamsDBModel favouriteStream = new LiveStreamsDBModel();
    private ArrayList<LiveStreamsDBModel> favouriteStreams = new ArrayList<>();
    private String getActiveLiveStreamCategoryId = AppConst.PASSWORD_UNSET;
    private String getActiveLiveStreamCategoryName = "ALL";
    private Handler handlerAspectRatio;
    private RecyclerView.LayoutManager layoutManager;
    LiveStreamDBHandler liveStreamDBHandler;
    public AsyncTask loadEPGData = null;
    private SharedPreferences loginPreferencesEPGAsyncStopped;
    private SharedPreferences loginPreferencesSharedPref;
    private SharedPreferences loginPreferencesSharedPrefLogin;
    private SharedPreferences loginPreferencesSharedPref_allowed_format;
    private SharedPreferences loginPreferencesSharedPref_opened_video;
    private SharedPreferences loginPreferencesUserAgent;
    private int mCurrentAspectRatio = s_allAspectRatio[0];
    private int mCurrentAspectRatioIndex = 4;
    String mFilePath;
    @BindView(R.id.video_view)
    NSTIJKPlayerSmallEPG mVideoView;
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;
    private Handler periodicTaskHandler;
    @BindView(R.id.player_view)
    PlayerView player_view;
    @BindView(R.id.rl_add_channel_to_fav)
    RelativeLayout rl_add_channel_to_fav;
    SearchView searchView;
    SharedPreferences.Editor sharedPrefEditor;
    SharedPreferences sharedPreferences;
    ThreadControl tControl = new ThreadControl();
    private Toolbar toolbar;

    /* renamed from: tv  reason: collision with root package name */
    TypedValue f37tv;
    @BindView(R.id.tv_no_record_found)
    TextView tvNoRecordFound;
    @BindView(R.id.tv_noStream)
    TextView tvNoStream;
    @BindView(R.id.tv_view_provider)
    TextView tvViewProvider;
    @BindView(R.id.tv_cat_title)
    TextView tv_cat_title;
    TextView txtDisplay;
    Unbinder unbinder;
    SeekBar vlcSeekbar;

    @Override // android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    public static NewEPGFragment newInstance(String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString("ACTIVE_LIVE_STREAM_CATEGORY_ID", str);
        bundle.putString("ACTIVE_LIVE_STREAM_CATEGORY_NAME", str2);
        NewEPGFragment newEPGFragment = new NewEPGFragment();
        newEPGFragment.setArguments(bundle);
        return newEPGFragment;
    }

    @Override // android.support.v4.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.context = getContext();
        mSettings = new Settings(this.context);
        this.sharedPreferences = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        this.mCurrentAspectRatioIndex = this.sharedPreferences.getInt(AppConst.ASPECT_RATIO, this.mCurrentAspectRatioIndex);
        if (this.context != null) {
            this.loginPreferencesSharedPref_opened_video = this.context.getSharedPreferences(AppConst.LOGIN_PREF_OPENED_VIDEO, 0);
            SharedPreferences.Editor edit = this.loginPreferencesSharedPref_opened_video.edit();
            this.loginPreferencesEPGAsyncStopped = this.context.getSharedPreferences(AppConst.LOGIN_PREF_EPG_SYNC_STOPPED, 0);
            edit.putInt(AppConst.LOGIN_PREF_OPENED_VIDEO_ID, 0);
            edit.putString("LOGIN_PREF_OPENED_VIDEO_URL_M3U", "");
            edit.apply();
        }
        if (getArguments() != null) {
            this.getActiveLiveStreamCategoryId = getArguments().getString("ACTIVE_LIVE_STREAM_CATEGORY_ID");
            this.getActiveLiveStreamCategoryName = getArguments().getString("ACTIVE_LIVE_STREAM_CATEGORY_NAME");
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [?, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    @Override // android.support.v4.app.Fragment
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view;
        if (mSettings.getPlayer() == 3) {
            PlayerSelectedSinglton.getInstance().setPlayerType("epg");
            view = layoutInflater.inflate((int) R.layout.fragment_new_epg_exo, viewGroup, false);
        } else {
            view = layoutInflater.inflate((int) R.layout.fragment_new_epg_streams, viewGroup, false);
        }
        this.unbinder = ButterKnife.bind(this, view);
        ActivityCompat.invalidateOptionsMenu(getActivity());
        setHasOptionsMenu(true);
        try {
            setToolbarLogoImagewithSearchView();
        } catch (Exception unused) {
        }
        initialize();
        this.currentEvent.setSelected(true);
        return view;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(@NonNull View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (getView() != null) {
            getView().setOnKeyListener(new View.OnKeyListener() {
                /* class com.nst.yourname.view.fragment.NewEPGFragment.AnonymousClass1 */

                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    if (keyEvent.getAction() == 1) {
                        return false;
                    }
                    if ((i == 20 || i == 19 || i == 22 || i == 21 || i == 23 || i == 66) && NewEPGFragment.this.epg != null) {
                        return NewEPGFragment.this.epg.onKeyDown(i, keyEvent);
                    }
                    return false;
                }
            });
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0120, code lost:
        if (r5.equals(com.nst.yourname.miscelleneious.common.AppConst.HTTP) != false) goto L_0x0124;
     */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0127  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x014a  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0165  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0180  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x01a3  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x01cf  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0233  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0244  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0290  */
    /* JADX WARNING: Removed duplicated region for block: B:83:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    public void onCreateEPG(boolean z) {
        int i;
        char c = 0;
        this.loginPreferencesSharedPref = this.context.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_PLAYER, 0);
        this.loginPreferencesSharedPrefLogin = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        final String string = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SELECTED_PLAYER, "");
        this.loginPreferencesSharedPref_allowed_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_ALLOWED_FORMAT, 0);
        String string2 = this.loginPreferencesSharedPrefLogin.getString("username", "");
        String string3 = this.loginPreferencesSharedPrefLogin.getString("password", "");
        this.allowedFormat = this.loginPreferencesSharedPref_allowed_format.getString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "");
        if (this.allowedFormat != null && !this.allowedFormat.isEmpty() && !this.allowedFormat.equals("") && this.allowedFormat.equals("default")) {
            this.allowedFormat = "";
        } else if (this.allowedFormat != null && !this.allowedFormat.isEmpty() && !this.allowedFormat.equals("") && this.allowedFormat.equals("ts")) {
            this.allowedFormat = ".ts";
        } else if (this.allowedFormat == null || this.allowedFormat.isEmpty() || this.allowedFormat.equals("") || !this.allowedFormat.equals("m3u8")) {
            this.allowedFormat = "";
        } else {
            this.allowedFormat = ".m3u8";
        }
        String string4 = this.loginPreferencesSharedPrefLogin.getString(AppConst.LOGIN_PREF_SERVER_URL, "");
        String string5 = this.loginPreferencesSharedPrefLogin.getString(AppConst.LOGIN_PREF_SERVER_PROTOCOL, "");
        String string6 = this.loginPreferencesSharedPrefLogin.getString(AppConst.LOGIN_PREF_SERVER_HTTPS_PORT, "");
        String string7 = this.loginPreferencesSharedPrefLogin.getString(AppConst.LOGIN_PREF_SERVER_PORT, "");
        String string8 = this.loginPreferencesSharedPrefLogin.getString(AppConst.LOGIN_PREF_SERVER_RTMP_PORT, "");
        int hashCode = string5.hashCode();
        if (hashCode != 3213448) {
            if (hashCode != 3504631) {
                if (hashCode == 99617003 && string5.equals(AppConst.HTTPS)) {
                    c = 1;
                    switch (c) {
                        case 0:
                            if (!string4.startsWith("http://")) {
                                string4 = "http://" + string4;
                                break;
                            }
                            break;
                        case 1:
                            if (!string4.startsWith("https://")) {
                                string4 = "https://" + string4;
                            }
                            string7 = string6;
                            break;
                        case 2:
                            if (!string4.startsWith("rmtp://")) {
                                string4 = "rmtp://" + string4;
                            }
                            string7 = string8;
                            break;
                        default:
                            if (!string4.startsWith("http://") && !string4.startsWith("https://")) {
                                string4 = "http://" + string4;
                                break;
                            }
                    }
                    if (this.allowedFormat.equals("")) {
                        this.mFilePath = string4 + ":" + string7 + "/" + string2 + "/" + string3 + "/";
                    } else {
                        this.mFilePath = string4 + ":" + string7 + "/live/" + string2 + "/" + string3 + "/";
                    }
                    this.mFilePath = Utils.getFormattedUrl(this.mFilePath);
                    this.currentEventTextView = this.currentEvent;
                    this.currentEventTimeTextView = this.currentEventTime;
                    this.currentEventDescriptionTextView = this.currentEventDescription;
                    this.tv_cat_title.setText(this.getActiveLiveStreamCategoryName);
                    this.epg.setCurrentEventTextView(this.currentEventTextView);
                    this.epg.setCurrentEventTimeTextView(this.currentEventTimeTextView);
                    this.epg.setCurrentEventDescriptionTextView(this.currentEventDescriptionTextView);
                    if (mSettings.getPlayer() == 3) {
                        this.epg.setActivity(getActivity());
                        this.epg.setPlayer(this.player_view);
                    } else {
                        this.mVideoView.setActivity(getActivity(), this.mVideoView);
                        this.epg.setActivity(getActivity(), this.mVideoView);
                    }
                    this.epg.setVideoPathUrl(this.mFilePath);
                    this.epg.setExtensionType(this.allowedFormat);
                    this.epg.setLoader(this.app_video_loading);
                    this.epg.setVideoStatus(this.app_video_status);
                    this.epg.setVideoStatusText(this.app_video_status_text);
                    this.periodicTaskHandler = new Handler();
                    this.epg.setEPGClickListener(new EPGClickListener() {
                        /* class com.nst.yourname.view.fragment.NewEPGFragment.AnonymousClass2 */

                        @Override // com.nst.yourname.view.utility.epg.EPGClickListener
                        public void onChannelClicked(int i, EPGChannel ePGChannel) {
                            int i2;
                            try {
                                i2 = Integer.parseInt(ePGChannel.getStreamID());
                            } catch (NumberFormatException unused) {
                                i2 = -1;
                            }
                            String name = ePGChannel.getName();
                            String num = ePGChannel.getNum();
                            String epgChannelID = ePGChannel.getEpgChannelID();
                            String imageURL = ePGChannel.getImageURL();
                            String catID = ePGChannel.getCatID();
                            String videoURL = ePGChannel.getVideoURL();
                            String openedCategoryID = ePGChannel.getOpenedCategoryID();
                            if (NewEPGFragment.this.epg != null) {
                                NewEPGFragment.this.epg.clearEPGImageCache();
                            }
                            NewEPGFragment.this.epg.EPGPlayPopUp(NewEPGFragment.this.getContext(), string, i2, num, name, epgChannelID, imageURL, openedCategoryID, videoURL, catID);
                        }

                        @Override // com.nst.yourname.view.utility.epg.EPGClickListener
                        public void onEventClicked(int i, int i2, EPGEvent ePGEvent) {
                            int i3;
                            try {
                                i3 = Integer.parseInt(ePGEvent.getChannel().getStreamID());
                            } catch (NumberFormatException unused) {
                                i3 = -1;
                            }
                            String name = ePGEvent.getChannel().getName();
                            String num = ePGEvent.getChannel().getNum();
                            String epgChannelID = ePGEvent.getChannel().getEpgChannelID();
                            String imageURL = ePGEvent.getChannel().getImageURL();
                            String catID = ePGEvent.getChannel().getCatID();
                            String openedCategoryID = ePGEvent.getChannel().getOpenedCategoryID();
                            String videoURL = ePGEvent.getChannel().getVideoURL();
                            NewEPGFragment.this.epg.selectEvent(ePGEvent, true);
                            if (NewEPGFragment.this.epg != null) {
                                NewEPGFragment.this.epg.clearEPGImageCache();
                            }
                            NewEPGFragment.this.epg.EPGPlayPopUp(NewEPGFragment.this.getContext(), string, i3, num, name, epgChannelID, imageURL, openedCategoryID, videoURL, catID);
                        }

                        @Override // com.nst.yourname.view.utility.epg.EPGClickListener
                        public void onResetButtonClicked() {
                            NewEPGFragment.this.epg.resetButtonClicked();
                        }
                    });
                    if (!z && this.epg != null) {
                        EPGEvent selectedEvent = this.epg.getSelectedEvent();
                        try {
                            i = Integer.parseInt(selectedEvent.getChannel().getStreamID());
                        } catch (NumberFormatException unused) {
                            i = -1;
                        }
                        String name = selectedEvent.getChannel().getName();
                        String num = selectedEvent.getChannel().getNum();
                        String epgChannelID = selectedEvent.getChannel().getEpgChannelID();
                        String imageURL = selectedEvent.getChannel().getImageURL();
                        String catID = selectedEvent.getChannel().getCatID();
                        String videoURL = selectedEvent.getChannel().getVideoURL();
                        String openedCategoryID = selectedEvent.getChannel().getOpenedCategoryID();
                        this.epg.selectEvent(selectedEvent, true);
                        if (this.epg != null) {
                            this.epg.clearEPGImageCache();
                        }
                        this.epg.EPGPlayPopUp(getContext(), string, i, num, name, epgChannelID, imageURL, openedCategoryID, videoURL, catID);
                        return;
                    }
                    return;
                }
            } else if (string5.equals(AppConst.RMTP)) {
                c = 2;
                switch (c) {
                }
                if (this.allowedFormat.equals("")) {
                }
                this.mFilePath = Utils.getFormattedUrl(this.mFilePath);
                this.currentEventTextView = this.currentEvent;
                this.currentEventTimeTextView = this.currentEventTime;
                this.currentEventDescriptionTextView = this.currentEventDescription;
                this.tv_cat_title.setText(this.getActiveLiveStreamCategoryName);
                this.epg.setCurrentEventTextView(this.currentEventTextView);
                this.epg.setCurrentEventTimeTextView(this.currentEventTimeTextView);
                this.epg.setCurrentEventDescriptionTextView(this.currentEventDescriptionTextView);
                if (mSettings.getPlayer() == 3) {
                }
                this.epg.setVideoPathUrl(this.mFilePath);
                this.epg.setExtensionType(this.allowedFormat);
                this.epg.setLoader(this.app_video_loading);
                this.epg.setVideoStatus(this.app_video_status);
                this.epg.setVideoStatusText(this.app_video_status_text);
                this.periodicTaskHandler = new Handler();
                this.epg.setEPGClickListener(new EPGClickListener() {
                    /* class com.nst.yourname.view.fragment.NewEPGFragment.AnonymousClass2 */

                    @Override // com.nst.yourname.view.utility.epg.EPGClickListener
                    public void onChannelClicked(int i, EPGChannel ePGChannel) {
                        int i2;
                        try {
                            i2 = Integer.parseInt(ePGChannel.getStreamID());
                        } catch (NumberFormatException unused) {
                            i2 = -1;
                        }
                        String name = ePGChannel.getName();
                        String num = ePGChannel.getNum();
                        String epgChannelID = ePGChannel.getEpgChannelID();
                        String imageURL = ePGChannel.getImageURL();
                        String catID = ePGChannel.getCatID();
                        String videoURL = ePGChannel.getVideoURL();
                        String openedCategoryID = ePGChannel.getOpenedCategoryID();
                        if (NewEPGFragment.this.epg != null) {
                            NewEPGFragment.this.epg.clearEPGImageCache();
                        }
                        NewEPGFragment.this.epg.EPGPlayPopUp(NewEPGFragment.this.getContext(), string, i2, num, name, epgChannelID, imageURL, openedCategoryID, videoURL, catID);
                    }

                    @Override // com.nst.yourname.view.utility.epg.EPGClickListener
                    public void onEventClicked(int i, int i2, EPGEvent ePGEvent) {
                        int i3;
                        try {
                            i3 = Integer.parseInt(ePGEvent.getChannel().getStreamID());
                        } catch (NumberFormatException unused) {
                            i3 = -1;
                        }
                        String name = ePGEvent.getChannel().getName();
                        String num = ePGEvent.getChannel().getNum();
                        String epgChannelID = ePGEvent.getChannel().getEpgChannelID();
                        String imageURL = ePGEvent.getChannel().getImageURL();
                        String catID = ePGEvent.getChannel().getCatID();
                        String openedCategoryID = ePGEvent.getChannel().getOpenedCategoryID();
                        String videoURL = ePGEvent.getChannel().getVideoURL();
                        NewEPGFragment.this.epg.selectEvent(ePGEvent, true);
                        if (NewEPGFragment.this.epg != null) {
                            NewEPGFragment.this.epg.clearEPGImageCache();
                        }
                        NewEPGFragment.this.epg.EPGPlayPopUp(NewEPGFragment.this.getContext(), string, i3, num, name, epgChannelID, imageURL, openedCategoryID, videoURL, catID);
                    }

                    @Override // com.nst.yourname.view.utility.epg.EPGClickListener
                    public void onResetButtonClicked() {
                        NewEPGFragment.this.epg.resetButtonClicked();
                    }
                });
                if (!z) {
                    return;
                }
                return;
            }
        }
        c = 65535;
        switch (c) {
        }
        if (this.allowedFormat.equals("")) {
        }
        this.mFilePath = Utils.getFormattedUrl(this.mFilePath);
        this.currentEventTextView = this.currentEvent;
        this.currentEventTimeTextView = this.currentEventTime;
        this.currentEventDescriptionTextView = this.currentEventDescription;
        this.tv_cat_title.setText(this.getActiveLiveStreamCategoryName);
        this.epg.setCurrentEventTextView(this.currentEventTextView);
        this.epg.setCurrentEventTimeTextView(this.currentEventTimeTextView);
        this.epg.setCurrentEventDescriptionTextView(this.currentEventDescriptionTextView);
        if (mSettings.getPlayer() == 3) {
        }
        this.epg.setVideoPathUrl(this.mFilePath);
        this.epg.setExtensionType(this.allowedFormat);
        this.epg.setLoader(this.app_video_loading);
        this.epg.setVideoStatus(this.app_video_status);
        this.epg.setVideoStatusText(this.app_video_status_text);
        this.periodicTaskHandler = new Handler();
        this.epg.setEPGClickListener(new EPGClickListener() {
            /* class com.nst.yourname.view.fragment.NewEPGFragment.AnonymousClass2 */

            @Override // com.nst.yourname.view.utility.epg.EPGClickListener
            public void onChannelClicked(int i, EPGChannel ePGChannel) {
                int i2;
                try {
                    i2 = Integer.parseInt(ePGChannel.getStreamID());
                } catch (NumberFormatException unused) {
                    i2 = -1;
                }
                String name = ePGChannel.getName();
                String num = ePGChannel.getNum();
                String epgChannelID = ePGChannel.getEpgChannelID();
                String imageURL = ePGChannel.getImageURL();
                String catID = ePGChannel.getCatID();
                String videoURL = ePGChannel.getVideoURL();
                String openedCategoryID = ePGChannel.getOpenedCategoryID();
                if (NewEPGFragment.this.epg != null) {
                    NewEPGFragment.this.epg.clearEPGImageCache();
                }
                NewEPGFragment.this.epg.EPGPlayPopUp(NewEPGFragment.this.getContext(), string, i2, num, name, epgChannelID, imageURL, openedCategoryID, videoURL, catID);
            }

            @Override // com.nst.yourname.view.utility.epg.EPGClickListener
            public void onEventClicked(int i, int i2, EPGEvent ePGEvent) {
                int i3;
                try {
                    i3 = Integer.parseInt(ePGEvent.getChannel().getStreamID());
                } catch (NumberFormatException unused) {
                    i3 = -1;
                }
                String name = ePGEvent.getChannel().getName();
                String num = ePGEvent.getChannel().getNum();
                String epgChannelID = ePGEvent.getChannel().getEpgChannelID();
                String imageURL = ePGEvent.getChannel().getImageURL();
                String catID = ePGEvent.getChannel().getCatID();
                String openedCategoryID = ePGEvent.getChannel().getOpenedCategoryID();
                String videoURL = ePGEvent.getChannel().getVideoURL();
                NewEPGFragment.this.epg.selectEvent(ePGEvent, true);
                if (NewEPGFragment.this.epg != null) {
                    NewEPGFragment.this.epg.clearEPGImageCache();
                }
                NewEPGFragment.this.epg.EPGPlayPopUp(NewEPGFragment.this.getContext(), string, i3, num, name, epgChannelID, imageURL, openedCategoryID, videoURL, catID);
            }

            @Override // com.nst.yourname.view.utility.epg.EPGClickListener
            public void onResetButtonClicked() {
                NewEPGFragment.this.epg.resetButtonClicked();
            }
        });
        if (!z) {
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        this.loginPreferencesSharedPref_opened_video = this.context.getSharedPreferences(AppConst.LOGIN_PREF_OPENED_VIDEO, 0);
        int i = this.loginPreferencesSharedPref_opened_video.getInt(AppConst.LOGIN_PREF_OPENED_VIDEO_ID, 0);
        String string = this.loginPreferencesSharedPref_opened_video.getString("LOGIN_PREF_OPENED_VIDEO_URL_M3U", "");
        if (i != 0) {
            this.loginPreferencesSharedPref_opened_video.edit().apply();
            if (this.epg != null) {
                if (mSettings.getPlayer() == 3) {
                    if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                        this.epg.playingURL = Uri.parse(string);
                    } else {
                        EPG epg2 = this.epg;
                        epg2.playingURL = Uri.parse(this.mFilePath + i + this.allowedFormat);
                    }
                    this.epg.stopRetry = false;
                    this.epg.retryCount = 0;
                    this.epg.retrying = false;
                    this.epg.initializePlayer();
                } else {
                    if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                        this.mVideoView.setVideoURI(Uri.parse(string), true, "");
                    } else {
                        NSTIJKPlayerSmallEPG nSTIJKPlayerSmallEPG = this.mVideoView;
                        nSTIJKPlayerSmallEPG.setVideoURI(Uri.parse(this.mFilePath + i + this.allowedFormat), true, "");
                    }
                    this.mVideoView.retryCount = 0;
                    this.mVideoView.retrying = false;
                    this.mVideoView.start();
                }
            }
        }
        if (!(this.epg == null || this.epg.loadEPGDataAsyncTaskRunning != 1 || this.tControl == null)) {
            this.tControl.resume();
        }
        super.onResume();
    }

    private void setToolbarLogoImagewithSearchView() {
        this.toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
    }

    @Override // android.support.v4.app.Fragment
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if (this.context != null && this.toolbar != null) {
            TypedValue typedValue = new TypedValue();
            if (this.context.getTheme().resolveAttribute(16843499, typedValue, true)) {
                TypedValue.complexToDimensionPixelSize(typedValue.data, this.context.getResources().getDisplayMetrics());
            }
            for (int i = 0; i < this.toolbar.getChildCount(); i++) {
                if (this.toolbar.getChildAt(i) instanceof ActionMenuView) {
                    ((Toolbar.LayoutParams) this.toolbar.getChildAt(i).getLayoutParams()).gravity = 16;
                }
            }
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }

    @Override // android.support.v4.app.Fragment
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.nav_home) {
            startActivity(new Intent(this.context, NewDashboardActivity.class));
        }
        if (itemId == R.id.nav_settings) {
            startActivity(new Intent(this.context, SettingsActivity.class));
        }
        if (itemId != R.id.action_logout1 || this.context == null) {
            return false;
        }
        new AlertDialog.Builder(this.context, R.style.AlertDialogCustom).setTitle(getResources().getString(R.string.logout_title)).setMessage(getResources().getString(R.string.logout_message)).setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            /* class com.nst.yourname.view.fragment.NewEPGFragment.AnonymousClass4 */

            public void onClick(DialogInterface dialogInterface, int i) {
                Utils.logoutUser(NewEPGFragment.this.context);
            }
        }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            /* class com.nst.yourname.view.fragment.NewEPGFragment.AnonymousClass3 */

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
        return false;
    }

    private void initialize() {
        int i;
        this.context = getContext();
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        if (this.context != null) {
            onCreateEPG(false);
            if (!this.getActiveLiveStreamCategoryId.equals("-1")) {
                if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                    i = this.liveStreamDBHandler.getAvailableCountM3U(this.getActiveLiveStreamCategoryId, "live");
                } else {
                    i = this.liveStreamDBHandler.getLiveStreamsCount(this.getActiveLiveStreamCategoryId);
                }
                if (i != 0 || this.getActiveLiveStreamCategoryId.equals(AppConst.PASSWORD_UNSET)) {
                    onWindowFocusChanged(this.getActiveLiveStreamCategoryId, this.epgFragment, R.id.epg);
                    return;
                }
                if (this.pbLoader != null) {
                    this.pbLoader.setVisibility(4);
                }
                if (this.tvNoStream != null) {
                    this.tvNoStream.setVisibility(0);
                }
            } else if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                ArrayList<FavouriteM3UModel> favouritesM3U = getFavouritesM3U();
                if (favouritesM3U == null || favouritesM3U.size() == 0) {
                    if (this.pbLoader != null) {
                        this.pbLoader.setVisibility(4);
                    }
                    if (this.tvNoStream != null) {
                        this.tvNoStream.setVisibility(0);
                    }
                    if (this.rl_add_channel_to_fav != null) {
                        this.rl_add_channel_to_fav.setVisibility(0);
                        return;
                    }
                    return;
                }
                onWindowFocusChanged(this.getActiveLiveStreamCategoryId, this.epgFragment, R.id.epg);
            } else {
                ArrayList<FavouriteDBModel> favourites = getFavourites();
                if (favourites == null || favourites.size() == 0) {
                    if (this.pbLoader != null) {
                        this.pbLoader.setVisibility(4);
                    }
                    if (this.tvNoStream != null) {
                        this.tvNoStream.setVisibility(0);
                    }
                    if (this.rl_add_channel_to_fav != null) {
                        this.rl_add_channel_to_fav.setVisibility(0);
                        return;
                    }
                    return;
                }
                onWindowFocusChanged(this.getActiveLiveStreamCategoryId, this.epgFragment, R.id.epg);
            }
        }
    }

    public ArrayList<FavouriteDBModel> getFavourites() {
        ArrayList<FavouriteDBModel> allFavourites;
        if (this.context == null) {
            return null;
        }
        this.database = new DatabaseHandler(this.context);
        if (this.database == null || (allFavourites = this.database.getAllFavourites("live", SharepreferenceDBHandler.getUserID(this.context))) == null || allFavourites.size() == 0) {
            return null;
        }
        return allFavourites;
    }

    public ArrayList<FavouriteM3UModel> getFavouritesM3U() {
        ArrayList<FavouriteM3UModel> favouriteM3U;
        if (this.context == null || this.liveStreamDBHandler == null || (favouriteM3U = this.liveStreamDBHandler.getFavouriteM3U("live")) == null || favouriteM3U.size() == 0) {
            return null;
        }
        return favouriteM3U;
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        if (this.epg != null) {
            this.epg.clearEPGImageCache();
            this.epg.destroyVideoPlayBack();
            this.epg.stopRetry = true;
        }
        if (this.loadEPGData != null && this.loadEPGData.getStatus().equals(AsyncTask.Status.RUNNING)) {
            this.loadEPGData.cancel(true);
        }
        if (this.epg != null && this.epg.loadEPGDataAsyncTaskRunning == 1) {
            this.tControl.cancel();
        }
        this.loginPreferencesSharedPref_opened_video = this.context.getSharedPreferences(AppConst.LOGIN_PREF_OPENED_VIDEO, 0);
        SharedPreferences.Editor edit = this.loginPreferencesSharedPref_opened_video.edit();
        edit.clear();
        edit.apply();
        super.onDestroyView();
        this.unbinder.unbind();
    }

    @Override // android.support.v4.app.Fragment
    public void onStart() {
        super.onStart();
    }

    @Override // android.support.v4.app.Fragment
    public void onStop() {
        if (this.epg != null) {
            this.epg.clearEPGImageCache();
            this.epg.destroyVideoPlayBack();
        }
        try {
            if (this.epg != null && this.epg.loadEPGDataAsyncTaskRunning == 1) {
                this.tControl.pause();
            }
        } catch (Exception unused) {
        }
        super.onStop();
        this.periodicTaskHandler.removeCallbacksAndMessages(null);
    }

    @Override // android.support.v4.app.Fragment
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // android.support.v4.app.Fragment
    public void onPause() {
        super.onPause();
        if (this.epg != null) {
            this.epg.clearEPGImageCache();
            this.epg.destroyVideoPlayBack();
        }
    }

    private void onWindowFocusChanged(String str, RelativeLayout relativeLayout, int i) {
        this.loadEPGData = new AsyncTask<Void, Integer, Boolean>(/*this.epg, 0, str, relativeLayout*/) {
            /* class com.nst.yourname.view.fragment.NewEPGFragment.AnonymousClass1AsyncLoadEPGData */
            private ArrayList<PasswordStatusDBModel> categoryWithPasword;
            EPG epg;
            private int epgStopped = 0;
            private boolean flag_full_epg;
            int i = 0;
            private ArrayList<String> listPassword = new ArrayList<>();
            private ArrayList<LiveStreamsDBModel> liveListDetail;
            private ArrayList<LiveStreamsDBModel> liveListDetailAvailable;
            private ArrayList<LiveStreamsDBModel> liveListDetailUnlcked;
            private ArrayList<LiveStreamsDBModel> liveListDetailUnlckedDetail;
            private Map<EPGChannel, List<EPGEvent>> map = Maps.newLinkedHashMap();
            final String val$categoryID;
            final RelativeLayout val$epgFragment;

            /* Incorrect method signature, types: com.nst.yourname.view.fragment.NewEPGFragment, com.nst.yourname.view.utility.epg.EPG, int */
            {
                //ToDo: check this...
                this.val$categoryID = str;
                this.val$epgFragment = relativeLayout;
                this.i = 0;
                epg = this.epg;
                this.epg.loadEPGDataAsyncTaskRunning = 1;
            }

            private void parseDataall_channels(String str) {
                if (this.flag_full_epg) {
                    parsedataallchannelsfull(str);
                } else {
                    parsedataallchannelslimitted(str);
                }
            }

            private void parseData(String str) {
                if (this.flag_full_epg) {
                    parseDatafull(str);
                } else {
                    parseDatalimitted(str);
                }
            }

            public ArrayList<LiveStreamsDBModel> getFavourites() {
                if (NewEPGFragment.this.context == null) {
                    return null;
                }
                if (SharepreferenceDBHandler.getCurrentAPPType(NewEPGFragment.this.context).equals(AppConst.TYPE_M3U)) {
                    NewEPGFragment.this.liveStreamDBHandler = new LiveStreamDBHandler(NewEPGFragment.this.context);
                    ArrayList<FavouriteM3UModel> favouriteM3U = NewEPGFragment.this.liveStreamDBHandler.getFavouriteM3U("live");
                    ArrayList<LiveStreamsDBModel> arrayList = new ArrayList<>();
                    Iterator<FavouriteM3UModel> it = favouriteM3U.iterator();
                    while (it.hasNext()) {
                        FavouriteM3UModel next = it.next();
                        ArrayList<LiveStreamsDBModel> m3UFavouriteRow = NewEPGFragment.this.liveStreamDBHandler.getM3UFavouriteRow(next.getCategoryID(), next.getUrl());
                        if (m3UFavouriteRow != null && m3UFavouriteRow.size() > 0) {
                            arrayList.add(m3UFavouriteRow.get(0));
                        }
                    }
                    if (arrayList.size() != 0) {
                        return arrayList;
                    }
                    return null;
                }
                NewEPGFragment.this.database = new DatabaseHandler(NewEPGFragment.this.context);
                ArrayList<FavouriteDBModel> allFavourites = NewEPGFragment.this.database.getAllFavourites("live", SharepreferenceDBHandler.getUserID(NewEPGFragment.this.context));
                ArrayList<LiveStreamsDBModel> arrayList2 = new ArrayList<>();
                Iterator<FavouriteDBModel> it2 = allFavourites.iterator();
                while (it2.hasNext()) {
                    FavouriteDBModel next2 = it2.next();
                    LiveStreamsDBModel liveStreamFavouriteRow = new LiveStreamDBHandler(NewEPGFragment.this.context).getLiveStreamFavouriteRow(next2.getCategoryID(), String.valueOf(next2.getStreamID()));
                    if (liveStreamFavouriteRow != null) {
                        arrayList2.add(liveStreamFavouriteRow);
                    }
                }
                if (arrayList2.size() != 0) {
                    return arrayList2;
                }
                return null;
            }

            private ArrayList<String> getPasswordSetCategories() {
                this.categoryWithPasword = NewEPGFragment.this.liveStreamDBHandler.getAllPasswordStatus(SharepreferenceDBHandler.getUserID(NewEPGFragment.this.context));
                if (this.categoryWithPasword != null) {
                    Iterator<PasswordStatusDBModel> it = this.categoryWithPasword.iterator();
                    while (it.hasNext()) {
                        PasswordStatusDBModel next = it.next();
                        if (next.getPasswordStatus().equals("1")) {
                            this.listPassword.add(next.getPasswordStatusCategoryId());
                        }
                    }
                }
                return this.listPassword;
            }

            private ArrayList<LiveStreamsDBModel> getUnlockedCategories(ArrayList<LiveStreamsDBModel> arrayList, ArrayList<String> arrayList2) {
                if (arrayList == null) {
                    return null;
                }
                Iterator<LiveStreamsDBModel> it = arrayList.iterator();
                while (it.hasNext()) {
                    LiveStreamsDBModel next = it.next();
                    boolean z = false;
                    if (arrayList2 != null) {
                        Iterator<String> it2 = arrayList2.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                break;
                            }
                            if (next.getCategoryId().equals(it2.next())) {
                                z = true;
                                break;
                            }
                        }
                        if (!z && this.liveListDetailUnlcked != null) {
                            this.liveListDetailUnlcked.add(next);
                        }
                    }
                }
                return this.liveListDetailUnlcked;
            }

            private void parsedataallchannelsfull(String str) {
                ArrayList<LiveStreamsDBModel> arrayList;
                EPGChannel ePGChannel;
                EPGChannel ePGChannel2;
                EPGEvent ePGEvent = null;
                EPGEvent ePGEvent2;
                String str2 = str;
                NewEPGFragment.this.liveStreamDBHandler = new LiveStreamDBHandler(NewEPGFragment.this.context);
                try {
                    LiveStreamDBHandler liveStreamDBHandler = new LiveStreamDBHandler(NewEPGFragment.this.context);
                    if (str2.equals("-1")) {
                        arrayList = getFavourites();
                    } else {
                        arrayList = liveStreamDBHandler.getAllLiveStreasWithSkyId(str2, "live");
                    }
                    this.categoryWithPasword = new ArrayList<>();
                    this.liveListDetailUnlcked = new ArrayList<>();
                    this.liveListDetailUnlckedDetail = new ArrayList<>();
                    this.liveListDetailAvailable = new ArrayList<>();
                    this.liveListDetail = new ArrayList<>();
                    if (NewEPGFragment.this.liveStreamDBHandler.getParentalStatusCount(SharepreferenceDBHandler.getUserID(NewEPGFragment.this.context)) <= 0 || arrayList == null) {
                        this.liveListDetailAvailable = arrayList;
                    } else {
                        this.listPassword = getPasswordSetCategories();
                        if (this.listPassword != null) {
                            this.liveListDetailUnlckedDetail = getUnlockedCategories(arrayList, this.listPassword);
                        }
                        this.liveListDetailAvailable = this.liveListDetailUnlckedDetail;
                    }
                    if (this.liveListDetailAvailable != null) {
                        ePGChannel2 = null;
                        EPGChannel ePGChannel3 = null;
                        int i2 = 0;
                        ePGChannel = null;
                        int i3 = -1;
                        EPGEvent ePGEvent3 = null;
                        while (true) {
                            if (i2 < this.liveListDetailAvailable.size()) {
                                if (NewEPGFragment.this.tControl != null) {
                                    NewEPGFragment.this.tControl.waitIfPaused();
                                }
                                if (NewEPGFragment.this.loadEPGData == null || !NewEPGFragment.this.loadEPGData.isCancelled()) {
                                    if (NewEPGFragment.this.tControl != null && NewEPGFragment.this.tControl.isCancelled()) {
                                        break;
                                    }
                                    String name = this.liveListDetailAvailable.get(i2).getName();
                                    String epgChannelId = this.liveListDetailAvailable.get(i2).getEpgChannelId();
                                    String streamIcon = this.liveListDetailAvailable.get(i2).getStreamIcon();
                                    EPGChannel ePGChannel4 = /*r5*/null;
                                    String str3 = epgChannelId;
                                    EPGChannel ePGChannel5 = ePGChannel3;
                                    EPGChannel ePGChannel6 = new EPGChannel(streamIcon, name, i2, this.liveListDetailAvailable.get(i2).getStreamId(), this.liveListDetailAvailable.get(i2).getNum(), this.liveListDetailAvailable.get(i2).getEpgChannelId(), this.liveListDetailAvailable.get(i2).getCategoryId(), this.liveListDetailAvailable.get(i2).getUrl(), str);
                                    if (ePGChannel == null) {
                                        ePGChannel = ePGChannel4;
                                    }
                                    if (ePGChannel5 != null) {
                                        ePGChannel4.setPreviousChannel(ePGChannel5);
                                        ePGChannel5.setNextChannel(ePGChannel4);
                                    }
                                    List<EPGEvent> arrayList2 = new ArrayList<>();
                                    this.map.put(ePGChannel4, arrayList2);
                                    if (str3.equals("")) {
                                        long currentTimeMillis = System.currentTimeMillis() - Long.parseLong("86400000");
                                        long parseLong = Long.parseLong("7200000") + currentTimeMillis;
                                        long j = currentTimeMillis;
                                        EPGEvent ePGEvent4 = ePGEvent3;
                                        int i4 = 0;
                                        while (true) {
                                            if (i4 < 50) {
                                                if (NewEPGFragment.this.loadEPGData != null && NewEPGFragment.this.loadEPGData.isCancelled()) {
                                                    break;
                                                }
                                                EPGEvent ePGEvent5 = new EPGEvent(ePGChannel4, j, parseLong, NewEPGFragment.this.context.getResources().getString(R.string.no_information), streamIcon, "");
                                                if (ePGEvent != null) {
                                                    ePGEvent5.setPreviousEvent(ePGEvent);
                                                    ePGEvent.setNextEvent(ePGEvent5);
                                                }
                                                ePGChannel4.addEvent(ePGEvent5);
                                                arrayList2.add(ePGEvent5);
                                                i4++;
                                                ePGEvent4 = ePGEvent5;
                                                j = parseLong;
                                                parseLong = Long.parseLong("7200000") + parseLong;
                                            } else {
                                                break;
                                            }
                                        }
                                    } else {
                                        i3++;
                                        ArrayList<XMLTVProgrammePojo> epg2 = NewEPGFragment.this.liveStreamDBHandler.getEPG(str3);
                                        if (epg2 != null && epg2.size() != 0) {
                                            ePGEvent = ePGEvent3;
                                            int i5 = 0;
                                            Long l = null;
                                            while (true) {
                                                if (i5 < epg2.size()) {
                                                    if (NewEPGFragment.this.loadEPGData != null && NewEPGFragment.this.loadEPGData.isCancelled()) {
                                                        break;
                                                    }
                                                    String start = epg2.get(i5).getStart();
                                                    String stop = epg2.get(i5).getStop();
                                                    String title = epg2.get(i5).getTitle();
                                                    String desc = epg2.get(i5).getDesc();
                                                    Long valueOf = Long.valueOf(Utils.epgTimeConverter(start));
                                                    Long valueOf2 = Long.valueOf(Utils.epgTimeConverter(stop));
                                                    if (l != null && valueOf.equals(l)) {
                                                        ePGEvent2 = new EPGEvent(ePGChannel4, valueOf.longValue(), valueOf2.longValue(), title, streamIcon, desc);
                                                        if (ePGEvent != null) {
                                                            ePGEvent2.setPreviousEvent(ePGEvent);
                                                            ePGEvent.setNextEvent(ePGEvent2);
                                                        }
                                                        ePGChannel4.addEvent(ePGEvent2);
                                                        arrayList2.add(ePGEvent2);
                                                    } else if (l != null) {
                                                        EPGEvent ePGEvent6 = new EPGEvent(ePGChannel4, l.longValue(), valueOf.longValue(), NewEPGFragment.this.context.getResources().getString(R.string.no_information), streamIcon, "");
                                                        if (ePGEvent != null) {
                                                            ePGEvent6.setPreviousEvent(ePGEvent);
                                                            ePGEvent.setNextEvent(ePGEvent6);
                                                        }
                                                        ePGChannel4.addEvent(ePGEvent6);
                                                        arrayList2.add(ePGEvent6);
                                                        EPGEvent ePGEvent7 = new EPGEvent(ePGChannel4, valueOf.longValue(), valueOf2.longValue(), title, streamIcon, desc);
                                                        ePGEvent7.setPreviousEvent(ePGEvent6);
                                                        ePGEvent6.setNextEvent(ePGEvent7);
                                                        ePGChannel4.addEvent(ePGEvent7);
                                                        arrayList2.add(ePGEvent7);
                                                        ePGEvent2 = ePGEvent7;
                                                    } else {
                                                        ePGEvent2 = new EPGEvent(ePGChannel4, valueOf.longValue(), valueOf2.longValue(), title, streamIcon, desc);
                                                        if (ePGEvent != null) {
                                                            ePGEvent2.setPreviousEvent(ePGEvent);
                                                            ePGEvent.setNextEvent(ePGEvent2);
                                                        }
                                                        ePGChannel4.addEvent(ePGEvent2);
                                                        arrayList2.add(ePGEvent2);
                                                    }
                                                    long currentTimeMillis2 = System.currentTimeMillis();
                                                    if (i5 != epg2.size() - 1 || valueOf2.longValue() >= currentTimeMillis2) {
                                                        ePGEvent = ePGEvent2;
                                                    } else {
                                                        long longValue = valueOf2.longValue();
                                                        ePGEvent = new EPGEvent(ePGChannel4, longValue, longValue + currentTimeMillis2 + Long.parseLong("7200000"), NewEPGFragment.this.context.getResources().getString(R.string.no_information), streamIcon, "");
                                                        ePGEvent.setPreviousEvent(ePGEvent2);
                                                        ePGEvent2.setNextEvent(ePGEvent);
                                                        ePGChannel4.addEvent(ePGEvent);
                                                        arrayList2.add(ePGEvent);
                                                    }
                                                    if (i5 == 0 && valueOf.longValue() > currentTimeMillis2) {
                                                        EPGEvent ePGEvent8 = new EPGEvent(ePGChannel4, currentTimeMillis2 - Long.parseLong("86400000"), valueOf.longValue(), NewEPGFragment.this.context.getResources().getString(R.string.no_information), streamIcon, "");
                                                        ePGEvent8.setPreviousEvent(ePGEvent);
                                                        ePGEvent.setNextEvent(ePGEvent8);
                                                        ePGChannel4.addEvent(ePGEvent8);
                                                        arrayList2.add(ePGEvent8);
                                                        ePGEvent = ePGEvent8;
                                                    }
                                                    i5++;
                                                    l = valueOf2;
                                                } else {
                                                    break;
                                                }
                                            }
                                        } else {
                                            long currentTimeMillis3 = System.currentTimeMillis() - Long.parseLong("86400000");
                                            long parseLong2 = Long.parseLong("7200000") + currentTimeMillis3;
                                            long j2 = currentTimeMillis3;
                                            EPGEvent ePGEvent9 = ePGEvent3;
                                            int i6 = 0;
                                            while (true) {
                                                if (i6 < 50) {
                                                    if (NewEPGFragment.this.loadEPGData != null && NewEPGFragment.this.loadEPGData.isCancelled()) {
                                                        break;
                                                    }
                                                    EPGEvent ePGEvent10 = new EPGEvent(ePGChannel4, j2, parseLong2, NewEPGFragment.this.context.getResources().getString(R.string.no_information), streamIcon, "");
                                                    if (ePGEvent != null) {
                                                        ePGEvent10.setPreviousEvent(ePGEvent);
                                                        ePGEvent.setNextEvent(ePGEvent10);
                                                    }
                                                    ePGChannel4.addEvent(ePGEvent10);
                                                    arrayList2.add(ePGEvent10);
                                                    i6++;
                                                    ePGEvent9 = ePGEvent10;
                                                    j2 = parseLong2;
                                                    parseLong2 = Long.parseLong("7200000") + parseLong2;
                                                } else {
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    ePGEvent3 = ePGEvent;
                                    int i7 = i3;
                                    if (i7 != 10) {
                                        if (i7 == 0 || i7 % 50 != 0) {
                                            i2++;
                                            i3 = i7;
                                            ePGChannel2 = ePGChannel4;
                                            ePGChannel3 = ePGChannel2;
                                        }
                                    }
                                    publishProgress(Integer.valueOf(i7));
                                    i2++;
                                    i3 = i7;
                                    ePGChannel2 = ePGChannel4;
                                    ePGChannel3 = ePGChannel2;
                                } else {
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                    } else {
                        ePGChannel2 = null;
                        ePGChannel = null;
                    }
                    if (ePGChannel2 != null) {
                        ePGChannel2.setNextChannel(ePGChannel);
                    }
                    if (ePGChannel != null) {
                        ePGChannel.setPreviousChannel(ePGChannel2);
                    }
                } catch (Throwable th) {
                    throw new RuntimeException(th.getMessage(), th);
                }
            }

            private void parseDatafull(String str) {
                ArrayList<LiveStreamsDBModel> arrayList;
                EPGChannel ePGChannel;
                EPGChannel ePGChannel2;
                EPGEvent ePGEvent;
                String str2 = str;
                NewEPGFragment.this.liveStreamDBHandler = new LiveStreamDBHandler(NewEPGFragment.this.context);
                try {
                    LiveStreamDBHandler liveStreamDBHandler = new LiveStreamDBHandler(NewEPGFragment.this.context);
                    if (str2.equals("-1")) {
                        arrayList = getFavourites();
                    } else {
                        arrayList = liveStreamDBHandler.getAllLiveStreasWithSkyId(str2, "live");
                    }
                    this.categoryWithPasword = new ArrayList<>();
                    this.liveListDetailUnlcked = new ArrayList<>();
                    this.liveListDetailUnlckedDetail = new ArrayList<>();
                    this.liveListDetailAvailable = new ArrayList<>();
                    this.liveListDetail = new ArrayList<>();
                    if (NewEPGFragment.this.liveStreamDBHandler.getParentalStatusCount(SharepreferenceDBHandler.getUserID(NewEPGFragment.this.context)) <= 0 || arrayList == null) {
                        this.liveListDetailAvailable = arrayList;
                    } else {
                        this.listPassword = getPasswordSetCategories();
                        if (this.listPassword != null) {
                            this.liveListDetailUnlckedDetail = getUnlockedCategories(arrayList, this.listPassword);
                        }
                        this.liveListDetailAvailable = this.liveListDetailUnlckedDetail;
                    }
                    if (this.liveListDetailAvailable != null) {
                        ePGChannel2 = null;
                        int i2 = -1;
                        EPGChannel ePGChannel3 = null;
                        int i3 = 0;
                        ePGChannel = null;
                        int i4 = 0;
                        EPGEvent ePGEvent2 = null;
                        while (true) {
                            if (i3 < this.liveListDetailAvailable.size()) {
                                if (NewEPGFragment.this.tControl != null) {
                                    NewEPGFragment.this.tControl.waitIfPaused();
                                }
                                if (NewEPGFragment.this.loadEPGData == null || !NewEPGFragment.this.loadEPGData.isCancelled()) {
                                    if (NewEPGFragment.this.tControl != null && NewEPGFragment.this.tControl.isCancelled()) {
                                        break;
                                    }
                                    String name = this.liveListDetailAvailable.get(i3).getName();
                                    String epgChannelId = this.liveListDetailAvailable.get(i3).getEpgChannelId();
                                    String streamIcon = this.liveListDetailAvailable.get(i3).getStreamIcon();
                                    String streamId = this.liveListDetailAvailable.get(i3).getStreamId();
                                    String num = this.liveListDetailAvailable.get(i3).getNum();
                                    String epgChannelId2 = this.liveListDetailAvailable.get(i3).getEpgChannelId();
                                    String url = this.liveListDetailAvailable.get(i3).getUrl();
                                    String categoryId = this.liveListDetailAvailable.get(i3).getCategoryId();
                                    if (!epgChannelId.equals("")) {
                                        int i5 = i2 + 1;
                                        ArrayList<XMLTVProgrammePojo> epg2 = NewEPGFragment.this.liveStreamDBHandler.getEPG(epgChannelId);
                                        if (epg2 == null || epg2.size() == 0) {
                                            ePGChannel3 = ePGChannel3;
                                            i2 = i5;
                                        } else {
                                            EPGChannel ePGChannel4 = /*r3*/null;
                                            ArrayList<XMLTVProgrammePojo> arrayList2 = epg2;
                                            EPGChannel ePGChannel5 = ePGChannel3;
                                            EPGChannel ePGChannel6 = new EPGChannel(streamIcon, name, i4, streamId, num, epgChannelId2, categoryId, url, str);
                                            i4++;
                                            if (ePGChannel == null) {
                                                ePGChannel = ePGChannel4;
                                            }
                                            if (ePGChannel5 != null) {
                                                ePGChannel4.setPreviousChannel(ePGChannel5);
                                                ePGChannel5.setNextChannel(ePGChannel4);
                                            }
                                            List<EPGEvent> arrayList3 = new ArrayList<>();
                                            this.map.put(ePGChannel4, arrayList3);
                                            EPGEvent ePGEvent3 = ePGEvent2;
                                            int i6 = 0;
                                            Long l = null;
                                            while (true) {
                                                if (i6 < arrayList2.size()) {
                                                    if (NewEPGFragment.this.loadEPGData != null && NewEPGFragment.this.loadEPGData.isCancelled()) {
                                                        break;
                                                    }
                                                    ArrayList<XMLTVProgrammePojo> arrayList4 = arrayList2;
                                                    String start = arrayList4.get(i6).getStart();
                                                    String stop = arrayList4.get(i6).getStop();
                                                    String title = arrayList4.get(i6).getTitle();
                                                    String desc = arrayList4.get(i6).getDesc();
                                                    Long valueOf = Long.valueOf(Utils.epgTimeConverter(start));
                                                    Long valueOf2 = Long.valueOf(Utils.epgTimeConverter(stop));
                                                    if (l != null && valueOf.equals(l)) {
                                                        ePGEvent = new EPGEvent(ePGChannel4, valueOf.longValue(), valueOf2.longValue(), title, streamIcon, desc);
                                                        if (ePGEvent3 != null) {
                                                            ePGEvent.setPreviousEvent(ePGEvent3);
                                                            ePGEvent3.setNextEvent(ePGEvent);
                                                        }
                                                        ePGChannel4.addEvent(ePGEvent);
                                                        arrayList3.add(ePGEvent);
                                                    } else if (l != null) {
                                                        EPGEvent ePGEvent4 = new EPGEvent(ePGChannel4, l.longValue(), valueOf.longValue(), NewEPGFragment.this.context.getResources().getString(R.string.no_information), streamIcon, "");
                                                        if (ePGEvent3 != null) {
                                                            ePGEvent4.setPreviousEvent(ePGEvent3);
                                                            ePGEvent3.setNextEvent(ePGEvent4);
                                                        }
                                                        ePGChannel4.addEvent(ePGEvent4);
                                                        arrayList3.add(ePGEvent4);
                                                        ePGEvent = new EPGEvent(ePGChannel4, valueOf.longValue(), valueOf2.longValue(), title, streamIcon, desc);
                                                        ePGEvent.setPreviousEvent(ePGEvent4);
                                                        ePGEvent4.setNextEvent(ePGEvent);
                                                        ePGChannel4.addEvent(ePGEvent);
                                                        arrayList3.add(ePGEvent);
                                                    } else {
                                                        ePGEvent = new EPGEvent(ePGChannel4, valueOf.longValue(), valueOf2.longValue(), title, streamIcon, desc);
                                                        if (ePGEvent3 != null) {
                                                            ePGEvent.setPreviousEvent(ePGEvent3);
                                                            ePGEvent3.setNextEvent(ePGEvent);
                                                        }
                                                        ePGChannel4.addEvent(ePGEvent);
                                                        arrayList3.add(ePGEvent);
                                                    }
                                                    long currentTimeMillis = System.currentTimeMillis();
                                                    if (i6 == arrayList4.size() - 1 && valueOf2.longValue() < currentTimeMillis) {
                                                        long longValue = valueOf2.longValue();
                                                        EPGEvent ePGEvent5 = new EPGEvent(ePGChannel4, longValue, longValue + currentTimeMillis + Long.parseLong("7200000"), NewEPGFragment.this.context.getResources().getString(R.string.no_information), streamIcon, "");
                                                        ePGEvent5.setPreviousEvent(ePGEvent);
                                                        ePGEvent.setNextEvent(ePGEvent5);
                                                        ePGChannel4.addEvent(ePGEvent5);
                                                        arrayList3.add(ePGEvent5);
                                                        ePGEvent = ePGEvent5;
                                                    }
                                                    if (i6 != 0 || valueOf.longValue() <= currentTimeMillis) {
                                                        ePGEvent3 = ePGEvent;
                                                    } else {
                                                        ePGEvent3 = new EPGEvent(ePGChannel4, currentTimeMillis - Long.parseLong("86400000"), valueOf.longValue(), NewEPGFragment.this.context.getResources().getString(R.string.no_information), streamIcon, "");
                                                        ePGEvent3.setPreviousEvent(ePGEvent);
                                                        ePGEvent.setNextEvent(ePGEvent3);
                                                        ePGChannel4.addEvent(ePGEvent3);
                                                        arrayList3.add(ePGEvent3);
                                                    }
                                                    i6++;
                                                    arrayList2 = arrayList4;
                                                    l = valueOf2;
                                                } else {
                                                    break;
                                                }
                                            }
                                            ePGEvent2 = ePGEvent3;
                                            i2 = i5;
                                            ePGChannel2 = ePGChannel4;
                                            ePGChannel3 = ePGChannel2;
                                        }
                                    }
                                    if (i2 != 10) {
                                        if (i2 == 0 || i2 % 50 != 0) {
                                            i3++;
                                        }
                                    }
                                    publishProgress(Integer.valueOf(i2));
                                    i3++;
                                } else {
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                    } else {
                        ePGChannel2 = null;
                        ePGChannel = null;
                    }
                    if (ePGChannel2 != null) {
                        ePGChannel2.setNextChannel(ePGChannel);
                    }
                    if (ePGChannel != null) {
                        ePGChannel.setPreviousChannel(ePGChannel2);
                    }
                } catch (Throwable th) {
                    throw new RuntimeException(th.getMessage(), th);
                }
            }

            private void parseDatalimitted(String str) {
                ArrayList<LiveStreamsDBModel> arrayList;
                EPGChannel ePGChannel;
                EPGChannel ePGChannel2;
                int i2;
                ArrayList<XMLTVProgrammePojo> epg2;
                int i3;
                EPGEvent ePGEvent;
                String str2 = str;
                NewEPGFragment.this.liveStreamDBHandler = new LiveStreamDBHandler(NewEPGFragment.this.context);
                try {
                    LiveStreamDBHandler liveStreamDBHandler = new LiveStreamDBHandler(NewEPGFragment.this.context);
                    if (str2.equals("-1")) {
                        arrayList = getFavourites();
                    } else {
                        arrayList = liveStreamDBHandler.getAllLiveStreasWithSkyId(str2, "live");
                    }
                    this.categoryWithPasword = new ArrayList<>();
                    this.liveListDetailUnlcked = new ArrayList<>();
                    this.liveListDetailUnlckedDetail = new ArrayList<>();
                    this.liveListDetailAvailable = new ArrayList<>();
                    this.liveListDetail = new ArrayList<>();
                    if (NewEPGFragment.this.liveStreamDBHandler.getParentalStatusCount(SharepreferenceDBHandler.getUserID(NewEPGFragment.this.context)) <= 0 || arrayList == null) {
                        this.liveListDetailAvailable = arrayList;
                    } else {
                        this.listPassword = getPasswordSetCategories();
                        if (this.listPassword != null) {
                            this.liveListDetailUnlckedDetail = getUnlockedCategories(arrayList, this.listPassword);
                        }
                        this.liveListDetailAvailable = this.liveListDetailUnlckedDetail;
                    }
                    if (this.liveListDetailAvailable != null) {
                        ePGChannel2 = null;
                        int i4 = -1;
                        EPGChannel ePGChannel3 = null;
                        int i5 = 0;
                        ePGChannel = null;
                        int i6 = 0;
                        EPGEvent ePGEvent2 = null;
                        while (true) {
                            if (i5 < this.liveListDetailAvailable.size()) {
                                if (NewEPGFragment.this.tControl != null) {
                                    NewEPGFragment.this.tControl.waitIfPaused();
                                }
                                if (NewEPGFragment.this.loadEPGData == null || !NewEPGFragment.this.loadEPGData.isCancelled()) {
                                    if (NewEPGFragment.this.tControl != null && NewEPGFragment.this.tControl.isCancelled()) {
                                        break;
                                    }
                                    String name = this.liveListDetailAvailable.get(i5).getName();
                                    String epgChannelId = this.liveListDetailAvailable.get(i5).getEpgChannelId();
                                    String streamIcon = this.liveListDetailAvailable.get(i5).getStreamIcon();
                                    String streamId = this.liveListDetailAvailable.get(i5).getStreamId();
                                    String num = this.liveListDetailAvailable.get(i5).getNum();
                                    String epgChannelId2 = this.liveListDetailAvailable.get(i5).getEpgChannelId();
                                    String url = this.liveListDetailAvailable.get(i5).getUrl();
                                    String categoryId = this.liveListDetailAvailable.get(i5).getCategoryId();
                                    if (epgChannelId.equals("") || (epg2 = NewEPGFragment.this.liveStreamDBHandler.getEPG(epgChannelId)) == null || epg2.size() == 0) {
                                        i2 = i5;
                                        ePGChannel3 = ePGChannel3;
                                    } else {
                                        int i7 = i4 + 1;
                                        EPGChannel ePGChannel4 = /*r5*/null;
                                        EPGChannel ePGChannel5 = ePGChannel3;
                                        EPGChannel ePGChannel6 = new EPGChannel(streamIcon, name, i6, streamId, num, epgChannelId2, categoryId, url, str);
                                        i6++;
                                        if (ePGChannel == null) {
                                            ePGChannel = ePGChannel4;
                                        }
                                        if (ePGChannel5 != null) {
                                            ePGChannel4.setPreviousChannel(ePGChannel5);
                                            ePGChannel5.setNextChannel(ePGChannel4);
                                        }
                                        List<EPGEvent> arrayList2 = new ArrayList<>();
                                        this.map.put(ePGChannel4, arrayList2);
                                        EPGEvent ePGEvent3 = ePGEvent2;
                                        int i8 = 0;
                                        boolean z = false;
                                        Long l = null;
                                        while (true) {
                                            if (i8 >= epg2.size()) {
                                                break;
                                            }
                                            String start = epg2.get(i8).getStart();
                                            String stop = epg2.get(i8).getStop();
                                            String title = epg2.get(i8).getTitle();
                                            String desc = epg2.get(i8).getDesc();
                                            Long valueOf = Long.valueOf(Utils.epgTimeConverter(start));
                                            Long valueOf2 = Long.valueOf(Utils.epgTimeConverter(stop));
                                            if (NewEPGFragment.this.loadEPGData != null && NewEPGFragment.this.loadEPGData.isCancelled()) {
                                                break;
                                            }
                                            int i9 = i5;
                                            EPGChannel ePGChannel7 = ePGChannel;
                                            int i10 = i8;
                                            if (!Utils.isEventVisible(valueOf.longValue(), valueOf2.longValue(), NewEPGFragment.this.context)) {
                                                if (!z) {
                                                    i3 = i10;
                                                    i8 = i3 + 1;
                                                    i5 = i9;
                                                    ePGChannel = ePGChannel7;
                                                }
                                            }
                                            long millis = LocalDateTime.now().toDateTime().getMillis() + Utils.getTimeShiftMilliSeconds(NewEPGFragment.this.context);
                                            long j = millis;
                                            if (valueOf.longValue() > 12600000 + millis) {
                                                ePGEvent = ePGEvent3;
                                            } else if (l != null && valueOf.equals(l)) {
                                                ePGEvent = new EPGEvent(ePGChannel4, valueOf.longValue(), valueOf2.longValue(), title, streamIcon, desc);
                                                if (ePGEvent3 != null) {
                                                    ePGEvent.setPreviousEvent(ePGEvent3);
                                                    ePGEvent3.setNextEvent(ePGEvent);
                                                }
                                                ePGChannel4.addEvent(ePGEvent);
                                                arrayList2.add(ePGEvent);
                                            } else if (l != null) {
                                                EPGEvent ePGEvent4 = new EPGEvent(ePGChannel4, l.longValue(), valueOf.longValue(), NewEPGFragment.this.context.getResources().getString(R.string.no_information), streamIcon, "");
                                                if (ePGEvent3 != null) {
                                                    ePGEvent4.setPreviousEvent(ePGEvent3);
                                                    ePGEvent3.setNextEvent(ePGEvent4);
                                                }
                                                ePGChannel4.addEvent(ePGEvent4);
                                                arrayList2.add(ePGEvent4);
                                                EPGEvent ePGEvent5 = new EPGEvent(ePGChannel4, valueOf.longValue(), valueOf2.longValue(), title, streamIcon, desc);
                                                ePGEvent5.setPreviousEvent(ePGEvent4);
                                                ePGEvent4.setNextEvent(ePGEvent5);
                                                ePGChannel4.addEvent(ePGEvent5);
                                                arrayList2.add(ePGEvent5);
                                                ePGEvent = ePGEvent5;
                                            } else {
                                                ePGEvent = new EPGEvent(ePGChannel4, valueOf.longValue(), valueOf2.longValue(), title, streamIcon, desc);
                                                if (ePGEvent3 != null) {
                                                    ePGEvent.setPreviousEvent(ePGEvent3);
                                                    ePGEvent3.setNextEvent(ePGEvent);
                                                }
                                                ePGChannel4.addEvent(ePGEvent);
                                                arrayList2.add(ePGEvent);
                                            }
                                            i3 = i10;
                                            if (i3 == epg2.size() - 1 && valueOf2.longValue() < j) {
                                                long longValue = valueOf2.longValue();
                                                long parseLong = Long.parseLong("3600000") + longValue;
                                                EPGEvent ePGEvent6 = ePGEvent;
                                                long j2 = longValue;
                                                int i11 = 0;
                                                while (true) {
                                                    if (i11 < 3) {
                                                        if (NewEPGFragment.this.loadEPGData != null && NewEPGFragment.this.loadEPGData.isCancelled()) {
                                                            break;
                                                        }
                                                        EPGEvent ePGEvent7 = new EPGEvent(ePGChannel4, j2, parseLong, NewEPGFragment.this.context.getResources().getString(R.string.no_information), streamIcon, "");
                                                        if (ePGEvent6 != null) {
                                                            ePGEvent7.setPreviousEvent(ePGEvent6);
                                                            ePGEvent6.setNextEvent(ePGEvent7);
                                                        }
                                                        ePGChannel4.addEvent(ePGEvent7);
                                                        arrayList2.add(ePGEvent7);
                                                        i11++;
                                                        ePGEvent6 = ePGEvent7;
                                                        j2 = parseLong;
                                                        parseLong = Long.parseLong("3600000") + parseLong;
                                                    } else {
                                                        break;
                                                    }
                                                }
                                                ePGEvent = ePGEvent6;
                                            }
                                            if (i3 != 0 || valueOf.longValue() <= j) {
                                                ePGEvent3 = ePGEvent;
                                            } else {
                                                long longValue2 = valueOf.longValue();
                                                EPGEvent ePGEvent8 = ePGEvent;
                                                long j3 = j;
                                                int i12 = 0;
                                                while (true) {
                                                    if (i12 < 3) {
                                                        if (NewEPGFragment.this.loadEPGData != null && NewEPGFragment.this.loadEPGData.isCancelled()) {
                                                            break;
                                                        }
                                                        EPGEvent ePGEvent9 = new EPGEvent(ePGChannel4, j3, longValue2, NewEPGFragment.this.context.getResources().getString(R.string.no_information), streamIcon, "");
                                                        if (ePGEvent8 != null) {
                                                            ePGEvent9.setPreviousEvent(ePGEvent8);
                                                            ePGEvent8.setNextEvent(ePGEvent9);
                                                        }
                                                        ePGChannel4.addEvent(ePGEvent9);
                                                        arrayList2.add(ePGEvent9);
                                                        i12++;
                                                        ePGEvent8 = ePGEvent9;
                                                        j3 = longValue2;
                                                        longValue2 = Long.parseLong("3600000") + longValue2;
                                                    } else {
                                                        break;
                                                    }
                                                }
                                                ePGEvent3 = ePGEvent8;
                                            }
                                            l = valueOf2;
                                            z = true;
                                            i8 = i3 + 1;
                                            i5 = i9;
                                            ePGChannel = ePGChannel7;
                                        }
                                        i2 = i5;
                                        ePGEvent2 = ePGEvent3;
                                        ePGChannel2 = ePGChannel4;
                                        ePGChannel3 = ePGChannel2;
                                        i4 = i7;
                                        ePGChannel = ePGChannel;
                                    }
                                    if (i4 != 10) {
                                        if (i4 == 0 || i4 % 50 != 0) {
                                            i5 = i2 + 1;
                                        }
                                    }
                                    publishProgress(Integer.valueOf(i4));
                                    i5 = i2 + 1;
                                } else {
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                    } else {
                        ePGChannel2 = null;
                        ePGChannel = null;
                    }
                    if (ePGChannel2 != null) {
                        ePGChannel2.setNextChannel(ePGChannel);
                    }
                    if (ePGChannel != null) {
                        ePGChannel.setPreviousChannel(ePGChannel2);
                    }
                } catch (Throwable th) {
                    throw new RuntimeException(th.getMessage(), th);
                }
            }

            private void parsedataallchannelslimitted(String str) {
                ArrayList<LiveStreamsDBModel> arrayList;
                EPGChannel ePGChannel;
                EPGChannel ePGChannel2;
                EPGChannel ePGChannel3;
                int i2;
                EPGEvent ePGEvent = null;
                EPGEvent ePGEvent2;
                String str2 = str;
                NewEPGFragment.this.liveStreamDBHandler = new LiveStreamDBHandler(NewEPGFragment.this.context);
                try {
                    LiveStreamDBHandler liveStreamDBHandler = new LiveStreamDBHandler(NewEPGFragment.this.context);
                    if (str2.equals("-1")) {
                        arrayList = getFavourites();
                    } else {
                        arrayList = liveStreamDBHandler.getAllLiveStreasWithSkyId(str2, "live");
                    }
                    this.categoryWithPasword = new ArrayList<>();
                    this.liveListDetailUnlcked = new ArrayList<>();
                    this.liveListDetailUnlckedDetail = new ArrayList<>();
                    this.liveListDetailAvailable = new ArrayList<>();
                    this.liveListDetail = new ArrayList<>();
                    if (NewEPGFragment.this.liveStreamDBHandler.getParentalStatusCount(SharepreferenceDBHandler.getUserID(NewEPGFragment.this.context)) <= 0 || arrayList == null) {
                        this.liveListDetailAvailable = arrayList;
                    } else {
                        this.listPassword = getPasswordSetCategories();
                        if (this.listPassword != null) {
                            this.liveListDetailUnlckedDetail = getUnlockedCategories(arrayList, this.listPassword);
                        }
                        this.liveListDetailAvailable = this.liveListDetailUnlckedDetail;
                    }
                    if (this.liveListDetailAvailable != null) {
                        ePGChannel2 = null;
                        EPGChannel ePGChannel4 = null;
                        int i3 = 0;
                        ePGChannel = null;
                        int i4 = -1;
                        EPGEvent ePGEvent3 = null;
                        while (true) {
                            if (i3 < this.liveListDetailAvailable.size()) {
                                if (NewEPGFragment.this.tControl != null) {
                                    NewEPGFragment.this.tControl.waitIfPaused();
                                }
                                if (NewEPGFragment.this.loadEPGData == null || !NewEPGFragment.this.loadEPGData.isCancelled()) {
                                    if (NewEPGFragment.this.tControl != null && NewEPGFragment.this.tControl.isCancelled()) {
                                        break;
                                    }
                                    String name = this.liveListDetailAvailable.get(i3).getName();
                                    String epgChannelId = this.liveListDetailAvailable.get(i3).getEpgChannelId();
                                    String streamIcon = this.liveListDetailAvailable.get(i3).getStreamIcon();
                                    EPGChannel ePGChannel5 = /*r5*/null;
                                    String str3 = epgChannelId;
                                    EPGChannel ePGChannel6 = ePGChannel4;
                                    EPGChannel ePGChannel7 = new EPGChannel(streamIcon, name, i3, this.liveListDetailAvailable.get(i3).getStreamId(), this.liveListDetailAvailable.get(i3).getNum(), this.liveListDetailAvailable.get(i3).getEpgChannelId(), this.liveListDetailAvailable.get(i3).getCategoryId(), this.liveListDetailAvailable.get(i3).getUrl(), str);
                                    if (ePGChannel == null) {
                                        ePGChannel = ePGChannel5;
                                    }
                                    if (ePGChannel6 != null) {
                                        ePGChannel5.setPreviousChannel(ePGChannel6);
                                        ePGChannel6.setNextChannel(ePGChannel5);
                                    }
                                    List<EPGEvent> arrayList2 = new ArrayList<>();
                                    this.map.put(ePGChannel5, arrayList2);
                                    if (str3 == null || str3.equals("")) {
                                        i2 = i3;
                                        ePGChannel3 = ePGChannel;
                                        long millis = LocalDateTime.now().toDateTime().getMillis();
                                        long parseLong = Long.parseLong("3600000") + millis;
                                        long j = millis;
                                        EPGEvent ePGEvent4 = ePGEvent3;
                                        int i5 = 0;
                                        while (true) {
                                            if (i5 < 3) {
                                                if (NewEPGFragment.this.loadEPGData != null && NewEPGFragment.this.loadEPGData.isCancelled()) {
                                                    break;
                                                }
                                                EPGEvent ePGEvent5 = new EPGEvent(ePGChannel5, j, parseLong, NewEPGFragment.this.context.getResources().getString(R.string.no_information), streamIcon, "");
                                                if (ePGEvent != null) {
                                                    ePGEvent5.setPreviousEvent(ePGEvent);
                                                    ePGEvent.setNextEvent(ePGEvent5);
                                                }
                                                ePGChannel5.addEvent(ePGEvent5);
                                                arrayList2.add(ePGEvent5);
                                                i5++;
                                                j = parseLong;
                                                ePGEvent4 = ePGEvent5;
                                                parseLong = Long.parseLong("3600000") + parseLong;
                                            } else {
                                                break;
                                            }
                                        }
                                    } else {
                                        i4++;
                                        ArrayList<XMLTVProgrammePojo> epg2 = NewEPGFragment.this.liveStreamDBHandler.getEPG(str3);
                                        if (epg2 == null || epg2.size() == 0) {
                                            i2 = i3;
                                            ePGChannel3 = ePGChannel;
                                            long millis2 = LocalDateTime.now().toDateTime().getMillis();
                                            long parseLong2 = Long.parseLong("3600000") + millis2;
                                            long j2 = millis2;
                                            ePGEvent = ePGEvent3;
                                            int i6 = 0;
                                            while (true) {
                                                if (i6 < 3) {
                                                    if (NewEPGFragment.this.loadEPGData != null && NewEPGFragment.this.loadEPGData.isCancelled()) {
                                                        break;
                                                    }
                                                    EPGEvent ePGEvent6 = new EPGEvent(ePGChannel5, j2, parseLong2, NewEPGFragment.this.context.getResources().getString(R.string.no_information), streamIcon, "");
                                                    if (ePGEvent != null) {
                                                        ePGEvent6.setPreviousEvent(ePGEvent);
                                                        ePGEvent.setNextEvent(ePGEvent6);
                                                    }
                                                    ePGChannel5.addEvent(ePGEvent6);
                                                    arrayList2.add(ePGEvent6);
                                                    i6++;
                                                    j2 = parseLong2;
                                                    ePGEvent = ePGEvent6;
                                                    parseLong2 = Long.parseLong("3600000") + parseLong2;
                                                } else {
                                                    break;
                                                }
                                            }
                                        } else {
                                            EPGEvent ePGEvent7 = ePGEvent3;
                                            int i7 = 0;
                                            boolean z = false;
                                            Long l = null;
                                            while (true) {
                                                if (i7 < epg2.size()) {
                                                    if (NewEPGFragment.this.loadEPGData != null && NewEPGFragment.this.loadEPGData.isCancelled()) {
                                                        break;
                                                    }
                                                    String start = epg2.get(i7).getStart();
                                                    String stop = epg2.get(i7).getStop();
                                                    String title = epg2.get(i7).getTitle();
                                                    String desc = epg2.get(i7).getDesc();
                                                    Long valueOf = Long.valueOf(Utils.epgTimeConverter(start));
                                                    Long valueOf2 = Long.valueOf(Utils.epgTimeConverter(stop));
                                                    ArrayList<XMLTVProgrammePojo> arrayList3 = epg2;
                                                    int i8 = i3;
                                                    EPGChannel ePGChannel8 = ePGChannel;
                                                    if (Utils.isEventVisible(valueOf.longValue(), valueOf2.longValue(), NewEPGFragment.this.context) || z) {
                                                        if (valueOf.longValue() <= LocalDateTime.now().toDateTime().getMillis() + Utils.getTimeShiftMilliSeconds(NewEPGFragment.this.context) + 12600000) {
                                                            if (l != null && valueOf.equals(l)) {
                                                                ePGEvent2 = new EPGEvent(ePGChannel5, valueOf.longValue(), valueOf2.longValue(), title, streamIcon, desc);
                                                                if (ePGEvent7 != null) {
                                                                    ePGEvent2.setPreviousEvent(ePGEvent7);
                                                                    ePGEvent7.setNextEvent(ePGEvent2);
                                                                }
                                                                ePGChannel5.addEvent(ePGEvent2);
                                                                arrayList2.add(ePGEvent2);
                                                            } else if (l != null) {
                                                                EPGEvent ePGEvent8 = new EPGEvent(ePGChannel5, l.longValue(), valueOf.longValue(), NewEPGFragment.this.context.getResources().getString(R.string.no_information), streamIcon, "");
                                                                if (ePGEvent7 != null) {
                                                                    ePGEvent8.setPreviousEvent(ePGEvent7);
                                                                    ePGEvent7.setNextEvent(ePGEvent8);
                                                                }
                                                                ePGChannel5.addEvent(ePGEvent8);
                                                                arrayList2.add(ePGEvent8);
                                                                EPGEvent ePGEvent9 = new EPGEvent(ePGChannel5, valueOf.longValue(), valueOf2.longValue(), title, streamIcon, desc);
                                                                ePGEvent9.setPreviousEvent(ePGEvent8);
                                                                ePGEvent8.setNextEvent(ePGEvent9);
                                                                ePGChannel5.addEvent(ePGEvent9);
                                                                arrayList2.add(ePGEvent9);
                                                                ePGEvent2 = ePGEvent9;
                                                            } else {
                                                                ePGEvent2 = new EPGEvent(ePGChannel5, valueOf.longValue(), valueOf2.longValue(), title, streamIcon, desc);
                                                                if (ePGEvent7 != null) {
                                                                    ePGEvent2.setPreviousEvent(ePGEvent7);
                                                                    ePGEvent7.setNextEvent(ePGEvent2);
                                                                }
                                                                ePGChannel5.addEvent(ePGEvent2);
                                                                arrayList2.add(ePGEvent2);
                                                            }
                                                            ePGEvent7 = ePGEvent2;
                                                            l = valueOf2;
                                                        }
                                                        z = true;
                                                    }
                                                    i7++;
                                                    epg2 = arrayList3;
                                                    i3 = i8;
                                                    ePGChannel = ePGChannel8;
                                                } else {
                                                    break;
                                                }
                                            }
                                            i2 = i3;
                                            ePGChannel3 = ePGChannel;
                                            ePGEvent = ePGEvent7;
                                        }
                                    }
                                    ePGEvent3 = ePGEvent;
                                    int i9 = i4;
                                    if (i9 != 10) {
                                        if (i9 == 0 || i9 % 50 != 0) {
                                            i3 = i2 + 1;
                                            i4 = i9;
                                            ePGChannel2 = ePGChannel5;
                                            ePGChannel4 = ePGChannel2;
                                            ePGChannel = ePGChannel3;
                                        }
                                    }
                                    publishProgress(Integer.valueOf(i9));
                                    i3 = i2 + 1;
                                    i4 = i9;
                                    ePGChannel2 = ePGChannel5;
                                    ePGChannel4 = ePGChannel2;
                                    ePGChannel = ePGChannel3;
                                } else {
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                    } else {
                        ePGChannel2 = null;
                        ePGChannel = null;
                    }
                    if (ePGChannel2 != null) {
                        ePGChannel2.setNextChannel(ePGChannel);
                    }
                    if (ePGChannel != null) {
                        ePGChannel.setPreviousChannel(ePGChannel2);
                    }
                } catch (Throwable th) {
                    throw new RuntimeException(th.getMessage(), th);
                }
            }

            public Boolean doInBackground(Void... voidArr) {
                String string = NewEPGFragment.this.context.getSharedPreferences(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, 0).getString(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, "");
                this.flag_full_epg = NewEPGFragment.this.context.getSharedPreferences(AppConst.LOGIN_PREF_AUTO_START, 0).getBoolean(AppConst.LOGIN_PREF_FULL_EPG, false);
                try {
                    if (string.equals("all")) {
                        parseDataall_channels(this.val$categoryID);
                    } else {
                        parseData(this.val$categoryID);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }

            public void onProgressUpdate(Integer... numArr) {
                if (this.map != null && this.map.size() > 0) {
                    if (NewEPGFragment.this.pbLoader != null) {
                        NewEPGFragment.this.pbLoader.setVisibility(4);
                    }
                    if (NewEPGFragment.this.epgView != null && this.epg != null) {
                        this.epg.hideProgressDialogBox();
                        NewEPGFragment.this.epgView.setVisibility(0);
                        try {
                            this.epg.setEPGData(new EPGDataImpl(this.map));
                            this.epg.recalculateAndRedraw(null, false, this.val$epgFragment, this.epg);
                        } catch (Exception unused) {
                        }
                    }
                }
            }

            public void onPostExecute(Boolean bool) {
                if (NewEPGFragment.this.epgView != null && this.map != null && this.map.size() > 0 && this.epg != null) {
                    this.epg.loadEPGDataAsyncTaskRunning = 0;
                    this.epg.hideProgressDialogBox();
                    NewEPGFragment.this.epgView.setVisibility(0);
                    try {
                        this.epg.setEPGData(new EPGDataImpl(this.map));
                        this.epg.recalculateAndRedraw(null, false, this.val$epgFragment, this.epg);
                    } catch (Exception unused) {
                    }
                } else if (NewEPGFragment.this.epgView != null) {
                    NewEPGFragment.this.epgView.setVisibility(8);
                    NewEPGFragment.this.tvNoRecordFound.setVisibility(0);
                    NewEPGFragment.this.tvNoRecordFound.setText(NewEPGFragment.this.getResources().getString(R.string.no_epg_guide_found));
                }
                if (NewEPGFragment.this.pbLoader != null) {
                    NewEPGFragment.this.pbLoader.setVisibility(4);
                }
            }
        }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, new Void[0]);
    }

    @OnClick({R.id.app_video_box})
    public void onViewClicked() {
        EPGEvent selectedEvent = this.epg.getSelectedEvent();
        if (this.epg == null || selectedEvent == null) {
            onCreateEPG(false);
        } else {
            onCreateEPG(true);
        }
    }
}
