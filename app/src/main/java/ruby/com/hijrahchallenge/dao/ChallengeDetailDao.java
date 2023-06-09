package ruby.com.hijrahchallenge.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ruby.com.hijrahchallenge.model.ChallengeDetail;
import ruby.com.hijrahchallenge.model.response.Answer;

@Dao
public interface ChallengeDetailDao {

    @Insert
    void insert(ChallengeDetail challengeDetail);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<ChallengeDetail> challengeDetails);

    @Query("DELETE FROM challenge_detail")
    void deleteAll();

    @Query("SELECT * from challenge_detail WHERE challengeId=:challengeId")
    LiveData<List<ChallengeDetail>> getChallengeById(final int challengeId);

    @Query("SELECT * from challenge_detail")
    LiveData<List<ChallengeDetail>> getChallengeDetail();

    @Query("UPDATE challenge_detail SET participantCount = participantCount +1 WHERE id IN (:id)")
    void updateCDetailParticipant(int id);



}
