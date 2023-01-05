package es.unex.cheapgamesv2.data.room;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import es.unex.cheapgamesv2.data.model.Tienda;
import es.unex.cheapgamesv2.data.model.Videogame;

@Dao
public interface TiendaDao {

    @Insert
    void insertarTienda(Tienda tienda);

    @Query("SELECT * FROM tienda WHERE storeID=(:storeID)")
    Tienda getTiendaByID(String storeID);

    @Query("SELECT * FROM tienda")
    List<Tienda> getAllTienda();

    @Query("DELETE FROM tienda WHERE storeID=(:storeID)")
    int deleteTiendaByID(String storeID);
}
