package ruby.com.hijrahchallenge.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;

@Entity(tableName = "challenge")
public class Challenge {

    @PrimaryKey
    @NonNull
    @ColumnInfo
    private int id;

    private String name;

    private String date;

    private String caption;

    private String source;

    @ColumnInfo(name = "like_count")
    private int likeCount;



    public Challenge(@NonNull int id, String name, String date, String caption, String source, int likeCount) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.caption = caption;
        this.source = source;
        this.likeCount = likeCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

}
