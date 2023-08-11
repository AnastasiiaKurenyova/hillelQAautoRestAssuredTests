package ua.ithillel.dto;

public class UserLoginObject {

    private String email;
    private String password;

    public UserLoginObject(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserLoginObject() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
