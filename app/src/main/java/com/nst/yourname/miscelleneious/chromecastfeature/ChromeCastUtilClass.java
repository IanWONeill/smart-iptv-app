package com.nst.yourname.miscelleneious.chromecastfeature;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.nst.yourname.miscelleneious.common.Utils;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

public class ChromeCastUtilClass {
    static final boolean $assertionsDisabled = false;

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0003, code lost:
        r3 = r3.getRemoteMediaClient();
     */
    public static void loadRemoteMedia(MediaInfo mediaInfo, CastSession castSession, final Context context) {
        //ToDo; initialized...
        final RemoteMediaClient remoteMediaClient = null;
        if (castSession != null && remoteMediaClient != null) {
            remoteMediaClient.addListener(new RemoteMediaClient.Listener() {
                /* class com.nst.yourname.miscelleneious.chromecastfeature.ChromeCastUtilClass.AnonymousClass1 */

                @Override // com.google.android.gms.cast.framework.media.RemoteMediaClient.Listener
                public void onStatusUpdated() {
                    Log.e("", "onStatusUpdated()");
                    context.startActivity(new Intent(context, ExpandedControlsActivity.class));
                    remoteMediaClient.removeListener(this);
                }

                @Override // com.google.android.gms.cast.framework.media.RemoteMediaClient.Listener
                public void onMetadataUpdated() {
                    Log.e("", "onMetadataUpdated()");
                }

                @Override // com.google.android.gms.cast.framework.media.RemoteMediaClient.Listener
                public void onQueueStatusUpdated() {
                    Log.e("", "onQueueStatusUpdated()");
                }

                @Override // com.google.android.gms.cast.framework.media.RemoteMediaClient.Listener
                public void onPreloadStatusUpdated() {
                    Log.e("", "onPreloadStatusUpdated()");
                }

                @Override // com.google.android.gms.cast.framework.media.RemoteMediaClient.Listener
                public void onSendingRemoteMediaRequest() {
                    Log.e("", "onSendingRemoteMediaRequest()");
                }

                @Override // com.google.android.gms.cast.framework.media.RemoteMediaClient.Listener
                public void onAdBreakStatusUpdated() {
                    Log.e("", "onAdBreakStatusUpdated()");
                }
            });
            remoteMediaClient.load(mediaInfo, true, 0);
        }
    }

    public static void castHLS(final Handler handler, final RemoteMediaClient remoteMediaClient, String str, final MediaMetadata mediaMetadata, final Context context) {
        Utils.showProgressDialog((Activity) context);
        new OkHttpClient().newCall(new Request.Builder().url(str).build()).enqueue(new Callback() {
            /* class com.nst.yourname.miscelleneious.chromecastfeature.ChromeCastUtilClass.AnonymousClass2 */

            @Override // okhttp3.Callback
            public void onFailure(@NotNull Call call, @NotNull IOException iOException) {
                iOException.printStackTrace();
                Utils.hideProgressDialog();
                Log.e("chrome cast ====>  ", "Unable to cast,please try again");
                Toast.makeText(context, "Unable to cast,please try again ", 0).show();
            }

            @Override // okhttp3.Callback
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Utils.hideProgressDialog();
                Log.e("url with token==> ", "" + response.request().url().toString());
                final MediaInfo build = new MediaInfo.Builder(response.request().url().toString()).setStreamType(1).setContentType("application/x-mpegurl").setMetadata(mediaMetadata).build();
                handler.post(new Runnable() {
                    /* class com.nst.yourname.miscelleneious.chromecastfeature.ChromeCastUtilClass.AnonymousClass2.AnonymousClass1 */

                    public void run() {
                        remoteMediaClient.addListener(new RemoteMediaClient.Listener() {
                            /* class com.nst.yourname.miscelleneious.chromecastfeature.ChromeCastUtilClass.AnonymousClass2.AnonymousClass1.AnonymousClass1 */

                            @Override // com.google.android.gms.cast.framework.media.RemoteMediaClient.Listener
                            public void onStatusUpdated() {
                                Log.e("chromecastUtile class=", "onStatusUpdated()");
                                context.startActivity(new Intent(context, ExpandedControlsActivity.class));
                                remoteMediaClient.removeListener(this);
                            }

                            @Override // com.google.android.gms.cast.framework.media.RemoteMediaClient.Listener
                            public void onMetadataUpdated() {
                                Log.e("chromecastUtile clas", "onMetadataUpdated()");
                            }

                            @Override // com.google.android.gms.cast.framework.media.RemoteMediaClient.Listener
                            public void onQueueStatusUpdated() {
                                Log.e("chromecastUtile clas", "onQueueStatusUpdated()");
                            }

                            @Override // com.google.android.gms.cast.framework.media.RemoteMediaClient.Listener
                            public void onPreloadStatusUpdated() {
                                Log.e("chromecastUtile clas", "onPreloadStatusUpdated()");
                            }

                            @Override // com.google.android.gms.cast.framework.media.RemoteMediaClient.Listener
                            public void onSendingRemoteMediaRequest() {
                                Log.e("chromecastUtile clas", "onSendingRemoteMediaRequest()");
                            }

                            @Override // com.google.android.gms.cast.framework.media.RemoteMediaClient.Listener
                            public void onAdBreakStatusUpdated() {
                                Log.e("chromecastUtile clas", "onAdBreakStatusUpdated()");
                            }
                        });
                        remoteMediaClient.load(build, true, 0);
                    }
                });
            }
        });
    }

    public static String getWifiIp(Context context) {
        String macAddress = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo().getMacAddress();
        if (macAddress == null) {
            return macAddress;
        }
        return "https://" + macAddress;
    }
}
