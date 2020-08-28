package com.nst.yourname.view.utility.epg.domain;

import android.os.Parcelable;
import android.view.View;

public class EPGState extends View.BaseSavedState {
    private EPGEvent currentEvent = null;

    public EPGState(Parcelable parcelable) {
        super(parcelable);
    }

    public EPGEvent getCurrentEvent() {
        return this.currentEvent;
    }

    public void setCurrentEvent(EPGEvent ePGEvent) {
        this.currentEvent = ePGEvent;
    }
}
