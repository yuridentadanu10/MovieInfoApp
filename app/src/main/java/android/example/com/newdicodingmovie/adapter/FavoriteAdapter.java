package android.example.com.newdicodingmovie.adapter;

import android.content.Intent;
import android.database.Cursor;
import android.example.com.newdicodingmovie.R;
import android.example.com.newdicodingmovie.detailActivity.DetailMovieActivity;
import android.example.com.newdicodingmovie.parcelable.MovieParcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.HolderFavorite>{
    private Cursor listFavorite;
    @NonNull
    @Override
    public HolderFavorite onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_favorite, parent, false);
        return new HolderFavorite(view);
    }

    public FavoriteAdapter(Cursor items) {
        refresh(items);
    }

    public void refresh(Cursor items) {
        listFavorite = items;
        notifyDataSetChanged();
    }

    private MovieParcelable getItem(int position) {
        if (!listFavorite.moveToPosition(position)) {
            throw new IllegalStateException("Invalid Position!");
        }
        return new MovieParcelable(listFavorite);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderFavorite holder, int position) {
        holder.onBind(getItem(position));
    }



    @Override
    public int getItemCount() {
        if (listFavorite == null) return 0;
        return listFavorite.getCount();
    }

    class HolderFavorite extends RecyclerView.ViewHolder {
        private ImageView ivPoster;
        private TextView mTitle,mOverview;
        private CardView mListView;

        HolderFavorite(@NonNull View itemView) {
            super(itemView);
            mTitle=itemView.findViewById(R.id.tvTitle);
            mOverview=itemView.findViewById(R.id.tvOverview);
            mListView=itemView.findViewById(R.id.item_card);
            ivPoster = itemView.findViewById(R.id.img_movie);

        }

        void onBind(final MovieParcelable item) {

            mTitle.setText(item.getTitle());
            mOverview.setText(item.getDescription());

            if (item.getPosterPath() != null && !item.getPosterPath().isEmpty()) {
                Picasso.get().load("https://image.tmdb.org/t/p/w185/" + item.getPosterPath()).into(ivPoster);
            }

            mListView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent openDetail =new Intent(itemView.getContext(), DetailMovieActivity.class);
                    openDetail.putExtra(DetailMovieActivity.MOVIE_ITEM,item);
                    itemView.getContext().startActivity(openDetail);
                }
            });


        }


    }
}
