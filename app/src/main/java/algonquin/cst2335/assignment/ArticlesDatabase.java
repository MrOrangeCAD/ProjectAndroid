package algonquin.cst2335.assignment;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Articles.class}, version=1)
public abstract class ArticlesDatabase extends RoomDatabase {

    public abstract ArticlesDAO articlesDAO();

}
