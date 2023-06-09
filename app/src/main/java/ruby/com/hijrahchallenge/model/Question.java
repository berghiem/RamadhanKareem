package ruby.com.hijrahchallenge.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.sql.Timestamp;

@Entity(tableName = "question")
public class Question {


    @PrimaryKey
    @NonNull
    private int id;

    private String text;

    public Question(@NonNull int id, String text) {
        this.id = id;
        this.text = text;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
