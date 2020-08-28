package com.nst.yourname.miscelleneious.chromecastfeature;

import android.content.Context;
import com.google.android.gms.cast.CastMediaControlIntent;
import com.google.android.gms.cast.framework.CastOptions;
import com.google.android.gms.cast.framework.OptionsProvider;
import com.google.android.gms.cast.framework.SessionProvider;
import com.google.android.gms.cast.framework.media.CastMediaOptions;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import java.util.List;

public final class CastOptionsProvider implements OptionsProvider {
    @Override // com.google.android.gms.cast.framework.OptionsProvider
    public List<SessionProvider> getAdditionalSessionProviders(Context context) {
        return null;
    }

    @Override // com.google.android.gms.cast.framework.OptionsProvider
    public CastOptions getCastOptions(Context context) {
        return new CastOptions.Builder().setReceiverApplicationId(CastMediaControlIntent.DEFAULT_MEDIA_RECEIVER_APPLICATION_ID).setCastMediaOptions(new CastMediaOptions.Builder().setNotificationOptions(new NotificationOptions.Builder().setTargetActivityClassName(ExpandedControlsActivity.class.getName()).build()).setExpandedControllerActivityClassName(ExpandedControlsActivity.class.getName()).build()).build();
    }
}
