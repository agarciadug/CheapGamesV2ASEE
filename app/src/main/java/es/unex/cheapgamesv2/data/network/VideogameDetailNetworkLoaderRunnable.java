package es.unex.cheapgamesv2.data.network;

import java.io.IOException;
import java.util.List;

import es.unex.cheapgamesv2.AppExecutors;
import es.unex.cheapgamesv2.data.model.DetalleVideojuegoRespuesta;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideogameDetailNetworkLoaderRunnable implements Runnable{

    private final OnVideogameDetailLoadedListener mOnVideogameDetailLoadedListener;
    private final String mID;
    private DetalleVideojuegoRespuesta dVR;
    public VideogameDetailNetworkLoaderRunnable(String ID, OnVideogameDetailLoadedListener onVideogameDetailLoadedListener){
        mID=ID;
        mOnVideogameDetailLoadedListener = onVideogameDetailLoadedListener;
    }

    @Override
    public void run() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.cheapshark.com/api/1.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CheapSharkApi cheapSharkApi = retrofit.create(CheapSharkApi.class);
        dVR = new DetalleVideojuegoRespuesta();
        try {
            dVR = cheapSharkApi.getVideogamesById(mID).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AppExecutors.getInstance().mainThread().execute(() -> mOnVideogameDetailLoadedListener.onVideogameDetailLoaded(dVR));

    }
}
