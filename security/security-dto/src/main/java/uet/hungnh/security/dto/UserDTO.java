package uet.hungnh.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import uet.hungnh.security.dto.validation.ValidEmail;
import uet.hungnh.security.dto.validation.ValidPassword;

import javax.validation.constraints.NotNull;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

public class UserDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String username;

    @NotNull
    @ValidEmail
    private String email;

    @JsonProperty(access = WRITE_ONLY)
    @NotNull
    @ValidPassword
    private String password;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
