package com.example.movieapp.Model;

import java.util.List;

public class PersonResponseResults {
    private Double popularity;
    private String known_for_department;
    private Integer gender;
    private Integer id;
    private String profile_path;
    private boolean adult;
    private List<PersonResponseResultsKnowFor> known_for;
    private String name;

    public PersonResponseResults() {
    }

    public PersonResponseResults(Double popularity, String known_for_department, Integer gender, Integer id, String profile_path, boolean adult, List<PersonResponseResultsKnowFor> known_for, String name) {
        this.popularity = popularity;
        this.known_for_department = known_for_department;
        this.gender = gender;
        this.id = id;
        this.profile_path = profile_path;
        this.adult = adult;
        this.known_for = known_for;
        this.name = name;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getKnown_for_department() {
        return known_for_department;
    }

    public void setKnown_for_department(String known_for_department) {
        this.known_for_department = known_for_department;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //change some line getPoster_path
    public String getProfile_path() {
        //create a baseUrl to this poster
        String baseUrl = "https://image.tmdb.org/t/p/w500";
        return baseUrl + profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public List<PersonResponseResultsKnowFor> getKnown_for() {
        return known_for;
    }

    public void setKnown_for(List<PersonResponseResultsKnowFor> known_for) {
        this.known_for = known_for;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
