package android.example.com.newdicodingmovie.base;

import android.example.com.newdicodingmovie.apiSource.DataSourceAPI;

import androidx.fragment.app.Fragment;

public class BaseFragment  extends Fragment{

        public static final String KEY_MOVIES = "movies";
        public static final String KEY_SERIES = "series";

        public DataSourceAPI getMovieDataSources () {
        return new DataSourceAPI();
    }

    }
