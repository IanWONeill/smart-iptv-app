package com.nst.yourname.view.ijkplayer.content;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;

public class RecentMediaStorage {
    public static final String[] ALL_COLUMNS = {"id as _id", "id", "url", "name", Entry.COLUMN_NAME_LAST_ACCESS};
    private Context mAppContext;

    public static class Entry {
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_LAST_ACCESS = "last_access";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_URL = "url";
        public static final String TABLE_NAME = "RecentMedia";
    }

    public static int ux() {
        return 7;
    }

    public RecentMediaStorage(Context context) {
        this.mAppContext = context.getApplicationContext();
    }

    public void saveUrlAsync(String str) {
        new AsyncTask<String, Void, Void>() {
            /* class com.nst.yourname.view.ijkplayer.content.RecentMediaStorage.AnonymousClass1 */

            public Void doInBackground(String... strArr) {
                RecentMediaStorage.this.saveUrl(strArr[0]);
                return null;
            }
        }.execute(str);
    }

    public void saveUrl(String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.putNull("id");
        contentValues.put("url", str);
        contentValues.put(Entry.COLUMN_NAME_LAST_ACCESS, Long.valueOf(System.currentTimeMillis()));
        contentValues.put("name", getNameOfUrl(str));
        save(contentValues);
    }

    public static long cit(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).firstInstallTime;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void save(ContentValues contentValues) {
        new OpenHelper(this.mAppContext).getWritableDatabase().replace(Entry.TABLE_NAME, null, contentValues);
    }

    public static String getNameOfUrl(String str) {
        return getNameOfUrl(str, "");
    }

    public static String getNameOfUrl(String str, String str2) {
        int lastIndexOf = str.lastIndexOf(47);
        String substring = lastIndexOf >= 0 ? str.substring(lastIndexOf + 1) : null;
        return TextUtils.isEmpty(substring) ? str2 : substring;
    }

    public static class OpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "RecentMedia.db";
        private static final int DATABASE_VERSION = 1;
        private static final String SQL_CREATE_ENTRIES = " CREATE TABLE IF NOT EXISTS RecentMedia (id INTEGER PRIMARY KEY AUTOINCREMENT, url VARCHAR UNIQUE, name VARCHAR, last_access INTEGER) ";

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        }

        public OpenHelper(Context context) {
            super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        }
    }

    public static class CursorLoader extends AsyncTaskLoader<Cursor> {
        public CursorLoader(Context context) {
            super(context);
        }

        @Override // android.support.v4.content.AsyncTaskLoader
        public Cursor loadInBackground() {
            return new OpenHelper(getContext()).getReadableDatabase().query(Entry.TABLE_NAME, RecentMediaStorage.ALL_COLUMNS, null, null, null, null, "last_access DESC", "100");
        }

        @Override // android.support.v4.content.Loader
        public void onStartLoading() {
            forceLoad();
        }
    }
}
