package es.unex.cheapgamesv2.data.room;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import es.unex.cheapgamesv2.data.model.Videogame;

@Dao
public interface VideogameDao {

    @Insert(onConflict = REPLACE)
    void bulkInsert(List<Videogame> repo);

    @Query("SELECT * FROM videogame  WHERE (external LIKE '%' || :title || '%' OR\n" +
            "            external LIKE :title || '%' OR\n" +
            "            external LIKE '%' || :title OR\n" +
            "            external LIKE :title)")
    LiveData<List<Videogame>> getVideogamesByTitle(String title);

    @Query("SELECT count(*) FROM videogame WHERE (external LIKE '%' || :title || '%' OR\n" +
            "            external LIKE :title || '%' OR\n" +
            "            external LIKE '%' || :title OR\n" +
            "            external LIKE :title)")
    int getNumberVideogamesByTitle(String title);

    @Query("DELETE FROM videogame WHERE (external LIKE '%' || :title || '%' OR\n" +
            "            external LIKE :title || '%' OR\n" +
            "            external LIKE '%' || :title OR\n" +
            "            external LIKE :title)")
    int deleteVideogamesByTitle(String title);
}
