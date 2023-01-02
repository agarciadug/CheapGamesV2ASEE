package es.unex.cheapgamesv2.data.network;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import es.unex.cheapgamesv2.AppExecutors;
import es.unex.cheapgamesv2.data.model.DetalleVideojuegoRespuesta;
import es.unex.cheapgamesv2.data.model.Videogame;
import es.unex.cheapgamesv2.data.model.VideogameDeal;

public class VideogameDetailNetworkDataSource {
    private static final String LOG_TAG = VideogameDetailNetworkDataSource.class.getSimpleName();
    private static VideogameDetailNetworkDataSource sInstance;

    // LiveData storing the latest downloaded weather forecasts
    private final MutableLiveData<VideogameDeal[]> mDownloadedVideogameDetails;

    private VideogameDetailNetworkDataSource() {
        mDownloadedVideogameDetails = new MutableLiveData<>();
    }

    public synchronized static VideogameDetailNetworkDataSource getInstance() {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            sInstance = new VideogameDetailNetworkDataSource();
            Log.d(LOG_TAG, "Made new network data source");
        }
        return sInstance;
    }

    public LiveData<VideogameDeal[]> getCurrentRepos() {
        return mDownloadedVideogameDetails;
    }

    /**
     * Gets the newest repos
     */
    public void fetchRepos(String idVideogame) {
        Log.d(LOG_TAG, "Fetch repos started");
        // Get gata from network and pass it to LiveData
        AppExecutors.getInstance().networkIO().execute(new VideogameDetailNetworkLoaderRunnable(idVideogame, videogameDetail -> mDownloadedVideogameDetails.postValue(videogameDetail.toArray(new VideogameDeal[0]))));
    }
}
