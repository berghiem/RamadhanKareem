package ruby.com.hijrahchallenge.api.call;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ruby.com.hijrahchallenge.model.ChallengeDetail;
import ruby.com.hijrahchallenge.model.request.AnswerOfQuestion;
import ruby.com.hijrahchallenge.model.response.Answer;
import ruby.com.hijrahchallenge.model.response.ChallengeResponse;
import ruby.com.hijrahchallenge.model.ChallengeAccepted;
import ruby.com.hijrahchallenge.model.Register;
import ruby.com.hijrahchallenge.model.User;
import ruby.com.hijrahchallenge.model.request.Login;
import ruby.com.hijrahchallenge.model.response.QuizAndQuestion;
import ruby.com.hijrahchallenge.model.response.Sign;

public interface APIInterface {

    @POST("api.php?act=register&m=new")
    Call<Sign> register (@Body Register register);

    @POST("api.php?act=login&m=login")
    Call<Sign> login (@Body Login login);

    @POST("forgotPassword")
    Call<Sign> forgotPassword (@Query("email")String email);

    @POST("tokenValidation")
    Call<Boolean> isTokenValid(@Query("token")String token);

    @GET("user")
    Call<User> getUser (@Query("token")String token, @Query("userId")String userId );


    @GET("challenge")
    Call<ChallengeResponse> getChallenge (@Query("token")String token  );

    @POST
    Call<Integer> likeAChallenge (@Query("token")String token, @Query("id")int userid, @Query("id")int challengeId);


    @GET("challengeDetail")
    Call<List<ChallengeDetail>> getChallengeDetail (@Query("token")String token  );


    @GET("question")
    Call<QuizAndQuestion> getQuestion (@Query("token")String token  );

    @GET("quiz")
    Call<QuizAndQuestion> getQuiz (@Query("token")String token  );

    @GET("countParticipantQuiz")
    Call<Integer> countParticipantQuiz (@Query("token")String token, @Query("id")String idQuiz   );

    @GET("countParticipantQuestion")
    Call<Integer> countParticipantQuestion (@Query("token")String token, @Query("id")String idQuestion   );


    @GET("allAnswer")
    Call<List<Answer>> getAllAnswer (@Query("token")String token ,@Query("id")int idQ );

    @POST //return total participant
    Call<Integer> acceptChallenge(@Body ruby.com.hijrahchallenge.model.request.ChallengeAccepted challengeAccepted);

    //@POST
    //Call<String> submitAnswerOfQuiz(@Query("token")String token, @Query("answer")String userId);

    @POST
    Call<String> submitAnswerOfQuestion(@Query("token")String token, @Body AnswerOfQuestion answerOfQuestion);

    @POST
    Call<List<Answer>> findAnswerByAnswerKeyWord (@Query("token")String token, @Query("keyword")String word ,@Query("id")int idq  );

    @POST
    Call<List<Answer>> findAnswerByUsername (@Query("token")String token, @Query("username")String username ,@Query("id")int idq );



    @POST
    Call<String> shareAChallenge (@Query("token")String token, @Query("id")int challengeId);

    @POST
    Call<Integer> likeAnAnswer (@Query("token")String token, @Query("id")int answerId, @Query("id")int userid);

    @POST
    Call<String> shareAnAnswer (@Query("token")String token, @Query("id")int answerId);







}
