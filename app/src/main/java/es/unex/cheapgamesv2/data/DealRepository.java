package es.unex.cheapgamesv2.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.unex.cheapgamesv2.AppExecutors;
import es.unex.cheapgamesv2.data.model.DetalleVideojuegoRespuesta;
import es.unex.cheapgamesv2.data.model.Videogame;
import es.unex.cheapgamesv2.data.model.VideogameDeal;
import es.unex.cheapgamesv2.data.network.VideogameDetailNetworkDataSource;
import es.unex.cheapgamesv2.data.network.VideogameNetworkDataSource;
import es.unex.cheapgamesv2.data.room.TiendaDao;
import es.unex.cheapgamesv2.data.room.VideogameDao;
import es.unex.cheapgamesv2.data.room.VideogameDealDao;

public class DealRepository {

    private static final String LOG_TAG = DealRepository.class.getSimpleName();

    // For Singleton instantiation
    private static DealRepository sInstance;
    private final VideogameDealDao mVideogameDealDao;
    private final VideogameDetailNetworkDataSource mVideogameDetailNetworkDataSource;
    private final AppExecutors mExecutors = AppExecutors.getInstance();
    private final MutableLiveData<String> userFilterLiveData = new MutableLiveData<>();
    private final Map<String, Long> lastUpdateTimeMillisMap = new HashMap<>();
    private String idVideogame="";
    private ArrayList<VideogameDeal> videogameDealArrayList = new ArrayList<>();
    private static final long MIN_TIME_FROM_LAST_FETCH_MILLIS = 30000;

    private DealRepository(VideogameDealDao videogameDealDao, VideogameDetailNetworkDataSource videogameDetailNetworkDataSource) {

        mVideogameDealDao=videogameDealDao;
        mVideogameDetailNetworkDataSource = videogameDetailNetworkDataSource;
        // LiveData that fetches repos from network
        LiveData<VideogameDeal[]> networkData = mVideogameDetailNetworkDataSource.getCurrentRepos();
        // As long as the repository exists, observe the network LiveData.
        // If that LiveData changes, update the database.
        networkData.observeForever(newVideogamesFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                // Deleting cached repos of user
                if (newVideogamesFromNetwork!=null){
                    mVideogameDealDao.deleteVideogameDealByID(idVideogame);
                }
                // Insert our new repos into local database
                mVideogameDealDao.bulkInsert(Arrays.asList(newVideogamesFromNetwork));
                Log.d(LOG_TAG, "New values inserted in Room");
            });
        });
    }

    public synchronized static DealRepository getInstance(VideogameDealDao dao, VideogameDetailNetworkDataSource nds) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            sInstance = new DealRepository(dao,nds);
            Log.d(LOG_TAG, "Made new repository");
        }
        return sInstance;
    }

    public void setVideogameDealID(final String videogameID){
        idVideogame=videogameID;
        userFilterLiveData.setValue(videogameID);
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (isFetchNeeded(videogameID)) {
                doFetchRepos(videogameID);
            }
        });
    }

    public void doFetchRepos(String videogameID){
        Log.d(LOG_TAG, "Fetching VideogameDeals from CheapShark");
        AppExecutors.getInstance().diskIO().execute(() -> {
            mVideogameDealDao.deleteVideogameDealByID(videogameID);
            mVideogameDetailNetworkDataSource.fetchRepos(videogameID);
            lastUpdateTimeMillisMap.put(videogameID, System.currentTimeMillis());
        });
    }

    /**
     * Database related operations
     **/

    public LiveData<List<VideogameDeal>> getCurrentVideogames() {
        return Transformations.switchMap(userFilterLiveData, mVideogameDealDao::getVideogameDealByID);
    }

    /**
     * Checks if we have to update the repos data.
     * @return Whether a fetch is needed
     */
    private boolean isFetchNeeded(String videogameID) {
        Long lastFetchTimeMillis = lastUpdateTimeMillisMap.get(videogameID);
        lastFetchTimeMillis = lastFetchTimeMillis == null ? 0L : lastFetchTimeMillis;
        long timeFromLastFetch = System.currentTimeMillis() - lastFetchTimeMillis;
        return timeFromLastFetch > MIN_TIME_FROM_LAST_FETCH_MILLIS || mVideogameDealDao.getNumberVideogameDealByID(videogameID) == 0;
    }
}
