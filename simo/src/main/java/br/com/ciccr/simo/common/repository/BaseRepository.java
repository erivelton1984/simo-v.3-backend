package br.com.ciccr.simo.common.repository;

import br.com.ciccr.simo.common.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
}
