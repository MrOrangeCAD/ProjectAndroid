package algonquin.cst2335.assignment;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ArticlesDAO {
    @Insert
    public long insertArticle(Articles favArticles);

    @Query("Select * from Articles")
    public List<Articles> getAllArticles();

    @Delete
    public void deleteArticle(Articles favArticles);
}
