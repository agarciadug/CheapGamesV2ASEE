package es.unex.cheapgamesv2.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.cheapgamesv2.data.DealRepository;
import es.unex.cheapgamesv2.data.model.Videogame;
import es.unex.cheapgamesv2.data.model.VideogameDeal;

public class DetailVideogameActivityViewModel extends ViewModel {

    private final DealRepository mRepository;
    private final LiveData<List<VideogameDeal>> mVideogamesDeal;
    private String mVideogameID = "";

    public DetailVideogameActivityViewModel(DealRepository repository) {
        mRepository = repository;
        mVideogamesDeal = mRepository.getCurrentVideogames();
    }

    public void setVideogameID(String videogameID){
        mVideogameID = videogameID;
        mRepository.setVideogameDealID(videogameID);
    }

    public void onRefresh() {
        mRepository.doFetchRepos(mVideogameID);
    }

    public LiveData<List<VideogameDeal>> getVideogameDeals() {
        return mVideogamesDeal;
    }
}
