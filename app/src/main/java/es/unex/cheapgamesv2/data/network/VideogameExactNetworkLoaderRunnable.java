package es.unex.cheapgamesv2.data.network;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import es.unex.cheapgamesv2.AppExecutors;
import es.unex.cheapgamesv2.data.model.Videogame;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideogameExactNetworkLoaderRunnable implements Runnable{

    private final OnVideogamesLoadedListener mOnVideogamesLoadedListener;
    private final String mTitle;
    public VideogameExactNetworkLoaderRunnable(String title, OnVideogamesLoadedListener onVideogamesLoadedListener){
        mTitle=title;
        mOnVideogamesLoadedListener = onVideogamesLoadedListener;
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
            lVBR = cheapSharkApi.getVideogameExact(mTitle,"1").execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i=0;i<lVBR.size();i++){
            Log.v("Juego recuperado", lVBR.get(i).getExternal());
        }
        List<Videogame> finalLVBR = lVBR;
        AppExecutors.getInstance().mainThread().execute(() -> mOnVideogamesLoadedListener.onVideogamesLoaded(finalLVBR));
    }
}
