package com.studentManagement.service.validator;

import com.studentManagement.config.ApplicationConfig;
import com.studentManagement.model.Principal;
import com.studentManagement.model.User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalValidator {
    private ApplicationConfig applicationConfig;

    public PrincipalValidator(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    public Principal updatePrincipal(Principal oldPrincipal, User principalInfo) {
        oldPrincipal.setFirstName(
                principalInfo.getFirstName() == null || principalInfo.getFirstName().trim().isEmpty() ?
                        oldPrincipal.getFirstName() : principalInfo.getFirstName()
        );

        oldPrincipal.setLastName(
                principalInfo.getLastName() == null || principalInfo.getLastName().trim().isEmpty() ?
                        oldPrincipal.getLastName() : principalInfo.getLastName()
        );

        oldPrincipal.setEmail(
                principalInfo.getEmail() == null || principalInfo.getEmail().trim().isEmpty() ?
                        oldPrincipal.getEmail() : principalInfo.getEmail()
        );

        oldPrincipal.setPassword(
                principalInfo.getPassword() == null || principalInfo.getPassword().trim().isEmpty() ?
                        oldPrincipal.getPassword() : principalInfo.getPassword()
        );

        updateUser(oldPrincipal, principalInfo);

        return oldPrincipal;
    }

    private void updateUser(Principal principal, User principalInfo) {
        User user = principal.getInfo();
        user.setFirstName(principalInfo.getFirstName());
        user.setLastName(principalInfo.getLastName());
        user.setEmail(principalInfo.getEmail());
        user.setSsoId(principalInfo.getSsoId());
//        user.setPassword(applicationConfig.passwordEncoder().encode(principalInfo.getPassword()));
    }
}
