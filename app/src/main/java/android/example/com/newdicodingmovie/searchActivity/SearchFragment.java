package android.example.com.newdicodingmovie.searchActivity;


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
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.example.com.newdicodingmovie.R;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieParcelable>>, View.OnClickListener , MovieDataSourceCallback {


    private static final String QUERY_EXTRA = "EXTRAS_QUERY";
    private static final String FILTER_EXTRA = "EXTRAS_FILTER";
    private ArrayList<MovieParcelable> ListMovie = new ArrayList<>();
    private String selectedFilter;
    private ProgressBar progressBar;
    private MovieAdapter movieAdapter;

    private EditText querySearch;
    private String query = "avenger";

    public SearchFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(R.string.search);
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        movieAdapter = new MovieAdapter(getActivity());
        movieAdapter.notifyDataSetChanged();
        RecyclerView recyclerView = view.findViewById(R.id.search_recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(movieAdapter);

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                OpenDetail(ListMovie.get(position));
            }
        });

        querySearch = view.findViewById(R.id.search_query);
        Button searchButton = view.findViewById(R.id.btn_search);
        searchButton.setOnClickListener(this);
        if (savedInstanceState == null) {
            getMovieDataSources().getMovies(DataSourceAPI.URL_MOVIE, this);
        } else {
            ListMovie = savedInstanceState.getParcelableArrayList("film");
            movieAdapter.isiList(ListMovie);
        }
    }

    @NonNull
    @Override
    public Loader<ArrayList<MovieParcelable>> onCreateLoader(int id, @Nullable Bundle args) {
        String filter = "Movies";
        if (args != null) {
            query = args.getString(QUERY_EXTRA);
            filter = args.getString(FILTER_EXTRA);
        }

    showLoading(true);

        return new MyAsyncTaskLoader(getActivity(), "search", query, filter);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<MovieParcelable>> loader, ArrayList<MovieParcelable> data) {
        ListMovie.clear();
        ListMovie.addAll(data);

       showLoading(false);

        movieAdapter.clearAndSetData(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<MovieParcelable>> loader) {

    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("film", ListMovie);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_search) {
            showLoading(true);
            query = querySearch.getText().toString();
            if (TextUtils.isEmpty(query)) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString(QUERY_EXTRA, query);
            bundle.putString(FILTER_EXTRA, selectedFilter);
            getLoaderManager().restartLoader(0, bundle, this);
        }
    }
    private void OpenDetail(MovieParcelable Parcelable) {
        Intent openMovie = new Intent(getActivity(), DetailMovieActivity.class);
        openMovie.putExtra("film", Parcelable);
        startActivity(openMovie);
    }

    @Override
    public void onSuccess(ResponseMovie movieResponse) {
        ListMovie = movieResponse.getResults();
        movieAdapter.isiList(ListMovie);
        showLoading(false);
    }

    @Override
    public void onFailed(String error) {
        error = "Failed";
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}