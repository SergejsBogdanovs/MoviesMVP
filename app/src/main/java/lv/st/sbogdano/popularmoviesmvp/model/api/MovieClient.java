package lv.st.sbogdano.popularmoviesmvp.model.api;


import lv.st.sbogdano.popularmoviesmvp.model.data.Result;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieClient {

  @GET("movie/popular?api_key=ec37c7f4a198ea5f17aa1db00536041c")
  Call<Result>getMoviesList();
}
