package com.nst.yourname.view.utility.epg.misc;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nst.yourname.view.utility.epg.EPGData;
import com.nst.yourname.view.utility.epg.domain.EPGChannel;
import com.nst.yourname.view.utility.epg.domain.EPGEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EPGDataImpl implements EPGData {
    private List<EPGChannel> channels = Lists.newArrayList();
    private Map<String, EPGChannel> channelsByName = new HashMap();

    public EPGDataImpl(Map<EPGChannel, List<EPGEvent>> map) {
        this.channels = Lists.newArrayList(map.keySet());
        indexChannels();
    }

    @Override // com.nst.yourname.view.utility.epg.EPGData
    public EPGChannel getChannel(int i) {
        return this.channels.get(i);
    }

    @Override // com.nst.yourname.view.utility.epg.EPGData
    public EPGChannel getOrCreateChannel(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        EPGChannel ePGChannel = this.channelsByName.get(str4);
        if (ePGChannel != null) {
            return ePGChannel;
        }
        return addNewChannel(str, str2, str3, str4, str5, str6, str7);
    }

    @Override // com.nst.yourname.view.utility.epg.EPGData
    public List<EPGEvent> getEvents(int i) {
        if (i >= this.channels.size() || this.channels.get(i) == null) {
            return null;
        }
        return this.channels.get(i).getEvents();
    }

    @Override // com.nst.yourname.view.utility.epg.EPGData
    public EPGEvent getEvent(int i, int i2) {
        return this.channels.get(i).getEvents().get(i2);
    }

    @Override // com.nst.yourname.view.utility.epg.EPGData
    public int getChannelCount() {
        return this.channels.size();
    }

    @Override // com.nst.yourname.view.utility.epg.EPGData
    public boolean hasData() {
        return !this.channels.isEmpty();
    }

    @Override // com.nst.yourname.view.utility.epg.EPGData
    public EPGChannel addNewChannel(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        int size = this.channels.size();
        EPGChannel ePGChannel = new EPGChannel(str2, str, size, str3, str4, str5, str6, str7, str6);
        if (size > 0) {
            EPGChannel ePGChannel2 = this.channels.get(size - 1);
            ePGChannel2.setNextChannel(ePGChannel);
            ePGChannel.setPreviousChannel(ePGChannel2);
        }
        this.channels.add(ePGChannel);
        this.channelsByName.put(ePGChannel.getNum(), ePGChannel);
        return ePGChannel;
    }

    private void indexChannels() {
        this.channelsByName = Maps.newLinkedHashMap();
        for (int i = 0; i < this.channels.size(); i++) {
            EPGChannel ePGChannel = this.channels.get(i);
            this.channelsByName.put(ePGChannel.getNum(), ePGChannel);
        }
    }
}
