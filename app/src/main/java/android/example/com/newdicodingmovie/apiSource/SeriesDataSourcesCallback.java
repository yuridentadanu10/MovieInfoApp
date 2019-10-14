package android.example.com.newdicodingmovie.apiSource;

import android.example.com.newdicodingmovie.apiSource.response.ResponseSeries;

public interface SeriesDataSourcesCallback {
    void onSuccess(ResponseSeries movieResponse);

    void onFailed(String error);
}
