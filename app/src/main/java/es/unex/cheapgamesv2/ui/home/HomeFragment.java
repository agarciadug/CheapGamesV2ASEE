package es.unex.cheapgamesv2.ui.home;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.cheapgamesv2.AppContainer;
import es.unex.cheapgamesv2.AppExecutors;
import es.unex.cheapgamesv2.InjectorUtils;
import es.unex.cheapgamesv2.MyApplication;
import es.unex.cheapgamesv2.R;
import es.unex.cheapgamesv2.data.BestDealsRepository;
import es.unex.cheapgamesv2.data.model.Deal;
import es.unex.cheapgamesv2.data.model.Videogame;
import es.unex.cheapgamesv2.data.model.VideogameDeal;
import es.unex.cheapgamesv2.data.network.VideogameBestNetworkDataSource;
import es.unex.cheapgamesv2.data.network.VideogamesBestNetworkLoaderRunnable;
import es.unex.cheapgamesv2.data.room.CheapGamesDB;
import es.unex.cheapgamesv2.databinding.FragmentHomeBinding;
import es.unex.cheapgamesv2.ui.detail.DetailVideogameActivityViewModel;
import es.unex.cheapgamesv2.ui.detail.DetailVideogameViewModelFactory;
import es.unex.cheapgamesv2.ui.search.VideogameAdapter;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private DealAdapter mAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private BestDealsRepository mRepository;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HomeFragmentViewModelFactory factory = InjectorUtils.provideHomeFragmentViewModelFactory(requireContext().getApplicationContext());
        AppContainer appContainer = ((MyApplication) requireActivity().getApplication()).appContainer;
        HomeFragmentViewModel mViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.homeViewModelFactory).get(HomeFragmentViewModel.class);
        mViewModel.onRefresh();
        mViewModel.getVideogames().observe(getViewLifecycleOwner(), videogameBestList -> {
            mAdapter.swap(videogameBestList);
            if (videogameBestList != null && videogameBestList.size() != 0) showBestDataView();
            else Log.v("ERROR", "No se muestra nada");
        });
        recyclerView = view.findViewById(R.id.lista_busqueda);
        mAdapter = new DealAdapter(requireContext(), new ArrayList<>());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }
    private void showBestDataView() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}