package com.nst.yourname.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import com.nst.yourname.model.pojo.ExternalPlayerModelClass;
import java.util.ArrayList;

public class ExternalPlayerDataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "externalplayerdatabase.db";
    private static final int DATABASE_VERSION = 3;
    private static final String KEY_APP_ICON = "appicon";
    private static final String KEY_APP_NAME = "appname";
    private static final String KEY_ID = "id";
    private static final String KEY_PACKAGE_NAME = "packagename";
    private static final String KEY_USER_ID = "user_id_referred";
    private static final String TABLE_EXTERNAL_PLAYER = "table_external_player";
    String CREATE_EXTERNAL_PLAYER_TABLE = "CREATE TABLE IF NOT EXISTS table_external_player(id INTEGER PRIMARY KEY,appname TEXT,packagename TEXT,appicon TEXT,user_id_referred TEXT)";
    private Context context;
    private SQLiteDatabase db;

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public ExternalPlayerDataBase(@Nullable Context context2) {
        super(context2, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 3);
        this.context = context2;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(this.CREATE_EXTERNAL_PLAYER_TABLE);
    }

    public void addExternalPlayer(String str, String str2) {
        try {
            this.db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_APP_NAME, str);
            contentValues.put("packagename", str2);
            this.db.insert(TABLE_EXTERNAL_PLAYER, null, contentValues);
            this.db.close();
        } catch (SQLiteException unused) {
        }
    }

    public ArrayList<ExternalPlayerModelClass> getExternalPlayer() {
        ArrayList<ExternalPlayerModelClass> arrayList = new ArrayList<>();
        try {
            this.db = getReadableDatabase();
            Cursor rawQuery = this.db.rawQuery("SELECT  * FROM table_external_player ", null);
            if (rawQuery.moveToFirst()) {
                do {
                    ExternalPlayerModelClass externalPlayerModelClass = new ExternalPlayerModelClass();
                    externalPlayerModelClass.setId(rawQuery.getInt(0));
                    externalPlayerModelClass.setAppname(rawQuery.getString(1));
                    externalPlayerModelClass.setPackagename(rawQuery.getString(2));
                    externalPlayerModelClass.setAppicon(rawQuery.getString(3));
                    arrayList.add(externalPlayerModelClass);
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

    public boolean CheckPlayerExistense(String str) {
        ArrayList arrayList = new ArrayList();
        String str2 = "SELECT  * FROM table_external_player WHERE appname='" + str + "'";
        try {
            this.db = getReadableDatabase();
            Cursor rawQuery = this.db.rawQuery(str2, null);
            if (rawQuery.moveToFirst()) {
                do {
                    ExternalPlayerModelClass externalPlayerModelClass = new ExternalPlayerModelClass();
                    externalPlayerModelClass.setAppname(rawQuery.getString(1));
                    arrayList.add(externalPlayerModelClass);
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

    public int removePlayer(String str) {
        try {
            this.db = getWritableDatabase();
            SQLiteDatabase sQLiteDatabase = this.db;
            int delete = sQLiteDatabase.delete(TABLE_EXTERNAL_PLAYER, "appname='" + str + "' ", null);
            try {
                this.db.close();
                return delete;
            } catch (SQLiteDatabaseLockedException unused) {
                return delete;
            } catch (SQLiteException unused2) {
                return delete;
            }
        } catch (SQLiteDatabaseLockedException unused3) {
            return 0;
        } catch (SQLiteException unused4) {
            return 0;
        }
    }

    public int getExternalPlayercount() {
        int i;
        try {
            this.db = getReadableDatabase();
            Cursor rawQuery = this.db.rawQuery("SELECT  * FROM table_external_player ", null);
            if (rawQuery.moveToFirst()) {
                i = rawQuery.getCount();
            } else {
                i = 0;
            }
            rawQuery.close();
            return i;
        } catch (SQLiteDatabaseLockedException unused) {
            return 0;
        } catch (SQLiteException unused2) {
            return 0;
        }
    }
}
