package com.nst.yourname.view.utility;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;
import com.nst.yourname.R;

public class ListViewMaxHeight extends ListView {
    private final int maxHeight;

    public ListViewMaxHeight(Context context) {
        this(context, null);
    }

    public ListViewMaxHeight(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ListViewMaxHeight(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.ListViewMaxHeight);
            this.maxHeight = obtainStyledAttributes.getDimensionPixelSize(0, Integer.MAX_VALUE);
            obtainStyledAttributes.recycle();
            return;
        }
        this.maxHeight = 0;
    }

    public void onMeasure(int i, int i2) {
        int size = MeasureSpec.getSize(i2);
        if (this.maxHeight > 0 && this.maxHeight < size) {
            i2 = MeasureSpec.makeMeasureSpec(this.maxHeight, MeasureSpec.getMode(i2));
        }
        super.onMeasure(i, i2);
    }
}
