package com.example.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.example.movieapp.Adapter.PersonProfileImagesAdapter;
import com.example.movieapp.Client.RetrofitClient;
import com.example.movieapp.Interface.RetrofitService;
import com.example.movieapp.Model.PersonDetails;
import com.example.movieapp.Model.PersonImages;
import com.example.movieapp.Model.PersonImagesProfiles;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonDetailActivity extends AppCompatActivity {
    private KenBurnsView personDetailProfileImageView;
    private LinearLayoutCompat personDetailAlsoKnownAsLayout;
    private LinearLayoutCompat personDetailBirthdayLayout;
    private LinearLayoutCompat personDetailPlaceOfBirthLayout;
    private LinearLayoutCompat personDetailDeathdayLayout;
    private LinearLayoutCompat personDetailDepartmentLayout;
    private LinearLayoutCompat personDetailHomepageLayout;
    private LinearLayoutCompat personDetailBiographyLayout;
    private LinearLayoutCompat personDetailProfileImagesLayout;

    private AppCompatTextView personDetailAlsoKnownAs;
    private AppCompatTextView personDetailBirthday;
    private AppCompatTextView personDetailPlaceOfBirth;
    private AppCompatTextView personDetailDeathday;
    private AppCompatTextView personDetailDepartment;
    private AppCompatTextView personDetailHomepage;
    private AppCompatTextView personDetailBiography;

    private AppCompatTextView personalDetailName;

    private RecyclerView personDetailProfileImagesRecyclerView;
    private PersonProfileImagesAdapter personProfileImagesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);
        Intent intent = getIntent();

        RetrofitService retrofitService = RetrofitClient.getClient().create(RetrofitService.class);

        personDetailAlsoKnownAsLayout = findViewById(R.id.person_detail_also_known_as_layout);
        personDetailBirthdayLayout = findViewById(R.id.person_detail_birthday_layout);
        personDetailPlaceOfBirthLayout = findViewById(R.id.person_detail_place_of_birth_layout);
        personDetailDeathdayLayout = findViewById(R.id.person_detail_deathday_layout);
        personDetailDepartmentLayout = findViewById(R.id.person_detail_department_layout);
        personDetailHomepageLayout = findViewById(R.id.person_detail_homepage_layout);
        personDetailBiographyLayout = findViewById(R.id.person_detail_biography_layout);
        personDetailProfileImagesLayout = findViewById(R.id.person_detail_profile_images_layout);

        personDetailAlsoKnownAs = findViewById(R.id.person_detail_also_known_as);
        personDetailBirthday = findViewById(R.id.person_detail_birthday);
        personDetailPlaceOfBirth = findViewById(R.id.person_detail_place_of_birth);
        personDetailDeathday = findViewById(R.id.person_detail_deathday);
        personDetailDepartment = findViewById(R.id.person_detail_department);
        personDetailHomepage = findViewById(R.id.person_detail_homepage);
        personDetailBiography = findViewById(R.id.person_detail_biography);

        personalDetailName = findViewById(R.id.personal_detail_name);
        personDetailProfileImageView = findViewById(R.id.person_detail_profile_image_view);

        personDetailProfileImagesRecyclerView = findViewById(R.id.person_detail_profile_images_recycler_view);
        personDetailProfileImagesRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));

        if (intent != null && intent.getExtras() != null){
            int id = Integer.parseInt(intent.getExtras().getString("id"));
            Call<PersonDetails> personDetailsCall = retrofitService.getPersonDetailsById(id,BuildConfig.THE_MOVIE_DB_API_KEY);
            personDetailsCall.enqueue(new Callback<PersonDetails>() {
                @Override
                public void onResponse(@NonNull Call<PersonDetails> call, @NonNull Response<PersonDetails> response) {
                    PersonDetails personDetailsResponse = response.body();
                    if (personDetailsResponse != null){
                        preparePersonDetails(personDetailsResponse);
                    }
                    else {
                        Toast.makeText(PersonDetailActivity.this, "Any details not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PersonDetails> call, @NonNull Throwable t) {
                    Toast.makeText(PersonDetailActivity.this, "Any details not found", Toast.LENGTH_SHORT).show();
                }
            });
            Call<PersonImages> personImagesCall = retrofitService.getPersonImagesById(id,BuildConfig.THE_MOVIE_DB_API_KEY);
            personImagesCall.enqueue(new Callback<PersonImages>() {
                @Override
                public void onResponse(@NonNull Call<PersonImages> call,@NonNull Response<PersonImages> response) {
                    PersonImages personImages = response.body();
                    if (personImagesCall != null){
                        List<PersonImagesProfiles> personImagesProfilesList = personImages.getProfiles();
                        if (personImagesProfilesList != null && personImagesProfilesList.size() > 0){
                            personDetailProfileImagesLayout.setVisibility(View.VISIBLE);
                            personProfileImagesAdapter = new PersonProfileImagesAdapter(PersonDetailActivity.this,personImagesProfilesList);
                            personDetailProfileImagesRecyclerView.setAdapter(personProfileImagesAdapter);
                            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(PersonDetailActivity.this,R.anim.layout_slide_right);
                            personDetailProfileImagesRecyclerView.setLayoutAnimation(controller);
                            personDetailProfileImagesRecyclerView.scheduleLayoutAnimation();
                        }
                        else {
                            personDetailProfileImagesLayout.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PersonImages> call,@NonNull Throwable t) {

                }
            });
        }
    }

    private void preparePersonDetails(PersonDetails personDetailsResponse) {
        String profilePath = personDetailsResponse.getProfile_path();
        String name = personDetailsResponse.getName();
        String birthday = personDetailsResponse.getBirthday();
        String placeOfBirth = personDetailsResponse.getPlace_of_birth();
        String deathDay = personDetailsResponse.getDeathday();
        String department = personDetailsResponse.getKnown_for_department();
        String homepage = personDetailsResponse.getHomepage();
        String biography = personDetailsResponse.getBiography();

        List<String> alsoKnownAsList = personDetailsResponse.getAlso_known_as();
        Picasso.with(this).load(profilePath).into(personDetailProfileImageView);

        if (name != null){
            if (name.length() > 0){
                personalDetailName.setText(name);
                personalDetailName.setVisibility(View.VISIBLE);
            }
            else {
                personalDetailName.setVisibility(View.GONE);
            }
        }
        else {
            personalDetailName.setVisibility(View.GONE);
        }

        if (birthday != null){
            if (birthday.length() > 0){
                personDetailBirthday.setText(birthday);
                personDetailBirthdayLayout.setVisibility(View.VISIBLE);
            }
            else {
                personDetailBirthdayLayout.setVisibility(View.GONE);
            }
        }
        else {
            personDetailBirthdayLayout.setVisibility(View.GONE);
        }

        if (placeOfBirth != null){
            if (placeOfBirth.length() > 0){
                personDetailPlaceOfBirth.setText(placeOfBirth);
                personDetailPlaceOfBirthLayout.setVisibility(View.VISIBLE);
            }
            else {
                personDetailPlaceOfBirthLayout.setVisibility(View.GONE);
            }
        }
        else {
            personDetailPlaceOfBirthLayout.setVisibility(View.GONE);
        }

        if (deathDay != null){
            if (deathDay.length() > 0){
                personDetailDeathday.setText(deathDay);
                personDetailDeathdayLayout.setVisibility(View.VISIBLE);
            }
            else {
                personDetailDeathdayLayout.setVisibility(View.GONE);
            }
        }
        else {
            personDetailDeathdayLayout.setVisibility(View.GONE);
        }

        if (department != null){
            if (department.length() > 0){
                personDetailDepartment.setText(department);
                personDetailDepartmentLayout.setVisibility(View.VISIBLE);
            }
            else {
                personDetailDepartmentLayout.setVisibility(View.GONE);
            }
        }
        else {
            personDetailDepartmentLayout.setVisibility(View.GONE);
        }

        if (homepage != null){
            if (homepage.length() > 0){
                personDetailHomepage.setText(homepage);
                personDetailHomepageLayout.setVisibility(View.VISIBLE);
            }
            else {
                personDetailHomepageLayout.setVisibility(View.GONE);
            }
        }
        else {
            personDetailHomepageLayout.setVisibility(View.GONE);
        }

        if (biography != null){
            if (biography.length() > 0){
                personDetailBiography.setText(biography);
                personDetailBiographyLayout.setVisibility(View.VISIBLE);
            }
            else {
                personDetailBiographyLayout.setVisibility(View.GONE);
            }
        }
        else {
            personDetailBiographyLayout.setVisibility(View.GONE);
        }

        if (alsoKnownAsList != null){
            if (alsoKnownAsList.size() > 0){
                StringBuilder stringBuilder = new StringBuilder();
                for (int i=0; i<alsoKnownAsList.size(); i++){
                    if (i == alsoKnownAsList.size() - 1){
                        stringBuilder.append(alsoKnownAsList.get(i));
                    }
                    else {
                        stringBuilder.append(alsoKnownAsList.get(i)).append(", ");
                    }
                }
                personDetailAlsoKnownAs.setText(stringBuilder.toString());
                personDetailAlsoKnownAsLayout.setVisibility(View.VISIBLE);
            }
            else {
                personDetailAlsoKnownAsLayout.setVisibility(View.GONE);
            }
        }
        else {
            personDetailAlsoKnownAsLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
