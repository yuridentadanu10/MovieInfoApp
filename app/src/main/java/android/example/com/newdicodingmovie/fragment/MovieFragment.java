package android.example.com.newdicodingmovie.fragment;


import android.content.Intent;
import android.example.com.newdicodingmovie.ItemClickSupport;
import android.example.com.newdicodingmovie.adapter.MovieAdapter;
import android.example.com.newdicodingmovie.apiSource.DataSourceAPI;
import android.example.com.newdicodingmovie.apiSource.MovieDataSourceCallback;
import android.example.com.newdicodingmovie.apiSource.response.ResponseMovie;
import android.example.com.newdicodingmovie.base.BaseFragment;
import android.example.com.newdicodingmovie.detailActivity.DetailMovieActivity;
import android.example.com.newdicodingmovie.parcelable.MovieParcelable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.example.com.newdicodingmovie.R;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends BaseFragment implements MovieDataSourceCallback {
    private ArrayList<MovieParcelable> movies = new ArrayList<>();
    private MovieAdapter movieAdapter;
    private ProgressBar mProgressBar;
    public static final String MOVIE_ITEM = "film";

    public MovieFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movieAdapter = new MovieAdapter(movies);
        mProgressBar = view.findViewById(R.id.progressBar);
        final RecyclerView movieList = view.findViewById(R.id.movie_list);
        movieList.setHasFixedSize(true);
        movieList.setLayoutManager(new LinearLayoutManager(getContext()));
        movieList.setAdapter(movieAdapter);
        ItemClickSupport.addTo(movieList).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                OpenDetail(movies.get(position));
            }
        });
        showLoading(true);
        if (savedInstanceState == null) {
            getMovieDataSources().getMovies(DataSourceAPI.URL_MOVIE, this);
        } else {
            movies = savedInstanceState.getParcelableArrayList(KEY_MOVIES);
            movieAdapter.isiList(movies);
            showLoading(false);
        }
    }

    private void showLoading(Boolean state) {
        if (state) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSuccess(ResponseMovie movieResponse) {
        movies = movieResponse.getResults();
        movieAdapter.isiList(movies);
        showLoading(false);
    }

    @Override
    public void onFailed(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(KEY_MOVIES, movies);
        super.onSaveInstanceState(outState);
    }

    private void OpenDetail(MovieParcelable Parcelable) {
        Intent openMovie = new Intent(getActivity(), DetailMovieActivity.class);
        openMovie.putExtra(MOVIE_ITEM, Parcelable);
        startActivity(openMovie);
    }
}
