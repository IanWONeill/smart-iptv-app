package com.nst.yourname.miscelleneious;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class FullScreenVideoView extends VideoView {
    public FullScreenVideoView(Context context) {
        super(context);
    }

    public FullScreenVideoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FullScreenVideoView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(i, i2);
    }
}
