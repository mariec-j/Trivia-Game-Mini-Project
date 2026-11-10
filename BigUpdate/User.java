package BigUpdate;

// File: User.java
import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private int score;
    private String password;
    // other fields

    public User(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() { return username; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public void setPassword(String password) { this.password = password; }
    public void verifyPassword(String inp) {
        if (inp == password) {
            // Fill later
        } else {
            // Fill later
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", score=" + score +
                '}';
    }
}

