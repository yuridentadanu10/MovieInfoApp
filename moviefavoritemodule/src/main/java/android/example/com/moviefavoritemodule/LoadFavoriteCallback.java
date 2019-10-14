package android.example.com.moviefavoritemodule;

import android.database.Cursor;

public interface LoadFavoriteCallback {
    void postExecute(Cursor movieFavorite);
}
