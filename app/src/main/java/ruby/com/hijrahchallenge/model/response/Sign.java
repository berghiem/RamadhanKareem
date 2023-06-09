package ruby.com.hijrahchallenge.model.response;

import com.google.gson.annotations.SerializedName;

public class
Sign {

@SerializedName("issuccess")
private boolean issuccess;

@SerializedName("token")
private String token;

@SerializedName("message")
private String message;

@SerializedName("userId")
private int userId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isIssuccess() {
        return issuccess;
    }

    public void setIssuccess(boolean issuccess) {
        this.issuccess = issuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
