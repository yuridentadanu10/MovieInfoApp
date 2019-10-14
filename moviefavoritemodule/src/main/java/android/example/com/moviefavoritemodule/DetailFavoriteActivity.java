package android.example.com.moviefavoritemodule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailFavoriteActivity extends AppCompatActivity {

    public static String MOVIE_ITEM = "film";
    private MovieParcelable movieParcel;
    TextView mtitle, mreleaseDate, mOverview;
    ImageView mposterPath,mBackPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_favorite);

        movieParcel  = getIntent().getParcelableExtra(MOVIE_ITEM);
        mposterPath = findViewById(R.id.img_movie);
        mBackPoster = findViewById(R.id.ivBackPoster);
        mtitle = findViewById(R.id.tvTitle);
        mreleaseDate = findViewById(R.id.tvReleaseDate);
        mOverview = findViewById(R.id.tvOverview);

        getSupportActionBar().setTitle(movieParcel.getTitle());
        mtitle.setText(movieParcel.getTitle());
        mOverview.setText(movieParcel.getOverview());
        Picasso.get().load("https://image.tmdb.org/t/p/w185/" + movieParcel.getPoster_path()).into(mposterPath);
        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + movieParcel.getBackdrop_path()).placeholder(this.getResources().getDrawable(R.drawable.ic_launcher_foreground)).error(this.getResources().getDrawable(R.drawable.ic_launcher_foreground)).into(mBackPoster);
        String release =movieParcel.getRelease_date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date date = dateFormat.parse(release);
            SimpleDateFormat baru=new SimpleDateFormat("EEEE, dd MMM yyyy");
            String NewRelease = baru.format(date);
            mreleaseDate.setText(NewRelease);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
