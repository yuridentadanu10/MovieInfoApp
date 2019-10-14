package android.example.com.newdicodingmovie.apiSource;

import android.example.com.newdicodingmovie.BuildConfig;
import android.example.com.newdicodingmovie.apiSource.response.ResponseMovie;
import android.example.com.newdicodingmovie.apiSource.response.ResponseSeries;
import android.net.Uri;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class DataSourceAPI {

    public static final String URL_TOP_RATED = "https://api.themoviedb.org/3/movie/top_rated?api_key={apiKey}&language=en-US&page=1";
    public static final String URL_MOVIE = "https://api.themoviedb.org/3/movie/now_playing?api_key={apiKey}&language=en-US&page=1";
    public static final String URL_SERIES = "https://api.themoviedb.org/3/discover/tv?api_key={apiKey}&language=" + switchLanguage.getCountry();
    public static final String URL_DISCOVER = "https://api.themoviedb.org/3/discover/movie";

    public void getSeries(String movieEndpoint, final SeriesDataSourcesCallback callback) {
        AndroidNetworking.get(movieEndpoint)
                .addPathParameter("apiKey", BuildConfig.API_KEY)
                .setTag(DataSourceAPI.class)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsObject(ResponseSeries.class, new ParsedRequestListener<ResponseSeries>() {
                    @Override
                    public void onResponse(ResponseSeries response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ERROR", "onError: ", anError);
                        callback.onFailed("Terjadi kesalahan saat menghubungi server");
                    }
                });
    }

    public void getMovies(String movieEndpoint, final MovieDataSourceCallback callback) {
        AndroidNetworking.get(movieEndpoint)
                .addPathParameter("apiKey", BuildConfig.API_KEY)
                .setTag(DataSourceAPI.class)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsObject(ResponseMovie.class, new ParsedRequestListener<ResponseMovie>() {
                    @Override
                    public void onResponse(ResponseMovie response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ERROR", "onError: ", anError);
                        callback.onFailed("Terjadi kesalahan saat menghubungi server");
                    }
                });
    }

    public static URL getNotificationURL(String date) {
        Uri uri = Uri.parse(URL_DISCOVER).buildUpon()
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .appendQueryParameter("primary_release_date.gte", date)
                .appendQueryParameter("primary_release_date.lte", date).build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static class getHttp {
        public static String getFromHTTP(URL url) throws IOException {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try {
                InputStream inputStream = urlConnection.getInputStream();

                Scanner scanner = new Scanner(inputStream);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    return scanner.next();
                } else {
                    return null;
                }

            } finally {
                urlConnection.disconnect();
            }

        }
    }
}
