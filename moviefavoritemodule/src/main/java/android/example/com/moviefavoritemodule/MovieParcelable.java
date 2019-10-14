package android.example.com.moviefavoritemodule;

import android.database.Cursor;
import android.example.com.moviefavoritemodule.database.ContractDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import static android.example.com.moviefavoritemodule.database.ContractDatabase.getColumnInt;
import static android.example.com.moviefavoritemodule.database.ContractDatabase.getColumnString;

public class MovieParcelable implements Parcelable {
    private int id;
    private String title;
    private String vote_average;
    private String overview;
    private String release_date;
    private String poster_path;
    private String backdrop_path;


    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }


    public String getRelease_date() {
        return release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    protected MovieParcelable(Parcel in) {
        this.title = in.readString();
        this.vote_average = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.poster_path = in.readString();
        this.backdrop_path = in.readString();
    }

    public static final Creator<MovieParcelable> CREATOR = new Creator<MovieParcelable>() {
        @Override
        public MovieParcelable createFromParcel(Parcel in) {
            return new MovieParcelable(in);
        }

        @Override
        public MovieParcelable[] newArray(int size) {
            return new MovieParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.title);
        parcel.writeString(this.vote_average);
        parcel.writeString(this.overview);
        parcel.writeString(this.release_date);
        parcel.writeString(this.poster_path);
        parcel.writeString(this.backdrop_path);
    }

    public MovieParcelable(int id, String title, String vote_average, String overview, String release_date, String poster_path, String backdrop_path) {
        this.id = id;
        this.title = title;
        this.vote_average = vote_average;
        this.overview = overview;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
    }

    public MovieParcelable(Cursor cursor) {
        this.id = getColumnInt(cursor, ContractDatabase.MovieColumns.ID);
        this.title = getColumnString(cursor, ContractDatabase.MovieColumns.TITLE);
        this.overview = getColumnString(cursor, ContractDatabase.MovieColumns.DESCRIPTION);
        this.release_date = getColumnString(cursor, ContractDatabase.MovieColumns.RELEASE);
        this.vote_average = getColumnString(cursor, ContractDatabase.MovieColumns.VOTE_AVERAGE);
        this.poster_path = getColumnString(cursor, ContractDatabase.MovieColumns.POSTER);
        this.backdrop_path = getColumnString(cursor, ContractDatabase.MovieColumns.BACKDROP_POSTER);
    }

}
