package es.unex.cheapgamesv2.data.network;

import java.io.IOException;
import java.util.List;

import es.unex.cheapgamesv2.AppExecutors;
import es.unex.cheapgamesv2.data.model.Videogame;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideogamesNetworkLoaderRunnable implements Runnable{

    private final OnVideogamesLoadedListener mOnVideogamesLoadedListener;
    private final String mTitle;
    public VideogamesNetworkLoaderRunnable(String title, OnVideogamesLoadedListener onVideogamesLoadedListener){
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
            lVBR = cheapSharkApi.getVideogamesByTitle(mTitle).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Videogame> finalLVBR = lVBR;
        AppExecutors.getInstance().mainThread().execute(() -> mOnVideogamesLoadedListener.onVideogamesLoaded(finalLVBR));
    }
}
