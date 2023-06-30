package es.unex.cheapgamesv2.data.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.unex.cheapgamesv2.data.model.ListaSeguimiento;
import es.unex.cheapgamesv2.data.model.Usuario;
import es.unex.cheapgamesv2.data.model.Videogame;
import es.unex.cheapgamesv2.data.model.VideogameDeal;

@Database(entities = {Usuario.class, ListaSeguimiento.class, Videogame.class, VideogameDeal.class}, version = 10)
public abstract class CheapGamesDB extends RoomDatabase {

    private static CheapGamesDB INSTANCE;

    public synchronized static CheapGamesDB getInstance(Context context) {
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, CheapGamesDB.class, "CheapGames.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }


    public abstract ListaSeguimientoDao listaSeguimientoDao();
    public abstract UsuarioDao usuarioDao();
    public abstract VideogameDao videogameDao();
    public abstract VideogameDealDao videogameDealDao();
}
