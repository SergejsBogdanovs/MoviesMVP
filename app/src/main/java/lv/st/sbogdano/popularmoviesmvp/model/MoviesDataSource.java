package lv.st.sbogdano.popularmoviesmvp.model;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Main entry point for accessing movies data
 */

public interface MoviesDataSource {

    interface LoadMoviesCallback {

        void onMoviesLoaded(List<Movie> movies);

        void onDataNotAvailable();
    }

    void getMovies(@NonNull LoadMoviesCallback callback);

    void refreshMovies();
}
