package com.example.movieapp.Model;

import java.util.List;

class PersonResponseResultsKnowFor {
    private String release_date;
    private Long id;
    private Long vote_count;
    private boolean video;
    private String media_type;
    private float vote_average;
    private String title;
    private List<Integer> genre_ids;
    private String original_title;
    private String original_language;
    private boolean adult;
    private String backdrop_path;
    private String overview;
    private String poster_path;

    public PersonResponseResultsKnowFor() {
    }

    public PersonResponseResultsKnowFor(String release_date, Long id, Long vote_count, boolean video, String media_type, float vote_average, String title, List<Integer> genre_ids, String original_title, String original_language, boolean adult, String backdrop_path, String overview, String poster_path) {
        this.release_date = release_date;
        this.id = id;
        this.vote_count = vote_count;
        this.video = video;
        this.media_type = media_type;
        this.vote_average = vote_average;
        this.title = title;
        this.genre_ids = genre_ids;
        this.original_title = original_title;
        this.original_language = original_language;
        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVote_count() {
        return vote_count;
    }

    public void setVote_count(Long vote_count) {
        this.vote_count = vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    //change some line getPoster_path
    public String getPoster_path() {
        //create a baseUrl to this poster
        String baseUrl = "https://image.tmdb.org/t/p/w500";
        return baseUrl + poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
}

