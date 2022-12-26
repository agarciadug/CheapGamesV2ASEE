package es.unex.cheapgamesv2.data.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import es.unex.cheapgamesv2.data.model.Usuario;

@Dao
public interface UsuarioDao {

    @Insert
    void registerUser(Usuario usuario);

    @Query("SELECT * FROM usuario WHERE email=(:email) and password=(:password)")
    Usuario login(String email, String password);

    @Update
    public int update(Usuario usuario);

    @Delete
    public int delete(Usuario usuario);


}
