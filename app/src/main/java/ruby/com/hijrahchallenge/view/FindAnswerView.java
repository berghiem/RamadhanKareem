package ruby.com.hijrahchallenge.view;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ruby.com.hijrahchallenge.Navigate;
import ruby.com.hijrahchallenge.R;
import ruby.com.hijrahchallenge.adapter.AnswerAdapter;
import ruby.com.hijrahchallenge.model.response.Answer;
import ruby.com.hijrahchallenge.room.db.HijrahViewModel;

public class FindAnswerView extends android.support.v4.app.Fragment implements View.OnClickListener, AnswerAdapter.Like {

    @BindView(R.id.search)
    TextInputEditText search;

    @BindView(R.id.result)
    RecyclerView result;

    @BindView(R.id.nameButton)
    Button nameButton;

    @BindView(R.id.answerButton)
    Button answerButton;

    @BindView(R.id.closebtn)
    Button closeButton;

    private Navigate navigate;
    private Context context;
    private HijrahViewModel hijrahViewModel;
    private AnswerAdapter answerAdapter;
    private int userId;
    private String token;


    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.navigate = (Navigate) context;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search,
                container, false);
        ButterKnife.bind(this, view);
        answerAdapter = new AnswerAdapter(context, this);

        nameButton.setOnClickListener(this);
        answerButton.setOnClickListener(this);
        closeButton.setOnClickListener(this);

        result.setVisibility(View.GONE);

        hijrahViewModel = ViewModelProviders.of(this).get(HijrahViewModel.class);
        hijrahViewModel.getAnswers().observe(this, new Observer<List<Answer>>() {
            @Override
            public void onChanged(@Nullable List<Answer> answers) {
                answerAdapter.setAnswers(answers);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nameButton:
                String username = search.getText().toString();
                hijrahViewModel.findAnswerByUsername(token, username);
                result.setVisibility(View.VISIBLE);
                break;

            case R.id.answerButton:
                String keyword = search.getText().toString();
                hijrahViewModel.findAnswerByKeyword(token, keyword);
                result.setVisibility(View.VISIBLE);
                break;

            case R.id.closebtn:
                navigate.navigateToChallengePage();
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        this.token = sharedPref.getString(getString(R.string.token), "");
        this.userId = sharedPref.getInt(getString(R.string.userid), 0);

    }

    @Override
    public void likeAnswer(int answerId) {

    }
}
