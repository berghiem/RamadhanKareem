package ruby.com.hijrahchallenge.model;

public class ChallengeAccepted {

    private User user;
    private Challenge challenge;
    private int countEasy;
    private int countMedium;
    private int countHard;



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public int getCountEasy() {
        return countEasy;
    }

    public void setCountEasy(int countEasy) {
        this.countEasy = countEasy;
    }

    public int getCountMedium() {
        return countMedium;
    }

    public void setCountMedium(int countMedium) {
        this.countMedium = countMedium;
    }

    public int getCountHard() {
        return countHard;
    }

    public void setCountHard(int countHard) {
        this.countHard = countHard;
    }
}
