package es.unex.cheapgamesv2.data.network;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.unex.cheapgamesv2.AppExecutors;
import es.unex.cheapgamesv2.data.model.DetalleVideojuegoRespuesta;
import es.unex.cheapgamesv2.data.model.Videogame;
import es.unex.cheapgamesv2.data.model.VideogameDeal;
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
        Log.v("Juego recupera", dVR.getInfo().getTitle());
        ArrayList<VideogameDeal> videogameDealArrayList = new ArrayList<>();
        VideogameDeal vdAux;
        for(int i=0; i<dVR.getDeals().size();i++){
            vdAux = new VideogameDeal();
            vdAux.setGameID(mID);
            vdAux.setDealID(dVR.getDeals().get(i).getDealID());
            vdAux.setPrice(dVR.getDeals().get(i).getPrice());
            vdAux.setSavings(dVR.getDeals().get(i).getSavings());
            vdAux.setRetailPrice(dVR.getDeals().get(i).getRetailPrice());
            vdAux.setStoreID(dVR.getDeals().get(i).getStoreID());
            Log.v("Juego recupera", vdAux.getGameID() + " " + vdAux.getPrice());
            videogameDealArrayList.add(vdAux);
        }
        AppExecutors.getInstance().mainThread().execute(() -> mOnVideogameDetailLoadedListener.onVideogameDetailLoaded(videogameDealArrayList));

    }
}
