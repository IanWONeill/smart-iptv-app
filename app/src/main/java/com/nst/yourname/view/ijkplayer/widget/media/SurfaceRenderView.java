package com.nst.yourname.view.ijkplayer.widget.media;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.nst.yourname.view.ijkplayer.widget.media.IRenderView;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.ISurfaceTextureHolder;

public class SurfaceRenderView extends SurfaceView implements IRenderView {
    private MeasureHelper mMeasureHelper;
    private SurfaceCallback mSurfaceCallback;

    public static String bCBhdXR() {
        return "U";
    }

    public static String mw() {
        return "3VjaCBraW5kIG9mIGFwcHMgY291bGQgc2VuZCB0aGUgZGF0YSB0byBsZWdhbCBhdXRob3JpdGllcy4=";
    }

    @Override // com.nst.yourname.view.ijkplayer.widget.media.IRenderView
    public View getView() {
        return this;
    }

    @Override // com.nst.yourname.view.ijkplayer.widget.media.IRenderView
    public boolean shouldWaitForResize() {
        return true;
    }

    public SurfaceRenderView(Context context) {
        super(context);
        initView(context);
    }

    public SurfaceRenderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    public SurfaceRenderView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context);
    }

    @TargetApi(21)
    public SurfaceRenderView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        initView(context);
    }

    private void initView(Context context) {
        this.mMeasureHelper = new MeasureHelper(this);
        this.mSurfaceCallback = new SurfaceCallback(this);
        getHolder().addCallback(this.mSurfaceCallback);
        getHolder().setType(0);
    }

    @Override // com.nst.yourname.view.ijkplayer.widget.media.IRenderView
    public void setVideoSize(int i, int i2) {
        if (i > 0 && i2 > 0) {
            this.mMeasureHelper.setVideoSize(i, i2);
            getHolder().setFixedSize(i, i2);
            requestLayout();
        }
    }

    @Override // com.nst.yourname.view.ijkplayer.widget.media.IRenderView
    public void setVideoSampleAspectRatio(int i, int i2) {
        if (i > 0 && i2 > 0) {
            this.mMeasureHelper.setVideoSampleAspectRatio(i, i2);
            requestLayout();
        }
    }

    @Override // com.nst.yourname.view.ijkplayer.widget.media.IRenderView
    public void setVideoRotation(int i) {
        Log.e("", "SurfaceView doesn't support rotation (" + i + ")!\n");
    }

    @Override // com.nst.yourname.view.ijkplayer.widget.media.IRenderView
    public void setAspectRatio(int i) {
        this.mMeasureHelper.setAspectRatio(i);
        requestLayout();
    }

    public void onMeasure(int i, int i2) {
        this.mMeasureHelper.doMeasure(i, i2);
        setMeasuredDimension(this.mMeasureHelper.getMeasuredWidth(), this.mMeasureHelper.getMeasuredHeight());
    }

    private static final class InternalSurfaceHolder implements ISurfaceHolder {
        private SurfaceHolder mSurfaceHolder;
        private SurfaceRenderView mSurfaceView;

        @Override // com.nst.yourname.view.ijkplayer.widget.media.IRenderView.ISurfaceHolder
        @Nullable
        public SurfaceTexture getSurfaceTexture() {
            return null;
        }

        public InternalSurfaceHolder(@NonNull SurfaceRenderView surfaceRenderView, @Nullable SurfaceHolder surfaceHolder) {
            this.mSurfaceView = surfaceRenderView;
            this.mSurfaceHolder = surfaceHolder;
        }

        @Override // com.nst.yourname.view.ijkplayer.widget.media.IRenderView.ISurfaceHolder
        public void bindToMediaPlayer(IMediaPlayer iMediaPlayer) {
            if (iMediaPlayer != null) {
                if (Build.VERSION.SDK_INT >= 16 && (iMediaPlayer instanceof ISurfaceTextureHolder)) {
                    ((ISurfaceTextureHolder) iMediaPlayer).setSurfaceTexture(null);
                }
                iMediaPlayer.setDisplay(this.mSurfaceHolder);
            }
        }

        @Override // com.nst.yourname.view.ijkplayer.widget.media.IRenderView.ISurfaceHolder
        @NonNull
        public IRenderView getRenderView() {
            return this.mSurfaceView;
        }

        @Override // com.nst.yourname.view.ijkplayer.widget.media.IRenderView.ISurfaceHolder
        @Nullable
        public SurfaceHolder getSurfaceHolder() {
            return this.mSurfaceHolder;
        }

        @Override // com.nst.yourname.view.ijkplayer.widget.media.IRenderView.ISurfaceHolder
        @Nullable
        public Surface openSurface() {
            if (this.mSurfaceHolder == null) {
                return null;
            }
            return this.mSurfaceHolder.getSurface();
        }
    }

    @Override // com.nst.yourname.view.ijkplayer.widget.media.IRenderView
    public void addRenderCallback(IRenderCallback iRenderCallback) {
        this.mSurfaceCallback.addRenderCallback(iRenderCallback);
    }

    @Override // com.nst.yourname.view.ijkplayer.widget.media.IRenderView
    public void removeRenderCallback(IRenderCallback iRenderCallback) {
        this.mSurfaceCallback.removeRenderCallback(iRenderCallback);
    }

    private static final class SurfaceCallback implements SurfaceHolder.Callback {
        private int mFormat;
        private int mHeight;
        private boolean mIsFormatChanged;
        private Map<IRenderCallback, Object> mRenderCallbackMap = new ConcurrentHashMap();
        private SurfaceHolder mSurfaceHolder;
        private WeakReference<SurfaceRenderView> mWeakSurfaceView;
        private int mWidth;

        public SurfaceCallback(@NonNull SurfaceRenderView surfaceRenderView) {
            this.mWeakSurfaceView = new WeakReference<>(surfaceRenderView);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: java.util.Map<com.nst.yourname.view.ijkplayer.widget.media.IRenderView$IRenderCallback, java.lang.Object>} */
        /* JADX WARNING: Multi-variable type inference failed */
        public void addRenderCallback(@NonNull IRenderCallback iRenderCallback) {
            InternalSurfaceHolder internalSurfaceHolder;
            this.mRenderCallbackMap.put(iRenderCallback, iRenderCallback);
            if (this.mSurfaceHolder != null) {
                internalSurfaceHolder = new InternalSurfaceHolder(this.mWeakSurfaceView.get(), this.mSurfaceHolder);
                iRenderCallback.onSurfaceCreated(internalSurfaceHolder, this.mWidth, this.mHeight);
            } else {
                internalSurfaceHolder = null;
            }
            if (this.mIsFormatChanged) {
                if (internalSurfaceHolder == null) {
                    internalSurfaceHolder = new InternalSurfaceHolder(this.mWeakSurfaceView.get(), this.mSurfaceHolder);
                }
                iRenderCallback.onSurfaceChanged(internalSurfaceHolder, this.mFormat, this.mWidth, this.mHeight);
            }
        }

        public void removeRenderCallback(@NonNull IRenderCallback iRenderCallback) {
            this.mRenderCallbackMap.remove(iRenderCallback);
        }

        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            this.mSurfaceHolder = surfaceHolder;
            this.mIsFormatChanged = false;
            this.mFormat = 0;
            this.mWidth = 0;
            this.mHeight = 0;
            InternalSurfaceHolder internalSurfaceHolder = new InternalSurfaceHolder(this.mWeakSurfaceView.get(), this.mSurfaceHolder);
            for (IRenderCallback iRenderCallback : this.mRenderCallbackMap.keySet()) {
                iRenderCallback.onSurfaceCreated(internalSurfaceHolder, 0, 0);
            }
        }

        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            this.mSurfaceHolder = null;
            this.mIsFormatChanged = false;
            this.mFormat = 0;
            this.mWidth = 0;
            this.mHeight = 0;
            InternalSurfaceHolder internalSurfaceHolder = new InternalSurfaceHolder(this.mWeakSurfaceView.get(), this.mSurfaceHolder);
            for (IRenderCallback iRenderCallback : this.mRenderCallbackMap.keySet()) {
                iRenderCallback.onSurfaceDestroyed(internalSurfaceHolder);
            }
        }

        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            this.mSurfaceHolder = surfaceHolder;
            this.mIsFormatChanged = true;
            this.mFormat = i;
            this.mWidth = i2;
            this.mHeight = i3;
            InternalSurfaceHolder internalSurfaceHolder = new InternalSurfaceHolder(this.mWeakSurfaceView.get(), this.mSurfaceHolder);
            for (IRenderCallback iRenderCallback : this.mRenderCallbackMap.keySet()) {
                iRenderCallback.onSurfaceChanged(internalSurfaceHolder, i, i2, i3);
            }
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(SurfaceRenderView.class.getName());
    }

    @TargetApi(14)
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (Build.VERSION.SDK_INT >= 14) {
            accessibilityNodeInfo.setClassName(SurfaceRenderView.class.getName());
        }
    }
}
