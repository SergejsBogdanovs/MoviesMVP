package lv.st.sbogdano.popularmoviesmvp.movies;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import lv.st.sbogdano.popularmoviesmvp.model.MoviesDataSource;
import lv.st.sbogdano.popularmoviesmvp.model.MoviesRepository;
import lv.st.sbogdano.popularmoviesmvp.model.data.Movie;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Listen to user action from the UI {@link MoviesFragment}, retrieves the data and updates the UI
 * as required.
 */
public class MoviesPresenter implements MoviesContract.Presenter {

  private final MoviesRepository mMoviesRepository;

  private final MoviesContract.View mMoviesView;

  private MoviesSortingType mCurrentSortingType = MoviesSortingType.POPULAR;

  private boolean mFirstLoad = true;

  public MoviesPresenter(@NonNull MoviesRepository moviesRepository,
      @NonNull MoviesContract.View moviesView) {
    mMoviesRepository = checkNotNull(moviesRepository, "moviesRepository cannot be null");
    mMoviesView = checkNotNull(moviesView, "moviesView cannot be null!");
    mMoviesView.setPresenter(this);
  }

  @Override
  public void start() {
    loadMovies(false);
  }

  @Override
  public void loadMovies(boolean forceUpdate) {
    // Simplification for sample: a network reload will be forced on firs load.
    loadMovies(forceUpdate || mFirstLoad, true);
    mFirstLoad = false;
  }

  /**
   * @param forceUpdate Pass in true to refresh the data in the {@link MoviesRepository}.
   * @param showLoadingUi Pass in true to display the loading icon in the UI.
   */
  private void loadMovies(boolean forceUpdate, boolean showLoadingUi) {
    if (showLoadingUi) {
      mMoviesView.setLoadingIndicator(true);
    }
    if (forceUpdate) {
      mMoviesRepository.refreshMovies();
    }

    mMoviesRepository.getMovies(new MoviesDataSource.LoadMoviesCallback() {
      @Override
      public void onMoviesLoaded(List<Movie> movies) {

        if (showLoadingUi) {
          mMoviesView.setLoadingIndicator(false);
        }

        processMovies(movies);
      }

      @Override
      public void onDataNotAvailable() {
        mMoviesView.showLoadingMoviesError();
      }
    });
  }

  private void processMovies(List<Movie> movies) {
    if (movies.isEmpty()) {
      processEmptyMovies();
    } else {
      // Show the list of movies.
      mMoviesView.showMovies(movies);

      // Set the sorting label's text.
      showSortingLabel();
    }
  }

  private void processEmptyMovies() {
    mMoviesView.showNoMovies();
  }

  private void showSortingLabel() {
    switch (mCurrentSortingType) {
      case TOP_RATED:
        mMoviesView.showTopRatedSortingLabel();
        break;
      default:
        mMoviesView.showPopularSortingLabel();
        break;
    }
  }


  @Override
  public void openMoviesDetails(@NonNull Movie requestedMovie) {

  }

  @Override
  public void setSorting(MoviesSortingType requestType) {

  }

  @Override
  public MoviesSortingType getSorting() {
    return null;
  }
}
