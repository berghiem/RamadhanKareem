package ruby.com.hijrahchallenge.model;

public class Register {

    private String email;
    private String username;
    private String password;
    private String IPadress;
    private String uniQodeRef;

    public Register(String email, String username, String password, String IPadress, String uniQodeRef) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.IPadress = IPadress;
        this.uniQodeRef = uniQodeRef;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getIPadress() {
        return IPadress;
    }

    public void setIPadress(String IPadress) {
        this.IPadress = IPadress;
    }

    public String getUniQodeRef() {
        return uniQodeRef;
    }

    public void setUniQodeRef(String uniQodeRef) {
        this.uniQodeRef = uniQodeRef;
    }
}
