package algonquin.cst2335.assignment;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Articles {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="ID")
    public long id;

    @ColumnInfo(name="headlineArticle")
    private String headlineArticle;

    @ColumnInfo(name="abstractArticle")
    private String abstractArticle;

    @ColumnInfo(name="urlArticle")
    private String urlArticle;

    @ColumnInfo(name="pub_date")
    private String pubDate;
    public Articles(String headlineArticle, String abstractArticle, String urlArticle, String pubDate){
        this.headlineArticle = headlineArticle;
        this.abstractArticle = abstractArticle;
        this.urlArticle = urlArticle;
        this.pubDate = pubDate;
    }

    public String getHeadlineArticle(){
        return headlineArticle;
    }
    /*public void setHeadlineArticle(String headlineArticle){
        this.headlineArticle = headlineArticle;
    }*/

    public String getAbstractArticle(){
        return abstractArticle;
    }
    /*public void setAbstractArticle(String abstractArticle){
        this.abstractArticle = abstractArticle;
    }*/

    public String getUrlArticle(){return urlArticle;}
    /*public void setUrlArticle(String urlArticle){
        this.urlArticle = urlArticle;
    }*/

    public String getPubDate(){return pubDate;}
/*    public void setPubDate(String pubDate){
        this.pubDate = pubDate;
    }*/
}
