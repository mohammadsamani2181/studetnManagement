package com.studentManagement.service.impl;

import com.studentManagement.config.ApplicationConfig;
import com.studentManagement.config.PrincipalConfig;
import com.studentManagement.model.DTO.request.PrincipalDTOUpdateRequest;
import com.studentManagement.model.DTO.response.PrincipalDTOResponse;
import com.studentManagement.model.Principal;
import com.studentManagement.model.SchoolRole;
import com.studentManagement.model.User;
import com.studentManagement.repository.PrincipalRepository;
import com.studentManagement.service.PrincipalService;
import com.studentManagement.service.mapper.PrincipalDTOResponseMapper;
import com.studentManagement.service.validator.PrincipalValidator;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PrincipalServiceImpl implements PrincipalService {
    private PrincipalRepository principalRepository;
    private ApplicationConfig applicationConfig;
    private PrincipalValidator principalValidator;
    private PrincipalDTOResponseMapper principalDTOResponseMapper;
    private PrincipalConfig principalConfig;

    public PrincipalServiceImpl(PrincipalRepository principalRepository,
                                ApplicationConfig applicationConfig,
                                PrincipalValidator principalValidator,
                                PrincipalDTOResponseMapper principalDTOResponseMapper) {

        this.principalRepository = principalRepository;
        this.applicationConfig = applicationConfig;
        this.principalValidator = principalValidator;
        this.principalDTOResponseMapper = principalDTOResponseMapper;
        this.principalConfig = PrincipalConfig.getInstance();
    }

    @Override
    public Principal save(Principal principal) {
        Set<SchoolRole> authorities = new HashSet<>();
        authorities.add(SchoolRole.PRINCIPAL);
        authorities.add(SchoolRole.PRINCIPAL_ASSISTANT);
        authorities.add(SchoolRole.TEACHER);
        authorities.add(SchoolRole.STUDENT);

        User user = User.builder()
                .firstName(principal.getFirstName())
                .lastName(principal.getLastName())
                .email(principal.getEmail())
                .password(applicationConfig.passwordEncoder().encode(principal.getPassword()))
                .roles(authorities)
                .build();

        principal.setInfo(user);
        return principalRepository.saveAndFlush(principal);
    }

    @Override
    public PrincipalDTOResponse update(PrincipalDTOUpdateRequest principalDTOUpdateRequest) {

        return principalDTOResponseMapper.apply(principalRepository.save(
                principalValidator.updatePrincipal(principalConfig.getPrincipal(), principalDTOUpdateRequest)
        ));
    }

}
