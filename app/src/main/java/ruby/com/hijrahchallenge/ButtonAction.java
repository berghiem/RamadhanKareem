package ruby.com.hijrahchallenge;

import android.view.View;

import java.util.List;

import ruby.com.hijrahchallenge.model.ChallengeDetail;

public interface ButtonAction {
    public void likeAChallenge();

    public void shareAChallenge(View view);

    public void submitAnswer();

    public void likeAnswer();

    public void shareAnswer();

    public List<ChallengeDetail> getChallengeDetails();


}
