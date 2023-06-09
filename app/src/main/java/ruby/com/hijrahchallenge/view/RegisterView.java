package ruby.com.hijrahchallenge.view;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ruby.com.hijrahchallenge.GoHomePage;
import ruby.com.hijrahchallenge.R;
import ruby.com.hijrahchallenge.api.call.APIInterface;
import ruby.com.hijrahchallenge.api.call.RetrofitClient;
import ruby.com.hijrahchallenge.model.Register;
import ruby.com.hijrahchallenge.model.response.Sign;
import ruby.com.hijrahchallenge.util.EditTextValidator;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class RegisterView extends Fragment implements View.OnClickListener {

    private Button register;
    private EditText email;
    private EditText username;
    private  EditText password;
    private  EditText uniqcode;
    private GoHomePage goHomePageCallback;
    private Context context;

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.context = context;
        this.goHomePageCallback = (GoHomePage) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register,
                container, false);

        email = (TextInputEditText)view.findViewById(R.id.email);
        username =(TextInputEditText) view.findViewById(R.id.username);
        password = (TextInputEditText)view.findViewById(R.id.password);
        uniqcode =(TextInputEditText)view.findViewById(R.id.uniqcode);
        register = (Button) view.findViewById(R.id.register);
        register.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.register :
                Log.i("register","clicked");
                Toast.makeText(getActivity(),"halo",Toast.LENGTH_SHORT);

                int errorCount = 0;
                   if(!EditTextValidator.isValidEmail(email.getText())){
                       email.setError("Please enter valid mail address");
                       errorCount ++;
                   }

                   if(!EditTextValidator.isValidPassword(password.getText())){
                       password.setError("Password must be at least 7 characters long");
                       errorCount ++;
                   }

                   if(!EditTextValidator.isValid(username.getText())){
                       username.setError("Please enter username");
                       errorCount ++;
                   }

                   if(errorCount > 0){
                       break;
                   }


                    APIInterface service = RetrofitClient.getClient().create(APIInterface.class);
                    final Register register = new Register(email.getText().toString(), username.getText().toString(),
                                        password.getText().toString(),null,uniqcode.getText().toString());
                    Call<Sign> call = service.register(register);
                    call.enqueue(new Callback<Sign>() {
                        @Override
                        public void onResponse(Call<Sign> call, Response<Sign> response) {
                            if(response.isSuccessful() && response.body().isIssuccess()){
                                goHomePageCallback.navigateTologin();

                            }else{
                                Log.i("register_failed",response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<Sign> call, Throwable t) {
                           Log.i("server_register_failed","fail response call "+t.getLocalizedMessage());
                        }
                    });

                break;

        }
    }


}
