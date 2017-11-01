package lv.st.sbogdano.popularmoviesmvp.movies;

import android.support.v4.app.Fragment;

/**
 * Display a grid of movies (sorted by populars by default). User can choose to view popular
 * or top rated movies.
 */

public class MoviesFragment extends Fragment implements MoviesContract.View {

    public static MoviesFragment newInstance() {
        return new MoviesFragment();
    }
}
