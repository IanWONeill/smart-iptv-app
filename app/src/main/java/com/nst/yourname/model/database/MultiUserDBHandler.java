package com.nst.yourname.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.model.ExtendedAppInfo;
import com.nst.yourname.model.MultiUserDBModel;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class MultiUserDBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "iptv_smarters_multi_user.db";
    private static final int DATABASE_VERSION = 4;
    private static final String KEY_AUTO_ID = "auto_id";
    private static final String KEY_CREATED_AT = "user_created";
    private static final String KEY_EPGURL = "epgurl";
    private static final String KEY_MAGPORTAL = "magportal";
    private static final String KEY_MAGPORTAL2 = "magportal2";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_SERVER_URL = "server_url";
    private static final String KEY_TYPE = "type";
    private static final String KEY_TYPE_OF_M3U = "type_of_m3u";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_EXT_RESELLER = "app_reseller_info";
    private static final String KEY_USER_EXT_ISVOD = "app_vod_only";
    private static final String KEY_USER_EXT_THEME = "app_theme_selected";
    private static final String TABLE_LOGIN = "login_user";
    private static final String TABLE_MULTI_USER = "multi_user";
    private static final String TABLE_MULTI_USER_EPG_M3U = "multi_user_epg_m3u";
    private static final String TABLE_MULTI_USER_M3U = "multi_user_m3u";
    private static final String TABLE_MULTI_USER_APP_ISVOD = "multi_user_app_is_vod";
    private static final String TABLE_MULTI_USER_APP_EXT = "multi_user_ext";

    private String ALTERED_TABLE_MULTIUSER = "ALTER TABLE multi_user ADD COLUMN magportal2 TEXT;";
    String CREATE_LOGIN_TABLE = "CREATE TABLE IF NOT EXISTS login_user(server_url TEXT,user_created TEXT)";
    String CREATE_MULTIUSER_EPG_TABLE = "CREATE TABLE IF NOT EXISTS multi_user_epg_m3u(auto_id INTEGER PRIMARY KEY,user_id TEXT,epgurl TEXT)";
    String CREATE_MULTIUSER_TABLE = "CREATE TABLE IF NOT EXISTS multi_user(auto_id INTEGER PRIMARY KEY,name TEXT,username TEXT,password TEXT,magportal TEXT,magportal2 TEXT)";
    String CREATE_MULTIUSER_TABLE_M3U = "CREATE TABLE IF NOT EXISTS multi_user_m3u(auto_id INTEGER PRIMARY KEY,name TEXT,username TEXT,password TEXT,magportal TEXT,type_of_m3u TEXT)";
    String CREATE_MULTIUSER_TABLE_EXT = "CREATE TABLE IF NOT EXISTS multi_user_ext(auto_id INTEGER PRIMARY KEY,app_reseller_info TEXT,app_vod_only TEXT,app_theme_selected TEXT)";
    Context context;
    SQLiteDatabase db;

    public MultiUserDBHandler(Context context2) {
        super(context2, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 4);
        this.context = context2;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(this.CREATE_MULTIUSER_TABLE);
        sQLiteDatabase.execSQL(this.CREATE_MULTIUSER_EPG_TABLE);
        sQLiteDatabase.execSQL(this.CREATE_MULTIUSER_TABLE_M3U);
        sQLiteDatabase.execSQL(this.CREATE_LOGIN_TABLE);
        sQLiteDatabase.execSQL(this.CREATE_MULTIUSER_TABLE_EXT);
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i < 2) {
            sQLiteDatabase.execSQL(this.CREATE_MULTIUSER_EPG_TABLE);
            sQLiteDatabase.execSQL(this.CREATE_MULTIUSER_TABLE_M3U);
        }
        if (i < 3) {
            sQLiteDatabase.execSQL(this.ALTERED_TABLE_MULTIUSER);
        }
        if (i < 4) {
            sQLiteDatabase.execSQL(this.CREATE_LOGIN_TABLE);
            sQLiteDatabase.execSQL(this.CREATE_MULTIUSER_TABLE_EXT);
        }
    }

    public void addmultiusers(String str, String str2, String str3, String str4, String str5) {
        try {
            this.db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", str.replaceAll("'", ""));
            contentValues.put("username", str2);
            contentValues.put("password", str3);
            contentValues.put(KEY_MAGPORTAL, str4);
            contentValues.put(KEY_MAGPORTAL2, str5);
            this.db.insert(TABLE_MULTI_USER, null, contentValues);
            this.db.close();
        } catch (SQLiteException unused) {
        }
    }

    public void updateMultiUser(int i, String str) {
        this.db = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_MAGPORTAL2, str);
            this.db.update(TABLE_MULTI_USER, contentValues, "auto_id = ?", new String[]{String.valueOf(i)});
            this.db.close();
        } catch (SQLiteException unused) {
        }
    }
    //app_reseller_info TEXT,app_vod_only TEXT,app_theme_selected TEXT
    public void addAppInfos(String str, String str2, String str3) {
        try {
            this.db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_reseller_info", str);
            contentValues.put("app_vod_only", str2);
            contentValues.put("app_theme_selected", str3);
            this.db.insert(TABLE_MULTI_USER_APP_EXT, null, contentValues);
            this.db.close();
        } catch (SQLiteException unused) {
        }
    }

    public void updateAppInfos(int i, String str) {
        this.db = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_MAGPORTAL2, str);
            this.db.update(TABLE_MULTI_USER_APP_EXT, contentValues, "auto_id = ?", new String[]{String.valueOf(i)});
            this.db.close();
        } catch (SQLiteException unused) {
        }
    }
    public ArrayList<ExtendedAppInfo> getAllAppExtras() {
        ArrayList<ExtendedAppInfo> arrayList = new ArrayList<>();
        try {
            this.db = getReadableDatabase();
            Cursor rawQuery = this.db.rawQuery("SELECT  * FROM multi_user_ext", null);
            if (rawQuery.moveToFirst()) {
                do {
                    ExtendedAppInfo multiUserDBModel = new ExtendedAppInfo();
                    multiUserDBModel.setResellerInfo (rawQuery.getString(1));
                    multiUserDBModel.setVodOnly (rawQuery.getString(2));
                    multiUserDBModel.setThemeSelected (rawQuery.getString(3));
                    arrayList.add(multiUserDBModel);
                } while (rawQuery.moveToNext());
            }
            rawQuery.close();
            return arrayList;
        } catch (SQLiteDatabaseLockedException unused) {
            return arrayList;
        } catch (SQLiteException unused2) {
            return arrayList;
        }
    }

    public void updateEditMultiUserdetails(int i, String str, String str2, String str3, String str4, String str5) {
        this.db = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", str);
            contentValues.put("username", str2);
            contentValues.put("password", str3);
            contentValues.put(KEY_MAGPORTAL, str4);
            contentValues.put(KEY_MAGPORTAL2, str5);
            this.db.update(TABLE_MULTI_USER, contentValues, "auto_id = ?", new String[]{String.valueOf(i)});
            this.db.close();
        } catch (SQLiteException unused) {
        }
    }

    public void addmultiusersM3U(String str, String str2, String str3, String str4, String str5) {
        try {
            this.db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", str.replaceAll("'", ""));
            contentValues.put("username", str2);
            contentValues.put("password", str3);
            contentValues.put(KEY_MAGPORTAL, str4);
            contentValues.put(KEY_TYPE_OF_M3U, str5);
            this.db.insert(TABLE_MULTI_USER_M3U, null, contentValues);
            this.db.close();
        } catch (SQLiteException unused) {
        }
    }

    public void updatemultiusersM3U(int i, String str, String str2, String str3) {
        this.db = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", str);
            contentValues.put(KEY_MAGPORTAL, str3);
            contentValues.put(KEY_TYPE_OF_M3U, str2);
            this.db.update(TABLE_MULTI_USER_M3U, contentValues, "auto_id = ?", new String[]{String.valueOf(i)});
            this.db.close();
        } catch (SQLiteException unused) {
        }
    }

    public void addmultiusersEPG(String str, String str2) {
        try {
            this.db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("user_id", str);
            contentValues.put(KEY_EPGURL, str2);
            this.db.insert(TABLE_MULTI_USER_EPG_M3U, null, contentValues);
            this.db.close();
        } catch (SQLiteException unused) {
        }
    }

    public void editmultiusersEPG(String str, String str2) {
        this.db = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_EPGURL, str2);
            this.db.update(TABLE_MULTI_USER_EPG_M3U, contentValues, "user_id = ?", new String[]{String.valueOf(str)});
            this.db.close();
        } catch (SQLiteException unused) {
        }
    }

    public String getUserEPG(int i) {
        String str = "";
        String str2 = "SELECT  * FROM multi_user_epg_m3u WHERE user_id ='" + i + "'";
        try {
            this.db = getReadableDatabase();
            Cursor rawQuery = this.db.rawQuery(str2, null);
            if (rawQuery.getCount() > 0) {
                rawQuery.moveToFirst();
                str = rawQuery.getString(rawQuery.getColumnIndex(KEY_EPGURL));
            }
            rawQuery.close();
            return str;
        } catch (SQLiteDatabaseLockedException unused) {
            return "";
        } catch (SQLiteException unused2) {
            return "";
        }
    }

    public ArrayList<MultiUserDBModel> getAllUsers() {
        ArrayList<MultiUserDBModel> arrayList = new ArrayList<>();
        try {
            this.db = getReadableDatabase();
            Cursor rawQuery = this.db.rawQuery("SELECT  * FROM multi_user ORDER BY auto_id DESC", null);
            if (rawQuery.moveToFirst()) {
                do {
                    MultiUserDBModel multiUserDBModel = new MultiUserDBModel();
                    multiUserDBModel.setname(rawQuery.getString(1));
                    multiUserDBModel.setusername(rawQuery.getString(2));
                    multiUserDBModel.setpassword(rawQuery.getString(3));
                    multiUserDBModel.setmagportal(rawQuery.getString(4));
                    multiUserDBModel.setmagportal2(rawQuery.getString(5));
                    arrayList.add(multiUserDBModel);
                } while (rawQuery.moveToNext());
            }
            rawQuery.close();
            return arrayList;
        } catch (SQLiteDatabaseLockedException unused) {
            return arrayList;
        } catch (SQLiteException unused2) {
            return arrayList;
        }
    }

    public ArrayList<MultiUserDBModel> getAllUsersM3U() {
        ArrayList<MultiUserDBModel> arrayList = new ArrayList<>();
        try {
            this.db = getReadableDatabase();
            Cursor rawQuery = this.db.rawQuery("SELECT  * FROM multi_user_m3u ORDER BY auto_id DESC", null);
            if (rawQuery.moveToFirst()) {
                do {
                    MultiUserDBModel multiUserDBModel = new MultiUserDBModel();
                    multiUserDBModel.setname(rawQuery.getString(1));
                    multiUserDBModel.setusername(rawQuery.getString(2));
                    multiUserDBModel.setpassword(rawQuery.getString(3));
                    multiUserDBModel.setmagportal(rawQuery.getString(4));
                    multiUserDBModel.setM3uType(rawQuery.getString(5));
                    arrayList.add(multiUserDBModel);
                } while (rawQuery.moveToNext());
            }
            rawQuery.close();
            return arrayList;
        } catch (SQLiteDatabaseLockedException unused) {
            return arrayList;
        } catch (SQLiteException unused2) {
            return arrayList;
        }
    }

    public boolean checkregistration(String str, String str2, String str3, String str4, String str5, String str6) {
        String str7;
        try {
            String replaceAll = str.replaceAll("'", "");
            if (str5.equals(AppConst.TYPE_M3U)) {
                str7 = "SELECT  count(*) FROM multi_user_m3u WHERE name ='" + replaceAll + "' AND " + "username" + " ='" + str2 + "' AND " + "password" + " ='" + str3 + "' AND " + KEY_MAGPORTAL + "='" + str4 + "'";
            } else {
                str7 = "SELECT  count(*) FROM multi_user WHERE name ='" + replaceAll + "' AND " + "username" + " ='" + str2 + "' AND " + "password" + " ='" + str3 + "' AND (" + KEY_MAGPORTAL + " LIKE '%" + str4 + "%' OR " + KEY_MAGPORTAL2 + " LIKE '%" + str6 + "%' )";
            }
            this.db = getReadableDatabase();
            Cursor rawQuery = this.db.rawQuery(str7, null);
            rawQuery.moveToFirst();
            int i = rawQuery.getInt(0);
            rawQuery.close();
            if (i > 0) {
                return true;
            }
            return false;
        } catch (SQLiteDatabaseLockedException unused) {
            return false;
        } catch (SQLiteException unused2) {
            return false;
        }
    }

    public ArrayList<MultiUserDBModel> getUserDetails(int i) {
        ArrayList<MultiUserDBModel> arrayList = new ArrayList<>();
        try {
            this.db = getReadableDatabase();
            Cursor rawQuery = this.db.rawQuery("SELECT  * FROM multi_user_m3u WHERE auto_id ='" + i + "'", null);
            if (rawQuery.moveToFirst()) {
                do {
                    MultiUserDBModel multiUserDBModel = new MultiUserDBModel();
                    multiUserDBModel.setname(rawQuery.getString(1));
                    multiUserDBModel.setusername(rawQuery.getString(2));
                    multiUserDBModel.setpassword(rawQuery.getString(3));
                    multiUserDBModel.setmagportal(rawQuery.getString(4));
                    multiUserDBModel.setM3uType(rawQuery.getString(5));
                    arrayList.add(multiUserDBModel);
                } while (rawQuery.moveToNext());
            }
            rawQuery.close();
            return arrayList;
        } catch (SQLiteDatabaseLockedException unused) {
            return arrayList;
        } catch (SQLiteException unused2) {
            return arrayList;
        }
    }

    public ArrayList<MultiUserDBModel> getUserDetailsAPI(String str, String str2, String str3, String str4, String str5) {
        ArrayList<MultiUserDBModel> arrayList = new ArrayList<>();
        try {
            this.db = getReadableDatabase();
            Cursor rawQuery = this.db.rawQuery("SELECT  * FROM multi_user WHERE name ='" + str + "' AND " + "username" + " ='" + str2 + "' AND " + "password" + " ='" + str3 + "' AND (" + KEY_MAGPORTAL + " LIKE '%" + str4 + "%' OR " + KEY_MAGPORTAL2 + " LIKE '%" + str5 + "%' )", null);
            if (rawQuery.moveToFirst()) {
                do {
                    MultiUserDBModel multiUserDBModel = new MultiUserDBModel();
                    multiUserDBModel.setname(rawQuery.getString(1));
                    multiUserDBModel.setusername(rawQuery.getString(2));
                    multiUserDBModel.setpassword(rawQuery.getString(3));
                    multiUserDBModel.setmagportal(rawQuery.getString(4));
                    multiUserDBModel.setmagportal2(rawQuery.getString(5));
                    arrayList.add(multiUserDBModel);
                } while (rawQuery.moveToNext());
            }
            rawQuery.close();
            return arrayList;
        } catch (SQLiteDatabaseLockedException unused) {
            return arrayList;
        } catch (SQLiteException unused2) {
            return arrayList;
        }
    }

    public ArrayList<MultiUserDBModel> getUserDetailsM3U(String str, String str2, String str3, String str4) {
        ArrayList<MultiUserDBModel> arrayList = new ArrayList<>();
        try {
            this.db = getReadableDatabase();
            Cursor rawQuery = this.db.rawQuery("SELECT  * FROM multi_user_m3u WHERE name ='" + str + "' AND " + "username" + " ='" + str2 + "' AND " + "password" + " ='" + str3 + "' AND " + KEY_MAGPORTAL + " LIKE '%" + str4 + "%'", null);
            if (rawQuery.moveToFirst()) {
                do {
                    MultiUserDBModel multiUserDBModel = new MultiUserDBModel();
                    multiUserDBModel.setname(rawQuery.getString(1));
                    multiUserDBModel.setusername(rawQuery.getString(2));
                    multiUserDBModel.setpassword(rawQuery.getString(3));
                    multiUserDBModel.setmagportal(rawQuery.getString(4));
                    multiUserDBModel.setM3uType(rawQuery.getString(5));
                    arrayList.add(multiUserDBModel);
                } while (rawQuery.moveToNext());
            }
            rawQuery.close();
            return arrayList;
        } catch (SQLiteDatabaseLockedException unused) {
            return arrayList;
        } catch (SQLiteException unused2) {
            return arrayList;
        }
    }

    public void deleteUserAPI(int i) {
        this.db = getWritableDatabase();
        SQLiteDatabase sQLiteDatabase = this.db;
        sQLiteDatabase.execSQL("DELETE FROM multi_user WHERE auto_id='" + i + "'");
        this.db.close();
    }

    public void deleteUserM3U(int i) {
        this.db = getWritableDatabase();
        SQLiteDatabase sQLiteDatabase = this.db;
        sQLiteDatabase.execSQL("DELETE FROM multi_user_m3u WHERE auto_id='" + i + "'");
        SQLiteDatabase sQLiteDatabase2 = this.db;
        sQLiteDatabase2.execSQL("DELETE FROM multi_user_epg_m3u WHERE user_id='" + i + "'");
        this.db.close();
    }

    public int getAutoIdLoggedInUser(String str, String str2, String str3, String str4, String str5, String str6) {
        String str7;
        try {
            if (str5.equals(AppConst.TYPE_M3U)) {
                str7 = "SELECT auto_id FROM multi_user_m3u WHERE name='" + str + "' AND " + "username" + "='" + str2 + "' AND " + "password" + "='" + str3 + "' AND " + KEY_MAGPORTAL + " LIKE '%" + str4 + "%'";
            } else {
                str7 = "SELECT auto_id FROM multi_user WHERE name='" + str + "' AND " + "username" + "='" + str2 + "' AND " + "password" + "='" + str3 + "' AND (" + KEY_MAGPORTAL + " LIKE '%" + str4 + "%' OR " + KEY_MAGPORTAL2 + " LIKE '%" + str6 + "%' )";
            }
            this.db = getReadableDatabase();
            Cursor rawQuery = this.db.rawQuery(str7, null);
            if (rawQuery == null || !rawQuery.moveToFirst()) {
                return -1;
            }
            int i = rawQuery.getInt(rawQuery.getColumnIndex(KEY_AUTO_ID));
            rawQuery.close();
            return i;
        } catch (SQLiteDatabaseLockedException unused) {
            return -1;
        } catch (SQLiteException unused2) {
            return -1;
        }
    }

    public void saveLoginData(String str, String str2) {
        try {
            this.db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_SERVER_URL, str);
            contentValues.put(KEY_CREATED_AT, str2);
            this.db.insert(TABLE_LOGIN, null, contentValues);
            this.db.close();
        } catch (SQLiteDatabaseLockedException e) {
            Log.d("", "" + e);
        } catch (SQLiteException e2) {
            Log.d("", "" + e2);
        }
    }

    public void saveLoginDataMods(String str, String str2,Boolean str3) {
        try {
            this.db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_SERVER_URL, str);
            contentValues.put(KEY_CREATED_AT, str2);
            contentValues.put (TABLE_MULTI_USER_APP_ISVOD,str3);
            this.db.insert(TABLE_LOGIN, null, contentValues);
            this.db.close();
        } catch (SQLiteDatabaseLockedException e) {
            Log.d("", "" + e);
        } catch (SQLiteException e2) {
            Log.d("", "" + e2);
        }
    }

    public ArrayList<MultiUserDBModel> getSaveLoginDate() {
        ArrayList<MultiUserDBModel> arrayList = new ArrayList<>();
        this.db = getReadableDatabase();
        Cursor rawQuery = this.db.rawQuery("SELECT * FROM login_user", null);
        if (rawQuery.moveToFirst()) {
            MultiUserDBModel multiUserDBModel = new MultiUserDBModel();
            multiUserDBModel.setServerUrl(rawQuery.getString(0));
            multiUserDBModel.setDate(rawQuery.getString(1));
            arrayList.add(multiUserDBModel);
            rawQuery.close();
        }
        return arrayList;
    }

    public void deleteSaveLogin() {
        this.db = getWritableDatabase();
        this.db.execSQL("DELETE FROM login_user");
        this.db.close();
    }

    public boolean getAllUsersCount() {
        ArrayList arrayList = new ArrayList();
        try {
            this.db = getReadableDatabase();
            Cursor rawQuery = this.db.rawQuery("SELECT  * FROM multi_user ORDER BY auto_id DESC", null);
            if (rawQuery.moveToFirst()) {
                do {
                    MultiUserDBModel multiUserDBModel = new MultiUserDBModel();
                    multiUserDBModel.setname(rawQuery.getString(1));
                    arrayList.add(multiUserDBModel);
                } while (rawQuery.moveToNext());
            }
            rawQuery.close();
            if (arrayList.size() > 0) {
                return true;
            }
            return false;
        } catch (SQLiteDatabaseLockedException unused) {
            return false;
        } catch (SQLiteException unused2) {
            return false;
        }
    }
}
