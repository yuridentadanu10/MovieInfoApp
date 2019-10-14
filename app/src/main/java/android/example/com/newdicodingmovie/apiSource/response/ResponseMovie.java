package android.example.com.newdicodingmovie.apiSource.response;

import android.example.com.newdicodingmovie.parcelable.MovieParcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseMovie {
    @SerializedName("results")
    private ArrayList<MovieParcelable> results;
    public ArrayList<MovieParcelable> getResults(){
        return results;
    }
}
