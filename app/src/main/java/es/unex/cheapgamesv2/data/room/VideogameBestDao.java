package es.unex.cheapgamesv2.data.room;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import es.unex.cheapgamesv2.data.model.VideogameBest;

@Dao
public interface VideogameBestDao {

    @Insert(onConflict = REPLACE)
    void bulkInsert(List<VideogameBest> videogameBestList);

    @Query("SELECT * FROM videogameBest")
    LiveData<List<VideogameBest>> getVideogamesBest();

    @Query("SELECT count(*) FROM videogameDeal")
    int getNumberVideogameBest();

    @Query("DELETE FROM videogameBest")
    int deleteVideogamesBest();
}
