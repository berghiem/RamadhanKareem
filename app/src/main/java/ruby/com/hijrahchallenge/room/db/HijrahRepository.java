package ruby.com.hijrahchallenge.room.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ruby.com.hijrahchallenge.api.call.APIInterface;
import ruby.com.hijrahchallenge.api.call.RetrofitClient;
import ruby.com.hijrahchallenge.dao.AnswerDao;
import ruby.com.hijrahchallenge.dao.ChallengeDao;
import ruby.com.hijrahchallenge.dao.ChallengeDetailDao;
import ruby.com.hijrahchallenge.dao.QuestionDao;
import ruby.com.hijrahchallenge.dao.UserDao;
import ruby.com.hijrahchallenge.model.Challenge;
import ruby.com.hijrahchallenge.model.ChallengeDetail;
import ruby.com.hijrahchallenge.model.Question;
import ruby.com.hijrahchallenge.model.User;
import ruby.com.hijrahchallenge.model.request.AnswerOfQuestion;
import ruby.com.hijrahchallenge.model.request.ChallengeAccepted;
import ruby.com.hijrahchallenge.model.response.Answer;
import ruby.com.hijrahchallenge.model.response.ChallengeResponse;
import ruby.com.hijrahchallenge.model.response.QuizAndQuestion;

public class HijrahRepository {

    private UserDao userDao;
    private ChallengeDao challengeDao;
    private ChallengeDetailDao cdetailDao;
    private QuestionDao questionDao;
    private AnswerDao answerDao;

    private LiveData<List<User>> users;
    private LiveData<List<Challenge>> challenges;
    private LiveData<List<ChallengeDetail>> challengeDetails;
    private LiveData<List<Question>> questions;
    private LiveData<List<Answer>> answers;


    HijrahRepository(Application application) {
        HijrahRoomDatabase db = HijrahRoomDatabase.getDatabase(application);
        userDao = db.userDao();
        users = userDao.getAllUser();

        challengeDao = db.challengeDao();
        challenges = challengeDao.getChallenge();

        cdetailDao = db.challengeDetailDao();
        challengeDetails = cdetailDao.getChallengeDetail();

        questionDao = db.questionDao();
        questions = questionDao.getAllQuestion();

        answerDao = db.answerDao();
        answers = answerDao.getAnswer();
    }


    LiveData<List<User>> getAllUser() {
        return users;
    }


    public void insertUser(User user) {
        new insertUserAsyncTask(userDao).execute(user);
    }

    LiveData<List<Challenge>> getChallenge() {
        return challenges;
    }

    LiveData<List<ChallengeDetail>> getChallengeDetail() {
        return challengeDetails;
    }

    LiveData<List<Question>> getQuestion() {
        return questions;
    }

    LiveData<List<Answer>> getAnswers() {
        return answers;
    }


    public void loadChallenge(String token) {
        new loadChallengeAsyncTask(challengeDao, cdetailDao, token).execute();
    }

    public void loadQuestion(String token) {
        new loadQuestionAsyncTask(questionDao, token).execute();
    }

    public void loadAnswer(String token,int idq) {
        new loadAnswerAsyncTask(answerDao, token, idq).execute();
    }


    public void loadChallengeDetail(String token) {
        new loadChallengeDetailAsyncTask(cdetailDao, token).execute();
    }

    public void likeAChallenge(String token,int userId, int challengeId){
        new likeAChallengeAsyncTask(challengeDao, userId, token, challengeId).execute();
    }

    public void acceptChallenge(ChallengeAccepted challengeAccepted) {
        new acceptClAsysnctask(cdetailDao, challengeAccepted).execute();
    }

    public void likeAnswer(String token, int userId, int answerId) {
        new likeAnswerAsyncTask(answerDao, token, userId, answerId).execute();
    }

    public void  submitAnswer(String token, AnswerOfQuestion answer){
        new submitAnswerAsyncTask(token,answer).execute();
    }

