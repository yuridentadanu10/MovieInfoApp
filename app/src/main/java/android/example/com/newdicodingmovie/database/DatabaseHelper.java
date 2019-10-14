package android.example.com.newdicodingmovie.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import static android.example.com.newdicodingmovie.database.ContractDatabase.MovieColumns.BACKDROP_POSTER;
import static android.example.com.newdicodingmovie.database.ContractDatabase.MovieColumns.DESCRIPTION;
import static android.example.com.newdicodingmovie.database.ContractDatabase.MovieColumns.ID;
import static android.example.com.newdicodingmovie.database.ContractDatabase.MovieColumns.LANGUAGE;
import static android.example.com.newdicodingmovie.database.ContractDatabase.MovieColumns.POPULARITY;
import static android.example.com.newdicodingmovie.database.ContractDatabase.MovieColumns.POSTER;
import static android.example.com.newdicodingmovie.database.ContractDatabase.MovieColumns.RELEASE;
import static android.example.com.newdicodingmovie.database.ContractDatabase.MovieColumns.TITLE;
import static android.example.com.newdicodingmovie.database.ContractDatabase.MovieColumns.VOTE_AVERAGE;
import static android.example.com.newdicodingmovie.database.ContractDatabase.TABLE_MOVIE;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbFavoriteMovie";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_FAVORITE="create table " + TABLE_MOVIE + " ( " +
                ID + " integer primary key autoincrement, " +
                TITLE + " text not null, " +
                DESCRIPTION + " text not null, " +
                POSTER + " text not null, " +
                POPULARITY + " text not null, " +
                LANGUAGE + " text not null, " +
                RELEASE + " text not null, " +
                VOTE_AVERAGE + " text not null, " +
                BACKDROP_POSTER + " text not null " +
                " ); " ;
        sqLiteDatabase.execSQL(CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_MOVIE );
        onCreate(sqLiteDatabase);
    }
}
