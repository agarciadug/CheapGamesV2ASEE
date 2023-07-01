package es.unex.cheapgamesv2.data.network;

import java.util.List;

import es.unex.cheapgamesv2.data.model.Deal;
import es.unex.cheapgamesv2.data.model.Videogame;
import es.unex.cheapgamesv2.data.model.VideogameBest;
import es.unex.cheapgamesv2.data.model.VideogameDeal;

public interface OnVideogamesBestLoadedListener {
    void onVideogamesBestLoaded(List<VideogameBest> listaDeals);
}
