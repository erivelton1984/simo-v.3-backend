package br.com.ciccr.simo.modules.user.validator;

import br.com.ciccr.simo.common.exception.BusinessException;
import br.com.ciccr.simo.common.exception.ResourceNotFoundException;
import br.com.ciccr.simo.modules.role.constants.RoleMessages;
import br.com.ciccr.simo.modules.role.model.Role;
import br.com.ciccr.simo.modules.role.repository.RoleRepository;
import br.com.ciccr.simo.modules.user.constants.UserMessages;
import br.com.ciccr.simo.modules.user.model.User;
import br.com.ciccr.simo.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void validateEmailAvailable(String email) {

        if (userRepository.existsByEmail(email)) {
            throw new BusinessException("Já existe um usuário cadastrado com este e-mail.");
        }
    }

    public User validateUser(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado."));
    }

    public Role validateRole(Long roleId) {

        return roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Perfil não encontrado."));
    }

    public User findUserOrThrow(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(UserMessages.USER_NOT_FOUND));
    }

    public Role findRoleOrThrow(Long id) {

        return roleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(RoleMessages.ROLE_NOT_FOUND));
    }

    public void validatePasswordConfirmation(String newPassword, String confirmPassword) {

        if (!newPassword.equals(confirmPassword)) {
            throw new BusinessException(
                    UserMessages.PASSWORDS_DO_NOT_MATCH
            );
        }
    }

}