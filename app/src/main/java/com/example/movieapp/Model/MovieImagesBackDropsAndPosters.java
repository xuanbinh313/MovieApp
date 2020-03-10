package com.example.movieapp.Model;

public class MovieImagesBackDropsAndPosters
{
    private Double aspect_ratio;
    private String file_path;
    private Long height;
    private String iso_639_1;
    private Double vote_average;
    private Integer vote_count;
    private Integer width;

    public MovieImagesBackDropsAndPosters() {
    }

    public MovieImagesBackDropsAndPosters(Double aspect_ratio, String file_path, Long height, String iso_639_1, Double vote_average, Integer vote_count, Integer width) {
        this.aspect_ratio = aspect_ratio;
        this.file_path = file_path;
        this.height = height;
        this.iso_639_1 = iso_639_1;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.width = width;
    }

    public Double getAspect_ratio() {
        return aspect_ratio;
    }

    public void setAspect_ratio(Double aspect_ratio) {
        this.aspect_ratio = aspect_ratio;
    }

    public String getFile_path() {
        String baseUrl = "https://image.tmdb.org/t/p/w500";
        return baseUrl + file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }
}
