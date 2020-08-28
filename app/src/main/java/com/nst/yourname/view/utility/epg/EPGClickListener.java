package com.nst.yourname.view.utility.epg;

import com.nst.yourname.view.utility.epg.domain.EPGChannel;
import com.nst.yourname.view.utility.epg.domain.EPGEvent;

public interface EPGClickListener {
    void onChannelClicked(int i, EPGChannel ePGChannel);

    void onEventClicked(int i, int i2, EPGEvent ePGEvent);

    void onResetButtonClicked();
}
