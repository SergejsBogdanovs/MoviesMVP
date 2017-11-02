package lv.st.sbogdano.popularmoviesmvp.movies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lv.st.sbogdano.popularmoviesmvp.R;
import lv.st.sbogdano.popularmoviesmvp.adapters.MoviesAdapter;
import lv.st.sbogdano.popularmoviesmvp.model.data.Movie;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Display a grid of movies (sorted by populars by default). User can choose to view popular
 * or top rated movies.
 */
public class MoviesFragment extends Fragment implements MoviesContract.View {

    private MoviesContract.Presenter mPresenter;

    private MoviesAdapter mMoviesAdapter;

    private View noMoviesView;

    private ImageView noMoviesIcon;

    @BindView(R.id.rvMovies)
    RecyclerView mRecyclerView;

    public MoviesFragment() {

    }

    public static MoviesFragment newInstance() {
        return new MoviesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMoviesAdapter = new MoviesAdapter(getContext(), new ArrayList<>(0));
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(MoviesContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, root);

        // Set up movies view
        mRecyclerView.setAdapter(mMoviesAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager());

        return root;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showMovies(List<Movie> movies) {

    }

    @Override
    public void showMovieDetailsUi(Long movieId) {

    }

    @Override
    public void showLoadingMoviesError() {

    }

    @Override
    public void showNoMovies() {

    }
}
