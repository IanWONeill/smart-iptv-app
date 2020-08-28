package com.nst.yourname.view.utility.epg.misc;

import com.nst.yourname.view.utility.epg.EPG;
import com.nst.yourname.view.utility.epg.EPGData;

public class EPGDataListener {
    private EPG epg;

    public EPGDataListener(EPG epg2) {
        this.epg = epg2;
    }

    public void processData(EPGData ePGData) {
        this.epg.setEPGData(ePGData);
        this.epg.recalculateAndRedraw(null, false, null, null);
    }
}
