package uet.hungnh.security.model.entity;

import org.joda.time.DateTime;
import uet.hungnh.common.model.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"token"}, name = "verification_token_unq")
        }
)
public class VerificationToken extends BaseEntity {

    private static final long serialVersionUID = -8693380713411574372L;
    private static final int EXPIRATION_IN_HOURS = 24 * 7;

    @Column(nullable = false)
    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            nullable = false,
            name = "user_id",
            foreignKey = @ForeignKey(name = "fk_verification_token_user")
    )
    private User user;

    @Column(nullable = false)
    private Date expiredDate;

    public VerificationToken() {
        super();
        this.expiredDate = DateTime.now()
                .plusHours(EXPIRATION_IN_HOURS)
                .toDate();
    }

    public VerificationToken(String token) {
        this();
        this.token = token;
    }

    public VerificationToken(String token, User user) {
        this();
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }
}
