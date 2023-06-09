package ruby.com.hijrahchallenge.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.sql.Timestamp;

@Entity(tableName = "user")
public class User {


    @PrimaryKey
    @NonNull
    private int id;

    
    private String username;

    
    private String password;

    
    private String email;

    
    private Timestamp lastLogin;

    
    private String uniQcode;

    
    private String IPadress;

    
    private int downlink;

    public String getIPadress() {
        return IPadress;
    }

    public void setIPadress(String IPadress) {
        this.IPadress = IPadress;
    }

    public int getDownlink() {
        return downlink;
    }

    public void setDownlink(int downlink) {
        this.downlink = downlink;
    }


    public String getUniQcode() {
        return uniQcode;
    }

    public void setUniQcode(String uniQcode) {
        this.uniQcode = uniQcode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }
}
