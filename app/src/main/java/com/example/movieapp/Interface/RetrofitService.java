package com.example.movieapp.Interface;

import com.example.movieapp.Model.MovieResponse;
import com.example.movieapp.Model.PersonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    //https://api.themoviedb.org/3/search/movie?api_key="API_KEY"""&query="NAME_MOVIE"
    //https://api.themoviedb.org/3/search/movie?api_key=960b19e2000f6101dbf7c6080898d76c&query=avenger

    //create a service to the get results and converts results into model classes
    @GET("search/movie")
    Call<MovieResponse> getMovieByQuery(@Query("api_key") String api_key, @Query("query") String query);

    //create a service for person response

    //before that create a model for person results
    @GET("search/person")
    Call<PersonResponse> getPersonByQuery(@Query("api_key") String api_key, @Query("query") String query);
}
