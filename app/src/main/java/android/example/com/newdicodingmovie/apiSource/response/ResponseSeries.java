package android.example.com.newdicodingmovie.apiSource.response;

import android.example.com.newdicodingmovie.parcelable.SeriesParcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseSeries {
    @SerializedName("results")
    private ArrayList<SeriesParcelable> results;
    public ArrayList<SeriesParcelable> getResults(){
        return results;
    }
}
