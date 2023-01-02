package es.unex.cheapgamesv2.data.network;

import java.util.List;

import es.unex.cheapgamesv2.data.model.DetalleVideojuegoRespuesta;
import es.unex.cheapgamesv2.data.model.VideogameDeal;

public interface OnVideogameDetailLoadedListener {
    public void onVideogameDetailLoaded(List<VideogameDeal> dVR);
}
