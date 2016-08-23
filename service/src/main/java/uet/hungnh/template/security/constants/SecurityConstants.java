package uet.hungnh.template.security.constants;

public class SecurityConstants {

    // HEADERS
    public static final String USERNAME_HEADER = "X-Auth-Username";
    public static final String PASSWORD_HEADER = "X-Auth-Password";
    public static final String TOKEN_HEADER = "X-Auth-Token";
    public static final String FORWARDED_FOR_HEADER = "X-Forwarded-For";

    // ROLES
    public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    // ENDPOINTS
    public static final String AUTHENTICATION_ENDPOINT = "/authenticate";
}
