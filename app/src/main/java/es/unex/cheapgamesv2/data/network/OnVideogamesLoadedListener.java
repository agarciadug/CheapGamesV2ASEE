package es.unex.cheapgamesv2.data.network;

import java.util.List;

import es.unex.cheapgamesv2.data.model.Videogame;

public interface OnVideogamesLoadedListener {
    public void onVideogamesLoaded(List<Videogame> lVBR);
}
