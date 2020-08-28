package com.nst.yourname.view.inbuiltsmartersplayer;

import android.view.View;
import java.lang.ref.WeakReference;

public final class MeasureHelper {
    private int mCurrentAspectRatio = 0;
    private int mMeasuredHeight;
    private int mMeasuredWidth;
    private int mVideoHeight;
    private int mVideoRotationDegree;
    private int mVideoSarDen;
    private int mVideoSarNum;
    private int mVideoWidth;
    private WeakReference<View> mWeakView;

    public MeasureHelper(View view) {
        this.mWeakView = new WeakReference<>(view);
    }

    public View getView() {
        if (this.mWeakView == null) {
            return null;
        }
        return this.mWeakView.get();
    }

    public void setVideoSize(int i, int i2) {
        this.mVideoWidth = i;
        this.mVideoHeight = i2;
    }

    public void setVideoSampleAspectRatio(int i, int i2) {
        this.mVideoSarNum = i;
        this.mVideoSarDen = i2;
    }

    public void setVideoRotation(int i) {
        this.mVideoRotationDegree = i;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0101, code lost:
        if (r1 > r9) goto L_0x0125;
     */
    public void doMeasure(int i, int i2) {
        int i3;
        float f;
        if (this.mVideoRotationDegree == 90 || this.mVideoRotationDegree == 270) {
            i2 = i;
            i = i2;
        }
        int defaultSize = View.getDefaultSize(this.mVideoWidth, i);
        int defaultSize2 = View.getDefaultSize(this.mVideoHeight, i2);
        if (this.mCurrentAspectRatio != 3) {
            if (this.mVideoWidth > 0 && this.mVideoHeight > 0) {
                int mode = View.MeasureSpec.getMode(i);
                i = View.MeasureSpec.getSize(i);
                int mode2 = View.MeasureSpec.getMode(i2);
                i2 = View.MeasureSpec.getSize(i2);
                if (mode == Integer.MIN_VALUE && mode2 == Integer.MIN_VALUE) {
                    float f2 = (float) i;
                    float f3 = (float) i2;
                    float f4 = f2 / f3;
                    switch (this.mCurrentAspectRatio) {
                        case 4:
                            f = 1.7777778f;
                            if (this.mVideoRotationDegree == 90 || this.mVideoRotationDegree == 270) {
                                f = 0.5625f;
                                break;
                            }
                        case 5:
                            f = 1.3333334f;
                            if (this.mVideoRotationDegree == 90 || this.mVideoRotationDegree == 270) {
                                f = 0.75f;
                                break;
                            }
                        default:
                            f = ((float) this.mVideoWidth) / ((float) this.mVideoHeight);
                            if (this.mVideoSarNum > 0 && this.mVideoSarDen > 0) {
                                f = (f * ((float) this.mVideoSarNum)) / ((float) this.mVideoSarDen);
                                break;
                            }
                    }
                    boolean z = f > f4;
                    switch (this.mCurrentAspectRatio) {
                        case 0:
                        case 4:
                        case 5:
                            if (!z) {
                                i = (int) (f3 * f);
                                break;
                            } else {
                                i2 = (int) (f2 / f);
                                break;
                            }
                        case 1:
                            if (!z) {
                                i2 = (int) (f2 / f);
                                break;
                            } else {
                                i = (int) (f3 * f);
                                break;
                            }
                        case 2:
                        case 3:
                        default:
                            if (!z) {
                                int min = Math.min(this.mVideoHeight, i2);
                                i2 = min;
                                i = (int) (((float) min) * f);
                                break;
                            } else {
                                i = Math.min(this.mVideoWidth, i);
                                i2 = (int) (((float) i) / f);
                                break;
                            }
                    }
                } else if (mode == 1073741824 && mode2 == 1073741824) {
                    if (this.mVideoWidth * i2 < this.mVideoHeight * i) {
                        i = (this.mVideoWidth * i2) / this.mVideoHeight;
                    } else if (this.mVideoWidth * i2 > this.mVideoHeight * i) {
                        i2 = (this.mVideoHeight * i) / this.mVideoWidth;
                    }
                } else if (mode == 1073741824) {
                    int i4 = (this.mVideoHeight * i) / this.mVideoWidth;
                    if (mode2 != Integer.MIN_VALUE || i4 <= i2) {
                        i2 = i4;
                    }
                } else {
                    if (mode2 == 1073741824) {
                        i3 = (this.mVideoWidth * i2) / this.mVideoHeight;
                        if (mode == Integer.MIN_VALUE) {
                        }
                    } else {
                        i3 = this.mVideoWidth;
                        int i5 = this.mVideoHeight;
                        if (mode2 != Integer.MIN_VALUE || i5 <= i2) {
                            i2 = i5;
                        } else {
                            i3 = (this.mVideoWidth * i2) / this.mVideoHeight;
                        }
                        if (mode == Integer.MIN_VALUE && i3 > i) {
                            i2 = (this.mVideoHeight * i) / this.mVideoWidth;
                        }
                    }
                    i = i3;
                }
            } else {
                i = defaultSize;
                i2 = defaultSize2;
            }
        }
        this.mMeasuredWidth = i;
        this.mMeasuredHeight = i2;
    }

    public int getMeasuredWidth() {
        return this.mMeasuredWidth;
    }

    public int getMeasuredHeight() {
        return this.mMeasuredHeight;
    }

    public void setAspectRatio(int i) {
        this.mCurrentAspectRatio = i;
    }
}
