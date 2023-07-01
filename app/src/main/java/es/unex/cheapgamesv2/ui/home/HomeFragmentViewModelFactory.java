package es.unex.cheapgamesv2.ui.home;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import es.unex.cheapgamesv2.data.BestDealsRepository;
import es.unex.cheapgamesv2.data.VideogameRepository;
import es.unex.cheapgamesv2.ui.search.SearchVideogameFragmentViewModel;

public class HomeFragmentViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    private final BestDealsRepository mRepository;

    public HomeFragmentViewModelFactory(BestDealsRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

        return (T) new HomeFragmentViewModel(mRepository);
    }
}
