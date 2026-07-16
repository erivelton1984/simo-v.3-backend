package br.com.ciccr.simo.auth.service;

import br.com.ciccr.simo.auth.security.CustomUserDetails;
import br.com.ciccr.simo.common.exception.ResourceNotFoundException;
import br.com.ciccr.simo.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        return repository.findByEmail(username)
                .map(CustomUserDetails::new)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado."));
    }
}