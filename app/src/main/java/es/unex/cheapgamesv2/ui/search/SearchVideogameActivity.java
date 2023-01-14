package es.unex.cheapgamesv2.ui.search;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import es.unex.cheapgamesv2.AppContainer;
import es.unex.cheapgamesv2.InjectorUtils;
import es.unex.cheapgamesv2.MyApplication;
import es.unex.cheapgamesv2.R;
import es.unex.cheapgamesv2.databinding.ActivitySearchVideogameBinding;

public class SearchVideogameActivity extends AppCompatActivity {

    private VideogameAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SearchView mSearchView;

    private ActivitySearchVideogameBinding binding;


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
                mAdapter.clear(); return false;
            }
        });
    }

    private void showReposDataView(){
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        mAdapter.clear();
        return super.onSupportNavigateUp();
    }
}