package com.example.moviewatcher.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.moviewatcher.viewmodels.MovieListViewModel;
import com.example.moviewatcher.R;
import com.example.moviewatcher.adapters.MovieListAdapter;
import com.example.moviewatcher.utils.MyConstants;
import com.example.moviewatcher.utils.MyMethods;
import com.example.moviewatcher.webservices.omdb.OmdbInterface;
import com.example.moviewatcher.webservices.omdb.OmdbSearch;
import com.example.moviewatcher.webservices.omdb.OmdbSearchResponse;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar_movie_list)
    Toolbar toolbar;

    @BindView(R.id.drawer_movie_list)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_movie_list)
    NavigationView navigationView;

    @BindView(R.id.rv_movie_list)
    RecyclerView recyclerView;

    @BindView(R.id.et_search_title)
    EditText etSearchTitle;

    @BindView(R.id.et_search_year)
    EditText etSearchYear;

    @BindView(R.id.spinner_movie_types)
    Spinner spinnerMovieTypes;

    @OnClick(R.id.btn_movie_list_search)
    public void btnMovieListSearch() { searchMovieDatabase(); }

    @OnClick(R.id.btn_previous_page)
    public void btnPreviousPage() { if (currentPage > 1) previousPage(); }

    @OnClick(R.id.btn_next_page)
    public void btnNextPage() { if (currentPage < totalPages) nextPage(); }

    private MovieListAdapter adapter;
    private MovieListViewModel movieListViewModel;
    private ArrayList<OmdbSearch> searchArrayList = new ArrayList<>();
    private int totalPages = 1;
    private int currentPage = 1;
    private MyMethods mm = new MyMethods();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        ButterKnife.bind(this);

        setDrawerLayout();
        setRecyclerView();
    }

    private void searchMovieDatabase() {
        searchArrayList.clear();
        adapter.notifyDataSetChanged();
        String title = etSearchTitle.getText().toString();
        String year = etSearchYear.getText().toString();
        String type = spinnerMovieTypes.getSelectedItem().toString();
        if (type.equals("all")) {type = "";}
        movieListViewModel = ViewModelProviders.of(this).get(MovieListViewModel.class);
        movieListViewModel.getData(OmdbInterface.API_KEY, title, type, year, currentPage);
        movieListViewModel.getMovieListRepository().observe(this, new Observer<OmdbSearchResponse>() {
            @Override
            public void onChanged(OmdbSearchResponse omdbSearchResponse) {
                if (omdbSearchResponse.getResponse().equals("True")) {
                    List<OmdbSearch> omdbSearchDetailsList = omdbSearchResponse.getOmdbSearches();
                    searchArrayList.addAll(omdbSearchDetailsList);
                    adapter.setMovieList(searchArrayList);
                    setTotalResultPages(omdbSearchResponse);
                }
            }
        });
    }

    private void setTotalResultPages(OmdbSearchResponse omdbSearchResponse) {
        int totalResults = Integer.parseInt(omdbSearchResponse.getTotalResults());
        totalPages = (totalResults + 9) / 10;
        mm.getToastMessage(this, totalResults + " " + getString(R.string.toast_total_results_found));
    }

    private void previousPage() {
        currentPage--;
        searchMovieDatabase();
    }

    private void nextPage() {
        currentPage++;
        searchMovieDatabase();
    }

    private void setRecyclerView() {
        if (adapter == null) {
            adapter = new MovieListAdapter(this, searchArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        } else { adapter.notifyDataSetChanged(); }

        adapter.setListener(new MovieListAdapter.OnMovieItemClickListener() {
            @Override
            public void onItemClick(OmdbSearch omdbSearch) {
                openMovieDetails(omdbSearch);
            }
        });
    }

    private void openMovieDetails(OmdbSearch omdbSearch) {
        Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
        intent.putExtra(MyConstants.MOVIE_ID, omdbSearch.getImdbID());
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