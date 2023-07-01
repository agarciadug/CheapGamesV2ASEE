package es.unex.cheapgamesv2.data.network;

import java.io.IOException;
import java.util.List;

import es.unex.cheapgamesv2.AppExecutors;
import es.unex.cheapgamesv2.data.model.Deal;
import es.unex.cheapgamesv2.data.model.Videogame;
import es.unex.cheapgamesv2.data.model.VideogameBest;
import es.unex.cheapgamesv2.data.model.VideogameDeal;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideogamesBestNetworkLoaderRunnable implements Runnable{

    private final OnVideogamesBestLoadedListener mOnVideogamesBestLoadedListener;
    public VideogamesBestNetworkLoaderRunnable(OnVideogamesBestLoadedListener onVideogamesBestLoadedListener){
        mOnVideogamesBestLoadedListener = onVideogamesBestLoadedListener;
    }

    @Override
    public void run() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.cheapshark.com/api/1.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CheapSharkApi cheapSharkApi = retrofit.create(CheapSharkApi.class);
        try {
            List<VideogameBest> listaVideogamesBest = cheapSharkApi.getVideogamesBest().execute().body();
            AppExecutors.getInstance().mainThread().execute(() -> mOnVideogamesBestLoadedListener.onVideogamesBestLoaded(listaVideogamesBest));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
