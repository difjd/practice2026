package dev.vorstu.security;

import dev.vorstu.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication==null || !(authentication.getPrincipal() instanceof CustomUserDetails customUserDetails))
            throw new IllegalStateException("user is not authenticated");

        return customUserDetails.getUser();
    }
}
