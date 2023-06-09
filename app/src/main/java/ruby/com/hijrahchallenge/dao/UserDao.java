package ruby.com.hijrahchallenge.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ruby.com.hijrahchallenge.model.User;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("DELETE FROM user")
    void deleteAll();

    @Query("SELECT * from user  ")
    LiveData<List<User>> getAllUser();
}
