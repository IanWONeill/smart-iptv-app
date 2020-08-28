package com.nst.yourname.view.ijkplayer.widget.media;

import android.view.View;
import android.widget.MediaController;

public interface IMediaController {
    void hide();

    boolean isShowing();

    void setAnchorView(View view);

    void setEnabled(boolean z);

    void setMediaPlayer(MediaController.MediaPlayerControl mediaPlayerControl);

    void show();

    void show(int i);

    void showOnce(View view);
}
