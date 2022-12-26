package es.unex.cheapgamesv2.data.model;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "seguimiento", primaryKeys = {"videogameID", "usuarioID"})
public class ListaSeguimiento {

    @Ignore
    public final static String VIDEOGAME_ID = "videogameID";
    @Ignore
    public final static String USUARIO_ID = "usuarioID";


    @ColumnInfo(name = "videogameID")
    @NonNull
    private String videogameID = new String();

    @ColumnInfo(name = "usuarioID")
    @NonNull
    private String usuarioID = new String();

    public ListaSeguimiento(String videogameID, String usuarioID) {
        this.videogameID = videogameID;
        this.usuarioID = usuarioID;
    }

    public String getVideogameID() { return videogameID; }

    public void setVideogameID(String videogameId) { this.videogameID = videogameId; }

    public String getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(String usuarioId) { usuarioId = usuarioId;}

    @Ignore
    ListaSeguimiento(Intent intent) {
        videogameID = intent.getStringExtra(ListaSeguimiento.VIDEOGAME_ID);
        usuarioID = intent.getStringExtra(ListaSeguimiento.USUARIO_ID);
    }
}
