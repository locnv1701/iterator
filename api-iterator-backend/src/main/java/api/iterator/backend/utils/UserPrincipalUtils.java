package api.iterator.backend.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;

public class UserPrincipalUtils {
    // Get user type in security context holder
    public static String getUserType() {
        return new ArrayList<>(SecurityContextHolder.getContext().getAuthentication().getAuthorities()).get(0).getAuthority();
    }
}
