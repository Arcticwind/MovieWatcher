package com.example.moviewatcher.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.moviewatcher.databases.MovieDao;
import com.example.moviewatcher.databases.WishListDatabase;
import com.example.moviewatcher.models.Movie;

import java.util.List;

public class WishListRepository {

    private MovieDao movieDao;
    private LiveData<List<Movie>> allMovies;

    public WishListRepository(Application application) {
        WishListDatabase database = WishListDatabase.getInstance(application);
        movieDao = database.movieDao();
        allMovies = movieDao.getAllMovies();
    }

    public void insert(Movie movie) { new InsertMovieAsyncTask(movieDao).execute(movie); }

    public void delete(Movie movie) { new DeleteMovieAsyncTask(movieDao).execute(movie); }

    public void deleteAllMovies() { new DeleteAllMoviesAsyncTask(movieDao).execute(); }

    public LiveData<List<Movie>> getAllMovies() {return allMovies; }

    private static class InsertMovieAsyncTask extends AsyncTask<Movie, Void, Void> {
        private MovieDao movieDao;

        public InsertMovieAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.insert(movies[0]);
            return null;
        }
    }

    private static class DeleteMovieAsyncTask extends AsyncTask<Movie, Void, Void> {
        private MovieDao movieDao;

        public DeleteMovieAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.delete(movies[0]);
            return null;
        }
    }

    private static class DeleteAllMoviesAsyncTask extends AsyncTask<Void, Void, Void> {
        private MovieDao movieDao;

        public DeleteAllMoviesAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            movieDao.deleteAllMovies();
            return null;
        }
    }
}
