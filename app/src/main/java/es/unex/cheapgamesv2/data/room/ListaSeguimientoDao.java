package es.unex.cheapgamesv2.data.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import es.unex.cheapgamesv2.data.model.ListaSeguimiento;

@Dao
public interface ListaSeguimientoDao {

    @Insert
    void insertarSeguimiento(ListaSeguimiento seguimiento);

    @Query("SELECT * FROM seguimiento WHERE usuarioID=(:usuarioID)")
    List<ListaSeguimiento> obtenerSeguimientos(String usuarioID);

    @Query ("SELECT * FROM seguimiento WHERE videogameID=(:videogameID) and usuarioID=(:usuarioID)")
    List<ListaSeguimiento> obtenerSeguido(String videogameID, String usuarioID);

    @Query ("DELETE FROM seguimiento WHERE videogameID=(:videogameID) and usuarioID=(:usuarioID)")
    public int borrarSeguimiento(String videogameID, String usuarioID);
}
