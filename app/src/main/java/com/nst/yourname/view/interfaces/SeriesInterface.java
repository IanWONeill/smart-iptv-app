package com.nst.yourname.view.interfaces;

import com.google.gson.JsonElement;

public interface SeriesInterface extends BaseInterface {
    void getSeriesEpisodeInfo(JsonElement jsonElement);

    void seriesError(String str);
}
