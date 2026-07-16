package br.com.ciccr.simo.common.config;

import br.com.ciccr.simo.modules.role.enums.RoleName;
import br.com.ciccr.simo.modules.role.model.Role;
import br.com.ciccr.simo.modules.role.repository.RoleRepository;
import br.com.ciccr.simo.modules.user.model.User;
import br.com.ciccr.simo.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("dev")
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        createAdminUser();

    }

    private void createAdminUser() {

        if (userRepository.existsByEmail("admin@simo.pr.gov.br")) {
            return;
        }

        Role master = roleRepository.findByName(RoleName.MASTER)
                .orElseThrow(() -> new RuntimeException("Perfil MASTER não encontrado."));

        User admin = User.builder()
                .fullName("Administrador SIMO")
                .email("tec.ciccr@gmail.com")
                .password(passwordEncoder.encode("admin@123"))
                .active(true)
                .role(master)
                .build();

        userRepository.save(admin);

        log.info("=========================================");
        log.info("Usuário administrador criado com sucesso.");
        log.info("Email: tec.ciccr@gmail.com");
        log.info("Senha: admin@123");
        log.info("=========================================");
    }

}