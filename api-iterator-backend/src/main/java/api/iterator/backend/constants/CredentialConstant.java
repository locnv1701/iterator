package api.iterator.backend.constants;

public class CredentialConstant {
    public static final String SECRET = "ITERATOR_AUTHORIZATION";
    public static final long EXPIRATION_TIME = 86_400_000; // 1 day
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REGISTER_URL = "/api/user/register";
    public static final String SIGN_IN_URL = "/api/user/login";
    public static final String CLAIM = "user_type";
}
