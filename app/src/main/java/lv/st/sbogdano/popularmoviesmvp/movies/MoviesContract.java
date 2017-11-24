package lv.st.sbogdano.popularmoviesmvp.movies;

import android.support.annotation.NonNull;

import java.util.List;

import lv.st.sbogdano.popularmoviesmvp.BasePresenter;
import lv.st.sbogdano.popularmoviesmvp.BaseView;
import lv.st.sbogdano.popularmoviesmvp.model.data.Movie;

/**
 * This specifies the contract between view and presenter.
 */
public interface MoviesContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showMovies(List<Movie> movies);

        void showMovieDetailsUi(Long movieId);

        void showLoadingMoviesError();

        void showNoMovies();

        void showPopularSortingLabel();

        void showTopRatedSortingLabel();
    }

    interface Presenter extends BasePresenter {

        void loadMovies(boolean forceUpdate);

        void openMoviesDetails(@NonNull Movie requestedMovie);

        void setSorting(MoviesSortingType requestType);

        MoviesSortingType getSorting();
    }
}
