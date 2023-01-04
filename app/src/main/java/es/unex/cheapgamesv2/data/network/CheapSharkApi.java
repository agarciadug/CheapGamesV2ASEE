package es.unex.cheapgamesv2.data.network;

import java.util.ArrayList;
import java.util.List;

import es.unex.cheapgamesv2.data.model.DetalleVideojuegoRespuesta;
import es.unex.cheapgamesv2.data.model.Store;
import es.unex.cheapgamesv2.data.model.Videogame;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CheapSharkApi {

    @GET("games?title=")
    Call<List<Videogame>> getVideogamesByTitle(@Query("title") String title);

    @GET("games?id=")
    Call<DetalleVideojuegoRespuesta> getVideogamesById(@Query("id") String id);

    @GET("games")
    Call<List<Videogame>> getVideogameExact(@Query("title") String title, @Query("exact") String exact);

    @GET("stores")
    Call<ArrayList<Store>> getAllStores();
}
