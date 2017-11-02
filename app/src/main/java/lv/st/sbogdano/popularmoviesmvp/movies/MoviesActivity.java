package lv.st.sbogdano.popularmoviesmvp.movies;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import lv.st.sbogdano.popularmoviesmvp.R;
import lv.st.sbogdano.popularmoviesmvp.util.ActivityUtils;
import lv.st.sbogdano.popularmoviesmvp.util.Injection;

public class MoviesActivity extends AppCompatActivity {

    private static final String CURRENT_SORTING_KEY = "CURRENT_SORTING_KEY";

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private MoviesPresenter mMoviesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        // Set up the navigation drawer
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        MoviesFragment moviesFragment =
                (MoviesFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (moviesFragment == null) {
            // Create the fragment
            moviesFragment = MoviesFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), moviesFragment, R.id.contentFrame);
        }

        // Create the presenter
        mMoviesPresenter = new MoviesPresenter(
                Injection.provideMoviesRepository(getApplicationContext()),
                moviesFragment);

        // Load previously saved state, if available.
        if (savedInstanceState != null) {
            MoviesSortingType currentSorting =
                    (MoviesSortingType) savedInstanceState.getSerializable(CURRENT_SORTING_KEY);
            mMoviesPresenter.setSorting(currentSorting);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CURRENT_SORTING_KEY, mMoviesPresenter.getSorting());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.popular_movies_navigation_menu_item:
                    // Do nothing, we are already on that screen
                    break;
                case R.id.top_rated_movies_navigation_menu_item:
                    // TODO: Implement loading top rated movies
                    break;
                default:
                    break;
            }

            // Close the navigation drawer when an item is selected.
            menuItem.setChecked(true);
            mDrawerLayout.closeDrawers();
            return true;
        });
    }

}
