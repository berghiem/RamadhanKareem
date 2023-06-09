package ruby.com.hijrahchallenge.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ruby.com.hijrahchallenge.R;

public class MyPageView extends android.support.v4.app.Fragment {

    private TextView ceasy;
    private TextView cmedium;
    private TextView chard;
    private TextView username;
    private TextView uniQcode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mypage,
                container, false);

        ceasy = (TextView) view.findViewWithTag(R.id.easy);
        cmedium = (TextView) view.findViewWithTag(R.id.medium);
        chard = (TextView) view.findViewWithTag(R.id.hard);
        username = (TextView) view.findViewWithTag(R.id.username);
        uniQcode = (TextView) view.findViewWithTag(R.id.uniqcode);

        return view;
    }
}
