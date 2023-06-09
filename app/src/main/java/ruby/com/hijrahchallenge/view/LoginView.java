package ruby.com.hijrahchallenge.view;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ruby.com.hijrahchallenge.Navigate;
import ruby.com.hijrahchallenge.R;
import ruby.com.hijrahchallenge.api.call.APIInterface;
import ruby.com.hijrahchallenge.api.call.RetrofitClient;
import ruby.com.hijrahchallenge.model.request.Login;
import ruby.com.hijrahchallenge.model.response.Sign;
import ruby.com.hijrahchallenge.util.EditTextValidator;

public class LoginView extends Fragment implements View.OnClickListener {

    @BindView(R.id.email)
    EditText email;
    //@BindView(R.id.username)
    //EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login)
    Button login;

    @BindView(R.id.loginFields)
    LinearLayout loginFields;
    @BindView(R.id.progress)
    LinearLayout progress;


    private Navigate goHomePageCallback;
    private Context context;

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.context = context;
        this.goHomePageCallback = (Navigate) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login,
                container, false);
        ButterKnife.bind(this,view);
        login.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:

                int errorCount = 0;
                if(!EditTextValidator.isValidEmail(email.getText())){
                    email.setError("Please enter valid mail address");
                    errorCount ++;
                }

                if(!EditTextValidator.isValidPassword(password.getText())){
                    password.setError("Password must be at least 7 characters long");
                    errorCount ++;
                }


                if(errorCount > 0){
                    break;
                }

                loginFields.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                 new AsyncTask<Void,Void,Void>(){
                    @Override
                    protected Void doInBackground(Void... voids) {

                        APIInterface service = RetrofitClient.getClient().create(APIInterface.class);
                        Call<Sign> call = service.login(new Login(email.getText().toString(),password.getText().toString()));
                        call.enqueue(new Callback<Sign>() {
                            @Override
                            public void onResponse(Call<Sign> call, Response<Sign> response) {
                                if(response.isSuccessful() && response.body().isIssuccess() && response.body().getToken()!=null){

                                    String token = response.body().getToken();
                                    int userId = response.body().getUserId();
                                    goHomePageCallback.navigateToHome(token, userId);

                                }else{
                                    Log.w("loginfailed",response.message());
                                    progress.setVisibility(View.GONE);
                                    loginFields.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onFailure(Call<Sign> call, Throwable t) {
                                Log.e("loginfailed","failure ",t);
                                progress.setVisibility(View.GONE);
                                loginFields.setVisibility(View.VISIBLE);
                            }
                        });
                        return null;
                    }
                }.execute();


                break;
        }

    }
}
