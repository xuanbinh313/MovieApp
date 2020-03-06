package com.example.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.movieapp.Client.RetrofitClient;
import com.example.movieapp.Interface.RetrofitService;
import com.example.movieapp.Model.MovieDetails;
import com.example.movieapp.Model.MovieDetailsGenres;
import com.example.movieapp.Model.MovieDetailsProductionCountries;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    private LinearLayoutCompat movieDetailOriginalTitleLayout;
    private LinearLayoutCompat movieDetailOriginalLanguageLayout;
    private LinearLayoutCompat movieDetailAdultLayout;
    private LinearLayoutCompat movieDetailStatusLayout;
    private LinearLayoutCompat movieDetailRuntimeLayout;
    private LinearLayoutCompat movieDetailBudgetLayout;
    private LinearLayoutCompat movieDetailRevenueLayout;
    private LinearLayoutCompat movieDetailGenreLayout;
    private LinearLayoutCompat movieDetailProductionCountryLayout;
    private LinearLayoutCompat movieDetailReleaseDateLayout;
    private LinearLayoutCompat movieDetailHomepageLayout;
    private LinearLayoutCompat movieDetailOverviewLayout;

    private AppCompatTextView movieDetailOriginalTitle;
    private AppCompatTextView movieDetailOriginalLanguage;
    private AppCompatTextView movieDetailAdult;
    private AppCompatTextView movieDetailStatus;
    private AppCompatTextView movieDetailRuntime;
    private AppCompatTextView movieDetailBudget;
    private AppCompatTextView movieDetailRevenue;
    private AppCompatTextView movieDetailGenre;
    private AppCompatTextView movieDetailProductionCountry;
    private AppCompatTextView movieDetailReleaseDate;
    private AppCompatTextView movieDetailHomepage;
    private AppCompatTextView movieDetailOverview;

    private KenBurnsView movieDetailPosterImageView;
    private CircleImageView movieDetailBackDropPosterCircleImageView;
    private ArcProgress movieRatingBar;
    private AppCompatTextView movieDetailTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        RetrofitService retrofitService = RetrofitClient.getClient().create(RetrofitService.class);

        movieDetailOriginalTitleLayout = findViewById(R.id.movie_detail_original_title_layout);
        movieDetailOriginalLanguageLayout = findViewById(R.id.movie_detail_original_language_layout);
        movieDetailAdultLayout = findViewById(R.id.movie_detail_adult_layout);
        movieDetailStatusLayout = findViewById(R.id.movie_detail_status_layout);
        movieDetailRuntimeLayout = findViewById(R.id.movie_detail_runtime_layout);
        movieDetailBudgetLayout = findViewById(R.id.movie_detail_budget_layout);
        movieDetailRevenueLayout = findViewById(R.id.movie_detail_revenue_layout);
        movieDetailGenreLayout = findViewById(R.id.movie_detail_genre_layout);
        movieDetailProductionCountryLayout = findViewById(R.id.movie_detail_production_country_layout);
        movieDetailReleaseDateLayout = findViewById(R.id.movie_detail_release_date_layout);
        movieDetailHomepageLayout = findViewById(R.id.movie_detail_homepage_layout);
        movieDetailOverviewLayout = findViewById(R.id.movie_detail_overview_layout);

        movieDetailOriginalTitle = findViewById(R.id.movie_detail_original_title);
        movieDetailOriginalLanguage = findViewById(R.id.movie_detail_original_language);
        movieDetailAdult = findViewById(R.id.movie_detail_adult);
        movieDetailStatus = findViewById(R.id.movie_detail_status);
        movieDetailRuntime = findViewById(R.id.movie_detail_runtime);
        movieDetailBudget = findViewById(R.id.movie_detail_budget);
        movieDetailRevenue = findViewById(R.id.movie_detail_revenue);
        movieDetailGenre = findViewById(R.id.movie_detail_genre);
        movieDetailProductionCountry = findViewById(R.id.movie_detail_production_country);
        movieDetailReleaseDate = findViewById(R.id.movie_detail_release_date);
        movieDetailHomepage = findViewById(R.id.movie_detail_homepage);
        movieDetailOverview = findViewById(R.id.movie_detail_overview);

        movieDetailPosterImageView = findViewById(R.id.movie_detail_poster_image_view);
        movieDetailBackDropPosterCircleImageView = findViewById(R.id.movie_detail_poster_circle_image_view);
        movieRatingBar = findViewById(R.id.movie_detail_rating_bar);
        movieDetailTitle = findViewById(R.id.movie_detail_title);

        if (intent != null && intent.getExtras() != null)
        {
            if (intent.getExtras().getString("id") != null)
            {
                int id = Integer.parseInt(intent.getExtras().getString("id"));
                Call<MovieDetails> movieDetailsCall = retrofitService.getMovieDetailsById(id,BuildConfig.THE_MOVIE_DB_API_KEY);
                movieDetailsCall.enqueue(new Callback<MovieDetails>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieDetails> call,@NonNull Response<MovieDetails> response) {
                        MovieDetails movieDetailsResponse = response.body();
                        if (movieDetailsResponse != null)
                        {
                            prepareMovieDetails(movieDetailsResponse);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieDetails> call,@NonNull Throwable t) {

                    }
                });
            }
        }
    }

    private void prepareMovieDetails(MovieDetails movieDetailsResponse)
    {
        Double voteAverage = movieDetailsResponse.getVote_average() * 10;
        String title = movieDetailsResponse.getTitle();
        String originalLanguage = movieDetailsResponse.getOriginal_language();
        boolean adult = movieDetailsResponse.getAdult();
        String originalTitle = movieDetailsResponse.getOriginal_title();
        String status = movieDetailsResponse.getStatus();
        Integer runtime = movieDetailsResponse.getRuntime();
        Integer budget = movieDetailsResponse.getBudget();
        Integer revenue = movieDetailsResponse.getRevenue();
        List<MovieDetailsGenres> movieDetailsGenresList = movieDetailsResponse.getGenres();
        List<MovieDetailsProductionCountries> movieDetailsProductionCountriesList = movieDetailsResponse.getProduction_countries();
        String releaseDate = movieDetailsResponse.getRelease_date();
        String homepage = movieDetailsResponse.getHomepage();
        String overview = movieDetailsResponse.getOverview();
        String posterPath = movieDetailsResponse.getPoster_path();
        String backDropPath = movieDetailsResponse.getBackdrop_path();

        Picasso.with(MovieDetailActivity.this).load(posterPath).into(movieDetailPosterImageView);
        Picasso.with(MovieDetailActivity.this).load(backDropPath).into(movieDetailBackDropPosterCircleImageView);
        int rating = voteAverage.intValue();
        movieRatingBar.setProgress(rating);

        movieDetailTitle.setText(title);
        if (originalTitle != null)
        {
            if (originalTitle.length() > 0)
            {
            movieDetailOriginalTitle.setText(originalTitle);
            movieDetailOriginalTitleLayout.setVisibility(View.VISIBLE);
            }
            else
            {
                movieDetailOriginalTitleLayout.setVisibility(View.GONE);
            }
        }
        else
        {
            movieDetailOriginalTitleLayout.setVisibility(View.GONE);
        }

        if (originalLanguage != null)
        {
            if (originalLanguage.length() > 0)
            {
                movieDetailOriginalLanguage.setText(originalLanguage);
                movieDetailOriginalLanguageLayout.setVisibility(View.VISIBLE);
            }
            else
            {
                movieDetailOriginalLanguageLayout.setVisibility(View.GONE);
            }
        }
        else
        {
            movieDetailOriginalLanguageLayout.setVisibility(View.GONE);
        }

        if (adult)
        {
            movieDetailAdult.setText("Yes");
            movieDetailAdultLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            movieDetailAdult.setText("No");
            movieDetailAdultLayout.setVisibility(View.VISIBLE);
        }

        if (status != null)
        {
            if (status.length() > 0)
            {
                movieDetailStatus.setText(originalLanguage);
                movieDetailStatusLayout.setVisibility(View.VISIBLE);
            }
            else
            {
                movieDetailStatusLayout.setVisibility(View.GONE);
            }
        }
        else
        {
            movieDetailStatusLayout.setVisibility(View.GONE);
        }

        if (runtime != null)
        {
            if (runtime != 0)
            {
                movieDetailRuntime.setText(String.valueOf(runtime));
                movieDetailRuntimeLayout.setVisibility(View.VISIBLE);
            }
            else
            {
                movieDetailRuntimeLayout.setVisibility(View.GONE);
            }
        }
        else
        {
            movieDetailRuntimeLayout.setVisibility(View.GONE);
        }

        if (budget != null)
        {
            if (budget != 0)
            {
                movieDetailBudget.setText(String.valueOf(budget));
                movieDetailBudgetLayout.setVisibility(View.VISIBLE);
            }
            else
            {
                movieDetailBudgetLayout.setVisibility(View.GONE);
            }
        }
        else
        {
            movieDetailBudgetLayout.setVisibility(View.GONE);
        }

        if (revenue != null)
        {
            if (revenue != 0)
            {
                movieDetailRevenue.setText(String.valueOf(revenue));
                movieDetailRevenueLayout.setVisibility(View.VISIBLE);
            }
            else
            {
                movieDetailRevenueLayout.setVisibility(View.GONE);
            }
        }
        else
        {
            movieDetailRevenueLayout.setVisibility(View.GONE);
        }

        if (movieDetailsGenresList != null && movieDetailsGenresList.size() > 0)
        {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i=0; i < movieDetailsGenresList.size(); i++)
            {
                if (i == movieDetailsGenresList.size()-1)
                {
                    stringBuilder.append(movieDetailsGenresList.get(i).getName());
                }
                else
                {
                    stringBuilder.append(movieDetailsGenresList.get(i).getName()).append(", ");
                }
            }
            movieDetailGenre.setText(stringBuilder.toString());
            movieDetailGenreLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            movieDetailGenreLayout.setVisibility(View.GONE);
        }

        if (movieDetailsProductionCountriesList != null && movieDetailsProductionCountriesList.size() > 0)
        {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i=0; i < movieDetailsProductionCountriesList.size(); i++)
            {
                if (i == movieDetailsProductionCountriesList.size()-1)
                {
                    stringBuilder.append(movieDetailsProductionCountriesList.get(i).getName());
                }
                else
                {
                    stringBuilder.append(movieDetailsProductionCountriesList.get(i).getName()).append(", ");
                }
            }
            movieDetailProductionCountry.setText(stringBuilder.toString());
            movieDetailProductionCountryLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            movieDetailProductionCountryLayout.setVisibility(View.GONE);
        }

        if (releaseDate != null)
        {
            if (releaseDate.length() > 0)
            {
                movieDetailReleaseDate.setText(releaseDate);
                movieDetailReleaseDateLayout.setVisibility(View.VISIBLE);
            }
            else
            {
                movieDetailReleaseDateLayout.setVisibility(View.GONE);
            }
        }
        else
        {
            movieDetailReleaseDateLayout.setVisibility(View.GONE);
        }

        if (overview != null)
        {
            if (overview.length() > 0)
            {
                movieDetailOverview.setText(overview);
                movieDetailOverviewLayout.setVisibility(View.VISIBLE);
            }
            else
            {
                movieDetailOverviewLayout.setVisibility(View.GONE);
            }
        }
        else
        {
            movieDetailOverviewLayout.setVisibility(View.GONE);
        }

        if (homepage != null)
        {
            if (homepage.length() > 0)
            {
                movieDetailHomepage.setText(homepage);
                Log.d("AAA",homepage);
                movieDetailHomepageLayout.setVisibility(View.VISIBLE);
            }
            else
            {
                movieDetailHomepageLayout.setVisibility(View.GONE);
            }
        }
        else
        {
            movieDetailHomepageLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
