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
import com.nst.yourname.model.LiveStreamCategoryIdDBModel;
import com.nst.yourname.model.callback.GetSeriesStreamCallback;
import com.nst.yourname.model.callback.GetSeriesStreamCategoriesCallback;
import com.nst.yourname.model.callback.SeriesDBModel;
import java.util.ArrayList;
import java.util.Map;

public class SeriesStreamsDatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "series_streams_v2_tv.db";
    private static final int DATABASE_VERSION = 3;
    private static final String KEY_BACKDROPE = "backdrop";
    private static final String KEY_CAST = "cast_series_stream_v2";
    private static final String KEY_CATEGORY_ID = "category_id_series_stream_v2";
    private static final String KEY_CATEGORY_ID_SERIES = "category_id_series_v2";
    private static final String KEY_CATEGORY_NAME_SERIES = "category_name_series_v2";
    private static final String KEY_COVER = "stream_cover_series_stream_v2";
    private static final String KEY_DB_SERIES_STREAMS_CATEGORY = "series_streams_status_category";
    private static final String KEY_DB_SERIES_STREAMS_CATEGORY_ID = "series_streams_status_category_id";
    private static final String KEY_DB_SERIES_STREAMS_CAT_CATEGORY = "series_streams_cat_status_category";
    private static final String KEY_DB_SERIES_STREAMS_CAT_CATEGORY_ID = "series_streams_cat_status_category_id";
    private static final String KEY_DB_SERIES_STREAMS_CAT_LAST_UPDATED_DATE = "series_streams_cat_last_updated_date";
    private static final String KEY_DB_SERIES_STREAMS_CAT_STATUS_STATE = "series_streams_cat_status_state";
    private static final String KEY_DB_SERIES_STREAMS_LAST_UPDATED_DATE = "series_streams_last_updated_date";
    private static final String KEY_DB_SERIES_STREAMS_STATUS_STATE = "series_streams_status_state";
    private static final String KEY_DIRECTOR = "director_series_stream_v2";
    private static final String KEY_GENERE = "genre_series_stream_v2";
    private static final String KEY_ID_SERIES_STREAMS = "id_series_stream_v2";
    private static final String KEY_ID_SERIES_STREAMS_CAT_STATUS = "series_streams_cat_status_id";
    private static final String KEY_ID_SERIES_STREAMS_STATUS = "series_streams_status_id";
    private static final String KEY_ID_VOD = "id_series_v2";
    private static final String KEY_LAST_MODIFIED = "last_modified_series_stream_v2";
    private static final String KEY_NAME = "name_series_stream_v2";
    private static final String KEY_NUM_SERIES_STREAMS = "num_series_stream_v2";
    private static final String KEY_PLOT = "plot_series_stream_v2";
    private static final String KEY_RATING = "rating_series_stream_v2";
    private static final String KEY_RELEASE_DATE = "release_date_series_stream_v2";
    private static final String KEY_SERIES_ID = "stream_id_series_stream_v2";
    private static final String KEY_STREAM_TYPE = "stream_type_series_stream_v2";
    private static final String KEY_YOUTUBE_TRAILER = "youtube_trailer";
    private static final String TABLE_IPTV_SERIES_CATEGORY = "series_category_v2";
    private static final String TABLE_IPTV_SERIES_STREAMS = "series_streams_v2";
    private static final String TABLE_SERIES_STREAM_CAT_STATUS = "series_streams_cat_status";
    private static final String TABLE_SERIES_STREAM_STATUS = "series_streams_status";
    private String ALTER_CREATE_SERIES_STREAMS_1 = "ALTER TABLE series_streams_v2 ADD COLUMN youtube_trailer TEXT;";
    private String ALTER_CREATE_SERIES_STREAMS_2 = "ALTER TABLE series_streams_v2 ADD COLUMN backdrop TEXT;";
    String CREATE_SERIES_CATEGORY_TABLE = "CREATE TABLE IF NOT EXISTS series_category_v2(id_series_v2 INTEGER PRIMARY KEY,category_name_series_v2 TEXT,category_id_series_v2 TEXT)";
    String CREATE_SERIES_STREAMS = "CREATE TABLE IF NOT EXISTS series_streams_v2(id_series_stream_v2 INTEGER PRIMARY KEY,num_series_stream_v2 TEXT,name_series_stream_v2 TEXT,stream_type_series_stream_v2 TEXT,stream_id_series_stream_v2 TEXT,stream_cover_series_stream_v2 TEXT,plot_series_stream_v2 TEXT,cast_series_stream_v2 TEXT,director_series_stream_v2 TEXT,genre_series_stream_v2 TEXT,release_date_series_stream_v2 TEXT,last_modified_series_stream_v2 TEXT,rating_series_stream_v2 TEXT,category_id_series_stream_v2 TEXT,youtube_trailer TEXT,backdrop TEXT)";
    String CREATE_SERIES_STREAM_CAT_STATUS_TABLE = "CREATE TABLE IF NOT EXISTS series_streams_cat_status(series_streams_cat_status_id INTEGER PRIMARY KEY,series_streams_cat_status_state TEXT,series_streams_cat_last_updated_date TEXT,series_streams_cat_status_category TEXT,series_streams_cat_status_category_id TEXT)";
    String CREATE_SERIES_STREAM_STATUS_TABLE = "CREATE TABLE IF NOT EXISTS series_streams_status(series_streams_status_id INTEGER PRIMARY KEY,series_streams_status_state TEXT,series_streams_last_updated_date TEXT,series_streams_status_category TEXT,series_streams_status_category_id TEXT)";
    Context context;
    SQLiteDatabase db;

    public SeriesStreamsDatabaseHandler(Context context2) {
        super(context2, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 3);
        this.context = context2;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(this.CREATE_SERIES_CATEGORY_TABLE);
        sQLiteDatabase.execSQL(this.CREATE_SERIES_STREAMS);
        sQLiteDatabase.execSQL(this.CREATE_SERIES_STREAM_CAT_STATUS_TABLE);
        sQLiteDatabase.execSQL(this.CREATE_SERIES_STREAM_STATUS_TABLE);
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i2 >= 3) {
            sQLiteDatabase.execSQL(this.ALTER_CREATE_SERIES_STREAMS_1);
            sQLiteDatabase.execSQL(this.ALTER_CREATE_SERIES_STREAMS_2);
        }
    }

    public void addSeriesCategories(Map<String, GetSeriesStreamCategoriesCallback> map) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            for (GetSeriesStreamCategoriesCallback getSeriesStreamCategoriesCallback : map.values()) {
                if (getSeriesStreamCategoriesCallback.getCategoryId() != null) {
                    contentValues.put(KEY_CATEGORY_ID_SERIES, getSeriesStreamCategoriesCallback.getCategoryId());
                } else {
                    contentValues.put(KEY_CATEGORY_ID_SERIES, "");
                }
                if (getSeriesStreamCategoriesCallback.getCategoryName() != null) {
                    contentValues.put(KEY_CATEGORY_NAME_SERIES, getSeriesStreamCategoriesCallback.getCategoryName());
                } else {
                    contentValues.put(KEY_CATEGORY_NAME_SERIES, "");
                }
                writableDatabase.insert(TABLE_IPTV_SERIES_CATEGORY, null, contentValues);
            }
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void addSeriesCategories(ArrayList<GetSeriesStreamCategoriesCallback> arrayList) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            int size = arrayList.size();
            if (size != 0) {
                for (int i = 0; i < size; i++) {
                    contentValues.put(KEY_CATEGORY_ID_SERIES, arrayList.get(i).getCategoryId());
                    contentValues.put(KEY_CATEGORY_NAME_SERIES, arrayList.get(i).getCategoryName());
                    writableDatabase.insert(TABLE_IPTV_SERIES_CATEGORY, null, contentValues);
                }
            }
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public ArrayList<LiveStreamCategoryIdDBModel> getAllSeriesCategories() {
        ArrayList<LiveStreamCategoryIdDBModel> arrayList = new ArrayList<>();
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT * FROM series_category_v2 WHERE category_id_series_v2 !=-5", null);
            if (rawQuery.moveToFirst()) {
                do {
                    LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
                    liveStreamCategoryIdDBModel.setId(Integer.parseInt(rawQuery.getString(0)));
                    liveStreamCategoryIdDBModel.setLiveStreamCategoryName(rawQuery.getString(1));
                    liveStreamCategoryIdDBModel.setLiveStreamCategoryID(rawQuery.getString(2));
                    arrayList.add(liveStreamCategoryIdDBModel);
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

    private int checkUnCategoryCountSeries(String str) {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  * FROM series_category_v2 WHERE category_id_series_v2 ='" + str + "'", null);
            int count = rawQuery.getCount();
            rawQuery.close();
            return count;
        } catch (SQLiteDatabaseLockedException unused) {
            return 0;
        } catch (SQLiteException unused2) {
            return 0;
        }
    }

    public void addAllSeriesStreams(ArrayList<GetSeriesStreamCallback> arrayList) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            int size = arrayList.size();
            if (size != 0) {
                for (int i = 0; i < size; i++) {
                    if (arrayList.get(i).getNum() != null) {
                        contentValues.put(KEY_NUM_SERIES_STREAMS, String.valueOf(arrayList.get(i).getNum()));
                    } else {
                        contentValues.put(KEY_NUM_SERIES_STREAMS, "");
                    }
                    if (arrayList.get(i).getName() != null) {
                        contentValues.put(KEY_NAME, arrayList.get(i).getName());
                    } else {
                        contentValues.put(KEY_NAME, "");
                    }
                    if (arrayList.get(i).getStreamType() != null) {
                        contentValues.put(KEY_STREAM_TYPE, String.valueOf(arrayList.get(i).getStreamType()));
                    } else {
                        contentValues.put(KEY_STREAM_TYPE, "");
                    }
                    if (arrayList.get(i).getSeriesId() != null) {
                        contentValues.put(KEY_SERIES_ID, arrayList.get(i).getSeriesId());
                    } else {
                        contentValues.put(KEY_SERIES_ID, "");
                    }
                    if (arrayList.get(i).getCover() != null) {
                        contentValues.put(KEY_COVER, arrayList.get(i).getCover());
                    } else {
                        contentValues.put(KEY_COVER, "");
                    }
                    if (arrayList.get(i).getPlot() != null) {
                        contentValues.put(KEY_PLOT, arrayList.get(i).getPlot());
                    } else {
                        contentValues.put(KEY_PLOT, "");
                    }
                    if (arrayList.get(i).getCast() != null) {
                        contentValues.put(KEY_CAST, arrayList.get(i).getCast());
                    } else {
                        contentValues.put(KEY_CAST, "");
                    }
                    if (arrayList.get(i).getDirector() != null) {
                        contentValues.put(KEY_DIRECTOR, String.valueOf(arrayList.get(i).getDirector()));
                    } else {
                        contentValues.put(KEY_DIRECTOR, "");
                    }
                    if (arrayList.get(i).getGenre() != null) {
                        contentValues.put(KEY_GENERE, arrayList.get(i).getGenre());
                    } else {
                        contentValues.put(KEY_GENERE, "");
                    }
                    if (arrayList.get(i).getReleaseDate() != null) {
                        contentValues.put(KEY_RELEASE_DATE, String.valueOf(arrayList.get(i).getReleaseDate()));
                    } else {
                        contentValues.put(KEY_RELEASE_DATE, "");
                    }
                    if (arrayList.get(i).getLastModified() != null) {
                        contentValues.put(KEY_LAST_MODIFIED, String.valueOf(arrayList.get(i).getLastModified()));
                    } else {
                        contentValues.put(KEY_LAST_MODIFIED, "");
                    }
                    if (arrayList.get(i).getRating() != null) {
                        contentValues.put(KEY_RATING, String.valueOf(arrayList.get(i).getRating()));
                    } else {
                        contentValues.put(KEY_RATING, "");
                    }
                    if (arrayList.get(i).getCategoryId() == null) {
                        contentValues.put(KEY_CATEGORY_ID, "-5");
                    } else if (checkUnCategoryCountSeries(arrayList.get(i).getCategoryId()) > 0) {
                        contentValues.put(KEY_CATEGORY_ID, String.valueOf(arrayList.get(i).getCategoryId()));
                    } else {
                        contentValues.put(KEY_CATEGORY_ID, "-5");
                    }
                    if (arrayList.get(i).getYoutubTrailer() != null) {
                        contentValues.put(KEY_YOUTUBE_TRAILER, String.valueOf(arrayList.get(i).getYoutubTrailer()));
                    } else {
                        contentValues.put(KEY_YOUTUBE_TRAILER, "");
                    }
                    if (arrayList.get(i).getBackdropPath() != null) {
                        contentValues.put(KEY_BACKDROPE, arrayList.get(i).getBackdropPath().toString());
                    } else {
                        contentValues.put(KEY_BACKDROPE, "");
                    }
                    writableDatabase.insert(TABLE_IPTV_SERIES_STREAMS, null, contentValues);
                }
            }
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public ArrayList<SeriesDBModel> getAllSeriesStreamsWithCategoryId(String str) {
        String str2;
        String str3;
        String str4 = str;
        String seriesSubCatSort = SharepreferenceDBHandler.getSeriesSubCatSort(this.context);
        int i = 10;
        int i2 = 1;
        int i3 = 0;
        if (str4.equals("") || str4.equals(AppConst.PASSWORD_UNSET)) {
            if (seriesSubCatSort.equalsIgnoreCase("1")) {
                str2 = "SELECT  * FROM series_streams_v2 ORDER BY last_modified_series_stream_v2 DESC";
            } else if (seriesSubCatSort.equals(AppConst.PASSWORD_UNSET)) {
                str2 = "SELECT  * FROM series_streams_v2";
            } else {
                str2 = seriesSubCatSort.equals("3") ? "SELECT  * FROM series_streams_v2 ORDER BY name_series_stream_v2 DESC" : "SELECT  * FROM series_streams_v2 ORDER BY name_series_stream_v2 ASC";
            }
            ArrayList<SeriesDBModel> arrayList = new ArrayList<>();
            try {
                Cursor rawQuery = getReadableDatabase().rawQuery(str2, null);
                if (rawQuery.moveToFirst()) {
                    while (true) {
                        SeriesDBModel seriesDBModel = new SeriesDBModel();
                        seriesDBModel.setIdAuto(Integer.parseInt(rawQuery.getString(0)));
                        seriesDBModel.setNum(rawQuery.getString(i2));
                        seriesDBModel.setName(rawQuery.getString(2));
                        seriesDBModel.setStreamType(rawQuery.getString(3));
                        seriesDBModel.setseriesID(Integer.parseInt(rawQuery.getString(4)));
                        seriesDBModel.setcover(rawQuery.getString(5));
                        seriesDBModel.setplot(rawQuery.getString(6));
                        seriesDBModel.setcast(rawQuery.getString(7));
                        seriesDBModel.setdirector(rawQuery.getString(8));
                        seriesDBModel.setgenre(rawQuery.getString(9));
                        seriesDBModel.setreleaseDate(rawQuery.getString(10));
                        seriesDBModel.setlast_modified(rawQuery.getString(11));
                        seriesDBModel.setrating(rawQuery.getString(12));
                        seriesDBModel.setCategoryId(rawQuery.getString(13));
                        seriesDBModel.setYouTubeTrailer(rawQuery.getString(14));
                        seriesDBModel.setBackdrop(rawQuery.getString(15));
                        arrayList.add(seriesDBModel);
                        if (!rawQuery.moveToNext()) {
                            break;
                        }
                        i2 = 1;
                    }
                }
                rawQuery.close();
                return arrayList;
            } catch (SQLiteDatabaseLockedException unused) {
                return null;
            } catch (SQLiteException unused2) {
                return null;
            }
        } else {
            if (seriesSubCatSort.equals(AppConst.PASSWORD_UNSET)) {
                str3 = "SELECT * FROM series_streams_v2 WHERE category_id_series_stream_v2 ='" + str4 + "'";
            } else if (seriesSubCatSort.equals("3")) {
                str3 = "SELECT * FROM series_streams_v2 WHERE category_id_series_stream_v2 ='" + str4 + "' ORDER BY " + KEY_NAME + " DESC";
            } else {
                str3 = "SELECT * FROM series_streams_v2 WHERE category_id_series_stream_v2 ='" + str4 + "' ORDER BY " + KEY_NAME + " ASC";
            }
            ArrayList<SeriesDBModel> arrayList2 = new ArrayList<>();
            try {
                Cursor rawQuery2 = getReadableDatabase().rawQuery(str3, null);
                if (rawQuery2.moveToFirst()) {
                    while (true) {
                        SeriesDBModel seriesDBModel2 = new SeriesDBModel();
                        seriesDBModel2.setIdAuto(Integer.parseInt(rawQuery2.getString(i3)));
                        seriesDBModel2.setNum(rawQuery2.getString(1));
                        seriesDBModel2.setName(rawQuery2.getString(2));
                        seriesDBModel2.setStreamType(rawQuery2.getString(3));
                        seriesDBModel2.setseriesID(Integer.parseInt(rawQuery2.getString(4)));
                        seriesDBModel2.setcover(rawQuery2.getString(5));
                        seriesDBModel2.setplot(rawQuery2.getString(6));
                        seriesDBModel2.setcast(rawQuery2.getString(7));
                        seriesDBModel2.setdirector(rawQuery2.getString(8));
                        seriesDBModel2.setgenre(rawQuery2.getString(9));
                        seriesDBModel2.setreleaseDate(rawQuery2.getString(i));
                        seriesDBModel2.setlast_modified(rawQuery2.getString(11));
                        seriesDBModel2.setrating(rawQuery2.getString(12));
                        seriesDBModel2.setCategoryId(rawQuery2.getString(13));
                        seriesDBModel2.setYouTubeTrailer(rawQuery2.getString(14));
                        seriesDBModel2.setBackdrop(rawQuery2.getString(15));
                        arrayList2.add(seriesDBModel2);
                        if (!rawQuery2.moveToNext()) {
                            break;
                        }
                        i3 = 0;
                        i = 10;
                    }
                }
                rawQuery2.close();
                return arrayList2;
            } catch (SQLiteDatabaseLockedException unused3) {
                return null;
            } catch (SQLiteException unused4) {
                return null;
            }
        }
    }

    public SeriesDBModel getSeriesStreamsWithSeriesId(String str) {
        String str2 = "SELECT  * FROM series_streams_v2 WHERE stream_id_series_stream_v2 ='" + str + "'";
        new ArrayList();
        SeriesDBModel seriesDBModel = new SeriesDBModel();
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery(str2, null);
            if (rawQuery.moveToFirst()) {
                do {
                    seriesDBModel.setIdAuto(Integer.parseInt(rawQuery.getString(0)));
                    seriesDBModel.setNum(rawQuery.getString(1));
                    seriesDBModel.setName(rawQuery.getString(2));
                    seriesDBModel.setStreamType(rawQuery.getString(3));
                    seriesDBModel.setseriesID(Integer.parseInt(rawQuery.getString(4)));
                    seriesDBModel.setcover(rawQuery.getString(5));
                    seriesDBModel.setplot(rawQuery.getString(6));
                    seriesDBModel.setcast(rawQuery.getString(7));
                    seriesDBModel.setdirector(rawQuery.getString(8));
                    seriesDBModel.setgenre(rawQuery.getString(9));
                    seriesDBModel.setreleaseDate(rawQuery.getString(10));
                    seriesDBModel.setlast_modified(rawQuery.getString(11));
                    seriesDBModel.setrating(rawQuery.getString(12));
                    seriesDBModel.setCategoryId(rawQuery.getString(13));
                    seriesDBModel.setYouTubeTrailer(rawQuery.getString(14));
                    seriesDBModel.setBackdrop(rawQuery.getString(15));
                } while (rawQuery.moveToNext());
            }
            rawQuery.close();
            return seriesDBModel;
        } catch (SQLiteDatabaseLockedException unused) {
            return null;
        } catch (SQLiteException unused2) {
            return null;
        }
    }

    public int getAllSeriesStreamCount() {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM series_streams_v2", null);
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

    public int getSeriesCount(String str) {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM series_streams_v2 WHERE category_id_series_stream_v2='" + str + "'", null);
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

    public void addSeriesStreamsCatStatus(DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModel) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_DB_SERIES_STREAMS_CAT_STATUS_STATE, databaseUpdatedStatusDBModel.getDbUpadatedStatusState());
            contentValues.put(KEY_DB_SERIES_STREAMS_CAT_LAST_UPDATED_DATE, databaseUpdatedStatusDBModel.getDbLastUpdatedDate());
            contentValues.put(KEY_DB_SERIES_STREAMS_CAT_CATEGORY, databaseUpdatedStatusDBModel.getDbCategory());
            contentValues.put(KEY_DB_SERIES_STREAMS_CAT_CATEGORY_ID, databaseUpdatedStatusDBModel.getGetDbCategoryID());
            writableDatabase.insert(TABLE_SERIES_STREAM_CAT_STATUS, null, contentValues);
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void addSeriesStreamsStatus(DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModel) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_DB_SERIES_STREAMS_STATUS_STATE, databaseUpdatedStatusDBModel.getDbUpadatedStatusState());
            contentValues.put(KEY_DB_SERIES_STREAMS_LAST_UPDATED_DATE, databaseUpdatedStatusDBModel.getDbLastUpdatedDate());
            contentValues.put(KEY_DB_SERIES_STREAMS_CATEGORY, databaseUpdatedStatusDBModel.getDbCategory());
            contentValues.put(KEY_DB_SERIES_STREAMS_CATEGORY_ID, databaseUpdatedStatusDBModel.getGetDbCategoryID());
            writableDatabase.insert(TABLE_SERIES_STREAM_STATUS, null, contentValues);
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public int getSeriesStreamsCatDBStatusCount() {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  * FROM series_streams_cat_status", null);
            int count = rawQuery.getCount();
            rawQuery.close();
            return count;
        } catch (SQLiteDatabaseLockedException unused) {
            return 0;
        } catch (SQLiteException unused2) {
            return 0;
        }
    }

    public int getSeriesStreamsDBStatusCount() {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  * FROM series_streams_status", null);
            int count = rawQuery.getCount();
            rawQuery.close();
            return count;
        } catch (SQLiteDatabaseLockedException unused) {
            return 0;
        } catch (SQLiteException unused2) {
            return 0;
        }
    }

    public DatabaseUpdatedStatusDBModel getdateSeriesStreamsCatDBStatus(String str, String str2) {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT * FROM series_streams_cat_status WHERE series_streams_cat_status_category = '" + str + "' AND " + KEY_DB_SERIES_STREAMS_CAT_CATEGORY_ID + " = '" + str2 + "'", null);
            DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModel = new DatabaseUpdatedStatusDBModel();
            if (rawQuery.moveToFirst()) {
                do {
                    databaseUpdatedStatusDBModel.setIdAuto(Integer.parseInt(rawQuery.getString(0)));
                    databaseUpdatedStatusDBModel.setDbUpadatedStatusState(rawQuery.getString(1));
                    databaseUpdatedStatusDBModel.setDbLastUpdatedDate(rawQuery.getString(2));
                    databaseUpdatedStatusDBModel.setDbCategory(rawQuery.getString(3));
                    databaseUpdatedStatusDBModel.setDbCategoryID(rawQuery.getString(4));
                } while (rawQuery.moveToNext());
            }
            rawQuery.close();
            return databaseUpdatedStatusDBModel;
        } catch (SQLiteDatabaseLockedException unused) {
            return null;
        } catch (Exception unused2) {
            return null;
        }
    }

    public DatabaseUpdatedStatusDBModel getdateSeriesStreamsDBStatus(String str, String str2) {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT * FROM series_streams_status WHERE series_streams_status_category = '" + str + "' AND " + KEY_DB_SERIES_STREAMS_CATEGORY_ID + " = '" + str2 + "'", null);
            DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModel = new DatabaseUpdatedStatusDBModel();
            if (rawQuery.moveToFirst()) {
                do {
                    databaseUpdatedStatusDBModel.setIdAuto(Integer.parseInt(rawQuery.getString(0)));
                    databaseUpdatedStatusDBModel.setDbUpadatedStatusState(rawQuery.getString(1));
                    databaseUpdatedStatusDBModel.setDbLastUpdatedDate(rawQuery.getString(2));
                    databaseUpdatedStatusDBModel.setDbCategory(rawQuery.getString(3));
                    databaseUpdatedStatusDBModel.setDbCategoryID(rawQuery.getString(4));
                } while (rawQuery.moveToNext());
            }
            rawQuery.close();
            return databaseUpdatedStatusDBModel;
        } catch (SQLiteDatabaseLockedException unused) {
            return null;
        } catch (Exception unused2) {
            return null;
        }
    }

    public boolean updateSeriesStreamsCatDBStatus(String str, String str2, String str3) {
        try {
            String str4 = "SELECT rowid FROM series_streams_cat_status WHERE series_streams_cat_status_category = '" + str + "' AND " + KEY_DB_SERIES_STREAMS_CAT_CATEGORY_ID + " = '" + str2 + "'";
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            String str5 = "";
            Cursor rawQuery = writableDatabase.rawQuery(str4, null);
            if (rawQuery != null) {
                if (rawQuery.moveToFirst()) {
                    do {
                        str5 = String.valueOf(Integer.parseInt(rawQuery.getString(rawQuery.getColumnIndex(KEY_ID_SERIES_STREAMS_CAT_STATUS))));
                    } while (rawQuery.moveToNext());
                }
            } else if (this.context != null) {
                Toast.makeText(this.context, "cursor is null", 1).show();
            }
            if (!str5.equals("")) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(KEY_DB_SERIES_STREAMS_CAT_STATUS_STATE, str3);
                writableDatabase.update(TABLE_SERIES_STREAM_CAT_STATUS, contentValues, "series_streams_cat_status_id= ?", new String[]{str5});
                if (rawQuery != null) {
                    rawQuery.close();
                }
                return true;
            }
            if (rawQuery != null) {
                rawQuery.close();
            }
            return false;
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
            return false;
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
            return false;
        }
    }

    public boolean updateSeriesStreamsDBStatus(String str, String str2, String str3) {
        try {
            String str4 = "SELECT rowid FROM series_streams_status WHERE series_streams_status_category = '" + str + "' AND " + KEY_DB_SERIES_STREAMS_CATEGORY_ID + " = '" + str2 + "'";
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            String str5 = "";
            Cursor rawQuery = writableDatabase.rawQuery(str4, null);
            if (rawQuery != null) {
                if (rawQuery.moveToFirst()) {
                    do {
                        str5 = String.valueOf(Integer.parseInt(rawQuery.getString(rawQuery.getColumnIndex(KEY_ID_SERIES_STREAMS_STATUS))));
                    } while (rawQuery.moveToNext());
                }
            } else if (this.context != null) {
                Toast.makeText(this.context, "cursor is null", 1).show();
            }
            if (!str5.equals("")) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(KEY_DB_SERIES_STREAMS_STATUS_STATE, str3);
                writableDatabase.update(TABLE_SERIES_STREAM_STATUS, contentValues, "series_streams_status_id= ?", new String[]{str5});
                if (rawQuery != null) {
                    rawQuery.close();
                }
                return true;
            }
            if (rawQuery != null) {
                rawQuery.close();
            }
            return false;
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
            return false;
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
            return false;
        }
    }

    public boolean updateSeriesStreamsCatDBStatusAndDate(String str, String str2, String str3, String str4) {
        try {
            String str5 = "SELECT rowid FROM series_streams_cat_status WHERE series_streams_cat_status_category = '" + str + "' AND " + KEY_DB_SERIES_STREAMS_CAT_CATEGORY_ID + " = '" + str2 + "'";
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            String str6 = "";
            Cursor rawQuery = writableDatabase.rawQuery(str5, null);
            if (rawQuery != null) {
                if (rawQuery.moveToFirst()) {
                    do {
                        str6 = String.valueOf(Integer.parseInt(rawQuery.getString(rawQuery.getColumnIndex(KEY_ID_SERIES_STREAMS_CAT_STATUS))));
                    } while (rawQuery.moveToNext());
                }
            } else if (this.context != null) {
                Toast.makeText(this.context, "cursor is null", 1).show();
            }
            if (!str6.equals("")) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(KEY_DB_SERIES_STREAMS_CAT_STATUS_STATE, str3);
                contentValues.put(KEY_DB_SERIES_STREAMS_CAT_LAST_UPDATED_DATE, str4);
                writableDatabase.update(TABLE_SERIES_STREAM_CAT_STATUS, contentValues, "series_streams_cat_status_id= ?", new String[]{str6});
                if (rawQuery != null) {
                    rawQuery.close();
                }
                return true;
            }
            if (rawQuery != null) {
                rawQuery.close();
            }
            return false;
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
            return false;
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
            return false;
        }
    }

    public boolean updateseriesStreamsDBStatusAndDate(String str, String str2, String str3, String str4) {
        try {
            String str5 = "SELECT rowid FROM series_streams_status WHERE series_streams_status_category = '" + str + "' AND " + KEY_DB_SERIES_STREAMS_CATEGORY_ID + " = '" + str2 + "'";
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            String str6 = "";
            Cursor rawQuery = writableDatabase.rawQuery(str5, null);
            if (rawQuery != null) {
                if (rawQuery.moveToFirst()) {
                    do {
                        str6 = String.valueOf(Integer.parseInt(rawQuery.getString(rawQuery.getColumnIndex(KEY_ID_SERIES_STREAMS_STATUS))));
                    } while (rawQuery.moveToNext());
                }
            } else if (this.context != null) {
                Toast.makeText(this.context, "cursor is null", 1).show();
            }
            if (!str6.equals("")) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(KEY_DB_SERIES_STREAMS_STATUS_STATE, str3);
                contentValues.put(KEY_DB_SERIES_STREAMS_LAST_UPDATED_DATE, str4);
                writableDatabase.update(TABLE_SERIES_STREAM_STATUS, contentValues, "series_streams_status_id= ?", new String[]{str6});
                if (rawQuery != null) {
                    rawQuery.close();
                }
                return true;
            }
            if (rawQuery != null) {
                rawQuery.close();
            }
            return false;
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
            return false;
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
            return false;
        }
    }

    public void emptySeriesStreamCatRecords() {
        try {
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.execSQL("delete from series_category_v2");
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void emptySeriesStreamRecords() {
        try {
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.execSQL("delete from series_streams_v2");
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void emptySeriesStreamCatandSeriesStreamRecords() {
        try {
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.execSQL("delete from series_category_v2");
            writableDatabase.execSQL("delete from series_streams_v2");
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void deleteAndRecreateSeriesStreams() {
        try {
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.execSQL("DROP TABLE IF EXISTS series_streams_v2");
            writableDatabase.execSQL("DROP TABLE IF EXISTS series_streams_status");
            onCreate(writableDatabase);
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void deleteAndRecreateAllVSeriesTables() {
        try {
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.execSQL("DROP TABLE IF EXISTS series_streams_v2");
            writableDatabase.execSQL("DROP TABLE IF EXISTS series_streams_status");
            writableDatabase.execSQL("DROP TABLE IF EXISTS series_category_v2");
            writableDatabase.execSQL("DROP TABLE IF EXISTS series_streams_cat_status");
            onCreate(writableDatabase);
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void deleteAndRecreateSeriesCategories() {
        try {
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.execSQL("DROP TABLE IF EXISTS series_category_v2");
            writableDatabase.execSQL("DROP TABLE IF EXISTS series_streams_cat_status");
            onCreate(writableDatabase);
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public int getUncatCount(String str) {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT * FROM series_streams_v2 WHERE category_id_series_stream_v2 ='" + str + "'", null);
            rawQuery.moveToFirst();
            int i = rawQuery.getInt(0);
            rawQuery.close();
            return i;
        } catch (SQLiteDatabaseLockedException unused) {
            return 0;
        } catch (SQLiteException unused2) {
            return 0;
        } catch (Exception unused3) {
            return 0;
        }
    }
}
