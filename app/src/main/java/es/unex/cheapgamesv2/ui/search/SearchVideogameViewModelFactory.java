package es.unex.cheapgamesv2.ui.search;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import es.unex.cheapgamesv2.data.VideogameRepository;

public class SearchVideogameViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    private final VideogameRepository mRepository;

    public SearchVideogameViewModelFactory(VideogameRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

        return (T) new SearchVideogameActivityViewModel(mRepository);
    }
}
