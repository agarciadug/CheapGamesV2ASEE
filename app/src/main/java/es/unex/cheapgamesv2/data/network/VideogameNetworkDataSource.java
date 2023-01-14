package es.unex.cheapgamesv2.data.network;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import es.unex.cheapgamesv2.AppExecutors;
import es.unex.cheapgamesv2.data.model.Videogame;

public class VideogameNetworkDataSource {
    private static final String LOG_TAG = VideogameNetworkDataSource.class.getSimpleName();
    private static VideogameNetworkDataSource sInstance;

    // LiveData storing the latest downloaded weather forecasts
    private final MutableLiveData<Videogame[]> mDownloadedVideogames;

    private VideogameNetworkDataSource() {
        mDownloadedVideogames = new MutableLiveData<>();
    }

    public synchronized static VideogameNetworkDataSource getInstance() {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            sInstance = new VideogameNetworkDataSource();
            Log.d(LOG_TAG, "Made new network data source");
        }
        return sInstance;
    }

    public LiveData<Videogame[]> getCurrentRepos() {
        return mDownloadedVideogames;
    }

    /**
     * Gets the newest repos
     */
    public void fetchRepos(String titleVideogame) {
        Log.d(LOG_TAG, "Fetch repos started");
        // Get gata from network and pass it to LiveData
        AppExecutors.getInstance().networkIO().execute(new VideogamesNetworkLoaderRunnable(titleVideogame, videogames -> mDownloadedVideogames.postValue(videogames.toArray(new Videogame[0]))));
    }
}
