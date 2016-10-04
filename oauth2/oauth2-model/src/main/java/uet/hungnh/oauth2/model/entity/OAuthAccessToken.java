package uet.hungnh.oauth2.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"accessToken"}, name = "oauthaccesstoken_accesstoken_unq")
}
)
public class OAuthAccessToken implements Serializable {

    private static final long serialVersionUID = -1293275677006121196L;

    @Id
    @Column(nullable = false)
    private String accessToken;

    private String refreshToken;

    @Column(nullable = false)
    private Date expiredDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_user_id")
    private OAuthUser user;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public OAuthUser getUser() {
        return user;
    }

    public void setUser(OAuthUser user) {
        this.user = user;
    }
}
