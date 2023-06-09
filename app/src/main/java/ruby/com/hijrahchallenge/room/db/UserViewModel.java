package ruby.com.hijrahchallenge.room.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ruby.com.hijrahchallenge.model.User;
import ruby.com.hijrahchallenge.model.response.ChallengeResponse;

public class UserViewModel extends AndroidViewModel {

    private HijrahRepository mRepository;

    private LiveData<List<User>> users;

    public UserViewModel(Application application) {
        super(application);
        mRepository = new HijrahRepository(application);
        users = mRepository.getAllUser();
    }

    public LiveData<List<User>> getAllUser() {
        return users;
    }

    public void insertUser(User user) {
        mRepository.insertUser(user);

   }

}
