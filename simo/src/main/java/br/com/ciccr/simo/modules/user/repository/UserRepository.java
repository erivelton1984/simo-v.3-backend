package br.com.ciccr.simo.modules.user.repository;

import br.com.ciccr.simo.common.repository.BaseRepository;
import br.com.ciccr.simo.modules.user.model.User;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {

    @EntityGraph(attributePaths = "role")
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}