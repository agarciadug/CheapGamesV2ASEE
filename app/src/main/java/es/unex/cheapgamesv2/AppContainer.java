package es.unex.cheapgamesv2;

import android.content.Context;

import es.unex.cheapgamesv2.data.DealRepository;
import es.unex.cheapgamesv2.data.VideogameRepository;
import es.unex.cheapgamesv2.data.network.VideogameDetailNetworkDataSource;
import es.unex.cheapgamesv2.data.network.VideogameNetworkDataSource;
import es.unex.cheapgamesv2.data.room.CheapGamesDB;
import es.unex.cheapgamesv2.ui.detail.DetailVideogameViewModelFactory;
import es.unex.cheapgamesv2.ui.search.SearchVideogameViewModelFactory;

public class AppContainer {

    private CheapGamesDB database;
    private VideogameNetworkDataSource networkDataSource;
    public VideogameRepository repository;
    public SearchVideogameViewModelFactory factory;
    public VideogameDetailNetworkDataSource dvNetworkDataSource;
    public DealRepository dealRepository;
    public DetailVideogameViewModelFactory dVfactory;

    public AppContainer(Context context){
        database = CheapGamesDB.getInstance(context);
        networkDataSource = VideogameNetworkDataSource.getInstance();
        repository = VideogameRepository.getInstance(database.videogameDao(), networkDataSource);
        factory = new SearchVideogameViewModelFactory(repository);
        dvNetworkDataSource = VideogameDetailNetworkDataSource.getInstance();
        dealRepository = DealRepository.getInstance(database.videogameDealDao(),dvNetworkDataSource);
        dVfactory = new DetailVideogameViewModelFactory(dealRepository);
    }
}
