package lv.st.sbogdano.popularmoviesmvp.movies;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

  @BindView(R.id.noMovies)
  View mNoMoviesView;

  @BindView(R.id.noMoviesIcon)
  ImageView mNoMoviesIcon;

  @BindView(R.id.noMoviesText)
  TextView mNoMoviesText;

  @BindView(R.id.sortingLabel)
  TextView mSortingLabel;

  @BindView(R.id.moviesLL)
  LinearLayout mMoviesView;

  @BindView(R.id.rvMovies)
  RecyclerView mRecyclerView;

  @BindView(R.id.refresh_layout)
  SwipeRefreshLayout mSwipeRefreshLayout;

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
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    mRecyclerView.setAdapter(mMoviesAdapter);

    // Set up progress indicator.
    mSwipeRefreshLayout.setColorSchemeColors(
        ContextCompat.getColor(getActivity(), R.color.colorPrimary),
        ContextCompat.getColor(getActivity(), R.color.colorAccent),
        ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

    mSwipeRefreshLayout.setOnRefreshListener(() -> mPresenter.loadMovies(false));

    setHasOptionsMenu(true);

    return root;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_refresh:
        mPresenter.loadMovies(true);
    }
    return true;
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.movies_fragment_menu, menu);
  }

  @Override
  public void setLoadingIndicator(final boolean active) {
    if (getView() == null) {
      return;
    }

    // Make sure setRefreshing() is called the layout is done with everything else.
    final SwipeRefreshLayout srl = getView().findViewById(R.id.refresh_layout);
    srl.post(() -> srl.setRefreshing(active));
  }

  @Override
  public void showMovies(List<Movie> movies) {
    mMoviesAdapter.replaceData(movies);
    mMoviesView.setVisibility(View.VISIBLE);
    mNoMoviesView.setVisibility(View.GONE);
  }

  @Override
  public void showMovieDetailsUi(Long movieId) {

  }

  @Override
  public void showLoadingMoviesError() {

  }

  @Override
  public void showNoMovies() {
    showNoMoviesView(
        getResources().getString(R.string.no_movies_text),
        R.drawable.do_not_disturb);
  }

  private void showNoMoviesView(String mainText, int iconRes) {
    mMoviesView.setVisibility(View.GONE);
    mNoMoviesView.setVisibility(View.VISIBLE);

    mNoMoviesText.setText(mainText);
    mNoMoviesIcon.setImageDrawable(getResources().getDrawable(iconRes));
  }

  @Override
  public void showPopularSortingLabel() {
    mSortingLabel.setText(getResources().getString(R.string.popular_label));
  }

  @Override
  public void showTopRatedSortingLabel() {
    mSortingLabel.setText(getResources().getString(R.string.top_rated_label));
  }
}
