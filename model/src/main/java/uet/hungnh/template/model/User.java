package uet.hungnh.template.model;

import uet.hungnh.template.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "user_account",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"username"}, name = "user_account_username_unq"),
                @UniqueConstraint(columnNames = {"email"}, name = "user_account_email_unq")
        }
)
public class User extends BaseEntity {

    private static final long serialVersionUID = -3236711771882631639L;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
}
