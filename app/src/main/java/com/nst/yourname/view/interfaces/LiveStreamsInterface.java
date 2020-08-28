package com.nst.yourname.view.interfaces;

import android.widget.TextView;
import com.nst.yourname.model.FavouriteDBModel;
import com.nst.yourname.model.callback.LiveStreamCategoriesCallback;
import com.nst.yourname.model.callback.LiveStreamsCallback;
import com.nst.yourname.model.callback.LiveStreamsEpgCallback;
import java.util.ArrayList;
import java.util.List;

public interface LiveStreamsInterface extends BaseInterface {
    void liveStreamCategories(List<LiveStreamCategoriesCallback> list);

    void liveStreams(List<LiveStreamsCallback> list, ArrayList<FavouriteDBModel> arrayList);

    void liveStreamsEpg(LiveStreamsEpgCallback liveStreamsEpgCallback, TextView textView, TextView textView2);
}
