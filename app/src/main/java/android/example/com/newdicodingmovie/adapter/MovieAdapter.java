package android.example.com.newdicodingmovie.adapter;

import android.content.Context;
import android.example.com.newdicodingmovie.R;
import android.example.com.newdicodingmovie.parcelable.MovieParcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.HolderMovie>{
    private Context context;
    @NonNull
    @Override
    public HolderMovie onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new HolderMovie(view);
    }
    public MovieAdapter(Context context) {
        this.context = context;
    }

    public MovieAdapter(ArrayList<MovieParcelable> items) {
        this.items = items;
    }

    private ArrayList<MovieParcelable> items = new ArrayList<>();

    public void isiList (ArrayList<MovieParcelable> items) {
        this.items = new ArrayList<>();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void clearAndSetData(ArrayList<MovieParcelable> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull HolderMovie holder, int position) {
        holder.onBind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class HolderMovie extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvDateRelease, tvOverview;
        private ImageView ivPoster;
        HolderMovie(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDateRelease = itemView.findViewById(R.id.tvReleaseDate);
            ivPoster = itemView.findViewById(R.id.img_movie);
            tvOverview=itemView.findViewById(R.id.tvDescription);

        }

        void onBind(MovieParcelable item) {
            tvOverview.setText(item.getDescription());
            tvTitle.setText(item.getTitle());
            if (item.getPosterPath() != null && !item.getPosterPath().isEmpty()) {
                Picasso.get().load("https://image.tmdb.org/t/p/w185/" + item.getPosterPath()).into(ivPoster);
            }
            String release =item.getReleaseDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            try {
                Date date = dateFormat.parse(release);
                SimpleDateFormat baru=new SimpleDateFormat("dd MMM yyyy");
                String NewRelease = baru.format(date);
                tvDateRelease.setText(NewRelease);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
