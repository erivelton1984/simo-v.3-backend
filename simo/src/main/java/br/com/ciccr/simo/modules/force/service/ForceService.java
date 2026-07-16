package br.com.ciccr.simo.modules.force.service;

import br.com.ciccr.simo.modules.force.dto.request.CreateForceRequest;
import br.com.ciccr.simo.modules.force.dto.request.UpdateForceRequest;
import br.com.ciccr.simo.modules.force.dto.response.ForceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ForceService {

    ForceResponse create(CreateForceRequest request);

    ForceResponse update(Long id, UpdateForceRequest request);

    ForceResponse findById(Long id);

    Page<ForceResponse> findAll(Pageable pageable);

    void delete(Long id);

}