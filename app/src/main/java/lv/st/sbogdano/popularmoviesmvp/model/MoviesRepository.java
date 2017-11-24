package lv.st.sbogdano.popularmoviesmvp.model;

import android.support.annotation.NonNull;

import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lv.st.sbogdano.popularmoviesmvp.BuildConfig;
import lv.st.sbogdano.popularmoviesmvp.model.api.MovieClient;
import lv.st.sbogdano.popularmoviesmvp.model.data.Movie;
import lv.st.sbogdano.popularmoviesmvp.model.data.Result;
import lv.st.sbogdano.popularmoviesmvp.movies.MoviesActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of a remote data source with static access to the data for easy testing.
 */

public class MoviesRepository implements MoviesDataSource {

  private static final String TAG = MoviesRepository.class.getSimpleName();
  private static MoviesRepository INSTANCE;


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
    Log.v(TAG, "getMoviesFromRemoteDataSource: ");

    Retrofit.Builder builder = new Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    MovieClient client = retrofit.create(MovieClient.class);

    Call<Result> resultCall = client.getMoviesList();

    resultCall.enqueue(new Callback<Result>() {
      @Override
      public void onResponse(Call<Result> call, Response<Result> response) {
        List<Movie> resultFromApi = response.body().getResults();
        refreshCache(resultFromApi);
        callback.onMoviesLoaded(new ArrayList<>(mCachedMovies.values()));
      }

      @Override
      public void onFailure(Call<Result> call, Throwable t) {
        callback.onDataNotAvailable();
      }
    });

  }

  private void refreshCache(List<Movie> movies) {
    Log.v(TAG, "refreshCache: " + movies.get(1).getTitle());
    if (mCachedMovies == null) {
      mCachedMovies = new LinkedHashMap<>();
    }
    mCachedMovies.clear();
    for (Movie movie : movies) {
      Log.v(TAG, "refreshCache: " + movie.getTitle() );
      mCachedMovies.put(movie.getId(), movie);
    }
    mCacheIsDirty = false;
  }

}
