package lv.st.sbogdano.popularmoviesmvp.model;

import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lv.st.sbogdano.popularmoviesmvp.model.api.ApiInterface;
import lv.st.sbogdano.popularmoviesmvp.model.api.ApiModule;
import lv.st.sbogdano.popularmoviesmvp.model.data.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of a remote data source with static access to the data for easy testing.
 */

public class MoviesRepository implements MoviesDataSource {

    private static MoviesRepository INSTANCE;

    private ApiInterface apiInterface = ApiModule.getApiInterface();

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    Map<Long, Movie> mCachedMovies;

    /**
     * Marks the cache is invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean mCacheIsDirty = false;


    // Prevent direct instantiation.
    private MoviesRepository() {
    }

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

        // Respond immediately with cache if available and not dirty.
        if (mCachedMovies != null && !mCacheIsDirty) {
            callback.onMoviesLoaded(new ArrayList<>(mCachedMovies.values()));
            return;
        }

        if (mCacheIsDirty) {
            getMoviesFromRemoteDataSource(callback);
        }
    }


    @Override
    public void refreshMovies() {
        mCacheIsDirty = true;
    }

    private void getMoviesFromRemoteDataSource(@NonNull final LoadMoviesCallback callback) {
        INSTANCE.getMovies(new LoadMoviesCallback() {
            @Override
            public void onMoviesLoaded(List<Movie> movies) {

                apiInterface.getListMovies("popular").enqueue(new Callback<List<Movie>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Movie>> call, @NonNull Response<List<Movie>> response) {
                        if (response.body() != null) {
                            movies.addAll(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Movie>> call, Throwable t) {

                    }
                });

                refreshCache(movies);
                callback.onMoviesLoaded(new ArrayList<>(mCachedMovies.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshCache(List<Movie> movies) {
        if (mCachedMovies == null) {
            mCachedMovies = new LinkedHashMap<>();
        }
        mCachedMovies.clear();
        for (Movie movie : movies) {
            mCachedMovies.put(movie.getId(), movie);
        }
        mCacheIsDirty = false;
    }

}
