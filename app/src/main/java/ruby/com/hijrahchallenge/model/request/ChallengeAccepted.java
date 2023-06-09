package ruby.com.hijrahchallenge.model.request;

public class ChallengeAccepted {
    private int userid;
    private int challengeId;
    private int challengeDetailId;
    private String token;

    public ChallengeAccepted(int userid, int challengeId, int challengeDetailId, String token) {
        this.userid = userid;
        this.challengeId = challengeId;
        this.challengeDetailId = challengeDetailId;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(int challengeId) {
        this.challengeId = challengeId;
    }

    public int getChallengeDetailId() {
        return challengeDetailId;
    }

    public void setChallengeDetailId(int challengeDetailId) {
        this.challengeDetailId = challengeDetailId;
    }
}
