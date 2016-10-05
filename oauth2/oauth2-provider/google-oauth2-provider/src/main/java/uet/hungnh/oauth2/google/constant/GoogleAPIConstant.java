package uet.hungnh.oauth2.google.constant;

public class GoogleAPIConstant {
    public static final String GOOGLE_TOKEN_INFO_URL_TEMPLATE = "https://www.googleapis.com/oauth2/v3/tokeninfo?access_token={access_token}";
    public static final String GOOGLE_USER_PROFILE_URL_TEMPLATE = "https://www.googleapis.com/plus/v1/people/{user_id}?access_token={access_token}&fields={fields}";
}
