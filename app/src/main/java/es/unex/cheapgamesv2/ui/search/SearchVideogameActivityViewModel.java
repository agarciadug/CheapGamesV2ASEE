package es.unex.cheapgamesv2.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.cheapgamesv2.data.VideogameRepository;
import es.unex.cheapgamesv2.data.model.Videogame;

public class SearchVideogameActivityViewModel extends ViewModel {

    private final VideogameRepository mRepository;
    private final LiveData<List<Videogame>> mVideogames;
    private String mTitle = "";

    public SearchVideogameActivityViewModel(VideogameRepository repository) {
        mRepository = repository;
        mVideogames = mRepository.getCurrentVideogames();
    }

    public void setTitle(String title){
        mTitle = title;
        mRepository.setTitleVideogame(title);
    }

    public void onRefresh() { mRepository.doFetchRepos(mTitle);
    }

    public LiveData<List<Videogame>> getVideogames() {
        return mVideogames;
    }
}
