package lv.st.sbogdano.popularmoviesmvp.model.api;

import java.util.List;

import lv.st.sbogdano.popularmoviesmvp.model.data.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sbogdano on 02/11/2017.
 */

public interface ApiInterface {

    @GET("movie/{sorting_type}")
    Call<List<Movie>> getListMovies(@Path("sorting_type") String sortingType);
}
