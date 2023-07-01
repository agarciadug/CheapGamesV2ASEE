package es.unex.cheapgamesv2;

import android.content.Context;

import es.unex.cheapgamesv2.data.BestDealsRepository;
import es.unex.cheapgamesv2.data.DealRepository;
import es.unex.cheapgamesv2.data.VideogameRepository;
import es.unex.cheapgamesv2.data.network.VideogameBestNetworkDataSource;
import es.unex.cheapgamesv2.data.network.VideogameDetailNetworkDataSource;
import es.unex.cheapgamesv2.data.network.VideogameNetworkDataSource;
import es.unex.cheapgamesv2.data.room.CheapGamesDB;
import es.unex.cheapgamesv2.ui.detail.DetailVideogameViewModelFactory;
import es.unex.cheapgamesv2.ui.home.HomeFragmentViewModelFactory;
import es.unex.cheapgamesv2.ui.search.SearchVideogameViewModelFactory;

/**
 * Provides static methods to inject the various classes needed for the app
 */
public class InjectorUtils {

    public static VideogameRepository provideRepository(Context context) {
        CheapGamesDB database = CheapGamesDB.getInstance(context.getApplicationContext());
        VideogameNetworkDataSource networkDataSource = VideogameNetworkDataSource.getInstance();
        return VideogameRepository.getInstance(database.videogameDao(), networkDataSource);
    }

    public static SearchVideogameViewModelFactory provideMainActivityViewModelFactory(Context context) {
        VideogameRepository repository = provideRepository(context.getApplicationContext());
        return new SearchVideogameViewModelFactory(repository);
    }

    public static DealRepository provideDealRepository(Context context) {
        CheapGamesDB database = CheapGamesDB.getInstance(context.getApplicationContext());
        VideogameDetailNetworkDataSource networkDataSource = VideogameDetailNetworkDataSource.getInstance();
        return DealRepository.getInstance(database.videogameDealDao(), networkDataSource);
    }

    public static DetailVideogameViewModelFactory provideDetailVideogameViewModelFactory(Context context) {
        DealRepository repository = provideDealRepository(context.getApplicationContext());
        return new DetailVideogameViewModelFactory(repository);
    }

    public static BestDealsRepository provideBestRepository(Context context) {
        CheapGamesDB database = CheapGamesDB.getInstance(context.getApplicationContext());
        VideogameBestNetworkDataSource networkDataSource = VideogameBestNetworkDataSource.getInstance();
        return BestDealsRepository.getInstance(database.videogameBestDao(), networkDataSource);
    }

    public static HomeFragmentViewModelFactory provideHomeFragmentViewModelFactory(Context context) {
        BestDealsRepository repository = provideBestRepository(context.getApplicationContext());
        return new HomeFragmentViewModelFactory(repository);
    }

}