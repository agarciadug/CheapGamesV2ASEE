package es.unex.cheapgamesv2;

import android.content.Context;

import es.unex.cheapgamesv2.data.BestDealsRepository;
import es.unex.cheapgamesv2.data.DealRepository;
import es.unex.cheapgamesv2.data.VideogameRepository;
import es.unex.cheapgamesv2.data.model.UsuarioGlobal;
import es.unex.cheapgamesv2.data.network.VideogameBestNetworkDataSource;
import es.unex.cheapgamesv2.data.network.VideogameDetailNetworkDataSource;
import es.unex.cheapgamesv2.data.network.VideogameNetworkDataSource;
import es.unex.cheapgamesv2.data.room.CheapGamesDB;
import es.unex.cheapgamesv2.data.room.VideogameDealDao;
import es.unex.cheapgamesv2.ui.detail.DetailVideogameViewModelFactory;
import es.unex.cheapgamesv2.ui.home.HomeFragmentViewModel;
import es.unex.cheapgamesv2.ui.home.HomeFragmentViewModelFactory;
import es.unex.cheapgamesv2.ui.home.HomeViewModel;
import es.unex.cheapgamesv2.ui.search.SearchVideogameViewModelFactory;

public class AppContainer {
    private CheapGamesDB database;
    private VideogameNetworkDataSource networkDataSource;
    private VideogameDetailNetworkDataSource dvNetworkDataSource;
    private VideogameBestNetworkDataSource videogameBestNetworkDataSource;
    public VideogameRepository repository;
    public SearchVideogameViewModelFactory factory;
    public DealRepository dealRepository;
    public DetailVideogameViewModelFactory dVfactory;
    public BestDealsRepository bestDealsRepository;
    public HomeFragmentViewModelFactory homeViewModelFactory;
    public UsuarioGlobal usuarioGlobal;

    public AppContainer(Context context){
        database = CheapGamesDB.getInstance(context);
        networkDataSource = VideogameNetworkDataSource.getInstance();
        repository = VideogameRepository.getInstance(database.videogameDao(), networkDataSource);
        factory = new SearchVideogameViewModelFactory(repository);
        dvNetworkDataSource = VideogameDetailNetworkDataSource.getInstance();
        dealRepository = DealRepository.getInstance(database.videogameDealDao(),dvNetworkDataSource);
        dVfactory = new DetailVideogameViewModelFactory(dealRepository);
        videogameBestNetworkDataSource = VideogameBestNetworkDataSource.getInstance();
        bestDealsRepository = BestDealsRepository.getInstance(database.videogameBestDao(),videogameBestNetworkDataSource);
        homeViewModelFactory = new HomeFragmentViewModelFactory(bestDealsRepository);

        usuarioGlobal = UsuarioGlobal.getInstance(); // Crear instancia de UsuarioGlobal
    }
}

