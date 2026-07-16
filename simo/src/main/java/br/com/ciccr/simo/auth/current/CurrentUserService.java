package br.com.ciccr.simo.auth.current;

import br.com.ciccr.simo.auth.security.CustomUserDetails;
import br.com.ciccr.simo.modules.role.enums.RoleName;
import br.com.ciccr.simo.modules.user.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    public User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {
            throw new IllegalStateException("Usuário não autenticado.");
        }

        return userDetails.getUser();
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public String getCurrentEmail() {
        return getCurrentUser().getEmail();
    }

    public RoleName getCurrentRole() {
        return getCurrentUser().getRole().getName();
    }

}