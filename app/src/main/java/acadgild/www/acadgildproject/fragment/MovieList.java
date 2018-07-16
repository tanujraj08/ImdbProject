package acadgild.www.acadgildproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import acadgild.www.acadgildproject.R;
import acadgild.www.acadgildproject.adapter.ListAdapter;
import acadgild.www.acadgildproject.async.GetMovieInfo;
import acadgild.www.acadgildproject.async.GetSingleMovieInfo;
import acadgild.www.acadgildproject.database.MovieDataBase;
import acadgild.www.acadgildproject.model.Constants;
import acadgild.www.acadgildproject.model.MovieInfo;
import acadgild.www.acadgildproject.views.DetailsActivity;

public class MovieList extends ListFragment {

    public String URL;
    private ListView listview;
    private List<MovieInfo> movieList;
    public ListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.movie_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listview = getListView();
        movieList = new ArrayList<>();

        SetList(Constants.MOVIE_NOW_PLAYING);

        getActivity().setTitle(Constants.NOW_PLAYING);

        adapter = new ListAdapter(getActivity(), R.layout.list_item, movieList);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        MovieInfo movieInfo = movieList.get(position);
        adapter.imageLoader.clearCache();
        showDetails(movieInfo.getId());
    }

    void showDetails(String id) {

        Intent intent = new Intent();
        intent.setClass(getActivity(), DetailsActivity.class);
        intent.putExtra("MovieID", id);
        startActivity(intent);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {

            case R.id.watchlist:

                WatchList();

                return true;

            case R.id.favorites:

                Favorites();

                return true;

            case R.id.refresh:

                String barTitle = getActivity().getTitle().toString();

                switch(barTitle) {
                    case Constants.MOST_POPULAR:
                        SetList(Constants.MOVIE_POPULAR);
                    case Constants.UPCOMING:
                        SetList(Constants.MOVIE_UPCOMING);
                    case Constants.NOW_PLAYING:
                        SetList(Constants.MOVIE_NOW_PLAYING);
                    case Constants.TOP_RATED:
                        SetList(Constants.MOVIE_TOP_RATED);
                    case Constants.LATEST:
                        SetListSingleMovie(Constants.MOVIE_LATEST);
                }

                return true;

            case R.id.most_popular:

                SetList(Constants.MOVIE_POPULAR);
                getActivity().setTitle(Constants.MOST_POPULAR);

                return true;

            case R.id.upcoming_movies:

                SetList(Constants.MOVIE_UPCOMING);
                getActivity().setTitle(Constants.UPCOMING);

                return true;

            case R.id.latest_movies:

                SetListSingleMovie(Constants.MOVIE_LATEST);
                getActivity().setTitle(Constants.LATEST);

                return true;

            case R.id.now_playing:

                SetList(Constants.MOVIE_NOW_PLAYING);
                getActivity().setTitle(Constants.NOW_PLAYING);

                return true;

            case R.id.top_rated:

                SetList(Constants.MOVIE_TOP_RATED);
                getActivity().setTitle(Constants.TOP_RATED);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void SetList(String context_path) {

        URL = Constants.BASE_URL + Constants.API_VERSION + "/" + context_path + Constants.API_KEY;
        movieList.clear();
        new GetMovieInfo(getActivity(), movieList, listview).execute(URL);
    }

    private void SetListSingleMovie(String context_path) {

        URL = Constants.BASE_URL + Constants.API_VERSION + "/" + context_path + Constants.API_KEY;
        movieList.clear();
        new GetSingleMovieInfo(getActivity(), movieList, listview).execute(URL);
    }

    private void Favorites() {
        movieList.clear();
        MovieDataBase db = new MovieDataBase(getActivity());
        movieList = db.getFavorites();
        if (movieList.isEmpty()) {
            Toast.makeText(getActivity(), "Favorites list is empty", Toast.LENGTH_SHORT).show();
        } else {
            getActivity().setTitle(Constants.FAVORITES);
            adapter = new ListAdapter(getActivity(), R.layout.list_item, movieList);
            listview.setAdapter(adapter);
        }
    }

    private void WatchList() {
        movieList.clear();
        MovieDataBase db = new MovieDataBase(getActivity());
        movieList = db.getWatchList();
        if (movieList.isEmpty()) {
            Toast.makeText(getActivity(), "Watchlist is empty", Toast.LENGTH_SHORT).show();
        } else {
            getActivity().setTitle(Constants.WATCHLIST);
            adapter = new ListAdapter(getActivity(), R.layout.list_item, movieList);
            listview.setAdapter(adapter);
        }
    }

}

