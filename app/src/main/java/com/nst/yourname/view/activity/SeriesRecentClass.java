package com.nst.yourname.view.activity;

import android.content.Context;
import com.nst.yourname.model.EpisodesUsingSinglton;
import com.nst.yourname.model.callback.GetEpisdoeDetailsCallback;
import com.nst.yourname.model.database.SeriesRecentWatchDatabase;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.view.inbuiltsmartersplayer.VideoInfo;
import java.util.ArrayList;
import java.util.List;

public class SeriesRecentClass {
    private static SeriesRecentWatchDatabase recentWatchDBHandler1;
    Context context;

    public SeriesRecentClass(Context context2) {
        this.context = context2;
        recentWatchDBHandler1 = new SeriesRecentWatchDatabase(context2);
    }

    public int checkRecentWatch(String str) {
        VideoInfo.getInstance().setEpisodeId(str);
        return recentWatchDBHandler1.isStreamAvailable(str);
    }

    public void updateSeriesElapsedStatus(String str, long j) {
        if (recentWatchDBHandler1 != null) {
            recentWatchDBHandler1.updateSeriesRecentWatch(str, Long.valueOf(j));
        }
    }

    public void setSeriesRecentWatched() {
        if (VideoInfo.getInstance() != null && streamCheckSeries(VideoInfo.getInstance().getEpisodeId(), SharepreferenceDBHandler.getUserID(this.context)) == 0) {
            setRecentWatchDB(this.context, EpisodesUsingSinglton.getInstance().getEpisodeList(), VideoInfo.getInstance().getCurrentWindowIndex());
        }
    }

    public int streamCheckSeries(String str, int i) {
        return recentWatchDBHandler1.isStreamAvailable(str);
    }

    private void setRecentWatchDB(Context context2, List<GetEpisdoeDetailsCallback> list, int i) {
        if (recentWatchDBHandler1.getSeriesRecentwatchmCount() >= 100) {
            new ArrayList();
            ArrayList<GetEpisdoeDetailsCallback> allSeriesRecentWatch = recentWatchDBHandler1.getAllSeriesRecentWatch("getOnedata");
            if (allSeriesRecentWatch != null && allSeriesRecentWatch.isEmpty()) {
                recentWatchDBHandler1.deleteSeriesRecentwatch(allSeriesRecentWatch.get(0).getId());
            }
            setDataIntoRecentWatchDB(context2, list, i);
            return;
        }
        setDataIntoRecentWatchDB(context2, list, i);
    }

    private void setDataIntoRecentWatchDB(Context context2, List<GetEpisdoeDetailsCallback> list, int i) {
        new SeriesRecentWatchDatabase(context2).addAllSeriesRecentWatch(list.get(i));
    }
}
