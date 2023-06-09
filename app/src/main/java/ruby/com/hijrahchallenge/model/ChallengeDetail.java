package ruby.com.hijrahchallenge.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "challenge_detail", foreignKeys = @ForeignKey(entity = Challenge.class,
        parentColumns = "id",
        childColumns = "challengeId",
        onDelete = CASCADE))
public class ChallengeDetail {
    @PrimaryKey
    @NonNull
    private int id;

    private int level;

    private String text;

    private int challengeId;

    private int participantCount;

    public ChallengeDetail(@NonNull int id, int level, String text, int challengeId, int participantCount) {
        this.id = id;
        this.level = level;
        this.text = text;
        this.challengeId = challengeId;
        this.participantCount = participantCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(int challengeId) {
        this.challengeId = challengeId;
    }


    public int getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(int participantCount) {
        this.participantCount = participantCount;
    }

}
