package br.com.ciccr.simo.modules.role.repository;

import br.com.ciccr.simo.common.repository.BaseRepository;
import br.com.ciccr.simo.modules.role.model.Role;
import br.com.ciccr.simo.modules.role.enums.RoleName;

import java.util.Optional;

public interface RoleRepository extends BaseRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);

    boolean existsByName(String name);

}