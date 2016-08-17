package uet.hungnh.template.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class UserRole implements Serializable {

    private static final long serialVersionUID = -5552012192365161311L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userRoleId;

    private Long userId;
    private String role;

    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
