package com.nst.yourname.view.utility;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;
import java.lang.reflect.Field;

public class NonSwipeableViewPager extends ViewPager {
    @Override // android.support.v4.view.ViewPager
    public boolean executeKeyEvent(KeyEvent keyEvent) {
        return false;
    }

    @Override // android.support.v4.view.ViewPager
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override // android.support.v4.view.ViewPager
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public NonSwipeableViewPager(Context context) {
        super(context);
        setMyScroller();
    }

    public NonSwipeableViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setMyScroller();
    }

    private void setMyScroller() {
        try {
            Field declaredField = ViewPager.class.getDeclaredField("mScroller");
            declaredField.setAccessible(true);
            declaredField.set(this, new MyScroller(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyScroller extends Scroller {
        public MyScroller(Context context) {
            super(context, new DecelerateInterpolator());
        }

        public void startScroll(int i, int i2, int i3, int i4, int i5) {
            super.startScroll(i, i2, i3, i4, 350);
        }
    }
}
