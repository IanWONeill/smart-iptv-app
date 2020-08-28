package com.nst.yourname.model;

import java.util.List;

public class MoviesUsingSinglton {
    private static MoviesUsingSinglton myObj;
    private List<LiveStreamsDBModel> MoviesList;

    private MoviesUsingSinglton() {
    }

    public static MoviesUsingSinglton getInstance() {
        if (myObj == null) {
            myObj = new MoviesUsingSinglton();
        }
        return myObj;
    }

    public void setMoviesList(List<LiveStreamsDBModel> list) {
        this.MoviesList = list;
    }

    public List<LiveStreamsDBModel> getMoviesList() {
        return this.MoviesList;
    }
}
