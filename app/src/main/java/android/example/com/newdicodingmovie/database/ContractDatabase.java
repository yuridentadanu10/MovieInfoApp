package android.example.com.newdicodingmovie.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class ContractDatabase {

    public static String TABLE_MOVIE = "favorite";
    private static final String SCHEME_MOVIE = "content";
    public static final String AUTHORITY = "android.example.com.newdicodingmovie";

   public static final class MovieColumns implements BaseColumns {
       public static String ID = "_id";
       public static String TITLE = "title";
       public static String DESCRIPTION = "description";
       public  static String POSTER = "poster";
       public static String RELEASE = "releaseDate";
       public static String VOTE_AVERAGE = "voteAverage";
       public static String BACKDROP_POSTER = "backdropPath";
       public static String POPULARITY = "popularity";
       public static String LANGUAGE = "language";
    }

    public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME_MOVIE)
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

}
