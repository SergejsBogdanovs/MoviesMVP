package lv.st.sbogdano.popularmoviesmvp.movies;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import lv.st.sbogdano.popularmoviesmvp.R;

public class MoviesActivity extends AppCompatActivity {

    private static final String CURRENT_SORTING_KEY = "CURRENT_SORTING_KEY";

    private DrawerLayout mDrawerLayout;

    private MoviesPresenter mMoviesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        // Set up the navigation drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

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
