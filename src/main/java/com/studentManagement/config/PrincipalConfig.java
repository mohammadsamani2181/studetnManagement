package com.studentManagement.config;

import com.studentManagement.model.Principal;
import com.studentManagement.repository.PrincipalRepository;

public class PrincipalConfig {
    private static PrincipalConfig instance;
    private PrincipalRepository principalRepository;

    private PrincipalConfig() {
    }

    public void setPrincipalRepository(PrincipalRepository principalRepository) {
        this.principalRepository = principalRepository;
    }


    public static PrincipalConfig getInstance() {
        if (instance == null) {
            synchronized (Principal.class) {
                if (instance == null) {
                    instance = new PrincipalConfig();
                }
            }
        }
        return instance;
    }


    public Principal getPrincipal() {
        Principal existingPrincipal = principalRepository.getPrincipal();

        if (existingPrincipal == null) {
            Principal principal = Principal.builder()
                    .firstName("principal")
                    .lastName("principal")
                    .email("principal@gamil.com")
                    .password("principalPassword")
                    .build();


            return principalRepository.save(principal);
        }

        return existingPrincipal;
    }
}
