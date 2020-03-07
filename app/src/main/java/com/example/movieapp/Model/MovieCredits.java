package com.example.movieapp.Model;

import java.util.List;

public class MovieCredits
{
    private Integer id;
    private List<MovieCreditsCast> movieCreditsCast;
    private List<MovieCreditsCrew> movieCreditsCrew;

    public MovieCredits() {
    }

    public MovieCredits(Integer id, List<MovieCreditsCast> movieCreditsCast, List<MovieCreditsCrew> movieCreditsCrew) {
        this.id = id;
        this.movieCreditsCast = movieCreditsCast;
        this.movieCreditsCrew = movieCreditsCrew;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MovieCreditsCast> getMovieCreditsCast() {
        return movieCreditsCast;
    }

    public void setMovieCreditsCast(List<MovieCreditsCast> movieCreditsCast) {
        this.movieCreditsCast = movieCreditsCast;
    }

    public List<MovieCreditsCrew> getMovieCreditsCrew() {
        return movieCreditsCrew;
    }

    public void setMovieCreditsCrew(List<MovieCreditsCrew> movieCreditsCrew) {
        this.movieCreditsCrew = movieCreditsCrew;
    }
}