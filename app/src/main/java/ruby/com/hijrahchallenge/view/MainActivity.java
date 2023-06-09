package ruby.com.hijrahchallenge.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import ruby.com.hijrahchallenge.Navigate;
import ruby.com.hijrahchallenge.R;
import ruby.com.hijrahchallenge.model.Challenge;
import ruby.com.hijrahchallenge.model.ChallengeDetail;
import ruby.com.hijrahchallenge.model.User;
import ruby.com.hijrahchallenge.room.db.UserViewModel;

public class MainActivity extends AppCompatActivity implements ChallengeView.FindAnswerTransisition, Navigate {

    private TextView mTextMessage;
    private HomeView homeFragment;
    private MyPageView myPageFragment;
    private ChallengeView challengeFragment;
    private FindAnswerView findAnswerFragment;
    private BottomNavigationView bottomNavigationView;
    private UserViewModel userViewModel;

    private String token;
    private int userId;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    ft.replace(R.id.fragment_container, homeFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                    return true;

                case R.id.navigation_challenge_list:
                    ft.replace(R.id.fragment_container, challengeFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                    return true;

                case R.id.navigation_my_page:
                    ft.replace(R.id.fragment_container, myPageFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                    return true;


            }

            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        bottomNavigationView = findViewById(R.id.navigation);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (savedInstanceState != null) {
            // cleanup any existing fragments in case we are in detailed mode
            getFragmentManager().executePendingTransactions();
            Fragment fragmentById = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (fragmentById != null) {
                ft.remove(fragmentById).commit();
            }
        }

        homeFragment = new HomeView();
        myPageFragment = new MyPageView();
        challengeFragment = new ChallengeView();
        findAnswerFragment = new FindAnswerView();

        ft.replace(R.id.fragment_container, homeFragment, "home_view");
        ft.addToBackStack(null);
        ft.commit();

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getAllUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable final List<User> users) {
                userId = userViewModel.getAllUser().getValue().get(0).getId();
            }
        });


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        this.token = sharedPref.getString(getString(R.string.token), "");

        //TODO : jam 4 subuh aja
        if (!token.isEmpty()) {

            // hijrahViewModel.loadQuestion(token);
            //    hijrahViewModel.loadQuiz(token);
        }

    }


    @Override
    public void onBackPressed() {

        HomeView myFragment = (HomeView) getSupportFragmentManager().findFragmentByTag("home_view");
        if (myFragment != null && !myFragment.isVisible()) {
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, homeFragment);
            ft.addToBackStack(null);
            ft.commit();
            bottomNavigationView.getMenu().getItem(0).setChecked(true);

        }

    }


    @Override
    public void toFindAnswer() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, findAnswerFragment, "home_view");
        ft.addToBackStack(null);
        ft.commit();

    }

    @Override
    public void navigateTologin() {

    }

    @Override
    public void navigateToHome(String token, int userId) {

    }

    @Override
    public void navigateToChallengePage() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, challengeFragment, "home_view");
        ft.addToBackStack(null);
        ft.commit();
    }
}
