package com.example.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.example.movieapp.Adapter.MovieSearchAdapter;
import com.example.movieapp.Adapter.PersonSearchAdapter;
import com.example.movieapp.Client.RetrofitClient;
import com.example.movieapp.Interface.RetrofitService;
import com.example.movieapp.Model.MovieResponse;
import com.example.movieapp.Model.MovieResponseResults;
import com.example.movieapp.Model.PersonResponse;
import com.example.movieapp.Model.PersonResponseResults;
import com.google.gson.Gson;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private NiceSpinner sourceSpinner;
    private AppCompatEditText queryEditText;
    private AppCompatButton querySearchButton;
    private RecyclerView resultsRecyclerView;
    private String movie = "By The Title";
    private String person = "By Person Name";

    private RetrofitService retrofitService;

    private MovieSearchAdapter movieSearchAdapter;
    private PersonSearchAdapter personSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //disable the keyword on starts
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        sourceSpinner = findViewById(R.id.source_spinner);
        queryEditText = findViewById(R.id.query_edit_text);
        querySearchButton = findViewById(R.id.query_search_button);
        resultsRecyclerView = findViewById(R.id.results_recycler_view);
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        Paper.init(this);

        retrofitService = RetrofitClient.getClient().create(RetrofitService.class);

        final ArrayList<String> category = new ArrayList<>();
        //set list for source spinner
        //person name means actors
        category.add(movie);
        category.add(person);

        sourceSpinner.attachDataSource(category);

        //retrieve the position at start and the set the spinner
        if (Paper.book().read("position") != null){
            int position = Paper.book().read("position");
            sourceSpinner.setSelectedIndex(position);
        }

        //set the text on edit text on create
        int position = sourceSpinner.getSelectedIndex();
        if (position == 0){
            queryEditText.setHint("Enter any movie title...");
        }
        else {
            queryEditText.setHint("Enter any person name...");
        }
        sourceSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                //when source spinner in clicked change the text of the edit text
                if (position == 0){
                    queryEditText.setHint("Enter any movie title...");
                }
                else {
                    queryEditText.setHint("Enter any person name...");
                }
            }
        });

        //retrieve the results from paper db and start
        if (Paper.book().read("cache") != null){
            String results = Paper.book().read("cache");
            if (Paper.book().read("source") != null){
                String source = Paper.book().read("source");
                if (source.equals("movie")){
                    //convert the string cache to model movie response class using gson
                    MovieResponse movieResponse = new Gson().fromJson(results,MovieResponse.class);
                    if (movieResponse != null){
                        List<MovieResponseResults> movieResponseResults = movieResponse.getResults();
                        movieSearchAdapter = new MovieSearchAdapter(MainActivity.this,movieResponseResults);
                        resultsRecyclerView.setAdapter(movieSearchAdapter);

                        //create some animation to recycler view item loading
                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MainActivity.this,R.anim.layout_slide_right);
                        resultsRecyclerView.setLayoutAnimation(controller);
                        resultsRecyclerView.scheduleLayoutAnimation();

                        //now store the results in paper database to access offline
                        Paper.book().write("cache",new Gson().toJson(movieResponse));

                        //store also the category to set the spinner at app start
                        Paper.book().write("source","movie");
                    }
                }
                else {
                    PersonResponse personResponse = new Gson().fromJson(results,PersonResponse.class);
                    if (personResponse != null){
                        List<PersonResponseResults> personResponseResults = personResponse.getResults();
                        personSearchAdapter = new PersonSearchAdapter(MainActivity.this,personResponseResults);
                        resultsRecyclerView.setAdapter(personSearchAdapter);

                        //create some animation to recycler view item loading
                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MainActivity.this,R.anim.layout_slide_right);
                        resultsRecyclerView.setLayoutAnimation(controller);
                        resultsRecyclerView.scheduleLayoutAnimation();

                        //now store the results in paper database to access offline
                        Paper.book().write("cache",new Gson().toJson(personResponse));

                        //store also the category to set the spinner at app start
                        Paper.book().write("source","person");
                    }
                }
            }
        }
        //get the query from user
        querySearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (queryEditText.getText() != null){
                    String query = queryEditText.getText().toString();
                    if (query.equals("") || query.equals(" ")){
                        Toast.makeText(MainActivity.this,"Please enter any text...",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        queryEditText.setText("");

                        //get the category to search the query. movie person
                        //replace all spaces with + in query
                        //otherwise the query only working for single word
                        //example: avengers age of ultron
                        //retrofit convert the query into: avengers%20age%20of%20ultron and will not find anything
                        //so we have to change "spaces" to "+"
                        String finalQuery = query.replaceAll(" ","+");

                        if (category.size() > 0){
                            String categoryName = category.get(sourceSpinner.getSelectedIndex());
                            if (categoryName.equals(movie)){
                                Call<MovieResponse> movieResponseCall = retrofitService.getMovieByQuery(BuildConfig.THE_MOVIE_DB_API_KEY ,finalQuery);
                                movieResponseCall.enqueue(new Callback<MovieResponse>() {
                                    @Override
                                    public void onResponse(@NonNull Call<MovieResponse> call,@NonNull Response<MovieResponse> response) {
                                        MovieResponse movieResponse = response.body();
                                        if (movieResponse != null){
                                            List<MovieResponseResults> movieResponseResults = movieResponse.getResults();
                                            movieSearchAdapter = new MovieSearchAdapter(MainActivity.this,movieResponseResults);
                                            resultsRecyclerView.setAdapter(movieSearchAdapter);

                                            //create some animation to recycler view item loading
                                            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MainActivity.this,R.anim.layout_slide_right);
                                            resultsRecyclerView.setLayoutAnimation(controller);
                                            resultsRecyclerView.scheduleLayoutAnimation();

                                            //now store the results in paper database to access offline
                                            Paper.book().write("cache",new Gson().toJson(movieResponse));

                                            //store also the category to set the spinner at app start
                                            Paper.book().write("source","movie");
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<MovieResponse> call,@NonNull Throwable t) {

                                    }
                                });
                            }
                            else {
                                Call<PersonResponse> personResponseCall = retrofitService.getPersonByQuery(BuildConfig.THE_MOVIE_DB_API_KEY ,finalQuery);
                                personResponseCall.enqueue(new Callback<PersonResponse>() {
                                    @Override
                                    public void onResponse(@NonNull Call<PersonResponse> call,@NonNull Response<PersonResponse> response) {
                                        PersonResponse personResponse = response.body();
                                        if (personResponse != null){
                                            List<PersonResponseResults> personResponseResults = personResponse.getResults();
                                            personSearchAdapter = new PersonSearchAdapter(MainActivity.this,personResponseResults);
                                            resultsRecyclerView.setAdapter(personSearchAdapter);

                                            //create some animation to recycler view item loading
                                            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MainActivity.this,R.anim.layout_slide_right);
                                            resultsRecyclerView.setLayoutAnimation(controller);
                                            resultsRecyclerView.scheduleLayoutAnimation();

                                            //now store the results in paper database to access offline
                                            Paper.book().write("cache",new Gson().toJson(personResponse));

                                            //store also the category to set the spinner at app start
                                            Paper.book().write("source","person");
                                        }
                                    }
                                    @Override
                                    public void onFailure(@NonNull Call<PersonResponse> call,@NonNull Throwable t) {

                                    }
                                });
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        //set the position of spinner in offline to retrive at start
        Paper.book().write("position",sourceSpinner.getSelectedIndex());
    }
}
