package lv.st.sbogdano.popularmoviesmvp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lv.st.sbogdano.popularmoviesmvp.R;
import lv.st.sbogdano.popularmoviesmvp.model.data.Movie;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by sbogdano on 02/11/2017.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<Movie> mMovies;
    private Context mContext;

    public MoviesAdapter(Context context, List<Movie> movies) {
        setList(movies);
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View movieView = inflater.inflate(R.layout.movie_item, parent, false);

        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            Picasso.with(mContext)
                    .load("https://image.tmdb.org/t/p/w500" + mMovies.get(position).getPosterPath())
                    .resize(100, 100)
                    .into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void replaceData(List<Movie> movies) {
        setList(movies);
        notifyDataSetChanged();
    }

    private void setList(List<Movie> movies) {
        this.mMovies = checkNotNull(movies);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.moviePoster)
        ImageView moviePoster;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
