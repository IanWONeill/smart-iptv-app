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
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.model.callback.GetEpisdoeDetailsCallback;
import java.util.ArrayList;

public class SeriesRecentWatchDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "iptv_series_recent_watch.db";
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_CAT_ID = "cat_id";
    private static final String KEY_ELAPSED_TIME = "elapsed_time";
    private static final String KEY_SERIES_ID = "series_id";
    private static final String KEY_USER_ID = "user_id_referred";
    private static final String TABLE_SERIES_RECENT_WATCH = "iptv_series_recent_watch";
    String CREATE_SERIES_RECENT_WATCH = ("CREATE TABLE IF NOT EXISTS iptv_series_recent_watch(id INTEGER PRIMARY KEY,episode_id TEXT," + this.KEY_EPISODE_NAME + " TEXT," + this.KEY_ContainerExtension + " TEXT," + "added" + " TEXT," + this.KEY_EPISODE_ICON + " TEXT," + KEY_SERIES_ID + " TEXT," + KEY_USER_ID + " TEXT," + KEY_ELAPSED_TIME + " TEXT," + KEY_CAT_ID + " TEXT," + "cover" + " TEXT," + "image" + " TEXT)");
    public final String KEY_ADDED = "added";
    public String KEY_ContainerExtension = "containerExtension";
    public String KEY_EPISODE_ICON = "episode_icon";
    private final String KEY_EPISODE_ID = "episode_id";
    public String KEY_EPISODE_NAME = "episode_name";
    private final String KEY_ID = "id";
    public final String SERIES_BACK = "image";
    public final String SERIES_COVER = "cover";
    Context context;
    SQLiteDatabase db;

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public SeriesRecentWatchDatabase(Context context2) {
        super(context2, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        this.context = context2;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(this.CREATE_SERIES_RECENT_WATCH);
    }

    public void deleteAndRecreateAllTables() {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.execSQL("DROP TABLE IF EXISTS iptv_series_recent_watch");
            onCreate(writableDatabase);
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void addAllSeriesRecentWatch(GetEpisdoeDetailsCallback getEpisdoeDetailsCallback) {
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            if (getEpisdoeDetailsCallback.getId() != null) {
                contentValues.put("episode_id", getEpisdoeDetailsCallback.getId());
            } else {
                contentValues.put("episode_id", "");
            }
            if (getEpisdoeDetailsCallback.getTitle() != null) {
                contentValues.put(this.KEY_EPISODE_NAME, getEpisdoeDetailsCallback.getTitle());
            } else {
                contentValues.put(this.KEY_EPISODE_NAME, "");
            }
            if (getEpisdoeDetailsCallback.getContainerExtension() != null) {
                contentValues.put(this.KEY_ContainerExtension, getEpisdoeDetailsCallback.getContainerExtension());
            } else {
                contentValues.put(this.KEY_ContainerExtension, "");
            }
            if (getEpisdoeDetailsCallback.getAdded() != null) {
                contentValues.put("added", getEpisdoeDetailsCallback.getAdded());
            } else {
                contentValues.put("added", "");
            }
            if (getEpisdoeDetailsCallback.getSeriesCover() != null) {
                contentValues.put(this.KEY_EPISODE_ICON, getEpisdoeDetailsCallback.getSeriesCover());
            } else {
                contentValues.put(this.KEY_EPISODE_ICON, "");
            }
            contentValues.put(KEY_USER_ID, Integer.valueOf(userID));
            contentValues.put(KEY_CAT_ID, getEpisdoeDetailsCallback.getCategoryId());
            contentValues.put(KEY_SERIES_ID, getEpisdoeDetailsCallback.getSeriesId());
            if (getEpisdoeDetailsCallback.getImage() != null) {
                contentValues.put("cover", getEpisdoeDetailsCallback.getImage());
            } else {
                contentValues.put("cover", "");
            }
            if (getEpisdoeDetailsCallback.getMovieImage() != null) {
                contentValues.put("image", getEpisdoeDetailsCallback.getMovieImage());
            } else {
                contentValues.put("image", "");
            }
            Long.valueOf(writableDatabase.insert(TABLE_SERIES_RECENT_WATCH, null, contentValues));
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public int getSeriesRecentwatchmCount() {
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM iptv_series_recent_watch WHERE user_id_referred=" + userID + "", null);
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

    public void deleteSeriesRecentwatch(String str) {
        try {
            int userID = SharepreferenceDBHandler.getUserID(this.context);
            this.db = getWritableDatabase();
            SQLiteDatabase sQLiteDatabase = this.db;
            sQLiteDatabase.delete(TABLE_SERIES_RECENT_WATCH, "episode_id='" + str + "'  AND  " + KEY_USER_ID + "=" + userID + "", null);
            this.db.close();
        } catch (Exception unused) {
        }
    }

    public void deleteALLSeriesRecentwatch() {
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        this.db = getWritableDatabase();
        SQLiteDatabase sQLiteDatabase = this.db;
        sQLiteDatabase.delete(TABLE_SERIES_RECENT_WATCH, "user_id_referred=" + userID + "", null);
        this.db.close();
    }

    public ArrayList<GetEpisdoeDetailsCallback> getAllSeriesRecentWatch(String str) {
        String str2;
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        ArrayList<GetEpisdoeDetailsCallback> arrayList = new ArrayList<>();
        if (str.equals("getalldata")) {
            String seriesSubCatSort = SharepreferenceDBHandler.getSeriesSubCatSort(this.context);
            if (seriesSubCatSort.equals(AppConst.PASSWORD_UNSET)) {
                str2 = "SELECT  * FROM iptv_series_recent_watch WHERE user_id_referred=" + userID + "";
            } else if (seriesSubCatSort.equals("1")) {
                str2 = "SELECT  * FROM iptv_series_recent_watch WHERE user_id_referred=" + userID + " ORDER BY " + "added" + " DESC";
            } else if (seriesSubCatSort.equals("2")) {
                str2 = "SELECT  * FROM iptv_series_recent_watch WHERE user_id_referred=" + userID + " ORDER BY " + this.KEY_EPISODE_NAME + " ASC";
            } else if (seriesSubCatSort.equals("3")) {
                str2 = "SELECT  * FROM iptv_series_recent_watch WHERE user_id_referred=" + userID + " ORDER BY " + this.KEY_EPISODE_NAME + " DESC";
            } else {
                str2 = "SELECT  * FROM iptv_series_recent_watch WHERE user_id_referred=" + userID + "";
            }
        } else if (str.equals("getOnedata")) {
            str2 = "SELECT  * FROM iptv_series_recent_watch WHERE user_id_referred=" + userID + " ORDER BY " + "id" + " ASC LIMIT 1";
        } else {
            str2 = null;
        }
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery(str2, null);
            if (rawQuery.moveToFirst()) {
                do {
                    GetEpisdoeDetailsCallback getEpisdoeDetailsCallback = new GetEpisdoeDetailsCallback();
                    getEpisdoeDetailsCallback.setId(rawQuery.getString(1));
                    getEpisdoeDetailsCallback.setTitle(rawQuery.getString(2));
                    getEpisdoeDetailsCallback.setContainerExtension(rawQuery.getString(3));
                    getEpisdoeDetailsCallback.setAdded(rawQuery.getString(4));
                    getEpisdoeDetailsCallback.setSeriesCover(rawQuery.getString(5));
                    getEpisdoeDetailsCallback.setSeriesId(rawQuery.getString(6));
                    getEpisdoeDetailsCallback.setElapsed_time(rawQuery.getString(8));
                    getEpisdoeDetailsCallback.setCategoryId(rawQuery.getString(9));
                    getEpisdoeDetailsCallback.setImage(rawQuery.getString(10));
                    getEpisdoeDetailsCallback.setMovieImage(rawQuery.getString(11));
                    arrayList.add(getEpisdoeDetailsCallback);
                } while (rawQuery.moveToNext());
            }
            rawQuery.close();
            return arrayList;
        } catch (SQLiteDatabaseLockedException unused) {
            return null;
        } catch (SQLiteException unused2) {
            return null;
        }
    }

    public int updateSeriesRecentWatch(String str, Long l) {
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_ELAPSED_TIME, l);
            return writableDatabase.update(TABLE_SERIES_RECENT_WATCH, contentValues, "episode_id=" + str + " AND " + KEY_USER_ID + "=" + userID, null);
        } catch (Exception unused) {
            return 0;
        }
    }

    public int isStreamAvailable(String str) {
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM iptv_series_recent_watch WHERE episode_id='" + str + "' AND " + KEY_USER_ID + "='" + userID + "'", null);
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

    public Long gettimeElapsed(String str) {
        Long valueOf;
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        long j = -1L;
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  * FROM iptv_series_recent_watch WHERE user_id_referred=" + userID + " AND " + "episode_id" + "=" + str + " LIMIT 1", null);
            if (rawQuery.moveToFirst()) {
                while (true) {
                    valueOf = Long.valueOf(Long.parseLong(rawQuery.getString(8)));
                    try {
                        if (!rawQuery.moveToNext()) {
                            break;
                        }
                    } catch (SQLiteDatabaseLockedException unused) {
                        return valueOf;
                    } catch (SQLiteException unused2) {
                        return valueOf;
                    }
                }
                j = valueOf;
            }
            rawQuery.close();
            return j;
        } catch (SQLiteDatabaseLockedException unused3) {
            return j;
        } catch (SQLiteException unused4) {
            return j;
        }
    }
}
