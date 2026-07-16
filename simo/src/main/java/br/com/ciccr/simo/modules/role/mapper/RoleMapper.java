package br.com.ciccr.simo.modules.role.mapper;

import br.com.ciccr.simo.common.mapper.MapperConfiguration;
import br.com.ciccr.simo.modules.role.dto.response.RoleResponse;
import br.com.ciccr.simo.modules.role.model.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface RoleMapper {

    RoleResponse toResponse(Role role);

    List<RoleResponse> toResponseList(List<Role> roles);

}