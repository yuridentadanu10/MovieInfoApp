package android.example.com.newdicodingmovie.parcelable;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import static android.example.com.newdicodingmovie.database.ContractDatabase.MovieColumns.BACKDROP_POSTER;
import static android.example.com.newdicodingmovie.database.ContractDatabase.MovieColumns.DESCRIPTION;
import static android.example.com.newdicodingmovie.database.ContractDatabase.MovieColumns.LANGUAGE;
import static android.example.com.newdicodingmovie.database.ContractDatabase.MovieColumns.POPULARITY;
import static android.example.com.newdicodingmovie.database.ContractDatabase.MovieColumns.POSTER;
import static android.example.com.newdicodingmovie.database.ContractDatabase.MovieColumns.RELEASE;
import static android.example.com.newdicodingmovie.database.ContractDatabase.MovieColumns.TITLE;
import static android.example.com.newdicodingmovie.database.ContractDatabase.MovieColumns.VOTE_AVERAGE;
import static android.example.com.newdicodingmovie.database.ContractDatabase.getColumnInt;
import static android.example.com.newdicodingmovie.database.ContractDatabase.getColumnString;
import static android.provider.BaseColumns._ID;



public class MovieParcelable implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("overview")
    private String description;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("vote_average")
    private String voteAverage;
    @SerializedName("original_language")
    private String language_ori;
    @SerializedName("popularity")
    private String popularity;

    public MovieParcelable () {

    }

    public MovieParcelable(JSONObject object) {
        try {
            int id = object.getInt("id");
            String title = object.getString("title");
            String description = object.getString("overview");
            String voteAverage = object.getString("vote_average");
            String language_ori = object.getString("original_language");
            String release_date = object.getString("release_date");
            String poster_path = object.getString("poster_path");
            String backdrop_path = object.getString("backdrop_path");

            this.id = id;
            this.title = title;
            this.description = description;
            this.language_ori = language_ori;
            this.popularity = popularity;
            this.voteAverage = voteAverage;
            this.releaseDate = release_date;
            this.posterPath = poster_path;
            this.backdropPath = backdrop_path;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getLanguage_ori() {
        return language_ori;
    }

    public void setLanguage_ori(String language_ori) {
        this.language_ori = language_ori;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    protected MovieParcelable(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readString();
        popularity = in.readString();
        language_ori = in.readString();
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

        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(posterPath);
        parcel.writeString(backdropPath);
        parcel.writeString(releaseDate);
        parcel.writeString(voteAverage);
        parcel.writeString(popularity);
        parcel.writeString(language_ori);
    }

    public MovieParcelable(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor,TITLE);
        this.description = getColumnString(cursor, DESCRIPTION);
        this.posterPath = getColumnString(cursor, POSTER);
        this.voteAverage = getColumnString(cursor, VOTE_AVERAGE);
        this.releaseDate = getColumnString(cursor, RELEASE);
        this.backdropPath = getColumnString(cursor, BACKDROP_POSTER);
        this.popularity = getColumnString(cursor, POPULARITY);
        this.language_ori = getColumnString(cursor, LANGUAGE);
    }
}