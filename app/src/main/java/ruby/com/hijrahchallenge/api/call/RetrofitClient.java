package ruby.com.hijrahchallenge.api.call;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient   {

   // private static final String BASE_URL = "http://ramadhanchallenge.000webhostapp.com/";
    private static final String BASE_URL = "http://race.indoweb.xyz/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        if (retrofit==null) {
           retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient).build();
        }
        return retrofit;
    }




}
