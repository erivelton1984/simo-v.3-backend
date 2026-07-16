package br.com.ciccr.simo.modules.role.service;

import br.com.ciccr.simo.modules.role.dto.response.RoleResponse;
import br.com.ciccr.simo.modules.role.mapper.RoleMapper;
import br.com.ciccr.simo.modules.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;
    private final RoleMapper mapper;

    public List<RoleResponse> findAll() {

        return mapper.toResponseList(
                repository.findAll()
        );
    }

}