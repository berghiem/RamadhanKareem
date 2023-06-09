package ruby.com.hijrahchallenge.view;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ruby.com.hijrahchallenge.ButtonAction;
import ruby.com.hijrahchallenge.ChallengeAction;
import ruby.com.hijrahchallenge.R;
import ruby.com.hijrahchallenge.adapter.AnswerAdapter;
import ruby.com.hijrahchallenge.adapter.ChallengeAdapter;
import ruby.com.hijrahchallenge.model.Challenge;
import ruby.com.hijrahchallenge.model.ChallengeDetail;
import ruby.com.hijrahchallenge.model.Question;
import ruby.com.hijrahchallenge.model.request.AnswerOfQuestion;
import ruby.com.hijrahchallenge.model.request.ChallengeAccepted;
import ruby.com.hijrahchallenge.model.response.Answer;
import ruby.com.hijrahchallenge.room.db.HijrahViewModel;

public class ChallengeView extends android.support.v4.app.Fragment implements ChallengeAdapter.Accept, AnswerAdapter.Like, View.OnClickListener{
    private RecyclerView challengeListView;
    private RecyclerView answerListView;
    private Context context;
    private String token;
    private int userId;
    private HijrahViewModel hijrahViewModel;
    private Challenge challenge;
    private List<ChallengeDetail> challengeDetails;
    private List<Answer> answers;
    private TextView questionTv;
    private TextView countAnswer;
    private LinearLayout findAnswer;
    private Button submitAnswer;
    private Question question;
    private TextInputEditText myAnswerInput;
    private FindAnswerTransisition findAnswerTransisition;
    private AppCompatSpinner sorting;


    public interface FindAnswerTransisition{
        public void toFindAnswer();
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.context = context;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.challenge_list,
                container, false);

        challengeListView = (RecyclerView) view.findViewById(R.id.challenges);
        answerListView = (RecyclerView) view.findViewById(R.id.answers);
        questionTv =(TextView) view.findViewById(R.id.question);
        countAnswer = (TextView) view.findViewById(R.id.countAnswer);
        submitAnswer = (Button) view.findViewById(R.id.submitAnswer);
        findAnswer = (LinearLayout) view.findViewById(R.id.findAnswer);
        myAnswerInput = (TextInputEditText) view.findViewById(R.id.myAnswer);
        sorting = (AppCompatSpinner) view.findViewById(R.id.sorting);


        final ChallengeAdapter challengeAdapter = new ChallengeAdapter(context,this);
        final AnswerAdapter answerAdapter = new AnswerAdapter(context,this);

        challengeListView.setAdapter(challengeAdapter);
        answerListView.setAdapter(answerAdapter);
        submitAnswer.setOnClickListener(this);
        findAnswer.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.sorting, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sorting.setAdapter(adapter);

        hijrahViewModel = ViewModelProviders.of(this).get(HijrahViewModel.class);
        hijrahViewModel.getChallenge().observe(this, new Observer<List<Challenge>>() {
            @Override
            public void onChanged(@Nullable List<Challenge> challenges) {
                challenge = challenges.get(0);
            }
        });
        hijrahViewModel.getChallengeDetail().observe(this, new Observer<List<ChallengeDetail>>() {
            @Override
            public void onChanged(@Nullable List<ChallengeDetail> details) {
                if (details != null && details.size() > 0) {
                    challengeDetails = details;
                    challengeAdapter.setChallengeDetails(details);
                }
            }
        });

        hijrahViewModel.getAnswers().observe(this, new Observer<List<Answer>>() {
            @Override
            public void onChanged(@Nullable List<Answer> ans) {
                if(ans != null && ans.size() > 0){
                    answers = ans;
                    answerAdapter.setAnswers(answers);
                    countAnswer.setText(answers.size()+"");
                }
            }
        });
        hijrahViewModel.getQuestion().observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(@Nullable List<Question> questions) {
                if(questions != null && questions.size() > 0){
                    question = questions.get(0);
                    questionTv.setText(question.getText());
                }
            }
        });

        getFragmentManager().addOnBackStackChangedListener(new android.support.v4.app.FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        this.token = sharedPref.getString(getString(R.string.token), "");
        this.userId = sharedPref.getInt(getString(R.string.userid),0);
        //TODO : jam 4 subuh aja
        if (!token.isEmpty()) {

            hijrahViewModel.loadChallengeDetail(token);
            hijrahViewModel.loadQuestion(token);
            if(question != null){
                hijrahViewModel.loadAnswer(token,question.getId());
            }

        }
    }

    @Override
    public void acceptChallenge(int cDetailId) {
        ChallengeAccepted challengeAccepted = new ChallengeAccepted(userId,challenge.getId(), cDetailId,token);
        hijrahViewModel.acceptChallenge(challengeAccepted);
    }

    @Override
    public void likeAnswer(int answerId) {
        hijrahViewModel.likeAnswer(token, userId,answerId);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.submitAnswer :
                AnswerOfQuestion answer = new AnswerOfQuestion();
                answer.setAnswer(myAnswerInput.getText().toString());
                answer.setQuestionid(question.getId());
                answer.setUserid(userId);

                hijrahViewModel.submitAnswer(token,answer );
                break;

            case R.id.findAnswer :
                findAnswerTransisition.toFindAnswer();
                break;

        }
    }


}
