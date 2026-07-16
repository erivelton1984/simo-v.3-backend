package br.com.ciccr.simo.modules.force.repository;

import br.com.ciccr.simo.common.repository.BaseRepository;
import br.com.ciccr.simo.modules.force.model.Force;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForceRepository extends BaseRepository<Force, Long> {

    boolean existsByNameIgnoreCase(String name);

    Optional<Force> findByNameIgnoreCase(String name);

}