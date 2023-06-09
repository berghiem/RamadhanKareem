package ruby.com.hijrahchallenge.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ruby.com.hijrahchallenge.model.Question;
import ruby.com.hijrahchallenge.model.User;

@Dao
public interface QuestionDao {

    @Insert
    void insert(Question question);

    @Query("DELETE FROM question")
    void deleteAll();

    @Query("SELECT * from question  ")
    LiveData<List<Question>> getAllQuestion();
}
