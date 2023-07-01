package es.unex.cheapgamesv2.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.cheapgamesv2.data.BestDealsRepository;
import es.unex.cheapgamesv2.data.VideogameRepository;
import es.unex.cheapgamesv2.data.model.Videogame;
import es.unex.cheapgamesv2.data.model.VideogameBest;
import es.unex.cheapgamesv2.data.model.VideogameDeal;

public class HomeFragmentViewModel extends ViewModel {

    private final BestDealsRepository mRepository;
    private final LiveData<List<VideogameBest>> mVideogames;

    public HomeFragmentViewModel(BestDealsRepository repository) {
        mRepository = repository;
        mVideogames = mRepository.getCurrentVideogames();
    }

    public void onRefresh() { mRepository.doFetchRepos();}

    public LiveData<List<VideogameBest>> getVideogames() {
        return mVideogames;
    }
}
