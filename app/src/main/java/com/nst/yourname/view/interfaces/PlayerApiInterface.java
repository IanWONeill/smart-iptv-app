package com.nst.yourname.view.interfaces;

import com.nst.yourname.model.callback.GetSeriesStreamCallback;
import com.nst.yourname.model.callback.GetSeriesStreamCategoriesCallback;
import com.nst.yourname.model.callback.LiveStreamCategoriesCallback;
import com.nst.yourname.model.callback.LiveStreamsCallback;
import com.nst.yourname.model.callback.VodCategoriesCallback;
import com.nst.yourname.model.callback.VodStreamsCallback;
import java.util.List;

public interface PlayerApiInterface extends BaseInterfaceV2 {
    void getLiveStreamCatFailed(String str);

    void getLiveStreamCategories(List<LiveStreamCategoriesCallback> list);

    void getLiveStreamFailed(String str);

    void getLiveStreams(List<LiveStreamsCallback> list);

    void getSeriesCategories(List<GetSeriesStreamCategoriesCallback> list);

    void getSeriesStreamCatFailed(String str);

    void getSeriesStreams(List<GetSeriesStreamCallback> list);

    void getSeriesStreamsFailed(String str);

    void getVODStreamCatFailed(String str);

    void getVODStreamCategories(List<VodCategoriesCallback> list);

    void getVODStreams(List<VodStreamsCallback> list);

    void getVODStreamsFailed(String str);
}
