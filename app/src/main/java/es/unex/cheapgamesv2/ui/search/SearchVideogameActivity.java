package es.unex.cheapgamesv2.ui.search;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.SearchView;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.cheapgamesv2.AppContainer;
import es.unex.cheapgamesv2.InjectorUtils;
import es.unex.cheapgamesv2.MyApplication;
import es.unex.cheapgamesv2.data.model.Videogame;
import es.unex.cheapgamesv2.databinding.ActivitySearchVideogameBinding;
//import es.unex.cheapgamesv2.ui.search.databinding.ActivitySearchVideogameBinding;

import es.unex.cheapgamesv2.R;

public class SearchVideogameActivity extends AppCompatActivity {

    private VideogameAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SearchView mSearchView;

    private AppBarConfiguration appBarConfiguration;
    private ActivitySearchVideogameBinding binding;
    RecyclerView listvjBusqueda;
    List<Videogame> lVjBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySearchVideogameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mRecyclerView = findViewById(R.id.lista_busqueda);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new VideogameAdapter(this,new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);

        SearchVideogameViewModelFactory factory = InjectorUtils.provideMainActivityViewModelFactory(this.getApplicationContext());
        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        SearchVideogameActivityViewModel mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory)appContainer.factory).get(SearchVideogameActivityViewModel.class);
        mViewModel.getVideogames().observe(this, videogames -> {
            mAdapter.swap(videogames);
            // Show the repo list or the loading screen based on whether the repos data exists and is loaded
            if (videogames != null && videogames.size() != 0) showReposDataView();
            //else showLoading();
        });

        mSearchView = findViewById(R.id.search_videogame);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mViewModel.setTitle(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        /*NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_search_videogame);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);*/
    }

    private void showReposDataView(){
        /*mProgressBar.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);*/
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /*@Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_search_videogame);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }*/
}