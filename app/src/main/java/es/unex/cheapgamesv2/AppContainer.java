package es.unex.cheapgamesv2;

import android.content.Context;

import es.unex.cheapgamesv2.data.VideogameRepository;
import es.unex.cheapgamesv2.data.network.VideogameNetworkDataSource;
import es.unex.cheapgamesv2.data.room.CheapGamesDB;
import es.unex.cheapgamesv2.ui.search.SearchVideogameViewModelFactory;

public class AppContainer {

    private CheapGamesDB database;
    private VideogameNetworkDataSource networkDataSource;
    public VideogameRepository repository;
    public SearchVideogameViewModelFactory factory;

    public AppContainer(Context context){
        database = CheapGamesDB.getInstance(context);
        networkDataSource = VideogameNetworkDataSource.getInstance();
        repository = VideogameRepository.getInstance(database.videogameDao(), networkDataSource);
        factory = new SearchVideogameViewModelFactory(repository);
    }
}
