package android.example.com.newdicodingmovie.fragment;


import android.content.Context;
import android.database.Cursor;
import android.example.com.newdicodingmovie.adapter.FavoriteAdapter;
import android.example.com.newdicodingmovie.base.BaseFragment;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.example.com.newdicodingmovie.R;
import android.widget.ProgressBar;

import static android.example.com.newdicodingmovie.database.ContractDatabase.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class MixFavoriteFragment extends BaseFragment {
    private Context context;
    private RecyclerView favoriteRecycleView;
    private Cursor cursor;
    private FavoriteAdapter favoriteAdapter;
    private ProgressBar progressBar;

    public MixFavoriteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mix_favorite, container, false);
        context = view.getContext();
        favoriteRecycleView = view.findViewById(R.id.movie_list);
        progressBar=view.findViewById(R.id.progressBar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.Favorite);
        setupList();
        new MovieAsyncTask().execute();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        new MovieAsyncTask().execute();
    }

    private void setupList() {
        favoriteAdapter = new FavoriteAdapter(cursor);
        favoriteRecycleView.setLayoutManager(new LinearLayoutManager(context));
        favoriteRecycleView.setHasFixedSize(true);
        favoriteRecycleView.setAdapter(favoriteAdapter);
    }

    private class MovieAsyncTask extends AsyncTask<Void, Void, Cursor> {


        @Override
        protected Cursor doInBackground(Void... voids) {
            return context.getContentResolver().query(CONTENT_URI, null, null, null, null);

        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            MixFavoriteFragment.this.cursor = cursor;
            favoriteAdapter.refresh(MixFavoriteFragment.this.cursor);
            showLoading(false);
        }
    }
    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
