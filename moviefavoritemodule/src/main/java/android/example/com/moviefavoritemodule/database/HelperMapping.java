package android.example.com.moviefavoritemodule.database;

import android.database.Cursor;
import android.example.com.moviefavoritemodule.MovieParcelable;

import java.util.ArrayList;

public class HelperMapping {

    public static ArrayList<MovieParcelable> mapCursortoList(Cursor movieCursor) {
        ArrayList<MovieParcelable> movieList = new ArrayList<>();
        while (movieCursor.moveToFirst()) {
            int id = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(ContractDatabase.MovieColumns.ID));
            String title = movieCursor.getString(movieCursor.getColumnIndexOrThrow(ContractDatabase.MovieColumns.TITLE));
            String overview = movieCursor.getString(movieCursor.getColumnIndexOrThrow(ContractDatabase.MovieColumns.DESCRIPTION));
            String vote_average = movieCursor.getString(movieCursor.getColumnIndexOrThrow(ContractDatabase.MovieColumns.VOTE_AVERAGE));
            String release_date = movieCursor.getString(movieCursor.getColumnIndexOrThrow(ContractDatabase.MovieColumns.RELEASE));
            String poster_path = movieCursor.getString(movieCursor.getColumnIndexOrThrow(ContractDatabase.MovieColumns.POSTER));
            String backdrop_path = movieCursor.getString(movieCursor.getColumnIndexOrThrow(ContractDatabase.MovieColumns.BACKDROP_POSTER));
            movieList.add(new MovieParcelable(id, title, vote_average, overview, release_date, poster_path, backdrop_path));
        }
        movieCursor.close();
        return movieList;
    }
}
