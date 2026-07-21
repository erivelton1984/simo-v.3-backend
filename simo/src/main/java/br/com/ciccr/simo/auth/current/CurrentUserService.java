package br.com.ciccr.simo.auth.current;

import br.com.ciccr.simo.auth.security.CustomUserDetails;
import br.com.ciccr.simo.common.exception.ResourceNotFoundException;
import br.com.ciccr.simo.modules.role.enums.RoleName;
import br.com.ciccr.simo.modules.user.model.User;
import br.com.ciccr.simo.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    public User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {

            throw new IllegalStateException("Usuário não autenticado.");
        }

        return userRepository.findById(userDetails.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado."));
    }

    public Long getCurrentUserId() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {

            throw new IllegalStateException("Usuário não autenticado.");
        }

        return userDetails.getId();
    }

    public String getCurrentEmail() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {

            throw new IllegalStateException("Usuário não autenticado.");
        }

        return userDetails.getUsername();
    }

    public String getCurrentRole() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {

            throw new IllegalStateException("Usuário não autenticado.");
        }

        return userDetails.getRole();
    }
}