    public void findAnswerByUsername(String token, String username, int idq) {
        new findAnswerByUsernameAsynTask(token, username, answerDao, idq).execute();
    }

    public void findAnswerByKeyword(String token, String keyword, int idq) {
        new findAnswerByKeywordAsynTask(token, keyword, answerDao, idq).execute();
    }


    private static class insertUserAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertUserAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


    private static class loadChallengeAsyncTask extends AsyncTask<Void, Void, Void> {

        private ChallengeDao challengeDao;
        private ChallengeDetailDao challengeDetailDao;
        private String token;


        loadChallengeAsyncTask(ChallengeDao challengeDao, ChallengeDetailDao challengeDetailDao, String token) {
            this.challengeDao = challengeDao;
            this.challengeDetailDao = challengeDetailDao;
            this.token = token;
        }

        @Override
        protected Void doInBackground(Void... v) {

            APIInterface service = RetrofitClient.getClient().create(APIInterface.class);
            Call<ChallengeResponse> call = service.getChallenge(token);
            call.enqueue(new Callback<ChallengeResponse>() {
                @Override
                public void onResponse(Call<ChallengeResponse> call, Response<ChallengeResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ChallengeResponse cr = response.body();
                        Challenge challenge = new Challenge(cr.getId(), cr.getName(),
                                cr.getDate(), cr.getCaption(), cr.getSource(), cr.getLikeCount());
                        challengeDao.deleteAll();
                        challengeDao.insert(challenge);

                    } else {
                        Log.w("getChallenge", "call response is success BUT response body is NULL");

                    }
                }

                @Override
                public void onFailure(Call<ChallengeResponse> call, Throwable t) {
                    Log.e("getChallenge", "FAILURE call response", t);
                }
            });

