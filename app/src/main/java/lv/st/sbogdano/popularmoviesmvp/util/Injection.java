package lv.st.sbogdano.popularmoviesmvp.util;

import android.content.Context;
import android.support.annotation.NonNull;

import lv.st.sbogdano.popularmoviesmvp.model.MoviesRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by sbogdano on 02/11/2017.
 */

public class Injection {

    public static MoviesRepository provideMoviesRepository(@NonNull Context context) {
        checkNotNull(context);
        return MoviesRepository.getInstance();
    }
}
