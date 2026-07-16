package br.com.ciccr.simo.auth.service;

import br.com.ciccr.simo.auth.dto.request.LoginRequest;
import br.com.ciccr.simo.auth.dto.response.AuthUserResponse;
import br.com.ciccr.simo.auth.dto.response.LoginResponse;
import br.com.ciccr.simo.auth.jwt.JwtService;
import br.com.ciccr.simo.modules.user.model.User;
import br.com.ciccr.simo.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    @Value("${jwt.expiration}")
    private Long expiration;

    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        User user = userRepository.findByEmail(request.email())
                .orElseThrow();

        String token = jwtService.generateToken(user);

        return new LoginResponse(

                token,

                "Bearer",

                expiration,

                new AuthUserResponse(
                        user.getId(),
                        user.getFullName(),
                        user.getEmail(),
                        user.getRole().getName().name()
                )
        );
    }

}