package ruby.com.hijrahchallenge.room.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import ruby.com.hijrahchallenge.dao.AnswerDao;
import ruby.com.hijrahchallenge.dao.ChallengeDao;
import ruby.com.hijrahchallenge.dao.ChallengeDetailDao;
import ruby.com.hijrahchallenge.dao.QuestionDao;
import ruby.com.hijrahchallenge.dao.UserDao;
import ruby.com.hijrahchallenge.model.Challenge;
import ruby.com.hijrahchallenge.model.ChallengeDetail;
import ruby.com.hijrahchallenge.model.Question;
import ruby.com.hijrahchallenge.model.User;
import ruby.com.hijrahchallenge.model.response.Answer;
import ruby.com.hijrahchallenge.util.Converter;


@Database(entities = {User.class, Challenge.class, ChallengeDetail.class, Answer.class, Question.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class HijrahRoomDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract ChallengeDao challengeDao();
    public abstract ChallengeDetailDao challengeDetailDao();
    public abstract QuestionDao questionDao();
    public abstract AnswerDao answerDao();

    private static HijrahRoomDatabase INSTANCE;


    static HijrahRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (HijrahRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            HijrahRoomDatabase.class, "hijrah_database")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();

                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final UserDao mDao;
        private final ChallengeDao mchallengeDao;
        private final QuestionDao mquestionDao;
        private final ChallengeDetailDao mchallengeDetailDao;
        private final AnswerDao manswerDao;


        PopulateDbAsync(HijrahRoomDatabase db) {
            mDao = db.userDao();
            mchallengeDao = db.challengeDao();
            mchallengeDetailDao = db.challengeDetailDao();
            mquestionDao = db.questionDao();
            manswerDao = db.answerDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            mchallengeDao.deleteAll();
            return null;
        }
    }

}
