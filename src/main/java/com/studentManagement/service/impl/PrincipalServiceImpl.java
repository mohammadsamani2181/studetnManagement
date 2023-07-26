package com.studentManagement.service.impl;

import com.studentManagement.config.ApplicationConfig;
import com.studentManagement.config.PrincipalConfig;
import com.studentManagement.model.DTO.response.PrincipalDTOResponse;
import com.studentManagement.model.Principal;
import com.studentManagement.model.User;
import com.studentManagement.repository.PrincipalRepository;
import com.studentManagement.service.PrincipalService;
import com.studentManagement.service.UserService;
import com.studentManagement.service.mapper.PrincipalDTOResponseMapper;
import com.studentManagement.service.validator.PrincipalValidator;
import com.studentManagement.utils.ServiceUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class PrincipalServiceImpl implements PrincipalService {
    private PrincipalRepository principalRepository;
    private ApplicationConfig applicationConfig;
    private PrincipalValidator principalValidator;
    private PrincipalDTOResponseMapper principalDTOResponseMapper;
    private PrincipalConfig principalConfig;
    private UserService userService;

    public PrincipalServiceImpl(PrincipalRepository principalRepository,
                                ApplicationConfig applicationConfig,
                                PrincipalValidator principalValidator,
                                PrincipalDTOResponseMapper principalDTOResponseMapper, UserService userService) {

        this.principalRepository = principalRepository;
        this.applicationConfig = applicationConfig;
        this.principalValidator = principalValidator;
        this.principalDTOResponseMapper = principalDTOResponseMapper;
        this.userService = userService;
        this.principalConfig = PrincipalConfig.getInstance();
    }

    @Override
    public Principal save(Principal principal) {
        User user = User.builder()
                .firstName(principal.getFirstName())
                .lastName(principal.getLastName())
                .email(principal.getEmail())
                .password(applicationConfig.passwordEncoder().encode(principal.getPassword()))
                .ssoId(45L)
                .build();

        ServiceUtils.makePrincipal(user);

        principal.setInfo(user);
        return principalRepository.saveAndFlush(principal);
    }

//    @Override
//    public PrincipalDTOResponse update(PrincipalDTOUpdateRequest principalDTOUpdateRequest) {
//
//        return principalDTOResponseMapper.apply(principalRepository.save(
//                principalValidator.updatePrincipal(principalConfig.getPrincipal(), principalDTOUpdateRequest)
//        ));
//    }

    @Override
    public PrincipalDTOResponse update(Long userSsoId) {

        Optional<User> user = userService.getUserBySsoId(userSsoId);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no User with the given SsoID");
        } else {
            userService.deleteUser(user.get());
        }


        Principal principal = principalValidator.updatePrincipal(principalConfig.getPrincipal(), user.get());

        return principalDTOResponseMapper.apply(principalRepository.save(principal));
    }

}
