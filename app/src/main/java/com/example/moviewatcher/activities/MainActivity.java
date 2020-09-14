package com.example.moviewatcher.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.moviewatcher.R;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar_main)
    Toolbar toolbar;

    @BindView(R.id.drawer_main_activity)
    DrawerLayout drawerLayout;
    
    @BindView(R.id.nav_main)
    NavigationView navigationView;

    @OnClick(R.id.btn_main_movies)
    public void btnMainMovies() { startMovieListActivity(); }

    @OnClick(R.id.btn_main_wish_list)
    public void btnMainWishList() { startMovieWishListActivity(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        
        setDrawerLayout();
    }

    private void setDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open_drawer, R.string.nav_close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void startMovieListActivity() { startActivity(new Intent(getApplicationContext(), MovieListActivity.class)); }

    private void startMovieWishListActivity() { startActivity(new Intent(getApplicationContext(), MovieWishListActivity.class)); }

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