package com.nst.yourname.view.inbuiltsmartersplayer;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.callback.GetEpisdoeDetailsCallback;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class VideoInfo implements Parcelable {
    public static final int AR_16_9_FIT_PARENT = 4;
    public static final int AR_4_3_FIT_PARENT = 5;
    public static final int AR_ASPECT_FILL_PARENT = 1;
    public static final int AR_ASPECT_FIT_PARENT = 0;
    public static final int AR_ASPECT_WRAP_CONTENT = 2;
    public static final int AR_MATCH_PARENT = 3;
    public static final Creator<VideoInfo> CREATOR = new Creator<VideoInfo>() {
        /* class com.nst.yourname.view.inbuiltsmartersplayer.VideoInfo.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public VideoInfo createFromParcel(Parcel parcel) {
            return new VideoInfo(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public VideoInfo[] newArray(int i) {
            return new VideoInfo[i];
        }
    };
    public static final String PLAYER_IMPL_IJK = "ijk";
    public static final String PLAYER_IMPL_SYSTEM = "system";
    public static int floatView_height = 300;
    public static int floatView_width = 400;
    public static float floatView_x = 2.14748365E9f;
    public static float floatView_y = 2.14748365E9f;
    private static VideoInfo myObj;
    private String APPType;
    private Handler EPGHandler;
    private boolean MaxTime;
    private int aspectRatio;
    private ArrayList<LiveStreamsDBModel> availableChannels;
    private List<GetEpisdoeDetailsCallback> availableSeries;
    private int bgColor;
    private Context context;
    private String currentChannelLogo;
    private String currentEpgChannelID;
    private int currentPositionSeekbar;
    private boolean currentVideoAsCover;
    private int currentWindowIndex;
    private String episodeId;
    private List<GetEpisdoeDetailsCallback> episodeList;
    private String fingerprint;
    private boolean fullScreenAnimation;
    private boolean fullScreenOnly;
    private boolean fullscreen;
    private boolean headerFooterHidden;
    private boolean isHWOrSW;
    private boolean isTimeElapsed;
    private boolean isVODPlayer;
    private String lastFingerprint;
    private Uri lastUri;
    private boolean looping;
    private boolean nextOrPrevPressed;
    private HashSet<Option> options;
    private String playerImpl;
    private boolean portraitWhenFullScreen;
    private boolean progress;
    private int recentlyFinishedStreamID;
    private boolean resuming;
    private int retryCount;
    private int retryInterval;
    private boolean retrying;
    private boolean seeked;
    private boolean showTopBar;
    private boolean stopHandler;
    private int streamid;
    private long timeElapsed;
    private long timeShift;
    private String title;
    private String typeofstream;
    private Uri uri;
    private int videoNum;

    public int describeContents() {
        return 0;
    }

    public static VideoInfo getInstance() {
        if (myObj == null) {
            myObj = new VideoInfo();
        }
        return myObj;
    }

    public VideoInfo(VideoInfo videoInfo) {
        this.options = new HashSet<>();
        this.showTopBar = false;
        this.fingerprint = "12345";
        this.portraitWhenFullScreen = true;
        this.aspectRatio = 0;
        this.retryInterval = 0;
        this.bgColor = -16777216;
        this.playerImpl = PLAYER_IMPL_IJK;
        this.fullScreenAnimation = true;
        this.looping = false;
        this.currentVideoAsCover = true;
        this.fullScreenOnly = false;
        this.currentWindowIndex = 0;
        this.retryCount = 0;
        this.progress = false;
        this.stopHandler = false;
        this.nextOrPrevPressed = false;
        this.title = videoInfo.title;
        this.portraitWhenFullScreen = videoInfo.portraitWhenFullScreen;
        this.aspectRatio = videoInfo.aspectRatio;
        Iterator<Option> it = videoInfo.options.iterator();
        while (it.hasNext()) {
            try {
                this.options.add(it.next().clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        this.showTopBar = videoInfo.showTopBar;
        this.retryInterval = videoInfo.retryInterval;
        this.bgColor = videoInfo.bgColor;
        this.playerImpl = videoInfo.playerImpl;
        this.fullScreenAnimation = videoInfo.fullScreenAnimation;
        this.looping = videoInfo.looping;
        this.currentVideoAsCover = videoInfo.currentVideoAsCover;
        this.fullScreenOnly = videoInfo.fullScreenOnly;
    }

    public boolean isFullScreenOnly() {
        return this.fullScreenOnly;
    }

    public VideoInfo setFullScreenOnly(boolean z) {
        this.fullScreenOnly = z;
        return this;
    }

    public boolean isFullScreenAnimation() {
        return this.fullScreenAnimation;
    }

    public VideoInfo setFullScreenAnimation(boolean z) {
        this.fullScreenAnimation = z;
        return this;
    }

    public VideoInfo setEpisodeId(String str) {
        this.episodeId = str;
        return this;
    }

    public String getEpisodeId() {
        return this.episodeId;
    }

    public String getPlayerImpl() {
        return this.playerImpl;
    }

    public VideoInfo setPlayerImpl(String str) {
        this.playerImpl = str;
        return this;
    }

    public int getBgColor() {
        return this.bgColor;
    }

    public VideoInfo setBgColor(@ColorInt int i) {
        this.bgColor = i;
        return this;
    }

    public int getRetryInterval() {
        return this.retryInterval;
    }

    public VideoInfo setRetryInterval(int i) {
        this.retryInterval = i;
        return this;
    }

    public HashSet<Option> getOptions() {
        return this.options;
    }

    public VideoInfo addOption(Option option) {
        this.options.add(option);
        return this;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: java.util.HashSet<com.nst.yourname.view.inbuiltsmartersplayer.Option>} */
    /* JADX WARNING: Multi-variable type inference failed */
    public VideoInfo addOptions(Collection<Option> collection) {
        this.options.addAll(collection);
        return this;
    }

    public boolean isShowTopBar() {
        return this.showTopBar;
    }

    public VideoInfo setShowTopBar(boolean z) {
        this.showTopBar = z;
        return this;
    }

    public boolean isPortraitWhenFullScreen() {
        return this.portraitWhenFullScreen;
    }

    public VideoInfo setPortraitWhenFullScreen(boolean z) {
        this.portraitWhenFullScreen = z;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public VideoInfo setTitle(String str) {
        this.title = str;
        return this;
    }

    public int getAspectRatio() {
        return this.aspectRatio;
    }

    public VideoInfo setAspectRatio(int i) {
        this.aspectRatio = i;
        return this;
    }

    public VideoInfo() {
        this.options = new HashSet<>();
        this.showTopBar = false;
        this.fingerprint = "12345";
        this.portraitWhenFullScreen = true;
        this.aspectRatio = 0;
        this.retryInterval = 0;
        this.bgColor = -16777216;
        this.playerImpl = PLAYER_IMPL_IJK;
        this.fullScreenAnimation = true;
        this.looping = false;
        this.currentVideoAsCover = true;
        this.fullScreenOnly = false;
        this.currentWindowIndex = 0;
        this.retryCount = 0;
        this.progress = false;
        this.stopHandler = false;
        this.nextOrPrevPressed = false;
    }

    public VideoInfo(Uri uri2) {
        this.options = new HashSet<>();
        this.showTopBar = false;
        this.fingerprint = "12345";
        this.portraitWhenFullScreen = true;
        this.aspectRatio = 0;
        this.retryInterval = 0;
        this.bgColor = -16777216;
        this.playerImpl = PLAYER_IMPL_IJK;
        this.fullScreenAnimation = true;
        this.looping = false;
        this.currentVideoAsCover = true;
        this.fullScreenOnly = false;
        this.currentWindowIndex = 0;
        this.retryCount = 0;
        this.progress = false;
        this.stopHandler = false;
        this.nextOrPrevPressed = false;
        this.uri = uri2;
    }

    public VideoInfo(String str) {
        this.options = new HashSet<>();
        this.showTopBar = false;
        this.fingerprint = "12345";
        this.portraitWhenFullScreen = true;
        this.aspectRatio = 0;
        this.retryInterval = 0;
        this.bgColor = -16777216;
        this.playerImpl = PLAYER_IMPL_IJK;
        this.fullScreenAnimation = true;
        this.looping = false;
        this.currentVideoAsCover = true;
        this.fullScreenOnly = false;
        this.currentWindowIndex = 0;
        this.retryCount = 0;
        this.progress = false;
        this.stopHandler = false;
        this.nextOrPrevPressed = false;
        this.uri = Uri.parse(str);
    }

    protected VideoInfo(Parcel parcel) {
        this.options = new HashSet<>();
        boolean z = false;
        this.showTopBar = false;
        this.fingerprint = "12345";
        this.portraitWhenFullScreen = true;
        this.aspectRatio = 0;
        this.retryInterval = 0;
        this.bgColor = -16777216;
        this.playerImpl = PLAYER_IMPL_IJK;
        this.fullScreenAnimation = true;
        this.looping = false;
        this.currentVideoAsCover = true;
        this.fullScreenOnly = false;
        this.currentWindowIndex = 0;
        this.retryCount = 0;
        this.progress = false;
        this.stopHandler = false;
        this.nextOrPrevPressed = false;
        this.fingerprint = parcel.readString();
        this.uri = (Uri) parcel.readParcelable(Uri.class.getClassLoader());
        this.title = parcel.readString();
        this.portraitWhenFullScreen = parcel.readByte() != 0;
        this.aspectRatio = parcel.readInt();
        this.lastFingerprint = parcel.readString();
        this.lastUri = (Uri) parcel.readParcelable(Uri.class.getClassLoader());
        this.options = (HashSet) parcel.readSerializable();
        this.showTopBar = parcel.readByte() != 0;
        this.retryInterval = parcel.readInt();
        this.bgColor = parcel.readInt();
        this.playerImpl = parcel.readString();
        this.fullScreenAnimation = parcel.readByte() != 0;
        this.looping = parcel.readByte() != 0;
        this.currentVideoAsCover = parcel.readByte() != 0;
        this.fullScreenOnly = parcel.readByte() != 0 ? true : z;
    }

    public String getFingerprint() {
        return this.fingerprint;
    }

    public Uri getUri() {
        return this.uri;
    }

    public VideoInfo setUri(Uri uri2) {
        this.uri = uri2;
        return this;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.fingerprint);
        parcel.writeParcelable(this.uri, i);
        parcel.writeString(this.title);
        parcel.writeByte(this.portraitWhenFullScreen ? (byte) 1 : 0);
        parcel.writeInt(this.aspectRatio);
        parcel.writeString(this.lastFingerprint);
        parcel.writeParcelable(this.lastUri, i);
        parcel.writeSerializable(this.options);
        parcel.writeByte(this.showTopBar ? (byte) 1 : 0);
        parcel.writeInt(this.retryInterval);
        parcel.writeInt(this.bgColor);
        parcel.writeString(this.playerImpl);
        parcel.writeByte(this.fullScreenAnimation ? (byte) 1 : 0);
        parcel.writeByte(this.looping ? (byte) 1 : 0);
        parcel.writeByte(this.currentVideoAsCover ? (byte) 1 : 0);
        parcel.writeByte(this.fullScreenOnly ? (byte) 1 : 0);
    }

    public boolean isLooping() {
        return this.looping;
    }

    public void setLooping(boolean z) {
        this.looping = z;
    }

    public boolean isCurrentVideoAsCover() {
        return this.currentVideoAsCover;
    }

    public void setCurrentVideoAsCover(boolean z) {
        this.currentVideoAsCover = z;
    }

    public VideoInfo setCurrentWindowIndex(int i) {
        this.currentWindowIndex = i;
        return this;
    }

    public int getCurrentWindowIndex() {
        return this.currentWindowIndex;
    }

    public VideoInfo setisHWOrSW(boolean z) {
        this.isHWOrSW = z;
        return this;
    }

    public boolean getisHWOrSW() {
        return this.isHWOrSW;
    }

    public VideoInfo setisTimeElapsed(boolean z) {
        this.isTimeElapsed = z;
        return this;
    }

    public boolean getisTimeElapsed() {
        return this.isTimeElapsed;
    }

    public VideoInfo settimeElapsed(long j) {
        this.timeElapsed = j;
        return this;
    }

    public long gettimeElapsed() {
        return this.timeElapsed;
    }

    public VideoInfo setisVODPlayer(boolean z) {
        this.isVODPlayer = z;
        return this;
    }

    public boolean getisVODPlayer() {
        return this.isVODPlayer;
    }

    public VideoInfo setresuming(boolean z) {
        this.resuming = z;
        return this;
    }

    public boolean getresuming() {
        return this.resuming;
    }

    public VideoInfo setMaxTime(boolean z) {
        this.MaxTime = z;
        return this;
    }

    public boolean getMaxTime() {
        return this.MaxTime;
    }

    public VideoInfo setretryCount(int i) {
        this.retryCount = i;
        return this;
    }

    public int getretryCount() {
        return this.retryCount;
    }

    public void setretrying(boolean z) {
        this.retrying = z;
    }

    public boolean getretrying() {
        return this.retrying;
    }

    public boolean setseeked(boolean z) {
        this.seeked = z;
        return this.seeked;
    }

    public boolean getseeked() {
        return this.seeked;
    }

    public VideoInfo setfullscreen(boolean z) {
        this.fullscreen = z;
        return this;
    }

    public boolean getfullscreen() {
        return this.fullscreen;
    }

    public VideoInfo setstreamid(int i) {
        this.streamid = i;
        return this;
    }

    public int getStreamid() {
        return this.streamid;
    }

    public VideoInfo settypeofstream(String str) {
        this.typeofstream = str;
        return this;
    }

    public String getTypeofstream() {
        return this.typeofstream;
    }

    public VideoInfo setvideoNum(int i) {
        this.videoNum = i;
        return this;
    }

    public int getVideoNum() {
        return this.videoNum;
    }

    public VideoInfo setAvailableChannels(ArrayList<LiveStreamsDBModel> arrayList) {
        this.availableChannels = arrayList;
        return this;
    }

    public VideoInfo setAvailableSeries(List<GetEpisdoeDetailsCallback> list) {
        this.availableSeries = list;
        return this;
    }

    public ArrayList<LiveStreamsDBModel> getAvailableChannels() {
        return this.availableChannels;
    }

    public List<GetEpisdoeDetailsCallback> getAvailableSeries() {
        return this.availableSeries;
    }

    public VideoInfo setAPPType(String str) {
        this.APPType = str;
        return this;
    }

    public String getAPPType() {
        return this.APPType;
    }

    public VideoInfo setProgress(boolean z) {
        this.progress = z;
        return this;
    }

    public boolean getProgress() {
        return this.progress;
    }

    public VideoInfo setCurrentPositionSeekbar(int i) {
        this.currentPositionSeekbar = i;
        return this;
    }

    public int getCurrentPositionSeekbar() {
        return this.currentPositionSeekbar;
    }

    public VideoInfo setheaderFooterHidden(boolean z) {
        this.headerFooterHidden = z;
        return this;
    }

    public boolean getheaderFooterHidden() {
        return this.headerFooterHidden;
    }

    public VideoInfo setCurrentChannelLogo(String str) {
        this.currentChannelLogo = str;
        return this;
    }

    public String getCurrentChannelLogo() {
        return this.currentChannelLogo;
    }

    public VideoInfo setCurrentEpgChannelID(String str) {
        this.currentEpgChannelID = str;
        return this;
    }

    public String getCurrentEpgChannelID() {
        return this.currentEpgChannelID;
    }

    public VideoInfo setContext(Context context2) {
        this.context = context2;
        return this;
    }

    public Context getContext() {
        return this.context;
    }

    public VideoInfo setTimeShift(long j) {
        this.timeShift = j;
        return this;
    }

    public long getTimeShift() {
        return this.timeShift;
    }

    public VideoInfo setStopHandler(boolean z) {
        this.stopHandler = z;
        return this;
    }

    public boolean getStopHandler() {
        return this.stopHandler;
    }

    public VideoInfo setNextOrPrevPressed(boolean z) {
        this.nextOrPrevPressed = z;
        return this;
    }

    public boolean getNextOrPrevPressed() {
        return this.nextOrPrevPressed;
    }

    public VideoInfo setRecentlyFinishedStreamID(int i) {
        this.recentlyFinishedStreamID = i;
        return this;
    }

    public int getRecentlyFinishedStreamID() {
        return this.recentlyFinishedStreamID;
    }
}
