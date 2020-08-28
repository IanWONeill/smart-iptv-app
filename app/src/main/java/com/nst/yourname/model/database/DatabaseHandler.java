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
import com.nst.yourname.model.FavouriteDBModel;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "iptv_smarters_tv.db";
    private static final int DATABASE_VERSION = 3;
    private static final String KEY_CATEGORY_ID = "categoryID";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_NUM_LIVE_STREAMS = "num";
    private static final String KEY_STREAM_ID = "streamID";
    private static final String KEY_TYPE = "type";
    private static final String KEY_USER_ID = "user_id_referred";
    private static final String TABLE_IPTV_AVAILABLE_CHANNNELS = "iptv_live_streams";
    private static final String TABLE_IPTV_FAVOURITES = "iptv_favourites";
    private String ALTER_PRODUCTS_TABLE_1 = "ALTER TABLE iptv_favourites ADD COLUMN name TEXT;";
    private String ALTER_PRODUCTS_TABLE_2 = "ALTER TABLE iptv_favourites ADD COLUMN user_id_referred TEXT;";
    private String ALTER_PRODUCTS_TABLE_3 = "ALTER TABLE iptv_favourites ADD COLUMN num TEXT;";
    String CREATE_PRODUCTS_TABLE = "CREATE TABLE IF NOT EXISTS iptv_favourites(id INTEGER PRIMARY KEY,type TEXT,streamID TEXT,categoryID TEXT,name TEXT,user_id_referred TEXT,num TEXT)";
    Context context;
    SQLiteDatabase db;

    public DatabaseHandler(Context context2) {
        super(context2, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 3);
        this.context = context2;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        Log.e("fav_oncreate", "asdfasdfsadfa");
        sQLiteDatabase.execSQL(this.CREATE_PRODUCTS_TABLE);
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        Log.e("fav_upgrade", "oldVersion:" + i + "newversion:" + i2);
        if (i2 >= 3) {
            sQLiteDatabase.execSQL(this.ALTER_PRODUCTS_TABLE_1);
            sQLiteDatabase.execSQL(this.ALTER_PRODUCTS_TABLE_2);
            sQLiteDatabase.execSQL(this.ALTER_PRODUCTS_TABLE_3);
        }
    }

    public void addToFavourite(FavouriteDBModel favouriteDBModel, String str) {
        try {
            this.db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("type", str);
            contentValues.put(KEY_STREAM_ID, Integer.valueOf(favouriteDBModel.getStreamID()));
            contentValues.put(KEY_CATEGORY_ID, favouriteDBModel.getCategoryID());
            contentValues.put("name", favouriteDBModel.getName());
            contentValues.put(KEY_USER_ID, Integer.valueOf(favouriteDBModel.getUserID()));
            contentValues.put("num", favouriteDBModel.getNum());
            this.db.insert(TABLE_IPTV_FAVOURITES, null, contentValues);
            this.db.close();
        } catch (SQLiteException unused) {
        }
    }

    public void deleteFavourite(int i, String str, String str2, String str3, int i2) {
        try {
            this.db = getWritableDatabase();
            SQLiteDatabase sQLiteDatabase = this.db;
            sQLiteDatabase.delete(TABLE_IPTV_FAVOURITES, "streamID='" + i + "' AND " + KEY_USER_ID + "=" + i2, null);
            this.db.close();
        } catch (SQLiteException unused) {
        }
    }

    public void deleteAndRecreateAllTables() {
        this.db = getWritableDatabase();
        this.db.execSQL("DROP TABLE IF EXISTS iptv_favourites");
        onCreate(this.db);
        this.db.close();
    }

    public ArrayList<FavouriteDBModel> getAllFavourites(String str, int i) {
        String str2;
        int i2;
        ArrayList<FavouriteDBModel> arrayList = new ArrayList<>();
        String str3 = "";
        if (str.equalsIgnoreCase("live")) {
            str3 = SharepreferenceDBHandler.getLiveSubCatSort(this.context);
        }
        if (str.equalsIgnoreCase(AppConst.VOD) || str.equalsIgnoreCase(AppConst.EVENT_TYPE_MOVIE)) {
            str3 = SharepreferenceDBHandler.getVODSubCatSort(this.context);
        }
        if (str.equalsIgnoreCase("series")) {
            str3 = SharepreferenceDBHandler.getSeriesSubCatSort(this.context);
        }
        new ArrayList();
        if (str3.equals(AppConst.PASSWORD_UNSET)) {
            str2 = "SELECT  * FROM iptv_favourites WHERE type='" + str + "' AND " + KEY_USER_ID + "=" + i + " ";
        } else if (str3.equals("1")) {
            str2 = "SELECT  * FROM iptv_favourites WHERE type='" + str + "' AND " + KEY_USER_ID + "=" + i + "";
        } else if (str3.equals("2")) {
            str2 = "SELECT  * FROM iptv_favourites WHERE type='" + str + "'AND " + KEY_USER_ID + "=" + i + " ORDER BY " + "name" + " ASC ";
        } else if (str3.equals("3")) {
            str2 = "SELECT  * FROM iptv_favourites WHERE type='" + str + "' AND " + KEY_USER_ID + "=" + i + " ORDER BY " + "name" + " DESC ";
        } else if (str3.equals("4")) {
            str2 = "SELECT  * FROM iptv_favourites WHERE type='" + str + "' AND " + KEY_USER_ID + "=" + i + " ORDER BY cast(" + "num" + " as REAL) ASC ";
        } else if (str3.equals("5")) {
            str2 = "SELECT  * FROM iptv_favourites WHERE type='" + str + "' AND " + KEY_USER_ID + "=" + i + " ORDER BY cast(" + "num" + " as REAL) DESC ";
        } else {
            str2 = "SELECT  * FROM iptv_favourites WHERE type='" + str + "' AND " + KEY_USER_ID + "=" + i + "";
        }
        try {
            this.db = getReadableDatabase();
            Cursor rawQuery = this.db.rawQuery(str2, null);
            if (rawQuery.moveToFirst()) {
                do {
                    FavouriteDBModel favouriteDBModel = new FavouriteDBModel();
                    int i3 = 0;
                    try {
                        int parseInt = Integer.parseInt(rawQuery.getString(0));
                        i2 = Integer.parseInt(rawQuery.getString(2));
                        i3 = parseInt;
                    } catch (NumberFormatException unused) {
                        i2 = -1;
                    }
                    favouriteDBModel.setId(i3);
                    favouriteDBModel.setType(rawQuery.getString(1));
                    favouriteDBModel.setStreamID(i2);
                    favouriteDBModel.setCategoryID(rawQuery.getString(3));
                    favouriteDBModel.setName(rawQuery.getString(4));
                    favouriteDBModel.setUserID(i);
                    arrayList.add(favouriteDBModel);
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

    public int getFavouriteCount(String str, int i) {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM iptv_favourites WHERE type='" + str + "' AND " + KEY_USER_ID + "=" + i + "  ", null);
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

    public ArrayList<FavouriteDBModel> checkFavourite(int i, String str, String str2, int i2) {
        int i3;
        ArrayList<FavouriteDBModel> arrayList = new ArrayList<>();
        try {
            this.db = getReadableDatabase();
            Cursor rawQuery = this.db.rawQuery("SELECT  * FROM iptv_favourites WHERE streamID='" + i + "' AND " + KEY_CATEGORY_ID + "='" + str + "' AND " + "type" + "='" + str2 + "'  AND " + KEY_USER_ID + "=" + i2 + "", null);
            if (rawQuery.moveToFirst()) {
                do {
                    try {
                        i3 = Integer.parseInt(rawQuery.getString(2));
                    } catch (NumberFormatException unused) {
                        i3 = -1;
                    }
                    FavouriteDBModel favouriteDBModel = new FavouriteDBModel();
                    favouriteDBModel.setId(Integer.parseInt(rawQuery.getString(0)));
                    favouriteDBModel.setType(rawQuery.getString(1));
                    favouriteDBModel.setStreamID(i3);
                    favouriteDBModel.setCategoryID(rawQuery.getString(3));
                    arrayList.add(favouriteDBModel);
                } while (rawQuery.moveToNext());
            }
            rawQuery.close();
            return arrayList;
        } catch (SQLiteDatabaseLockedException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
            return arrayList;
        } catch (SQLiteException unused3) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
            return arrayList;
        }
    }

    public void deleteDataForUser(int i) {
        try {
            this.db = getWritableDatabase();
            SQLiteDatabase sQLiteDatabase = this.db;
            sQLiteDatabase.delete(TABLE_IPTV_FAVOURITES, "user_id_referred='" + i + "'", null);
            this.db.close();
        } catch (SQLiteException unused) {
        }
    }
}
