package com.nst.yourname.view.utility;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import com.nst.yourname.R;

public class LoadingGearSpinner extends ImageView {
    private static final int IMAGE_RESOURCE_ID = 2131230988;
    private static final int ROTATE_ANIMATION_DURATION = 1000;

    public LoadingGearSpinner(Context context) {
        super(context, null);
    }

    public LoadingGearSpinner(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public LoadingGearSpinner(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        setImageResource(R.drawable.gear_orange);
        startAnimation();
    }

    private void startAnimation() {
        clearAnimation();
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setRepeatCount(-1);
        startAnimation(rotateAnimation);
    }

    public void setVisibility(int i) {
        if (i == 0) {
            startAnimation();
        } else {
            clearAnimation();
        }
        super.setVisibility(i);
    }
}
