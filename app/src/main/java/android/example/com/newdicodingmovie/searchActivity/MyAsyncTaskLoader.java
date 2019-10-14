package android.example.com.newdicodingmovie.searchActivity;

import android.content.Context;
import android.example.com.newdicodingmovie.BuildConfig;
import android.example.com.newdicodingmovie.parcelable.MovieParcelable;

import androidx.loader.content.AsyncTaskLoader;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MyAsyncTaskLoader extends AsyncTaskLoader<ArrayList<MovieParcelable>> {

    private ArrayList<MovieParcelable> movieData;
    private boolean requestHasResult = false;

    private String mode;
    private String url;
    private String movieTitle;
    private String filter;


    MyAsyncTaskLoader(final Context context, String mode, String query, String filterSearch) {
        super(context);

        onContentChanged();
        this.mode = mode;
        this.movieTitle = query;
        this.filter = "movie";


    }

    @Override
    protected void onStartLoading() {
        if(takeContentChanged()) {
            forceLoad();
        } else if (requestHasResult){
            deliverResult(movieData);
        }
    }

    @Override
    public void deliverResult(final ArrayList<MovieParcelable> data) {
        movieData = data;
        requestHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();

        if (requestHasResult) {
            movieData = null;
            requestHasResult = false;
        }
    }

    private static final String API_KEY = BuildConfig.API_KEY;

    @Override
    public ArrayList<MovieParcelable> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MovieParcelable> allMovies = new ArrayList<>();

        switch (mode) {
            case "nowplaying":
                url = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + API_KEY + "&language=en-US&page=1";
                break;
            case "search":
                url = "https://api.themoviedb.org/3/search/" + filter + "?api_key=" + API_KEY + "&language=en-US&query=" + movieTitle;
                break;
        }

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0 ; i < list.length() ; i++) {
                        JSONObject movie = list.getJSONObject(i);
                        MovieParcelable movieItems = new MovieParcelable(movie);
                        allMovies.add(movieItems);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });

        return allMovies;
    }
}
