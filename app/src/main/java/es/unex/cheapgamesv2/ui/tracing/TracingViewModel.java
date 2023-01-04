package es.unex.cheapgamesv2.ui.tracing;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.cheapgamesv2.data.VideogameRepository;
import es.unex.cheapgamesv2.data.model.Videogame;

public class TracingViewModel extends ViewModel {

    private final VideogameRepository mRepository;
    private final LiveData<List<Videogame>> mVideogames;
    private String mIds = "";

    public TracingViewModel(VideogameRepository repository) {
        mRepository = repository;
        mVideogames = mRepository.getCurrentVideogames();
    }

    public void setIds(String ids){
        mIds = ids;
        mRepository.setTitleVideogame(ids);
    }

    public void onRefresh() {
        mRepository.doFetchRepos(mIds);
    }

    public LiveData<List<Videogame>> getVideogames() {
        return mVideogames;
    }
}