package com.nst.yourname.model;

import android.content.Context;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LiveStreamCategoryIdDBModel implements Serializable {
    private int id;
    private String liveStreamCategoryID;
    private String liveStreamCategoryName;
    private int liveStreamCounter;
    private int parentId;

    public int getParentId() {
        return this.parentId;
    }

    public void setParentId(int i) {
        this.parentId = i;
    }

    public LiveStreamCategoryIdDBModel() {
    }

    public LiveStreamCategoryIdDBModel(String str, String str2, int i) {
        this.liveStreamCategoryID = str;
        this.liveStreamCategoryName = str2;
        this.parentId = i;
    }

    public int getLiveStreamCounter() {
        return this.liveStreamCounter;
    }

    public void setLiveStreamCounter(int i) {
        this.liveStreamCounter = i;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getLiveStreamCategoryID() {
        return this.liveStreamCategoryID;
    }

    public void setLiveStreamCategoryID(String str) {
        this.liveStreamCategoryID = str;
    }

    public String getLiveStreamCategoryName() {
        return this.liveStreamCategoryName;
    }

    public void setLiveStreamCategoryName(String str) {
        this.liveStreamCategoryName = str;
    }

    public static List<LiveStreamCategoryIdDBModel> createMovies(int i, Context context, LiveStreamDBHandler liveStreamDBHandler, int i2, int i3) {
        return listOfMOview(liveStreamDBHandler.getAllMovieCategoriesHavingParentIdZero(i2, i3), context, liveStreamDBHandler);
    }

    public static ArrayList<LiveStreamCategoryIdDBModel> createChannels(int i, Context context, LiveStreamDBHandler liveStreamDBHandler, int i2, int i3) {
        return listOfChannels(liveStreamDBHandler.getLiveCategoriesinRange(i2, i3), context, liveStreamDBHandler);
    }

    public static ArrayList<LiveStreamCategoryIdDBModel> listOfMOview(ArrayList<LiveStreamCategoryIdDBModel> arrayList, Context context, LiveStreamDBHandler liveStreamDBHandler) {
        if (context == null) {
            return null;
        }
        LiveStreamDBHandler liveStreamDBHandler2 = new LiveStreamDBHandler(context);
        ArrayList<LiveStreamCategoryIdDBModel> arrayList2 = new ArrayList<>();
        Iterator<LiveStreamCategoryIdDBModel> it = arrayList.iterator();
        while (it.hasNext()) {
            LiveStreamCategoryIdDBModel next = it.next();
            String liveStreamCategoryID2 = next.getLiveStreamCategoryID();
            ArrayList<LiveStreamsDBModel> allLiveStreasWithCategoryId = liveStreamDBHandler2.getAllLiveStreasWithCategoryId(liveStreamCategoryID2, AppConst.EVENT_TYPE_MOVIE);
            Iterator<LiveStreamCategoryIdDBModel> it2 = liveStreamDBHandler2.getAllMovieCategoriesHavingParentIdNotZero(liveStreamCategoryID2).iterator();
            while (true) {
                if (it2.hasNext()) {
                    if (liveStreamDBHandler2.getAllLiveStreasWithCategoryId(String.valueOf(it2.next().getLiveStreamCategoryID()), AppConst.EVENT_TYPE_MOVIE).size() > 0) {
                        arrayList2.add(next);
                        break;
                    }
                } else {
                    break;
                }
            }
            if (allLiveStreasWithCategoryId.size() > 0) {
                arrayList2.add(next);
            }
        }
        return arrayList2;
    }

    public static ArrayList<LiveStreamCategoryIdDBModel> listOfChannels(ArrayList<LiveStreamCategoryIdDBModel> arrayList, Context context, LiveStreamDBHandler liveStreamDBHandler) {
        if (context == null) {
            return null;
        }
        LiveStreamDBHandler liveStreamDBHandler2 = new LiveStreamDBHandler(context);
        ArrayList<LiveStreamCategoryIdDBModel> arrayList2 = new ArrayList<>();
        Iterator<LiveStreamCategoryIdDBModel> it = arrayList.iterator();
        while (it.hasNext()) {
            LiveStreamCategoryIdDBModel next = it.next();
            String liveStreamCategoryID2 = next.getLiveStreamCategoryID();
            ArrayList<LiveStreamsDBModel> allLiveStreasWithCategoryId = liveStreamDBHandler2.getAllLiveStreasWithCategoryId(liveStreamCategoryID2, "live");
            Iterator<LiveStreamCategoryIdDBModel> it2 = liveStreamDBHandler2.getAllMovieCategoriesHavingParentIdNotZero(liveStreamCategoryID2).iterator();
            while (true) {
                if (it2.hasNext()) {
                    if (liveStreamDBHandler2.getAllLiveStreasWithCategoryId(String.valueOf(it2.next().getLiveStreamCategoryID()), "live").size() > 0) {
                        arrayList2.add(next);
                        break;
                    }
                } else {
                    break;
                }
            }
            if (allLiveStreasWithCategoryId.size() > 0) {
                arrayList2.add(next);
            }
        }
        return arrayList2;
    }
}
