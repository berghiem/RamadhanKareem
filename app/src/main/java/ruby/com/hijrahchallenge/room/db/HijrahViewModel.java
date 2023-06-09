package ruby.com.hijrahchallenge.room.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ruby.com.hijrahchallenge.model.Challenge;
import ruby.com.hijrahchallenge.model.ChallengeDetail;
import ruby.com.hijrahchallenge.model.Question;
import ruby.com.hijrahchallenge.model.request.AnswerOfQuestion;
import ruby.com.hijrahchallenge.model.request.ChallengeAccepted;
import ruby.com.hijrahchallenge.model.response.Answer;
import ruby.com.hijrahchallenge.model.response.ChallengeResponse;
import ruby.com.hijrahchallenge.model.response.QuizAndQuestion;

public class HijrahViewModel extends AndroidViewModel {


    private HijrahRepository mRepository;

    private LiveData<List<Challenge>> challenge;

    private LiveData<List<ChallengeDetail>> challengeDetail;

    private LiveData<List<Question>> question;

    private LiveData<List<Answer>> answers;

    public HijrahViewModel(Application application) {
        super(application);
        mRepository = new HijrahRepository(application);
        challenge = mRepository.getChallenge();
        challengeDetail = mRepository.getChallengeDetail();
        question = mRepository.getQuestion();
        answers = mRepository.getAnswers();
    }

    public LiveData<List<Challenge>> getChallenge() {
        return challenge;
    }

    public LiveData<List<ChallengeDetail>> getChallengeDetail(){
        return challengeDetail;
    }

    public LiveData<List<Question>> getQuestion() {
        return question;
    }

    public LiveData<List<Answer>> getAnswers() {
        return answers;
    }

    public void loadChallenge(String token){
        mRepository.loadChallenge(token);

    }


    public void loadQuestion(String token) {
        mRepository.loadQuestion(token);

    }

    public void loadAnswer(String token, int idq) {
        mRepository.loadAnswer(token, idq);
    }

    public void loadChallengeDetail(String token) {
        mRepository.loadChallengeDetail(token);
    }

    public void likeAChallenge(String token, int userId, int challengeId) {
        mRepository.likeAChallenge(token, userId, challengeId);
    }


    public void acceptChallenge(ChallengeAccepted challengeAccepted) {
        mRepository.acceptChallenge(challengeAccepted);
    }

    public void likeAnswer(String token, int userId, int answerId) {
        mRepository.likeAnswer( token,  userId, answerId);
    }

    public void submitAnswer(String token, AnswerOfQuestion answer){
        mRepository.submitAnswer(token, answer);
    }

    public void findAnswerByUsername(String token, String username, int idq){
        mRepository.findAnswerByUsername(token, username, idq);
    }

    public void findAnswerByKeyword(String token, String keyword, int idq){
        mRepository.findAnswerByKeyword(token, keyword,idq);
    }

}
