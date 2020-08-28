package com.nst.yourname.view.interfaces;

import com.nst.yourname.model.callback.SearchTMDBTVShowsCallback;
import com.nst.yourname.model.callback.TMDBTVShowsInfoCallback;
import com.nst.yourname.model.callback.TMDBTrailerCallback;

public interface SearchTVShowsInterface extends BaseInterface {
    void getTVShowsGenreAndDirector(TMDBTVShowsInfoCallback tMDBTVShowsInfoCallback);

    void getTVShowsInfo(SearchTMDBTVShowsCallback searchTMDBTVShowsCallback);

    void getTrailerTVShows(TMDBTrailerCallback tMDBTrailerCallback);
}
