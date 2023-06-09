package ruby.com.hijrahchallenge.view;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ruby.com.hijrahchallenge.ButtonAction;
import ruby.com.hijrahchallenge.OnItemClickImage;
import ruby.com.hijrahchallenge.R;
import ruby.com.hijrahchallenge.api.call.APIInterface;
import ruby.com.hijrahchallenge.api.call.RetrofitClient;
import ruby.com.hijrahchallenge.model.Challenge;
import ruby.com.hijrahchallenge.model.response.ChallengeResponse;
import ruby.com.hijrahchallenge.room.db.HijrahViewModel;

/**
 * Created by Ruby on 4/23/2018.
 */


public class HomeView extends android.support.v4.app.Fragment implements View.OnClickListener {
    private Context context;
    private TextView textTV;
    private TextView sourceTV;
    private TextView shareCountTV;
    private TextView likeCountTV;
    private ImageView likeButton;
    private LinearLayout shareButton;
    private CardView cardView;
    private boolean isLike;
    private String token;
    private Challenge challenge;
    private HijrahViewModel hijrahViewModel;


    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.context = context;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.challenge,
                container, false);

        textTV = (TextView) view.findViewById(R.id.text);
        sourceTV = (TextView) view.findViewById(R.id.source);
        likeCountTV = (TextView) view.findViewById(R.id.likeCount);
        shareCountTV = (TextView) view.findViewById(R.id.shareCount);
        likeButton = (ImageView) view.findViewById(R.id.likeButton);
        shareButton = (LinearLayout) view.findViewById(R.id.shareButton);
        cardView = (CardView) view.findViewById(R.id.card_challenge);
        isLike = false;

        likeButton.setOnClickListener(this);
        shareButton.setOnClickListener(this);


        hijrahViewModel = ViewModelProviders.of(this).get(HijrahViewModel.class);
        hijrahViewModel.getChallenge().observe(this, new Observer<List<Challenge>>() {
            @Override
            public void onChanged(@Nullable List<Challenge> challenges) {
                if (challenges != null & challenges.size() > 0) {
                    challenge = challenges.get(0);
                    updateView(challenge.getCaption(), challenge.getSource(), challenge.getLikeCount(), challenge.getShareCount());

                }
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        this.token = sharedPref.getString(getString(R.string.token), "");

        //TODO : jam 4 subuh aja
        if (!token.isEmpty()) {

            hijrahViewModel.loadChallenge(token);
        }
    }

    public void updateView(String text, String source, int likeCount, int shareCount) {
        textTV.setText(text);
        sourceTV.setText(source);
        likeCountTV.setText("" + likeCount);
        shareCountTV.setText("" + shareCount);
    }

    @Override
    public void onClick(View view) {
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.token), Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.token), "");
        int userId = sharedPref.getInt(getString(R.string.userid), 0);

        switch (view.getId()) {
            case R.id.likeButton:
                if (!isLike) {
                    likeButton.setImageResource(R.drawable.likecolored);
                    isLike = true;
                } else {
                    likeButton.setImageResource(R.drawable.like);
                    isLike = false;
                }
                hijrahViewModel.likeAChallenge(token, userId, challenge.getId());
                break;

            case R.id.shareButton:
                shareAChallenge();
                break;
        }
    }

    public void shareAChallenge() {
        Bitmap bitmap = convertToImage(cardView);
        try {
            FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory() + "/path/to/file.png");
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Environment.getExternalStorageDirectory() + "/path/to/file.png");
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.app_name)));
    }

    private Bitmap convertToImage(View view) {

        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;

    }
}
