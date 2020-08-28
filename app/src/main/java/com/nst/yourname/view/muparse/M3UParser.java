package com.nst.yourname.view.muparse;

import android.content.Context;
import android.os.Build;
import android.util.Base64;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.M3UCategoriesModel;
import com.nst.yourname.model.M3UModel;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.SeriesStreamsDatabaseHandler;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class M3UParser {
    private static final String EXT_GROUP_TITLE = "group-title";
    private static final String EXT_ID = "tvg-id";
    private static final String EXT_ID_CAPITAL = "tvg-ID";
    private static final String EXT_INF = "#EXTINF";
    private static final String EXT_LOGO = "tvg-logo";
    private static final String EXT_M3U = "#EXTM3U";
    private static final String EXT_NAME = "tvg-name";
    private static final String EXT_PLAYLIST_NAME = "#PLAYLIST";
    private static final String EXT_URL = "http://";
    private static final String EXT_URL_HTTPS = "https://";
    LiveStreamDBHandler liveStreamDBHandler;
    HashMap<String, String> myMap = new HashMap<>();
    private SeriesStreamsDatabaseHandler seriesStreamsDatabaseHandler;

    public String convertStreamToString(InputStream inputStream) {
        try {
            return new Scanner(inputStream).useDelimiter("\\A").next();
        } catch (NoSuchElementException unused) {
            return "";
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:102:0x0206  */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x0240 A[Catch:{ Exception -> 0x0274 }] */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x01e8  */
    public boolean parseFileNew(InputStream inputStream, Context context) throws FileNotFoundException {
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        String[] split = convertStreamToString(inputStream).split(EXT_INF);
        int i = 0;
        for (int i2 = 0; i2 < split.length; i2++) {
            i++;
            String str = split[i2];
            String str2 = "";
            M3UModel m3UModel = new M3UModel();
            M3UCategoriesModel m3UCategoriesModel = new M3UCategoriesModel();
            try {
                if (!str.contains(EXT_M3U) && !str.equals("")) {
                    if (!str.equals("#")) {
                        if (str.contains(EXT_LOGO) || str.contains(EXT_GROUP_TITLE) || str.contains(EXT_NAME)) {
                            String[] split2 = str.split("\"");
                            String str3 = str2;
                            for (int i3 = 0; i3 < split2.length; i3++) {
                                if (!split2[i3].contains(EXT_ID)) {
                                    if (!split2[i3].contains(EXT_ID_CAPITAL)) {
                                        if (split2[i3].contains(EXT_NAME)) {
                                            int i4 = i3 + 1;
                                            if (i4 < split2.length) {
                                                m3UModel.setName(split2[i4]);
                                            } else {
                                                m3UModel.setName("");
                                            }
                                        } else if (split2[i3].contains(EXT_LOGO)) {
                                            int i5 = i3 + 1;
                                            if (i5 < split2.length) {
                                                m3UModel.setStreamIcon(split2[i5]);
                                            } else {
                                                m3UModel.setStreamIcon("");
                                            }
                                        } else if (split2[i3].contains(EXT_GROUP_TITLE)) {
                                            int i6 = i3 + 1;
                                            if (i6 < split2.length) {
                                                str3 = split2[i6];
                                                m3UModel.setCategoryName(str3);
                                                if (!str3.equals("")) {
                                                    m3UCategoriesModel.setCategoryId(String.valueOf(base64Encode(str3)));
                                                    m3UCategoriesModel.setCategoryName(str3);
                                                }
                                            } else {
                                                m3UModel.setCategoryName("");
                                            }
                                        }
                                    }
                                }
                                int i7 = i3 + 1;
                                if (i7 < split2.length) {
                                    m3UModel.setEpgChannelId(split2[i7]);
                                } else {
                                    m3UModel.setEpgChannelId("");
                                }
                            }
                            str2 = str3;
                        }
                        String str4 = "";
                        String str5 = "";
                        if (!str.contains(EXT_LOGO) && !str.contains(EXT_GROUP_TITLE)) {
                            if (!str.contains(EXT_NAME)) {
                                if (str.contains(EXT_URL)) {
                                    try {
                                        str4 = str.substring(str.indexOf("\",") + 5, str.lastIndexOf(EXT_URL)).replace("\n", "");
                                    } catch (Exception unused) {
                                        str4 = "";
                                    }
                                } else if (str.contains(EXT_URL_HTTPS)) {
                                    try {
                                        str4 = str.substring(str.indexOf("\",") + 5, str.lastIndexOf(EXT_URL_HTTPS)).replace("\n", "");
                                    } catch (Exception unused2) {
                                        str4 = "";
                                    }
                                }
                                if (str.contains(EXT_URL)) {
                                    try {
                                        str5 = str.substring(str.lastIndexOf(EXT_URL)).replace("\n", "").replace("\r", "");
                                    } catch (Exception unused3) {
                                        str5 = "";
                                    }
                                } else if (str.contains(EXT_URL_HTTPS)) {
                                    try {
                                        str5 = str.substring(str.lastIndexOf(EXT_URL_HTTPS)).replace("\n", "").replace("\r", "");
                                    } catch (Exception unused4) {
                                        str5 = "";
                                    }
                                }
                                m3UModel.setName(str4);
                                m3UModel.setNum(Integer.valueOf(i));
                                m3UModel.setUrl(str5);
                                if (!str2.equals("")) {
                                    if (!hashMap.containsKey(str2)) {
                                        hashMap.put(str2, m3UCategoriesModel);
                                    }
                                    if (hashMap.containsKey(str2)) {
                                        m3UModel.setCategoryId(String.valueOf(((M3UCategoriesModel) hashMap.get(str2)).getCategoryId()));
                                    } else {
                                        m3UModel.setCategoryId(String.valueOf(base64Encode(str2)));
                                    }
                                }
                                hashMap2.put(String.valueOf(i2), m3UModel);
                            }
                        }
                        if (str.contains(EXT_URL)) {
                            try {
                                str4 = str.substring(str.indexOf("\",") + 2, str.lastIndexOf(EXT_URL)).replace("\n", "");
                            } catch (Exception unused5) {
                                str4 = str.substring(str.indexOf("\",") + 2, str.lastIndexOf("\n")).replace("\n", "");
                            }
                            if (str.contains(EXT_URL)) {
                            }
                            m3UModel.setName(str4);
                            m3UModel.setNum(Integer.valueOf(i));
                            m3UModel.setUrl(str5);
                            if (!str2.equals("")) {
                            }
                            hashMap2.put(String.valueOf(i2), m3UModel);
                        } else {
                            if (str.contains(EXT_URL_HTTPS)) {
                                try {
                                    str4 = str.substring(str.indexOf("\",") + 2, str.lastIndexOf(EXT_URL_HTTPS)).replace("\n", "");
                                } catch (Exception unused6) {
                                    str4 = str.substring(str.indexOf("\",") + 2, str.lastIndexOf("\n")).replace("\n", "");
                                }
                            }
                            if (str.contains(EXT_URL)) {
                            }
                            m3UModel.setName(str4);
                            m3UModel.setNum(Integer.valueOf(i));
                            m3UModel.setUrl(str5);
                            if (!str2.equals("")) {
                            }
                            hashMap2.put(String.valueOf(i2), m3UModel);
                        }
                    }
                }
            } catch (Exception unused7) {
            }
        }
        if (context != null) {
            this.liveStreamDBHandler = new LiveStreamDBHandler(context);
            this.seriesStreamsDatabaseHandler = new SeriesStreamsDatabaseHandler(context);
            if (this.liveStreamDBHandler != null) {
                String currentDateValue = currentDateValue();
                if (currentDateValue != null) {
                    this.seriesStreamsDatabaseHandler.updateSeriesStreamsCatDBStatusAndDate(AppConst.DB_SERIES_STREAMS_CAT, AppConst.DB_SERIES_STREAMS_CAT_ID, AppConst.DB_UPDATED_STATUS_FINISH, currentDateValue);
                    this.seriesStreamsDatabaseHandler.updateseriesStreamsDBStatusAndDate(AppConst.DB_SERIES_STREAMS, AppConst.DB_SERIES_STREAMS_ID, AppConst.DB_UPDATED_STATUS_FINISH, currentDateValue);
                    this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, currentDateValue);
                }
                this.liveStreamDBHandler.addAllAvailableChannelALLM3U(hashMap2);
                this.liveStreamDBHandler.addALLM3UCategories(hashMap);
                this.liveStreamDBHandler.removeDuplicateEntriesM3U();
                this.liveStreamDBHandler.removeDuplicateFavEntriesM3U();
                this.liveStreamDBHandler.updateMoveToStatusMapping("live");
                this.liveStreamDBHandler.updateMoveToStatusMapping(AppConst.EVENT_TYPE_MOVIE);
                this.liveStreamDBHandler.updateMoveToStatusMapping("series");
            }
        }
        return true;
    }

    private String currentDateValue() {
        return Utils.parseDateToddMMyyyy(Calendar.getInstance().getTime().toString());
    }

    public String parseFileForM3uFile(InputStream inputStream, Context context) throws FileNotFoundException {
        String str;
        String[] split = convertStreamToString(inputStream).split(EXT_INF);
        for (String str2 : split) {
            try {
                if (str2.equals("")) {
                    continue;
                } else {
                    if (str2.contains(EXT_URL)) {
                        try {
                            str = str2.substring(str2.lastIndexOf(EXT_URL)).replace("\n", "").replace("\r", "");
                        } catch (Exception unused) {
                            str = "";
                        }
                    } else if (str2.contains(EXT_URL_HTTPS)) {
                        try {
                            str = str2.substring(str2.lastIndexOf(EXT_URL_HTTPS)).replace("\n", "").replace("\r", "");
                        } catch (Exception unused2) {
                            str = "";
                        }
                    } else {
                        str = "";
                    }
                    if (!str.equals("")) {
                        return str;
                    }
                }
            } catch (Exception unused3) {
            }
        }
        return "";
    }

    private String base64Encode(String str) {
        byte[] bArr = new byte[0];
        if (Build.VERSION.SDK_INT >= 19) {
            bArr = str.getBytes(StandardCharsets.UTF_8);
        }
        return Base64.encodeToString(bArr, 0).replace("\n", "");
    }
}
