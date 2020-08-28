package com.nst.yourname.view.utility.epg;

import com.nst.yourname.view.utility.epg.domain.EPGChannel;
import com.nst.yourname.view.utility.epg.domain.EPGEvent;
import java.util.List;

public interface EPGData {
    EPGChannel addNewChannel(String str, String str2, String str3, String str4, String str5, String str6, String str7);

    EPGChannel getChannel(int i);

    int getChannelCount();

    EPGEvent getEvent(int i, int i2);

    List<EPGEvent> getEvents(int i);

    EPGChannel getOrCreateChannel(String str, String str2, String str3, String str4, String str5, String str6, String str7);

    boolean hasData();
}
