package com.example.moviewatcher.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.moviewatcher.models.Movie;
import com.example.moviewatcher.R;
import com.example.moviewatcher.viewmodels.WishListViewModel;
import com.example.moviewatcher.adapters.MovieWishListAdapter;
import com.example.moviewatcher.dialogs.ClearWishListDialog;
import com.example.moviewatcher.utils.MyConstants;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieWishListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar_watch_wish_list)
    Toolbar toolbar;

    @BindView(R.id.drawer_watch_wish_list)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_watch_wish_list)
    NavigationView navigationView;

    @BindView(R.id.rv_watch_wish_list)
    RecyclerView recyclerView;

    private WishListViewModel wishListViewModel;
    private MovieWishListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_wish_list);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setDrawerLayout();
        setRecyclerView();

    }

    private void setRecyclerView() {
        wishListViewModel = ViewModelProviders.of(this).get(WishListViewModel.class);
        wishListViewModel.getAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                adapter.setMovieList(movies);
            }
        });

        List<Movie> movies = new ArrayList<>();
        adapter = new MovieWishListAdapter(this, movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setListener(new MovieWishListAdapter.OnWishListItemClickListener() {
            @Override
            public void onItemClick(Movie movie) {
                openMovieDetails(movie);
            }
        });

        enableSwipeToDelete();
    }

    private void enableSwipeToDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                wishListViewModel.delete(adapter.getWishMovieAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void openMovieDetails(Movie movie) {
        Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
        intent.putExtra(MyConstants.MOVIE_ID, movie.getMovieId());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_wish_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_clear_wish_list) { new ClearWishListDialog().show(getSupportFragmentManager(), "ClearWishListDialog");
        }
        return super.onOptionsItemSelected(item);
    }
}