package br.com.ciccr.simo.modules.force.service;

import br.com.ciccr.simo.common.exception.ConflictException;
import br.com.ciccr.simo.common.service.BaseService;
import br.com.ciccr.simo.modules.force.constants.ForceMessages;
import br.com.ciccr.simo.modules.force.dto.request.CreateForceRequest;
import br.com.ciccr.simo.modules.force.dto.request.UpdateForceRequest;
import br.com.ciccr.simo.modules.force.dto.response.ForceResponse;
import br.com.ciccr.simo.modules.force.mapper.ForceMapper;
import br.com.ciccr.simo.modules.force.model.Force;
import br.com.ciccr.simo.modules.force.repository.ForceRepository;
import br.com.ciccr.simo.modules.force.validator.ForceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ForceServiceImpl extends BaseService<Force, Long>
        implements ForceService {

    private final ForceRepository repository;
    private final ForceMapper mapper;
    private final ForceValidator validator;

    @Override
    protected JpaRepository<Force, Long> repository() {
        return repository;
    }

    @Override
    public ForceResponse create(CreateForceRequest request) {

        validator.validateCreate(request.name());

        Force entity = mapper.toEntity(request);

        entity.setActive(true);

        return mapper.toResponse(save(entity));
    }

    @Override
    public ForceResponse update(Long id, UpdateForceRequest request) {

        Force entity = findOrThrow(id, ForceMessages.NOT_FOUND);

        validator.validateUpdate(id, request.name());

        mapper.updateEntity(request, entity);

        return mapper.toResponse(save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ForceResponse findById(Long id) {

        return mapper.toResponse(
                findOrThrow(id, ForceMessages.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ForceResponse> findAll(Pageable pageable) {

        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public void delete(Long id) {

        Force entity = findOrThrow(id, ForceMessages.NOT_FOUND);

        validator.validateDelete(entity);

        delete(entity);
    }

}