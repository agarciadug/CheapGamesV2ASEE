package es.unex.cheapgamesv2.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.unex.cheapgamesv2.AppExecutors;
import es.unex.cheapgamesv2.data.model.Videogame;
import es.unex.cheapgamesv2.data.network.VideogameNetworkDataSource;
import es.unex.cheapgamesv2.data.room.VideogameDao;

public class VideogameRepository {

    private static final String LOG_TAG = VideogameRepository.class.getSimpleName();

    // For Singleton instantiation
    private static VideogameRepository sInstance;
    private final VideogameDao mVideogameDao;
    private final VideogameNetworkDataSource mVideogameNetworkDataSource;
    private final AppExecutors mExecutors = AppExecutors.getInstance();
    private final MutableLiveData<String> userFilterLiveData = new MutableLiveData<>();
    private final Map<String, Long> lastUpdateTimeMillisMap = new HashMap<>();
    private static final long MIN_TIME_FROM_LAST_FETCH_MILLIS = 30000;

    private VideogameRepository(VideogameDao videogameDao, VideogameNetworkDataSource repoNetworkDataSource) {

        mVideogameDao=videogameDao;
        mVideogameNetworkDataSource = repoNetworkDataSource;
        // LiveData that fetches repos from network
        LiveData<Videogame[]> networkData = mVideogameNetworkDataSource.getCurrentRepos();
        // As long as the repository exists, observe the network LiveData.
        // If that LiveData changes, update the database.
        networkData.observeForever(newVideogamesFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                // Deleting cached repos of user
                if (newVideogamesFromNetwork.length > 0){
                    mVideogameDao.deleteVideogamesByTitle(newVideogamesFromNetwork[0].getExternal());
                }
                // Insert our new repos into local database
                mVideogameDao.bulkInsert(Arrays.asList(newVideogamesFromNetwork));
                Log.d(LOG_TAG, "New values inserted in Room");
            });
        });
    }

    public synchronized static VideogameRepository getInstance(VideogameDao dao, VideogameNetworkDataSource nds) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            sInstance = new VideogameRepository(dao, nds);
            Log.d(LOG_TAG, "Made new repository");
        }
        return sInstance;
    }

    public void setTitleVideogame(final String titleVideogame){
        userFilterLiveData.setValue(titleVideogame);
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (isFetchNeeded(titleVideogame)) {
                doFetchRepos(titleVideogame);
            }
        });
    }

    public void doFetchRepos(String titleVideogame){
        Log.d(LOG_TAG, "Fetching Videogames from CheapShark");
        AppExecutors.getInstance().diskIO().execute(() -> {
            mVideogameDao.deleteVideogamesByTitle(titleVideogame);
            mVideogameNetworkDataSource.fetchRepos(titleVideogame);
            lastUpdateTimeMillisMap.put(titleVideogame, System.currentTimeMillis());
        });
    }

    /**
     * Database related operations
     **/

    public LiveData<List<Videogame>> getCurrentVideogames() {
        return Transformations.switchMap(userFilterLiveData, mVideogameDao::getVideogamesByTitle);
    }

    /**
     * Checks if we have to update the repos data.
     * @return Whether a fetch is needed
     */
    private boolean isFetchNeeded(String title) {
        Long lastFetchTimeMillis = lastUpdateTimeMillisMap.get(title);
        lastFetchTimeMillis = lastFetchTimeMillis == null ? 0L : lastFetchTimeMillis;
        long timeFromLastFetch = System.currentTimeMillis() - lastFetchTimeMillis;
        return timeFromLastFetch > MIN_TIME_FROM_LAST_FETCH_MILLIS || mVideogameDao.getNumberVideogamesByTitle(title) == 0;
    }
}
