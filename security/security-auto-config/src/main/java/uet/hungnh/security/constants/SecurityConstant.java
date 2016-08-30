package uet.hungnh.security.constants;

public class SecurityConstant {

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
    public static final String LOGIN_ENDPOINT = "/user/login";
    public static final String LOGOUT_ENDPOINT = "/user/logout";
    public static final String REGISTRATION_ENDPOINT = "/user/registration";
    public static final String EMAIL_CONFIRMATION_ENDPOINT = "/user/registration/confirmation";
    public static final String REQUEST_RESET_PASSWORD_ENDPOINT = "/user/password/request-reset";
    public static final String RESET_PASSWORD_ENDPOINT = "/user/password/reset";
    public static final String CHANGE_PASSWORD_ENDPOINT = "/user/password/update";
}
