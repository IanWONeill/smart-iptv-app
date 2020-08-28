package com.nst.yourname.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.pojo.PanelAvailableChannelsPojo;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class RecentWatchDBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "iptv_movie_streams_recent_watch.db";
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_ADDED = "added";
    private static final String KEY_AVAIL_CHANNEL_CATEGORY_NAME = "category_name";
    private static final String KEY_AVAIL_CHANNEL_CONTAINER_EXTENSION = "container_extension";
    private static final String KEY_AVAIL_CHANNEL_LIVE = "live";
    private static final String KEY_AVAIL_CHANNEL_SERIES_NO = "series_no";
    private static final String KEY_AVAIL_CHANNEL_TYPE_NAME = "type_name";
    private static final String KEY_CATEGORY_ID_LIVE_STREAMS = "categoryID";
    private static final String KEY_CUSTOMER_SID = "custom_sid";
    private static final String KEY_DIRECT_SOURCE = "direct_source";
    private static final String KEY_EPG_CHANNEL_ID = "epg_channel_id";
    private static final String KEY_ID_LIVE_STREAMS = "id";
    private static final String KEY_MOVIE_DURTION = "movie_duration";
    private static final String KEY_MOVIE_ELAPSED_TIME = "movie_elapsed_time";
    private static final String KEY_NAME = "name";
    private static final String KEY_NUM_LIVE_STREAMS = "num";
    private static final String KEY_STREAM_ICON = "stream_icon";
    private static final String KEY_STREAM_ID = "stream_id";
    private static final String KEY_STRESM_TYPE = "stream_type";
    private static final String KEY_TV_ARCHIVE = "tv_archive";
    private static final String KEY_TV_ARCHIVE_DURATION = "tv_archive_duration";
    private static final String KEY_USER_ID = "user_id_referred";
    private static final String TABLE_IPTV_AVAILABLE_CHANNNELS = "iptv_movie_streams_recent_watch";
    String CREATE_LIVE_AVAILABLE_CHANELS = "CREATE TABLE IF NOT EXISTS iptv_movie_streams_recent_watch(id INTEGER PRIMARY KEY,num TEXT,name TEXT,stream_type TEXT,stream_id TEXT,stream_icon TEXT,epg_channel_id TEXT,added TEXT,categoryID TEXT,custom_sid TEXT,tv_archive TEXT,direct_source TEXT,tv_archive_duration TEXT,type_name TEXT,category_name TEXT,series_no TEXT,live TEXT,container_extension TEXT,user_id_referred TEXT,movie_elapsed_time TEXT,movie_duration TEXT)";
    Context context;
    SQLiteDatabase db;

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public RecentWatchDBHandler(Context context2) {
        super(context2, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        this.context = context2;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(this.CREATE_LIVE_AVAILABLE_CHANELS);
    }

    public void deleteAndRecreateAllTables() {
        try {
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.execSQL("DROP TABLE IF EXISTS iptv_movie_streams_recent_watch");
            onCreate(writableDatabase);
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Integer):void}
     arg types: [java.lang.String, int]
     candidates:
      ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Byte):void}
      ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Float):void}
      ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.String):void}
      ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Long):void}
      ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Boolean):void}
      ClspMth{android.content.ContentValues.put(java.lang.String, byte[]):void}
      ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Double):void}
      ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Short):void}
      ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Integer):void} */
    public void addAllAvailableChannel(PanelAvailableChannelsPojo panelAvailableChannelsPojo) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            if (panelAvailableChannelsPojo.getNum() != null) {
                contentValues.put("num", String.valueOf(panelAvailableChannelsPojo.getNum()));
            } else {
                contentValues.put("num", "");
            }
            if (panelAvailableChannelsPojo.getName() != null) {
                contentValues.put("name", panelAvailableChannelsPojo.getName());
            } else {
                contentValues.put("name", "");
            }
            if (panelAvailableChannelsPojo.getStreamType() != null) {
                contentValues.put(KEY_STRESM_TYPE, panelAvailableChannelsPojo.getStreamType());
            } else {
                contentValues.put(KEY_STRESM_TYPE, "");
            }
            if (panelAvailableChannelsPojo.getStreamId() != null) {
                contentValues.put(KEY_STREAM_ID, panelAvailableChannelsPojo.getStreamId());
            } else {
                contentValues.put(KEY_STREAM_ID, "");
            }
            if (panelAvailableChannelsPojo.getStreamIcon() != null) {
                contentValues.put(KEY_STREAM_ICON, panelAvailableChannelsPojo.getStreamIcon());
            } else {
                contentValues.put(KEY_STREAM_ICON, "");
            }
            if (panelAvailableChannelsPojo.getEpgChannelId() != null) {
                contentValues.put(KEY_EPG_CHANNEL_ID, panelAvailableChannelsPojo.getEpgChannelId());
            } else {
                contentValues.put(KEY_EPG_CHANNEL_ID, "");
            }
            if (panelAvailableChannelsPojo.getAdded() != null) {
                contentValues.put(KEY_ADDED, panelAvailableChannelsPojo.getAdded());
            } else {
                contentValues.put(KEY_ADDED, "");
            }
            if (panelAvailableChannelsPojo.getCategoryId() != null) {
                contentValues.put(KEY_CATEGORY_ID_LIVE_STREAMS, panelAvailableChannelsPojo.getCategoryId());
            } else {
                contentValues.put(KEY_CATEGORY_ID_LIVE_STREAMS, "");
            }
            if (panelAvailableChannelsPojo.getCustomSid() != null) {
                contentValues.put(KEY_CUSTOMER_SID, panelAvailableChannelsPojo.getCustomSid());
            } else {
                contentValues.put(KEY_CUSTOMER_SID, "");
            }
            if (panelAvailableChannelsPojo.getTvArchive() != null) {
                contentValues.put(KEY_TV_ARCHIVE, panelAvailableChannelsPojo.getTvArchive());
            } else {
                contentValues.put(KEY_TV_ARCHIVE, "");
            }
            if (panelAvailableChannelsPojo.getDirectSource() != null) {
                contentValues.put(KEY_DIRECT_SOURCE, panelAvailableChannelsPojo.getDirectSource());
            } else {
                contentValues.put(KEY_DIRECT_SOURCE, "");
            }
            if (panelAvailableChannelsPojo.getTvArchiveDuration() != null) {
                contentValues.put(KEY_TV_ARCHIVE_DURATION, panelAvailableChannelsPojo.getTvArchiveDuration());
            } else {
                contentValues.put(KEY_TV_ARCHIVE_DURATION, "");
            }
            if (panelAvailableChannelsPojo.getTypeName() != null) {
                contentValues.put(KEY_AVAIL_CHANNEL_TYPE_NAME, String.valueOf(panelAvailableChannelsPojo.getTypeName()));
            } else {
                contentValues.put(KEY_AVAIL_CHANNEL_TYPE_NAME, "");
            }
            if (panelAvailableChannelsPojo.getCategoryName() != null) {
                contentValues.put("category_name", panelAvailableChannelsPojo.getCategoryName());
            } else {
                contentValues.put("category_name", "");
            }
            if (panelAvailableChannelsPojo.getSeriesNo() != null) {
                contentValues.put(KEY_AVAIL_CHANNEL_SERIES_NO, String.valueOf(panelAvailableChannelsPojo.getSeriesNo()));
            } else {
                contentValues.put(KEY_AVAIL_CHANNEL_SERIES_NO, "");
            }
            if (panelAvailableChannelsPojo.getLive() != null) {
                contentValues.put(KEY_AVAIL_CHANNEL_SERIES_NO, String.valueOf(panelAvailableChannelsPojo.getSeriesNo()));
            } else {
                contentValues.put(KEY_AVAIL_CHANNEL_SERIES_NO, "");
            }
            if (panelAvailableChannelsPojo.getLive() != null) {
                contentValues.put(KEY_AVAIL_CHANNEL_LIVE, panelAvailableChannelsPojo.getLive());
            } else {
                contentValues.put(KEY_AVAIL_CHANNEL_LIVE, "");
            }
            if (panelAvailableChannelsPojo.getContainerExtension() != null) {
                contentValues.put(KEY_AVAIL_CHANNEL_CONTAINER_EXTENSION, String.valueOf(panelAvailableChannelsPojo.getContainerExtension()));
            } else {
                contentValues.put(KEY_AVAIL_CHANNEL_CONTAINER_EXTENSION, "");
            }
            if (panelAvailableChannelsPojo.getUserIdReferred() != -1) {
                contentValues.put(KEY_USER_ID, Integer.valueOf(panelAvailableChannelsPojo.getUserIdReferred()));
            } else {
                contentValues.put(KEY_USER_ID,/* (Integer)*/ -1);
            }
            if (panelAvailableChannelsPojo.getMovieElapsedTime() != -1) {
                contentValues.put(KEY_MOVIE_ELAPSED_TIME, Long.valueOf(panelAvailableChannelsPojo.getMovieElapsedTime()));
            } else {
                contentValues.put(KEY_MOVIE_ELAPSED_TIME, /*(Integer)*/ -1);
            }
            if (panelAvailableChannelsPojo.getMovieDuration() != -1) {
                contentValues.put(KEY_MOVIE_DURTION, Long.valueOf(panelAvailableChannelsPojo.getMovieDuration()));
            } else {
                //ToDo: Integer...
                contentValues.put(KEY_MOVIE_DURTION, /*(Integer) */-1);
            }
            writableDatabase.insert(TABLE_IPTV_AVAILABLE_CHANNNELS, null, contentValues);
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public int getRecentwatchmoviesCount(int i) {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM iptv_movie_streams_recent_watch WHERE user_id_referred=" + i + "", null);
            rawQuery.moveToFirst();
            int i2 = rawQuery.getInt(0);
            rawQuery.close();
            return i2;
        } catch (SQLiteDatabaseLockedException unused) {
            return 0;
        } catch (SQLiteException unused2) {
            return 0;
        }
    }

    public int getLiveStreamsCount(int i) {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM iptv_movie_streams_recent_watch WHERE user_id_referred='" + i + "'", null);
            rawQuery.moveToFirst();
            int i2 = rawQuery.getInt(0);
            rawQuery.close();
            return i2;
        } catch (SQLiteDatabaseLockedException unused) {
            return 0;
        } catch (SQLiteException unused2) {
            return 0;
        }
    }

    public ArrayList<LiveStreamsDBModel> getAllLiveStreasWithCategoryId(String str, int i, String str2) {
        String str3;
        ArrayList<LiveStreamsDBModel> arrayList = new ArrayList<>();
        if (str2.equals("getalldata")) {
            String vODSubCatSort = SharepreferenceDBHandler.getVODSubCatSort(this.context);
            if (vODSubCatSort.equals(AppConst.PASSWORD_UNSET)) {
                str3 = "SELECT  * FROM iptv_movie_streams_recent_watch WHERE user_id_referred=" + i + "";
            } else if (vODSubCatSort.equals("1")) {
                str3 = "SELECT  * FROM iptv_movie_streams_recent_watch WHERE user_id_referred=" + i + " ORDER BY " + "id" + " DESC";
            } else if (vODSubCatSort.equals("2")) {
                str3 = "SELECT  * FROM iptv_movie_streams_recent_watch WHERE user_id_referred=" + i + " ORDER BY " + "name" + " ASC";
            } else if (vODSubCatSort.equals("3")) {
                str3 = "SELECT  * FROM iptv_movie_streams_recent_watch WHERE user_id_referred=" + i + " ORDER BY " + "name" + " DESC";
            } else {
                str3 = "SELECT  * FROM iptv_movie_streams_recent_watch WHERE user_id_referred=" + i + "";
            }
        } else if (str2.equals("getOnedata")) {
            str3 = "SELECT  * FROM iptv_movie_streams_recent_watch WHERE user_id_referred=" + i + " ORDER BY " + KEY_ADDED + " ASC LIMIT 1";
        } else {
            str3 = null;
        }
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery(str3, null);
            if (rawQuery.moveToFirst()) {
                do {
                    LiveStreamsDBModel liveStreamsDBModel = new LiveStreamsDBModel();
                    try {
                        liveStreamsDBModel.setIdAuto(Integer.parseInt(rawQuery.getString(0)));
                    } catch (NumberFormatException unused) {
                    }
                    liveStreamsDBModel.setNum(rawQuery.getString(1));
                    liveStreamsDBModel.setName(rawQuery.getString(2));
                    liveStreamsDBModel.setStreamType(rawQuery.getString(3));
                    liveStreamsDBModel.setStreamId(rawQuery.getString(4));
                    liveStreamsDBModel.setStreamIcon(rawQuery.getString(5));
                    liveStreamsDBModel.setEpgChannelId(rawQuery.getString(6));
                    liveStreamsDBModel.setAdded(rawQuery.getString(7));
                    liveStreamsDBModel.setCategoryId(rawQuery.getString(8));
                    liveStreamsDBModel.setCustomSid(rawQuery.getString(9));
                    liveStreamsDBModel.setTvArchive(rawQuery.getString(10));
                    liveStreamsDBModel.setDirectSource(rawQuery.getString(11));
                    liveStreamsDBModel.setTvArchiveDuration(rawQuery.getString(12));
                    liveStreamsDBModel.setTypeName(rawQuery.getString(13));
                    liveStreamsDBModel.setCategoryName(rawQuery.getString(14));
                    liveStreamsDBModel.setSeriesNo(rawQuery.getString(15));
                    liveStreamsDBModel.setLive(rawQuery.getString(16));
                    liveStreamsDBModel.setContaiinerExtension(rawQuery.getString(17));
                    liveStreamsDBModel.setUserIdReffered(rawQuery.getInt(18));
                    liveStreamsDBModel.setMovieElapsedTime(rawQuery.getLong(19));
                    liveStreamsDBModel.setMovieDuraton(rawQuery.getLong(20));
                    arrayList.add(liveStreamsDBModel);
                } while (rawQuery.moveToNext());
            }
            rawQuery.close();
            return arrayList;
        } catch (SQLiteDatabaseLockedException unused2) {
            return null;
        } catch (SQLiteException unused3) {
            return null;
        }
    }

    public void deleteRecentwatch(int i, String str) {
        try {
            int userID = SharepreferenceDBHandler.getUserID(this.context);
            this.db = getWritableDatabase();
            SQLiteDatabase sQLiteDatabase = this.db;
            sQLiteDatabase.delete(TABLE_IPTV_AVAILABLE_CHANNNELS, "stream_id='" + i + "'  AND " + KEY_STRESM_TYPE + "='" + str + "' AND " + KEY_USER_ID + "=" + userID + "", null);
            this.db.close();
        } catch (Exception unused) {
        }
    }

    public void deleteALLRecentwatch(String str, int i) {
        this.db = getWritableDatabase();
        SQLiteDatabase sQLiteDatabase = this.db;
        sQLiteDatabase.delete(TABLE_IPTV_AVAILABLE_CHANNNELS, "stream_type='" + str + "' AND " + KEY_USER_ID + "=" + i + "", null);
        this.db.close();
    }

    public int getUncatCount(String str, String str2) {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM iptv_movie_streams_recent_watch WHERE ( stream_type LIKE '%" + str2 + "%' OR " + KEY_STRESM_TYPE + " LIKE '%radio%' )  AND " + KEY_CATEGORY_ID_LIVE_STREAMS + "='" + str + "'", null);
            rawQuery.moveToFirst();
            int i = rawQuery.getInt(0);
            rawQuery.close();
            return i;
        } catch (SQLiteDatabaseLockedException unused) {
            return 0;
        } catch (SQLiteException unused2) {
            return 0;
        }
    }

    public boolean updateResumePlayerStatus(String str, String str2, boolean z, long j) {
        try {
            String str3 = "SELECT rowid FROM iptv_movie_streams_recent_watch WHERE stream_type = '" + str2 + "' AND " + KEY_STREAM_ID + " = '" + str + "'";
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            String str4 = "";
            Cursor rawQuery = writableDatabase.rawQuery(str3, null);
            if (rawQuery != null) {
                if (rawQuery.moveToFirst()) {
                    do {
                        try {
                            str4 = String.valueOf(Integer.parseInt(rawQuery.getString(rawQuery.getColumnIndex("id"))));
                        } catch (NumberFormatException unused) {
                            str4 = "";
                        }
                    } while (rawQuery.moveToNext());
                }
            } else if (this.context != null) {
                Toast.makeText(this.context, "cursor is null", 1).show();
            }
            if (!str4.equals("")) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(KEY_MOVIE_ELAPSED_TIME, Long.valueOf(j));
                writableDatabase.update(TABLE_IPTV_AVAILABLE_CHANNNELS, contentValues, "id= ?", new String[]{str4});
                if (rawQuery != null) {
                    rawQuery.close();
                }
                return true;
            }
            if (rawQuery != null) {
                rawQuery.close();
            }
            return false;
        } catch (SQLiteDatabaseLockedException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
            return false;
        } catch (SQLiteException unused3) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
            return false;
        }
    }

    public boolean updateResumePlayerStatuTimes(String str, String str2, boolean z, long j, long j2, int i) {
        try {
            String str3 = "SELECT rowid FROM iptv_movie_streams_recent_watch WHERE stream_type = '" + str2 + "' AND " + KEY_USER_ID + " = '" + i + "' AND " + KEY_STREAM_ID + " = '" + str + "'";
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            String str4 = "";
            Cursor rawQuery = writableDatabase.rawQuery(str3, null);
            if (rawQuery != null) {
                if (rawQuery.moveToFirst()) {
                    do {
                        try {
                            str4 = String.valueOf(Integer.parseInt(rawQuery.getString(rawQuery.getColumnIndex("id"))));
                        } catch (NumberFormatException unused) {
                            str4 = "";
                        }
                    } while (rawQuery.moveToNext());
                }
            } else if (this.context != null) {
                Toast.makeText(this.context, "cursor is null", 1).show();
            }
            if (!str4.equals("")) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(KEY_MOVIE_ELAPSED_TIME, Long.valueOf(j2));
                contentValues.put(KEY_MOVIE_DURTION, Long.valueOf(j));
                writableDatabase.update(TABLE_IPTV_AVAILABLE_CHANNNELS, contentValues, "id= ?", new String[]{str4});
                if (rawQuery != null) {
                    rawQuery.close();
                }
                return true;
            }
            if (rawQuery != null) {
                rawQuery.close();
            }
            return false;
        } catch (SQLiteDatabaseLockedException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
            return false;
        } catch (SQLiteException unused3) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
            return false;
        }
    }

    public int isStreamAvailable(String str, int i) {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM iptv_movie_streams_recent_watch WHERE stream_id='" + str + "' AND " + KEY_USER_ID + "='" + i + "'", null);
            rawQuery.moveToFirst();
            int i2 = rawQuery.getInt(0);
            rawQuery.close();
            return i2;
        } catch (SQLiteDatabaseLockedException unused) {
            return 0;
        } catch (SQLiteException unused2) {
            return 0;
        }
    }

    public LiveStreamsDBModel getStreamStatus(String str, int i) {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT * FROM iptv_movie_streams_recent_watch WHERE stream_id = '" + str + "' AND " + KEY_USER_ID + " = '" + i + "'", null);
            LiveStreamsDBModel liveStreamsDBModel = new LiveStreamsDBModel();
            if (rawQuery.moveToFirst()) {
                do {
                    try {
                        liveStreamsDBModel.setIdAuto(Integer.parseInt(rawQuery.getString(0)));
                    } catch (NumberFormatException unused) {
                    }
                    liveStreamsDBModel.setNum(rawQuery.getString(1));
                    liveStreamsDBModel.setName(rawQuery.getString(2));
                    liveStreamsDBModel.setStreamType(rawQuery.getString(3));
                    liveStreamsDBModel.setStreamId(rawQuery.getString(4));
                    liveStreamsDBModel.setStreamIcon(rawQuery.getString(5));
                    liveStreamsDBModel.setEpgChannelId(rawQuery.getString(6));
                    liveStreamsDBModel.setAdded(rawQuery.getString(7));
                    liveStreamsDBModel.setCategoryId(rawQuery.getString(8));
                    liveStreamsDBModel.setCustomSid(rawQuery.getString(9));
                    liveStreamsDBModel.setTvArchive(rawQuery.getString(10));
                    liveStreamsDBModel.setDirectSource(rawQuery.getString(11));
                    liveStreamsDBModel.setTvArchiveDuration(rawQuery.getString(12));
                    liveStreamsDBModel.setTypeName(rawQuery.getString(13));
                    liveStreamsDBModel.setCategoryName(rawQuery.getString(14));
                    liveStreamsDBModel.setSeriesNo(rawQuery.getString(15));
                    liveStreamsDBModel.setLive(rawQuery.getString(16));
                    liveStreamsDBModel.setContaiinerExtension(rawQuery.getString(17));
                    liveStreamsDBModel.setUserIdReffered(rawQuery.getInt(18));
                    liveStreamsDBModel.setMovieElapsedTime(rawQuery.getLong(19));
                    liveStreamsDBModel.setMovieDuraton(rawQuery.getLong(20));
                } while (rawQuery.moveToNext());
            }
            rawQuery.close();
            return liveStreamsDBModel;
        } catch (SQLiteDatabaseLockedException unused2) {
            return null;
        } catch (SQLiteException unused3) {
            return null;
        }
    }

    public void deletRecentWatchForThisUser(int i) {
        try {
            this.db = getWritableDatabase();
            SQLiteDatabase sQLiteDatabase = this.db;
            sQLiteDatabase.delete(TABLE_IPTV_AVAILABLE_CHANNNELS, "user_id_referred='" + i + "'", null);
            this.db.close();
        } catch (SQLiteException unused) {
        }
    }
}
