package api.iterator.backend.configs;

import api.iterator.backend.configs.users.CustomUserDetailsService;
import api.iterator.backend.constants.CredentialConstant;
import api.iterator.backend.constants.UserTypeConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

/**
 * Configure API authentication and authorization
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final String APPLICANT = UserTypeConstant.APPLICANT;
    private final String SHARER = UserTypeConstant.SHARER;

    public WebSecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(CredentialConstant.REGISTER_URL).permitAll();
        http.authorizeRequests().antMatchers(CredentialConstant.SIGN_IN_URL).permitAll();
        http.authorizeRequests().antMatchers("/health/liveness").permitAll();

        http.authorizeRequests().antMatchers("/api/display/applicant/**").hasAuthority(SHARER);

        http.authorizeRequests().antMatchers("/api/display/sharer/job-post/**").hasAuthority(APPLICANT);

        http.authorizeRequests().antMatchers("/api/applicant/edit-education/**").hasAnyAuthority(APPLICANT);
        http.authorizeRequests().antMatchers("/api/applicant/edit-experiences/**").hasAnyAuthority(APPLICANT);
        http.authorizeRequests().antMatchers("/api/applicant/edit-skills/**").hasAnyAuthority(APPLICANT);

        http.authorizeRequests().antMatchers("/api/sharer/edit-job-post/**").hasAnyAuthority(SHARER);


        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new JWTAuthenticationFilter(authenticationManager()));
        http.addFilter(new JWTAuthorizationFilter(authenticationManager()));
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Collections.singletonList("x-auth-token"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
