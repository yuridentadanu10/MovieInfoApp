package android.example.com.newdicodingmovie.detailActivity;


import android.content.ContentValues;
import android.database.Cursor;
import android.example.com.newdicodingmovie.R;
import android.example.com.newdicodingmovie.base.BaseAppCompatActivity;
import android.example.com.newdicodingmovie.database.FavoriteHelper;
import android.example.com.newdicodingmovie.parcelable.MovieParcelable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.example.com.newdicodingmovie.database.ContractDatabase.CONTENT_URI;
import static android.example.com.newdicodingmovie.database.ContractDatabase.MovieColumns.BACKDROP_POSTER;
import static android.example.com.newdicodingmovie.database.ContractDatabase.MovieColumns.DESCRIPTION;
import static android.example.com.newdicodingmovie.database.ContractDatabase.MovieColumns.ID;
import static android.example.com.newdicodingmovie.database.ContractDatabase.MovieColumns.LANGUAGE;
import static android.example.com.newdicodingmovie.database.ContractDatabase.MovieColumns.POPULARITY;
import static android.example.com.newdicodingmovie.database.ContractDatabase.MovieColumns.POSTER;
import static android.example.com.newdicodingmovie.database.ContractDatabase.MovieColumns.RELEASE;
import static android.example.com.newdicodingmovie.database.ContractDatabase.MovieColumns.TITLE;
import static android.example.com.newdicodingmovie.database.ContractDatabase.MovieColumns.VOTE_AVERAGE;

public class DetailMovieActivity extends BaseAppCompatActivity {
    TextView mTitle,mRating,mOverview,mReleaseDate;
    ImageView mPoster,mBackdrop;
    FloatingActionButton mFavorite;
    public static final String MOVIE_ITEM = "film";
    private FavoriteHelper favoriteHelper;
    private Boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        MovieParcelable fill = getIntent().getParcelableExtra(MOVIE_ITEM);

        mTitle =findViewById(R.id.tvTitle);
        mRating =findViewById(R.id.txt_rating);
        mReleaseDate=findViewById(R.id.tvReleaseDate);
        mOverview = findViewById(R.id.tvOverview);
        mPoster=findViewById(R.id.img_movie);
        mBackdrop=findViewById(R.id.ivBackPoster);
        mFavorite=findViewById(R.id.ic_favorite);
        String release =fill.getReleaseDate();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date date = dateFormat.parse(release);
            SimpleDateFormat baru=new SimpleDateFormat("EEEE, dd MMM yyyy");
            String NewRelease = baru.format(date);
            mReleaseDate.setText(NewRelease);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mRating.setText(fill.getVoteAverage());
        mTitle.setText(fill.getTitle());
        mOverview.setText(fill.getDescription());
        Picasso.get().load("https://image.tmdb.org/t/p/w185/" + fill.getPosterPath()).into(mPoster);
        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + fill.getBackdropPath()).placeholder(this.getResources().getDrawable(R.drawable.ic_launcher_foreground)).error(this.getResources().getDrawable(R.drawable.ic_launcher_foreground)).into(mBackdrop);
        getSupportActionBar().setTitle(fill.getTitle());


        getDataSqLite();
        getSupportActionBar().setTitle(fill.getTitle());
        mFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) RemoveFromFavorite();
                else SaveToFavorite();

                isFavorite = !isFavorite;
                ClickFavorite();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(favoriteHelper!=null)favoriteHelper.close();
    }



    public void ClickFavorite(){
        if (isFavorite)mFavorite.setImageResource(R.drawable.ic_favorite_checked);
        else mFavorite.setImageResource(R.drawable.ic_favorite_unchecked);
    }

    private void getDataSqLite(){
        MovieParcelable fill = getIntent().getParcelableExtra("film");
        favoriteHelper=new FavoriteHelper(this);
        favoriteHelper.open();

        Cursor cursor = getContentResolver().query(Uri.parse(CONTENT_URI + "/" + fill.getId()),null,
                null,
                null,
                null);

        if (cursor != null){
            if (cursor.moveToFirst())isFavorite=true;
            cursor.close();
        }
        ClickFavorite();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void SaveToFavorite(){
        MovieParcelable fill = getIntent().getParcelableExtra("film");
        ContentValues values=new ContentValues();
        values.put(ID,fill.getId());
        values.put(TITLE,fill.getTitle());
        values.put(POSTER,fill.getPosterPath());
        values.put(DESCRIPTION,fill.getDescription());
        values.put(BACKDROP_POSTER,fill.getBackdropPath());
        values.put(VOTE_AVERAGE,fill.getVoteAverage());
        values.put(RELEASE,fill.getReleaseDate());
        values.put(POPULARITY,fill.getPopularity());
        values.put(LANGUAGE,fill.getLanguage_ori());
        getContentResolver().insert(CONTENT_URI,values);
        Toast.makeText(this,"Favorited",Toast.LENGTH_SHORT).show();
    }

    private void RemoveFromFavorite(){
        MovieParcelable fill = getIntent().getParcelableExtra("film");
        getContentResolver().delete(Uri.parse(CONTENT_URI + "/" + fill.getId() ),null,
                null);
        Toast.makeText(this,"Unfavorited",Toast.LENGTH_SHORT).show();
    }


}
