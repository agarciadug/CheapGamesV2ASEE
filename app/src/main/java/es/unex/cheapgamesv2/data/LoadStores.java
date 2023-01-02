package es.unex.cheapgamesv2.data;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import es.unex.cheapgamesv2.AppExecutors;
import es.unex.cheapgamesv2.data.model.Store;
import es.unex.cheapgamesv2.data.model.Tienda;
import es.unex.cheapgamesv2.data.network.CheapSharkApi;
import es.unex.cheapgamesv2.data.room.CheapGamesDB;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoadStores {

    private final Context context;

    public LoadStores(Context context){
        this.context = context;
    }

    public void Stores() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.cheapshark.com/api/1.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CheapGamesDB cheapGamesDB = CheapGamesDB.getInstance(context);
        CheapSharkApi service = retrofit.create(CheapSharkApi.class);
        Call<ArrayList<Store>> respuesta = service.getAllStores();
        respuesta.enqueue(new Callback<ArrayList<Store>>() {
            @Override
            public void onResponse(Call<ArrayList<Store>> call, Response<ArrayList<Store>> response) {
                if (response.isSuccessful()) {

                    ArrayList<Store> respuestaStore = response.body();

                    for (Store s : respuestaStore) {
                        Log.v("Tiendas", s.getStoreName());
                        Tienda t = new Tienda(s.getStoreID(),s.getStoreName());
                        AppExecutors.getInstance().diskIO().execute(() -> {
                            if(cheapGamesDB.tiendaDao().getTiendaByID(t.getStoreID())==null){
                                cheapGamesDB.tiendaDao().insertarTienda(t);
                            }
                        });
                    }

                }
                else{
                    Log.v("Tiendas", "No cargo tiendas");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Store>> call, Throwable t) {

            }
        });
    }
}
