package android.example.com.moviefavoritemodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.HandlerThread;
import android.widget.Toast;

import java.util.ArrayList;

import static android.example.com.moviefavoritemodule.database.ContractDatabase.CONTENT_URI;
import static android.example.com.moviefavoritemodule.database.HelperMapping.mapCursortoList;

public class MainActivity extends AppCompatActivity implements LoadFavoriteCallback{
private FavoriteAdapter favoriteAdapter;
private RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.movies_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        favoriteAdapter = new FavoriteAdapter(this);
        recyclerView.setAdapter(favoriteAdapter);
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        new getInBackground().execute();
    }

    @Override
    public void postExecute(Cursor movies) {
        ArrayList<MovieParcelable> listMovies = mapCursortoList(movies);
        if (listMovies.size() > 0) {
            favoriteAdapter.setFavoriteMovie(listMovies);
        } else {
            Toast.makeText(this, "Favorite Masih Kosong", Toast.LENGTH_SHORT).show();
            favoriteAdapter.setFavoriteMovie(new ArrayList<MovieParcelable>());
        }
    }

    private class getInBackground extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor movies) {
            super.onPostExecute(movies);
            favoriteAdapter.setListMovies(movies);
            favoriteAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(favoriteAdapter);
        }
    }
}

