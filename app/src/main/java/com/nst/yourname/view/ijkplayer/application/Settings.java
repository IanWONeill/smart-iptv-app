package com.nst.yourname.view.ijkplayer.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;

public class Settings {
    public static final int PV_PLAYER__AndroidMediaPlayer = 1;
    public static final int PV_PLAYER__Auto = 0;
    public static final int PV_PLAYER__IjkExoMediaPlayer = 3;
    public static final int PV_PLAYER__IjkMediaPlayer = 2;
    private SharedPreferences loginPreferencesAfterLogin = this.mAppContext.getSharedPreferences(AppConst.LOGIN_PREF_MEDIA_CODEC, 0);
    private SharedPreferences loginPreferencesAfterLoginInfBuf = this.mAppContext.getSharedPreferences(AppConst.LOGIN_PREF_INF_BUF, 0);
    private SharedPreferences loginPreferencesAfterLoginOPENGL = this.mAppContext.getSharedPreferences(AppConst.LOGIN_PREF_OPENGL, 0);
    private SharedPreferences loginPreferencesAfterLoginOPENSL_ES = this.mAppContext.getSharedPreferences(AppConst.LOGIN_PREF_OPENSL_ES, 0);
    private SharedPreferences loginPreferencesAfterLoginSubtitleSize = this.mAppContext.getSharedPreferences(AppConst.LOGIN_PREF_SUB_FONT_SIZE, 0);
    private SharedPreferences.Editor loginPrefsEditorOPENGL;
    private SharedPreferences.Editor loginPrefsEditorOpenSLES;
    private Context mAppContext;
    private SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mAppContext);

    public Settings(Context context) {
        this.mAppContext = context.getApplicationContext();
    }

    public boolean getEnableBackgroundPlay() {
        return this.mSharedPreferences.getBoolean(this.mAppContext.getString(R.string.pref_key_enable_background_play), false);
    }

    public int getPlayer() {
        String string = this.loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_MEDIA_CODEC, "");
        if (string.equals(this.mAppContext.getResources().getString(R.string.native_decoder))) {
            return 3;
        }
        return (!string.equals(this.mAppContext.getResources().getString(R.string.hardware_decoder)) && string.equals(this.mAppContext.getResources().getString(R.string.software_decoder))) ? 2 : 2;
    }

    public boolean getUsingMediaCodec() {
        return this.loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_MEDIA_CODEC, "").equals(this.mAppContext.getResources().getString(R.string.hardware_decoder));
    }

    public boolean getUsingMediaCodecAutoRotate() {
        return this.mSharedPreferences.getBoolean(this.mAppContext.getString(R.string.pref_key_using_media_codec_auto_rotate), false);
    }

    public boolean getMediaCodecHandleResolutionChange() {
        return this.mSharedPreferences.getBoolean(this.mAppContext.getString(R.string.pref_key_media_codec_handle_resolution_change), false);
    }

    public boolean getUsingOpenSLES() {
        return this.loginPreferencesAfterLoginOPENSL_ES.getString(AppConst.LOGIN_PREF_OPENSL_ES, "").equals("checked");
    }

    public boolean getUsingInfBuf() {
        return this.loginPreferencesAfterLoginInfBuf.getString(AppConst.LOGIN_PREF_INF_BUF, "unchecked").equals("checked");
    }

    public String getUsingSubtitleSize() {
        return this.loginPreferencesAfterLoginSubtitleSize.getString(AppConst.LOGIN_PREF_SUB_FONT_SIZE, "20");
    }

    public String getPixelFormat() {
        return this.loginPreferencesAfterLoginOPENGL.getString(AppConst.LOGIN_PREF_OPENGL, "");
    }

    public boolean getEnableNoView() {
        return this.mSharedPreferences.getBoolean(this.mAppContext.getString(R.string.pref_key_enable_no_view), false);
    }

    public boolean getEnableSurfaceView() {
        return this.mSharedPreferences.getBoolean(this.mAppContext.getString(R.string.pref_key_enable_surface_view), false);
    }

    public boolean getEnableTextureView() {
        return this.mSharedPreferences.getBoolean(this.mAppContext.getString(R.string.pref_key_enable_texture_view), false);
    }

    public boolean getEnableDetachedSurfaceTextureView() {
        return this.mSharedPreferences.getBoolean(this.mAppContext.getString(R.string.pref_key_enable_detached_surface_texture), false);
    }

    public boolean getUsingMediaDataSource() {
        return this.mSharedPreferences.getBoolean(this.mAppContext.getString(R.string.pref_key_using_mediadatasource), false);
    }

    public String getLastDirectory() {
        return this.mSharedPreferences.getString(this.mAppContext.getString(R.string.pref_key_last_directory), "/");
    }

    public void setLastDirectory(String str) {
        this.mSharedPreferences.edit().putString(this.mAppContext.getString(R.string.pref_key_last_directory), str).apply();
    }
}
