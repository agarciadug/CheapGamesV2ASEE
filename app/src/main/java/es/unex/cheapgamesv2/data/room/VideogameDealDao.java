package es.unex.cheapgamesv2.data.room;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import es.unex.cheapgamesv2.data.model.Videogame;
import es.unex.cheapgamesv2.data.model.VideogameDeal;

@Dao
public interface VideogameDealDao {

    @Insert(onConflict = REPLACE)
    void bulkInsert(List<VideogameDeal> videogameDeals);

    @Query("SELECT * FROM videogameDeal WHERE gameID=(:gameID)")
    LiveData<List<VideogameDeal>> getVideogameDealByID(String gameID);

    @Query("SELECT count(*) FROM videogameDeal WHERE gameID=(:gameID)")
    int getNumberVideogameDealByID(String gameID);

    @Query("DELETE FROM videogameDeal WHERE gameID=(:gameID)")
    int deleteVideogameDealByID(String gameID);
}
