package br.com.ciccr.simo.modules.user.service;

import br.com.ciccr.simo.common.exception.BusinessException;
import br.com.ciccr.simo.common.service.BaseService;
import br.com.ciccr.simo.modules.role.model.Role;
import br.com.ciccr.simo.modules.user.constants.UserMessages;
import br.com.ciccr.simo.modules.user.dto.request.ChangePasswordRequest;
import br.com.ciccr.simo.modules.user.dto.request.CreateUserRequest;
import br.com.ciccr.simo.modules.user.dto.request.UpdateUserRequest;
import br.com.ciccr.simo.modules.user.dto.response.UserResponse;
import br.com.ciccr.simo.modules.user.dto.response.UserSummaryResponse;
import br.com.ciccr.simo.modules.user.mapper.UserMapper;
import br.com.ciccr.simo.modules.user.model.User;
import br.com.ciccr.simo.modules.user.repository.UserRepository;
import br.com.ciccr.simo.modules.user.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService extends BaseService<User, Long> {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final UserValidator validator;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected UserRepository repository() {
        return repository;
    }

    public UserResponse create(CreateUserRequest request) {

        validator.validateEmailAvailable(request.email());

        Role role = validator.findRoleOrThrow(request.roleId());

        User user = mapper.toEntity(request);

        user.setRole(role);

        user.setPassword(
                passwordEncoder.encode(request.password())
        );

        User saved = repository.save(user);

        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {

        User user = validator.findUserOrThrow(id);

        return mapper.toResponse(user);
    }

    @Transactional(readOnly = true)
    public Page<UserSummaryResponse> findAll(Pageable pageable) {
        return repository
                .findAll(pageable)
                .map(mapper::toSummary);
    }

    public UserResponse update(Long id, UpdateUserRequest request) {

        User user = validator.findUserOrThrow(id);

        if (!user.getEmail().equals(request.email())) {
            validator.validateEmailAvailable(request.email());
        }

        Role role = validator.findRoleOrThrow(request.roleId());

        mapper.update(request, user);

        user.setRole(role);

        return mapper.toResponse(repository.save(user));
    }

    public void delete(Long id) {

        User user = validator.findUserOrThrow(id);

        user.setActive(false);

        repository.save(user);
    }

    public void changePassword(Long id, ChangePasswordRequest request) {

        User user = validator.findUserOrThrow(id);

        if (!passwordEncoder.matches(
                request.currentPassword(),
                user.getPassword())) {

            throw new BusinessException(UserMessages.INVALID_PASSWORD);
        }

        validator.validatePasswordConfirmation(
                request.newPassword(),
                request.confirmPassword()
        );

        user.setPassword(
                passwordEncoder.encode(
                        request.newPassword()
                )
        );

        repository.save(user);
    }

    public void activate(Long id) {

        User user = validator.findUserOrThrow(id);

        user.setActive(true);

        repository.save(user);
    }

    public void deactivate(Long id) {

        User user = validator.findUserOrThrow(id);

        user.setActive(false);

        repository.save(user);
    }

    public void lock(Long id) {

        User user = validator.findUserOrThrow(id);

        user.setLocked(true);

        repository.save(user);
    }

    public void unlock(Long id) {

        User user = validator.findUserOrThrow(id);

        user.setLocked(false);

        repository.save(user);
    }
}
