package es.unex.cheapgamesv2.data.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.unex.cheapgamesv2.data.model.ListaSeguimiento;
import es.unex.cheapgamesv2.data.model.Tienda;
import es.unex.cheapgamesv2.data.model.Usuario;
import es.unex.cheapgamesv2.data.model.Videogame;

@Database(entities = {Usuario.class, Tienda.class, ListaSeguimiento.class, Videogame.class}, version = 1)
public abstract class CheapGamesDB extends RoomDatabase {

    private static CheapGamesDB INSTANCE;

    public synchronized static CheapGamesDB getInstance(Context context) {
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, CheapGamesDB.class, "CheapGames.db").build();
        }
        return INSTANCE;
    }

    /*public abstract UsuarioDao usuarioDao();
    public abstract TiendaDao tiendaDao();
    public abstract ListaSeguimientoDao listaSeguimientoDao();*/
    public abstract VideogameDao videogameDao();
}
