package br.com.ciccr.simo.modules.user.controller;

import br.com.ciccr.simo.common.controller.BaseController;
import br.com.ciccr.simo.common.response.ApiResult;
import br.com.ciccr.simo.modules.user.constants.UserMessages;
import br.com.ciccr.simo.modules.user.dto.request.ChangePasswordRequest;
import br.com.ciccr.simo.modules.user.dto.request.CreateUserRequest;
import br.com.ciccr.simo.modules.user.dto.request.UpdateUserRequest;
import br.com.ciccr.simo.modules.user.dto.response.UserResponse;
import br.com.ciccr.simo.modules.user.dto.response.UserSummaryResponse;
import br.com.ciccr.simo.modules.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<ApiResult<UserResponse>> create(@Valid @RequestBody CreateUserRequest request) {
        return created(UserMessages.USER_CREATED, service.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<UserResponse>> findById(@PathVariable Long id) {
        return ok(UserMessages.USER_NOT_FOUND, service.findById(id));
    }

    @GetMapping
    public ResponseEntity<ApiResult<Page<UserSummaryResponse>>> findAll(Pageable pageable) {
        return ok(UserMessages.USERS_NOT_FOUND, service.findAll(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<UserResponse>> update(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        return ok(UserMessages.USER_UPDATED, service.update(id, request));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<ApiResult<Void>> changePassword(@PathVariable Long id, @Valid @RequestBody ChangePasswordRequest request) {
        service.changePassword(id, request);
        return ok(UserMessages.PASSWORD_CHANGED);
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<ApiResult<Void>> activate(@PathVariable Long id) {
        service.activate(id);
        return ok(UserMessages.USER_ACTIVATED);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResult<Void>> deactivate(@PathVariable Long id) {
        service.deactivate(id);
        return ok(UserMessages.USER_DEACTIVATED);
    }

    @PatchMapping("/{id}/lock")
    public ResponseEntity<ApiResult<Void>> lock(@PathVariable Long id) {
        service.lock(id);
        return ok(UserMessages.USER_LOCKED);
    }

    @PatchMapping("/{id}/unlock")
    public ResponseEntity<ApiResult<Void>> unlock(@PathVariable Long id) {
        service.unlock(id);
        return ok(UserMessages.USER_UNLOCKED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return noContent();
    }

}