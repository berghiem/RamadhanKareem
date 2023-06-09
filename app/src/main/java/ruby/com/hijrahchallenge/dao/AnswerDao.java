package ruby.com.hijrahchallenge.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ruby.com.hijrahchallenge.model.Challenge;
import ruby.com.hijrahchallenge.model.response.Answer;

@Dao
public interface AnswerDao {

    @Insert
    void insert(Answer answer);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Answer> answers);

    @Query("DELETE FROM answer")
    void deleteAll();

    @Query("SELECT * from answer  ")
    LiveData<List<Answer>> getAnswer();

    @Query("SELECT * from answer order by date desc ")
    LiveData<List<Answer>> getByLatestAnswer();

    @Query("SELECT * from answer order by date asc ")
    LiveData<List<Answer>> getByOldestAnswer();

    @Query("SELECT * from answer order by like_count desc ")
    LiveData<List<Answer>> getByPopularAnswer();



    @Query("UPDATE answer SET like_count =:count WHERE id IN (:answerid)")
    void updateAnswerLike(int answerid, int count);
}
