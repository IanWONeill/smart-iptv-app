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
import com.nst.yourname.model.FavouriteM3UModel;
import com.nst.yourname.model.LiveStreamCategoryIdDBModel;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.M3UCategoriesModel;
import com.nst.yourname.model.M3UModel;
import com.nst.yourname.model.callback.GetEpisdoeDetailsCallback;
import com.nst.yourname.model.callback.LiveStreamCategoriesCallback;
import com.nst.yourname.model.callback.LiveStreamsCallback;
import com.nst.yourname.model.callback.VodCategoriesCallback;
import com.nst.yourname.model.callback.VodStreamsCallback;
import com.nst.yourname.model.pojo.PanelAvailableChannelsPojo;
import com.nst.yourname.model.pojo.XMLTVProgrammePojo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
public class LiveStreamDBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "iptv_live_streams_tv.db";
    private static final int DATABASE_VERSION = 4;
    private static final String KEY_ADDED = "added";
    private static final String KEY_ADDED_VOD = "added";
    private static final String KEY_AVAIL_CHANNEL_CATEGORY_NAME = "category_name";
    private static final String KEY_AVAIL_CHANNEL_CONTAINER_EXTENSION = "container_extension";
    private static final String KEY_AVAIL_CHANNEL_LIVE = "live";
    private static final String KEY_AVAIL_CHANNEL_SERIES_NO = "series_no";
    private static final String KEY_AVAIL_CHANNEL_TYPE_NAME = "type_name";
    private static final String KEY_AVAIL_CHANNEL_URL = "url";
    private static final String KEY_CATEGORY_ID = "categoryID";
    private static final String KEY_CATEGORY_ID_LIVE = "categoryID_live";
    private static final String KEY_CATEGORY_ID_LIVE_STREAMS = "categoryID";
    private static final String KEY_CATEGORY_ID_MOVIE = "categoryID_movie";
    private static final String KEY_CATEGORY_ID_VOD = "categoryId";
    private static final String KEY_CATEGORY_NAME = "categoryname";
    private static final String KEY_CATEGORY_NAME_LIVE = "categoryname_live";
    private static final String KEY_CATEGORY_NAME_MOVIE = "categoryname_movie";
    private static final String KEY_CHANNEL_ID = "channel_id";
    private static final String KEY_CONTAINER_EXT_VOD = "containerExtension";
    private static final String KEY_CUSTOMER_SID = "custom_sid";
    private static final String KEY_CUSTOM_SID_VOD = "customSid";
    private static final String KEY_DB_CATEGORY = "iptv_db_updated_status_category";
    private static final String KEY_DB_CATEGORY_ID = "iptv_db_updated_status_category_id";
    private static final String KEY_DB_LAST_UPDATED_DATE = "iptv_db_updated_status_last_updated_date";
    private static final String KEY_DB_SERIES_M3U_STREAM_CAT_ID = "series_m3u_stream_container_cat_id";
    private static final String KEY_DB_SERIES_M3U_STREAM_EXT = "series_m3u_stream_container_ext";
    private static final String KEY_DB_SERIES_M3U_STREAM_ID = "series_m3u_stream_id";
    private static final String KEY_DB_SERIES_M3U_STREAM_IMAGE = "series_m3u_stream_container_image";
    private static final String KEY_DB_SERIES_M3U_STREAM_TITLE = "series_m3u_stream_title";
    private static final String KEY_DB_STATUS_STATE = "iptv_db_updated_status_state";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DIRECT_SOURCE = "direct_source";
    private static final String KEY_DIRECT_SOURCE_VOD = "directSource";
    private static final String KEY_EPG_CHANNEL_ID = "epg_channel_id";
    private static final String KEY_ID = "id";
    private static final String KEY_ID_AUTO_EPG = "id_epg_aut0";
    private static final String KEY_ID_AUTO_VOD = "id_auto_vod";
    private static final String KEY_ID_DB_UPDATE_STATUS = "iptv_db_update_status_id";
    private static final String KEY_ID_LIVE = "id_live";
    private static final String KEY_ID_LIVE_STREAMS = "id";
    private static final String KEY_ID_MAG_PORTAL = "id_auto_mag";
    private static final String KEY_ID_MOVIE = "id_movie";
    private static final String KEY_ID_PARENT_ID = "paent_id";
    private static final String KEY_ID_PASWORD = "id_password";
    private static final String KEY_ID_PASWORD_STATUS = "id_password_status";
    private static final String KEY_ID_SERIES_M3U_STREAMS_AUTO_INC = "series_m3u_streams_auto_inc";
    private static final String KEY_MAG_PORTAL = "mag_portal";
    private static final String KEY_MOVE_TO = "move_to";
    private static final String KEY_MOVIE_DURTION = "movie_duration";
    private static final String KEY_MOVIE_ELAPSED_TIME = "movie_elapsed_time";
    private static final String KEY_NAME = "name";
    private static final String KEY_NAME_VOD = "name";
    private static final String KEY_NUM_LIVE_STREAMS = "num";
    private static final String KEY_NUM_VOD = "num_";
    private static final String KEY_PASSWORD_STATUS = "password_status";
    private static final String KEY_PASSWORD_STATUS_CAT_ID = "password_status_cat_id";
    private static final String KEY_PASSWORD_USER_DETAIL = "user_detail";
    private static final String KEY_SERIAL_NO_VOD = "seriesNo";
    private static final String KEY_START = "start";
    private static final String KEY_STOP = "stop";
    private static final String KEY_STREAMTYPE_VOD = "streamType";
    private static final String KEY_STREAM_ICON = "stream_icon";
    private static final String KEY_STREAM_ICON_VOD = "streamIcon";
    private static final String KEY_STREAM_ID = "stream_id";
    private static final String KEY_STREAM_ID_VOD = "streamId";
    private static final String KEY_STRESM_TYPE = "stream_type";
    private static final String KEY_TITLE = "title";
    private static final String KEY_TV_ARCHIVE = "tv_archive";
    private static final String KEY_TV_ARCHIVE_DURATION = "tv_archive_duration";
    private static final String KEY_URL = "url";
    private static final String KEY_USER_DETAIL = "password_user_detail";
    private static final String KEY_USER_ID = "user_id_referred";
    private static final String KEY_USER_PASSWORD = "password";
    private static final String TABLE_DATABASE_UPDATE_STATUS = "iptv_db_update_status";
    private static final String TABLE_EPG = "epg";
    private static final String TABLE_IPTV_ALL_M3U_CATEGORY = "iptv_m3u_all_category";
    private static final String TABLE_IPTV_AVAILABLE_CHANNNELS = "iptv_live_streams";
    private static final String TABLE_IPTV_AVAILABLE_CHANNNELS_ALL_M3U = "iptv_live_streams_m3u_all";
    private static final String TABLE_IPTV_AVAILABLE_CHANNNELS_M3U = "iptv_live_streams_m3u";
    private static final String TABLE_IPTV_FAVOURITES_M3U = "iptv_m3u_favourites";
    private static final String TABLE_IPTV_LIVE_CATEGORY = "iptv_live_category";
    private static final String TABLE_IPTV_LIVE_CATEGORY_M3U = "iptv_live_category_m3u";
    private static final String TABLE_IPTV_LIVE_STREAMS_CATEGORY = "iptv_live_streams_category";
    private static final String TABLE_IPTV_MAG_PORTAL = "iptv_mag_portal_table";
    private static final String TABLE_IPTV_MOVIE_CATEGORY = "iptv_movie_category";
    private static final String TABLE_IPTV_MOVIE_CATEGORY_M3U = "iptv_movie_category_m3u";
    private static final String TABLE_IPTV_PASSWORD = "iptv_password_table";
    private static final String TABLE_IPTV_PASSWORD_M3U = "iptv_password_table_m3u";
    private static final String TABLE_IPTV_PASSWORD_STATUS = "iptv_password_status_table";
    private static final String TABLE_IPTV_PASSWORD_STATUS_M3U = "iptv_password_status_table_m3u";
    private static final String TABLE_IPTV_RECENT_WATCHED_M3U = "iptv_recent_watched_m3u";
    private static final String TABLE_IPTV_SERIES_CATEGORY_M3U = "iptv_series_category_m3u";
    private static final String TABLE_IPTV_VOD_STREAMS = "iptv_vod_streams";
    private static final String TABLE_M3U_SERIES_STREAMS = "series_m3u_streams";
    private String ALTER_PASSWORD_STATUS_TABLE_2 = "ALTER TABLE iptv_password_status_table ADD COLUMN user_id_referred TEXT;";
    private String ALTER_PASSWORD_TABLE_1 = "ALTER TABLE iptv_password_table ADD COLUMN user_id_referred TEXT;";
    String CREATE_DB_UPDATED_STATUS_TABLE = "CREATE TABLE IF NOT EXISTS iptv_db_update_status(iptv_db_update_status_id INTEGER PRIMARY KEY,iptv_db_updated_status_state TEXT,iptv_db_updated_status_last_updated_date TEXT,iptv_db_updated_status_category TEXT,iptv_db_updated_status_category_id TEXT)";
    String CREATE_EPG_TABLE = "CREATE TABLE IF NOT EXISTS epg(id_epg_aut0 INTEGER PRIMARY KEY,title TEXT,start TEXT,stop TEXT,description TEXT,channel_id TEXT)";
    String CREATE_FAV_M3U_TABLE = "CREATE TABLE IF NOT EXISTS iptv_m3u_favourites(id INTEGER PRIMARY KEY,url TEXT,user_id_referred TEXT,name TEXT,categoryID TEXT)";
    String CREATE_LIVE_AVAILABLE_CHANELS = "CREATE TABLE IF NOT EXISTS iptv_live_streams(id INTEGER PRIMARY KEY,num TEXT,name TEXT,stream_type TEXT,stream_id TEXT,stream_icon TEXT,epg_channel_id TEXT,added TEXT,categoryID TEXT,custom_sid TEXT,tv_archive TEXT,direct_source TEXT,tv_archive_duration TEXT,type_name TEXT,category_name TEXT,series_no TEXT,live TEXT,container_extension TEXT)";
    String CREATE_LIVE_AVAILABLE_CHANELS_ALL_M3U = "CREATE TABLE IF NOT EXISTS iptv_live_streams_m3u_all(id INTEGER PRIMARY KEY,num TEXT,name TEXT,stream_type TEXT,stream_id TEXT,stream_icon TEXT,epg_channel_id TEXT,added TEXT,categoryID TEXT,custom_sid TEXT,tv_archive TEXT,direct_source TEXT,tv_archive_duration TEXT,type_name TEXT,category_name TEXT,series_no TEXT,live TEXT,url TEXT,container_extension TEXT,user_id_referred TEXT,move_to TEXT)";
    String CREATE_LIVE_AVAILABLE_CHANELS_M3U = "CREATE TABLE IF NOT EXISTS iptv_live_streams_m3u(id INTEGER PRIMARY KEY,num TEXT,name TEXT,stream_type TEXT,stream_id TEXT,stream_icon TEXT,epg_channel_id TEXT,added TEXT,categoryID TEXT,custom_sid TEXT,tv_archive TEXT,direct_source TEXT,tv_archive_duration TEXT,type_name TEXT,category_name TEXT,series_no TEXT,live TEXT,container_extension TEXT,url TEXT,user_id_referred TEXT)";
    String CREATE_LIVE_CATEGORY_TABLE = "CREATE TABLE IF NOT EXISTS iptv_live_category(id_live INTEGER PRIMARY KEY,categoryID_live TEXT,categoryname_live TEXT,paent_id TEXT)";
    String CREATE_LIVE_CATEGORY_TABLE_M3U = "CREATE TABLE IF NOT EXISTS iptv_live_category_m3u(id INTEGER PRIMARY KEY,categoryID TEXT,categoryname TEXT,user_id_referred TEXT)";
    String CREATE_LIVE_STREAM_CATEGORY_TABLE = "CREATE TABLE IF NOT EXISTS iptv_live_streams_category(id INTEGER PRIMARY KEY,categoryID TEXT,categoryname TEXT)";
    String CREATE_M3U_CATEGORY_TABLE = "CREATE TABLE IF NOT EXISTS iptv_m3u_all_category(id INTEGER PRIMARY KEY,categoryID TEXT,categoryname TEXT,user_id_referred TEXT)";
    String CREATE_M3U_SERIES_STREAMS_TABLE = "CREATE TABLE IF NOT EXISTS series_m3u_streams(series_m3u_streams_auto_inc INTEGER PRIMARY KEY,series_m3u_stream_id TEXT,series_m3u_stream_title TEXT,series_m3u_stream_container_ext TEXT,series_m3u_stream_container_image TEXT,series_m3u_stream_container_cat_id TEXT)";
    String CREATE_MAG_PORTAL_TABLE = "CREATE TABLE IF NOT EXISTS iptv_mag_portal_table(id_auto_mag INTEGER PRIMARY KEY,mag_portal TEXT)";
    String CREATE_MOVIE_CATEGORY_TABLE = "CREATE TABLE IF NOT EXISTS iptv_movie_category(id_movie INTEGER PRIMARY KEY,categoryID_movie TEXT,categoryname_movie TEXT,paent_id TEXT)";
    String CREATE_MOVIE_CATEGORY_TABLE_M3U = "CREATE TABLE IF NOT EXISTS iptv_movie_category_m3u(id INTEGER PRIMARY KEY,categoryID TEXT,categoryname TEXT,paent_id TEXT,user_id_referred TEXT)";
    String CREATE_PASSWORD_STATUS_TABLE = "CREATE TABLE IF NOT EXISTS iptv_password_status_table(id_password_status INTEGER PRIMARY KEY,password_status_cat_id TEXT,password_user_detail TEXT,password_status TEXT,user_id_referred TEXT)";
    String CREATE_PASSWORD_STATUS_TABLE_M3U = "CREATE TABLE IF NOT EXISTS iptv_password_status_table_m3u(id_password_status INTEGER PRIMARY KEY,password_status_cat_id TEXT,password_user_detail TEXT,password_status TEXT,user_id_referred TEXT)";
    String CREATE_PASSWORD_TABLE = "CREATE TABLE IF NOT EXISTS iptv_password_table(id_password INTEGER PRIMARY KEY,user_detail TEXT,password TEXT,user_id_referred TEXT)";
    String CREATE_PASSWORD_TABLE_M3U = "CREATE TABLE IF NOT EXISTS iptv_password_table_m3u(id_password INTEGER PRIMARY KEY,user_detail TEXT,password TEXT,user_id_referred TEXT)";
    String CREATE_RECENT_WATCHED_M3U = "CREATE TABLE IF NOT EXISTS iptv_recent_watched_m3u(id INTEGER PRIMARY KEY,num TEXT,name TEXT,stream_type TEXT,stream_id TEXT,stream_icon TEXT,epg_channel_id TEXT,added TEXT,categoryID TEXT,custom_sid TEXT,tv_archive TEXT,direct_source TEXT,tv_archive_duration TEXT,type_name TEXT,category_name TEXT,series_no TEXT,live TEXT,container_extension TEXT,url TEXT,user_id_referred TEXT,movie_elapsed_time TEXT,movie_duration TEXT)";
    String CREATE_SERIES_CATEGORY_TABLE_M3U = "CREATE TABLE IF NOT EXISTS iptv_series_category_m3u(id INTEGER PRIMARY KEY,categoryID TEXT,categoryname TEXT,user_id_referred TEXT)";
    String CREATE_VOD_TABLE = "CREATE TABLE IF NOT EXISTS iptv_vod_streams(id_auto_vod INTEGER PRIMARY KEY,num_ TEXT,name TEXT,streamType TEXT,streamId TEXT,streamIcon TEXT,added TEXT,categoryId TEXT,seriesNo TEXT,containerExtension TEXT,customSid TEXT,directSource TEXT)";
    Context context;
    SQLiteDatabase db;

    public LiveStreamDBHandler(Context context2) {
        super(context2, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 4);
        this.context = context2;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(this.CREATE_LIVE_CATEGORY_TABLE);
        sQLiteDatabase.execSQL(this.CREATE_MOVIE_CATEGORY_TABLE);
        sQLiteDatabase.execSQL(this.CREATE_EPG_TABLE);
        sQLiteDatabase.execSQL(this.CREATE_PASSWORD_TABLE);
        sQLiteDatabase.execSQL(this.CREATE_PASSWORD_STATUS_TABLE);
        sQLiteDatabase.execSQL(this.CREATE_LIVE_STREAM_CATEGORY_TABLE);
        sQLiteDatabase.execSQL(this.CREATE_LIVE_AVAILABLE_CHANELS);
        sQLiteDatabase.execSQL(this.CREATE_VOD_TABLE);
        sQLiteDatabase.execSQL(this.CREATE_DB_UPDATED_STATUS_TABLE);
        sQLiteDatabase.execSQL(this.CREATE_MAG_PORTAL_TABLE);
        sQLiteDatabase.execSQL(this.CREATE_LIVE_AVAILABLE_CHANELS_ALL_M3U);
        sQLiteDatabase.execSQL(this.CREATE_M3U_CATEGORY_TABLE);
        sQLiteDatabase.execSQL(this.CREATE_FAV_M3U_TABLE);
        sQLiteDatabase.execSQL(this.CREATE_M3U_SERIES_STREAMS_TABLE);
        sQLiteDatabase.execSQL(this.CREATE_LIVE_AVAILABLE_CHANELS_M3U);
        sQLiteDatabase.execSQL(this.CREATE_LIVE_CATEGORY_TABLE_M3U);
        sQLiteDatabase.execSQL(this.CREATE_MOVIE_CATEGORY_TABLE_M3U);
        sQLiteDatabase.execSQL(this.CREATE_SERIES_CATEGORY_TABLE_M3U);
        sQLiteDatabase.execSQL(this.CREATE_PASSWORD_STATUS_TABLE_M3U);
        sQLiteDatabase.execSQL(this.CREATE_PASSWORD_TABLE_M3U);
        sQLiteDatabase.execSQL(this.CREATE_RECENT_WATCHED_M3U);
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i < 3) {
            sQLiteDatabase.execSQL(this.ALTER_PASSWORD_TABLE_1);
            sQLiteDatabase.execSQL(this.ALTER_PASSWORD_STATUS_TABLE_2);
        }
        if (i < 4) {
            sQLiteDatabase.execSQL(this.CREATE_LIVE_AVAILABLE_CHANELS_ALL_M3U);
            sQLiteDatabase.execSQL(this.CREATE_M3U_CATEGORY_TABLE);
            sQLiteDatabase.execSQL(this.CREATE_FAV_M3U_TABLE);
            sQLiteDatabase.execSQL(this.CREATE_M3U_SERIES_STREAMS_TABLE);
            sQLiteDatabase.execSQL(this.CREATE_LIVE_AVAILABLE_CHANELS_M3U);
            sQLiteDatabase.execSQL(this.CREATE_LIVE_CATEGORY_TABLE_M3U);
            sQLiteDatabase.execSQL(this.CREATE_MOVIE_CATEGORY_TABLE_M3U);
            sQLiteDatabase.execSQL(this.CREATE_SERIES_CATEGORY_TABLE_M3U);
            sQLiteDatabase.execSQL(this.CREATE_PASSWORD_STATUS_TABLE_M3U);
            sQLiteDatabase.execSQL(this.CREATE_PASSWORD_TABLE_M3U);
            sQLiteDatabase.execSQL(this.CREATE_RECENT_WATCHED_M3U);
        }
    }

    public void deleteAndRecreateAllTables() {
        try {
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.execSQL("DROP TABLE IF EXISTS iptv_live_category");
            writableDatabase.execSQL("DROP TABLE IF EXISTS iptv_movie_category");
            writableDatabase.execSQL("DROP TABLE IF EXISTS epg");
            writableDatabase.execSQL("DROP TABLE IF EXISTS iptv_live_streams_category");
            writableDatabase.execSQL("DROP TABLE IF EXISTS iptv_live_streams");
            writableDatabase.execSQL("DROP TABLE IF EXISTS iptv_vod_streams");
            writableDatabase.execSQL("DROP TABLE IF EXISTS iptv_mag_portal_table");
            onCreate(writableDatabase);
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void addMagPortal(String str) {
        try {
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_MAG_PORTAL, str);
            writableDatabase.insert(TABLE_IPTV_MAG_PORTAL, null, contentValues);
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public int getMagportal(String str) {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM iptv_mag_portal_table WHERE mag_portal='" + str + "'", null);
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

    public String getMagportal() {
        new ArrayList();
        String str = "";
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  * FROM iptv_mag_portal_table", null);
            if (rawQuery.moveToFirst()) {
                do {
                    str = rawQuery.getString(1);
                } while (rawQuery.moveToNext());
            }
            rawQuery.close();
            return str;
        } catch (SQLiteDatabaseLockedException unused) {
            return "";
        } catch (SQLiteException unused2) {
            return "";
        }
    }

    public void addMovieCategories(List<VodCategoriesCallback> list) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            int size = list.size();
            if (size != 0) {
                for (int i = 0; i < size; i++) {
                    contentValues.put(KEY_CATEGORY_ID_MOVIE, list.get(i).getCategoryId());
                    contentValues.put(KEY_CATEGORY_NAME_MOVIE, list.get(i).getCategoryName());
                    contentValues.put(KEY_ID_PARENT_ID, list.get(i).getParentId());
                    writableDatabase.insert(TABLE_IPTV_MOVIE_CATEGORY, null, contentValues);
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
    public void addMovieCategoriesM3U(M3UCategoriesModel m3UCategoriesModel) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            int userID = SharepreferenceDBHandler.getUserID(this.context);
            contentValues.put("categoryID", m3UCategoriesModel.getCategoryId());
            contentValues.put(KEY_CATEGORY_NAME, m3UCategoriesModel.getCategoryName());
            contentValues.put(KEY_ID_PARENT_ID, (Integer) 0);
            contentValues.put(KEY_USER_ID, Integer.valueOf(userID));
            writableDatabase.insert(TABLE_IPTV_MOVIE_CATEGORY_M3U, null, contentValues);
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void addSeriesCategoriesM3U(M3UCategoriesModel m3UCategoriesModel) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            int userID = SharepreferenceDBHandler.getUserID(this.context);
            contentValues.put("categoryID", m3UCategoriesModel.getCategoryId());
            contentValues.put(KEY_CATEGORY_NAME, m3UCategoriesModel.getCategoryName());
            contentValues.put(KEY_USER_ID, Integer.valueOf(userID));
            writableDatabase.insert(TABLE_IPTV_SERIES_CATEGORY_M3U, null, contentValues);
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public M3UCategoriesModel getCatByCatIDALLM3U(String str) {
        String str2 = "SELECT  * FROM iptv_m3u_all_category WHERE categoryID='" + str + "' AND " + KEY_USER_ID + "='" + SharepreferenceDBHandler.getUserID(this.context) + "'";
        M3UCategoriesModel m3UCategoriesModel = new M3UCategoriesModel();
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery(str2, null);
            if (rawQuery.moveToFirst()) {
                do {
                    m3UCategoriesModel.setCategoryId(rawQuery.getString(1));
                    m3UCategoriesModel.setCategoryName(rawQuery.getString(2));
                } while (rawQuery.moveToNext());
            }
            rawQuery.close();
            return m3UCategoriesModel;
        } catch (SQLiteDatabaseLockedException unused) {
            return null;
        } catch (SQLiteException unused2) {
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    public boolean checkCategoryExistsM3U(String str, String str2) {
        char c;
        //ToDo: initialized...
        String str3 = null;
        int i;
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        int hashCode = str2.hashCode();
        if (hashCode != 3322092) {
            if (hashCode == 104087344 && str2.equals(AppConst.EVENT_TYPE_MOVIE)) {
                c = 1;
                switch (c) {
                    case 0:
                        str3 = "SELECT  COUNT(*) FROM iptv_live_category_m3u WHERE categoryID='" + str + "' AND " + KEY_USER_ID + "='" + userID + "'";
                        break;
                    case 1:
                        str3 = "SELECT  COUNT(*) FROM iptv_movie_category_m3u WHERE categoryID='" + str + "' AND " + KEY_USER_ID + "='" + userID + "'";
                        break;
                    default:
                        str3 = "SELECT  COUNT(*) FROM iptv_series_category_m3u WHERE categoryID='" + str + "' AND " + KEY_USER_ID + "='" + userID + "'";
                        break;
                }
                Cursor rawQuery = getReadableDatabase().rawQuery(str3, null);
                rawQuery.moveToFirst();
                i = rawQuery.getInt(0);
                rawQuery.close();
                if (i > 0) {
                    return true;
                }
                return false;
            }
        } else if (str2.equals(KEY_AVAIL_CHANNEL_LIVE)) {
            c = 0;
            switch (c) {
            }
            Cursor rawQuery2 = getReadableDatabase().rawQuery(str3, null);
            rawQuery2.moveToFirst();
            i = rawQuery2.getInt(0);
            rawQuery2.close();
            if (i > 0) {
            }
        }
        c = 65535;
        switch (c) {
        }
        try {
            Cursor rawQuery22 = getReadableDatabase().rawQuery(str3, null);
            rawQuery22.moveToFirst();
            i = rawQuery22.getInt(0);
            rawQuery22.close();
            if (i > 0) {
            }
        } catch (SQLiteDatabaseLockedException unused) {
            return false;
        } catch (SQLiteException unused2) {
            return false;
        }
        //ToDo: return...
        return false;
    }

    public void addLiveCategoriesM3U(M3UCategoriesModel m3UCategoriesModel) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            int userID = SharepreferenceDBHandler.getUserID(this.context);
            contentValues.put("categoryID", m3UCategoriesModel.getCategoryId());
            contentValues.put(KEY_CATEGORY_NAME, m3UCategoriesModel.getCategoryName());
            contentValues.put(KEY_USER_ID, Integer.valueOf(userID));
            writableDatabase.insert(TABLE_IPTV_LIVE_CATEGORY_M3U, null, contentValues);
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void addAllAvailableChannelALLM3U(Map<String, M3UModel> map) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            for (M3UModel m3UModel : map.values()) {
                if (m3UModel.getName() == null) {
                    Log.e("sdsadsd", "addAllAvailableChannel:" + m3UModel.getName());
                }
                if (m3UModel.getNum() != null) {
                    contentValues.put("num", String.valueOf(m3UModel.getNum()));
                } else {
                    contentValues.put("num", "");
                }
                if (m3UModel.getName() != null) {
                    contentValues.put("name", m3UModel.getName());
                } else {
                    contentValues.put("name", "");
                }
                if (m3UModel.getStreamType() != null) {
                    contentValues.put(KEY_STRESM_TYPE, m3UModel.getStreamType());
                } else {
                    contentValues.put(KEY_STRESM_TYPE, "");
                }
                if (m3UModel.getStreamId() != null) {
                    contentValues.put(KEY_STREAM_ID, m3UModel.getStreamId());
                } else {
                    contentValues.put(KEY_STREAM_ID, "");
                }
                if (m3UModel.getStreamIcon() != null) {
                    contentValues.put(KEY_STREAM_ICON, m3UModel.getStreamIcon());
                } else {
                    contentValues.put(KEY_STREAM_ICON, "");
                }
                if (m3UModel.getEpgChannelId() != null) {
                    contentValues.put(KEY_EPG_CHANNEL_ID, m3UModel.getEpgChannelId());
                } else {
                    contentValues.put(KEY_EPG_CHANNEL_ID, "");
                }
                if (m3UModel.getAdded() != null) {
                    contentValues.put("added", m3UModel.getAdded());
                } else {
                    contentValues.put("added", "");
                }
                if (m3UModel.getCategoryId() != null) {
                    contentValues.put("categoryID", m3UModel.getCategoryId());
                } else {
                    contentValues.put("categoryID", "");
                }
                if (m3UModel.getCustomSid() != null) {
                    contentValues.put(KEY_CUSTOMER_SID, m3UModel.getCustomSid());
                } else {
                    contentValues.put(KEY_CUSTOMER_SID, "");
                }
                if (m3UModel.getTvArchive() != null) {
                    contentValues.put(KEY_TV_ARCHIVE, m3UModel.getTvArchive());
                } else {
                    contentValues.put(KEY_TV_ARCHIVE, "");
                }
                if (m3UModel.getDirectSource() != null) {
                    contentValues.put(KEY_DIRECT_SOURCE, m3UModel.getDirectSource());
                } else {
                    contentValues.put(KEY_DIRECT_SOURCE, "");
                }
                if (m3UModel.getTvArchiveDuration() != null) {
                    contentValues.put(KEY_TV_ARCHIVE_DURATION, m3UModel.getTvArchiveDuration());
                } else {
                    contentValues.put(KEY_TV_ARCHIVE_DURATION, "");
                }
                if (m3UModel.getTypeName() != null) {
                    contentValues.put(KEY_AVAIL_CHANNEL_TYPE_NAME, String.valueOf(m3UModel.getTypeName()));
                } else {
                    contentValues.put(KEY_AVAIL_CHANNEL_TYPE_NAME, "");
                }
                if (m3UModel.getCategoryName() != null) {
                    contentValues.put("category_name", m3UModel.getCategoryName());
                } else {
                    contentValues.put("category_name", "");
                }
                if (m3UModel.getSeriesNo() != null) {
                    contentValues.put(KEY_AVAIL_CHANNEL_SERIES_NO, String.valueOf(m3UModel.getSeriesNo()));
                } else {
                    contentValues.put(KEY_AVAIL_CHANNEL_SERIES_NO, "");
                }
                if (m3UModel.getLive() != null) {
                    contentValues.put(KEY_AVAIL_CHANNEL_LIVE, m3UModel.getLive());
                } else {
                    contentValues.put(KEY_AVAIL_CHANNEL_LIVE, "");
                }
                if (m3UModel.getContainerExtension() != null) {
                    contentValues.put(KEY_AVAIL_CHANNEL_CONTAINER_EXTENSION, String.valueOf(m3UModel.getContainerExtension()));
                } else {
                    contentValues.put(KEY_AVAIL_CHANNEL_CONTAINER_EXTENSION, "");
                }
                if (m3UModel.getUrl() != null) {
                    contentValues.put("url", m3UModel.getUrl());
                } else {
                    contentValues.put("url", "");
                }
                if (m3UModel.getMoveTo() != null) {
                    contentValues.put(KEY_MOVE_TO, m3UModel.getMoveTo());
                } else {
                    contentValues.put(KEY_MOVE_TO, "");
                }
                contentValues.put(KEY_USER_ID, Integer.valueOf(SharepreferenceDBHandler.getUserID(this.context)));
                writableDatabase.insert(TABLE_IPTV_AVAILABLE_CHANNNELS_ALL_M3U, null, contentValues);
            }
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
            writableDatabase.close();
            Log.e("sdsadsd", "addAllAvailableChannel: Transaction end");
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void updateMoveToStatus(String str, String str2) {
        try {
            int userID = SharepreferenceDBHandler.getUserID(this.context);
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_MOVE_TO, str2);
            if (str.equals(AppConst.PASSWORD_UNSET)) {
                writableDatabase.update(TABLE_IPTV_AVAILABLE_CHANNNELS_ALL_M3U, contentValues, "user_id_referred = ?", new String[]{String.valueOf(userID)});
            } else {
                writableDatabase.update(TABLE_IPTV_AVAILABLE_CHANNNELS_ALL_M3U, contentValues, "categoryID = ? AND user_id_referred = ?", new String[]{str, String.valueOf(userID)});
            }
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void updateMoveToStatusURL(String str, String str2, String str3) {
        try {
            int userID = SharepreferenceDBHandler.getUserID(this.context);
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_MOVE_TO, str2);
            writableDatabase.update(TABLE_IPTV_AVAILABLE_CHANNNELS_ALL_M3U, contentValues, "categoryID = ? AND user_id_referred = ? AND url = ?", new String[]{str, String.valueOf(userID), str3});
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public int getCountMovedItems(String str, String str2) {
        String str3;
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        if (str.equals(AppConst.PASSWORD_UNSET)) {
            str3 = "SELECT  COUNT(*) FROM iptv_live_streams_m3u_all WHERE user_id_referred='" + userID + "' AND " + KEY_MOVE_TO + "='" + str2 + "'";
        } else {
            str3 = "SELECT  COUNT(*) FROM iptv_live_streams_m3u_all WHERE categoryID='" + str + "' AND " + KEY_USER_ID + "='" + userID + "' AND " + KEY_MOVE_TO + "='" + str2 + "'";
        }
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery(str3, null);
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

    public String checkM3UItemMoved(String str, String str2) {
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  move_to FROM iptv_live_streams_m3u_all WHERE categoryID='" + str + "' AND " + KEY_USER_ID + "='" + userID + "' AND " + "url" + "='" + str2 + "' LIMIT 1", null);
            rawQuery.moveToFirst();
            String string = rawQuery.getString(0);
            rawQuery.close();
            return string;
        } catch (SQLiteDatabaseLockedException unused) {
            return "";
        } catch (SQLiteException unused2) {
            return "";
        }
    }

    public void addAllAvailableChannelM3U(ArrayList<LiveStreamsDBModel> arrayList, String str) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            Iterator<LiveStreamsDBModel> it = arrayList.iterator();
            while (it.hasNext()) {
                LiveStreamsDBModel next = it.next();
                if (next.getName() == null) {
                    Log.e("sdsadsd", "addAllAvailableChannel:" + next.getName());
                }
                if (next.getNum() != null) {
                    contentValues.put("num", String.valueOf(next.getNum()));
                } else {
                    contentValues.put("num", "");
                }
                if (next.getName() != null) {
                    contentValues.put("name", next.getName());
                } else {
                    contentValues.put("name", "");
                }
                contentValues.put(KEY_STRESM_TYPE, str);
                if (next.getStreamId() != null) {
                    contentValues.put(KEY_STREAM_ID, next.getStreamId());
                } else {
                    contentValues.put(KEY_STREAM_ID, "");
                }
                if (next.getStreamIcon() != null) {
                    contentValues.put(KEY_STREAM_ICON, next.getStreamIcon());
                } else {
                    contentValues.put(KEY_STREAM_ICON, "");
                }
                if (next.getEpgChannelId() != null) {
                    contentValues.put(KEY_EPG_CHANNEL_ID, next.getEpgChannelId());
                } else {
                    contentValues.put(KEY_EPG_CHANNEL_ID, "");
                }
                if (next.getAdded() != null) {
                    contentValues.put("added", next.getAdded());
                } else {
                    contentValues.put("added", "");
                }
                if (next.getCategoryId() != null) {
                    contentValues.put("categoryID", next.getCategoryId());
                } else {
                    contentValues.put("categoryID", "");
                }
                if (next.getCustomSid() != null) {
                    contentValues.put(KEY_CUSTOMER_SID, next.getCustomSid());
                } else {
                    contentValues.put(KEY_CUSTOMER_SID, "");
                }
                if (next.getTvArchive() != null) {
                    contentValues.put(KEY_TV_ARCHIVE, next.getTvArchive());
                } else {
                    contentValues.put(KEY_TV_ARCHIVE, "");
                }
                if (next.getDirectSource() != null) {
                    contentValues.put(KEY_DIRECT_SOURCE, next.getDirectSource());
                } else {
                    contentValues.put(KEY_DIRECT_SOURCE, "");
                }
                if (next.getTvArchiveDuration() != null) {
                    contentValues.put(KEY_TV_ARCHIVE_DURATION, next.getTvArchiveDuration());
                } else {
                    contentValues.put(KEY_TV_ARCHIVE_DURATION, "");
                }
                if (next.getTypeName() != null) {
                    contentValues.put(KEY_AVAIL_CHANNEL_TYPE_NAME, String.valueOf(next.getTypeName()));
                } else {
                    contentValues.put(KEY_AVAIL_CHANNEL_TYPE_NAME, "");
                }
                if (next.getCategoryName() != null) {
                    contentValues.put("category_name", next.getCategoryName());
                } else {
                    contentValues.put("category_name", "");
                }
                if (next.getSeriesNo() != null) {
                    contentValues.put(KEY_AVAIL_CHANNEL_SERIES_NO, String.valueOf(next.getSeriesNo()));
                } else {
                    contentValues.put(KEY_AVAIL_CHANNEL_SERIES_NO, "");
                }
                if (next.getLive() != null) {
                    contentValues.put(KEY_AVAIL_CHANNEL_LIVE, next.getLive());
                } else {
                    contentValues.put(KEY_AVAIL_CHANNEL_LIVE, "");
                }
                if (next.getContaiinerExtension() != null) {
                    contentValues.put(KEY_AVAIL_CHANNEL_CONTAINER_EXTENSION, String.valueOf(next.getContaiinerExtension()));
                } else {
                    contentValues.put(KEY_AVAIL_CHANNEL_CONTAINER_EXTENSION, "");
                }
                if (next.getUrl() != null) {
                    contentValues.put("url", next.getUrl());
                } else {
                    contentValues.put("url", "");
                }
                contentValues.put(KEY_USER_ID, Integer.valueOf(SharepreferenceDBHandler.getUserID(this.context)));
                writableDatabase.insert(TABLE_IPTV_AVAILABLE_CHANNNELS_M3U, null, contentValues);
            }
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
            writableDatabase.close();
            Log.e("sdsadsd", "addAllAvailableChannel: Transaction end");
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void addAllAvailableChannel(Map<String, PanelAvailableChannelsPojo> map) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            for (PanelAvailableChannelsPojo panelAvailableChannelsPojo : map.values()) {
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
                } else if (panelAvailableChannelsPojo.getTypeName() != null) {
                    String typeName = panelAvailableChannelsPojo.getTypeName();
                    if ((typeName != null && typeName.contains("Live")) || (typeName != null && typeName.contains("Live Streams"))) {
                        contentValues.put(KEY_STRESM_TYPE, KEY_AVAIL_CHANNEL_LIVE);
                    } else if ((typeName != null && typeName.contains("Movies")) || (typeName != null && typeName.contains("Movies Streams"))) {
                        contentValues.put(KEY_STRESM_TYPE, AppConst.EVENT_TYPE_MOVIE);
                    }
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
                    contentValues.put("added", panelAvailableChannelsPojo.getAdded());
                } else {
                    contentValues.put("added", "");
                }
                if (panelAvailableChannelsPojo.getCategoryId() != null) {
                    contentValues.put("categoryID", panelAvailableChannelsPojo.getCategoryId());
                } else {
                    contentValues.put("categoryID", "");
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
                writableDatabase.insert(TABLE_IPTV_AVAILABLE_CHANNNELS, null, contentValues);
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

    public void addAllAvailableChannel(List<LiveStreamsCallback> list) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            for (LiveStreamsCallback liveStreamsCallback : list) {
                if (liveStreamsCallback.getNum() != null) {
                    contentValues.put("num", String.valueOf(liveStreamsCallback.getNum()));
                } else {
                    contentValues.put("num", "");
                }
                if (liveStreamsCallback.getName() != null) {
                    contentValues.put("name", liveStreamsCallback.getName());
                } else {
                    contentValues.put("name", "");
                }
                if (liveStreamsCallback.getStreamType() != null) {
                    contentValues.put(KEY_STRESM_TYPE, liveStreamsCallback.getStreamType());
                } else {
                    contentValues.put(KEY_STRESM_TYPE, "");
                }
                if (liveStreamsCallback.getStreamId() != null) {
                    contentValues.put(KEY_STREAM_ID, liveStreamsCallback.getStreamId());
                } else {
                    contentValues.put(KEY_STREAM_ID, "");
                }
                if (liveStreamsCallback.getStreamIcon() != null) {
                    contentValues.put(KEY_STREAM_ICON, liveStreamsCallback.getStreamIcon());
                } else {
                    contentValues.put(KEY_STREAM_ICON, "");
                }
                if (liveStreamsCallback.getEpgChannelId() != null) {
                    contentValues.put(KEY_EPG_CHANNEL_ID, liveStreamsCallback.getEpgChannelId());
                } else {
                    contentValues.put(KEY_EPG_CHANNEL_ID, "");
                }
                if (liveStreamsCallback.getAdded() != null) {
                    contentValues.put("added", liveStreamsCallback.getAdded());
                } else {
                    contentValues.put("added", "");
                }
                if (liveStreamsCallback.getCategoryId() == null) {
                    contentValues.put("categoryID", "-2");
                } else if (checkUnCategoryCountLive(liveStreamsCallback.getCategoryId()) > 0) {
                    contentValues.put("categoryID", liveStreamsCallback.getCategoryId());
                } else {
                    contentValues.put("categoryID", "-2");
                }
                if (liveStreamsCallback.getCustomSid() != null) {
                    contentValues.put(KEY_CUSTOMER_SID, liveStreamsCallback.getCustomSid());
                } else {
                    contentValues.put(KEY_CUSTOMER_SID, "");
                }
                if (liveStreamsCallback.getTvArchive() != null) {
                    contentValues.put(KEY_TV_ARCHIVE, liveStreamsCallback.getTvArchive());
                } else {
                    contentValues.put(KEY_TV_ARCHIVE, "");
                }
                if (liveStreamsCallback.getDirectSource() != null) {
                    contentValues.put(KEY_DIRECT_SOURCE, liveStreamsCallback.getDirectSource());
                } else {
                    contentValues.put(KEY_DIRECT_SOURCE, "");
                }
                if (liveStreamsCallback.getTvArchiveDuration() != null) {
                    contentValues.put(KEY_TV_ARCHIVE_DURATION, liveStreamsCallback.getTvArchiveDuration());
                } else {
                    contentValues.put(KEY_TV_ARCHIVE_DURATION, "");
                }
                contentValues.put(KEY_AVAIL_CHANNEL_TYPE_NAME, "");
                contentValues.put("category_name", "");
                contentValues.put(KEY_AVAIL_CHANNEL_SERIES_NO, "");
                contentValues.put(KEY_AVAIL_CHANNEL_LIVE, "");
                contentValues.put(KEY_AVAIL_CHANNEL_CONTAINER_EXTENSION, "");
                writableDatabase.insert(TABLE_IPTV_AVAILABLE_CHANNNELS, null, contentValues);
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

    private int checkUnCategoryCountMovies(String str) {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  * FROM iptv_movie_category WHERE categoryID_movie ='" + str + "'", null);
            int count = rawQuery.getCount();
            rawQuery.close();
            return count;
        } catch (SQLiteDatabaseLockedException unused) {
            return 0;
        } catch (SQLiteException unused2) {
            return 0;
        }
    }

    private int checkUnCategoryCountLive(String str) {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  * FROM iptv_live_category WHERE categoryID_live ='" + str + "'", null);
            int count = rawQuery.getCount();
            rawQuery.close();
            return count;
        } catch (SQLiteDatabaseLockedException unused) {
            return 0;
        } catch (SQLiteException unused2) {
            return 0;
        }
    }

    public void addAllAvailableMovies(List<VodStreamsCallback> list) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            for (VodStreamsCallback vodStreamsCallback : list) {
                if (vodStreamsCallback.getNum() != null) {
                    contentValues.put("num", String.valueOf(vodStreamsCallback.getNum()));
                } else {
                    contentValues.put("num", "");
                }
                if (vodStreamsCallback.getName() != null) {
                    contentValues.put("name", vodStreamsCallback.getName());
                } else {
                    contentValues.put("name", "");
                }
                if (vodStreamsCallback.getStreamType() != null) {
                    contentValues.put(KEY_STRESM_TYPE, vodStreamsCallback.getStreamType());
                } else {
                    contentValues.put(KEY_STRESM_TYPE, "");
                }
                if (vodStreamsCallback.getStreamId() != null) {
                    contentValues.put(KEY_STREAM_ID, vodStreamsCallback.getStreamId());
                } else {
                    contentValues.put(KEY_STREAM_ID, "");
                }
                if (vodStreamsCallback.getStreamIcon() != null) {
                    contentValues.put(KEY_STREAM_ICON, vodStreamsCallback.getStreamIcon());
                } else {
                    contentValues.put(KEY_STREAM_ICON, "");
                }
                contentValues.put(KEY_EPG_CHANNEL_ID, "");
                if (vodStreamsCallback.getAdded() != null) {
                    contentValues.put("added", vodStreamsCallback.getAdded());
                } else {
                    contentValues.put("added", "");
                }
                if (vodStreamsCallback.getCategoryId() == null) {
                    contentValues.put("categoryID", "-3");
                } else if (checkUnCategoryCountMovies(vodStreamsCallback.getCategoryId()) > 0) {
                    contentValues.put("categoryID", vodStreamsCallback.getCategoryId());
                } else {
                    contentValues.put("categoryID", "-3");
                }
                if (vodStreamsCallback.getCustomSid() != null) {
                    contentValues.put(KEY_CUSTOMER_SID, vodStreamsCallback.getCustomSid());
                } else {
                    contentValues.put(KEY_CUSTOMER_SID, "");
                }
                contentValues.put(KEY_TV_ARCHIVE, "");
                if (vodStreamsCallback.getDirectSource() != null) {
                    contentValues.put(KEY_DIRECT_SOURCE, vodStreamsCallback.getDirectSource());
                } else {
                    contentValues.put(KEY_DIRECT_SOURCE, "");
                }
                contentValues.put(KEY_TV_ARCHIVE_DURATION, "");
                contentValues.put(KEY_AVAIL_CHANNEL_TYPE_NAME, "");
                contentValues.put("category_name", "");
                if (vodStreamsCallback.getSeriesNo() != null) {
                    contentValues.put(KEY_AVAIL_CHANNEL_SERIES_NO, String.valueOf(vodStreamsCallback.getSeriesNo()));
                } else {
                    contentValues.put(KEY_AVAIL_CHANNEL_SERIES_NO, "");
                }
                contentValues.put(KEY_AVAIL_CHANNEL_LIVE, "");
                if (vodStreamsCallback.getContainerExtension() != null) {
                    contentValues.put(KEY_AVAIL_CHANNEL_CONTAINER_EXTENSION, String.valueOf(vodStreamsCallback.getContainerExtension()));
                } else {
                    contentValues.put(KEY_AVAIL_CHANNEL_CONTAINER_EXTENSION, "");
                }
                writableDatabase.insert(TABLE_IPTV_AVAILABLE_CHANNNELS, null, contentValues);
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

    public ArrayList<LiveStreamCategoryIdDBModel> getAllliveCategories() {
        String str;
        int i;
        ArrayList<LiveStreamCategoryIdDBModel> arrayList = new ArrayList<>();
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        String currentAPPType = SharepreferenceDBHandler.getCurrentAPPType(this.context);
        System.currentTimeMillis();
        if (currentAPPType.equals(AppConst.TYPE_M3U)) {
            str = "SELECT * FROM iptv_live_category_m3u WHERE user_id_referred='" + userID + "' AND " + "categoryID" + " IS NOT NULL";
        } else {
            str = "SELECT * FROM iptv_live_category INNER JOIN iptv_live_streams ON iptv_live_category.categoryID_live = iptv_live_streams.categoryID GROUP BY iptv_live_streams.categoryID ORDER BY iptv_live_category.id_live";
        }
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery(str, null);
            if (rawQuery.moveToFirst()) {
                System.currentTimeMillis();
                do {
                    try {
                        i = Integer.parseInt(rawQuery.getString(3));
                    } catch (NumberFormatException unused) {
                        i = 0;
                    }
                    LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
                    liveStreamCategoryIdDBModel.setId(Integer.parseInt(rawQuery.getString(0)));
                    liveStreamCategoryIdDBModel.setLiveStreamCategoryID(rawQuery.getString(1));
                    liveStreamCategoryIdDBModel.setLiveStreamCategoryName(rawQuery.getString(2));
                    liveStreamCategoryIdDBModel.setParentId(i);
                    arrayList.add(liveStreamCategoryIdDBModel);
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

    public ArrayList<LiveStreamCategoryIdDBModel> getAllm3uCategories() {
        ArrayList<LiveStreamCategoryIdDBModel> arrayList = new ArrayList<>();
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        new ArrayList();
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT * FROM iptv_m3u_all_category WHERE user_id_referred='" + userID + "'", null);
            if (rawQuery.moveToFirst()) {
                do {
                    LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
                    liveStreamCategoryIdDBModel.setId(Integer.parseInt(rawQuery.getString(0)));
                    liveStreamCategoryIdDBModel.setLiveStreamCategoryID(rawQuery.getString(1));
                    liveStreamCategoryIdDBModel.setLiveStreamCategoryName(rawQuery.getString(2));
                    liveStreamCategoryIdDBModel.setParentId(Integer.parseInt(rawQuery.getString(3)));
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

    public ArrayList<LiveStreamCategoryIdDBModel> getAllMovieCategories() {
        ArrayList<LiveStreamCategoryIdDBModel> arrayList = new ArrayList<>();
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT * FROM iptv_movie_category", null);
            if (rawQuery.moveToFirst()) {
                do {
                    LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
                    liveStreamCategoryIdDBModel.setId(Integer.parseInt(rawQuery.getString(0)));
                    liveStreamCategoryIdDBModel.setLiveStreamCategoryID(rawQuery.getString(1));
                    liveStreamCategoryIdDBModel.setLiveStreamCategoryName(rawQuery.getString(2));
                    liveStreamCategoryIdDBModel.setParentId(Integer.parseInt(rawQuery.getString(3)));
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

    public ArrayList<LiveStreamCategoryIdDBModel> getAllMovieCategoriesHavingParentIdZero() {
        String str;
        int i;
        ArrayList<LiveStreamCategoryIdDBModel> arrayList = new ArrayList<>();
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            str = "SELECT * FROM iptv_movie_category_m3u WHERE user_id_referred='" + userID + "' AND " + "categoryID" + " IS NOT NULL";
        } else {
            str = "SELECT * FROM iptv_movie_category WHERE paent_id=0";
        }
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery(str, null);
            if (rawQuery.moveToFirst()) {
                do {
                    try {
                        i = Integer.parseInt(rawQuery.getString(3));
                    } catch (NumberFormatException unused) {
                        i = 0;
                    }
                    LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
                    liveStreamCategoryIdDBModel.setId(Integer.parseInt(rawQuery.getString(0)));
                    liveStreamCategoryIdDBModel.setLiveStreamCategoryID(rawQuery.getString(1));
                    liveStreamCategoryIdDBModel.setLiveStreamCategoryName(rawQuery.getString(2));
                    liveStreamCategoryIdDBModel.setParentId(i);
                    arrayList.add(liveStreamCategoryIdDBModel);
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

    public ArrayList<LiveStreamCategoryIdDBModel> getAllSeriesCategories() {
        ArrayList<LiveStreamCategoryIdDBModel> arrayList = new ArrayList<>();
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT * FROM iptv_series_category_m3u WHERE user_id_referred='" + userID + "' AND " + "categoryID" + " IS NOT NULL", null);
            if (rawQuery.moveToFirst()) {
                do {
                    LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
                    liveStreamCategoryIdDBModel.setId(Integer.parseInt(rawQuery.getString(0)));
                    liveStreamCategoryIdDBModel.setLiveStreamCategoryID(rawQuery.getString(1));
                    liveStreamCategoryIdDBModel.setLiveStreamCategoryName(rawQuery.getString(2));
                    liveStreamCategoryIdDBModel.setParentId(0);
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

    public ArrayList<LiveStreamCategoryIdDBModel> getAllMovieCategoriesHavingParentIdZero(int i, int i2) {
        ArrayList<LiveStreamCategoryIdDBModel> arrayList = new ArrayList<>();
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT * FROM iptv_movie_category WHERE id_movie>=" + i + " AND " + KEY_ID_MOVIE + "<=" + i2 + " AND " + KEY_ID_PARENT_ID + "=0", null);
            if (rawQuery.moveToFirst()) {
                do {
                    LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
                    liveStreamCategoryIdDBModel.setId(Integer.parseInt(rawQuery.getString(0)));
                    liveStreamCategoryIdDBModel.setLiveStreamCategoryID(rawQuery.getString(1));
                    liveStreamCategoryIdDBModel.setLiveStreamCategoryName(rawQuery.getString(2));
                    liveStreamCategoryIdDBModel.setParentId(Integer.parseInt(rawQuery.getString(3)));
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

    public ArrayList<LiveStreamCategoryIdDBModel> getAllLiveCategoriesHavingParentIdNotZero(String str) {
        ArrayList<LiveStreamCategoryIdDBModel> arrayList = new ArrayList<>();
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT * FROM iptv_live_category WHERE paent_id=" + str, null);
            if (rawQuery.moveToFirst()) {
                do {
                    LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
                    liveStreamCategoryIdDBModel.setId(Integer.parseInt(rawQuery.getString(0)));
                    liveStreamCategoryIdDBModel.setLiveStreamCategoryID(rawQuery.getString(1));
                    liveStreamCategoryIdDBModel.setLiveStreamCategoryName(rawQuery.getString(2));
                    liveStreamCategoryIdDBModel.setParentId(Integer.parseInt(rawQuery.getString(3)));
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

    public ArrayList<LiveStreamCategoryIdDBModel> getAllMovieCategoriesHavingParentIdNotZero(String str) {
        ArrayList<LiveStreamCategoryIdDBModel> arrayList = new ArrayList<>();
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT * FROM iptv_movie_category WHERE paent_id='" + str + "'", null);
            if (rawQuery.moveToFirst()) {
                do {
                    LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
                    liveStreamCategoryIdDBModel.setId(Integer.parseInt(rawQuery.getString(0)));
                    liveStreamCategoryIdDBModel.setLiveStreamCategoryID(rawQuery.getString(1));
                    liveStreamCategoryIdDBModel.setLiveStreamCategoryName(rawQuery.getString(2));
                    liveStreamCategoryIdDBModel.setParentId(Integer.parseInt(rawQuery.getString(3)));
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

    public int getAvailableChannelsCount() {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM iptv_live_streams", null);
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

    public int getAvailableChannelsCountM3U() {
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM iptv_live_streams_m3u_all WHERE user_id_referred='" + userID + "'", null);
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

    public int getStreamsCount(String str) {
        if (str.equals(KEY_AVAIL_CHANNEL_LIVE)) {
            try {
                Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM iptv_live_streams WHERE stream_type='" + str + "' OR " + KEY_STRESM_TYPE + "='created_live'", null);
                rawQuery.moveToFirst();
                int i = rawQuery.getInt(0);
                rawQuery.close();
                return i;
            } catch (SQLiteDatabaseLockedException unused) {
                return 0;
            } catch (SQLiteException unused2) {
                return 0;
            }
        } else if (str.equals(AppConst.EVENT_TYPE_MOVIE)) {
            try {
                Cursor rawQuery2 = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM iptv_live_streams WHERE stream_type='" + str + "' OR " + KEY_STRESM_TYPE + "='created_movie'", null);
                rawQuery2.moveToFirst();
                int i2 = rawQuery2.getInt(0);
                rawQuery2.close();
                return i2;
            } catch (SQLiteDatabaseLockedException unused3) {
                return 0;
            } catch (SQLiteException unused4) {
                return 0;
            }
        } else {
            try {
                Cursor rawQuery3 = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM iptv_live_streams WHERE stream_type='" + str + "' ", null);
                rawQuery3.moveToFirst();
                int i3 = rawQuery3.getInt(0);
                rawQuery3.close();
                return i3;
            } catch (SQLiteDatabaseLockedException unused5) {
                return 0;
            } catch (SQLiteException unused6) {
                return 0;
            }
        }
    }

    public int getM3UCount(String str) {
        String str2;
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        if (str.equals(AppConst.PASSWORD_UNSET)) {
            str2 = "SELECT  COUNT(*) FROM iptv_live_streams_m3u_all WHERE user_id_referred='" + userID + "' AND " + KEY_MOVE_TO + " NOT IN ('live','movie','series') OR " + KEY_MOVE_TO + " IS NULL";
        } else {
            str2 = "SELECT  COUNT(*) FROM iptv_live_streams_m3u_all WHERE categoryID='" + str + "' AND " + KEY_USER_ID + "='" + userID + "' AND " + KEY_MOVE_TO + " NOT IN ('live','movie','series') OR " + KEY_MOVE_TO + " IS NULL";
        }
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery(str2, null);
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

    public int getAvailableCountM3U(String str, String str2) {
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        String str3 = "";
        if (str.equals(AppConst.PASSWORD_UNSET)) {
            str3 = "SELECT  COUNT(*) FROM iptv_live_streams_m3u WHERE user_id_referred='" + userID + "' AND " + KEY_STRESM_TYPE + "='" + str2 + "'";
        } else if (!str.equals("-1")) {
            str3 = "SELECT  COUNT(*) FROM iptv_live_streams_m3u WHERE categoryID='" + str + "' AND " + KEY_USER_ID + "='" + userID + "' AND " + KEY_STRESM_TYPE + "='" + str2 + "'";
        }
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery(str3, null);
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

    public int getSubCatMovieCount(String str, String str2) {
        if (str2.equals(KEY_AVAIL_CHANNEL_LIVE)) {
            try {
                Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM iptv_live_streams WHERE ( stream_type LIKE '%" + str2 + "%' OR " + KEY_STRESM_TYPE + " LIKE '%radio%' )  AND " + "categoryID" + "='" + str + "'", null);
                rawQuery.moveToFirst();
                int i = rawQuery.getInt(0);
                rawQuery.close();
                return i;
            } catch (SQLiteDatabaseLockedException unused) {
                return 0;
            } catch (SQLiteException unused2) {
                return 0;
            }
        } else if (str2.equals(AppConst.EVENT_TYPE_MOVIE)) {
            try {
                Cursor rawQuery2 = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM iptv_live_streams WHERE ( stream_type LIKE '%" + str2 + "%' OR " + KEY_STRESM_TYPE + " LIKE '%radio%' )  AND " + "categoryID" + "='" + str + "'", null);
                rawQuery2.moveToFirst();
                int i2 = rawQuery2.getInt(0);
                rawQuery2.close();
                return i2;
            } catch (SQLiteDatabaseLockedException unused3) {
                return 0;
            } catch (SQLiteException unused4) {
                return 0;
            }
        } else {
            try {
                Cursor rawQuery3 = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM iptv_live_streams WHERE ( stream_type LIKE '%" + str2 + "%' OR " + KEY_STRESM_TYPE + " LIKE '%radio%' )  AND " + "categoryID" + "='" + str + "'", null);
                rawQuery3.moveToFirst();
                int i3 = rawQuery3.getInt(0);
                rawQuery3.close();
                return i3;
            } catch (SQLiteDatabaseLockedException unused5) {
                return 0;
            } catch (SQLiteException unused6) {
                return 0;
            }
        }
    }

    public int getEPGCount() {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  * FROM epg", null);
            int count = rawQuery.getCount();
            rawQuery.close();
            return count;
        } catch (SQLiteDatabaseLockedException unused) {
            return 0;
        } catch (SQLiteException unused2) {
            return 0;
        }
    }

    public int getLiveStreamsCount(String str) {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM iptv_live_streams WHERE categoryID='" + str + "'", null);
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

    public void addEPG(List<XMLTVProgrammePojo> list) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            int size = list.size();
            if (size != 0) {
                for (int i = 0; i < size; i++) {
                    contentValues.put("title", list.get(i).getTitle());
                    contentValues.put("start", list.get(i).getStart());
                    contentValues.put(KEY_STOP, list.get(i).getStop());
                    contentValues.put("description", list.get(i).getDesc());
                    contentValues.put(KEY_CHANNEL_ID, list.get(i).getChannel());
                    writableDatabase.insert(TABLE_EPG, null, contentValues);
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

    public ArrayList<XMLTVProgrammePojo> getEPG(String str) {
        ArrayList<XMLTVProgrammePojo> arrayList = new ArrayList<>();
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  * FROM epg WHERE channel_id='" + str + "'", null);
            if (rawQuery.moveToFirst()) {
                do {
                    XMLTVProgrammePojo xMLTVProgrammePojo = new XMLTVProgrammePojo();
                    xMLTVProgrammePojo.setTitle(rawQuery.getString(1));
                    xMLTVProgrammePojo.setStart(rawQuery.getString(2));
                    xMLTVProgrammePojo.setStop(rawQuery.getString(3));
                    xMLTVProgrammePojo.setDesc(rawQuery.getString(4));
                    xMLTVProgrammePojo.setChannel(rawQuery.getString(5));
                    arrayList.add(xMLTVProgrammePojo);
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

    public ArrayList<LiveStreamsDBModel> getChannelDetails(String str, String str2) {
        String str3 = str;
        if (str3 == null) {
            return null;
        }
        ArrayList<LiveStreamsDBModel> arrayList = new ArrayList<>();
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        int i = 3;
        if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            try {
                Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  * FROM iptv_live_streams_m3u WHERE stream_type LIKE '%live%' AND num='" + str3 + "' AND " + KEY_USER_ID + "='" + userID + "' LIMIT 1", null);
                if (rawQuery.moveToFirst()) {
                    do {
                        LiveStreamsDBModel liveStreamsDBModel = new LiveStreamsDBModel();
                        liveStreamsDBModel.setIdAuto(Integer.parseInt(rawQuery.getString(0)));
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
                        liveStreamsDBModel.setUrl(rawQuery.getString(18));
                        arrayList.add(liveStreamsDBModel);
                    } while (rawQuery.moveToNext());
                }
                rawQuery.close();
                return arrayList;
            } catch (SQLiteDatabaseLockedException unused) {
                return null;
            } catch (SQLiteException unused2) {
                return null;
            } catch (Exception unused3) {
                return null;
            }
        } else {
            try {
                Cursor rawQuery2 = getReadableDatabase().rawQuery("SELECT  * FROM iptv_live_streams WHERE stream_type LIKE '%live%' AND num='" + str3 + "' LIMIT 1", null);
                if (rawQuery2.moveToFirst()) {
                    while (true) {
                        LiveStreamsDBModel liveStreamsDBModel2 = new LiveStreamsDBModel();
                        liveStreamsDBModel2.setIdAuto(Integer.parseInt(rawQuery2.getString(0)));
                        liveStreamsDBModel2.setNum(rawQuery2.getString(1));
                        liveStreamsDBModel2.setName(rawQuery2.getString(2));
                        liveStreamsDBModel2.setStreamType(rawQuery2.getString(i));
                        liveStreamsDBModel2.setStreamId(rawQuery2.getString(4));
                        liveStreamsDBModel2.setStreamIcon(rawQuery2.getString(5));
                        liveStreamsDBModel2.setEpgChannelId(rawQuery2.getString(6));
                        liveStreamsDBModel2.setAdded(rawQuery2.getString(7));
                        liveStreamsDBModel2.setCategoryId(rawQuery2.getString(8));
                        liveStreamsDBModel2.setCustomSid(rawQuery2.getString(9));
                        liveStreamsDBModel2.setTvArchive(rawQuery2.getString(10));
                        liveStreamsDBModel2.setDirectSource(rawQuery2.getString(11));
                        liveStreamsDBModel2.setTvArchiveDuration(rawQuery2.getString(12));
                        liveStreamsDBModel2.setTypeName(rawQuery2.getString(13));
                        liveStreamsDBModel2.setCategoryName(rawQuery2.getString(14));
                        liveStreamsDBModel2.setSeriesNo(rawQuery2.getString(15));
                        liveStreamsDBModel2.setLive(rawQuery2.getString(16));
                        liveStreamsDBModel2.setContaiinerExtension(rawQuery2.getString(17));
                        arrayList.add(liveStreamsDBModel2);
                        if (!rawQuery2.moveToNext()) {
                            break;
                        }
                        i = 3;
                    }
                }
                rawQuery2.close();
                return arrayList;
            } catch (SQLiteDatabaseLockedException unused4) {
                return null;
            } catch (SQLiteException unused5) {
                return null;
            } catch (Exception unused6) {
                return null;
            }
        }
    }

    public ArrayList<FavouriteM3UModel> getFirstchannelNumFav() {
        int i;
        String liveSubCatSort = SharepreferenceDBHandler.getLiveSubCatSort(this.context);
        new ArrayList();
        String str = "";
        ArrayList<FavouriteM3UModel> arrayList = new ArrayList<>();
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            char c = 65535;
            switch (liveSubCatSort.hashCode()) {
                case 48:
                    if (liveSubCatSort.equals(AppConst.PASSWORD_UNSET)) {
                        c = 0;
                        break;
                    }
                    break;
                case 50:
                    if (liveSubCatSort.equals("2")) {
                        c = 1;
                        break;
                    }
                    break;
                case 51:
                    if (liveSubCatSort.equals("3")) {
                        c = 2;
                        break;
                    }
                    break;
                case 52:
                    if (liveSubCatSort.equals("4")) {
                        c = 3;
                        break;
                    }
                    break;
                case 53:
                    if (liveSubCatSort.equals("5")) {
                        c = 4;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    str = "SELECT  * FROM iptv_m3u_favourites WHERE  (SELECT iptv_live_streams_m3u_all.id FROM iptv_live_streams_m3u_all WHERE iptv_m3u_favourites.url = iptv_live_streams_m3u_all.url AND iptv_live_streams_m3u_all.move_to='live' AND iptv_live_streams_m3u_all.user_id_referred=iptv_m3u_favourites.user_id_referred AND iptv_m3u_favourites.user_id_referred ='" + userID + "' LIMIT 1) LIMIT 1";
                    break;
                case 1:
                    str = "SELECT  * FROM iptv_m3u_favourites WHERE  (SELECT iptv_live_streams_m3u_all.id FROM iptv_live_streams_m3u_all WHERE iptv_m3u_favourites.url = iptv_live_streams_m3u_all.url AND iptv_live_streams_m3u_all.move_to='live' AND iptv_live_streams_m3u_all.user_id_referred=iptv_m3u_favourites.user_id_referred AND iptv_m3u_favourites.user_id_referred ='" + userID + "' LIMIT 1) ORDER BY " + TABLE_IPTV_FAVOURITES_M3U + "." + "name" + " ASC LIMIT 1";
                    break;
                case 2:
                    str = "SELECT  * FROM iptv_m3u_favourites WHERE  (SELECT iptv_live_streams_m3u_all.id FROM iptv_live_streams_m3u_all WHERE iptv_m3u_favourites.url = iptv_live_streams_m3u_all.url AND iptv_live_streams_m3u_all.move_to='live' AND iptv_live_streams_m3u_all.user_id_referred=iptv_m3u_favourites.user_id_referred AND iptv_m3u_favourites.user_id_referred ='" + userID + "' LIMIT 1) ORDER BY " + TABLE_IPTV_FAVOURITES_M3U + "." + "name" + " DESC LIMIT 1";
                    break;
                case 3:
                    str = "SELECT  * FROM iptv_m3u_favourites WHERE  (SELECT iptv_live_streams_m3u_all.id FROM iptv_live_streams_m3u_all WHERE iptv_m3u_favourites.url = iptv_live_streams_m3u_all.url AND iptv_live_streams_m3u_all.move_to='live' AND iptv_live_streams_m3u_all.user_id_referred=iptv_m3u_favourites.user_id_referred AND iptv_m3u_favourites.user_id_referred ='" + userID + "' ORDER BY cast(" + TABLE_IPTV_AVAILABLE_CHANNNELS_ALL_M3U + "." + "num" + " as REAL) ASC LIMIT 1) LIMIT 1";
                    break;
                case 4:
                    str = "SELECT  * FROM iptv_m3u_favourites WHERE  (SELECT iptv_live_streams_m3u_all.id FROM iptv_live_streams_m3u_all WHERE iptv_m3u_favourites.url = iptv_live_streams_m3u_all.url AND iptv_live_streams_m3u_all.move_to='live' AND iptv_live_streams_m3u_all.user_id_referred=iptv_m3u_favourites.user_id_referred AND iptv_m3u_favourites.user_id_referred ='" + userID + "' ORDER BY cast(" + TABLE_IPTV_AVAILABLE_CHANNNELS_ALL_M3U + "." + "num" + " as REAL) DESC LIMIT 1) LIMIT 1";
                    break;
                default:
                    str = "SELECT  * FROM iptv_m3u_favourites WHERE  (SELECT iptv_live_streams_m3u_all.id FROM iptv_live_streams_m3u_all WHERE iptv_m3u_favourites.url = iptv_live_streams_m3u_all.url AND iptv_live_streams_m3u_all.move_to='live' AND iptv_live_streams_m3u_all.user_id_referred=iptv_m3u_favourites.user_id_referred AND iptv_m3u_favourites.user_id_referred ='" + userID + "' LIMIT 1) LIMIT 1";
                    break;
            }
        }
        try {
            this.db = getReadableDatabase();
            Cursor rawQuery = this.db.rawQuery(str, null);
            if (rawQuery.moveToFirst()) {
                do {
                    FavouriteM3UModel favouriteM3UModel = new FavouriteM3UModel();
                    try {
                        i = Integer.parseInt(rawQuery.getString(0));
                    } catch (NumberFormatException unused) {
                        i = 0;
                    }
                    favouriteM3UModel.setId(i);
                    favouriteM3UModel.setUrl(rawQuery.getString(1));
                    favouriteM3UModel.setUserID(Integer.parseInt(rawQuery.getString(2)));
                    favouriteM3UModel.setName(rawQuery.getString(3));
                    favouriteM3UModel.setCategoryID(rawQuery.getString(4));
                    arrayList.add(favouriteM3UModel);
                } while (rawQuery.moveToNext());
            }
            rawQuery.close();
            return arrayList;
        } catch (SQLiteDatabaseLockedException unused2) {
            return arrayList;
        } catch (SQLiteException unused3) {
            return arrayList;
        }
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Removed duplicated region for block: B:143:0x0595 A[Catch:{ SQLiteDatabaseLockedException -> 0x063e, SQLiteException -> 0x063d }, LOOP:1: B:143:0x0595->B:144:0x0637, LOOP_START] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x02cd A[Catch:{ SQLiteDatabaseLockedException -> 0x037f, SQLiteException -> 0x037e }, LOOP:0: B:64:0x02cd->B:65:0x0378, LOOP_START] */
    public ArrayList<LiveStreamsDBModel> getFirstchannelNum(String str) {
        Cursor rawQuery;
        char c;
        String str2;
        char c2;
        Cursor rawQuery2;
        char c3;
        String str3;
        char c4;
        String str4 = str;
        String liveSubCatSort = SharepreferenceDBHandler.getLiveSubCatSort(this.context);
        ArrayList<LiveStreamsDBModel> arrayList = new ArrayList<>();
        String str5 = "";
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            if (str4 == null || !str4.equals(AppConst.PASSWORD_UNSET)) {
                if (str4 != null && !str4.equals("-1")) {
                    switch (liveSubCatSort.hashCode()) {
                        case 48:
                            if (liveSubCatSort.equals(AppConst.PASSWORD_UNSET)) {
                                c3 = 0;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case 49:
                            if (liveSubCatSort.equals("1")) {
                                c3 = 1;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case 50:
                        default:
                            c3 = 65535;
                            break;
                        case 51:
                            if (liveSubCatSort.equals("3")) {
                                c3 = 2;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case 52:
                            if (liveSubCatSort.equals("4")) {
                                c3 = 3;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case 53:
                            if (liveSubCatSort.equals("5")) {
                                c3 = 4;
                                break;
                            }
                            c3 = 65535;
                            break;
                    }
                    switch (c3) {
                        case 0:
                            str3 = "SELECT  * FROM iptv_live_streams_m3u WHERE ( stream_type LIKE '%live%' OR stream_type LIKE '%radio%' ) AND ( categoryID ='" + str4 + "' ) AND ( " + KEY_USER_ID + " ='" + userID + "') LIMIT 1";
                            break;
                        case 1:
                            str3 = "SELECT  * FROM iptv_live_streams_m3u WHERE ( stream_type LIKE '%live%' OR stream_type LIKE '%radio%' ) AND categoryID ='" + str4 + "' AND " + KEY_USER_ID + " ='" + userID + "' ORDER BY " + "added" + " DESC LIMIT 1";
                            break;
                        case 2:
                            str3 = "SELECT  * FROM iptv_live_streams_m3u WHERE ( stream_type LIKE '%live%' OR stream_type LIKE '%radio%' ) AND categoryID ='" + str4 + "' AND " + KEY_USER_ID + " ='" + userID + "' ORDER BY " + "name" + " DESC LIMIT 1";
                            break;
                        case 3:
                            str3 = "SELECT  * FROM iptv_live_streams_m3u WHERE ( stream_type LIKE '%live%' OR stream_type LIKE '%radio%' ) AND categoryID ='" + str4 + "' AND " + KEY_USER_ID + " ='" + userID + "' ORDER BY cast(" + "num" + " as REAL) ASC LIMIT 1";
                            break;
                        case 4:
                            str3 = "SELECT  * FROM iptv_live_streams_m3u WHERE ( stream_type LIKE '%live%' OR stream_type LIKE '%radio%' ) AND categoryID ='" + str4 + "' AND " + KEY_USER_ID + " ='" + userID + "' ORDER BY cast(" + "num" + " as REAL) DESC LIMIT 1";
                            break;
                        default:
                            str3 = "SELECT  * FROM iptv_live_streams_m3u WHERE ( stream_type LIKE '%live%' OR stream_type LIKE '%radio%' ) AND categoryID ='" + str4 + "' AND " + KEY_USER_ID + " ='" + userID + "' ORDER BY " + "name" + " ASC LIMIT 1";
                            break;
                    }
                } else {
                    if (str4 != null) {
                        str4.equals("-1");
                    }
                    rawQuery2 = getReadableDatabase().rawQuery(str5, null);
                    if (!rawQuery2.moveToFirst()) {
                        do {
                            LiveStreamsDBModel liveStreamsDBModel = new LiveStreamsDBModel();
                            liveStreamsDBModel.setIdAuto(Integer.parseInt(rawQuery2.getString(0)));
                            liveStreamsDBModel.setNum(rawQuery2.getString(1));
                            liveStreamsDBModel.setName(rawQuery2.getString(2));
                            liveStreamsDBModel.setStreamType(rawQuery2.getString(3));
                            liveStreamsDBModel.setStreamId(rawQuery2.getString(4));
                            liveStreamsDBModel.setStreamIcon(rawQuery2.getString(5));
                            liveStreamsDBModel.setEpgChannelId(rawQuery2.getString(6));
                            liveStreamsDBModel.setAdded(rawQuery2.getString(7));
                            liveStreamsDBModel.setCategoryId(rawQuery2.getString(8));
                            liveStreamsDBModel.setCustomSid(rawQuery2.getString(9));
                            liveStreamsDBModel.setTvArchive(rawQuery2.getString(10));
                            liveStreamsDBModel.setDirectSource(rawQuery2.getString(11));
                            liveStreamsDBModel.setTvArchiveDuration(rawQuery2.getString(12));
                            liveStreamsDBModel.setTypeName(rawQuery2.getString(13));
                            liveStreamsDBModel.setCategoryName(rawQuery2.getString(14));
                            liveStreamsDBModel.setSeriesNo(rawQuery2.getString(15));
                            liveStreamsDBModel.setLive(rawQuery2.getString(16));
                            liveStreamsDBModel.setContaiinerExtension(rawQuery2.getString(17));
                            liveStreamsDBModel.setUrl(rawQuery2.getString(18));
                            arrayList.add(liveStreamsDBModel);
                        } while (rawQuery2.moveToNext());
                    }
                    rawQuery2.close();
                    return arrayList;
                }
            } else {
                switch (liveSubCatSort.hashCode()) {
                    case 48:
                        if (liveSubCatSort.equals(AppConst.PASSWORD_UNSET)) {
                            c4 = 0;
                            break;
                        }
                        c4 = 65535;
                        break;
                    case 49:
                        if (liveSubCatSort.equals("1")) {
                            c4 = 1;
                            break;
                        }
                        c4 = 65535;
                        break;
                    case 50:
                    default:
                        c4 = 65535;
                        break;
                    case 51:
                        if (liveSubCatSort.equals("3")) {
                            c4 = 2;
                            break;
                        }
                        c4 = 65535;
                        break;
                    case 52:
                        if (liveSubCatSort.equals("4")) {
                            c4 = 3;
                            break;
                        }
                        c4 = 65535;
                        break;
                    case 53:
                        if (liveSubCatSort.equals("5")) {
                            c4 = 4;
                            break;
                        }
                        c4 = 65535;
                        break;
                }
                switch (c4) {
                    case 0:
                        str3 = "SELECT  * FROM iptv_live_streams_m3u WHERE stream_type LIKE '%live%' AND user_id_referred='" + userID + "' LIMIT 1";
                        break;
                    case 1:
                        str3 = "SELECT  * FROM iptv_live_streams_m3u WHERE stream_type LIKE '%live%' AND user_id_referred='" + userID + "' ORDER BY " + "added" + " DESC LIMIT 1";
                        break;
                    case 2:
                        str3 = "SELECT  * FROM iptv_live_streams_m3u WHERE stream_type LIKE '%live%' AND user_id_referred='" + userID + "' ORDER BY " + "name" + " DESC LIMIT 1";
                        break;
                    case 3:
                        str3 = "SELECT  * FROM iptv_live_streams_m3u WHERE stream_type LIKE '%live%' AND user_id_referred='" + userID + "' ORDER BY cast(" + "num" + " as REAL) ASC LIMIT 1";
                        break;
                    case 4:
                        str3 = "SELECT  * FROM iptv_live_streams_m3u WHERE stream_type LIKE '%live%' AND user_id_referred='" + userID + "' ORDER BY cast(" + "num" + " as REAL) DESC LIMIT 1";
                        break;
                    default:
                        str3 = "SELECT  * FROM iptv_live_streams_m3u WHERE stream_type LIKE '%live%' AND user_id_referred='" + userID + "' ORDER BY " + "name" + " ASC LIMIT 1";
                        break;
                }
            }
            str5 = str3;
            try {
                rawQuery2 = getReadableDatabase().rawQuery(str5, null);
                if (!rawQuery2.moveToFirst()) {
                    rawQuery2.close();
                }
                rawQuery2.close();
                return arrayList;
            } catch (SQLiteDatabaseLockedException unused) {
                return null;
            } catch (SQLiteException unused2) {
                return null;
            }
        } else {
            if (str4 == null || !str4.equals(AppConst.PASSWORD_UNSET)) {
                if (str4 != null && !str4.equals("-1")) {
                    switch (liveSubCatSort.hashCode()) {
                        case 48:
                            if (liveSubCatSort.equals(AppConst.PASSWORD_UNSET)) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 49:
                            if (liveSubCatSort.equals("1")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 50:
                        default:
                            c = 65535;
                            break;
                        case 51:
                            if (liveSubCatSort.equals("3")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 52:
                            if (liveSubCatSort.equals("4")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 53:
                            if (liveSubCatSort.equals("5")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                    }
                    switch (c) {
                        case 0:
                            str2 = "SELECT  * FROM iptv_live_streams WHERE ( stream_type LIKE '%live%' OR stream_type LIKE '%radio%' ) AND ( categoryID ='" + str4 + "' ) LIMIT 1";
                            break;
                        case 1:
                            str2 = "SELECT  * FROM iptv_live_streams WHERE ( stream_type LIKE '%live%' OR stream_type LIKE '%radio%' ) AND categoryID ='" + str4 + "' ORDER BY " + "added" + " DESC LIMIT 1";
                            break;
                        case 2:
                            str2 = "SELECT  * FROM iptv_live_streams WHERE ( stream_type LIKE '%live%' OR stream_type LIKE '%radio%' ) AND categoryID ='" + str4 + "' ORDER BY " + "name" + " DESC LIMIT 1";
                            break;
                        case 3:
                            str2 = "SELECT  * FROM iptv_live_streams WHERE ( stream_type LIKE '%live%' OR stream_type LIKE '%radio%' ) AND categoryID ='" + str4 + "' ORDER BY cast(" + "num" + " as REAL) ASC LIMIT 1";
                            break;
                        case 4:
                            str2 = "SELECT  * FROM iptv_live_streams WHERE ( stream_type LIKE '%live%' OR stream_type LIKE '%radio%' ) AND categoryID ='" + str4 + "' ORDER BY cast(" + "num" + " as REAL) DESC LIMIT 1";
                            break;
                        default:
                            str2 = "SELECT  * FROM iptv_live_streams WHERE ( stream_type LIKE '%live%' OR stream_type LIKE '%radio%' ) AND categoryID ='" + str4 + "' ORDER BY " + "name" + " ASC LIMIT 1";
                            break;
                    }
                } else {
                    if (str4 == null) {
                        if (liveSubCatSort.equals(AppConst.PASSWORD_UNSET)) {
                            str5 = "SELECT  * FROM iptv_live_streams WHERE ( stream_type LIKE '%live%' OR stream_type LIKE '%radio%' ) AND ( categoryID ='" + "-2" + "' ) LIMIT 1";
                        } else if (liveSubCatSort.equals("1")) {
                            str5 = "SELECT  * FROM iptv_live_streams WHERE ( stream_type LIKE '%live%' OR stream_type LIKE '%radio%' ) AND categoryID ='" + "-2" + "' ORDER BY " + "added" + " DESC LIMIT 1";
                        } else if (liveSubCatSort.equals("3")) {
                            str5 = "SELECT  * FROM iptv_live_streams WHERE ( stream_type LIKE '%live%' OR stream_type LIKE '%radio%' ) AND categoryID ='" + "-2" + "' ORDER BY " + "name" + " DESC LIMIT 1";
                        } else {
                            str5 = "SELECT  * FROM iptv_live_streams WHERE ( stream_type LIKE '%live%' OR stream_type LIKE '%radio%' ) AND categoryID ='" + "-2" + "' ORDER BY " + "name" + " ASC LIMIT 1";
                        }
                    }
                    rawQuery = getReadableDatabase().rawQuery(str5, null);
                    if (!rawQuery.moveToFirst()) {
                        do {
                            LiveStreamsDBModel liveStreamsDBModel2 = new LiveStreamsDBModel();
                            liveStreamsDBModel2.setIdAuto(Integer.parseInt(rawQuery.getString(0)));
                            liveStreamsDBModel2.setNum(rawQuery.getString(1));
                            liveStreamsDBModel2.setName(rawQuery.getString(2));
                            liveStreamsDBModel2.setStreamType(rawQuery.getString(3));
                            liveStreamsDBModel2.setStreamId(rawQuery.getString(4));
                            liveStreamsDBModel2.setStreamIcon(rawQuery.getString(5));
                            liveStreamsDBModel2.setEpgChannelId(rawQuery.getString(6));
                            liveStreamsDBModel2.setAdded(rawQuery.getString(7));
                            liveStreamsDBModel2.setCategoryId(rawQuery.getString(8));
                            liveStreamsDBModel2.setCustomSid(rawQuery.getString(9));
                            liveStreamsDBModel2.setTvArchive(rawQuery.getString(10));
                            liveStreamsDBModel2.setDirectSource(rawQuery.getString(11));
                            liveStreamsDBModel2.setTvArchiveDuration(rawQuery.getString(12));
                            liveStreamsDBModel2.setTypeName(rawQuery.getString(13));
                            liveStreamsDBModel2.setCategoryName(rawQuery.getString(14));
                            liveStreamsDBModel2.setSeriesNo(rawQuery.getString(15));
                            liveStreamsDBModel2.setLive(rawQuery.getString(16));
                            liveStreamsDBModel2.setContaiinerExtension(rawQuery.getString(17));
                            arrayList.add(liveStreamsDBModel2);
                        } while (rawQuery.moveToNext());
                    }
                    rawQuery.close();
                    return arrayList;
                }
            } else {
                switch (liveSubCatSort.hashCode()) {
                    case 48:
                        if (liveSubCatSort.equals(AppConst.PASSWORD_UNSET)) {
                            c2 = 0;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 49:
                        if (liveSubCatSort.equals("1")) {
                            c2 = 1;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 50:
                    default:
                        c2 = 65535;
                        break;
                    case 51:
                        if (liveSubCatSort.equals("3")) {
                            c2 = 2;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 52:
                        if (liveSubCatSort.equals("4")) {
                            c2 = 3;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 53:
                        if (liveSubCatSort.equals("5")) {
                            c2 = 4;
                            break;
                        }
                        c2 = 65535;
                        break;
                }
                switch (c2) {
                    case 0:
                        str2 = "SELECT  * FROM iptv_live_streams WHERE stream_type LIKE '%live%' OR stream_type LIKE '%radio%' LIMIT 1";
                        break;
                    case 1:
                        str2 = "SELECT  * FROM iptv_live_streams WHERE stream_type LIKE '%live%' OR stream_type LIKE '%radio%' ORDER BY added DESC LIMIT 1";
                        break;
                    case 2:
                        str2 = "SELECT  * FROM iptv_live_streams WHERE stream_type LIKE '%live%' OR stream_type LIKE '%radio%' ORDER BY name DESC LIMIT 1";
                        break;
                    case 3:
                        str2 = "SELECT  * FROM iptv_live_streams WHERE stream_type LIKE '%live%' OR stream_type LIKE '%radio%' ORDER BY cast(num as REAL) ASC LIMIT 1";
                        break;
                    case 4:
                        str2 = "SELECT  * FROM iptv_live_streams WHERE stream_type LIKE '%live%' OR stream_type LIKE '%radio%' ORDER BY cast(num as REAL) DESC LIMIT 1";
                        break;
                    default:
                        str2 = "SELECT  * FROM iptv_live_streams WHERE stream_type LIKE '%live%' OR stream_type LIKE '%radio%' ORDER BY name ASC LIMIT 1";
                        break;
                }
            }
            str5 = str2;
            try {
                rawQuery = getReadableDatabase().rawQuery(str5, null);
                if (!rawQuery.moveToFirst()) {
                    rawQuery.close();
                }
                rawQuery.close();
                return arrayList;
            } catch (SQLiteDatabaseLockedException unused3) {
                return null;
            } catch (SQLiteException unused4) {
                return null;
            }
        }
    }

    public ArrayList<LiveStreamsDBModel> getCatDetailsWithStreamID(int i, String str) {
        String str2;
        ArrayList<LiveStreamsDBModel> arrayList = new ArrayList<>();
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            str2 = "SELECT  * FROM iptv_live_streams_m3u WHERE ( stream_type LIKE '%live%' OR stream_type LIKE '%radio%' ) AND ( url ='" + str + "' ) AND ( " + KEY_USER_ID + " ='" + userID + "') LIMIT 1";
        } else {
            str2 = "SELECT  * FROM iptv_live_streams WHERE ( stream_type LIKE '%live%' OR stream_type = 'radio_streams' ) AND ( stream_id='" + i + "' ) ";
        }
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery(str2, null);
            if (rawQuery.moveToFirst()) {
                do {
                    LiveStreamsDBModel liveStreamsDBModel = new LiveStreamsDBModel();
                    liveStreamsDBModel.setIdAuto(Integer.parseInt(rawQuery.getString(0)));
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
                    arrayList.add(liveStreamsDBModel);
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

    public ArrayList<LiveStreamsDBModel> getAllLiveStreamsArchive(String str) {
        String str2;
        String str3 = str;
        String liveSubCatSort = SharepreferenceDBHandler.getLiveSubCatSort(this.context);
        int i = 10;
        int i2 = 1;
        int i3 = 0;
        if (str3.equals(AppConst.PASSWORD_UNSET)) {
            ArrayList<LiveStreamsDBModel> arrayList = new ArrayList<>();
            if (liveSubCatSort.equals(AppConst.PASSWORD_UNSET)) {
                str2 = "SELECT  * FROM iptv_live_streams WHERE stream_type='live' AND tv_archive='1' AND epg_channel_id IS NOT NULL AND epg_channel_id !='' ";
            } else if (liveSubCatSort.equals("1")) {
                str2 = "SELECT  * FROM iptv_live_streams WHERE stream_type='live' AND tv_archive='1' AND epg_channel_id IS NOT NULL AND epg_channel_id !='' ORDER BY added DESC";
            } else {
                str2 = liveSubCatSort.equals("3") ? "SELECT  * FROM iptv_live_streams WHERE stream_type='live' AND tv_archive='1' AND epg_channel_id IS NOT NULL AND epg_channel_id !='' ORDER BY name DESC" : "SELECT  * FROM iptv_live_streams WHERE stream_type='live' AND tv_archive='1' AND epg_channel_id IS NOT NULL AND epg_channel_id !='' ORDER BY name ASC";
            }
            try {
                Cursor rawQuery = getReadableDatabase().rawQuery(str2, null);
                if (rawQuery.moveToFirst()) {
                    while (true) {
                        LiveStreamsDBModel liveStreamsDBModel = new LiveStreamsDBModel();
                        liveStreamsDBModel.setIdAuto(Integer.parseInt(rawQuery.getString(i3)));
                        liveStreamsDBModel.setNum(rawQuery.getString(1));
                        liveStreamsDBModel.setName(rawQuery.getString(2));
                        liveStreamsDBModel.setStreamType(rawQuery.getString(3));
                        liveStreamsDBModel.setStreamId(rawQuery.getString(4));
                        liveStreamsDBModel.setStreamIcon(rawQuery.getString(5));
                        liveStreamsDBModel.setEpgChannelId(rawQuery.getString(6));
                        liveStreamsDBModel.setAdded(rawQuery.getString(7));
                        liveStreamsDBModel.setCategoryId(rawQuery.getString(8));
                        liveStreamsDBModel.setCustomSid(rawQuery.getString(9));
                        liveStreamsDBModel.setTvArchive(rawQuery.getString(i));
                        liveStreamsDBModel.setDirectSource(rawQuery.getString(11));
                        liveStreamsDBModel.setTvArchiveDuration(rawQuery.getString(12));
                        liveStreamsDBModel.setTypeName(rawQuery.getString(13));
                        liveStreamsDBModel.setCategoryName(rawQuery.getString(14));
                        liveStreamsDBModel.setSeriesNo(rawQuery.getString(15));
                        liveStreamsDBModel.setLive(rawQuery.getString(16));
                        liveStreamsDBModel.setContaiinerExtension(rawQuery.getString(17));
                        arrayList.add(liveStreamsDBModel);
                        if (!rawQuery.moveToNext()) {
                            break;
                        }
                        i3 = 0;
                        i = 10;
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
            ArrayList<LiveStreamsDBModel> arrayList2 = new ArrayList<>();
            try {
                Cursor rawQuery2 = getReadableDatabase().rawQuery("SELECT  * FROM iptv_live_streams WHERE stream_type='live' AND tv_archive='1' AND categoryID ='" + str3 + "' AND " + KEY_EPG_CHANNEL_ID + " IS NOT NULL AND " + KEY_EPG_CHANNEL_ID + " !='' ORDER BY " + "added" + " DESC", null);
                if (rawQuery2.moveToFirst()) {
                    while (true) {
                        LiveStreamsDBModel liveStreamsDBModel2 = new LiveStreamsDBModel();
                        liveStreamsDBModel2.setIdAuto(Integer.parseInt(rawQuery2.getString(0)));
                        liveStreamsDBModel2.setNum(rawQuery2.getString(i2));
                        liveStreamsDBModel2.setName(rawQuery2.getString(2));
                        liveStreamsDBModel2.setStreamType(rawQuery2.getString(3));
                        liveStreamsDBModel2.setStreamId(rawQuery2.getString(4));
                        liveStreamsDBModel2.setStreamIcon(rawQuery2.getString(5));
                        liveStreamsDBModel2.setEpgChannelId(rawQuery2.getString(6));
                        liveStreamsDBModel2.setAdded(rawQuery2.getString(7));
                        liveStreamsDBModel2.setCategoryId(rawQuery2.getString(8));
                        liveStreamsDBModel2.setCustomSid(rawQuery2.getString(9));
                        liveStreamsDBModel2.setTvArchive(rawQuery2.getString(10));
                        liveStreamsDBModel2.setDirectSource(rawQuery2.getString(11));
                        liveStreamsDBModel2.setTvArchiveDuration(rawQuery2.getString(12));
                        liveStreamsDBModel2.setTypeName(rawQuery2.getString(13));
                        liveStreamsDBModel2.setCategoryName(rawQuery2.getString(14));
                        liveStreamsDBModel2.setSeriesNo(rawQuery2.getString(15));
                        liveStreamsDBModel2.setLive(rawQuery2.getString(16));
                        liveStreamsDBModel2.setContaiinerExtension(rawQuery2.getString(17));
                        arrayList2.add(liveStreamsDBModel2);
                        if (!rawQuery2.moveToNext()) {
                            break;
                        }
                        i2 = 1;
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

    public ArrayList<LiveStreamsDBModel> getAllLiveStreasWithSkyId(String str, String str2) {
        String str3;
        String str4 = str;
        String str5 = str2;
        ArrayList<LiveStreamsDBModel> arrayList = new ArrayList<>();
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        String str6 = "";
        if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            if (str4.equals(AppConst.PASSWORD_UNSET)) {
                str6 = "SELECT  * FROM iptv_live_streams_m3u WHERE stream_type ='" + str5 + "' AND " + KEY_USER_ID + "='" + userID + "'";
            } else if (!str4.equals("-2") && !str4.equals("-3") && !str4.equals("null")) {
                str6 = "SELECT  * FROM iptv_live_streams_m3u WHERE stream_type ='" + str5 + "' AND " + "categoryID" + " ='" + str4 + "' AND " + KEY_USER_ID + "='" + userID + "'";
            }
            try {
                Cursor rawQuery = getReadableDatabase().rawQuery(str6, null);
                if (rawQuery.moveToFirst()) {
                    do {
                        LiveStreamsDBModel liveStreamsDBModel = new LiveStreamsDBModel();
                        liveStreamsDBModel.setIdAuto(Integer.parseInt(rawQuery.getString(0)));
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
                        liveStreamsDBModel.setUrl(rawQuery.getString(18));
                        arrayList.add(liveStreamsDBModel);
                    } while (rawQuery.moveToNext());
                }
                rawQuery.close();
                return arrayList;
            } catch (SQLiteDatabaseLockedException unused) {
                return null;
            } catch (SQLiteException unused2) {
                return null;
            }
        } else {
            if (str4.equals(AppConst.PASSWORD_UNSET) && str5.equals(KEY_AVAIL_CHANNEL_LIVE)) {
                str3 = "SELECT  * FROM iptv_live_streams WHERE stream_type LIKE '%" + str5 + "%' OR " + KEY_STRESM_TYPE + " LIKE '%radio%'";
            } else if (str4.equals("-2") || str4.equals("-3")) {
                str3 = "SELECT  * FROM iptv_live_streams WHERE (stream_type LIKE '%" + str5 + "%' OR " + KEY_STRESM_TYPE + " LIKE '%radio%' ) AND " + "categoryID" + " ='" + str4 + "'";
            } else if (str4.equals("null")) {
                str3 = "SELECT  * FROM iptv_live_streams WHERE categoryID ='" + str4 + "'";
            } else {
                str3 = "SELECT  * FROM iptv_live_streams WHERE categoryID ='" + str4 + "'";
            }
            try {
                Cursor rawQuery2 = getReadableDatabase().rawQuery(str3, null);
                if (rawQuery2.moveToFirst()) {
                    do {
                        LiveStreamsDBModel liveStreamsDBModel2 = new LiveStreamsDBModel();
                        liveStreamsDBModel2.setIdAuto(Integer.parseInt(rawQuery2.getString(0)));
                        liveStreamsDBModel2.setNum(rawQuery2.getString(1));
                        liveStreamsDBModel2.setName(rawQuery2.getString(2));
                        liveStreamsDBModel2.setStreamType(rawQuery2.getString(3));
                        liveStreamsDBModel2.setStreamId(rawQuery2.getString(4));
                        liveStreamsDBModel2.setStreamIcon(rawQuery2.getString(5));
                        liveStreamsDBModel2.setEpgChannelId(rawQuery2.getString(6));
                        liveStreamsDBModel2.setAdded(rawQuery2.getString(7));
                        liveStreamsDBModel2.setCategoryId(rawQuery2.getString(8));
                        liveStreamsDBModel2.setCustomSid(rawQuery2.getString(9));
                        liveStreamsDBModel2.setTvArchive(rawQuery2.getString(10));
                        liveStreamsDBModel2.setDirectSource(rawQuery2.getString(11));
                        liveStreamsDBModel2.setTvArchiveDuration(rawQuery2.getString(12));
                        liveStreamsDBModel2.setTypeName(rawQuery2.getString(13));
                        liveStreamsDBModel2.setCategoryName(rawQuery2.getString(14));
                        liveStreamsDBModel2.setSeriesNo(rawQuery2.getString(15));
                        liveStreamsDBModel2.setLive(rawQuery2.getString(16));
                        liveStreamsDBModel2.setContaiinerExtension(rawQuery2.getString(17));
                        arrayList.add(liveStreamsDBModel2);
                    } while (rawQuery2.moveToNext());
                }
                rawQuery2.close();
                return arrayList;
            } catch (SQLiteDatabaseLockedException unused3) {
                return null;
            } catch (SQLiteException unused4) {
                return null;
            }
        }
    }

    public ArrayList<LiveStreamsDBModel> getAllLiveStreasWithCategoryId(String str, String str2) {
        String str3;
        String str4;
        String str5;
        String str6 = str;
        String str7 = str2;
        if (str7.equals(KEY_AVAIL_CHANNEL_LIVE)) {
            str3 = SharepreferenceDBHandler.getLiveSubCatSort(this.context);
        } else if (str7.equals("series")) {
            str3 = SharepreferenceDBHandler.getSeriesSubCatSort(this.context);
        } else {
            str3 = SharepreferenceDBHandler.getVODSubCatSort(this.context);
        }
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        String currentAPPType = SharepreferenceDBHandler.getCurrentAPPType(this.context);
        String str8 = "";
        ArrayList<LiveStreamsDBModel> arrayList = new ArrayList<>();
        char c = 65535;
        if (currentAPPType.equals(AppConst.TYPE_M3U)) {
            if (!str6.equals(AppConst.PASSWORD_UNSET)) {
                if (!str6.equals("-2") && !str6.equals("-3") && !str6.equals("null")) {
                    switch (str3.hashCode()) {
                        case 48:
                            if (str3.equals(AppConst.PASSWORD_UNSET)) {
                                c = 0;
                                break;
                            }
                            break;
                        case 49:
                            if (str3.equals("1")) {
                                c = 1;
                                break;
                            }
                            break;
                        case 51:
                            if (str3.equals("3")) {
                                c = 2;
                                break;
                            }
                            break;
                        case 52:
                            if (str3.equals("4")) {
                                c = 3;
                                break;
                            }
                            break;
                        case 53:
                            if (str3.equals("5")) {
                                c = 4;
                                break;
                            }
                            break;
                    }
                    switch (c) {
                        case 0:
                            str8 = "SELECT  * FROM iptv_live_streams_m3u WHERE stream_type ='" + str7 + "' AND " + "categoryID" + " ='" + str6 + "' AND " + KEY_USER_ID + "='" + userID + "'";
                            break;
                        case 1:
                            str8 = "SELECT  * FROM iptv_live_streams_m3u WHERE stream_type ='" + str7 + "' AND " + "categoryID" + " ='" + str6 + "' AND " + KEY_USER_ID + "='" + userID + "' ORDER BY " + "added" + " DESC";
                            break;
                        case 2:
                            str8 = "SELECT  * FROM iptv_live_streams_m3u WHERE stream_type ='" + str7 + "' AND " + "categoryID" + " ='" + str6 + "' AND " + KEY_USER_ID + "='" + userID + "' ORDER BY " + "name" + " DESC";
                            break;
                        case 3:
                            str8 = "SELECT  * FROM iptv_live_streams_m3u WHERE stream_type ='" + str7 + "' AND " + "categoryID" + " ='" + str6 + "' AND " + KEY_USER_ID + "='" + userID + "' ORDER BY cast(" + "num" + " as REAL) ASC";
                            break;
                        case 4:
                            str8 = "SELECT  * FROM iptv_live_streams_m3u WHERE stream_type ='" + str7 + "' AND " + "categoryID" + " ='" + str6 + "' AND " + KEY_USER_ID + "='" + userID + "' ORDER BY cast(" + "num" + " as REAL) DESC";
                            break;
                        default:
                            str8 = "SELECT  * FROM iptv_live_streams_m3u WHERE stream_type ='" + str7 + "' AND " + "categoryID" + " ='" + str6 + "' AND " + KEY_USER_ID + "='" + userID + "' ORDER BY " + "name" + " ASC";
                            break;
                    }
                }
            } else {
                switch (str3.hashCode()) {
                    case 48:
                        if (str3.equals(AppConst.PASSWORD_UNSET)) {
                            c = 0;
                            break;
                        }
                        break;
                    case 49:
                        if (str3.equals("1")) {
                            c = 1;
                            break;
                        }
                        break;
                    case 51:
                        if (str3.equals("3")) {
                            c = 2;
                            break;
                        }
                        break;
                    case 52:
                        if (str3.equals("4")) {
                            c = 3;
                            break;
                        }
                        break;
                    case 53:
                        if (str3.equals("5")) {
                            c = 4;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        str5 = "SELECT  * FROM iptv_live_streams_m3u WHERE stream_type ='" + str7 + "' AND " + KEY_USER_ID + "='" + userID + "'";
                        break;
                    case 1:
                        str5 = "SELECT  * FROM iptv_live_streams_m3u WHERE stream_type ='" + str7 + "' AND " + KEY_USER_ID + "='" + userID + "' ORDER BY " + "added" + " DESC";
                        break;
                    case 2:
                        str5 = "SELECT  * FROM iptv_live_streams_m3u WHERE stream_type ='" + str7 + "' AND " + KEY_USER_ID + "='" + userID + "' ORDER BY " + "name" + " DESC";
                        break;
                    case 3:
                        str5 = "SELECT  * FROM iptv_live_streams_m3u WHERE stream_type ='" + str7 + "' AND " + KEY_USER_ID + "='" + userID + "' ORDER BY cast(" + "num" + " as REAL) ASC";
                        break;
                    case 4:
                        str5 = "SELECT  * FROM iptv_live_streams_m3u WHERE stream_type ='" + str7 + "' AND " + KEY_USER_ID + "='" + userID + "' ORDER BY cast(" + "num" + " as REAL) DESC";
                        break;
                    default:
                        str5 = "SELECT  * FROM iptv_live_streams_m3u WHERE stream_type ='" + str7 + "' AND " + KEY_USER_ID + "='" + userID + "' ORDER BY " + "name" + " ASC";
                        break;
                }
                str8 = str5;
            }
            try {
                Cursor rawQuery = getReadableDatabase().rawQuery(str8, null);
                if (rawQuery.moveToFirst()) {
                    while (true) {
                        if (SharepreferenceDBHandler.getAsyncTaskStatus(this.context) == 1) {
                            SharepreferenceDBHandler.setAsyncTaskStatus(0, this.context);
                        } else {
                            LiveStreamsDBModel liveStreamsDBModel = new LiveStreamsDBModel();
                            liveStreamsDBModel.setIdAuto(Integer.parseInt(rawQuery.getString(0)));
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
                            liveStreamsDBModel.setUrl(rawQuery.getString(18));
                            arrayList.add(liveStreamsDBModel);
                            if (!rawQuery.moveToNext()) {
                            }
                        }
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
            if (!str6.equals(AppConst.PASSWORD_UNSET) || !str7.equals(KEY_AVAIL_CHANNEL_LIVE)) {
                if (!str6.equals(AppConst.PASSWORD_UNSET) || !str7.equals(AppConst.EVENT_TYPE_MOVIE)) {
                    if (!str6.equals("-2") && !str6.equals("-3")) {
                        if (!str6.equals("null")) {
                            switch (str3.hashCode()) {
                                case 48:
                                    if (str3.equals(AppConst.PASSWORD_UNSET)) {
                                        c = 0;
                                        break;
                                    }
                                    break;
                                case 49:
                                    if (str3.equals("1")) {
                                        c = 1;
                                        break;
                                    }
                                    break;
                                case 51:
                                    if (str3.equals("3")) {
                                        c = 2;
                                        break;
                                    }
                                    break;
                                case 52:
                                    if (str3.equals("4")) {
                                        c = 3;
                                        break;
                                    }
                                    break;
                                case 53:
                                    if (str3.equals("5")) {
                                        c = 4;
                                        break;
                                    }
                                    break;
                            }
                            switch (c) {
                                case 0:
                                    str4 = "SELECT  * FROM iptv_live_streams WHERE categoryID ='" + str6 + "'";
                                    break;
                                case 1:
                                    str4 = "SELECT  * FROM iptv_live_streams WHERE categoryID ='" + str6 + "' ORDER BY " + "added" + " DESC";
                                    break;
                                case 2:
                                    str4 = "SELECT  * FROM iptv_live_streams WHERE categoryID ='" + str6 + "' ORDER BY " + "name" + " DESC";
                                    break;
                                case 3:
                                    str4 = "SELECT  * FROM iptv_live_streams WHERE categoryID ='" + str6 + "' ORDER BY cast(" + "num" + " as REAL) ASC";
                                    break;
                                case 4:
                                    str4 = "SELECT  * FROM iptv_live_streams WHERE categoryID ='" + str6 + "' ORDER BY cast(" + "num" + " as REAL) DESC";
                                    break;
                                default:
                                    str4 = "SELECT  * FROM iptv_live_streams WHERE categoryID ='" + str6 + "' ORDER BY " + "name" + " ASC";
                                    break;
                            }
                        } else {
                            switch (str3.hashCode()) {
                                case 48:
                                    if (str3.equals(AppConst.PASSWORD_UNSET)) {
                                        c = 0;
                                        break;
                                    }
                                    break;
                                case 49:
                                    if (str3.equals("1")) {
                                        c = 1;
                                        break;
                                    }
                                    break;
                                case 51:
                                    if (str3.equals("3")) {
                                        c = 2;
                                        break;
                                    }
                                    break;
                                case 52:
                                    if (str3.equals("4")) {
                                        c = 3;
                                        break;
                                    }
                                    break;
                                case 53:
                                    if (str3.equals("5")) {
                                        c = 4;
                                        break;
                                    }
                                    break;
                            }
                            switch (c) {
                                case 0:
                                    str4 = "SELECT  * FROM iptv_live_streams WHERE categoryID ='" + str6 + "'";
                                    break;
                                case 1:
                                    str4 = "SELECT  * FROM iptv_live_streams WHERE categoryID ='" + str6 + "' ORDER BY " + "added" + " DESC";
                                    break;
                                case 2:
                                    str4 = "SELECT  * FROM iptv_live_streams WHERE categoryID ='" + str6 + "' ORDER BY " + "name" + " DESC";
                                    break;
                                case 3:
                                    str4 = "SELECT  * FROM iptv_live_streams WHERE categoryID ='" + str6 + "' ORDER BY cast(" + "num" + " as REAL) ASC";
                                    break;
                                case 4:
                                    str4 = "SELECT  * FROM iptv_live_streams WHERE categoryID ='" + str6 + "' ORDER BY cast(" + "num" + " as REAL) DESC";
                                    break;
                                default:
                                    str4 = "SELECT  * FROM iptv_live_streams WHERE categoryID ='" + str6 + "' ORDER BY " + "name" + " ASC";
                                    break;
                            }
                        }
                    } else {
                        switch (str3.hashCode()) {
                            case 48:
                                if (str3.equals(AppConst.PASSWORD_UNSET)) {
                                    c = 0;
                                    break;
                                }
                                break;
                            case 49:
                                if (str3.equals("1")) {
                                    c = 1;
                                    break;
                                }
                                break;
                            case 51:
                                if (str3.equals("3")) {
                                    c = 2;
                                    break;
                                }
                                break;
                            case 52:
                                if (str3.equals("4")) {
                                    c = 3;
                                    break;
                                }
                                break;
                            case 53:
                                if (str3.equals("5")) {
                                    c = 4;
                                    break;
                                }
                                break;
                        }
                        switch (c) {
                            case 0:
                                str4 = "SELECT  * FROM iptv_live_streams WHERE (stream_type LIKE '%" + str7 + "%' OR " + KEY_STRESM_TYPE + " LIKE '%radio%' ) AND " + "categoryID" + " ='" + str6 + "'";
                                break;
                            case 1:
                                str4 = "SELECT  * FROM iptv_live_streams WHERE (stream_type LIKE '%" + str7 + "%' OR " + KEY_STRESM_TYPE + " LIKE '%radio%' ) AND " + "categoryID" + " ='" + str6 + "' ORDER BY " + "added" + " DESC";
                                break;
                            case 2:
                                str4 = "SELECT  * FROM iptv_live_streams WHERE (stream_type LIKE '%" + str7 + "%' OR " + KEY_STRESM_TYPE + " LIKE '%radio%' ) AND " + "categoryID" + " ='" + str6 + "' ORDER BY " + "name" + " DESC";
                                break;
                            case 3:
                                str4 = "SELECT  * FROM iptv_live_streams WHERE (stream_type LIKE '%" + str7 + "%' OR " + KEY_STRESM_TYPE + " LIKE '%radio%' ) AND " + "categoryID" + " ='" + str6 + "' ORDER BY cast(" + "num" + " as REAL) ASC";
                                break;
                            case 4:
                                str4 = "SELECT  * FROM iptv_live_streams WHERE (stream_type LIKE '%" + str7 + "%' OR " + KEY_STRESM_TYPE + " LIKE '%radio%' ) AND " + "categoryID" + " ='" + str6 + "' ORDER BY cast(" + "num" + " as REAL) DESC";
                                break;
                            default:
                                str4 = "SELECT  * FROM iptv_live_streams WHERE stream_type LIKE '%" + str7 + "%'  AND " + "categoryID" + " ='" + str6 + "' ORDER BY " + "name" + " ASC";
                                break;
                        }
                    }
                } else {
                    switch (str3.hashCode()) {
                        case 48:
                            if (str3.equals(AppConst.PASSWORD_UNSET)) {
                                c = 0;
                                break;
                            }
                            break;
                        case 49:
                            if (str3.equals("1")) {
                                c = 1;
                                break;
                            }
                            break;
                        case 50:
                            if (str3.equals("2")) {
                                c = 2;
                                break;
                            }
                            break;
                        case 51:
                            if (str3.equals("3")) {
                                c = 3;
                                break;
                            }
                            break;
                    }
                    switch (c) {
                        case 0:
                            str4 = "SELECT  * FROM iptv_live_streams WHERE stream_type LIKE '%" + str7 + "%' ";
                            break;
                        case 1:
                            str4 = "SELECT  * FROM iptv_live_streams WHERE stream_type LIKE '%" + str7 + "%'  ORDER BY " + "added" + " DESC";
                            break;
                        case 2:
                            str4 = "SELECT  * FROM iptv_live_streams WHERE stream_type LIKE '%" + str7 + "%'  ORDER BY " + "name" + " ASC";
                            break;
                        case 3:
                            str4 = "SELECT  * FROM iptv_live_streams WHERE stream_type LIKE '%" + str7 + "%'  ORDER BY " + "name" + " DESC";
                            break;
                        default:
                            str4 = "SELECT  * FROM iptv_live_streams WHERE stream_type LIKE '%" + str7 + "%'  ORDER BY cast(" + "num" + " as REAL) ASC";
                            break;
                    }
                }
            } else {
                switch (str3.hashCode()) {
                    case 48:
                        if (str3.equals(AppConst.PASSWORD_UNSET)) {
                            c = 0;
                            break;
                        }
                        break;
                    case 49:
                        if (str3.equals("1")) {
                            c = 1;
                            break;
                        }
                        break;
                    case 51:
                        if (str3.equals("3")) {
                            c = 2;
                            break;
                        }
                        break;
                    case 52:
                        if (str3.equals("4")) {
                            c = 3;
                            break;
                        }
                        break;
                    case 53:
                        if (str3.equals("5")) {
                            c = 4;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        str4 = "SELECT  * FROM iptv_live_streams WHERE stream_type LIKE '%" + str7 + "%' OR " + KEY_STRESM_TYPE + " LIKE '%radio%'";
                        break;
                    case 1:
                        str4 = "SELECT  * FROM iptv_live_streams WHERE stream_type LIKE '%" + str7 + "%' OR " + KEY_STRESM_TYPE + " LIKE '%radio%' ORDER BY " + "added" + " DESC";
                        break;
                    case 2:
                        str4 = "SELECT  * FROM iptv_live_streams WHERE stream_type LIKE '%" + str7 + "%' OR " + KEY_STRESM_TYPE + " LIKE '%radio%' ORDER BY " + "name" + " DESC";
                        break;
                    case 3:
                        str4 = "SELECT  * FROM iptv_live_streams WHERE stream_type LIKE '%" + str7 + "%' OR " + KEY_STRESM_TYPE + " LIKE '%radio%' ORDER BY cast(" + "num" + " as REAL) ASC";
                        break;
                    case 4:
                        str4 = "SELECT  * FROM iptv_live_streams WHERE stream_type LIKE '%" + str7 + "%' OR " + KEY_STRESM_TYPE + " LIKE '%radio%' ORDER BY cast(" + "num" + " as REAL) DESC";
                        break;
                    default:
                        str4 = "SELECT  * FROM iptv_live_streams WHERE stream_type LIKE '%" + str7 + "%' OR " + KEY_STRESM_TYPE + " LIKE '%radio%' ORDER BY " + "name" + " ASC";
                        break;
                }
            }
            try {
                Cursor rawQuery2 = getReadableDatabase().rawQuery(str4, null);
                if (rawQuery2.moveToFirst()) {
                    do {
                        LiveStreamsDBModel liveStreamsDBModel2 = new LiveStreamsDBModel();
                        liveStreamsDBModel2.setIdAuto(Integer.parseInt(rawQuery2.getString(0)));
                        liveStreamsDBModel2.setNum(rawQuery2.getString(1));
                        liveStreamsDBModel2.setName(rawQuery2.getString(2));
                        liveStreamsDBModel2.setStreamType(rawQuery2.getString(3));
                        liveStreamsDBModel2.setStreamId(rawQuery2.getString(4));
                        liveStreamsDBModel2.setStreamIcon(rawQuery2.getString(5));
                        liveStreamsDBModel2.setEpgChannelId(rawQuery2.getString(6));
                        liveStreamsDBModel2.setAdded(rawQuery2.getString(7));
                        liveStreamsDBModel2.setCategoryId(rawQuery2.getString(8));
                        liveStreamsDBModel2.setCustomSid(rawQuery2.getString(9));
                        liveStreamsDBModel2.setTvArchive(rawQuery2.getString(10));
                        liveStreamsDBModel2.setDirectSource(rawQuery2.getString(11));
                        liveStreamsDBModel2.setTvArchiveDuration(rawQuery2.getString(12));
                        liveStreamsDBModel2.setTypeName(rawQuery2.getString(13));
                        liveStreamsDBModel2.setCategoryName(rawQuery2.getString(14));
                        liveStreamsDBModel2.setSeriesNo(rawQuery2.getString(15));
                        liveStreamsDBModel2.setLive(rawQuery2.getString(16));
                        liveStreamsDBModel2.setContaiinerExtension(rawQuery2.getString(17));
                        arrayList.add(liveStreamsDBModel2);
                    } while (rawQuery2.moveToNext());
                }
                rawQuery2.close();
                return arrayList;
            } catch (SQLiteDatabaseLockedException unused3) {
                return null;
            } catch (SQLiteException unused4) {
                return null;
            }
        }
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x04f5, code lost:
        if (r0.equals(com.nst.yourname.miscelleneious.common.AppConst.PASSWORD_UNSET) != false) goto L_0x04f9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x005d, code lost:
        if (r0.equals(com.nst.yourname.miscelleneious.common.AppConst.PASSWORD_UNSET) != false) goto L_0x0061;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x01ef, code lost:
        if (r0.equals(com.nst.yourname.miscelleneious.common.AppConst.PASSWORD_UNSET) != false) goto L_0x01f3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x03da, code lost:
        if (r0.equals(com.nst.yourname.miscelleneious.common.AppConst.PASSWORD_UNSET) != false) goto L_0x03de;
     */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x0635 A[Catch:{ SQLiteDatabaseLockedException -> 0x06d6, SQLiteException -> 0x06d5 }, LOOP:0: B:119:0x0635->B:120:0x06cf, LOOP_START] */
    public ArrayList<LiveStreamsDBModel> getAllM3UWithCategoryId(String str, Boolean bool) {
        Cursor rawQuery;
        String str2;
        String playlistSort = SharepreferenceDBHandler.getPlaylistSort(this.context);
        ArrayList<LiveStreamsDBModel> arrayList = new ArrayList<>();
        String str3 = "";
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        char c = 0;
        if (bool.booleanValue()) {
            if (str.equals(AppConst.PASSWORD_UNSET)) {
                switch (playlistSort.hashCode()) {
                    case 48:
                        break;
                    case 49:
                        if (playlistSort.equals("1")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 50:
                    default:
                        c = 65535;
                        break;
                    case 51:
                        if (playlistSort.equals("3")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 52:
                        if (playlistSort.equals("4")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 53:
                        if (playlistSort.equals("5")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        str2 = "SELECT  * FROM iptv_live_streams_m3u_all WHERE user_id_referred='" + userID + "' AND " + KEY_MOVE_TO + " NOT IN ('live','movie','series') OR " + KEY_MOVE_TO + " IS NULL";
                        break;
                    case 1:
                        str2 = "SELECT  * FROM iptv_live_streams_m3u_all WHERE user_id_referred='" + userID + "' AND " + KEY_MOVE_TO + " NOT IN ('live','movie','series') OR " + KEY_MOVE_TO + " IS NULL ORDER BY " + "added" + " DESC";
                        break;
                    case 2:
                        str2 = "SELECT  * FROM iptv_live_streams_m3u_all WHERE user_id_referred='" + userID + "' AND " + KEY_MOVE_TO + " NOT IN ('live','movie','series') OR " + KEY_MOVE_TO + " IS NULL ORDER BY " + "name" + " DESC";
                        break;
                    case 3:
                        str2 = "SELECT  * FROM iptv_live_streams_m3u_all WHERE user_id_referred='" + userID + "' AND " + KEY_MOVE_TO + " NOT IN ('live','movie','series') OR " + KEY_MOVE_TO + " IS NULL ORDER BY cast(" + "num" + " as REAL) ASC";
                        break;
                    case 4:
                        str2 = "SELECT  * FROM iptv_live_streams_m3u_all WHERE user_id_referred='" + userID + "' AND " + KEY_MOVE_TO + " NOT IN ('live','movie','series') OR " + KEY_MOVE_TO + " IS NULL ORDER BY cast(" + "num" + " as REAL) DESC";
                        break;
                    default:
                        str2 = "SELECT  * FROM iptv_live_streams_m3u_all WHERE user_id_referred='" + userID + "' AND " + KEY_MOVE_TO + " NOT IN ('live','movie','series') OR " + KEY_MOVE_TO + " IS NULL ORDER BY " + "name" + " ASC";
                        break;
                }
            } else {
                if (!str.equals("-2") && !str.equals("-3") && !str.equals("null")) {
                    switch (playlistSort.hashCode()) {
                        case 48:
                            break;
                        case 49:
                            if (playlistSort.equals("1")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 50:
                        default:
                            c = 65535;
                            break;
                        case 51:
                            if (playlistSort.equals("3")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 52:
                            if (playlistSort.equals("4")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 53:
                            if (playlistSort.equals("5")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                    }
                    switch (c) {
                        case 0:
                            str2 = "SELECT  * FROM iptv_live_streams_m3u_all WHERE categoryID ='" + str + "' AND " + KEY_USER_ID + "='" + userID + "' AND " + KEY_MOVE_TO + " NOT IN ('live','movie','series') OR " + KEY_MOVE_TO + " IS NULL";
                            break;
                        case 1:
                            str2 = "SELECT  * FROM iptv_live_streams_m3u_all WHERE categoryID ='" + str + "' AND " + KEY_USER_ID + "='" + userID + "' AND " + KEY_MOVE_TO + " NOT IN ('live','movie','series') OR " + KEY_MOVE_TO + " IS NULL ORDER BY " + "added" + " DESC";
                            break;
                        case 2:
                            str2 = "SELECT  * FROM iptv_live_streams_m3u_all WHERE categoryID ='" + str + "' AND " + KEY_USER_ID + "='" + userID + "' AND " + KEY_MOVE_TO + " NOT IN ('live','movie','series') OR " + KEY_MOVE_TO + " IS NULL ORDER BY " + "name" + " DESC";
                            break;
                        case 3:
                            str2 = "SELECT  * FROM iptv_live_streams_m3u_all WHERE categoryID ='" + str + "' AND " + KEY_USER_ID + "='" + userID + "' AND " + KEY_MOVE_TO + " NOT IN ('live','movie','series') OR " + KEY_MOVE_TO + " IS NULL ORDER BY cast(" + "num" + " as REAL) ASC";
                            break;
                        case 4:
                            str2 = "SELECT  * FROM iptv_live_streams_m3u_all WHERE categoryID ='" + str + "' AND " + KEY_USER_ID + "='" + userID + "' AND " + KEY_MOVE_TO + " NOT IN ('live','movie','series') OR " + KEY_MOVE_TO + " IS NULL ORDER BY cast(" + "num" + " as REAL) DESC";
                            break;
                        default:
                            str2 = "SELECT  * FROM iptv_live_streams_m3u_all WHERE categoryID ='" + str + "' AND " + KEY_USER_ID + "='" + userID + "' AND " + KEY_MOVE_TO + " NOT IN ('live','movie','series') OR " + KEY_MOVE_TO + " IS NULL ORDER BY " + "name" + " ASC";
                            break;
                    }
                }
                rawQuery = getReadableDatabase().rawQuery(str3, null);
                if (rawQuery.moveToFirst()) {
                    do {
                        LiveStreamsDBModel liveStreamsDBModel = new LiveStreamsDBModel();
                        liveStreamsDBModel.setNum(rawQuery.getString(1));
                        liveStreamsDBModel.setName(rawQuery.getString(2));
                        liveStreamsDBModel.setStreamType(rawQuery.getString(3));
                        liveStreamsDBModel.setStreamId(rawQuery.getString(4));
                        liveStreamsDBModel.setStreamIcon(rawQuery.getString(5));
                        liveStreamsDBModel.setEpgChannelId(rawQuery.getString(6));
                        liveStreamsDBModel.setAdded(rawQuery.getString(7));
                        liveStreamsDBModel.setCategoryId(rawQuery.getString(8));
                        liveStreamsDBModel.setCustomSid(rawQuery.getString(9));
                        liveStreamsDBModel.setDirectSource(rawQuery.getString(11));
                        liveStreamsDBModel.setTvArchiveDuration(rawQuery.getString(12));
                        liveStreamsDBModel.setTypeName(rawQuery.getString(13));
                        liveStreamsDBModel.setCategoryName(rawQuery.getString(14));
                        liveStreamsDBModel.setSeriesNo(rawQuery.getString(15));
                        liveStreamsDBModel.setLive(rawQuery.getString(16));
                        liveStreamsDBModel.setUrl(rawQuery.getString(17));
                        liveStreamsDBModel.setContaiinerExtension(rawQuery.getString(18));
                        arrayList.add(liveStreamsDBModel);
                    } while (rawQuery.moveToNext());
                }
                rawQuery.close();
                return arrayList;
            }
        } else if (str.equals(AppConst.PASSWORD_UNSET)) {
            switch (playlistSort.hashCode()) {
                case 48:
                    break;
                case 49:
                    if (playlistSort.equals("1")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 50:
                default:
                    c = 65535;
                    break;
                case 51:
                    if (playlistSort.equals("3")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 52:
                    if (playlistSort.equals("4")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 53:
                    if (playlistSort.equals("5")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    str2 = "SELECT  * FROM iptv_live_streams_m3u_all WHERE user_id_referred='" + userID + "'";
                    break;
                case 1:
                    str2 = "SELECT  * FROM iptv_live_streams_m3u_all WHERE user_id_referred='" + userID + "' ORDER BY " + "added" + " DESC";
                    break;
                case 2:
                    str2 = "SELECT  * FROM iptv_live_streams_m3u_all WHERE user_id_referred='" + userID + "' ORDER BY " + "name" + " DESC";
                    break;
                case 3:
                    str2 = "SELECT  * FROM iptv_live_streams_m3u_all WHERE user_id_referred='" + userID + "' ORDER BY cast(" + "num" + " as REAL) ASC";
                    break;
                case 4:
                    str2 = "SELECT  * FROM iptv_live_streams_m3u_all WHERE user_id_referred='" + userID + "' ORDER BY cast(" + "num" + " as REAL) DESC";
                    break;
                default:
                    str2 = "SELECT  * FROM iptv_live_streams_m3u_all WHERE user_id_referred='" + userID + "' ORDER BY " + "name" + " ASC";
                    break;
            }
        } else {
            if (!str.equals("-2") && !str.equals("-3") && !str.equals("null")) {
                switch (playlistSort.hashCode()) {
                    case 48:
                        break;
                    case 49:
                        if (playlistSort.equals("1")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 50:
                    default:
                        c = 65535;
                        break;
                    case 51:
                        if (playlistSort.equals("3")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 52:
                        if (playlistSort.equals("4")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 53:
                        if (playlistSort.equals("5")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        str3 = "SELECT  * FROM iptv_live_streams_m3u_all WHERE categoryID ='" + str + "' AND " + KEY_USER_ID + "='" + userID + "'";
                        break;
                    case 1:
                        str3 = "SELECT  * FROM iptv_live_streams_m3u_all WHERE categoryID ='" + str + "' AND " + KEY_USER_ID + "='" + userID + "' ORDER BY " + "added" + " DESC";
                        break;
                    case 2:
                        str3 = "SELECT  * FROM iptv_live_streams_m3u_all WHERE categoryID ='" + str + "' AND " + KEY_USER_ID + "='" + userID + "' ORDER BY " + "name" + " DESC";
                        break;
                    case 3:
                        str3 = "SELECT  * FROM iptv_live_streams_m3u_all WHERE categoryID ='" + str + "' AND " + KEY_USER_ID + "='" + userID + "' ORDER BY cast(" + "num" + " as REAL) ASC";
                        break;
                    case 4:
                        str3 = "SELECT  * FROM iptv_live_streams_m3u_all WHERE categoryID ='" + str + "' AND " + KEY_USER_ID + "='" + userID + "' ORDER BY cast(" + "num" + " as REAL) DESC";
                        break;
                    default:
                        str3 = "SELECT  * FROM iptv_live_streams_m3u_all WHERE categoryID ='" + str + "' AND " + KEY_USER_ID + "='" + userID + "' ORDER BY " + "name" + " ASC";
                        break;
                }
            }
            rawQuery = getReadableDatabase().rawQuery(str3, null);
            if (rawQuery.moveToFirst()) {
                rawQuery.close();
            }
            rawQuery.close();
            return arrayList;
        }
        str3 = str2;
        try {
            rawQuery = getReadableDatabase().rawQuery(str3, null);
            if (rawQuery.moveToFirst()) {
            }
            rawQuery.close();
            return arrayList;
        } catch (SQLiteDatabaseLockedException unused) {
            return null;
        } catch (SQLiteException unused2) {
            return null;
        }
    }

    public void addLiveCategories(Map<String, LiveStreamCategoriesCallback> map) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            for (LiveStreamCategoriesCallback liveStreamCategoriesCallback : map.values()) {
                if (liveStreamCategoriesCallback.getCategoryId() != null) {
                    contentValues.put(KEY_CATEGORY_ID_LIVE, liveStreamCategoriesCallback.getCategoryId());
                } else {
                    contentValues.put(KEY_CATEGORY_ID_LIVE, "");
                }
                if (liveStreamCategoriesCallback.getCategoryName() != null) {
                    contentValues.put(KEY_CATEGORY_NAME_LIVE, liveStreamCategoriesCallback.getCategoryName());
                } else {
                    contentValues.put(KEY_CATEGORY_NAME_LIVE, "");
                }
                if (liveStreamCategoriesCallback.getParentId() != null) {
                    contentValues.put(KEY_ID_PARENT_ID, liveStreamCategoriesCallback.getParentId());
                } else {
                    contentValues.put(KEY_ID_PARENT_ID, "");
                }
                writableDatabase.insert(TABLE_IPTV_LIVE_CATEGORY, null, contentValues);
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

    public void addLiveCategories(List<LiveStreamCategoriesCallback> list) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            int size = list.size();
            if (size != 0) {
                for (int i = 0; i < size; i++) {
                    contentValues.put(KEY_CATEGORY_ID_LIVE, list.get(i).getCategoryId());
                    contentValues.put(KEY_CATEGORY_NAME_LIVE, list.get(i).getCategoryName());
                    contentValues.put(KEY_ID_PARENT_ID, list.get(i).getParentId());
                    writableDatabase.insert(TABLE_IPTV_LIVE_CATEGORY, null, contentValues);
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

    public void addALLM3UCategories(Map<String, M3UCategoriesModel> map) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            for (M3UCategoriesModel m3UCategoriesModel : map.values()) {
                if (m3UCategoriesModel.getCategoryId() != null) {
                    contentValues.put("categoryID", m3UCategoriesModel.getCategoryId());
                } else {
                    contentValues.put("categoryID", "");
                }
                if (m3UCategoriesModel.getCategoryName() != null) {
                    contentValues.put(KEY_CATEGORY_NAME, m3UCategoriesModel.getCategoryName());
                } else {
                    contentValues.put(KEY_CATEGORY_NAME, "");
                }
                contentValues.put(KEY_USER_ID, Integer.valueOf(SharepreferenceDBHandler.getUserID(this.context)));
                writableDatabase.insert(TABLE_IPTV_ALL_M3U_CATEGORY, null, contentValues);
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

    public LiveStreamsDBModel getLiveStreamFavouriteRow(String str, String str2) {
        new ArrayList();
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  * FROM iptv_live_streams WHERE categoryID='" + str + "' AND " + KEY_STREAM_ID + "='" + str2 + "'", null);
            LiveStreamsDBModel liveStreamsDBModel = new LiveStreamsDBModel();
            if (rawQuery.moveToFirst()) {
                do {
                    liveStreamsDBModel.setIdAuto(Integer.parseInt(rawQuery.getString(0)));
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
                } while (rawQuery.moveToNext());
            }
            rawQuery.close();
            return liveStreamsDBModel;
        } catch (SQLiteDatabaseLockedException unused) {
            return null;
        } catch (SQLiteException unused2) {
            return null;
        }
    }

    public ArrayList<LiveStreamsDBModel> getM3UFavouriteRow(String str, String str2) {
        ArrayList<LiveStreamsDBModel> arrayList = new ArrayList<>();
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  * FROM iptv_live_streams_m3u_all WHERE categoryID='" + str + "' AND " + "url" + "='" + str2 + "' AND " + KEY_USER_ID + "='" + userID + "'", null);
            LiveStreamsDBModel liveStreamsDBModel = new LiveStreamsDBModel();
            if (rawQuery.moveToFirst()) {
                do {
                    liveStreamsDBModel.setNum(rawQuery.getString(1));
                    liveStreamsDBModel.setName(rawQuery.getString(2));
                    liveStreamsDBModel.setStreamType(rawQuery.getString(3));
                    liveStreamsDBModel.setStreamId(rawQuery.getString(4));
                    liveStreamsDBModel.setStreamIcon(rawQuery.getString(5));
                    liveStreamsDBModel.setEpgChannelId(rawQuery.getString(6));
                    liveStreamsDBModel.setAdded(rawQuery.getString(7));
                    liveStreamsDBModel.setCategoryId(rawQuery.getString(8));
                    liveStreamsDBModel.setCustomSid(rawQuery.getString(9));
                    liveStreamsDBModel.setDirectSource(rawQuery.getString(11));
                    liveStreamsDBModel.setTvArchiveDuration(rawQuery.getString(12));
                    liveStreamsDBModel.setTypeName(rawQuery.getString(13));
                    liveStreamsDBModel.setCategoryName(rawQuery.getString(14));
                    liveStreamsDBModel.setSeriesNo(rawQuery.getString(15));
                    liveStreamsDBModel.setLive(rawQuery.getString(16));
                    liveStreamsDBModel.setUrl(rawQuery.getString(17));
                    liveStreamsDBModel.setContaiinerExtension(rawQuery.getString(18));
                    arrayList.add(liveStreamsDBModel);
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

    public int getDBStatusCount() {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  * FROM iptv_db_update_status", null);
            int count = rawQuery.getCount();
            rawQuery.close();
            return count;
        } catch (SQLiteDatabaseLockedException unused) {
            return 0;
        } catch (SQLiteException unused2) {
            return 0;
        }
    }

    public DatabaseUpdatedStatusDBModel getdateDBStatus(String str, String str2) {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT * FROM iptv_db_update_status WHERE iptv_db_updated_status_category = '" + str + "' AND " + KEY_DB_CATEGORY_ID + " = '" + str2 + "'", null);
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

    public boolean updateDBStatus(String str, String str2, String str3) {
        try {
            String str4 = "SELECT rowid FROM iptv_db_update_status WHERE iptv_db_updated_status_category = '" + str + "' AND " + KEY_DB_CATEGORY_ID + " = '" + str2 + "'";
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            String str5 = "";
            Cursor rawQuery = writableDatabase.rawQuery(str4, null);
            if (rawQuery != null) {
                if (rawQuery.moveToFirst()) {
                    do {
                        str5 = String.valueOf(Integer.parseInt(rawQuery.getString(rawQuery.getColumnIndex(KEY_ID_DB_UPDATE_STATUS))));
                    } while (rawQuery.moveToNext());
                }
            } else if (this.context != null) {
                Toast.makeText(this.context, "cursor is null", 1).show();
            }
            if (!str5.equals("")) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(KEY_DB_STATUS_STATE, str3);
                writableDatabase.update(TABLE_DATABASE_UPDATE_STATUS, contentValues, "iptv_db_update_status_id= ?", new String[]{str5});
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

    public void makeEmptyChanelsRecord() {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.execSQL("delete from iptv_live_streams");
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public boolean updateDBStatusAndDate(String str, String str2, String str3, String str4) {
        try {
            String str5 = "SELECT rowid FROM iptv_db_update_status WHERE iptv_db_updated_status_category = '" + str + "' AND " + KEY_DB_CATEGORY_ID + " = '" + str2 + "'";
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            String str6 = "";
            Cursor rawQuery = writableDatabase.rawQuery(str5, null);
            if (rawQuery != null) {
                if (rawQuery.moveToFirst()) {
                    do {
                        str6 = String.valueOf(Integer.parseInt(rawQuery.getString(rawQuery.getColumnIndex(KEY_ID_DB_UPDATE_STATUS))));
                    } while (rawQuery.moveToNext());
                }
            } else if (this.context != null) {
                Toast.makeText(this.context, "cursor is null", 1).show();
            }
            if (!str6.equals("")) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(KEY_DB_STATUS_STATE, str3);
                contentValues.put(KEY_DB_LAST_UPDATED_DATE, str4);
                writableDatabase.update(TABLE_DATABASE_UPDATE_STATUS, contentValues, "iptv_db_update_status_id= ?", new String[]{str6});
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

    public void makeEmptyLiveCategory() {
        try {
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.execSQL("delete from iptv_live_category");
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void makeEmptyMovieCategory() {
        try {
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.execSQL("delete from iptv_movie_category");
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void makeEmptyEPG() {
        try {
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.execSQL("delete from epg");
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void addDBUpdatedStatus(DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModel) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_DB_STATUS_STATE, databaseUpdatedStatusDBModel.getDbUpadatedStatusState());
            contentValues.put(KEY_DB_LAST_UPDATED_DATE, databaseUpdatedStatusDBModel.getDbLastUpdatedDate());
            contentValues.put(KEY_DB_CATEGORY, databaseUpdatedStatusDBModel.getDbCategory());
            contentValues.put(KEY_DB_CATEGORY_ID, databaseUpdatedStatusDBModel.getGetDbCategoryID());
            writableDatabase.insert(TABLE_DATABASE_UPDATE_STATUS, null, contentValues);
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void makeEmptyAllTablesRecords() {
        try {
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.execSQL("delete from iptv_live_category");
            writableDatabase.execSQL("delete from iptv_movie_category");
            writableDatabase.execSQL("delete from epg");
            writableDatabase.execSQL("delete from iptv_live_streams");
            writableDatabase.execSQL("delete from iptv_db_update_status");
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void removeAvailableChannelM3U(String str) {
        try {
            int userID = SharepreferenceDBHandler.getUserID(this.context);
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (str.equals(AppConst.PASSWORD_UNSET)) {
                writableDatabase.execSQL("DELETE FROM iptv_live_streams_m3u WHERE user_id_referred='" + userID + "'");
            } else {
                writableDatabase.execSQL("DELETE FROM iptv_live_streams_m3u WHERE categoryID ='" + str + "' AND " + KEY_USER_ID + "='" + userID + "'");
            }
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void removeAvailableChannelM3UUrl(String str, String str2) {
        try {
            int userID = SharepreferenceDBHandler.getUserID(this.context);
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.execSQL("DELETE FROM iptv_live_streams_m3u WHERE categoryID ='" + str + "' AND " + KEY_USER_ID + "='" + userID + "' AND " + "url" + "='" + str2 + "'");
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void removeLiveCategoriesM3U(String str) {
        try {
            int userID = SharepreferenceDBHandler.getUserID(this.context);
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (str.equals(AppConst.PASSWORD_UNSET)) {
                writableDatabase.execSQL("DELETE FROM iptv_live_category_m3u WHERE user_id_referred='" + userID + "'");
            } else {
                writableDatabase.execSQL("DELETE FROM iptv_live_category_m3u WHERE categoryID ='" + str + "' AND " + KEY_USER_ID + "='" + userID + "'");
            }
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void removeMovieCategoriesM3U(String str) {
        try {
            int userID = SharepreferenceDBHandler.getUserID(this.context);
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (str.equals(AppConst.PASSWORD_UNSET)) {
                writableDatabase.execSQL("DELETE FROM iptv_movie_category_m3u WHERE user_id_referred='" + userID + "'");
            } else {
                writableDatabase.execSQL("DELETE FROM iptv_movie_category_m3u WHERE categoryID ='" + str + "' AND " + KEY_USER_ID + "='" + userID + "'");
            }
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void removeSeriesCategoriesM3U(String str) {
        try {
            int userID = SharepreferenceDBHandler.getUserID(this.context);
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (str.equals(AppConst.PASSWORD_UNSET)) {
                writableDatabase.execSQL("DELETE FROM iptv_series_category_m3u WHERE user_id_referred='" + userID + "'");
            } else {
                writableDatabase.execSQL("DELETE FROM iptv_series_category_m3u WHERE categoryID ='" + str + "' AND " + KEY_USER_ID + "='" + userID + "'");
            }
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void makeEmptyLiveStreams() {
        try {
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.execSQL("delete from iptv_live_streams WHERE (stream_type LIKE '%live%' OR stream_type LIKE '%radio%' )");
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void makeEmptyVODStreams() {
        try {
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.execSQL("delete from iptv_live_streams WHERE stream_type LIKE '%movie%'");
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void makeEmptyAllChannelsVODTablesRecords() {
        try {
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.execSQL("delete from iptv_live_category");
            writableDatabase.execSQL("delete from iptv_movie_category");
            writableDatabase.execSQL("delete from iptv_live_streams");
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public ArrayList<PasswordStatusDBModel> getAllPasswordStatus(int i) {
        ArrayList<PasswordStatusDBModel> arrayList = new ArrayList<>();
        String str = SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U) ? TABLE_IPTV_PASSWORD_STATUS_M3U : TABLE_IPTV_PASSWORD_STATUS;
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT * FROM " + str + " WHERE " + KEY_USER_ID + "=" + i + "", null);
            if (rawQuery.moveToFirst()) {
                do {
                    PasswordStatusDBModel passwordStatusDBModel = new PasswordStatusDBModel();
                    passwordStatusDBModel.setIdPaswordStaus(Integer.parseInt(rawQuery.getString(0)));
                    passwordStatusDBModel.setPasswordStatusCategoryId(rawQuery.getString(1));
                    passwordStatusDBModel.setPasswordStatusUserDetail(rawQuery.getString(2));
                    passwordStatusDBModel.setPasswordStatus(rawQuery.getString(3));
                    arrayList.add(passwordStatusDBModel);
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

    public void addPassword(PasswordDBModel passwordDBModel) {
        String currentAPPType = SharepreferenceDBHandler.getCurrentAPPType(this.context);
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_PASSWORD_USER_DETAIL, passwordDBModel.getUserDetail());
            contentValues.put("password", passwordDBModel.getUserPassword());
            contentValues.put(KEY_USER_ID, Integer.valueOf(passwordDBModel.getUserId()));
            if (currentAPPType.equals(AppConst.TYPE_M3U)) {
                writableDatabase.insert(TABLE_IPTV_PASSWORD_M3U, null, contentValues);
            } else {
                writableDatabase.insert(TABLE_IPTV_PASSWORD, null, contentValues);
            }
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void addPasswordStatus(PasswordStatusDBModel passwordStatusDBModel) {
        String str = SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U) ? TABLE_IPTV_PASSWORD_STATUS_M3U : TABLE_IPTV_PASSWORD_STATUS;
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_PASSWORD_STATUS_CAT_ID, passwordStatusDBModel.getPasswordStatusCategoryId());
            contentValues.put(KEY_USER_DETAIL, passwordStatusDBModel.getPasswordStatusUserDetail());
            contentValues.put(KEY_PASSWORD_STATUS, passwordStatusDBModel.getPasswordStatus());
            contentValues.put(KEY_USER_ID, Integer.valueOf(passwordStatusDBModel.getUserID()));
            writableDatabase.insert(str, null, contentValues);
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public PasswordStatusDBModel getPasswordStatus(String str, String str2, int i) {
        String str3 = SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U) ? TABLE_IPTV_PASSWORD_STATUS_M3U : TABLE_IPTV_PASSWORD_STATUS;
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT * FROM " + str3 + " WHERE " + KEY_USER_DETAIL + " = '" + str + "' AND " + KEY_PASSWORD_STATUS_CAT_ID + " = '" + str2 + "' AND " + KEY_USER_ID + " = " + i + "", null);
            PasswordStatusDBModel passwordStatusDBModel = new PasswordStatusDBModel();
            if (rawQuery.moveToFirst()) {
                do {
                    passwordStatusDBModel.setIdPaswordStaus(Integer.parseInt(rawQuery.getString(0)));
                    passwordStatusDBModel.setPasswordStatusCategoryId(rawQuery.getString(1));
                    passwordStatusDBModel.setPasswordStatusUserDetail(rawQuery.getString(2));
                    passwordStatusDBModel.setPasswordStatus(rawQuery.getString(3));
                } while (rawQuery.moveToNext());
            }
            rawQuery.close();
            return passwordStatusDBModel;
        } catch (SQLiteDatabaseLockedException unused) {
            return null;
        } catch (SQLiteException unused2) {
            return null;
        }
    }

    public boolean updatePasswordStatus(String str, String str2, String str3, int i) {
        String str4 = SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U) ? TABLE_IPTV_PASSWORD_STATUS_M3U : TABLE_IPTV_PASSWORD_STATUS;
        try {
            String str5 = "SELECT rowid FROM " + str4 + " WHERE " + KEY_USER_DETAIL + " = '" + str + "' AND " + KEY_PASSWORD_STATUS_CAT_ID + " = '" + str2 + "' AND " + KEY_USER_ID + " =" + i + "";
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            String str6 = "";
            Cursor rawQuery = writableDatabase.rawQuery(str5, null);
            if (rawQuery != null) {
                if (rawQuery.moveToFirst()) {
                    do {
                        str6 = String.valueOf(Integer.parseInt(rawQuery.getString(rawQuery.getColumnIndex(KEY_ID_PASWORD_STATUS))));
                    } while (rawQuery.moveToNext());
                }
            } else if (this.context != null) {
                Toast.makeText(this.context, "cursor is null", 1).show();
            }
            if (!str6.equals("")) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(KEY_PASSWORD_STATUS, str3);
                writableDatabase.update(str4, contentValues, "id_password_status= ?", new String[]{str6});
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

    public ArrayList<PasswordDBModel> getAllPassword(int i) {
        String str;
        ArrayList<PasswordDBModel> arrayList = new ArrayList<>();
        if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            str = "SELECT * FROM iptv_password_table_m3u WHERE user_id_referred=" + i + "";
        } else {
            str = "SELECT * FROM iptv_password_table WHERE user_id_referred=" + i + "";
        }
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery(str, null);
            if (rawQuery.moveToFirst()) {
                do {
                    PasswordDBModel passwordDBModel = new PasswordDBModel();
                    passwordDBModel.setId(Integer.parseInt(rawQuery.getString(0)));
                    passwordDBModel.setUserDetail(rawQuery.getString(1));
                    passwordDBModel.setUserPassword(rawQuery.getString(2));
                    arrayList.add(passwordDBModel);
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

    public boolean upDatePassword(String str, String str2, int i) {
        String str3 = SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U) ? TABLE_IPTV_PASSWORD_M3U : TABLE_IPTV_PASSWORD;
        try {
            String str4 = "SELECT rowid FROM " + str3 + " WHERE " + KEY_PASSWORD_USER_DETAIL + " = '" + str + "' AND " + KEY_USER_ID + " = " + i + "";
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            String str5 = "";
            Cursor rawQuery = writableDatabase.rawQuery(str4, null);
            if (rawQuery != null) {
                if (rawQuery.moveToFirst()) {
                    do {
                        str5 = String.valueOf(Integer.parseInt(rawQuery.getString(rawQuery.getColumnIndex(KEY_ID_PASWORD))));
                    } while (rawQuery.moveToNext());
                }
            } else if (this.context != null) {
                Toast.makeText(this.context, "cursor is null", 1).show();
            }
            if (!str5.equals("")) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("password", str2);
                writableDatabase.update(str3, contentValues, "id_password= ?", new String[]{str5});
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

    public void makeEmptyStatus() {
        try {
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.execSQL("delete from iptv_db_update_status");
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public int getParentalStatusCount(int i) {
        String str = SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U) ? TABLE_IPTV_PASSWORD_STATUS_M3U : TABLE_IPTV_PASSWORD_STATUS;
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  * FROM " + str + " WHERE " + KEY_USER_ID + "=" + i + "", null);
            int count = rawQuery.getCount();
            rawQuery.close();
            return count;
        } catch (SQLiteDatabaseLockedException unused) {
            return 0;
        } catch (SQLiteException unused2) {
            return 0;
        }
    }

    public int getMovieCategoryCount() {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  * FROM iptv_movie_category", null);
            int count = rawQuery.getCount();
            rawQuery.close();
            return count;
        } catch (SQLiteDatabaseLockedException unused) {
            return 0;
        } catch (SQLiteException unused2) {
            return 0;
        }
    }

    public int getLiveCategoryCount() {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  * FROM iptv_live_category", null);
            int count = rawQuery.getCount();
            rawQuery.close();
            return count;
        } catch (SQLiteDatabaseLockedException unused) {
            return 0;
        } catch (SQLiteException unused2) {
            return 0;
        }
    }

    public ArrayList<LiveStreamCategoryIdDBModel> getMovieCategoriesinRange(int i, int i2) {
        ArrayList<LiveStreamCategoryIdDBModel> arrayList = new ArrayList<>();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        readableDatabase.beginTransaction();
        try {
            Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM iptv_movie_category WHERE id_movie>=" + i + " AND " + KEY_ID_MOVIE + "<=" + i2, null);
            if (rawQuery.moveToFirst()) {
                do {
                    LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
                    liveStreamCategoryIdDBModel.setId(Integer.parseInt(rawQuery.getString(0)));
                    liveStreamCategoryIdDBModel.setLiveStreamCategoryID(rawQuery.getString(1));
                    liveStreamCategoryIdDBModel.setLiveStreamCategoryName(rawQuery.getString(2));
                    liveStreamCategoryIdDBModel.setParentId(Integer.parseInt(rawQuery.getString(3)));
                    arrayList.add(liveStreamCategoryIdDBModel);
                } while (rawQuery.moveToNext());
            }
            rawQuery.close();
            readableDatabase.setTransactionSuccessful();
            readableDatabase.endTransaction();
            return arrayList;
        } catch (SQLiteDatabaseLockedException unused) {
            return null;
        } catch (SQLiteException unused2) {
            return null;
        }
    }

    public ArrayList<LiveStreamCategoryIdDBModel> getLiveCategoriesinRange(int i, int i2) {
        ArrayList<LiveStreamCategoryIdDBModel> arrayList = new ArrayList<>();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        readableDatabase.beginTransaction();
        try {
            Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM iptv_live_category WHERE id_live>=" + i + " AND " + KEY_ID_LIVE + "<=" + i2, null);
            if (rawQuery.moveToFirst()) {
                do {
                    LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
                    liveStreamCategoryIdDBModel.setId(Integer.parseInt(rawQuery.getString(0)));
                    liveStreamCategoryIdDBModel.setLiveStreamCategoryID(rawQuery.getString(1));
                    liveStreamCategoryIdDBModel.setLiveStreamCategoryName(rawQuery.getString(2));
                    liveStreamCategoryIdDBModel.setParentId(Integer.parseInt(rawQuery.getString(3)));
                    arrayList.add(liveStreamCategoryIdDBModel);
                } while (rawQuery.moveToNext());
            }
            rawQuery.close();
            readableDatabase.setTransactionSuccessful();
            readableDatabase.endTransaction();
            return arrayList;
        } catch (SQLiteDatabaseLockedException unused) {
            return null;
        } catch (SQLiteException unused2) {
            return null;
        }
    }

    public int getUncatCount(String str, String str2) {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM iptv_live_streams WHERE ( stream_type LIKE '%" + str2 + "%' OR " + KEY_STRESM_TYPE + " LIKE '%radio%' )  AND " + "categoryID" + "='" + str + "'", null);
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

    public void deletePasswordDataForUser(int i) {
        String str;
        String str2;
        if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            str2 = TABLE_IPTV_PASSWORD_M3U;
            str = TABLE_IPTV_PASSWORD_STATUS_M3U;
        } else {
            str2 = TABLE_IPTV_PASSWORD;
            str = TABLE_IPTV_PASSWORD_STATUS;
        }
        try {
            this.db = getWritableDatabase();
            SQLiteDatabase sQLiteDatabase = this.db;
            sQLiteDatabase.delete(str2, "user_id_referred='" + i + "'", null);
            SQLiteDatabase sQLiteDatabase2 = this.db;
            sQLiteDatabase2.delete(str, "user_id_referred='" + i + "'", null);
            this.db.close();
        } catch (SQLiteException unused) {
        }
    }

    public void addToFavourite(FavouriteM3UModel favouriteM3UModel) {
        try {
            this.db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("url", favouriteM3UModel.getUrl());
            contentValues.put(KEY_USER_ID, Integer.valueOf(favouriteM3UModel.getUserID()));
            contentValues.put("name", favouriteM3UModel.getName());
            contentValues.put("categoryID", favouriteM3UModel.getCategoryID());
            this.db.insert(TABLE_IPTV_FAVOURITES_M3U, null, contentValues);
            this.db.close();
        } catch (SQLiteException unused) {
        }
    }

    public ArrayList<FavouriteM3UModel> checkFavourite(String str, int i) {
        ArrayList<FavouriteM3UModel> arrayList = new ArrayList<>();
        try {
            this.db = getReadableDatabase();
            Cursor rawQuery = this.db.rawQuery("SELECT  * FROM iptv_m3u_favourites WHERE url='" + str + "' AND " + KEY_USER_ID + "=" + i + "", null);
            if (rawQuery.moveToFirst()) {
                do {
                    FavouriteM3UModel favouriteM3UModel = new FavouriteM3UModel();
                    favouriteM3UModel.setId(Integer.parseInt(rawQuery.getString(0)));
                    favouriteM3UModel.setUrl(str);
                    favouriteM3UModel.setUserID(i);
                    favouriteM3UModel.setName(rawQuery.getString(3));
                    favouriteM3UModel.setCategoryID(rawQuery.getString(4));
                    arrayList.add(favouriteM3UModel);
                } while (rawQuery.moveToNext());
            }
            rawQuery.close();
            return arrayList;
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
            return arrayList;
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
            return arrayList;
        }
    }

    public void deleteFavourite(String str, int i) {
        try {
            this.db = getWritableDatabase();
            SQLiteDatabase sQLiteDatabase = this.db;
            sQLiteDatabase.delete(TABLE_IPTV_FAVOURITES_M3U, "url='" + str + "' AND " + KEY_USER_ID + "=" + i, null);
            this.db.close();
        } catch (SQLiteException unused) {
        }
    }

    public int getFavouriteCount(int i) {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM iptv_m3u_favourites WHERE  (SELECT iptv_live_streams_m3u_all.id FROM iptv_live_streams_m3u_all WHERE iptv_m3u_favourites.url = iptv_live_streams_m3u_all.url AND iptv_live_streams_m3u_all.user_id_referred=iptv_m3u_favourites.user_id_referred AND iptv_live_streams_m3u_all.move_to<>'live' AND iptv_live_streams_m3u_all.move_to<>'series' AND iptv_live_streams_m3u_all.move_to<>'movie' AND iptv_m3u_favourites.user_id_referred ='" + i + "')", null);
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

    public ArrayList<FavouriteM3UModel> getFavouriteM3U(String str) {
        String str2;
        String str3;
        int i;
        if (str.equals(KEY_AVAIL_CHANNEL_LIVE)) {
            str2 = SharepreferenceDBHandler.getLiveSubCatSort(this.context);
        } else if (str.equals("series")) {
            str2 = SharepreferenceDBHandler.getSeriesSubCatSort(this.context);
        } else {
            str2 = SharepreferenceDBHandler.getVODSubCatSort(this.context);
        }
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        ArrayList<FavouriteM3UModel> arrayList = new ArrayList<>();
        char c = 65535;
        switch (str2.hashCode()) {
            case 48:
                if (str2.equals(AppConst.PASSWORD_UNSET)) {
                    c = 0;
                    break;
                }
                break;
            case 50:
                if (str2.equals("2")) {
                    c = 1;
                    break;
                }
                break;
            case 51:
                if (str2.equals("3")) {
                    c = 2;
                    break;
                }
                break;
            case 52:
                if (str2.equals("4")) {
                    c = 3;
                    break;
                }
                break;
            case 53:
                if (str2.equals("5")) {
                    c = 4;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                str3 = "SELECT  * FROM iptv_m3u_favourites WHERE  (SELECT iptv_live_streams_m3u_all.id FROM iptv_live_streams_m3u_all WHERE iptv_m3u_favourites.url = iptv_live_streams_m3u_all.url AND iptv_live_streams_m3u_all.move_to='" + str + "' AND " + TABLE_IPTV_AVAILABLE_CHANNNELS_ALL_M3U + "." + KEY_USER_ID + "=" + TABLE_IPTV_FAVOURITES_M3U + "." + KEY_USER_ID + " AND " + TABLE_IPTV_FAVOURITES_M3U + "." + KEY_USER_ID + " ='" + userID + "')";
                break;
            case 1:
                str3 = "SELECT  * FROM iptv_m3u_favourites WHERE  (SELECT iptv_live_streams_m3u_all.id FROM iptv_live_streams_m3u_all WHERE iptv_m3u_favourites.url = iptv_live_streams_m3u_all.url AND iptv_live_streams_m3u_all.move_to='" + str + "' AND " + TABLE_IPTV_AVAILABLE_CHANNNELS_ALL_M3U + "." + KEY_USER_ID + "=" + TABLE_IPTV_FAVOURITES_M3U + "." + KEY_USER_ID + " AND " + TABLE_IPTV_FAVOURITES_M3U + "." + KEY_USER_ID + " ='" + userID + "') ORDER BY " + TABLE_IPTV_FAVOURITES_M3U + "." + "name" + " ASC ";
                break;
            case 2:
                str3 = "SELECT  * FROM iptv_m3u_favourites WHERE  (SELECT iptv_live_streams_m3u_all.id FROM iptv_live_streams_m3u_all WHERE iptv_m3u_favourites.url = iptv_live_streams_m3u_all.url AND iptv_live_streams_m3u_all.move_to='" + str + "' AND " + TABLE_IPTV_AVAILABLE_CHANNNELS_ALL_M3U + "." + KEY_USER_ID + "=" + TABLE_IPTV_FAVOURITES_M3U + "." + KEY_USER_ID + " AND " + TABLE_IPTV_FAVOURITES_M3U + "." + KEY_USER_ID + " ='" + userID + "') ORDER BY " + TABLE_IPTV_FAVOURITES_M3U + "." + "name" + " DESC ";
                break;
            case 3:
                str3 = "SELECT  * FROM iptv_m3u_favourites WHERE  (SELECT iptv_live_streams_m3u_all.id FROM iptv_live_streams_m3u_all WHERE iptv_m3u_favourites.url = iptv_live_streams_m3u_all.url AND iptv_live_streams_m3u_all.move_to='" + str + "' AND " + TABLE_IPTV_AVAILABLE_CHANNNELS_ALL_M3U + "." + KEY_USER_ID + "=" + TABLE_IPTV_FAVOURITES_M3U + "." + KEY_USER_ID + " AND " + TABLE_IPTV_FAVOURITES_M3U + "." + KEY_USER_ID + " ='" + userID + "' ORDER BY cast(" + TABLE_IPTV_AVAILABLE_CHANNNELS_ALL_M3U + "." + "num" + " as REAL) ASC)";
                break;
            case 4:
                str3 = "SELECT  * FROM iptv_m3u_favourites WHERE  (SELECT iptv_live_streams_m3u_all.id FROM iptv_live_streams_m3u_all WHERE iptv_m3u_favourites.url = iptv_live_streams_m3u_all.url AND iptv_live_streams_m3u_all.move_to='" + str + "' AND " + TABLE_IPTV_AVAILABLE_CHANNNELS_ALL_M3U + "." + KEY_USER_ID + "=" + TABLE_IPTV_FAVOURITES_M3U + "." + KEY_USER_ID + " AND " + TABLE_IPTV_FAVOURITES_M3U + "." + KEY_USER_ID + " ='" + userID + "' ORDER BY cast(" + TABLE_IPTV_AVAILABLE_CHANNNELS_ALL_M3U + "." + "num" + " as REAL) DESC)";
                break;
            default:
                str3 = "SELECT  * FROM iptv_m3u_favourites WHERE  (SELECT iptv_live_streams_m3u_all.id FROM iptv_live_streams_m3u_all WHERE iptv_m3u_favourites.url = iptv_live_streams_m3u_all.url AND iptv_live_streams_m3u_all.move_to='" + str + "' AND " + TABLE_IPTV_AVAILABLE_CHANNNELS_ALL_M3U + "." + KEY_USER_ID + "=" + TABLE_IPTV_FAVOURITES_M3U + "." + KEY_USER_ID + " AND " + TABLE_IPTV_FAVOURITES_M3U + "." + KEY_USER_ID + " ='" + userID + "')";
                break;
        }
        try {
            this.db = getReadableDatabase();
            Cursor rawQuery = this.db.rawQuery(str3, null);
            if (rawQuery.moveToFirst()) {
                do {
                    FavouriteM3UModel favouriteM3UModel = new FavouriteM3UModel();
                    try {
                        i = Integer.parseInt(rawQuery.getString(0));
                    } catch (NumberFormatException unused) {
                        i = 0;
                    }
                    favouriteM3UModel.setId(i);
                    favouriteM3UModel.setUrl(rawQuery.getString(1));
                    favouriteM3UModel.setUserID(Integer.parseInt(rawQuery.getString(2)));
                    favouriteM3UModel.setName(rawQuery.getString(3));
                    favouriteM3UModel.setCategoryID(rawQuery.getString(4));
                    arrayList.add(favouriteM3UModel);
                } while (rawQuery.moveToNext());
            }
            rawQuery.close();
            return arrayList;
        } catch (SQLiteDatabaseLockedException unused2) {
            return arrayList;
        } catch (SQLiteException unused3) {
            return arrayList;
        }
    }

    public int getFavouriteCountM3U(String str) {
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM iptv_m3u_favourites WHERE  (SELECT iptv_live_streams_m3u_all.id FROM iptv_live_streams_m3u_all WHERE iptv_m3u_favourites.url = iptv_live_streams_m3u_all.url AND iptv_live_streams_m3u_all.move_to='" + str + "' AND " + TABLE_IPTV_AVAILABLE_CHANNNELS_ALL_M3U + "." + KEY_USER_ID + "=" + TABLE_IPTV_FAVOURITES_M3U + "." + KEY_USER_ID + " AND " + TABLE_IPTV_FAVOURITES_M3U + "." + KEY_USER_ID + " ='" + userID + "')", null);
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

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public ArrayList<FavouriteM3UModel> getAllFavourites(int i) {
        char c;
        String str;
        ArrayList<FavouriteM3UModel> arrayList = new ArrayList<>();
        String playlistSort = SharepreferenceDBHandler.getPlaylistSort(this.context);
        new ArrayList();
        switch (playlistSort.hashCode()) {
            case 48:
                if (playlistSort.equals(AppConst.PASSWORD_UNSET)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 49:
            default:
                c = 65535;
                break;
            case 50:
                if (playlistSort.equals("2")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 51:
                if (playlistSort.equals("3")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                str = "SELECT  iptv_m3u_favourites.* FROM iptv_m3u_favourites WHERE  (SELECT iptv_live_streams_m3u_all.id FROM iptv_live_streams_m3u_all WHERE iptv_m3u_favourites.url = iptv_live_streams_m3u_all.url AND iptv_live_streams_m3u_all.user_id_referred=iptv_m3u_favourites.user_id_referred AND iptv_live_streams_m3u_all.move_to<>'live' AND iptv_live_streams_m3u_all.move_to<>'series' AND iptv_live_streams_m3u_all.move_to<>'movie' AND iptv_m3u_favourites.user_id_referred ='" + i + "')";
                break;
            case 1:
                str = "SELECT  iptv_m3u_favourites.* FROM iptv_m3u_favourites WHERE  (SELECT iptv_live_streams_m3u_all.id FROM iptv_live_streams_m3u_all WHERE iptv_m3u_favourites.url = iptv_live_streams_m3u_all.url AND iptv_live_streams_m3u_all.user_id_referred=iptv_m3u_favourites.user_id_referred AND iptv_live_streams_m3u_all.move_to<>'live' AND iptv_live_streams_m3u_all.move_to<>'series' AND iptv_live_streams_m3u_all.move_to<>'movie' AND iptv_m3u_favourites.user_id_referred ='" + i + "') ORDER BY " + TABLE_IPTV_FAVOURITES_M3U + "." + "name" + " ASC ";
                break;
            case 2:
                str = "SELECT  iptv_m3u_favourites.* FROM iptv_m3u_favourites WHERE  (SELECT iptv_live_streams_m3u_all.id FROM iptv_live_streams_m3u_all WHERE iptv_m3u_favourites.url = iptv_live_streams_m3u_all.url AND iptv_live_streams_m3u_all.user_id_referred=iptv_m3u_favourites.user_id_referred AND iptv_live_streams_m3u_all.move_to<>'live' AND iptv_live_streams_m3u_all.move_to<>'series' AND iptv_live_streams_m3u_all.move_to<>'movie' AND iptv_m3u_favourites.user_id_referred ='" + i + "') ORDER BY " + TABLE_IPTV_FAVOURITES_M3U + "." + "name" + " DESC ";
                break;
            default:
                str = "SELECT  iptv_m3u_favourites.* FROM iptv_m3u_favourites WHERE  (SELECT iptv_live_streams_m3u_all.id FROM iptv_live_streams_m3u_all WHERE iptv_m3u_favourites.url = iptv_live_streams_m3u_all.url AND iptv_live_streams_m3u_all.user_id_referred=iptv_m3u_favourites.user_id_referred AND iptv_live_streams_m3u_all.move_to<>'live' AND iptv_live_streams_m3u_all.move_to<>'series' AND iptv_live_streams_m3u_all.move_to<>'movie' AND iptv_m3u_favourites.user_id_referred ='" + i + "')";
                break;
        }
        try {
            this.db = getReadableDatabase();
            Cursor rawQuery = this.db.rawQuery(str, null);
            if (rawQuery.moveToFirst()) {
                do {
                    FavouriteM3UModel favouriteM3UModel = new FavouriteM3UModel();
                    favouriteM3UModel.setId(Integer.parseInt(rawQuery.getString(0)));
                    favouriteM3UModel.setUrl(rawQuery.getString(1));
                    favouriteM3UModel.setUserID(i);
                    favouriteM3UModel.setName(rawQuery.getString(3));
                    favouriteM3UModel.setCategoryID(rawQuery.getString(4));
                    arrayList.add(favouriteM3UModel);
                } while (rawQuery.moveToNext());
            }
            rawQuery.close();
            return arrayList;
        } catch (SQLiteDatabaseLockedException unused) {
            return arrayList;
        } catch (SQLiteException unused2) {
            return arrayList;
        } catch (Exception unused3) {
            return arrayList;
        }
    }

    public ArrayList<GetEpisdoeDetailsCallback> getM3USeriesStreams(String str) {
        String str2;
        if (str.equals(AppConst.PASSWORD_UNSET)) {
            str2 = "SELECT  * FROM series_m3u_streams";
        } else {
            str2 = "SELECT  * FROM series_m3u_streams WHERE series_m3u_stream_container_cat_id='" + str + "'";
        }
        ArrayList<GetEpisdoeDetailsCallback> arrayList = new ArrayList<>();
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery(str2, null);
            if (rawQuery.moveToFirst()) {
                do {
                    GetEpisdoeDetailsCallback getEpisdoeDetailsCallback = new GetEpisdoeDetailsCallback();
                    getEpisdoeDetailsCallback.setId(rawQuery.getString(1));
                    getEpisdoeDetailsCallback.setTitle(rawQuery.getString(2));
                    getEpisdoeDetailsCallback.setContainerExtension(rawQuery.getString(3));
                    getEpisdoeDetailsCallback.setImage(rawQuery.getString(4));
                    getEpisdoeDetailsCallback.setCategoryId(rawQuery.getString(5));
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

    public ArrayList<GetEpisdoeDetailsCallback> getM3UStreamsByLike(String str) {
        String str2 = "SELECT * FROM series_m3u_streams WHERE " + str;
        ArrayList<GetEpisdoeDetailsCallback> arrayList = new ArrayList<>();
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery(str2, null);
            if (rawQuery.moveToFirst()) {
                do {
                    GetEpisdoeDetailsCallback getEpisdoeDetailsCallback = new GetEpisdoeDetailsCallback();
                    getEpisdoeDetailsCallback.setId(rawQuery.getString(1));
                    getEpisdoeDetailsCallback.setTitle(rawQuery.getString(2));
                    getEpisdoeDetailsCallback.setContainerExtension(rawQuery.getString(3));
                    getEpisdoeDetailsCallback.setImage(rawQuery.getString(4));
                    getEpisdoeDetailsCallback.setCategoryId(rawQuery.getString(5));
                    arrayList.add(getEpisdoeDetailsCallback);
                } while (rawQuery.moveToNext());
            }
            rawQuery.close();
            return arrayList;
        } catch (SQLiteDatabaseLockedException unused) {
            return null;
        } catch (SQLiteException unused2) {
            return null;
        } catch (Exception unused3) {
            return null;
        }
    }

    public ArrayList<GetEpisdoeDetailsCallback> getM3UStreamsBySeason(String str) {
        String str2 = "SELECT  * FROM series_m3u_streams WHERE series_m3u_stream_title LIKE '%" + str + "%'";
        ArrayList<GetEpisdoeDetailsCallback> arrayList = new ArrayList<>();
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery(str2, null);
            if (rawQuery.moveToFirst()) {
                do {
                    GetEpisdoeDetailsCallback getEpisdoeDetailsCallback = new GetEpisdoeDetailsCallback();
                    getEpisdoeDetailsCallback.setId(rawQuery.getString(1));
                    getEpisdoeDetailsCallback.setTitle(rawQuery.getString(2));
                    getEpisdoeDetailsCallback.setContainerExtension(rawQuery.getString(3));
                    getEpisdoeDetailsCallback.setImage(rawQuery.getString(4));
                    getEpisdoeDetailsCallback.setCategoryId(rawQuery.getString(5));
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

    public void addM3USeriesStreams(Map<String, GetEpisdoeDetailsCallback> map) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            if (map.size() != 0) {
                for (GetEpisdoeDetailsCallback getEpisdoeDetailsCallback : map.values()) {
                    if (getEpisdoeDetailsCallback.getId() != null) {
                        contentValues.put(KEY_DB_SERIES_M3U_STREAM_ID, String.valueOf(getEpisdoeDetailsCallback.getId()));
                    } else {
                        contentValues.put(KEY_DB_SERIES_M3U_STREAM_ID, "");
                    }
                    if (getEpisdoeDetailsCallback.getTitle() != null) {
                        contentValues.put(KEY_DB_SERIES_M3U_STREAM_TITLE, getEpisdoeDetailsCallback.getTitle());
                    } else {
                        contentValues.put(KEY_DB_SERIES_M3U_STREAM_TITLE, "");
                    }
                    if (getEpisdoeDetailsCallback.getContainerExtension() != null) {
                        contentValues.put(KEY_DB_SERIES_M3U_STREAM_EXT, String.valueOf(getEpisdoeDetailsCallback.getContainerExtension()));
                    } else {
                        contentValues.put(KEY_DB_SERIES_M3U_STREAM_EXT, "");
                    }
                    if (getEpisdoeDetailsCallback.getImage() != null) {
                        contentValues.put(KEY_DB_SERIES_M3U_STREAM_IMAGE, String.valueOf(getEpisdoeDetailsCallback.getImage()));
                    } else {
                        contentValues.put(KEY_DB_SERIES_M3U_STREAM_IMAGE, "");
                    }
                    if (getEpisdoeDetailsCallback.getCategoryId() != null) {
                        contentValues.put(KEY_DB_SERIES_M3U_STREAM_CAT_ID, String.valueOf(getEpisdoeDetailsCallback.getCategoryId()));
                    } else {
                        contentValues.put(KEY_DB_SERIES_M3U_STREAM_CAT_ID, "");
                    }
                    writableDatabase.insert(TABLE_M3U_SERIES_STREAMS, null, contentValues);
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

    public int getAllM3UEpisodesStreamCount() {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM series_m3u_streams", null);
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

    public int getUncatCountM3U(String str) {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT * FROM series_m3u_streams WHERE series_m3u_stream_container_cat_id ='" + str + "'", null);
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

    public void makeEmptyAllTablesRecordsM3U() {
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.execSQL("DELETE FROM iptv_m3u_all_category WHERE user_id_referred='" + userID + "'");
            writableDatabase.execSQL("DELETE FROM iptv_live_streams_m3u_all WHERE user_id_referred='" + userID + "'");
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void makeEmptyAllTablesRecordsM3U(int i) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.execSQL("DELETE FROM iptv_movie_category_m3u WHERE user_id_referred='" + i + "'");
            writableDatabase.execSQL("DELETE FROM iptv_series_category_m3u WHERE user_id_referred='" + i + "'");
            writableDatabase.execSQL("DELETE FROM iptv_live_category_m3u WHERE user_id_referred='" + i + "'");
            writableDatabase.execSQL("DELETE FROM iptv_live_streams_m3u WHERE user_id_referred='" + i + "'");
            writableDatabase.execSQL("DELETE FROM iptv_live_streams_m3u_all WHERE user_id_referred='" + i + "'");
            writableDatabase.execSQL("DELETE FROM iptv_m3u_all_category WHERE user_id_referred='" + i + "'");
            writableDatabase.execSQL("DELETE FROM iptv_m3u_favourites WHERE user_id_referred='" + i + "'");
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void removeDuplicateEntriesM3U() {
        int i;
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        SQLiteDatabase readableDatabase = getReadableDatabase();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            Cursor rawQuery = readableDatabase.rawQuery("SELECT iptv_live_streams_m3u.id  FROM iptv_live_streams_m3u LEFT OUTER JOIN iptv_live_streams_m3u_all ON iptv_live_streams_m3u.url = iptv_live_streams_m3u_all.url AND iptv_live_streams_m3u.user_id_referred = iptv_live_streams_m3u_all.user_id_referred AND iptv_live_streams_m3u.categoryID = iptv_live_streams_m3u_all.categoryID GROUP BY iptv_live_streams_m3u.id HAVING iptv_live_streams_m3u.user_id_referred ='" + userID + "' AND " + TABLE_IPTV_AVAILABLE_CHANNNELS_ALL_M3U + "." + "url" + " IS NULL", null);
            while (rawQuery.moveToNext()) {
                try {
                    i = Integer.parseInt(rawQuery.getString(0));
                } catch (NumberFormatException unused) {
                    i = 0;
                }
                writableDatabase.delete(TABLE_IPTV_AVAILABLE_CHANNNELS_M3U, "id = ? AND user_id_referred = ?", new String[]{String.valueOf(i), String.valueOf(userID)});
            }
            rawQuery.close();
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
            writableDatabase.close();
            readableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused2) {
            writableDatabase.endTransaction();
            writableDatabase.close();
            readableDatabase.close();
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused3) {
            writableDatabase.endTransaction();
            writableDatabase.close();
            readableDatabase.close();
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (Exception unused4) {
            writableDatabase.endTransaction();
            writableDatabase.close();
            readableDatabase.close();
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void removeDuplicateFavEntriesM3U() {
        int i;
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        SQLiteDatabase readableDatabase = getReadableDatabase();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            Cursor rawQuery = readableDatabase.rawQuery("SELECT iptv_m3u_favourites.id  FROM iptv_m3u_favourites LEFT JOIN iptv_live_streams_m3u_all ON iptv_m3u_favourites.url = iptv_live_streams_m3u_all.url AND iptv_m3u_favourites.user_id_referred = iptv_live_streams_m3u_all.user_id_referred AND iptv_m3u_favourites.categoryID = iptv_live_streams_m3u_all.categoryID GROUP BY iptv_m3u_favourites.id HAVING iptv_m3u_favourites.user_id_referred ='" + userID + "' AND " + TABLE_IPTV_AVAILABLE_CHANNNELS_ALL_M3U + "." + "url" + " IS NULL", null);
            while (rawQuery.moveToNext()) {
                try {
                    i = Integer.parseInt(rawQuery.getString(0));
                } catch (NumberFormatException unused) {
                    i = 0;
                }
                writableDatabase.delete(TABLE_IPTV_FAVOURITES_M3U, "id = ? AND user_id_referred = ?", new String[]{String.valueOf(i), String.valueOf(userID)});
            }
            rawQuery.close();
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
            writableDatabase.close();
            readableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused2) {
            writableDatabase.endTransaction();
            writableDatabase.close();
            readableDatabase.close();
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused3) {
            writableDatabase.endTransaction();
            writableDatabase.close();
            readableDatabase.close();
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (Exception unused4) {
            writableDatabase.endTransaction();
            writableDatabase.close();
            readableDatabase.close();
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void updateMoveToStatusMapping(String str) {
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.beginTransaction();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        try {
            Cursor rawQuery = readableDatabase.rawQuery("SELECT iptv_live_streams_m3u_all.id  FROM iptv_live_streams_m3u_all LEFT OUTER JOIN iptv_live_streams_m3u ON iptv_live_streams_m3u.url = iptv_live_streams_m3u_all.url AND iptv_live_streams_m3u.user_id_referred = iptv_live_streams_m3u_all.user_id_referred AND iptv_live_streams_m3u.categoryID = iptv_live_streams_m3u_all.categoryID WHERE iptv_live_streams_m3u.url IS NOT NULL AND iptv_live_streams_m3u.user_id_referred ='" + userID + "' AND " + TABLE_IPTV_AVAILABLE_CHANNNELS_M3U + "." + KEY_STRESM_TYPE + " ='" + str + "' GROUP BY " + TABLE_IPTV_AVAILABLE_CHANNNELS_ALL_M3U + "." + "id" + " HAVING " + TABLE_IPTV_AVAILABLE_CHANNNELS_M3U + "." + KEY_STRESM_TYPE + " ='" + str + "'", null);
            while (rawQuery.moveToNext()) {
                String string = rawQuery.getString(0);
                ContentValues contentValues = new ContentValues();
                contentValues.put(KEY_MOVE_TO, str);
                writableDatabase.update(TABLE_IPTV_AVAILABLE_CHANNNELS_ALL_M3U, contentValues, "id = ? AND user_id_referred = ?", new String[]{string, String.valueOf(userID)});
            }
            rawQuery.close();
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
            writableDatabase.close();
            readableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            writableDatabase.endTransaction();
            writableDatabase.close();
            readableDatabase.close();
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            writableDatabase.endTransaction();
            writableDatabase.close();
            readableDatabase.close();
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (Exception unused3) {
            writableDatabase.endTransaction();
            writableDatabase.close();
            readableDatabase.close();
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public void addMultipleCategoriesM3U(ArrayList<LiveStreamCategoryIdDBModel> arrayList, String str) {
        String str2;
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            char c = 65535;
            int hashCode = str.hashCode();
            if (hashCode != -905838985) {
                if (hashCode != 3322092) {
                    if (hashCode == 104087344) {
                        if (str.equals(AppConst.EVENT_TYPE_MOVIE)) {
                            c = 1;
                        }
                    }
                } else if (str.equals(KEY_AVAIL_CHANNEL_LIVE)) {
                    c = 0;
                }
            } else if (str.equals("series")) {
                c = 2;
            }
            switch (c) {
                case 0:
                    str2 = TABLE_IPTV_LIVE_CATEGORY_M3U;
                    break;
                case 1:
                    str2 = TABLE_IPTV_MOVIE_CATEGORY_M3U;
                    break;
                case 2:
                    str2 = TABLE_IPTV_SERIES_CATEGORY_M3U;
                    break;
                default:
                    str2 = "";
                    break;
            }
            if (!str2.equals("")) {
                Iterator<LiveStreamCategoryIdDBModel> it = arrayList.iterator();
                while (it.hasNext()) {
                    LiveStreamCategoryIdDBModel next = it.next();
                    if (next.getLiveStreamCategoryID() != null) {
                        contentValues.put("categoryID", next.getLiveStreamCategoryID());
                    } else {
                        contentValues.put("categoryID", "");
                    }
                    if (next.getLiveStreamCategoryName() != null) {
                        contentValues.put(KEY_CATEGORY_NAME, next.getLiveStreamCategoryName());
                    } else {
                        contentValues.put(KEY_CATEGORY_NAME, "");
                    }
                    contentValues.put(KEY_USER_ID, Integer.valueOf(SharepreferenceDBHandler.getUserID(this.context)));
                    writableDatabase.insert(str2, null, contentValues);
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
    public void addAllAvailableChannel(PanelAvailableChannelsPojo panelAvailableChannelsPojo, String str) {
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
            contentValues.put(KEY_STRESM_TYPE, str);
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
                contentValues.put("added", panelAvailableChannelsPojo.getAdded());
            } else {
                contentValues.put("added", "");
            }
            if (panelAvailableChannelsPojo.getCategoryId() != null) {
                contentValues.put("categoryID", panelAvailableChannelsPojo.getCategoryId());
            } else {
                contentValues.put("categoryID", "");
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
            if (panelAvailableChannelsPojo.getMovieElapsedTime() != -1) {
                contentValues.put(KEY_MOVIE_ELAPSED_TIME, Long.valueOf(panelAvailableChannelsPojo.getMovieElapsedTime()));
            } else {
                contentValues.put(KEY_MOVIE_ELAPSED_TIME, /*(Integer)*/ -1);
            }
            if (panelAvailableChannelsPojo.getMovieDuration() != -1) {
                contentValues.put(KEY_MOVIE_DURTION, Long.valueOf(panelAvailableChannelsPojo.getMovieDuration()));
            } else {
                contentValues.put(KEY_MOVIE_DURTION,/* (Integer) */-1);
            }
            if (panelAvailableChannelsPojo.getUrl() != null) {
                contentValues.put("url", panelAvailableChannelsPojo.getUrl());
            } else {
                contentValues.put("url", "");
            }
            contentValues.put(KEY_USER_ID, Integer.valueOf(SharepreferenceDBHandler.getUserID(this.context)));
            writableDatabase.insert(TABLE_IPTV_RECENT_WATCHED_M3U, null, contentValues);
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }

    public int getRecentwatchCount(int i, String str) {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM iptv_recent_watched_m3u WHERE user_id_referred=" + i + " AND " + KEY_STRESM_TYPE + "='" + str + "'", null);
            rawQuery.moveToFirst();
            int i2 = rawQuery.getInt(0);
            rawQuery.close();
            return i2;
        } catch (SQLiteDatabaseLockedException unused) {
            return 0;
        } catch (SQLiteException unused2) {
            return 0;
        } catch (Exception unused3) {
            return 0;
        }
    }

    public void deleteRecentwatch(String str, String str2, String str3, int i) {
        this.db = getWritableDatabase();
        SQLiteDatabase sQLiteDatabase = this.db;
        sQLiteDatabase.delete(TABLE_IPTV_RECENT_WATCHED_M3U, "url='" + str + "'  AND " + KEY_STRESM_TYPE + "='" + str2 + "' AND " + KEY_USER_ID + "=" + i + "", null);
        this.db.close();
    }

    public void deleteALLRecentwatch(String str, int i) {
        this.db = getWritableDatabase();
        SQLiteDatabase sQLiteDatabase = this.db;
        sQLiteDatabase.delete(TABLE_IPTV_RECENT_WATCHED_M3U, "stream_type='" + str + "' AND " + KEY_USER_ID + "=" + i + "", null);
        this.db.close();
    }

    public boolean updateResumePlayerStatus(String str, String str2, boolean z, long j) {
        try {
            String str3 = "SELECT rowid FROM iptv_recent_watched_m3u WHERE stream_type = '" + str2 + "' AND " + KEY_USER_ID + " = '" + SharepreferenceDBHandler.getUserID(this.context) + "' AND " + "url" + " = '" + str + "'";
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
                writableDatabase.update(TABLE_IPTV_RECENT_WATCHED_M3U, contentValues, "id= ?", new String[]{str4});
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
            String str3 = "SELECT rowid FROM iptv_recent_watched_m3u WHERE stream_type = '" + str2 + "' AND " + KEY_USER_ID + " = '" + i + "' AND " + "url" + " = '" + str + "'";
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
                writableDatabase.update(TABLE_IPTV_RECENT_WATCHED_M3U, contentValues, "id= ?", new String[]{str4});
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
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM iptv_recent_watched_m3u WHERE url='" + str + "' AND " + KEY_USER_ID + "='" + i + "'", null);
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
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT * FROM iptv_recent_watched_m3u WHERE url = '" + str + "' AND " + KEY_USER_ID + " = '" + i + "'", null);
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
                    liveStreamsDBModel.setUrl(rawQuery.getString(18));
                    liveStreamsDBModel.setUserIdReffered(rawQuery.getInt(19));
                    liveStreamsDBModel.setMovieElapsedTime(rawQuery.getLong(20));
                    liveStreamsDBModel.setMovieDuraton(rawQuery.getLong(21));
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

    public ArrayList<LiveStreamsDBModel> getAllLiveStreasWithCategoryId(String str, int i, String str2) {
        String str3;
        ArrayList<LiveStreamsDBModel> arrayList = new ArrayList<>();
        if (str2.equals("getalldata")) {
            String vODSubCatSort = SharepreferenceDBHandler.getVODSubCatSort(this.context);
            if (vODSubCatSort.equals(AppConst.PASSWORD_UNSET)) {
                str3 = "SELECT  * FROM iptv_recent_watched_m3u WHERE user_id_referred=" + i + "";
            } else if (vODSubCatSort.equals("1")) {
                str3 = "SELECT  * FROM iptv_recent_watched_m3u WHERE user_id_referred=" + i + " ORDER BY " + "id" + " DESC";
            } else if (vODSubCatSort.equals("2")) {
                str3 = "SELECT  * FROM iptv_recent_watched_m3u WHERE user_id_referred=" + i + " ORDER BY " + "name" + " ASC";
            } else if (vODSubCatSort.equals("3")) {
                str3 = "SELECT  * FROM iptv_recent_watched_m3u WHERE user_id_referred=" + i + " ORDER BY " + "name" + " DESC";
            } else {
                str3 = "SELECT  * FROM iptv_recent_watched_m3u WHERE user_id_referred=" + i + "";
            }
        } else if (str2.equals("getOnedata")) {
            str3 = "SELECT  * FROM iptv_recent_watched_m3u WHERE user_id_referred=" + i + " ORDER BY " + "added" + " ASC LIMIT 1";
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
                    liveStreamsDBModel.setUrl(rawQuery.getString(18));
                    liveStreamsDBModel.setUserIdReffered(rawQuery.getInt(19));
                    liveStreamsDBModel.setMovieElapsedTime(rawQuery.getLong(20));
                    liveStreamsDBModel.setMovieDuraton(rawQuery.getLong(21));
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

    public boolean isExistFavourite(int i) {
        SharepreferenceDBHandler.getUserID(this.context);
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT * FROM iptv_live_streams WHERE stream_id='" + i + "'", null);
            String valueOf = String.valueOf(i);
            ArrayList arrayList = new ArrayList();
            if (rawQuery.moveToFirst()) {
                do {
                    arrayList.add(rawQuery.getString(4));
                } while (rawQuery.moveToNext());
            }
            if (arrayList.contains(valueOf)) {
                return true;
            }
            return false;
        } catch (SQLiteDatabaseLockedException unused) {
            return false;
        } catch (SQLiteException unused2) {
            return false;
        }
    }

    public int getUncatCountM3UALL(Boolean bool) {
        String str;
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        if (bool.booleanValue()) {
            str = "SELECT  COUNT(*) FROM iptv_live_streams_m3u_all WHERE categoryID='' AND user_id_referred='" + userID + "' AND " + KEY_MOVE_TO + " NOT IN ('live','movie','series') OR " + KEY_MOVE_TO + " IS NULL";
        } else {
            str = "SELECT  COUNT(*) FROM iptv_live_streams_m3u_all WHERE categoryID='' AND user_id_referred='" + userID + "'";
        }
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery(str, null);
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

    public int getUncatCountM3UByType(String str) {
        int userID = SharepreferenceDBHandler.getUserID(this.context);
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM iptv_live_streams_m3u WHERE categoryID='' AND user_id_referred='" + userID + "' AND " + KEY_STRESM_TYPE + "='" + str + "'", null);
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
}
