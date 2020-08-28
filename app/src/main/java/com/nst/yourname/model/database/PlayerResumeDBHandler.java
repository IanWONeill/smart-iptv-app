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

public class PlayerResumeDBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "iptv_smarters_tv_box_resume_player.db";
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_ID = "id";
    private static final String KEY_STREAM_DURATION = "stream_duration";
    private static final String KEY_STREAM_FINISH = "stream_finish";
    private static final String KEY_STREAM_ID = "streamID";
    private static final String KEY_STREAM_TIME_ELAPSED = "stream_time_elapsed";
    private static final String KEY_TYPE = "type";
    private static final String TABLE_IPTV_RESUME_PLAYER = "iptv_resume_player";
    String CREATE_RESUME_PLAYER_TABLE = "CREATE TABLE IF NOT EXISTS iptv_resume_player(id INTEGER PRIMARY KEY,type TEXT,streamID TEXT,stream_finish TEXT,stream_time_elapsed TEXT,stream_duration TEXT)";
    Context context;
    SQLiteDatabase db;

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public PlayerResumeDBHandler(Context context2) {
        super(context2, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        this.context = context2;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(this.CREATE_RESUME_PLAYER_TABLE);
    }

    public void addToResumePlayer(PlayerResumeDBModel playerResumeDBModel, String str) {
        this.db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", str);
        contentValues.put(KEY_STREAM_ID, playerResumeDBModel.getStreamId());
        contentValues.put(KEY_STREAM_FINISH, Boolean.valueOf(playerResumeDBModel.isStreamFinish()));
        contentValues.put(KEY_STREAM_TIME_ELAPSED, Boolean.valueOf(playerResumeDBModel.isStreamFinish()));
        contentValues.put(KEY_STREAM_DURATION, Boolean.valueOf(playerResumeDBModel.isStreamFinish()));
        this.db.insert(TABLE_IPTV_RESUME_PLAYER, null, contentValues);
        this.db.close();
    }

    public PlayerResumeDBModel getStreamStatus(String str, String str2) {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT * FROM iptv_resume_player WHERE streamID = '" + str + "' AND " + "type" + " = '" + str2 + "'", null);
            PlayerResumeDBModel playerResumeDBModel = new PlayerResumeDBModel();
            if (rawQuery.moveToFirst()) {
                do {
                    playerResumeDBModel.setIdAuto(Integer.parseInt(rawQuery.getString(0)));
                    playerResumeDBModel.setEventType(rawQuery.getString(1));
                    playerResumeDBModel.setStreamId(rawQuery.getString(2));
                    playerResumeDBModel.setStreamFinish(Boolean.parseBoolean(rawQuery.getString(3)));
                    playerResumeDBModel.setTimeElapsed(rawQuery.getLong(4));
                    playerResumeDBModel.setStreamDuration(rawQuery.getLong(5));
                } while (rawQuery.moveToNext());
            }
            rawQuery.close();
            return playerResumeDBModel;
        } catch (SQLiteDatabaseLockedException unused) {
            return null;
        } catch (SQLiteException unused2) {
            return null;
        }
    }

    public boolean updateResumePlayerStatus(String str, String str2, boolean z, long j) {
        try {
            String str3 = "SELECT rowid FROM iptv_resume_player WHERE type = '" + str2 + "' AND " + KEY_STREAM_ID + " = '" + str + "'";
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            String str4 = "";
            Cursor rawQuery = writableDatabase.rawQuery(str3, null);
            if (rawQuery != null) {
                if (rawQuery.moveToFirst()) {
                    do {
                        str4 = String.valueOf(Integer.parseInt(rawQuery.getString(rawQuery.getColumnIndex("id"))));
                    } while (rawQuery.moveToNext());
                }
            } else if (this.context != null) {
                Toast.makeText(this.context, "cursor is null", 1).show();
            }
            if (!str4.equals("")) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(KEY_STREAM_FINISH, Boolean.valueOf(z));
                contentValues.put(KEY_STREAM_TIME_ELAPSED, Long.valueOf(j));
                writableDatabase.update(TABLE_IPTV_RESUME_PLAYER, contentValues, "id= ?", new String[]{str4});
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

    public boolean updateResumePlayerStatuTimes(String str, String str2, boolean z, long j, long j2) {
        try {
            String str3 = "SELECT rowid FROM iptv_resume_player WHERE type = '" + str2 + "' AND " + KEY_STREAM_ID + " = '" + str + "'";
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            String str4 = "";
            Cursor rawQuery = writableDatabase.rawQuery(str3, null);
            if (rawQuery != null) {
                if (rawQuery.moveToFirst()) {
                    do {
                        str4 = String.valueOf(Integer.parseInt(rawQuery.getString(rawQuery.getColumnIndex("id"))));
                    } while (rawQuery.moveToNext());
                }
            } else if (this.context != null) {
                Toast.makeText(this.context, "cursor is null", 1).show();
            }
            if (!str4.equals("")) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(KEY_STREAM_FINISH, Boolean.valueOf(z));
                contentValues.put(KEY_STREAM_TIME_ELAPSED, Long.valueOf(j2));
                contentValues.put(KEY_STREAM_DURATION, Long.valueOf(j));
                writableDatabase.update(TABLE_IPTV_RESUME_PLAYER, contentValues, "id= ?", new String[]{str4});
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

    public int isStreamAvailable(String str, String str2) {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  COUNT(*) FROM iptv_resume_player WHERE streamID='" + str + "' AND " + "type" + "='" + str2 + "'", null);
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

    public void deleteAndRecreateAllTables() {
        try {
            getReadableDatabase();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.execSQL("DROP TABLE IF EXISTS iptv_resume_player");
            onCreate(writableDatabase);
            writableDatabase.close();
        } catch (SQLiteDatabaseLockedException unused) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        } catch (SQLiteException unused2) {
            Log.w(NotificationCompat.CATEGORY_MESSAGE, "exception");
        }
    }
}
