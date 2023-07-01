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
import es.unex.cheapgamesv2.data.model.VideogameBest;
import es.unex.cheapgamesv2.data.network.VideogameBestNetworkDataSource;
import es.unex.cheapgamesv2.data.room.VideogameBestDao;

public class BestDealsRepository {

    private static final String LOG_TAG = BestDealsRepository.class.getSimpleName();

    // For Singleton instantiation
    private static BestDealsRepository sInstance;
    private final VideogameBestDao mVideogameBestDao;
    private final VideogameBestNetworkDataSource mVideogameBestNetworkDataSource;
    private final AppExecutors mExecutors = AppExecutors.getInstance();
    private final MutableLiveData<String> userFilterLiveData = new MutableLiveData<>();
    private final Map<String, Long> lastUpdateTimeMillisMap = new HashMap<>();
    private LiveData<List<VideogameBest>> mCurrentVideogames;
    private static final long MIN_TIME_FROM_LAST_FETCH_MILLIS = 30000;

    private BestDealsRepository(VideogameBestDao videogameBestDao, VideogameBestNetworkDataSource videogameBestNetworkDataSource) {
        mVideogameBestDao = videogameBestDao;
        mVideogameBestNetworkDataSource = videogameBestNetworkDataSource;

        // LiveData that fetches repos from network
        LiveData<VideogameBest[]> networkData = videogameBestNetworkDataSource.getCurrentBestVideogames();
        // As long as the repository exists, observe the network LiveData.
        // If that LiveData changes, update the database.
        networkData.observeForever(newVideogamesFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                // Deleting cached repos of user
                if (newVideogamesFromNetwork != null) {
                    mVideogameBestDao.deleteVideogamesBest();
                }
                // Insert our new repos into local database
                mVideogameBestDao.bulkInsert(Arrays.asList(newVideogamesFromNetwork));
                Log.d(LOG_TAG, "New values inserted in Room");
            });
        });
    }

    public synchronized static BestDealsRepository getInstance(VideogameBestDao dao, VideogameBestNetworkDataSource nds) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            sInstance = new BestDealsRepository(dao, nds);
            Log.d(LOG_TAG, "Made new repository");
        }
        return sInstance;
    }

    public void doFetchRepos() {
        Log.d(LOG_TAG, "Fetching VideogameDeals from CheapShark");
        AppExecutors.getInstance().diskIO().execute(() -> {
            mVideogameBestDao.deleteVideogamesBest();
            mVideogameBestNetworkDataSource.fetchRepos();
        });
    }

    /**
     * Database related operations
     **/

    public LiveData<List<VideogameBest>> getCurrentVideogames() {
        return mVideogameBestDao.getVideogamesBest();
    }

    /**
     * Checks if we have to update the repos data.
     *
     * @return Whether a fetch is needed
     */
    private boolean isFetchNeeded() {
        Long lastFetchTimeMillis = lastUpdateTimeMillisMap.get("key");
        lastFetchTimeMillis = lastFetchTimeMillis == null ? 0L : lastFetchTimeMillis;
        long timeFromLastFetch = System.currentTimeMillis() - lastFetchTimeMillis;
        return timeFromLastFetch > MIN_TIME_FROM_LAST_FETCH_MILLIS || mVideogameBestDao.getNumberVideogameBest() == 0;
    }
}
