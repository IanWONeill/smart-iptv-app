package com.nst.yourname.view.ijkplayer.widget.media;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.MediaController;
import java.util.ArrayList;
import java.util.Iterator;

public class AndroidMediaController extends MediaController implements IMediaController {
    private ActionBar mActionBar;
    private ArrayList<View> mShowOnceArray = new ArrayList<>();

    private void initView(Context context) {
    }

    public AndroidMediaController(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    public AndroidMediaController(Context context, boolean z) {
        super(context, z);
        initView(context);
    }

    public AndroidMediaController(Context context) {
        super(context);
        initView(context);
    }

    public void setSupportActionBar(@Nullable ActionBar actionBar) {
        this.mActionBar = actionBar;
        if (isShowing()) {
            actionBar.show();
        } else {
            actionBar.hide();
        }
    }

    @Override // com.nst.yourname.view.ijkplayer.widget.media.IMediaController
    public void show() {
        super.show();
        if (this.mActionBar != null) {
            this.mActionBar.show();
        }
    }

    @Override // com.nst.yourname.view.ijkplayer.widget.media.IMediaController
    public void hide() {
        super.hide();
        if (this.mActionBar != null) {
            this.mActionBar.hide();
        }
        Iterator<View> it = this.mShowOnceArray.iterator();
        while (it.hasNext()) {
            it.next().setVisibility(8);
        }
        this.mShowOnceArray.clear();
    }

    @Override // com.nst.yourname.view.ijkplayer.widget.media.IMediaController
    public void showOnce(@NonNull View view) {
        this.mShowOnceArray.add(view);
        view.setVisibility(0);
        show();
    }
}
