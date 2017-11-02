package lv.st.sbogdano.popularmoviesmvp.model.api;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sbogdano on 02/11/2017.
 */

public class ApiModule {

    public static ApiInterface getApiInterface() {

        OkHttpClient httpClient = new OkHttpClient();

        httpClient.interceptors().add(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        });


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create());


        ApiInterface apiInterface = builder.build().create(ApiInterface.class);

        return apiInterface;
    }
}
