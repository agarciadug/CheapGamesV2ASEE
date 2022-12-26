package es.unex.cheapgamesv2.data.network;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.unex.cheapgamesv2.AppExecutors;
import es.unex.cheapgamesv2.data.model.Videogame;
import es.unex.cheapgamesv2.data.model.VideojuegoGlobal;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideogameTitleSteamAppIDLoaderRunnable implements Runnable{
    private final OnVideogameTitleSteamAppIDLoadedListener mOnVideogameTitleSteamAppIDLoadedListener;
    private final String mTitle;
    private final String mSteamAppID;
    public VideogameTitleSteamAppIDLoaderRunnable(String title, String SteamAppID, OnVideogameTitleSteamAppIDLoadedListener onVideogameTitleSteamAppIDLoadedListener){
        mTitle = title;
        mSteamAppID=SteamAppID;
        mOnVideogameTitleSteamAppIDLoadedListener = onVideogameTitleSteamAppIDLoadedListener;
    }

    @Override
    public void run() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.cheapshark.com/api/1.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CheapSharkApi cheapSharkApi = retrofit.create(CheapSharkApi.class);
        List<Videogame> lVBR = null;
        try {
            lVBR= cheapSharkApi.getVideogameByTitleAndSteamAppID(mTitle,mSteamAppID).execute().body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Videogame> videojuego = lVBR;

        /* Pasar datos a variable global*/
        VideojuegoGlobal.setGameID(lVBR.get(0).getGameID());
        VideojuegoGlobal.setCheapest(lVBR.get(0).getCheapest());
        VideojuegoGlobal.setSteamAppID(lVBR.get(0).getSteamAppID());
        VideojuegoGlobal.setCheapestDealID(lVBR.get(0).getCheapestDealID());
        VideojuegoGlobal.setExternal(lVBR.get(0).getExternal());
        VideojuegoGlobal.setThumb(lVBR.get(0).getThumb());
        AppExecutors.getInstance().mainThread().execute(() -> mOnVideogameTitleSteamAppIDLoadedListener.OnVideogameTitleSteamAppIDLoaded(videojuego));

    }
}
