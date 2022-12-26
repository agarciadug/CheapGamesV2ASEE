package es.unex.cheapgamesv2.data.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import es.unex.cheapgamesv2.data.model.Tienda;

@Dao
public interface TiendaDao {
    @Insert
    void insertarTienda(Tienda tienda);

    @Query("SELECT * FROM tienda WHERE storeID=(:storeID)")
    Tienda getTiendaByID(String storeID);
}
