package es.unex.cheapgamesv2.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import es.unex.cheapgamesv2.AppContainer;
import es.unex.cheapgamesv2.InjectorUtils;
import es.unex.cheapgamesv2.MyApplication;
import es.unex.cheapgamesv2.R;
import es.unex.cheapgamesv2.databinding.ActivitySearchVideogameBinding;

public class SearchVideogameFragment extends Fragment {

    private VideogameAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SearchView mSearchView;

    private ActivitySearchVideogameBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivitySearchVideogameBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mRecyclerView = view.findViewById(R.id.lista_busqueda);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mAdapter = new VideogameAdapter(requireContext(), new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);

        SearchVideogameViewModelFactory factory = InjectorUtils.provideMainActivityViewModelFactory(requireContext().getApplicationContext());
        AppContainer appContainer = ((MyApplication) requireActivity().getApplication()).appContainer;
        SearchVideogameFragmentViewModel mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) appContainer.factory).get(SearchVideogameFragmentViewModel.class);
        mViewModel.getVideogames().observe(getViewLifecycleOwner(), videogames -> {
            mAdapter.swap(videogames);
            // Mostrar la lista de repositorios o la pantalla de carga según si los datos de repositorios existen y están cargados
            if (videogames != null && videogames.size() != 0) {
                showReposDataView();
            }
        });

        mSearchView = view.findViewById(R.id.search_videogame);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mViewModel.setTitle(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.clear();
                return false;
            }
        });
    }

    private void showReposDataView() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mAdapter.clear();
            mSearchView.setQuery("235SAD3242GSDG", true);
            mSearchView.setVisibility(View.INVISIBLE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
