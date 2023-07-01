package es.unex.cheapgamesv2.data.network;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import es.unex.cheapgamesv2.AppExecutors;
import es.unex.cheapgamesv2.data.model.VideogameBest;
import es.unex.cheapgamesv2.data.model.VideogameDeal;

public class VideogameBestNetworkDataSource {
    private static final String LOG_TAG = VideogameBestNetworkDataSource.class.getSimpleName();
    private static VideogameBestNetworkDataSource sInstance;

    // LiveData storing the latest downloaded weather forecasts
    private final MutableLiveData<VideogameBest[]> mDownloadedVideogameBest;

    private VideogameBestNetworkDataSource() {
        mDownloadedVideogameBest = new MutableLiveData<>();
    }

    public synchronized static VideogameBestNetworkDataSource getInstance() {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            sInstance = new VideogameBestNetworkDataSource();
            Log.d(LOG_TAG, "Made new network data source");
        }
        return sInstance;
    }

    public LiveData<VideogameBest[]> getCurrentBestVideogames() {
        return mDownloadedVideogameBest;
    }

    /**
     * Gets the newest repos
     */
    public void fetchRepos() {
        Log.d(LOG_TAG, "Fetch videogames best started");
        // Get gata from network and pass it to LiveData
        AppExecutors.getInstance().networkIO().execute(new VideogamesBestNetworkLoaderRunnable(videogameBestList -> mDownloadedVideogameBest.postValue(videogameBestList.toArray(new VideogameBest[0]))));
    }
}
