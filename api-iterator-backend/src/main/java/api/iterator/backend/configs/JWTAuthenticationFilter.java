package api.iterator.backend.configs;

import api.iterator.backend.configs.users.CustomUserPrincipal;
import api.iterator.backend.constants.CredentialConstant;
import api.iterator.backend.models.UserResponseModel;
import api.iterator.backend.models.logins.UserLoginRequestModel;
import api.iterator.backend.models.logins.UserLoginResponseModel;
import api.iterator.backend.utils.HttpServletUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Check user credentials
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final String SIGN_IN_URL = CredentialConstant.SIGN_IN_URL;
    private static final String SECRET = CredentialConstant.SECRET;
    private static final long EXPIRATION_TIME = CredentialConstant.EXPIRATION_TIME;
    private static final String CLAIM = CredentialConstant.CLAIM;

    private final ObjectMapper MAPPER = new ObjectMapper();
    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        // Set login URL
        setFilterProcessesUrl(SIGN_IN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Authentication authentication = null;
        try {
            UserLoginRequestModel credentials = MAPPER.readValue(request.getInputStream(), UserLoginRequestModel.class);

            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getUserName(),
                            credentials.getUserPassword()
                    )
            );
        } catch (AuthenticationException | IOException e) {
            try {
                // Return response if credentials are not correct
                UserResponseModel userResponseModel = new UserResponseModel(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Your username or password are not correct."
                );

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                HttpServletUtils.writeJsonResponse(response, MAPPER.writeValueAsString(userResponseModel));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {
        CustomUserPrincipal userPrincipal = (CustomUserPrincipal) auth.getPrincipal();

        String token = JWT.create()
                .withSubject(userPrincipal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim(CLAIM, userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));

        // Set login principal response model
        UserLoginResponseModel userLoginResponseModel = new UserLoginResponseModel(
                userPrincipal.getUserId(),
                userPrincipal.getApplicantId(),
                token
        );

        HttpServletUtils.writeJsonResponse(response, MAPPER.writeValueAsString(userLoginResponseModel));
    }
}
