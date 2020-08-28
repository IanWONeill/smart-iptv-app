package com.nst.yourname.view.interfaces;

import com.nst.yourname.model.callback.SearchTMDBMoviesCallback;
import com.nst.yourname.model.callback.TMDBCastsCallback;
import com.nst.yourname.model.callback.TMDBGenreCallback;
import com.nst.yourname.model.callback.TMDBTrailerCallback;

public interface SearchMoviesInterface extends BaseInterface {
    void getCasts(TMDBCastsCallback tMDBCastsCallback);

    void getGenre(TMDBGenreCallback tMDBGenreCallback);

    void getMovieInfo(SearchTMDBMoviesCallback searchTMDBMoviesCallback);

    void getTrailer(TMDBTrailerCallback tMDBTrailerCallback);
}
