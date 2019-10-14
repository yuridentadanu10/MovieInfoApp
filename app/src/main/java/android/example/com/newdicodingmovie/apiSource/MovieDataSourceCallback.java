package android.example.com.newdicodingmovie.apiSource;

import android.example.com.newdicodingmovie.apiSource.response.ResponseMovie;

public interface MovieDataSourceCallback {
    void onSuccess(ResponseMovie movieResponse);

    void onFailed(String error);

}
