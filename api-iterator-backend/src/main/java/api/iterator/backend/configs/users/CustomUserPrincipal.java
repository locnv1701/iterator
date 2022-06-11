package api.iterator.backend.configs.users;

import api.iterator.backend.constants.UserTypeConstant;
import api.iterator.backend.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Get user principal
 */
public class CustomUserPrincipal implements UserDetails {
    private static final String APPLICANT = UserTypeConstant.APPLICANT;

    private final UserEntity userEntity;

    public CustomUserPrincipal(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Long getUserId() {
        return userEntity.getId();
    }

    public Long getApplicantId() {
        return userEntity.getApplicantId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userEntity.getUserType()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return userEntity.getUserPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
