package es.unex.cheapgamesv2.ui.detail;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import es.unex.cheapgamesv2.data.DealRepository;

public class DetailVideogameViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private final DealRepository mRepository;

    public DetailVideogameViewModelFactory(DealRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

        return (T) new DetailVideogameActivityViewModel(mRepository);
    }

}
