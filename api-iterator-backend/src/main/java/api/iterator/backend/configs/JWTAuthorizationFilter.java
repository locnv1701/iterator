package api.iterator.backend.configs;

import api.iterator.backend.constants.CredentialConstant;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Check user authority
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private static final String SIGN_IN_URL = CredentialConstant.SIGN_IN_URL;
    private static final String AUTHORIZATION_HEADER = CredentialConstant.AUTHORIZATION_HEADER;
    private static final String TOKEN_PREFIX = CredentialConstant.TOKEN_PREFIX;
    private static final String SECRET = CredentialConstant.SECRET;
    private static final String CLAIM = CredentialConstant.CLAIM;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        // Authorization is required for all URL except sign in url
        if (!request.getServletPath().contains(SIGN_IN_URL)) {
            String header = request.getHeader(AUTHORIZATION_HEADER);

            if (header == null || !header.startsWith(TOKEN_PREFIX)) {
                chain.doFilter(request, response);
                return;
            }

            UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION_HEADER);

        if (token != null) {
            // parse the token
            try {
                DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                        .build()
                        .verify(token.replace(TOKEN_PREFIX, ""));

                String user = decodedJWT.getSubject();

                // Get user type from claim
                String userType = decodedJWT.getClaim(CLAIM).asArray(String.class)[0];
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(userType));

                if (user != null) {
                    return new UsernamePasswordAuthenticationToken(user, null, authorities);
                }
            } catch (JWTVerificationException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
