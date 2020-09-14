package com.example.moviewatcher.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviewatcher.models.Movie;
import com.example.moviewatcher.viewmodels.MovieDetailsViewModel;
import com.example.moviewatcher.R;
import com.example.moviewatcher.viewmodels.WishListViewModel;
import com.example.moviewatcher.utils.MyConstants;
import com.example.moviewatcher.utils.MyMethods;
import com.example.moviewatcher.webservices.omdb.OmdbDetailsResponse;
import com.example.moviewatcher.webservices.omdb.OmdbInterface;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieDetailsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar_movie_details)
    Toolbar toolbar;

    @BindView(R.id.drawer_movie_details)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_movie_details)
    NavigationView navigationView;

    @BindView(R.id.img_movie_details_poster)
    ImageView imgPoster;

    @BindView(R.id.tv_movie_details_title)
    TextView tvTitle;

    @BindView(R.id.tv_movie_details_actors)
    TextView tvActors;

    @BindView(R.id.tv_movie_details_year)
    TextView tvYear;

    @BindView(R.id.tv_movie_details_director)
    TextView tvDirector;

    @BindView(R.id.tv_movie_details_duration)
    TextView tvDuration;

    @BindView(R.id.tv_movie_details_genre)
    TextView tvGenre;

    @BindView(R.id.tv_movie_details_type)
    TextView tvType;

    @BindView(R.id.tv_movie_details_rating)
    TextView tvRating;

    @BindView(R.id.tv_movie_details_description)
    TextView tvDescription;

    @OnClick(R.id.btn_check_on_imdb)
    public void btnCheckOnImdb() { openImdbUrl(); }

    @OnClick(R.id.btn_add_to_wish_list)
    public void btnAddToWishList(View v) { addMovieToWishList(v); }

    private MovieDetailsViewModel movieDetailsViewModel;
    private String movieId, moviePosterUrl;
    private WishListViewModel wishListViewModel;
    private MyMethods mm = new MyMethods();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(this);

        setTitle("");
        setSupportActionBar(toolbar);
        setDrawerLayout();
        displaySelectedMovie();
    }

    private void displaySelectedMovie() {
        String id = getIntent().getStringExtra(MyConstants.MOVIE_ID);
        movieDetailsViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);
        movieDetailsViewModel.getData(OmdbInterface.API_KEY, id, "full");
        movieDetailsViewModel.getMovieDetailsRepository().observe(this, new Observer<OmdbDetailsResponse>() {
            @Override
            public void onChanged(OmdbDetailsResponse omdbDetailsResponse) {
                setUpdatedValues(omdbDetailsResponse);
            }
        });
    }

    private void setUpdatedValues(OmdbDetailsResponse omdbDetailsResponse) {
        tvTitle.setText(omdbDetailsResponse.getTitle());
        tvActors.setText(omdbDetailsResponse.getActors());
        tvDirector.setText(omdbDetailsResponse.getDirector());
        tvDuration.setText(omdbDetailsResponse.getRuntime());
        tvGenre.setText(omdbDetailsResponse.getGenre());
        tvType.setText(omdbDetailsResponse.getType());
        tvRating.setText(omdbDetailsResponse.getImdbRating());
        tvYear.setText(omdbDetailsResponse.getYear());
        tvDescription.setText(omdbDetailsResponse.getPlot());
        Glide.with(this).load(omdbDetailsResponse.getPoster()).into(imgPoster);
        movieId = omdbDetailsResponse.getImdbID();
        moviePosterUrl = omdbDetailsResponse.getPoster();
    }

    private void addMovieToWishList(View v) {
        Movie movie = new Movie(movieId, tvTitle.getText().toString(), tvGenre.getText().toString(), tvType.getText().toString(), tvYear.getText().toString(), tvDescription.getText().toString(), moviePosterUrl);
        wishListViewModel = ViewModelProviders.of(this).get(WishListViewModel.class);
        wishListViewModel.insert(movie);
        mm.getToastMessage(this, tvType.getText().toString() + " " + getString(R.string.toast_wish_list_add));
        v.setEnabled(false);
    }

    private void openImdbUrl() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/title/" + movieId + "/"));
        startActivity(intent);
    }

    private void setDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open_drawer, R.string.nav_close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_drawer_main: startActivity(new Intent(getApplicationContext(), MainActivity.class)); break;
            case R.id.menu_drawer_movie_list: startActivity(new Intent(getApplicationContext(), MovieListActivity.class)); break;
            case R.id.menu_drawer_movie_wish_list: startActivity(new Intent(getApplicationContext(), MovieWishListActivity.class)); break;
        }
        return false;
    }
}