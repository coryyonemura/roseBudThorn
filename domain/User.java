package domain;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String email;
    private String username;
    private String password;
    private Date dateCreated;

    public User(int id, String email, String username, String password, Date dateCreated) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.dateCreated = dateCreated;
    }

    public int getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }
    
    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Date getDateCreated() {
        return this.dateCreated;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}