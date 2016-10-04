package uet.hungnh.oauth2.constant;

public class OAuth2Constant {
    private static final String FB_GRAPH_API_ENDPOINT = "https://graph.facebook.com/v2.7/";
    public static final String FB_EXCHANGE_TOKEN_URL_TEMPLATE = FB_GRAPH_API_ENDPOINT + "/oauth/access_token?grant_type=fb_exchange_token&client_id={client_id}&client_secret={client_secret}&fb_exchange_token={fb_exchange_token}";
}