            return null;
        }

    }

    private static class loadChallengeDetailAsyncTask extends AsyncTask<Void, Void, Void> {

        private ChallengeDetailDao challengeDetailDao;
        private String token;


        public loadChallengeDetailAsyncTask(ChallengeDetailDao challengeDao,String token) {
            this.challengeDetailDao = challengeDetailDao;
            this.token = token;
        }

        @Override
        protected Void doInBackground(Void... v) {

            APIInterface service = RetrofitClient.getClient().create(APIInterface.class);
            Call<List<ChallengeDetail>> call = service.getChallengeDetail(token);
            call.enqueue(new Callback<List<ChallengeDetail>>() {
                @Override
                public void onResponse(Call<List<ChallengeDetail>> call, Response<List<ChallengeDetail>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<ChallengeDetail> c = response.body();
                        challengeDetailDao.deleteAll();
                        challengeDetailDao.insert(c);

                    } else {
                        Log.w("getChallengeDetails", "call response is success BUT response body is NULL");

                    }
                }

                @Override
                public void onFailure(Call<List<ChallengeDetail>> call, Throwable t) {
                    Log.e("getChallengeDetails", "FAILURE call response", t);
                }
            });

            return null;
        }

    }

    private class loadQuestionAsyncTask extends AsyncTask<Void, Void, Void> {
        private QuestionDao questionDao;
        private String token;
        private int idq;

        public loadQuestionAsyncTask(QuestionDao questionDao, String token) {
            this.questionDao = questionDao;
            this.token = token;

        }


        @Override
        protected Void doInBackground(Void... voids) {
            APIInterface service = RetrofitClient.getClient().create(APIInterface.class);
            Call<QuizAndQuestion> call = service.getQuestion(token);
            call.enqueue(new Callback<QuizAndQuestion>() {
                @Override
                public void onResponse(Call<QuizAndQuestion> call, Response<QuizAndQuestion> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        QuizAndQuestion qq = response.body();
                        Question q = new Question(qq.getId(), qq.getText());
                        idq = q.getId();
                        questionDao.deleteAll();
                        questionDao.insert(q);

                    } else {
                        Log.w("getQuestion", "call response is success BUT response body is NULL");

                    }
                }

                @Override
                public void onFailure(Call<QuizAndQuestion> call, Throwable t) {

                    Log.e("getQuestion", "FAILURE call response", t);
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loadAnswer(token, idq);
        }
    }

    private class loadAnswerAsyncTask extends AsyncTask<Void, Void, Void> {

        private AnswerDao answerDao;
        private String token;
        private int idQ;

        public loadAnswerAsyncTask(AnswerDao ad, String t, int idq) {
            answerDao = ad;
            token = t;
            this.idQ = idq;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            APIInterface service = RetrofitClient.getClient().create(APIInterface.class);
            Call<List<Answer>> call = service.getAllAnswer(token, idQ);
            call.enqueue(new Callback<List<Answer>>() {
                @Override
                public void onResponse(Call<List<Answer>> call, Response<List<Answer>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Answer> answers = response.body();
                        answerDao.deleteAll();
                        answerDao.insert(answers);

                    } else {
                        Log.w("getAnswer", "call response is success BUT response body is NULL");

                    }
                }

                @Override
                public void onFailure(Call<List<Answer>> call, Throwable t) {

                    Log.e("getAnswer", "FAILURE call response", t);
                }
            });
            return null;
        }
    }

    private class likeAChallengeAsyncTask extends AsyncTask<Void, Void, Void>{

        private ChallengeDao cdao;
        private String token;
        private int cId;
        private int userId;

        public likeAChallengeAsyncTask(ChallengeDao challengeDao, int userId, String token, int challengeId) {
            this.cdao = challengeDao;
            this.token = token;
            this.cId = challengeId;
            this.userId = userId;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            APIInterface service = RetrofitClient.getClient().create(APIInterface.class);
            Call<Integer> call = service.likeAChallenge(token, userId, cId);
            call.enqueue(new Callback<Integer>() {

                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if(response.isSuccessful() && response.body()!= null ){
                        int rvalue = response.body().intValue();
                        if(rvalue > 0){
                            challengeDao.updateChallengeLike(cId,rvalue );
                            Log.i("updateLikeChallenge", "success update like");
                            return;
                        }
                        return;
                    }

                    Log.w("updateLikeChallenge", "call response is success BUT response body is NULL or other else");
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Log.e("updateLikeChallenge", "FAIL update like" , t);

                }
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            loadChallengeDetail(token);

        }
    }


    private class acceptClAsysnctask extends AsyncTask<Void, Void,Void>{
        private ChallengeDetailDao cdao;
        private ChallengeAccepted challengeAccepted ;



        public acceptClAsysnctask(ChallengeDetailDao challengeDetailDao, ChallengeAccepted challengeAccepted) {
            this.cdao = challengeDetailDao;
            this.challengeAccepted = challengeAccepted;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            APIInterface service = RetrofitClient.getClient().create(APIInterface.class);
            Call<Integer> call = service.acceptChallenge(challengeAccepted);
            call.enqueue(new Callback<Integer>() {

                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if(response.isSuccessful() && response.body()!= null ){
                        if(response.body().intValue() > 0){
                            int id = challengeAccepted.getChallengeDetailId();
                            cdetailDao.updateCDetailParticipant(id);
                            Log.i("upCDetailParticipant", "success update challenge detail participant");
                            return;
                        }
                        return;
                    }

                    Log.w("upCDetailParticipant", "call response is success BUT response body is NULL or other else");
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Log.e("upCDetailParticipant", "FAIL update participant" , t);

                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            loadChallengeDetail(challengeAccepted.getToken());

        }
    }

    private class likeAnswerAsyncTask extends AsyncTask<Void, Void,Void>{
        private AnswerDao answerDao;
        private String token;
        private int userId;
        private int answerId;

        public likeAnswerAsyncTask(AnswerDao answerDao, String token, int userId, int answerId) {
            this.answerDao = answerDao;
            this.token = token;
            this.userId = userId;
            this.answerId = answerId;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            APIInterface service = RetrofitClient.getClient().create(APIInterface.class);
            Call<Integer> call = service.likeAnAnswer(token, answerId, userId);
            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        int rvalue = response.body().intValue();
                        if (rvalue > 0) {
                            answerDao.updateAnswerLike(answerId, rvalue);
                            Log.i("updateAnswerLike", "success update like");
                            return;
                        }
                        return;
                    }

                    Log.w("updateAnswerLike", "call response is success BUT response body is NULL or other else");

                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Log.e("updateLikeChallenge", "FAIL update like", t);
                }
            });
            return null;
        }
    }

    private class submitAnswerAsyncTask extends AsyncTask<Void, Void, Void> {
        private AnswerOfQuestion answer;
        private String token;


        public submitAnswerAsyncTask(String token, AnswerOfQuestion answer) {
            this.answer = answer;
            this.token = token;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            APIInterface service = RetrofitClient.getClient().create(APIInterface.class);
            Call<String> call = service.submitAnswerOfQuestion(token, answer);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()){
                        Log.i("submitAnswer", "success submit answer");
                    }

                    Log.w("submitAnswer", "UNsuccess submit answer" );
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("submitAnswer", "fail submit answer" , t);
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            loadQuestion(token);
            loadChallengeDetail(token);
        }
    }

    private class findAnswerByUsernameAsynTask extends AsyncTask<Void, Void, Void>{
        private String token;
        private String username;
        private AnswerDao answerDao;
        private int idq;


        public findAnswerByUsernameAsynTask(String token, String username, AnswerDao answerDao, int idq) {
            this.token = token;
            this.username = username;
            this.answerDao = answerDao;
            this.idq = idq;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            APIInterface service = RetrofitClient.getClient().create(APIInterface.class);
            Call<List<Answer>> call = service.findAnswerByUsername(token,username, idq);
            call.enqueue(new Callback<List<Answer>>() {
                @Override
                public void onResponse(Call<List<Answer>> call, Response<List<Answer>> response) {
                    if(response.isSuccessful() && response.body() != null ){
                        answerDao.deleteAll();
                        answerDao.insert(response.body());

                        Log.i("fAnswerKeyword","success find answer by keyword");
                    }

                    Log.w("fAnswerKeyword","UNsuccess find answer by keyword");
                }

                @Override
                public void onFailure(Call<List<Answer>> call, Throwable t) {

                    Log.e("fAnswerKeyword","FAIL find answer by keyword");
                }
            });
            return null;
        }
    }

    private class findAnswerByKeywordAsynTask extends AsyncTask<Void, Void, Void>{
        private String token;
        private String keyword;
        private int idq;
        private AnswerDao answerDao;


        public findAnswerByKeywordAsynTask(String token, String keyword, AnswerDao answerDao, int idq) {
            this.token = token;
            this.keyword = keyword;
            this.answerDao = answerDao;
            this.idq = idq;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            APIInterface service = RetrofitClient.getClient().create(APIInterface.class);
            Call<List<Answer>> call = service.findAnswerByAnswerKeyWord(token,keyword, idq);
            call.enqueue(new Callback<List<Answer>>() {
                @Override
                public void onResponse(Call<List<Answer>> call, Response<List<Answer>> response) {
                    if(response.isSuccessful() && response.body() != null ){
                        answerDao.deleteAll();
                        answerDao.insert(response.body());

                        Log.i("fAnswerKeyword","success find answer by keyword");
                    }

                    Log.w("fAnswerKeyword","UNsuccess find answer by keyword");
                }

                @Override
                public void onFailure(Call<List<Answer>> call, Throwable t) {
                    Log.e("fAnswerKeyword","FAIL find answer by keyword");

                }
            });
            return null;
        }
    }
}