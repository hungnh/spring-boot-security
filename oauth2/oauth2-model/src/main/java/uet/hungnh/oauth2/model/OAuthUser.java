package uet.hungnh.oauth2.model;

import uet.hungnh.common.model.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {
                @UniqueConstraint(columnNames = {"username"}, name = "user_account_username_unq"),
                @UniqueConstraint(columnNames = {"email"}, name = "user_account_email_unq")
        }
)
public class OAuthUser extends BaseEntity {
}
