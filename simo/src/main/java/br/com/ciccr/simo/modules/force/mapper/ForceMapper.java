package br.com.ciccr.simo.modules.force.mapper;

import br.com.ciccr.simo.modules.force.dto.request.CreateForceRequest;
import br.com.ciccr.simo.modules.force.dto.request.UpdateForceRequest;
import br.com.ciccr.simo.modules.force.dto.response.ForceResponse;
import br.com.ciccr.simo.modules.force.model.Force;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ForceMapper {

    Force toEntity(CreateForceRequest request);

    ForceResponse toResponse(Force force);

    void updateEntity(UpdateForceRequest request,
                      @MappingTarget Force force);

}