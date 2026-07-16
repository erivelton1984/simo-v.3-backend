package br.com.ciccr.simo.modules.user.mapper;

import br.com.ciccr.simo.common.mapper.MapperConfiguration;
import br.com.ciccr.simo.modules.user.dto.request.CreateUserRequest;
import br.com.ciccr.simo.modules.user.dto.request.UpdateUserRequest;
import br.com.ciccr.simo.modules.user.dto.response.UserResponse;
import br.com.ciccr.simo.modules.user.dto.response.UserSummaryResponse;
import br.com.ciccr.simo.modules.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "active", ignore = true)
    User toEntity(CreateUserRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    void update(UpdateUserRequest request,
                @MappingTarget User user);

    @Mapping(target = "role", source = "role.name")
    UserResponse toResponse(User user);

    @Mapping(target = "role", source = "role.name")
    UserSummaryResponse toSummary(User user);

    List<UserSummaryResponse> toSummaryList(List<User> users);

}