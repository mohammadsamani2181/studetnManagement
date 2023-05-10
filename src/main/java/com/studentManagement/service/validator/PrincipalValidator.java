package com.studentManagement.service.validator;

import com.studentManagement.config.ApplicationConfig;
import com.studentManagement.model.DTO.request.PrincipalDTOUpdateRequest;
import com.studentManagement.model.Principal;
import org.springframework.stereotype.Service;

@Service
public class PrincipalValidator {
    private ApplicationConfig applicationConfig;

    public PrincipalValidator(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    public Principal updatePrincipal(Principal oldPrincipal, PrincipalDTOUpdateRequest newPrincipal) {
        oldPrincipal.setFirstName(
                newPrincipal.getFirstName() == null || newPrincipal.getFirstName().trim().isEmpty() ?
                        oldPrincipal.getFirstName() : newPrincipal.getFirstName()
        );

        oldPrincipal.setLastName(
                newPrincipal.getLastName() == null || newPrincipal.getLastName().trim().isEmpty() ?
                        oldPrincipal.getLastName() : newPrincipal.getLastName()
        );

        oldPrincipal.setEmail(
                newPrincipal.getEmail() == null || newPrincipal.getEmail().trim().isEmpty() ?
                        oldPrincipal.getEmail() : newPrincipal.getEmail()
        );

        oldPrincipal.setPassword(
                newPrincipal.getPassword() == null || newPrincipal.getPassword().trim().isEmpty() ?
                        oldPrincipal.getPassword() : newPrincipal.getPassword()
        );

        updateUser(oldPrincipal);

        return oldPrincipal;
    }

    private void updateUser(Principal principal) {
//        return User.builder()
//                .firstName(principal.getFirstName())
//                .lastName(principal.getLastName())
//                .email(principal.getEmail())
//                .password(applicationConfig.passwordEncoder().encode(principal.getPassword()))
//                .build();

        principal.getInfo().setFirstName(principal.getFirstName());
        principal.getInfo().setLastName(principal.getLastName());
        principal.getInfo().setEmail(principal.getEmail());
        principal.getInfo().setPassword(applicationConfig.passwordEncoder().encode(principal.getPassword()));
    }
}
