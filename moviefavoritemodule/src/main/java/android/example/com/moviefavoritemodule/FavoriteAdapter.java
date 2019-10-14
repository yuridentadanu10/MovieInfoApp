package android.example.com.moviefavoritemodule;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MovieHolder>{
    private Cursor listFavorite;
    private ArrayList<MovieParcelable> movies = new ArrayList<>();
    @NonNull
    @Override
    public FavoriteAdapter.MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_favorite, parent, false);
        return new FavoriteAdapter.MovieHolder(view);
    }
    public Context context;
    public FavoriteAdapter(Context context){
        this.context=context;

    }
    public FavoriteAdapter(Cursor items) {
        refresh(items);
    }
    public void setListMovies(Cursor cursor) {
        this.listFavorite = cursor;
    }
    public void refresh(Cursor items) {
        listFavorite = items;
        notifyDataSetChanged();
    }
public void setFavoriteMovie(ArrayList<MovieParcelable> items) {
    movies.clear();
    movies.addAll(items);
    notifyDataSetChanged();
}
    private MovieParcelable getItem(int position) {
        if (!listFavorite.moveToPosition(position)) {
            throw new IllegalStateException("Invalid Position!");
        }
        return new MovieParcelable(listFavorite);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.MovieHolder holder, int position) {
        holder.onBind(getItem(position));
    }



    @Override
    public int getItemCount() {
        if (listFavorite == null) return 0;
        return listFavorite.getCount();
    }

    class MovieHolder extends RecyclerView.ViewHolder {
        private ImageView ivPoster;
        private TextView mTitle,mOverview;
        private CardView mListView;

        MovieHolder(@NonNull View itemView) {
            super(itemView);
            mTitle=itemView.findViewById(R.id.tvTitle);
            mOverview=itemView.findViewById(R.id.tvOverview);
            mListView=itemView.findViewById(R.id.item_card);
            ivPoster = itemView.findViewById(R.id.img_movie);

        }

        void onBind(final MovieParcelable item) {

            mTitle.setText(item.getTitle());
            mOverview.setText(item.getOverview());

            if (item.getPoster_path() != null && !item.getPoster_path().isEmpty()) {
                Picasso.get().load("https://image.tmdb.org/t/p/w185/" + item.getPoster_path()).into(ivPoster);
            }

            mListView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent openDetail =new Intent(itemView.getContext(), DetailFavoriteActivity.class);
                    openDetail.putExtra(DetailFavoriteActivity.MOVIE_ITEM,item);
                    itemView.getContext().startActivity(openDetail);
                }
            });


        }


    }
}
