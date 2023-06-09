package ruby.com.hijrahchallenge.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ruby.com.hijrahchallenge.model.Challenge;

@Dao
public interface  ChallengeDao {

    @Insert
    void insert(Challenge challenge);

    @Query("DELETE FROM challenge")
    void deleteAll();

    @Query("SELECT * from challenge  ")
    LiveData<List<Challenge>> getChallenge();

    @Query("UPDATE challenge SET like_count =:count WHERE id IN (:challengeId)")
    void updateChallengeLike(int challengeId, int count);

}

