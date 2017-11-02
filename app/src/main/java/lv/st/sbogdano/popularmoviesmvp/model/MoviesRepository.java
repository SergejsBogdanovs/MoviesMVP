package lv.st.sbogdano.popularmoviesmvp.model;

import android.support.annotation.NonNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of a remote data source with static access to the data for easy testing.
 */

public class MoviesRepository implements MoviesDataSource {

    private static MoviesRepository INSTANCE;

    private static final Map<String, Movie> MOVIES_SERVICE_DATA = new LinkedHashMap<>();

    private MoviesDataSource mMoviesRemoteDataSource;

    // Prevent direct instantiation.
    private MoviesRepository() {}

    public static MoviesRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MoviesRepository();
        }

        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance()} to create a new instance next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Get movies from remote data source.
     * @param callback
     */
    @Override
    public void getMovies(@NonNull LoadMoviesCallback callback) {
        checkNotNull(callback);
        getMoviesFromRemoteDataSource(callback);
    }


    @Override
    public void refreshMovies() {

    }

    private void getMoviesFromRemoteDataSource(@NonNull final LoadMoviesCallback callback) {
        mMoviesRemoteDataSource.getMovies(new LoadMoviesCallback() {
            @Override
            public void onMoviesLoaded(List<Movie> movies) {

            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

}
